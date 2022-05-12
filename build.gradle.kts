import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val packageVersion = "1.3.0"

plugins {
  id("java")
  kotlin("jvm") version "1.9.20"
  id("com.vanniktech.maven.publish") version "0.28.0"
}

group = "at.deckweiss"
version = packageVersion
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
  mavenCentral()
}

dependencies {
  implementation("org.springframework.boot:spring-boot-autoconfigure:3.1.1")
  implementation("org.springframework.boot:spring-boot-configuration-processor:3.1.1")
  api("org.springframework.boot:spring-boot-starter-mail:3.1.1")
}

tasks.test {
  useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
  kotlinOptions.jvmTarget = "17"
}

mavenPublishing {
  coordinates(
    groupId = "at.deckweiss",
    artifactId = "email-starter",
    version = packageVersion
  )

  pom {
    name.set("Deckweiss Email Starter")
    description.set("Library based on Spring Boot to send emails with Kotlin")
    url.set("https://github.com/deckweiss/kotlin-email-starter")
    inceptionYear.set("2022")

    licenses {
      license {
        name.set("The Apache License, Version 2.0")
        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
        distribution.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
      }
    }

    developers {
      developer {
        id.set("klausbetz")
        name.set("Klaus Betz")
        url.set("https://www.deckweiss.at")
      }
    }

    scm {
      url.set("https://github.com/deckweiss/kotlin-email-starter")
    }
  }

  publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
  signAllPublications()
}
