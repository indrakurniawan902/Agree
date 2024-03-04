package com.agree.ecosystem.monitoring.presentation.menu.cultivationactivities.dateinput

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.monitoring.databinding.LayoutYearBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.oratakashi.viewbinding.core.binding.fragment.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
class SelectYearBottomSheet(
    private val callback: (Int) -> Unit
) : BottomSheetDialogFragment(), SelectYearDataContract {
    private val binding: LayoutYearBottomSheetBinding by viewBinding()
    private val viewModel: SelectYearViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.rvItemYearDialog.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
        initAction()
    }
    private fun initObserver() {
        addObservers(SelectYearObserver(this, viewModel))
        viewModel.getListYear()
    }
    private fun initAction() {
        binding.ivClose.setOnClickListener { dismiss() }
    }

    override fun getListYear(data: List<Int>) {
        adapter.addOrUpdateAll(data)
    }

    private val adapter = SelectYearAdapter(onItemClicked = {
        callback.invoke(it)
        dismiss()
    })

    companion object {
        const val TAG = "PickYearBottomSheet"
    }
}
