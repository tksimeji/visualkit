plugins {
    id("io.papermc.paperweight.userdev") version "1.7.7"
}

dependencies {
    compileOnly(project(":api"))
    paperweight.paperDevBundle("1.21.3-R0.1-SNAPSHOT")
}
