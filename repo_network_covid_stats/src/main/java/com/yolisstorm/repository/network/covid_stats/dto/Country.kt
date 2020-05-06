package com.yolisstorm.repository.network.covid_stats.dto

import com.google.gson.annotations.SerializedName
import java.util.*

data class Country(
    @SerializedName("Country")
    val name: String,
    @SerializedName("ISO2")
    val locale: Locale,
    @SerializedName("Slug")
    val slug: String
)