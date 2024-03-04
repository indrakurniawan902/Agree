# Color Configuration

Colors system leverages a purposeful set of color styles as the perfect starting point for any brand or project. When it comes to color, contrast is critical for ensuring text is legible.

---

## Color Primary

The primary color is your "***brand***" color, and is used across all interactive elements such as buttons, links, inputs, etc. This color can define the overall feel and can elicit emotion, color primary variant token :

* colorPrimary_pressed ( ***colorPrimary70*** )

* colorPrimary_hover ( ***colorPrimary60*** )

* colorPrimary ( ***colorPrimary50*** )

* colorPrimary40

* colorPrimary30

* colorPrimary20

* colorPrimary10

```xml title="themes.xml"
<style name="YourTheme" parent="Legion.Light">
    ...
    <item name="colorPrimary_pressed">@color/aqf_pressed</item>
    <item name="colorPrimary_hover">@color/aqf_hover</item>
    <item name="colorPrimary">@color/aqf_normal</item>
    <item name="colorPrimary40">@color/aqf_40</item>
    <item name="colorPrimary30">@color/aqf_30</item>
    <item name="colorPrimary20">@color/aqf_20</item>
    <item name="colorPrimary10">@color/aqf_10</item>
    ...
</style>
```

## Color Secondary

The secondary colors is color highlight or complement the primary color. These are to be used sparingly to make the UI elements stand out. These colors are also usually defined in the brand guidelines, color secondary variant token :

* colorSecondary_pressed ( ***colorSecondary70*** )

* colorSecondary_hover ( ***colorSecondary60*** )

* colorSecondary ( ***colorSecondary50*** )

* colorSecondary40

* colorSecondary30

* colorSecondary20

* colorSecondary10

```xml title="themes.xml"
<style name="YourTheme" parent="Legion.Light">
    ...
    <item name="colorSecondary_pressed">@color/poultry_70</item>
    <item name="colorSecondary_hover">@color/poultry_60</item>
    <item name="colorSecondary">@color/poultry_50</item>
    <item name="colorSecondary40">@color/poultry_40</item>
    <item name="colorSecondary30">@color/poultry_30</item>
    <item name="colorSecondary20">@color/poultry_20</item>
    <item name="colorSecondary10">@color/poultry_10</item>
    ...
</style>
```

## Color Tertiary

The tertiary color is a neutral color and is the foundation of the color system. Almost everything in UI design — text, form fields, backgrounds, dividers, outline card — are usually gray. color tertiary variant token :

* black_100

* black_90

* black_80

* black_70

* black_60

* black_50

* black_40

* black_30

* black_20

* black_10

* white ( ***default : black_10*** )

* black_font ( ***default : black_40*** )

```xml title="themes.xml"
<style name="YourTheme" parent="Legion.Light">
    ...
    <item name="black_100">@color/black</item>
    <item name="black_90">@color/black_90</item>
    <item name="black_80">@color/black_font</item>
    <item name="black_70">@color/black_70</item>
    <item name="black_60">@color/black_60</item>
    <item name="black_50">@color/black_50</item>
    <item name="black_40">@color/black_disable</item>
    <item name="black_30">@color/black_30</item>
    <item name="black_20">@color/black_20</item>
    <item name="black_10">@color/white</item>
    <item name="white">@color/white</item>
    <item name="black_font">@color/black_font</item>
    ...
</style>
```

## Color Accent

### Error

Error colors are used across error states and in "destructive" actions. They communicate a destructive/negative action, such as removing a user from your team, color error variant token :

* error_pressed ( ***error_70*** )

* error_hover ( ***error_60*** )

* error_normal ( ***error_50*** )

* error_40

* error_30

* error_20

* error_10

```xml title="themes.xml"
<style name="YourTheme" parent="Legion.Light">
    ...
    <item name="error_pressed">@color/error_pressed</item>
    <item name="error_hover">@color/error_hover</item>
    <item name="error_normal">@color/error_normal</item>
    <item name="error_40">@color/error_40</item>
    <item name="error_30">@color/error_30</item>
    <item name="error_20">@color/error_20</item>
    <item name="error_10">@color/error_10</item>
    ...
</style>
```

### Warning

Warning colors can communicate that an action is potentially destructive or "on-hold". These colors are commonly used in confirmations to grab the users' attention, color warning variant token :

* warning_pressed ( ***warning_70*** )

* warning_hover ( ***warning_60*** )

* warning_normal ( ***warning_50*** )

* warning_40

* warning_30

* warning_20

* warning_10

```xml title="themes.xml"
<style name="YourTheme" parent="Legion.Light">
    ...
    <item name="warning_pressed">@color/warning_pressed</item>
    <item name="warning_hover">@color/warning_hover</item>
    <item name="warning_normal">@color/warning_normal</item>
    <item name="warning_40">@color/warning_40</item>
    <item name="warning_30">@color/warning_30</item>
    <item name="warning_20">@color/warning_20</item>
    <item name="warning_10">@color/warning_10</item>
    ...
</style>
```

### Success

Success colors communicate a positive action, postive trend, or a successful confirmation. If you're using green as your primary color, it can be helpful to introduce a different hue for your success green, color success variant token :

* success_pressed ( ***success_70*** )

* success_hover ( ***success_60*** )

* success_normal ( ***success_50*** )

* success_40

* success_30

* success_20

* success_10

```xml title="themes.xml"
<style name="YourTheme" parent="Legion.Light">
    ...
    <item name="success_pressed">@color/success_pressed</item>
    <item name="success_hover">@color/success_hover</item>
    <item name="success_normal">@color/success_normal</item>
    <item name="success_40">@color/success_40</item>
    <item name="success_30">@color/success_30</item>
    <item name="success_20">@color/success_20</item>
    <item name="success_10">@color/success_10</item>
    ...
</style>
```

### Info

Information colors hehe, color info variant token :

* info_pressed ( ***info_70*** )

* info_hover ( ***info_60*** )

* info_normal ( ***info_50*** )

* info_40

* info_30

* info_20

* info_10

```xml title="themes.xml"
<style name="YourTheme" parent="Legion.Light">
    ...
    <item name="info_pressed">@color/info_pressed</item>
    <item name="info_hover">@color/info_hover</item>
    <item name="info_normal">@color/info_normal</item>
    <item name="info_40">@color/info_40</item>
    <item name="info_30">@color/info_30</item>
    <item name="info_20">@color/info_20</item>
    <item name="info_10">@color/info_10</item>
    ...
</style>
```
