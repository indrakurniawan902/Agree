package com.agree.ecosystem.core.analytics

import android.os.Bundle

interface AgrAnalytics {

    /**
     * method to log Event
     * @param eventName name of the event
     * @param params parameter according to the event that occurred
     */
    fun logEvent(eventName: String, params: Bundle?)

    /**
     * method to ser user's property
     * @param name name of the property
     * @param value value of the property
     */
    fun setUserProperty(name: String, value: String)

    /**
     * method to set user's id, later on this id will be embedded to each event that happens
     * @param id id to set
     */
    fun setUserId(id: String)

    /**
     * method to clear analytics data in the device
     */
    fun clearData()

    /**
     * method to get instance Id of the application
     * @param listener action when the id is set
     */
    fun instanceId(listener: (String) -> Unit)
}
