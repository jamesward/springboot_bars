package bars

import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.html.dom.serialize
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.annotation.Id
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.http.MediaType
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.server.*

data class Bar(@Id val id: Long?, val name: String)

@Repository
interface BarRepository: ReactiveCrudRepository<Bar, Long>

@SpringBootApplication
@RestController
class WebApp {

    @Bean
    fun http(@Autowired barRepository: BarRepository) = coRouter {
        GET("/") {
            ServerResponse.ok()
                .contentType(MediaType.TEXT_HTML)
                .bodyValueAndAwait(Html.index.serialize(true))
        }
        GET("/bars") {
            ServerResponse.ok().bodyAndAwait(barRepository.findAll().asFlow())
        }
        POST("/bars") { request ->
            val bar = request.awaitBody<Bar>()
            val saved = barRepository.save(bar).awaitFirstOrNull()
            if (saved == null) {
                ServerResponse.badRequest().buildAndAwait()
            }
            else {
                ServerResponse.ok().buildAndAwait()
            }
        }
    }
}

fun main(args: Array<String>) {
    runApplication<WebApp>(*args)
}
