dependencies {
    api(project(":api-entity"))
    api(libs.kotlinx.coroutines.core.jvm)
    api(libs.toolkit.core.qbean.helper)
    api(libs.bouncycastle.bcprov)
    api("org.apache.commons:commons-lang3")
    api(libs.commons.beanutils)
    api(libs.commons.io)
    api(libs.nxcloud.core.validation)
    compileOnly("jakarta.validation:jakarta.validation-api")
    compileOnly("org.springframework:spring-context")
    compileOnly("org.springframework:spring-web")
    compileOnly("javax.servlet:javax.servlet-api")
}