# PrimaryOutlineSlimButton

PrimaryOutlineSlimButton is a Button with outline variant and using primary color, has a characteristic that wrap content on Button Component,  PrimaryOutlineSlimButton includes ui/ux in it

## Component Name

* [**LGN**] : LgnPrimaryOutlineSlimButton

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
    <com.telkom.legion.component.button.outline.LgnPrimaryOutlineSlimButton
            android:id="@+id/btnOutlineSlimPrimary"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginStart="@dimen/dimen_16dp"
            android:layout_marginTop="@dimen/dimen_20dp"
            android:layout_marginEnd="@dimen/dimen_16dp"
            android:text="Large Button"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@+id/btnSmallPrimary" />
    ```
=== "Dynamic using Kotlin [LGN]"
    ``` kotlin title="SampleFragment.kt"
    ...
    with(binding) {
        containerBase.addView( //ViewGroup for Dynamic Layout
            LgnPrimaryOutlineSlimButton(requiredContext()).apply {
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
btnOutlineSlimPrimary.setOnClickListener {
    //Your Action on Listener
}
```
