package com.example.bschiranth1692.choresapp.ui

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.example.bschiranth1692.choresapp.R
import com.example.bschiranth1692.choresapp.data.ChoreListAdapter
import com.example.bschiranth1692.choresapp.data.ChoresDatabaseHelper
import com.example.bschiranth1692.choresapp.data.model.Chore
import kotlinx.android.synthetic.main.activity_chore_list.*

class ChoreListActivity : AppCompatActivity() {


    private var adapter:ChoreListAdapter? = null
    private var choreList:ArrayList<Chore>? = null
    private var choreListItems:ArrayList<Chore>? = null

    var dbHelper: ChoresDatabaseHelper? = null

    var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chore_list)

        progressDialog = ProgressDialog(this)

        choreList = ArrayList<Chore>()
        choreListItems = ArrayList()
        adapter = ChoreListAdapter(choreListItems!!,this)

        recyclerViewId.layoutManager = LinearLayoutManager(this)
        recyclerViewId.adapter = adapter

        //progress messagee
        progressDialog!!.setMessage("Loading..")
        progressDialog!!.show()


        dbHelper = ChoresDatabaseHelper(this)
        choreList = dbHelper!!.readAllChores()

        for(c in choreList!!.iterator()){
            var chore = Chore()
            chore.id = c.id
            chore.choreName = c.choreName
            chore.assigedTo = c.assigedTo
            chore.assignedBy = c.assignedBy
            chore.timeAssigned = c.timeAssigned
            choreListItems!!.add(chore)
        }

        if(choreList == null) Log.d("TAG","EMPTY LIST")

        var handler: Handler = Handler()
        handler.post(Runnable {
            progressDialog!!.dismiss()
        })

        adapter!!.notifyDataSetChanged()


        fabBtn.setOnClickListener {

        }
    }
}
