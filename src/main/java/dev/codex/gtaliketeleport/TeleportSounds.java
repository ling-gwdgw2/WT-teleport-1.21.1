package dev.codex.gtaliketeleport;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public final class TeleportSounds {
    public static final SoundEvent CAMERA_IN = create("teleport.camera_in");
    public static final SoundEvent CAMERA_OUT = create("teleport.camera_out");
    public static final SoundEvent TELEPORT = create("teleport.teleport");
    public static final SoundEvent ZOOM_IN_LONG = create("teleport.zoom_in_long");
    public static final SoundEvent ZOOM_IN_SHORT = create("teleport.zoom_in_short");
    public static final SoundEvent ZOOM_OUT_LONG = create("teleport.zoom_out_long");
    public static final SoundEvent ZOOM_OUT_SHORT = create("teleport.zoom_out_short");

    public static final SoundEvent GTA5_DEZOOM = create("teleport.gta5_dezoom");
    public static final SoundEvent GTA5_WIND = create("teleport.gta5_wind");
    public static final SoundEvent GTA5_ZOOM = create("teleport.gta5_zoom");
    public static final SoundEvent GTA5_LANDING = create("teleport.gta5_landing");

    private TeleportSounds() {
    }

    private static SoundEvent create(String path) {
        return SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(GtaLikeTeleport.MOD_ID, path));
    }
}