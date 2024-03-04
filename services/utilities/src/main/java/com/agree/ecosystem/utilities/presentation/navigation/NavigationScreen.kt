package com.agree.ecosystem.utilities.presentation.navigation

sealed class NavigationScreen {
    object About : NavigationScreen() {
        val name: String = this::class.java.simpleName
    }

    object Help : NavigationScreen() {
        val name: String = this::class.java.simpleName
    }

    object Tnc : NavigationScreen() {
        val name: String = this::class.java.simpleName
    }

    object Parser {
        fun parse(screen: String?): NavigationScreen? {
            return when (screen) {
                About::class.java.simpleName -> About
                Help::class.java.simpleName -> Help
                Tnc::class.java.simpleName -> Tnc
                else -> null
            }
        }
    }
}
