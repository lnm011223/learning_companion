package com.lnm011223.learning_companion

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager

import kotlinx.android.synthetic.main.fragment_more.*


class MoreFragment : Fragment() {
    val dbHelper = MyDatabaseHelper(MyApplication.context,"LearningData.db",1)
    val db = dbHelper.writableDatabase
    var similarities_id_get = -1
    var questionlist = ArrayList<Question>()
    lateinit var moreModel: MoreModel
    //mainflag控制按钮是否开始暂停，flag判断是否是刚开始，还是按了暂停以后又开始
    var flag = true
    var mainflag = false
    var completeflag = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        moreModel = ViewModelProvider(this).get(MoreModel::class.java)
        similarities_id_get = arguments?.getInt("similarities_id")!!
        //在这接收不同倒计时的时间
        moreModel.surplustime = 70 * 1000
        if (isDarkTheme(requireActivity())) {
            Log.d("aaa","yes")
            morestartexercise_btn.iconTint = ContextCompat.getColorStateList(requireActivity(),R.color.white)
            morestartexercise_btn.setTextColor(ContextCompat.getColorStateList(requireActivity(),R.color.white)!!)
        }

        if (flag) {
            morestartexercise_btn.text = "开始答题"
            morestartexercise_btn.icon = ContextCompat.getDrawable(requireActivity(),R.drawable.ic_baseline_edit_24)
        }
        morestartexercise_btn.setOnClickListener {
            if (morestartexercise_btn.isExtended) {
                morestartexercise_btn.shrink()
                if (flag) {
                    initsimilarities()
                    morestartexercise_btn.icon = ContextCompat.getDrawable(requireActivity(),R.drawable.ic_baseline_pause_24)
                    mainflag = true
                    timeStemp = moreModel.surplustime
                    timer?.cancel()
                    getCountDownTime()
                    //按下开始后便不是从未开始了
                    flag = false
                }else{
                    moreexercisequestion_recycleview.visibility = View.VISIBLE
                    mainflag = true
                    morestartexercise_btn.icon = ContextCompat.getDrawable(requireActivity(),R.drawable.ic_baseline_pause_24)
                    timeStemp = moreModel.surplustime
                    timer?.cancel()
                    getCountDownTime()
                }



            }else{
                morestartexercise_btn.extend()
                moreexercisequestion_recycleview.visibility = View.INVISIBLE
                mainflag = false
                morestartexercise_btn.text = "继续答题"
                morestartexercise_btn.icon = ContextCompat.getDrawable(requireActivity(),R.drawable.ic_baseline_play_arrow_24)
                moreModel.surplustime = timeStemp
                timer?.cancel()

            }
        }

        morecomplete_Button.setOnClickListener {
            if (!completeflag) {
                moreModel.surplustime = timeStemp
                timer?.cancel()
                moretime_text.setTextColor(
                    ContextCompat.getColorStateList(
                        requireActivity(),
                        R.color.red
                    )
                )
                morestartexercise_btn.extend()
                morestartexercise_btn.text = "已提交！"
                morestartexercise_btn.setBackgroundColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.red
                    )
                )
                morestartexercise_btn.strokeWidth = 0
                morestartexercise_btn.isClickable = false
                morecomplete_Button.text = "查看结果"
                morecomplete_Button.icon = ContextCompat.getDrawable(requireActivity(),R.drawable.ic_baseline_notes_24)
                morestartexercise_btn.icon = ContextCompat.getDrawable(requireActivity(),R.drawable.ic_baseline_notifications_active_24)
                completeflag = true

            }else{
                val bundle = Bundle()
                bundle.putBoolean("flag",true)
                bundle.putParcelableArrayList("questionlist",moreModel.questionlist)
                Navigation.findNavController(morecomplete_Button).navigate(R.id.action_moreFragment_to_moreResultFragment,bundle)
            }
        }


        val layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        moreexercisequestion_recycleview.layoutManager = layoutManager
        val adapter = MoreAdapter(moreModel.questionlist)
        moreexercisequestion_recycleview.adapter = adapter

    }
    private var timeStemp:Int? = null
    override fun onStart() {
        super.onStart()
        timeStemp = moreModel.surplustime
        Log.d("aaa",timeStemp.toString())
        if (mainflag) {
            getCountDownTime()
        }

        Log.d("aaa","start")
    }

    override fun onStop() {
        super.onStop()
        Log.d("aaa","stop")
        timer?.cancel()

    }

    override fun onPause() {
        super.onPause()
        Log.d("aaa","pause")
        moreModel.surplustime = timeStemp
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_more, container, false)
    }
    @SuppressLint("Range")
    private fun initsimilarities() {
        moreModel.questionlist.clear()
        val cursor = db.rawQuery("select * from Question where similarities_id = $similarities_id_get",null)

        if (cursor.moveToFirst()) {
            do {
                val title = cursor.getString(cursor.getColumnIndex("title")).toString()
                val answer = cursor.getString(cursor.getColumnIndex("answer")).toString()
                val score = cursor.getInt(cursor.getColumnIndex("score")).toInt()
                val video_url = cursor.getString(cursor.getColumnIndex("video_url")).toString()
                val similarities_id = cursor.getInt(cursor.getColumnIndex("similarities_id"))
                Log.d("aaa","yes")
                moreModel.questionlist.add(Question(title,answer,score,video_url,similarities_id,false))
                Log.d("aaa",title)

            }while (cursor.moveToNext())
            cursor.close()
        }
    }
    private var timer: CountDownTimer? = null
    private fun getCountDownTime() {
        //timeStemp = 63 * 1000
        timer = object : CountDownTimer(timeStemp!!.toLong(), 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(l: Long) {
                //val day = l / (1000 * 24 * 60 * 60) //单位天
                //val hour = (l - day * (1000 * 24 * 60 * 60)) / (1000 * 60 * 60) //单位时
                //val minute =
                //    (l - day * (1000 * 24 * 60 * 60) - hour * (1000 * 60 * 60)) / (1000 * 60) //单位分
                //val second =
                //    (l - day * (1000 * 24 * 60 * 60) - hour * (1000 * 60 * 60) - minute * (1000 * 60)) / 1000 //单位秒

                val minute = l / (60 * 1000)
                val second = ( l - minute * (60 * 1000) ) / 1000

                timeStemp = l.toInt()
                //Log.d("aaa",timeStemp.toString())
                moretime_text.text = "${String.format("%02d", minute)}:${String.format("%02d", second)}"

            }

            override fun onFinish() {

                //倒计时为0时执行此方法
                Toast.makeText(context,"时间结束！", Toast.LENGTH_SHORT).show()
                moretime_text.setTextColor(ContextCompat.getColorStateList(requireActivity(),R.color.red))
                morestartexercise_btn.extend()
                morestartexercise_btn.text = "时间结束，已自动提交！"
                morestartexercise_btn.setBackgroundColor(ContextCompat.getColor(requireActivity(),R.color.red))
                morestartexercise_btn.icon = ContextCompat.getDrawable(requireActivity(),R.drawable.ic_baseline_notifications_active_24)
                morestartexercise_btn.strokeWidth = 0
                morestartexercise_btn.isClickable = false
                completeflag = true
                morecomplete_Button.text = "查看结果"
                morecomplete_Button.icon = ContextCompat.getDrawable(requireActivity(),R.drawable.ic_baseline_notes_24)
            }
        }
        (timer as CountDownTimer).start()
    }

    private fun isDarkTheme(context: Context): Boolean {
        val flag = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return flag == Configuration.UI_MODE_NIGHT_YES
    }



}