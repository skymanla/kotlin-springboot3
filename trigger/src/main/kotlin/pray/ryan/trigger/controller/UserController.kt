package pray.ryan.trigger.controller

import jakarta.validation.Valid
import jakarta.validation.ValidationException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import pray.ryan.trigger.dto.request.SignUpRequestDto
import pray.ryan.trigger.service.SignUpService

@RestController
class UserController(
    private val signUpService: SignUpService
) {
    @PostMapping("/sign-up")
    fun signUp(@RequestBody @Valid signUpRequestDto: SignUpRequestDto): ResponseEntity<*> {
        return try {
            ResponseEntity.status(201).body("")
        } catch (e: MethodArgumentNotValidException) {
            ResponseEntity.badRequest().body(e)
        }
    }
}