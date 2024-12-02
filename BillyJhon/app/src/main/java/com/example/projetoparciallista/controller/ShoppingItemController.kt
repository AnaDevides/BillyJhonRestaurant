package com.example.projetoparciallista.controller

import com.example.projetoparciallista.models.ShoppingItem
import com.example.projetoparciallista.repository.ShoppingItemRepository

object ShoppingItemController {
    fun createItem(listId: Long, name: String, quantity: Int, unit: String, category: String) {
        ShoppingItemRepository.addItem(ShoppingItem(0, name, quantity, unit, category))
    }

    fun getItemsForList(listId: Long): List<ShoppingItem> {
        return ShoppingItemRepository.getItemsByList(listId)
    }

    fun deleteItem(item: ShoppingItem) {
        ShoppingItemRepository.removeItem(item)
    }
}