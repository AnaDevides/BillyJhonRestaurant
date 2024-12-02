package com.example.projetoparciallista.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projetoparciallista.databinding.ActivityOrderSummaryBinding
import com.google.firebase.firestore.FirebaseFirestore

data class OrderItem(
    var id: String = "",
    val itemId: String = "",
    val name: String = "",
    val price: Double = 0.0,
    var quantity: Int = 1
)

class OrderSummaryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderSummaryBinding
    private lateinit var db: FirebaseFirestore
    private val orderAdapter = OrderSummaryAdapter { item, action ->
        modifyOrder(item, action)
    }

    private var totalAmount = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderSummaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseFirestore.getInstance()

        binding.recyclerViewOrder.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewOrder.adapter = orderAdapter

        loadOrderItems()

        binding.btnConfirmOrder.setOnClickListener {
            confirmOrder()
        }
    }

    private fun loadOrderItems() {
        db.collection("orders").get()
            .addOnSuccessListener { result ->
                val items = result.map { doc ->
                    val orderItem = doc.toObject(OrderItem::class.java)
                    orderItem.id = doc.id // Guarda o ID do documento
                    orderItem
                }
                orderAdapter.submitList(items)
                updateTotal(items)
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Erro ao carregar pedido: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun modifyOrder(item: OrderItem, action: String) {
        when (action) {
            "increment" -> item.quantity++
            "decrement" -> if (item.quantity > 1) item.quantity--
            "remove" -> {
                db.collection("orders").document(item.id).delete()
                    .addOnSuccessListener {
                        Toast.makeText(this, "Item removido!", Toast.LENGTH_SHORT).show()
                        loadOrderItems()
                    }
                return
            }
        }

        db.collection("orders").document(item.id)
            .update("quantity", item.quantity)
            .addOnSuccessListener {
                loadOrderItems()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Erro ao atualizar pedido: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun updateTotal(items: List<OrderItem>) {
        totalAmount = items.sumOf { it.quantity * it.price }
        binding.tvTotalAmount.text = "Total: R$ %.2f".format(totalAmount)
    }

    private fun confirmOrder() {
        Toast.makeText(this, "Pedido confirmado! Total: R$ %.2f".format(totalAmount), Toast.LENGTH_SHORT).show()
        db.collection("orders").get().addOnSuccessListener { result ->
            for (doc in result) {
                db.collection("orders").document(doc.id).delete()
            }
            finish()
        }
    }
}