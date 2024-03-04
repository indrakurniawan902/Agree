package com.agree.ecosystem.auth.presentation.menu.changepassword

interface ChangePasswordDataContract {
    fun onLoading()

    fun onChangePasswordSuccess()

    fun onChangePasswordFailed(e: Throwable?)
}
