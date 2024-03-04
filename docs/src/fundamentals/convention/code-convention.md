# Code Convention

## Class/File Naming

- For Regular Class use PascalCase : class Login

- For datasource use PascalCase with suffix DataSource : class ProductDataSource

- For Model Data Class use PascalCase : data class User

- For Data Access Object use PascalCase with suffix Dao : ProductDao

- For Repository use PascalCase with suffix Repository : ProductRepository

- For End point use pascalCase with suffix ApiService: ProductApiService

- Kotlin Extension Function put in one package with Ext as suffix in file ViewExt

## Variable Naming

Use camelCase and explicit type data

Example

``` kotlin title="Sample.kt"
val fullName : String = "Agree"
```

## Constants and PreferenceKey

Put all constants in one seperate object class according to its purpose.

For all preference key put all in Constant Object Class

Constant use CAPITAL_CHARACTER : Constant.USER_ID

## Enum Class

Naming use PascalCase : `enum class OrderType`

Example

```kotlin title="State.kt"
enum class State(val value: Int) {
    COLLAPSED(0),
    COLLAPSING(1),
    EXPANDING(2),
    EXPANDED(3)
}
```

## Comment Style

If there are feature with complex function use comment (//) or (/**) before function

Example

``` kotlin title="Sample.kt"
/**
 * TODO ....
 * @property ....
 * @return .....
 */
```
