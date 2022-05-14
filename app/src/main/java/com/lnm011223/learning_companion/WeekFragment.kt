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
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.fragment_book.*
import kotlinx.android.synthetic.main.fragment_week.*


class WeekFragment : Fragment() {

    lateinit var topicModel: TopicModel
    private val weeklist = ArrayList<Topic>()
    private val weeknumberlist = arrayListOf<String>("一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二", "十三", "十四", "十五", "十六", "十七", "十八", "十九", "二十")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        topicModel = ViewModelProvider(this.activity!!).get(TopicModel::class.java)
        if (!arguments?.getString("subject").toString().equals("null")) {
            if (!arguments?.getString("term").toString().equals("null")){
            topicModel.topic = Topic("","","","",arguments?.getString("subject").toString(),
                arguments?.getString("term").toString()
            )
                Log.d("aaa","${topicModel.topic.subject} ${topicModel.topic.term}")
            }
        }
        Toast.makeText(context,"${topicModel.topic.subject} ${topicModel.topic.term}", Toast.LENGTH_SHORT).show()
        errorbook_button.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_weekFragment_to_errorbookFragment))
        initWeeks()
        val layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        week_recycleview.layoutManager = layoutManager
        val adapter = WeekAdapter(weeklist)
        week_recycleview.adapter = adapter
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_week, container, false)
    }
    private fun initWeeks() {
        weeklist.clear()
        for (i in weeknumberlist) {
            weeklist.add(Topic("","","","第${i}周", topicModel.topic.subject,topicModel.topic.term))
        }
    }

}