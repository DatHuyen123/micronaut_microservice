package com.dirox.entity

import javax.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: Long = 0,
    @Column(name = "username", nullable = false) var userName: String = "",
    @Column(name = "password", nullable = false) var password: String = "",
    @Column(name = "age", nullable = false) var age: Int = 0,
    @Column(name = "role", nullable = false) var role: String = ""
)
