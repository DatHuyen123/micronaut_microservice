package com.dirox.command

import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank

@Introspected
data class UserRegisterCommand(
    @NotBlank var userName: String,
    @NotBlank var password: String,
    var age: Int,
    @NotBlank var role: String
)