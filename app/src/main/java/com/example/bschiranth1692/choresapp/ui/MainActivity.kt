package com.example.bschiranth1692.choresapp.ui

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.widget.Toast
import com.example.bschiranth1692.choresapp.R
import com.example.bschiranth1692.choresapp.data.ChoresDatabaseHelper
import com.example.bschiranth1692.choresapp.data.model.Chore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var dbHelper: ChoresDatabaseHelper? = null

    var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        dbHelper = ChoresDatabaseHelper(this)

        //start new activity
        if(dbHelper!!.getChoresCount() > 0){
            startActivity(Intent(this,ChoreListActivity::class.java))
        }

        progressDialog = ProgressDialog(this)

        saveChoreButtonId.setOnClickListener {


            if(!TextUtils.isEmpty(enterChoreId.text.toString()) &&
                    !TextUtils.isEmpty(assignById.text.toString()) &&
                    !TextUtils.isEmpty(assignToId.text.toString())){

                //progress messagee
                progressDialog!!.setMessage("Saving...")
                progressDialog!!.show()

                //save to db
                var chore:Chore = Chore()

                chore.choreName = enterChoreId.text.toString()
                chore.assignedBy = assignById.text.toString()
                chore.assigedTo = assignToId.text.toString()


                saveToDb(chore)

                //show progress dialog before dismissing
                var handler:Handler = Handler()
                handler.postDelayed(Runnable {
                    progressDialog!!.dismiss()
                    Toast.makeText(this,"Saved!",Toast.LENGTH_SHORT).show()
                },1000)

                //go to list view
                startActivity(Intent(this,ChoreListActivity::class.java))

            }else {
                Toast.makeText(this,"Please enter all fields",Toast.LENGTH_LONG).show()
            }
        }
    }

    fun saveToDb(chore: Chore){
        dbHelper!!.createChore(chore)
    }
}
