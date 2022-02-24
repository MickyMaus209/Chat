plugins {
    id("application")
    id("org.openjfx.javafxplugin") version "0.0.10"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "de.mickymaus209"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

application {
    mainClass.set("de.mickymaus209.chatclient.ChatClient")
}

javafx {
    version = "17"
    modules("javafx.controls", "javafx.fxml")
}