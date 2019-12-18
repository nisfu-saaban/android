package com.sambudisp.firstkotlin.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sambudisp.firstkotlin.R
import com.sambudisp.firstkotlin.model.StudentsRequest
import kotlinx.android.synthetic.main.activity_students_detail2.view.*
import kotlinx.android.synthetic.main.viewholder_config.view.*

class ConfigAdapter(private val studentsRequestList: List<StudentsRequest>,
                    private val onClick: (student: StudentsRequest) -> Unit )
    : RecyclerView.Adapter<ConfigAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView: View = inflater.inflate(R.layout.viewholder_config, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return studentsRequestList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val student = studentsRequestList[position]
        holder.bind(student)

        holder.itemView.setOnClickListener {
            onClick(student)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(studentsRequest: StudentsRequest) {
            itemView.txt_name_config.text = studentsRequest.namanya
            itemView.txt_email_config.text = studentsRequest.emailnya
        }
    }

}
