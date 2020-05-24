package com.yolisstorm.covidpulse.features.summary.views.dialogs.chooseCountry

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yolisstorm.data_sources.databases.main.entities.Country

class ChooseCountryAdapter (
	private val clickListener: CountryClickListener
) : ListAdapter<Country, RecyclerView.ViewHolder>(CountryDiffCallback()) {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
		when (RecyclerViewItemType.getByValue(viewType)) {
			RecyclerViewItemType.Item -> CountryViewHolder.from(parent)
			else -> throw(IllegalArgumentException("Неправильный holder для Adapter"))
		}

	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
		when (holder) {
			is CountryViewHolder -> holder.bind(clickListener, getItem(position))
		}
	}

	override fun getItemId(position: Int): Long =
		getItem(position).id

	override fun getItemViewType(position: Int): Int =
		when (getItem(position)) {
			is Country -> RecyclerViewItemType.Item.type
			else -> RecyclerViewItemType.Fake.type
		}

	enum class RecyclerViewItemType(val type: Int) {

		Header(0),
		Item(1),
		Fake(2);

		companion object {
			fun getByValue(value: Int) = values()[value]
		}
	}

}

class CountryClickListener(private val clickListener: (item: Country) -> Unit) {
	fun onClick(item: Country) = clickListener(item)
}

class CountryDiffCallback : DiffUtil.ItemCallback<Country>() {
	override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean {
		return oldItem.id == newItem.id
	}

	override fun areContentsTheSame(oldItem: Country, newItem: Country): Boolean {
		return oldItem == newItem
	}
}