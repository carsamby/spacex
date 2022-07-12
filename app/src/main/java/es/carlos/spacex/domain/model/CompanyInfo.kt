package es.carlos.spacex.domain.model

data class CompanyInfo(
    val name: String?,
    val founder: String?,
    val year: Int?,
    val employees: Int?,
    val launch_sites: Int?,
    val valuation: Long?
)