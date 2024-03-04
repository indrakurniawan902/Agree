# Architecture

Agree Ecosystem uses the basic "Clean Architecture" architecture and uses MVVM
( Model, View, ViewModel ) and is built with a modular concept, and uses the dynamic feature module from Android Jetpack

## Ilustration

![architecture graph](/assets/images/architecture.png)

!!! danger "Base Module"
    in this module contains the main application code of Agree Ecosystem, starting from the splash screen, auth, home and others

!!! info "Library Module"
    This module will be automatically installed when the user first downloads the application

!!! warning "Dynamic Feature Module"
    This module will be automatically downloaded when the user uses it

## Implementation

![architecture implementation](/assets/images/arch_implementation.png)
