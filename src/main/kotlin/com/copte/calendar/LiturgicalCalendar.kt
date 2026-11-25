package com.copte.calendar

import java.time.DayOfWeek
import java.time.LocalDate

/**
 * Jour liturgique : tout ce qu'une application doit savoir pour choisir les lectures et l'affichage
 * d'une date. Les libellés traduits ne sont pas ici : utiliser [Feast.id] et [Season.id] comme clés.
 */
public data class LiturgicalDay(
    public val date: LocalDate,
    public val coptic: CopticDate,
    public val dayOfWeek: DayOfWeek,
    /** Jours depuis Pâques de la même année grégorienne (négatif avant Pâques). */
    public val daysFromEaster: Int,
    public val season: Season,
    /** Fêtes du jour, de la plus importante à la moins importante. Vide si aucune. */
    public val feasts: List<Feast>,
) {
    /** Rang du dimanche dans le mois copte (1 à 5), ou `null` si ce n'est pas un dimanche. */
    public val sundayOfCopticMonth: Int?
        get() = if (dayOfWeek == DayOfWeek.SUNDAY) (coptic.day - 1) / 7 + 1 else null

    /** Clé stable « MM-JJ » du jour copte (lectures et Synaxaire à date fixe). */
    public val copticKey: String get() = coptic.key
}

/** Calcul du [LiturgicalDay] pour une date grégorienne. */
public object LiturgicalCalendar {

    public fun dayOf(date: LocalDate): LiturgicalDay {
        val coptic = CopticCalendar.fromGregorian(date)
        val p = Paschalion.daysFromEaster(date)
        val feasts = (movableFeasts(p) + fixedFeasts(date, coptic)).sortedBy { it.rank.ordinal }
        return LiturgicalDay(
            date = date,
            coptic = coptic,
            dayOfWeek = date.dayOfWeek,
            daysFromEaster = p,
            season = seasonOf(date, coptic, p),
            feasts = feasts,
        )
    }

    /**
     * Nativité de l'année copte donnée : 25 décembre julien, soit le 7 janvier grégorien (1900–2099).
     * C'est le 29 Kiahk, ou le 28 Kiahk l'année qui suit une année copte bissextile.
     */
    public fun nativity(copticYear: Int): LocalDate =
        Paschalion.julianToGregorian(copticYear + 283, 12, 25)

    private fun movableFeasts(p: Int): List<Feast> = when (p) {
        -66 -> listOf(Feast.JONAH_PASSOVER)
        -7 -> listOf(Feast.PALM_SUNDAY)
        -3 -> listOf(Feast.HOLY_THURSDAY)
        -2 -> listOf(Feast.GOOD_FRIDAY)
        0 -> listOf(Feast.EASTER)
        7 -> listOf(Feast.THOMAS_SUNDAY)
        39 -> listOf(Feast.ASCENSION)
        49 -> listOf(Feast.PENTECOST)
        else -> emptyList()
    }

    private fun fixedFeasts(date: LocalDate, c: CopticDate): List<Feast> {
        val result = mutableListOf<Feast>()
        if (date == nativity(c.year)) result += Feast.NATIVITY
        when (c.month to c.day) {
            CopticMonth.TOUT to 1 -> result += Feast.NAYROUZ
            CopticMonth.TOUT to 17, CopticMonth.BARAMHAT to 10 -> result += Feast.CROSS
            CopticMonth.TOUBA to 6 -> result += Feast.CIRCUMCISION
            CopticMonth.TOUBA to 11 -> result += Feast.EPIPHANY
            CopticMonth.TOUBA to 13 -> result += Feast.WEDDING_CANA
            CopticMonth.AMSHIR to 8 -> result += Feast.PRESENTATION
            CopticMonth.BARAMHAT to 29 -> result += Feast.ANNUNCIATION
            CopticMonth.BASHANS to 24 -> result += Feast.ENTRY_INTO_EGYPT
            CopticMonth.ABIB to 5 -> result += Feast.APOSTLES
            CopticMonth.MESRA to 13 -> result += Feast.TRANSFIGURATION
            CopticMonth.MESRA to 16 -> result += Feast.ASSUMPTION
            else -> Unit
        }
        return result
    }

    private fun seasonOf(date: LocalDate, c: CopticDate, p: Int): Season {
        when (p) {
            in -69..-67 -> return Season.NINEVEH_FAST
            in -55..-8 -> return Season.GREAT_LENT
            in -7..-1 -> return Season.HOLY_WEEK
            in 0..49 -> return Season.HOLY_FIFTY_DAYS
        }
        // 4 Abib de l'année copte qui contient la Pâques de cette année grégorienne (début juillet).
        val apostlesEnd = CopticCalendar.toGregorian(CopticDate(date.year - 284, CopticMonth.ABIB, 4))
        if (p >= 50 && !date.isAfter(apostlesEnd)) return Season.APOSTLES_FAST

        val nativityFastStart = CopticCalendar.toGregorian(CopticDate(c.year, CopticMonth.HATOUR, 16))
        if (!date.isBefore(nativityFastStart) && date.isBefore(nativity(c.year))) return Season.NATIVITY_FAST

        if (c.month == CopticMonth.MESRA && c.day <= 15) return Season.VIRGIN_FAST
        return Season.ORDINARY
    }
}
