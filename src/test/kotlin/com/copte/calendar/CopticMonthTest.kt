package com.copte.calendar

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CopticMonthTest {
    @Test
    fun `treize mois numerotes de 1 a 13`() {
        assertEquals((1..13).toList(), CopticMonth.entries.map { it.number })
        assertEquals(CopticMonth.KIAHK, CopticMonth.of(4))
    }

    @Test
    fun `cle stable du jour`() {
        assertEquals("04-29", CopticDate(1743, CopticMonth.KIAHK, 29).key)
    }

    @Test
    fun `jour hors limites refuse`() {
        assertFailsWith<IllegalArgumentException> { CopticDate(1743, CopticMonth.NASIE, 7) }
        assertFailsWith<IllegalArgumentException> { CopticDate(1743, CopticMonth.TOUT, 31) }
    }
}
