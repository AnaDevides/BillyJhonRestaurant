package com.example.projetoparciallista.repository

import com.example.projetoparciallista.models.ShoppingItem

object ShoppingItemRepository {
    private val items = mutableListOf<ShoppingItem>()
    private var currentId = 1L

    fun addItem(item: ShoppingItem) {
        items.add(item.copy(id = currentId++))
    }

    fun getItemsByList(listId: Long): List<ShoppingItem> {
        return items.filter { it.id == listId } // Adicione lógica para associar itens às listas
    }

    fun removeItem(item: ShoppingItem) {
        items.remove(item)
    }
}