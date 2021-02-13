package demo.freei.xploreimages.util

class RequestConfig {
    companion object {
        const val BASE_URL = "https://api.unsplash.com"
        const val SEARCH_IMAGES_TIME_DELAY = 400L

        /*PagingConfig*/
        const val INITIAL_PAGE_NUMBER = 1
        const val QUERY_PAGE_SIZE = 10
        const val PREFETCH_DISTANCE = 5

        /*MAX_SIZE = 100 items (Maximum size must be at least pageSize + 2*prefetchDist)*/
        const val MAX_SIZE = (QUERY_PAGE_SIZE + PREFETCH_DISTANCE * 2) * 5
    }
}