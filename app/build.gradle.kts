plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id ("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "com.example.remitconnectapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.remitconnectapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.0")
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    //implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material3:material3-android:1.2.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Navigation in compose
    implementation("androidx.navigation:navigation-compose:2.7.6")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")


    // flagKit
    implementation("com.github.murgupluoglu:flagkit-android:1.0.2")

    //splash-screen
    implementation("androidx.core:core-splashscreen:1.0.1")

    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
//    implementation ("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    //OkHttp
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

    //hilt
    implementation("com.google.dagger:hilt-android:2.49")
    //kapt("androidx.hilt:hilt-compiler:1.2.0")
    kapt("com.google.dagger:hilt-compiler:2.49")
    // kapt("com.google.dagger:hilt-android-compiler:$2.49")
    implementation("androidx.hilt:hilt-work:1.2.0")

    // Sandwich
    implementation("com.github.skydoves:sandwich:1.2.1")

    //Gson
    implementation("com.google.code.gson:gson:2.8.6")

    //Coroutines
    implementation(
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4",
    )

    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")


    // bottom sheet
    implementation(platform("androidx.compose:compose-bom:2023.06.01"))

    //implementation ("com.google.accompanist:accompanist-navigation-material:1.7.0")
    implementation("io.github.raamcosta.compose-destinations:animations-core:1.9.60")

    //data store
    implementation ("androidx.datastore:datastore:1.0.0")
    implementation ("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")

    implementation( "com.google.accompanist:accompanist-systemuicontroller:0.34.0")

    //unit test

    testImplementation("org.mockito:mockito-inline:4.2.0")
    testImplementation("app.cash.turbine:turbine:0.6.0")
    testImplementation(
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4",
    )
    testImplementation(
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4",
    )
    testImplementation("androidx.arch.core:core-testing:2.1.0")
    testImplementation("io.mockk:mockk:1.13.4")
    testImplementation("com.google.truth:truth:1.1.4")
    testImplementation(
        "com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0",
    )


}

