package com.yolisstorm.data_sources.databases.main.entities

import androidx.room.*
import com.google.android.gms.maps.model.LatLng
import java.util.*

@Entity(
	tableName = "cases",
	indices = [
		Index(
			value = ["id", "country_id"],
			unique = true
		)
	],
	foreignKeys = [
		ForeignKey(
			entity = Country::class,
			parentColumns = ["id"],
			childColumns = ["country_id"],
			onUpdate = ForeignKey.CASCADE,
			onDelete = ForeignKey.CASCADE
		)
	]
)
data class Case(
	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "id")
	val id: Long = 0L,
	@ColumnInfo(name = "location")
	val location: LatLng? = null,
	@ColumnInfo(name = "country_id")
	val countryId: Long,
	@ColumnInfo(name = "date")
	val date: Date,
	@ColumnInfo(name = "confirmed")
	val confirmed: Int,
	@ColumnInfo(name = "deaths")
	val deaths: Int,
	@ColumnInfo(name = "recovered")
	val recovered: Int
)
