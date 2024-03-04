package com.agree.ecosystem.common.presentation.menu.notifications

import androidx.recyclerview.widget.RecyclerView
import com.agree.ecosystem.common.data.reqres.model.notification.UpdateStateInboxPost
import com.agree.ecosystem.common.databinding.FragmentNotificationsBinding
import com.agree.ecosystem.common.domain.reqres.model.notification.Inbox
import com.agree.ecosystem.common.presentation.navigation.MenuNavigation
import com.agree.ecosystem.core.utils.base.AgrFragment
import com.agree.ecosystem.core.utils.domain.reqres.AgrPrefUsecase
import com.agree.ecosystem.core.utils.utility.eventbus.AppEvent
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.core.utils.utility.navigation.navigation
import com.devbase.presentation.recyclerview.endless.OnLoadMoreListener
import com.telkom.legion.component.viewstate.showDefaultLayout
import com.telkom.legion.component.viewstate.showEmptyLayout
import com.telkom.legion.component.viewstate.showLoadingLayout
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotificationsFragment :
    AgrFragment<FragmentNotificationsBinding>(),
    NotificationDataContracts,
    OnLoadMoreListener {

    private val prefs: AgrPrefUsecase by inject()

    private val adapter: NotificationAdapter by lazy {
        NotificationAdapter { data ->
            if (data?.isRead == false) {
                viewModel.updateInbox(
                    id = data.id,
                    body = UpdateStateInboxPost("read", true)
                )
                broadcastEvent { AppEvent.FETCH_NOTIFICATION_BADGE }
            }
            data.let {
                if (data != null) {
                    menuNav.fromNotificationListToDetailNotification(data)
                }
            }
        }
    }

    override fun initUI() {
        super.initUI()
        with(binding) {
            rvListNotification.adapter = adapter
        }
    }

    override fun initObserver() {
        super.initObserver()
        addObservers(
            NotificationObserver(this, viewModel = viewModel)
        )
        fetchNotificationLists(prefs.userId)
    }

    override fun fetchNotificationLists(userId: String?) {
        viewModel.fetchListNotifications(userId)
    }

    override fun notificationListsOnLoading() {
        binding.msvListNotification.showLoadingLayout()
    }

    override fun notificationListsOnSuccess(data: List<Inbox>) {
        with(binding) {
            msvListNotification.showDefaultLayout()
            adapter.apply {
                setLoadMoreListener(this@NotificationsFragment)
                setEndlessScroll(rvListNotification)
                resetEndlessScroll()
                adapter.clear()
                adapter.addOrUpdateAll(data)
            }
        }
    }

    override fun emptyNotificationLists() {
        with(binding) {
            msvListNotification.showEmptyLayout()
        }
    }

    override fun fetchLoadMoreNotificationLists(userId: String?) {
        viewModel.fetchLoadMoreListsNotifications(userId)
    }

    override fun loadMoreNotificationListsOnLoading() {
        if (adapter.itemCount > 0) adapter.showLoadMoreLoading()
    }

    override fun loadMoreNotificationListsOnSuccess(data: List<Inbox>) {
        if (adapter.itemCount > 0) adapter.hideLoadMoreLoading()
        adapter.addOrUpdateAll(data)
        isLoading = false
    }

    override fun loadMoreNotificationListsOnFailed() {
        if (adapter.itemCount > 0) adapter.showLoadMoreError()
    }

    override fun emptyLoadMoreNotificationLists() {
        adapter.finishLoadMore()
    }

    override fun getNotificationBadge(userId: String?) {
        viewModel.fetchSizeUnreadNotification(userId)
    }

    override fun onLoadMore(skip: Int?, limit: Int?, totalItemsCount: Int?, view: RecyclerView?) {
        fetchLoadMoreNotificationLists(prefs.userId)
    }

    override fun onLoadMoreRetryButtonClicked(skip: Int?, limit: Int?) {
        adapter.hideLoadMoreLoading()
        fetchLoadMoreNotificationLists(prefs.userId)
    }

    private var isLoading: Boolean = false
    private val menuNav: MenuNavigation by navigation { activity }
    private val viewModel: NotificationViewModel by viewModel()
}
