package com.example.projetoparciallista.activities

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projetoparciallista.databinding.ItemMenuBinding

// Adapter para a RecyclerView que exibe os itens do menu
class MenuAdapter(private val onClick: (String) -> Unit) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {
    private val menuItems = mutableListOf<MenuItem>()

    fun submitList(items: List<MenuItem>) {
        menuItems.clear()
        menuItems.addAll(items)
        notifyDataSetChanged()
    }

    // ViewHolder para cada item do menu
    class MenuViewHolder(private val binding: ItemMenuBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MenuItem, onClick: (String) -> Unit) {
            binding.tvName.text = item.name
            binding.tvDescription.text = item.description
            binding.tvPrice.text = "R$ ${item.price}"

            // Usando Glide para carregar a imagem do item
            Glide.with(binding.root.context).load(item.imageUrl).into(binding.ivImage)

            // Definindo o click para cada item
            binding.root.setOnClickListener {
                onClick(item.id)  // Passa o ID do item ao clicar
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(menuItems[position], onClick)
    }

    override fun getItemCount(): Int = menuItems.size
}
