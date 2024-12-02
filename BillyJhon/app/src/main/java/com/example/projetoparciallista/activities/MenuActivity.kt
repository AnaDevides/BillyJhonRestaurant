package com.example.projetoparciallista.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projetoparciallista.MainActivity
import com.example.projetoparciallista.controller.AuthController
import com.example.projetoparciallista.databinding.ActivityLoginBinding
import com.example.projetoparciallista.databinding.ActivityMenuBinding
import com.example.projetoparciallista.utils.ValidationUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

data class MenuItem(
    var id: String = "",
    val name: String = "",
    val descriptionCompleted: String = "",
    val description: String = "",
    val price: Double = 0.0,
    val category: String = "",
    val imageUrl: String = ""
)

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private val menuAdapter = MenuAdapter { itemId ->
        openItemDetail(itemId)  // Passa o itemId para a função que abre os detalhes
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        // Configura a RecyclerView
        binding.recyclerViewMenu.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewMenu.adapter = menuAdapter
        binding.btnResume.setOnClickListener{
            startActivity(Intent(this, OrderSummaryActivity::class.java))
        }
        binding.btnSair.setOnClickListener{
            logout()
        }

        // Carrega os itens do cardápio
        loadMenu()
    }

    private fun loadMenu() {
        // Carrega os itens do Firestore
        db.collection("menuItems").get()
            .addOnSuccessListener { result ->
                val items = result.map { document ->
                    document.toObject(MenuItem::class.java).apply {
                        id = document.id // Pega o ID do item no Firestore
                    }
                }
                menuAdapter.submitList(items)
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Erro ao carregar o menu: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    // Função chamada ao clicar em um item do cardápio
    private fun openItemDetail(itemId: String) {
        // Inicia a activity de detalhes do item
        val intent = Intent(this, ItemDetailActivity::class.java)
        intent.putExtra("ITEM_ID", itemId)
        startActivity(intent)
    }

    private fun logout() {
        // Realiza o logout do usuário
        auth.signOut()

        // Após o logout, redireciona o usuário para a tela de login
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()  // Finaliza a atividade atual
    }
}