package com.omer.covidstats.view.dashboard

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.omer.covidstats.R
import com.omer.covidstats.model.Countries
import com.omer.covidstats.view.dashboard.adapters.CountryListAdapter
import com.omer.covidstats.viewmodel.CovidViewModel


class WorldFragment : Fragment() {

    private lateinit var countryList: RecyclerView
    private lateinit var searchBar: EditText
    private val covidVM: CovidViewModel by activityViewModels()
    private val sortsearchViewModel: SortSearchViewModel by activityViewModels()
    private lateinit var originalData: List<Countries>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupObservers()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_world, container, false)
        countryList = view.findViewById(R.id.countriesList)
        searchBar = view.findViewById(R.id.search_editText)
        searchBar.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                val searchText = s.toString()
                if (searchText.length > 0) {
                    sortsearchViewModel.isSearched = true
                    sortsearchViewModel.searchedData = originalData.filter { it -> it.country.contains(searchText, true) }
                    updateUI(sortsearchViewModel.searchedData)
                }
                else {
                    if (::originalData.isInitialized) {
                        updateUI(originalData)
                    }
                }
            }

        })


        val toolBar = view.findViewById<Toolbar>(R.id.toolBarWorld)
        toolBar.inflateMenu(R.menu.sort_menu)
        toolBar.setOnMenuItemClickListener(Toolbar.OnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.sort_AZ -> sortsearchViewModel.sortedType.value = 1
                R.id.sort_ZA -> sortsearchViewModel.sortedType.value = 2
                else -> sortsearchViewModel.sortedType.value = 0
            }
            false
        })

        return view
    }


    private fun setupObservers() {
        covidVM.summaryLiveData.observe(this, Observer {
            originalData = it.countries
            sortsearchViewModel.isSearched = false
            updateUI(it.countries)
        })

        sortsearchViewModel.sortedType.observe(this, Observer {
            if (sortsearchViewModel.isSearched) {
                updateUI(sortsearchViewModel.searchedData)
            }
            else {
                if (::originalData.isInitialized) {
                    updateUI(originalData)
                }
            }
        })
    }



    private fun updateUI(data: List<Countries>) {
        countryList.adapter = CountryListAdapter(sortedData(data))
        countryList.layoutManager = LinearLayoutManager(activity)
    }

    private fun sortedData(data: List<Countries>): List<Countries> {
        when (sortsearchViewModel.sortedType.value) {
            1 -> return data.sortedWith(compareBy { it.country })
            2 -> return data.sortedWith(compareByDescending { it.country })
            else -> return data.sortedWith(compareByDescending { it.totalConfirmed })
        }
    }
}

class SortSearchViewModel: ViewModel() {
    var sortedType = MutableLiveData<Int>()
    var isSearched: Boolean = false
    lateinit var searchedData: List<Countries>
    init {
        sortedType.value = 0
    }

}