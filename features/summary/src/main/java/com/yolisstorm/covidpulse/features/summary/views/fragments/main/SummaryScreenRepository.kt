package com.yolisstorm.covidpulse.features.summary.views.fragments.main

class SummaryScreenRepository private constructor(
) {

    companion object {

        //Для Singleton
        @Volatile
        private var instance: SummaryScreenRepository? = null

        fun getInstance() =
                instance
                    ?: synchronized(this) {
                    instance
                        ?: SummaryScreenRepository()
                            .also { instance = it }
                }
        }

}