# Snack Bar

Snackbars notify users of processes that have been or will be performed by the app. They appear temporarily, towards the bottom of the screen. They must not interfere with the user experience, and they do not require user input to disappear.

---

![snackbar](/assets/images/snack-bar.png)

## Snack Bar Variant

* Normal ( ***Using White Background Colors*** )

* Error ( ***Using Red Background Colors*** )

* Warning ( ***Using Yellow Background Colors*** )

* Success ( ***Using Green Background Colors*** )

## Snack Bar Features

* Title ( *Display title text on snack bar* )

* Subtitle ( *Display sub title text on snack bar* )

* Icon Image ( *Display Icon Image on snackbar* )

* Avatar Image ( *Display Avatar Image on snack bar* )

* Action Button ( *Display Action Button on snackbar* )

## How to Use

We use the ***Builder Pattern***, so developers can customize as needed

```kotlin title="SampleFragment.kt"
AgrSnackbar.setup(requireActivity()) { //Fill setup() params with activity
    // Fill params what you need to show in snackbars
}.show()
```

---

***Example Use title and description***

```kotlin title="SampleActivity.kt"
AgrSnackbar.setup(this) { //Fill setup() params with activity
    title = "Your Message Title"
    description = "Your Description"
    type = AgrSnackbar.ToastType.NORMAL
}.show()
```

***Example simple usecase, you can set just title or just description***

```kotlin title="SampleActivity.kt"
AgrSnackbar.setup(this) { //Fill setup() params with activity
    title = "Your Message Title"
}.show()
```

```kotlin title="SampleActivity.kt"
AgrSnackbar.setup(this) { //Fill setup() params with activity
    description = "Your Description"
}.show()
```

---

## Params

### title: String

``` kotlin title="Example.kt"
AgrSnackbar.setup(requireActivity()) { //Fill setup() params with activity
    title = "Hello Agree"
}.show()
```

!!! error "Required Field"
    title is required field, if you pass it, and not set any field, Snack Bar will show blank view's,
    You must fill at least one field. but if you don't need a title at your snack bar, you can skip title field
    and just fill a description field.

---

### description: String

``` kotlin title="Example.kt"
AgrSnackbar.setup(requireActivity()) { //Fill setup() params with activity
    description = "This is Description"
}.show()
```

!!! info "Optional"
    Description is optional, If you do not fill in this field, this field will not be displayed in the Snack Bar

---

### type: ToastType

There are several types of variants:

* ToastType.**NORMAL** (*default*)

* ToastType.**WARNING**

* ToastType.**ERROR**

* ToastType.**SUCCESS**

``` kotlin title="Example.kt"
AgrSnackbar.setup(requireActivity()) { //Fill setup() params with activity
    type = AgrSnackbar.ToastType.SUCCESS
}.show()
```

!!! info "Optional"
    type is optional, If you do not fill in this field, default type will be displayed in the Snack Bar

---

### duration: Snackbar.Duration

There are several snackbar duration:

* Snackbar.LENGTH_SHORT (*default*)

* Snackbar.LENGTH_LONG

* Snackbar.LENGTH_INDEFINITE

``` kotlin title="Example.kt"
AgrSnackbar.setup(requireActivity()) { //Fill setup() params with activity
    duration = Snackbar.LENGTH_SHORT
}.show()
```

!!! info "Optional"
    type is optional, If you do not fill in this field, default duration will be displayed in the Snack Bar

---

### actionText: String

``` kotlin title="Example.kt"
AgrSnackbar.setup(requireActivity()) { //Fill setup() params with activity
    actionText = "Refresh"
}.show()
```

!!! info "Optional"
    Action Text is optional, If this field is not filled, it will be replaced with a close icon on the Snack Bar

---

### icon: Drawable*

``` kotlin title="Example.kt"
AgrSnackbar.setup(requireActivity()) { //Fill setup() params with activity
    icon = ContextCompat.getDrawable(requiredContext(), R.drawable.ic_close))
}.show()
```

!!! info "Optional"
    Icon is optional, If you do not fill in this field, icon will not be displayed in the Snack Bar

!!! warning "*Note"
    You can't set icon and avatar at same time, it will throw an exception and will ***Force Close***

---

### avatarUrl: String*

``` kotlin title="Example.kt"
AgrSnackbar.setup(requireActivity()) { //Fill setup() params with activity
    avatarUrl = "https://google.com/image.png"
}.show()
```

!!! info "Optional"
    AvatarUrl is optional, If you do not fill in this field, Avatar will not be displayed in the Snack Bar

!!! warning "*Note"
    You can't set icon and avatar at same time, it will throw an exception and will ***Force Close***

---

### view: View

``` kotlin title="Example.kt"
AgrSnackbar.setup(requireActivity()) { //Fill setup() params with activity
    view = binding.root
}.show()
```

!!! info "Optional"
    view is optional, If you do not fill in this field, Snack bar will anchor to bottom of content view's

---

### setActionButtonListener(): Unit

``` kotlin title="Example.kt"
AgrSnackbar.setup(requireActivity()) { //Fill setup() params with activity
    setActionButtonListener {
        toast("Hello Agree")
    }
}.show()
```

!!! info "Optional"
    type is optional, If you do not fill in this field, default action button is dismiss Snack Bar

---

## Extension Function

We know that some developers are too lazy to code, so we provide an extension function to simplify and speed up developers in code.

!!! info
    Extension functions can only be used in activities and fragments and only show title on Snack Bar, besides that you must use the ***Builder Pattern***!

### successSnackbar(content: String)

```kotlin title="ExampleFragment.kt"
override fun onUpdateAvatarSuccess(data: Profile) {
    successSnackBar(getStringResource(R.string.label_update_profile_success))
}
```

### errorSnackbar(content: String)

```kotlin title="ExampleFragment.kt"
override fun onUpdateAvatarSuccess(data: Profile) {
    errorSnackbar(getStringResource(R.string.label_update_profile_success))
}
```

### warningSnackbar(content: String)

```kotlin title="ExampleFragment.kt"
override fun onUpdateAvatarSuccess(data: Profile) {
    warningSnackbar(getStringResource(R.string.label_update_profile_success))
}
```

### normalSnackbar(content: String)

```kotlin title="ExampleFragment.kt"
override fun onUpdateAvatarSuccess(data: Profile) {
    normalSnackbar(getStringResource(R.string.label_update_profile_success))
}
```
