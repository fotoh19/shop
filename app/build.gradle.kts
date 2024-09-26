plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-kapt")
}

android {
    namespace = "com.note.shop"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.note.shop"
        minSdk = 26
        targetSdk = 33
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures{
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-database:21.0.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
//    implementation("androidx.navigation:navigation-safe-args-gradle-plugin:2.8.0")

    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")

    kapt("androidx.room:room-compiler:2.6.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    // ViewModel
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    // ViewModel utilities for Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.5")
    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.5")
    // Lifecycles only (without ViewModel or LiveData)
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.5")

    // Jetpack Compose integration
    implementation("androidx.navigation:navigation-compose:2.8.0")

    // Views/Fragments integration
    implementation("androidx.navigation:navigation-fragment-ktx:2.8.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.8.0")

    // Feature module support for Fragments
    implementation("androidx.navigation:navigation-dynamic-features-fragment:2.8.0")

    // Testing Navigation
    androidTestImplementation("androidx.navigation:navigation-testing:2.8.0")

    implementation("androidx.activity:activity-ktx:1.9.2")
    implementation("com.github.bumptech.glide:glide:4.12.0")
    implementation("com.tbuonomo:dotsindicator:5.0")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("androidx.room:room-common:2.6.1")
}