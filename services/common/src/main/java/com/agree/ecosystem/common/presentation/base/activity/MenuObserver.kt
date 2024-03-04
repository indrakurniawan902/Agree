package com.agree.ecosystem.common.presentation.base.activity

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.agree.ecosystem.common.presentation.menu.notifications.NotificationViewModel
import com.oratakashi.viewbinding.core.tools.State
import kotlinx.coroutines.flow.collectLatest

class MenuObserver(
    private val view: MenuDataContract,
    private val viewModel: NotificationViewModel
) : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        owner.lifecycleScope.launchWhenStarted {
            viewModel.unreadCount.collectLatest {
                when (it) {
                    is State.Default -> Unit
                    is State.Loading -> Unit
                    is State.Success -> {
                        view.onNotificationBadgeSuccess(it.data)
                    }
                    is State.Failure -> Unit
                    is State.Empty -> Unit
                }
            }
        }
    }
}
