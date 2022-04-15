plugins {
    application
    id("ca.cutterslade.analyze")
    id("com.github.ben-manes.versions")
    id("com.github.ngyewch.capsule") version "0.1.4"
    id("se.ascp.gradle.gradle-versions-filter")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(project(":ntfsvc-client"))

    implementation("org.apache.commons:commons-configuration2:2.7")
}

application {
    mainClass.set("io.github.ngyewch.ntfsvc.client.app.Main")
}

tasks.register("package") {
    dependsOn("build", "packageFatCapsule")
}

capsule {
    capsuleManifest {
        capsuleManifest.applicationId.set((project.group as String) + "." + project.name)
        capsuleManifest.minJavaVersion.set("1.8")
    }
}
