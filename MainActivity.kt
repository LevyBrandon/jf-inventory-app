package com.example.jfinventory

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = FirebaseDatabase.getInstance().getReference("Estoque")

        val editNome = findViewById<EditText>(R.id.editNome)
        val editQtd = findViewById<EditText>(R.id.editQuantidade)
        val btnSalvar = findViewById<Button>(R.id.btnSalvar)
        val txtLista = findViewById<TextView>(R.id.txtLista)

        btnSalvar.setOnClickListener {
            val nome = editNome.text.toString()
            val qtd = editQtd.text.toString().toIntOrNull() ?: 0
            val id = database.push().key

            if (nome.isNotEmpty() && id != null) {
                val novoItem = Item(id, nome, qtd)
                database.child(id).setValue(novoItem).addOnSuccessListener {
                    Toast.makeText(this, "Peça cadastrada!", Toast.LENGTH_SHORT).show()
                    editNome.text.clear()
                    editQtd.text.clear()
                }
            }
        }

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val listaString = StringBuilder()
                for (itemSnapshot in snapshot.children) {
                    val item = itemSnapshot.getValue(Item::class.java)
                    listaString.append("📦 ${item?.nome} | Qtd: ${item?.quantidade}\n")
                }
                txtLista.text = listaString.toString()
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }
}
