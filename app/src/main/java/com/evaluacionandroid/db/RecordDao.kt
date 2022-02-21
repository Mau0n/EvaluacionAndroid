package com.evaluacionandroid.db

import androidx.room.*

@Dao
interface RecordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearch(search: Search)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResult(nationality: Nationality)

    @Transaction
    @Query("SELECT * FROM search")
    suspend fun getAllSearches(): List<Search>

    @Transaction
    @Query("SELECT * FROM search WHERE id = :id")
    suspend fun getSearchWithNationality(id: Long): List<SearchWithNationalities>

    @Transaction
    @Query("SELECT * FROM search")
    suspend fun getAllSearchWithNationality(): List<SearchWithNationalities>

}