plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.google.android.libraries.mapsplatform.secrets.gradle.plugin)
}

android {
    namespace = "com.example.madfinalproject"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.madfinalproject"
        minSdk = 24
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

    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.play.services.maps)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.storage)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    // Maps SDK for Android
    implementation("com.google.android.gms:play-services-maps:19.0.0")
    implementation ("com.karumi:dexter:6.2.3")
    implementation ("com.google.android.gms:play-services-location:21.0.1")

    //Photos See
    implementation ("com.github.bumptech.glide:glide:4.15.1")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.15.1")

    //Extra Dependencies r
    implementation("com.google.firebase:firebase-firestore:24.9.1")  // Ensure latest Firestore version
    implementation("com.google.firebase:firebase-auth:22.3.1")  // Ensure latest Firebase Auth version
    implementation("com.google.firebase:firebase-storage:21.0.1") // Ensure latest Firebase Storage version
    implementation("com.google.android.gms:play-services-tasks:18.0.2")



}