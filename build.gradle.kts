import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.3.1.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.3.72"
    id("org.jetbrains.kotlin.plugin.noarg") version "1.3.72"
    id("org.sonarqube") version "2.7"
    id("jacoco")

    kotlin("jvm") version "1.3.72"
    kotlin("plugin.spring") version "1.3.72"
    kotlin("plugin.jpa") version "1.3.72"
}

repositories {
    mavenLocal()
    mavenCentral()
    jcenter()
}

java.sourceCompatibility = JavaVersion.VERSION_1_8

extra["springVersion"] = "2.3.1.RELEASE"

tasks.bootJar{
    enabled = false
}

subprojects {
    group = "br.com.r7"
    version = "0.0.1-SNAPSHOT"

    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "kotlin")
    apply(plugin = "kotlin-allopen")
    apply(plugin = "kotlin-noarg")
    apply(plugin = "jacoco")

    repositories {
        mavenLocal()
        mavenCentral()
        jcenter()
    }

    dependencyManagement{
        imports {
            mavenBom("org.springframework.boot:spring-boot-dependencies:${property("springVersion")}")
        }
    }

    dependencies {
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

        testImplementation ("org.assertj:assertj-core:3.11.1")
        testImplementation ("org.junit.jupiter:junit-jupiter-api:5.2.0")
        testImplementation ("org.junit.jupiter:junit-jupiter-params:5.2.0")
        testImplementation ("org.junit.jupiter:junit-jupiter-engine:5.2.0")
        testImplementation ("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")
    }

    allOpen{
        annotation("org.springframework.stereotype.Component")
        annotation("org.springframework.stereotype.Service")
        annotation("org.springframework.stereotype.Repository")
        annotation("org.springframework.context.annotation.Configuration")
        annotation("org.springframework.context.annotation.ComponentScan")
    }

    noArg{
        annotation("javax.persistence.Entity")
    }

    tasks.jacocoTestReport{
        reports {
            xml.isEnabled = true
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }

}


