package com.example.cryptocart.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.cryptocart.R
import com.example.cryptocart.database.DataBaseCrypto
import com.example.cryptocart.database.DataBaseHelper

class SellActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sell)
        val data = intent.extras?.getInt("id")
        val spinner: Spinner = findViewById(R.id.spinner)
        ArrayAdapter.createFromResource(this, R.array.spinner,android.R.layout.simple_spinner_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        val textView = findViewById<View>(R.id.textView3) as TextView
        val dataBaseHelper = DataBaseHelper(this,null,null, 1)
        textView.text = textView.text.toString()+" "+dataBaseHelper.takeName(data!!)
        val profit = findViewById<View>(R.id.profitView) as TextView
        val string: String = "0"
        profit.text = getString(R.string.profitView,string)
        //Listenery

        val calculateButton = findViewById<Button>(R.id.buttonProfit)
        calculateButton.setOnClickListener(calculateActivityListener)

        val backButton = findViewById<Button>(R.id.buttonBackCrypt)
        backButton.setOnClickListener(backActivityListener)

        val sellButton = findViewById<Button>(R.id.buttonSellCrypt)
        sellButton.setOnClickListener(sellActivityListener)
    }

    val sellActivityListener = View.OnClickListener { sell() }
    val backActivityListener = View.OnClickListener { back() }
    val calculateActivityListener =  View.OnClickListener { calkulate() }

    private fun sell(){
        val count = findViewById<View>(R.id.countEditCrypto) as EditText
        val price = findViewById<View>(R.id.priceEditCrypt) as EditText
        val spinner = findViewById<Spinner>(R.id.spinner)
        if(!count.text.isEmpty() && !price.text.isEmpty()){
            val dataBaseCrypto = DataBaseCrypto(this,null,null,1)
            if(count.text.toString().toFloat()<=dataBaseCrypto.countCrypto(intent.extras?.getInt("id")!!,spinner.selectedItem.toString())){
                if(dataBaseCrypto.sell(count.text.toString().toFloat(),price.text.toString().toFloat(),intent.extras?.getInt("id")!!, spinner.selectedItem.toString()))
                {
                    Toast.makeText(this, R.string.sellCryptoOk,Toast.LENGTH_SHORT).show()
                }
                else Toast.makeText(this,R.string.sellCryptoBad, Toast.LENGTH_SHORT).show()
            }
            else Toast.makeText(this,R.string.errorCount, Toast.LENGTH_SHORT).show()
        }
        else Toast.makeText(this,R.string.badDane, Toast.LENGTH_SHORT).show()
    }

    private fun back(){
        var data = intent.extras?.getInt("id")
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("id",data)
        startActivity(intent)
    }

    private fun calkulate(){
        val count = findViewById<View>(R.id.countEditCrypto) as EditText
        val price = findViewById<View>(R.id.priceEditCrypt) as EditText
        val spinner = findViewById<Spinner>(R.id.spinner)
        if(!count.text.isEmpty() && !price.text.isEmpty()){
            val dataBaseCrypto = DataBaseCrypto(this,null,null,1)
            if(count.text.toString().toFloat()<=dataBaseCrypto.countCrypto(intent.extras?.getInt("id")!!,spinner.selectedItem.toString())) {
                val result = price.text.toString().toFloat()
                val resultDB  = dataBaseCrypto.calculate(intent.extras?.getInt("id")!!, spinner.selectedItem.toString())
                if(resultDB*count.text.toString().toFloat()<result){
                    val profit = findViewById<View>(R.id.profitView) as TextView
                    val string  = result-resultDB
                    profit.text = getString(R.string.profitView,string.toString())
                }
                else {
                    val profit = findViewById<View>(R.id.profitView) as TextView
                    val res = resultDB - result
                    println(res)
                    val string = "-" +res.toString()
                    profit.text = getString(R.string.profitView, string)
                }
            }
            else{
                Toast.makeText(this,R.string.errorCount,Toast.LENGTH_SHORT).show()
            }
        }
        else {
            Toast.makeText(this, R.string.badDane, Toast.LENGTH_SHORT).show()
        }
    }
}