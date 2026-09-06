package bars.server

import bars.shared.Bar
import bars.shared.IBarService
import bars.shared.InvalidBarNameException
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Service

@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
class BarService(
    private val repository: BarRepository,
) : IBarService {
    override suspend fun listBars(): List<Bar> = repository.findAll()
        .asFlow()
        .map(BarRow::toBar)
        .toList()

    override suspend fun createBar(name: String): Bar {
        val normalizedName = name.trim()
        when {
            normalizedName.isEmpty() -> throw InvalidBarNameException("Bar name must not be blank")
            normalizedName.length > MAX_NAME_LENGTH -> throw InvalidBarNameException(
                "Bar name must be at most $MAX_NAME_LENGTH characters",
            )
        }

        return repository.save(BarRow(name = normalizedName)).awaitSingle().toBar()
    }

    private companion object {
        const val MAX_NAME_LENGTH = 255
    }
}
