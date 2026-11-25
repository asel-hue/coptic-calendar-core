package com.copte.calendar

import java.time.LocalDate

/**
 * Contrat d'intégration entre les applications coptes (KatamerosCopte, LivreAGPIA, applications d'offices).
 *
 * Deux moyens, en lecture seule :
 * 1. **Liens** (aucune permission) : ouvrir l'application de lectures sur un jour ou un office.
 * 2. **ContentProvider** de KatamerosCopte : lire le jour liturgique (date copte, temps, fêtes, saints).
 *    Protégé par une permission de niveau `signature` : seules les applications signées avec la même clé y ont accès.
 *
 * Règle d'évolution : on ajoute (colonne, chemin, paramètre), on ne modifie ni ne retire rien.
 * [VERSION] augmente à chaque ajout. Détails et exemples : `docs/INTEGRATION.md` de KatamerosCopte.
 */
public object LiturgyContract {
    /** Version du contrat, renvoyée dans la colonne [Columns.CONTRACT_VERSION]. */
    public const val VERSION: Int = 1

    public const val AUTHORITY: String = "com.copte.katameros.liturgy"
    public const val PERMISSION_READ: String = "com.copte.katameros.permission.READ_LITURGY"

    public const val PATH_DAY: String = "day"
    public const val PATH_TODAY: String = "today"

    /** Paramètre de requête optionnel : langue des titres (`fr`, `ar`, `en`). Par défaut `fr`. */
    public const val PARAM_LANG: String = "lang"

    /** `content://com.copte.katameros.liturgy/day/2026-11-25` */
    public fun dayUri(date: LocalDate, lang: String? = null): String =
        "content://$AUTHORITY/$PATH_DAY/$date" + (lang?.let { "?$PARAM_LANG=$it" } ?: "")

    /** `content://com.copte.katameros.liturgy/today` (jour du téléphone, fuseau local). */
    public fun todayUri(lang: String? = null): String =
        "content://$AUTHORITY/$PATH_TODAY" + (lang?.let { "?$PARAM_LANG=$it" } ?: "")

    /** Lien qui ouvre l'application de lectures sur un jour : `copte://jour/2026-11-25`. */
    public fun dayLink(date: LocalDate): String = "copte://jour/$date"

    /** Lien qui ouvre un office précis : `copte://lectures/2026-11-25/liturgie`. */
    public fun readingsLink(date: LocalDate, office: Office): String = "copte://lectures/$date/${office.slug}"

    /** Offices adressables par lien. [slug] est stable. */
    public enum class Office(public val slug: String) {
        VESPERS("vepres"),
        MATINS("matines"),
        LITURGY("liturgie");

        public companion object {
            public fun fromSlug(slug: String?): Office? = entries.firstOrNull { it.slug == slug }
        }
    }

    /** Colonnes de la ligne unique renvoyée pour un jour. Les listes sont des tableaux JSON. */
    public object Columns {
        public const val CONTRACT_VERSION: String = "contract_version"
        /** Date grégorienne ISO « 2026-11-25 ». */
        public const val DATE: String = "date"
        public const val COPTIC_YEAR: String = "coptic_year"
        public const val COPTIC_MONTH: String = "coptic_month"
        public const val COPTIC_DAY: String = "coptic_day"
        /** Clé stable « MM-JJ » du jour copte. */
        public const val COPTIC_KEY: String = "coptic_key"
        /** Identifiant de [Season] (« nativity_fast »…). */
        public const val SEASON_ID: String = "season_id"
        /** Tableau JSON des identifiants de [Feast] (« ["nativity"] »). */
        public const val FEAST_IDS: String = "feast_ids"
        /** Titre du jour dans la langue demandée (fête, sinon temps liturgique), ou vide. */
        public const val TITLE: String = "title"
        /** Langue effectivement utilisée pour [TITLE] et [COMMEMORATIONS]. */
        public const val LANG: String = "lang"
        /** Tableau JSON `[{"id": "31601", "title": "…"}]` des commémorations du Synaxaire. */
        public const val COMMEMORATIONS: String = "commemorations"
        /** Origine des lectures (« annual:03-16 », « feast:palm_sunday »…), utile au débogage. */
        public const val READINGS_SOURCE: String = "readings_source"
        /** Lien vers ce jour dans l'application de lectures. */
        public const val DAY_LINK: String = "day_link"

        public val ALL: List<String> = listOf(
            CONTRACT_VERSION, DATE, COPTIC_YEAR, COPTIC_MONTH, COPTIC_DAY, COPTIC_KEY,
            SEASON_ID, FEAST_IDS, TITLE, LANG, COMMEMORATIONS, READINGS_SOURCE, DAY_LINK,
        )
    }
}
