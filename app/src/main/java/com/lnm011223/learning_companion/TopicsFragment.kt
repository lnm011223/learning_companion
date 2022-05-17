package com.lnm011223.learning_companion

import android.annotation.SuppressLint
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
    private var topiclist = ArrayList<Topic>()
    val dbHelper = MyDatabaseHelper(MyApplication.context,"LearningData.db",1)
    val db = dbHelper.writableDatabase
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
        topiclist = topicModel.topiclist
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
    @SuppressLint("Range")
    private fun initTopics() {
        topicModel.topiclist.clear()
        //val cursor = db.query("Topic",null,null,null,null,null,null)
        val cursor = db.rawQuery("select * from Topic where subject ='${topicModel.topic.subject}' and term = '${topicModel.topic.term}' and week = '${topicModel.topic.week}'",null)
        if (cursor.moveToFirst()) {
            do {
                val topic = cursor.getString(cursor.getColumnIndex("topic")).toString()
                val topic_type = cursor.getString(cursor.getColumnIndex("topic_type")).toString()
                val video_url = cursor.getString(cursor.getColumnIndex("video_url")).toString()
                Log.d("aaa","yes")
                Log.d("aaa","$topic + $topic_type + $video_url")
                topicModel.topiclist.add(Topic(topic,topic_type,video_url,topicModel.topic.week,topicModel.topic.subject,topicModel.topic.term))
            }while (cursor.moveToNext())
            cursor.close()
        }
    }

}