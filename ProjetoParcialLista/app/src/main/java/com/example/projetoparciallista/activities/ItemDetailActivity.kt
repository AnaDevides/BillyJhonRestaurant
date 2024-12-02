package com.example.projetoparciallista.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.projetoparciallista.databinding.ActivityItemDetailBinding
import com.google.firebase.firestore.FirebaseFirestore

class ItemDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityItemDetailBinding
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseFirestore.getInstance()

        // Recebe os dados do item
        val itemId = intent.getStringExtra("ITEM_ID")
        if (itemId != null) {
            loadItemDetails(itemId)
        } else {
            Toast.makeText(this, "Erro ao carregar item.", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.btnAddToCart.setOnClickListener {
            itemId?.let { id -> addToOrder(id) }
        }
    }

    private fun loadItemDetails(itemId: String) {
        db.collection("menuItems").document(itemId).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val item = document.toObject(MenuItem::class.java)
                    if (item != null) {
                        binding.tvName.text = item.name
                        binding.tvDescription.text = item.descriptionCompleted
                        binding.tvPrice.text = "R$ ${item.price}"
                        Glide.with(this).load(item.imageUrl).into(binding.ivItemImage)
                    }
                } else {
                    Toast.makeText(this, "Item não encontrado.", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Erro ao carregar item: ${e.message}", Toast.LENGTH_SHORT).show()
                finish()
            }
    }

    private fun addToOrder(itemId: String) {
        // Carrega os detalhes do item
        db.collection("menuItems").document(itemId).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val item = document.toObject(MenuItem::class.java)
                    if (item != null) {
                        // Cria um mapa com os dados do item
                        val orderItem = hashMapOf(
                            "itemId" to itemId,
                            "name" to item.name,
                            "description" to item.description,
                            "price" to item.price,
                            "imageUrl" to item.imageUrl,
                            "quantity" to 1
                        )

                        // Adiciona o pedido na coleção "orders"
                        db.collection("orders").add(orderItem)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Item adicionado ao pedido!", Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(this, "Erro ao adicionar item: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                    }
                } else {
                    Toast.makeText(this, "Item não encontrado.", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Erro ao carregar detalhes do item: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}