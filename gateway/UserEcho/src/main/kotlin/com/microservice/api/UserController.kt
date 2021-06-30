package com.microservice.api

import com.microservice.model.UserModel
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import io.micronaut.security.annotation.Secured
import io.micronaut.security.authentication.Authentication
import io.micronaut.security.rules.SecurityRule
import java.security.Principal
import javax.annotation.security.PermitAll

@Controller("/user")
class UserController {

    @Secured("ROLE_ADMIN")
    @Produces(MediaType.TEXT_PLAIN)
    @Get
    fun index(authentication: Authentication): String {
        return authentication.name
    }

    @PermitAll
    @Get("/user")
    fun getUser(): UserModel {
        val userModel: UserModel = UserModel(id = 1 , username = "dvdat1" , password = "1234546")
        return userModel
    }

}