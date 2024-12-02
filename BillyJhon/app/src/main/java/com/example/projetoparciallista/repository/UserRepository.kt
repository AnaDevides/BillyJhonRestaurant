package com.example.projetoparciallista.repository

import com.example.projetoparciallista.models.User

object UserRepository {
    private val users = mutableListOf<User>()

    fun addUser(user: User) {
        users.add(user)
    }

    fun findUserByEmail(email: String): User? {
        return users.find { it.email == email }
    }
}