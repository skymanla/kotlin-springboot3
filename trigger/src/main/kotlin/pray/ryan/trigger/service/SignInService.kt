package pray.ryan.trigger.service

import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pray.ryan.trigger.domain.repository.UserRepository
import pray.ryan.trigger.dto.request.SignInRequestDto
import pray.ryan.trigger.dto.response.TokenResponseDto
import pray.ryan.trigger.exception.CommonException
import pray.ryan.trigger.utils.JwtUtils

@Service
@Slf4j
class SignInService(
    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val jwtUtils: JwtUtils,
) {

    private val logger: Logger = LoggerFactory.getLogger(SignInService::class.java)

    @Transactional(readOnly = true)
    fun singIn(signInRequestDto: SignInRequestDto): TokenResponseDto {
        try {
            // id 존재 여부
            val user = userRepository.findById(signInRequestDto.id) ?: throw CommonException("NON_EXISTS_ID", "존재하지 않는 ID 입니다")

            // password 확인
            if (!passwordEncoder.matches(signInRequestDto.pwd, user.pwd)) {
                throw CommonException("NOT_MATCH_PASSWORD", "패스워드를 확인해주세요")
            }

            // jwt token 생성
            val at: String = jwtUtils.createdAT(user.id)
            val rt: String = jwtUtils.createdRT(user.id)
            return TokenResponseDto(at, rt)
        } catch (e: CommonException) {
            throw e
        }
    }
}