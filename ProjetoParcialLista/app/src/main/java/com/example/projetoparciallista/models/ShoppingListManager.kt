package com.example.projetoparciallista.models

object ShoppingListManager {
    private val shoppingLists: MutableList<ShoppingList> = mutableListOf()

    fun addShoppingList(shoppingList: ShoppingList) {
        shoppingLists.add(shoppingList)
    }

    fun removeShoppingList(shoppingList: ShoppingList) {
        shoppingLists.remove(shoppingList)
    }

    fun getShoppingLists(): List<ShoppingList> {
        return shoppingLists
    }

    fun getItemsForList(listTitle: String): MutableList<Item> {
        val list = shoppingLists.find { it.title == listTitle }
        return list?.itens ?: mutableListOf() // Retorna a lista de itens ou uma lista vazia
    }

    fun addItemToList(listTitle: String, item: Item) {
        val list = shoppingLists.find { it.title == listTitle }
        list?.itens?.add(item) // Adiciona o item à lista de itens da lista de compras
    }

    fun removeItemFromList(listTitle: String, item: Item) {
        val list = shoppingLists.find { it.title == listTitle }
        list?.itens?.remove(item) // Remove o item da lista de itens
    }

    fun clearLists() {
        shoppingLists.clear()
    }

}