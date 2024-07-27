package com.esep.outdoora.TwoFactorAuth

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class TwoFactorAuthenticationService(@Autowired val smsService: SmsService) {

    fun generateVerificationCode(): String {
        return java.lang.String.format("%06d", Random.nextInt(999999))
    }

    fun sendVerificationCodeBySms(phoneNumber: String?) {
        val code = generateVerificationCode()
        smsService.sendSms(phoneNumber, "Your Outdora verification code is: $code")

    }

    fun verifyCode(phoneNumber: String?, inputCode: String, actualCode: String): Boolean {
        return actualCode == inputCode
    }


}