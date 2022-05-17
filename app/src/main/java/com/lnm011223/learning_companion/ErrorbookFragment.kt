package com.lnm011223.learning_companion

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.fragment_errorbook.*
import kotlinx.android.synthetic.main.fragment_exercise.*


class ErrorbookFragment : Fragment() {
    lateinit var receiver: ErrorBookChangeReceiver
    val dbHelper = MyDatabaseHelper_error(MyApplication.context,"ErrorBookData.db",1)
    val db = dbHelper.writableDatabase
    var errorbooklist = ArrayList<ErrorBookQuestion>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initErrorBookList()
        val layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        errorbook_recycleview.layoutManager = layoutManager
        val adapter = ErrorBookAdapter(errorbooklist)
        errorbook_recycleview.adapter = adapter

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_errorbook, container, false)
    }

    override fun onStart() {
        super.onStart()
        Log.d("aaa","strat")
    }

    override fun onResume() {
        super.onResume()
        Log.d("aaa","resume")
        val intentFilter = IntentFilter()
        intentFilter.addAction("ErrorBookDataChange")
        receiver = ErrorBookChangeReceiver()
        activity?.registerReceiver(receiver,intentFilter)
    }

    override fun onPause() {
        super.onPause()
        Log.d("aaa","pause")
        activity?.unregisterReceiver(receiver)

    }

    @SuppressLint("Range")
    private fun initErrorBookList() {
        errorbooklist.clear()
        val cursor = db.rawQuery("select * from ErrorBook",null)

        if (cursor.moveToFirst()) {
            do {
                val title = cursor.getString(cursor.getColumnIndex("title")).toString()
                val answer = cursor.getString(cursor.getColumnIndex("answer")).toString()
                val video_url = cursor.getString(cursor.getColumnIndex("video_url")).toString()
                errorbooklist.add(ErrorBookQuestion(title,answer,video_url,false))


            }while (cursor.moveToNext())
            cursor.close()
        }
    }
    inner class ErrorBookChangeReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            initErrorBookList()
            val layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
            errorbook_recycleview.layoutManager = layoutManager
            val adapter = ErrorBookAdapter(errorbooklist)
            errorbook_recycleview.adapter = adapter
        }
    }
}