package com.agree.ecosystem.finances.presentation.menu.finance.cultivator.data.profilefarmer.dynamic

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.agree.ecosystem.finances.data.reqres.model.DynamicFormBaseItem
import com.agree.ecosystem.finances.data.reqres.model.SingleField
import com.agree.ui.builder.Builder
import com.agree.ui.databinding.LayoutFormContainerBinding
import com.devbase.presentation.recyclerview.DevRecyclerViewAdapter
import com.devbase.presentation.recyclerview.viewbinding.DevItemViewHolder
import timber.log.Timber

class DynamicFormInfoCultivatorAdapter(context: Context) :
    DevRecyclerViewAdapter<DynamicFormBaseItem>() {

    private val builder: Builder by lazy {
        Builder(context = context)
    }


    override fun getItemViewType(position: Int): Int {
        return listData[position]?.type?.ordinal ?: 0
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DevItemViewHolder<DynamicFormBaseItem> {
        return ViewHolder(
            LayoutFormContainerBinding.inflate(
                getLayoutInflater(parent),
                parent,
                false
            )
        )
    }

    inner class ViewHolder(val bindLayout: ViewBinding) :
        DevItemViewHolder<DynamicFormBaseItem>(bindLayout) {
        @SuppressLint("ResourceType")
        override fun bind(data: DynamicFormBaseItem?) {
            with(bindLayout) {
                data?.let { item ->
                    when (item) {
                        is SingleField -> {
                            val bind = this as LayoutFormContainerBinding
                            bind.containerDynamic.addView(builder.setupSingleTextField {
                                id = View.generateViewId()
                                tag = item.fieldTag
                                hint = item.hintText
                                text = item.defaultVal ?: ""
                                suffixText = item.suffix
                                if (item.isRequired) isRequired = item.isRequired else isOptional =
                                    true
                                isEnable = item.isEnable
//                            inputType = FormInputType.parseInputType(item.type.type)
                                placeHolder = item.placeholderText
                            })
                            Timber.tag("SINGLEFIELD").v("YA ${item.fieldTag}")

                        }

                        else -> {}
                    }
                }
            }
        }

    }

}