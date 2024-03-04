# Project Structure

let's unbox the Agree Ecosystem project more deeply by getting to know the project structure in this application, As you know, we use [clean architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html) so you have to group your code based on its' purposes and functionality

``` kotlin
    📦 Root Package
    ┣ 📂 data
    ┃ ┗ 📂 reqres
    ┃   ┣ 📂 model
    ┃   ┃ ┗ 📂 login
    ┃   ┃   ┗ 📜 LoginItem.kt
    ┃   ┃   ┗ 📜 LoginBodyPost.kt
    ┃   ┣ 📂 web
    ┃   ┃ ┣ 📜 AgreeAuthApi.kt
    ┃   ┃ ┗ 📜 AgreeAuthApiClient.kt
    ┃   ┃
    ┃   ┣ 📜 AuthDataStore.kt
    ┃   ┗ 📜 AuthRepository.kt
    ┃
    ┣ 📂 di
    ┃ ┣ 📂 features
    ┃ ┃ ┗ 📜 ReqresModule.kt
    ┃ ┃
    ┃ ┣ 📜 AppModule.kt
    ┃ ┗ 📜 NavigationModule.kt
    ┃
    ┣ 📂domain
    ┃ ┗ 📂reqres
    ┃ ┃ ┣ 📂model
    ┃ ┃ ┃ ┗ 📜 Login.kt
    ┃ ┃ ┃
    ┃ ┃ ┣ 📜 AuthInteractor.kt
    ┃ ┃ ┗ 📜 AuthUseCase.kt
    ┃ ┃
    ┣ 📂 presentation
    ┃ ┣ 📂 auth
    ┃ ┃ ┣ 📂 login
    ┃ ┃ ┃ ┣ 📜 LoginDataContract.kt
    ┃ ┃ ┃ ┣ 📜 LoginObserver.kt
    ┃ ┃ ┃ ┣ 📜 LoginFragment.kt
    ┃ ┃ ┃ ┗ 📜 LoginViewModel.kt
    ┃ ┃ ┗ 📜 ContainerAuthFragment.kt
    ┃ ┣ 📂 base
    ┃ ┃ ┣ 📂 activity
    ┃ ┃ ┃ ┗ 📂 main
    ┃ ┃ ┃   ┗ 📜 MainActivity.kt
    ┃ ┃ ┗ 📂 splash
    ┃ ┃   ┗ 📜 SplashFragment.kt
    ┃ ┣ 📂 menu
    ┃ ┃ ┣ 📂 home
    ┃ ┃ ┃ ┣ 📜 HomeDataContract.kt
    ┃ ┃ ┃ ┣ 📜 HomeObserver.kt
    ┃ ┃ ┃ ┣ 📜 HomeFragment.kt
    ┃ ┃ ┃ ┗ 📜 HomeViewModel.kt
    ┃ ┃ ┗ 📜 ContainerMenuFragment.kt
    ┃ ┗ 📂 navigation
    ┃   ┗ 📂 auth
    ┃     ┣ 📜 AuthNavigation.kt
    ┃     ┗ 📜 AuthNavigationUsecase.kt
    ┣ 📂utils
    ┃ ┗ 📜 SomeUtils.kt
    ┃
    ┗ 📜 MainApp.kt
```

Let's dive in on each and every one of them:

## Data *(Layer)*

This layer basically handle all data source, from getting data from web service or saving it to local database. Inside it, you can save as many service as you need and you can name that service as you want *but always represent what it do*. Take a look at the example above, inside it we have **reqres** service. And from there, let's break what inside of every service should look like.

- **db**, this  package is where you store your *Database Access Object*. Keep in mind that we use [Room Database](https://developer.android.com/topic/libraries/architecture/room) for Local Database Framework. And it should always inherit `DevDao`
  >Naming convention for dao is: `{Purpose}Dao.kt`
- **model**, this package is where you store your model object either is for web service or local database.
  > Naming convention for object from API is : `{Purpose}Item.kt`<br/>
    Naming convention for object for Local Database is : `{Purpose}Entity.kt`

- **web**, this package contains class for hitting API purposes. And do remember that we use [Retrofit](https://github.com/square/retrofit) for that.
  - `ApiClient`, is an interface where we define our endpoint
    > Naming convention for ApiClient is : `{Purpose}ApiClient.kt`
  - `Api`, is a class that implement ApiClient, should always implement `WebService`
    > Naming convention for Api is : `{Purpose}Api.kt`
<br/>

Next to the three packages we just mentioned, you have two other classes that you should define. Both of them are:

- `Repository`, it's an interface that define every process that you do with data source. Like syncing data, hitting api, load data from api, etc. And all of that process define in method each.
  > Naming convention for Repository is : `{Purpose}Repository.kt`
- `DataStore`, it's a class that inherit Repository. In this class you after you override the method in Repository, you define how you want to do that in every one of them.
  > Naming convention for DataStore is : `{Purpose}DataStore.kt`

**P.S.** outside all of service package, now you have `Database` class for Room Database. It's should have next to all of service package if you implement Local Database.
>Naming convention for database is: `{AppName}Database.kt` or `{ServiceName}Database.kt`

## DI *(Dependency Injection)*

As you know, this codebase use [Koin](https://insert-koin.io/) to implement Dependency injection. To store modules, you store it inside this package. **But do remember**, for injection that used globally you store it right inside di package but if the injection for specific-purposes like `ViewModel` you store it inside features package by creating a Kotlin file.
> Naming convention for that file is : `{Purpose}Module.kt`

## Domain *(Layer)*

This package is where you map your model object from *data layer* to a new model object to use in *presentation layer*. Beside doing that, you should also consider removing variables that you do not use so it's only what matters showed. The package inside domain package **should always** follow the data layer. And inside of every packages, you should have:

- **Model** Package, here you define new model object that you will use in *presentation layer*
  > Naming convention for model object in domain layer is : `{Purpose}.kt`

- **UseCase**, it's an interface where you define every process that you use to bridge the *data layer* and the *presentation layer* to methods.
  > Naming convention for UseCase is : `{Purpose}UseCase.kt`

- **Interactor**, here you implement usecase  and do the data mapping that we talked about
  > Naming convention for Interactor is : `{Purpose}Interactor.kt`

## Presentation *(Layer)*

As written in the package, this package handle all the presentation or everything that you on see on the device. it stores your activities, fragments, adapters, navigation, observer and even ViewModels. You can create package inside it based on page you need. but in general, the presentation layer consists of 4 files:

- **DataContract**, This interface defines what actions and states will be used in your Fragment/Activity, this refers to the SOLID principle
  > Naming convention for DataContract is : `{Purpose}DataContract.kt`
- **Observer**, This observer class serves to observe data from the livedata provided by the viewmodel, and is passed to the fragment / activity via the DataContract interface.
  > Naming convention for Observer is : `{Purpose}Observer.kt`
- **ViewModel**, ViewModel class is designed to store and manage UI-related data in a lifecycle conscious way. The ViewModel class allows data to survive configuration changes such as screen rotations.
  > Naming convention for ViewModel is : `{Purpose}ViewModel.kt`
- **Fragment**, I think all developers already know what a fragment is, so I don't need to explain it again here
  > Naming convention for Fragment is : `{Purpose}Fragment.kt`

## Utils

This package is where you put all of your utilities and project-specific configuration like extention function or other utility that you use across your apps. You can group utilities into packages inside utils package based on its function.
