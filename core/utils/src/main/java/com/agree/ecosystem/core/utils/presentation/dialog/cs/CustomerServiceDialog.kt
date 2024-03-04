package com.agree.ecosystem.core.utils.presentation.dialog.cs

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.agree.ecosystem.core.utils.databinding.LayoutCustomerServiceDialogBinding
import com.agree.ecosystem.core.utils.domain.reqres.AgrPrefUsecase
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.oratakashi.viewbinding.core.binding.bottomsheet.viewBinding
import org.koin.android.ext.android.inject

class CustomerServiceDialog(
    private val callback: (() -> Unit?)? = null
) : BottomSheetDialogFragment() {
    private val binding: LayoutCustomerServiceDialogBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            btnNegative.setOnClickListener {
                dismiss()
            }

            btnPositive.setOnClickListener {
                sendMessage()
                callback?.invoke()
                dismiss()
            }
        }
    }

    fun sendMessage() {
        val phone =
            if (prefs.contactInfo == "") "6281280006756"
            else prefs.contactInfo.replace("08", "628")
        val url = "https://api.whatsapp.com/send?phone=$phone"

        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    private val prefs: AgrPrefUsecase by inject()
}
