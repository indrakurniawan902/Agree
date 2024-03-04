package com.agree.ecosystem.common.presentation.menu.notifications

import androidx.lifecycle.viewModelScope
import com.agree.ecosystem.common.data.reqres.model.notification.NotificationParams
import com.agree.ecosystem.common.data.reqres.model.notification.UpdateStateInboxPost
import com.agree.ecosystem.common.domain.reqres.NotificationUseCase
import com.agree.ecosystem.common.domain.reqres.model.notification.Inbox
import com.agree.ecosystem.common.domain.reqres.model.notification.UnreadInbox
import com.agree.ecosystem.common.domain.reqres.model.notification.UpdateInbox
import com.agree.ecosystem.core.utils.utility.extension.immutable
import com.agree.ecosystem.core.utils.utility.extension.setHandler
import com.devbase.data.source.DevData
import com.devbase.data.utilities.DevViewModel
import com.devbase.data.utilities.rx.transformers.flowableScheduler
import com.oratakashi.viewbinding.core.tools.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class NotificationViewModel(
    private val useCase: NotificationUseCase
) : DevViewModel() {
    var page = 1

    private val _userId = DevData<String>().apply { default() }
    val userId get() = _userId

    private val _lists = DevData<List<Inbox>>().apply { default() }
    val lists get() = _lists.immutable()

    private val _loadMoreLists = DevData<List<Inbox>>().apply { default() }
    val loadMoreLists get() = _loadMoreLists.immutable()

    private val _updateInbox = DevData<UpdateInbox>().apply { default() }
    val updateInbox get() = _updateInbox.immutable()

    private val _unreadCount = MutableStateFlow<State<UnreadInbox>>(State.default())
    val unreadCount get() = _unreadCount

    fun fetchListNotifications(userId: String?) {
        page = 1
        useCase.fetchListNotifications(NotificationParams(10, 1, userId.orEmpty()))
            .setHandler(_lists)
            .let { return@let disposable::add }
    }

    fun fetchLoadMoreListsNotifications(userId: String?) {
        useCase.fetchListNotifications(NotificationParams(10, page, userId.orEmpty()))
            .setHandler(_loadMoreLists)
            .let { return@let disposable::add }
    }

    fun updateInbox(id: String, body: UpdateStateInboxPost) {
        useCase.updateInbox(id, body)
            .setHandler(_updateInbox)
            .let { return@let disposable::add }
    }

    fun fetchSizeUnreadNotification(userId: String?) {
        useCase.fetchSizeListNotifications(userId.orEmpty())
            .compose(flowableScheduler())
            .cache()
            .onBackpressureBuffer()
            .doOnSubscribe {
                it.request(Long.MAX_VALUE)
            }
            .subscribe({
                viewModelScope.launch { _unreadCount.emit(State.success(it)) }
            }, {
                viewModelScope.launch { _unreadCount.emit(State.fail(it, it.message)) }
            }).let { return@let disposable::add }
    }
}
