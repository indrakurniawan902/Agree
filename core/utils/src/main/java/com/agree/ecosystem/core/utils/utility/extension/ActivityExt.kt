package com.agree.ecosystem.core.utils.utility.extension

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.IdRes
import androidx.annotation.NavigationRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.ColorUtils
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavGraph
import androidx.navigation.fragment.findNavController

// @Deprecated(
//    message = "Change into successSnackBar(message: String) in com.agree.ui",
//    level = DeprecationLevel.ERROR,
//    replaceWith = ReplaceWith("successSnackBar(content)", "com.agree.ui.snackbar.successSnackBar")
// )

fun FragmentActivity.initGraph(
    @IdRes hostId: Int,
    @NavigationRes navId: Int,
    bundle: Bundle? = null,
    block: ((NavGraph) -> Unit)? = null
) {
//    val navController = Navigation.findNavController(this, hostId)
    val navController = supportFragmentManager.findFragmentById(hostId)?.findNavController()
    val graph = navController?.navInflater?.inflate(navId)
    if (graph != null) {
        block?.invoke(graph)
        navController.setGraph(graph, bundle)
    }
}

inline fun <reified T : AppCompatActivity> Fragment.getActivityAs(): T? {
    return if (activity is T) activity as T else null
}

inline fun <reified T : Fragment> Fragment.getParentAs(): T? {
    return if (parentFragment is T) parentFragment as T else null
}

fun Int.getAttrsValue(context: Context): Int {
    val value = TypedValue()
    context.theme.resolveAttribute(this, value, true)
    return value.data
}

fun Context.getAttrsValue(@AttrRes attr: Int): Int {
    val value = TypedValue()
    theme.resolveAttribute(attr, value, true)
    return value.data
}

fun Activity.setStatusBarColor(@ColorInt color: Int) {
    val window = window
    val decorView: View = window.decorView
    val wic = WindowInsetsControllerCompat(window, decorView)
    wic.isAppearanceLightStatusBars = ColorUtils.calculateLuminance(color) > 0.5
    window.statusBarColor = color
}
