package com.example.hhplusweek3.entity.userQueueToken

import com.example.hhplusweek3.domain.userQueueToken.UserQueueToken
import com.example.hhplusweek3.entity.user.UserEntity
import jakarta.persistence.*

enum class UserQueueTokenStatus {
    IN_QUEUE,
    IN_PROGRESS,
    EXPIRED,
}

@Entity
@Table(name = "user_queue_tokens")
data class UserQueueTokenEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    @ManyToOne
    val user: UserEntity,
    val token: String,
    val status: UserQueueTokenStatus = UserQueueTokenStatus.IN_QUEUE,
) {
    fun toDomain(): UserQueueToken {
        return UserQueueToken(
            id = requireNotNull(id),
            user = user.toDomain(),
            token = token,
            status = status,
        )
    }
}
