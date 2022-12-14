package pray.ryan.trigger.exception

import org.springframework.http.HttpStatus
import java.lang.RuntimeException

data class CommonException(
    val code: Any,
    override val message: String,
    val status: HttpStatus? = null
): RuntimeException(message)