package com.lnm011223.learning_companion

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


class Topic (val topic:String,val topic_type:String,val video_url: String,val week:String,val subject:String,val term:String)
@Parcelize
class Question (val title:String,val answer:String,val score:Int,val video_url:String,val similarities_id:String,var isRight: Boolean) : Parcelable

