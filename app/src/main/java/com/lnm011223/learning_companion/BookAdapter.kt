package com.lnm011223.learning_companion

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView


class BookAdapter(val booklist: List<Book>) : RecyclerView.Adapter<BookAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var termid_text:TextView = view.findViewById(R.id.termid_text)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.term_item,parent,false)
        val viewHolder = ViewHolder(view)
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            val book = booklist[position]
            Toast.makeText(parent.context,"${book.subject} ${book.term}",Toast.LENGTH_SHORT).show()
            val bundle = Bundle()
            bundle.putString("subject",book.subject)
            bundle.putString("term",book.term)
            Navigation.findNavController(view).navigate(R.id.action_bookFragment_to_weekFragment,bundle)
        }


        return viewHolder
    }

    override fun onBindViewHolder(holder: BookAdapter.ViewHolder, position: Int) {
        val book = booklist[position]
        holder.termid_text.text = book.term
    }

    override fun getItemCount() = booklist.size

}