package pray.ryan.trigger.service

import okhttp3.OkHttpClient
import org.springframework.stereotype.Service
import pray.ryan.trigger.utils.RetrofitProvider
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Service
class RetrofitService {
    fun retrofitBuild(url: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .client(provideOkHttpClient(RetrofitProvider()))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun provideOkHttpClient(interceptor: RetrofitProvider): OkHttpClient
            = OkHttpClient.Builder().run {
        addInterceptor(interceptor)
        build()
    }
}