package com.example.bschiranth1692.choresapp.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.bschiranth1692.choresapp.data.model.Chore

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

    //inserts new chore
    fun createChore(chore: Chore){
        var db :SQLiteDatabase = writableDatabase

        //create content values - hashmap
        var values: ContentValues = ContentValues()

        values.put(KEY_CHORE_NAME, chore.choreName)
        values.put(KEY_CHORE_ASSIGNED_BY , chore.assignedBy)
        values.put(KEY_CHORE_ASSIGNED_TO,chore.assigedTo)
        values.put(KEY_CHORE_ASSIGNED_TIME, chore.timeAssigned)

        //insert into db
        db.insert(TABLE_NAME,null,values)

    }

}