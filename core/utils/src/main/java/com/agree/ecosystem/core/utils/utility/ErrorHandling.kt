package com.agree.ecosystem.core.utils.utility

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.agree.ecosystem.core.utils.R
import com.agree.ecosystem.core.utils.presentation.dialog.connectionerror.ConnectionErrorDialogFragment
import com.agree.ecosystem.core.utils.presentation.dialog.genericerror.GenericErrorDialogFragment
import com.agree.ecosystem.core.utils.presentation.dialog.sessionsexpired.SessionsExpiredDialogFragment
import com.agree.ecosystem.core.utils.utility.eventbus.AppEvent
import com.agree.ecosystem.core.utils.utility.eventbus.EventBus
import com.agree.ui.snackbar.errorSnackBar
import com.agree.ui.snackbar.normalSnackBar
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

/**
 * Default Error Handling without show anything on production
 */
fun Fragment.defaultErrorHandling(httpCode: Int, message: String, retry: () -> Unit) {
    val fragmentName: String = this::class.qualifiedName.orEmpty()
    when (httpCode) {
        401 -> {
            if (!fragmentName.contains("auth")) {
                SessionsExpiredDialogFragment {
                    val eventBus = inject<EventBus>().value
                    lifecycleScope.launch { eventBus.invokeEvent(AppEvent.FORCE_LOGOUT) }
                }.showNow(childFragmentManager, "dialog")
            }
        }
        999 -> {
            ConnectionErrorDialogFragment(retry).showNow(childFragmentManager, "dialog")
        }
        500 -> normalSnackBar(getString(R.string.label_internal_server_error))
        else -> {
            if (AppConfig.isDebug) {
                errorSnackBar(message)
            }
        }
    }
}

/**
 * Show Generic Error Bottom Sheet when error detected
 */
fun Fragment.genericDialogErrorHandling(httpCode: Int, message: String, retry: () -> Unit) {
    val fragmentName: String = this::class.qualifiedName.orEmpty()
    when (httpCode) {
        401 -> {
            if (!fragmentName.contains("auth")) {
                SessionsExpiredDialogFragment {
                    val eventBus = inject<EventBus>().value
                    lifecycleScope.launch { eventBus.invokeEvent(AppEvent.FORCE_LOGOUT) }
                }.showNow(childFragmentManager, "dialog")
            }
        }
        999 -> {
            ConnectionErrorDialogFragment(retry).showNow(childFragmentManager, "dialog")
        }
        500 -> normalSnackBar(getString(R.string.label_internal_server_error))
        else -> {
            if (AppConfig.isDebug) {
                errorSnackBar(message)
            }
            GenericErrorDialogFragment(retry).showNow(childFragmentManager, "dialog")
        }
    }
}
