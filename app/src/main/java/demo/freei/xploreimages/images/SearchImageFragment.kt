package demo.freei.xploreimages.images

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import demo.freei.xploreimages.R
import demo.freei.xploreimages.databinding.FragmentSearchImageBinding
import demo.freei.xploreimages.models.Image
import demo.freei.xploreimages.util.viewBinding

/**
 * A fragment representing a list of Items.
 */

@AndroidEntryPoint
class SearchImageFragment : Fragment(R.layout.fragment_search_image) {

    private val viewModel by viewModels<SearchImageViewModel>()
    private val binding by viewBinding(FragmentSearchImageBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val adapter = SearchImageAdapter(object : SearchImageAdapter.OnImageClickListener {
            override fun onClick(image: Image) {
                navigateToImageDetailScreen(image)
            }
        })
        binding.apply {
            rvImages.setHasFixedSize(true)
            rvImages.itemAnimator = null
            rvImages.adapter = adapter
        }

        viewModel.imagesPagingData.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        adapter.addLoadStateListener { loadState ->
            binding.apply {
                pbImages.isVisible = loadState.source.refresh is LoadState.Loading
                rvImages.isVisible = loadState.source.refresh is LoadState.NotLoading
                ivImagesError.isVisible = loadState.source.refresh is LoadState.Error

                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    adapter.itemCount < 1
                ) {
                    rvImages.isVisible = false
                    tvNoData.isVisible = true
                } else {
                    tvNoData.isVisible = false
                }
            }
        }

        setupSearchEditText()
    }

    private fun setupSearchEditText() {
        binding.etSearch.doAfterTextChanged { editable ->
            editable?.let {
                viewModel.requestImages(editable.toString())
            }
        }
    }

    private fun navigateToImageDetailScreen(image: Image) {
        val bundle = Bundle().apply {
            putParcelable("image", image)
        }
        findNavController().navigate(
            R.id.action_searchImageFragment_to_imageDetailFragment,
            bundle
        )
    }
}