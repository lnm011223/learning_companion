package com.lnm011223.learning_companion

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView

class QuestionAdapter (val questionlist: List<Question>) : RecyclerView.Adapter<QuestionAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val questiontitle_text: TextView = view.findViewById(R.id.questiontitle_text)
        val answer_input: EditText = view.findViewById(R.id.answer_input)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.question_item,parent,false)
        val viewHolder = ViewHolder(view)
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            val question = questionlist[position]
            Toast.makeText(parent.context,"${question.answer} ${question.score} ${question.video_url} ${question.similarities_id}",Toast.LENGTH_SHORT).show()
            //val bundle = Bundle()

            //Navigation.findNavController(view).navigate(R.id.action_bookFragment_to_weekFragment,bundle)
        }



        return viewHolder
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: QuestionAdapter.ViewHolder, position: Int) {
        val question = questionlist[position]
        holder.questiontitle_text.text = "${position + 1}.${question.title}"

        holder.answer_input.addTextChangedListener {
            if (holder.answer_input.text.toString() == question.answer) {
                question.isRight = true
                Log.d("aaa","${question.isRight}")
            }else{
                question.isRight = false
                Log.d("aaa","${question.isRight}")
            }
        }
    }

    override fun getItemCount() = questionlist.size

}