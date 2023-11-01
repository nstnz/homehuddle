val Ktor = "2.3.5"
val Kodein = "7.16.0"
val Firebase = "1.10.3"
val Storage = "1.1.0"
val Precompose = "1.5.3"
val Voyager = "1.0.0-rc05"
val SqlDelightVersion = "1.5.5"

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose")
    id("com.squareup.sqldelight")
    kotlin("plugin.serialization")
}

sqldelight {
    database("AppDatabase") {
        packageName = "com.homehuddle"
        sourceFolders = listOf("sqldelight")
    }
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
                implementation(compose.material3)
                implementation(compose.ui)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
                implementation(compose.materialIconsExtended)

                implementation("io.github.thechance101:chart:Beta-0.0.5")
                implementation("org.kodein.di:kodein-di:$Kodein")
                implementation("com.squareup.sqldelight:runtime:$SqlDelightVersion")
                implementation("com.squareup.sqldelight:coroutines-extensions:$SqlDelightVersion")

                implementation("io.ktor:ktor-client-core:$Ktor")
                implementation("io.ktor:ktor-client-content-negotiation:$Ktor")
                implementation("io.ktor:ktor-client-logging:$Ktor")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$Ktor")

                implementation("dev.gitlive:firebase-auth:$Firebase")
                implementation("dev.gitlive:firebase-database:$Firebase")
                implementation("dev.gitlive:firebase-firestore:$Firebase")
                implementation("dev.gitlive:firebase-crashlytics:$Firebase")

                implementation("cafe.adriel.voyager:voyager-navigator:$Voyager")
                implementation("cafe.adriel.voyager:voyager-bottom-sheet-navigator:$Voyager")
                implementation("cafe.adriel.voyager:voyager-tab-navigator:$Voyager")
                implementation("cafe.adriel.voyager:voyager-transitions:$Voyager")

                api("moe.tlaster:precompose:$Precompose")
                api("moe.tlaster:precompose-viewmodel:$Precompose")
                implementation("media.kamel:kamel-image:0.8.2")

                implementation("dev.icerock.moko:media-compose:0.11.0")
                implementation("dev.icerock.moko:permissions-compose:0.16.0")
                //implementation("dev.icerock.moko:maps:0.6.0")
                //implementation("dev.icerock.moko:maps-google:0.6.0")
                implementation("com.russhwolf:multiplatform-settings:$Storage")

                api("com.mohamedrejeb.calf:calf-ui:0.2.0")
                implementation("io.github.aakira:napier:2.6.1")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
                implementation("com.benasher44:uuid:0.8.1")
            }
        }
        val androidMain by getting {
            dependencies {
                api("androidx.activity:activity-compose:1.7.2")
                api("androidx.appcompat:appcompat:1.6.1")
                api("androidx.core:core-ktx:1.10.1")

                implementation(compose.preview)
                implementation(compose.uiTooling)
                implementation("com.squareup.sqldelight:android-driver:$SqlDelightVersion")

                implementation("io.ktor:ktor-client-logging-jvm:$Ktor")
                implementation("io.ktor:ktor-client-json-jvm:$Ktor")
                implementation("io.ktor:ktor-client-android:$Ktor")

                implementation(platform("com.google.firebase:firebase-bom:32.0.0"))
                implementation("com.google.firebase:firebase-auth")
                implementation("com.google.firebase:firebase-firestore")
                implementation("com.google.firebase:firebase-config")
                implementation("com.google.firebase:firebase-storage")
                implementation("com.google.android.gms:play-services-auth:20.7.0")

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
         //   implementation("com.squareup.sqldelight:native-driver:$SqlDelightVersion")
        }
    }
}

kotlin.targets
    .matching { it is org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget }
    .configureEach {
        val target = this as org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

        target.binaries
            .matching { it is org.jetbrains.kotlin.gradle.plugin.mpp.Framework }
            .configureEach {
                val framework = this as org.jetbrains.kotlin.gradle.plugin.mpp.Framework
                val frameworks = listOf("Base", "Maps").map { frameworkPath ->
                    project.file("../ios-app/Pods/GoogleMaps/$frameworkPath/Frameworks").path.let { "-F$it" }
                }.plus(
                    project.file("../ios-app/Pods/Mapbox-iOS-SDK/dynamic").path.let { "-F$it" }
                )

                framework.linkerOpts(frameworks)
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

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }
}