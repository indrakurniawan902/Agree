package com.agree.ecosystem.agreepedia.presentation.menu.home

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.devbase.data.source.VmData
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AgreepediaObserver(
    private val view: AgreepediaDataContract,
    private val viewModel: AgreepediaViewModel
) : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        owner.lifecycleScope.launch {
            viewModel.filterSector.collectLatest { view.onFilterStateChanged(it) }
        }
        viewModel.articles.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onGetAgreepediaArticlesLoading()
                is VmData.Success -> {
                    view.onGetAgreepediaArticlesSuccess(it.data)
                }
                is VmData.Failure -> view.onGetAgreepediaArticlesFailed(it.throwable)
                is VmData.Empty -> view.onGetAgreepediaArticlesEmpty()
            }
        }
        viewModel.loadMoreArticles.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onLoadMoreLoading()
                is VmData.Success -> {
                    view.onLoadMoreSuccess(it.data)
                }
                is VmData.Failure -> view.onLoadMoreFailed()
                is VmData.Empty -> view.onLoadMoreEmpty()
            }
        }
    }
}
