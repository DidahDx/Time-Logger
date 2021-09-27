package com.github.didahdx.timelogger.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ProgramDto(
    @SerializedName("program_time")
    var programTime: String,
    @SerializedName("hour_hand_color")
    var hourHandColor: String,
    @SerializedName("wall_color")
    var wallColor: String,
    @SerializedName("clock_face_color")
    var clockFaceColor: String,
)