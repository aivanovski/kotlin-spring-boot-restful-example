package com.example.kotlinbootrest.service

import org.springframework.stereotype.Service
import java.util.regex.Pattern

@Service
class EmailValidator {

    private val emailPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)

    fun isEmailValid(email: String?): Boolean {
        return email != null && emailPattern.matcher(email).matches()
    }

}