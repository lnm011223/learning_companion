package com.lnm011223.learning_companion

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat

import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView

class ErrorBookAdapter (val errorlist: List<ErrorBookQuestion>) : RecyclerView.Adapter<ErrorBookAdapter.ViewHolder>() {
    val dbHelper = MyDatabaseHelper_error(MyApplication.context,"ErrorBookData.db",1)
    val db = dbHelper.writableDatabase
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val errortitle_text: TextView = view.findViewById(R.id.errortitle_text)
        val erroranswer_input: EditText = view.findViewById(R.id.erroranswer_input)
        val showanswer_btn: Button = view.findViewById(R.id.showanswer_btn)
        val deleteerror_btn: Button = view.findViewById(R.id.deleteerror_btn)
        val rightanswer_text: TextView = view.findViewById(R.id.rightanswer_text)
        val errorvideo_btn: Button = view.findViewById(R.id.errorvideo_btn)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.errorbook_item,parent,false)
        val viewHolder = ViewHolder(view)
        //viewHolder.itemView.setOnClickListener {
        //    val position = viewHolder.adapterPosition
        //    val errorBookQuestion = errorlist[position]
        //
        //    //val bundle = Bundle()
//
        //    //Navigation.findNavController(view).navigate(R.id.action_bookFragment_to_weekFragment,bundle)
        //}
        //viewHolder.showanswer_btn.setOnClickListener {
        //    val position = viewHolder.adapterPosition
        //    val errorBookQuestion = errorlist[position]
        //
        //}
        viewHolder.deleteerror_btn.setOnClickListener {
            val position = viewHolder.adapterPosition
            val errorBookQuestion = errorlist[position]
            AlertDialog.Builder(parent.context).apply {
                setTitle("确认：")
                setMessage("真的要删除这条记录吗？")
                setNegativeButton("否") { _, _ ->


                }
                setPositiveButton("是") { _, _ ->
                    db.delete("ErrorBook", "title = ?", arrayOf(errorBookQuestion.title))
                    val intent = Intent("ErrorBookDataChange")

                    context.sendBroadcast(intent)

                }


                show()
            }
        }

        viewHolder.errorvideo_btn.setOnClickListener {
            val position = viewHolder.adapterPosition
            val errorBookQuestion = errorlist[position]
            val bundle = Bundle()
            bundle.putString("video_url",errorBookQuestion.video_url)
            Navigation.findNavController(viewHolder.errorvideo_btn).navigate(R.id.action_errorbookFragment_to_errorVideoFragment,bundle)
        }



        return viewHolder
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ErrorBookAdapter.ViewHolder, position: Int) {
        val errorBookQuestion = errorlist[position]
        holder.errortitle_text.text = "${position+1}.${errorBookQuestion.title}"
        holder.showanswer_btn.setOnClickListener {
            holder.rightanswer_text.text = errorBookQuestion.answer
            if (errorBookQuestion.isRight) {
                holder.erroranswer_input.setTextColor(ContextCompat.getColorStateList(MyApplication.context,R.color.blue))
            }else{
                holder.erroranswer_input.setTextColor(ContextCompat.getColorStateList(MyApplication.context,R.color.red))
            }
        }
        holder.erroranswer_input.addTextChangedListener {
            if (holder.erroranswer_input.text.toString() == errorBookQuestion.answer) {
                errorBookQuestion.isRight = true
                Log.d("aaa","${errorBookQuestion.isRight}")
            }else{
                errorBookQuestion.isRight = false
                Log.d("aaa","${errorBookQuestion.isRight}")
            }
        }
    }

    override fun getItemCount() = errorlist.size

}
