plugins {
    id("org.springframework.boot") version "3.3.0"
    id("io.spring.dependency-management") version "1.1.5"
    id("org.asciidoctor.jvm.convert") version "3.3.2"
    kotlin("plugin.jpa") version "1.9.24"
    kotlin("jvm") version "1.9.24"
    kotlin("plugin.spring") version "1.9.24"
}

group = "com.esep"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        java
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

extra["snippetsDir"] = file("build/generated-snippets")

dependencies {
    implementation("org.apache.logging.log4j:log4j-api:2.14.1")
    implementation("org.apache.logging.log4j:log4j-core:2.14.1")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-websocket")
    implementation("org.springframework.boot:spring-boot-starter-mail")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-database-postgresql")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.session:spring-session-core")
    implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6")
    implementation("com.twilio.sdk:twilio:8.25.0")
    implementation("org.springframework.boot:spring-boot-starter-mail:3.1.5")
    implementation("org.apache.mahout:mahout-mr:0.13.0")
    implementation("org.apache.mahout:mahout-math:0.13.0")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    developmentOnly("org.springframework.boot:spring-boot-docker-compose")

    runtimeOnly("com.h2database:h2")
    runtimeOnly("org.postgresql:postgresql")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
    testImplementation("io.cucumber:cucumber-java:7.14.0")
    testImplementation("io.cucumber:cucumber-junit:7.14.0")
    testImplementation("io.cucumber:cucumber-spring:7.14.0")
    testImplementation("io.cucumber:cucumber-junit-platform-engine:7.14.0")
    testImplementation("io.cucumber:cucumber-bom:7.14.0")
    testRuntimeOnly("org.junit.platform:junit-platform-console-standalone:1.8.2")

    // https://mvnrepository.com/artifact/io.kotest/kotest-assertions-core-jvm
    testImplementation("io.kotest:kotest-assertions-core-jvm:5.9.1")

//	// https://mvnrepository.com/artifact/io.kotest/kotest-runner-junit5-jvm
//	testImplementation("io.kotest:kotest-runner-junit5-jvm:5.9.1")
    // https://mvnrepository.com/artifact/io.mockk/mockk
    testImplementation("io.mockk:mockk:1.13.11")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.test {
    outputs.dir(project.extra["snippetsDir"]!!)
}

tasks.asciidoctor {
    inputs.dir(project.extra["snippetsDir"]!!)
    dependsOn(tasks.test)
}

tasks {
    val consoleLauncherTest by registering(JavaExec::class) {
        dependsOn(testClasses)
        doFirst {
            println("Running parallel test")
        }
        classpath = sourceSets["test"].runtimeClasspath
        mainClass.set("org.junit.platform.console.ConsoleLauncher")
        args("--include-engine", "cucumber")
        args("--details", "tree")
        args("--scan-classpath")

        systemProperty("cucumber.execution.parallel.enabled", true)
        systemProperty("cucumber.execution.parallel.config.strategy", "dynamic")
        systemProperty(
            "cucumber.plugin",
            "pretty, summary, timeline:build/reports/timeline, html:build/reports/cucumber.html"
        )
        systemProperty("cucumber.publish.quiet", true)
    }

    register("cucumber", JavaExec::class) {
        dependsOn(testClasses)
        doFirst {
            println("Running Cucumber tests")
        }
        classpath = sourceSets["test"].runtimeClasspath
        mainClass.set("org.junit.platform.console.ConsoleLauncher")
        args("--include-engine", "cucumber")
        args("--details", "tree")
        args("--scan-classpath")

        systemProperty("cucumber.execution.parallel.enabled", "true")
        systemProperty("cucumber.execution.parallel.config.strategy", "dynamic")
        systemProperty(
            "cucumber.plugin",
            "pretty, summary, timeline:build/reports/timeline, html:build/reports/cucumber.html"
        )
        systemProperty("cucumber.publish.quiet", "true")
    }
}
