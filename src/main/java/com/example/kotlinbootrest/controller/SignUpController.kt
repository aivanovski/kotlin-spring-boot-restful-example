@file:Suppress("FoldInitializerAndIfToElvis")

package com.example.kotlinbootrest.controller

import com.example.kotlinbootrest.service.EmailValidator
import com.example.kotlinbootrest.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
class SignUpController {

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var emailValidator: EmailValidator

    @RequestMapping(Api.SIGN_UP, method = [RequestMethod.POST])
    fun createNewUser(@RequestBody userDto: UserDto): ResponseObj {
        if (!emailValidator.isEmailValid(userDto.email)) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Email has not valid format")
        }

        if (userDto.password == null || userDto.password.isEmpty()) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Password is empty")
        }

        val user = userService.createNewUser(userDto.email!!, userDto.password)
        if (user == null) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to create user")
        }

        return ResponseObj(user.email, user.uid)
    }

    data class UserDto(val email: String?, val password: String?)

    data class ResponseObj(val email: String, val uid: String)

}