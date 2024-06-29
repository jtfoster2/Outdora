package com.esep.outdoora.user

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    fun createNewUser(email: String): User {
        val user = userRepository.findByEmail(email) ?: User(
            email = email,
        )
        return userRepository.save(user)
    }
}