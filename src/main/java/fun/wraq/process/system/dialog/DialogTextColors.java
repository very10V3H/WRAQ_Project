package fun.wraq.process.system.dialog;

import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * AI-Generated, 2026-10-25
 * 对话文本地点着色：把 NPC 对话里出现的地点名替换为对应地区的颜色。
 * <p>
 * 使用 {@link Component#visit} 遍历所有文本片段，保留原有样式（粗体/斜体/事件），
 * 只覆盖地点名本身的颜色；地点按长度优先匹配，避免「望山据点」被「望山」截断。
 */
public final class DialogTextColors {

    private record Place(String text, Style style) {
    }

    /**
     * 从长到短匹配，避免短地点名截断长地点名。
     */
    private static final List<Place> PLACES = new ArrayList<>(List.of(
            new Place("旭升岛功能区", CustomStyle.styleOfSunIsland),
            new Place("旭升岛轻轨站", CustomStyle.styleOfSunIsland),
            new Place("旭升岛", CustomStyle.styleOfSunIsland),
            new Place("天空城", CustomStyle.styleOfSky),
            new Place("平原村", CustomStyle.styleOfPlain),
            new Place("平原暂时", CustomStyle.styleOfPlain),
            new Place("雨林村", CustomStyle.styleOfForest),
            new Place("雨林东侧", CustomStyle.styleOfForest),
            new Place("海岸村", CustomStyle.styleOfWater),
            new Place("海岸一带", CustomStyle.styleOfWater),
            new Place("薰曦村", CustomStyle.styleOfJacaranda),
            new Place("北洋村", CustomStyle.styleOfSnow),
            new Place("风之高地", CustomStyle.styleOfWind),
            new Place("暗黑城堡", CustomStyle.styleOfCastle),
            new Place("火山村", CustomStyle.styleOfVolcano),
            new Place("火山区域", CustomStyle.styleOfVolcano),
            new Place("熔岩区域", CustomStyle.styleOfVolcano),
            new Place("熔岩地带", CustomStyle.styleOfVolcano),
            new Place("东洋塔", CustomStyle.styleOfHusk),
            new Place("雷光岛", CustomStyle.styleOfLightning),
            new Place("樱岛", CustomStyle.styleOfSakura),
            new Place("绯樱村", CustomStyle.styleOfSakura),
            new Place("尘月宫", CustomStyle.styleOfMoon),
            new Place("尘月", CustomStyle.styleOfMoon),
            new Place("望山据点", CustomStyle.styleOfMoontain),
            new Place("望山阁", CustomStyle.styleOfMoontain),
            new Place("望山的", CustomStyle.styleOfMoontain),
            new Place("望山暂时", CustomStyle.styleOfMoontain),
            new Place("望山那边", CustomStyle.styleOfMoontain),
            new Place("鹰眼工厂", CustomStyle.styleOfHarbinger),
            new Place("菌菇聚落", CustomStyle.MUSHROOM_STYLE),
            new Place("末地", CustomStyle.styleOfEnd),
            new Place("炼魔塔", CustomStyle.MANA_TOWER_STYLE),
            new Place("炼魔平原", CustomStyle.MANA_TOWER_STYLE),
            new Place("远古之城", CustomStyle.styleOfWarden),
            new Place("古城", CustomStyle.styleOfWarden),
            new Place("熔岩地堡", CustomStyle.BUNKER_STYLE),
            new Place("圣光岛", CustomStyle.DIVINE_STYLE),
            new Place("极冬之境", CustomStyle.styleOfIce),
            new Place("下界II", CustomStyle.styleOfNether),
            new Place("下界I", CustomStyle.styleOfNether),
            new Place("下界", CustomStyle.styleOfNether),
            new Place("唤魔湖", CustomStyle.styleOfMana),
            new Place("玉林小镇", CustomStyle.styleOfForest),
            new Place("玉林饭店", CustomStyle.styleOfForest),
            new Place("旧主城", CustomStyle.styleOfForest),
            new Place("旧铁匠铺", CustomStyle.styleOfForest),
            new Place("旧商品兑换", CustomStyle.styleOfForest),
            new Place("玛瑙矿区", CustomStyle.styleOfForest),
            new Place("北境晶钻矿区", CustomStyle.styleOfSnow),
            new Place("极寒前哨所", CustomStyle.styleOfIce),
            new Place("海底神殿", CustomStyle.styleOfWater),
            new Place("粉钻矿区", CustomStyle.styleOfSakuraMine),
            new Place("禁忌之地", CustomStyle.styleOfMana)
    ));

    static {
        PLACES.sort(Comparator.comparingInt((Place place) -> place.text().length()).reversed());
    }

    private DialogTextColors() {
    }

    /** 遍历组件所有文本片段，给地点名叠加对应颜色；不改动原组件。 */
    public static Component colorize(Component source) {
        MutableComponent result = Component.empty();
        source.visit((style, text) -> {
            appendColored(result, text, style);
            return Optional.empty();
        }, Style.EMPTY);
        return result;
    }

    private static void appendColored(MutableComponent out, String text, Style baseStyle) {
        int cursor = 0;
        while (cursor < text.length()) {
            Place found = null;
            int foundIndex = Integer.MAX_VALUE;
            for (Place place : PLACES) {
                int index = text.indexOf(place.text(), cursor);
                if (index < 0) {
                    continue;
                }
                if (index < foundIndex
                        || (index == foundIndex && found != null
                        && place.text().length() > found.text().length())) {
                    found = place;
                    foundIndex = index;
                }
            }
            if (found == null) {
                append(out, text.substring(cursor), baseStyle);
                return;
            }
            if (foundIndex > cursor) {
                append(out, text.substring(cursor, foundIndex), baseStyle);
            }
            append(out, found.text(), found.style().getColor() == null
                    ? baseStyle
                    : baseStyle.withColor(found.style().getColor()));
            cursor = foundIndex + found.text().length();
        }
    }

    private static void append(MutableComponent out, String text, Style style) {
        if (!text.isEmpty()) {
            out.append(Component.literal(text).withStyle(style));
        }
    }
}
