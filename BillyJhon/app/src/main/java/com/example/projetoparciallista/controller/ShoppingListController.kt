package com.example.projetoparciallista.controller

import com.example.projetoparciallista.models.ShoppingList
import com.example.projetoparciallista.repository.ShoppingItemRepository
import com.example.projetoparciallista.repository.ShoppingListRepository

object ShoppingListController {
    fun createList(title: String, imageUri: String? = null) {
        ShoppingListRepository.addList(ShoppingList(0, title, imageUri))
    }

    fun getShoppingLists(): List<ShoppingList> {
        return ShoppingListRepository.getLists()
    }

    fun deleteList(list: ShoppingList) {
        ShoppingListRepository.removeList(list)
        ShoppingItemRepository.getItemsByList(list.id).forEach {
            ShoppingItemRepository.removeItem(it)
        }
    }
}