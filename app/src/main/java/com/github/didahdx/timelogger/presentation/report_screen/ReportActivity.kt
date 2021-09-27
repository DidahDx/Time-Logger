package com.github.didahdx.timelogger.presentation.report_screen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.github.didahdx.timelogger.common.Resource
import com.github.didahdx.timelogger.databinding.ActivityReportBinding
import com.github.didahdx.timelogger.presentation.extensions.hide
import com.github.didahdx.timelogger.presentation.extensions.show
import dagger.android.AndroidInjection
import javax.inject.Inject

class ReportActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var reportViewModel: ReportViewModel
    lateinit var binding: ActivityReportBinding
    val reportHeader = "| ProgramTime | Event | Message | ActualTime | DisplayMessage |\n"

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityReportBinding.inflate(layoutInflater)
        setContentView(binding.root)
        reportViewModel = ViewModelProvider(this, viewModelFactory)
            .get(ReportViewModel::class.java)

        reportViewModel.state.observe(this, { results ->
            results?.let { result ->
                when (result) {
                    is Resource.Loading -> {
                        binding.progress.show()
                        binding.report.hide()
                    }
                    is Resource.Success -> {
                        binding.progress.hide()
                        binding.report.show()
                        binding.report.text = String.format(
                            "%s %s", reportHeader, result.data.toString()
                                .replace("[", "")
                                .replace("]", "")
                                .replace(",", "")
                        )
                    }
                    is Resource.Error -> {
                        binding.progress.hide()
                        binding.report.show()
                        binding.report.text= result.message
                    }
                }
            }
        })
    }
}