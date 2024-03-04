# AgrRadioGroup

AgrRadioGroup is a container for radio button handles, this is a derivative of the Material Radio group with the addition of a utility that makes it easier to set radio buttons and add a separator in each row of radio buttons.

## Component

|Component Name|Id|Description|
|---|---|---|
|Same with Material Radio Group|

## Xml Attributes

|Attribute Name|Xml Attrs|Description|
|---|---|---|
|Show Dividers|`android:showDividers`|To set visibility dividers directly in xml|
|Same with Material Radio Group|

## How to Use

### Implement in XML

=== "Static in Xml"
    ``` xml title="layout_sample.xml"
    <com.agree.uikit.widget.radio.AgrRadioGroup
        android:id="@+id/rgSample"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:showDividers="middle"
        android:layout_marginHorizontal="16dp"
        android:layout_marginTop="16dp">
        <com.agree.uikit.widget.radio.AgrRadioButton
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Sample 1"/>
        <com.agree.uikit.widget.radio.AgrRadioButton
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Sample 2"/>
    </com.agree.uikit.widget.radio.AgrRadioGroup>
    ```
    !!! warning
        You can only add child with **AgrRadioButton**, If you add with other layout it will throw Exception and cause Force Close!
=== "Dynamic using Kotlin"
    ``` xml title="layout_sample.xml"
    <com.agree.uikit.widget.radio.AgrRadioGroup
        android:id="@+id/rgSample"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:showDividers="middle"
        android:layout_marginHorizontal="16dp"
        android:layout_marginTop="16dp"/>
    ```

### Add Radio Button From Kotlin

```kotlin title="SampleFragment.kt"
rgSample.addAll( //rgSample is AgrRadioGroup ID on Xml
    listOf(
        "Kategori 1",
        "Kategori 2",
        "Kategori 3"
    )
)
```

!!! info
    You can pass a list string in the `addAll()` parameter

!!! warning
    Be careful, adding dynamically will remove the existing list of radio buttons, and replace them with a new list

### Add Checked Listener

```kotlin title="SampleFragment.kt"
rgSample.setListener { //rgSample is AgrRadioGroup ID on Xml
    toast(it)
}
```

!!! info
    You can get checked value from listener by using **`it`**

### Get Checked Text Value

```kotlin title="SampleFragment.kt"
val text: String = rgSample.text //rgSample is AgrRadioGroup ID on Xml
```

!!! info
    Attribute text will return checked value, If the user has not checked the radio button, it will return an empty string

### Set Checked Text

```kotlin title="SampleFragment.kt"
rgSample.text = "Kategori 1" //rgSample is AgrRadioGroup ID on Xml
```

!!! info
    AgrRadioGroup can automatically detect if the given text is in the radio button list, AgrRadioGroup will immediately change it to checked status, and if it is not found from the radio button list it will be skipped.

### Show Dividers

#### Divider Type

- **none** ***(default)*** : No added dividers

- **beginning** : Adding dividers at the beginning of the line

- **middle** : Adding dividers at the middle of the line

- **end** : Adding dividers at the end of the line

```xml title="layout_sample.xml" hl_lines="5"
<com.agree.uikit.widget.radio.AgrRadioGroup
    android:id="@+id/rgSample"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:showDividers="middle"
    android:layout_marginHorizontal="16dp"
    android:layout_marginTop="16dp">
```
