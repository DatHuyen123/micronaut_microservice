package com.dirox.mapper

import com.dirox.command.UserRegisterCommand
import com.dirox.dto.UserDTO
import com.dirox.entity.User
import org.mapstruct.Mapper
import org.mapstruct.Mappings

@Mapper(componentModel = "jsr330")
interface UserMapper {

    @Mappings
    fun convertRegisterCommandToEntity(userRegisterCommand: UserRegisterCommand): User

    @Mappings
    fun convertEntityToDTO(user: User): UserDTO

    @Mappings
    fun convertEntityListToDTOList(users: List<User>): List<UserDTO>
}