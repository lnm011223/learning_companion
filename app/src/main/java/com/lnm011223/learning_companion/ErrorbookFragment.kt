package com.lnm011223.learning_companion

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider


class ErrorbookFragment : Fragment() {
    lateinit var topicModel: TopicModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        topicModel = ViewModelProvider(this.requireActivity()).get(TopicModel::class.java)
        Log.d("aaa","${topicModel.topic.subject} ${topicModel.topic.term}")
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_errorbook, container, false)
    }


}