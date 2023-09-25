import com.google.protobuf.gradle.generateProtoTasks
import com.google.protobuf.gradle.plugins
import com.google.protobuf.gradle.protobuf
import com.google.protobuf.gradle.protoc

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("androidx.navigation.safeargs")
    id("kotlin-kapt")
    kotlin("kapt")

    // Hilt
    id("dagger.hilt.android.plugin")

    id("com.google.protobuf") version "0.8.17"


}

android {
    compileSdk = 33

    defaultConfig {
        applicationId = "com.freephoenix888.savemylife"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
//            proguardFiles getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
        }
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
        isCoreLibraryDesugaringEnabled = true
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.2.0-beta02"
    }
}


dependencies {
    val core_version = "1.6.0"
    implementation("androidx.core:core-ktx:$core_version")

    val appcompatVersion = "1.5.1"
    implementation("androidx.appcompat:appcompat:$appcompatVersion")
    // For loading and tinting drawables on older versions of the platform
    implementation("androidx.appcompat:appcompat-resources:$appcompatVersion")

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1")
    implementation("androidx.navigation:navigation-ui-ktx:2.4.2")
    implementation("androidx.activity:activity-ktx:1.4.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.4.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.4.1")
    implementation("androidx.compose.runtime:runtime-livedata:1.1.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.1")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    val roomVersion = "2.4.2"
    implementation("androidx.room:room-runtime:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")
    // To use Kotlin annotation processing tool (kapt)
    kapt("androidx.room:room-compiler:$roomVersion")
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$roomVersion")
    implementation("commons-io:commons-io:2.5")

    /* Jetpack compose */
    // Integration with activities
    implementation("androidx.activity:activity-compose:1.4.0")
    // Compose Material Design
    implementation("androidx.compose.material3:material3:1.0.0")
//    implementation("androidx.compose.material:material:1.3.0")
    // Animations
    implementation("androidx.compose.animation:animation:1.1.1")
    // Tooling support (Previews, etc.)
    implementation("androidx.compose.ui:ui-tooling:1.1.1")
    // Integration with ViewModels
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.4.1")
    // UI Tests
    testImplementation("androidx.compose.ui:ui-test-junit4:1.1.1")
    // Navigation
    implementation("androidx.navigation:navigation-compose:2.5.0-rc01")
    // Material icons extended
    implementation("androidx.compose.material:material-icons-extended:1.1.1")
    // View model
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.4.1")
    // Accompanist
    val accompanistVersion = "0.27.0"
    implementation("com.google.accompanist:accompanist-permissions:${accompanistVersion}")
    implementation("com.google.accompanist:accompanist-flowlayout:${accompanistVersion}")

    val workVersion = "2.7.1"
    // Kotlin + coroutines
    implementation("androidx.work:work-runtime-ktx:$workVersion")
    // optional - RxJava2 support
    implementation("androidx.work:work-rxjava2:$workVersion")
    // optional - GCMNetworkManager support
    implementation("androidx.work:work-gcm:$workVersion")
    // optional - Test helpers
    androidTestImplementation("androidx.work:work-testing:$workVersion")
    // optional - Multiprocess support
    implementation("androidx.work:work-multiprocess:$workVersion")



    // Hilt
    implementation("com.google.dagger:hilt-android:2.38.1")
    kapt("com.google.dagger:hilt-android-compiler:2.38.1")
    // Hilt+Compose
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    // Hilt work
    implementation("androidx.hilt:hilt-work:1.0.0")
    // When using Kotlin.
    kapt("androidx.hilt:hilt-compiler:1.0.0")


    // Play Services
    implementation("com.google.android.gms:play-services-base:18.0.1")
    implementation("com.google.android.gms:play-services-location:19.0.1")

    // Easy permission
    implementation("com.vmadalin:easypermissions-ktx:1.0.0")

    // Typed DataStore (Typed API surface, such as Proto)
    implementation("androidx.datastore:datastore:1.0.0")
    // optional - RxJava2 support
    implementation("androidx.datastore:datastore-rxjava2:1.0.0")
    // optional - RxJava3 support
    implementation("androidx.datastore:datastore-rxjava3:1.0.0")
    implementation("com.google.protobuf:protobuf-kotlin:3.19.1")

    implementation("com.github.alorma:compose-settings-ui:0.7.2")
    implementation("com.github.alorma:compose-settings-storage-preferences:0.7.2")

    // AndroidX Savedstate
    val savedStateVersion = "1.2.0"
    // Java language implementation
    implementation("androidx.savedstate:savedstate:${savedStateVersion}")
    // Kotlin
    implementation("androidx.savedstate:savedstate-ktx:${savedStateVersion}")

    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")

    implementation("com.jakewharton.timber:timber:4.7.1")



}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}


protobuf {

    protoc {
        artifact = "com.google.protobuf:protoc:3.19.4"
    }

    generateProtoTasks {
        all().forEach { task ->
            task.plugins{
                create("kotlin")
                create("java")
            }
        }
    }
}