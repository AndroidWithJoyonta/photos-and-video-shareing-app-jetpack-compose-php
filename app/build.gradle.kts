import org.gradle.kotlin.dsl.implementation

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.sharing.monio"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.sharing.monio"
        minSdk = 24
        targetSdk = 35
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.firebase.auth)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)



    implementation("androidx.navigation:navigation-compose:2.9.0")

    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.google.android.gms:play-services-auth:20.7.0")
    implementation("io.coil-kt.coil3:coil-compose:3.2.0")
    implementation("androidx.media3:media3-exoplayer:1.3.1")
    implementation("androidx.media3:media3-ui:1.3.1")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.7")


    // Jetpack Compose BOM (replaces individual versioning)
     platform("androidx.compose:compose-bom:2024.05.00")

// Compose runtime for collectAsState
    implementation ("androidx.compose.runtime:runtime")

// Lifecycle integration for ViewModel and StateFlow
    implementation ("androidx.lifecycle:lifecycle-runtime-compose")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose")

    implementation("io.coil-kt.coil3:coil-network-okhttp:3.2.0") // Only available on Android/JVM.
    implementation("io.coil-kt.coil3:coil-network-ktor2:3.2.0")
    implementation("io.coil-kt.coil3:coil-network-ktor3:3.2.0")
    implementation("com.google.android.gms:play-services-ads:24.4.0")



}