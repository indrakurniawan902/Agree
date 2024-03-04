package com.agree.ecosystem.utilities.presentation.menu.about

interface AboutAgreeDataContract {

    fun onGetAboutAgreeLoading()

    fun onGetAboutAgreeSuccess(data: String?)

    fun onGetAboutAgreeFailed(e: Throwable?)
}
