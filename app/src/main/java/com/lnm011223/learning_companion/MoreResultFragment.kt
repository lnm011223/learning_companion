package com.lnm011223.learning_companion

import android.content.ContentValues
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.fragment_more_result.*
import kotlinx.android.synthetic.main.fragment_result.*


class MoreResultFragment : Fragment() {
    var resultlist = ArrayList<Question>()
    val dbHelper = MyDatabaseHelper_error(MyApplication.context,"ErrorBookData.db",1)
    val db = dbHelper.writableDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        resultlist = requireArguments().getParcelableArrayList<Question>("questionlist") as ArrayList<Question>
        for (i in resultlist) {
            if (!i.isRight) {
                val values = ContentValues().apply {
                    put("title", i.title)
                    put("answer", i.answer)
                    put("video_url", i.video_url)
                }
                db.insert("ErrorBook", null, values)
            }
        }
        val layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        moreresult_recycleview.layoutManager = layoutManager
        val adapter = MoreResultAdapter(resultlist)
        moreresult_recycleview.adapter = adapter
        var score: Int = 0
        var right_score = 0
        for (i in resultlist) {
            right_score += i.score
            if (i.isRight) {
                score += i.score
            }
        }
        if (score == right_score) {
            morecomments_text.text = "你是最棒的！"
        }else{
            morecomments_text.text = "错题已收录到你的错题本！"
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_more_result, container, false)
    }


}