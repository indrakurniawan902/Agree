package com.agree.ecosystem.core.utils.utility.extension

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

fun <T> MutableLiveData<T>.immutable() = this as LiveData<T>

fun <T> MutableStateFlow<T>.immutable() = this.asStateFlow()
