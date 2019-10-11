package com.ivanovsky.kotlinbootrest.repository

import com.google.common.truth.Truth.assertThat
import com.example.kotlinbootrest.App
import com.example.kotlinbootrest.model.User
import com.example.kotlinbootrest.repository.UserRepository
import com.example.kotlinbootrest.repository.jpa.UserJpaRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import java.util.*

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [App::class])
class UserRepositoryTest {

    @Autowired
    private lateinit var userRepo: UserRepository

    @Autowired
    private lateinit var userJpaRepo: UserJpaRepository

    @Before
    fun init() {
        userJpaRepo.deleteAll()
    }

    @Test
    fun `add should return User`() {
        val expectedUser = firstUser()

        val user = userRepo.add(firstUser())

        assertThat(user.id).isGreaterThan(0)
        assertThat(user.uid).isEqualTo(expectedUser.uid)
        assertThat(user.email).isEqualTo(expectedUser.email)
        assertThat(user.password).isEqualTo(expectedUser.password)
    }

    @Test
    fun `getAll should return users`() {
        userRepo.add(firstUser())
        userRepo.add(secondUser())

        val expectedFirstUser = firstUser()
        val expectedSecondUser = secondUser()

        val users = userRepo.getAll()

        assertThat(users.size).isEqualTo(2)

        val firstUser = users[0]
        assertThat(firstUser.id).isGreaterThan(0)
        assertThat(firstUser.uid).isEqualTo(expectedFirstUser.uid)
        assertThat(firstUser.email).isEqualTo(expectedFirstUser.email)
        assertThat(firstUser.password).isEqualTo(expectedFirstUser.password)

        val secondUser = users[1]
        assertThat(secondUser.id).isGreaterThan(0)
        assertThat(secondUser.uid).isEqualTo(expectedSecondUser.uid)
        assertThat(secondUser.email).isEqualTo(expectedSecondUser.email)
        assertThat(secondUser.password).isEqualTo(expectedSecondUser.password)
    }

    @Test
    fun `getById should return User`() {
        val expectedUser = firstUser()
        val userId = userRepo.add(firstUser()).id

        val user = userRepo.getById(userId!!)

        assertThat(user).isNotNull()

        assertThat(user!!.id).isEqualTo(userId)
        assertThat(user.uid).isEqualTo(expectedUser.uid)
        assertThat(user.email).isEqualTo(expectedUser.email)
        assertThat(user.password).isEqualTo(expectedUser.password)
    }

    private fun firstUser(): User {
        return User(null, FIRST_UID, FIRST_EMAIL, FIRST_PASSWORD)
    }

    private fun secondUser(): User {
        return User(null, SECOND_UID, SECOND_EMAIL, SECOND_PASSWORD)
    }

    companion object {

        private val FIRST_UID = UUID.randomUUID().toString()
        private const val FIRST_EMAIL = "first@email.com"
        private const val FIRST_PASSWORD = "abc123"

        private val SECOND_UID = UUID.randomUUID().toString()
        private const val SECOND_EMAIL = "second@email.com"
        private const val SECOND_PASSWORD = "qwerty"

    }

}