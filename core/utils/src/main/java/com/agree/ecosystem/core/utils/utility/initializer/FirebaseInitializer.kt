package com.agree.ecosystem.core.utils.utility.initializer

import android.content.Context
import androidx.startup.Initializer
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import com.google.firebase.ktx.initialize

class FirebaseInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        Firebase.initialize(context)
        Firebase.analytics
        Firebase.app
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}
