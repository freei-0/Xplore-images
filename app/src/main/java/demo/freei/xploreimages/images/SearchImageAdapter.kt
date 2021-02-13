package demo.freei.xploreimages.images

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import demo.freei.xploreimages.databinding.ItemSearchImageBinding
import demo.freei.xploreimages.models.Image


class SearchImageAdapter(private val listener: OnImageClickListener) :
    PagingDataAdapter<Image, SearchImageAdapter.ImageViewHolder>(diffCallback) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Image>() {
            override fun areItemsTheSame(oldItem: Image, newItem: Image) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Image, newItem: Image) =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding =
            ItemSearchImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    inner class ImageViewHolder(private val binding: ItemSearchImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    item?.let {
                        listener.onClick(it)
                    }
                }
            }
        }

        fun bind(image: Image) {
            binding.apply {
//                ivSearchImage.layout(0,0,0,0)
                Glide.with(itemView)
                    .load(image.urls?.regular)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(android.R.drawable.stat_notify_error)
                    .into(ivSearchImage)
            }
        }
    }

    interface OnImageClickListener {
        fun onClick(image: Image)
    }

}