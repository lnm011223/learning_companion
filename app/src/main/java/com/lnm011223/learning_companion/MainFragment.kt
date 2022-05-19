package com.lnm011223.learning_companion


import AutoLineLayout
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val bundle = Bundle()

        chinese_card.setOnClickListener {
            bundle.putString("subject","chinese")
            Log.d("aaa",bundle.getString("subject").toString())
            Navigation.findNavController(chinese_card).navigate(R.id.action_mainFragment_to_bookFragment,bundle)
        }
        math_card.setOnClickListener{

            //bundle.putString("subject","math")
            //Log.d("aaa",bundle.getString("subject").toString())
            //Navigation.findNavController(math_card).navigate(R.id.action_mainFragment_to_bookFragment,bundle)
            //val
            //for (i in 0..4){
            //    val
            //}
            //val text_test = EditText(MyApplication.context)
            //
            //test_linear.addView(text_test)
            val test1 = "（填空）在900÷[(36－24)×25]中，先算（　　）法，再算（　　）法，最后算（　　）法。"
            val test = "1.$test1"
            val chaifeng = "（　　）"
            val parts = test.split(chaifeng)
            val num = parts.size
            var editTextlist = ArrayList<EditText>()
            var textViewlist = ArrayList<TextView>()

            var kuohaonum = 0

            //var autoLineLayout = AntoLineUtil(MyApplication.context)

            //test_linear.addView(autoLineLayout)
            var flag = false
            for (i in 0..num-1) {
                if (i!=num-1) {
                    if (i == 0) {
                        flag = true
                    }else{
                        flag = false
                    }
                    var text_test = TextView(MyApplication.context)
                    var lefttext = TextView(MyApplication.context)
                    var righttext = TextView(MyApplication.context)
                    var edit_test = EditText(MyApplication.context)
                    lefttext.text = "("
                    righttext.text = ")"
                    textViewlist.add(text_test)
                    editTextlist.add(edit_test)
                    if (flag) {
                        textViewlist[i].text = "${parts[i]}"
                    }else{
                        textViewlist[i].text = "${parts[i]}"
                    }

                    editTextlist[i].apply {
                        isCursorVisible = false
                        hint = "____"
                        //minWidth = 30
                        background = null
                        setPadding(0,0,0,0)
                        minHeight = text_test.height
                        textSize = 14f}
                    autoLineLayout.apply {
                        addView(textViewlist[i])
                        addView(lefttext)
                        addView(editTextlist[i])
                        addView(righttext)
                    }

                }else{
                    var text_test = TextView(MyApplication.context)
                    textViewlist.add(text_test)
                    textViewlist[i].text = "${parts[i]}"
                    autoLineLayout.apply {
                        addView(textViewlist[i])

                    }
                }
            }
            val ringtanswer = "减,乘,除"
            var answer = ""
            for (i in editTextlist){
                i.addTextChangedListener {
                    answer = ""
                    var x = 0
                    for (j in editTextlist) {
                        if (x == 0){
                            answer += "${j.text}"
                            x = 1
                        }else{
                            answer += ",${j.text}"
                        }


                    }
                    if (answer == ringtanswer) {
                        Toast.makeText(context, "回答正确", Toast.LENGTH_SHORT).show()
                        for (a in textViewlist) {

                            a.setTextColor(ContextCompat.getColorStateList(MyApplication.context,R.color.blue))
                        }

                    }
                }
            }
        }





    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_main, container, false)

    }


}