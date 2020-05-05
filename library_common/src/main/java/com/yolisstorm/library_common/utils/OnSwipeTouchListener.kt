package com.skishift.companion.utils

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import timber.log.Timber
import java.lang.Exception
import kotlin.math.abs

class OnSwipeTouchListener(
	context: Context?,
	private val swipeListeners: IOnSwipe,
	private val swipeThresholdX: Int = 100,
	private val swipeVelocityThresholdX: Int = 100,
	private val swipeThresholdY: Int = 100,
	private val swipeVelocityThresholdY: Int = 100
) : View.OnTouchListener {
	
	private val gestureDetector: GestureDetector
	
	init {
		gestureDetector = GestureDetector(context, GestureListener())
	}
	
	override fun onTouch(view: View?, event: MotionEvent?): Boolean {
		return gestureDetector.onTouchEvent(event)
	}
	
	private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
		
		override fun onSingleTapUp(e: MotionEvent?): Boolean {
			swipeListeners.onSingleTouch()
			return true
		}
		
		override fun onDown(e: MotionEvent?): Boolean {
			return true
		}
		
		override fun onFling(
			e1: MotionEvent?,
			e2: MotionEvent?,
			velocityX: Float,
			velocityY: Float
		): Boolean {
			var result = false
			try {
				if (e1 != null && e2 != null) {
					val diffY = e2.y - e1.y
					val diffX = e2.x - e1.x
					if (abs(diffX) > swipeThresholdX && abs(velocityX) > swipeVelocityThresholdX) {
						if (diffX > 0)
							swipeListeners.onSwipeRight()
						else
							swipeListeners.onSwipeLeft()
						result = true
					} else if (abs(diffY) > swipeThresholdY && abs(velocityY) > swipeVelocityThresholdY) {
						if (diffY > 0)
							swipeListeners.onSwipeBottom()
						else
							swipeListeners.onSwipeTop()
						result = true
					}
				}
			} catch (ex: Exception) {
				Timber.e(ex)
			}
			return result
		}
	}
}