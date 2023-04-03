dependencies {
    api(libs.nxcloud.core.base)
    api(libs.toolkit.core.base.enums)
    api(libs.toolkit.core.qbean.api)
    api(libs.guava)
    annotationProcessor(libs.toolkit.core.qbean.processor)
    compileOnly("org.springframework:spring-context")
    compileOnly("jakarta.validation:jakarta.validation-api")
    compileOnly(libs.nxcloud.core.validation)
}