package fun.wraq.series.secret;

import fun.wraq.common.fast.Te;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

public enum SecretSeries {

    HOLY(0, "holy", Te.s("神圣", CustomStyle.DIVINE_STYLE)),
    NETHER(1, "nether", Te.s("炼狱", CustomStyle.styleOfNether));

    public final int id;
    public final String name;
    public final Component description;

    SecretSeries(int id, String name, Component description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    private static final Map<SecretSeries, Block> seriesToChestBlockMap = new HashMap<>();

    public static Block getChestBlock(SecretSeries series) {
        if (seriesToChestBlockMap.isEmpty()) {
            seriesToChestBlockMap.put(HOLY,
                    ForgeRegistries.BLOCKS.getValue(new ResourceLocation("quark", "birch_chest")));
            seriesToChestBlockMap.put(NETHER,
                    ForgeRegistries.BLOCKS.getValue(new ResourceLocation("quark", "nether_brick_chest")));
        }
        return seriesToChestBlockMap.get(series);
    }
}
