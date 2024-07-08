package com.esep.outdoora.TwoFactorAuth


import com.twilio.Twilio
import com.twilio.rest.api.v2010.account.Message
import com.twilio.type.PhoneNumber
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Service


@Service
class SmsService {

    val ACCOUNT_SID: String = "YOUR_ACCOUNT_SID"
    val AUTH_TOKEN: String = "YOUR_AUTH_TOKEN"
    val FROM_NUMBER: String = "YOUR_TWILIO_PHONE_NUMBER"


    @PostConstruct
    fun init() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN)
    }

    fun sendSms(to: String?, body: String?) {
        Message.creator(PhoneNumber(to), PhoneNumber(FROM_NUMBER), body).create()
    }

}