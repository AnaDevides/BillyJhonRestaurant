package com.example.projetoparciallista.repository

import com.example.projetoparciallista.models.ShoppingList

object ShoppingListRepository {
    private val shoppingLists = mutableListOf<ShoppingList>()
    private var currentId = 1L

    fun addList(list: ShoppingList) {
        shoppingLists.add(list.copy(id = currentId++))
    }

    fun getLists(): List<ShoppingList> {
        return shoppingLists.sortedBy { it.title }
    }

    fun removeList(list: ShoppingList) {
        shoppingLists.remove(list)
    }
}