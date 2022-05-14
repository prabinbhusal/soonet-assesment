package com.assessment.soonet.data.repository

import com.assessment.soonet.data.api.ApiService
import com.assessment.soonet.data.model.Photo
import retrofit2.Response
import javax.inject.Inject

class RemoteDataRepository
@Inject
constructor(
    private val api: ApiService
) : Repository.RemoteData {
    override suspend fun getPhotos(page: Int): Response<List<Photo>?>? {
        return api.getPhotos(page = page)
    }
}
