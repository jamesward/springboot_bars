package bars.web

import bars.shared.Bar
import kotlin.test.Test
import kotlin.test.assertEquals

class AppTest {
    @Test
    fun appendsCreatedBarWithoutMutatingExistingList() {
        val existing = listOf(Bar(1, "First"))
        val updated = existing + Bar(2, "Second")

        assertEquals(listOf(Bar(1, "First")), existing)
        assertEquals(listOf(Bar(1, "First"), Bar(2, "Second")), updated)
    }
}
