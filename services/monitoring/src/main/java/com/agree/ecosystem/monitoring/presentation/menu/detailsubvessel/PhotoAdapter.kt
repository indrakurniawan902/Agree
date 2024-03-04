package com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel

import android.view.ViewGroup
import android.widget.FrameLayout
import com.agree.ecosystem.monitoring.databinding.ItemPhotoBinding
import com.devbase.presentation.recyclerview.DevRecyclerViewAdapter
import com.devbase.presentation.recyclerview.viewbinding.DevItemViewHolder

class PhotoAdapter(
    private val isMargin: Boolean = false,
    private val callback: (Int) -> Unit
) : DevRecyclerViewAdapter<String>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DevItemViewHolder<String> =
        ViewHolder(ItemPhotoBinding.inflate(getLayoutInflater(parent), parent, false))

    inner class ViewHolder(private val bindLayout: ItemPhotoBinding) :
        DevItemViewHolder<String>(bindLayout) {
        override fun bind(data: String?) {
            with(bindLayout) {
                data?.let { url ->
                    if (isMargin) {
                        val layoutParams = imgPhoto.layoutParams as FrameLayout.LayoutParams
                        val margin = 25
                        layoutParams.setMargins(margin, margin, margin, margin)
                        layoutParams.width = 220
                        layoutParams.height = 140
                        imgPhoto.layoutParams = layoutParams
                    }
                    imgPhoto.url = url
                    imgPhoto.setOnClickListener {
                        callback.invoke(absoluteAdapterPosition)
                    }
                }
            }
        }
    }
}
