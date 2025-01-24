package pray.ryan.trigger.controller

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import jakarta.validation.ValidationException
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpHeaders.SET_COOKIE
import org.springframework.http.ResponseCookie
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import io.swagger.v3.oas.annotations.Hidden
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import pray.ryan.trigger.dto.request.SignInRequestDto
import pray.ryan.trigger.dto.request.SignUpRequestDto
import pray.ryan.trigger.dto.response.RestResponseDto
import pray.ryan.trigger.dto.response.TokenResponseDto
import pray.ryan.trigger.service.RefreshTokenService
import pray.ryan.trigger.service.SignInService
import pray.ryan.trigger.service.SignUpService
import pray.ryan.trigger.utils.JwtUtils

@Tag(name = "예제 API", description = "Swagger 테스트용 API")
@RestController
class UserController(
    private val signUpService: SignUpService,
    private val signInService: SignInService,
    private val jwtUtils: JwtUtils,
    private val refreshTokenService: RefreshTokenService
) {
    @Operation(summary = "문자열 반복", description = "파라미터로 받은 문자열을 2번 반복합니다.")
    @Parameter(name = "str", description = "2번 반복할 문자열")
    @PostMapping("/sign-up")
    fun signUp(@RequestBody @Valid signUpRequestDto: SignUpRequestDto): ResponseEntity<RestResponseDto> {
        signUpService.saveUser(signUpRequestDto)
        return ResponseEntity.status(201).body(RestResponseDto(true, "가입에 성공했습니다"))
    }

    @Hidden
    @PostMapping("/sign-in")
    fun signIn(@RequestBody @Valid signInRequestDto: SignInRequestDto, response: HttpServletResponse): ResponseEntity<RestResponseDto> {
        val tokenResponse = signInService.singIn(signInRequestDto)

        val tokenResponseMap: MutableMap<String, Any> = mutableMapOf()
        tokenResponseMap["at"] = tokenResponse.at

        // response header set cookie
        val refreshTokenCookie = ResponseCookie.from("refreshToken", tokenResponse.rt)
            .maxAge(jwtUtils.getRTExpireTime())
            .path("/")
            .secure(true)
            .sameSite("None")
            .httpOnly(true)
            .build()

        return ResponseEntity
            .ok()
            .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
            .body(RestResponseDto(true, "", tokenResponseMap))
    }

    @PostMapping("/refresh")
    fun handleRefreshToken(@CookieValue("refreshToken") token: String): ResponseEntity<RestResponseDto> {
        val tokenResponseDto = refreshTokenService.refreshToken(token)
        return ResponseEntity.ok().body(RestResponseDto(success = true, message = "", data = tokenResponseDto))
    }
}
