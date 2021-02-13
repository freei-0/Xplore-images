package demo.freei.xploreimages.api

import demo.freei.xploreimages.BuildConfig
import demo.freei.xploreimages.models.Image
import demo.freei.xploreimages.models.ImagesResponse
import demo.freei.xploreimages.util.RequestConfig.Companion.INITIAL_PAGE_NUMBER
import demo.freei.xploreimages.util.RequestConfig.Companion.QUERY_PAGE_SIZE
import retrofit2.http.GET
import retrofit2.http.Query

interface ImagesApi {

    @GET("/photos")
    suspend fun getImages(
        @Query("page")
        pageNumber: Int = INITIAL_PAGE_NUMBER,
        @Query("per_page")
        perPageCount: Int = QUERY_PAGE_SIZE,
        @Query("client_id")
        clientId: String = BuildConfig.ACCESS_KEY
    ): MutableList<Image>

    @GET("search/photos")
    suspend fun searchImages(
        @Query("query")
        searchQuery: String = "",
        @Query("page")
        pageNumber: Int = INITIAL_PAGE_NUMBER,
        @Query("per_page")
        perPageCount: Int = QUERY_PAGE_SIZE,
        @Query("order_by")
        orderBy: String = "latest",
        @Query("client_id")
        clientId: String = BuildConfig.ACCESS_KEY
    ): ImagesResponse

}