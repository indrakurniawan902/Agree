# Form Validation

The **Agree Ecosystem** codebase includes a form validation feature, it is hoped that developers can easily perform form validation and reduce boilerplate and repetitive actions, No more nested ifs that are like mountains, and no more nested whens that make it hard to sleep, just with ***6 action*** and you can sleep well

---

## Guideline

- Extend Fragment with **`AgrFormFragment`**

    **Example:**

    ```kotlin
    class LoginFragment : AgrFormFragment<FragmentLoginBinding>() { ... }
    ```

    By extending to AgrFormFragment, you can use all form validation functions

- Register View Form State on `initForm()` method

    **Example:**

    ```kotlin
    registerFormState(
        etUsername.initState(),
        etPassword.initState()
    )
    ```

    By registering a view to form state, you can take advantage of the form state feature in the Agree Ecosystem codebase, and you can also activate the **`view.state`** feature to get the latest state ui conditions.

- Add rules in your view on the `initForm()` method

    **Example:**

    ```kotlin
    etUsername.addRule(
        etUsername.state,
        notEmptyRule(
            getString(
                R.string.error_empty_field,
                getString(R.string.label_username)
            )
        ),
        LoginRule(
            usernameRule(getString(R.string.error_username_rule)),
            mobileNumberOnlyRule(getString(R.string.error_rule_phone))
        )
    )
    etPassword.addRule(
        etPassword.state,
        minLengthRule(8, getString(R.string.error_rule_password_8chars)),
        notEmptyRule(
            getString(
                R.string.error_empty_field,
                getString(R.string.label_password)
            )
        )
    )
    ```

    by adding rules, form validation will automatically run using a text listener if the view is edit text, and using a check listener if the view is a checkbox or radio button

- Handle `onAllFormValidated()` state

    **Example:**

    ```kotlin
    override fun onAllFormValidated() {
        with(binding) {
            btnLogin.isEnable = true
        }
    }
    ```

    Form Validation will automatically pass to the `onAllFormValidated()` method contained in the fragment if all forms are valid

- Handle `onFormUnvalidated()` state

    **Example:**

    ```kotlin
    override fun onFormUnvalidated(view: View, errorMessage: String) {
        with(binding) {
            (view as AgrTextField).error = errorMessage
            btnLogin.isEnable = true
        }
    }
    ```

    Form validation will automatically pass to the `onFormUnvalidated()` method if there are invalid views, and will pass parameters in the form of invalid views and invalid error message rules.

- Handle `onFormValidated()` state

    **Example:**

    ```kotlin
    override fun onFormValidated(view: View) {
        (view as AgrTextField).error = ""
    }
    ```

    Form validation will automatically pass to the `onFormValidated()` method if the view that has an error has been fixed with invalid view parameters

## How it work

!!! info
    If the graph doesn't appear after decrypting the page, please try reloading this page again

``` mermaid
sequenceDiagram
    actor Users
    participant Fragment
    participant AgrFormFragment
    participant ValidationViewModel

    Users ->>+ Fragment: Open Some Page


    Fragment->>+AgrFormFragment: registerFormState()
    AgrFormFragment->>+ValidationViewModel: registerValidation()
    ValidationViewModel-->>-AgrFormFragment: State observed in viewmodel
    AgrFormFragment-->>-Fragment: Instance state from views

    Fragment->>+AgrFormFragment: addRule()
    AgrFormFragment-->>-Fragment: Instance regex rules from views

    Fragment -->>- Users: Show Views of page

    Users ->>+ Fragment: Input Some Text on Form Field
    Fragment ->>+ AgrFormFragment: Validating Users input with rules
    AgrFormFragment ->>+ ValidationViewModel: Validating input in Current Views with Rules
    ValidationViewModel -->>- AgrFormFragment: Result validation in current views
    AgrFormFragment -->> Fragment: onFormValidated() or onFormUnvalidated()
    Fragment -->> Users: Show Error Message when rules not valid
    AgrFormFragment ->>+ ValidationViewModel: Validating input in all Views
    ValidationViewModel -->>- AgrFormFragment: Result validation in all views
    AgrFormFragment -->>- Fragment: onAllFormValidated()
    Fragment -->>- Users: End of Validation when all form field valid
```
