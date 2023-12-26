plugins {
    alias(libs.plugins.android)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.david.weatherchannel"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.david.weatherchannel"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appCompat)
    implementation(libs.androidx.material)
    implementation(libs.androidx.constraintLayout)
    testImplementation(libs.unit.test.junit)

    // Android tests
    androidTestImplementation(libs.bundles.tests.android)
}