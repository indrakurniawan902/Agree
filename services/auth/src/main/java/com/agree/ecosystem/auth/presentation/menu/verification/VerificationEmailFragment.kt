package com.agree.ecosystem.auth.presentation.menu.verification

import android.os.CountDownTimer
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.agree.ecosystem.auth.R
import com.agree.ecosystem.auth.databinding.FragmentVerificationEmailBinding
import com.agree.ecosystem.core.utils.base.AgrFragment
import com.devbase.utils.ext.disable
import com.devbase.utils.ext.enable
import com.devbase.utils.util.getStringResource

class VerificationEmailFragment : AgrFragment<FragmentVerificationEmailBinding>() {

    override fun initUI() {
        super.initUI()
        countDown()

        with(binding) {
            tvStep.text = args.step

            when (args.step) {
                getString(R.string.label_second_step) -> {
                    tvTitle.text = getString(R.string.label_verify_reset_password_with_email, args.email)
                }
                getString(R.string.label_third_step) -> {
                    tvTitle.text = getString(R.string.label_verify_register_with_email, args.email)
                }
            }
        }
    }

    override fun initAction() {
        super.initAction()
        with(binding) {
            toolbar.setBackButtonOnClick { requireActivity().onBackPressed() }

            tvChangeEmail.setOnClickListener {
                Toast.makeText(context, "Under Development", Toast.LENGTH_SHORT).show()
            }

            btnResend.setOnClickListener {
                countDown()
            }
        }
    }

    private fun countDown() = object : CountDownTimer(60000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            val timer = "00:${millisUntilFinished / 1000}"
            with(binding) {
                tvSendTimer.text = context?.getString(R.string.label_send_link, timer)
                btnResend.disable()
            }
        }

        override fun onFinish() {
            with(binding) {
                tvSendTimer.text = getStringResource(R.string.label_not_received_link)
                btnResend.enable()
            }
        }
    }.start()

    override fun onPause() {
        countDown().cancel()
        super.onPause()
    }

    private val args: VerificationEmailFragmentArgs by navArgs()
}
