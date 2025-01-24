package pray.ryan.trigger.utils

import okhttp3.Interceptor
import okhttp3.Response
import org.springframework.beans.factory.annotation.Value
import java.io.IOException


class RetrofitProvider: Interceptor {

    @Value("\${tmdb.token}")
    private lateinit var token: String

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain) : Response = with(chain) {
        val newRequest = request().newBuilder()
            .addHeader("Bearer", token)
            .build()
        proceed(newRequest)
    }
}