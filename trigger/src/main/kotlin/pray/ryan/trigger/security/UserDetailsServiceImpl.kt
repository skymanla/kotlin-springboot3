package pray.ryan.trigger.security

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import pray.ryan.trigger.domain.User
import pray.ryan.trigger.domain.repository.UserRepository

@Service
class UserDetailsServiceImpl(
    private val userRepository: UserRepository
): UserDetailsService {

    override fun loadUserByUsername(id: String?): UserDetails {
        val user: User = (id?.let { userRepository.findById(it) } ?: throw UsernameNotFoundException("'${id}'는 존재하지 않습니다")) as User

        return UserDetailsImpl(user)
    }
}