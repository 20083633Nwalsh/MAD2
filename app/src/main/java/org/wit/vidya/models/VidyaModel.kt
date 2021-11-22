package org.wit.vidya.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VidyaModel(      var id: Long = 0,
                            var title: String = "",
                          var description: String = "") : Parcelable
