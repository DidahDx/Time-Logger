package com.github.didahdx.sampleanalogclock

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatImageView

class SimpleAnalogClock @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr, defStyleRes) {
    private val face: AppCompatImageView
    private val hour: AppCompatImageView
    private val minute: AppCompatImageView
    private val second: AppCompatImageView
    fun setFaceDrawable(drawable: Drawable?): SimpleAnalogClock {
        face.setImageDrawable(drawable)
        return this
    }

    fun setHourDrawable(drawable: Drawable?): SimpleAnalogClock {
        hour.setImageDrawable(drawable)
        return this
    }

    fun setMinuteDrawable(drawable: Drawable?): SimpleAnalogClock {
        minute.setImageDrawable(drawable)
        return this
    }

    fun setSecondDrawable(drawable: Drawable?): SimpleAnalogClock {
        second.setImageDrawable(drawable)
        return this
    }

    fun rotateHourHand(angle: Float): SimpleAnalogClock {
        hour.rotation = angle
        return this
    }

    fun rotateMinuteHand(angle: Float): SimpleAnalogClock {
        minute.rotation = angle
        return this
    }

    fun rotateSecondHand(angle: Float): SimpleAnalogClock {
        second.rotation = angle
        return this
    }

    fun setTime(hour: Int, min: Int, seconds: Int, millis: Int): SimpleAnalogClock {
        val hourMillis = hour * HOURS
        val minMillis = min * MINUTES
        val secondMillis = seconds * SECONDS
        rotateHourHand(0.0000083.toFloat() * (hourMillis + minMillis + secondMillis + millis))
        rotateMinuteHand(0.0001.toFloat() * (minMillis + secondMillis + millis))
        rotateSecondHand(0.006.toFloat() * (secondMillis + millis))
        return this
    }

    fun setTime(hour: Int, min: Int, seconds: Int): SimpleAnalogClock {
        return setTime(hour, min, seconds, 0)
    }

    fun setFaceTint(color: Int): SimpleAnalogClock {
        face.setColorFilter(color)
        return this
    }

    fun setHourTint(color: Int): SimpleAnalogClock {
        hour.setColorFilter(color)
        return this
    }

    fun setMinuteTint(color: Int): SimpleAnalogClock {
        minute.setColorFilter(color)
        return this
    }

    fun setSecondTint(color: Int): SimpleAnalogClock {
        second.setColorFilter(color)
        return this
    }

    companion object {
        private const val HOURS = 3600000L
        private const val MINUTES = 60000L
        private const val SECONDS = 1000L
    }

    init {
        inflate(context, R.layout.analog_clock, this)
        face = findViewById(R.id.face)
        hour = findViewById(R.id.hour_hand)
        minute = findViewById(R.id.minute_hand)
        second = findViewById(R.id.second_hand)
        val typedArray = context.theme.obtainStyledAttributes(
            attrs, R.styleable.SimpleAnalogClock, defStyleAttr, defStyleRes
        )
        val faceDrawable = typedArray.getDrawable(R.styleable.SimpleAnalogClock_faceDrawable)
        val hourDrawable = typedArray.getDrawable(R.styleable.SimpleAnalogClock_hourDrawable)
        val minuteDrawable = typedArray.getDrawable(R.styleable.SimpleAnalogClock_minuteDrawable)
        val secondDrawable = typedArray.getDrawable(R.styleable.SimpleAnalogClock_secondDrawable)
        setFaceDrawable(faceDrawable ?: context.getDrawable(R.drawable.clock_00_face))
            .setHourDrawable(hourDrawable ?: context.getDrawable(R.drawable.clock_00_short))
            .setMinuteDrawable(minuteDrawable ?: context.getDrawable(R.drawable.clock_00_long))
            .setSecondDrawable(secondDrawable ?: context.getDrawable(R.drawable.clock_00_second))
        val faceColor = typedArray.getColor(R.styleable.SimpleAnalogClock_faceTint, -1)
        val hourColor = typedArray.getColor(R.styleable.SimpleAnalogClock_hourTint, -1)
        val minuteColor = typedArray.getColor(R.styleable.SimpleAnalogClock_minuteTint, -1)
        val secondColor = typedArray.getColor(R.styleable.SimpleAnalogClock_secondTint, -1)
        if (faceColor != -1) setFaceTint(faceColor)
        if (hourColor != -1) setHourTint(hourColor)
        if (minuteColor != -1) setMinuteTint(minuteColor)
        if (secondColor != -1) setSecondTint(secondColor)
        rotateHourHand(typedArray.getFloat(R.styleable.SimpleAnalogClock_hourRotation, 0f))
        rotateMinuteHand(typedArray.getFloat(R.styleable.SimpleAnalogClock_minuteRotation, 0f))
        rotateSecondHand(typedArray.getFloat(R.styleable.SimpleAnalogClock_secondRotation, 0f))
    }
}