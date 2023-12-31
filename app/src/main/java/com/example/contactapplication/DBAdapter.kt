package com.example.contactapplication

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast


private val DB_NAME = "person"
val TABLE_NAME = "person_contact"
private val DB_VERSION = 1

private val ID = "id"
private val FNAME = "fname"
private val LNAME = "lname"
private val MOBILE = "mobile"
private val EMAIL = "email"

class DBAdapter(var context: Context) {

    var CREATE_TABLE = "CREATE TABLE $TABLE_NAME($ID INTEGER PRIMARY KEY AUTOINCREMENT, $FNAME TEXT, $LNAME TEXT, $EMAIL TEXT, $MOBILE TEXT)"

    var myOpenHelper = MyOpenHelper(context)
    var sqliteDatabase = myOpenHelper.writableDatabase

    fun createData(fname: String, lname: String, mobile: String, email: String) {
        var values = ContentValues()
        values.put(FNAME, fname)
        values.put(LNAME,lname)
        values.put(MOBILE,mobile)
        values.put(EMAIL,email)

        var rowId = sqliteDatabase.insert(TABLE_NAME, null, values)
        if (rowId > 0){
            Toast.makeText(context, "$rowId inserted successfully", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }

    fun updateData(id:Int,fname: String, lname: String, mobile: String, email: String) {
        var values = ContentValues()
        values.put(FNAME, fname)
        values.put(LNAME,lname)
        values.put(MOBILE,mobile)
        values.put(EMAIL,email)

        var rowId = sqliteDatabase.update(TABLE_NAME, values,"$ID = $id", null)
        if (rowId > 0){
            Toast.makeText(context, "$rowId inserted successfully", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }

    fun fetchData(): ArrayList<UserData> {
        var dataList = ArrayList<UserData>()
        var cursor = sqliteDatabase.rawQuery("SELECT * FROM $TABLE_NAME", null, null)
        if(cursor.count > 0){
            cursor.moveToFirst()
            do {
                var id = cursor.getInt(0)
                var fname = cursor.getString(1)
                var lname = cursor.getString(2)
                var mobile = cursor.getString(3)
                var email = cursor.getString(4)

                dataList.add(UserData(id, fname, lname, mobile, email))

            }while (cursor.moveToNext())
        }else{
            Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT).show()
        }
        return dataList

    }

    fun deleteSingleRow(rowId :Int){

        var deleteRaw = sqliteDatabase.delete(TABLE_NAME,"$ID = $rowId",null)

        if (deleteRaw > 0){
            Toast.makeText(context, "$deleteRaw Raw Deleted", Toast.LENGTH_SHORT).show()
        } else{
            Toast.makeText(context, "Data not found", Toast.LENGTH_SHORT).show()
        }

    }

    fun deleteAllData(){
        var deleteRaw = sqliteDatabase.delete(TABLE_NAME,null, null)
        if (deleteRaw > 0){
            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT).show()
        }
    }

    inner class MyOpenHelper(context: Context): SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
        override fun onCreate(sqliteDB: SQLiteDatabase?) {
            sqliteDB!!.execSQL(CREATE_TABLE)
        }

        override fun onUpgrade(sqliteDB: SQLiteDatabase?, p1: Int, p2: Int) {

        }
    }
}