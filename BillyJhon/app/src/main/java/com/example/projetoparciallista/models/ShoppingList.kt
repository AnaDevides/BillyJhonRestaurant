package com.example.projetoparciallista.models

data class ShoppingList(
    val id: Long,
    var title: String,
    var imageUri: String? = null,
    val itens: MutableList<Item> = mutableListOf()
)