# Basic Knowladge

Some guides and basic knowledge before you start exploring deeper into this Agree Ecosystem project

## Prerequisite

- **Android Studio**: Flamigo version ***2022.2.1 Patch 1*** or higher
- **JDK**: Java Development Kit 17 or higher
- **Terminal**: If you using **Mac** or **Linux** you can use native terminal, but if you using **Windows** you can use [Gitbash](https://git-scm.com/downloads) or [Hyper](https://hyper.is/#installation) or another terminal that can run bash scripts
- **Git Client**: If you using **Mac** or **Linux** you can use embaded git client, but if you using **Windows** you can use [Gitbash](https://git-scm.com/downloads)
- **Android Version** : Android Lollipop 5.1.1 SDK 22 or higher

## Initial Configuration

- Open your global `gradle.properties` directory on with ***Terminal***:
    * For **Windows** : `C:\Users\<you>\.gradle\gradle.properties`

    ``` bash title="Terminal"
    cd C:\Users\<you>\.gradle
    ```

    * For **Mac/Linux** : `/Users/<you>/.gradle/gradle.properties`

    ``` bash title="Terminal"
    cd ~/.gradle
    ```

- Create global `gradle.properties`, If you already have it, you can skip this step

    ``` bash title="Terminal"
    touch gradle.properties
    ```

- Add Nexus Credentials to global `gradle.properties`

    ``` bash title="Terminal"
    echo "nexus_username=??" >> gradle.properties && echo "nexus_password=??" >> gradle.properties
    ```

- Add Legion Credentials to global `gradle.properties`

    ``` bash title="Terminal"
    echo "legion_username=??" >> gradle.properties && echo "legion_password=??" >> gradle.properties
    ```

    !!! warning "Credentials Placeholder"
        for credentials **nexus** and **legion** please ask Maintener to replace placeholder `??` with credentials
