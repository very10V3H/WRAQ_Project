package fun.wraq.render.gui.illustrate;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fun.wraq.common.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * AI-Generated, 2025-10-18
 * <p>
 * 材料获取途径数据：读取 mod 内置的固定资产 {@code assets/vmd/material_sources.json}，
 * 该文件由 {@code tools/generate_material_sources.ps1} 检索代码后生成。
 * 在图鉴中查看材料时，tooltip 会追加对应的获取途径。
 */
public final class MaterialSourceData {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final Map<Item, List<String>> SOURCES = new LinkedHashMap<>();
    private static boolean loaded = false;

    private MaterialSourceData() {
    }

    public static List<String> get(Item item) {
        if (!FMLEnvironment.dist.isClient()) {
            return List.of();
        }
        ensureLoaded();
        return SOURCES.getOrDefault(item, List.of());
    }

    public static boolean hasSource(Item item) {
        return !get(item).isEmpty();
    }

    private static void ensureLoaded() {
        if (loaded) {
            return;
        }
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft == null) {
            return;
        }
        ResourceManager resourceManager = minecraft.getResourceManager();
        if (resourceManager == null) {
            return;
        }
        loaded = true;
        ResourceLocation location = new ResourceLocation(Utils.MOD_ID, "material_sources.json");
        Optional<Resource> resource = resourceManager.getResource(location);
        if (resource.isEmpty()) {
            LOGGER.warn("[VMD] material_sources.json not found");
            return;
        }
        try (BufferedReader reader = resource.get().openAsReader()) {
            JsonObject object = JsonParser.parseReader(reader).getAsJsonObject();
            for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
                ResourceLocation id = ResourceLocation.tryParse(entry.getKey());
                if (id == null) {
                    continue;
                }
                Item item = ForgeRegistries.ITEMS.getValue(id);
                if (item == null) {
                    continue;
                }
                List<String> sources = new ArrayList<>();
                if (entry.getValue().isJsonArray()) {
                    for (JsonElement element : entry.getValue().getAsJsonArray()) {
                        String source = element.getAsString();
                        if (!source.isBlank()) {
                            sources.add(source);
                        }
                    }
                } else {
                    String source = entry.getValue().getAsString();
                    if (!source.isBlank()) {
                        sources.add(source);
                    }
                }
                if (!sources.isEmpty()) {
                    SOURCES.put(item, List.copyOf(sources));
                }
            }
        } catch (Exception exception) {
            LOGGER.warn("[VMD] Failed to load material_sources.json", exception);
        }
    }
}
