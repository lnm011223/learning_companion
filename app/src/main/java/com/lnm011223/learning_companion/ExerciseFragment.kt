package com.lnm011223.learning_companion




import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_exercise.*


class ExerciseFragment : Fragment() {
    lateinit var topicModel: TopicModel
    lateinit var timeModel: TimeModel
    //mainflag控制按钮是否开始暂停，flag判断是否是刚开始，还是按了暂停以后又开始
    var flag = true
    var mainflag = false
    var completeflag = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("aaa","create")
    }

    @SuppressLint("ResourceType")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        topicModel = ViewModelProvider(this.requireActivity()).get(TopicModel::class.java)
        timeModel = ViewModelProvider(this).get(TimeModel::class.java)
        if (arguments?.getString("subject").toString() != "null") {
            if (arguments?.getString("term").toString() != "null"){
                if (arguments?.getString("week").toString() != "null")
                    if (arguments?.getString("topic").toString() != "null")
                        if (!arguments?.getString("topic_type").toString().equals("null"))
                            topicModel.topic = Topic(arguments?.getString("topic").toString(),arguments?.getString("topic_type").toString(),"",arguments?.getString("week").toString(),arguments?.getString("subject").toString(),
                                arguments?.getString("term").toString()
                            )
                Log.d("aaa","${topicModel.topic.topic} ${topicModel.topic.topic_type} ${topicModel.topic.week} ${topicModel.topic.subject} ${topicModel.topic.term}")
            }
        }
        Toast.makeText(context,"${topicModel.topic.topic} ${topicModel.topic.topic_type} ${topicModel.topic.week} ${topicModel.topic.subject} ${topicModel.topic.term}", Toast.LENGTH_SHORT).show()

        //在这接收不同倒计时的时间
        timeModel.surplustime = 63 * 1000
        Log.d("aaa","creatactivity")
        if (isDarkTheme(requireActivity())) {
            Log.d("aaa","yes")
            startexercise_btn.iconTint = ContextCompat.getColorStateList(requireActivity(),R.color.white)
            startexercise_btn.setTextColor(ContextCompat.getColorStateList(requireActivity(),R.color.white)!!)
        }

        if (flag) {
            startexercise_btn.text = "开始答题"
            startexercise_btn.icon = ContextCompat.getDrawable(requireActivity(),R.drawable.ic_baseline_edit_24)
        }
        startexercise_btn.setOnClickListener {
            if (startexercise_btn.isExtended) {
                startexercise_btn.shrink()
                if (flag) {
                    mainflag = true
                    timeStemp = timeModel.surplustime
                    timer?.cancel()
                    getCountDownTime()
                    //按下开始后便不是从未开始了
                    flag = false
                }else{
                    mainflag = true
                    startexercise_btn.icon = ContextCompat.getDrawable(requireActivity(),R.drawable.ic_baseline_pause_24)
                    timeStemp = timeModel.surplustime
                    timer?.cancel()
                    getCountDownTime()
                }



            }else{
                startexercise_btn.extend()
                mainflag = false
                startexercise_btn.text = "继续答题"
                startexercise_btn.icon = ContextCompat.getDrawable(requireActivity(),R.drawable.ic_baseline_play_arrow_24)
                timeModel.surplustime = timeStemp
                timer?.cancel()

            }
        }

        complete_Button.setOnClickListener {
            if (!completeflag) {
                timeModel.surplustime = timeStemp
                timer?.cancel()
                time_text.setTextColor(
                    ContextCompat.getColorStateList(
                        requireActivity(),
                        R.color.red
                    )
                )
                startexercise_btn.extend()
                startexercise_btn.text = "已提交！"
                startexercise_btn.setBackgroundColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.red
                    )
                )
                startexercise_btn.strokeWidth = 0
                startexercise_btn.isClickable = false
                complete_Button.text = "查看结果"
                complete_Button.icon = ContextCompat.getDrawable(requireActivity(),R.drawable.ic_baseline_notes_24)
                completeflag = true

            }else{
                Navigation.findNavController(complete_Button).navigate(R.id.action_exerciseFragment_to_resultFragment)
            }
        }
    }
    private var timeStemp:Int? = null
    override fun onStart() {
        super.onStart()
        timeStemp = timeModel.surplustime
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
        timeModel.surplustime = timeStemp
    }

    override fun onResume() {
        super.onResume()
        Log.d("aaa","resume")
        //timeStemp = topicModel.surplustime!!
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("aaa","destory")
    }

    override fun onDestroyView() {
        super.onDestroyView()

        Log.d("aaa","destoryview")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("aaa","createview")
        return inflater.inflate(R.layout.fragment_exercise, container, false)
    }


    //private var timeStemp:Int = 86400000//1000 * 24 * 60 * 60

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
                time_text.text = "${String.format("%02d", minute)}:${String.format("%02d", second)}"

            }

            override fun onFinish() {

                //倒计时为0时执行此方法
                Toast.makeText(context,"时间结束！",Toast.LENGTH_SHORT).show()
                time_text.setTextColor(ContextCompat.getColorStateList(requireActivity(),R.color.red))
                startexercise_btn.extend()
                startexercise_btn.text = "时间结束，已自动提交！"
                startexercise_btn.setBackgroundColor(ContextCompat.getColor(requireActivity(),R.color.red))
                startexercise_btn.strokeWidth = 0
                startexercise_btn.isClickable = false
                completeflag = true
                complete_Button.text = "查看结果"
                complete_Button.icon = ContextCompat.getDrawable(requireActivity(),R.drawable.ic_baseline_notes_24)
            }
        }
        (timer as CountDownTimer).start()
    }

    private fun isDarkTheme(context: Context): Boolean {
        val flag = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return flag == Configuration.UI_MODE_NIGHT_YES
    }




}