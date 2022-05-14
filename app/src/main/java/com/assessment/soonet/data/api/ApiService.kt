package com.assessment.soonet.data.api

import com.assessment.soonet.data.model.Photo
import com.assessment.soonet.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("list")
    suspend fun getPhotos(
        @Query("page") page: Int,
        @Query("limit") limit: Int = Constants.PAGE_SIZE
    ): Response<List<Photo>?>?
}
