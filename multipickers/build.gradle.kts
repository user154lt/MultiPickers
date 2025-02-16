import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.plugin.compose)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.vanniktech.maven.publish)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    jvm("desktop")
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    applyDefaultHierarchyTemplate()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.ui)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.runtime)
            }
        }
    }

}

android {
    namespace = "github.returdev.multipickers"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}


mavenPublishing {
    coordinates(
        groupId = "io.github.user154lt",
        artifactId = "multipickers",
        version = "1.0.1"
    )
    pom {
        name.set("Compose multiplatform multipicker library")
        description.set(
            "A Jetpack Compose Multiplatform Library that features a collection of easily " +
                    "implementable pickers, can be used for Android, desktop and iOS targets"
        )
        inceptionYear.set("2025")
        url.set("https://github.com/user154lt/MultiPickers")
        licenses {
            license {
                name.set("Apache")
                url.set("https://www.apache.org/licenses/LICENSE-2.0")
            }
        }
        developers {
            developer {
                id.set("User154lt")
                name.set("User154")
                url = "https://github.com/user154lt/"
            }
            developer {
                id.set("ReturDev")
                name.set("Sergio")
                url = "https://github.com/ReturDev"
            }
        }
        scm{
            url.set("https://github.com/user154lt/MultiPickers")
        }
    }
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
    signAllPublications()
}


