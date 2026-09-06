package bars.shared

import kotlinx.serialization.Serializable

@Serializable
data class Bar(
    val id: Long,
    val name: String,
)
