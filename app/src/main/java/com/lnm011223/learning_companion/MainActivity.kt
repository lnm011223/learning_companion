package com.lnm011223.learning_companion


import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream


class MainActivity : AppCompatActivity() {
    lateinit var topicModel: TopicModel
    @SuppressLint("SdCardPath")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val dbHelper = MyDatabaseHelper(this,"LearningData.db",1)
        val dbHelper_error = MyDatabaseHelper_error(this,"ErrorBookData.db",1)
        dbHelper_error.writableDatabase

        dbHelper.copyFileFromAssets(MyApplication.context,"LearningData.db","/data/data/com.lnm011223.learning_companion/databases","LearningData.db")
        val insetsController = WindowCompat.getInsetsController(
            window, window.decorView
        )
        if (!isDarkTheme(this)){

            //insetsController?.isAppearanceLightStatusBars = true
            //insetsController?.isAppearanceLightNavigationBars = true
            insetsController?.apply {
                isAppearanceLightStatusBars = true


            }

        }
        insetsController?.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        insetsController?.hide(WindowInsetsCompat.Type.navigationBars())
        window.statusBarColor = Color.TRANSPARENT
        topicModel = ViewModelProvider(this).get(TopicModel::class.java)
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.bookFragment -> back_button.setOnClickListener{navController.navigate(R.id.action_bookFragment_to_mainFragment)}
                R.id.weekFragment -> back_button.setOnClickListener{navController.navigate(R.id.action_weekFragment_to_bookFragment)}
                R.id.errorbookFragment -> back_button.setOnClickListener { navController.navigate(R.id.action_errorbookFragment_to_weekFragment) }
                R.id.topicsFragment -> back_button.setOnClickListener { navController.navigate(R.id.action_topicsFragment_to_weekFragment) }
                R.id.videoFragment -> back_button.setOnClickListener { navController.navigate(R.id.action_videoFragment_to_topicsFragment) }
                R.id.exerciseFragment -> back_button.setOnClickListener { navController.navigate(R.id.action_exerciseFragment_to_topicsFragment) }
                R.id.explainFragment -> back_button.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putBoolean("flag",false)
                    navController.navigate(R.id.action_explainFragment_to_resultFragment,bundle)
                }
                R.id.resultFragment -> back_button.setOnClickListener { navController.navigate(R.id.action_resultFragment_to_topicsFragment) }
                R.id.moreFragment -> back_button.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putBoolean("flag",false)
                    navController.navigate(R.id.action_moreFragment_to_resultFragment,bundle)
                }
                else -> back_button.setOnClickListener { navController.navigate(R.id.weekFragment) }

            }

        }

    }

    private fun isDarkTheme(context: Context): Boolean {
        val flag = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return flag == Configuration.UI_MODE_NIGHT_YES
    }




}

