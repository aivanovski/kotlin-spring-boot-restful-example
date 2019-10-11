package com.example.kotlinbootrest.repository

import com.example.kotlinbootrest.model.User
import com.example.kotlinbootrest.repository.jpa.UserJpaRepository
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class UserRepository {

    @Autowired
    private lateinit var userRepo: UserJpaRepository

    fun getAll(): List<User> {
        return userRepo.findAll()
    }

    fun add(user: User): User {
        return userRepo.saveAndFlush(user)
    }

    fun getById(id: Long): User? {
        return userRepo.findById(id).orElse(null)
    }

    fun update(user: User) {
        val id = user.id ?: return
        val existingUser = getById(id) ?: return

        BeanUtils.copyProperties(user, existingUser)
        userRepo.saveAndFlush(existingUser)
    }

    fun getAllByEmail(email: String): List<User> {
        return userRepo.findByEmail(email)
    }

}