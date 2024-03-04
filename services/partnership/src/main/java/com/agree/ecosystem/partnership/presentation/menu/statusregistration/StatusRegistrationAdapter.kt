package com.agree.ecosystem.partnership.presentation.menu.statusregistration

import android.view.ViewGroup
import com.agree.ecosystem.core.utils.utility.ConverterDate
import com.agree.ecosystem.core.utils.utility.convertUTCTimeTo
import com.agree.ecosystem.core.utils.utility.extension.getAttrsValue
import com.agree.ecosystem.partnership.R
import com.agree.ecosystem.partnership.databinding.ItemRegistrationStatusBinding
import com.agree.ecosystem.partnership.domain.reqres.model.registrationstatus.RegistrationStatus
import com.devbase.presentation.databinding.ItemLoadMoreBinding
import com.devbase.presentation.recyclerview.DevRecyclerViewAdapter
import com.devbase.presentation.recyclerview.viewbinding.DevEndlessItemViewHolder
import com.devbase.presentation.recyclerview.viewbinding.DevItemViewHolder

class StatusRegistrationAdapter(
    private val onItemClicked: (registrationStatus: RegistrationStatus) -> Unit
) : DevRecyclerViewAdapter<RegistrationStatus>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevItemViewHolder<RegistrationStatus> =
        when (viewType) {
            CONTENT -> ViewHolder(
                ItemRegistrationStatusBinding.inflate(getLayoutInflater(parent), parent, false)
            )
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

    inner class ViewHolder(private val bindLayout: ItemRegistrationStatusBinding) :
        DevItemViewHolder<RegistrationStatus>(bindLayout) {
        override fun bind(data: RegistrationStatus?) {
            with(bindLayout) {
                data?.let { registrationStatus ->
                    val context = root.context
                    imgPartnerLogo.url = registrationStatus.companyLogo
                    tvPartnerName.text = registrationStatus.companyName
                    tvRequestDate.text = context.getString(
                        R.string.label_status_registration_submit_date,
                        registrationStatus.createdAt.convertUTCTimeTo(ConverterDate.FULL_DATE)
                    )
                    tvRegistrationStatus.apply {
                        text = registrationStatus.status.alias
                        setTextColor(registrationStatus.status.textColor.getAttrsValue(context))
                    }
                    cvRegistrationStatus.setCardBackgroundColor(registrationStatus.status.backgroundColor.getAttrsValue(context))
                    root.setOnClickListener {
                        onItemClicked(registrationStatus)
                    }
                }
            }
        }
    }
}
