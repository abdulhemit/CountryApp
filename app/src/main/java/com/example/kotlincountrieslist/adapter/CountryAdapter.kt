package com.example.kotlincountrieslist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlincountrieslist.R
import com.example.kotlincountrieslist.databinding.ItemCountryBinding
import com.example.kotlincountrieslist.model.CountryModel
import com.example.kotlincountrieslist.util.downloadFromUrl
import com.example.kotlincountrieslist.util.placeholderProgressBar
import com.example.kotlincountrieslist.view.FeedFragmentDirections
import kotlinx.android.synthetic.main.item_country.view.*

class CountryAdapter(val countryList: ArrayList<CountryModel>): RecyclerView.Adapter<CountryAdapter.CountryHolder>(),CountryClickListener {


    class CountryHolder(val view: ItemCountryBinding):RecyclerView.ViewHolder(view.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemCountryBinding>(inflater, R.layout.item_country,parent,false)
        //var binding = ItemCountryBinding.inflate(LayoutInflater.from(parent.context))
        return CountryHolder(view)
    }

    override fun onBindViewHolder(holder: CountryHolder, position: Int) {

        holder.view.country = countryList.get(position)
        holder.view.listener = this
        /*
        holder.binding.name.text = countryList.get(position).countryName
        holder.binding.region.text = countryList.get(position).countryRegion
        holder.itemView.setOnClickListener {
            val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment(countryList.get(position).uuid)
            Navigation.findNavController(it).navigate(action)
        }
        countryList.get(position).imageUrl?.let {
            holder.binding.imageView.downloadFromUrl(it,placeholderProgressBar(holder.itemView.context))
        }

         */
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    //@SuppressLint("NotifyDataSetChanged")
    fun updateCountryList(newCountryList:List<CountryModel>){
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }

    override fun onCountryClicked(v:View) {
        val uuid = v.countryUuid?.text.toString().toInt()
        val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment(uuid)
        Navigation.findNavController(v).navigate(action)
    }


}