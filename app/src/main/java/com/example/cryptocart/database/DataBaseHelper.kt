package com.example.cryptocart.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.cryptocart.Class.Crypt
import com.example.cryptocart.Class.Users

class DataBaseHelper(context: Context, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, DATABASE_NAME,factory, DATABASE_VERSION) {
    //Table users
    private val TABLE_USERS = "users"

    private val  USERS_COLUMN_ID  = "user_id"
    private val  USERS_COLUMN_LOGIN = "login"
    private val  USERS_COLUMN_PASS = "password"
    private val  USERS_COLUMN_NAME = "name"





    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "CryptoCartDataBase.db"}

    override fun onCreate(database: SQLiteDatabase) {
        //USERS TABLE
        val CREATE_USER_TABLE = ("CREATE TABLE "+
                TABLE_USERS+"("
                + USERS_COLUMN_ID + " INTEGER PRIMARY KEY,"
                + USERS_COLUMN_LOGIN + " TEXT,"
                + USERS_COLUMN_PASS + " TEXT,"
                + USERS_COLUMN_NAME + " TEXT)"
                )
        database.execSQL(CREATE_USER_TABLE)
        //CRYPTO TABLE
    }

    override fun onUpgrade(database: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        database.execSQL("DROP TABLE IF EXISTS" + TABLE_USERS)
        onCreate(database)
    }
 //Fun TABLE USER
    //Dodawanie nowego usera przy rejestracji
    fun addUser(user: Users):Boolean{
        val data = ContentValues()
        data.put(USERS_COLUMN_LOGIN, user.login)
        data.put(USERS_COLUMN_PASS, user.pass)
        data.put(USERS_COLUMN_NAME, user.name)

        val database = this.writableDatabase
        val newRow = database.insert(TABLE_USERS, null, data)
        database.close()
        return newRow>0
    }
    //Pobieranie imienia z bazy danych
    fun takeName(id: Int): String{
        val query =
            "SELECT name FROM $TABLE_USERS WHERE $USERS_COLUMN_ID = \"$id\" "
        val database = this.writableDatabase
        val cursor = database.rawQuery(query,null)
        if(cursor.moveToFirst()) {
            cursor.moveToFirst()
            return cursor.getString(0)
        }
        return ""
    }
    //Pobieranie id z bazy danych
    fun takeID(user: Users): Int{
        val query =
            "SELECT user_id FROM $TABLE_USERS WHERE $USERS_COLUMN_PASS = \"${user.pass}\" "+
                    "and $USERS_COLUMN_LOGIN = \"${user.login}\" "
        val database = this.writableDatabase
        val cursor = database.rawQuery(query,null)
        if(cursor.moveToFirst()){
            cursor.moveToFirst()
            return cursor.getInt(0)
        }
        return 0
    }
    //Sprawdzenie czy istnieje taki user
    fun checkUser(user: Users): Boolean {
        var result = false
        val query =
            "SELECT * FROM $TABLE_USERS WHERE $USERS_COLUMN_PASS = \"${user.pass}\" " +
                    "and $USERS_COLUMN_LOGIN = \"${user.login}\" "
        val  database = this.writableDatabase
        val cursor = database.rawQuery(query,null)
        if(cursor.moveToFirst()){
            result = true
        }
        return result
    }
    //Sprawdzenie czy już istieje takie imie
    fun checkName(name: String): Boolean {
        var result = false;
        var query =
            "SELECT user_id FROM $TABLE_USERS WHERE $USERS_COLUMN_NAME = \"$name\" "
        val database = this.writableDatabase
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst())
            result = true
        return result
    }


}