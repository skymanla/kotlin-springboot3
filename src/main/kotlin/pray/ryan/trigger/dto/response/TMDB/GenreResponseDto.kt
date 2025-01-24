package pray.ryan.trigger.dto.response.TMDB

import java.util.Optional


class GenreResponseDto(
    val genres: Array<Genre>
)

interface Genre {
    val id: Int
    val name: String
}