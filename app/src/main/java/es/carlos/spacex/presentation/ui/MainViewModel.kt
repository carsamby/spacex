package es.carlos.spacex.presentation.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.carlos.spacex.common.Constants.Dates.API_LOCAL_DATE_FORMAT
import es.carlos.spacex.common.Constants.Dates.YEAR_DATE_FORMAT
import es.carlos.spacex.common.Resource
import es.carlos.spacex.common.Utils
import es.carlos.spacex.common.Utils.reformatDate
import es.carlos.spacex.data.remote.SpaceApi.Companion.getLaunchRequest
import es.carlos.spacex.domain.model.CompanyInfo
import es.carlos.spacex.domain.model.Launch
import es.carlos.spacex.domain.use_cases.GetCompanyInfoUseCase
import es.carlos.spacex.domain.use_cases.GetLaunchesUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCompanyInfoUseCase: GetCompanyInfoUseCase,
    private val getLaunchesUseCase: GetLaunchesUseCase
) : ViewModel() {

    private val _companyInfo = mutableStateOf(UiState<CompanyInfo>())
    val companyInfo: State<UiState<CompanyInfo>> = _companyInfo

    private val _launches = mutableStateOf(UiState<List<Launch>>())
    val launches: State<UiState<List<Launch>>> = _launches


    init {
        getCompanyInfo()
        getLaunches()
    }

    fun getLaunches(
        orderType: Utils.OrderType = Utils.OrderType.DESC,
        dateSince: Int? = null,
        isSuccess: Boolean? = null
    ) {
        getLaunchesUseCase(
            getLaunchRequest(
                orderType,
                (dateSince?.toString()?.reformatDate(YEAR_DATE_FORMAT, API_LOCAL_DATE_FORMAT)
                    ?: dateSince) as String?,
                isSuccess
            )
        ).onEach {
            _launches.value = when (it) {
                is Resource.Success -> UiState(it.data, true)
                is Resource.Loading -> UiState(it.data, isLoading = true)
                is Resource.Error -> UiState(it.data, isSuccess = false, error = it.message)
            }
        }.launchIn(viewModelScope)
    }

    fun getCompanyInfo() {
        getCompanyInfoUseCase().onEach {
            _companyInfo.value =
                when (it) {
                    is Resource.Success -> UiState(it.data, true, isLoading = false)
                    is Resource.Loading -> UiState(it.data, isLoading = true)
                    is Resource.Error -> UiState(
                        it.data, false,
                        isLoading = false,
                        error = it.message
                    )
                }
        }.launchIn(viewModelScope)
    }

}