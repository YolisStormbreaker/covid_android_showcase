package com.yolisstorm.covidpulse.features.summary.views.dialogs.chooseCountry

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.yolisstorm.covidpulse.features.summary.R
import com.yolisstorm.covidpulse.features.summary.databinding.DialogChooseCountryBinding
import com.yolisstorm.library.extensions.safeNavigateTo
import com.yolisstorm.library.utils.EventObserver
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class ChooseCountryDialogFragment : DialogFragment() {

	companion object {
		fun newInstance(): ChooseCountryDialogFragment? =
			ChooseCountryDialogFragment()
	}

	override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

		val binding : DialogChooseCountryBinding =
			DataBindingUtil.inflate(
				LayoutInflater.from(context),
				R.layout.dialog_choose_country,
				null,
				false
			)

		val viewModel by viewModel<ChooseCountryViewModel>()

		val resultDialog = AlertDialog.Builder(requireContext())
			.setView(binding.root)
			.create()
			.also {
				it.window?.setBackgroundDrawable(
					ColorDrawable(Color.TRANSPARENT)
				)
			}

		binding.countriesList.apply {
			LinearLayoutManager(context).run {
				isSmoothScrollbarEnabled = true
				layoutManager = this
			}
			ChooseCountryAdapter(
				CountryClickListener {
					viewModel.selectedLocale.postValue(it.countryCode)
					resultDialog.cancel()
				}
			).also { countriesAdapter ->
				countriesAdapter.hasStableIds()
				adapter = countriesAdapter
				viewModel.countriesList.observe(this@ChooseCountryDialogFragment, Observer { list ->
					if (list?.isNotEmpty() == true)
						countriesAdapter.submitList(list.sortedBy { it.countryCode.displayCountry })
				})
			}
		}

		with(viewModel) {
			wantToClickCancel.observe(this@ChooseCountryDialogFragment, EventObserver {
				resultDialog.cancel()
			})
			wantToChooseMyCountry.observe(this@ChooseCountryDialogFragment, EventObserver {
				viewModel.selectedLocale.postValue(Locale.getDefault())
				resultDialog.cancel()
			})
		}

		binding.viewModel = viewModel

		return resultDialog

	}
}