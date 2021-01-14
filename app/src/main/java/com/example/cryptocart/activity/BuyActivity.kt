package com.example.cryptocart.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.cryptocart.Class.Crypt
import com.example.cryptocart.R
import com.example.cryptocart.database.DataBaseCrypto
import com.example.cryptocart.database.DataBaseHelper

class BuyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy)
        var data = intent.extras?.getInt("id")
        val spinner: Spinner = findViewById(R.id.spinner)
        ArrayAdapter.createFromResource(this, R.array.spinner,android.R.layout.simple_spinner_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        var textView = findViewById<View>(R.id.textView3) as TextView
        var dataBaseHelper = DataBaseHelper(this,null,null, 1)
        textView.text = textView.text.toString()+" "+dataBaseHelper.takeName(data!!)

        //Listenery Button

        val buttonBuy = findViewById<Button>(R.id.buttonProfit)
        buttonBuy.setOnClickListener(buyActivityListener)

        val buttonBack = findViewById<Button>(R.id.buttonBackCrypt)
        buttonBack.setOnClickListener(backActivityListener)

    }
    private val backActivityListener = View.OnClickListener { back() }
    private val buyActivityListener = View.OnClickListener { buyCrypt() }
    private fun back(){
        var data = intent.extras?.getInt("id")
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("id",data)
        startActivity(intent)
    }
    private fun buyCrypt(){
        var count = findViewById<View>(R.id.countEditCrypto) as EditText
        var price = findViewById<View>(R.id.priceEditCrypt) as EditText
        val spinner: Spinner = findViewById(R.id.spinner)
        if(count.text.length>0 && price.text.length>0){
            var crypt = Crypt(spinner.selectedItem.toString(), count.text.toString().toFloat(), price.text.toString().toFloat(),
                intent.extras?.getInt("id")!!,1
            )
            var dataBaseCrypto = DataBaseCrypto(this,null,null,1)
            if(dataBaseCrypto.addCrypto(crypt))
            {
                Toast.makeText(this, R.string.ok,Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this,R.string.bad,Toast.LENGTH_SHORT).show()
            }
        }
        else{
            Toast.makeText(this,R.string.badDane,Toast.LENGTH_SHORT).show()
        }
    }
}