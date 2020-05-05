package com.yolisstorm.library.enums

enum class CommonRecyclerViewItemType(val type: Int) {
	
	Header(0),
	Item(1),
	Fake(2);
	
	companion object {
		fun getByValue(value: Int) = values()[value]
	}
	
}