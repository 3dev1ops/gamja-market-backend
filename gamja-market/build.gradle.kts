plugins {
    // 버전만 정의하고 적용(apply)은 false로 설정
    java
    id("org.springframework.boot") version "3.2.2" apply false
    id("io.spring.dependency-management") version "1.1.4" apply false
    kotlin("jvm") version "1.9.22" apply false
    kotlin("plugin.spring") version "1.9.22" apply false
    kotlin("plugin.jpa") version "1.9.22" apply false
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

allprojects {
    group = "com.gamjamarket"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
        maven { url = uri("https://repo.spring.io/milestone") }
    }
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "io.spring.dependency-management")

    repositories {
        mavenCentral()
        maven { url = uri("https://repo.spring.io/milestone") }
    }

    // 버전 관리 플러그인 설정
    the<io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension>().apply {
        imports {
            mavenBom("org.springframework.boot:spring-boot-dependencies:3.2.2")
        }
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}
