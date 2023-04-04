dependencies {
    implementation(project(":api-provider"))
    implementation(libs.nxcloud.starter.jpa)
    implementation(libs.modelmapper)
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("mysql:mysql-connector-java")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-amqp")
    implementation(libs.nxcloud.ext.starter.springmvc.automapping)
}

val jar by tasks.getting(Jar::class) {
    manifest {
        // kotlin 会自动编译成两个文件 需要指定带 Kt 后缀的类名
        attributes["Main-Class"] = "com.example.api.gateway.ExampleApiGatewayApplicationKt"
    }
}

tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    launchScript()
}