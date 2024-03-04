# PrimaryFitButton

PrimaryFitButton is a Button with the main color, has a wide characteristic that fills the screen, PrimaryFitButton includes ui/ux in it

## Component Name

* [**LGN**] : LgnPrimaryFitButton

## Component

|Component Name|Id|Description|
|---|---|---|
|TextView|tvText|To display label on Button|
|Progress Bar|pbButton|To progress indicator on Button|

## Xml Attributes

|Attribute Name|Xml Attrs|Description|
|---|---|---|
|Text|`android:text`|To set Text value directly via xml|
|Enable Status|`android:enabled`|To set enable or disable button directly via xml|
|Loading Status|`app:isLoading`|To set loading status button directly via xml|

## How to Use

### Implement in Code

=== "Static in Xml [LGN]"
    ``` xml title="layout_sample.xml"
    <com.telkom.legion.component.button.fill.LgnPrimaryFitButton
            android:id="@+id/btnFitPrimary"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginHorizontal="16dp"
            android:layout_marginTop="20dp"
            android:text="Fit Button" />
    ```
=== "Dynamic using Kotlin [LGN]"
    ``` kotlin title="SampleFragment.kt"
    ...
    with(binding) {
        containerBase.addView( //ViewGroup for Dynamic Layout
            LgnPrimaryFitButton(requiredContext()).apply {
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

### Add Click Listener

```kotlin title="SampleFragment.kt"
btnFitPrimary.setOnClickListener {
    //Your Action on Listener
}
```
