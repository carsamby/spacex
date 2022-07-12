package es.carlos.spacex.data.remote

import es.carlos.spacex.common.Constants.Dates.API_LOCAL_DATE_FORMAT
import es.carlos.spacex.common.Utils
import es.carlos.spacex.common.Utils.dateToString
import es.carlos.spacex.data.dto.CompanyInfoDto
import es.carlos.spacex.data.dto.Docs
import es.carlos.spacex.domain.model.request.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import java.util.*

interface SpaceApi {

    @GET("company")
    suspend fun getCompanyInfo(): CompanyInfoDto

    @POST("launches/query")
    @Headers("Content-Type: application/json")
    suspend fun getLaunches(@Body request: LaunchRequest): Docs

    companion object {

        fun getLaunchRequest(
            sort: Utils.OrderType,
            sinceDate: String?,
            isSuccess: Boolean?
        ): LaunchRequest {

            val query = Query()

            sinceDate?.let {
                query.dateUtc = DateUtc(
                    sinceDate,
                    null
                )
            }

            isSuccess?.let {
                query.success = isSuccess
            }

            return LaunchRequest(
                query,
                Options(
                    listOf(PopulateItem("rocket", Select(1, 1))),
                    false,
                    Sort(sort.query)
                )
            )
        }

    }

}