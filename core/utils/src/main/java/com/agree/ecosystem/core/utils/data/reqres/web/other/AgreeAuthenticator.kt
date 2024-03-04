package com.agree.ecosystem.core.utils.data.reqres.web.other

import com.agree.ecosystem.core.utils.data.reqres.EngineTokenRepository
import com.agree.ecosystem.core.utils.data.reqres.TokenRepository
import com.agree.ecosystem.core.utils.domain.reqres.AgrPrefUsecase
import com.agree.ecosystem.core.utils.utility.Constant
import com.agree.ecosystem.core.utils.utility.CredentialManagement
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class AgreeAuthenticator(
    private val repo: TokenRepository,
    private val prefs: AgrPrefUsecase,
    private val repoMonitoring: EngineTokenRepository
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        return if (response.responseCount >= 3) {
            null
        } else {
            if (response.request.url.encodedPath.contains("engine")) {
                val newTokenRequest = repoMonitoring.singleRefreshToken(
                    CredentialManagement.getUsernameEngine(),
                    CredentialManagement.getPasswordEngine(),
                    Constant.MONITORING_LOGIN_DURATION,
                ).blockingGet()
                if (newTokenRequest.isSuccessful) {
                    newTokenRequest.body()?.let {
                        prefs.accessTokenEngine = it.data?.accessToken.orEmpty()
                        response.request.newBuilder()
                            .removeHeader("Authorization")
                            .addHeader("Authorization", "Bearer ${prefs.accessTokenEngine}")
                            .build()
                    }
                } else {
                    null
                }
            } else {
                val newTokenRequest = repo.singleRefreshToken(prefs.refreshToken).blockingGet()
                if (newTokenRequest.isSuccessful) {
                    newTokenRequest.body()?.let {
                        prefs.accessToken = it.data?.accessToken.orEmpty()
                        prefs.refreshToken = it.data?.refreshToken.orEmpty()
                        response.request.newBuilder()
                            .removeHeader("Authorization")
                            .addHeader("Authorization", "Bearer ${prefs.accessToken}")
                            .build()
                    }
                } else {
                    null
                }
            }
        }
    }
}

internal val Response.responseCount: Int
    get() = generateSequence(this) { it.priorResponse }.count()
