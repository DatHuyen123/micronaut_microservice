package com.dirox.service

import com.dirox.command.UserRegisterCommand
import com.dirox.command.UserUpdateCommand
import com.dirox.dto.UserDTO
import com.dirox.entity.User

interface UserService {

    fun create(userRegisterCommand: UserRegisterCommand): UserDTO

    fun getAllUsers(): List<UserDTO>

    fun getUserById(userId: Long): User?

    fun getUserByUserName(userName: String): User?

    fun getUserDTOById(userId: Long): UserDTO

    fun getUserDTOByUserName(userName: String): UserDTO

    fun deleteUserById(userId: Long)

    fun updateUserById(userUpdateCommand: UserUpdateCommand, userId: Long)
}