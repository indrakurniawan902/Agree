package com.agree.ecosystem.finances.presentation.menu.finance.cultivator.data.profilefarmer

import android.graphics.drawable.Drawable
import android.view.ViewGroup
import com.agree.ecosystem.finances.R
import com.agree.ecosystem.finances.databinding.ItemListFarmerInfoBinding
import com.agree.ecosystem.finances.domain.reqres.model.ProfileFormData
import com.agree.ecosystem.finances.domain.reqres.model.profilefarmer.FarmerInfoPercentage
import com.devbase.presentation.recyclerview.DevRecyclerViewAdapter
import com.devbase.presentation.recyclerview.viewbinding.DevItemViewHolder
import com.devbase.utils.util.getColorResource
import com.devbase.utils.util.getDrawableResource
import com.devbase.utils.util.getStringResource
import com.oratakashi.viewbinding.core.image.load

class ProfileCultvatorAdapter(
    onItemClicked: (Map<String, FarmerInfoPercentage>?) -> Unit,
    val isFormLoan: Boolean
) : DevRecyclerViewAdapter<Map<String, FarmerInfoPercentage>>(onItemClicked) {


    inner class ViewHolder(private val bindLayout: ItemListFarmerInfoBinding) :
        DevItemViewHolder<Map<String, FarmerInfoPercentage>>(
            bindLayout
        ) {
        override fun bind(data: Map<String, FarmerInfoPercentage>?) {
            with(bindLayout) {
                data?.let { mapProfileItem ->
                    when (mapProfileItem.keys.first()) {
                        ProfileFormData.PERSONAL_INFO.label -> {
                            bindData(
                                ProfileFormData.PERSONAL_INFO.value,
                                getStringResource(R.string.label_info_personal_description),
                                getDrawableResource(R.drawable.ic_personal_info)
                            )
                        }

                        ProfileFormData.FAMILY_INFO.label -> {
                            bindData(
                                ProfileFormData.FAMILY_INFO.value,
                                getStringResource(R.string.label_info_family_description),
                                getDrawableResource(R.drawable.ic_family_info)
                            )
                        }

                        ProfileFormData.SPOUSE_INFO.label -> {
                            bindData(
                                ProfileFormData.SPOUSE_INFO.value,
                                getStringResource(R.string.label_info_couple_description),
                                getDrawableResource(R.drawable.ic_spouse_info)
                            )
                        }

                        ProfileFormData.ADDRESS_INFO.label -> {
                            bindData(
                                ProfileFormData.ADDRESS_INFO.value,
                                getStringResource(R.string.label_info_address_description),
                                getDrawableResource(R.drawable.ic_address_info)
                            )
                        }

                        ProfileFormData.JOB_INFO.label -> {
                            bindData(
                                ProfileFormData.JOB_INFO.value,
                                getStringResource(R.string.label_info_jobs_description),
                                getDrawableResource(R.drawable.ic_job_info)
                            )
                        }

                        ProfileFormData.EMERGENCY_INFO.label -> {
                            bindData(
                                ProfileFormData.EMERGENCY_INFO.value,
                                getStringResource(R.string.label_emergency_contact_description),
                                getDrawableResource(R.drawable.ic_document_info)
                            )
                        }

                        ProfileFormData.BUSINESS_INFO.label -> {
                            bindData(
                                ProfileFormData.BUSINESS_INFO.value,
                                getStringResource(R.string.label_info_business_description),
                                getDrawableResource(R.drawable.ic_business_info)
                            )
                        }

                        ProfileFormData.ASSET_INFO.label -> {
                            bindData(
                                ProfileFormData.ASSET_INFO.value,
                                getStringResource(R.string.label_info_asset_description),
                                getDrawableResource(R.drawable.ic_asset_info)
                            )
                        }

                        ProfileFormData.BANK_INFO.label -> {
                            bindData(
                                ProfileFormData.BANK_INFO.value,
                                getStringResource(R.string.label_info_bank_description),
                                getDrawableResource(R.drawable.ic_bank_info)
                            )
                        }

                        ProfileFormData.PHOTOS_DATA.label -> {
                            bindData(
                                ProfileFormData.PHOTOS_DATA.value,
                                getStringResource(R.string.label_info_document_description),
                                getDrawableResource(R.drawable.ic_document_info)
                            )
                        }
                    }
                    if (mapProfileItem.values.first().isCompleted) {
                        tvInfoFarmerItem.text =
                            root.resources.getString(R.string.label_data_completed)
                    } else {
                        tvInfoFarmerItem.text = root.resources.getString(
                            R.string.label_info_data_cultivator_borrower,
                            mapProfileItem.values.first().mandatoryFields.toString(),
                            mapProfileItem.values.first().filledFields.toString()
                        )
                        tvInfoFarmerItem.setTextColor(getColorResource(com.agree.ui.R.color.warning_500))
                    }
                }
            }
        }

        private fun bindData(infoTitle: String, infoContent: String, icon: Drawable?) {
            with(bindLayout) {
                if (isFormLoan) {

                } else {
                    tvFarmerInfoTitle.text = infoTitle
                }
                tvFarmerInfoContent.text = infoContent
                icon?.let {
                    ivInfoIcon.load(it)
                }
            }
        }
    }


    override fun getItemViewType(position: Int) =
        if (listData[position] != null) CONTENT else LOAD_MORE


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DevItemViewHolder<Map<String, FarmerInfoPercentage>> {
        return ViewHolder(
            ItemListFarmerInfoBinding.inflate(
                getLayoutInflater(parent),
                parent,
                false
            )
        )
    }
}
