package pray.ryan.trigger.utils

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import jakarta.annotation.PostConstruct
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import pray.ryan.trigger.dto.response.TokenResponseDto
import pray.ryan.trigger.exception.CommonException
import java.util.*

@Component
class JwtUtils(
    private val userDetailsService: UserDetailsService
) {
    private val AT_EXP_TIME: Long = 1000L * 60 * 3 // access token expiration time 3분
    private val RT_EXP_TIME: Long = 1000L * 60 * 60 * 5 // refresh token expiration time 5시간
    private var AT_JWT_SECRET: String = "trigger_at"
    private var RT_JWT_SECRET: String = "trigger_rt"
    private val SIGNATURE_ALG: SignatureAlgorithm = SignatureAlgorithm.HS256
    private val AT_KEY_CREATED = "created"
    private val RT_KEY_CREATED = "refresh"

    // at generated
    fun generatedAT(claims: Map<String, Any>): String {
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + AT_EXP_TIME))
            .signWith(SIGNATURE_ALG, AT_JWT_SECRET)
            .compact()
    }

    // rt generated
    fun generatedRT(claims: Map<String, Any>): String {
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + RT_EXP_TIME))
            .signWith(SIGNATURE_ALG, RT_JWT_SECRET)
            .compact()
    }

    // at + rt 로그인 시 2개 동시 생성
    fun generatedToken(id: String): TokenResponseDto {
        val at: String = createdAT(id)
        val rt: String = createdRT(id)

        return TokenResponseDto(at, rt)
    }

    fun createdAT(id: String): String {
        val claims: Claims = Jwts.claims()
        claims["id"] = id
        claims["type"] = AT_KEY_CREATED

        return generatedAT(claims)
    }

    fun createdRT(id: String): String {
        val claims: Claims = Jwts.claims()
        claims["id"] = id
        claims["type"] = RT_KEY_CREATED

        return generatedRT(claims)
    }

    // get token by header
    fun resolveATByHeader(request: HttpServletRequest): String {
        val requestHeader: String = request.getHeader("Authorization")
        return requestHeader.substring("Bearer ".length)
    }

    fun resolveRTByHeader(request: HttpServletRequest): String {
        return request.getHeader("refreshToken")
    }

    fun getAllClaimsAT(token: String): Claims {
        return Jwts.parser()
            .setSigningKey(AT_JWT_SECRET)
            .parseClaimsJws(token)
            .body
    }

    fun getAllClaimsRT(token: String): Claims {
        return Jwts.parser()
            .setSigningKey(RT_JWT_SECRET)
            .parseClaimsJws(token)
            .body
    }

    // refresh token 재발급 가능한 상태인지?
    fun validateRT(token: String): String {
        try {
            val claims: Claims = getAllClaimsRT(token)
            // 만료가 되지 않았다면 access token 재발행
            if (!claims.expiration.before(Date())) {
                // get id
                val id: String = parseIdByRT(token)
                return createdAT(id)
            }
            // 만료가 되었으니 exception 발생
            throw Exception("재로그인이 필요합니다")
        } catch (e: Exception) {
            throw CommonException("EXPIRED_RT", e.localizedMessage, HttpStatus.UNAUTHORIZED)
        }
    }

    fun parseIdByAT(token: String): String {
        val claims: Claims =  getAllClaimsAT(token)
        return claims["id"] as String
    }

    fun parseIdByRT(token: String): String {
        val claims: Claims = getAllClaimsRT(token)
        return claims["id"] as String
    }

    fun getAuthentication(id: String): Authentication {
        val userDetails: UserDetails = userDetailsService.loadUserByUsername(id)

        return UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
    }

    // at 살아있는지 여부
    fun validation(token: String): Boolean {
        val claims: Claims = getAllClaimsAT(token)
        val exp: Date = claims.expiration
        return exp.after(Date())
    }

    fun getATExpireTime(): Long = AT_EXP_TIME
    fun getRTExpireTime(): Long = RT_EXP_TIME
}