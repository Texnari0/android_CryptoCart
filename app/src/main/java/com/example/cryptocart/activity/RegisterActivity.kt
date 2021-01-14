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

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val buttonRegister = findViewById<Button>(R.id.buttonReg)
        buttonRegister.setOnClickListener(registerActivityListener)

        val buttonBack = findViewById<Button>(R.id.back)
        buttonBack.setOnClickListener(backActivityListener)
    }
    private val backActivityListener = View.OnClickListener { backActivity() }
    private val registerActivityListener = View.OnClickListener{registerActivity()}

    private fun backActivity(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
    //TODO добавити провірку чи існує вже таке імя
    private fun registerActivity(){

        val login  = findViewById<View>(R.id.logEditText) as EditText
        val pass  = findViewById<View>(R.id.passwordEditText) as EditText
        val passRep = findViewById<View>(R.id.passRepeatEditText) as EditText
        val name = findViewById<View>(R.id.nameEditText) as EditText
        if(login.text.length>0 && pass.text.length>0 && pass.text.toString().equals(passRep.text.toString()) && name.text.length>0) {
            val dataBaseHelper = DataBaseHelper(this, null, null, 1)
            val user = Users(name.text.toString(), login.text.toString(), pass.text.toString())
            if (dataBaseHelper.checkName(name.text.toString()) == false) {
                if (dataBaseHelper.addUser(user)) {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("id", dataBaseHelper.takeID(user))
                    startActivity(intent)
                } else {
                    Toast.makeText(this, R.string.errorLogin, Toast.LENGTH_SHORT).show()
                    pass.text.clear()
                    passRep.text.clear()
                }
            } else {

                Toast.makeText(this, R.string.errorName, Toast.LENGTH_SHORT).show()
                pass.text.clear()
                passRep.text.clear()
                name.text.clear()
            }
        }
        else{
            Toast.makeText(this, R.string.errorLogin, Toast.LENGTH_SHORT).show()
            pass.text.clear()
            passRep.text.clear()
        }
    }
}