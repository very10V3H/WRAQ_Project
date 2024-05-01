package com.very.wraq.render.hud;

import com.very.wraq.valueAndTools.Utils.Utils;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class ItemHud {
    public static Map<String, ResourceLocation> map = new HashMap<>();
    public static String[] itemStrings = {
            "mine_shield",
            "purple_iron",
            "blackforestsword0",
            "seasword0",
            "wither_bow_0",
            "sakura_bow",
            "ice_sword",
            "ice_bow",
            "soulsword",
            "lxyzo_sword",
            "snow_shield",
            "nether_shield",
            "snowrune0",
            "ice_sceptre",
            "soulsceptre",
            "devil_blood",
            "wither_book",
            "earth_book",
            "wither_sword_0",
            "snow_power",
            "spring_scale0",
            "xiangli_pickaxe",
            "fire_work_gun",
            "soulsword",
            "black_feisa_sceptre",
            "fengxiaoju_curios1",
            "guangyi_bow",
            "liulixian_curios",
            "shangmengli_sceptre",
            "shangmengli_sceptre1",
            "shangmengli_curios1",
            "shangmengli_curios11",
            "showdicker_bow",
            "zeus_sword",
            "zeus_curios",
            "wc_bow",
            "fengxiaoju_curios",
            "very_new_curios",
            "black_feisa_sceptre",
            "zeus_curios",
            "eliaoi_book",
            "liulixian_sword",
            "fire_cracker",
            "snowrune2",
            "snowrune3",
            "end_rune0",
            "soulsceptre",
            "end_rune0",
            "ice_book"
    };
    public static void Init() {
        map.put("shangmengli_sceptre", new ResourceLocation(Utils.MOD_ID,"textures/item/" + "shangmengli_sceptre" + ".png"));
        map.put("shangmengli_sceptre1", new ResourceLocation(Utils.MOD_ID,"textures/item/" + "shangmengli_sceptre1" + ".png"));
        map.put("shangmengli_curios1", new ResourceLocation(Utils.MOD_ID,"textures/item/" + "shangmengli_curios1" + ".png"));
        map.put("shangmengli_curios11", new ResourceLocation(Utils.MOD_ID,"textures/item/" + "shangmengli_curios11" + ".png"));
    }
}
