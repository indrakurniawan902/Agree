package com.agree.ecosystem.core.utils.utility

import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoField
import java.util.Date
import java.util.Locale

private const val SECOND_MILLIS = 1000
private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
private const val TODAY = 24 * HOUR_MILLIS

/**
 * Convert Date to String with Templated Date Format
 */
fun Date.toString(format: ConverterDate): String {
    return try {
        SimpleDateFormat(
            format.formatter, Locale("id", "ID")
        ).format(this)
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}

/**
 * Convert Date to String with Custom Date Format
 */
fun Date.toString(format: String): String {
    return try {
        SimpleDateFormat(
            format, Locale("id", "ID")
        ).format(this)
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}

/**
 * Convert String Date UTC Format to Template Date Format
 */
fun String.convertUTCTimeTo(desireFormat: ConverterDate): String {
    val dateFormat = SimpleDateFormat(ConverterDate.UTC.formatter, Locale("id", "ID"))
    return try {
        val date = dateFormat.parse(this) ?: Date()
        val hour = 3600 * 1000
        val newDate = Date(date.time + 7 * hour)
        dateFormat.applyPattern(desireFormat.formatter)
        dateFormat.format(newDate)
    } catch (e: ParseException) {
        e.printStackTrace()
        ""
    }
}

fun String.convertUTC3TimeTo(desireFormat: ConverterDate): String {
    val dateFormat = SimpleDateFormat(ConverterDate.UTC.formatter, Locale("id", "ID"))
    return try {
        val date = dateFormat.parse(this) ?: Date()
        val hour = 3600 * 1000
        val newDate = Date(date.time + 7 * hour)
        dateFormat.applyPattern(desireFormat.formatter)
        dateFormat.format(newDate).toString()
    } catch (e: ParseException) {
        e.printStackTrace()
        ""
    }
}

fun String.convertUTC2TimeTo(desireFormat: ConverterDate): String {
    val dateFormat = SimpleDateFormat(ConverterDate.UTC2.formatter, Locale("id", "ID"))
    return try {
        val date = dateFormat.parse(this) ?: Date()
        val newDate = Date(date.time)
        dateFormat.applyPattern(desireFormat.formatter)
        dateFormat.format(newDate).toString()
    } catch (e: ParseException) {
        e.printStackTrace()
        ""
    }
}

fun String.convertUTCTimezone(desireFormat: ConverterDate, visibleTimeZone: Boolean = true): String {
    val zonedDateTime = ZonedDateTime.parse(this).withZoneSameInstant(ZoneId.systemDefault())
    val timeZoneId = zonedDateTime.format(DateTimeFormatter.ofPattern("zzz", Locale("id", "ID")))
    val timeZone = if (timeZoneId.contains("GMT") || !visibleTimeZone) String() else " $timeZoneId"
    return DateTimeFormatter.ofPattern(desireFormat.formatter).format(zonedDateTime).plus(timeZone)
}

/**
 * Convert String Datetime into Time 12H Format
 */
fun String.convertTimeToNewTimeFormat(fromFormat: ConverterDate, desireFormat: ConverterDate): String {
    val dateFormat = SimpleDateFormat(fromFormat.formatter, Locale("id", "ID"))
    return try {
        val date = dateFormat.parse(this) ?: Date()
        val newDate = Date(date.time)
        dateFormat.applyPattern(desireFormat.formatter)
        dateFormat.format(newDate)
    } catch (e: ParseException) {
        e.printStackTrace()
        ""
    }
}

/**
 * Convert Local Date UTC Format to Template Date Format
 */
fun LocalDate.toString(format: ConverterDate): String {
    return format(
        DateTimeFormatter.ofPattern(format.formatter, Locale("id"))
    )
}

/**
 * Convert Local Date UTC Format to Custom Date Format
 */
fun LocalDate.toString(format: String): String {
    return format(
        DateTimeFormatter.ofPattern(format)
    )
}

fun Long.toLocalDateTime(): LocalDateTime {
    return LocalDateTime.ofInstant(toInstant(), ZoneId.systemDefault())
}

fun Long.toInstant(): Instant {
    return Instant.ofEpochMilli(this)
}

fun now(): LocalDateTime {
    return LocalDateTime.now(ZoneId.systemDefault())
}

fun nowUnix(): Long {
    return now().toUnix()
}

fun LocalDateTime.toUnix(): Long {
    return atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
}

fun Long.toDateString(format: ConverterDate): String {
    return toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString(format)
}

fun String.UTCtoDate(): Date {
    return Date.from(ZonedDateTime.parse(this).withZoneSameInstant(ZoneId.systemDefault()).toInstant())
}

fun Date.minutesOfDay() = toInstant().atZone(ZoneId.systemDefault()).get(ChronoField.MINUTE_OF_DAY)

fun Date.isBeforeNow(): Boolean {
    return Date().minutesOfDay() > minutesOfDay()
}
