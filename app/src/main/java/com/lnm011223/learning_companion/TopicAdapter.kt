package com.lnm011223.learning_companion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView

class TopicAdapter(val topiclist: List<Topic>) : RecyclerView.Adapter<TopicAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var topictext: TextView = view.findViewById(R.id.topic_text)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.topic_item,parent,false)
        val viewHolder = ViewHolder(view)
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            val topic = topiclist[position]
            //Toast.makeText(parent.context,"${week.week}${week.subject}${week.term}",Toast.LENGTH_SHORT).show()
            val bundle = Bundle()

            bundle.apply {
                putString("subject",topic.subject)
                putString("term",topic.term)
                putString("week",topic.week)
                putString("topic",topic.topic)
                putString("topic_type",topic.topic_type)
            }
            when(topic.topic_type) {
                "video" -> Navigation.findNavController(parent).navigate(R.id.action_topicsFragment_to_videoFragment,bundle)
                "exercise" -> Navigation.findNavController(parent).navigate(R.id.action_topicsFragment_to_exerciseFragment,bundle)
            }



        }


        return viewHolder
    }

    override fun onBindViewHolder(holder: TopicAdapter.ViewHolder, position: Int) {
        val topic = topiclist[position]
        holder.topictext.text = topic.topic
    }

    override fun getItemCount() = topiclist.size

}