package link.kotlin.backend.services

import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.features.json.JacksonSerializer
import io.ktor.client.features.json.JsonFeature
import link.kotlin.backend.logger
import kotlin.concurrent.thread

fun createHttpClient(): HttpClient {
    val asyncClient = HttpClient(Apache) {
        install(JsonFeature) {
            serializer = JacksonSerializer()
        }
    }

    Runtime.getRuntime().addShutdownHook(thread(start = false) {
        logger<HttpClient>().info("HttpClient Shutdown called...")
        try {
            asyncClient.close()
            logger<HttpClient>().info("HttpClient Shutdown done...")
        } catch (e: Exception) {
            logger<HttpClient>().info("HttpClient Shutdown exceptionally", e)
        }
    })

    return asyncClient
}
