plugins {
    java
    id("application")
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "de.mickymaus209"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

application {
    mainClass.set("de.mickymaus209.chatserver.ChatServer")
}