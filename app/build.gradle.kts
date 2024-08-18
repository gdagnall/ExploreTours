plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.android.libraries.mapsplatform.secrets.gradle.plugin)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.geogad.ai.hacka.exploretours"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.geogad.ai.hacka.exploretours"
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
        buildConfig = true
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.generativeai)
    implementation("androidx.compose.material:material-icons-extended-android:1.6.8")
//    implementation(libs.ktor.android)
//
//    //implementation(libs.ktor.client.serialization )
//    //implementation(libs.ktor.client.logging)
//   implementation(libs.ktor.client.core)
//    implementation(libs.ktor.client.cio)
//    implementation(libs.ktor.client.okhttp)
    implementation("io.coil-kt:coil-compose:2.7.0")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.4")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.4")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.4")

    val ktor_version = "2.3.12"

    //Ktor Client
    implementation("io.ktor:ktor-client-core:$ktor_version")
    //To use Android Engine
    implementation("io.ktor:ktor-client-android:$ktor_version")
    //To use OKHTTP Engine
    implementation("io.ktor:ktor-client-okhttp:$ktor_version")
    //To use Logging Feature
    implementation("io.ktor:ktor-client-logging:$ktor_version")
    implementation("ch.qos.logback:logback-classic:1.2.3")
    //To use Content Negotiation Feature
    implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
    //To use Content Serialization in Json Feature
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
    // Authentication
    implementation("io.ktor:ktor-client-auth:$ktor_version")


//    implementation(platform("io.ktor:ktor-bom:2.3.12"))
//    implementation("io.ktor:ktor-client-android")
//    implementation("io.ktor:ktor-client-serialization")
//    implementation("io.ktor:ktor-client-logging")
//    implementation("io.ktor:ktor-client-content-negotiation")
//    implementation("io.ktor:ktor-serialization-kotlinx-json")

    // Koin for Android
    implementation("io.insert-koin:koin-androidx-compose:3.5.6")

    //Coil
    implementation("io.coil-kt:coil-compose:2.7.0")

    // Webview
    implementation("androidx.webkit:webkit:1.8.0")


    // HTTP engine: that handles network requests.
//    implementation ("io.ktor:ktor-client-android:2.3.12")
//// It used for JSON serialization and deserialization settings and //is recommended for multiplatform projects
//    implementation ("io.ktor:ktor-client-serialization:2.3.12")
////It is kotlinx.serialization, which is used for entity //serialization
//    implementation ("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.1")
////It's for logging HTTP requests
//    implementation ("io.ktor:ktor-client-logging-jvm:2.3.12")
}