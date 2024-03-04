package com.agree.ecosystem.finances.data.source

import com.agree.ecosystem.finances.domain.reqres.model.loansubmission.ApplyLoanCultivator
import com.agree.ecosystem.finances.domain.reqres.model.loansubmission.PlantingSeasonData

class LoanDetailBorrowerWithLoanPackageIdFakeData {
    val datas = listOf(
        ApplyLoanCultivator(
            "aa243e83-8c45-4508-9a94-74435797fe06",
            "56d0f572-55d0-4f3d-b97e-67b61017a17a",
            "Petani Kedua",
            "5361816464940400",
            "",
            2000.0,
            1000.0,
            true,
            listOf(),
            isEligibleIncompleteDataGroup = listOf(
                "profileData",
                "familyData"
            ),
            plantingSeasonData = listOf(
                PlantingSeasonData(
                    "undefined", "23036d84-89a3-4469-a95c-8edc687902a7", "",
                    "", "", 0.0F, "", ""
                )
            ),
            imageBW = "https://agree-minio-agree-logtan-dev.vsan-apps.playcourt.id/agree/borrower/bw/ab73e62c-9ce8-4ebe-817d-9cc5031fd006-1802032806910008.png"
        ),
        ApplyLoanCultivator(
            "3f1bb4ea-d771-46fb-bf89-50ffe622253e",
            "7034a786-46fc-4b89-a166-c23969014eae",
            "Testakun",
            "5361816464940400",
            "https://minio.agreeculture.id/agree/initialAvatar/9dd62441-6978-4d8a-b897-0c6dc66a20b9.png",
            2000.0,
            1000.0,
            false,
            listOf(
                "borrowerHasIncompleteDataGroup"
            ),
            isEligibleIncompleteDataGroup = listOf(
            ),
//            "borrowerHasActiveLoan",
            listOf(
                PlantingSeasonData(
                    "undefined", "23036d84-89a3-4469-a95c-8edc687902a7",
                    "", "", "", 0.0F, "", ""
                ), PlantingSeasonData(
                    "asssas", "6af8aa34-9d6d-4652-9004-b33d6c11f6e3",
                    "W10313062022", "3c0dca55-8b67-4016-ad57-dbe2506fecbb", "",
                    0.0122F, "", ""
                )
            ),
            imageBW = "https://agree-minio-agree-logtan-dev.vsan-apps.playcourt.id/agree/borrower/bw/ab73e62c-9ce8-4ebe-817d-9cc5031fd006-1802032806910008.png"
        ),

        ApplyLoanCultivator(
            borrowerId = "381c16d2-cfc6-450b-bfad-201157b97088",
            farmerId = "bc1e0e92-6fd0-481d-b6c9-cc1e6efc4695",
            name = "Fauzanismail",
            nik = "4646434946767679",
            image = "https://agree-minio-agree-logtan-dev.vsan-apps.playcourt.id/agree/initialAvatar/4e3996b7-84f1-449b-b1d4-29786c57cfaf.png",
            loanPackageMaxAmount = 2000.0,
            1000.0,
            isEligible = false,
            isEligibleErrorMessage = listOf(
                "borrowerHasActiveLoan"
            ),
            isEligibleIncompleteDataGroup = listOf(
                "profileData",
                "familyData"
            ),
            plantingSeasonData = listOf(
                PlantingSeasonData(
                    landName = "Deket monas",
                    landId = "17df456b-9347-476a-a487-6b754dc4b472",
                    landCode = "G1907032021",
                    plantingSeasonId = "925fabec-d879-4394-a5e3-745f608aa618",
                    plantingSeasonStartDate = "",
                    size = 1000F,
                    budgetPlanId = "2560fe84-8d37-49f9-a546-35c4b36d9730",
                    budgetPlanTotalPrice = "270000"
                ),
                PlantingSeasonData(
                    landName = "Coba ganti",
                    landId = "17df456b-9347-476a-a487-6b754dc4b472",
                    landCode = "G4811042021",
                    plantingSeasonId = "47a03c14-3cf1-42ab-a543-c44c3549b6b6",
                    plantingSeasonStartDate = "",
                    size = 02F,
                    budgetPlanId = "2560fe84-8d37-49f9-a546-35c4b36d9730",
                    budgetPlanTotalPrice = "270000"
                ),
                PlantingSeasonData(
                    landName = "Lahan aktif 26",
                    landId = "76d14439-2e3d-40ee-8508-ad852754762b",
                    landCode = "A5212042021",
                    plantingSeasonId = "ba57019b-5553-4818-87a5-4bb13a3781d2",
                    plantingSeasonStartDate = "",
                    size = 01F,
                    budgetPlanId = "",
                    budgetPlanTotalPrice = ""
                ),
            ),
            imageBW = "https://agree-minio-agree-logtan-dev.vsan-apps.playcourt.id/agree/borrower/bw/ab73e62c-9ce8-4ebe-817d-9cc5031fd006-1802032806910008.png"
        ),

        ApplyLoanCultivator(
            borrowerId = "c68c8394-a476-4abf-8c96-e53a4b9d42bf",
            farmerId = "f2680c68-d3ac-4400-be27-e8f8c59186b8",
            name = "Nicholas Saputra",
            nik = "4646494645484848",
            image = "http://agree-minio-agree-logtan-dev.vsan-apps.playcourt.id/agree/initialAvatar/51f0644d-cb4b-4955-a187-88feb0a4d10d.png",
            loanPackageMaxAmount = 2000.0,
            1000.0,
            isEligible = false,
            isEligibleErrorMessage = listOf(
                "borrowerHasActiveLoan"
            ),
            isEligibleIncompleteDataGroup = listOf(
            ),
            plantingSeasonData = listOf(
                PlantingSeasonData(
                    landName = "Avaba",
                    landId = "64bbca46-13f8-40e7-8773-922b0b896ca5",
                    landCode = "G718022021",
                    plantingSeasonId = "64c498f8-8b2a-4794-ace8-6cfe0d737125",
                    plantingSeasonStartDate = "",
                    size = 20F,
                    budgetPlanId = "2560fe84-8d37-49f9-a546-35c4b36d9730",
                    budgetPlanTotalPrice = "270000"
                ),
                PlantingSeasonData(
                    landName = "Di bawah pohon",
                    landId = "a977fcef-707c-49bc-92c5-9efa14b222f8",
                    landCode = "D1224022021",
                    plantingSeasonId = "ce89831e-ea34-4559-8f27-09d5b9dc93e9",
                    plantingSeasonStartDate = "",
                    size = 00F,
                    budgetPlanId = "",
                    budgetPlanTotalPrice = ""
                ),
                PlantingSeasonData(
                    landName = "Lahan aktif 1",
                    landId = "64bbca46-13f8-40e7-8773-922b0b896ca5",
                    landCode = "G1405032021",
                    plantingSeasonId = "e943dee8-cda0-4236-9be0-78c012dd87fb",
                    plantingSeasonStartDate = "",
                    size = 05F,
                    budgetPlanId = "2560fe84-8d37-49f9-a546-35c4b36d9730",
                    budgetPlanTotalPrice = "270000"
                ),
                PlantingSeasonData(
                    landName = "Lahan aktif 2",
                    landId = "64bbca46-13f8-40e7-8773-922b0b896ca5",
                    landCode = "G1505032021",
                    plantingSeasonId = "4d94b77e-5c01-473d-a95f-aa69063d4901",
                    plantingSeasonStartDate = "",
                    size = 05F,
                    budgetPlanId = "2560fe84-8d37-49f9-a546-35c4b36d9730",
                    budgetPlanTotalPrice = "270000"
                )
            ),
            imageBW = "https://agree-minio-agree-logtan-dev.vsan-apps.playcourt.id/agree/borrower/bw/ab73e62c-9ce8-4ebe-817d-9cc5031fd006-1802032806910008.png"
        ),


        ApplyLoanCultivator(
            borrowerId = "1facbd66-2f06-4f28-8bb2-c6aab94c7008",
            farmerId = "73eb4ee6-2cd2-4546-9edc-c760558b115f",
            name = "Remote TV",
            nik = "4646464979464997",
            image = "http://agree-minio-agree-logtan-dev.vsan-apps.playcourt.id/agree/initialAvatar/86fa5234-1574-453d-b0e9-242a0409947f.png",
            loanPackageMaxAmount = 2000.0,
            1000.0,
            isEligible = true,
            isEligibleIncompleteDataGroup = listOf(
            ),
            plantingSeasonData = listOf(
                PlantingSeasonData(
                    landName = "Sebelah rumah Pak RT",
                    landId = "4b1da957-74bc-4648-978c-752e5294d1f0",
                    landCode = "D1024022021",
                    plantingSeasonId = "b77bba8c-dd24-4f5f-a313-33c571ff8b9b",
                    plantingSeasonStartDate = "",
                    size = 10F,
                    budgetPlanId = "",
                    budgetPlanTotalPrice = ""
                ),
                PlantingSeasonData(
                    landName = "Sebelah rumah Pak RT2",
                    landId = "4317d762-d3f3-4da2-95b8-553cb20d3723",
                    landCode = "G1124022021",
                    plantingSeasonId = "c714cd67-fc2e-4e4e-aa30-0ccf73f377de",
                    plantingSeasonStartDate = "",
                    size = 10F,
                    budgetPlanId = "2560fe84-8d37-49f9-a546-35c4b36d9730",
                    budgetPlanTotalPrice = "270000"
                ),
                PlantingSeasonData(
                    landName = "Lahan aktif 3",
                    landId = "4317d762-d3f3-4aaa2-95b8-553cb20d3723",
                    landCode = "G1605032021",
                    plantingSeasonId = "2ceff077-5c64-4218-a7a7-6c85a620d62b",
                    plantingSeasonStartDate = "",
                    size = 10F,
                    budgetPlanId = "2560fe84-8d37-49f9-a546-35c4b36d9730",
                    budgetPlanTotalPrice = "270000"
                ),
            ),
            imageBW = "https://agree-minio-agree-logtan-dev.vsan-apps.playcourt.id/agree/borrower/bw/ab73e62c-9ce8-4ebe-817d-9cc5031fd006-1802032806910008.png"
        ),

        ApplyLoanCultivator(
            borrowerId = "666b8d5f-9ccc-47d7-91b3-d924a2c99035",
            farmerId = "f012b676-8c85-4db9-8871-384ae9391864",
            name = "Safirah",
            nik = "0784219446464346",
            image = "",
            loanPackageMaxAmount = 2000.0,
            1000.0,
            isEligible = false,
            isEligibleErrorMessage = listOf(
                "borrowerHasIncompleteDataGroup"
            ),
            isEligibleIncompleteDataGroup = listOf(
                "bankData"
            ),
            plantingSeasonData = listOf(
                PlantingSeasonData(
                    landName = "Sjsjaj",
                    landId = "8f5804f9-26aa-47dd-a45e-c68487504993",
                    landCode = "G417022021",
                    plantingSeasonId = "536012f5-6614-404a-b376-6c31a1ee83a8",
                    plantingSeasonStartDate = "",
                    size = 05F,
                    budgetPlanId = "2560fe84-8d37-49f9-a546-35c4b36d9730",
                    budgetPlanTotalPrice = "270000"
                ),
                PlantingSeasonData(
                    landName = "Lahan aktif 18",
                    landId = "8f5804f9-26aa-47dd-a45e-c68487504993",
                    landCode = "G3907042021",
                    plantingSeasonId = "7dbe478e-0543-4524-ba95-1ed434199468",
                    plantingSeasonStartDate = "",
                    size = 02F,
                    budgetPlanId = "2560fe84-8d37-49f9-a546-35c4b36d9730",
                    budgetPlanTotalPrice = "270000"
                ),
                PlantingSeasonData(
                    landName = "Lahan aktif 192",
                    landId = "8f5804f9-26aa-47dd-a45e-c68487504993",
                    landCode = "A4111042021",
                    plantingSeasonId = "ee76bc03-5edd-4985-865a-304495d3edb1",
                    plantingSeasonStartDate = "",
                    size = 01F,
                    budgetPlanId = "",
                    budgetPlanTotalPrice = ""
                ),
            ),
            imageBW = "https://agree-minio-agree-logtan-dev.vsan-apps.playcourt.id/agree/borrower/bw/ab73e62c-9ce8-4ebe-817d-9cc5031fd006-1802032806910008.png"
        ),

        ApplyLoanCultivator(
            borrowerId = "5abaf801-6b38-405f-bef9-d68af9ff99a4",
            farmerId = "3ceaec4b-48c1-461c-9cd6-be3532108039",
            name = "testets",
            nik = "2222222222222211",
            image = "http://agree-minio-agree-logtan-dev.vsan-apps.playcourt.id/agree/initialAvatar/bb58d6fd-6341-48a6-9f11-38f349f95274.png",
            loanPackageMaxAmount = 2000.0,
            1000.0,
            isEligible = false,
            isEligibleErrorMessage = listOf(
                "borrowerDoesNotHaveActiveLands"
            ),
            isEligibleIncompleteDataGroup = listOf(),
            plantingSeasonData = listOf(),
            imageBW = "https://agree-minio-agree-logtan-dev.vsan-apps.playcourt.id/agree/borrower/bw/ab73e62c-9ce8-4ebe-817d-9cc5031fd006-1802032806910008.png"
        ),
    )
}
