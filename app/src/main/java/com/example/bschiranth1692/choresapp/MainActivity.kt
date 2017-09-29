package com.example.bschiranth1692.choresapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.bschiranth1692.choresapp.data.ChoresDatabaseHelper

class MainActivity : AppCompatActivity() {

    var dbHelper: ChoresDatabaseHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = ChoresDatabaseHelper(this)
    }
}
