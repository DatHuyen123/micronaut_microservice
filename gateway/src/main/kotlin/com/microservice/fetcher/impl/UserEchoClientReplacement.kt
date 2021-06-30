package com.microservice.fetcher.impl

import com.microservice.fetcher.UsernameFetcher
import com.microservice.model.UserModel
import com.microservice.model.command.UserRegisterCommand
import io.micronaut.context.env.Environment
import io.micronaut.context.annotation.Requires
import io.reactivex.Single
import javax.inject.Singleton

@Requires(env = [Environment.TEST])
@Singleton
class UserEchoClientReplacement: UsernameFetcher {

    override fun findUsername(): Single<String> {
        return Single.just("sherlock123123")
    }

    /*override fun findUser(): UserModel {
        TODO("Not yet implemented")
    }*/

    override fun signup(userRegisterCommand: UserRegisterCommand): UserModel {
        TODO("Not yet implemented")
    }

    override fun getByUsername(username: String): UserModel {
        TODO("Not yet implemented")
    }

}