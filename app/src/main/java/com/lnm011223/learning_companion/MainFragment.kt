package com.lnm011223.learning_companion

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_main.*
import kotlin.math.log


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
            bundle.putString("subject","math")
            Log.d("aaa",bundle.getString("subject").toString())
            Navigation.findNavController(math_card).navigate(R.id.action_mainFragment_to_bookFragment,bundle)
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