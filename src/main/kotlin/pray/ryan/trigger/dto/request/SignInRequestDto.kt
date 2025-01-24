package pray.ryan.trigger.dto.request

import jakarta.validation.constraints.NotBlank

data class SignInRequestDto(
    @field:NotBlank(message = "id는 필수입니다")
    val id: String,

    @field:NotBlank(message = "패스워드는 필수입니다")
    val pwd: String
)