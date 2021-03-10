package bars

import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class BarTests(@Autowired val barRepository: BarRepository) {

    @Test
    fun `save must work`(): Unit = runBlocking {
        val createdBar = barRepository.save(Bar(null, "asdf")).awaitFirst()
        val foundBar = createdBar.id?.let { barRepository.findById(it).awaitFirst() }
        assert(createdBar == foundBar)
    }

    @Test
    fun `count must be 1`(): Unit = runBlocking {
        assert(barRepository.count().awaitFirst() == 1L)
    }

}