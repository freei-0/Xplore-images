package demo.freei.xploreimages.images

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import demo.freei.xploreimages.repository.ImagesRepository
import demo.freei.xploreimages.util.RequestConfig.Companion.SEARCH_IMAGES_TIME_DELAY
import demo.freei.xploreimages.util.debounce
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject


@HiltViewModel
class SearchImageViewModel @Inject constructor(
    private val imagesRepository: ImagesRepository,
    state: SavedStateHandle
) : ViewModel() {

    private val imagesLiveData = state.getLiveData("", "")

    val imagesPagingData = imagesLiveData
        .debounce(SEARCH_IMAGES_TIME_DELAY, CoroutineScope(Dispatchers.Main))
        .switchMap { queryString ->
            imagesRepository.getImages(queryString).cachedIn(viewModelScope)
        }

    fun requestImages(query: String) {
        imagesLiveData.value = query
    }

}