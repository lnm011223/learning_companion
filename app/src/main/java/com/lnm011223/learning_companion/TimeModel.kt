package com.lnm011223.learning_companion

import androidx.lifecycle.ViewModel

class TimeModel : ViewModel() {

    var surplustime: Int? = 90 * 60 * 1000
    var questionlist = ArrayList<Question>()
}