package com.very.wraq.process.system.season;

import com.very.wraq.process.system.element.Element;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import sereneseasons.api.season.Season;
import sereneseasons.handler.season.SeasonHandler;
import sereneseasons.season.SeasonSavedData;
import sereneseasons.season.SeasonTime;

import java.util.*;

public class MySeason {

    public static String clientSeason;
    public static String currentSeason;
    public static int currentElementEffectBroadDelay = 0;

    public static String early = "EARLY";
    public static String mid = "MID";
    public static String late = "LATE";
    public static String spring = "SPRING";
    public static String summer = "SUMMER";
    public static String autumn = "AUTUMN";
    public static String winter = "WINTER";

    public static Map<String, Map<String, Double>> seasonElementEffectMap = new HashMap<>() {{
        Arrays.stream(Season.SubSeason.values()).forEach(subSeason -> {
            Map<String, Double> map = new HashMap<>();
            double tier0 = 0.35, tier1 = 0.2, tier2 = 0.1, tier3 = 0.05;
            if (subSeason.equals(Season.SubSeason.EARLY_SPRING)) {
                map.put(Element.water, tier0);
                map.put(Element.life, tier1);
                map.put(Element.ice, tier2);
                map.put(Element.fire, -tier0);
                map.put(Element.lightning, -tier1);
            }
            if (subSeason.equals(Season.SubSeason.MID_SPRING)) {
                map.put(Element.life, tier0);
                map.put(Element.water, tier1);
                map.put(Element.wind, tier2);
                map.put(Element.ice, -tier0);
                map.put(Element.fire, -tier1);
            }
            if (subSeason.equals(Season.SubSeason.LATE_SPRING)) {
                map.put(Element.life, tier0);
                map.put(Element.fire, tier1);
                map.put(Element.water, tier2);
                map.put(Element.ice, -tier0);
                map.put(Element.lightning, -tier1);
            }
            if (subSeason.equals(Season.SubSeason.EARLY_SUMMER)) {
                map.put(Element.fire, tier0);
                map.put(Element.life, tier1);
                map.put(Element.lightning, tier2);
                map.put(Element.ice, -tier0);
            }
            if (subSeason.equals(Season.SubSeason.MID_SUMMER)) {
                map.put(Element.fire, tier0);
                map.put(Element.lightning, tier1);
                map.put(Element.stone, tier2);
                map.put(Element.ice, -tier0);
            }
            if (subSeason.equals(Season.SubSeason.LATE_SUMMER)) {
                map.put(Element.lightning, tier0);
                map.put(Element.fire, tier1);
                map.put(Element.wind, tier2);
                map.put(Element.ice, -tier0);
            }
            if (subSeason.equals(Season.SubSeason.EARLY_AUTUMN)) {
                map.put(Element.wind, tier0);
                map.put(Element.fire, tier1);
                map.put(Element.lightning, tier2);
                map.put(Element.ice, -tier0);
            }
            if (subSeason.equals(Season.SubSeason.MID_AUTUMN)) {
                map.put(Element.wind, tier0);
                map.put(Element.stone, tier1);
                map.put(Element.water, tier2);
                map.put(Element.life, -tier0);
            }
            if (subSeason.equals(Season.SubSeason.LATE_AUTUMN)) {
                map.put(Element.wind, tier0);
                map.put(Element.stone, tier1);
                map.put(Element.ice, tier2);
                map.put(Element.fire, -tier0);
                map.put(Element.life, -tier1);
            }
            if (subSeason.equals(Season.SubSeason.EARLY_WINTER)) {
                map.put(Element.stone, tier0);
                map.put(Element.ice, tier1);
                map.put(Element.wind, tier2);
                map.put(Element.fire, -tier0);
                map.put(Element.life, -tier1);
                map.put(Element.water, -tier2);
                map.put(Element.lightning, -tier3);
            }
            if (subSeason.equals(Season.SubSeason.MID_WINTER)) {
                map.put(Element.ice, tier0);
                map.put(Element.stone, tier1);
                map.put(Element.wind, tier2);
                map.put(Element.fire, -tier0);
                map.put(Element.life, -tier1);
                map.put(Element.water, -tier2);
                map.put(Element.lightning, -tier3);
            }
            if (subSeason.equals(Season.SubSeason.LATE_WINTER)) {
                map.put(Element.ice, tier0);
                map.put(Element.stone, tier1);
                map.put(Element.wind, tier2);
                map.put(Element.fire, -tier0);
                map.put(Element.life, -tier1);
                map.put(Element.water, -tier2);
                map.put(Element.lightning, -tier3);
            }
            put(subSeason.name(), map);
        });

    }};

    public static Map<String, String> nameMap = new HashMap<>() {{
        Arrays.stream(Season.SubSeason.values()).forEach(subSeason -> {
            String prefix = "";
            if (subSeason.name().contains(early)) prefix = "孟";
            if (subSeason.name().contains(mid)) prefix = "仲";
            if (subSeason.name().contains(late)) prefix = "季";
            String suffix = "";
            if (subSeason.name().contains(spring)) suffix = "春";
            if (subSeason.name().contains(summer)) suffix = "夏";
            if (subSeason.name().contains(autumn)) suffix = "秋";
            if (subSeason.name().contains(winter)) suffix = "冬";
            put(subSeason.name(), prefix + suffix);
        });
    }};

    public static Map<String, Component> seasonComponentMap = new HashMap<>() {{
        Arrays.stream(Season.SubSeason.values()).forEach(subSeason -> {
            Style style;
            if (subSeason.name().contains(spring)) style = CustomStyle.styleOfLife;
            else if (subSeason.name().contains(summer)) style = CustomStyle.styleOfPower;
            else if (subSeason.name().contains(autumn)) style = CustomStyle.styleOfGold;
            else style = CustomStyle.styleOfSnow;
            put(subSeason.name(), Component.literal(nameMap.get(subSeason.name())).withStyle(style));
        });
    }};

    public static List<Component> getElementEffectContent(String season) {
        if (season == null) return null;
        List<Component> components = new ArrayList<>();
        Map<String, Double> singleElementEffectMap = seasonElementEffectMap.get(season);
        String[] name = {Element.life, Element.water, Element.fire, Element.stone, Element.ice, Element.lightning, Element.wind};
        Component[] description = {Element.Description.LifeElement("自然"),
                Element.Description.WaterElement("自然"),
                Element.Description.FireElement("自然"),
                Element.Description.StoneElement("自然"),
                Element.Description.IceElement("自然"),
                Element.Description.LightningElement("自然"),
                Element.Description.WindElement("自然")
        };
        for (int i = 0; i < name.length; i++) {
            double value = singleElementEffectMap.getOrDefault(name[i], 0d);
            components.add(Component.literal("").
                    append(description[i]).
                    append(getComponent(value)));
        }
        return components;
    }

    public static Component getComponent(double value) {
        if (value == 0d) return Component.literal(" - 0%").withStyle(ChatFormatting.WHITE);
        String format = String.format("%.0f%%", Math.abs(value) * 100);
        if (value > 0) return Component.literal(" ▲ " + format).withStyle(ChatFormatting.GREEN);
        else return Component.literal(" ▼ " + format).withStyle(ChatFormatting.RED);
    }

    public static double getCurrentSeasonElementEffect(String elementType) {
        if (currentSeason == null) return 0;
        Map<String, Double> map = seasonElementEffectMap.get(currentSeason);
        return map.getOrDefault(elementType, 0d);
    }

    public static double mobHurtDamageEffect(Mob mob) {
        Element.Unit unit = Element.entityElementUnit.getOrDefault(mob.getId(), new Element.Unit(Element.life, 0d));
        if (unit.value() == 0) return 0;
        return getCurrentSeasonElementEffect(unit.type());
    }

    public static double playerResonanceDamageEnhance(Player player) {
        if (!Element.PlayerResonanceType.containsKey(player)) return 0;
        return getCurrentSeasonElementEffect(Element.PlayerResonanceType.get(player));
    }

    public static double playerTotalDamageRate() {
        if (currentSeason.contains(spring) || currentSeason.contains(autumn)) {
            return 0.25;
        }
        return -0.25;
    }

    public static void tick(TickEvent.LevelTickEvent event) {
        if (event.side.isServer() && event.phase.equals(TickEvent.Phase.START) && event.level.equals(event.level.getServer().getLevel(Level.OVERWORLD))) {
            if (MySeason.currentSeason == null) {
                SeasonSavedData seasonData = SeasonHandler.getSeasonSavedData(event.level);
                SeasonTime time = new SeasonTime(seasonData.seasonCycleTicks);
                MySeason.currentSeason = time.getSubSeason().name();
            }
            if (MySeason.currentElementEffectBroadDelay == event.level.getServer().getTickCount()) {
                List<Component> components = MySeason.getElementEffectContent(currentSeason);
                if (components != null) {
                    List<ServerPlayer> playerList = event.level.getServer().getPlayerList().getPlayers();
                    playerList.forEach(MySeason::sendElementEffectInfoToPlayer);
                }
            }
            int tick = event.level.getServer().getTickCount();
            if (tick % 20 == 0) {
                playerInfoSendDelay.forEach((key, value) -> {
                    if (value < tick) {
                        sendExElementEffectInfoToPlayer(key);
                    }
                });
                playerInfoSendDelay.entrySet().removeIf(entry -> entry.getValue() < tick);
            }
        }
    }

    public static Map<Player, Integer> playerInfoSendDelay = new HashMap<>();

    public static void sendElementEffectInfoToPlayer(Player player) {
        List<Component> components = MySeason.getElementEffectContent(currentSeason);
        if (components != null) {
            Compute.sendFormatMSG(player, Component.literal("季节").withStyle(CustomStyle.styleOfLife), Component.literal("现在是 ").withStyle(ChatFormatting.WHITE).
                    append(MySeason.seasonComponentMap.get(MySeason.currentSeason)).
                    append(Component.literal(" 时节").withStyle(ChatFormatting.WHITE)));
            Compute.sendFormatMSG(player, Component.literal("季节").withStyle(CustomStyle.styleOfLife), Component.literal("").withStyle(ChatFormatting.WHITE).
                    append(MySeason.seasonComponentMap.get(MySeason.currentSeason)).
                    append(Component.literal(" 时分对元素的影响如下:").withStyle(ChatFormatting.WHITE)));
            components.forEach(component -> {
                Compute.msgSendToPlayer(player, component, 2);
            });
            playerInfoSendDelay.put(player, player.getServer().getTickCount() + 200);
            Compute.soundToPlayer(player, SoundEvents.EXPERIENCE_ORB_PICKUP);
        }
    }

    public static void sendExElementEffectInfoToPlayer(Player player) {
        Compute.sendFormatMSG(player, Component.literal("季节").withStyle(CustomStyle.styleOfLife), Component.literal("自然元素强度将会影响:").withStyle(ChatFormatting.WHITE));
        Compute.msgSendToPlayer(player, Component.literal("1.受到该元素影响的怪物受到的伤害（正的自然元素将减少怪物受到的伤害）").withStyle(ChatFormatting.WHITE), 2);
        Compute.msgSendToPlayer(player, Component.literal("2.玩家归一化元素强度的改变（将直接百分比改变玩家归一化元素强度）").withStyle(ChatFormatting.WHITE), 2);
        Compute.msgSendToPlayer(player, Component.literal("3.共鸣元素将会提供伤害影响").withStyle(ChatFormatting.WHITE), 2);
        Compute.sendFormatMSG(player, Component.literal("季节").withStyle(CustomStyle.styleOfLife), Component.literal("在").withStyle(ChatFormatting.WHITE).
                append(Component.literal("春").withStyle(CustomStyle.styleOfLife)).
                append(Component.literal("/").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("秋").withStyle(CustomStyle.styleOfGold)).
                append(Component.literal("两个怡人的季节，玩家造成的伤害将会").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("提升25%").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("，在").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("夏").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("/").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("冬").withStyle(CustomStyle.styleOfSnow)).
                append(Component.literal("两个较为不舒适的季节，玩家造成的伤害将会").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("降低25%").withStyle(ChatFormatting.GREEN)));
        Compute.soundToPlayer(player, SoundEvents.EXPERIENCE_ORB_PICKUP);
    }
}
