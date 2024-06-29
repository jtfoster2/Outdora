package com.esep.outdoora.oauth2

import com.esep.outdoora.user.User
import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository

@Entity
@Table(name = "provider_details")
data class ProviderDetails(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User? = null,

    @Column(nullable = false)
    var providerName: String? = null,

    @Column(nullable = false)
    var providerUserId: String? = null,
)

interface ProviderDetailsRepository: JpaRepository<ProviderDetails, Long> {
    fun findByUserId(userId: Long): ProviderDetails?
    fun findByProviderUserId(providerUserId: String): ProviderDetails?
}