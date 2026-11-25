package com.copte.calendar

/**
 * Date du calendrier copte (année de l'ère des Martyrs, AM).
 * La conversion depuis et vers le calendrier grégorien arrive en phase 2 (`CopticCalendar`).
 */
public data class CopticDate(
    public val year: Int,
    public val month: CopticMonth,
    public val day: Int,
) {
    init {
        require(year >= 1) { "Année copte invalide : $year" }
        val max = if (month == CopticMonth.NASIE) 6 else 30
        require(day in 1..max) { "Jour invalide pour ${month.name} : $day" }
    }

    /** Clé stable « MM-DD » utilisée par les données (Synaxaire, lectures du jour fixe). */
    public val key: String get() = "%02d-%02d".format(month.number, day)
}
