package com.assessment.soonet.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.assessment.soonet.data.model.Photo
import com.assessment.soonet.data.usecase.GetPhotos
import com.assessment.soonet.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel
@Inject
constructor(
    private val getPhotos: GetPhotos,
) : ViewModel() {

    private val _pagingEvent = MutableLiveData<PagingData<Photo>>()
    val pagingEvent: LiveData<PagingData<Photo>> get() = _pagingEvent

    init {
        getPhotos()
    }

    private fun getPhotos() {
        viewModelScope.launch {
            Pager(config = PagingConfig(pageSize = Constants.PAGE_SIZE)) {
                DashboardPagingSource(getPhotos = getPhotos)
            }.flow.cachedIn(viewModelScope).collect {
                _pagingEvent.value = it
            }

        }
    }
}
