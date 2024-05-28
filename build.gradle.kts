import org.jetbrains.kotlin.fir.declarations.builder.buildScript

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    //id ("org.jetbrains.kotlin.plugin.serialization") version "1.6.3"
}

buildscript {
    repositories {
        gradlePluginPortal()
    }
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.49")
        classpath("org.jetbrains.kotlin:kotlin-serialization:1.4.1")
    }
}
