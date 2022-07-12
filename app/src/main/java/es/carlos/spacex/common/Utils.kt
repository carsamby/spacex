package es.carlos.spacex.common

import android.annotation.SuppressLint
import es.carlos.spacex.common.Constants.Dates.API_LOCAL_DATE_FORMAT
import es.carlos.spacex.common.Constants.Dates.PRETTY_DATE_FORMAT
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    fun Date.dateToString(format: String = PRETTY_DATE_FORMAT): String {
        //simple date formatter
        val dateFormatter = SimpleDateFormat(format, Locale.getDefault())

        //return the formatted date string
        return dateFormatter.format(this)
    }

    @SuppressLint("NewApi")
    fun String.reformatDate(
        inFormat: String = API_LOCAL_DATE_FORMAT,
        outFormat: String = PRETTY_DATE_FORMAT
    ): String {
        val parsed = SimpleDateFormat(inFormat, Locale.getDefault()).parse(this)

        parsed?.let { return SimpleDateFormat(outFormat, Locale.getDefault()).format(it) }

        return this
    }

    @SuppressLint("NewApi")
    fun String.toDate(): Date {
        val format = SimpleDateFormat(API_LOCAL_DATE_FORMAT, Locale.getDefault())
        return try {
            format.parse(this) as Date
        } catch (e: ParseException) {
            e.printStackTrace()
            Date()
        }
    }

    fun Int.toDate(): String {



        return ""
    }

    enum class OrderType(val query: String, val desc: String) {DESC("desc", "Descendant"),  ASC("asc", "Ascendant") }
    enum class LaunchState(val desc: String) { SUCCESS("Success"), FAILURE("Failure"), NONE("None") }

}