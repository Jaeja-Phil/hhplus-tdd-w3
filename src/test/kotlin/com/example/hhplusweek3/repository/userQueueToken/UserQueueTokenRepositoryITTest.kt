package com.example.hhplusweek3.repository.userQueueToken

import com.example.hhplusweek3.domain.userQueueToken.UserQueueTokenCreateObject
import com.example.hhplusweek3.entity.user.UserEntity
import com.example.hhplusweek3.repository.user.UserJpaRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserQueueTokenRepositoryITTest(
    @Autowired private val userJpaRepository: UserJpaRepository,
    @Autowired private val userQueueTokenJpaRepository: UserQueueTokenJpaRepository
){
    private val userQueueTokenRepository: UserQueueTokenRepository by lazy {
        UserQueueTokenRepository(userQueueTokenJpaRepository)
    }


    @Test
    fun `findByUserId - should return UserQueueToken list if found`() {
        // Given
        val userEntity = userJpaRepository.save(UserEntity(balance = 100.0))
        val userQueueToken = userQueueTokenRepository.save(UserQueueTokenCreateObject(user = userEntity, token = "token"))
        val userId = userEntity.id!!

        // When
        val result = userQueueTokenRepository.findByUserId(userId)

        // Then
        assertEquals(1, result.size)
        assertEquals(userQueueToken.id, result.first().id)
    }
}