import com.android.build.gradle.BaseExtension

plugins {
    id("java-platform")
    id("maven-publish")
}

group = "agree"
version = "1.0.0-alpha02"

//afterEvaluate {
//    val nexusUsername = if(properties["maintener_username"] != null) {
//        properties["maintener_username"]
//    } else if(properties["nexus_username"] != null) {
//        properties["nexus_username"]
//    } else {
//        "developer"
//    }
//
//    val nexusPassword = if(properties["maintener_password"] != null) {
//        properties["maintener_password"]
//    } else if(properties["nexus_password"] != null) {
//        properties["nexus_password"]
//    } else {
//        "password"
//    }
//
//    publishing {
//        publications {
//            create<MavenPublication>("library") {
//                group = this@afterEvaluate.group
//                version = this@afterEvaluate.version.toString()
//                artifactId = "core"
//                from(components["javaPlatform"])
//
//                pom.withXml {
//                    val dependenciesNode = asNode().appendNode("dependencies")
//                    configurations.api.get().allDependencies.forEach {
//                        dependenciesNode.appendNode("dependency").apply {
//                            appendNode("groupId", it.group)
//                            appendNode("artifactId", it.name)
//                            appendNode("version", findProject(it.name)?.version)
//                        }
//                    }
//                }
//            }
//        }
//
//        repositories {
//            maven {
//                setUrl("https://nexus.playcourt.id/repository/agree-logtan/")
//                credentials {
//                    username = nexusUsername.toString()
//                    password = nexusPassword.toString()
//                }
//            }
//        }
//    }
//}

subprojects {
    group = "agree"

    apply(plugin = "com.android.library")
    apply(plugin = "kotlin-android")
    apply(plugin = "kotlin-parcelize")
    apply(plugin = "kotlin-kapt")
    apply(plugin = "com.google.devtools.ksp")
    if(name == "uikit") apply(plugin = "maven-publish")

    afterEvaluate {
        val nexusUsername = if(properties["maintener_username"] != null) {
            properties["maintener_username"]
        } else if(properties["nexus_username"] != null) {
            properties["nexus_username"]
        } else {
            "developer"
        }

        val nexusPassword = if(properties["maintener_password"] != null) {
            properties["maintener_password"]
        } else if(properties["nexus_password"] != null) {
            properties["nexus_password"]
        } else {
            "password"
        }

//        if(name == "uikit") run {
//            publishing {
//                publications {
//                    register<MavenPublication>("library") {
//                        from(components["debug"])
//                        group = this@afterEvaluate.group
//                        version = this@afterEvaluate.version.toString()
//                        artifactId = "core-${this@afterEvaluate.name}"
//                    }
//                }
//
//                repositories {
////                    maven {
////                        name = "local"
////                        setUrl("$rootDir/libs")
////                    }
//                    maven {
//                        name = "remote"
//                        setUrl("https://nexus.playcourt.id/repository/agree-logtan/")
//                        credentials {
//                            username = nexusUsername.toString()
//                            password = nexusPassword.toString()
//                        }
//                    }
//                }
//            }
//        }
    }

    plugins.withType(BasePlugin::class.java).configureEach {
        configure<BaseExtension> {
            namespace = "com.agree.$name"
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
        if(
            !name.contains("dependencies")
        ) {
            add("implementation", project(":core:dependencies"))
        }

        if(name.contains("utils") || name == "ui") {
            val libs = rootProject.project.libs
            add("implementation", platform(libs.telkom.legion))
            add("implementation", libs.bundles.telkom.legion)
        }

        if(name.contains("utils")) {
            add("implementation", project(":core:ui"))
            add("implementation", project(":core:analytics"))
        }
    }
}

dependencies {
    constraints {
//        api(project(":core:uikit"))
    }
}