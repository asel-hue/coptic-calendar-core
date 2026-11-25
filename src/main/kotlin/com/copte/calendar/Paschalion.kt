package com.copte.calendar

import java.time.LocalDate

/**
 * Comput pascal de l'Église copte : Pâques est calculée dans le calendrier julien (comme dans les
 * Églises orthodoxes), puis convertie en date grégorienne.
 */
public object Paschalion {

    /** Dimanche de Pâques (Résurrection) pour une année grégorienne. */
    public fun easter(gregorianYear: Int): LocalDate {
        // Algorithme julien de Meeus.
        val a = gregorianYear % 4
        val b = gregorianYear % 7
        val c = gregorianYear % 19
        val d = (19 * c + 15) % 30
        val e = (2 * a + 4 * b - d + 34) % 7
        val month = (d + e + 114) / 31
        val day = (d + e + 114) % 31 + 1
        return julianToGregorian(gregorianYear, month, day)
    }

    /** Pâques qui gouverne la date donnée : celle de la même année grégorienne. */
    public fun easterFor(date: LocalDate): LocalDate = easter(date.year)

    /** Nombre de jours entre Pâques de l'année et la date (négatif avant Pâques). */
    public fun daysFromEaster(date: LocalDate): Int =
        (date.toEpochDay() - easterFor(date).toEpochDay()).toInt()

    internal fun julianToGregorian(year: Int, month: Int, day: Int): LocalDate {
        val a = (14 - month) / 12
        val y = year + 4800 - a
        val m = month + 12 * a - 3
        val jdn = day + (153 * m + 2) / 5 + 365L * y + y / 4 - 32083
        return LocalDate.ofEpochDay(jdn - 2_440_588L)
    }
}
