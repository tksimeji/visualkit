import cl.franciscosolis.sonatypecentralupload.SonatypeCentralUploadTask

plugins {
    java
    kotlin("jvm") version "2.1.0"
    `maven-publish`
    id("cl.franciscosolis.sonatype-central-upload") version "1.0.2"
    id("com.github.johnrengelman.shadow") version "7.+"
}

group = "com.tksimeji"
version = "1.0.0-beta.1"

allprojects {
    apply {
        plugin("java")
        plugin("org.jetbrains.kotlin.jvm")
    }

    val targetJavaVersion = 21

    java {
        val javaVersion = JavaVersion.toVersion(targetJavaVersion)

        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
        toolchain.languageVersion = JavaLanguageVersion.of(targetJavaVersion)

        withSourcesJar()
        withJavadocJar()
    }

    repositories {
        mavenCentral()
        maven("https://repo.papermc.io/repository/maven-public/") {
            name = "papermc"
        }
        maven("https://oss.sonatype.org/content/groups/public/") {
            name = "sonatype"
        }
    }

    dependencies {
        implementation("com.google.guava:guava:33.4.0-jre")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    }

    tasks.withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"

        if (targetJavaVersion >= 10 || JavaVersion.current().isJava10Compatible) {
            options.release.set(targetJavaVersion)
        }
    }
}

subprojects {
    group = rootProject.group
    version = rootProject.version
}

dependencies {
    implementation(project(":modules:api"))
    implementation(project(":modules:core"))
}

publishing {
    publications {
        register<MavenPublication>("maven") {
            pom {
                name.set("tksimeji")
                description.set("The Minecraft GUI framework")
                url.set("https://github.com/tksimeji/kunectron")
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://github.com/tksimeji/kunectron/blob/master/LICENSE")
                        distribution.set("repo")
                    }
                }
                developers {
                    developer {
                        id.set("tksimeji")
                        name.set("tksimeji")
                        email.set("tksimeji<at>outlook.com")
                    }
                }
                scm {
                    url.set("https://github.com/tksimeji/kunectron")
                }
            }
        }
    }
}

tasks.named<Jar>("jar") {
    from(project(":modules:api").sourceSets.main.get().output)
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.named<SonatypeCentralUploadTask>("sonatypeCentralUpload") {
    dependsOn("jar", "sourcesJar", "javadocJar", "generatePomFileForMavenPublication")

    username = System.getenv("SONATYPE_CENTRAL_USERNAME")
    password = System.getenv("SONATYPE_CENTRAL_PASSWORD")

    archives = files(
        tasks.named("jar"),
        tasks.named("sourcesJar"),
        tasks.named("javadocJar"),
    )

    pom = file(
        tasks.named("generatePomFileForMavenPublication").get().outputs.files.single()
    )

    signingKey.set(File("key.asc").readText())
    signingKeyPassphrase = System.getenv("PGP_SIGNING_KEY_PASSPHRASE")
}
