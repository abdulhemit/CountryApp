package com.example.kotlincountrieslist.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlincountrieslist.adapter.CountryAdapter
import com.example.kotlincountrieslist.databinding.FragmentFeedBinding
import com.example.kotlincountrieslist.viewmodel.FeedViewModel


class FeedFragment : Fragment() {
    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FeedViewModel
    private val countryAdapter = CountryAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = ViewModelProviders.of(this).get(FeedViewModel::class.java)
        viewModel.refreshData()

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = countryAdapter



        binding.swipeRefreshlayout.setOnRefreshListener {
            binding.swipeRefreshlayout.isRefreshing = false
            binding.recyclerView.visibility = View.GONE
            binding.countryError.visibility = View.GONE
            binding.countryLoading.visibility = View.VISIBLE
            viewModel.refreshFromAPI()
        }

        observeLiveData()


        return view
    }
    fun observeLiveData(){
        viewModel.countries.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.recyclerView.visibility = View.VISIBLE
                countryAdapter.updateCountryList(it)

            }
        })
        viewModel.countryError.observe(viewLifecycleOwner, Observer { Error->
            Error?.let {
                if (it){
                    binding.countryError.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                    binding.countryLoading.visibility = View.GONE
                }else{
                    binding.countryError.visibility = View.GONE
                }
            }
        })
        viewModel.countryLoading.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it){
                    binding.countryLoading.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                    binding.countryError.visibility = View.GONE
                }else {
                    binding.countryLoading.visibility = View.GONE
                }
            }
        })
    }

}