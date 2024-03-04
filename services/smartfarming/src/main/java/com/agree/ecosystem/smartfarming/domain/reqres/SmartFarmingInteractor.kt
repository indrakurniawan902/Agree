package com.agree.ecosystem.smartfarming.domain.reqres

import com.agree.ecosystem.smartfarming.data.reqres.SmartFarmingRepository
import com.agree.ecosystem.smartfarming.data.reqres.model.UpdateReadStatusRequestBody
import com.agree.ecosystem.smartfarming.data.reqres.model.devicehsitory.DeviceHistoryQuery
import com.agree.ecosystem.smartfarming.domain.reqres.model.SmartFarming.ParameterTestData
import com.agree.ecosystem.smartfarming.domain.reqres.model.SmartFarming.UpdateReadStatus
import com.agree.ecosystem.smartfarming.domain.reqres.model.devicehistory.DeviceHistory
import com.agree.ecosystem.smartfarming.domain.reqres.model.tools.Device
import io.reactivex.Flowable

class SmartFarmingInteractor (private val repo : SmartFarmingRepository) : SmartFarmingUsecase {
    override fun getParameterTest(
        subVesselId: String,
        schema: String,
        deviceId: String,
        date: String?
    ): Flowable<ParameterTestData> {
        return repo.getParameterTest(subVesselId = subVesselId, schema = schema, deviceId = deviceId, date = date).map {
            it.toParameterTestData()
        }
    }

    override fun getDeviceHistoryData(
        period: String,
        date: String,
        query: DeviceHistoryQuery
    ): Flowable<List<DeviceHistory>> {
        return repo.getDeviceHistoryData(period, date, query).map {
            it.map { data -> data.toDeviceHistory() }
        }
    }

    override fun getListDevice(subVesselId: String): Flowable<List<Device>> {
        return repo.getListDevice(subVesselId).map {
            it.map { data -> data.toDeviceDataItem() }
        }
    }

    override fun updateReadStatus(body: UpdateReadStatusRequestBody): Flowable<UpdateReadStatus> {
        return repo.updateReadStatus(body).map {
            it.toUpdateReadStatus()
        }
    }
}
