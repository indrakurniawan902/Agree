package com.agree.locales

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.agree.locales.presentation.provider.AppLocaleProvider
import com.agree.locales.presentation.activitycallback.LocaleActivityCallback
import com.agree.locales.presentation.transformer.ButtonViewTransformer
import com.agree.locales.presentation.transformer.SingleFieldTransformer
import com.agree.locales.presentation.transformer.TextFieldTransformer
import com.agree.locales.presentation.transformer.ToolbarTransformer
import com.blankj.utilcode.util.ActivityUtils
import dev.b3nedikt.restring.Restring
import dev.b3nedikt.reword.Reword
import dev.b3nedikt.reword.RewordInterceptor
import dev.b3nedikt.viewpump.ViewPump
import timber.log.Timber
import java.util.*

object LanguageHelper {
    /**
     * Change Current Language
     */
    fun injectLanguage(tag: String) {
        Locale.setDefault(Locale.forLanguageTag(tag))
    }

    fun initLocaleSupport(context: Context) {
        ActivityUtils.addActivityLifecycleCallbacks(LocaleActivityCallback())
        Restring.init(context)
        Restring.localeProvider = AppLocaleProvider
        ViewPump.init(RewordInterceptor)

        /**
         * Legion Locale Support
         */
        Reword.addViewTransformer(
            ButtonViewTransformer,
            SingleFieldTransformer,
            TextFieldTransformer,
            ToolbarTransformer
        )
    }
}
