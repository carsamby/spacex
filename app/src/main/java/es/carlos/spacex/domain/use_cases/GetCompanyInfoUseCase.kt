package es.carlos.spacex.domain.use_cases

import es.carlos.spacex.common.Resource
import es.carlos.spacex.domain.exceptionSafeFlow
import es.carlos.spacex.domain.model.CompanyInfo
import es.carlos.spacex.domain.repository.SpaceRepository
import java.util.concurrent.Flow
import javax.inject.Inject

class GetCompanyInfoUseCase @Inject constructor(
    private val spaceRepository: SpaceRepository
) {

    operator fun invoke() = exceptionSafeFlow<CompanyInfo> {
        emit(Resource.Loading())
        val companyInfo = spaceRepository.getCompanyInfo()
        emit(Resource.Success(companyInfo))
    }

}