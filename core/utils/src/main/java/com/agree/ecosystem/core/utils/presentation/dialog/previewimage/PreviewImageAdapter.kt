package com.agree.ecosystem.core.utils.presentation.dialog.previewimage

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView.ScaleType
import com.agree.ecosystem.core.utils.databinding.ItemPreviewImageBinding
import com.devbase.presentation.recyclerview.DevRecyclerViewAdapter
import com.devbase.presentation.recyclerview.viewbinding.DevItemViewHolder
import com.devbase.utils.ext.loadImage

class PreviewImageAdapter(
    private val context: Context
) : DevRecyclerViewAdapter<String>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevItemViewHolder<String> =
        ViewHolder(ItemPreviewImageBinding.inflate(getLayoutInflater(parent), parent, false))

    inner class ViewHolder(private val bindLayout: ItemPreviewImageBinding) :
        DevItemViewHolder<String>(bindLayout) {
        override fun bind(data: String?) {
            with(bindLayout) {
                data?.let { it ->
                    imgPhoto.loadImage(
                        context,
                        imageUrl = it,
                        scaleType = ScaleType.CENTER_INSIDE
                    )

                    imgPhoto.onFitScreen {
                        isFitScreen?.invoke(it)
                    }
                }
            }
        }
    }

    var isFitScreen: ((Boolean) -> Unit)? = null
    fun onFitScreen(isFitScreen: (Boolean) -> Unit) {
        this.isFitScreen = isFitScreen
    }
}
