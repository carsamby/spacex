package es.carlos.spacex.data.repository

import es.carlos.spacex.data.remote.SpaceApi
import es.carlos.spacex.domain.model.CompanyInfo
import es.carlos.spacex.domain.model.Launch
import es.carlos.spacex.domain.model.request.LaunchRequest
import es.carlos.spacex.domain.repository.SpaceRepository
import javax.inject.Inject

class SpaceRepositoryImpl @Inject constructor(private val apiClient: SpaceApi) : SpaceRepository {

    override suspend fun getCompanyInfo(): CompanyInfo =
        apiClient.getCompanyInfo().toCompanyInfo()

    override suspend fun getLaunches(request: LaunchRequest): List<Launch> =
        apiClient.getLaunches(request).docs.map { launch -> launch.toLaunch() }

}