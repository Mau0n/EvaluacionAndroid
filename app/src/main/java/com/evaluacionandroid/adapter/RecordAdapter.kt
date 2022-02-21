package com.evaluacionandroid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.evaluacionandroid.R
import com.evaluacionandroid.db.Nationality
import com.evaluacionandroid.db.SearchWithNationalities
import com.evaluacionandroid.model.NationalityProbability

class RecordAdapter(private val context: Context, private val recordList: List<SearchWithNationalities>): RecyclerView.Adapter<RecordAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.record_item_layout, parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = recordList[position]
        val adapter = NationalityAdapter()
        val nationalitiesList = ArrayList<NationalityProbability>()

        //Prepare result list
        currentItem.nationalities.forEach {
            nationalitiesList.add(
                castNationalityToNationalityProbability(it)
            )
        }

        adapter.setCountriesList(nationalitiesList)

        holder.name.text = currentItem.search.name

        holder.resultList.setHasFixedSize(true)
        holder.resultList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        holder.resultList.adapter = adapter
    }

    override fun getItemCount(): Int {
        return recordList.size
    }

    private fun castNationalityToNationalityProbability(nationality: Nationality): NationalityProbability {
        return NationalityProbability(nationality.countryId, nationality.probability.toDouble())
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name: TextView = itemView.findViewById(R.id.name)
        val resultList: RecyclerView = itemView.findViewById(R.id.result_list)

    }
}