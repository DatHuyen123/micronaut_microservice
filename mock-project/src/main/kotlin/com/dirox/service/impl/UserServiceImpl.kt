package com.dirox.service.impl

import com.dirox.command.UserRegisterCommand
import com.dirox.command.UserUpdateCommand
import com.dirox.dto.UserDTO
import com.dirox.entity.User
import com.dirox.exceptions.UserBadRequestException
import com.dirox.mapper.UserMapper
import com.dirox.repository.UserRepository
import com.dirox.service.UserService
import io.micronaut.transaction.annotation.ReadOnly
import io.micronaut.transaction.annotation.TransactionalAdvice
import org.hibernate.exception.ConstraintViolationException
import javax.inject.Inject
import javax.inject.Singleton
import javax.persistence.EntityManager

@Singleton
open class UserServiceImpl : UserService {

    @Inject
    lateinit var entityManager: EntityManager

    @Inject
    lateinit var userRepository: UserRepository

    @Inject
    lateinit var userMapper: UserMapper

    @TransactionalAdvice
    override fun create(userRegisterCommand: UserRegisterCommand): UserDTO {
        val userName = userRegisterCommand.userName
        val user = getUserByUserName(userName)
        return user?.let { throw UserBadRequestException("User already existed: $userName") } ?: run {
            val createdUser = userRepository.save(userMapper.convertRegisterCommandToEntity(userRegisterCommand))
            userMapper.convertEntityToDTO(createdUser)
        }
    }

    @ReadOnly
    override fun getAllUsers(): List<UserDTO> {
        return userMapper.convertEntityListToDTOList(userRepository.findAll().distinct())
    }

    @ReadOnly
    override fun getUserById(userId: Long): User? {
        return userRepository.findById(userId).orElse(null)
    }

    @ReadOnly
    override fun getUserByUserName(userName: String): User? {
        val query = entityManager
            .createQuery("select u from User as u where u.userName = :userName", User::class.java)
            .setParameter("userName", userName)
        return if (query.resultList.size > 0) query.resultList[0] else null
    }

    @ReadOnly
    override fun getUserDTOById(userId: Long): UserDTO {
        return userMapper.convertEntityToDTO(
            getUserById(userId) ?: throw UserBadRequestException("User does not exist or has been removed: $userId")
        )
    }

    @ReadOnly
    override fun getUserDTOByUserName(userName: String): UserDTO {
        return getUserByUserName(userName)?.let { userMapper.convertEntityToDTO(it) }
            ?: throw UserBadRequestException("User does not exist or has been removed: $userName")
    }

    @TransactionalAdvice
    override fun deleteUserById(userId: Long) {
        val user = getUserById(userId)
        user?.let {
            try {
                userRepository.deleteById(userId)
            } catch (e: ConstraintViolationException) {
                throw UserBadRequestException("User does not exist or has been removed: $userId")
            }
        } ?: throw UserBadRequestException("User does not exist or has been removed: $userId")
    }

    @TransactionalAdvice
    override fun updateUserById(userUpdateCommand: UserUpdateCommand, userId: Long) {
        val user = getUserById(userId)
        user?.let {
            user.userName = userUpdateCommand.userName
            user.age = userUpdateCommand.age
            user.role = userUpdateCommand.role
//            user.password = userUpdateCommand.password
            userRepository.update(user)
        } ?: throw UserBadRequestException("User does not exist or has been removed: $userId")
    }
}