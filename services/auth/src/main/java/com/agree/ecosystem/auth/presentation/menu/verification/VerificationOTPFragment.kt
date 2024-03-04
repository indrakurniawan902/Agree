package com.agree.ecosystem.auth.presentation.menu.verification

import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.agree.ecosystem.auth.R
import com.agree.ecosystem.auth.databinding.FragmentVerificationOtpBinding
import com.agree.ecosystem.auth.presentation.navigation.AuthNavigation
import com.agree.ecosystem.core.utils.base.AgrFragment
import com.agree.ecosystem.core.utils.utility.navigation.navigation
import com.devbase.utils.ext.disable
import com.devbase.utils.ext.enable
import com.devbase.utils.util.getStringResource
import com.oratakashi.viewbinding.core.tools.gone
import com.oratakashi.viewbinding.core.tools.onTextChanged
import com.oratakashi.viewbinding.core.tools.visible

class VerificationOTPFragment : AgrFragment<FragmentVerificationOtpBinding>() {

    override fun initUI() {
        super.initUI()
        countDown()

        with(binding) {
            tvStep.text = args.step

            etCode1.onTextChanged {
                if (etCode1.text.toString().length == 1) {
                    etCode2.requestFocus()
                }
            }
            etCode2.onTextChanged {
                if (etCode2.text.toString().length == 1) {
                    etCode3.requestFocus()
                }
            }
            etCode3.onTextChanged {
                if (etCode3.text.toString().length == 1) {
                    etCode4.requestFocus()
                }
            }
            etCode4.onTextChanged {
                buttonEnable = etCode4.text.toString().length == 1
                if (etCode4.text.toString().length == 1) {
                    if (args.step == getStringResource(R.string.label_third_step))
                        authNav.fromVerificationOTPToLogin()
                    else
                        authNav.fromVerificationOTPToCreateNewPassword()
                }
            }
            etCode2.setOnKeyListener(deleteListener)
            etCode3.setOnKeyListener(deleteListener)
            etCode4.setOnKeyListener(deleteListener)
            tvTitle.text = getString(R.string.label_verify_with_phone_number, args.phoneNumber)
        }
    }

    override fun initAction() {
        super.initAction()
        with(binding) {
            toolbar.setBackButtonOnClick { requireActivity().onBackPressed() }

            tvChangePhone.setOnClickListener {
                Toast.makeText(context, "Under Development", Toast.LENGTH_SHORT).show()
            }

            btnResend.setOnClickListener {
                if (!buttonEnable) {
                    tvErrorCode.text = getStringResource(R.string.label_code_not_empty)
                    tvErrorCode.visible()
                    return@setOnClickListener
                }
                tvErrorCode.gone()
                countDown()
            }
        }
    }

    private val deleteListener: View.OnKeyListener by lazy {
        with(binding) {
            android.view.View.OnKeyListener { view, keyCode, event ->
                val isDeleteClicked =
                    event.action == android.view.KeyEvent.ACTION_DOWN && keyCode == android.view.KeyEvent.KEYCODE_DEL
                when (view) {
                    etCode6 -> {
                        if (isDeleteClicked && etCode6.text.toString().isBlank()) {
                            etCode5.requestFocus()
                        }
                    }
                    etCode5 -> {
                        if (isDeleteClicked && etCode5.text.toString().isBlank()) {
                            etCode4.requestFocus()
                        }
                    }
                    etCode4 -> {
                        if (isDeleteClicked && etCode4.text.toString().isBlank()) {
                            etCode3.requestFocus()
                        }
                    }
                    etCode3 -> {
                        if (isDeleteClicked && etCode3.text.toString().isBlank()) {
                            etCode2.requestFocus()
                        }
                    }
                    etCode2 -> {
                        if (isDeleteClicked && etCode2.text.toString().isBlank()) {
                            etCode1.requestFocus()
                        }
                    }
                }
                return@OnKeyListener false
            }
        }
    }

    private fun countDown() = object : CountDownTimer(60000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            val timer = "00:${millisUntilFinished / 1000}"
            with(binding) {
                tvSendTimer.text = context?.getString(R.string.label_send_code_otp, timer)
                btnResend.disable()
            }
        }

        override fun onFinish() {
            with(binding) {
                tvSendTimer.text = getStringResource(R.string.label_not_received_code)
                btnResend.enable()
            }
        }
    }.start()

    override fun onPause() {
        countDown().cancel()
        super.onPause()
    }

    private val args: VerificationOTPFragmentArgs by navArgs()
    private val authNav: AuthNavigation by navigation { activity }
    private var buttonEnable = false
}
