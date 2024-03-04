package com.agree.ecosystem.common.presentation.navigation

import android.app.Activity
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.agree.ecosystem.agreepedia.domain.model.article.Article
import com.agree.ecosystem.agreepedia.presentation.base.activity.AgreepediaActivity
import com.agree.ecosystem.auth.presentation.base.activity.AuthActivity
import com.agree.ecosystem.auth.presentation.navigation.navparams.changepassword.FromParams
import com.agree.ecosystem.auth.presentation.navigation.navparams.screen.NavigationParams
import com.agree.ecosystem.common.R
import com.agree.ecosystem.common.domain.reqres.model.notification.Inbox
import com.agree.ecosystem.common.presentation.menu.home.HomeFragmentDirections
import com.agree.ecosystem.common.presentation.menu.notifications.NotificationsFragmentDirections
import com.agree.ecosystem.common.presentation.menu.partnership.PartnershipFragmentDirections
import com.agree.ecosystem.common.utils.enums.notification.StatesInbox
import com.agree.ecosystem.core.utils.utility.navigation.getDefaultNavOptions
import com.agree.ecosystem.finances.presentation.base.activity.finance.FinanceActivity
import com.agree.ecosystem.monitoring.MonitoringId
import com.agree.ecosystem.monitoring.presentation.base.activity.activitysop.ActivitySopActivity
import com.agree.ecosystem.monitoring.presentation.base.activity.detailsubvassel.DetailSubVesselActivity
import com.agree.ecosystem.monitoring.presentation.base.activity.monitoring.MonitoringActivity
import com.agree.ecosystem.monitoring.presentation.base.activity.settingsoffline.SettingsOfflineMonitoringActivity
import com.agree.ecosystem.partnership.presentation.base.activity.liststatusregister.ListStatusRegisterActivity
import com.agree.ecosystem.partnership.presentation.base.activity.registerpartnership.RegisterPartnershipActivity
import com.agree.ecosystem.partnership.presentation.base.activity.statusregister.StatusRegisterActivity
import com.agree.ecosystem.users.presentation.base.activity.profile.ProfileActivity
import com.agree.ecosystem.utilities.domain.reqres.model.subsector.SubSector
import com.agree.ecosystem.utilities.presentation.base.activity.UtilitiesActivity
import com.agree.ecosystem.utilities.presentation.navigation.NavigationScreen
import com.oratakashi.viewbinding.core.tools.startActivity

class MenuNavigationImpl(
    private val nav: NavController?,
    private val activity: Activity?,
) : MenuNavigation {

    override fun getNavController(): NavController? {
        return nav
    }

    override fun goToPrevious() {
        nav?.navigateUp()
    }

    override fun fromSettingsToProfile() {
        runCatching {
            activity?.startActivity(ProfileActivity::class.java)
        }.onFailure {
            it.printStackTrace()
        }
    }

    override fun fromSettingsToTermsConditions() {
        runCatching {
            activity?.startActivity(UtilitiesActivity::class.java) {
                it.putExtra("screen", NavigationScreen.Tnc.name)
            }
        }.onFailure {
            it.printStackTrace()
        }
    }

    override fun fromSettingsToAboutAgree() {
        runCatching {
            activity?.startActivity(UtilitiesActivity::class.java) {
                it.putExtra("screen", NavigationScreen.About.name)
            }
        }.onFailure {
            it.printStackTrace()
        }
    }

    override fun fromSettingsToHelp() {
        runCatching {
            activity?.startActivity(UtilitiesActivity::class.java) {
                it.putExtra("screen", NavigationScreen.Help.name)
            }
        }.onFailure {
            it.printStackTrace()
        }
    }

    override fun fromSettingsToOfflineMonitoring() {
        runCatching {
            activity?.startActivity(SettingsOfflineMonitoringActivity::class.java)
        }.onFailure {
            it.printStackTrace()
        }
    }

    override fun fromHomeToHelp() {
        runCatching {
            activity?.startActivity(UtilitiesActivity::class.java) {
                it.putExtra("screen", NavigationScreen.Help.name)
            }
        }.onFailure {
            it.printStackTrace()
        }
    }

    override fun fromHomeToCompanies() {
        runCatching {
            activity?.startActivity(RegisterPartnershipActivity::class.java)
        }
    }

    override fun fromHomeToAgreepedia() {
        runCatching {
            activity?.startActivity(AgreepediaActivity::class.java)
        }.onFailure {
            it.printStackTrace()
        }
    }

    override fun fromHomeToCompanyPartnerDetail(id: String) {
        runCatching {
            activity?.startActivity(RegisterPartnershipActivity::class.java) {
                it.putExtra("id", id)
            }
        }.onFailure {
            it.printStackTrace()
        }
    }

    override fun fromHomeToDetailSubVessel(id: String, sectorId: String) {
        runCatching {
            activity?.startActivity(DetailSubVesselActivity::class.java) {
                it.putExtra("id", id)
                it.putExtra("sectorId", sectorId)
            }
        }
    }

    override fun fromHomeToFinance(tab: Int) {
        runCatching {
            activity?.startActivity(FinanceActivity::class.java) {
                it.putExtra("initTab", tab)
            }
        }.onFailure { it.printStackTrace() }
    }

    override fun fromHomeToDetaiActiveLoan() {
        runCatching {
            activity?.startActivity(FinanceActivity::class.java) {
                it.putExtra("activeLoanDetail", "id")
            }
        }.onFailure { it.printStackTrace() }
    }

    override fun fromVesselToDetailMonitoring(id: String) {
        runCatching {
            activity?.startActivity(MonitoringActivity::class.java) {
                it.putExtra("id", id)
            }
        }
    }

    override fun fromHomeToAgreepediaDetail(article: Article) {
        runCatching {
            activity?.startActivity(AgreepediaActivity::class.java) {
                it.putExtra("payload", article)
            }
        }.onFailure {
            it.printStackTrace()
        }
    }

    override fun fromListSubVesselToCompanies() {
        runCatching {
            activity?.startActivity(RegisterPartnershipActivity::class.java)
        }
    }

    override fun fromListVesselToCompanies() {
        runCatching {
            activity?.startActivity(RegisterPartnershipActivity::class.java)
        }
    }

    override fun fromNotificationListToDetailNotification(inboxId: Inbox) {
        runCatching {
            nav?.navigate(
                NotificationsFragmentDirections.actionNotificationsFragmentToDetailNotificationFragment(
                    inboxId.id
                )
            )
        }.onFailure {
            nav?.navigate(R.id.notificationsFragment, null, getDefaultNavOptions())
        }
    }

    override fun fromDetailNotificationToDetailStatusPartnership(idSubmission: String) {
        activity?.startActivity(StatusRegisterActivity::class.java) {
            it.putExtra(
                "submissionId",
                bundleOf("submissionId" to idSubmission)
            )
        }
    }

    override fun fromDetailNotificationToHistory(
        idSubVessel: String,
        createdAt: String,
        stateInbox: String
    ) {
        activity?.startActivity(ActivitySopActivity::class.java) {
            it.putExtra("subVesselId", idSubVessel)
            it.putExtra("date", createdAt)

            it.putExtra(
                "destinationId",
                if (stateInbox.contains(StatesInbox.PAST_MONITORING.value))
                    MonitoringId.historyActivityFragment
                else MonitoringId.activitySopFragment
            )
        }
    }

    override fun fromHomeSectorToListSubVessel() {
        runCatching {
            nav?.navigate(
                HomeFragmentDirections.actionHomeFragmentToMonitoringFragment()
            )
        }.onFailure {
            nav?.navigate(R.id.monitoringFragment, null, getDefaultNavOptions())
        }
    }

    override fun fromPartnershipToDetailPartnership(companyId: String) {
        runCatching {
            nav?.navigate(
                PartnershipFragmentDirections.actionPartnershipListFragmentToDetailPartnershipFragment(
                    companyId
                )
            )
        }.onFailure {
            nav?.navigate(R.id.detailPartnershipFragment, null, getDefaultNavOptions())
        }
    }

    override fun fromPartnershipToStatusRegistration() {
        activity?.startActivity(ListStatusRegisterActivity::class.java)
    }

    override fun fromPartnershipToCompanies(subSector: List<SubSector>) {
        runCatching {
            activity?.startActivity(RegisterPartnershipActivity::class.java) {
                it.putExtra("sector", bundleOf("selectedSector" to subSector))
            }
        }
    }

    override fun fromDetailPartnershipToDetailCompany(companyId: String) {
        runCatching {
            activity?.startActivity(RegisterPartnershipActivity::class.java) {
                it.putExtra("id", companyId)
            }
        }.onFailure {
            it.printStackTrace()
        }
    }

    override fun fromMenuToChangePassword() {
        runCatching {
            activity?.startActivity(AuthActivity::class.java) {
                it.putExtra("screen", NavigationParams.CHANGE_PASSWORD.name)
                it.putExtra("from", FromParams.MENU.name)
            }
        }.onFailure {
            it.printStackTrace()
        }
    }

    override fun fromMenuToLogin() {
        runCatching {
            activity?.startActivity(AuthActivity::class.java) {
                it.putExtra("screen", NavigationParams.LOGIN.name)
            }
        }.onFailure {
            it.printStackTrace()
        }
    }
}
