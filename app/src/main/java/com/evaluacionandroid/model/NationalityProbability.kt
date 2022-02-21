package com.evaluacionandroid.model

import com.google.gson.annotations.SerializedName

data class NationalityProbability(

    @SerializedName("country_id")
    var countryId: String,

    @SerializedName("probability")
    var probability: Double

)