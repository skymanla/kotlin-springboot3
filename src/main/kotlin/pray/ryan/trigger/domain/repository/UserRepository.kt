package pray.ryan.trigger.domain.repository

import org.springframework.data.jpa.repository.JpaRepository
import pray.ryan.trigger.domain.User

interface UserRepository: JpaRepository<User, Int> {
    fun findById(id: String): User?
    fun findByNick(nick: String): User?
}