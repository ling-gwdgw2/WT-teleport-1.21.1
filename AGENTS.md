# Système de Gouvernance et Suivi - Grand Teleport (NeoForge Port)

Ce document régit les règles de développement, l'architecture du projet et le suivi de la roadmap pour le portage de Grand Teleport vers NeoForge 1.21.1.

---

## Environnement technique & Stack
- **Jeu** : Minecraft 1.21.1
- **Framework** : NeoForge (version 21.1.234)
- **Langage** : Java 21
- **Outil de Build** : Gradle (version 9.2.1)
- **Mod ID** : `gtalike_teleport`
- **Licence** : MIT (code source) + restriction ZapSplat sur les fichiers audio (voir LICENSE)

---

## Architecture du projet
```text
c:/Users/user/Desktop/dev/mod Grand Teleport Neoforge portage/
├── build.gradle                               # Configuration Gradle
├── settings.gradle                             # Paramètres Gradle
├── gradle.properties                           # Propriétés du projet et versions
├── README.md                                   # README principal avec crédits
├── AGENTS.md                                   # Fichier de gouvernance (ce fichier)
└── src/
    └── main/
        ├── java/
        │   └── dev/
        │       └── codex/
        │           └── gtaliketeleport/
        │               ├── GtaLikeTeleport.java        # Classe principale du mod
        │               ├── GtaLikeTeleportConfig.java  # Système de configuration portabilité
        │               ├── client/                     # Logique client & rendu caméra (Étape 3)
        │               ├── network/                    # Gestion des paquets réseau (Étape 2)
        │               └── mixin/                      # Mixins de caméra (Étape 4)
        └── templates/
            └── META-INF/
                └── neoforge.mods.toml          # Template de configuration NeoForge
```

---

## Règles de développement universelles
- **Chirurgical** : Ne jamais réécrire un fichier entier si une modification ciblée et chirurgicale est possible.
- **Dépendances** : Ne jamais inventer, ajouter ou installer de dépendance sans validation explicite.
- **Qualité** : Typage strict, modularité, lisibilité, pas de fonctions ou variables mortes. Essayer le plus possible de diviser (splitter) les fichiers volumineux en classes/fichiers distincts lorsque c'est utile afin de rendre le code plus gérable. Le code doit être conçu pour être facilement manipulable par des agents IA, ce qui facilitera grandement les modifications futures et les portages vers d'autres versions de Minecraft.
- **Validation** : Toujours poser des questions et obtenir la validation explicite de l'utilisateur avant de modifier le code.
- **Git** : Ne jamais pousser (`git push`) ou committer de modifications sur un dépôt distant sans l'accord explicite préalable de l'utilisateur. Suivre scrupuleusement la **Politique Git — Branches & Versions** (section dédiée ci-dessous) pour toute opération de branche, tag ou release.
- **Internationalisation** : Toutes les descriptions, textes et tooltips du mod doivent être traduits en français (`fr_fr.json`) et en anglais (`en_us.json`) via le système de localisation natif de Minecraft (`Component.translatable()`).
- **Fin de Session** : Ne pas décider de terminer de manière autonome. Suivre le protocole strict de fin de session (mise à jour d'AGENTS.md, compilation, résumé dans un bloc texte unique) si l'utilisateur en signale la fin.

---

## Politique Git — Branches & Versions

Ce document dicte le flux de travail absolu pour la gestion du dépôt, le portage de versions et les cycles de release. Tu dois scrupuleusement suivre ces règles pour chaque opération Git, commit ou création de branche.

### 1. Stratégie d'Architecture des Branches

Le dépôt suit un flux de travail basé sur « Git Flow », adapté aux contraintes de dépendances du modding Minecraft. La branche `main` représente toujours la version stable la plus récente du mod (la pointe de la technologie), tandis que les branches `mc/` sont des archives historiques gelées.

#### Rôles des Branches

| Branche | Rôle |
|---------|------|
| `main` | Miroir de la dernière version stable et prête pour la production pour la version de Minecraft la plus récente prise en charge. Ne jamais commiter de code instable ou de bêtas actives directement ici. |
| `develop/<mc-version>` | L'atelier actif. 90 % du développement, du code expérimental, et des builds Alpha et Beta se font ici (ex. `develop/1.21.2`). |
| `mc/<mc-version>` | Archives historiques pour les anciennes versions de Minecraft (ex. `mc/1.20.1`, `mc/1.21.1`). Gelées ; mises à jour uniquement pour des corrections de bugs critiques. |
| `feature/<nom>` | Branches temporaires à courte durée de vie, créées à partir de `develop/<mc-version>` pour les gros chantiers (ex. réécriture du réseau). Supprimées immédiatement après leur fusion. |

### 2. Flux de Travail Quotidien Standard

#### Développement de Fonctionnalités & Tests Beta

1. Baser toujours le travail sur la branche de développement active : `develop/<mc-version-actuelle>`.
2. Compiler et publier les versions Alpha et Beta directement depuis cette branche.
3. Utiliser des tags sémantiques clairs pour les pré-versions NeoForge : `vneoforge-X.Y.Z-alpha.N` ou `vneoforge-X.Y.Z-beta.N`.

#### Sortie d'une Version Stable (Release)

Lorsque la branche `develop/<mc-version-actuelle>` est entièrement testée et jugée stable :

1. Bascule sur `main` (`git checkout main`).
2. Fusionne la branche de développement : `git merge develop/<mc-version-actuelle>`.
3. Crée le tag de la release officielle : `git tag -a vneoforge-X.Y.Z -m "Release neoforge-X.Y.Z"`.
4. **PAS DE DOUBLE PUSH** : Ne pas créer de branche `mc/` pour la version active actuelle. La branche `main` suffit pour gérer la version la plus récente.

### 3. Protocole de Changement de Version (Portage vers un Minecraft plus récent)

Lorsque le développement bascule vers une version plus récente de Minecraft (ex. passage de 1.21.1 à 1.21.2 ou supérieure) :

| Étape | Action |
|-------|--------|
| **Étape 1** | Archiver le `main` actuel → créer `mc/1.21.1` à partir de `main` |
| **Étape 2** | Mettre à jour le `main` → mettre à jour `build.gradle` & dépendances sur `main` |
| **Étape 3** | Lancer le nouveau dev → créer `develop/1.21.2` à partir du `main` mis à jour |

Séquence de commandes stricte :

```bash
# Étape 1 : Sécuriser l'ancienne version dans une branche d'archive
git checkout main
git checkout -b mc/1.21.1
git push -u origin mc/1.21.1

# Étape 2 : Préparer la branche main pour le futur
git checkout main
# [Appliquer ici les modifications du build.gradle, des mappings Mojang et de NeoForge]
git commit -am "chore: upgrade build system core to Minecraft 1.21.2"
git push origin main

# Étape 3 : Ouvrir le nouveau cycle de développement actif
git checkout -b develop/1.21.2
git push -u origin develop/1.21.2
```

### 4. Protocole de Maintenance Legacy (Correction d'anciennes versions)

Si un bug critique est signalé sur une version archivée (ex. `mc/1.20.1`) :

1. Bascule sur la branche d'archive spécifique : `git checkout mc/1.20.1`.
2. Corrige le bug, teste, commite et publie le correctif (ex. tag `v1.0.1-1.20.1`).
3. **Reporter le correctif** : Si le bug existe aussi dans la version moderne, bascule sur `develop/<mc-version-actuelle>` et utilise `git cherry-pick <commit-hash>` pour importer uniquement le commit du correctif. Ne jamais fusionner (`merge`) une vieille branche `mc/` directement dans `main`.

### 5. Protocole d'Automatisation des Releases (CI/CD GitHub Actions)

Le projet utilise GitHub Actions combiné avec les tâches Gradle `modrinth` (via le plugin Minotaur) et `curseforge` (via CurseForgeGradle) pour automatiser la publication des fichiers JAR. L'IA doit suivre ces règles pour la livraison.

#### Déclenchement automatique

Toute publication sur les plateformes est STRICTEMENT interdite lors d'un simple `push` sur les branches. Le déploiement automatique se déclenche UNIQUEMENT lors de la création et du push d'un tag Git respectant la nomenclature sémantique (ex. `git push origin vneoforge-1.0.1`).

Workflow concerné : `.github/workflows/publish.yml` (trigger : tags `v*`).

#### Nomenclature des versions NeoForge

Toutes les versions publiées pour ce port NeoForge doivent être **préfixées par `neoforge-`** afin de les distinguer du mod Fabric d'origine (`gtp`).

| Élément | Format | Exemple |
|---------|--------|---------|
| `mod_version` (`gradle.properties`) | `neoforge-X.Y.Z` | `neoforge-1.0.1` |
| Pré-release alpha | `neoforge-X.Y.Z-alpha.N` | `neoforge-1.1.0-alpha.1` |
| Pré-release bêta | `neoforge-X.Y.Z-beta` ou `neoforge-X.Y.Z-beta.N` | `neoforge-1.0.1-beta` |
| Tag Git | `v` + version complète | `vneoforge-1.0.1` |
| JAR produit | `GrandTeleport-NeoForge-<mc>-<mod_version>.jar` | `GrandTeleport-NeoForge-1.21.1-neoforge-1.0.1.jar` |

L'IA ne doit **jamais** publier une version NeoForge sans le préfixe `neoforge-`.

#### Changelog obligatoire par version (bilingue EN + FR)

Avant chaque nouvelle release, l'IA doit mettre à jour **deux fichiers** à la racine du projet :

| Fichier | Plateforme | Format |
|---------|------------|--------|
| `CHANGELOG.md` | Modrinth | Markdown |
| `CHANGELOG_CURSEFORGE.html` | CurseForge | HTML (le Markdown s'affiche mal sur CurseForge) |

Règles :
1. **Bilingue obligatoire** : chaque version doit contenir une section **English** et une section **Français** avec le même contenu traduit.
2. Une section par version dans `CHANGELOG.md`, intitulée exactement comme `mod_version` (ex. `## neoforge-1.0.1`).
3. `CHANGELOG_CURSEFORGE.html` ne contient que la **version en cours de publication** (pas l'historique complet).
4. Contenu structuré (au minimum : Added/Ajouts, Fixed/Corrections, Changed/Modifications).
5. La section de la version à publier doit être rédigée **avant** de créer le tag Git ; pas de release sans changelog à jour.
6. Ne pas réutiliser le `README.md` comme changelog de release.

#### Gestion dynamique des types de versions dans Gradle

L'IA doit s'assurer que le fichier `build.gradle` utilise une logique ternaire basée sur le nom de la version pour classifier le déploiement. Le type de release doit être configuré de la manière suivante :

1. Si la version contient `"alpha"` → Déploiement en tant qu'Alpha (Pre-release).
2. Si la version contient `"beta"` → Déploiement en tant que Bêta (Pre-release).
3. Sinon → Déploiement en tant que Release officielle (Stable).

#### Instructions pour l'IA en phase de déploiement

Avant de générer une commande de release, l'IA doit valider :

1. Que `mod_version` dans `gradle.properties` respecte le préfixe `neoforge-`.
2. Que `CHANGELOG.md` (Markdown bilingue) et `CHANGELOG_CURSEFORGE.html` (HTML bilingue) sont à jour pour la version à publier.
3. Que les variables d'environnement `MODRINTH_TOKEN` et `CURSEFORGE_TOKEN` sont correctement appelées dans le script du workflow GitHub.
4. Que les IDs de projets (`projectId` pour Modrinth et ID numérique CurseForge dans la tâche `curseforge` du `build.gradle`) sont correctement déclarés.
5. Qu'aucun jeton d'authentification (Token API) n'est écrit en dur dans le code source du projet. Ils doivent impérativement transiter par les secrets de dépôt GitHub (`${{ secrets.MODRINTH_TOKEN }}` et `${{ secrets.CURSEFORGE_TOKEN }}`).

---

## Roadmap

### ✅ En place
- **Étape 1 : Initialisation & Structure**
  - Fichiers Gradle du MDK NeoForge 1.21.1 configurés.
  - Fichier `neoforge.mods.toml` configuré avec le Mod ID `gtalike_teleport` et les crédits.
  - Classe principale `GtaLikeTeleport.java` implémentée.
  - Classe `GtaLikeTeleportConfig.java` portée et compatible avec l'API NeoForge `FMLPaths`.
  - Fichier `README.md` créé pour créditer hookuru_.
  - Compilation Java validée avec succès.
- **Étape 2 : Le Payload Réseau (Network)**
  - Implémentation de `StartServerTeleportPayload`, `ServerTeleportAckPayload` et `BypassNextServerTeleportPayload` sous forme de records et StreamCodecs.
  - Gestion de l'enregistrement réseau via `RegisterPayloadHandlersEvent` sur le Mod Event Bus.
  - Création des classes `GtaLikeTeleportClientNetworking` et `GtaLikeTeleportServerNetworking`.
  - Intégration dans la classe principale `GtaLikeTeleport.java`.
  - Résolution d'accès sur `DimensionIds.java` (rendue publique) et compilation validée avec succès.
- **Étape 3 : La Logique Client & Calculs Caméra**
  - Interpolation (Lerp) de la caméra sur le client et transition.
  - Remplacement de mixins Fabric par des événements NeoForge client natifs (`MovementInputUpdateEvent`, `RenderGuiEvent.Post`, `ClientTickEvent.Post`).
  - Utilisation de mixins ciblés sur `Camera` et `MouseHandler` pour surcharger la position de la caméra et bloquer la souris.
  - Portage de toutes les classes de logique (`TeleportTransitionController`, `TeleportStepEffectRenderer`, `TeleportCommandMatcher`, `TeleportDestinationParser`, `TeleportSounds`) et des classes de compatibilité.
  - Compilation Java validée avec succès.

- **Étape 4 : Gestion des Nuages & Rendu Visuel**
  - Masquage automatique des nuages (`LevelRendererMixin`) durant les transitions de téléportation pour éviter les coupures visuelles.
  - Masquage dynamique du modèle du joueur (`EntityRendererMixin`) pour une vue à la première personne fluide.
  - Neutralisation du menu pause durant la transition cinématique (`MinecraftMixin`).
  - Portage complet de la compatibilité avec Waystones (`WaystonesWarpPlateHandler`, `WaystonesWarpPlateBlockEntityMixin`) et JourneyMap (`JourneyMapClientNetworkDispatcherMixin`).
  - Compilation Java validée avec succès.

- **Correctifs post-tests (session finale)**
  - **Bug fix** : `GtaLikeTeleportMixinPlugin` utilisait `ModList` (runtime) dans `shouldApplyMixin()`, appelé bien avant l'initialisation de FML. Remplacé par `LoadingModList` (early-loading) → NPE corrigé.
  - **Bug fix** : `NeoForge.EVENT_BUS.register(this)` dans `GtaLikeTeleport` levait une `IllegalArgumentException` car la classe n'a aucune méthode `@SubscribeEvent` (elle utilise `addListener()`). Ligne supprimée.
  - **Validation en jeu** : mod testé et fonctionnel en jeu avec des mods tiers (Waystones, JourneyMap, Sodium, etc.).
- **Personnalisations avancées (VFX, SFX & Config)**
  - Toggles de personnalisation en jeu (presets, packs de sons GTA, vignette, shutter flash, scanlines).
  - Filtres d'intégration pour limiter les transitions (vanilla `/tp`, Waystones, JourneyMap, portails).
  - Double colonne de configuration dans l'écran de paramètres.
  - **Synchronisation Audio GTA V** : Importation et configuration de 4 fichiers audio GTA V (`gta5_dezoom`, `gta5_wind`, `gta5_zoom`, `gta5_landing`) parfaitement calés sur les phases de zoom-out, de voyage, de zoom-in et d'impact final.
  - **Shading caméra (ébauche non validée)** : assets GLSL `satellite_camera` + `loadEffect` + mixins `GameRendererAccessor`/`PostChainAccessor`/`PostPassAccessor` + fallback Java (tint vert + bloom) présents dans le code, mais l'effet post-processing n'est **pas fonctionnel ni validé en jeu** (non présent dans le port Fabric d'origine).
  - Compilation et packaging final validés avec succès.
- **Correctifs config & sons GTA V (session 03/07/2026)**
  - **Sons GTA V** : copie des 4 `.ogg` depuis `audio/` vers `sounds/teleport/` (`gta5_dezoom`, `gta5_wind`, `gta5_zoom`, `gta5_landing`) — validé en jeu.
  - **Bouton Config menu Mods** : enregistrement `IConfigScreenFactory` via `ModContainer.registerExtensionPoint()` (NeoForge 21.1.234) + commande `/gtp config`.
  - **Interface config** : fix labels effacés par `repositionWidgets()`, override `protected rebuildWidgets()`, layout par défaut propre (`configLayoutCustom=false`), migration `configVersion=3` pour purger les anciens layouts dev sauvegardés.
  - **IconButton** : suppression du double rendu texte (superposition illisible), colonnes toggle recalculées dynamiquement — validé en jeu.
  - Compilation `gradlew build` validée, JAR `GrandTeleport-NeoForge-1.21.1-1.0.0-beta.jar` produit.
- **Mise en pause VFX non aboutis (session 03/07/2026)**
  - **Sons GTA V par défaut** : `customSoundsEnabled=true`, `soundPack=gta` (`configVersion=5`).
  - **VFX mis de côté** : shading/bloom satellite (`enableSatelliteCameraFx=false`), shutter flash, vignette, fade color — retirés de l'écran config General, rendu désactivé.
  - **Page FX Satellite supprimée** de l'écran config (layout buggé, tuning reporté).
  - Code shader/overlay conservé en arrière-plan pour reprise future.
- **Nettoyage UI config (session 03/07/2026, `configVersion=6`)**
  - **Scanline glitch retiré** : toggle interference supprimé de General (`enableInterferenceLines=false`).
  - **General réorganisé** : grille 2×2 (Effect/Movement, Cross-dimension/Preset).
  - **Sons** : espacement sliders corrigé (hauteur réelle 44px, plus de chevauchement).
- **Modes sons 3 états (session 03/07/2026, `configVersion=7`)**
  - Cycle **GTA V → Default (Mod) → OFF → GTA V** (`soundPack=gta|default|off`).
  - Mode **OFF** = silence total (suppression fallback sons vanilla Minecraft).
  - Slider volume Minecraft retiré de l'UI ; plan : `implement-plan-sound-modes-fix.md`.
- **CI/CD Modrinth & CurseForge (session 03/07/2026)**
  - Plugins Gradle : `com.modrinth.minotaur` + `net.darkhax.curseforgegradle` dans `build.gradle`.
  - Workflow `.github/workflows/publish.yml` : déclenchement sur tag `v*`, Java 21, `./gradlew build modrinth curseforge`.
  - Tokens via secrets GitHub (`MODRINTH_TOKEN`, `CURSEFORGE_TOKEN`) — jamais en dur dans le code.
  - Détection dynamique du type de release (alpha / beta / release) selon `project.version`.
  - Nomenclature `neoforge-` obligatoire + changelog bilingue EN/FR (`CHANGELOG.md` + `CHANGELOG_CURSEFORGE.html`) requis par version.

### 🔄 À faire
- **Effet Shading de Caméra & Exposition GLSL** (en pause) : finaliser et valider en jeu le post-processing `satellite_camera`. Réactiver via `enableSatelliteCameraFx` + page config dédiée quand prêt.
- **Shutter flash / vignette / fade color / scanline glitch** (en pause) : brancher au rendu ou retirer définitivement selon validation utilisateur.
- **i18n complète écran config** (Phase 3) : plan `.kilo/plans/20260703053000-i18n-config-screen.md` — titres/onglets encore en anglais dur dans `getDefaultItemText()`, à valider explicitement avant implémentation.

---

## Instructions pour l'assistant
1. **Lecture prioritaire** : Lire impérativement le contenu d`AGENTS.md` au début de chaque session ou tâche.
2. **Notification** : À chaque modification d'`AGENTS.md`, prévenir l'utilisateur et expliquer le changement.
3. **Git & CI/CD** : Respecter la section **Politique Git — Branches & Versions** (dont le **Protocole d'Automatisation des Releases**) pour toute création de branche, merge, tag, release ou portage de version Minecraft.
4. **Fin de session** : Appliquer le protocole de fin de session lorsque l'utilisateur le demande.
5. **Format du résumé de fin de session** : Le résumé final doit être rédigé en **texte brut uniquement** (pas de markdown, pas de titres `#`, pas de gras `**`, pas de listes `-`), dans un seul bloc de texte continu que l'utilisateur peut copier directement.
