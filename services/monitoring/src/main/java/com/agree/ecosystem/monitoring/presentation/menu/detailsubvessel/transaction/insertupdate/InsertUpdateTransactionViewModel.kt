package com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.transaction.insertupdate

import com.agree.ecosystem.core.utils.utility.extension.immutable
import com.agree.ecosystem.core.utils.utility.extension.setHandler
import com.agree.ecosystem.monitoring.data.reqres.model.transaction.insert.InsertTransactionBodyPost
import com.agree.ecosystem.monitoring.data.reqres.model.transaction.update.UpdateTransactionBodyPost
import com.agree.ecosystem.monitoring.domain.reqres.MonitoringUseCase
import com.agree.ecosystem.monitoring.domain.reqres.model.detailcompany.DetailCompany
import com.agree.ecosystem.monitoring.domain.reqres.model.transaction.TransactionDetail
import com.agree.ecosystem.monitoring.domain.reqres.model.transaction.insert.InsertTransaction
import com.agree.ecosystem.monitoring.domain.reqres.model.transaction.update.UpdateTransaction
import com.devbase.data.source.DevData
import com.devbase.data.utilities.DevViewModel
import okhttp3.MultipartBody

class InsertUpdateTransactionViewModel(
    private val usecase: MonitoringUseCase
) : DevViewModel() {
    private val _transactionDetail = DevData<TransactionDetail>().apply { default() }
    val transactionDetail = _transactionDetail.immutable()

    fun getTransactionDetail(id: String) {
        usecase.getTransactionDetail(id)
            .setHandler(_transactionDetail)
            .let { return@let disposable::add }
    }

    private val _insertTransaction = DevData<InsertTransaction>().apply { default() }
    val insertTransaction = _insertTransaction.immutable()

    fun insertNewTransaction(data: InsertTransactionBodyPost, images: List<MultipartBody.Part>) {
        usecase.insertTransaction(
            data, images
        ).setHandler(_insertTransaction).let { return@let disposable::add }
    }

    private val _updateTransaction = DevData<UpdateTransaction>().apply { default() }
    val updateTransaction = _updateTransaction.immutable()

    fun updateTransaction(id: String, data: UpdateTransactionBodyPost, images: List<MultipartBody.Part>) {
        usecase.updateTransaction(
            id, data, images
        ).setHandler(_updateTransaction).let { return@let disposable::add }
    }

    private val _productType = DevData<DetailCompany>().apply { default() }
    val productType = _productType.immutable()

    fun getDetailCompany(companyId: String) {
        usecase.getDetailCompany(
            companyId
        ).setHandler(_productType).let { return@let disposable::add }
    }
}
