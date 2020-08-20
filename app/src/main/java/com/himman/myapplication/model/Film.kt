package com.himman.myapplication.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Film (
    var deskripsi: String? = "",
    var director: String? = "",
    var genre: String? = "",
    var judul: String? = "",
    var poster: String? = "",
    var rating: String? = ""

) : Parcelable