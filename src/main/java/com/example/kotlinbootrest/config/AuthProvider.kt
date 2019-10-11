package com.example.kotlinbootrest.config

import com.example.kotlinbootrest.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component

@Component
class AuthProvider : AuthenticationProvider {

    @Autowired
    private lateinit var userService: UserService

    override fun authenticate(authentication: Authentication?): Authentication? {
        if (authentication == null) {
            return null
        }

        val email = authentication.name
        val password = authentication.credentials.toString()

        val user = userService.getUserByEmail(email) ?: return null

        if (email == user.email && password == user.password) {
            return UsernamePasswordAuthenticationToken(email, password, mutableListOf())
        }

        return null
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return authentication == UsernamePasswordAuthenticationToken::class.java
    }

}