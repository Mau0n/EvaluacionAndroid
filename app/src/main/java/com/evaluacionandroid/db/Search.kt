package com.evaluacionandroid.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Search (

    @PrimaryKey
    val id: Long,

    val name: String

)