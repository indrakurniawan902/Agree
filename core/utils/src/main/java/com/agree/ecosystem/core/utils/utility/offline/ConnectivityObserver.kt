package com.agree.ecosystem.core.utils.utility.offline

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {

    /**
     * Check if the device is connected to the internet
     */
    val isConnected: Boolean

    /**
     * Observe the connectivity status
     */
    fun observe(): Flow<Status>

    enum class Status {
        Available, Unavailable, Losing, Lost
    }
}
