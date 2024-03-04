package com.agree.ecosystem.auth.presentation.menu.forgotaccount

import android.text.method.LinkMovementMethod
import com.agree.ecosystem.auth.R
import com.agree.ecosystem.auth.databinding.FragmentForgotAccountBinding
import com.agree.ecosystem.auth.presentation.navigation.AuthNavigation
import com.agree.ecosystem.core.utils.presentation.dialog.cs.CustomerServiceDialog
import com.agree.ecosystem.core.utils.utility.navigation.navigation
import com.agree.ecosystem.core.utils.utility.singleLinkSpan
import com.devbase.presentation.viewbinding.DevFormFragment
import com.oratakashi.viewbinding.core.tools.gone

class ForgotAccountFragment : DevFormFragment<FragmentForgotAccountBinding>() {

    override fun initUI() {
        super.initUI()
        with(binding) {
            toolbarForgotAccount.navController = authNav.getNavController()
            tvErrorCode.gone()
            tvCallCs.text = getString(R.string.label_call_cs).singleLinkSpan(requireContext()) {
                CustomerServiceDialog().showNow(childFragmentManager, "dialog")
            }
            tvCallCs.movementMethod = LinkMovementMethod.getInstance()
        }
    }

    override fun initAction() {
        super.initAction()
        with(binding) {
            btnRecoveryPhoneNumber.setOnClickListener {
                authNav.fromForgotAccountToInputPhone()
            }
            btnRecoveryEmail.setOnClickListener {
                authNav.fromForgotAccountToInputEmail()
            }
        }
    }

    override fun onNotValidated() {
        // Do Nothing
    }

    override fun onValidated() {
        // Do Nothing
    }

    private val authNav: AuthNavigation by navigation { activity }
}
