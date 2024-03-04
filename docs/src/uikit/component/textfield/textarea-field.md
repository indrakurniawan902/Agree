# TextAreaField

TextAreaField is a text input that can only be used to enter large amounts of text, TextAreaField already includes ui/ux for errors, hints, placeholders and more

## Component Name

* [**AGR**] : AgrTextAreaField

* [**LGN**] : LgnTextAreaField

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
|Input Type|`android:inputType`|To set input type on text field directly via xml|
|Enable Status|`android:enabled`|To set enable or disable text field directly via xml|
|Required Status|`app:isRequired`|To set required status and automaticly add **`*`** on hint label directly via xml|
|Optional Status|`app:isOptional`|To set optional status and automaticly add **`(Opsional)`** on hint label directly via xml|

!!! warning "Notes"
    You can't set **isRequired** true and **isOptional** true at same time

## How to Use

### Implement in Code

=== "Static in Xml [AGR]"
    ``` xml title="layout_sample.xml"
    <com.agree.uikit.widget.textfield.AgrTextAreaField
            android:id="@+id/etNotes"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="Catatan"
            app:placeholderText="Tambahkan Catatan"
            app:isOptional="true"/>
    ```
=== "Dynamic using Kotlin [AGR]"
    ``` kotlin title="SampleFragment.kt"
    ...
    with(binding) {
        containerBase.addView( //ViewGroup for Dynamic Layout
            AgrTextAreaField(requiredContext()).apply {
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
    <com.telkom.legion.component.textfield.LgnTextAreaField
            android:id="@+id/etNotes"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="Catatan"
            app:placeholderText="Tambahkan Catatan"
            app:isOptional="true"/>
    ```
=== "Dynamic using Kotlin [LGN]"
    ``` kotlin title="SampleFragment.kt"
    ...
    with(binding) {
        containerBase.addView( //ViewGroup for Dynamic Layout
            LgnTextAreaField(requiredContext()).apply {
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
