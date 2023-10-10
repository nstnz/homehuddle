val Ktor = "2.3.5"
val Kodein = "7.16.0"
val Firebase = "1.10.0"

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose")
}

kotlin {
    androidTarget()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.ui)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
                implementation(compose.materialIconsExtended)

                implementation("org.kodein.di:kodein-di:$Kodein")

                implementation("io.ktor:ktor-client-core:$Ktor")
                implementation("io.ktor:ktor-client-content-negotiation:$Ktor")
                implementation("io.ktor:ktor-client-logging:$Ktor")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$Ktor")

                implementation("dev.gitlive:firebase-auth:$Firebase")
                implementation("dev.gitlive:firebase-database:$Firebase")
                implementation("dev.gitlive:firebase-firestore:$Firebase")
                implementation("dev.gitlive:firebase-crashlytics:$Firebase")

                api("moe.tlaster:precompose:1.5.3")
                api("moe.tlaster:precompose-viewmodel:1.5.3")
            }
        }
        val androidMain by getting {
            dependencies {
                api("androidx.activity:activity-compose:1.7.2")
                api("androidx.appcompat:appcompat:1.6.1")
                api("androidx.core:core-ktx:1.10.1")

                implementation("io.ktor:ktor-client-logging-jvm:$Ktor")
                implementation("io.ktor:ktor-client-json-jvm:$Ktor")
                implementation("io.ktor:ktor-client-android:$Ktor")

                implementation("org.kodein.di:kodein-di-framework-android-x:$Kodein")
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
    }
}

android {
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    namespace = "com.homehuddle.common"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        minSdk = (findProperty("android.minSdk") as String).toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }
}
