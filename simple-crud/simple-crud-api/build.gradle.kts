tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}

dependencies {
    implementation(project(":storage:db-main"))

    implementation("org.springframework.boot:spring-boot-starter-web")

    compileOnly("org.springframework:spring-context")
}