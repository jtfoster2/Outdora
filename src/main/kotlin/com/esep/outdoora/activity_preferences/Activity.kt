package com.esep.outdoora.activity_preferences

import com.esep.outdoora.user.User
import jakarta.persistence.*

// A single activity with the adventureName, the set skillLevel of the user, and their attitude on it.
@Entity
@Table(name = "activities")
data class Activity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Enumerated(EnumType.STRING)
    val adventureName: AdventureName,

    @Enumerated(EnumType.STRING)
    val skillLevel: SkillLevel,

    @Enumerated(EnumType.STRING)
    val attitude: Attitude,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User
) {
    // Name of the activity being defined on a user's profile
    enum class AdventureName { SKIING, BACKPACKING, TRAVEL, HIKING, HOLIDATE }

    // Self-declared skill level in a certain activityName
    enum class SkillLevel { BEGINNER, INTERMEDIATE, ADVANCED }

    // Preferences for specific aspects of the adventure
    enum class Attitude { IMMERSION, RELAXATION, CHALLENGE, EXERCISE }
}