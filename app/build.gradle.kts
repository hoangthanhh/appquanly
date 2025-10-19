import org.gradle.kotlin.dsl.annotationProcessor
import org.gradle.kotlin.dsl.implementation

plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services") // ✅ thêm plugin Firebase
}

android {
    namespace = "utt.cntt.httt.manager"
    compileSdk = 36

    defaultConfig {
        applicationId = "utt.cntt.httt.appbanhang"
        minSdk = 24
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.messaging)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    // glider
    implementation("com.github.bumptech.glide:glide:5.0.5")
    annotationProcessor("com.github.bumptech.glide:glide:5.0.5")
    //RxJava
    implementation("io.reactivex.rxjava3:rxandroid:3.0.0")
    implementation("io.reactivex.rxjava3:rxjava:3.0.0")
    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.github.akarnokd:rxjava3-retrofit-adapter:3.0.0")
    implementation("com.nex3z:notification-badge:1.0.4")
    implementation("org.greenrobot:eventbus:3.2.0")
    implementation("io.github.pilgr:paperdb:2.7.1")
    implementation("com.google.code.gson:gson:2.8.9")
    implementation ("com.github.fornewid:neumorphism:0.3.2")
    implementation ("com.github.dhaval2404:imagepicker:2.1")
    implementation ("com.github.PhilJay:MPAndroidChart:v3.1.0")
}