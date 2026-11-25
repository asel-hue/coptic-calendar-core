# coptic-calendar-core

Bibliothèque Kotlin/JVM (sans dépendance Android) pour le **calendrier copte** et le **jour liturgique**.
Elle est partagée par les applications coptes (KatamerosCopte, LivreAGPIA, futures applications d'offices).

## Contenu
- `CopticDate`, `CopticMonth` : date copte (ère des Martyrs) et ses 13 mois. `key` donne la clé stable « MM-JJ », toujours en chiffres latins.
- `CopticCalendar` : conversion grégorien ↔ copte (`LocalDate.toCoptic()`, `CopticDate.toGregorian()`) et années bissextiles coptes.
- `Paschalion` : Pâques copte (comput julien) et `daysFromEaster`.
- `LiturgicalCalendar.dayOf(date)` renvoie un `LiturgicalDay` : date copte, jour de la semaine, jours depuis Pâques, temps liturgique (`Season`), fêtes (`Feast`) et rang du dimanche dans le mois copte.

```kotlin
val today = LiturgicalCalendar.dayOf(LocalDate.now())
today.coptic          // 16 HATOUR 1743 AM
today.season.id       // "nativity_fast"
today.feasts.map { it.id }
```

### Temps et fêtes modélisés
| Temps (`Season`) | Période |
|---|---|
| `nineveh_fast` | P − 69 → P − 67 (Pâque de Jonas à P − 66) |
| `great_lent` | P − 55 → P − 8 |
| `holy_week` | Rameaux (P − 7) → Samedi saint |
| `holy_fifty` | Pâques → Pentecôte (P + 49) |
| `apostles_fast` | P + 50 → 4 Abib |
| `nativity_fast` | 16 Hatour → veille de la Nativité |
| `virgin_fast` | 1 → 15 Mesra |

**Fêtes.** Les grandes et les petites fêtes du Seigneur, le Nayrouz, la Croix, les Apôtres et l'Assomption. Voir `Feast.kt`.

### Points à faire confirmer par un prêtre
- **Nativité.** Elle est fixée au 25 décembre julien (7 janvier), soit le 29 Kiahk, ou le 28 Kiahk l'année qui suit une année copte bissextile (cas de janvier 2024).
- **Fêtes qui en dépendent.** La Circoncision (6 Touba) et l'Épiphanie (11 Touba) restent à date copte fixe. Il faut confirmer qu'elles ne se décalent pas avec la Nativité.
- **Jeûnes non modélisés.** Les jeûnes du mercredi et du vendredi ne sont pas encore pris en compte.

Les libellés traduits (noms des mois, des fêtes) ne sont pas dans la bibliothèque : chaque application les fournit.

## Utilisation (JitPack)
```kotlin
// settings.gradle.kts
dependencyResolutionManagement { repositories { maven("https://jitpack.io") } }
// build.gradle.kts
implementation("com.github.asel-hue:coptic-calendar-core:<tag>")
```
En développement local, une application peut utiliser ce dossier directement (*composite build*) :
voir `settings.gradle` de KatamerosCopte.

## Commandes
- `./gradlew test` : tests unitaires.
- `./gradlew publishToMavenLocal` : publication locale, la même que celle que fait JitPack.

## Règles
- Aucune dépendance Android ni réseau.
- API publique explicite (`explicitApi()`). Les identifiants et les clés (`CopticDate.key` = « MM-JJ ») sont **stables** : ne jamais les renuméroter.
- Toute règle de calendrier est couverte par un test sur des dates de référence.

## Licence
PolyForm Noncommercial 1.0.0, voir `LICENSE`.
