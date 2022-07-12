package es.carlos.spacex.domain.model

import es.carlos.spacex.data.dto.Rocket
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.abs

data class Launch(
    val name: String?,
    val date: String?,
    val time: Long?,
    val rocket: Rocket?,
    val success: Boolean?,
    val imgUrl: String?,
    val webcast: String?,
    val wikipedia: String?,
    val article: String?
) {
    val isFuture: Boolean
        get() = Date().time < time ?: 0
    val daysLeft: Long
        get() = abs(TimeUnit.MILLISECONDS.toDays(Date().time - (time ?: 0)))
}
