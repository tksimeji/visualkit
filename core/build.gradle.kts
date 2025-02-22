dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.3-R0.1-SNAPSHOT")
    implementation("com.tksimeji:mojango:0.0.1")
    implementation(project(":api"))
    implementation(project(":v1_21_1"))
    implementation(project(":v1_21_3"))
}

tasks.processResources {
    val props = mapOf("version" to version)
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("paper-plugin.yml") {
        expand(props)
    }
}
