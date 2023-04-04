package pray.ryan.trigger.dto.retrofit

import pray.ryan.trigger.dto.response.TMDB.GenreResponseDto
import retrofit2.Call
import retrofit2.http.GET

interface TMDBInterface {

    @GET("/genre/movie/list?language=ko")
    fun getGenreMovie(): Call<GenreResponseDto>
}