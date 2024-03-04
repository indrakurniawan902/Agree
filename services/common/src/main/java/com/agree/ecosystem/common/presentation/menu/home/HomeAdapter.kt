package com.agree.ecosystem.common.presentation.menu.home

import android.view.ViewGroup
import com.agree.ecosystem.common.databinding.ItemPartnerAgreeBinding
import com.agree.ecosystem.common.domain.reqres.model.home.companypartner.CompanyPartner
import com.devbase.presentation.recyclerview.DevRecyclerViewAdapter
import com.devbase.presentation.recyclerview.viewbinding.DevItemViewHolder

class HomeAdapter(
    onClick: (companyPartner: CompanyPartner?) -> Unit
) : DevRecyclerViewAdapter<CompanyPartner>(onClickListener = onClick) {

    inner class ViewHolder(private val bindLayout: ItemPartnerAgreeBinding) :
        DevItemViewHolder<CompanyPartner>(bindLayout) {
        override fun bind(data: CompanyPartner?) {
            with(bindLayout) {
                data?.let { companyPartner ->
                    tvCompany.text = companyPartner.name
                    ivCompany.url = companyPartner.image
                }
                (binding.root.layoutParams as ViewGroup.MarginLayoutParams).apply {
                    if (layoutPosition == 4) rightMargin = 0
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DevItemViewHolder<CompanyPartner> {
        return ViewHolder(ItemPartnerAgreeBinding.inflate(getLayoutInflater(parent), parent, false))
    }
}
