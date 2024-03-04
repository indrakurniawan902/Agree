package com.agree.ecosystem.finances.domain.reqres.model

import com.agree.ecosystem.finances.R
import com.blankj.utilcode.util.StringUtils.getString

enum class ProfileFormData(val value: String, val label: String) {
    PERSONAL_INFO("Info Pribadi", "profileData"),
    JOB_INFO("Info Pekerjaan", "employmentData"),
    FAMILY_INFO("Info Keluarga", "familyData"),
    ADDRESS_INFO("Info Alamat", "addressData"),
    SPOUSE_INFO("Info Pasangan", "spouseData"),
    BANK_INFO("Info Bank", "bankData"),
    BUSINESS_INFO("Info Usaha", "businessData"),
    ASSET_INFO("Info Asset", "assetData"),
    EMERGENCY_INFO("Kontak Darurat", "emergencyData"),
    PHOTOS_DATA("Dokumen", "photosData")
}

enum class LoanPackagePaymentType(val value: String, val label: String) {
    TUNAI(getString(R.string.label_cash), getString(R.string.label_value_cash)),
    NONTUNAI(getString(R.string.label_non_cash), getString(R.string.label_value_non_cash))
}

enum class BankInfoForm(val value: String) {
    BANK_DATA("bankData"),
    BANK_NAME("bankName"),
    ACCOUNT_NAME("bankOwnerName"),
    ACCOUNT_NUMBER("accNumber"),
    BANK_BRANCH("bankBranch")
}

enum class FilterKeyData(val value: String) {
    SECTOR_DATA(getString(R.string.label_sector)),
    TYPE_LOAN_DATA(getString(R.string.label_filter_loan_type))
}
