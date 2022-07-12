package es.carlos.spacex.domain.use_cases

import es.carlos.spacex.common.Resource
import es.carlos.spacex.domain.exceptionSafeFlow
import es.carlos.spacex.domain.model.Launch
import es.carlos.spacex.domain.model.request.LaunchRequest
import es.carlos.spacex.domain.repository.SpaceRepository
import javax.inject.Inject

class GetLaunchesUseCase @Inject constructor(
    private val spaceRepository: SpaceRepository
) {

    operator fun invoke(request: LaunchRequest) = exceptionSafeFlow<List<Launch>> {
        emit(Resource.Loading())
        val launches = spaceRepository.getLaunches(request)
        emit(Resource.Success(launches))
    }

}