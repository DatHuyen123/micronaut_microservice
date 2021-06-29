package com.microservice.fetcher

import io.micronaut.http.annotation.Header
import io.reactivex.Single

interface UsernameFetcher {
    fun findUsername(@Header("Authorization") authorization: String): Single<String>
}