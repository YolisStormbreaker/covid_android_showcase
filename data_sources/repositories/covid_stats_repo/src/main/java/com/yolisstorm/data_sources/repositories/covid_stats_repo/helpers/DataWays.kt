package com.yolisstorm.data_sources.repositories.covid_stats_repo.helpers

enum class DataWays(val way: Int) {
	
	Unknown(0),
	LocalStore(1),
	Network(2);
	
	companion object {
		fun getByValue(value: Int) = values().find { it.way == value }
	}
}