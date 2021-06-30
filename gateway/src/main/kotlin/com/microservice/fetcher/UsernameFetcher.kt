package com.microservice.fetcher

import com.microservice.model.UserModel
import com.microservice.model.command.UserRegisterCommand
import io.reactivex.Single

interface UsernameFetcher {
    fun findUsername(): Single<String>
//    fun findUser(): UserModel
    fun signup(userRegisterCommand: UserRegisterCommand): UserModel
    fun getByUsername(username: String): UserModel
}