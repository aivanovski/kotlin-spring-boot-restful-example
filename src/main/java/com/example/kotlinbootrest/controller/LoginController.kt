@file:Suppress("FoldInitializerAndIfToElvis")

package com.example.kotlinbootrest.controller

import com.example.kotlinbootrest.service.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import javax.servlet.http.HttpServletRequest

@RestController
class LoginController {

    @Autowired
    private lateinit var authService: AuthService

    @RequestMapping(Api.LOGIN, method = [RequestMethod.GET])
    fun login(request: HttpServletRequest): ResponseObj {
        val user = authService.getAuthenticatedUserFromRequest(request)
        if (user == null) {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "access denied")
        }

        return ResponseObj(user.email, user.uid)
    }

    @Suppress("unused")
    class ResponseObj(val email: String, val uid: String)

}