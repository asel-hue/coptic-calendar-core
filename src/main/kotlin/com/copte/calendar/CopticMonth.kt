package com.copte.calendar

/**
 * Les treize mois du calendrier copte, dans l'ordre de l'année (1 = Tout).
 * Les douze premiers ont 30 jours ; Nasie (les épagomènes) en a 5, ou 6 les années bissextiles coptes.
 * Les libellés affichés vivent dans les applications (traductions), pas ici.
 */
public enum class CopticMonth(public val number: Int) {
    TOUT(1),
    BABA(2),
    HATOUR(3),
    KIAHK(4),
    TOUBA(5),
    AMSHIR(6),
    BARAMHAT(7),
    BARAMOUDA(8),
    BASHANS(9),
    BAOUNA(10),
    ABIB(11),
    MESRA(12),
    NASIE(13);

    public companion object {
        public fun of(number: Int): CopticMonth =
            entries.firstOrNull { it.number == number }
                ?: throw IllegalArgumentException("Mois copte invalide : $number (attendu 1..13)")
    }
}
