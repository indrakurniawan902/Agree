package com.agree.ecosystem.core.utils.widget.finance

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.agree.ecosystem.core.utils.databinding.LayoutStepperBinding

class Stepper(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val binding = LayoutStepperBinding.inflate(LayoutInflater.from(context), this, true)
    fun setStepper(stepList: List<String>, progress: Int) {
        val adapter = StepperAdapter(progress) {}.apply { addAll(stepList) }
        adapter.setProgress(progress)
        binding.apply {
            this.rvStepper.adapter = adapter
            this.rvStepper.scrollToPosition(progress)
        }
    }
}
