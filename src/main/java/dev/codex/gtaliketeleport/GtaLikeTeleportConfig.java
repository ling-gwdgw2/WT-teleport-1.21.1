package dev.codex.gtaliketeleport;

import net.neoforged.fml.loading.FMLPaths;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public final class GtaLikeTeleportConfig {
    private static final String FILE_NAME = "wt_teleport.properties";
    private static final String LEGACY_FILE_NAME = "grand_teleport.properties";
    private static final String OLD_LEGACY_FILE_NAME = "gtalike_teleport.properties";
    private static final String EFFECT_ENABLED_KEY = "effectEnabled";
    private static final String PLAYER_FREEZE_ENABLED_KEY = "playerFreezeEnabled";
    private static final String CROSS_DIMENSION_TRAVEL_ENABLED_KEY = "crossDimensionTravelEnabled";
    private static final String ZOOM_HEIGHTS_LINKED_KEY = "zoomHeightsLinked";
    private static final String ZOOM_OUT_STAGE_KEY_PREFIX = "zoomOutStage";
    private static final String ZOOM_IN_STAGE_KEY_PREFIX = "zoomInStage";
    private static final String NETHER_ZOOM_HEIGHTS_LINKED_KEY = "netherZoomHeightsLinked";
    private static final String NETHER_ZOOM_OUT_STAGE_KEY_PREFIX = "netherZoomOutStage";
    private static final String NETHER_ZOOM_IN_STAGE_KEY_PREFIX = "netherZoomInStage";
    private static final String END_ZOOM_HEIGHTS_LINKED_KEY = "endZoomHeightsLinked";
    private static final String END_ZOOM_OUT_STAGE_KEY_PREFIX = "endZoomOutStage";
    private static final String END_ZOOM_IN_STAGE_KEY_PREFIX = "endZoomInStage";
    private static final String ZOOM_OUT_STAGE_TICKS_KEY_PREFIX = "zoomOutStageTicks";
    private static final String ZOOM_IN_STAGE_TICKS_KEY_PREFIX = "zoomInStageTicks";
    private static final String ZOOM_STAGE_GLIDE_HEIGHT_KEY = "zoomStageGlideHeight";
    private static final String ZOOM_STAGE_GLIDE_TICKS_KEY = "zoomStageGlideTicks";
    private static final String BODY_CAMERA_HEIGHT_KEY = "bodyCameraHeight";
    private static final String BODY_GLIDE_HEIGHT_KEY = "bodyGlideHeight";
    private static final String BODY_GLIDE_TICKS_KEY = "bodyGlideTicks";
    private static final String LOCAL_PLAYER_HIDE_TICKS_KEY = "localPlayerHideTicks";
    private static final String CUSTOM_SOUNDS_ENABLED_KEY = "customSoundsEnabled";
    private static final String MINECRAFT_SOUND_VOLUME_KEY = "minecraftSoundVolume";
    private static final String CUSTOM_SOUND_VOLUME_KEY = "customSoundVolume";
    private static final String WARP_PLATE_TRANSITIONS_ENABLED_KEY = "warpPlateTransitionsEnabled";
    private static final String EXTERNAL_TELEPORT_TRANSITIONS_ENABLED_KEY = "externalTeleportTransitionsEnabled";
    private static final String FALLBACK_CHUNK_FADE_ENABLED_KEY = "fallbackChunkFadeEnabled";
    private static final String CONFIG_LAYOUT_EDITOR_BUTTON_VISIBLE_KEY = "configLayoutEditorButtonVisible";
    private static final String CONFIG_LAYOUT_DEBUG_ENABLED_KEY = "configLayoutDebugEnabled";
    private static final String CONFIG_LAYOUT_ASPECT_LOCKED_KEY = "configLayoutAspectLocked";
    private static final String CONFIG_LAYOUT_GRID_ENABLED_KEY = "configLayoutGridEnabled";
    private static final String CONFIG_LAYOUT_SNAP_ENABLED_KEY = "configLayoutSnapEnabled";
    private static final String CONFIG_LAYOUT_CUSTOM_KEY = "configLayoutCustom";
    private static final String CONFIG_LAYOUT_X_KEY = "configLayoutX";
    private static final String CONFIG_LAYOUT_Y_KEY = "configLayoutY";
    private static final String CONFIG_LAYOUT_WIDTH_KEY = "configLayoutWidth";
    private static final String CONFIG_LAYOUT_HEIGHT_KEY = "configLayoutHeight";
    private static final String CONFIG_LAYOUT_BASE_WIDTH_KEY = "configLayoutBaseWidth";
    private static final String CONFIG_LAYOUT_BASE_HEIGHT_KEY = "configLayoutBaseHeight";
    private static final String CONFIG_WIDGET_PREFIX = "configWidget.";
    private static final String CONFIG_TEXT_PREFIX = "configText.";
    private static final String TRANSITION_PRESET_KEY = "transitionPreset";
    private static final String SOUND_PACK_KEY = "soundPack";
    public static final String SOUND_PACK_GTA = "gta";
    public static final String SOUND_PACK_DEFAULT = "default";
    public static final String SOUND_PACK_OFF = "off";
    private static final String ENABLE_SHUTTER_FLASH_KEY = "enableShutterFlash";
    private static final String ENABLE_VIGNETTE_KEY = "enableVignette";
    private static final String ENABLE_INTERFERENCE_LINES_KEY = "enableInterferenceLines";
    private static final String FADE_COLOR_KEY = "fadeColor";
    private static final String ENABLE_VANILLA_TP_KEY = "enableVanillaTp";
    private static final String ENABLE_WAYSTONES_KEY = "enableWaystones";
    private static final String ENABLE_JOURNEYMAP_KEY = "enableJourneyMap";
    private static final String ENABLE_PORTALS_KEY = "enablePortals";
    private static final String ENABLE_SATELLITE_CAMERA_FX_KEY = "enableSatelliteCameraFx";
    private static final String SATELLITE_GAMMA_STRENGTH_KEY = "satelliteGammaStrength";
    private static final String SATELLITE_GAMMA_DECAY_TICKS_KEY = "satelliteGammaDecayTicks";
    private static final String SATELLITE_GAMMA_OVERLAY_LIFT_KEY = "satelliteGammaOverlayLift";
    private static final String SATELLITE_GAMMA_BLOWOUT_STRENGTH_KEY = "satelliteGammaBlowoutStrength";
    private static final String SATELLITE_COLOR_GRADE_MAX_KEY = "satelliteColorGradeMax";
    private static final String SATELLITE_PLUNGE_EXPOSURE_MAX_KEY = "satellitePlungeExposureMax";
    private static final String SATELLITE_SHADER_EXPOSURE_SCALE_KEY = "satelliteShaderExposureScale";
    private static final double DEFAULT_SATELLITE_GAMMA_STRENGTH = 2.0D;
    private static final double MIN_SATELLITE_GAMMA_STRENGTH = 0.1D;
    private static final double MAX_SATELLITE_GAMMA_STRENGTH = 4.0D;
    private static final double DEFAULT_SATELLITE_GAMMA_DECAY_TICKS = 6.0D;
    private static final double MIN_SATELLITE_GAMMA_DECAY_TICKS = 2.0D;
    private static final double MAX_SATELLITE_GAMMA_DECAY_TICKS = 15.0D;
    private static final double DEFAULT_SATELLITE_GAMMA_OVERLAY_LIFT = 0.55D;
    private static final double DEFAULT_SATELLITE_GAMMA_BLOWOUT_STRENGTH = 0.65D;
    private static final double DEFAULT_SATELLITE_COLOR_GRADE_MAX = 0.38D;
    private static final double DEFAULT_SATELLITE_PLUNGE_EXPOSURE_MAX = 0.6D;
    private static final double DEFAULT_SATELLITE_SHADER_EXPOSURE_SCALE = 1.25D;
    private static final double MIN_SATELLITE_UNIT = 0.0D;
    private static final double MAX_SATELLITE_UNIT = 1.0D;
    private static final double MIN_SATELLITE_SHADER_SCALE = 0.0D;
    private static final double MAX_SATELLITE_SHADER_SCALE = 3.0D;
    private static final int CURRENT_CONFIG_VERSION = 8;
    private static final String CONFIG_VERSION_KEY = "configVersion";
    private static final String DEFAULT_CONFIG_PROPERTIES = """
configVersion=8
enableSatelliteCameraFx=false
satelliteGammaStrength=2.0
satelliteGammaDecayTicks=6.0
satelliteGammaOverlayLift=0.55
satelliteGammaBlowoutStrength=0.65
satelliteColorGradeMax=0.38
satellitePlungeExposureMax=0.6
satelliteShaderExposureScale=1.25
bodyCameraHeight=6.0
transitionPreset=classic
soundPack=gta
enableShutterFlash=false
enableVignette=false
enableInterferenceLines=false
fadeColor=black
enableVanillaTp=true
enableWaystones=true
enableJourneyMap=true
enablePortals=true
bodyGlideHeight=0.5
bodyGlideTicks=10
configLayoutAspectLocked=false
configLayoutBaseHeight=353
configLayoutBaseWidth=640
configLayoutCustom=false
configLayoutDebugEnabled=false
configLayoutEditorButtonVisible=false
configLayoutGridEnabled=true
configLayoutSnapEnabled=false
crossDimensionTravelEnabled=false
customSoundVolume=0.5
customSoundsEnabled=true
effectEnabled=true
endZoomHeightsLinked=true
endZoomInStage1=20
endZoomInStage2=40
endZoomInStage3=60
endZoomOutStage1=20
endZoomOutStage2=40
endZoomOutStage3=60
externalTeleportTransitionsEnabled=true
fallbackChunkFadeEnabled=false
localPlayerHideTicks=2
minecraftSoundVolume=0.5
netherZoomHeightsLinked=true
netherZoomInStage1=20
netherZoomInStage2=40
netherZoomInStage3=60
netherZoomOutStage1=20
netherZoomOutStage2=40
netherZoomOutStage3=60
playerFreezeEnabled=true
warpPlateTransitionsEnabled=true
zoomHeightsLinked=true
zoomInStage1=20
zoomInStage2=40
zoomInStage3=60
zoomInStageTicks1=13
zoomInStageTicks2=13
zoomInStageTicks3=13
zoomOutStage1=20
zoomOutStage2=40
zoomOutStage3=60
zoomOutStageTicks1=13
zoomOutStageTicks2=13
zoomOutStageTicks3=13
zoomStageGlideHeight=0.5
zoomStageGlideTicks=13
            """;
    private static final double[] DEFAULT_STAGE_HEIGHTS = {20.0D, 40.0D, 60.0D};
    private static final double MIN_STAGE_HEIGHT = 8.0D;
    private static final double MAX_STAGE_HEIGHT = 512.0D;
    private static final double MIN_STAGE_GAP = 1.0D;
    private static final int[] DEFAULT_STAGE_TICKS = {13, 13, 13};
    private static final int MIN_STAGE_TICKS = 1;
    private static final int MAX_STAGE_TICKS = 200;
    private static final double DEFAULT_ZOOM_STAGE_GLIDE_HEIGHT = 0.5D;
    private static final double MIN_ZOOM_STAGE_GLIDE_HEIGHT = 0.1D;
    private static final double MAX_ZOOM_STAGE_GLIDE_HEIGHT = 5.0D;
    private static final int DEFAULT_ZOOM_STAGE_GLIDE_TICKS = 13;
    private static final double DEFAULT_BODY_CAMERA_HEIGHT = 6.0D;
    private static final double MIN_BODY_CAMERA_HEIGHT = 0.1D;
    private static final double MAX_BODY_CAMERA_HEIGHT = 10.0D;
    private static final double DEFAULT_BODY_GLIDE_HEIGHT = 0.5D;
    private static final double MIN_BODY_GLIDE_HEIGHT = 0.1D;
    private static final double MAX_BODY_GLIDE_HEIGHT = 5.0D;
    private static final int DEFAULT_BODY_GLIDE_TICKS = 10;
    private static final int MIN_LOCAL_PLAYER_HIDE_TICKS = 0;
    private static final int MAX_LOCAL_PLAYER_HIDE_TICKS = 20;
    private static final int DEFAULT_LOCAL_PLAYER_HIDE_TICKS = 2;
    private static final double DEFAULT_MINECRAFT_SOUND_VOLUME = 1.0D;
    private static final double DEFAULT_CUSTOM_SOUND_VOLUME = 0.3D;
    private static final double MIN_SOUND_VOLUME = 0.1D;
    private static final double MAX_SOUND_VOLUME = 1.0D;

    private static Path configPath;
    private static boolean effectEnabled = true;
    private static boolean playerFreezeEnabled = true;
    private static boolean crossDimensionTravelEnabled = false;
    private static boolean zoomHeightsLinked = true;
    private static double[] zoomOutStageHeights = DEFAULT_STAGE_HEIGHTS.clone();
    private static double[] zoomInStageHeights = DEFAULT_STAGE_HEIGHTS.clone();
    private static boolean netherZoomHeightsLinked = true;
    private static double[] netherZoomOutStageHeights = DEFAULT_STAGE_HEIGHTS.clone();
    private static double[] netherZoomInStageHeights = DEFAULT_STAGE_HEIGHTS.clone();
    private static boolean endZoomHeightsLinked = true;
    private static double[] endZoomOutStageHeights = DEFAULT_STAGE_HEIGHTS.clone();
    private static double[] endZoomInStageHeights = DEFAULT_STAGE_HEIGHTS.clone();
    private static int[] zoomOutStageTicks = DEFAULT_STAGE_TICKS.clone();
    private static int[] zoomInStageTicks = DEFAULT_STAGE_TICKS.clone();
    private static double zoomStageGlideHeight = DEFAULT_ZOOM_STAGE_GLIDE_HEIGHT;
    private static int zoomStageGlideTicks = DEFAULT_ZOOM_STAGE_GLIDE_TICKS;
    private static double bodyCameraHeight = DEFAULT_BODY_CAMERA_HEIGHT;
    private static double bodyGlideHeight = DEFAULT_BODY_GLIDE_HEIGHT;
    private static int bodyGlideTicks = DEFAULT_BODY_GLIDE_TICKS;
    private static int localPlayerHideTicks = DEFAULT_LOCAL_PLAYER_HIDE_TICKS;
    private static boolean customSoundsEnabled = true;
    private static double minecraftSoundVolume = DEFAULT_MINECRAFT_SOUND_VOLUME;
    private static double customSoundVolume = DEFAULT_CUSTOM_SOUND_VOLUME;
    private static boolean warpPlateTransitionsEnabled = true;
    private static boolean externalTeleportTransitionsEnabled = true;
    private static boolean fallbackChunkFadeEnabled = false;
    private static boolean configLayoutEditorButtonVisible = false;
    private static boolean configLayoutDebugEnabled = false;
    private static boolean configLayoutAspectLocked = true;
    private static boolean configLayoutGridEnabled = true;
    private static boolean configLayoutSnapEnabled = true;
    private static boolean configLayoutCustom = false;
    private static String transitionPreset = "classic";
    private static String soundPack = "gta";
    private static boolean enableShutterFlash = false;
    private static boolean enableVignette = false;
    private static boolean enableInterferenceLines = false;
    private static String fadeColor = "black";
    private static boolean enableVanillaTp = true;
    private static boolean enableWaystones = true;
    private static boolean enableJourneyMap = true;
    private static boolean enablePortals = true;
    private static boolean enableSatelliteCameraFx = false;
    private static double satelliteGammaStrength = DEFAULT_SATELLITE_GAMMA_STRENGTH;
    private static double satelliteGammaDecayTicks = DEFAULT_SATELLITE_GAMMA_DECAY_TICKS;
    private static double satelliteGammaOverlayLift = DEFAULT_SATELLITE_GAMMA_OVERLAY_LIFT;
    private static double satelliteGammaBlowoutStrength = DEFAULT_SATELLITE_GAMMA_BLOWOUT_STRENGTH;
    private static double satelliteColorGradeMax = DEFAULT_SATELLITE_COLOR_GRADE_MAX;
    private static double satellitePlungeExposureMax = DEFAULT_SATELLITE_PLUNGE_EXPOSURE_MAX;
    private static double satelliteShaderExposureScale = DEFAULT_SATELLITE_SHADER_EXPOSURE_SCALE;
    private static double configLayoutX = 0.0D;
    private static double configLayoutY = 0.0D;
    private static double configLayoutWidth = 0.0D;
    private static double configLayoutHeight = 0.0D;
    private static int configLayoutBaseWidth = 0;
    private static int configLayoutBaseHeight = 0;
    private static final Map<String, double[]> configWidgetLayouts = new HashMap<>();
    private static final Map<String, String> configTexts = new HashMap<>();

    private GtaLikeTeleportConfig() {
    }

    public static void load() {
        configPath = resolveConfigPath();
        migrateLegacyConfig();

        if (!Files.exists(configPath)) {
            resetToDefaults();
            save();
            return;
        }

        boolean rewriteConfig = false;
        Properties properties = new Properties();
        try (InputStream input = Files.newInputStream(configPath)) {
            properties.load(input);
            int version = readConfigVersion(properties);
            if (version < CURRENT_CONFIG_VERSION) {
                if (version < 7 || needsLayoutCleanup(properties)) {
                    restoreDefaultLayoutProperties(properties);
                }
                applyConfigVersionMigration(properties, version);
                properties.setProperty(CONFIG_VERSION_KEY, Integer.toString(CURRENT_CONFIG_VERSION));
                rewriteConfig = true;
            }
            rewriteConfig = prepareLoadedProperties(properties) || rewriteConfig;
            applyConfigProperties(properties);
            applySoundPackState();
            rewriteConfig = rewriteConfig || !properties.containsKey(CONFIG_LAYOUT_EDITOR_BUTTON_VISIBLE_KEY);
        } catch (IOException exception) {
            GtaLikeTeleport.LOGGER.warn("Failed to load Grand Teleport config, using defaults.", exception);
            resetToDefaults();
        }
        if (rewriteConfig) {
            save();
        }
    }

    public static boolean isEffectEnabled() {
        return effectEnabled;
    }

    public static boolean setEffectEnabled(boolean enabled) {
        effectEnabled = enabled;
        return save();
    }

    public static String getTransitionPreset() {
        return transitionPreset;
    }

    public static boolean setTransitionPreset(String preset) {
        applyPreset(preset);
        return true;
    }

    public static String getSoundPack() {
        return soundPack;
    }

    public static boolean isTeleportSoundsEnabled() {
        return !SOUND_PACK_OFF.equals(soundPack);
    }

    public static boolean isGtaSoundPack() {
        return SOUND_PACK_GTA.equals(soundPack);
    }

    public static boolean isDefaultSoundPack() {
        return SOUND_PACK_DEFAULT.equals(soundPack);
    }

    public static boolean setSoundPack(String pack) {
        soundPack = sanitizeSoundPack(pack);
        customSoundsEnabled = isTeleportSoundsEnabled();
        return save();
    }

    private static String sanitizeSoundPack(String pack) {
        if (pack != null) {
            pack = pack.trim();
        }
        if (SOUND_PACK_GTA.equals(pack) || SOUND_PACK_DEFAULT.equals(pack) || SOUND_PACK_OFF.equals(pack)) {
            return pack;
        }
        return SOUND_PACK_GTA;
    }

    private static void applySoundPackState() {
        soundPack = sanitizeSoundPack(soundPack);
        customSoundsEnabled = isTeleportSoundsEnabled();
    }

    public static boolean isShutterFlashEnabled() {
        return enableShutterFlash;
    }

    public static boolean setShutterFlashEnabled(boolean enabled) {
        enableShutterFlash = enabled;
        return save();
    }

    public static boolean isVignetteEnabled() {
        return enableVignette;
    }

    public static boolean setVignetteEnabled(boolean enabled) {
        enableVignette = enabled;
        return save();
    }

    public static boolean isInterferenceLinesEnabled() {
        return enableInterferenceLines;
    }

    public static boolean setInterferenceLinesEnabled(boolean enabled) {
        enableInterferenceLines = enabled;
        return save();
    }

    public static String getFadeColor() {
        return fadeColor;
    }

    public static boolean setFadeColor(String color) {
        fadeColor = color;
        return save();
    }

    public static boolean isVanillaTpEnabled() {
        return enableVanillaTp;
    }

    public static boolean setVanillaTpEnabled(boolean enabled) {
        enableVanillaTp = enabled;
        return save();
    }

    public static boolean isWaystonesEnabled() {
        return enableWaystones;
    }

    public static boolean setWaystonesEnabled(boolean enabled) {
        enableWaystones = enabled;
        return save();
    }

    public static boolean isJourneyMapEnabled() {
        return enableJourneyMap;
    }

    public static boolean setJourneyMapEnabled(boolean enabled) {
        enableJourneyMap = enabled;
        return save();
    }

    public static boolean isPortalsEnabled() {
        return enablePortals;
    }

    public static boolean setPortalsEnabled(boolean enabled) {
        enablePortals = enabled;
        return save();
    }

    public static boolean isSatelliteCameraFxEnabled() {
        return enableSatelliteCameraFx;
    }

    public static boolean setSatelliteCameraFxEnabled(boolean enabled) {
        enableSatelliteCameraFx = enabled;
        return save();
    }

    public static double getSatelliteGammaStrength() {
        return satelliteGammaStrength;
    }

    public static boolean setSatelliteGammaStrength(double value) {
        satelliteGammaStrength = clamp(value, MIN_SATELLITE_GAMMA_STRENGTH, MAX_SATELLITE_GAMMA_STRENGTH);
        return save();
    }

    public static double getSatelliteGammaDecayTicks() {
        return satelliteGammaDecayTicks;
    }

    public static boolean setSatelliteGammaDecayTicks(double value) {
        satelliteGammaDecayTicks = clamp(value, MIN_SATELLITE_GAMMA_DECAY_TICKS, MAX_SATELLITE_GAMMA_DECAY_TICKS);
        return save();
    }

    public static double getSatelliteGammaOverlayLift() {
        return satelliteGammaOverlayLift;
    }

    public static boolean setSatelliteGammaOverlayLift(double value) {
        satelliteGammaOverlayLift = clamp(value, MIN_SATELLITE_UNIT, MAX_SATELLITE_UNIT);
        return save();
    }

    public static double getSatelliteGammaBlowoutStrength() {
        return satelliteGammaBlowoutStrength;
    }

    public static boolean setSatelliteGammaBlowoutStrength(double value) {
        satelliteGammaBlowoutStrength = clamp(value, MIN_SATELLITE_UNIT, MAX_SATELLITE_UNIT);
        return save();
    }

    public static double getSatelliteColorGradeMax() {
        return satelliteColorGradeMax;
    }

    public static boolean setSatelliteColorGradeMax(double value) {
        satelliteColorGradeMax = clamp(value, MIN_SATELLITE_UNIT, MAX_SATELLITE_UNIT);
        return save();
    }

    public static double getSatellitePlungeExposureMax() {
        return satellitePlungeExposureMax;
    }

    public static boolean setSatellitePlungeExposureMax(double value) {
        satellitePlungeExposureMax = clamp(value, MIN_SATELLITE_UNIT, MAX_SATELLITE_UNIT);
        return save();
    }

    public static double getSatelliteShaderExposureScale() {
        return satelliteShaderExposureScale;
    }

    public static boolean setSatelliteShaderExposureScale(double value) {
        satelliteShaderExposureScale = clamp(value, MIN_SATELLITE_SHADER_SCALE, MAX_SATELLITE_SHADER_SCALE);
        return save();
    }

    public static double getMinSatelliteGammaStrength() {
        return MIN_SATELLITE_GAMMA_STRENGTH;
    }

    public static double getMaxSatelliteGammaStrength() {
        return MAX_SATELLITE_GAMMA_STRENGTH;
    }

    public static double getMinSatelliteGammaDecayTicks() {
        return MIN_SATELLITE_GAMMA_DECAY_TICKS;
    }

    public static double getMaxSatelliteGammaDecayTicks() {
        return MAX_SATELLITE_GAMMA_DECAY_TICKS;
    }

    public static void resetSatelliteCameraSettings() {
        Properties defaults = createDefaultProperties();
        enableSatelliteCameraFx = Boolean.parseBoolean(defaults.getProperty(
                ENABLE_SATELLITE_CAMERA_FX_KEY,
                Boolean.toString(enableSatelliteCameraFx)
        ));
        satelliteGammaStrength = readClampedDouble(
                defaults,
                SATELLITE_GAMMA_STRENGTH_KEY,
                satelliteGammaStrength,
                MIN_SATELLITE_GAMMA_STRENGTH,
                MAX_SATELLITE_GAMMA_STRENGTH
        );
        satelliteGammaDecayTicks = readClampedDouble(
                defaults,
                SATELLITE_GAMMA_DECAY_TICKS_KEY,
                satelliteGammaDecayTicks,
                MIN_SATELLITE_GAMMA_DECAY_TICKS,
                MAX_SATELLITE_GAMMA_DECAY_TICKS
        );
        satelliteGammaOverlayLift = readClampedDouble(
                defaults,
                SATELLITE_GAMMA_OVERLAY_LIFT_KEY,
                satelliteGammaOverlayLift,
                MIN_SATELLITE_UNIT,
                MAX_SATELLITE_UNIT
        );
        satelliteGammaBlowoutStrength = readClampedDouble(
                defaults,
                SATELLITE_GAMMA_BLOWOUT_STRENGTH_KEY,
                satelliteGammaBlowoutStrength,
                MIN_SATELLITE_UNIT,
                MAX_SATELLITE_UNIT
        );
        satelliteColorGradeMax = readClampedDouble(
                defaults,
                SATELLITE_COLOR_GRADE_MAX_KEY,
                satelliteColorGradeMax,
                MIN_SATELLITE_UNIT,
                MAX_SATELLITE_UNIT
        );
        satellitePlungeExposureMax = readClampedDouble(
                defaults,
                SATELLITE_PLUNGE_EXPOSURE_MAX_KEY,
                satellitePlungeExposureMax,
                MIN_SATELLITE_UNIT,
                MAX_SATELLITE_UNIT
        );
        satelliteShaderExposureScale = readClampedDouble(
                defaults,
                SATELLITE_SHADER_EXPOSURE_SCALE_KEY,
                satelliteShaderExposureScale,
                MIN_SATELLITE_SHADER_SCALE,
                MAX_SATELLITE_SHADER_SCALE
        );
        save();
    }

    public static void applyPreset(String preset) {
        transitionPreset = preset;
        if ("fast".equals(preset)) {
            zoomOutStageTicks = new int[]{5, 5, 5};
            zoomInStageTicks = new int[]{5, 5, 5};
            zoomStageGlideTicks = 5;
            bodyGlideTicks = 5;
            zoomOutStageHeights = new double[]{20, 50, 100};
            zoomInStageHeights = new double[]{20, 50, 100};
            netherZoomOutStageHeights = new double[]{20, 50, 100};
            netherZoomInStageHeights = new double[]{20, 50, 100};
            endZoomOutStageHeights = new double[]{20, 50, 100};
            endZoomInStageHeights = new double[]{20, 50, 100};
        } else if ("slow".equals(preset)) {
            zoomOutStageTicks = new int[]{25, 25, 25};
            zoomInStageTicks = new int[]{25, 25, 25};
            zoomStageGlideTicks = 30;
            bodyGlideTicks = 20;
            zoomOutStageHeights = new double[]{50, 120, 250};
            zoomInStageHeights = new double[]{50, 120, 250};
            netherZoomOutStageHeights = new double[]{40, 80, 120};
            netherZoomInStageHeights = new double[]{40, 80, 120};
            endZoomOutStageHeights = new double[]{50, 120, 250};
            endZoomInStageHeights = new double[]{50, 120, 250};
        } else {
            zoomOutStageTicks = DEFAULT_STAGE_TICKS.clone();
            zoomInStageTicks = DEFAULT_STAGE_TICKS.clone();
            zoomStageGlideTicks = DEFAULT_ZOOM_STAGE_GLIDE_TICKS;
            bodyGlideTicks = DEFAULT_BODY_GLIDE_TICKS;
            zoomOutStageHeights = DEFAULT_STAGE_HEIGHTS.clone();
            zoomInStageHeights = DEFAULT_STAGE_HEIGHTS.clone();
            netherZoomOutStageHeights = DEFAULT_STAGE_HEIGHTS.clone();
            netherZoomInStageHeights = DEFAULT_STAGE_HEIGHTS.clone();
            endZoomOutStageHeights = DEFAULT_STAGE_HEIGHTS.clone();
            endZoomInStageHeights = DEFAULT_STAGE_HEIGHTS.clone();
        }
        save();
    }

    public static boolean isPlayerFreezeEnabled() {
        return playerFreezeEnabled;
    }

    public static boolean setPlayerFreezeEnabled(boolean enabled) {
        playerFreezeEnabled = enabled;
        return save();
    }

    static boolean isCrossDimensionTravelEnabled() {
        return crossDimensionTravelEnabled;
    }

    static boolean setCrossDimensionTravelEnabled(boolean enabled) {
        crossDimensionTravelEnabled = enabled;
        return save();
    }

    static boolean areZoomHeightsLinked() {
        return areZoomHeightsLinked(ZoomDimension.OVERWORLD);
    }

    static boolean areZoomHeightsLinked(ZoomDimension dimension) {
        return switch (sanitizeZoomDimension(dimension)) {
            case NETHER -> netherZoomHeightsLinked;
            case END -> endZoomHeightsLinked;
            default -> zoomHeightsLinked;
        };
    }

    static double[] getZoomOutStageHeights() {
        return getZoomOutStageHeights(ZoomDimension.OVERWORLD);
    }

    static double[] getZoomOutStageHeights(ZoomDimension dimension) {
        return switch (sanitizeZoomDimension(dimension)) {
            case NETHER -> netherZoomOutStageHeights.clone();
            case END -> endZoomOutStageHeights.clone();
            default -> zoomOutStageHeights.clone();
        };
    }

    static double[] getZoomInStageHeights() {
        return getZoomInStageHeights(ZoomDimension.OVERWORLD);
    }

    static double[] getZoomInStageHeights(ZoomDimension dimension) {
        ZoomDimension safeDimension = sanitizeZoomDimension(dimension);
        return areZoomHeightsLinked(safeDimension) ? getZoomOutStageHeights(safeDimension) : getRawZoomInStageHeights(safeDimension);
    }

    static double[] getRawZoomInStageHeights() {
        return getRawZoomInStageHeights(ZoomDimension.OVERWORLD);
    }

    static double[] getRawZoomInStageHeights(ZoomDimension dimension) {
        return switch (sanitizeZoomDimension(dimension)) {
            case NETHER -> netherZoomInStageHeights.clone();
            case END -> endZoomInStageHeights.clone();
            default -> zoomInStageHeights.clone();
        };
    }

    static boolean setZoomStageHeights(boolean linked, double[] zoomOutHeights, double[] zoomInHeights) {
        return setZoomStageHeights(ZoomDimension.OVERWORLD, linked, zoomOutHeights, zoomInHeights);
    }

    static boolean setZoomStageHeights(ZoomDimension dimension, boolean linked, double[] zoomOutHeights, double[] zoomInHeights) {
        double[] sanitizedOut = sanitizeStageHeights(zoomOutHeights);
        double[] sanitizedIn = sanitizeStageHeights(linked ? sanitizedOut : zoomInHeights);
        switch (sanitizeZoomDimension(dimension)) {
            case NETHER -> {
                netherZoomHeightsLinked = linked;
                netherZoomOutStageHeights = sanitizedOut;
                netherZoomInStageHeights = sanitizedIn;
            }
            case END -> {
                endZoomHeightsLinked = linked;
                endZoomOutStageHeights = sanitizedOut;
                endZoomInStageHeights = sanitizedIn;
            }
            default -> {
                zoomHeightsLinked = linked;
                zoomOutStageHeights = sanitizedOut;
                zoomInStageHeights = sanitizedIn;
            }
        }
        return save();
    }

    static int[] getZoomOutStageTicks() {
        return zoomOutStageTicks.clone();
    }

    static int[] getZoomInStageTicks() {
        return zoomInStageTicks.clone();
    }

    static boolean setZoomStageTicks(int[] zoomOutTicks, int[] zoomInTicks) {
        zoomOutStageTicks = sanitizeStageTicks(zoomOutTicks);
        zoomInStageTicks = sanitizeStageTicks(zoomInTicks);
        return save();
    }

    static double getZoomStageGlideHeight() {
        return zoomStageGlideHeight;
    }

    static boolean setZoomStageGlideHeight(double height) {
        zoomStageGlideHeight = sanitizeZoomStageGlideHeight(height);
        return save();
    }

    static int getZoomStageGlideTicks() {
        return zoomStageGlideTicks;
    }

    static boolean setZoomStageGlideTicks(int ticks) {
        zoomStageGlideTicks = sanitizeStageTicksValue(ticks);
        return save();
    }

    static double getBodyCameraHeight() {
        return bodyCameraHeight;
    }

    static boolean setBodyCameraHeight(double height) {
        bodyCameraHeight = sanitizeBodyCameraHeight(height);
        return save();
    }

    static double getBodyGlideHeight() {
        return bodyGlideHeight;
    }

    static boolean setBodyGlideHeight(double height) {
        bodyGlideHeight = sanitizeBodyGlideHeight(height);
        return save();
    }

    static int getBodyGlideTicks() {
        return bodyGlideTicks;
    }

    static boolean setBodyGlideTicks(int ticks) {
        bodyGlideTicks = sanitizeStageTicksValue(ticks);
        return save();
    }

    static int getLocalPlayerHideTicks() {
        return localPlayerHideTicks;
    }

    static boolean setLocalPlayerHideTicks(int ticks) {
        localPlayerHideTicks = sanitizeLocalPlayerHideTicks(ticks);
        return save();
    }

    static boolean isCustomSoundsEnabled() {
        return customSoundsEnabled;
    }

    static boolean setCustomSoundsEnabled(boolean enabled) {
        if (!enabled) {
            soundPack = SOUND_PACK_OFF;
        } else if (SOUND_PACK_OFF.equals(soundPack)) {
            soundPack = SOUND_PACK_GTA;
        }
        customSoundsEnabled = isTeleportSoundsEnabled();
        return save();
    }

    static double getMinecraftSoundVolume() {
        return minecraftSoundVolume;
    }

    static boolean setMinecraftSoundVolume(double volume) {
        minecraftSoundVolume = sanitizeSoundVolume(volume);
        return save();
    }

    static double getCustomSoundVolume() {
        return customSoundVolume;
    }

    static boolean setCustomSoundVolume(double volume) {
        customSoundVolume = sanitizeSoundVolume(volume);
        return save();
    }

    public static boolean isWarpPlateTransitionsEnabled() {
        return warpPlateTransitionsEnabled;
    }

    static boolean setWarpPlateTransitionsEnabled(boolean enabled) {
        warpPlateTransitionsEnabled = enabled;
        return save();
    }

    public static boolean isExternalTeleportTransitionsEnabled() {
        return externalTeleportTransitionsEnabled;
    }

    static boolean setExternalTeleportTransitionsEnabled(boolean enabled) {
        externalTeleportTransitionsEnabled = enabled;
        return save();
    }

    static boolean isFallbackChunkFadeEnabled() {
        return fallbackChunkFadeEnabled;
    }

    static boolean setFallbackChunkFadeEnabled(boolean enabled) {
        fallbackChunkFadeEnabled = enabled;
        return save();
    }

    public static double[] sanitizeStageHeights(double[] values) {
        double[] source = values == null || values.length < 3 ? DEFAULT_STAGE_HEIGHTS : values;
        double[] sanitized = new double[3];
        sanitized[0] = clamp(roundStageHeight(source[0]), MIN_STAGE_HEIGHT, MAX_STAGE_HEIGHT - MIN_STAGE_GAP * 2.0D);
        sanitized[1] = clamp(roundStageHeight(source[1]), sanitized[0] + MIN_STAGE_GAP, MAX_STAGE_HEIGHT - MIN_STAGE_GAP);
        sanitized[2] = clamp(roundStageHeight(source[2]), sanitized[1] + MIN_STAGE_GAP, MAX_STAGE_HEIGHT);
        return sanitized;
    }

    static int[] sanitizeStageTicks(int[] values) {
        int[] source = values == null || values.length < 3 ? DEFAULT_STAGE_TICKS : values;
        int[] sanitized = new int[3];
        for (int i = 0; i < sanitized.length; i++) {
            sanitized[i] = sanitizeStageTicksValue(source[i]);
        }
        return sanitized;
    }

    static double sanitizeZoomStageGlideHeight(double value) {
        return Math.round(clamp(value, MIN_ZOOM_STAGE_GLIDE_HEIGHT, MAX_ZOOM_STAGE_GLIDE_HEIGHT) * 10.0D) / 10.0D;
    }

    static double sanitizeBodyCameraHeight(double value) {
        return Math.round(clamp(value, MIN_BODY_CAMERA_HEIGHT, MAX_BODY_CAMERA_HEIGHT) * 10.0D) / 10.0D;
    }

    static double sanitizeBodyGlideHeight(double value) {
        return Math.round(clamp(value, MIN_BODY_GLIDE_HEIGHT, MAX_BODY_GLIDE_HEIGHT) * 10.0D) / 10.0D;
    }

    static int sanitizeStageTicksValue(int value) {
        return clamp(value, MIN_STAGE_TICKS, MAX_STAGE_TICKS);
    }

    static int sanitizeLocalPlayerHideTicks(int value) {
        return clamp(value, MIN_LOCAL_PLAYER_HIDE_TICKS, MAX_LOCAL_PLAYER_HIDE_TICKS);
    }

    static double sanitizeSoundVolume(double value) {
        return Math.round(clamp(value, MIN_SOUND_VOLUME, MAX_SOUND_VOLUME) * 10.0D) / 10.0D;
    }

    public static double getMinStageHeight() {
        return MIN_STAGE_HEIGHT;
    }

    public static double getMaxStageHeight() {
        return MAX_STAGE_HEIGHT;
    }

    public static double getMinStageGap() {
        return MIN_STAGE_GAP;
    }

    static double[] getDefaultStageHeights() {
        return DEFAULT_STAGE_HEIGHTS.clone();
    }

    static int[] getDefaultStageTicks() {
        return DEFAULT_STAGE_TICKS.clone();
    }

    static int getMinStageTicks() {
        return MIN_STAGE_TICKS;
    }

    static int getMaxStageTicks() {
        return MAX_STAGE_TICKS;
    }

    static double getDefaultZoomStageGlideHeight() {
        return DEFAULT_ZOOM_STAGE_GLIDE_HEIGHT;
    }

    static double getMinZoomStageGlideHeight() {
        return MIN_ZOOM_STAGE_GLIDE_HEIGHT;
    }

    static double getMaxZoomStageGlideHeight() {
        return MAX_ZOOM_STAGE_GLIDE_HEIGHT;
    }

    static int getDefaultZoomStageGlideTicks() {
        return DEFAULT_ZOOM_STAGE_GLIDE_TICKS;
    }

    static double getDefaultBodyCameraHeight() {
        return DEFAULT_BODY_CAMERA_HEIGHT;
    }

    static double getMinBodyCameraHeight() {
        return MIN_BODY_CAMERA_HEIGHT;
    }

    static double getMaxBodyCameraHeight() {
        return MAX_BODY_CAMERA_HEIGHT;
    }

    static double getDefaultBodyGlideHeight() {
        return DEFAULT_BODY_GLIDE_HEIGHT;
    }

    static double getMinBodyGlideHeight() {
        return MIN_BODY_GLIDE_HEIGHT;
    }

    static double getMaxBodyGlideHeight() {
        return MAX_BODY_GLIDE_HEIGHT;
    }

    static int getDefaultBodyGlideTicks() {
        return DEFAULT_BODY_GLIDE_TICKS;
    }

    static int getDefaultLocalPlayerHideTicks() {
        return DEFAULT_LOCAL_PLAYER_HIDE_TICKS;
    }

    static int getMinLocalPlayerHideTicks() {
        return MIN_LOCAL_PLAYER_HIDE_TICKS;
    }

    static int getMaxLocalPlayerHideTicks() {
        return MAX_LOCAL_PLAYER_HIDE_TICKS;
    }

    static double getDefaultMinecraftSoundVolume() {
        return DEFAULT_MINECRAFT_SOUND_VOLUME;
    }

    static double getDefaultCustomSoundVolume() {
        return DEFAULT_CUSTOM_SOUND_VOLUME;
    }

    static double getMinSoundVolume() {
        return MIN_SOUND_VOLUME;
    }

    static double getMaxSoundVolume() {
        return MAX_SOUND_VOLUME;
    }

    static boolean isConfigLayoutEditorButtonVisible() {
        return configLayoutEditorButtonVisible;
    }

    static boolean setConfigLayoutEditorButtonVisible(boolean visible) {
        configLayoutEditorButtonVisible = visible;
        return save();
    }

    static boolean isConfigLayoutDebugEnabled() {
        return configLayoutDebugEnabled;
    }

    static boolean setConfigLayoutDebugEnabled(boolean enabled) {
        configLayoutDebugEnabled = enabled;
        return save();
    }

    static boolean isConfigLayoutAspectLocked() {
        return configLayoutAspectLocked;
    }

    static boolean setConfigLayoutAspectLocked(boolean locked) {
        configLayoutAspectLocked = locked;
        return save();
    }

    static boolean isConfigLayoutGridEnabled() {
        return configLayoutGridEnabled;
    }

    static boolean setConfigLayoutGridEnabled(boolean enabled) {
        configLayoutGridEnabled = enabled;
        return save();
    }

    static boolean isConfigLayoutSnapEnabled() {
        return configLayoutSnapEnabled;
    }

    static boolean setConfigLayoutSnapEnabled(boolean enabled) {
        configLayoutSnapEnabled = enabled;
        return save();
    }

    static boolean hasCustomConfigLayout() {
        return configLayoutCustom;
    }

    static double[] getConfigLayout() {
        return new double[]{configLayoutX, configLayoutY, configLayoutWidth, configLayoutHeight};
    }

    static int getConfigLayoutBaseWidth() {
        return configLayoutBaseWidth;
    }

    static int getConfigLayoutBaseHeight() {
        return configLayoutBaseHeight;
    }

    static boolean setConfigLayout(double x, double y, double width, double height) {
        return setConfigLayout(x, y, width, height, configLayoutBaseWidth, configLayoutBaseHeight);
    }

    static boolean setConfigLayout(double x, double y, double width, double height, int baseWidth, int baseHeight) {
        configLayoutCustom = true;
        configLayoutX = clamp(x, 0.0D, 1.0D);
        configLayoutY = clamp(y, 0.0D, 1.0D);
        configLayoutWidth = clamp(width, 0.0D, 1.0D);
        configLayoutHeight = clamp(height, 0.0D, 1.0D);
        configLayoutBaseWidth = Math.max(1, baseWidth);
        configLayoutBaseHeight = Math.max(1, baseHeight);
        return save();
    }

    static boolean resetConfigLayout() {
        Properties defaults = createDefaultProperties();
        configLayoutCustom = Boolean.parseBoolean(defaults.getProperty(
                CONFIG_LAYOUT_CUSTOM_KEY,
                Boolean.toString(configLayoutCustom)
        ));
        configLayoutX = readUnitDouble(defaults, CONFIG_LAYOUT_X_KEY, configLayoutX);
        configLayoutY = readUnitDouble(defaults, CONFIG_LAYOUT_Y_KEY, configLayoutY);
        configLayoutWidth = readUnitDouble(defaults, CONFIG_LAYOUT_WIDTH_KEY, configLayoutWidth);
        configLayoutHeight = readUnitDouble(defaults, CONFIG_LAYOUT_HEIGHT_KEY, configLayoutHeight);
        configLayoutBaseWidth = readPositiveInt(defaults, CONFIG_LAYOUT_BASE_WIDTH_KEY, configLayoutBaseWidth);
        configLayoutBaseHeight = readPositiveInt(defaults, CONFIG_LAYOUT_BASE_HEIGHT_KEY, configLayoutBaseHeight);
        return save();
    }

    static boolean hasConfigWidgetLayout(String id) {
        return configWidgetLayouts.containsKey(id);
    }

    static double[] getConfigWidgetLayout(String id) {
        double[] values = configWidgetLayouts.get(id);
        return values == null ? new double[]{0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D} : values.clone();
    }

    static boolean setConfigWidgetLayout(String id, double x, double y, double width, double height) {
        return setConfigWidgetLayout(id, x, y, width, height, 0, 0);
    }

    static boolean setConfigWidgetLayout(String id, double x, double y, double width, double height, int baseWidth, int baseHeight) {
        if (!isSafeId(id)) {
            return false;
        }
        configWidgetLayouts.put(id, new double[]{
                clamp(x, -2.0D, 3.0D),
                clamp(y, -2.0D, 3.0D),
                clamp(width, 0.01D, 3.0D),
                clamp(height, 0.01D, 3.0D),
                Math.max(0, baseWidth),
                Math.max(0, baseHeight)
        });
        return save();
    }
    static boolean resetConfigWidgetLayout(String id) {
        if (!isSafeId(id)) {
            return false;
        }
        Properties defaults = createDefaultProperties();
        String prefix = CONFIG_WIDGET_PREFIX + id;
        double x = readDouble(defaults, prefix + ".x", 0.0D);
        double y = readDouble(defaults, prefix + ".y", 0.0D);
        double width = readDouble(defaults, prefix + ".width", 0.0D);
        double height = readDouble(defaults, prefix + ".height", 0.0D);
        int baseWidth = readPositiveInt(defaults, prefix + ".baseWidth", 0);
        int baseHeight = readPositiveInt(defaults, prefix + ".baseHeight", 0);
        if (width > 0.0D && height > 0.0D) {
            configWidgetLayouts.put(id, new double[]{
                    clamp(x, -2.0D, 3.0D),
                    clamp(y, -2.0D, 3.0D),
                    clamp(width, 0.01D, 3.0D),
                    clamp(height, 0.01D, 3.0D),
                    baseWidth,
                    baseHeight
            });
        } else {
            configWidgetLayouts.remove(id);
        }
        return save();
    }

    static boolean resetConfigWidgetLayouts() {
        readWidgetLayouts(createDefaultProperties());
        return save();
    }

    static String getConfigText(String id, String fallback) {
        String value = configTexts.get(id);
        return value == null ? fallback : value;
    }

    static boolean setConfigText(String id, String text) {
        if (!isSafeId(id)) {
            return false;
        }
        if (text == null || text.isEmpty()) {
            configTexts.remove(id);
        } else {
            configTexts.put(id, text);
        }
        return save();
    }

    static boolean resetConfigText(String id) {
        if (!isSafeId(id)) {
            return false;
        }
        String value = createDefaultProperties().getProperty(CONFIG_TEXT_PREFIX + id);
        if (value == null || value.isEmpty()) {
            configTexts.remove(id);
        } else {
            configTexts.put(id, value);
        }
        return save();
    }

    private static void applyConfigProperties(Properties properties) {
        effectEnabled = Boolean.parseBoolean(properties.getProperty(EFFECT_ENABLED_KEY, Boolean.toString(effectEnabled)));
        playerFreezeEnabled = Boolean.parseBoolean(properties.getProperty(
                PLAYER_FREEZE_ENABLED_KEY,
                Boolean.toString(playerFreezeEnabled)
        ));
        crossDimensionTravelEnabled = Boolean.parseBoolean(properties.getProperty(
                CROSS_DIMENSION_TRAVEL_ENABLED_KEY,
                Boolean.toString(crossDimensionTravelEnabled)
        ));
        zoomHeightsLinked = Boolean.parseBoolean(properties.getProperty(
                ZOOM_HEIGHTS_LINKED_KEY,
                Boolean.toString(zoomHeightsLinked)
        ));
        zoomOutStageHeights = readStageHeights(properties, ZOOM_OUT_STAGE_KEY_PREFIX, DEFAULT_STAGE_HEIGHTS);
        zoomInStageHeights = readStageHeights(properties, ZOOM_IN_STAGE_KEY_PREFIX, DEFAULT_STAGE_HEIGHTS);
        if (zoomHeightsLinked) {
            zoomInStageHeights = zoomOutStageHeights.clone();
        }
        netherZoomHeightsLinked = Boolean.parseBoolean(properties.getProperty(
                NETHER_ZOOM_HEIGHTS_LINKED_KEY,
                Boolean.toString(zoomHeightsLinked)
        ));
        netherZoomOutStageHeights = readStageHeights(properties, NETHER_ZOOM_OUT_STAGE_KEY_PREFIX, zoomOutStageHeights);
        netherZoomInStageHeights = readStageHeights(properties, NETHER_ZOOM_IN_STAGE_KEY_PREFIX, zoomInStageHeights);
        if (netherZoomHeightsLinked) {
            netherZoomInStageHeights = netherZoomOutStageHeights.clone();
        }
        endZoomHeightsLinked = Boolean.parseBoolean(properties.getProperty(
                END_ZOOM_HEIGHTS_LINKED_KEY,
                Boolean.toString(zoomHeightsLinked)
        ));
        endZoomOutStageHeights = readStageHeights(properties, END_ZOOM_OUT_STAGE_KEY_PREFIX, zoomOutStageHeights);
        endZoomInStageHeights = readStageHeights(properties, END_ZOOM_IN_STAGE_KEY_PREFIX, zoomInStageHeights);
        if (endZoomHeightsLinked) {
            endZoomInStageHeights = endZoomOutStageHeights.clone();
        }
        zoomOutStageTicks = readStageTicks(properties, ZOOM_OUT_STAGE_TICKS_KEY_PREFIX, DEFAULT_STAGE_TICKS);
        zoomInStageTicks = readStageTicks(properties, ZOOM_IN_STAGE_TICKS_KEY_PREFIX, DEFAULT_STAGE_TICKS);
        zoomStageGlideHeight = readClampedDouble(properties, ZOOM_STAGE_GLIDE_HEIGHT_KEY, zoomStageGlideHeight, MIN_ZOOM_STAGE_GLIDE_HEIGHT, MAX_ZOOM_STAGE_GLIDE_HEIGHT);
        zoomStageGlideTicks = readClampedInt(properties, ZOOM_STAGE_GLIDE_TICKS_KEY, zoomStageGlideTicks, MIN_STAGE_TICKS, MAX_STAGE_TICKS);
        bodyCameraHeight = readClampedDouble(properties, BODY_CAMERA_HEIGHT_KEY, bodyCameraHeight, MIN_BODY_CAMERA_HEIGHT, MAX_BODY_CAMERA_HEIGHT);
        bodyGlideHeight = readClampedDouble(properties, BODY_GLIDE_HEIGHT_KEY, bodyGlideHeight, MIN_BODY_GLIDE_HEIGHT, MAX_BODY_GLIDE_HEIGHT);
        bodyGlideTicks = readClampedInt(properties, BODY_GLIDE_TICKS_KEY, bodyGlideTicks, MIN_STAGE_TICKS, MAX_STAGE_TICKS);
        localPlayerHideTicks = readClampedInt(properties, LOCAL_PLAYER_HIDE_TICKS_KEY, localPlayerHideTicks, MIN_LOCAL_PLAYER_HIDE_TICKS, MAX_LOCAL_PLAYER_HIDE_TICKS);
        customSoundsEnabled = Boolean.parseBoolean(properties.getProperty(
                CUSTOM_SOUNDS_ENABLED_KEY,
                Boolean.toString(customSoundsEnabled)
        ));
        minecraftSoundVolume = readClampedDouble(properties, MINECRAFT_SOUND_VOLUME_KEY, minecraftSoundVolume, MIN_SOUND_VOLUME, MAX_SOUND_VOLUME);
        customSoundVolume = readClampedDouble(properties, CUSTOM_SOUND_VOLUME_KEY, customSoundVolume, MIN_SOUND_VOLUME, MAX_SOUND_VOLUME);
        warpPlateTransitionsEnabled = Boolean.parseBoolean(properties.getProperty(
                WARP_PLATE_TRANSITIONS_ENABLED_KEY,
                Boolean.toString(warpPlateTransitionsEnabled)
        ));
        externalTeleportTransitionsEnabled = Boolean.parseBoolean(properties.getProperty(
                EXTERNAL_TELEPORT_TRANSITIONS_ENABLED_KEY,
                Boolean.toString(externalTeleportTransitionsEnabled)
        ));
        fallbackChunkFadeEnabled = Boolean.parseBoolean(properties.getProperty(
                FALLBACK_CHUNK_FADE_ENABLED_KEY,
                Boolean.toString(fallbackChunkFadeEnabled)
        ));
        configLayoutEditorButtonVisible = Boolean.parseBoolean(properties.getProperty(
                CONFIG_LAYOUT_EDITOR_BUTTON_VISIBLE_KEY,
                Boolean.toString(configLayoutEditorButtonVisible)
        ));
        configLayoutDebugEnabled = Boolean.parseBoolean(properties.getProperty(
                CONFIG_LAYOUT_DEBUG_ENABLED_KEY,
                Boolean.toString(configLayoutDebugEnabled)
        ));
        configLayoutAspectLocked = Boolean.parseBoolean(properties.getProperty(
                CONFIG_LAYOUT_ASPECT_LOCKED_KEY,
                Boolean.toString(configLayoutAspectLocked)
        ));
        configLayoutGridEnabled = Boolean.parseBoolean(properties.getProperty(
                CONFIG_LAYOUT_GRID_ENABLED_KEY,
                Boolean.toString(configLayoutGridEnabled)
        ));
        configLayoutSnapEnabled = Boolean.parseBoolean(properties.getProperty(
                CONFIG_LAYOUT_SNAP_ENABLED_KEY,
                Boolean.toString(configLayoutSnapEnabled)
        ));
        configLayoutCustom = Boolean.parseBoolean(properties.getProperty(
                CONFIG_LAYOUT_CUSTOM_KEY,
                Boolean.toString(configLayoutCustom)
        ));
        configLayoutX = readUnitDouble(properties, CONFIG_LAYOUT_X_KEY, configLayoutX);
        configLayoutY = readUnitDouble(properties, CONFIG_LAYOUT_Y_KEY, configLayoutY);
        configLayoutWidth = readUnitDouble(properties, CONFIG_LAYOUT_WIDTH_KEY, configLayoutWidth);
        configLayoutHeight = readUnitDouble(properties, CONFIG_LAYOUT_HEIGHT_KEY, configLayoutHeight);
        configLayoutBaseWidth = readPositiveInt(properties, CONFIG_LAYOUT_BASE_WIDTH_KEY, configLayoutBaseWidth);
        configLayoutBaseHeight = readPositiveInt(properties, CONFIG_LAYOUT_BASE_HEIGHT_KEY, configLayoutBaseHeight);
        transitionPreset = properties.getProperty(TRANSITION_PRESET_KEY, transitionPreset);
        soundPack = sanitizeSoundPack(properties.getProperty(SOUND_PACK_KEY, soundPack));
        applySoundPackState();
        enableShutterFlash = Boolean.parseBoolean(properties.getProperty(ENABLE_SHUTTER_FLASH_KEY, Boolean.toString(enableShutterFlash)));
        enableVignette = Boolean.parseBoolean(properties.getProperty(ENABLE_VIGNETTE_KEY, Boolean.toString(enableVignette)));
        enableInterferenceLines = Boolean.parseBoolean(properties.getProperty(ENABLE_INTERFERENCE_LINES_KEY, Boolean.toString(enableInterferenceLines)));
        fadeColor = properties.getProperty(FADE_COLOR_KEY, fadeColor);
        enableVanillaTp = Boolean.parseBoolean(properties.getProperty(ENABLE_VANILLA_TP_KEY, Boolean.toString(enableVanillaTp)));
        enableWaystones = Boolean.parseBoolean(properties.getProperty(ENABLE_WAYSTONES_KEY, Boolean.toString(enableWaystones)));
        enableJourneyMap = Boolean.parseBoolean(properties.getProperty(ENABLE_JOURNEYMAP_KEY, Boolean.toString(enableJourneyMap)));
        enablePortals = Boolean.parseBoolean(properties.getProperty(ENABLE_PORTALS_KEY, Boolean.toString(enablePortals)));
        enableSatelliteCameraFx = Boolean.parseBoolean(properties.getProperty(
                ENABLE_SATELLITE_CAMERA_FX_KEY,
                Boolean.toString(enableSatelliteCameraFx)
        ));
        satelliteGammaStrength = readClampedDouble(
                properties,
                SATELLITE_GAMMA_STRENGTH_KEY,
                satelliteGammaStrength,
                MIN_SATELLITE_GAMMA_STRENGTH,
                MAX_SATELLITE_GAMMA_STRENGTH
        );
        satelliteGammaDecayTicks = readClampedDouble(
                properties,
                SATELLITE_GAMMA_DECAY_TICKS_KEY,
                satelliteGammaDecayTicks,
                MIN_SATELLITE_GAMMA_DECAY_TICKS,
                MAX_SATELLITE_GAMMA_DECAY_TICKS
        );
        satelliteGammaOverlayLift = readClampedDouble(
                properties,
                SATELLITE_GAMMA_OVERLAY_LIFT_KEY,
                satelliteGammaOverlayLift,
                MIN_SATELLITE_UNIT,
                MAX_SATELLITE_UNIT
        );
        satelliteGammaBlowoutStrength = readClampedDouble(
                properties,
                SATELLITE_GAMMA_BLOWOUT_STRENGTH_KEY,
                satelliteGammaBlowoutStrength,
                MIN_SATELLITE_UNIT,
                MAX_SATELLITE_UNIT
        );
        satelliteColorGradeMax = readClampedDouble(
                properties,
                SATELLITE_COLOR_GRADE_MAX_KEY,
                satelliteColorGradeMax,
                MIN_SATELLITE_UNIT,
                MAX_SATELLITE_UNIT
        );
        satellitePlungeExposureMax = readClampedDouble(
                properties,
                SATELLITE_PLUNGE_EXPOSURE_MAX_KEY,
                satellitePlungeExposureMax,
                MIN_SATELLITE_UNIT,
                MAX_SATELLITE_UNIT
        );
        satelliteShaderExposureScale = readClampedDouble(
                properties,
                SATELLITE_SHADER_EXPOSURE_SCALE_KEY,
                satelliteShaderExposureScale,
                MIN_SATELLITE_SHADER_SCALE,
                MAX_SATELLITE_SHADER_SCALE
        );
        readWidgetLayouts(properties);
        readConfigTexts(properties);
    }

    private static void resetToDefaults() {
        applyConfigProperties(createDefaultProperties());
    }

    private static Properties createDefaultProperties() {
        Properties properties = new Properties();
        try {
            properties.load(new StringReader(DEFAULT_CONFIG_PROPERTIES));
        } catch (IOException ignored) {
        }
        return properties;
    }

    private static double[] readStageHeights(Properties properties, String prefix, double[] defaults) {
        double[] values = defaults.clone();
        for (int i = 0; i < values.length; i++) {
            values[i] = readDouble(properties, prefix + (i + 1), values[i]);
        }
        return sanitizeStageHeights(values);
    }

    private static int[] readStageTicks(Properties properties, String prefix, int[] defaults) {
        int[] values = defaults.clone();
        for (int i = 0; i < values.length; i++) {
            values[i] = readClampedInt(properties, prefix + (i + 1), values[i], MIN_STAGE_TICKS, MAX_STAGE_TICKS);
        }
        return sanitizeStageTicks(values);
    }

    private static void readWidgetLayouts(Properties properties) {
        configWidgetLayouts.clear();
        for (String key : properties.stringPropertyNames()) {
            if (!key.startsWith(CONFIG_WIDGET_PREFIX) || !key.endsWith(".x")) {
                continue;
            }
            String id = key.substring(CONFIG_WIDGET_PREFIX.length(), key.length() - 2);
            if (!isSafeId(id)) {
                continue;
            }
            String prefix = CONFIG_WIDGET_PREFIX + id;
            double x = readDouble(properties, prefix + ".x", 0.0D);
            double y = readDouble(properties, prefix + ".y", 0.0D);
            double width = readDouble(properties, prefix + ".width", 0.0D);
            double height = readDouble(properties, prefix + ".height", 0.0D);
            int baseWidth = readPositiveInt(properties, prefix + ".baseWidth", 0);
            int baseHeight = readPositiveInt(properties, prefix + ".baseHeight", 0);
            if (width > 0.0D && height > 0.0D) {
                configWidgetLayouts.put(id, new double[]{
                        clamp(x, -2.0D, 3.0D),
                        clamp(y, -2.0D, 3.0D),
                        clamp(width, 0.01D, 3.0D),
                        clamp(height, 0.01D, 3.0D),
                        baseWidth,
                        baseHeight
                });
            }
        }
    }
    private static void readConfigTexts(Properties properties) {
        configTexts.clear();
        for (String key : properties.stringPropertyNames()) {
            if (!key.startsWith(CONFIG_TEXT_PREFIX)) {
                continue;
            }
            String id = key.substring(CONFIG_TEXT_PREFIX.length());
            if (isSafeId(id)) {
                configTexts.put(id, properties.getProperty(key, ""));
            }
        }
    }
    private static int readConfigVersion(Properties properties) {
        try {
            return Integer.parseInt(properties.getProperty(CONFIG_VERSION_KEY, "0"));
        } catch (NumberFormatException ignored) {
            return 0;
        }
    }

    private static void applyConfigVersionMigration(Properties properties, int previousVersion) {
        if (previousVersion < 5) {
            properties.setProperty(CUSTOM_SOUNDS_ENABLED_KEY, "true");
            properties.setProperty(SOUND_PACK_KEY, "gta");
            properties.setProperty(ENABLE_SATELLITE_CAMERA_FX_KEY, "false");
            properties.setProperty(ENABLE_SHUTTER_FLASH_KEY, "false");
            properties.setProperty(ENABLE_VIGNETTE_KEY, "false");
        }
        if (previousVersion < 6) {
            properties.setProperty(ENABLE_INTERFERENCE_LINES_KEY, "false");
        }
        if (previousVersion < 7) {
            if (!Boolean.parseBoolean(properties.getProperty(CUSTOM_SOUNDS_ENABLED_KEY, "true"))) {
                properties.setProperty(SOUND_PACK_KEY, SOUND_PACK_OFF);
                properties.setProperty(CUSTOM_SOUNDS_ENABLED_KEY, "false");
            } else {
                properties.setProperty(SOUND_PACK_KEY, sanitizeSoundPack(properties.getProperty(SOUND_PACK_KEY, SOUND_PACK_GTA)));
                properties.setProperty(CUSTOM_SOUNDS_ENABLED_KEY, "true");
            }
        }
        if (previousVersion < 8) {
            syncSoundPackProperties(properties);
        }
    }

    private static void syncSoundPackProperties(Properties properties) {
        String pack = sanitizeSoundPack(properties.getProperty(SOUND_PACK_KEY, SOUND_PACK_GTA));
        if (!Boolean.parseBoolean(properties.getProperty(CUSTOM_SOUNDS_ENABLED_KEY, "true"))) {
            pack = SOUND_PACK_OFF;
        }
        properties.setProperty(SOUND_PACK_KEY, pack);
        properties.setProperty(CUSTOM_SOUNDS_ENABLED_KEY, Boolean.toString(isTeleportSoundPackEnabled(pack)));
    }

    private static boolean isTeleportSoundPackEnabled(String pack) {
        return !SOUND_PACK_OFF.equals(pack);
    }

    private static boolean needsLayoutCleanup(Properties properties) {
        if (Boolean.parseBoolean(properties.getProperty(CONFIG_LAYOUT_CUSTOM_KEY, "false"))) {
            return true;
        }
        return hasPropertyWithPrefix(properties, CONFIG_WIDGET_PREFIX)
                || hasPropertyWithPrefix(properties, CONFIG_TEXT_PREFIX);
    }

    private static boolean prepareLoadedProperties(Properties properties) {
        if (!isLegacyCompactLayoutConfig(properties)) {
            return false;
        }

        restoreDefaultLayoutProperties(properties);
        return true;
    }

    private static boolean isLegacyCompactLayoutConfig(Properties properties) {
        return !hasPropertyWithPrefix(properties, CONFIG_WIDGET_PREFIX)
                && !hasPropertyWithPrefix(properties, CONFIG_TEXT_PREFIX)
                && (properties.containsKey(CONFIG_LAYOUT_CUSTOM_KEY)
                || properties.containsKey(CONFIG_LAYOUT_BASE_WIDTH_KEY)
                || properties.containsKey(CONFIG_LAYOUT_BASE_HEIGHT_KEY)
                || properties.containsKey(CONFIG_LAYOUT_WIDTH_KEY)
                || properties.containsKey(CONFIG_LAYOUT_HEIGHT_KEY));
    }

    private static boolean hasPropertyWithPrefix(Properties properties, String prefix) {
        for (String key : properties.stringPropertyNames()) {
            if (key.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }

    private static void restoreDefaultLayoutProperties(Properties properties) {
        for (String key : properties.stringPropertyNames().stream()
                .filter(key -> key.startsWith("configLayout")
                        || key.startsWith(CONFIG_WIDGET_PREFIX)
                        || key.startsWith(CONFIG_TEXT_PREFIX))
                .toList()) {
            properties.remove(key);
        }

        Properties defaults = createDefaultProperties();
        for (String key : defaults.stringPropertyNames()) {
            if (key.startsWith("configLayout")
                    || key.startsWith(CONFIG_WIDGET_PREFIX)
                    || key.startsWith(CONFIG_TEXT_PREFIX)) {
                properties.setProperty(key, defaults.getProperty(key));
            }
        }
    }

    private static double readDouble(Properties properties, String key, double fallback) {
        try {
            return Double.parseDouble(properties.getProperty(key, Double.toString(fallback)));
        } catch (NumberFormatException ignored) {
            return fallback;
        }
    }

    private static double readUnitDouble(Properties properties, String key, double fallback) {
        return clamp(readDouble(properties, key, fallback), 0.0D, 1.0D);
    }

    private static double readClampedDouble(Properties properties, String key, double fallback, double min, double max) {
        return clamp(readDouble(properties, key, fallback), min, max);
    }

    private static int readClampedInt(Properties properties, String key, int fallback, int min, int max) {
        try {
            return clamp(Integer.parseInt(properties.getProperty(key, Integer.toString(fallback))), min, max);
        } catch (NumberFormatException ignored) {
            return fallback;
        }
    }

    private static int readPositiveInt(Properties properties, String key, int fallback) {
        try {
            return Math.max(0, Integer.parseInt(properties.getProperty(key, Integer.toString(fallback))));
        } catch (NumberFormatException ignored) {
            return fallback;
        }
    }

    private static double roundStageHeight(double value) {
        return Math.rint(value);
    }

    private static boolean isSafeId(String id) {
        return id != null && id.matches("[a-z0-9_]+") && id.length() <= 64;
    }

    private static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    private static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    private static boolean save() {
        if (configPath == null) {
            configPath = resolveConfigPath();
        }

        Properties properties = new Properties();
        properties.setProperty(EFFECT_ENABLED_KEY, Boolean.toString(effectEnabled));
        properties.setProperty(PLAYER_FREEZE_ENABLED_KEY, Boolean.toString(playerFreezeEnabled));
        properties.setProperty(CROSS_DIMENSION_TRAVEL_ENABLED_KEY, Boolean.toString(crossDimensionTravelEnabled));
        properties.setProperty(ZOOM_HEIGHTS_LINKED_KEY, Boolean.toString(zoomHeightsLinked));
        writeStageHeights(properties, ZOOM_OUT_STAGE_KEY_PREFIX, zoomOutStageHeights);
        writeStageHeights(properties, ZOOM_IN_STAGE_KEY_PREFIX, zoomInStageHeights);
        properties.setProperty(NETHER_ZOOM_HEIGHTS_LINKED_KEY, Boolean.toString(netherZoomHeightsLinked));
        writeStageHeights(properties, NETHER_ZOOM_OUT_STAGE_KEY_PREFIX, netherZoomOutStageHeights);
        writeStageHeights(properties, NETHER_ZOOM_IN_STAGE_KEY_PREFIX, netherZoomInStageHeights);
        properties.setProperty(END_ZOOM_HEIGHTS_LINKED_KEY, Boolean.toString(endZoomHeightsLinked));
        writeStageHeights(properties, END_ZOOM_OUT_STAGE_KEY_PREFIX, endZoomOutStageHeights);
        writeStageHeights(properties, END_ZOOM_IN_STAGE_KEY_PREFIX, endZoomInStageHeights);
        writeStageTicks(properties, ZOOM_OUT_STAGE_TICKS_KEY_PREFIX, zoomOutStageTicks);
        writeStageTicks(properties, ZOOM_IN_STAGE_TICKS_KEY_PREFIX, zoomInStageTicks);
        properties.setProperty(ZOOM_STAGE_GLIDE_HEIGHT_KEY, Double.toString(zoomStageGlideHeight));
        properties.setProperty(ZOOM_STAGE_GLIDE_TICKS_KEY, Integer.toString(zoomStageGlideTicks));
        properties.setProperty(BODY_CAMERA_HEIGHT_KEY, Double.toString(bodyCameraHeight));
        properties.setProperty(BODY_GLIDE_HEIGHT_KEY, Double.toString(bodyGlideHeight));
        properties.setProperty(BODY_GLIDE_TICKS_KEY, Integer.toString(bodyGlideTicks));
        properties.setProperty(LOCAL_PLAYER_HIDE_TICKS_KEY, Integer.toString(localPlayerHideTicks));
        properties.setProperty(CUSTOM_SOUNDS_ENABLED_KEY, Boolean.toString(customSoundsEnabled));
        properties.setProperty(MINECRAFT_SOUND_VOLUME_KEY, Double.toString(minecraftSoundVolume));
        properties.setProperty(CUSTOM_SOUND_VOLUME_KEY, Double.toString(customSoundVolume));
        properties.setProperty(WARP_PLATE_TRANSITIONS_ENABLED_KEY, Boolean.toString(warpPlateTransitionsEnabled));
        properties.setProperty(EXTERNAL_TELEPORT_TRANSITIONS_ENABLED_KEY, Boolean.toString(externalTeleportTransitionsEnabled));
        properties.setProperty(FALLBACK_CHUNK_FADE_ENABLED_KEY, Boolean.toString(fallbackChunkFadeEnabled));
        properties.setProperty(TRANSITION_PRESET_KEY, transitionPreset);
        properties.setProperty(SOUND_PACK_KEY, soundPack);
        properties.setProperty(ENABLE_SHUTTER_FLASH_KEY, Boolean.toString(enableShutterFlash));
        properties.setProperty(ENABLE_VIGNETTE_KEY, Boolean.toString(enableVignette));
        properties.setProperty(ENABLE_INTERFERENCE_LINES_KEY, Boolean.toString(enableInterferenceLines));
        properties.setProperty(FADE_COLOR_KEY, fadeColor);
        properties.setProperty(ENABLE_VANILLA_TP_KEY, Boolean.toString(enableVanillaTp));
        properties.setProperty(ENABLE_WAYSTONES_KEY, Boolean.toString(enableWaystones));
        properties.setProperty(ENABLE_JOURNEYMAP_KEY, Boolean.toString(enableJourneyMap));
        properties.setProperty(ENABLE_PORTALS_KEY, Boolean.toString(enablePortals));
        properties.setProperty(ENABLE_SATELLITE_CAMERA_FX_KEY, Boolean.toString(enableSatelliteCameraFx));
        properties.setProperty(SATELLITE_GAMMA_STRENGTH_KEY, Double.toString(satelliteGammaStrength));
        properties.setProperty(SATELLITE_GAMMA_DECAY_TICKS_KEY, Double.toString(satelliteGammaDecayTicks));
        properties.setProperty(SATELLITE_GAMMA_OVERLAY_LIFT_KEY, Double.toString(satelliteGammaOverlayLift));
        properties.setProperty(SATELLITE_GAMMA_BLOWOUT_STRENGTH_KEY, Double.toString(satelliteGammaBlowoutStrength));
        properties.setProperty(SATELLITE_COLOR_GRADE_MAX_KEY, Double.toString(satelliteColorGradeMax));
        properties.setProperty(SATELLITE_PLUNGE_EXPOSURE_MAX_KEY, Double.toString(satellitePlungeExposureMax));
        properties.setProperty(SATELLITE_SHADER_EXPOSURE_SCALE_KEY, Double.toString(satelliteShaderExposureScale));
        properties.setProperty(CONFIG_LAYOUT_EDITOR_BUTTON_VISIBLE_KEY, Boolean.toString(configLayoutEditorButtonVisible));
        properties.setProperty(CONFIG_LAYOUT_DEBUG_ENABLED_KEY, Boolean.toString(configLayoutDebugEnabled));
        properties.setProperty(CONFIG_LAYOUT_ASPECT_LOCKED_KEY, Boolean.toString(configLayoutAspectLocked));
        properties.setProperty(CONFIG_LAYOUT_GRID_ENABLED_KEY, Boolean.toString(configLayoutGridEnabled));
        properties.setProperty(CONFIG_LAYOUT_SNAP_ENABLED_KEY, Boolean.toString(configLayoutSnapEnabled));
        properties.setProperty(CONFIG_VERSION_KEY, Integer.toString(CURRENT_CONFIG_VERSION));
        properties.setProperty(CONFIG_LAYOUT_CUSTOM_KEY, Boolean.toString(configLayoutCustom));
        properties.setProperty(CONFIG_LAYOUT_X_KEY, Double.toString(configLayoutX));
        properties.setProperty(CONFIG_LAYOUT_Y_KEY, Double.toString(configLayoutY));
        properties.setProperty(CONFIG_LAYOUT_WIDTH_KEY, Double.toString(configLayoutWidth));
        properties.setProperty(CONFIG_LAYOUT_HEIGHT_KEY, Double.toString(configLayoutHeight));
        properties.setProperty(CONFIG_LAYOUT_BASE_WIDTH_KEY, Integer.toString(configLayoutBaseWidth));
        properties.setProperty(CONFIG_LAYOUT_BASE_HEIGHT_KEY, Integer.toString(configLayoutBaseHeight));
        for (Map.Entry<String, double[]> entry : configWidgetLayouts.entrySet()) {
            double[] values = entry.getValue();
            String prefix = CONFIG_WIDGET_PREFIX + entry.getKey();
            properties.setProperty(prefix + ".x", Double.toString(values[0]));
            properties.setProperty(prefix + ".y", Double.toString(values[1]));
            properties.setProperty(prefix + ".width", Double.toString(values[2]));
            properties.setProperty(prefix + ".height", Double.toString(values[3]));
            if (values.length > 5) {
                properties.setProperty(prefix + ".baseWidth", Integer.toString((int) Math.round(values[4])));
                properties.setProperty(prefix + ".baseHeight", Integer.toString((int) Math.round(values[5])));
            }
        }
        for (Map.Entry<String, String> entry : configTexts.entrySet()) {
            properties.setProperty(CONFIG_TEXT_PREFIX + entry.getKey(), entry.getValue());
        }

        try {
            Files.createDirectories(configPath.getParent());
            try (OutputStream output = Files.newOutputStream(configPath)) {
                properties.store(output, "WT Teleport client settings");
            }
            return true;
        } catch (IOException exception) {
            GtaLikeTeleport.LOGGER.error("Failed to save WT Teleport config to {}", configPath, exception);
            return false;
        }
    }
    private static Path resolveConfigPath() {
        return FMLPaths.CONFIGDIR.get().resolve(FILE_NAME);
    }

    private static Path resolveLegacyConfigPath() {
        return FMLPaths.CONFIGDIR.get().resolve(LEGACY_FILE_NAME);
    }

    private static Path resolveOldLegacyConfigPath() {
        return FMLPaths.CONFIGDIR.get().resolve(OLD_LEGACY_FILE_NAME);
    }

    private static void migrateLegacyConfig() {
        if (Files.exists(configPath)) {
            return;
        }
        Path legacyPath = resolveLegacyConfigPath();
        if (!Files.exists(legacyPath)) {
            legacyPath = resolveOldLegacyConfigPath();
        }
        if (!Files.exists(legacyPath)) {
            return;
        }
        try {
            Files.createDirectories(configPath.getParent());
            Files.copy(legacyPath, configPath);
        } catch (IOException ignored) {
            configPath = legacyPath;
        }
    }

    private static ZoomDimension sanitizeZoomDimension(ZoomDimension dimension) {
        return dimension == null ? ZoomDimension.OVERWORLD : dimension;
    }

    public enum ZoomDimension {
        OVERWORLD,
        NETHER,
        END;

        public static ZoomDimension fromLevel(ResourceKey<Level> dimension) {
            if (Level.NETHER.equals(dimension)) {
                return NETHER;
            }
            if (Level.END.equals(dimension)) {
                return END;
            }
            return OVERWORLD;
        }
    }

    private static void writeStageHeights(Properties properties, String prefix, double[] values) {
        double[] sanitized = sanitizeStageHeights(values);
        for (int i = 0; i < sanitized.length; i++) {
            properties.setProperty(prefix + (i + 1), Integer.toString((int) sanitized[i]));
        }
    }

    private static void writeStageTicks(Properties properties, String prefix, int[] values) {
        int[] sanitized = sanitizeStageTicks(values);
        for (int i = 0; i < sanitized.length; i++) {
            properties.setProperty(prefix + (i + 1), Integer.toString(sanitized[i]));
        }
    }
}
