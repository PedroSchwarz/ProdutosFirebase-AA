package com.example.projetocrudprodutos.repository

import com.example.projetocrudprodutos.model.Produto
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProdutosRepository {

    fun fetchProdutos(
        onSuccess: (produtos: List<Produto>) -> Unit,
        onFailure: (error: String) -> Unit
    ) {
        database.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                onFailure(p0.message)
            }

            override fun onDataChange(p0: DataSnapshot) {
                val produtos = mutableListOf<Produto>()
                p0.children.forEach { snapshot ->
                    val produto = snapshot.getValue(Produto::class.java)
                    produto?.let { produtos.add(produto) }
                }
                onSuccess(produtos)
            }
        })
    }

    fun createProduto(
        produto: Produto,
        onSuccess: () -> Unit,
        onFailure: (error: String) -> Unit
    ) {
        database.child(produto.id.toString()).setValue(produto)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    onFailure(task.exception!!.message!!)
                }
            }
    }

    fun updateProduto(
        produto: Produto,
        onSuccess: () -> Unit,
        onFailure: (error: String) -> Unit
    ) {
        database.child(produto.id.toString()).setValue(produto)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    onFailure(task.exception!!.message!!)
                }
            }
    }

    fun deleteProduto(
        id: Int,
        onSuccess: () -> Unit,
        onFailure: (error: String) -> Unit
    ) {
        database.child(id.toString()).removeValue()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    onFailure(task.exception!!.message!!)
                }
            }
    }

    companion object {
        private val database = FirebaseDatabase.getInstance().reference.child("produtos")
    }
}
