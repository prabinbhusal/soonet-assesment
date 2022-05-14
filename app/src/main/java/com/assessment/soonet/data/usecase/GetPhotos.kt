package com.assessment.soonet.data.usecase

import com.assessment.soonet.data.model.Photo
import com.assessment.soonet.data.repository.RemoteDataRepository
import com.assessment.soonet.data.state.DataState
import com.assessment.soonet.data.state.parseResponse
import com.assessment.soonet.util.BaseUseCase
import javax.inject.Inject

class GetPhotos
@Inject
constructor(
    private val repository: RemoteDataRepository
) : BaseUseCase.Params<DataState<List<Photo>?>?, GetPhotos.Params> {

    override suspend fun invoke(params: Params): DataState<List<Photo>>? {
        return when (val response = repository.getPhotos(page = params.page).parseResponse()) {
            is DataState.OnSuccess -> response.data?.let { DataState.OnSuccess(it) }
            is DataState.OnException -> DataState.OnException(response.e)
            is DataState.OnError -> DataState.OnError(response.errorBody, response.code)
        }
    }

    data class Params(
        val page: Int
    )
}
