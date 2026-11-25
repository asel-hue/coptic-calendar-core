package com.copte.calendar

import java.time.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

/** Le contrat est public : ces valeurs ne doivent jamais changer. */
class LiturgyContractTest {
    private val date = LocalDate.of(2026, 11, 25)

    @Test
    fun `uris et liens stables`() {
        assertEquals("content://com.copte.katameros.liturgy/day/2026-11-25", LiturgyContract.dayUri(date))
        assertEquals("content://com.copte.katameros.liturgy/day/2026-11-25?lang=ar", LiturgyContract.dayUri(date, "ar"))
        assertEquals("content://com.copte.katameros.liturgy/today", LiturgyContract.todayUri())
        assertEquals("copte://jour/2026-11-25", LiturgyContract.dayLink(date))
        assertEquals("copte://lectures/2026-11-25/liturgie", LiturgyContract.readingsLink(date, LiturgyContract.Office.LITURGY))
        assertEquals("com.copte.katameros.permission.READ_LITURGY", LiturgyContract.PERMISSION_READ)
    }

    @Test
    fun `offices et colonnes`() {
        assertEquals(LiturgyContract.Office.VESPERS, LiturgyContract.Office.fromSlug("vepres"))
        assertNull(LiturgyContract.Office.fromSlug("inconnu"))
        assertEquals(13, LiturgyContract.Columns.ALL.size)
        assertEquals("coptic_key", LiturgyContract.Columns.COPTIC_KEY)
    }
}
