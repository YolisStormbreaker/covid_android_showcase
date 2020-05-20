package com.yolisstorm.covidpulse.features.summary.views.fragments.main

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.yolisstorm.covidpulse.features.summary.R
import com.yolisstorm.data_sources.repositories.covid_stats_repo.CovidStatsRepositoryKoinModule
import com.yolisstorm.covidpulse.features.summary.databinding.FragmentSummaryScreenLayoutBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module

@ExperimentalCoroutinesApi
private val loadModules by lazy {
	val modules : List<Module> =
		CovidStatsRepositoryKoinModule.getModules().union(listOf(SummaryScreenKoinModule)).toList()
	loadKoinModules(modules)
}

@ExperimentalCoroutinesApi
private fun injectFeatures() = loadModules

private lateinit var binding : FragmentSummaryScreenLayoutBinding

@ExperimentalCoroutinesApi
class SummaryScreenFragment : Fragment() {

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {

		injectFeatures()

		binding = DataBindingUtil.inflate(inflater, R.layout.fragment_summary_screen_layout, container, false)

		val viewModel  by viewModel<SummaryScreenViewModel>()

		lifecycleScope.launchWhenResumed {
			viewModel.updateLastTwoCasesData()
		}

		binding.lifecycleOwner = this

		binding.viewModel = viewModel

		return binding.root
	}

	override fun onConfigurationChanged(newConfig: Configuration) {
		super.onConfigurationChanged(newConfig)
		val viewModel  by viewModel<SummaryScreenViewModel>()
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			viewModel.updateCurrentLocation(newConfig.locales.get(0))
		} else {
			viewModel.updateCurrentLocation(newConfig.locale)
		}
	}
}