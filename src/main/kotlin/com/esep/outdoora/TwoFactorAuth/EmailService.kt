package com.esep.outdoora.TwoFactorAuth

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.stereotype.Service


@Service
class EmailService(@Autowired val mailSender: JavaMailSender) {

    fun sendEmail(receiverEmail: String, subject: String, body: String) {
        val message = SimpleMailMessage()
        message.setTo(receiverEmail)
        message.subject = subject
        message.text = body
        mailSender.send(message)
    }
}

@Bean
fun getJavaMailSender(): JavaMailSender {
    val mailSender = JavaMailSenderImpl()
    mailSender.host = "smtp.gmail.com"
    mailSender.port = 587

    mailSender.username = "my.gmail@gmail.com"
    mailSender.password = "password"

    val props = mailSender.javaMailProperties
    props["mail.transport.protocol"] = "smtp"
    props["mail.smtp.auth"] = "true"
    props["mail.smtp.starttls.enable"] = "true"
    props["mail.debug"] = "true"

    return mailSender
}