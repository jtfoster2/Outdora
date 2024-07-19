package com.esep.outdoora.recommendationengine

import com.esep.outdoora.activity_preferences.ActivityPreferencesRepository
import com.esep.outdoora.profile.Profile
import com.esep.outdoora.profile.ProfileRepository
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel
import org.apache.mahout.cf.taste.model.DataModel
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.File


class RecommendEngine(
    private val profileRepository: ProfileRepository,
    private val activityPreferences: ActivityPreferencesRepository
) {
    val dataModel: DataModel by lazy { FileDataModel(createDateTempFile()) }

    fun basicRecommendProfileTo(userId: Long): List<Profile> {
        val currentProfile = profileRepository.findByUserId(userId)
        val activityPreference = activityPreferences.findByUserId(userId)
        val profiles = profileRepository.findAll()
        val recommendations = mutableListOf<Profile>()
        for (profile in profiles) {
            val profilePreferences = activityPreferences.findByUserId(profile.user.id!!)
            if (profilePreferences != null)
                if (activityPreference?.hikingAttitude == profilePreferences.hikingAttitude

                    || activityPreference?.travelAttitude == profilePreferences.travelAttitude

                    || activityPreference?.skiingAttitude == profilePreferences.skiingAttitude
                ) {
                    recommendations.add(profile)
                }

        }

        return recommendations
    }

    fun recommendProfilesTo(profile: Profile): List<Profile> {
//        val similarity = CityBlockSimilarity(dataModel)
//        val neighborhood: UserNeighborhood = ThresholdUserNeighborhood(0.1, similarity, model)
//        val recommender: UserBasedRecommender = GenericUserBasedRecommender(model, neighborhood, similarity)
//        val recommendations = profile.id?.let { recommender.recommend(it, 2) }
//
//        for (recommendation in recommendations) {
//            println("Recommendation: " + recommendation)
//
//        }
        return profileRepository.findAll()
    }

    fun createDateTempFile(): File {
        val tempDir = System.getProperty("com.esep.outdoora.recommendationengine")
        val tempFile = File.createTempFile("user_data", ".csv", File(tempDir))
        tempFile.deleteOnExit()
        println("Custom temporary file created at: ${tempFile.absolutePath}")

        tempFile.writeText("Custom Data")
        return tempFile
    }

}

@Configuration
class EnginConfig {
    @Bean
    fun recommendEngine(
        profileRepository: ProfileRepository,
        activityPreferences: ActivityPreferencesRepository
    ): RecommendEngine {
        return RecommendEngine(profileRepository, activityPreferences)
    }


}
