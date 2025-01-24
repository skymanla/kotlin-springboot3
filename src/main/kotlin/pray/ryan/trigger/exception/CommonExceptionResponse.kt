package pray.ryan.trigger.exception

data class CommonExceptionResponse(
    val message: String,
    val code: Any,
    val success: Boolean = false,
    val data: Any? = null
)