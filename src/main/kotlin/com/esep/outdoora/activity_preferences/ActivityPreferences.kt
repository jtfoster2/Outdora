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
    @Enumerated(EnumType.STRING)
    var skiingSkillLevel: SkillLevel,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var skiingAttitude: Attitude,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var backpackingSkillLevel: SkillLevel,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var backpackingAttitude: Attitude,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var travelSkillLevel: SkillLevel,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var travelAttitude: Attitude,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var hikingSkillLevel: SkillLevel,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var hikingAttitude: Attitude,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var holidateSkillLevel: SkillLevel,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var holidateAttitude: Attitude,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User
)

// Self-declared skill level in a certain activityName
enum class SkillLevel { BEGINNER, INTERMEDIATE, ADVANCED }

// Preferences for specific aspects of the adventure
enum class Attitude { IMMERSION, RELAXATION, CHALLENGE, EXERCISE }

interface ActivityPreferencesRepository: JpaRepository<ActivityPreferences, Long> {
    fun findByUserId(userId: Long): ActivityPreferences?
}

// @Enumerated(EnumType.STRING) // This tells JPA to store the enum as a string
// var status: ArticleStatus