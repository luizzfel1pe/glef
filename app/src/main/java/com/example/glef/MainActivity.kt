package com.example.glef

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity(), View.OnClickListener {
    var btSalvar: Button? = null
    var btConsultar: Button? = null
    var btAlterar: Button? = null
    var btExcluir: Button? = null
    var codigo: EditText? = null
    var nome: EditText? = null
    var email: EditText? = null
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.MainActivity)
        ) { v: View, insets: WindowInsetsCompat ->
            val systemBars =
                insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        codigo = findViewById<View>(R.id.txtCodigo) as EditText
        nome = findViewById<View>(R.id.txtNome) as EditText
        email = findViewById<View>(R.id.txtEmail) as EditText
        btSalvar = findViewById<View>(R.id.btSalvar) as Button
        btConsultar = findViewById<View>(R.id.btConsultar) as Button
        btAlterar = findViewById<View>(R.id.btAlterar) as Button
        btExcluir = findViewById<View>(R.id.btExcluir) as Button
        btSalvar!!.setOnClickListener(this)
        btConsultar!!.setOnClickListener(this)
        btAlterar!!.setOnClickListener(this)
        btExcluir!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        // TODO Auto-generated method stub
        if (v.id == R.id.btSalvar) {
            salvar()
        }
        if (v.id == R.id.btConsultar) {
            consultar()
        }
        if (v.id == R.id.btAlterar) {
            alterar()
        }
        if (v.id == R.id.btExcluir) {
            excluir()
        }
    }

    private fun consultar() {
        val txtCodigo = codigo!!.text.toString().toInt()
        val bd = BancoController(baseContext)
        val dados = bd.carregaDadosPeloCodigo(txtCodigo)
        if (dados.moveToFirst()) {
            nome!!.setText(dados.getString(1))
            email!!.setText(dados.getString(2))
        } else {
            val msg = "Código não cadastrado"
            Toast.makeText(applicationContext, msg, Toast.LENGTH_LONG).show()
            limpar()
        }
    }

    fun salvar() {
        var msg = ""
        val txtNome = nome!!.text.toString()
        val txtEmail = email!!.text.toString()
        if (txtNome.length == 0 || txtEmail.length < 10) {
            msg = "Atenção - Os campos Nome e E-mail devem ser preenchidos!!!"
            Toast.makeText(applicationContext, msg, Toast.LENGTH_LONG).show()
        } else {
            val bd = BancoController(baseContext)
            val resultado: String
            resultado = bd.insereDados(txtNome, txtEmail)
            Toast.makeText(applicationContext, resultado, Toast.LENGTH_LONG).show()
            limpar()
        }
    }

    fun alterar() {
        val id = codigo!!.text.toString().toInt()
        val txtNome = nome!!.text.toString()
        val txtEmail = email!!.text.toString()
        var msg = ""
        var erro = false
        if (codigo!!.text.toString().length == 0) {
            msg = "Preencha o campo ID para alterar os dados"
            Toast.makeText(applicationContext, msg, Toast.LENGTH_LONG).show()
            erro = true
        }
        if (txtNome.length == 0) {
            msg = "Preencha corretamente o campo Nome"
            Toast.makeText(applicationContext, msg, Toast.LENGTH_LONG).show()
            erro = true
        }
        if (txtEmail.length < 10) {
            msg = "Preencha corretamente o campo E-mail"
            Toast.makeText(applicationContext, msg, Toast.LENGTH_LONG).show()
            erro = true
        }
        if (erro == false) {
            val bd = BancoController(baseContext)
            msg = bd.alteraDados(id, txtNome, txtEmail)
            Toast.makeText(applicationContext, msg, Toast.LENGTH_LONG).show()
            limpar()
        }
    }

    fun excluir() {
        val id = codigo!!.text.toString().toInt()
        val bd = BancoController(baseContext)
        val res: String
        res = bd.excluirDados(id)
        Toast.makeText(applicationContext, res, Toast.LENGTH_LONG).show()
        limpar()
    }

    fun limpar() {
        codigo!!.setText("")
        nome!!.setText("")
        email!!.setText("")
    }
}

