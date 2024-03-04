package com.agree.ecosystem.core.utils.di

import com.agree.ecosystem.core.utils.data.reqres.web.other.AgreeAuthenticator
import com.agree.ecosystem.core.utils.data.reqres.web.other.AgreeInterceptor
import com.agree.ecosystem.core.utils.utility.AppConfig
import com.agree.ecosystem.core.utils.utility.Constant
import com.agree.ecosystem.core.utils.utility.UrlManagement
import com.agree.ecosystem.core.utils.utility.offline.ConnectivityObserver
import com.agree.ecosystem.core.utils.utility.offline.NetworkConnectivityObserver
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.devbase.data.source.web.libs.createOkHttpClient
import me.jessyan.progressmanager.ProgressManager
import me.jessyan.retrofiturlmanager.RetrofitUrlManager
import okhttp3.ConnectionSpec
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

/**
 * Repository Injection Module are modules that are responsible for injection of classes
 * related to Api Client and Interceptors
 * @author: @telkomdev-abdul
 * @since: 3 Oct 2022
 */
interface ApiModule {
    fun provideApiModules(): Array<Module> {
        return arrayOf(
            provideInterceptor(),
            provideAuthenticator(),
            provideChucker(),
            provideOkHttpClient(),
            provideConnectivityObserver()
        )
    }

    /**
     * Custom Interceptor for intercept bearer token
     */
    fun provideInterceptor(): Module {
        return module {
            single { AgreeInterceptor(get()) }
        }
    }

    /**
     * Authenticator for auto refresh feature
     */
    fun provideAuthenticator(): Module {
        return module {
            single { AgreeAuthenticator(get(), get(), get()) }
        }
    }

    /**
     * Chucker Interceptor for debugging api
     */
    fun provideChucker(): Module {
        return module {
            single {
                ChuckerCollector(
                    context = androidContext(),
                    showNotification = AppConfig.isDebug,
                    retentionPeriod = RetentionManager.Period.ONE_DAY
                )
            }

            single {
                ChuckerInterceptor.Builder(androidContext())
                    .apply {
                        collector(get())
                        maxContentLength(250_000L)
                        alwaysReadResponseBody(false)
                        if (!AppConfig.isDebug) {
                            redactHeaders("Authorization", "Bearer")
                            redactHeaders("Authorization", "Basic")
                        }
                    }
                    .build()
            }
        }
    }

    /**
     * OkHttp Client
     */
    fun provideOkHttpClient(): Module {
        return module {
            factory { (isDownloadOrUpload: Boolean?, withAuth: Boolean?) ->
                val client = createOkHttpClient(
                    androidContext(),
                    interceptors = arrayOf(
                        get<AgreeInterceptor>()
                    ),
                    authenticator = null,
                    pinner = null,
                    showDebugLog = AppConfig.isDebug
                ).newBuilder()
                val builder =
                    if (isDownloadOrUpload == true)
                        ProgressManager.getInstance().with(client).build().newBuilder()
                    else {
                        /**
                         * Dynamic Base Url
                         * @Headers({"Domain-Name: Constant.BASE_URL_DOMAIN"})
                         * Add the Domain-Name header to ApiClient
                         */
                        RetrofitUrlManager.getInstance()
                            .apply {
                                putDomain(Constant.BASE_URL_DOMAIN, UrlManagement.getBaseUrl())
                                putDomain("test", "https://test")
                            }
                            .with(client)
                            .build()
                            .newBuilder()
                    }
                if (withAuth == true) {
                    builder.authenticator(get<AgreeAuthenticator>())
                }
                builder
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .addInterceptor(get<ChuckerInterceptor>())
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .connectionSpecs(
                        listOf(
                            ConnectionSpec.CLEARTEXT,
                            ConnectionSpec.MODERN_TLS
                        )
                    ).apply {
                        /**
                         * Enable this if need bypassing SSL
                         */
//                if (AppConfig.isDebug) {
//                    val trustAllCerts = arrayOf<TrustManager>(
//                        object : X509TrustManager {
//                            @Throws(CertificateException::class)
//                            override fun checkClientTrusted(
//                                chain: Array<X509Certificate?>?,
//                                authType: String?
//                            ) {
//                            }
//
//                            @Throws(CertificateException::class)
//                            override fun checkServerTrusted(
//                                chain: Array<X509Certificate?>?,
//                                authType: String?
//                            ) {
//                            }
//
//                            override fun getAcceptedIssuers(): Array<X509Certificate?>? {
//                                return arrayOf()
//                            }
//                        }
//                    )
//
//                    val sslContext = SSLContext.getInstance("TLSv1.2")
//                    sslContext.init(null, trustAllCerts, SecureRandom())
//                    // Create an ssl socket factory with our all-trusting manager
//                    val sslSocketFactory = sslContext.socketFactory
//                    val trustManagerFactory: TrustManagerFactory =
//                        TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
//                    trustManagerFactory.init(null as KeyStore?)
//                    val trustManagers: Array<TrustManager> =
//                        trustManagerFactory.trustManagers
//                    check(!(trustManagers.size != 1 || trustManagers[0] !is X509TrustManager)) {
//                        "Unexpected default trust managers:" + trustManagers.contentToString()
//                    }
//
//                    val trustManager =
//                        trustManagers[0] as X509TrustManager
//
//                    sslSocketFactory(sslSocketFactory, trustManager)
//                    hostnameVerifier { hostname, session -> true }
//                }
                    }
                builder.build()
            }
        }
    }

    fun provideConnectivityObserver(): Module {
        return module {
            single { NetworkConnectivityObserver(androidContext()) } bind ConnectivityObserver::class
        }
    }
}
