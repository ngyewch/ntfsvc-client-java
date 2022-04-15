plugins {
    base
    id("ca.cutterslade.analyze") version "1.9.0"
    id("com.asarkar.gradle.build-time-tracker") version "4.2.0"
    id("com.github.ben-manes.versions") version "0.42.0"
    id("me.qoomon.git-versioning") version "5.1.5"
    id("se.ascp.gradle.gradle-versions-filter") version "0.1.10"
}

group = "com.github.ngyewch.ntfsvc"
version = "0.0.0-SNAPSHOT"
gitVersioning.apply {
    refs {
        considerTagsOnBranches = true
        tag("v(?<version>.*)") {
            version = "\${ref.version}"
        }
        branch(".+") {
            version = "\${ref}-SNAPSHOT"
        }
    }
}

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "maven-publish")
    apply(plugin = "signing")

    project.group = rootProject.group
    val isReleaseVersion = !(project.version as String).endsWith("SNAPSHOT")

    configure<PublishingExtension> {
        repositories {
            maven {
                if (isReleaseVersion) {
                    setUrl("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
                } else {
                    setUrl("https://s01.oss.sonatype.org/content/repositories/snapshots/")
                }
                credentials {
                    val ossrhUsername: String? by project
                    val ossrhPassword: String? by project
                    username = ossrhUsername
                    password = ossrhPassword
                }
            }
        }
    }

    tasks.withType<Javadoc> {
        configure(options, closureOf<StandardJavadocDocletOptions> {
            addStringOption("Xdoclint:none", "-quiet")
            addStringOption("-release", "8")
            links = listOf(
                "https://docs.oracle.com/javase/8/docs/api/",
            )
        })
    }
}
