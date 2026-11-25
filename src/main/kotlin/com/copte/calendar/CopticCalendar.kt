package com.copte.calendar

import java.time.LocalDate

/**
 * Conversion entre le calendrier grégorien et le calendrier copte (ère des Martyrs, AM),
 * par le numéro de jour julien (JDN).
 *
 * Repères : le 1er Tout 1 AM correspond au 29 août 284 julien (JDN 1 825 030). Une année copte est
 * bissextile (Nasie de 6 jours) quand `année % 4 == 3`, c'est-à-dire l'année qui précède une année
 * grégorienne bissextile : le Nayrouz suivant tombe alors le 12 septembre au lieu du 11 (1900–2099).
 */
public object CopticCalendar {

    private const val EPOCH_JDN: Long = 1_825_030L
    private const val UNIX_EPOCH_JDN: Long = 2_440_588L

    /** `true` si l'année copte compte 6 jours épagomènes (Nasie). */
    public fun isLeapYear(year: Int): Boolean = Math.floorMod(year, 4) == 3

    /** Nombre de jours du mois copte pour l'année donnée. */
    public fun daysInMonth(year: Int, month: CopticMonth): Int = when {
        month != CopticMonth.NASIE -> 30
        isLeapYear(year) -> 6
        else -> 5
    }

    /** Date copte correspondant à une date grégorienne. */
    public fun fromGregorian(date: LocalDate): CopticDate = fromJdn(date.toEpochDay() + UNIX_EPOCH_JDN)

    /** Date grégorienne correspondant à une date copte. */
    public fun toGregorian(date: CopticDate): LocalDate = LocalDate.ofEpochDay(toJdn(date) - UNIX_EPOCH_JDN)

    internal fun toJdn(date: CopticDate): Long =
        toJdn(date.year, date.month.number, date.day)

    private fun toJdn(year: Int, month: Int, day: Int): Long =
        EPOCH_JDN - 1 + 365L * (year - 1) + Math.floorDiv(year, 4) + 30L * (month - 1) + day

    internal fun fromJdn(jdn: Long): CopticDate {
        val year = Math.floorDiv(4 * (jdn - EPOCH_JDN) + 1463, 1461).toInt()
        val dayOfYear = (jdn - toJdn(year, 1, 1)).toInt() // 0..365
        val month = dayOfYear / 30 + 1
        val day = dayOfYear % 30 + 1
        return CopticDate(year, CopticMonth.of(month), day)
    }
}

/** Raccourci : `LocalDate.now().toCoptic()`. */
public fun LocalDate.toCoptic(): CopticDate = CopticCalendar.fromGregorian(this)

/** Raccourci : `copticDate.toGregorian()`. */
public fun CopticDate.toGregorian(): LocalDate = CopticCalendar.toGregorian(this)
