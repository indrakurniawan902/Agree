package com.agree.ecosystem.core.utils.utility

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.agree.ecosystem.core.utils.R
import com.agree.ecosystem.core.utils.databinding.LayoutCustomTimePickerDialogBinding
import com.agree.ecosystem.core.utils.databinding.LayoutDialogModalBinding
import com.agree.ecosystem.core.utils.databinding.LayoutFormDialogModalBinding
import com.devbase.utils.ext.invisible
import com.devbase.utils.ext.isNotNull
import com.devbase.utils.ext.isNull
import com.oratakashi.viewbinding.core.tools.onClick
import com.oratakashi.viewbinding.core.tools.visible

object DialogUtils {
    private lateinit var builder: AlertDialog.Builder
    private lateinit var dialog: AlertDialog

    fun showCustomDialog(
        context: Context,
        title: String,
        message: String,
        messageSpan: SpannableStringBuilder? = null,
        positiveAction: Pair<String, (() -> Unit)?>,
        negativeAction: Pair<String, (() -> Unit)?>? = null,
        autoDismiss: Boolean = false
    ) {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.layout_dialog_modal, null as ViewGroup?, false)
        val binding = LayoutDialogModalBinding.bind(view)
        with(binding) {
            tvTitle.text = title
            tvTitle.visibility = if (title.isEmpty()) {
                View.GONE
            } else {
                View.VISIBLE
            }
            tvMessage.text = if (messageSpan.isNotNull()) messageSpan else message
            btnPositive.let {
                it.text = positiveAction.first
                it.setOnClickListener {
                    dialog.dismiss()
                    positiveAction.second?.invoke()
                }
            }
            if (negativeAction.isNull()) {
                btnNegative.invisible()
            }
            negativeAction?.let { pair ->
                btnNegative.let {
                    it.visible()
                    it.text = pair.first
                    it.setOnClickListener {
                        dialog.dismiss()
                        pair.second?.invoke()
                    }
                }
            }

            builder = AlertDialog.Builder(context)
            builder.setView(view)
            builder.setCancelable(autoDismiss)
            dialog = builder.create()
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
        }
    }

    fun showCustomDialogWithDrawable(
        context: Context,
        title: String,
        message: String,
        messageSpan: SpannableStringBuilder? = null,
        positiveAction: Pair<String, (() -> Unit)?>,
        negativeAction: Pair<String, (() -> Unit)?>? = null,
        autoDismiss: Boolean = false
    ) {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.layout_dialog_modal, null as ViewGroup?, false)
        val binding = LayoutDialogModalBinding.bind(view)
        with(binding) {
            tvTitle.text = title
            ivAgreeFarmer.visible()
            tvTitle.visibility = if (title.isEmpty()) {
                View.GONE
            } else {
                View.VISIBLE
            }
            tvMessage.text = if (messageSpan.isNotNull()) messageSpan else message
            btnPositive.let {
                it.text = positiveAction.first
                it.setOnClickListener {
                    dialog.dismiss()
                    positiveAction.second?.invoke()
                }
            }
            if (negativeAction.isNull()) {
                btnNegative.invisible()
            }
            negativeAction?.let { pair ->
                btnNegative.let {
                    it.visible()
                    it.text = pair.first
                    it.setOnClickListener {
                        dialog.dismiss()
                        pair.second?.invoke()
                    }
                }
            }

            builder = AlertDialog.Builder(context)
            builder.setView(view)
            builder.setCancelable(autoDismiss)
            dialog = builder.create()
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
        }
    }

    fun showCustomFormDialog(
        context: Context,
        title: String,
        message: String,
        messageSpan: SpannableStringBuilder? = null,
        text: String? = null,
        placeholder: String,
        positiveAction: Pair<String, ((text: String) -> Unit)?>,
        negativeAction: Pair<String, (() -> Unit)?>? = null,
        autoDismiss: Boolean = false
    ) {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.layout_form_dialog_modal, null as ViewGroup?, false)
        val binding = LayoutFormDialogModalBinding.bind(view)
        with(binding) {
            tvTitle.text = title
            tvTitle.isVisible = title.isNotEmpty()
            tvMessage.text = if (messageSpan.isNotNull()) messageSpan else message
            if (!text.isNullOrEmpty()) etField.text = text
            etField.placeHolder = placeholder
            etField.isGone = placeholder.isEmpty()
            btnPositive.let {
                it.text = positiveAction.first
                it.setOnClickListener {
                    dialog.dismiss()
                    positiveAction.second?.invoke(etField.text)
                }
            }
            if (negativeAction.isNull()) {
                btnNegative.invisible()
            }
            negativeAction?.let { pair ->
                btnNegative.let {
                    it.visible()
                    it.text = pair.first
                    it.onClick {
                        dialog.dismiss()
                        pair.second?.invoke()
                    }
                }
            }

            builder = AlertDialog.Builder(context)
            builder.setView(view)
            builder.setCancelable(autoDismiss)
            dialog = builder.create()
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
        }
    }

    fun showCustomTimePickerDialog(
        context: Context,
        date: String,
        data: List<String>,
        positiveAction: ((time: String) -> Unit)? = null,
        negativeAction: (() -> Unit)? = null,
        autoDismiss: Boolean = false
    ) {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_custom_time_picker_dialog, null as ViewGroup?, false)
        val binding = LayoutCustomTimePickerDialogBinding.bind(view)
        with(binding) {
            tvDate.text = date
            cgTime.addAll(data)
            btnApply.setOnClickListener {
                dialog.dismiss()
                positiveAction?.invoke(cgTime.text)
            }
            btnBack.setOnClickListener {
                dialog.dismiss()
                negativeAction?.invoke()
            }

            builder = AlertDialog.Builder(context)
            builder.setView(view)
            builder.setCancelable(autoDismiss)
            dialog = builder.create()
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
        }
    }
}
