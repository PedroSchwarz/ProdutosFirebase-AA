package com.example.projetocrudprodutos

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.example.projetocrudprodutos.model.Produto

class CadastroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)
    }

    fun cadastrarAluno(view: View?) {
        val textoIdCadastro = findViewById(R.id.textoIdCadastro) as EditText
        val textoNomeCadastro = findViewById(R.id.textoNomeCadastro) as EditText
        val textoPrecoCadastro = findViewById(R.id.textoPrecoCadastro) as EditText
        val id = textoIdCadastro.text.toString().toInt()
        val nome = textoNomeCadastro.text.toString()
        val preco = textoPrecoCadastro.text.toString().toDouble()
        val produto = Produto(id, nome, preco)
        val it = Intent().apply {
            putExtra("produto", produto)
        }
        setResult(Activity.RESULT_OK, it)

        finish()
    }

    fun cancelarCadastro(view: View?) {
        finish()
    }
}
