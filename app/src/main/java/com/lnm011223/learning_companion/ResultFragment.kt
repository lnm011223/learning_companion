package com.lnm011223.learning_companion

import android.annotation.SuppressLint
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("aaa","test")
        comments_text.setOnClickListener { Navigation.findNavController(comments_text).navigate(R.id.action_resultFragment_to_moreFragment) }
        resultModel = ViewModelProvider(this.requireActivity()).get(ResultModel::class.java)
        if (arguments?.getBoolean("flag")!!) {
            resultlist_other = requireArguments().getParcelableArrayList<Question>("questionlist") as ArrayList<Question>
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
        for (i in resultlist) {
            if (i.isRight) {
                score += i.score
            }
        }
        score_text.text = "我的得分：$score"
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