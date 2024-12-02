package com.example.projetoparciallista.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projetoparciallista.R
import com.example.projetoparciallista.activities.ItemListActivity
import com.example.projetoparciallista.models.ShoppingList

class ShoppingListAdapter(private var listas: List<ShoppingList>, private val onListDelete: (Int) -> Unit) : RecyclerView.Adapter<ShoppingListAdapter.ListaViewHolder>() {

    class ListaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_shopping_list, parent, false)
        return ListaViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListaViewHolder, position: Int) {
        val lista = listas[position] // Obtém a ShoppingList pela posição

        holder.tvTitle.text = lista.title // Define o título da lista

        //Ao segurar na lista desejada ela sera deletada
        holder.itemView.setOnLongClickListener {
            onListDelete(position) // Chama o callback passando a posição
            true // Retorna true para indicar que o evento foi consumido
        }

        //Ao clicar na lista será aberta a lista de itens referentes a mesma
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, ItemListActivity::class.java)
            intent.putExtra("LISTA_NOME", lista.title)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return listas.size
    }

    fun updateList(newList:List<ShoppingList>) {
        listas = newList
        notifyDataSetChanged() // Notifica o adapter que os dados mudaram
    }




}