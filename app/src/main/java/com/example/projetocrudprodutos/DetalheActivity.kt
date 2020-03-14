package com.example.projetocrudprodutos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.example.projetocrudprodutos.model.Produto

class DetalheActivity : AppCompatActivity() {

    companion object {

        const val RESULT_EDIT = 1
        const val RESULT_DELETE = 2
    }

    private lateinit var textoIdDetalhe: EditText
    private lateinit var textoNomeDetalhe: EditText
    private lateinit var textoPrecoDetalhe: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhe)
        val intent = intent
        val produto = intent.getSerializableExtra("produto") as Produto

        textoIdDetalhe = findViewById<EditText>(R.id.textoIdDetalhe).apply {
            setText(produto.id.toString())
        }
        textoNomeDetalhe = findViewById<EditText>(R.id.textoNomeDetalhe).apply {
            setText(produto.nome)
        }
        textoPrecoDetalhe = findViewById<EditText>(R.id.textoPrecoDetalhe).apply {
            setText(produto.preco.toString())
        }

    }

    fun editarProduto(v: View?) {
        val produto = Produto(
            textoIdDetalhe.text.toString().toInt(),
            textoNomeDetalhe.text.toString(),
            textoPrecoDetalhe.text.toString().toDouble()
        )
        val data = Intent()
        data.putExtra("produto", produto)
        setResult(RESULT_EDIT, data)
        finish()
    }

    fun excluirProduto(v: View?) {
        setResult(RESULT_DELETE)
        finish()
    }

    fun voltar(v: View?) {
        finish()
    }
}
