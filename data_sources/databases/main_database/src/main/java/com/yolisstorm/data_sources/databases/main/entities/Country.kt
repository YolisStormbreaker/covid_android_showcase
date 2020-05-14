package com.yolisstorm.data_sources.databases.main.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(
	tableName = "countries"
)
data class Country (
	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "id")
	val id : Long = 0L,
	@ColumnInfo(name = "country_code")
	val locale: Locale,
	@ColumnInfo(name = "country_slug")
	val slug: String
)