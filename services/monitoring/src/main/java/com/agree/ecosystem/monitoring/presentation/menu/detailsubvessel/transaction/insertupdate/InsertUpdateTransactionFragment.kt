package com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.transaction.insertupdate

import android.annotation.SuppressLint
import android.view.View
import androidx.navigation.fragment.navArgs
import com.agree.ecosystem.core.utils.base.AgrFormFragment
import com.agree.ecosystem.core.utils.utility.ConverterDate
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.core.utils.utility.validation.addRule
import com.agree.ecosystem.core.utils.utility.validation.validation
import com.agree.ecosystem.monitoring.R
import com.agree.ecosystem.monitoring.data.reqres.model.transaction.insert.ImageArrayBodyPost
import com.agree.ecosystem.monitoring.data.reqres.model.transaction.insert.InsertTransactionBodyPost
import com.agree.ecosystem.monitoring.data.reqres.model.transaction.update.UpdateTransactionBodyPost
import com.agree.ecosystem.monitoring.databinding.FragmentInsertUpdateTransactionBinding
import com.agree.ecosystem.monitoring.domain.reqres.model.detailcompany.DetailCompany
import com.agree.ecosystem.monitoring.domain.reqres.model.transaction.TransactionDetail
import com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.shared.subvessel.SubVesselViewModel
import com.agree.ui.snackbar.errorSnackBar
import com.agree.ui.snackbar.successSnackBar
import com.devbase.presentation.validation.util.notEmptyRule
import com.devbase.utils.ext.toDate
import com.devbase.utils.ext.toString
import com.devbase.utils.util.getStringResource
import com.telkom.legion.component.textfield.base.LgnTextField
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class InsertUpdateTransactionFragment :
    AgrFormFragment<FragmentInsertUpdateTransactionBinding>(),
    InsertUpdateTransactionDataContract {

    private var isClicked: Boolean = false
    private var isInsert: Boolean = true
    private var transactionId: String? = null
    private val listUnit = arrayListOf<String>()

    override fun initUI() {
        super.initUI()

        with(binding) {
            if (!isInsert) {
                btnTransaction.text = getString(R.string.action_save)
                toolbar.text = getString(R.string.title_update_transaction)
                etBruto.unit = listUnit[1]
            } else {
                etBruto.unit = listUnit[0]
            }
        }
    }

    override fun initObserver() {
        super.initObserver()

        addObservers(InsertUpdateTransactionObserver(this, viewModel))
        validation.registerValidations(
            dateValidation,
            brutoValidation,
            productTypeValidation,
            offeringPriceValidation
        )
        sharedVm.observeState()
    }

    override fun initData() {
        super.initData()

        transactionId = args.transactionId
        listUnit.addAll(listOf("Kg", "Ton"))

        if (transactionId != null) {
            isInsert = false
            viewModel.getTransactionDetail(transactionId.toString())
        }

        binding.etBruto.addAll(listUnit.toList())
        viewModel.getDetailCompany(sharedVm.getCompanyId())
    }

    override fun initAction() {
        super.initAction()

        with(binding) {
            btnTransaction.setOnClickListener {
                isClicked = true

                if (isValid()) {
                    extractValueThenInsertOrUpdate()
                }
            }
            toolbar.setBackButtonOnClick { requireActivity().onBackPressed() }
        }
    }

    override fun initForm() {
        with(binding) {
            etDateTransaction.addRule(
                dateValidation,
                notEmptyRule(
                    getString(
                        R.string.error_empty_field,
                        getStringResource(R.string.label_activity_date)
                    )
                )
            )
            etBruto.addRule(
                brutoValidation,
                notEmptyRule(
                    getString(
                        R.string.error_empty_field,
                        getStringResource(R.string.label_transaction_bruto)
                    )
                )
            )
            etProductType.addRule(
                productTypeValidation,
                notEmptyRule(
                    getString(
                        R.string.error_empty_field,
                        getStringResource(R.string.label_product_type)
                    )
                )
            )
            etPriceOffer.addRule(
                offeringPriceValidation,
                notEmptyRule(
                    getString(
                        R.string.error_empty_field,
                        getStringResource(R.string.label_price_offer)
                    )
                )
            )
        }
    }

    override fun isValid(): Boolean {
        var isAllValid = true

        if (!dateValidation.value.isValid) onFormUnvalidated(
            dateValidation.value.view,
            dateValidation.value.errorMessage.ifEmpty {
                getString(R.string.error_empty_field, getString(R.string.label_activity_date))
            }
        )
        isAllValid = dateValidation.value.isValid && isAllValid

        if (!brutoValidation.value.isValid) onFormUnvalidated(
            brutoValidation.value.view,
            brutoValidation.value.errorMessage.ifEmpty {
                getString(R.string.error_empty_field, getString(R.string.label_transaction_bruto))
            }
        )
        isAllValid = brutoValidation.value.isValid && isAllValid

        if (!productTypeValidation.value.isValid) onFormUnvalidated(
            productTypeValidation.value.view,
            productTypeValidation.value.errorMessage.ifEmpty {
                getString(R.string.error_empty_field, getString(R.string.label_product_type))
            }
        )
        isAllValid = productTypeValidation.value.isValid && isAllValid

        if (!offeringPriceValidation.value.isValid) onFormUnvalidated(
            offeringPriceValidation.value.view,
            offeringPriceValidation.value.errorMessage.ifEmpty {
                getString(R.string.error_empty_field, getString(R.string.label_price_offer))
            }
        )
        isAllValid = offeringPriceValidation.value.isValid && isAllValid

        return isAllValid
    }

    override fun onAllFormValidated() {
        with(binding) {
            btnTransaction.isEnable = true
        }
    }

    override fun onFormUnvalidated(view: View, errorMessage: String) {
        if (view is LgnTextField && isClicked) {
            view.error = errorMessage
        }
    }

    override fun onFormValidated(view: View) {
        if (view is LgnTextField) view.error = ""
    }

    private val viewModel: InsertUpdateTransactionViewModel by viewModel()
    private val sharedVm: SubVesselViewModel by sharedViewModel()
    private val args: InsertUpdateTransactionFragmentArgs by navArgs()

    private val dateValidation by validation { etDateTransaction }
    private val brutoValidation by validation { etBruto }
    private val productTypeValidation by validation { etProductType }
    private val offeringPriceValidation by validation { etPriceOffer }

    override fun extractValueThenInsertOrUpdate() {
        if (isInsert) doInsertTransaction()
        else doUpdateTransaction()
    }

    @SuppressLint("SimpleDateFormat")
    private fun getTransactionDate(dateTransaction: String): String {
        val parser = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val selectedDate = parser.parse(dateTransaction) as Date

        return formatter.format(selectedDate)
    }

    private fun getTransactionImages(): ArrayList<MultipartBody.Part> {
        val photos = arrayListOf<ImageArrayBodyPost>()
        binding.multiplePhoto.images.forEach {
            photos.add(ImageArrayBodyPost(it))
        }

        val images = arrayListOf<MultipartBody.Part>()
        photos.forEach {
            images.add(
                MultipartBody.Part.createFormData(
                    "images", it.file.name, it.file.asRequestBody("image/*".toMediaTypeOrNull())
                )
            )
        }

        return images
    }

    private fun convertKilogramIntoTon(): String {
        with(binding) {
            var bruto = etBruto.text.toDouble()
            val unit = etBruto.unit.lowercase()

            if (unit == "kg") {
                bruto = etBruto.text.toDouble() / 1000.0
            }

            return bruto.toString()
        }
    }

    override fun doInsertTransaction() {
        with(binding) {
            val dateTransaction = getTransactionDate(etDateTransaction.text)
            val bruto = convertKilogramIntoTon()

            val data = InsertTransactionBodyPost(
                sharedVm.getSubVesselId(), dateTransaction, bruto,
                etProductType.text, etPriceOffer.text, etNotes.text
            )

            val images = getTransactionImages()

            viewModel.insertNewTransaction(data, images)
        }
    }

    override fun doUpdateTransaction() {
        with(binding) {
            val dateTransaction = getTransactionDate(etDateTransaction.text)
            val updateType = "submission-transaction"
            val bruto = convertKilogramIntoTon()

            val data = UpdateTransactionBodyPost(
                dateTransaction, bruto, etProductType.text,
                etPriceOffer.text, etNotes.text, updateType
            )

            val images = getTransactionImages()

            transactionId?.let { viewModel.updateTransaction(it, data, images) }
        }
    }

    override fun onInsertTransactionLoading() {
        binding.btnTransaction.isLoading = true
    }

    override fun onInsertTransactionSuccess() {
        activity?.onBackPressed()
        binding.btnTransaction.isLoading = false
        successSnackBar(getString(R.string.label_success_add_transaction))
    }

    override fun onInsertTransactionFailed(e: Throwable?) {
        binding.btnTransaction.isLoading = false
        errorSnackBar(getString(R.string.label_error_insert_snackbar))
    }

    private fun applyData(transactionDetail: TransactionDetail) {
        with(binding) {
            etDateTransaction.text = transactionDetail.dateTime.toDate(ConverterDate.SQL_DATE.formatter)
                ?.toString(ConverterDate.FULL_DATE.formatter).orEmpty()
            etProductType.text = transactionDetail.productType
            etBruto.text = transactionDetail.bruto.toString()
            etPriceOffer.text = transactionDetail.offeringPrice.toString()
            etNotes.text = transactionDetail.note
            multiplePhoto.addAll(transactionDetail.images)
        }
    }

    override fun onTransactionDetailLoading() {
        // Do Nothing
    }

    override fun onGetTransactionDetailSuccess(data: TransactionDetail) {
        applyData(data)
    }

    override fun onTransactionDetailFailed(e: Throwable?) {
        errorSnackBar(getString(R.string.label_title_fail_load_data))
    }

    override fun onUpdateTransactionLoading() {
        binding.btnTransaction.isLoading = true
    }

    override fun onUpdateTransactionSuccess() {
        activity?.onBackPressed()
        binding.btnTransaction.isLoading = false
        successSnackBar(getString(R.string.label_success_update_snackbar))
    }

    override fun onUpdateTransactionFailed(e: Throwable?) {
        binding.btnTransaction.isLoading = false
        errorSnackBar(getString(R.string.label_error_update_snackbar))
    }

    override fun onGetProductTypeLoading() {
        // Do Nothing
    }

    override fun onGetProductTypeSuccess(data: DetailCompany) {
        val productType: List<String>? = data.commodity.find { it.commodityId == sharedVm.getCommodityId() }?.productType

        if (productType != null) {
            binding.etProductType.addAll(productType)
        }
    }

    override fun onGetProductTypeFailed(e: Throwable?) {
        errorSnackBar(getString(R.string.label_title_fail_load_data))
    }
}
