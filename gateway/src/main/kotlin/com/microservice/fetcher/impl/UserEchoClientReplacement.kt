package com.microservice.fetcher.impl

import com.microservice.fetcher.UsernameFetcher
import io.micronaut.context.env.Environment
import io.micronaut.context.annotation.Requires
import io.reactivex.Single
import javax.inject.Singleton

@Requires(env = [Environment.TEST])
@Singleton
class UserEchoClientReplacement: UsernameFetcher {

    override fun findUsername(authorization: String): Single<String> {
        return Single.just("sherlock")
    }

}