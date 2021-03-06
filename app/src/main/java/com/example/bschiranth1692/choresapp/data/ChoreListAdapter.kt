package com.example.bschiranth1692.choresapp.data

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.bschiranth1692.choresapp.R
import com.example.bschiranth1692.choresapp.data.model.Chore
import com.example.bschiranth1692.choresapp.ui.ChoreListActivity
import kotlinx.android.synthetic.main.popup.view.*

/**
 * Created by bschiranth1692 on 9/29/17.
 */
class ChoreListAdapter(private val allChores:ArrayList<Chore>,
                       private val context:Context): RecyclerView.Adapter<ChoreListAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_row,parent,false),context,allChores)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        println("AFTER4.......................................................................................")
        holder?.bindViews(allChores[position])
        println("AFTER5.......................................................................................")
    }

    override fun getItemCount(): Int {
        if(allChores == null) return 0
        return allChores.size
    }


    inner class ViewHolder(itemView: View, context: Context,allChores: ArrayList<Chore>) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var context = context
        var choreName = itemView.findViewById<TextView>(R.id.listChoreNameId)
        var assigndBy = itemView.findViewById<TextView>(R.id.listAssignedById)
        var assignTo = itemView.findViewById<TextView>(R.id.listAssignedToId)
        var assignDate = itemView.findViewById<TextView>(R.id.listDateId)

        var editButton = itemView.findViewById<Button>(R.id.editButtonId)
        var delButton = itemView.findViewById<Button>(R.id.delButtonId)

        fun bindViews(chore:Chore){

            choreName.text = chore.choreName
            assigndBy.text = "Assigned By: "+chore.assignedBy
            assignTo.text = "Assigned To: "+chore.assigedTo
            assignDate.text = chore.getFormatedDate(chore.timeAssigned!!)

            editButton.setOnClickListener (this)
            delButton.setOnClickListener (this)
        }

        override fun onClick(view: View?) {

            when(view!!.id){
                R.id.editButtonId -> {
                    editChore(allChores[adapterPosition])
                }

                R.id.delButtonId -> {
                    delChore(adapterPosition)
                    allChores.removeAt(adapterPosition)
                    notifyItemRemoved(adapterPosition)
                }

                else -> return
            }

        }

        fun delChore(id:Int){
            var db:ChoresDatabaseHelper = ChoresDatabaseHelper(context)
            db.deleteChore(id)
            db.close()
        }

        fun editChore(chore: Chore){

            //pop up
            var dialogBuilder: AlertDialog.Builder? = null
            var dialog:AlertDialog? = null
            var dbHelper = ChoresDatabaseHelper(context)

            var view = LayoutInflater.from(context).inflate(R.layout.popup,null)

            dialogBuilder = AlertDialog.Builder(context).setView(view)
            dialog = dialogBuilder!!.create()
            dialog!!.show()

            view.saveChoreButtonId2.setOnClickListener {
                if(!TextUtils.isEmpty(view.enterChoreId2.text.toString()) &&
                        !TextUtils.isEmpty(view.assignById2.text.toString()) &&
                        !TextUtils.isEmpty(view.assignToId2.text.toString())){


                    chore.choreName = view.enterChoreId2.text.toString()
                    chore.assigedTo = view.assignToId2.text.toString()
                    chore.assignedBy = view.assignById2.text.toString()

                    dbHelper!!.updateChore(chore)
                    notifyItemChanged(adapterPosition,chore)

                    dialog!!.dismiss()

                }
            }
        }
    }
}