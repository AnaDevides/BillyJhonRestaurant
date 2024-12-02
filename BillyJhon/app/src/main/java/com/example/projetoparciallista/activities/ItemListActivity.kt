package com.example.projetoparciallista.activities

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projetoparciallista.R
import com.example.projetoparciallista.adapters.ItemAdapter
import com.example.projetoparciallista.models.Item
import com.example.projetoparciallista.models.ShoppingListManager
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ItemListActivity : AppCompatActivity() {

    private lateinit var adapter: ItemAdapter
    private lateinit var itens: MutableList<Item>
    private lateinit var listaNome: String
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        // Configurar o título da lista
        listaNome = intent.getStringExtra("LISTA_NOME") ?: "Lista"
        val textView = findViewById<TextView>(R.id.textViewListaNome)
        textView.text = listaNome
        searchView = findViewById(R.id.searchView)

        // Carregar itens da lista de compras
        itens = ShoppingListManager.getItemsForList(listaNome ?: "").toMutableList()

        // Configurar RecyclerView
        setupRecyclerView()
        setupSearchView(listaNome)

        // Configurar FAB para adicionar itens
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this, AdicionarItemActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_ADICIONAR_ITEM)
        }
    }

    private fun setupRecyclerView() {
        val recyclerView: RecyclerView = findViewById(R.id.rvItens)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val sortedItems = itens.sortedBy { it.nome }

        adapter = ItemAdapter(sortedItems, this)
        recyclerView.adapter = adapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_ADICIONAR_ITEM && resultCode == Activity.RESULT_OK) {
            val itemNome = data?.getStringExtra("itemNome")
            val quantidade = data?.getIntExtra("quantidade", 1)
            val unidade = data?.getStringExtra("unidade")
            val categoria = data?.getStringExtra("categoria")

            val item = Item(itemNome ?: "Item", quantidade ?: 1, unidade ?: "un", categoria ?: "Outros")
            itens.add(item)

            ShoppingListManager.addItemToList(listaNome ?: "", item) // Adiciona o item à lista global
            adapter.updateList(ShoppingListManager.getItemsForList(listaNome))
        }
    }

    fun deleteItem(position: Int) {
        // Obtenha a lista atual de itens a partir do manager
        val itensAtualizados = ShoppingListManager.getItemsForList(listaNome)

        if (position < 0 || position >= itensAtualizados.size) {
            Log.e("ItemListActivity", "Índice inválido: $position")
            return // Saia do método se o índice for inválido
        }

        val item = itensAtualizados[position]

        // Exibir um diálogo de confirmação antes de excluir
        AlertDialog.Builder(this)
            .setTitle("Excluir Item")
            .setMessage("Você tem certeza que deseja excluir ${item.nome}?")
            .setPositiveButton("Excluir") { _, _ ->
                // Remove o item da lista global
                ShoppingListManager.removeItemFromList(listaNome, item)

                // Notifica o adaptador da remoção
                adapter.updateList(ShoppingListManager.getItemsForList(listaNome))
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    companion object {
        private const val REQUEST_CODE_ADICIONAR_ITEM = 1
    }

    private fun setupSearchView(listTitle: String) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Obtém a lista de itens para a lista de compras específica
                val itemList = ShoppingListManager.getItemsForList(listTitle)

                // Filtra a lista conforme o texto da barra de busca
                val filteredList = itemList.filter {
                    it.nome.contains(newText ?: "", ignoreCase = true)
                }.sortedBy { it.nome }

                adapter.updateList(filteredList)
                return true
            }
        })
    }

}