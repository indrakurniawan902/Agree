package com.agree.ecosystem.splash.presentation.menu.splash

import androidx.constraintlayout.motion.widget.MotionLayout
import com.agree.ecosystem.core.utils.base.AgrFragment
import com.agree.ecosystem.core.utils.utility.navigation.navigation
import com.agree.ecosystem.splash.R
import com.agree.ecosystem.splash.databinding.FragmentSplashBinding
import com.agree.ecosystem.splash.presentation.navigation.SplashNavigation
import com.devbase.utils.util.logInfo
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : AgrFragment<FragmentSplashBinding>() {
    override fun initAction() {
        super.initAction()
        with(binding) {
            root.setTransitionListener(object : MotionLayout.TransitionListener {
                override fun onTransitionStarted(
                    motionLayout: MotionLayout?,
                    startId: Int,
                    endId: Int
                ) {
                    // Do Nothing
                }

                override fun onTransitionChange(
                    motionLayout: MotionLayout?,
                    startId: Int,
                    endId: Int,
                    progress: Float
                ) {
                    // Do Nothing
                }

                override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                    motionLayout?.let {
                        if (currentId == R.id.end && it.progress == 1f) {
                            MainScope().launch {
                                delay(400)
                                root.setTransition(R.id.animClosing)
                                root.transitionToEnd()
                            }
                        } else if (currentId == R.id.finish && it.progress == 1f) {
                            nav.toUpdater()
                        } else {
                            logInfo { it.progress.toString() }
                        }
                    }
                }

                override fun onTransitionTrigger(
                    motionLayout: MotionLayout?,
                    triggerId: Int,
                    positive: Boolean,
                    progress: Float
                ) {
                    // Do Nothing
                }
            })
        }
    }

    private val nav by navigation<SplashNavigation> { activity }
}
