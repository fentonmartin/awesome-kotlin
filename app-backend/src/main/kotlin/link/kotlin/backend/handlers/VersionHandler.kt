package link.kotlin.backend.handlers

import com.fasterxml.jackson.databind.ObjectMapper
import io.undertow.server.HttpHandler
import io.undertow.server.HttpServerExchange
import io.undertow.util.Headers
import link.kotlin.backend.jobs.KotlinVersionFetcher

/**
 * @author Ruslan Ibragimov
 * @since 2020.03
 */
class VersionHandler(
    private val kotlinVersionFetcher: KotlinVersionFetcher,
    private val objectMapper: ObjectMapper
) : CoroutinesHandler {
    override suspend fun handleRequest(exchange: HttpServerExchange) {
        exchange.responseHeaders.put(Headers.CONTENT_TYPE, "application/json")
        exchange.responseSender.send(objectMapper.writeValueAsString(kotlinVersionFetcher.getVersions()))
    }
}
