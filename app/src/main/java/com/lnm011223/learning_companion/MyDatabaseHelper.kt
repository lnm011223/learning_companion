package com.lnm011223.learning_companion

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.AssetManager
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.system.Os.open
import android.util.Log
import android.widget.Toast
import com.lnm011223.learning_companion.MyApplication.Companion.context
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.nio.channels.AsynchronousFileChannel.open
import java.nio.channels.AsynchronousServerSocketChannel.open
import java.nio.channels.DatagramChannel.open

class MyDatabaseHelper(context: Context, name: String, version: Int
) : SQLiteOpenHelper(context, name, null,version) {
    @SuppressLint("SdCardPath")
    private val dbpath = "/data/data/com.lnm011223.learning_companion/databases"
    override fun onCreate(db: SQLiteDatabase) {
        //db.execSQL(creatQuestion)
        //db.execSQL(createTopic)
        //copyFileFromAssets(MyApplication.context,"LearningData.db","/data/data/com.lnm011223.learning_companion/databases","LearningData.db")

        Toast.makeText(context,"Create succeeded",Toast.LENGTH_SHORT).show()
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }


    private val createTopic = "create table Topic (" +
            "id integer primary key autoincrement," +
            "subject text," +
            "term text," +
            "week text," +
            "topic text," +
            "topic_type text," +
            "video_url text)"

    private val creatQuestion = "create table Question (" +
            "id integer primary key autoincrement," +
            "subject text," +
            "term text," +
            "week text," +
            "topic text," +
            "title text," +
            "answer text," +
            "score integer," +
            "video_url text," +
            "similarities_id integer)"


    fun copyFileFromAssets(
        context: Context,
        assetName: String,
        savePath: String,
        saveName: String
    ) {
        // 若目标文件夹不存在，则创建
        //val dir = File(Environment.getExternalStorageDirectory().toString() + "/" + savePath)
        //if (!dir.exists()) {
        //    if (!dir.mkdir()) {
        //        Log.d("FileUtils", "mkdir error: $savePath")
        //        return
        //    }
        //}

        // 拷贝文件
        val filename = savePath + "/" + saveName
        val file = File(filename)
        if (!file.exists()) {
            try {
                val inStream: InputStream = context.assets.open(assetName)
                val fileOutputStream = FileOutputStream(filename)
                var byteread: Int
                val buffer = ByteArray(1024)
                while (inStream.read(buffer).also { byteread = it } != -1) {
                    fileOutputStream.write(buffer, 0, byteread)
                }
                fileOutputStream.flush()
                inStream.close()
                fileOutputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            Log.d("FileUtils", "[copyFileFromAssets] copy asset file: $assetName to : $filename")
            Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT).show()
        } else {
            Log.d("FileUtils", "[copyFileFromAssets] file is exist: $filename")
            //Toast.makeText(context, "已存在", Toast.LENGTH_SHORT).show()
        }
    }




}