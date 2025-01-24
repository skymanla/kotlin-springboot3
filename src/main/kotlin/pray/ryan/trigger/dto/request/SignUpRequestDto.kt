package pray.ryan.trigger.dto.request

import jakarta.validation.constraints.NotBlank
import pray.ryan.trigger.domain.User

data class SignUpRequestDto(
    @field:NotBlank(message = "id는 필수입니다")
    var id: String,

    @field:NotBlank(message = "닉네임은 필수입니다")
    var nick: String,

    @field:NotBlank(message = "패스워드는 필수입니다")
    var pwd: String,

    var name: String? = null
) {
    fun toEntity(): User = User(this.id, this.pwd, this.nick, this.name)
}