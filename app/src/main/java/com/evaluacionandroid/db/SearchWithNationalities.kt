package com.evaluacionandroid.db

import androidx.room.Embedded
import androidx.room.Relation

data class SearchWithNationalities (
    @Embedded val search: Search,
    @Relation(
        parentColumn = "id",
        entityColumn = "searchId"
    )
    val nationalities: List<Nationality>

)