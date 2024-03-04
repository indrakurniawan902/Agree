package com.agree.ecosystem.partnership.presentation.navigation

import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import com.agree.ecosystem.core.utils.utility.navigation.getDefaultNavOptions
import com.agree.ecosystem.partnership.R
import com.agree.ecosystem.partnership.domain.reqres.model.company.Company
import com.agree.ecosystem.partnership.domain.reqres.model.registrationstatusdetails.RegistrationStatusDetails
import com.agree.ecosystem.partnership.presentation.base.activity.liststatusregister.ListStatusRegisterActivity
import com.agree.ecosystem.partnership.presentation.menu.companies.CompaniesFragmentDirections
import com.agree.ecosystem.partnership.presentation.menu.companies.detail.DetailCompanyFragmentDirections
import com.agree.ecosystem.partnership.presentation.menu.registration.RegistrationFragmentDirections
import com.agree.ecosystem.utilities.domain.reqres.model.commodity.Commodity
import com.agree.ecosystem.utilities.presentation.base.activity.UtilitiesActivity
import com.agree.ecosystem.utilities.presentation.navigation.NavigationScreen
import com.oratakashi.viewbinding.core.tools.startActivity

class RegistrationNavigationImpl(
    private val nav: NavController?,
    private val activity: FragmentActivity?
) : RegistrationNavigation {
    override fun getNavController(): NavController? {
        return nav
    }

    override fun goToPrevious() {
        nav?.navigateUp()
    }

    override fun fromRegistrationToAddAddress() {
        runCatching {
            nav?.navigate(RegistrationFragmentDirections.actionRegistrationFragmentToAddAddressFragment())
        }.onFailure {
            nav?.navigate(R.id.addAddressFragment, null, getDefaultNavOptions())
        }
    }

    override fun fromCompanyDetailsToRegistration(data: Company, dataCommodity: List<Commodity>) {
        runCatching {
            nav?.navigate(DetailCompanyFragmentDirections.actionDetailCompanyFragmentToRegistrationFragment(data, dataCommodity.toTypedArray()))
        }.onFailure {
            nav?.navigate(R.id.registrationFragment, null, getDefaultNavOptions())
        }
    }

    override fun fromRegistrationToStatusRegistration(data: RegistrationStatusDetails) {
        activity?.startActivity(ListStatusRegisterActivity::class.java) {
            it.putExtra("companyId", bundleOf("companyId" to data.companyId))
            it.putExtra("fromRegister", true)
        }
        activity?.finish()
    }

    override fun fromCompanyListToCompanyDetail(id: String) {
        runCatching {
            nav?.navigate(
                CompaniesFragmentDirections.actionCompaniesFragmentToDetailCompanyFragment(
                    id
                )
            )
        }.onFailure {
            nav?.navigate(
                R.id.detailCompanyFragment,
                bundleOf("companyId" to id),
                getDefaultNavOptions()
            )
        }
    }

    override fun fromRegistrationToTnc() {
        runCatching {
            activity?.startActivity(UtilitiesActivity::class.java) {
                it.putExtra("screen", NavigationScreen.Tnc.name)
            }
        }.onFailure {
            it.printStackTrace()
        }
    }
}
