package com.esep.outdoora.profile

import com.esep.outdoora.activity_preferences.ActivityRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProfileDeletionService(
    private val profileRepository: ProfileRepository,
    private val activityRepository: ActivityRepository,
) {

    @Transactional
    fun deleteProfile(userId: Long): Boolean {
        val profile = profileRepository.findByUserId(userId)
        val activityPreferences = activityRepository.findByUserId(userId)

        activityPreferences?.let {
            activityRepository.delete(it)
        }
        profile?.let {
            profileRepository.delete(it)
        }
        return true
    }
}
