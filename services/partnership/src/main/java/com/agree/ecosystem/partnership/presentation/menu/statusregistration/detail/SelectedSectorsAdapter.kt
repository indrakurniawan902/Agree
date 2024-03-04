package com.agree.ecosystem.partnership.presentation.menu.statusregistration.detail

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.agree.ecosystem.partnership.R
import com.agree.ecosystem.partnership.databinding.ItemSelectedSectorsBinding
import com.agree.ecosystem.utilities.domain.reqres.model.subsector.SubSector
import com.devbase.presentation.recyclerview.DevRecyclerViewAdapter
import com.devbase.presentation.recyclerview.viewbinding.DevItemViewHolder

class SelectedSectorsAdapter : DevRecyclerViewAdapter<SubSector>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevItemViewHolder<SubSector> =
        ViewHolder(ItemSelectedSectorsBinding.inflate(getLayoutInflater(parent), parent, false))

    inner class ViewHolder(private val bindLayout: ItemSelectedSectorsBinding) :
        DevItemViewHolder<SubSector>(bindLayout) {
        override fun bind(data: SubSector?) {
            with(bindLayout) {
                data?.let { sector ->
                    tvSectorName.text = root.context.getString(R.string.label_subsector_name, sector.sectorName, sector.name)
                    rvCommodities.apply {
                        adapter = SelectedCommoditiesAdapter().apply {
                            clear()
                            addAll(sector.commodities)
                        }
                        setRecycledViewPool(RecyclerView.RecycledViewPool())
                    }
                }
            }
        }
    }
}
