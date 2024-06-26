package com.example.hhplusweek3.repository.user

import com.example.hhplusweek3.domain.user.UserCreateObject
import com.example.hhplusweek3.entity.user.UserEntity
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

class UserRepositoryTest {
    private val userJpaRepository = mockk<UserJpaRepository>()
    private val userRepository = UserRepository(userJpaRepository)

    private fun createUserEntityWithId(balance: Double): UserEntity {
        return UserEntity(id = UUID.randomUUID(), balance = balance)
    }

    private fun createUserEntityWithoutId(balance: Double): UserEntity {
        return UserEntity(balance = balance)
    }

    @Test
    fun `save - should save with passed UserCreateObject`() {
        // Given
        val userCreateObject = UserCreateObject(balance = 100.0)
        val userEntity = createUserEntityWithoutId(userCreateObject.balance)
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
        val userEntity = createUserEntityWithId(100.0)
        val id = userEntity.id!!
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

    @Test
    fun `update - should save with passed User`() {
        // Given
        val user = createUserEntityWithId(100.0)
        val updatedUser = user.copy()
        every { userJpaRepository.save(user) } returns updatedUser

        // When
        val result = userRepository.update(updatedUser.toDomain())

        // Then
        assertEquals(updatedUser.id, result.id)
        assertEquals(updatedUser.balance, result.balance)
    }
}