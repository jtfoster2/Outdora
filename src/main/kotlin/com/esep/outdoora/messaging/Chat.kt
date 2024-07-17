package com.esep.outdoora.messaging

import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository

@Entity
@Table(name = "chat")
data class Chat(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "user_a_id", nullable = false)
    val userAId: Long,

    @Column(name = "user_b_id", nullable = false)
    val userBId: Long,

    @Lob
    @Column(nullable = false)
    var chat: String,
)

interface ChatRepository: JpaRepository<Chat, Long> {
    fun findByUserAIdAndUserBId(userAId: Long, userBId: Long): Chat?
}

fun ChatRepository.findChat(userAId: Long, userBId: Long): Chat? {
    return this.findByUserAIdAndUserBId(userAId, userBId) ?: this.findByUserAIdAndUserBId(userBId, userAId)
}