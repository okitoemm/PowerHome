plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.g205emmanuelbryanhugo.powerhome"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.g205emmanuelbryanhugo.powerhome"
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

}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // ViewModel
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.livedata)
    implementation(libs.lifecycle.runtime)
    implementation(libs.lifecycle.viewmodel.ktx)

    // Dépendances pour la navigation
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)
    implementation(libs.drawerlayout) // Use the version catalog alias
    implementation(libs.coordinatorlayout)
    
    // SwipeRefreshLayout
    implementation(libs.swiperefreshlayout)
    
    // Material Design
    implementation("com.google.android.material:material:1.12.0")
}
