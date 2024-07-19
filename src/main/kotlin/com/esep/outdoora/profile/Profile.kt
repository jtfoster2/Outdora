package com.esep.outdoora.profile

import com.esep.outdoora.oauth2.ProviderDetails
import com.esep.outdoora.user.User
import jakarta.persistence.*
import org.hibernate.annotations.Type
import org.hibernate.type.descriptor.jdbc.BinaryJdbcType
import org.springframework.data.jpa.repository.JpaRepository

@Entity
@Table(name = "profile")
data class Profile(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var age: Int,

    @Column
    var description: String? = null,

    @Column
    var image: ByteArray? = null,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User
)

interface ProfileRepository: JpaRepository<Profile, Long> {
    fun findByUserId(userId: Long): Profile?
}