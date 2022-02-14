package com.example.vaccinefinder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.showlist.view.*

class listAdapter(private val items : ArrayList<dataCenter>) : RecyclerView.Adapter<ListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val views = LayoutInflater.from(parent.context).inflate(R.layout.showlist,parent,false)
        return ListViewHolder(views)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val currentItem = items[position]
        holder.centerName.text = currentItem.name
        holder.address.text = currentItem.address
        holder.fromdate.text = currentItem.from
        holder.todate.text = currentItem.to
        holder.feetype.text = currentItem.fees
        holder.vaccname.text = currentItem.vacciName
        holder.available.text = currentItem.available
        holder.date.text = currentItem.dat
        holder.age.text = currentItem.ageL
    }

    override fun getItemCount(): Int {
        return items.size
    }


}

class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    var centerName : TextView = itemView.findViewById(R.id.centerName)
    var address : TextView = itemView.findViewById(R.id.address)
    var fromdate : TextView = itemView.findViewById(R.id.from)
    var todate : TextView = itemView.findViewById(R.id.to)
    var feetype : TextView = itemView.findViewById(R.id.fees)
    var vaccname : TextView = itemView.findViewById(R.id.vaxName)
    var available : TextView = itemView.findViewById(R.id.available)
    var date : TextView = itemView.findViewById(R.id.date)
    var age : TextView = itemView.findViewById(R.id.ageLimit)
}