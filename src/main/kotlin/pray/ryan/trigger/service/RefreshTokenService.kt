package pray.ryan.trigger.service

import org.springframework.stereotype.Service
import pray.ryan.trigger.dto.response.TokenResponseDto
import pray.ryan.trigger.utils.JwtUtils

@Service
class RefreshTokenService(
    private val jwtUtils: JwtUtils
) {

    fun refreshToken(refreshToken: String): TokenResponseDto {
        val newAccessToken = jwtUtils.validateRT(refreshToken)
        return TokenResponseDto(newAccessToken, refreshToken)
    }
}