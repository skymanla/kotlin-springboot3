package pray.ryan.trigger.service

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pray.ryan.trigger.domain.User
import pray.ryan.trigger.domain.repository.UserRepository
import pray.ryan.trigger.dto.request.SignUpRequestDto

@Service
class SignUpService(
    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder
) {
    @Transactional
    fun saveUser(signUpRequestDto: SignUpRequestDto) {
        try {
            // 존재하는 id인지?
            val user: User? = userRepository.findById(signUpRequestDto.id)
            if (user != null) {
                throw RuntimeException("exists user id")
            }
            val nick: User? = userRepository.findByNick(signUpRequestDto.nick)
            if (nick != null) {
                throw RuntimeException("exists user nick")
            }

            // save
            signUpRequestDto.pwd = passwordEncoder.encode(signUpRequestDto.pwd)
            userRepository.save(signUpRequestDto.toEntity())
        } catch (e: RuntimeException) {
            throw e
        }
    }
}