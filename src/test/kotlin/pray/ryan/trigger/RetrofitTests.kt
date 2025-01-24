package pray.ryan.trigger

import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import pray.ryan.trigger.dto.retrofit.TMDBInterface
import pray.ryan.trigger.service.RetrofitService

@SpringBootTest
class RetrofitTests @Autowired constructor(
    private val retrofitService: RetrofitService
) {

    private val logger: Logger = LoggerFactory.getLogger(RetrofitTests::class.java)

    @Test
    fun retrofitConnTest() {
        // val url: String = "https://api.themoviedb.org/3/"
        // val retrofitBuild = retrofitService.retrofitBuild(url)
        // logger.info(url)
        // val retrofitCreate = retrofitBuild.create(TMDBInterface::class.java)

        // val getGenre = retrofitCreate.getGenreMovie().execute()
        // val data = getGenre.body()?.genres
    }
}