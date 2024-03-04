package com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.dialog.addactivity

interface AddActivityDataContract {

    fun onPostInsertSopDateLoading()

    fun onPostInsertSopDateSuccess(data: Any)

    fun onPostInsertSopDateFailed(e: Throwable?)
}
