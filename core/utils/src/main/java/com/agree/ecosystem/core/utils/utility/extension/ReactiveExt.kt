package com.agree.ecosystem.core.utils.utility.extension

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import com.devbase.data.source.DevData
import com.devbase.data.utilities.rx.genericErrorHandler
import com.devbase.data.utilities.rx.transformers.flowableScheduler
import io.reactivex.*
import io.reactivex.disposables.Disposable
import io.reactivex.subscribers.DisposableSubscriber

fun <R : Any> Single<R>.setHandler(
    devData: DevData<R>,
    codeHandler: (Int.(String?) -> Unit)? = null
): Disposable {
    return this.toFlowable().setHandler(
        devData, codeHandler
    )
}

fun <R : Any> Observable<R>.setHandler(
    devData: DevData<R>,
    codeHandler: (Int.(String?) -> Unit)? = null
): Disposable {
    return this.toFlowable(BackpressureStrategy.BUFFER).setHandler(
        devData, codeHandler
    )
}

fun <R : Any> Completable.setHandler(
    devData: DevData<R>,
    codeHandler: (Int.(String?) -> Unit)? = null
): Disposable {
    return this.toFlowable<R>().setHandler(
        devData, codeHandler
    )
}

fun <R : Any> Flowable<R>.setHandler(
    devData: DevData<R>,
    codeHandler: (Int.(String?) -> Unit)? = null
): Disposable {
    return this
        .compose(flowableScheduler())
        .cache()
        .onBackpressureBuffer()
        .doOnSubscribe {
            it.request(Long.MAX_VALUE)
        }
        .subscribeWith(object : DisposableSubscriber<R>() {
            override fun onStart() {
                super.onStart()
                devData.loading()
            }

            override fun onNext(t: R) {
                if (t is List<*> && t.isEmpty()) {
                    devData.empty()
                } else {
                    devData.success(t)
                }
                onComplete()
            }

            override fun onError(t: Throwable) {
                t.printStackTrace()
                val error = t.networkError()

                if (codeHandler != null) {
                    codeHandler.invoke(error.second, error.first.message)
                } else {
                    genericErrorHandler(error.first, devData)
                }

                onComplete()
            }

            override fun onComplete() {
                devData.default()
            }
        })
}

/**
 * Verify Token Section
 */

fun Lifecycle.addObservers(vararg lifecycleObserver: LifecycleObserver) {
    lifecycleObserver.forEach {
        addObserver(it)
    }
}

fun Fragment.addObservers(vararg lifecycleObserver: LifecycleObserver) {
    viewLifecycleOwner.lifecycle.addObservers(*lifecycleObserver)
}

fun AppCompatActivity.addObservers(vararg lifecycleObserver: LifecycleObserver) {
    lifecycle.addObservers(*lifecycleObserver)
}
