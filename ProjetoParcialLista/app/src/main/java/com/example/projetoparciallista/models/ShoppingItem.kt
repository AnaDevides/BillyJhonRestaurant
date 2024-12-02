package com.example.projetoparciallista.models

data class ShoppingItem(
    val id: Long,
    var name: String,
    var quantity: Int,
    var unit: String,
    var category: String,
    var isPurchased: Boolean = false
)