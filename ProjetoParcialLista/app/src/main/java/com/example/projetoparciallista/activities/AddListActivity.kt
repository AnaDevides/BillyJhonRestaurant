package com.example.projetoparciallista.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projetoparciallista.R

class AddListActivity : AppCompatActivity(){

    private lateinit var etListName: EditText
    private lateinit var btnAddList: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_list)

        val btnAdicionar: Button = findViewById(R.id.btnAddList)
        btnAdicionar.setOnClickListener {
            val nomeLista = findViewById<EditText>(R.id.etListName).text.toString()
            if (nomeLista.isNotBlank()) {
                val intent = Intent()
                intent.putExtra("nomeLista", nomeLista)
                setResult(Activity.RESULT_OK, intent) // Retorna o resultado para MainActivity
                finish() // Fecha a activity atual
            } else {
                Toast.makeText(this, "Por favor, insira um nome para a lista.", Toast.LENGTH_SHORT).show()
            }
        }
    }

}