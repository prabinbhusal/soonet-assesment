package com.assessment.soonet.data.repository

import com.assessment.soonet.data.model.Photo
import retrofit2.Response

interface Repository {

    interface RemoteData {
        suspend fun getPhotos(page: Int): Response<List<Photo>?>?
    }
}
