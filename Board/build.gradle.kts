dependencies {
    // kotest
    testImplementation("io.kotest:kotest-runner-junit5:5.6.2")
    testImplementation("io.kotest:kotest-assertions-core:5.6.2")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.3")

    // swagger
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0")

    // Redis
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    testImplementation("io.kotest.extensions:kotest-extensions-testcontainers:2.0.2")
}
