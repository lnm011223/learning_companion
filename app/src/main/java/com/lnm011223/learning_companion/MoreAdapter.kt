package com.lnm011223.learning_companion

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView

class MoreAdapter (val morelist: List<Question>) : RecyclerView.Adapter<MoreAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val questiontitle_text: TextView = view.findViewById(R.id.moretitle_text)
        val answer_input: EditText = view.findViewById(R.id.moreanswer_input)
        val moreid_text: TextView = view.findViewById(R.id.moreid_text)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.more_item,parent,false)
        val viewHolder = ViewHolder(view)
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            val more = morelist[position]

            //val bundle = Bundle()

            //Navigation.findNavController(view).navigate(R.id.action_bookFragment_to_weekFragment,bundle)
        }



        return viewHolder
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MoreAdapter.ViewHolder, position: Int) {
        val more = morelist[position]
        holder.moreid_text.text = "相似${position + 1}."
        holder.questiontitle_text.text = "${more.title}"

        holder.answer_input.addTextChangedListener {
            if (holder.answer_input.text.toString() == more.answer) {
                more.isRight = true
                Log.d("aaa","${more.isRight}")
            }else{
                more.isRight = false
                Log.d("aaa","${more.isRight}")
            }
        }
    }

    override fun getItemCount() = morelist.size

}