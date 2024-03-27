dependencies {
//    implementation("org.springframework.cloud:spring-cloud-starter-sleuth")
    implementation(platform("io.micrometer:micrometer-tracing-bom:latest.release"))
    implementation("io.micrometer:micrometer-tracing")
}