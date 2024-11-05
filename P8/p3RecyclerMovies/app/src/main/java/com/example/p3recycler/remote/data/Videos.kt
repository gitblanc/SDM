package com.example.p3recycler.remote.data


import com.google.gson.annotations.SerializedName

data class Videos(
    @SerializedName("results")
    val results: List<Any>
)