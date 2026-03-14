package com.example.jfinventory

// Modelo de dados para as peças do estoque
data class Item(
    val id: String? = null,
    val nome: String? = null,
    val quantidade: Int? = 0,
    val categoria: String? = "Hardware"
)
