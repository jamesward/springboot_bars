package bars.shared

import dev.kilua.rpc.AbstractServiceException
import dev.kilua.rpc.annotations.RpcService
import dev.kilua.rpc.annotations.RpcServiceException

@RpcServiceException
class InvalidBarNameException(override val message: String) : AbstractServiceException()

@RpcService
interface IBarService {
    suspend fun listBars(): List<Bar>

    suspend fun createBar(name: String): Bar
}
