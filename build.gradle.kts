plugins {
    java
    alias(libs.plugins.spring.boot)
}

repositories {
    mavenCentral()
}

configure<JavaPluginExtension> {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
