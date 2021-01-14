package com.example.cryptocart.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.cryptocart.Class.Crypt
import com.example.cryptocart.Class.Users

class DataBaseCrypto (context: Context, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, DataBaseCrypto.DATABASE_NAME,factory, DataBaseCrypto.DATABASE_VERSION) {
    private val TABLE_CRYPTO = "crypto"

    private val CRYPTO_COLUMN_ID = "id"
    private val CRYPTO_COLUMN_COUNT = "count"
    private val CRYPTO_COLUMN_PRICE = "price"
    private val CRYPTO_COLUMN_NAME = "crypt"
    private val CRYPTO_COLUMN_IDUSER = "user"



    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "CryptoCartDataBase2.db"
    }

    override fun onCreate(database: SQLiteDatabase) {
        val CREATE_CRYPTO_TABLE = ("CREATE TABLE "+
                TABLE_CRYPTO+"("
                + CRYPTO_COLUMN_ID + " INTEGER PRIMARY KEY,"
                + CRYPTO_COLUMN_COUNT + " REAL,"
                + CRYPTO_COLUMN_PRICE + " REAL,"
                + CRYPTO_COLUMN_NAME + " TEXT,"
                + CRYPTO_COLUMN_IDUSER + " INTEGER)"
                )
        database.execSQL(CREATE_CRYPTO_TABLE)
    }

    override fun onUpgrade(database: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        database.execSQL("DROP TABLE IF EXISTS" + TABLE_CRYPTO)
        onCreate(database)
    }

    //Fun TABLE CRYPTO
    fun addCrypto(crypt: Crypt):Boolean{
        val query = "SELECT count, price, id FROM $TABLE_CRYPTO WHERE $CRYPTO_COLUMN_IDUSER = \"${crypt.id_user}\" " +
                " AND $CRYPTO_COLUMN_NAME =  \"${crypt.name}\" "
        val database = this.writableDatabase
        val cursor = database.rawQuery(query,null)
        val data = ContentValues()
        var newRow: Int
        if(cursor.moveToFirst()){
            cursor.moveToFirst()
            var count = crypt.count+cursor.getFloat(0)
            var price = crypt.price+cursor.getFloat(1)
            data.put(CRYPTO_COLUMN_NAME,crypt.name)
            data.put(CRYPTO_COLUMN_COUNT,count)
            data.put(CRYPTO_COLUMN_PRICE,price)
            data.put(CRYPTO_COLUMN_IDUSER, crypt.id_user)
            var string:String = "id = " +cursor.getInt(2).toString()
            newRow = database.update(TABLE_CRYPTO,data,string,null)
        }
        else {
            data.put(CRYPTO_COLUMN_NAME, crypt.name)
            data.put(CRYPTO_COLUMN_COUNT, crypt.count)
            data.put(CRYPTO_COLUMN_PRICE, crypt.price)
            data.put(CRYPTO_COLUMN_IDUSER, crypt.id_user)
            newRow = database.insert(TABLE_CRYPTO, null, data).toInt()
        }
        database.close()
        return newRow>0
    }

    fun countCrypto(data: Int, selected: String): Float{
        var result: Float = 0.000F
        var query =
                "SELECT count FROM $TABLE_CRYPTO WHERE $CRYPTO_COLUMN_IDUSER  = \"$data\" " +
                        " AND $CRYPTO_COLUMN_NAME = \"$selected\" "
        val database = this.writableDatabase
        val cursor = database.rawQuery(query, null)
        if(cursor.moveToFirst()){
            cursor.moveToFirst()
           result = cursor.getFloat(0)
        }
        return  result
    }
    fun calculate(data: Int, selected: String):Float{
        var result: Float = 0.000F
        var query =
                "SELECT price,count FROM $TABLE_CRYPTO WHERE $CRYPTO_COLUMN_IDUSER = \"$data\" " +
                        " AND $CRYPTO_COLUMN_NAME = \"$selected\" "
        val database = this.writableDatabase
        val cursor = database.rawQuery(query,null)
        if(cursor.moveToFirst()){
            cursor.moveToFirst()
            result = cursor.getFloat(1)/cursor.getFloat(0)
        }
        return result
    }
    fun sell(count: Float, price: Float, date: Int, selected: String): Boolean{
        var result = false
        var newRow: Long = 0
        var query =
                "SELECT count, price, id FROM $TABLE_CRYPTO WHERE $CRYPTO_COLUMN_IDUSER = \"$date\" "+
                        " AND $CRYPTO_COLUMN_NAME = \"$selected\" "
        val database = this.writableDatabase
        val cursor = database.rawQuery(query,null)
        if(cursor.moveToFirst()){
            cursor.moveToFirst()
            val data = ContentValues()
            data.put(CRYPTO_COLUMN_NAME,selected)
            data.put(CRYPTO_COLUMN_ID,cursor.getInt(2))
            data.put(CRYPTO_COLUMN_IDUSER,date)
            var counted = cursor.getFloat(0) - count
            var priced = cursor.getFloat(1) - price
            if(priced<0) priced = 0F
            data.put(CRYPTO_COLUMN_COUNT,counted)
            data.put(CRYPTO_COLUMN_PRICE,priced)
            var id = cursor.getInt(2)
            var string = Array<String>(1){id.toString()}
            database.delete(TABLE_CRYPTO,"ID = ?",string )
            newRow = database.insert(TABLE_CRYPTO,null,data)
        }
        return newRow>0
    }
}