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
import pray.ryan.trigger.dto.request.SignInRequestDto
import pray.ryan.trigger.dto.request.SignUpRequestDto
import pray.ryan.trigger.dto.response.RestResponseDto
import pray.ryan.trigger.dto.response.TokenResponseDto
import pray.ryan.trigger.service.RefreshTokenService
import pray.ryan.trigger.service.SignInService
import pray.ryan.trigger.service.SignUpService
import pray.ryan.trigger.utils.JwtUtils

@RestController
class UserController(
    private val signUpService: SignUpService,
    private val signInService: SignInService,
    private val jwtUtils: JwtUtils,
    private val refreshTokenService: RefreshTokenService
) {
    @PostMapping("/sign-up")
    fun signUp(@RequestBody @Valid signUpRequestDto: SignUpRequestDto): ResponseEntity<RestResponseDto> {
        signUpService.saveUser(signUpRequestDto)
        return ResponseEntity.status(201).body(RestResponseDto(true, "가입에 성공했습니다"))
    }

    @PostMapping("/sign-in")
    fun signIn(@RequestBody @Valid signInRequestDto: SignInRequestDto, response: HttpServletResponse): ResponseEntity<RestResponseDto> {
        val token: TokenResponseDto = signInService.singIn(signInRequestDto)

        val atResponse: HashMap<String, Any> = HashMap<String, Any>()
        atResponse["at"] = token.at

        // response header set cookie
        val cookie: ResponseCookie = ResponseCookie.from("refreshToken", token.rt)
            .maxAge(jwtUtils.getRTExpireTime())
            .path("/")
            .secure(true)
            .sameSite("None")
            .httpOnly(true)
            .build()

        return ResponseEntity
            .ok()
            .header(SET_COOKIE, cookie.toString())
            .body(RestResponseDto(true, "", atResponse))
    }

    @PostMapping("/refresh")
    fun refreshToken(@CookieValue("refreshToken") refreshToken: String): ResponseEntity<RestResponseDto> {
        return ResponseEntity.ok().body(RestResponseDto(true, "", refreshTokenService.refreshToken(refreshToken)))
    }
}
