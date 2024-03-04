# Modules

Agree Ecosystem uses a modular concept, in addition to preventing boilerplate and repetitive code, we also use the concept of sub projects,

the concept of a sub project is a centralized configuration from the parent module to the child module, making it more efficient in managing dependencies and build.gradle of each module.

---

!!! info
    If the graph doesn't appear after decrypting the page, please try reloading this page again

## Base Module

Base module contains the main application code of Agree Ecosystem, starting from the splash screen, auth, home and others

``` mermaid
graph LR
    A[:base]
    B[:utils]
    C[:uikit]
    D[:analytics]
    E[:dependencies]
    F[:services]
    G[:apps]
    A -.-> G
    B -.-> A
    C -.-> A
    D -.-> A
    F -.-> A
```

??? "build.gradle"

    ``` groovy
        plugins {
            id 'com.android.application'
            id 'org.jetbrains.kotlin.android'
            id 'com.google.gms.google-services'
            id 'com.google.firebase.crashlytics'
            id 'androidx.navigation.safeargs.kotlin'
        }

        apply from: '../ktlint.gradle'
        apply plugin: 'kotlin-android'

        def keystorePropertiesFile = rootProject.file("keystore.properties")
        def keystoreProperties = new Properties()

        android {
            compileSdk 31

            defaultConfig {
                applicationId "com.agree.ecosystem"
                minSdk 22
                targetSdk 31
                versionCode appVersionCode
                versionName appVersion
                setProperty("archivesBaseName", "Ecosystem-v$versionName")
                testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
            }

            signingConfigs {
                release {
                    if(keystorePropertiesFile.exists()) {
                        keystoreProperties.load(new FileInputStream(keystorePropertiesFile))
                        storeFile rootProject.file(keystoreProperties['KEYSTORE_FILE'])
                        storePassword keystoreProperties['STORE_PASSWORD']
                        keyAlias keystoreProperties['KEY_ALIAS']
                        keyPassword keystoreProperties['KEY_PASSWORD']
                    }
                }
            }

            buildTypes {
                release {
                    resValue "string", "app_name", "Agree Ecosystem"
                    buildConfigField("String", "VARIANT", "\"release\"")
                    debuggable = false
                    minifyEnabled true
                    shrinkResources true
                    proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
                    testCoverageEnabled false
                    signingConfig signingConfigs.release
                    firebaseCrashlytics {
                        mappingFileUploadEnabled true
                    }
                }
                debug {
                    resValue "string", "app_name", "Agree Ecosystem-Debug"
                    buildConfigField("String", "VARIANT", "\"development\"")
                    versionNameSuffix "-dev"
                    minifyEnabled false
                    debuggable true
                    testCoverageEnabled false
                    proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
                    firebaseCrashlytics {
                        mappingFileUploadEnabled false
                    }
                }
            }

            flavorDimensions "enviroment"
            productFlavors {
                prod {
                    dimension "enviroment"
                    proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
                }
                staging {
                    dimension "enviroment"
                    proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
                }
                dev {
                    dimension "enviroment"
                    proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
                }
            }

            buildFeatures {
                dataBinding = true
                viewBinding true
            }

            compileOptions {
                sourceCompatibility JavaVersion.VERSION_1_8
                targetCompatibility JavaVersion.VERSION_1_8
            }

            kotlinOptions {
                jvmTarget = '1.8'
            }

            buildFeatures {
                viewBinding true
            }

            dynamicFeatures = [':apps:calc', ':apps:finance', ':apps:iot', ':apps:prices', ':apps:weather']
        }

        dependencies {
            implementation project(':core:dependencies')
            implementation project(':core:uikit')
            implementation project(':core:utils')
            implementation project(':core:analytics')

            implementation project(':services:partnership')
            implementation project(':services:monitoring')
            implementation project(':services:infrastructure')

            implementation 'com.google.android.play:core:1.10.3'
            implementation 'androidx.legacy:legacy-support-v4:1.0.0'
            implementation 'androidx.navigation:navigation-fragment-ktx:2.4.2'
            implementation 'androidx.navigation:navigation-ui-ktx:2.4.2'
            implementation 'com.google.android.play:core-ktx:1.8.1'

            //Pluto Debugger
            debugImplementation 'com.plutolib.plugins:exceptions:2.0.0'
            releaseImplementation 'com.plutolib.plugins:exceptions-no-op:2.0.0'
            debugImplementation 'com.plutolib:pluto:2.0.0'
            releaseImplementation 'com.plutolib:pluto-no-op:2.0.0'
            implementation "androidx.core:core-ktx:1.7.0"
            implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1"
            implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
        }

        check.dependsOn ktlint
    ```

## Parent Modules

!!! info
    The parent module is where the configuration center of the child module is, in the parent module there is a configuration of sub projects

### Core

The core module is the main module or module that can be shared between services and between sectors

#### Core Members

``` mermaid
graph RL
  A[core]
  B[utils] --> A
  C[uikit] --> A
  D[analytics] --> A
  E[dependencies] --> A
```

??? "build.gradle.kts"

    ``` kotlin
        import com.android.build.gradle.*

        plugins {
            id("java-platform")
        }

        group = "android"
        version = "1.1.0"

        subprojects {
            apply(plugin = "com.android.library")
            apply(plugin = "kotlin-android")
            apply(plugin = "kotlin-parcelize")
            apply(plugin = "kotlin-kapt")

            plugins.withType(BasePlugin::class.java).configureEach {
                configure<BaseExtension> {
                    compileSdkVersion(31)
                    defaultConfig {
                        minSdk = 21
                        targetSdk = 31
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
                        sourceCompatibility = JavaVersion.VERSION_1_8
                        targetCompatibility = JavaVersion.VERSION_1_8
                    }
                    buildFeatures.apply {
                        viewBinding = true
                        buildConfig = true
                    }
                    lintOptions.apply {
                        disable("Instantiatable")
                        isAbortOnError = false
                    }
                }
            }
        }

        dependencies {
            constraints {
                api(project(":core:utils"))
                api(project(":core:uikit"))
                api(project(":core:dependencies"))
            }
        }
    ```

### Services

Service module is the parent module for service modules owned by Agree, such as loan, calc, iot and others

#### Services Members

``` mermaid
graph RL
  A[services]
  B[partnership] --> A
  C[monitoring] --> A
  D[infrastructure] --> A
```

??? "build.gradle.kts"

    ``` kotlin
        import com.android.build.gradle.*

        plugins {
            id("java-platform")
        }

        group = "android"
        version = "1.1.0"

        subprojects {
            apply(plugin = "com.android.library")
            apply(plugin = "kotlin-android")
            apply(plugin = "kotlin-parcelize")
            apply(plugin = "kotlin-kapt")
            apply(plugin = "androidx.navigation.safeargs.kotlin")

            plugins.withType(BasePlugin::class.java).configureEach {
                configure<BaseExtension> {
                    compileSdkVersion(31)
                    defaultConfig {
                        minSdk = 21
                        targetSdk = 31
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
                        sourceCompatibility = JavaVersion.VERSION_1_8
                        targetCompatibility = JavaVersion.VERSION_1_8
                    }
                    buildFeatures.apply {
                        viewBinding = true
                        buildConfig = true
                    }
                    lintOptions.apply {
                        disable("Instantiatable")
                        isAbortOnError = false
                    }
                }
            }
        }

        dependencies {
            constraints {
                api(project(":services:monitoring"))
                api(project(":services:infrastructure"))
                api(project(":services:partnership"))
            }
        }
    ```

### Apps

!!! info
    Apps is a Dynamic feature module, so there is no need to configure sub projects

---

## Child Modules

### :utils

The utils module is a module that contains utilities and some reusable functions, and this module can be accessed by all modules in the application except the uikit, analytics and dependencies module.

``` mermaid
graph LR
  A[:utils]
  B[:uikit]
  C[:dependencies]
  D[:base]
  E[:apps]
  F[:services]
  A --> D
  A --> E
  A --> F
  B -.-> A
  C -.-> A
```

??? "build.gradle"

    ``` groovy
        apply from: '../../ktlint.gradle'
        apply plugin: 'kotlin-android'

        android {
            externalNativeBuild {
                cmake {
                    path "CMakeLists.txt"
                }
            }
            ndkVersion '24.0.8215888'
        }

        dependencies {
            implementation project(':core:dependencies')
            implementation project(':core:uikit')
            testImplementation 'junit:junit:4.12'
            debugImplementation "com.github.chuckerteam.chucker:library:3.5.2"
            releaseImplementation "com.github.chuckerteam.chucker:library-no-op:3.5.2"
            implementation 'me.jessyan:progressmanager:1.5.0'
            implementation 'me.jessyan:retrofit-url-manager:1.4.0'
            implementation "androidx.core:core-ktx:+"
            implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0"
            implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
        }
    ```

### :analytics

The analytics module is a module that is responsible for data analytics, in the Agree Ecosystem we use Google Analytics as the main tool and it includes Firebase

``` mermaid
graph LR
    A[:analytics]
    B[:apps]
    C[:base]
    D[:services]
    E[:dependencies]
    A --> B
    A --> C
    A --> D
    E -.-> A
```

??? "build.gradle"

    ``` groovy
        apply from: '../../ktlint.gradle'
        apply plugin: 'kotlin-android'

        dependencies {
            implementation project(':core:dependencies')
            implementation "androidx.core:core-ktx:+"
            implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0"
            implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
        }

        check.dependsOn ktlint
    ```

### :uikit

The Ui Kit Module is a module that is responsible for matters dealing with the user interface and user experience, the Ui Kit Module must also be independent and must not depend on other modules, the Ui Kit Module also contains components from the APL (Agree Product Languages, Design system that created by Agree)

``` mermaid
graph LR
    A[:uikit]
    B[:utils]
    C[:apps]
    D[:base]
    E[:services]
    A --> B
    A --> C
    A --> D
    A --> E
```

??? "build.gradle"

    ``` groovy
        apply from: '../../ktlint.gradle'
        apply plugin: 'kotlin-android'

        dependencies {
            api 'com.oratakashi:AndroidViewBinding:3.19.1'
            implementation 'androidx.appcompat:appcompat:1.4.1'
            implementation 'com.google.android.material:material:1.5.0'
            implementation 'androidx.constraintlayout:constraintlayout:2.1.3'
            implementation 'com.airbnb.android:paris:2.0.0'
            implementation 'androidx.legacy:legacy-support-v4:1.0.0'
            kapt 'com.airbnb.android:paris-processor:2.0.0'
            api "androidx.navigation:navigation-fragment-ktx:2.4.1"
            api "androidx.navigation:navigation-ui-ktx:2.4.1"
            implementation 'com.google.android.flexbox:flexbox:3.0.0'
            implementation "androidx.core:core-ktx:+"
            implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
        }

        check.dependsOn ktlint
    ```

### :dependencies

Dependencies module is a module that is responsible for distributing library dependencies to all modules, if you put a library in this module it is global and public, all modules that depend on module dependencies can access the library

``` mermaid
graph LR
    A[:dependencies]
    B[:utils]
    C[:app]
    D[:sectors]
    E[:services]
    F[:analytics]
    A --> B
    A --> C
    A --> D
    A --> E
    A --> F
```

??? "build.gradle"

    ``` groovy
        dependencies {
            ...

            //Firebase
            api platform('com.google.firebase:firebase-bom:29.0.4')
            api 'com.google.firebase:firebase-analytics-ktx'
            api 'com.google.firebase:firebase-crashlytics'

            //Codebase
            api platform('android:codebase:1.0.0')
            api 'android:codebase-data'
            api 'android:codebase-presentation'
            api 'android:codebase-firebase'
            api 'android:codebase-utils'
            api 'com.oratakashi:AndroidViewBinding:3.19.0'

            ...
        }
    ```

### :calc

The calc module is a apps module "Feed Calculator" in Agree, which has the feature of calculating the amount of feed in fishery and poultry farms

``` mermaid
graph LR
    A[:calc]
    C[:uikit]
    D[:analytics]
    E[:utils]
    F[:dependencies]
    C -.-> A
    D -.-> A
    E -.-> A
    F -.-> A
```

??? "build.gradle"

    ``` groovy
        dependencies {
            implementation project(':core:dependencies')
            implementation project(':core:uikit')
            implementation project(':core:utils')
            implementation "androidx.core:core-ktx:+"
            implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0"
            implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
        }
    ```

### :finance

The finance module is the "***Agree Modal***" apps module on Agree, this module features a capital loan application for users

``` mermaid
graph LR
    A[:finance]
    C[:dependencies]
    D[:utils]
    E[:uikit]
    F[:analytics]
    C -.-> A
    D -.-> A
    E -.-> A
    F -.-> A
```

??? "build.gradle"

    ``` groovy
        dependencies {
            implementation project(':core:dependencies')
            implementation project(':core:uikit')
            implementation project(':core:utils')
            implementation "androidx.core:core-ktx:+"
            implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0"
            implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
        }
    ```

### :iot

The iot module is the "***Agree Smartfarming***" apps module in Agree, this module has iot features for agriculture, fisheries and poultry farming

``` mermaid
graph LR
    A[:iot]
    C[:dependencies]
    D[:utils]
    E[:uikit]
    F[:analytics]
    C -.-> A
    D -.-> A
    E -.-> A
    F -.-> A
```

??? "build.gradle"

    ``` groovy
        dependencies {
            implementation project(':core:dependencies')
            implementation project(':core:uikit')
            implementation project(':core:utils')
            implementation "androidx.core:core-ktx:+"
            implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0"
            implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
        }
    ```

### :prices

The prices module is the "***Info Harga***" apps module in Agree, this module has the feature of monitoring commodity price information on the market

``` mermaid
graph LR
    A[:prices]
    C[:dependencies]
    D[:utils]
    E[:uikit]
    F[:analytics]
    C -.-> A
    D -.-> A
    E -.-> A
    F -.-> A
```

??? "build.gradle"

    ``` groovy
        dependencies {
            implementation project(':core:dependencies')
            implementation project(':core:uikit')
            implementation project(':core:utils')
            implementation "androidx.core:core-ktx:+"
            implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0"
            implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
        }
    ```

### :weather

The weather module is the "***Info Cuaca***" apps module in Agree, this module has the feature of monitoring weather information at the user's location

``` mermaid
graph LR
    A[:weather]
    C[:dependencies]
    D[:utils]
    E[:uikit]
    F[:analytics]
    C -.-> A
    D -.-> A
    E -.-> A
    F -.-> A
```

??? "build.gradle"

    ``` groovy
        dependencies {
            implementation project(':core:dependencies')
            implementation project(':core:uikit')
            implementation project(':core:utils')
            implementation "androidx.core:core-ktx:+"
            implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0"
            implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
        }
    ```

### :partnership

The partnership module is a service module that is responsible for handling everything related to the partnership, such as partnership registration, checking registration status, company partner details and others

``` mermaid
graph LR
    A[:partnership]
    B[:dependencies]
    C[:utils]
    D[:uikit]
    E[:analytics]
    F[:base]
    B -.-> A
    C -.-> A
    D -.-> A
    E -.-> A
    A --> F
```

??? "build.gradle"

    ``` groovy
        apply from: '../../ktlint.gradle'

        dependencies {
            implementation project(':core:analytics')
            implementation project(':core:dependencies')
            implementation project(':core:uikit')
            implementation project(':core:utils')
        }

        check.dependsOn ktlint
    ```

### :infrastructure

Module infrastructure is a service module that is responsible for the features of land, ponds, and farm cages

``` mermaid
graph LR
    A[:infrastructure]
    B[:dependencies]
    C[:utils]
    D[:uikit]
    E[:analytics]
    F[:base]
    B -.-> A
    C -.-> A
    D -.-> A
    E -.-> A
    A --> F
```

??? "build.gradle"

    ``` groovy
        apply from: '../../ktlint.gradle'

        dependencies {
            implementation project(':core:analytics')
            implementation project(':core:dependencies')
            implementation project(':core:uikit')
            implementation project(':core:utils')
        }

        check.dependsOn ktlint
    ```

### :monitoring

The monitoring module is a service module that is responsible for recording the daily activities of farmers, and cultivation SOPs

``` mermaid
graph LR
    A[:monitoring]
    B[:dependencies]
    C[:utils]
    D[:uikit]
    E[:analytics]
    F[:base]
    B -.-> A
    C -.-> A
    D -.-> A
    E -.-> A
    A --> F
```

??? "build.gradle"

    ``` groovy
        apply from: '../../ktlint.gradle'

        dependencies {
            implementation project(':core:analytics')
            implementation project(':core:dependencies')
            implementation project(':core:uikit')
            implementation project(':core:utils')
        }

        check.dependsOn ktlint
    ```
