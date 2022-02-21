package com.evaluacionandroid.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.evaluacionandroid.db.*
import com.evaluacionandroid.model.NationalityResult
import com.evaluacionandroid.request.APIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NationalityViewModel(context: Context) : ViewModel() {

    lateinit var listener: UICallback
    val dao: RecordDao = RecordDataBase.getInstance(context).recordDao

    private val _nationalizeResult = MutableLiveData<NationalityResult>()
    val nationalityResult: LiveData<NationalityResult>
        get() = _nationalizeResult

    private val _record = MutableLiveData<List<SearchWithNationalities>>()
    val record: LiveData<List<SearchWithNationalities>>
        get() = _record

    /**
     * Request to api nationalize.io
     */
    suspend fun searchByName(name: String){
        val apiInterface = APIService.create().getNationalitiesProbability(name)
        apiInterface.enqueue( object : Callback<NationalityResult> {
            override fun onResponse(call: Call<NationalityResult>?, response: Response<NationalityResult>?) {

                if(response?.body() != null) {
                    _nationalizeResult.value = response.body()
                } else {
                    listener.showError(true)
                }
            }

            override fun onFailure(call: Call<NationalityResult>?, t: Throwable?) {
                listener.showError(true)
            }
        })
    }

    /**
     * Insert searched names and their results
     */
    suspend fun saveSearch(result: NationalityResult){
        val tsLong: Long = System.currentTimeMillis()/1000
        dao.insertSearch(Search(tsLong, result.name))

        result.countriesList.forEach {
            dao.insertResult(
                Nationality(
                    tsLong,
                    it.countryId,
                    it.probability.toString()
                )
            )
        }
    }

    /**
     * Get Record from data base and reversed list
     */
    suspend fun getRecord() {
        _record.value = dao.getAllSearchWithNationality().reversed()
    }

}