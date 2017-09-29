package com.example.bschiranth1692.choresapp.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.bschiranth1692.choresapp.data.model.Chore
import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by bschiranth1692 on 9/28/17.
 */
class ChoresDatabaseHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME,null, DATABASE_VERSION){


    override fun onCreate(db: SQLiteDatabase?) {

        //create table sql statement
        var CREATE_CHORE_TABLE =
                "CREATE TABLE $TABLE_NAME " +
                "(" +
                "$KEY_ID INTEGER PRIMARY KEY," +
                "$KEY_CHORE_NAME TEXT," +
                "$KEY_CHORE_ASSIGNED_BY TEXT," +
                "$KEY_CHORE_ASSIGNED_TO TEXT," +
                "$KEY_CHORE_ASSIGNED_TIME LONG" +
                ")"

        db?.execSQL(CREATE_CHORE_TABLE) //execute the sql statement
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //drop current table and create new table
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    //CRUD
    //inserts new chore
    fun createChore(chore: Chore){
        var db :SQLiteDatabase = writableDatabase

        //create content values - hashmap
        var values:ContentValues = putContentValues(chore)

        //insert into db
        db.insert(TABLE_NAME,null,values)

        //close db
        db.close()

    }

    //read chore
    fun readChore(id:Int): Chore{
        var db :SQLiteDatabase = readableDatabase

        //get cursor
        var cursor:Cursor = db.query(TABLE_NAME, arrayOf(KEY_ID, KEY_CHORE_NAME,
                KEY_CHORE_ASSIGNED_BY, KEY_CHORE_ASSIGNED_TO, KEY_CHORE_ASSIGNED_TIME),
                KEY_ID+"=?", arrayOf(id.toString()),null,null,null,null)

        if(cursor != null){
            cursor.moveToFirst()
        }

        var chore = createChoreFromCursor(cursor)

        return chore
    }

    //get all the chores
    fun readAllChores(): ArrayList<Chore>{

        val allChores: ArrayList<Chore> = ArrayList()

        var db :SQLiteDatabase = readableDatabase

        //get cursor
        var cursor:Cursor = db.rawQuery("SELECT * FROM $TABLE_NAME",null)



        if(cursor.moveToFirst()){
            do {
                var chore = createChoreFromCursor(cursor)
                allChores.add(chore)
            }while (cursor.moveToNext())
        }



        return allChores
    }

    //update table
    fun updateChore(chore:Chore):Int{
        var db :SQLiteDatabase = writableDatabase

        var values:ContentValues = putContentValues(chore)

        //update query
        var n:Int = db.update(TABLE_NAME,values,"$KEY_CHORE_NAME =?", arrayOf(chore.id.toString()))

        db.close() //close db after write

        return n
    }

    //delete particular chore
    fun deleteChore(id: Int){
        var db:SQLiteDatabase = writableDatabase

        db.delete(TABLE_NAME,"$KEY_ID=?", arrayOf(id.toString()));

        db.close()
    }

    //create content values
    fun putContentValues(chore:Chore):ContentValues{

        var values: ContentValues = ContentValues()
        values.put(KEY_CHORE_NAME, chore.choreName)
        values.put(KEY_CHORE_ASSIGNED_BY , chore.assignedBy)
        values.put(KEY_CHORE_ASSIGNED_TO,chore.assigedTo)
        values.put(KEY_CHORE_ASSIGNED_TIME, System.currentTimeMillis())

        return values
    }

    //create chore
    fun createChoreFromCursor(cursor: Cursor):Chore{

        var chore = Chore()
        chore.id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
        chore.choreName = cursor.getString(cursor.getColumnIndex(KEY_CHORE_NAME))
        chore.assignedBy= cursor.getString(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_BY))
        chore.assigedTo= cursor.getString(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_TO))
        chore.timeAssigned= cursor.getLong(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_TIME))

        return chore
    }

    //get chore count
    fun getChoresCount(): Int{

        var db :SQLiteDatabase = readableDatabase

        var countQuery = "SELECT * FROM $TABLE_NAME"

        var cursor: Cursor = db.rawQuery(countQuery,null)

        if(cursor == null) return 0

        return cursor.count
    }

}