package com.microservice.model

data class UserModel(
    var id: Long = 1,
    var username: String = "",
    var password: String = ""
)