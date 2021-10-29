package com.github.didahdx.timelogger.presentation.clock_screen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import autodispose2.androidx.lifecycle.scope
import autodispose2.autoDispose
import com.github.didahdx.sampleanalogclock.SimpleAnalogClock
import com.github.didahdx.timelogger.R
import com.github.didahdx.timelogger.common.Resource
import com.github.didahdx.timelogger.data.remote.dto.ProgramDto
import com.github.didahdx.timelogger.databinding.ActivityMainBinding
import com.github.didahdx.timelogger.presentation.extensions.snackBar
import com.github.didahdx.timelogger.presentation.report_screen.ReportActivity
import dagger.android.AndroidInjection
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject

/**
 * Created by Daniel Didah on 9/25/21.
 */
class MainActivity : AppCompatActivity() {


    val rand = Random()

    enum class ClockFaceColorEnum {
        PURPLE, YELLOW, RED, GREEN, BLUE
    }

    enum class WallColorEnum {
        TealGreen, DeepPink, Rufous, Indigo, Tangerine
    }

    private val wallColor = arrayListOf<String>(
        WallColorEnum.TealGreen.name,
        WallColorEnum.DeepPink.name,
        WallColorEnum.Rufous.name,
        WallColorEnum.Indigo.name,
        WallColorEnum.Tangerine.name,
    )

    enum class HourHandColor {
        PictorialCarmine, SpanishGray, Crayola, Malachite, HonoluluBlue
    }

    private val hourHandColor = arrayListOf<String>(
        HourHandColor.PictorialCarmine.name,
        HourHandColor.SpanishGray.name,
        HourHandColor.Crayola.name,
        HourHandColor.Malachite.name,
        HourHandColor.HonoluluBlue.name,
    )

    private val clockFaceColor = arrayListOf<String>(
        ClockFaceColorEnum.PURPLE.name,
        ClockFaceColorEnum.YELLOW.name,
        ClockFaceColorEnum.RED.name,
        ClockFaceColorEnum.GREEN.name,
        ClockFaceColorEnum.BLUE.name,
    )

    @Inject
    lateinit var viewModel: ViewModelProvider.Factory

    lateinit var mainActivityViewModel: MainActivityViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var clock: SimpleAnalogClock

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clock = binding.clock
        mainActivityViewModel =
            ViewModelProvider(this, viewModel).get(MainActivityViewModel::class.java)
        mainActivityViewModel.state.observe(this, { it ->
            when (it) {
                is Resource.Success -> {
                    binding.displayMessage.text = it.data?.displayMessage ?: ""
                    it.data?.clockFaceColor?.let { color -> setClockFaceColor(color) }
                    it.data?.hourHandColor?.let { color -> setHourHandColor(color) }
                    it.data?.wallColor?.let { color -> setWallColor(color) }
                }
                is Resource.Error -> {
                    binding.constraint.snackBar("${it.message}")
                }
                is Resource.Loading -> {
                    //loading
                }
            }
        })

        mainActivityViewModel.timer.observe(this, { miliseconds ->
            val clockTick = miliseconds / 1000
            val millisec =miliseconds % 1000
            mainActivityViewModel.second = (clockTick % 60).toInt()
            mainActivityViewModel.minute = (clockTick / (60)).toInt()
            val numberFormat: NumberFormat = NumberFormat.getInstance(Locale.ENGLISH)
            numberFormat.minimumIntegerDigits = 2
            clock.setTime(
                mainActivityViewModel.hour,
                numberFormat.format(mainActivityViewModel.minute).toInt(),
                numberFormat.format(mainActivityViewModel.second).toInt(),
                millisec.toInt()
            )
            val random: Int = rand.nextInt(4)
            if (miliseconds.toInt() % 30000 == 0) {
                mainActivityViewModel.startServer(
                    ProgramDto(
                        "${mainActivityViewModel.hour}:${mainActivityViewModel.minute}:${mainActivityViewModel.second}",
                        hourHandColor[random],
                        wallColor[random],
                        clockFaceColor[random]
                    )
                )
            }

            if (miliseconds.toInt() % 40000 == 0) {
                mainActivityViewModel.stopServer(
                    ProgramDto(
                        "${mainActivityViewModel.hour}:${mainActivityViewModel.minute}:${mainActivityViewModel.second}",
                        hourHandColor[random],
                        wallColor[random],
                        clockFaceColor[random]
                    )
                )
            }

            if (miliseconds.toInt() % 50000 == 0) {
                mainActivityViewModel.getReport(
                    ProgramDto(
                        "${mainActivityViewModel.hour}:${mainActivityViewModel.minute}:${mainActivityViewModel.second}",
                        hourHandColor[random],
                        wallColor[random],
                        clockFaceColor[random]
                    )
                )
            }

        })


        clock.setFaceDrawable(ContextCompat.getDrawable(this, R.drawable.ic_clock_face_green))
        binding.report.setOnClickListener {
            startActivity(Intent(this, ReportActivity::class.java))
        }

        clock.setTime(
            mainActivityViewModel.hour,
            mainActivityViewModel.minute,
            mainActivityViewModel.second
        )

    }

    private fun setWallColor(color: String) {
        when (color) {
            WallColorEnum.Tangerine.name -> {
                binding.constraint.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.tangerine
                    )
                )
            }
            WallColorEnum.Indigo.name -> {
                binding.constraint.setBackgroundColor(ContextCompat.getColor(this, R.color.indigo))
            }
            WallColorEnum.Rufous.name -> {
                binding.constraint.setBackgroundColor(ContextCompat.getColor(this, R.color.rufous))
            }
            WallColorEnum.TealGreen.name -> {
                binding.constraint.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.teal_green
                    )
                )
            }
            WallColorEnum.DeepPink.name -> {
                binding.constraint.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.deep_pink
                    )
                )
            }
        }
    }

    private fun setHourHandColor(color: String) {
        when (color) {
            HourHandColor.HonoluluBlue.name -> {
                clock.setHourTint(R.color.honolulu_blue)
            }
            HourHandColor.Malachite.name -> {
                clock.setHourTint(R.color.malachite)
            }
            HourHandColor.Crayola.name -> {
                clock.setHourTint(R.color.crayola)
            }
            HourHandColor.SpanishGray.name -> {
                clock.setHourTint(R.color.spanish_gray)
            }
            HourHandColor.PictorialCarmine.name -> {
                clock.setHourTint(R.color.pictorial_carmine)
            }

        }
    }

    private fun setClockFaceColor(clockFaceColor: String) {
        when (clockFaceColor) {
            ClockFaceColorEnum.PURPLE.name -> {
                binding.clock.setFaceDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_clock_face_purple
                    )
                )
            }
            ClockFaceColorEnum.YELLOW.name -> {
                binding.clock.setFaceDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_clock_face_yellow
                    )
                )
            }
            ClockFaceColorEnum.RED.name -> {
                binding.clock.setFaceDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_clock_face_red
                    )
                )
            }
            ClockFaceColorEnum.GREEN.name -> {
                binding.clock.setFaceDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_clock_face_green
                    )
                )
            }
            ClockFaceColorEnum.BLUE.name -> {
                binding.clock.setFaceDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_clock_face_blue
                    )
                )
            }
        }
    }

}