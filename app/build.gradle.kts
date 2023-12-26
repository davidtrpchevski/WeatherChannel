plugins {
    alias(libs.plugins.android)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
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

    buildFeatures.apply {
        buildConfig = true
        viewBinding = true
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

    implementation(projects.service)
    implementation(projects.networking)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appCompat)
    implementation(libs.androidx.material)
    implementation(libs.androidx.constraintLayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.unit.test.junit)

    // Android tests
    androidTestImplementation(libs.bundles.tests.android)

    // Hilt
    implementation(libs.hilt)
    ksp(libs.hilt.compiler)
}