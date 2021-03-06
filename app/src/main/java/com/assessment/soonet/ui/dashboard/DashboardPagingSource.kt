package com.assessment.soonet.ui.dashboard

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.assessment.soonet.data.model.Photo
import com.assessment.soonet.data.state.DataState
import com.assessment.soonet.data.usecase.GetPhotos

class DashboardPagingSource(
    private val getPhotos: GetPhotos
) : PagingSource<Int, Photo>() {

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val page = params.key ?: 1
        return try {
            when (val response = getPhotos.invoke(GetPhotos.Params(page = page))) {
                is DataState.OnSuccess -> LoadResult.Page(
                    data = response.data,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (response.data.isEmpty()) null else page + 1
                )
                else -> LoadResult.Error(Exception("Error loading data!"))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
