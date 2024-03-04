# Multiple Image Field

MultiplePhotoField is an image picker component that has been built in with a request permission handler, and multiple image selection, MultiplePhotoField includes ui/ux for errors, hints, placeholders and more

## Component Name

* [**AGR**] : MultiplePhotoField

* [**LGN**] : MultiplePhotoField

## Component

|Component Name|Id|Description|
|---|---|---|
|TextView|tvHint|To display hint or label in text input|
|HorizontalScrollView|hsvImgPhoto|To display scrollable content if the image exceeds the phone screen|
|LinearLayout|containerImgPhoto|To display the image from the image picker dialog|
|TextView|tvHelper|To display help text below the input text if needed|
|TextView|tvError|To display error messages, this view will only appear if there are errors and will disappear when there are no errors|

## Xml Attributes

|Attribute Name|Xml Attrs|Description|
|---|---|---|
|Hint|`android:hint`|To set Hint label directly via xml|
|Helper Text|`app:helperText`|To set helper text directly via xml|
|Enable Status|`android:enabled`|To set enable or disable text field directly via xml|
|Required Status|`app:isRequired`|To set required status and automaticly add **`*`** on hint label directly via xml|
|Optional Status|`app:isOptional`|To set optional status and automaticly add **`(Opsional)`** on hint label directly via xml|
|Limit|`app:photoLimit`|To set maximum limit component can handle image directly via xml|

!!! warning "Notes"
You can't set **isRequired** true and **isOptional** true at same time

## How to Use

### Implement in Code

=== "Static in Xml [AGR]"
``` xml title="layout_sample.xml"
<com.agree.uikit.widget.image.MultiplePhotoField
android:id="@+id/multiplePhoto"
android:hint="Upload Foto"
app:isRequired="true"
app:photoLimit="2"
app:helperText="Harap tambahkan foto pendukung!"
android:layout_width="match_parent"
android:layout_height="wrap_content"/>
```
=== "Dynamic using Kotlin [AGR]"
``` kotlin title="SampleFragment.kt"
...
with(binding) {
containerBase.addView( //ViewGroup for Dynamic Layout
MultiplePhotoField(requiredContext()).apply {
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
<com.telkom.legion.component.image.MultiplePhotoField
android:id="@+id/multiplePhoto"
android:hint="Upload Foto"
app:isRequired="true"
app:photoLimit="2"
app:helperText="Harap tambahkan foto pendukung!"
android:layout_width="match_parent"
android:layout_height="wrap_content"/>
```
=== "Dynamic using Kotlin [LGN]"
``` kotlin title="SampleFragment.kt"
...
with(binding) {
containerBase.addView( //ViewGroup for Dynamic Layout
MultiplePhotoField(requiredContext()).apply {
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

### Add Listener

```kotlin title="SampleFragment.kt"
multiplePhoto.addListener(object: MultipleImageUploadListener {
    override fun onImageListChanged(files: List<File>) {
        //Your Action on Listener
    }
})
```

### Add Image Permission

```xml title="AndroidManifest.xml"
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.CAMERA" />
```

### Add List Images Url

```kotlin title="SampleFragment.kt"
multiplePhoto.addAll(listOf(
    "https://google.com/images/weeding/male.jpg",
    "https://google.com/images/weeding/female.jpg"
))
```

!!! warning "Notes"
Every url that is displayed in MultiplePhotoField will be automatically cached by the system, don't forget to call the `removeAllCachedImages()` method to clear the cache that has been rendered by this component

#### How it Works

!!! info
If the graph doesn't appear after decrypting the page, please try reloading this page again

??? info "Flowchart Diagram"

    ``` mermaid
    graph TB
        START([Start])
        END([End])
        IF{isEmpty?}
        A[Input List of Images Url]
        B[Do Foreach]
        C[Load as Bitmap]
        D[Show on UI]
        E[Cache into Tmp Files]
        F[Add to List Rendered Images]

        START --> A
        A --> IF
        IF -- No --> B
        IF -- Yes --> END
        B -- Item of Url --> C
        C --> D
        C --> E
        E --> F
        F --> END
        D --> END
    ```

### Remove All Cached Images

```kotlin title="SampleFragment.kt"
multiplePhoto.removeAllCachedImages()
```

