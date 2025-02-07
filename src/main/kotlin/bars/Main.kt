package bars

import kotlinx.html.dom.serialize
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.annotation.Id
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

data class Bar(@Id val id: Long?, val name: String)

@Repository
interface BarRepository: CrudRepository<Bar, Long>

@SpringBootApplication
@RestController
class Main(@Autowired val barRepository: BarRepository) {

    @GetMapping("/")
    fun index(): String = run {
        Html.index.serialize(true)
    }

    @GetMapping("/bars")
    fun getBars(): Iterable<Bar> = run {
        barRepository.findAll()
    }

    @PostMapping("/bars")
    fun createBar(@RequestBody bar: Bar): Unit = run {
        barRepository.save(bar)
    }
}

fun main(args: Array<String>) {
    runApplication<Main>(*args)
}
