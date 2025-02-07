package bars

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.devtools.restart.RestartScope
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Testcontainers

@Configuration
@Testcontainers
class PostgresTestConfiguration {
    @Bean
    @ServiceConnection
    @RestartScope
    fun postgres() = PostgreSQLContainer("postgres:17")
}

@SpringBootTest
class BarTests(@Autowired val barRepository: BarRepository) {

    @Test
    fun `save must work`() {
        val createdBar = barRepository.save(Bar(null, "asdf"))
        val foundBar = createdBar.id?.let { barRepository.findById(it) }?.get()
        assert(createdBar == foundBar)
    }

    @Test
    fun `count must be 1`() {
        assert(barRepository.count() == 1L)
    }

}
