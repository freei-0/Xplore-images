package demo.freei.xploreimages.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import demo.freei.xploreimages.api.ImagesApi
import demo.freei.xploreimages.util.RequestConfig.Companion.MAX_SIZE
import demo.freei.xploreimages.util.RequestConfig.Companion.PREFETCH_DISTANCE
import demo.freei.xploreimages.util.RequestConfig.Companion.QUERY_PAGE_SIZE
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImagesRepository @Inject constructor(private val imagesApi: ImagesApi) {

    fun getImages(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = QUERY_PAGE_SIZE,
                prefetchDistance = PREFETCH_DISTANCE,
                maxSize = MAX_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ImagePagingSource(imagesApi, query) }
        ).liveData

}