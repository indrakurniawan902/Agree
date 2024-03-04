package com.agree.ecosystem.partnership.presentation.menu.registration

import android.view.ViewGroup
import com.agree.ecosystem.partnership.databinding.ItemPartnershipTypeBinding
import com.agree.ecosystem.partnership.domain.reqres.model.company.PartnerType
import com.devbase.presentation.recyclerview.DevRecyclerViewAdapter
import com.devbase.presentation.recyclerview.viewbinding.DevItemViewHolder

class PartnershipTypeAdapter : DevRecyclerViewAdapter<PartnerType>() {

    private lateinit var partnerTypes: List<PartnerType?>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevItemViewHolder<PartnerType> =
        ViewHolder(ItemPartnershipTypeBinding.inflate(getLayoutInflater(parent), parent, false))

    inner class ViewHolder(private val bindLayout: ItemPartnershipTypeBinding) :
        DevItemViewHolder<PartnerType>(bindLayout) {
        override fun bind(data: PartnerType?) {
            with(bindLayout) {
                data?.let { partner ->
                    tvContent.text = partner.name
                    tvDescription.text = partner.desc
                }
            }
        }
    }

    fun setPartnershipType(partnerTypes: List<PartnerType?>) {
        this.partnerTypes = partnerTypes
    }
}
