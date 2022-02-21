package com.evaluacionandroid.db

import androidx.room.Entity

@Entity(
    primaryKeys=[
    "searchId",
    "countryId"
])
data class Nationality (

    val searchId: Long,

    val countryId: String,

    val probability: String

)