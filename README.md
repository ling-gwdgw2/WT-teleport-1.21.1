# WT-teleport-1.21.1

![WT-teleport Icon](icon.png)

## English

This project is an unofficial port of the original Fabric mod **Grand Teleport (GTP)** by **hookuru_** to the **NeoForge** ecosystem (Minecraft 1.21.1), customized and maintained as **WT-teleport**.

The original mod (for Fabric) can be found here:
* Modrinth: [https://modrinth.com/mod/gtp](https://modrinth.com/mod/gtp)
* GitHub: [https://github.com/hookuru/GrandTeleport](https://github.com/hookuru/GrandTeleport)

### Description

WT-teleport adds a GTA V-style cinematic camera transition when teleporting in Minecraft.

When a player teleports (via `/tp`, `/teleport`, or other supported means), the camera detaches from the player, rises vertically into the clouds through several zoom stages, moves to the destination, and then descends onto the player's final position smoothly.

### Features

* Bird's-eye camera zoom/dezoom effect and flyover transitions.
* Configurable heights and transition speeds.
* Distinct configurations for the Overworld, the Nether, and the End.
* Option to freeze the player (movement and camera rotation) during the transition.
* Inter-dimensional teleportation support.
* Custom built-in sound effects.

### Commands

* `/wtp on` or `/gtp on` - Enable the transition.
* `/wtp off` or `/gtp off` - Disable the transition.
* `/wtp status` or `/gtp status` - Show current status.
* `/wtp player_freeze on|off|status` - Toggle/check player freeze status.
* `/wtp config` - Open settings GUI.
* `/wtp sound gta|default|off` - Switch sound mode.

### Credits and License

* **Original Author**: hookuru_ (original mod supervision, code, assets).
* **NeoForge Port**: [cfrsh](https://github.com/f65q4fffs).
* **Code License**: This port is distributed under the **MIT License**, in accordance with the original project.
* **Audio License**: Bundled custom sound effects are edited audio assets from ZapSplat under a paid Premium plan. They may not be extracted, redistributed, sold, or reused separately. See [LICENSE](./LICENSE) for details.

---

## Français

Ce projet est un portage non officiel du mod original **Grand Teleport (GTP)** de **hookuru_** pour l'écosystème **NeoForge** (Minecraft 1.21.1).

Le mod d'origine (pour Fabric) peut être trouvé ici :
* Modrinth : [https://modrinth.com/mod/gtp](https://modrinth.com/mod/gtp)
* GitHub : [https://github.com/hookuru/GrandTeleport](https://github.com/hookuru/GrandTeleport)

### Description

Grand Teleport ajoute un effet de transition de caméra cinématique de style GTA V lors des téléportations dans Minecraft. 

Lorsque le joueur se téléporte (via `/tp`, `/teleport` ou d'autres moyens supportés), la caméra se détache du joueur, monte verticalement dans les nuages à travers plusieurs étapes de zoom, se déplace vers la destination, puis redescend sur la position finale du joueur de manière fluide.

### Fonctionnalités

* Effet cinématique de zoom/dézoom vertical et déplacement de caméra en vol d'oiseau.
* Hauteurs et transitions configurables.
* Configurations distinctes pour l'Overworld, le Nether et l'End.
* Option pour figer le joueur (mouvements et caméra) pendant la transition.
* Support des téléportations inter-dimensionnelles.
* Effets sonores intégrés.

### Commandes

* `/gtp on` ou `/grandtp on` - Activer la transition.
* `/gtp off` ou `/grandtp off` - Désactiver la transition.
* `/gtp status` ou `/grandtp status` - Afficher l'état actuel du mod.
* `/gtp player_freeze on|off|status` - Activer/désactiver/vérifier le blocage du joueur pendant la transition.

### Crédits et Licence

* **Auteur Original** : hookuru_ (code original, supervision, assets).
* **Portage NeoForge** : [cfrsh](https://github.com/f65q4fffs).
* **Licence du code** : Ce portage est distribué sous la licence **MIT**, conformément au projet original.
* **Licence audio** : Les effets sonores personnalisés inclus sont des assets édités à partir de sons licenciés depuis ZapSplat sous un plan Premium. Ils ne peuvent pas être extraits, redistribués, vendus ou réutilisés séparément. Voir [LICENSE](./LICENSE) pour les détails.
