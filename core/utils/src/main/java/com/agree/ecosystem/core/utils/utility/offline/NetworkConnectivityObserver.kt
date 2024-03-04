package com.agree.ecosystem.core.utils.utility.offline

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class NetworkConnectivityObserver(
    context: Context
) : ConnectivityObserver {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    /**
     * Check if the device is connected to the internet
     */
    override val isConnected: Boolean
        get() {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                checkInternetConnectionMarshmallow(connectivityManager)
            } else {
                checkInternetConnectionLegacy(connectivityManager)
            }
        }

    /**
     * Observe the connectivity status
     */
    override fun observe(): Flow<ConnectivityObserver.Status> {
        connectivityManager.isActiveNetworkMetered
        return callbackFlow {
            val callback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    launch { send(ConnectivityObserver.Status.Available) }
                }

                override fun onLosing(network: Network, maxMsToLive: Int) {
                    super.onLosing(network, maxMsToLive)
                    launch { send(ConnectivityObserver.Status.Losing) }
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    launch { send(ConnectivityObserver.Status.Lost) }
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    launch { send(ConnectivityObserver.Status.Unavailable) }
                }
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                // Versi SDK 24 (Nougat) dan yang lebih baru
                registerNetworkCallbackNougatAndAbove(callback)
            } else {
                // Versi SDK sebelum 24
                registerNetworkCallbackLegacy(callback)
            }

            awaitClose {
                connectivityManager.unregisterNetworkCallback(callback)
            }
        }.distinctUntilChanged()
    }

    /**
     * Register a network callback for API 21 to 23
     */
    private fun registerNetworkCallbackLegacy(callback: ConnectivityManager.NetworkCallback) {
        val networkChangeFilter = NetworkRequest.Builder()
            .build()
        connectivityManager.registerNetworkCallback(networkChangeFilter, callback)
    }

    /**
     * Register a network callback for API 24 and above
     */
    @RequiresApi(Build.VERSION_CODES.N)
    private fun registerNetworkCallbackNougatAndAbove(callback: ConnectivityManager.NetworkCallback) {
        connectivityManager.registerDefaultNetworkCallback(callback)
    }

    /**
     * Check if the device is connected to the internet on API 23 and above
     */
    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkInternetConnectionMarshmallow(connectivityManager: ConnectivityManager): Boolean {
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)

        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            ?: false
    }

    /**
     * Check if the device is connected to the internet on API 22 and below
     */
    @Suppress("DEPRECATION")
    private fun checkInternetConnectionLegacy(connectivityManager: ConnectivityManager): Boolean {
        val activeNetworkInfo = connectivityManager.activeNetworkInfo

        return (activeNetworkInfo != null) && activeNetworkInfo.isConnected
    }
}
