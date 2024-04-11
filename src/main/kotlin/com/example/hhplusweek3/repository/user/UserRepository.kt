package com.example.hhplusweek3.repository.user

import com.example.hhplusweek3.domain.user.User
import com.example.hhplusweek3.domain.user.UserCreateObject
import com.example.hhplusweek3.entity.user.UserEntity
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class UserRepository(
    private val userJpaRepository: UserJpaRepository
) {
    fun save(userCreateObject: UserCreateObject): User {
        return userJpaRepository.save(UserEntity.fromCreateObject(userCreateObject)).toDomain()
    }

    fun findById(id: UUID): User? {
        return userJpaRepository.findById(id).orElse(null)?.toDomain()
    }

    fun update(user: User): User {
        return userJpaRepository.save(user.toEntity()).toDomain()
    }
}
