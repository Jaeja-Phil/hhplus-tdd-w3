package com.example.hhplusweek3.repository.user

import com.example.hhplusweek3.domain.user.UserCreateObject
import com.example.hhplusweek3.entity.user.UserEntity
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class UserRepository(
    private val userJpaRepository: UserJpaRepository
) {
    fun save(userCreateObject: UserCreateObject): UserEntity {
        return userJpaRepository.save(
            UserEntity.fromCreateObject(userCreateObject)
        )
    }

    fun findById(id: UUID): UserEntity? {
        return userJpaRepository.findById(id).orElse(null)
    }
}
