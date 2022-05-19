package com.lnm011223.learning_companion

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.util.Log
import android.widget.Toast







/**
 * 开机自启动
 */
class BootCompleteReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == ACTION) {
            Toast.makeText(context,"Boot",Toast.LENGTH_SHORT).show()
            val newIntent = Intent(context, MainActivity::class.java)  // 要启动的Activity

            //1.如果自启动APP，参数为需要自动启动的应用包名
            //val newIntent = context.packageManager.getLaunchIntentForPackage(context.packageName)
            //下面这句话必须加上才能开机自动运行app的界面
            newIntent.addFlags(FLAG_ACTIVITY_NEW_TASK)
            //2.如果自启动Activity
            context.startActivity(newIntent)
            //3.如果自启动服务
//            context.startService(newIntent);
        }
    }

    companion object {
        const val ACTION = "android.intent.action.BOOT_COMPLETED"
    }
}