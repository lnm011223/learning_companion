package com.lnm011223.learning_companion


import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    lateinit var topicModel: TopicModel

    @SuppressLint("SdCardPath")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        // Hide the status bar.
        window.decorView.systemUiVisibility = View.STATUS_BAR_VISIBLE or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR or View.SYSTEM_UI_FLAG_FULLSCREEN
        // Remember that you should never show the action bar if the
        // status bar is hidden, so hide that too if necessary.
        actionBar?.hide()
    }
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
                R.id.bookFragment -> {back_button.apply {
                    show()
                    setOnClickListener{navController.navigate(R.id.action_bookFragment_to_mainFragment)}
                }}
                R.id.weekFragment -> back_button.setOnClickListener{navController.navigate(R.id.action_weekFragment_to_bookFragment)}
                R.id.errorbookFragment -> back_button.setOnClickListener { navController.navigate(R.id.action_errorbookFragment_to_weekFragment) }
                R.id.topicsFragment -> back_button.setOnClickListener { navController.navigate(R.id.action_topicsFragment_to_weekFragment) }
                R.id.videoFragment -> back_button.setOnClickListener { navController.navigate(R.id.action_videoFragment_to_topicsFragment) }
                R.id.exerciseFragment -> back_button.setOnClickListener { navController.navigate(R.id.action_exerciseFragment_to_topicsFragment) }
                R.id.errorVideoFragment -> back_button.setOnClickListener { navController.navigate(R.id.action_errorVideoFragment_to_errorbookFragment) }
                R.id.moreResultFragment -> back_button.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putBoolean("flag",false)
                    navController.navigate(R.id.action_moreResultFragment_to_resultFragment,bundle)
                }
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
                else -> back_button.hide()

            }

        }

    }

    private fun isDarkTheme(context: Context): Boolean {
        val flag = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return flag == Configuration.UI_MODE_NIGHT_YES
    }




}

