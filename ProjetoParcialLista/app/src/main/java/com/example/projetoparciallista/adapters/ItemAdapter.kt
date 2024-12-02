package com.example.projetoparciallista.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projetoparciallista.R
import com.example.projetoparciallista.activities.ItemListActivity
import com.example.projetoparciallista.models.Item
import com.example.projetoparciallista.models.ShoppingList

class ItemAdapter (private var itens: List<Item>, private val activity: ItemListActivity) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemName: TextView = itemView.findViewById(R.id.tvItemName)
        val itemQuantity: TextView = itemView.findViewById(R.id.tvItemQuantity)
        val itemCategory: TextView = itemView.findViewById(R.id.tvItemCategory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_lista, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itens[position]
        holder.itemName.text = item.nome
        holder.itemQuantity.text = "${item.quantidade} ${item.unidade}"
        holder.itemCategory.text = item.categoria

        holder.itemView.setOnClickListener {
            activity.deleteItem(position) // Chama o método deleteItem na atividade
        }

    }

    override fun getItemCount(): Int {
        return itens.size
    }

    fun updateList(newList:List<Item>) {
        itens = newList
        notifyDataSetChanged() // Notifica o adapter que os dados mudaram
    }
}