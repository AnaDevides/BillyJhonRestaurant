package com.example.projetoparciallista.activities

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projetoparciallista.R
import com.example.projetoparciallista.adapters.ShoppingListAdapter
import com.example.projetoparciallista.models.ShoppingList
import com.example.projetoparciallista.models.ShoppingListManager
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ShoppingListActivity  : AppCompatActivity(){

    private lateinit var rvListas: RecyclerView
    private lateinit var fab: FloatingActionButton
    private lateinit var searchView: SearchView
    private lateinit var adapter: ShoppingListAdapter
    private lateinit var logoutButton: Button
    private val listas: MutableList<ShoppingList> = ShoppingListManager.getShoppingLists().toMutableList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_list)

        // Inicializando os componentes
        rvListas = findViewById(R.id.rvListas)
        fab = findViewById(R.id.fab)
        searchView = findViewById(R.id.searchView)
        logoutButton = findViewById(R.id.logoutButton)
        setupRecyclerView()
        setupSearchView()
        setupLogoutButton()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_ADICIONAR_LISTA && resultCode == Activity.RESULT_OK) {
            val nomeLista = data?.getStringExtra("nomeLista")
            nomeLista?.let {
                // Cria uma nova ShoppingList e adiciona na lista global através do ShoppingListManager
                val novaLista = ShoppingList(id = System.currentTimeMillis(), title = it)
                ShoppingListManager.addShoppingList(novaLista)
                adapter.updateList(ShoppingListManager.getShoppingLists())
            }
        }
    }

    private fun setupRecyclerView() {
        val sortedLists = listas.sortedBy{ it.title }
        adapter = ShoppingListAdapter(sortedLists) { position ->
            deleteList(position)
        }
        rvListas.layoutManager = GridLayoutManager(this, 2)
        rvListas.adapter = adapter

        // Ação do botão flutuante
        fab.setOnClickListener {
            val intent = Intent(this, AddListActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_ADICIONAR_LISTA)
        }
    }

    private fun setupSearchView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Filtra a lista conforme o texto da barra de busca
                val filteredList = ShoppingListManager.getShoppingLists().filter { it.title.contains(newText ?: "", ignoreCase = true) }.sortedBy { it.title }
                adapter.updateList(filteredList)
                return true
            }
        })
    }

    companion object {
        private const val REQUEST_CODE_ADICIONAR_LISTA = 1
    }

    private fun deleteList(position: Int) {
        AlertDialog.Builder(this)
            .setTitle("Excluir Lista")
            .setMessage("Você tem certeza que deseja excluir a lista ${ShoppingListManager.getShoppingLists()[position].title}?")
            .setPositiveButton("Excluir") { _, _ ->
                val listaRemovida = ShoppingListManager.getShoppingLists()[position]
                ShoppingListManager.removeShoppingList(listaRemovida)

                // Atualiza o adapter com a nova lista após remoção
                adapter.updateList(ShoppingListManager.getShoppingLists())
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun setupLogoutButton() {
        logoutButton.setOnClickListener {
            // Lógica de logout, como limpar dados de sessão
            ShoppingListManager.clearLists() // Limpa as listas, se necessário
            startActivity(Intent(this, LoginActivity::class.java)) // Redireciona para a tela de login
            finish() // Finaliza a atividade atual
        }
    }

}