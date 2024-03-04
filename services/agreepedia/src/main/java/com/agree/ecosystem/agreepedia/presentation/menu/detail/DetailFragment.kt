package com.agree.ecosystem.agreepedia.presentation.menu.detail

import android.content.Intent
import android.net.Uri
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.agree.ecosystem.agreepedia.R
import com.agree.ecosystem.agreepedia.databinding.FragmentDetailBinding
import com.agree.ecosystem.agreepedia.domain.model.article.Article
import com.agree.ecosystem.agreepedia.presentation.navigation.MainNavigation
import com.agree.ecosystem.core.utils.base.AgrFragment
import com.agree.ecosystem.core.utils.utility.ConverterDate
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.core.utils.utility.navigation.navigation
import com.devbase.utils.ext.toDate
import com.devbase.utils.ext.toString
import com.telkom.legion.component.viewstate.showDefaultLayout
import com.telkom.legion.component.viewstate.showEmptyLayout
import com.telkom.legion.component.viewstate.showLoadingLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment :
    AgrFragment<FragmentDetailBinding>(),
    DetailDataContract {

    override fun initAction() {
        super.initAction()
        with(binding) {
            toolbar.setBackButtonOnClick { requireActivity().onBackPressed() }
        }
    }

    override fun initData() {
        super.initData()
        if (args.agreepedia != null) {
            onLoadData(args.agreepedia!!)
        } else if (args.oId != 0L) {
            fetchAgreepediaDetail(args.oId)
        }
    }

    override fun initObserver() {
        super.initObserver()
        addObservers(DetailObserver(this, viewModel))
    }

    private fun getIdArticle(url: String): Long {
        val index = if (url.last() == '/') 2 else 1
        val segment = url.split('/')
        return segment[segment.size - index].toLongOrNull() ?: 0L
    }

    override fun onResume() {
        super.onResume()
        binding.msvContent.showDefaultLayout()
    }

    private fun onWebClickable(uri: Uri?) {
        if (uri?.host == "agreeculture.id") {
            val url = uri.toString()
            if (url.contains("agreepedia/artikel")) {
                val oId = getIdArticle(url)
                if (oId != 0L) {
                    menuNav.fromDetailOpenLink(oId)
                } else openUri(uri)
            } else openUri(uri)
        } else openUri(uri)
    }

    private fun openUri(uri: Uri?) {
        val i = Intent(Intent.ACTION_VIEW).apply {
            data = uri
        }
        startActivity(i)
    }

    override fun fetchAgreepediaDetail(oId: Long) {
        viewModel.getAgreepediaDetail(oId)
    }

    override fun onGetAgreepediaDetailLoading() {
        binding.msvContent.showLoadingLayout()
    }

    override fun onGetAgreepediaDetailEmpty() {
        binding.msvContent.showEmptyLayout()
    }

    override fun onGetAgreepediaDetailFailed(e: Throwable?) {
        binding.msvContent.showEmptyLayout()
    }

    override fun onGetAgreepediaDetailSuccess(data: Article) {
        onLoadData(data)
    }

    private fun onLoadData(article: Article) {
        with(binding) {
            msvContent.showDefaultLayout()
            cgTags.addAll(article.tag.map { it.tagValue })
            val css = "<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\" />"
            val htmlString = css.plus(article.blogValue)
            tvTitleArticle.text = article.blogName
            tvSubTitleArticle.text = article.blogSubName
            tvAuthor.text = getString(R.string.label_agreepedia_by, article.createdBy)
            tvDate.text =
                article.beginDate.toDate(ConverterDate.SQL_DATE.formatter)?.toString(
                    ConverterDate.SIMPLE_DATE.formatter
                ).orEmpty()
            ivThumbnail.url = article.blogImage

            with(webViewContent) {
                val webClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?
                    ): Boolean {
                        onWebClickable(request?.url)
                        return true
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        msvWebView.showDefaultLayout()
                        binding.nestedScrollView.scrollTo(0, 0)
                    }
                }
                scrollBarStyle = View.SCROLLBARS_OUTSIDE_OVERLAY
                webViewClient = webClient
                loadDataWithBaseURL(
                    "file:///android_asset/",
                    htmlString,
                    "text/html",
                    "UTF-8",
                    null
                )
            }
        }
    }

    private val args: DetailFragmentArgs by navArgs()
    private val viewModel: DetailViewModel by viewModel()
    private val menuNav: MainNavigation by navigation { activity }
}
