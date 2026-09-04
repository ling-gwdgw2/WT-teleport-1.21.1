# WT-teleport 1.21.1 (Grand Teleport - NeoForge Port)

<div align="center">

<img src="icon.png" alt="WT Teleport Logo" width="128" height="128" />

# Grand Teleport (NeoForge Port)
**A cinematic GTA V-style camera transition mod for Minecraft 1.21.1**

[![Minecraft 1.21.1](https://img.shields.io/badge/Minecraft-1.21.1-brightgreen.svg)](https://minecraft.net/)
[![NeoForge](https://img.shields.io/badge/NeoForge-21.1.234+-orange.svg)](https://neoforged.net/)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Original Mod: GrandTeleport](https://img.shields.io/badge/Original%20Mod-GrandTeleport-red.svg)](https://github.com/hookuru/GrandTeleport.git)

---
### 🌐 Language: [English](#english) | [ภาษาไทย (Thai)](#ภาษาไทย-thai) | [Français](#français)
---

</div>

> [!IMPORTANT]
> ### 🌟 Credits & Original Developer Attribution / เครดิตผู้พัฒนาต้นฉบับ
> 
> This mod is an **unofficial NeoForge 1.21.1 port** of **Grand Teleport** (originally created for Fabric).
> 
> * **Original Creator / Developer**: **[hookuru_](https://github.com/hookuru)** & **Codex**
> * **Original Repository (Fabric)**: **[https://github.com/hookuru/GrandTeleport.git](https://github.com/hookuru/GrandTeleport.git)**
> * **NeoForge 1.21.1 Port by**: **cfrsh** / **[ling-gwdgw2](https://github.com/ling-gwdgw2/WT-teleport-1.21.1)**
> 
> All core concepts, camera transition choreography, and visual inspiration originate from the original work by **hookuru_**. If you enjoy this mod, please star and show appreciation to the original repository!

---

<a name="english"></a>
## 📖 Overview

**Grand Teleport (WT-teleport)** transforms teleportation in Minecraft into an immersive, cinematic experience inspired by the iconic character-switching camera transitions of **Grand Theft Auto V**.

Whenever you teleport via commands, Waystones, or supported map mods, the camera pulls out into the stratosphere in three discrete stages, glides across the terrain at high speed, and plunges back down to your destination coordinates accompanied by custom sound effects, screen flashes, and camera filters.

---

## ✨ Features

### 🎥 GTA V-Style Cinematic Transitions
* **Three-Stage Zoom-Out**: The camera pulls up rapidly in three dramatic stages, reaching high satellite altitude.
* **Sky Flyover**: High-speed aerial transit across terrain directly towards the destination coordinates.
* **Dramatic Plunge & Zoom-In**: Three-stage descent into the target location with landing camera impact.
* **Cross-Dimension Teleportation**: Seamless transitions even when traveling across dimensions (Overworld, Nether, The End).
* **Chunk & Horizon Safeguards**: Smooth chunk fading during high-altitude shots; hides local player model and clouds during transit to eliminate visual clipping.

### 🎨 Visual & Satellite Camera FX
* **Satellite Military Color Grade**: Real-time adjustable khaki tint simulating military overhead reconnaissance satellites.
* **Gamma Pulses & Blowout**: Dynamic exposure flashes on each zoom stage mimicking camera aperture adjustments.
* **Shutter Flash**: Camera shutter flashes synchronized with zoom transitions.
* **Vignette Effect**: Soft, cinematic edge darkening during flight.
* **Scanline Glitch & CRT Interference**: Subtle retro television scanlines and static glitch effects during high-speed transitions.

### 🔊 Dynamic Audio System
* **Three Sound Modes**:
  * `GTA V`: Iconic GTA-style transition sound effects.
  * `Default (Mod)`: Alternative atmospheric sound pack.
  * `OFF`: Completely silent for players who prefer vanilla soundscapes.
* **Dual Volume Sliders**: Adjust Minecraft game volume and teleportation audio effects independently.

### 🛠️ In-Game Configuration GUI & Live Layout Editor
* Open the settings in-game via `/gtp config` or through the NeoForge **Mods** menu.
* **6 Dedicated Config Pages**:
  1. *General & Visual Settings*
  2. *Zoom Stage Heights* (Altitude customization for all 3 zoom levels)
  3. *Zoom Stage Durations* (Fine-tune timing and speed)
  4. *Sound Settings* (Sound pack selector and volume sliders)
  5. *Satellite FX* (Live gamma strength, decay, khaki tint, bloom blowout)
  6. *Integrations* (Configure third-party mod behaviors)
* **Built-in Visual Layout Editor**:
  * Drag and resize UI elements directly on-screen.
  * Grid guides, center alignment lines, and snap-to-grid support.
  * Aspect-ratio locking and direct coordinate editing.

### 🔌 Broad Mod Compatibility
Fully compatible and integrated out-of-the-box with:
* **Vanilla Minecraft**: `/tp` and `/teleport` commands.
* **Waystones**: Warp Plates activation support.
* **JourneyMap**: Triggers cinematics when teleporting through map waypoints.
* **Nether & End Portals**: Cinematic transitions when stepping through portals.
* **Rendering & Optimization Mods**:
  * **Iris** & **Sodium** (Shader pack compatible)
  * **Distant Horizons** (LOD render support)
  * **Bobby** & **Voxy** (Render distance mods)

---

## ⌨️ In-Game Commands

You can use `/gtp` or `/wtp` (as well as `/wt_teleport`, `/wtteleport`, `/grandtp`):

| Command | Description |
|---|---|
| `/gtp config` | Opens the full in-game configuration and layout screen |
| `/gtp on` | Enables the cinematic teleportation effect |
| `/gtp off` | Disables the cinematic teleportation effect |
| `/gtp status` | Displays the current status of the mod |
| `/gtp sound <gta\|default\|off>` | Switches between GTA V sound pack, default mod sounds, or muted |
| `/gtp player_freeze <on\|off\|status>` | Toggles freezing player movement/input during camera flight |

---

## 📦 Requirements & Installation

### Requirements
* **Minecraft**: `1.21.1`
* **Mod Loader**: [NeoForge](https://neoforged.net/) `21.1.234` or newer
* **Java**: `Java 21`

### Installation
1. Install [NeoForge 1.21.1](https://neoforged.net/).
2. Download the latest `.jar` file from the [Releases](https://github.com/ling-gwdgw2/WT-teleport-1.21.1/releases) page.
3. Place the `.jar` file into your Minecraft `.minecraft/mods` directory.
4. Launch the game and enjoy cinematic teleportation!

---

## 🔨 Building from Source

To compile this project locally:

```bash
# Clone the repository
git clone https://github.com/ling-gwdgw2/WT-teleport-1.21.1.git
cd WT-teleport-1.21.1

# Build the mod using Gradle
./gradlew build
```
The compiled mod `.jar` will be available in `build/libs/`.

---

<a name="ภาษาไทย-thai"></a>
## 🇹🇭 ภาษาไทย (Thai Description)

### ภาพรวมของม็อด
**Grand Teleport (WT-teleport)** เป็นม็อดสำหรับ Minecraft 1.21.1 บน **NeoForge** ที่นำเอาระบบการเปลี่ยนมุมมองกล้องอันเป็นเอกลักษณ์ของเกม **Grand Theft Auto V (GTA V)** มาสู่ Minecraft

เมื่อผู้เล่นทำการวาร์ป (Teleport) ผ่านคำสั่ง, เสาวาร์ป (Waystones) หรือม็อดแผนที่ มุมมองกล้องจะซูมออกสู่ท้องฟ้าอย่างรวดเร็วเป็น 3 ระดับ (Zoom-out) บินข้ามแผนที่ไปยังพิกัดเป้าหมาย และทิ้งดิ่งซูมเข้าสู่ตัวละครอย่างนุ่มนวลและทรงพลัง พร้อมเอฟเฟกต์ภาพและเสียงแบบสมจริง

### ฟีเจอร์เด่น
* 🎬 **กล้องมุมมองสไตล์ GTA V**: ซูมออก 3 จังหวะ บินข้ามภูมิประเทศ และทิ้งดิ่งลงสู่จุดหมาย
* 🌌 **รองรับการข้ามมิติ**: แสดงผลอย่างลื่นไหลแม้จะวาร์ปข้ามมิติ (Overworld, Nether, The End)
* 📺 **เอฟเฟกต์ภาพ Satellite & Glitch**: ปรับแต่งสีสไตล์กล้องดาวเทียมทหาร (Khaki color grading), แสงแฟลชชัตเตอร์ (Shutter Flash), ขอบจอมืด (Vignette) และเส้นสแกนสัญญาณทีวีรบกวน (Scanline CRT Glitch)
* 🎵 **ระบบเสียง 3 โหมด**: เลือกใช้งานเสียงเอฟเฟกต์ GTA V, เสียงม็อดพื้นฐาน (Default) หรือปิดเสียง (OFF) พร้อมตัวปรับระดับเสียงแยกจากเสียงเกม
* ⚙️ **หน้าจอตั้งค่า GUI ในเกม**: ปรับแต่งค่าความสูงกล้อง, ระยะเวลา, แสงสี ได้อย่างละเอียด พร้อมเครื่องมือจัดตำแหน่ง UI (Layout Editor) ที่สามารถลาก ย่อ/ขยาย และมีเส้น Grid ช่วยจัดแนว
* 🧩 **รองรับม็อดอื่นหลากหลาย**: ทำงานร่วมกับ `/tp`, เสาวาร์ป Waystones Warp Plates, แผนที่ JourneyMap, ประตูมิติ รวมถึงม็อดกราฟิกอย่าง Iris, Sodium, Distant Horizons, Bobby และ Voxy

---

<a name="français"></a>
## 🇫🇷 Français

### À propos
Ce projet est un portage non officiel du mod original **Grand Teleport (GTP)** de **hookuru_** pour l'écosystème **NeoForge** (Minecraft 1.21.1).

Le mod d'origine (pour Fabric) peut être consulté ici :
* GitHub : [https://github.com/hookuru/GrandTeleport.git](https://github.com/hookuru/GrandTeleport.git)

### Description
Grand Teleport ajoute un effet de transition de caméra cinématique inspiré de **GTA V** lors des téléportations dans Minecraft. 

Lorsque le joueur se téléporte (via `/tp`, `/teleport`, Waystones, ou JourneyMap), la caméra se détache du joueur, monte verticalement dans les nuages à travers plusieurs étapes de zoom, traverse le monde à haute altitude vers la destination, puis redescend en plongeant sur la position finale du joueur de manière fluide.

### Fonctionnalités
* Effet cinématique de zoom/dézoom vertical en 3 étapes et survol à haute altitude.
* Effets visuels satellites : filtre couleur kaki, flashs gamma, vignetage et parasites CRT/scanlines.
* 3 modes sonores : GTA V, son par défaut du mod, et OFF (muet).
* Interface de configuration complète en jeu (`/gtp config`) avec éditeur de disposition visuel (grille, aimantation, verrouillage du ratio).
* Support des téléportations inter-dimensionnelles (Overworld, Nether, The End).
* Compatibilité native avec Waystones, JourneyMap, Iris, Sodium, Distant Horizons, Bobby et Voxy.

---

## 📜 Licenses & Attribution

* **Source Code**: Released under the **[MIT License](LICENSE)**.
* **Audio Assets**: The bundled custom sound effects are **not** licensed under the MIT License. They are derived from licensed audio via **ZapSplat** under a paid Premium plan and are included solely for use within Grand Teleport. They may not be extracted, sold, sublicensed, or redistributed independently. Please refer to the [ZapSplat Standard License](https://www.zapsplat.com/license-type/standard-license/) for full terms.
* **Original Project**: Fabric version developed by **[hookuru_](https://github.com/hookuru)** & **Codex** at [https://github.com/hookuru/GrandTeleport](https://github.com/hookuru/GrandTeleport.git).
