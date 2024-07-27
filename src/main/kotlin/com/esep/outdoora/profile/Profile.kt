package com.esep.outdoora.profile

import com.esep.outdoora.user.User
import jakarta.persistence.*
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


    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,


    @ElementCollection
    @CollectionTable(name = "likes", joinColumns = [JoinColumn(name = "profile_id")])
    val likes: MutableSet<Long?> = mutableSetOf()
)

interface ProfileRepository : JpaRepository<Profile, Long> {
    fun findByUserId(userId: Long): Profile?
}