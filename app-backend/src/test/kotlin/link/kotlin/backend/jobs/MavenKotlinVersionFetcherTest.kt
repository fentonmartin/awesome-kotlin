package link.kotlin.backend.jobs

import kotlinx.coroutines.runBlocking
import link.kotlin.backend.jobs.KotlinVersion.Type.STABLE
import link.kotlin.backend.services.createHttpClient
import link.kotlin.backend.services.createXmlMapper
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 * @author Ruslan Ibragimov
 * @since 2020.03
 */
class MavenKotlinVersionFetcherTest  {
    @Test
    fun `test existing version present`() = runBlocking {
        val fetcher = MavenKotlinVersionFetcher(createHttpClient(), createXmlMapper())

        val versions = fetcher.getVersions()
        assertTrue { versions.size > 228 }
        assertTrue { versions.contains(KotlinVersion(STABLE, "1.3.71")) }
    }
}
