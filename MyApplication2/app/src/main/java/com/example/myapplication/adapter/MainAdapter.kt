package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.DataItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item.view.*

class MainAdapter(var dataItem: List<DataItem>,
                  //val onClickListener: (DataItem) -> Unit,
                  val listener : dataListener): RecyclerView
                .Adapter<MainAdapter.TourViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TourViewHolder = TourViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item,parent,false))

    override fun getItemCount(): Int{
         val limit = 13
        if (dataItem.size > limit){
            return limit
        }else{
            return dataItem.size
        }
    }

    override fun onBindViewHolder(holder: MainAdapter.TourViewHolder, position: Int) {
        holder.bind(dataItem[position], listener)
    }

    inner class TourViewHolder(view: View):RecyclerView.ViewHolder(view){
            private val img: ImageView = itemView.findViewById(R.id.iv_item)

        fun bind(dataItem: DataItem, listener : dataListener){
            itemView.tv_item_title.text = dataItem.namaPariwisata
            itemView.tv_item_desc.text = dataItem.detailPariwisata
            Picasso.get()
                .load(dataItem
                .gambarPariwisata)
                .fit()
                .into(img)

            itemView.setOnClickListener { listener.onDataClick(dataItem) }
        }
    }
}

interface dataListener{
    fun onDataClick(dataItem: DataItem)
}