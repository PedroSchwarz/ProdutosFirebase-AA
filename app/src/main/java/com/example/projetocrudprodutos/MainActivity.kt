package com.example.projetocrudprodutos

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projetocrudprodutos.adapter.ProdutoAdapter
import com.example.projetocrudprodutos.model.Produto
import com.example.projetocrudprodutos.repository.ProdutosRepository
import com.google.firebase.database.*

class MainActivity : AppCompatActivity(), ProdutoAdapter.OnItemClickListener {
    private val REQ_CADASTRO = 1;
    private val REQ_DETALHE = 2;
    private var listaProdutos: ArrayList<Produto> = ArrayList()
    private var posicaoAlterar = -1

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: ProdutoAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    private val repository by lazy {
        ProdutosRepository()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewManager = LinearLayoutManager(this)
        viewAdapter = ProdutoAdapter(listaProdutos)
        viewAdapter.onItemClickListener = this

        recyclerView = findViewById<RecyclerView>(R.id.recyclerView).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        repository.fetchProdutos(onSuccess = { produtos ->
            listaProdutos.clear()
            listaProdutos.addAll(produtos)
            viewAdapter.notifyDataSetChanged()
        }, onFailure = {
            showMessage(it)
        })
    }

    override fun onItemClicked(view: View, position: Int) {
        val it = Intent(this, DetalheActivity::class.java)
        this.posicaoAlterar = position
        val produto = listaProdutos.get(position)
        it.putExtra("produto", produto)
        startActivityForResult(it, REQ_DETALHE)
    }

    fun abrirFormulario(view: View) {
        val it = Intent(this, CadastroActivity::class.java)
        startActivityForResult(it, REQ_CADASTRO)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_CADASTRO) {
            if (resultCode == Activity.RESULT_OK) {
                val produto = data?.getSerializableExtra("produto") as Produto
                repository.createProduto(produto, onSuccess = {
                    showMessage("Cadastro realizada com sucesso!")
                }, onFailure = { showMessage(it) })
            }
        } else if (requestCode == REQ_DETALHE) {
            if (resultCode == DetalheActivity.RESULT_EDIT) {
                val produto = data?.getSerializableExtra("produto") as Produto
                repository.updateProduto(produto, onSuccess = {
                    showMessage("Edicao realizada com sucesso!")
                }, onFailure = { showMessage(it) })
            } else if (resultCode == DetalheActivity.RESULT_DELETE) {
                val produto = listaProdutos[this.posicaoAlterar]
                repository.deleteProduto(produto.id, onSuccess = {
                    showMessage("Exclusao realizada com sucesso!")
                }, onFailure = { showMessage(it) })
            }
        }
    }
}
