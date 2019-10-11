package com.example.kotlinbootrest.service

import com.example.kotlinbootrest.model.User
import org.apache.tomcat.util.codec.binary.Base64
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest

@Service
class AuthService {

    @Autowired
    private lateinit var userService: UserService

    fun getAuthenticatedUserFromRequest(request: HttpServletRequest): User? {
        val authHeader = parseAuthHeader(request.getHeader(HttpHeaders.AUTHORIZATION)) ?: return null

        if (!isBasicAuthType(authHeader)) return null

        val credentials = parseAuthToken(authHeader.token) ?: return null

        val user = userService.getUserByEmail(credentials.email) ?: return null

        if (credentials.password != user.password) return null

        return user
    }

    private fun parseAuthHeader(header: String?): AuthHeader? {
        if (header == null) {
            return null
        }

        val values = header.split(" ".toRegex())
        if (values.size == 2
                && values[0].isNotEmpty()
                && values[1].isNotEmpty()) {
            return AuthHeader(values[0], values[1])
        }

        return null
    }

    private fun isBasicAuthType(authHeader: AuthHeader?): Boolean {
        return authHeader != null && authHeader.type == AUTHORIZATION_BASIC
    }

    private fun parseAuthToken(token: String): AuthCredentials? {
        val decodedToken = String(Base64.decodeBase64(token))
        val values = decodedToken.split(":")
        if (values.size == 2
                && values[0].isNotEmpty()
                && values[1].isNotEmpty()) {
            return AuthCredentials(values[0], values[1])
        }

        return null
    }

    private data class AuthHeader(val type: String, val token: String)
    private data class AuthCredentials(val email: String, val password: String)

    companion object {

        private const val AUTHORIZATION_BASIC = "Basic"

    }

}