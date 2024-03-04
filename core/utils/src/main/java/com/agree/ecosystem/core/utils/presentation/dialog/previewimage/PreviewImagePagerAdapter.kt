package com.agree.ecosystem.core.utils.presentation.dialog.previewimage

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.devbase.utils.ext.loadImage

class PreviewImagePagerAdapter : PagerAdapter() {
    override fun getCount(): Int {
        return images.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): View {
        val img = PreviewImageView(container.context)
        img.loadImage(
            container.context,
            imageUrl = images[position],
            scaleType = android.widget.ImageView.ScaleType.CENTER_INSIDE
        )
//        img.setImageResource(images[position])
        container.addView(img, ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.MATCH_PARENT)
        return img
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    companion object {
//        private val images = intArrayOf(R.drawable.img1, R.drawable.img2, R.drawable.img3)
//        private val images = intArrayOf(R.drawable.img1, R.drawable.img2, R.drawable.img3)
        private val images = listOf(
            "https://img.freepik.com/free-photo/tropical-green-leaves-background_53876-88891.jpg",
            "https://img.freepik.com/free-photo/low-angle-view-palm-trees-blue-sky-sunlight-during-daytime_181624-20499.jpg",
            "https://img.freepik.com/free-vector/summer-landscape-background-zoom_52683-42162.jpg"
        )
    }
}
