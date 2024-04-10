package com.example.hhplusweek3.repository.user

import com.example.hhplusweek3.entity.user.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserJpaRepository : JpaRepository<UserEntity, UUID>
