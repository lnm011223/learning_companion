package com.lnm011223.learning_companion

import androidx.lifecycle.ViewModel

class TopicModel : ViewModel() {
    var topic = Topic("1","1","1","1","1","1")
    var topiclist = ArrayList<Topic>()
}