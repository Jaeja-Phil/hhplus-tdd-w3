package com.example.hhplusweek3.repository.user

import com.example.hhplusweek3.domain.user.UserCreateObject
import com.example.hhplusweek3.entity.user.UserEntity
import com.example.hhplusweek3.entity.user.UserEntityFactory
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

class UserRepositoryTest {
    private val userJpaRepository = mockk<UserJpaRepository>()
    private val userRepository = UserRepository(userJpaRepository)
    private val userEntityFactory = mockk<UserEntityFactory>()

    @Test
    fun `save - should save with passed UserCreateObject`() {
        // Given
        val userCreateObject = UserCreateObject(balance = 100.0)
        val userEntity = UserEntity(balance = userCreateObject.balance)
        every { userEntityFactory.fromCreateObject(userCreateObject) } returns userEntity
        val savedUserEntity = userEntity.copy(id = UUID.randomUUID())
        every { userJpaRepository.save(userEntity) } returns savedUserEntity

        // When
        val result = userRepository.save(userCreateObject)

        // Then
        assertEquals(savedUserEntity.id, result.id)
        assertEquals(savedUserEntity.balance, result.balance)
    }

    @Test
    fun `findById - should return User if found`() {
        // Given
        val id = UUID.randomUUID()
        val userEntity = UserEntity(id = id, balance = 100.0)
        every { userJpaRepository.findById(id) } returns Optional.of(userEntity)

        // When
        val result = userRepository.findById(id)

        // Then
        assertEquals(userEntity.id, result?.id)
        assertEquals(userEntity.balance, result?.balance)
    }

    @Test
    fun `findById - should return null if not found`() {
        // Given
        val id = UUID.randomUUID()
        every { userJpaRepository.findById(id) } returns Optional.empty()

        // When
        val result = userRepository.findById(id)

        // Then
        assertEquals(null, result)
    }
}