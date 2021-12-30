
plugins {
    id("org.jetbrains.kotlin.jvm") version "1.5.0"
    application
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.google.guava:guava:30.1.1-jre")


    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")

    testImplementation("com.tngtech.archunit:archunit-junit5:0.22.0")
    testImplementation("com.tngtech.archunit:archunit-junit5-api:0.22.0")
    testImplementation("com.tngtech.archunit:archunit-junit5-engine:0.22.0")
}

application {
    // Define the main class for the application.
    mainClass.set("com.riteshk.archunit.junit5.kotlin.example.AppKt")
}

tasks.test {
    useJUnitPlatform()
}