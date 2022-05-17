package com.lnm011223.learning_companion

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.AssetManager
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.system.Os.open
import android.widget.Toast
import com.lnm011223.learning_companion.MyApplication.Companion.context
import java.io.FileOutputStream
import java.nio.channels.AsynchronousFileChannel.open
import java.nio.channels.AsynchronousServerSocketChannel.open
import java.nio.channels.DatagramChannel.open

class MyDatabaseHelper_error(context: Context, name: String, version: Int
) : SQLiteOpenHelper(context, name, null,version) {
    @SuppressLint("SdCardPath")
    private val dbpath = "/data/data/com.lnm011223.learning_companion/databases"
    override fun onCreate(db: SQLiteDatabase) {

        db.execSQL(creatErrorBook)

        Toast.makeText(context,"Create succeeded",Toast.LENGTH_SHORT).show()
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }




    private val creatErrorBook = "create table ErrorBook (" +
            "id integer primary key autoincrement," +
            "title text unique," +
            "answer text," +
            "video_url text)"




}