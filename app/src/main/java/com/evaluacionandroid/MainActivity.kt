package com.evaluacionandroid

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.evaluacionandroid.adapter.NationalityAdapter
import com.evaluacionandroid.fragment.HistoryFragment
import com.evaluacionandroid.viewmodel.NationalityViewModel
import com.evaluacionandroid.viewmodel.NationalityViewModelFactory
import com.evaluacionandroid.viewmodel.UICallback
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener, UICallback{

    private lateinit var vm: NationalityViewModel
    private lateinit var viewModelFactory: NationalityViewModelFactory

    private var adapter = NationalityAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModelFactory = NationalityViewModelFactory(this)
        vm = ViewModelProvider(this, viewModelFactory)
            .get(NationalityViewModel::class.java)

        vm.listener = this

        search_view.setOnQueryTextListener(this)
        history_btn.setOnClickListener {
            lifecycleScope.launch {
                vm.getRecord()
            }
        }

        configList()

        vm.nationalityResult.observe(this) {
            showProgressBar(false)
            name.text = it.name
            name.visibility = View.VISIBLE
            if (it.countriesList.isNullOrEmpty()) {
                message_error.text = getString(R.string.without_result)
                showError(true)
                result_list.visibility = View.GONE
            } else {
                lifecycleScope.launch {
                    vm.saveSearch(it)
                }
                adapter.setCountriesList(it.countriesList)
                result_list.visibility = View.VISIBLE
            }
        }

        vm.record.observe(this) {
            showProgressBar(false)
            if (it.isNullOrEmpty()) {
                Toast.makeText(applicationContext, getString(R.string.empty_record), Toast.LENGTH_LONG).show()
            } else {
                search_view.clearFocus()
                inflateFragment(HistoryFragment())
            }
        }

    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(!query.isNullOrEmpty() && query != name.text){
            hideSoftKeyboard(this)
            showError(false)
            showProgressBar(true)
            lifecycleScope.launch {
                vm.searchByName(query)
            }
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    override fun showError(show: Boolean) {
        message_error.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun showProgressBar(show: Boolean) {
        progress_bar.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun configList() {
        result_list.setHasFixedSize(true)
        result_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        result_list.adapter = adapter
    }

    private fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager = activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        if (inputMethodManager.isAcceptingText) {
            inputMethodManager.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
        }
    }

    private fun inflateFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.content_overlay_layout, fragment, fragment::class.simpleName)
            .addToBackStack(fragment.tag).commit()
    }

}
