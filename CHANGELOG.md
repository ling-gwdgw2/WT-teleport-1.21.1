# Changelog

All NeoForge port versions use the `neoforge-` prefix to distinguish them from the original Fabric mod.

---

## neoforge-1.0.1

### English

#### Added
- Three sound modes: GTA V, Default (Mod), and OFF (`/gtp sound gta|default|off`).
- GitHub Actions CI/CD pipeline for automatic Modrinth and CurseForge publishing (triggered by `v*` tags).

#### Fixed
- Config screen: readable IconButton labels, restored layout, dynamically recalculated toggle columns.
- Sounds: explicit separation between GTA V and default mod sound packs; fixed client config loading.
- Config button available from the NeoForge Mods menu and via `/gtp config`.

#### Changed
- Version naming: mandatory `neoforge-` prefix for all NeoForge port releases.

### Français

#### Ajouts
- Modes sons en 3 états : GTA V, Default (Mod) et OFF (`/gtp sound gta|default|off`).
- Pipeline CI/CD GitHub Actions pour publication automatique Modrinth et CurseForge (déclenchement par tag `v*`).

#### Corrections
- Écran de configuration : labels IconButton lisibles, layout restauré, colonnes toggle recalculées.
- Sons : séparation explicite des packs GTA V et sons par défaut du mod ; chargement config client corrigé.
- Bouton Config accessible depuis le menu Mods NeoForge et via `/gtp config`.

#### Modifications
- Nomenclature des versions : préfixe obligatoire `neoforge-` pour toutes les releases du port.

---

## neoforge-1.0.0

### English

#### Added
- Initial port of Grand Teleport (Fabric) to NeoForge 1.21.1.
- GTA V-style cinematic camera transition (zoom-out, flyover, landing).
- Waystones and JourneyMap compatibility; cloud and player model hiding during transitions.
- In-game config screen and `/gtp` commands.

### Français

#### Ajouts
- Port initial de Grand Teleport (Fabric) vers NeoForge 1.21.1.
- Transition caméra cinématique GTA V (zoom, survol, atterrissage).
- Compatibilité Waystones, JourneyMap, masquage nuages/joueur durant la transition.
- Écran de configuration in-game et commandes `/gtp`.