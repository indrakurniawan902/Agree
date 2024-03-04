# Theme Configuration

After implementing the gradle config, the next step is to configure the theme on android, and again the Agree Ecosystem project does not need to do this configuration

## Extend your existing theme with Legion theme

=== "Light Theme"
    ```xml title="themes.xml"
    <style name="YourTheme" parent="Legion.Light">
        ...
    </style>
    ```

=== "Dark Theme (WIP)"
    ```xml title="themes.xml"
    <style name="YourTheme" parent="Legion.Dark">
        ...
    </style>
    ```

!!! info
    Currently the dark theme is not yet available, so extending with the **Legion.Dark** theme will result in a Light Mode like extending the **Legion.Light** theme

## Override Colors set into your own colors

The majority of UI Kit components use primary and secondary colors, you can override color set colorPrimary and colorSecondary to change all colors in the UI Kit component

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
