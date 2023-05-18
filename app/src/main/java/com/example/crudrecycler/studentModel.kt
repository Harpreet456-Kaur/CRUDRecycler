package com.example.crudrecycler

import java.nio.channels.spi.AbstractSelectionKey

class studentModel(
    var name:String? =null,
    var rollno:String?=null,
    var key: String?=null
)
{
    override fun toString(): String {
        return "$name $rollno"
    }
}


