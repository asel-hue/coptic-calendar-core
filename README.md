# coptic-calendar-core

Bibliothèque Kotlin/JVM (sans dépendance Android) pour le **calendrier copte** et le **jour liturgique**.
Elle est partagée par les applications coptes (KatamerosCopte, LivreAGPIA, futures applications d'offices).

## Contenu prévu
- `CopticDate`, `CopticMonth` : date copte (ère des Martyrs) et ses 13 mois. **Disponible.**
- `CopticCalendar` : conversion grégorien ↔ copte, années bissextiles coptes. *Phase 2.*
- Comput pascal (Pâques copte, calcul julien) et fêtes mobiles : Jeûne de Ninive, Carême, Pentecôte, Jeûne des Apôtres. *Phase 2.*
- `LiturgicalDay` : modèle commun du jour (date copte, saison, fête, identifiants des saints et des lectures). *Phase 2.*

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
