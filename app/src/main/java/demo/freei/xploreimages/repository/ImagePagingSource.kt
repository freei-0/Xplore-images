package demo.freei.xploreimages.repository

import android.text.TextUtils
import androidx.paging.PagingSource
import androidx.paging.PagingState
import demo.freei.xploreimages.api.ImagesApi
import demo.freei.xploreimages.models.Image
import demo.freei.xploreimages.util.RequestConfig.Companion.INITIAL_PAGE_NUMBER
import retrofit2.HttpException
import java.io.IOException

/*
https://medium.com/swlh/paging3-recyclerview-pagination-made-easy-333c7dfa8797
*/

class ImagePagingSource(
    private val imagesApi: ImagesApi,
    private val query: String
) : PagingSource<Int, Image>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Image> {
        val position = params.key ?: INITIAL_PAGE_NUMBER

        return try {
            val images =
                if (TextUtils.isEmpty(query)) {
                    imagesApi.getImages(position, params.loadSize)
                } else {
                    imagesApi.searchImages(query, position, params.loadSize).results
                }

            LoadResult.Page(
                data = images,
                prevKey = if (position == INITIAL_PAGE_NUMBER) null else position - 1,
                nextKey = if (images.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Image>): Int? {
        return state.anchorPosition
    }
}