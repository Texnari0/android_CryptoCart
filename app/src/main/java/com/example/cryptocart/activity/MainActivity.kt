package com.example.cryptocart.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.cryptocart.R
import com.example.cryptocart.database.DataBaseCrypto
import com.example.cryptocart.database.DataBaseHelper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var data = intent.extras?.getInt("id")
        val spinner: Spinner = findViewById(R.id.spinner)
        ArrayAdapter.createFromResource(this, R.array.spinner,android.R.layout.simple_spinner_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        var textView = findViewById<View>(R.id.textView3) as TextView
        var dataBaseHelper = DataBaseHelper(this,null,null, 1)
        textView.text = textView.text.toString()+" "+dataBaseHelper.takeName(data!!)
        var textView2 = findViewById<View>(R.id.countView) as TextView
        var dataBaseCrypto = DataBaseCrypto(this,null,null,1)

        //Listenery Button
        val buttonHistory = findViewById<Button>(R.id.buttonHistory)
        buttonHistory.setOnClickListener(historyActivityListener)

        spinner?.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                var textView2 = findViewById<View>(R.id.countView) as TextView
                val selectedItem = parent!!.getItemAtPosition(position).toString()
                var string : String = dataBaseCrypto.countCrypto(data,spinner.selectedItem.toString()).toString()
                textView2.text = getString(R.string.uHave,string)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
        val buttonBuy = findViewById<Button>(R.id.buttonBuy)
        buttonBuy.setOnClickListener(buyActivityListener)

        val buttonSell = findViewById<Button>(R.id.buttonSell)
        buttonSell.setOnClickListener(sellActivityListener)

        val buttonSetting = findViewById<Button>(R.id.buttonSettings)
        buttonSetting.setOnClickListener(settingsActivityListener)

        val buttonLogOut = findViewById<Button>(R.id.logOut)
        buttonLogOut.setOnClickListener(logOutActivityListener)

        }
    private val historyActivityListener = View.OnClickListener{historyActivity()}
    private val buyActivityListener = View.OnClickListener { buyActivityListener() }
    private val sellActivityListener = View.OnClickListener { sellActivityListener() }
    private val settingsActivityListener = View.OnClickListener { settingsActivityListener() }
    private val logOutActivityListener = View.OnClickListener { logOutActivityListener() }

    private fun logOutActivityListener(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
    private fun settingsActivityListener(){
        var data = intent.extras?.getInt("id")
        val intent = Intent(this, SettingActivity::class.java)
        intent.putExtra("id",data)
        startActivity(intent)
    }
    private fun sellActivityListener(){
        var data = intent.extras?.getInt("id")
        val intent = Intent(this, SellActivity::class.java)
        intent.putExtra("id",data)
        startActivity(intent)
    }
    private fun buyActivityListener(){
        var data = intent.extras?.getInt("id")
        val intent = Intent(this, BuyActivity::class.java)
        intent.putExtra("id",data)
        startActivity(intent)
    }
    private fun historyActivity(){
        var data = intent.extras?.getInt("id")
        val intent = Intent(this, HistoryActivity::class.java)
        intent.putExtra("id",data)
        startActivity(intent)
    }
}
