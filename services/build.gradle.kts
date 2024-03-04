import com.android.build.gradle.*

plugins {
    id("java-platform")
}

group = "android"
version = "1.0.0"

subprojects {
    apply(plugin = "com.android.library")
    apply(plugin = "kotlin-android")
    apply(plugin = "kotlin-parcelize")
    apply(plugin = "kotlin-kapt")
    apply(plugin = "com.google.devtools.ksp")
    apply(plugin = "androidx.navigation.safeargs.kotlin")

    plugins.withType(BasePlugin::class.java).configureEach {
        configure<BaseExtension> {
            namespace = "com.agree.ecosystem.$name"
            compileSdkVersion(33)
            defaultConfig {
                minSdk = 22
                targetSdk = 33
                versionCode = 1
                versionName = project.version.toString()

                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                consumerProguardFiles("consumer-rules.pro")
            }
            buildTypes {
                getByName("debug") {
                    isMinifyEnabled = false
                    isDebuggable = true
                    proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
                }
                getByName("release") {
                    isMinifyEnabled = true
                    isDebuggable = false
                    proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
                }
            }
            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }
            buildFeatures.apply {
                viewBinding = true
                buildConfig = false
            }
            lintOptions.apply {
                disable("Instantiatable")
                isAbortOnError = false
            }
        }
    }

    dependencies {
        add("implementation", project(":core:analytics"))
        add("implementation", project(":core:dependencies"))
        add("implementation", project(":core:ui"))
        add("implementation", project(":core:utils"))
        add("implementation", project(":core:locales"))
    }
}

dependencies {
    constraints {
        api(project(":services:monitoring"))
        api(project(":services:agreepedia"))
        api(project(":services:partnership"))
    }
}