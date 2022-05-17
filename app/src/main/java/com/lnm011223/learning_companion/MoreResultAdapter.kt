package com.lnm011223.learning_companion

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView

class MoreResultAdapter (val resultlist: List<Question>) : RecyclerView.Adapter<MoreResultAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val resulttitle_text: TextView = view.findViewById(R.id.moreresulttitle_text)
        val resultanswer_text: TextView = view.findViewById(R.id.moreresultanswer_text)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.moreresult_item,parent,false)
        val viewHolder = ViewHolder(view)

        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            val result = resultlist[position]
            //Toast.makeText(parent.context,"${question.answer} ${question.score} ${question.video_url} ${question.similarities_id}",Toast.LENGTH_SHORT).show()
            //val bundle = Bundle()

            //Navigation.findNavController(view).navigate(R.id.action_bookFragment_to_weekFragment,bundle)
        }




        return viewHolder
    }


    @SuppressLint("ResourceAsColor", "SetTextI18n")
    override fun onBindViewHolder(holder: MoreResultAdapter.ViewHolder, position: Int) {
        val result = resultlist[position]
        holder.resulttitle_text.text = "${position+1}.${result.title}"
        holder.resultanswer_text.text = result.answer
        if (!result.isRight) {
            holder.resulttitle_text.setTextColor(ContextCompat.getColorStateList(MyApplication.context,R.color.red))
            holder.resultanswer_text.setTextColor(ContextCompat.getColorStateList(MyApplication.context,R.color.red))

        }

    }

    override fun getItemCount() = resultlist.size

}