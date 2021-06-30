package com.dirox.dto

import javax.validation.constraints.NotNull

data class UserDTO(
    var id: Long,
    var userName: String,
    var age: Int,
    val role: @NotNull String
)
