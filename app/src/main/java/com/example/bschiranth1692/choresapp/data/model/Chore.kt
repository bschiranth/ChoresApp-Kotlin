package com.example.bschiranth1692.choresapp.data.model

import java.text.DateFormat
import java.util.*

/**
 * Created by bschiranth1692 on 9/28/17.
 */
class Chore(){
    var choreName:String? = null
    var assignedBy: String? = null
    var assigedTo: String? = null
    var timeAssigned: Long? = null
    var id: Int? = null

    constructor(choreName:String,assignedBy: String,assigedTo: String,
                timeAssigned: Long,id:Int): this() {
        this.choreName = choreName
        this.assignedBy = assignedBy
        this.assigedTo = assigedTo
        this.timeAssigned = timeAssigned
        this.id = id
    }

    fun getFormatedDate(date: Long):String{

        //formatted time
        var dateFormat: DateFormat = DateFormat.getDateInstance()
        var formattedDate:String = dateFormat.format(date)
        return formattedDate
    }
}