package com.agree.ecosystem.users.presentation.menu.profile

import com.agree.ecosystem.core.utils.base.AgrFragment
import com.agree.ecosystem.core.utils.domain.reqres.AgrPrefUsecase
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.core.utils.utility.navigation.navigation
import com.agree.ecosystem.users.R
import com.agree.ecosystem.users.databinding.FragmentProfileBinding
import com.agree.ecosystem.users.domain.reqres.model.profile.Profile
import com.agree.ecosystem.users.presentation.navigation.MainNavigation
import com.agree.ui.snackbar.errorSnackBar
import com.telkom.legion.component.viewstate.showDefaultLayout
import com.telkom.legion.component.viewstate.showLoadingLayout
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : AgrFragment<FragmentProfileBinding>(), ProfileDataContract {

    private val nav: MainNavigation by navigation { activity }
    private val viewModel: ProfileViewModel by viewModel()
    private val prefs: AgrPrefUsecase by inject()

    override fun initUI() {
        super.initUI()
        with(binding) {
            toolbar.setBackButtonOnClick { requireActivity().onBackPressed() }
        }
    }

    override fun initAction() {
        super.initAction()
        with(binding) {
            btnEditProfile.setOnClickListener {
                nav.fromProfileToEditProfile()
            }
        }
    }

    override fun initObserver() {
        super.initObserver()
        addObservers(ProfileObserver(this, viewModel))
        viewModel.getProfile(prefs.userId)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getProfile(prefs.userId)
    }
    override fun onGetProfileLoading() {
        with(binding) {
            msvDetailProfile.showLoadingLayout()
        }
    }

    override fun onGetProfileSuccess(data: Profile) {
        with(binding) {
            msvDetailProfile.showDefaultLayout()
            imgProfile.url = data.avatar
            tvProfileName.text = data.name
            tvUsername.text = data.username
            tvJob.text = data.job
            tvPhone.text = data.phoneNumber
            tvEmail.text = data.email
            tvAddress.text = data.address
                .plus(data.villageName.withStartSeparator())
                .plus(data.subDistrictName.withStartSeparator())
                .plus(data.districtName.withStartSeparator())
                .plus(data.provinceName.withStartSeparator())
                .toWordCase()
            when (data.gender) {
                "male" -> tvGender.text = getString(R.string.label_male)
                "female" -> tvGender.text = getString(R.string.label_female)
            }
        }
    }

    override fun onGetProfileFailed(e: Throwable?) {
        errorSnackBar(e?.message.toString())
    }

    private fun String.toWordCase(): String {
        return this.split(" ")
            .map { if (it.contains("DKI") || it.contains("DI")) it else it.lowercase() }
            .joinToString(" ") { it.replaceFirstChar(Char::titlecase) }
    }

    private fun String.withStartSeparator(): String {
        return if (this.isNotEmpty()) ", ".plus(this)
        else this
    }
}
