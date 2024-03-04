package com.agree.ecosystem.agreepedia.presentation.menu.home

import android.view.ViewGroup
import com.agree.ecosystem.agreepedia.databinding.ItemAgreepediaArticleBinding
import com.agree.ecosystem.agreepedia.domain.model.article.Article
import com.agree.ecosystem.core.utils.utility.ConverterDate
import com.devbase.presentation.databinding.ItemLoadMoreBinding
import com.devbase.presentation.recyclerview.DevRecyclerViewAdapter
import com.devbase.presentation.recyclerview.viewbinding.DevEndlessItemViewHolder
import com.devbase.presentation.recyclerview.viewbinding.DevItemViewHolder
import com.devbase.utils.ext.toDate
import com.devbase.utils.ext.toString
import com.oratakashi.viewbinding.core.binding.recyclerview.inflateBinding

class AgreepediaAdapter(
    private val onItemClicked: (article: Article) -> Unit
) : DevRecyclerViewAdapter<Article>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DevItemViewHolder<Article> =
        when (viewType) {
            CONTENT -> ViewHolder(inflateBinding(parent))
            else -> DevEndlessItemViewHolder(
                ItemLoadMoreBinding.inflate(getLayoutInflater(parent)),
                getLoadMoreListener(),
                isLoading,
                loadMoreSkip,
                loadMoreLimit
            )
        }

    override fun getItemViewType(position: Int) =
        if (listData[position] != null) CONTENT else LOAD_MORE

    inner class ViewHolder(private val bindLayout: ItemAgreepediaArticleBinding) :
        DevItemViewHolder<Article>(bindLayout) {
        override fun bind(data: Article?) {
            with(bindLayout) {
                data?.let { agreepedia ->
                    tvTitleArticle.text = agreepedia.blogName
                    tvAuthorArticle.text = agreepedia.createdBy
                    ivImgArticle.url = agreepedia.blogImage
                    tvDateArticle.text =
                        agreepedia.beginDate.toDate(ConverterDate.SQL_DATE.formatter)
                            ?.toString(ConverterDate.SIMPLE_DATE.formatter).orEmpty()
                    root.setOnClickListener {
                        onItemClicked(agreepedia)
                    }
                }
            }
        }
    }
}
