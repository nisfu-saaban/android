package com.sambudisp.firstkotlin.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sambudisp.firstkotlin.R
import com.sambudisp.firstkotlin.model.StudentsRequest
import kotlinx.android.synthetic.main.adapter_students.view.*

//pilih extends Adapter yang dari RecyclerView, lalu di dalam kurung lancip pilih ViewHolder yang bikinan kita sendiri
//alt+Enter di AdapterStudent, pilih implement member, ctrl+A (pilih semuanya), klik OK
class AdapterStudents(private val studentsRequestList: List<StudentsRequest/*ini ngambil dari model*/>,
                      private val onClick: (student: StudentsRequest) -> Unit /*Fungsi Higher Order Function untuk proses respon item Recycler diklik*/)
    : RecyclerView.Adapter<AdapterStudents.ViewHolder>() {

    //Binding Layout dulu / disebut juga dengan Mengubungkan Layout dengan Kotlinnya (engine nya)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //proses mengInflate layout
        val inflater = LayoutInflater.from(parent.context) //parent ini sesuai dengan parameter di atas ^
        val itemView: View = inflater.inflate(R.layout.adapter_students, parent, false) //R nya dipilih sesuai yang sama dengan nama package com.sambudisp.firstkotlin
        return ViewHolder(itemView)
    }

    //digunakan untuk menampilkan "Perulangan Penampilan Data" sejumlah datanya
    override fun getItemCount(): Int {
        return studentsRequestList.size //nah ini jumlah datanya
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val student = studentsRequestList[position]
        holder.bind(student)

        //Proses onClicknya item RecyclerView yang diklik
        holder.itemView.setOnClickListener {
            onClick(student)
        }
    }

    //pilih ViewHolder yang dari RecyclerView, kalau merah nanti pilih yang "Constructor"
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(studentsRequest: StudentsRequest) {
            itemView.txtNama.text = studentsRequest.namanya
            itemView.txtEmail.text = studentsRequest.emailnya

            //untuk mengetahui kondisi "Avatarnya" null atau engga, kalau null perintah ini tidak dijalankan
            studentsRequest.avatarnya?.let {
                itemView.imgAvatar.setImageResource(it)
            }
        }
    }

}