package com.example.cryptocart.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.cryptocart.Class.Users
import com.example.cryptocart.R
import com.example.cryptocart.database.DataBaseHelper

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val buttonLogin = findViewById<Button>(R.id.loginButton)
        buttonLogin.setOnClickListener(loginActivityListener)

        val buttonRegister = findViewById<Button>(R.id.registerButton)
        buttonRegister.setOnClickListener(registerActivityListener)
    }

    private val registerActivityListener = View.OnClickListener{registerActivity()}
    private val loginActivityListener = View.OnClickListener { loginActivity() }

    private fun registerActivity(){
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
    private fun loginActivity(){
        val login  = findViewById<View>(R.id.loginEditText) as EditText
        val pass  = findViewById<View>(R.id.passEditView) as EditText
        if(login.text.length>0 && pass.text.length >0) {
            val user = Users(login.text.toString(), pass.text.toString())
            val dataBaseHelper = DataBaseHelper(this, null, null, 1)
            if (dataBaseHelper.checkUser(user) == true) {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("id",dataBaseHelper.takeID(user))
                startActivity(intent)
            } else {
                Toast.makeText(this, R.string.errorLogin, Toast.LENGTH_SHORT ).show()
                login.text.clear()
                pass.text.clear()
            }
        }
        else{
            Toast.makeText(this, R.string.errorLogin, Toast.LENGTH_SHORT ).show()
            login.text.clear()
            pass.text.clear()
        }
    }
}