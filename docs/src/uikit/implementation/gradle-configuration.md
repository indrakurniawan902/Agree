# Gradle Configuration

The UI Kit has now been published in ***Nexus Playcourt***, and all tribe in Telkom can use uikit only with the implement library.

## Add Telkom Repository Nexus on Gradle

=== "Gradle 7 or above"
    ```groovy title="settings.gradle"
    dependencyResolutionManagement {
        repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
        repositories {
            google()
            mavenCentral()
            ...
            maven {
                url "https://nexus.playcourt.id/repository/dev/"
                credentials {
                    username getProperty("nexus_username") ?: "developer"
                    password getProperty("nexus_password") ?: "password"
                }
            }
        }
    }
    ```
=== "Gradle 6 or below"
    ```groovy title="build.gradle"
    allprojects {
        repositories {
            google()
            mavenCentral()
            ...
            maven {
                url "https://nexus.playcourt.id/repository/dev/"
                credentials {
                    username project.properties["nexus_username"] ?: "developer"
                    password project.properties["nexus_password"] ?: "password"
                }
            }
        }
    }
    ```

## Add UI Kit Dependency on build.gradle

```groovy title="build.gradle"
dependencies {
    implementation platform('com.telkom.legion:android-platform:0.1.0-UPSTREAM18')
    implementation 'com.telkom.legion:android-component'
    implementation 'com.telkom.legion:android-extension'
}
```

!!! warning
    The UI Kit is currently still in the **testing** stage, there may be many changes and additions to features, components, or massive changes
