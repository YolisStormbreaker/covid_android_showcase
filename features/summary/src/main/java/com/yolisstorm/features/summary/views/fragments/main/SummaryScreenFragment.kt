package com.yolisstorm.features.summary.views.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.yolisstorm.features.summary.R
import com.yolisstorm.features.summary.databinding.FragmentSummaryScreenLayoutBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SummaryScreenFragment : Fragment() {

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {

		val binding : FragmentSummaryScreenLayoutBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_summary_screen_layout, container, false)

		val viewModel  by viewModel<SummaryScreenViewModel>()

		binding.lifecycleOwner = this

		setHasOptionsMenu(true)

		return binding.root
	}
}