import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.3.1.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.3.72"

    kotlin("jvm") version "1.3.72"
    kotlin("plugin.spring") version "1.3.72"
    kotlin("plugin.jpa") version "1.3.72"
}

repositories {
    mavenLocal()
    mavenCentral()
    jcenter()
}

java.sourceCompatibility = JavaVersion.VERSION_11

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


