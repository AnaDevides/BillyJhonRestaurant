package com.example.projetoparciallista.controller

import com.example.projetoparciallista.models.User
import com.example.projetoparciallista.repository.UserRepository

object AuthController {
    fun login(email: String, password: String): User? {
        val user = UserRepository.findUserByEmail(email)
        return if (user != null && user.password == password) {
            user
        } else {
            null
        }
    }

    fun register(name: String, email: String, password: String): Boolean {
        if (UserRepository.findUserByEmail(email) != null) return false
        UserRepository.addUser(User(0, name, email, password))
        return true
    }
}