# Resource Naming

For resource naming use snake_case

|Resource| Type | Format |
| ------ | ------ | ------ |
|Anim| Animation | anim_purpose |
|Color| Selector Color | selector_purpose |
|| Gradient Color | gradient_purpose |
|Drawable| Icon Drawable | ic_purpose |
|| Image Drawable | img_purpose |
|| Background Drawable | bg_purpose |
|| Selector Drawable | selector_purpose |
|| Other Drawable | custom_purpose |
|Layout| Activity Layout | activity_purpose |
|| Fragment Layout | fragment_purpose |
|| Adapter Item Layout | item_purpose |
|| Bottom Sheet Layout | bottom_sheet_purpose |
|| Dialog Layout | dialog_purpose |
|| Other Layout | layout_purpose |
|Menu| Menu | menu_purpose |
|Navigation| Navigation | nav_purpose |

## Colors Naming

for colors naming use camelCase with color as prefix and for color with transparent/opacity with decimal value

- For Regular Color : `colorDustyOrange`

- For Transparent Color : `colorBlackTransparent12`

Example

``` xml title="colors.xml"
    <color name="black_100">#212121</color>
    <color name="black_90">#424242</color>
    <color name="black_80">#616161</color>
    <color name="black_70">#757575</color>
    <color name="black_60">#9E9E9E</color>
    <color name="black_50">#BDBDBD</color>
    <color name="black_40">#E0E0E0</color>
    <color name="black_30">#EEEEEE</color>
    <color name="black_20">#F5F5F5</color>
    <color name="black_10">#FAFAFA</color>
```

## Dimen Naming

For margin/padding/view height and width dimen naming use snake_case with dimen as prefix

Example

``` xml title="dimens.xml"
    <dimen name="dimen_12dp">12dp</dimen>
```

For text size dimen naming use text_size as prefix

Example

``` xml title="dimens.xml"
    <dimen name="dimen_112sp">112sp</dimen>
```

## Strings

- Label Text (usually used in tv) : label_

- Button or Action Text : action_

- Activity/Fragment Title : title_

- Menu Item Text : menu_

- Hint Text : hint_

- Title Dialog Text : title_dialog

- Error Message : error_

- Sample Text (use tools:text) : sample_

Example

``` xml title="strings.xml"
    <string name="foo">Lorem Ipsum</string>
    <string name="label_open_with">Buka dengan</string>
    <string name="label_gallery">Gallery</string>
    <string name="label_camera">Camera</string>
    <string name="action_cancel">Batal</string>
    <string name="action_reupload">Unggah Ulang</string>
    <string name="hint_image_upload_size">Ukuran maksimal 2MB dengan format JPG, JPEG, atau PNG</string>
    <string name="action_upload">Unggah Foto Profil</string>
    <string name="label_self_photo">Foto Diri</string>
    <string name="label_optional">(opsional)</string>
    <string name="hint_search">Cari disini</string>
    <string name="placeholder_title_dialog">Pilih %1$s</string>
```

## Styles Naming

For Button styles and text styles put it in different XML files :

- styles_text.xml

- styles_button.xml

- styles.xml

For styles naming use PascalCase and inherit styles with ordering BaseStyle.Variant : `BaseTextInputLayout.Password`

Example

``` xml title="style_textinput.xml"
    // inherit style
    <style name="BaseTextInputLayout.Password">
        <item name="errorIconDrawable">@null</item>
        <item name="endIconMode">password_toggle</item>
    </style>
```
