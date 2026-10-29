package com.example.activity_tp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity() {
    private lateinit var btnAddCofe: Button
    private lateinit var Quantitecofe: TextView
    private lateinit var btnRemoveCofe: Button
    private lateinit var btnAddThe: Button
    private lateinit var QuantiteThe: TextView

    private lateinit var btnRemoveThe: Button
    private lateinit var btnAddCroissant: Button
    private lateinit var QuantiteCroissant: TextView

    private lateinit var btnRemoveCroissant: Button
    private lateinit var Client: EditText
    private lateinit var Totale: TextView
    private lateinit var btnvider:Button
    private lateinit var btnCommond: Button





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btnAddCofe = findViewById(R.id.p1)
        btnRemoveCofe = findViewById(R.id.m1)
        Quantitecofe = findViewById(R.id.qua1)
        btnAddThe = findViewById(R.id.p2)
        btnRemoveThe = findViewById(R.id.m2)
        QuantiteThe = findViewById(R.id.qua2)
        btnAddCroissant = findViewById(R.id.p3)
        btnRemoveCroissant = findViewById(R.id.m3)
        QuantiteCroissant = findViewById(R.id.qua3)
        Client = findViewById(R.id.edtclient)
        Totale = findViewById(R.id.total)
        btnvider = findViewById(R.id.vid)
        btnCommond = findViewById(R.id.comm)
        var max: Int = 0
        var quacofe: Int =0
        var quathe: Int =0
        var quaCroissant: Int =0
        btnAddCofe.setOnClickListener {
            quacofe +=1
            max +=10
            Quantitecofe.text=quacofe.toString()
            Totale.text = "Total: $max DH"
        }
        btnRemoveCofe.setOnClickListener {
            if(quacofe>0){
                quacofe -=1
                max -=10}
            Quantitecofe.text =quacofe.toString()
            Totale.text = "Total: $max DH"
        }
        btnAddThe.setOnClickListener {
            quathe +=1
            max+=20
            QuantiteThe.text=quathe.toString()
            Totale.text = "Total: $max DH"
        }
        btnRemoveThe.setOnClickListener {
            if (quathe > 0) {
                quathe -= 1
                max -=20
            }
            QuantiteThe.text = quathe.toString()
            Totale.text = "Total: $max DH"
        }
        btnAddCroissant.setOnClickListener {
            quaCroissant +=1
            max+=8
            QuantiteCroissant.text = quaCroissant.toString()
            Totale.text = "Total: $max DH"
        }
        btnRemoveCroissant.setOnClickListener {
            if (quaCroissant > 0) {
                quaCroissant -= 1
                max -=8
            }
            QuantiteCroissant.text = quaCroissant.toString()
            Totale.text = "Total: $max DH"
        }
        btnvider.setOnClickListener {
            Quantitecofe.text = "0"
            QuantiteThe.text = "0"
            QuantiteCroissant.text = "0"
            max = 0
            quacofe = 0
            quathe = 0
            quaCroissant = 0
            Client.text.clear()
            Totale.text = "Total: 0 DH"
        }
        btnCommond.setOnClickListener {
            if (Client.text.toString().isEmpty()) {
                Client.requestFocus()
                Client.hint = "entrer votre nom"
                Toast.makeText(this, "Veuillez entrer votre nom", Toast.LENGTH_LONG).show()
            }
            else if (quacofe == 0 && quathe == 0 && quaCroissant == 0) {
                Toast.makeText(this, "Le panier est vide", Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(this, "Merci ${Client.text.toString()}:${quacofe+quathe+quaCroissant} articles,${max} DH", Toast.LENGTH_LONG).show()
            }
        }
    }
}
