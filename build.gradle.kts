
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.1.5"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.21"
    kotlin("plugin.spring") version "1.9.21"
    kotlin("plugin.jpa") version "1.9.21"
    kotlin("plugin.lombok") version "1.9.21"
    id("io.freefair.lombok") version "8.1.0"
    id("org.jlleitschuh.gradle.ktlint") version "11.4.0"
    kotlin("kapt") version "1.9.21"
}

allprojects {
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "kotlin")
    apply(plugin = "kotlin-kapt")
    apply(plugin = "kotlin-spring")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "org.jetbrains.kotlin.plugin.jpa")
    apply(plugin = "org.jetbrains.kotlin.plugin.lombok")
    apply(plugin = "io.freefair.lombok")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    group = "com.sim"
    version = "0.0.1-SNAPSHOT"

    java {
        sourceCompatibility = JavaVersion.VERSION_17
    }

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
//        // web
//        implementation("org.springframework.boot:spring-boot-starter-web")

        // lombok
        implementation("org.projectlombok:lombok")
        annotationProcessor("org.projectlombok:lombok")

        // jpa
//        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        // h2
        testImplementation("com.h2database:h2")

        testImplementation("org.springframework.boot:spring-boot-starter-test")

        // ninja-squad:springmockk
        testImplementation("com.ninja-squad:springmockk:3.1.1")

        // mysql
        runtimeOnly("com.mysql:mysql-connector-j")

        // querydsl
        implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
        kapt("com.querydsl:querydsl-apt:5.0.0:jakarta")


        implementation("io.github.microutils:kotlin-logging:3.0.5")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += "-Xjsr305=strict"
            jvmTarget = "17"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}
