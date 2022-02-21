package com.evaluacionandroid.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.evaluacionandroid.R
import com.evaluacionandroid.adapter.RecordAdapter
import com.evaluacionandroid.viewmodel.NationalityViewModel
import kotlinx.android.synthetic.main.record_fragment_layout.*

class HistoryFragment: Fragment() {

    private lateinit var vm: NationalityViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.record_fragment_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        vm = activity?.run { ViewModelProvider(this).get(NationalityViewModel::class.java) }
            ?: throw Exception("ViewModel Invalid Activity")

        initComponents()
    }

    /**
     * Init components ton show history
     */
    private fun initComponents() {

        back.setOnClickListener { activity?.onBackPressed() }

        val adapter = context?.let { vm.record.value?.let { it1 -> RecordAdapter(it, it1) } }
        search_list.setHasFixedSize(true)
        search_list.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        search_list.adapter = adapter
    }
}