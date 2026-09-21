plugins {
    java
    alias(libs.plugins.spring.boot)
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform(libs.spring.boot.dependencies))
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.security.crypto)
    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.spring.boot.starter.liquibase)
    implementation(libs.spring.boot.starter.validation)
}

dependencies {
    implementation(libs.nimbus.jose.jwt)
    runtimeOnly(libs.postgresql.driver)
    implementation(libs.bouncy.castle)
}

configure<JavaPluginExtension> {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
