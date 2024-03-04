package com.agree.ecosystem.core.utils.presentation.dialog.sessionsexpired

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.agree.ecosystem.core.utils.databinding.FragmentSessionsExpiredDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.oratakashi.viewbinding.core.binding.bottomsheet.viewBinding

class SessionsExpiredDialogFragment(
    private val callback: () -> Unit
) : BottomSheetDialogFragment() {
    private val binding: FragmentSessionsExpiredDialogBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            dialog?.setCanceledOnTouchOutside(false)
            btnLogin.setOnClickListener {
                dismiss()
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        callback.invoke()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }
}
