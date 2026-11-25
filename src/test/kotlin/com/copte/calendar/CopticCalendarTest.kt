package com.copte.calendar

import java.time.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CopticCalendarTest {

    private fun d(y: Int, m: Int, day: Int) = LocalDate.of(y, m, day)

    @Test
    fun `Nayrouz le 11 ou le 12 septembre`() {
        assertEquals(CopticDate(1742, CopticMonth.TOUT, 1), d(2025, 9, 11).toCoptic())
        assertEquals(CopticDate(1743, CopticMonth.TOUT, 1), d(2026, 9, 11).toCoptic())
        // 2024 est bissextile : l'année copte 1739 a 6 épagomènes, Nayrouz 1740 tombe le 12 septembre 2023.
        assertEquals(CopticDate(1739, CopticMonth.NASIE, 6), d(2023, 9, 11).toCoptic())
        assertEquals(CopticDate(1740, CopticMonth.TOUT, 1), d(2023, 9, 12).toCoptic())
    }

    @Test
    fun `annees bissextiles coptes`() {
        assertTrue(CopticCalendar.isLeapYear(1739))
        assertTrue(CopticCalendar.isLeapYear(1743))
        assertFalse(CopticCalendar.isLeapYear(1740))
        assertEquals(6, CopticCalendar.daysInMonth(1739, CopticMonth.NASIE))
        assertEquals(5, CopticCalendar.daysInMonth(1742, CopticMonth.NASIE))
    }

    @Test
    fun `dates de reference`() {
        assertEquals(CopticDate(1743, CopticMonth.HATOUR, 16), d(2026, 11, 25).toCoptic())
        assertEquals(CopticDate(1741, CopticMonth.KIAHK, 29), d(2025, 1, 7).toCoptic())
        assertEquals(CopticDate(1740, CopticMonth.KIAHK, 28), d(2024, 1, 7).toCoptic())
        assertEquals(CopticDate(1742, CopticMonth.ABIB, 5), d(2026, 7, 12).toCoptic())
        assertEquals(CopticDate(1742, CopticMonth.MESRA, 1), d(2026, 8, 7).toCoptic())
        assertEquals(d(2026, 4, 12), CopticDate(1742, CopticMonth.BARAMOUDA, 4).toGregorian())
    }

    @Test
    fun `aller-retour et continuite de 1900 a 2100`() {
        var date = d(1900, 1, 1)
        var previous = date.minusDays(1).toCoptic()
        while (date.year <= 2100) {
            val coptic = date.toCoptic()
            assertEquals(date, coptic.toGregorian(), "aller-retour $date")
            assertTrue(coptic > previous, "ordre $date")
            previous = coptic
            date = date.plusDays(1)
        }
    }

    @Test
    fun `cle stable en chiffres latins meme en arabe`() {
        val saved = java.util.Locale.getDefault()
        try {
            java.util.Locale.setDefault(java.util.Locale.forLanguageTag("ar-EG"))
            assertEquals("04-29", CopticDate(1741, CopticMonth.KIAHK, 29).key)
        } finally {
            java.util.Locale.setDefault(saved)
        }
    }
}
