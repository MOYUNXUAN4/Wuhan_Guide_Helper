plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt") // 应用 kapt 插件
    id("com.google.gms.google-services")

}

android {
    namespace = "com.example.wuhan_guide_helper"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.wuhan_guide_helper"
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
        compose = true // 启用 Compose
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.4" // 根据你的 Compose 版本调整
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Jetpack Compose BOM
    implementation(platform("androidx.compose:compose-bom:2024.12.01"))
    implementation("androidx.activity:activity-compose:1.9.3")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")
    implementation("androidx.navigation:navigation-compose:2.8.5")
    implementation("androidx.compose.runtime:runtime-livedata:1.4.0")
    implementation ("androidx.navigation:navigation-compose:2.7.7")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation("com.google.maps.android:maps-compose:4.3.0")
    implementation ("io.coil-kt:coil-compose:2.4.0")
    // Google Play Services Maps
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation ("androidx.core:core-ktx:1.10.1")


    // Firebase BOM
    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

    // Accompanist
    implementation("com.google.accompanist:accompanist-pager:0.28.0")
    implementation("com.google.accompanist:accompanist-pager-indicators:0.28.0")

    // Room
    implementation("androidx.room:room-runtime:${rootProject.extra["room_version"]}")
    implementation(libs.androidx.appcompat)
    kapt("androidx.room:room-compiler:${rootProject.extra["room_version"]}")
    implementation("androidx.room:room-ktx:${rootProject.extra["room_version"]}")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.12.01"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}