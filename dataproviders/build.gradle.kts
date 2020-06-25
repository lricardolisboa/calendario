dependencies {
    implementation(project(":core"))
    implementation(project(":usecases"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.flywaydb:flyway-core")
    //implementation("org.postgresql:postgresql")
    implementation("com.h2database:h2:1.4.196")
}