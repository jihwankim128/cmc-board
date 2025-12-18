plugins {
    java
    jacoco
    id("org.springframework.boot") version "4.0.0"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "cmc"
version = "0.0.1-SNAPSHOT"
description = "board"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

val querydslVersion = "7.1"

dependencies {
    // Spring
    implementation("org.springframework.boot:spring-boot-starter-webmvc")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // Database
    runtimeOnly("com.mysql:mysql-connector-j")
    implementation("io.github.openfeign.querydsl:querydsl-jpa:${querydslVersion}")
    annotationProcessor("io.github.openfeign.querydsl:querydsl-apt:${querydslVersion}:jpa")
    annotationProcessor("jakarta.persistence:jakarta.persistence-api")
    testAnnotationProcessor("io.github.openfeign.querydsl:querydsl-apt:${querydslVersion}:jpa")
    testAnnotationProcessor("jakarta.persistence:jakarta.persistence-api")

    // tool
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:3.0.0")

    // test
    testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

// Jacoco Test Coverage
jacoco {
    toolVersion = "0.8.14"
}

// 커버리지 제외 항목들 추가
val exclusionPattern = listOf(
    "**/CmcApplication.class",
    "**/global/**",
    "**/Q[^Q]*.class"
)

fun createFilteredClassDirectories(): ConfigurableFileCollection {
    return files(
        sourceSets.main.get().output.classesDirs.files.map { dirPath ->
            fileTree(dirPath) {
                exclude(exclusionPattern)
            }
        }
    )
}

tasks.jacocoTestReport {
    classDirectories.setFrom(createFilteredClassDirectories())

    reports {
        xml.required = true
        csv.required = false
        html.outputLocation = layout.buildDirectory.dir("reports/jacoco/html")
    }

    dependsOn(tasks.test)
    finalizedBy(tasks.jacocoTestCoverageVerification)
}

tasks.jacocoTestCoverageVerification {
    classDirectories.setFrom(createFilteredClassDirectories())

    violationRules {
        rule {
            element = "CLASS"

            limit {
                counter = "LINE"
                value = "COVEREDRATIO"
                minimum = 0.90.toBigDecimal()
            }

            limit {
                counter = "BRANCH"
                value = "COVEREDRATIO"
                minimum = 0.90.toBigDecimal()
            }

            limit {
                counter = "METHOD"
                value = "COVEREDRATIO"
                minimum = 0.90.toBigDecimal()
            }
        }
    }
}