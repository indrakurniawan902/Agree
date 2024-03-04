package com.agree.ui.snackbar

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.snackbar.Snackbar
import com.telkom.legion.extension.snackbar.LgnSnackBar

fun FragmentActivity.normalSnackBar(message: String) {
    LgnSnackBar.setup(this) {
        title = message
        duration = Snackbar.LENGTH_LONG
        type = LgnSnackBar.ToastType.NORMAL
    }.show()
}

fun FragmentActivity.errorSnackBar(message: String) {
    LgnSnackBar.setup(this) {
        title = message
        duration = Snackbar.LENGTH_LONG
        type = LgnSnackBar.ToastType.ERROR
    }.show()
}

fun FragmentActivity.successSnackBar(message: String) {
    LgnSnackBar.setup(this) {
        title = message
        type = LgnSnackBar.ToastType.SUCCESS
        duration = Snackbar.LENGTH_LONG
    }.show()
}

fun FragmentActivity.warningSnackBar(message: String) {
    LgnSnackBar.setup(this) {
        title = message
        duration = Snackbar.LENGTH_LONG
        type = LgnSnackBar.ToastType.WARNING
    }.show()
}

fun Fragment.successSnackBar(message: String) {
    requireActivity().successSnackBar(message)
}

fun Fragment.warningSnackBar(message: String) {
    requireActivity().warningSnackBar(message)
}

fun Fragment.normalSnackBar(message: String) {
    requireActivity().normalSnackBar(message)
}

fun Fragment.errorSnackBar(message: String) {
    requireActivity().errorSnackBar(message)
}
