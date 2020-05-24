package com.yolisstorm.covidpulse.features.summary.views.dialogs.chooseCountry

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yolisstorm.covidpulse.features.summary.databinding.ViewHolderItemCountryBinding
import com.yolisstorm.data_sources.databases.main.entities.Country

class CountryViewHolder private constructor(val binding: ViewHolderItemCountryBinding) :
		RecyclerView.ViewHolder(binding.root) {

	fun bind(clickListener: CountryClickListener, item: Country) {
		binding.clickListener = clickListener
		binding.country = item
		binding.executePendingBindings()
	}

	companion object {
		fun from(parent: ViewGroup) : CountryViewHolder {
			val layoutInflater = LayoutInflater.from(parent.context)
			val binding = ViewHolderItemCountryBinding.inflate(layoutInflater, parent, false)
			return CountryViewHolder(binding)
		}
	}


}