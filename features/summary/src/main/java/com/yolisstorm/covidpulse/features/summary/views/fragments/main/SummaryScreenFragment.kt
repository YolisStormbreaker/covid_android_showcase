package com.yolisstorm.covidpulse.features.summary.views.fragments.main

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.yolisstorm.covidpulse.features.summary.R
import com.yolisstorm.covidpulse.features.summary.databinding.CaseCardBinding
import com.yolisstorm.covidpulse.features.summary.databinding.FragmentSummaryScreenLayoutBinding
import com.yolisstorm.data_sources.repositories.covid_stats_repo.CovidStatsRepositoryKoinModule
import com.yolisstorm.library.bindingAdapters.setRelativeDate
import com.yolisstorm.library.utils.EventObserver
import com.yolisstorm.library.utils.ExtCountDownTimer
import kotlinx.android.synthetic.main.fragment_summary_screen_layout.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import timber.log.Timber
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.seconds

@ExperimentalCoroutinesApi
private val loadModules by lazy {
	val modules: List<Module> =
		CovidStatsRepositoryKoinModule.getModules().union(listOf(SummaryScreenKoinModule)).toList()
	loadKoinModules(modules)
}

@ExperimentalCoroutinesApi
private fun injectFeatures() = loadModules

private lateinit var binding: FragmentSummaryScreenLayoutBinding

@ExperimentalTime
@ExperimentalCoroutinesApi
class SummaryScreenFragment(

) : Fragment() {


	private val timer: ExtCountDownTimer by ExtCountDownTimer(Duration.INFINITE, 1.seconds)

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {

		injectFeatures()

		binding = DataBindingUtil.inflate(
			inflater,
			R.layout.fragment_summary_screen_layout,
			container,
			false
		)

		val viewModel by viewModel<SummaryScreenViewModel>()

		binding.lifecycleOwner = this

		binding.viewModel = viewModel

		lifecycleScope.launchWhenResumed {
			viewModel.updateLastTwoCasesData()
			viewModel.lastTwoCases.observe(viewLifecycleOwner, Observer {
				Timber.d("result = $it")
			})
			viewModel.casesCovid.observe(viewLifecycleOwner, Observer {
				Timber.d("covid = $it")
				it?.let {
					DataBindingUtil.bind<CaseCardBinding>(card_covid)?.counterData = it.first
					DataBindingUtil.bind<CaseCardBinding>(card_covid)?.percentData = it.second
				}
			})
			viewModel.casesRecovered.observe(viewLifecycleOwner, Observer {
				Timber.d("Recovered = $it")
				it?.let {
					DataBindingUtil.bind<CaseCardBinding>(card_health)?.counterData =
						it.first
					DataBindingUtil.bind<CaseCardBinding>(card_health)?.percentData =
						it.second
				}
			})
			viewModel.casesDeath.observe(viewLifecycleOwner, Observer {
				Timber.d("Death = $it")
				it?.let {
					DataBindingUtil.bind<CaseCardBinding>(card_deaths)?.counterData =
						it.first
					DataBindingUtil.bind<CaseCardBinding>(card_deaths)?.percentData =
						it.second
				}
			})
			timer.startTimer()
			timer.timerTick.observe(viewLifecycleOwner, EventObserver {
				binding.lastUpdateLabel.setRelativeDate(viewModel.lastUpdate.value)
			})
		}

		return binding.root
	}


	override fun onConfigurationChanged(newConfig: Configuration) {
		super.onConfigurationChanged(newConfig)
		val viewModel by viewModel<SummaryScreenViewModel>()
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			viewModel.updateCurrentLocation(newConfig.locales.get(0))
		} else {
			viewModel.updateCurrentLocation(newConfig.locale)
		}
	}

	override fun onStop() {
		super.onStop()
		timer.finishTimer()
	}
}