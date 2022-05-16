package com.lnm011223.learning_companion


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_video.*


class VideoFragment : Fragment() {
    lateinit var topicModel: TopicModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        topicModel = ViewModelProvider(this.requireActivity()).get(TopicModel::class.java)
        if (!arguments?.getString("subject").toString().equals("null")) {
            if (!arguments?.getString("term").toString().equals("null")){
                if (!arguments?.getString("week").toString().equals("null"))
                    if (!arguments?.getString("topic").toString().equals("null"))
                        if (!arguments?.getString("topic_type").toString().equals("null"))
                    topicModel.topic = Topic(arguments?.getString("topic").toString(),arguments?.getString("topic_type").toString(),"",arguments?.getString("week").toString(),arguments?.getString("subject").toString(),
                        arguments?.getString("term").toString()
                    )
                Log.d("aaa","${topicModel.topic.topic} ${topicModel.topic.topic_type} ${topicModel.topic.week} ${topicModel.topic.subject} ${topicModel.topic.term}")
            }
        }
        Toast.makeText(context,"${topicModel.topic.topic} ${topicModel.topic.topic_type} ${topicModel.topic.week} ${topicModel.topic.subject} ${topicModel.topic.term}", Toast.LENGTH_SHORT).show()
        //val mediaController = android.widget.MediaController(activity)
        //mediaController.setAnchorView(video_view)
        //val url = "https://poss-videocloud.cns.com.cn/oss/2021/05/08/chinanews/MEIZI_YUNSHI/onair/25AFA3CA2F394DB38420CC0A44483E82.mp4"
        //video_view.setMediaController(mediaController)
        //video_view.setVideoURI(Uri.parse(url))
        //video_view.start()

        var url = ""
        url = arguments?.getString("video_rul").toString()
        bili_web.settings.javaScriptEnabled = true
        bili_web.webViewClient = WebViewClient()
        bili_web.loadUrl(url)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video, container, false)
    }



}