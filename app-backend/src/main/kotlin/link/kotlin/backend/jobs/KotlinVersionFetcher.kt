package link.kotlin.backend.jobs

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import link.kotlin.backend.jobs.KotlinVersion.Type.EAP
import link.kotlin.backend.jobs.KotlinVersion.Type.STABLE

/**
 * Fetches kotlin version
 *
 * @author Ruslan Ibragimov
 * @since 2020.03
 */
interface KotlinVersionFetcher {
    suspend fun getVersions(): List<KotlinVersion>
}

class MavenKotlinVersionFetcher(
    private val httpClient: HttpClient,
    private val xmlMapper: XmlMapper
) : KotlinVersionFetcher {
    override suspend fun getVersions(): List<KotlinVersion> {
        val urls = mapOf(
            STABLE to "https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-stdlib/maven-metadata.xml",
            EAP to "https://dl.bintray.com/kotlin/kotlin-eap/org/jetbrains/kotlin/kotlin-stdlib/maven-metadata.xml"
        )

        return urls.map { (type, url) ->
            val xml = httpClient.get<String>(url)
            val metadata = xmlMapper.readValue<MavenMetadata>(xml)
            metadata.versioning.versions.map { version ->
                KotlinVersion(type, version)
            }
        }.flatten()
    }
}

data class KotlinVersion(
    val type: Type,
    val value: String
) {
    enum class Type {
        STABLE,
        EAP
    }
}

@JsonIgnoreProperties("groupId", "artifactId", "version")
data class MavenMetadata(
    val versioning: MavenVersioning
)

@JsonIgnoreProperties("latest", "release", "lastUpdated")
data class MavenVersioning(
    val versions: List<String>
)
