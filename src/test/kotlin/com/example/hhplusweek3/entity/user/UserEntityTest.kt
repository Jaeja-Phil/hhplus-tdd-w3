package com.example.hhplusweek3.entity.user

import com.example.hhplusweek3.domain.user.UserCreateObject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class UserEntityTest {
    @Test
    fun `toDomain - should raise IllegalArgumentException when id is null`() {
        // Given
        val userEntity = UserEntity(
            id = null,
            balance = 100.0,
        )

        // When & Then
        assertThrows<IllegalArgumentException> {
            userEntity.toDomain()
        }
    }

    @Test
    fun `toDomain - should return User`() {
        // Given
        val userEntity = UserEntity(
            id = UUID.randomUUID(),
            balance = 100.0,
        )

        // When
        val result = userEntity.toDomain()

        // Then
        assertEquals(userEntity.id, result.id)
        assertEquals(userEntity.balance, result.balance)
    }

    @Test
    fun `fromCreateObject - should return UserEntity with null id`() {
        // Given
        val userCreateObject = UserCreateObject(
            balance = 100.0,
        )

        // When
        val userEntity = UserEntity.fromCreateObject(userCreateObject)

        // Then
        assertNull(userEntity.id)
        assertEquals(userCreateObject.balance, userEntity.balance)
    }
}