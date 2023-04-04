dependencies {
    api(project(":api-domain"))
    api(libs.toolkit.core.base.enums.converter)

    compileOnly(libs.nxcloud.starter.jpa)
    compileOnly("org.springframework.boot:spring-boot-starter-data-jpa")
    compileOnly("org.springframework.boot:spring-boot-starter-web")
    compileOnly("org.springframework.boot:spring-boot-starter-data-redis")
    compileOnly("org.springframework.session:spring-session-data-redis")

    implementation(libs.hypersistence.utils.hibernate55)

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("com.h2database:h2")

}
