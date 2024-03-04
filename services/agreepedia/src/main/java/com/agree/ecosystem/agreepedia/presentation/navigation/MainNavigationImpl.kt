package com.agree.ecosystem.agreepedia.presentation.navigation

import android.app.Activity
import androidx.navigation.NavController
import com.agree.ecosystem.agreepedia.R
import com.agree.ecosystem.agreepedia.domain.model.article.Article
import com.agree.ecosystem.agreepedia.presentation.base.activity.AgreepediaActivity
import com.agree.ecosystem.agreepedia.presentation.menu.home.AgreepediaFragmentDirections
import com.oratakashi.viewbinding.core.tools.startActivity

class MainNavigationImpl(
    private val nav: NavController?,
    private val activity: Activity?
) : MainNavigation {
    override fun fromHomeToAgreepediaDetail(article: Article) {
        runCatching {
            nav?.navigate(
                AgreepediaFragmentDirections.actionAgreepediaFragmentToAgreepediaDetailFragment(
                    article
                )
            )
        }.onFailure {
            nav?.navigate(R.id.detailFragment)
        }
    }

    override fun fromDetailOpenLink(oId: Long) {
        runCatching {
            activity?.startActivity(AgreepediaActivity::class.java) {
                it.putExtra("oId", oId)
            }
        }.onFailure {
            it.printStackTrace()
        }
    }
}
