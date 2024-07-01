package com.esep.outdoora

import jakarta.persistence.*


@Entity
@Table(name = "users")
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Long? = null

    @Column(nullable = false)
    private var name: String? = null

    @Column(nullable = false, unique = true)
    private var email: String? = null
}