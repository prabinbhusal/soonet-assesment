package com.assessment.soonet.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Photo(
    val id: String,
    val author: String,
    val width: String,
    val height: String,
    val url: String,
    @SerializedName("download_url")
    val downloadUrl: String,
) : Parcelable
