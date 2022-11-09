package com.example.kotlincountrieslist.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.kotlincountrieslist.databinding.FragmentCountryBinding
import com.example.kotlincountrieslist.util.downloadFromUrl
import com.example.kotlincountrieslist.util.placeholderProgressBar
import com.example.kotlincountrieslist.viewmodel.CountryViewModel

class CountryFragment : Fragment() {
    private var _binding: FragmentCountryBinding? = null
    private val binding get() = _binding!!
    private var countryUuid = 0
    private lateinit var viewModel: CountryViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCountryBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            countryUuid = CountryFragmentArgs.fromBundle(it).countryUuid
        }
        viewModel = ViewModelProviders.of(this).get(CountryViewModel::class.java)
        viewModel.getDataFromRoom(countryUuid)

        observeLiveData()
    }
    private fun observeLiveData(){
        viewModel.countryLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.countryName.text = it.countryName
                binding.countryCapital.text = it.countryCapital
                binding.countryCurrency.text = it.countryCurrency
                binding.countryLanguage.text = it.countryLanguage
                binding.countryRegion.text = it.countryRegion
                it.imageUrl?.let {
                        it1 -> binding.imageCountry.downloadFromUrl(it1, placeholderProgressBar(requireContext()))
                }
            }
        })
    }


}