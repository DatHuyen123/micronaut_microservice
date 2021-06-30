package com.dirox.controller

import com.dirox.command.UserRegisterCommand
import com.dirox.command.UserUpdateCommand
import com.dirox.dto.UserDTO
import com.dirox.entity.User
import com.dirox.exceptions.UserBadRequestException
import com.dirox.service.UserService
import io.micronaut.context.annotation.Parameter
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import io.micronaut.security.annotation.Secured
import io.micronaut.security.authentication.Authentication
import io.micronaut.security.rules.SecurityRule
import io.micronaut.validation.Validated
import javax.annotation.security.PermitAll
import javax.inject.Inject
import javax.validation.Valid

@Validated
@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller("/users")
class UserController {

    @Inject
    lateinit var userService: UserService

    @PermitAll
    @Post("/signup", consumes = [MediaType.APPLICATION_JSON], produces = [MediaType.APPLICATION_JSON])
    fun signup(@Body @Valid userRegisterCommand: UserRegisterCommand): HttpResponse<UserDTO> {
        return HttpResponse.created(userService.create(userRegisterCommand))
    }

    @Get("/", produces = [MediaType.APPLICATION_JSON])
    fun getAllUsers(authentication: Authentication): HttpResponse<List<UserDTO>> {

        val username = authentication.name
        val roles = authentication.attributes["roles"]

        return HttpResponse.ok(
            if ((roles as List<*>).contains("ROLE_ADMIN")) {
                userService.getAllUsers()
            } else {
                listOf(userService.getUserDTOByUserName(username))
            }
        )
    }

    /*@Get("/{userId}", produces = [MediaType.APPLICATION_JSON])
    fun getUserById(@PathVariable userId: Long, authentication: Authentication): HttpResponse<UserDTO> {

        val username = authentication.name
        val roles = authentication.attributes["roles"]

        return if ((roles as List<*>).contains("ROLE_ADMIN")) {
            HttpResponse.ok(userService.getUserDTOById(userId))
        } else {
            val user = userService.getUserByUserName(username)
            if (user != null && user.id == userId) {
                HttpResponse.ok(userService.getUserDTOById(userId))
            } else {
                throw UserBadRequestException("User does not exist or has been removed: $userId")
            }
        }
    }*/

    @Secured("ROLE_ADMIN")
    @Delete("/{userId}", produces = [MediaType.APPLICATION_JSON])
    fun deleteUserById(@PathVariable userId: Long, authentication: Authentication): HttpResponse<Any> {
        val currentUserName = authentication.name
        val currentUser = userService.getUserByUserName(currentUserName)
        if (currentUser != null) {
            val currentUserId = currentUser.id
            if (userId == currentUserId) {
                throw UserBadRequestException("Can not delete your own account.")
            }
            userService.deleteUserById(userId)
        } else {
            throw UserBadRequestException("User does not exist or has been removed: $userId")
        }
        return HttpResponse.status(HttpStatus.NO_CONTENT)
    }

    @Put("/{userId}", consumes = [MediaType.APPLICATION_JSON], produces = [MediaType.APPLICATION_JSON])
    fun updateUser(
        @Body @Valid userUpdateCommand: UserUpdateCommand,
        @PathVariable userId: Long
    ): HttpResponse<Any> {
        userService.updateUserById(userUpdateCommand, userId)
        return HttpResponse.status(HttpStatus.NO_CONTENT)
    }

    @PermitAll
    @Get("/{username}" , produces = [MediaType.APPLICATION_JSON])
    fun getByUsername(@PathVariable username: String): User? {
        return  userService.getUserByUserName(username)
    }
}