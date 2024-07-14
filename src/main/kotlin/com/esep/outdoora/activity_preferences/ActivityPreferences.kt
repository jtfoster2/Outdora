package com.esep.outdoora.activity_preferences

import com.esep.outdoora.user.User
import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository

@Entity
@Table(name = "activity_preferences")
data class ActivityPreferences(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    var activityList: List<Activity>,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User
)

interface ActivityPreferencesRepository: JpaRepository<ActivityPreferences, Long> {
    fun findByUserId(userId: Long): ActivityPreferences?
}