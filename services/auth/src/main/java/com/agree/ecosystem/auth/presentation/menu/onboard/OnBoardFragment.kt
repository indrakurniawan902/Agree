package com.agree.ecosystem.auth.presentation.menu.onboard

import android.os.Handler
import android.os.Looper
import androidx.viewpager2.widget.ViewPager2
import com.agree.ecosystem.auth.R
import com.agree.ecosystem.auth.databinding.FragmentOnBoardBinding
import com.agree.ecosystem.auth.domain.reqres.model.onboard.OnBoard
import com.agree.ecosystem.auth.presentation.navigation.AuthNavigation
import com.agree.ecosystem.core.utils.base.AgrFragment
import com.agree.ecosystem.core.utils.utility.navigation.navigation
import com.devbase.utils.util.getStringResource

class OnBoardFragment : AgrFragment<FragmentOnBoardBinding>() {

    private val sliderHandler: Handler by lazy {
        Handler(Looper.getMainLooper())
    }

    override fun initUI() {
        super.initUI()
        with(binding) {
            vpOnboard.apply {
                adapter = onBoardAdapter.apply {
                    clear()
                    addAll(listOnBoards)
                }
                clipToPadding = false
                clipChildren = false
                offscreenPageLimit = 3
                getChildAt(0).overScrollMode =
                    androidx.recyclerview.widget.RecyclerView.OVER_SCROLL_NEVER
                registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        doAutoSlide()
                    }
                })
            }
            dotsIndicator.attachTo(vpOnboard)
        }
    }

    override fun initAction() {
        super.initAction()
        with(binding) {
            btnLogin.setOnClickListener {
                nav.fromOnboardToLogin()
            }
            btnRegister.setOnClickListener {
                nav.fromOnboardToRegister()
            }
        }
    }

    private fun doAutoSlide() {
        sliderHandler.apply {
            removeCallbacks(sliderRunnable)
            postDelayed(sliderRunnable, 3000)
        }
    }

    private val sliderRunnable = Runnable {
        with(binding.vpOnboard) {
            val count = adapter?.itemCount ?: 0
            currentItem = (currentItem + 1) % count
        }
    }

    override fun onPause() {
        super.onPause()
        sliderHandler.removeCallbacks(sliderRunnable)
    }

    override fun onResume() {
        super.onResume()
        doAutoSlide()
    }

    private val nav: AuthNavigation by navigation { activity }
    private val onBoardAdapter: OnBoardAdapter by lazy { OnBoardAdapter() }
    private val listOnBoards: List<OnBoard> by lazy {
        listOf(
            OnBoard(
                getStringResource(R.string.label_onboard_headline1),
                getStringResource(R.string.label_onboard_headline_desc_1),
                R.drawable.ic_onboard_01
            ),
            OnBoard(
                getStringResource(R.string.label_onboard_headline2),
                getStringResource(R.string.label_onboard_headline_desc_2),
                R.drawable.ic_onboard_02
            ),
            OnBoard(
                getStringResource(R.string.label_onboard_headline3),
                getStringResource(R.string.label_onboard_headline_desc_3),
                R.drawable.ic_onboard_03
            )
        )
    }
}
