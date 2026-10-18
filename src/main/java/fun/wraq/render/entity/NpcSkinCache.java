/** AI-Generated, 2026-10-17 */
package fun.wraq.render.entity;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import fun.wraq.common.util.Utils;
import fun.wraq.entities.entities.npc.VmdNpc;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.resources.ResourceLocation;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * NPC 皮肤解析（仅客户端）。
 * <p>
 * 服务端从不下载皮肤，只把「皮肤来源」通过实体同步字段下发，客户端按以下顺序回退：
 * <ol>
 *     <li><b>内置 PNG</b>（{@code skinFile}）：{@code assets/vmd/textures/entity/npc/<name>.png}，永不联网；</li>
 *     <li><b>皮肤属性</b>（{@code skinProperty}）：创建 NPC 时从在线玩家 GameProfile 抄下的 {@code textures}
 *         属性（base64 + 签名）。交给 {@link SkinManager#getInsecureSkinLocation(GameProfile)} 还原，
 *         玩家改名/下线后依然有效；</li>
 *     <li><b>在线玩家</b>（{@code skinName}）：直接复用连接里的 {@link PlayerInfo}，零网络；</li>
 *     <li><b>兜底</b>：{@link DefaultPlayerSkin#getDefaultSkin(UUID)}，保证永不出现白板/紫黑格。</li>
 * </ol>
 * 解析结果按「来源指纹」缓存，避免每帧重复注册纹理。
 */
public final class NpcSkinCache {

    /** 来源指纹 → 纹理位置。指纹由皮肤来源本身构成，因此同皮肤多 NPC 共享一份纹理。 */
    private static final Map<String, ResourceLocation> CACHE = new ConcurrentHashMap<>();

    /** 已确认不存在的内置贴图，避免每帧查资源包。 */
    private static final Map<String, Boolean> FILE_MISSING = new ConcurrentHashMap<>();

    private NpcSkinCache() {
    }

    public static ResourceLocation resolve(VmdNpc npc) {
        String skinFile = npc.getSkinFile();
        if (!skinFile.isEmpty()) {
            ResourceLocation builtin = builtin(skinFile);
            if (builtin != null) {
                return builtin;
            }
        }

        String property = npc.getSkinProperty();
        if (!property.isEmpty()) {
            ResourceLocation fromProperty = fromProperty(property, npc.getSkinSignature());
            if (fromProperty != null) {
                return fromProperty;
            }
        }

        String skinName = npc.getSkinName();
        if (!skinName.isEmpty()) {
            ResourceLocation fromOnline = fromOnlinePlayer(skinName);
            if (fromOnline != null) {
                return fromOnline;
            }
        }

        // 内置兜底：assets/vmd/textures/entity/npc/default.png（缺失时才退到原版默认皮肤）
        ResourceLocation defaultBuiltin = builtin("default");
        if (defaultBuiltin != null) {
            return defaultBuiltin;
        }
        return DefaultPlayerSkin.getDefaultSkin(npc.getUUID());
    }

    // ------------------------------------------------------------------
    // L0：内置贴图
    // ------------------------------------------------------------------

    private static ResourceLocation builtin(String name) {
        String key = "file:" + name;
        ResourceLocation cached = CACHE.get(key);
        if (cached != null) {
            return cached;
        }
        if (Boolean.TRUE.equals(FILE_MISSING.get(name))) {
            return null;
        }
        ResourceLocation location = new ResourceLocation(Utils.MOD_ID, "textures/entity/npc/" + name + ".png");
        if (!resourceExists(location)) {
            FILE_MISSING.put(name, true);
            return null;
        }
        CACHE.put(key, location);
        return location;
    }

    private static boolean resourceExists(ResourceLocation location) {
        try {
            return Minecraft.getInstance().getResourceManager().getResource(location).isPresent();
        } catch (Exception e) {
            return false;
        }
    }

    // ------------------------------------------------------------------
    // L1：textures 属性（创建时从在线玩家抄取，可离线还原）
    // ------------------------------------------------------------------

    private static ResourceLocation fromProperty(String value, String signature) {
        // 用属性内容派生 UUID，让「同一套皮肤」的多个 NPC 共享纹理
        UUID profileId = UUID.nameUUIDFromBytes(value.getBytes(StandardCharsets.UTF_8));
        String key = "prop:" + profileId;
        ResourceLocation cached = CACHE.get(key);
        if (cached != null) {
            return cached;
        }
        try {
            GameProfile profile = new GameProfile(profileId, "vmd_npc");
            profile.getProperties().put("textures",
                    new Property("textures", value, signature == null ? "" : signature));
            SkinManager skinManager = Minecraft.getInstance().getSkinManager();
            ResourceLocation location = skinManager.getInsecureSkinLocation(profile);
            if (location != null) {
                CACHE.put(key, location);
            }
            return location;
        } catch (Exception e) {
            return null;
        }
    }

    // ------------------------------------------------------------------
    // L2：在线玩家（零网络）
    // ------------------------------------------------------------------

    private static ResourceLocation fromOnlinePlayer(String name) {
        ClientPacketListener connection = Minecraft.getInstance().getConnection();
        if (connection == null) {
            return null;
        }
        PlayerInfo info = connection.getPlayerInfo(name);
        if (info == null) {
            return null;
        }
        String key = "online:" + name.toLowerCase();
        ResourceLocation cached = CACHE.get(key);
        if (cached != null) {
            return cached;
        }
        ResourceLocation location = info.getSkinLocation();
        if (location != null) {
            CACHE.put(key, location);
        }
        return location;
    }

    /** 皮肤变更后清缓存（GM 用 /vmd npc set skin 后会调用）。 */
    public static void invalidate(String name) {
        if (name == null) {
            CACHE.clear();
            return;
        }
        CACHE.keySet().removeIf(key -> key.endsWith(":" + name) || key.endsWith(name));
        FILE_MISSING.clear();
    }
}
