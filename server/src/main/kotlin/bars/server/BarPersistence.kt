package bars.server

import bars.shared.Bar
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.reactive.ReactiveCrudRepository

@Table("bar")
data class BarRow(
    @Id val id: Long? = null,
    val name: String,
) {
    fun toBar(): Bar = Bar(
        id = requireNotNull(id) { "A persisted bar must have an id" },
        name = name,
    )
}

interface BarRepository : ReactiveCrudRepository<BarRow, Long>
