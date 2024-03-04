# Toolbar White [BETA]

White toolbar is the basic toolbar of the Legion component.

## Component Name

* [**AGR**] : AgrToolbarWhite

* [**LGN**] : LgnToolbarWhite

## Component

|Component Name|Id|Description|
|---|---|---|
|ImageButton|btnBack|To display navigation button on Toolbar|
|TextView|tvTitle|To display title on Toolbar|
|ImageButton|btnAction|To display action button on Toolbar|

!!! info
    This component is ***beta*** state, this component is still under development so there are very few features available, and there are still many limitations

## Xml Attributes

|Attribute Name|Xml Attrs|Description|
|---|---|---|
|Title|`android:text`|To set title directly via xml|
|Hide Back Button|`app:hideBackButton`|To set visibility navigation button directly via xml|
|Action Button|`app:endIconDrawable`|To set action button icon directly via xml|

## How to Use

### Implement in Code

=== "Static in Xml [AGR]"
    ``` xml title="layout_sample.xml"
    <com.agree.uikit.widget.toolbar.AgrToolbarWhite
            android:id="@+id/toolbar"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Sample Ui Kit"
            app:hideBackButton="true"
            app:layout_scrollFlags="scroll|enterAlways"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent" />
    ```
=== "Dynamic using Kotlin [AGR]"
    ``` kotlin title="SampleFragment.kt"
    ...
    with(binding) {
        containerBase.addView( //ViewGroup for Dynamic Layout
            AgrToolbarWhite(requiredContext()).apply {
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
    <com.telkom.legion.component.toolbar.LgnToolbarWhite
            android:id="@+id/toolbar"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Sample Ui Kit"
            app:hideBackButton="true"
            app:layout_scrollFlags="scroll|enterAlways"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent" />
    ```
=== "Dynamic using Kotlin [LGN]"
    ``` kotlin title="SampleFragment.kt"
    ...
    with(binding) {
        containerBase.addView( //ViewGroup for Dynamic Layout
            LgnToolbarWhite(requiredContext()).apply {
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

### Add Navigation Button Listener

```kotlin title="SampleFragment.kt"
toolbar.setBackButtonOnClick {
    //Your Action on Listener
}
```

### Add Action Button Listener

```kotlin title="SampleFragment.kt"
toolbar.setEndIconOnClick {
    //Your Action on Listener
}
```

### Intergrate with Nav Controller

```kotlin title="SampleFragment.kt"
toolbar.navController = findNavController()
```
