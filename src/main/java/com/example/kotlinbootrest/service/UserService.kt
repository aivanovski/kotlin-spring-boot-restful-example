package com.example.kotlinbootrest.service

import com.example.kotlinbootrest.model.User
import com.example.kotlinbootrest.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService {

    @Autowired
    private lateinit var userRepo: UserRepository

    fun createNewUser(email: String, password: String): User? {
        val existingUsers = userRepo.getAllByEmail(email)
        if (existingUsers.isNotEmpty()) {
            return null
        }

        return userRepo.add(User(null, UUID.randomUUID().toString(), email, password))
    }

    fun getUserByEmail(email: String): User? {
        return userRepo.getAllByEmail(email)
                .firstOrNull()
    }

}