package com.microservice.controller

import com.microservice.fetcher.UsernameFetcher
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.Produces
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.reactivex.Single

@Controller("/user")
class UserController(private val usernameFetcher: UsernameFetcher) {

    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Produces(MediaType.TEXT_PLAIN)
    @Get
    fun index(@Header("Authorization") authorization: String): Single<String> {
        return usernameFetcher.findUsername(authorization)
    }
}