package com.example.projetoparciallista.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projetoparciallista.R

class AdicionarItemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        val btnAdicionar: Button = findViewById(R.id.btnAdicionar)
        btnAdicionar.setOnClickListener {
            val nomeItem = findViewById<EditText>(R.id.etNomeItem).text.toString()
            val quantidade = findViewById<EditText>(R.id.etQuantidade).text.toString().toIntOrNull() ?: 1
            val unidade = findViewById<EditText>(R.id.etUnidade).text.toString()
            val categoria = findViewById<Spinner>(R.id.spCategoria).selectedItem.toString()

            if (nomeItem.isNotBlank()) {
                val intent = Intent()
                intent.putExtra("itemNome", nomeItem)
                intent.putExtra("quantidade", quantidade)
                intent.putExtra("unidade", unidade)
                intent.putExtra("categoria", categoria)
                setResult(Activity.RESULT_OK, intent) // Retorna o resultado
                finish() // Fecha a activity
            } else {
                Toast.makeText(this, "Por favor, insira o nome do item.", Toast.LENGTH_SHORT).show()
            }
        }
    }


}