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
import javax.annotation.security.PermitAll
import javax.inject.Inject

@Controller("/user")
class UserController(private val usernameFetcher: UsernameFetcher) {

    @Secured("ROLE_ADMIN")
    @Produces(MediaType.TEXT_PLAIN)
    @Get
    fun index(): Single<String> {
        return usernameFetcher.findUsername()
    }
}