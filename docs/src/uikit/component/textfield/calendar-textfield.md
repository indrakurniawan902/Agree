# CalendarField

CalendarField is a text input which can only be used for date input using date picker, CalendarField already includes ui/ux for errors, hints, placeholders and more

## Component Name

* [**AGR**] : AgrCalendarField

* [**LGN**] : LgnCalendarField

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
|Date Format|`app:dateFormat`|To set date format directly via xml|

!!! warning "Notes"
    You can't set **isRequired** true and **isOptional** true at same time

### Implement in Code

=== "Static in Xml [AGR]"
    ``` xml title="layout_sample.xml"
    <com.agree.uikit.widget.textfield.AgrCalendarField
            android:id="@+id/etCalendar"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="Tanggal Lahir"
            app:dateFormat="FULL_DATE"
            app:endIconDrawable="@drawable/ic_calendar"
            app:helperText="Pilih tanggal lahir anda!"
            app:placeholderText="Tanggal Lahir" />
    ```
=== "Dynamic using Kotlin [AGR]"
    ``` kotlin title="SampleFragment.kt"
    ...
    with(binding) {
        containerBase.addView( //ViewGroup for Dynamic Layout
            AgrCalendarField(requiredContext()).apply {
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
    <com.telkom.legion.component.textfield.LgnCalendarField
            android:id="@+id/etCalendar"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="Tanggal Lahir"
            app:dateFormat="FULL_DATE"
            app:endIconDrawable="@drawable/ic_calendar"
            app:helperText="Pilih tanggal lahir anda!"
            app:placeholderText="Tanggal Lahir" />
    ```
=== "Dynamic using Kotlin [LGN]"
    ``` kotlin title="SampleFragment.kt"
    ...
    with(binding) {
        containerBase.addView( //ViewGroup for Dynamic Layout
            LgnCalendarField(requiredContext()).apply {
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

### Date Format

* **DEFAULT_DATE**: DD/MM/YYYY

* **FULL_DATE**: DD MMMM YYYY

* **SIMPLE_DATE**: DD MMM YYYY

```kotlin title="SampleFragment.kt"
etCalendar.dateFormat = DateFormat.DEFAULT_DATE
```
