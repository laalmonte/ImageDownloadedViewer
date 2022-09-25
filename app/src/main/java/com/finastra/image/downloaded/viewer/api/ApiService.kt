package com.finastra.image.downloaded.viewer.api

import com.finastra.image.downloaded.viewer.data.Tracks
import retrofit2.http.GET
import retrofit2.Call as Call1

interface ApiService {
    @GET("/search?term=star&amp;country=au&amp;media=movie&amp;all")
    fun getData(): Call1<Tracks>
}