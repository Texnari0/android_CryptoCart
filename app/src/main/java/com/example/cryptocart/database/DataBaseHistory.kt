package com.example.cryptocart.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.cryptocart.Class.Crypt

//TODO
//COPY of DataBaseCrypto
class DataBaseHistory (context: Context, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, DataBaseHistory.DATABASE_NAME,factory, DataBaseHistory.DATABASE_VERSION) {
    private val TABLE_CRYPTO = "crypto"

    private val CRYPTO_COLUMN_ID = "id"
    private val CRYPTO_COLUMN_COUNT = "count"
    private val CRYPTO_COLUMN_PRICE = "price"
    private val CRYPTO_COLUMN_NAME = "crypt"
    private val CRYPTO_COLUMN_IDUSER = "user"
    private val CRYPTO_COLUMN_ACTIVE = "active"
    private val CRYPTO_COLUMN_BUY = "its_buy"



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
                + CRYPTO_COLUMN_IDUSER + " INTEGER,"
                + CRYPTO_COLUMN_ACTIVE + " INTEGER,"
                + CRYPTO_COLUMN_BUY + " INTEGER)"
                )
        database.execSQL(CREATE_CRYPTO_TABLE)
    }

    override fun onUpgrade(database: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        database.execSQL("DROP TABLE IF EXISTS" + TABLE_CRYPTO)
        onCreate(database)
    }

    //Fun TABLE CRYPTO
    fun addCrypto(crypt: Crypt):Boolean{
        val data = ContentValues()
        data.put(CRYPTO_COLUMN_NAME, crypt.name)
        data.put(CRYPTO_COLUMN_COUNT, crypt.count)
        data.put(CRYPTO_COLUMN_PRICE, crypt.price)
        data.put(CRYPTO_COLUMN_IDUSER, crypt.id_user)
        data.put(CRYPTO_COLUMN_BUY, crypt.buy)
        data.put(CRYPTO_COLUMN_ACTIVE, crypt.active)
        println(crypt.count)
        val database = this.writableDatabase
        val newRow = database.insert(TABLE_CRYPTO, null, data)
        database.close()
        return newRow>0
    }

    fun countCrypto(data: Int, selected: String): Float{
        var result: Float = 0.000F
        var query =
                "SELECT count FROM $TABLE_CRYPTO WHERE $CRYPTO_COLUMN_IDUSER  = \"$data\" " +
                        " AND $CRYPTO_COLUMN_NAME = \"$selected\" " +
                        " AND $CRYPTO_COLUMN_ACTIVE = 1 "+
                        " AND $CRYPTO_COLUMN_BUY = 1"
        val database = this.writableDatabase
        val cursor = database.rawQuery(query, null)
        if(cursor.moveToFirst()){
            cursor.moveToFirst()
            result+=cursor.getFloat(0)

            while(cursor.moveToNext()){
                println("Result" + result)
                result+=cursor.getFloat(0)
            }
        }
        return  result
    }
    fun calculate(data: Int, selected: String,count: Float):Float{
        var result: Float = 0.000F
        var query =
                "SELECT count, price, id FROM $TABLE_CRYPTO WHERE $CRYPTO_COLUMN_IDUSER = \"$data\" "+
                        " AND $CRYPTO_COLUMN_NAME = \"$selected\" "+
                        " AND $CRYPTO_COLUMN_ACTIVE = 1 "+
                        " AND $CRYPTO_COLUMN_BUY = 1"
        val database = this.writableDatabase
        val cursor = database.rawQuery(query,null)
        if(cursor.moveToFirst()){
            cursor.moveToFirst()
            var resultCountAll = 0.000F
            var resultCount = 0.000F
            var id: Int = 0
            var price: Float = 0.000F
            do{
                //DOC
                id = cursor.getInt(2)

                price = cursor.getFloat(1)
                resultCount = cursor.getFloat(0)
                resultCountAll+=resultCount
                result = resultCount*price
            }
            while(cursor.moveToNext() && resultCountAll<=count)
            if(resultCountAll>count){
                resultCount = count - resultCountAll
                result-=resultCount*price
            }

        }

        return result
    }
}

