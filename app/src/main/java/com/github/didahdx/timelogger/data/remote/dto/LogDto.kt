package com.github.didahdx.timelogger.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LogDto(
    @SerializedName("actual_time")
    var actualTime: String,
    @SerializedName("clock_face_color")
    var clockFaceColor: String,
    @SerializedName("display_message")
    var displayMessage: String,
    @SerializedName("hour_hand_color")
    var hourHandColor: String,
    @SerializedName("number_of_servers")
    var numberOfServers: String,
    @SerializedName("wall_color")
    var wallColor: String,
    @SerializedName("program_time")
    var programTime: String,
    var event: String,
    var message:String
){
    override fun toString(): String {
        return "\n| $programTime | $event | $message | $actualTime | $displayMessage |\n"
    }
}