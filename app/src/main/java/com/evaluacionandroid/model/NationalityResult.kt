package com.evaluacionandroid.model

import com.google.gson.annotations.SerializedName

data class NationalityResult (

    @SerializedName("name")
    var name: String,

    @SerializedName("country")
    var countriesList: List<NationalityProbability>

)