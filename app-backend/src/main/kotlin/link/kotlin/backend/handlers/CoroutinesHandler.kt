package link.kotlin.backend.handlers

import io.undertow.server.HttpHandler
import io.undertow.server.HttpServerExchange
import io.undertow.util.SameThreadExecutor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import link.kotlin.backend.logger
import kotlin.coroutines.CoroutineContext

/**
 * Analog of [io.undertow.server.HttpHandler] in coroutines world.
 *
 * @author Ruslan Ibragimov
 * @since 2020.03
 */
interface CoroutinesHandler {
    suspend fun handleRequest(exchange: HttpServerExchange)
}

/**
 * Bridge between thread and coroutines worlds.
 *
 * @author Ruslan Ibragimov
 * @since 2020.03
 */
class DefaultCoroutinesHandler(
    private val handler: CoroutinesHandler,
    private val context: CoroutineContext = Dispatchers.Unconfined
) : HttpHandler {
    override fun handleRequest(exchange: HttpServerExchange) {
        if (exchange.isDispatched) {
            LOGGER.info("Exchange already dispatched.")
            GlobalScope.launch(context = context) {
                handler.handleRequest(exchange)
            }
        } else {
            exchange.dispatch(SameThreadExecutor.INSTANCE, Runnable {
                GlobalScope.launch(context = context) {
                    handler.handleRequest(exchange)
                }
            })
        }
    }

    companion object {
        private val LOGGER = logger<CoroutinesHandler>()
    }
}

