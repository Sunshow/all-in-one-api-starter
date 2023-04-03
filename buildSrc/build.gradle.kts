plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.qdox)
    implementation(libs.javapoet)
    implementation(libs.guava)
    implementation(libs.kotlinpoet)
    implementation(libs.kotlinpoet.javapoet)
}