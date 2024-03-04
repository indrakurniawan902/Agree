package com.agree.ecosystem.finances.data.source

import com.agree.ecosystem.finances.domain.reqres.model.profilefarmer.FarmerInfoPercentage
import com.agree.ecosystem.finances.domain.reqres.model.profilefarmer.ProfileFarmer

class LoanDetailBorrowerFakeData {
    val data = ProfileFarmer(
        "aa243e83-8c45-4508-9a94-74435797fe06",
        "https://minio.agreeculture.id/agree/farmer/66af01be-9d4d-42c7-bdc8-46be600942d1.jpg",
        "yy",
        "null",
        "1",
        listOf(
            mapOf("profileData" to FarmerInfoPercentage(64, false, 0, 0)),
            mapOf("familyData" to FarmerInfoPercentage(66, false, 0, 0)),
            mapOf("addressData" to FarmerInfoPercentage(66, false, 0, 0)),
            mapOf("employmentData" to FarmerInfoPercentage(14, false, 0, 0)),
            mapOf("emergencyData" to FarmerInfoPercentage(0, false, 0, 0)),
            mapOf("businessData" to FarmerInfoPercentage(30, false, 0, 0)),
            mapOf("assetData" to FarmerInfoPercentage(0, false, 0, 0)),
            mapOf("bankData" to FarmerInfoPercentage(50, false, 0, 0)),
            mapOf("photosData" to FarmerInfoPercentage(66, false, 0, 0))
        ),
        "1994-01-01T00:00:00+07:00",
        0,
        "5361816464940400",
        listOf("56d0f572-55d0-4f3d-b97e-67b61017a17a"),
        "Petani Kedua",
        false,
        null,
        "08777",
        0
    )
}
