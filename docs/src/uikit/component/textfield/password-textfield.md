# AgrPasswordField

AgrPasswordField is a text input that can only be used to enter passwords, AgrPasswordField already includes ui/ux for errors, hints, placeholders and more

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
|Input Type|`android:inputType`|To set input type on text field directly via xml|
|Enable Status|`android:enabled`|To set enable or disable text field directly via xml|
|Required Status|`app:isRequired`|To set required status and automaticly add **`*`** on hint label directly via xml|
|Optional Status|`app:isOptional`|To set optional status and automaticly add **`(Opsional)`** on hint label directly via xml|

!!! warning "Notes"
    You can't set **isRequired** true and **isOptional** true at same time
