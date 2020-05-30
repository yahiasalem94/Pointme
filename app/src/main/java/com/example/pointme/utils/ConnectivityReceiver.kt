package com.example.pointme.utils

import android.annotation.TargetApi
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import androidx.lifecycle.LiveData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConnectionLiveData constructor(context: Context) : LiveData<Boolean>() {

    private var isOnline = false
    private var connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private var callback = ConnectionStatusCallback()

    init {
        isOnline = getInitialConnectionStatus()
        postValue(isOnline)
        try {
            connectivityManager.unregisterNetworkCallback(callback)
        } catch (e: Exception) {
            Log.w(this.javaClass.name, "NetworkCallback for Wi-fi was not registered or already unregistered")
        }
        connectivityManager.registerNetworkCallback(NetworkRequest.Builder().build(), callback)
    }

    private fun getInitialConnectionStatus(): Boolean {
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        return capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
    }

    inner class ConnectionStatusCallback : ConnectivityManager.NetworkCallback() {

        private val activeNetworks: MutableList<Network> = mutableListOf()

        override fun onLost(network: Network) {
            super.onLost(network)
            activeNetworks.removeAll { activeNetwork -> activeNetwork == network }
            isOnline = activeNetworks.isNotEmpty()
            postValue(isOnline)
        }

        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            if (activeNetworks.none { activeNetwork -> activeNetwork == network }) {
                activeNetworks.add(network)
            }
            isOnline = activeNetworks.isNotEmpty()
            postValue(isOnline)
        }
    }
}

//class ConnectionLiveData(val context: Context) : LiveData<Boolean>() {
//
//    private var connectivityManager: ConnectivityManager = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
//
//    private lateinit var connectivityManagerCallback: ConnectivityManager.NetworkCallback
//    private val activeNetworks: MutableList<Network> = mutableListOf()
//
//    override fun onActive() {
//        super.onActive()
//        updateConnection()
//        connectivityManager.registerDefaultNetworkCallback(getConnectivityManagerCallback())
//        connectivityManager.registerNetworkCallback(networkRequest, connectivityManagerCallback)
//
//    }
//
//    override fun onInactive() {
//        super.onInactive()
//        connectivityManager.unregisterNetworkCallback(connectivityManagerCallback)
//    }
//
//
//    private fun getConnectivityManagerCallback(): ConnectivityManager.NetworkCallback {
//        connectivityManagerCallback = object : ConnectivityManager.NetworkCallback() {
//            override fun onAvailable(network: Network?) {
//                // Add to list of active networks if not already in list
//                if (activeNetworks.none { activeNetwork -> activeNetwork.networkHandle == network!!.networkHandle }) activeNetworks.add(network!!)
//                val isNetworkConnected = activeNetworks.isNotEmpty()
//
//                postValue(isNetworkConnected)
//            }
//
//            override fun onLost(network: Network?) {
//                // Remove network from active network list
//                activeNetworks.removeAll { activeNetwork -> activeNetwork.networkHandle == network!!.networkHandle }
//                val isNetworkConnected = activeNetworks.isNotEmpty()
//                postValue(isNetworkConnected)
//            }
//        }
//        return connectivityManagerCallback
//    }
//
//    private fun updateConnection() {
//        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
//        postValue(activeNetwork?.isConnected == true)
//    }
//}