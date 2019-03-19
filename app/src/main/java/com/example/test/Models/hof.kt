package com.example.test.Models

import io.realm.RealmObject
import io.realm.annotations.Required
import io.realm.annotations.PrimaryKey

open class hof : RealmObject() {
    @PrimaryKey
    var id: Int = 0
    @Required
    var name: String? = ""
    var countnumber: Long = 0
    @Required
    var date: String? = null
    var maxpoints: Long = 0
}
