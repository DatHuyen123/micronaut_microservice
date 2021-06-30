package com.microservice.fetcher

import com.microservice.model.UserModel
import com.microservice.model.command.UserRegisterCommand
import io.micronaut.context.annotation.Parameter
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*
import io.micronaut.http.client.annotation.Client;
import io.micronaut.security.authentication.AuthenticationRequest
import io.micronaut.security.authentication.UsernamePasswordCredentials
import io.reactivex.Single;
import javax.annotation.security.PermitAll
import javax.validation.Valid

@Client(id = "userecho")
@Requires(notEnv = [Environment.TEST])
interface UserEchoClient: UsernameFetcher {

    @Consumes(MediaType.TEXT_PLAIN)
    @Get("/user")
    override fun findUsername(): Single<String>

    @PermitAll
    @Post("/users/signup", consumes = [MediaType.APPLICATION_JSON], produces = [MediaType.APPLICATION_JSON])
    override fun signup(@Body @Valid userRegisterCommand: UserRegisterCommand): UserModel

    @Get("/users/{username}" , produces = [MediaType.APPLICATION_JSON])
    override fun getByUsername(@PathVariable username: String): UserModel
}