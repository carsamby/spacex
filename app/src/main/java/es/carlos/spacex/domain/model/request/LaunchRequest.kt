package es.carlos.spacex.domain.model.request

import com.google.gson.annotations.SerializedName

data class LaunchRequest(

	val query: Query? = null,
	val options: Options? = null
)

data class Query(

	@SerializedName("date_utc")
	var dateUtc: DateUtc? = null,
	var success: Boolean? = null
)

data class PopulateItem(

	val path: String? = null,
	val select: Select? = null

)

data class Sort(

	@SerializedName("date_unix")
	val dateUnix: String? = null
)

data class DateUtc(

	@SerializedName("\$gte")
	val gte: String? = null,

	@SerializedName("\$lte")
	val lte: String? = null
)

data class Select(

	val name: Int? = null,
	val type: Int? = null

)

data class Options(

	val populate: List<PopulateItem?>? = null,
	val pagination: Boolean? = null,
	val sort: Sort? = null

)
