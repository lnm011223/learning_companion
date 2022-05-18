package com.lnm011223.learning_companion

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.fragment_book.*


class BookFragment : Fragment() {
    var subject = String()
    private val booklist = ArrayList<Topic>()
    private val termlist = arrayListOf<String>("三年级上册","三年级下册","四年级上册","四年级下册","五年级上册","五年级下册","六年级上册","六年级下册")
    lateinit var topicModel: TopicModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        topicModel = ViewModelProvider(this.requireActivity()).get(TopicModel::class.java)
        subject = arguments?.getString("subject").toString()
        if (subject != "null") {
            topicModel.topic.subject = subject
        }
        initBooks()
        val layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        term_recycleview.layoutManager = layoutManager
        val adapter = BookAdapter(booklist)
        term_recycleview.adapter = adapter
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_book, container, false)

    }
    private fun initBooks() {
        booklist.clear()
        for (i in termlist) {
            booklist.add(Topic("null","null","null","null",topicModel.topic.subject,i))
        }
    }

}