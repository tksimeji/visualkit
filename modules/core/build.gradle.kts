dependencies {
    implementation(project(":modules:api"))
    implementation(project(":modules:v1_21_1"))
    implementation(project(":modules:v1_21_3"))
    compileOnly("io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT")
    implementation("com.tksimeji:mojango:0.0.2")
}

tasks.processResources {
    val props = mapOf("version" to version)
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("paper-plugin.yml") {
        expand(props)
    }
}
