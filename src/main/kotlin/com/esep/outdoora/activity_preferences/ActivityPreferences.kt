package com.esep.outdoora.activity_preferences

import com.esep.outdoora.user.User
import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository

// Name of the activity being defined on a user's profile
enum class AdventureName { SKIING, BACKPACKING, TRAVEL, HIKING, HOLIDATE }

// Self-declared skill level in a certain activityName
enum class SkillLevel { BEGINNER, INTERMEDIATE, ADVANCED }

// Preferences for specific aspects of the adventure
enum class Attitude { IMMERSION, RELAXATION, CHALLENGE, EXERCISE }

// A single activity with the adventureName, the set skillLevel of the user, and their attitude on it.
data class Activity(val adventureName: AdventureName, val skillLevel: SkillLevel, val attitude: Attitude)

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