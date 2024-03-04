# Typography Configuration

Typography is defined as the science of style, appearance, and structure of fonts that aim to provide convenience and aesthetics to the reader. Effective typography depends not only on the content but also on the presentation of the typography itself, starting from the size, width, color, and font structure.

---

## Heading

Control the font size of an element using the `heading{size}` token. area uses :

* Title

* SubTitle

### Heading 1

Create your typography styles :

```xml title="typography.xml"
<style name="YourHeading1" parent="TextAppearance.MaterialComponents.Headline1">
    <item name="fontFamily">@font/montserrat_bold</item>
    <item name="android:fontFamily">@font/montserrat_bold</item>
    <item name="android:textSize">@dimen/dimen_34sp</item>
    <item name="lineHeight">@dimen/dimen_48dp</item>
    <item name="android:textStyle">normal</item>
</style>
```

Register on your themes :

```xml title="themes.xml"
<style name="YourTheme" parent="Legion.Light">
    ...
    <item name="heading1">@style/YourHeading1</item>
    ...
</style>
```

### Heading 2

Create your typography styles :

```xml title="typography.xml"
<style name="YourHeading2" parent="TextAppearance.MaterialComponents.Headline2">
    <item name="fontFamily">@font/montserrat_bold</item>
    <item name="android:fontFamily">@font/montserrat_bold</item>
    <item name="android:textSize">@dimen/dimen_28sp</item>
    <item name="lineHeight">@dimen/dimen_42dp</item>
    <item name="android:textStyle">normal</item>
</style>
```

Register on your themes :

```xml title="themes.xml"
<style name="YourTheme" parent="Legion.Light">
    ...
    <item name="heading2">@style/YourHeading2</item>
    ...
</style>
```

### Heading 3

Create your typography styles :

```xml title="typography.xml"
<style name="YourHeading3" parent="TextAppearance.MaterialComponents.Headline3">
    <item name="fontFamily">@font/montserrat_bold</item>
    <item name="android:fontFamily">@font/montserrat_bold</item>
    <item name="android:textSize">@dimen/dimen_24sp</item>
    <item name="lineHeight">@dimen/dimen_36dp</item>
    <item name="android:textStyle">normal</item>
</style>
```

Register on your themes :

```xml title="themes.xml"
<style name="YourTheme" parent="Legion.Light">
    ...
    <item name="heading3">@style/YourHeading3</item>
    ...
</style>
```

### Heading 4

Create your typography styles :

```xml title="typography.xml"
<style name="YourHeading4" parent="TextAppearance.MaterialComponents.Headline4">
    <item name="fontFamily">@font/montserrat_bold</item>
    <item name="android:fontFamily">@font/montserrat_bold</item>
    <item name="android:textSize">@dimen/dimen_22sp</item>
    <item name="lineHeight">@dimen/dimen_32dp</item>
    <item name="android:textStyle">normal</item>
</style>
```

Register on your themes :

```xml title="themes.xml"
<style name="YourTheme" parent="Legion.Light">
    ...
    <item name="heading4">@style/YourHeading4</item>
    ...
</style>
```

### Heading 5

Create your typography styles :

```xml title="typography.xml"
<style name="YourHeading5" parent="TextAppearance.MaterialComponents.Headline5">
    <item name="fontFamily">@font/montserrat_bold</item>
    <item name="android:fontFamily">@font/montserrat_bold</item>
    <item name="android:textSize">@dimen/dimen_20sp</item>
    <item name="lineHeight">@dimen/dimen_28dp</item>
    <item name="android:textStyle">normal</item>
</style>
```

Register on your themes :

```xml title="themes.xml"
<style name="YourTheme" parent="Legion.Light">
    ...
    <item name="heading5">@style/YourHeading5</item>
    ...
</style>
```

### Heading 6

Create your typography styles :

```xml title="typography.xml"
<style name="YourHeading6" parent="TextAppearance.MaterialComponents.Headline6">
    <item name="fontFamily">@font/montserrat_bold</item>
    <item name="android:fontFamily">@font/montserrat_bold</item>
    <item name="android:textSize">@dimen/dimen_18sp</item>
    <item name="lineHeight">@dimen/dimen_26dp</item>
    <item name="android:textStyle">normal</item>
</style>
```

Register on your themes :

```xml title="themes.xml"
<style name="YourTheme" parent="Legion.Light">
    ...
    <item name="heading6">@style/YourHeading6</item>
    ...
</style>
```

---

## Body Large

Control the font size of an element using the bodyLarge{Variant} token. area uses :

* Button

* Text Field

* Tabs

### Body Large Regular

Create your typography styles :

```xml title="typography.xml"
<style name="BodyLargeRegular" parent="TextAppearance.MaterialComponents.Body1">
    <item name="fontFamily">@font/montserrat_regular</item>
    <item name="android:fontFamily">@font/montserrat_regular</item>
    <item name="android:textSize">@dimen/dimen_18sp</item>
    <item name="lineHeight">@dimen/dimen_24dp</item>
    <item name="android:textStyle">normal</item>
</style>
```

Register on your themes :

```xml title="themes.xml"
<style name="YourTheme" parent="Legion.Light">
    ...
    <item name="bodyLargeRegular">@style/BodyLargeRegular</item>
    ...
</style>
```

### Body Large Medium

Create your typography styles :

```xml title="typography.xml"
<style name="BodyLargeMedium" parent="TextAppearance.MaterialComponents.Body1">
    <item name="fontFamily">@font/montserrat_medium</item>
    <item name="android:fontFamily">@font/montserrat_medium</item>
    <item name="android:textSize">@dimen/dimen_18sp</item>
    <item name="lineHeight">@dimen/dimen_24dp</item>
    <item name="android:textStyle">normal</item>
</style>
```

Register on your themes :

```xml title="themes.xml"
<style name="YourTheme" parent="Legion.Light">
    ...
    <item name="bodyLargeMedium">@style/BodyLargeMedium</item>
    ...
</style>
```

### Body Large SemiBold

Create your typography styles :

```xml title="typography.xml"
<style name="BodyLargeSemiBold" parent="TextAppearance.MaterialComponents.Body1">
    <item name="fontFamily">@font/montserrat_semibold</item>
    <item name="android:fontFamily">@font/montserrat_semibold</item>
    <item name="android:textSize">@dimen/dimen_18sp</item>
    <item name="lineHeight">@dimen/dimen_24dp</item>
    <item name="android:textStyle">normal</item>
</style>
```

Register on your themes :

```xml title="themes.xml"
<style name="YourTheme" parent="Legion.Light">
    ...
    <item name="bodyLargeSemiBold">@style/BodyLargeSemiBold</item>
    ...
</style>
```

### Body Large Bold

Create your typography styles :

```xml title="typography.xml"
<style name="BodyLargeBold" parent="TextAppearance.MaterialComponents.Body1">
    <item name="fontFamily">@font/montserrat_bold</item>
    <item name="android:fontFamily">@font/montserrat_bold</item>
    <item name="android:textSize">@dimen/dimen_18sp</item>
    <item name="lineHeight">@dimen/dimen_24dp</item>
    <item name="android:textStyle">normal</item>
</style>
```

Register on your themes :

```xml title="themes.xml"
<style name="YourTheme" parent="Legion.Light">
    ...
    <item name="bodyLargeBold">@style/BodyLargeBold</item>
    ...
</style>
```

### Body Large Italic

Create your typography styles :

```xml title="typography.xml"
<style name="BodyLargeItalic" parent="TextAppearance.MaterialComponents.Body1">
    <item name="fontFamily">@font/montserrat_italic</item>
    <item name="android:fontFamily">@font/montserrat_italic</item>
    <item name="android:textSize">@dimen/dimen_18sp</item>
    <item name="lineHeight">@dimen/dimen_24dp</item>
    <item name="android:textStyle">normal</item>
</style>
```

Register on your themes :

```xml title="themes.xml"
<style name="YourTheme" parent="Legion.Light">
    ...
    <item name="bodyLargeItalic">@style/BodyLargeItalic</item>
    ...
</style>
```

---

## Body Small

Control the font size of an element using the bodySmall{Variant} token. area uses :

* Body

* PlaceHolder

* TextLink

### Body Small Regular

Create your typography styles :

```xml title="typography.xml"
<style name="BodySmallRegular" parent="TextAppearance.MaterialComponents.Body2">
    <item name="fontFamily">@font/opensans_regular</item>
    <item name="android:fontFamily">@font/opensans_regular</item>
    <item name="android:textSize">@dimen/dimen_14sp</item>
    <item name="lineHeight">@dimen/dimen_18dp</item>
    <item name="android:textStyle">normal</item>
</style>
```

Register on your themes :

```xml title="themes.xml"
<style name="YourTheme" parent="Legion.Light">
    ...
    <item name="bodySmallRegular">@style/BodySmallRegular</item>
    ...
</style>
```

### Body Small Medium

Create your typography styles :

```xml title="typography.xml"
<style name="BodySmallMedium" parent="TextAppearance.MaterialComponents.Body2">
    <item name="fontFamily">@font/opensans_medium</item>
    <item name="android:fontFamily">@font/opensans_medium</item>
    <item name="android:textSize">@dimen/dimen_14sp</item>
    <item name="lineHeight">@dimen/dimen_18dp</item>
    <item name="android:textStyle">normal</item>
</style>
```

Register on your themes :

```xml title="themes.xml"
<style name="YourTheme" parent="Legion.Light">
    ...
    <item name="bodySmallMedium">@style/BodySmallMedium</item>
    ...
</style>
```

### Body Small SemiBold

Create your typography styles :

```xml title="typography.xml"
<style name="BodySmallSemiBold" parent="TextAppearance.MaterialComponents.Body2">
    <item name="fontFamily">@font/opensans_semibold</item>
    <item name="android:fontFamily">@font/opensans_semibold</item>
    <item name="android:textSize">@dimen/dimen_14sp</item>
    <item name="lineHeight">@dimen/dimen_18dp</item>
    <item name="android:textStyle">normal</item>
</style>
```

Register on your themes :

```xml title="themes.xml"
<style name="YourTheme" parent="Legion.Light">
    ...
    <item name="bodySmallSemiBold">@style/BodySmallSemiBold</item>
    ...
</style>
```

### Body Small Bold

Create your typography styles :

```xml title="typography.xml"
<style name="BodySmallBold" parent="TextAppearance.MaterialComponents.Body2">
    <item name="fontFamily">@font/opensans_bold</item>
    <item name="android:fontFamily">@font/opensans_bold</item>
    <item name="android:textSize">@dimen/dimen_14sp</item>
    <item name="lineHeight">@dimen/dimen_18dp</item>
    <item name="android:textStyle">normal</item>
</style>
```

Register on your themes :

```xml title="themes.xml"
<style name="YourTheme" parent="Legion.Light">
    ...
    <item name="bodySmallBold">@style/BodySmallBold</item>
    ...
</style>
```

### Body Small Italic

Create your typography styles :

```xml title="typography.xml"
<style name="BodySmallItalic" parent="TextAppearance.MaterialComponents.Body2">
    <item name="fontFamily">@font/opensans_italic</item>
    <item name="android:fontFamily">@font/opensans_italic</item>
    <item name="android:textSize">@dimen/dimen_14sp</item>
    <item name="lineHeight">@dimen/dimen_18dp</item>
    <item name="android:textStyle">normal</item>
</style>
```

Register on your themes :

```xml title="themes.xml"
<style name="YourTheme" parent="Legion.Light">
    ...
    <item name="bodySmallItalic">@style/BodySmallItalic</item>
    ...
</style>
```

---

## Caption Large

Control the font size of an element using the captionLarge{Variant} token. area uses :

* Tags

* Chips

* Filter

### Caption Large Regular

Create your typography styles :

```xml title="typography.xml"
<style name="CaptionLargeRegular" parent="TextAppearance.MaterialComponents.Caption">
    <item name="fontFamily">@font/opensans_regular</item>
    <item name="android:fontFamily">@font/opensans_regular</item>
    <item name="android:textSize">@dimen/dimen_12sp</item>
    <item name="lineHeight">@dimen/dimen_18dp</item>
    <item name="android:textStyle">normal</item>
</style>
```

Register on your themes :

```xml title="themes.xml"
<style name="YourTheme" parent="Legion.Light">
    ...
    <item name="captionLargeRegular">@style/CaptionLargeRegular</item>
    ...
</style>
```

### Caption Large SemiBold

Create your typography styles :

```xml title="typography.xml"
<style name="CaptionLargeSemiBold" parent="TextAppearance.MaterialComponents.Caption">
    <item name="fontFamily">@font/opensans_semibold</item>
    <item name="android:fontFamily">@font/opensans_semibold</item>
    <item name="android:textSize">@dimen/dimen_12sp</item>
    <item name="lineHeight">@dimen/dimen_18dp</item>
    <item name="android:textStyle">normal</item>
</style>
```

Register on your themes :

```xml title="themes.xml"
<style name="YourTheme" parent="Legion.Light">
    ...
    <item name="captionLargeSemiBold">@style/CaptionLargeSemiBold</item>
    ...
</style>
```

### Caption Large Bold

Create your typography styles :

```xml title="typography.xml"
<style name="CaptionLargeBold" parent="TextAppearance.MaterialComponents.Caption">
    <item name="fontFamily">@font/opensans_bold</item>
    <item name="android:fontFamily">@font/opensans_bold</item>
    <item name="android:textSize">@dimen/dimen_12sp</item>
    <item name="lineHeight">@dimen/dimen_18dp</item>
    <item name="android:textStyle">normal</item>
</style>
```

Register on your themes :

```xml title="themes.xml"
<style name="YourTheme" parent="Legion.Light">
    ...
    <item name="captionLargeBold">@style/CaptionLargeBold</item>
    ...
</style>
```

### Caption Large Italic

Create your typography styles :

```xml title="typography.xml"
<style name="CaptionLargeItalic" parent="TextAppearance.MaterialComponents.Caption">
    <item name="fontFamily">@font/opensans_italic</item>
    <item name="android:fontFamily">@font/opensans_italic</item>
    <item name="android:textSize">@dimen/dimen_12sp</item>
    <item name="lineHeight">@dimen/dimen_18dp</item>
    <item name="android:textStyle">normal</item>
</style>
```

Register on your themes :

```xml title="themes.xml"
<style name="YourTheme" parent="Legion.Light">
    ...
    <item name="captionLargeItalic">@style/CaptionLargeItalic</item>
    ...
</style>
```

---

## Caption Small

Control the font size of an element using the captionSmall{Variant} token. area uses :

* Overview

### Caption Small Regular

Create your typography styles :

```xml title="typography.xml"
<style name="CaptionSmallRegular" parent="TextAppearance.MaterialComponents.Caption">
    <item name="fontFamily">@font/montserrat_regular</item>
    <item name="android:fontFamily">@font/montserrat_regular</item>
    <item name="android:textSize">@dimen/dimen_10sp</item>
    <item name="lineHeight">@dimen/dimen_18dp</item>
    <item name="android:textStyle">normal</item>
</style>
```

Register on your themes :

```xml title="themes.xml"
<style name="YourTheme" parent="Legion.Light">
    ...
    <item name="captionSmallRegular">@style/CaptionSmallRegular</item>
    ...
</style>
```

### Caption Small SemiBold

Create your typography styles :

```xml title="typography.xml"
<style name="CaptionSmallSemiBold" parent="TextAppearance.MaterialComponents.Caption">
    <item name="fontFamily">@font/montserrat_semibold</item>
    <item name="android:fontFamily">@font/montserrat_semibold</item>
    <item name="android:textSize">@dimen/dimen_10sp</item>
    <item name="lineHeight">@dimen/dimen_18dp</item>
    <item name="android:textStyle">normal</item>
</style>
```

Register on your themes :

```xml title="themes.xml"
<style name="YourTheme" parent="Legion.Light">
    ...
    <item name="captionSmallSemiBold">@style/CaptionSmallSemiBold</item>
    ...
</style>
```

### Caption Small Bold

Create your typography styles :

```xml title="typography.xml"
<style name="CaptionSmallBold" parent="TextAppearance.MaterialComponents.Caption">
    <item name="fontFamily">@font/montserrat_bold</item>
    <item name="android:fontFamily">@font/montserrat_bold</item>
    <item name="android:textSize">@dimen/dimen_10sp</item>
    <item name="lineHeight">@dimen/dimen_18dp</item>
    <item name="android:textStyle">normal</item>
</style>
```

Register on your themes :

```xml title="themes.xml"
<style name="YourTheme" parent="Legion.Light">
    ...
    <item name="captionSmallBold">@style/CaptionSmallBold</item>
    ...
</style>
```

### Caption Small Italic

Create your typography styles :

```xml title="typography.xml"
<style name="CaptionSmallItalic" parent="TextAppearance.MaterialComponents.Caption">
    <item name="fontFamily">@font/montserrat_italic</item>
    <item name="android:fontFamily">@font/montserrat_italic</item>
    <item name="android:textSize">@dimen/dimen_10sp</item>
    <item name="lineHeight">@dimen/dimen_18dp</item>
    <item name="android:textStyle">normal</item>
</style>
```

Register on your themes :

```xml title="themes.xml"
<style name="YourTheme" parent="Legion.Light">
    ...
    <item name="captionSmallItalic">@style/CaptionSmallItalic</item>
    ...
</style>
```
