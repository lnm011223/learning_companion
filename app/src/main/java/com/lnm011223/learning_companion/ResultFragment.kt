package com.lnm011223.learning_companion

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.fragment_exercise.*
import kotlinx.android.synthetic.main.fragment_result.*



class ResultFragment : Fragment() {
    var resultlist = ArrayList<Question>()
    var resultlist_other = ArrayList<Question>()
    lateinit var resultModel: ResultModel
    val dbHelper = MyDatabaseHelper_error(MyApplication.context,"ErrorBookData.db",1)
    val db = dbHelper.writableDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("aaa","test")

        resultModel = ViewModelProvider(this.requireActivity()).get(ResultModel::class.java)
        if (arguments?.getBoolean("flag")!!) {
            resultlist_other = requireArguments().getParcelableArrayList<Question>("questionlist") as ArrayList<Question>
            for (i in resultlist_other) {
                if (!i.isRight) {
                    val values = ContentValues().apply {
                        put("title", i.title)
                        put("answer", i.answer)
                        put("video_url", i.video_url)
                    }
                    db.insert("ErrorBook", null, values)
                }
            }
        }


        initResults()
        if (resultModel.resultlist.isNotEmpty()){
            Log.d("aaa","not")
        }

    }

    override fun onStart() {
        super.onStart()
        Log.d("aaa","start")
        val layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        result_recycleview.layoutManager = layoutManager
        val adapter = ResultAdapter(resultlist)
        result_recycleview.adapter = adapter
        var score: Int = 0
        var right_score = 0
        for (i in resultlist) {
            right_score += i.score
            if (i.isRight) {
                score += i.score
            }
        }
        var level = score.toDouble()/right_score.toDouble()
        score_text.text = "我的得分：$score"
        comments_text.text = when {
            level < 0.6 -> "等级1"
            level >= 0.6 && level < 0.7 -> "等级2"
            level >= 0.7 && level < 0.8 -> "等级3"
            level >= 0.8 && level < 0.9 -> "等级4"
            level >= 0.9  -> "等级5"

            else -> {"else"}
        }
    }

    override fun onStop() {
        super.onStop()

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    private fun initResults() {
        if (resultlist_other.isNotEmpty()) {
            Log.d("aaa","yes")
            resultModel.resultlist = resultlist_other
            resultlist = resultModel.resultlist
        }else{
            Log.d("aaa","no")
            resultlist = resultModel.resultlist
        }
    }
}