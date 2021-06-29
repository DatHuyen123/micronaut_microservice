package com.microservice.config

import io.micronaut.http.HttpRequest
import io.micronaut.security.authentication.*
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableEmitter
import org.reactivestreams.Publisher
import javax.inject.Singleton

@Singleton
class AuthenticationProviderUserPassword: AuthenticationProvider {
    override fun authenticate(
        httpRequest: HttpRequest<*>?,
        authenticationRequest: AuthenticationRequest<*, *>?
    ): Publisher<AuthenticationResponse> {
        val userName = authenticationRequest?.identity.toString()
        val password = authenticationRequest?.secret.toString()

        return Flowable.create({ emitter: FlowableEmitter<AuthenticationResponse> ->
            if (userName.equals("sherlock") && password.equals("password")) {
                emitter.onNext(UserDetails(userName, arrayListOf("ROLE_ADMIN", "ROLE_USER")))
                emitter.onComplete()
            } else {
                emitter.onError(AuthenticationException(AuthenticationFailed()))
            }
        }, BackpressureStrategy.ERROR)
    }
}