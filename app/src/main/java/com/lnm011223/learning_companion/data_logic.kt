package com.lnm011223.learning_companion




class Topic (val topic:String,val topic_type:String,val topic_video: String,val week:String,val subject:String,val term:String)
class Exercises (val question:String,val answer:String,val score:Int)

//第一个表，专题表，用于存每一周的专题
//科目subject，学期term，周week，专题topic，类型topic_type，视频链接
//2*6*20*5*1
//把专题视频存放在bilibili上，在webview上播放
//第二个表，题目表
//专题，题目，答案，分值，讲解视频链接
//第三个表，错题本
//题目，答案