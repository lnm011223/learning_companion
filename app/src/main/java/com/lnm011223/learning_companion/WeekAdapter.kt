package com.lnm011223.learning_companion

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView

class WeekAdapter (val weeklist: List<Topic>) : RecyclerView.Adapter<WeekAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var weektext: TextView = view.findViewById(R.id.week_text)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.week_item,parent,false)
        val viewHolder = ViewHolder(view)
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            val week = weeklist[position]
            //Toast.makeText(parent.context,"${week.week}${week.subject}${week.term}",Toast.LENGTH_SHORT).show()
            val bundle = Bundle()

            bundle.apply {
                putString("subject",week.subject)
                putString("term",week.term)
                putString("week",week.week)
            }
            Navigation.findNavController(parent).navigate(R.id.action_weekFragment_to_topicsFragment,bundle)

        }


        return viewHolder
    }

    override fun onBindViewHolder(holder: WeekAdapter.ViewHolder, position: Int) {
        val week = weeklist[position]
        holder.weektext.text = week.week
    }

    override fun getItemCount() = 20

}