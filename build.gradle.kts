val springVersion = "2.5.5"

plugins {
    id("org.springframework.boot") version "2.5.5"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("java")
    id("org.liquibase.gradle") version "2.0.4"
}

group = "bym"
version = "0.0.1-SNAPSHOT"
val jdbcDriver = "org.postgresql.Driver"

repositories {
    mavenCentral()
}

dependencies {
    // Spring boot
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-elasticsearch")

    // Database
    implementation("org.liquibase:liquibase-core")
    runtimeOnly ("org.postgresql:postgresql")

    // Tests
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:postgresql:1.16.0")

    // Other
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // Documentation
    implementation("org.springdoc:springdoc-openapi-ui:1.5.11")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
