package com.copte.calendar

import java.util.Locale

/**
 * Date du calendrier copte (année de l'ère des Martyrs, AM).
 * Conversion : [CopticCalendar.fromGregorian] et [CopticCalendar.toGregorian].
 */
public data class CopticDate(
    public val year: Int,
    public val month: CopticMonth,
    public val day: Int,
) : Comparable<CopticDate> {
    init {
        require(year >= 1) { "Année copte invalide : $year" }
        val max = CopticCalendar.daysInMonth(year, month)
        require(day in 1..max) { "Jour invalide pour ${month.name} $year : $day (1..$max)" }
    }

    /**
     * Clé stable « MM-JJ » utilisée par les données (Synaxaire, lectures du jour fixe).
     * Toujours en chiffres latins, quelle que soit la langue du téléphone (Locale.ROOT).
     */
    public val key: String get() = String.format(Locale.ROOT, "%02d-%02d", month.number, day)

    override fun compareTo(other: CopticDate): Int =
        compareValuesBy(this, other, { it.year }, { it.month.number }, { it.day })

    override fun toString(): String = "$day ${month.name} $year AM"
}
