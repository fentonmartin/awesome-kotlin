plugins {
    kotlin("jvm").version("1.3.71")
    application
}

application {
    applicationName = "awesome"
    mainClassName = "link.kotlin.backend.Backend"
}

repositories {
    jcenter()
    maven { url = uri("https://dl.bintray.com/heapy/heap") }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.3.5")

    val jacksonVersion = "2.10.3"
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:$jacksonVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")

    implementation("io.github.config4k:config4k:0.4.1")
    implementation("io.undertow:undertow-core:2.0.30.Final")

    val koinVersion = "2.1.1"
    implementation("org.koin:koin-core-ext:$koinVersion")
    implementation("org.koin:koin-logger-slf4j:$koinVersion")

    implementation("org.slf4j:slf4j-api:1.7.30")
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("io.sentry:sentry-logback:1.7.30")

    implementation("com.rometools:rome:1.12.2")
    implementation("com.github.dfabulich:sitemapgen4j:1.1.2")
    implementation("org.jsoup:jsoup:1.13.1")
    implementation("by.heap.remark:remark-kotlin:1.2.0")

    val commonmarkVersion = "0.14.0"
    implementation("com.atlassian.commonmark:commonmark:$commonmarkVersion")
    implementation("com.atlassian.commonmark:commonmark-ext-gfm-tables:$commonmarkVersion")

    val ktorVersion = "1.3.1"
    implementation("io.ktor:ktor-client-jackson:$ktorVersion")
    implementation("io.ktor:ktor-client-apache:$ktorVersion")

    testImplementation("io.mockk:mockk:1.9.3")

    val junitVersion = "5.6.1"
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
}
