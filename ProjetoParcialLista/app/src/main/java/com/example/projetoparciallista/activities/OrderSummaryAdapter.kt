package com.example.projetoparciallista.activities

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projetoparciallista.databinding.ItemOrderBinding

class OrderSummaryAdapter(
    private val onAction: (OrderItem, String) -> Unit
) : RecyclerView.Adapter<OrderSummaryAdapter.OrderViewHolder>() {

    private val orderItems = mutableListOf<OrderItem>()

    fun submitList(items: List<OrderItem>) {
        orderItems.clear()
        orderItems.addAll(items)
        notifyDataSetChanged()
    }

    class OrderViewHolder(private val binding: ItemOrderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: OrderItem, onAction: (OrderItem, String) -> Unit) {
            binding.tvName.text = item.name
            binding.tvQuantity.text = "Quantidade: ${item.quantity}"
            binding.tvPrice.text = "R$ ${item.price}"
            binding.tvTotalPrice.text = "Total: R$ ${item.quantity * item.price}"

            binding.btnIncrease.setOnClickListener { onAction(item, "increment") }
            binding.btnDecrease.setOnClickListener { onAction(item, "decrement") }
            binding.btnRemove.setOnClickListener { onAction(item, "remove") }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(orderItems[position], onAction)
    }

    override fun getItemCount(): Int = orderItems.size
}