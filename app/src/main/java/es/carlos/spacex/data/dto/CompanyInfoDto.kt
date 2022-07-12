package es.carlos.spacex.data.dto

import com.google.gson.annotations.SerializedName
import es.carlos.spacex.domain.model.CompanyInfo

data class CompanyInfoDto(

    var headquarters: HeadquartersDto? = HeadquartersDto(),
    var links: LinkDto? = LinkDto(),
    var name: String? = null,
    var founder: String? = null,
    var founded: Int? = null,
    var employees: Int? = null,
    var vehicles: Int? = null,
    @SerializedName("launch_sites") var launchSites: Int? = null,
    var testSites: Int? = null,
    var ceo: String? = null,
    var cto: String? = null,
    var coo: String? = null,
    var ctoPropulsion: String? = null,
    var valuation: Long? = null,
    var summary: String? = null,
    var id: String? = null

) {
    fun toCompanyInfo(): CompanyInfo = CompanyInfo(
        name, founder, founded, employees, launchSites, valuation
    )
}

data class LinkDto(
    var flickr: String? = null,
    var website: String? = null,
    var twitter: String? = null,
    var elonTwitter: String? = null
)

data class HeadquartersDto(
    var address: String? = null,
    var city: String? = null,
    var state: String? = null
)
