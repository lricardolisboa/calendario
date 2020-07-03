plugins{
    application
}

application {
    mainClassName = "br.com.r7.calendario.CalendarioApplication"
}

dependencies {

    implementation(project(":core"))
    implementation(project(":usecases"))
    implementation(project(":dataproviders"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.11.0")
    implementation("io.jsonwebtoken:jjwt:0.9.1")

    implementation("io.springfox:springfox-swagger2:2.9.2")
    implementation("io.springfox:springfox-swagger-ui:2.9.2")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
}