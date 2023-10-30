plugins {
    kotlin("kapt")
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.samrish.driver"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.samrish.driver"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility =JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }

}

kapt {
    correctErrorTypes = true
}

dependencies {

    val composeBom = platform("androidx.compose:compose-bom:2023.01.00")
    val lifecycleVersion = "2.6.2"
    val appCompatVersion = "1.6.1"
    val volleyVersion = "1.2.1"
    val constraintLayoutVersion = "2.1.4"
    val activityVersion = "1.8.0"
    val nav_version = "2.7.4"
    val room_version = "2.6.0"


    implementation(composeBom)
    androidTestImplementation(composeBom)

    implementation("com.google.firebase:firebase-auth-ktx:22.2.0")
    implementation("com.google.android.gms:play-services-auth:20.7.0")
    debugImplementation ("androidx.compose.ui:ui-tooling:1.5.4")
    implementation("androidx.appcompat:appcompat:$appCompatVersion")
    implementation("androidx.constraintlayout:constraintlayout:$constraintLayoutVersion")
    implementation("androidx.activity:activity-ktx:$activityVersion")

    // Kotlin
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")

    // Feature module Support
    implementation("androidx.navigation:navigation-dynamic-features-fragment:$nav_version")

    //fcm
    implementation("com.google.firebase:firebase-messaging-ktx:23.3.1")
    implementation ("com.google.firebase:firebase-analytics-ktx:21.4.0")

    // Jetpack Compose Integration
    implementation("androidx.navigation:navigation-compose:$nav_version")

    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material:material-icons-core")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.compose.material3:material3-window-size-class")
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation("androidx.compose.runtime:runtime-livedata")
    implementation("androidx.compose.runtime:runtime-rxjava2")




    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:{$lifecycleVersion")
    // ViewModel utilities for Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion")
    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")
    // Lifecycles only (without ViewModel or LiveData)
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
    // Lifecycle utilities for Compose
    implementation("androidx.lifecycle:lifecycle-runtime-compose:$lifecycleVersion")

    // Saved state module for ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycleVersion")

    // alternately - if using Java8, use the following instead of lifecycle-compiler
    implementation("androidx.lifecycle:lifecycle-common-java8:$lifecycleVersion")

    implementation("androidx.room:room-runtime:$room_version")


    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")

    ksp("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")

    implementation ("com.github.kittinunf.fuel:fuel:2.3.1")
    implementation ("com.github.kittinunf.fuel:fuel-moshi:2.3.1")
    ksp("com.squareup.moshi:moshi-kotlin-codegen:1.15.0")
    implementation("com.squareup.moshi:moshi:1.15.0")

    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-android-compiler:2.48")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    implementation("org.greenrobot:eventbus:3.3.1")

}
