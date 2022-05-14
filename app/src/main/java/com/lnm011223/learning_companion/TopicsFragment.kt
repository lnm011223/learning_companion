package com.lnm011223.learning_companion

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.fragment_topics.*
import kotlinx.android.synthetic.main.fragment_week.*


class TopicsFragment : Fragment() {
    lateinit var topicModel: TopicModel
    private val topiclist = ArrayList<Topic>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        topicModel = ViewModelProvider(this.requireActivity()).get(TopicModel::class.java)
        if (!arguments?.getString("subject").toString().equals("null")) {
            if (!arguments?.getString("term").toString().equals("null")){
                if (!arguments?.getString("week").toString().equals("null"))
                topicModel.topic = Topic("","","",arguments?.getString("week").toString(),arguments?.getString("subject").toString(),
                    arguments?.getString("term").toString()
                )
                Log.d("aaa","${topicModel.topic.week} ${topicModel.topic.subject} ${topicModel.topic.term}")
            }
        }
        Toast.makeText(context,"${topicModel.topic.week} ${topicModel.topic.subject} ${topicModel.topic.term}", Toast.LENGTH_SHORT).show()

        initTopics()
        val layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        topic_recycleview.layoutManager = layoutManager
        val adapter = TopicAdapter(topiclist)
        topic_recycleview.adapter = adapter
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_topics, container, false)
    }
    private fun initTopics() {
        topiclist.clear()
        topiclist.add(Topic("专题一","video","",topicModel.topic.week,topicModel.topic.subject,topicModel.topic.term))
        topiclist.add(Topic("专题一","exercise","",topicModel.topic.week,topicModel.topic.subject,topicModel.topic.term))

        topiclist.add(Topic("专题二","video","",topicModel.topic.week,topicModel.topic.subject,topicModel.topic.term))
        topiclist.add(Topic("专题二","exercise","",topicModel.topic.week,topicModel.topic.subject,topicModel.topic.term))


        topiclist.add(Topic("检测试卷","exercise","",topicModel.topic.week,topicModel.topic.subject,topicModel.topic.term))
    }

}