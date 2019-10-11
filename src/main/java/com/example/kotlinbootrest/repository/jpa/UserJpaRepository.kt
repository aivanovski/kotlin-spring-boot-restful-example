package com.example.kotlinbootrest.repository.jpa

import com.example.kotlinbootrest.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserJpaRepository : JpaRepository<User, Long> {

    fun findByEmail(email: String): List<User>

}