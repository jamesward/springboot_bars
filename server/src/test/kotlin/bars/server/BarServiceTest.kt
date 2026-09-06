package bars.server

import bars.shared.InvalidBarNameException
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@SpringBootTest(classes = [WebApp::class, PostgresTestConfiguration::class])
class BarServiceTest @Autowired constructor(
    private val barService: BarService,
    private val repository: BarRepository,
    private val applicationContext: ApplicationContext,
) {
    @BeforeEach
    fun clearDatabase(): Unit = runBlocking {
        repository.deleteAll().awaitFirstOrNull()
    }

    @Test
    fun `creates and lists bars through the shared service`(): Unit = runBlocking {
        val created = barService.createBar("  The Kotlin Arms  ")
        val bars = barService.listBars()

        assertTrue(created.id > 0)
        assertEquals("The Kotlin Arms", created.name)
        assertEquals(listOf(created), bars)
    }

    @Test
    fun `rejects blank bar names`(): Unit = runBlocking {
        val exception = assertFailsWith<InvalidBarNameException> {
            barService.createBar("   ")
        }

        assertEquals("Bar name must not be blank", exception.message)
        assertTrue(barService.listBars().isEmpty())
    }

    @Test
    fun `serves the Wasm host page without authentication`() {
        val body = WebTestClient.bindToApplicationContext(applicationContext)
            .build()
            .get()
            .uri("/")
            .exchange()
            .expectStatus().isOk
            .expectHeader().contentTypeCompatibleWith(MediaType.TEXT_HTML)
            .expectBody<String>()
            .returnResult()
            .responseBody

        assertNotNull(body)
        assertTrue(body.contains("id=\"root\""))
        assertTrue(body.contains("src=\"web.js\""))
    }
}
