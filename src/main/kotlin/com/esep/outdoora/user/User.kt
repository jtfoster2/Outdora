package com.esep.outdoora.user

import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var email: String? = null
)

interface UserRepository: JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
}