package com.microservice.model

data class UserModel (
    var userName: String,
    var password: String = "",
    var age: Int,
    val role: String
)