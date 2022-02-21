package com.evaluacionandroid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.evaluacionandroid.R
import com.evaluacionandroid.model.NationalityProbability
import java.util.*
import kotlin.collections.ArrayList

class NationalityAdapter: RecyclerView.Adapter<NationalityAdapter.ViewHolder>() {

    private val countriesList = ArrayList<NationalityProbability>()

    fun setCountriesList(list: List<NationalityProbability>){
        countriesList.clear()
        countriesList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.nationality_item_layout, parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = countriesList[position]

        val countryName = Locale("", currentItem.countryId)

        holder.countyName.text = countryName.displayCountry
        holder.probability.text = currentItem.probability.toString()
    }

    override fun getItemCount(): Int {
        return countriesList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val countyName: TextView = itemView.findViewById(R.id.country)
        val probability: TextView = itemView.findViewById(R.id.probability)

    }
}