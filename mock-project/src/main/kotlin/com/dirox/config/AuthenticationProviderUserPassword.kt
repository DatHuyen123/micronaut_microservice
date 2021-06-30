package com.dirox.config

import com.dirox.service.UserService
import io.micronaut.http.HttpRequest
import io.micronaut.security.authentication.*
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableEmitter
import org.reactivestreams.Publisher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticationProviderUserPassword : AuthenticationProvider {

    @Inject
    lateinit var userService: UserService

    override fun authenticate(
        httpRequest: HttpRequest<*>?,
        authenticationRequest: AuthenticationRequest<*, *>?
    ): Publisher<AuthenticationResponse> {

        val userName = authenticationRequest?.identity.toString()
        val password = authenticationRequest?.secret.toString()

        val user = userService.getUserByUserName(userName)

        return Flowable.create({ emitter: FlowableEmitter<AuthenticationResponse> ->
            if (user != null && userName == user.userName && password == user.password) {
                emitter.onNext(UserDetails(userName, arrayListOf(user.role)))
                emitter.onComplete()
            } else {
                emitter.onError(AuthenticationException(AuthenticationFailed()))
            }
        }, BackpressureStrategy.ERROR)
    }
}