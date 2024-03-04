package com.agree.ecosystem.finances.presentation.menu.finance.cultivator.data.profilefarmer.dynamic

import androidx.navigation.fragment.navArgs
import com.agree.ecosystem.core.utils.base.AgrFragment
import com.agree.ecosystem.core.utils.domain.reqres.AgrPrefUsecase
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.finances.data.reqres.model.DynamicFormBaseItem
import com.agree.ecosystem.finances.data.reqres.model.SingleField
import com.agree.ecosystem.finances.databinding.FragmentDynamicformInfoCultivatorBinding
import com.agree.ecosystem.finances.domain.reqres.model.profilefarmer.FormFieldSchema
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class DynamicformInfoCultivatorFragment : AgrFragment<FragmentDynamicformInfoCultivatorBinding>(),
    DynamicformInfoCultivatorDataContract {

    private val args: DynamicformInfoCultivatorFragmentArgs by navArgs()
    private val pref: AgrPrefUsecase by inject()
    private val viewModel: DynamicFormInfoCultivatorViewModel by viewModel()

    private val adapter: DynamicFormInfoCultivatorAdapter by lazy {
        DynamicFormInfoCultivatorAdapter(requireContext())
    }

    override fun initUI() {
        super.initUI()
        with(binding) {

        }
    }


    override fun initAction() {
        super.initAction()
    }


    override fun initData() {
        super.initData()
    }

    override fun initObserver() {
        super.initObserver()
        addObservers(DynamicFormInfoCultivatorObserver(this, viewModel))
        fetchDynamicDataCultivator(
            args.params.farmerBorrowerId,
            args.params.schemeName
        )


    }

    override fun fetchDynamicDataCultivator(
        borrowerId: String,
        schemeName: String
    ) {
        viewModel.fetchData(borrowerId, schemeName)
    }

    override fun dynamicDataCultivatorOnLoading() {
        Timber.tag("DINAMIKFORM").v("LOADING")
    }

    override fun dynamicDataCultivatorOnSuccess(data: Map<String, FormFieldSchema>) {
        Timber.tag("DINAMIKFORM").v("SUKSES")
        val listForm = mutableListOf<DynamicFormBaseItem>()

        if (data != null) {
            val listFromItem = data.toList()
            listFromItem.forEachIndexed { index, pair ->
                Timber.tag("DINAMIKFORM").v("${index}\n${pair.first}\n${pair.second}\n\n${pair.second.componentType}")
            }
        }

        val itemFormInRv = SingleField(
            fieldTag = "asdas",
            defaultVal = "ddadas",
            errorText = "asdasda",
            hintText = "asdadwqd",
            suffix = "adsasda",
            id = "lalala",
            placeholderText = "212e321e2",
            isRequired = true,
            isEnable = true,
            textWatcher = null,
            inputType = 0, digits = ""
        )


        listForm.add(itemFormInRv)


        adapter.apply {
            adapter.clear()
            adapter.addOrUpdateAll(listForm)
        }
        binding.rvLoanSubmission.adapter = adapter
    }

    override fun dynamicDataCultivatorOnEmpty(data: Map<String, FormFieldSchema>?) {
        Timber.tag("DINAMIKFORM").v("EMPTY")
    }

    override fun dynamicDataCultivatorOnError(e: Throwable?) {
        Timber.tag("DINAMIKFORM").v("ERROR")
    }
}
