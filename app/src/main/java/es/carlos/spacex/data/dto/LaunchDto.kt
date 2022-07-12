package es.carlos.spacex.data.dto

import com.google.gson.annotations.SerializedName
import es.carlos.spacex.common.Utils.reformatDate
import es.carlos.spacex.common.Utils.toDate
import es.carlos.spacex.domain.model.Launch

data class Docs(
    val docs: List<LaunchDto>
)

data class LaunchDto(

    var fairings: Fairings? = Fairings(),
    var links: Links? = Links(),
    @SerializedName("static_fire_date_utc")
    var staticFireDateUtc: String? = null,
    @SerializedName("static_fire_date_unix")
    var staticFireDateUnix: Int? = null,
    var net: Boolean? = null,
    var window: Int? = null,
    var rocket: Rocket? = null,
    var success: Boolean? = null,
    var failures: ArrayList<Failures> = arrayListOf(),
    var details: String? = null,
    // Giving some parsing errors...
    //var crew: ArrayList<String> = arrayListOf(),
    var ships: ArrayList<String> = arrayListOf(),
    var capsules: ArrayList<String> = arrayListOf(),
    var payloads: ArrayList<String> = arrayListOf(),
    var launchpad: String? = null,
    @SerializedName("flight_number")
    var flightNumber: Int? = null,
    var name: String? = null,
    @SerializedName("date_utc")
    var dateUtc: String? = null,
    @SerializedName("date_unix")
    var dateUnix: Long? = null,
    @SerializedName("date_local")
    var dateLocal: String? = null,
    @SerializedName("date_precision")
    var datePrecision: String? = null,
    var upcoming: Boolean? = null,
    var cores: ArrayList<Cores> = arrayListOf(),
    @SerializedName("auto_update")
    var autoUpdate: Boolean? = null,
    var tbd: Boolean? = null,
    @SerializedName("launch_library_id")
    var launchLibraryId: String? = null,
    var id: String? = null

) {
    fun toLaunch(): Launch = Launch(
        name,
        dateUtc?.reformatDate(),
        dateUtc?.toDate()?.time,
        rocket,
        success,
        links?.patch?.small,
        links?.webcast,
        links?.wikipedia,
        links?.article
    )
}

data class Rocket(
    val id: String? = null,
    val name: String? = null,
    val type: String? = null
)

data class Fairings(

    var reused: Boolean? = null,
    @SerializedName("recovery_attempt")
    var recoveryAttempt: Boolean? = null,
    var recovered: Boolean? = null,
    var ships: ArrayList<String> = arrayListOf()

)


data class Patch(

    var small: String? = null,
    var large: String? = null

)

data class Reddit(

    var campaign: String? = null,
    var launch: String? = null,
    var media: String? = null,
    var recovery: String? = null

)

data class Flickr(

    var small: ArrayList<String> = arrayListOf(),
    var original: ArrayList<String> = arrayListOf()

)

data class Links(

    var patch: Patch? = Patch(),
    var reddit: Reddit? = Reddit(),
    var flickr: Flickr? = Flickr(),
    var presskit: String? = null,
    var webcast: String? = null,
    @SerializedName("youtube_id") var youtubeId: String? = null,
    var article: String? = null,
    var wikipedia: String? = null

)

data class Failures(

    var time: Int? = null,
    var altitude: String? = null,
    var reason: String? = null

)

data class Cores(
    var core: String? = null,
    var flight: Int? = null,
    var gridfins: Boolean? = null,
    var legs: Boolean? = null,
    var reused: Boolean? = null,
    @SerializedName("landing_attempt")
    var landingAttempt: Boolean? = null,
    @SerializedName("landing_success")
    var landingSuccess: String? = null,
    @SerializedName("landing_type")
    var landingType: String? = null,
    var landpad: String? = null
)
