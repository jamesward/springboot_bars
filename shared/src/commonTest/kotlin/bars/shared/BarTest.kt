package bars.shared

import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class BarTest {
    @Test
    fun serializesAndDeserializes() {
        val bar = Bar(id = 42, name = "The Kotlin Arms")

        val encoded = Json.encodeToString(bar)
        val decoded = Json.decodeFromString<Bar>(encoded)

        assertEquals(bar, decoded)
    }
}
