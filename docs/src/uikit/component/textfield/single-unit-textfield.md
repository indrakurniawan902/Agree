# SingleUnitField

SingleUnitField is a text input which can only be used for dropdown input using bottom sheet picker and also have text input layout, SingleUnitField already includes ui/ux for errors, hints, placeholders and more

## Component Name

* [**AGR**] : AgrSingleUnitField

* [**LGN**] : LgnSingleUnitField

## Component

|Component Name|Id|Description|
|---|---|---|
|TextView|tvHint|To display hint or label in text input|
|TextInputLayout|etBase|To display hint or label in text input|
|TextView|tvHelper|To display help text below the input text if needed|
|TextView|tvError|To display error messages, this view will only appear if there are errors and will disappear when there are no errors|

## Xml Attributes

|Attribute Name|Xml Attrs|Description|
|---|---|---|
|Hint|`android:hint`|To set Hint label directly via xml|
|Text|`android:text`|To set Text value directly via xml|
|Placeholder|`app:placeholderText`|To set placeholder text directly via xml|
|Helper Text|`app:helperText`|To set helper text directly via xml|
|Enable Status|`android:enabled`|To set enable or disable text field directly via xml|
|Required Status|`app:isRequired`|To set required status and automaticly add **`*`** on hint label directly via xml|
|Optional Status|`app:isOptional`|To set optional status and automaticly add **`(Opsional)`** on hint label directly via xml|

!!! warning "Notes"
    You can't set **isRequired** true and **isOptional** true at same time

### Implement in Code

=== "Static in Xml [AGR]"
    ``` xml title="layout_sample.xml"
    <com.agree.uikit.widget.textfield.AgrSingleUnitField
            android:id="@+id/etUnit"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="Luas Area"
            app:placeholderText="Masukkan luas area"
            android:inputType="number"/>
    ```
=== "Dynamic using Kotlin [AGR]"
    ``` kotlin title="SampleFragment.kt"
    ...
    with(binding) {
        containerBase.addView( //ViewGroup for Dynamic Layout
            AgrSingleUnitField(requiredContext()).apply {
                //Your View's customization here
            },
            LinearLayout.LayoutParams( //For example we use viewgroup LinearLayout
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        )
    }
    ...
    ```
=== "Static in Xml [LGN]"
    ``` xml title="layout_sample.xml"
    <com.telkom.legion.component.textfield.LgnSingleUnitField
            android:id="@+id/etUnit"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="Luas Area"
            app:placeholderText="Masukkan luas area"
            android:inputType="number"/>
    ```
=== "Dynamic using Kotlin [LGN]"
    ``` kotlin title="SampleFragment.kt"
    ...
    with(binding) {
        containerBase.addView( //ViewGroup for Dynamic Layout
            LgnSingleUnitField(requiredContext()).apply {
                //Your View's customization here
            },
            LinearLayout.LayoutParams( //For example we use viewgroup LinearLayout
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        )
    }
    ...
    ```

### Add Unit Change Listener

```kotlin title="SampleFragment.kt"
etUnit.setOnUnitListener { text -> //text is selected value from BottomSheet
    //Your Action on Listener
}
```

### Show Bottom Sheet [***Default Behaviour***]

!!! info
    Dropdown by default has the behavior of displaying a bottom sheet containing a list of radio buttons.
    But you must give current your *FragmentManager* from your existing Fragment or Activity

```kotlin title="SampleFragment.kt"
etUnit.fragmentManager = childFragmentManager
```

### Add Click Listener

!!! info
    Dropdown by default has the behavior of displaying a bottom sheet containing a list of radio buttons. If you want to override the dropdown behavior, you can override it in the following way

```kotlin title="SampleFragment.kt"
etUnit.setOnClickListener { view ->
    //Your Action on Listener
}
```

!!! warning
    Don't Give *FragmentManager* into Dropdown if you want override behaviour

### Add Data in List

```kotlin title="SampleFragment.kt"
etUnit.addAll(
    listOf(
        "Kategori 1",
        "Kategori 2",
        "Kategori 3"
    )
)
```

!!! info
    You can just put list of String into dropdown field, and dropdown field will automatic show list into BottomSheet picker with list of radio button
