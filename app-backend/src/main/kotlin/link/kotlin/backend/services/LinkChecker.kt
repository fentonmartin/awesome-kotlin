package link.kotlin.backend.services

import io.ktor.client.HttpClient
import io.ktor.client.request.head
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode.Companion.NotFound
import io.ktor.http.HttpStatusCode.Companion.OK
import kotlinx.coroutines.withTimeoutOrNull
import link.kotlin.backend.logger

interface LinkChecker {
    suspend fun check(link: String): LinkStatus
}

internal class DefaultLinkChecker(
    private val httpClient: HttpClient
) : LinkChecker {
    override suspend fun check(link: String): LinkStatus {
        return try {
            withTimeoutOrNull(10000) {
                httpClient.head<HttpResponse>(link)
            }?.run {
                when (status) {
                    NotFound -> {
                        LOGGER.error("NOT FOUND: $link")
                        LinkStatus.NOT_FOUND
                    }
                    OK -> LinkStatus.OK
                    else -> {
                        LOGGER.error("${status}: $link")
                        LinkStatus.ERROR("Http Status: $status")
                    }
                }
            } ?: LinkStatus.TIMEOUT
        } catch (e: Exception) {
            LOGGER.error("Error checking link $link.", e)
            LinkStatus.ERROR(e.message ?: "Unknown error")
        }
    }

    companion object {
        private val LOGGER = logger<LinkChecker>()
    }
}

sealed class LinkStatus {
    object OK : LinkStatus()
    object NOT_FOUND : LinkStatus()
    object TIMEOUT : LinkStatus()
    class ERROR(val message: String) : LinkStatus()
}
