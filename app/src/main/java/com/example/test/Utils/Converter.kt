package com.example.test.Utils

import com.example.test.Models.HallOfFrame
import com.example.test.Models.hof

class Converter {

    fun toHof(hallframe: HallOfFrame): hof {
        var current = hof()
        current.id = hallframe.id
        current.countnumber = hallframe.countNumber.toLong()
        current.date = hallframe.date
        current.maxpoints = hallframe.maxpoints.toLong()
        current.name = hallframe.name
        return current
    }
}