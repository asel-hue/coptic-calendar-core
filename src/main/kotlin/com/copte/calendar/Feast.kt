package com.copte.calendar

/**
 * Fêtes reconnues par la bibliothèque. [id] est un identifiant **stable** : il sert de clé aux données
 * (lectures, libellés traduits) et au contrat d'intégration entre applications. Ne jamais le modifier.
 */
public enum class Feast(public val id: String, public val rank: Rank) {
    // Fêtes à date copte fixe
    NAYROUZ("nayrouz", Rank.MINOR),
    CROSS("cross", Rank.MINOR),                       // 17 Tout (et 10 Baramhat)
    NATIVITY("nativity", Rank.MAJOR),                 // 29 Kiahk, voir LiturgicalCalendar.nativity
    CIRCUMCISION("circumcision", Rank.MINOR),         // 6 Touba
    EPIPHANY("epiphany", Rank.MAJOR),                 // 11 Touba
    WEDDING_CANA("wedding_cana", Rank.MINOR),         // 13 Touba
    PRESENTATION("presentation", Rank.MINOR),         // 8 Amshir
    ANNUNCIATION("annunciation", Rank.MAJOR),         // 29 Baramhat
    ENTRY_INTO_EGYPT("entry_into_egypt", Rank.MINOR), // 24 Bashans
    APOSTLES("apostles", Rank.FEAST),                 // 5 Abib
    TRANSFIGURATION("transfiguration", Rank.MINOR),   // 13 Mesra
    ASSUMPTION("assumption", Rank.FEAST),             // 16 Mesra

    // Fêtes mobiles (par rapport à Pâques)
    JONAH_PASSOVER("jonah_passover", Rank.FEAST),     // P − 66
    PALM_SUNDAY("palm_sunday", Rank.MAJOR),           // P − 7
    HOLY_THURSDAY("holy_thursday", Rank.MINOR),       // P − 3
    GOOD_FRIDAY("good_friday", Rank.FEAST),           // P − 2
    EASTER("easter", Rank.MAJOR),                     // P
    THOMAS_SUNDAY("thomas_sunday", Rank.MINOR),       // P + 7
    ASCENSION("ascension", Rank.MAJOR),               // P + 39
    PENTECOST("pentecost", Rank.MAJOR);               // P + 49

    /** MAJOR : grande fête du Seigneur ; MINOR : petite fête du Seigneur ; FEAST : autre fête notable. */
    public enum class Rank { MAJOR, MINOR, FEAST }
}

/**
 * Temps liturgique ou jeûne du jour. [id] est stable (clé de données et d'intégration).
 * Les jeûnes du mercredi et du vendredi ne sont pas modélisés ici.
 */
public enum class Season(public val id: String) {
    ORDINARY("ordinary"),
    NATIVITY_FAST("nativity_fast"),   // 16 Hatour → veille de la Nativité
    NINEVEH_FAST("nineveh_fast"),     // P − 69 → P − 67
    GREAT_LENT("great_lent"),         // P − 55 → P − 8
    HOLY_WEEK("holy_week"),           // P − 7 (Rameaux) → P − 1 (Samedi saint)
    HOLY_FIFTY_DAYS("holy_fifty"),    // P → P + 49 (Pentecôte)
    APOSTLES_FAST("apostles_fast"),   // P + 50 → 4 Abib
    VIRGIN_FAST("virgin_fast"),       // 1 → 15 Mesra
}
