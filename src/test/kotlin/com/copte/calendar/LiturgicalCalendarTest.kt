package com.copte.calendar

import java.time.DayOfWeek
import java.time.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class LiturgicalCalendarTest {

    private fun d(y: Int, m: Int, day: Int) = LocalDate.of(y, m, day)
    private fun day(y: Int, m: Int, dd: Int) = LiturgicalCalendar.dayOf(d(y, m, dd))

    /** Pâques copte (= Pâques orthodoxe), dates grégoriennes. */
    private val easters = mapOf(
        2020 to d(2020, 4, 19), 2021 to d(2021, 5, 2), 2022 to d(2022, 4, 24), 2023 to d(2023, 4, 16),
        2024 to d(2024, 5, 5), 2025 to d(2025, 4, 20), 2026 to d(2026, 4, 12), 2027 to d(2027, 5, 2),
        2028 to d(2028, 4, 16), 2029 to d(2029, 4, 8), 2030 to d(2030, 4, 28), 2031 to d(2031, 4, 13),
        2032 to d(2032, 5, 2), 2033 to d(2033, 4, 24), 2034 to d(2034, 4, 9), 2035 to d(2035, 4, 29),
    )

    @Test
    fun `Paques copte de 2020 a 2035`() {
        easters.forEach { (year, expected) ->
            assertEquals(expected, Paschalion.easter(year), "Pâques $year")
            assertEquals(DayOfWeek.SUNDAY, expected.dayOfWeek)
        }
    }

    @Test
    fun `fetes et temps mobiles 2026`() {
        assertEquals(Season.NINEVEH_FAST, day(2026, 2, 2).season)          // lundi P − 69
        assertEquals(listOf(Feast.JONAH_PASSOVER), day(2026, 2, 5).feasts)  // jeudi P − 66
        assertEquals(Season.ORDINARY, day(2026, 2, 15).season)
        assertEquals(Season.GREAT_LENT, day(2026, 2, 16).season)            // P − 55
        assertEquals(Season.HOLY_WEEK, day(2026, 4, 5).season)
        assertEquals(listOf(Feast.PALM_SUNDAY), day(2026, 4, 5).feasts)
        assertEquals(listOf(Feast.GOOD_FRIDAY), day(2026, 4, 10).feasts)
        assertEquals(listOf(Feast.EASTER), day(2026, 4, 12).feasts)
        assertEquals(Season.HOLY_FIFTY_DAYS, day(2026, 4, 12).season)
        assertEquals(listOf(Feast.ASCENSION), day(2026, 5, 21).feasts)
        assertEquals(listOf(Feast.PENTECOST), day(2026, 5, 31).feasts)
        assertEquals(Season.APOSTLES_FAST, day(2026, 6, 1).season)
        assertEquals(Season.APOSTLES_FAST, day(2026, 7, 11).season)        // 4 Abib
        assertEquals(Season.ORDINARY, day(2026, 7, 12).season)
        assertEquals(listOf(Feast.APOSTLES), day(2026, 7, 12).feasts)      // 5 Abib
    }

    @Test
    fun `jeunes et fetes a date fixe`() {
        assertEquals(Season.VIRGIN_FAST, day(2026, 8, 7).season)
        assertEquals(Season.VIRGIN_FAST, day(2026, 8, 21).season)
        assertEquals(listOf(Feast.ASSUMPTION), day(2026, 8, 22).feasts)
        assertEquals(listOf(Feast.NAYROUZ), day(2026, 9, 11).feasts)
        assertEquals(Season.ORDINARY, day(2026, 10, 1).season)
        assertEquals(Season.NATIVITY_FAST, day(2026, 11, 25).season)       // 16 Hatour
        assertEquals(Season.NATIVITY_FAST, day(2026, 12, 15).season)
        assertEquals(Season.NATIVITY_FAST, day(2027, 1, 6).season)
        assertEquals(listOf(Feast.NATIVITY), day(2027, 1, 7).feasts)
        assertEquals(listOf(Feast.EPIPHANY), day(2027, 1, 19).feasts)
    }

    @Test
    fun `Nativite le 7 janvier meme quand c'est le 28 Kiahk`() {
        val y2024 = day(2024, 1, 7)
        assertEquals(CopticDate(1740, CopticMonth.KIAHK, 28), y2024.coptic)
        assertEquals(listOf(Feast.NATIVITY), y2024.feasts)
        assertTrue(day(2024, 1, 8).feasts.isEmpty())
        assertEquals(Season.ORDINARY, day(2024, 1, 7).season)
    }

    @Test
    fun `dimanche du mois copte`() {
        val palm = day(2026, 4, 5) // dimanche 27 Baramhat
        assertEquals(4, palm.sundayOfCopticMonth)
        assertNull(day(2026, 4, 6).sundayOfCopticMonth)
    }

    @Test
    fun `chaque jour de 1950 a 2100 se calcule`() {
        var date = d(1950, 1, 1)
        while (date.year <= 2100) {
            LiturgicalCalendar.dayOf(date)
            date = date.plusDays(1)
        }
    }
}
