package com.example.test.Models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class HallOfFrame(
    @SerializedName("id")
    @Expose
    var id: Int = 0,

    @SerializedName("name")
    @Expose
    var name: String = "",

    @SerializedName("date")
    @Expose
    var date: String = "",

    @SerializedName("maxpoints")
    @Expose
    var maxpoints: Int = 0,

    @SerializedName("coutnumber")
    @Expose
    var countNumber: Int = 0
) {
}