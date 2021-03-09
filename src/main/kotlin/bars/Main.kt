package bars

import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.html.dom.serialize
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.annotation.Id
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

data class Bar(@Id val id: Long?, val name: String)

@Repository
interface BarRepository: ReactiveCrudRepository<Bar, Long>

@SpringBootApplication
@RestController
class WebApp(val barRepository: BarRepository) {

    @GetMapping("/")
    fun index(): String {
        return Html.index.serialize(false)
    }

    @GetMapping("/bars")
    suspend fun getBars() = run {
        barRepository.findAll().collectList().awaitFirst()
    }

    @PostMapping("/bars")
    suspend fun addBar(@RequestBody bar: Bar): Unit = run {
        val saved = barRepository.save(bar).awaitFirstOrNull()

        if (saved == null) {
            ResponseEntity<Unit>(HttpStatus.INTERNAL_SERVER_ERROR)
        } else {
            ResponseEntity<Unit>(HttpStatus.NO_CONTENT)
        }
    }

}

fun main(args: Array<String>) {
    runApplication<WebApp>(*args)
}