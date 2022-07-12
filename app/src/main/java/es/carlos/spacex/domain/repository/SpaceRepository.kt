package es.carlos.spacex.domain.repository

import es.carlos.spacex.domain.model.CompanyInfo
import es.carlos.spacex.domain.model.Launch
import es.carlos.spacex.domain.model.request.LaunchRequest

interface SpaceRepository {

    suspend fun getCompanyInfo(): CompanyInfo
    suspend fun getLaunches(request: LaunchRequest): List<Launch>

}