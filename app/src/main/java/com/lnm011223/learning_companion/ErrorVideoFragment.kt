package com.lnm011223.learning_companion

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.fragment_error_video.*
import kotlinx.android.synthetic.main.fragment_video.*


class ErrorVideoFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_error_video, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var url = ""
        url = arguments?.getString("video_url").toString()
        Log.d("aaa",url)
        errorvideo_webview.settings.javaScriptEnabled = true
        errorvideo_webview.webViewClient = WebViewClient()
        errorvideo_webview.loadUrl(url)
    }
}