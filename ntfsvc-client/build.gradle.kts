plugins {
    `java-library`
    `maven-publish`
    signing
    id("ca.cutterslade.analyze")
    id("com.github.ben-manes.versions")
    id("se.ascp.gradle.gradle-versions-filter")
}

val isReleaseVersion = !(project.version as String).endsWith("SNAPSHOT")

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8

    withJavadocJar()
    withSourcesJar()
}

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.2.2")
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
}

tasks {
    withType(Sign::class) {
        onlyIf { isReleaseVersion }
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])

            groupId = project.group as String
            artifactId = project.name
            version = project.version as String

            pom {
                name.set("ntfsvc-client")
                description.set("Notification Service Client.")
                url.set("https://github.com/ngyewch/ntfsvc")
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://github.com/ngyewch/ntfsvc/blob/main/LICENSE")
                    }
                }
                scm {
                    connection.set("scm:git:git@github.com:ngyewch/ntfsvc.git")
                    developerConnection.set("scm:git:git@github.com:ngyewch/ntfsvc.git")
                    url.set("https://github.com/ngyewch/ntfsvc")
                }
                developers {
                    developer {
                        id.set("ngyewch")
                        name.set("Nick Ng")
                    }
                }
            }
        }
    }
}

signing {
    sign(publishing.publications["maven"])
}
