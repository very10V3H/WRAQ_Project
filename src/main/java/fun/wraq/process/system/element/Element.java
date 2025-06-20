package fun.wraq.process.system.element;

import com.mojang.datafixers.util.Pair;
import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.ElementEffectTimeS2CPacket;
import fun.wraq.networking.misc.ParticlePackets.ElementParticle.*;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.system.element.equipAndCurios.waterElement.WaterElementSword;
import fun.wraq.process.system.season.MySeason;
import fun.wraq.render.particles.ModParticles;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.divine.equip.armor.DivineArmorCommon;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class Element {
    public static String clientResonanceType;

    public static String Prefix = "Element";

    public static String life = Prefix + "Life";
    public static String water = Prefix + "Water";
    public static String fire = Prefix + "Fire";
    public static String stone = Prefix + "Stone";
    public static String ice = Prefix + "Ice";
    public static String lightning = Prefix + "Lightning";
    public static String wind = Prefix + "Wind";
    public static String NULL = Prefix + "Null";

    public static List<String> elementList = new ArrayList<>() {{
        addAll(List.of(life, water, fire, stone, ice, lightning, wind));
    }};

    public static Map<Item, Double> LifeElementValue = new HashMap<>();
    public static Map<Item, Double> WaterElementValue = new HashMap<>();
    public static Map<Item, Double> FireElementValue = new HashMap<>();
    public static Map<Item, Double> StoneElementValue = new HashMap<>();
    public static Map<Item, Double> IceElementValue = new HashMap<>();
    public static Map<Item, Double> LightningElementValue = new HashMap<>();
    public static Map<Item, Double> WindElementValue = new HashMap<>();

    public static Map<String, Map<Item, Double>> elementValueMap = new HashMap<>();
    public static Map<String, Map<Item, Double>> getElementValueMap() {
        if (elementValueMap.isEmpty()) {
            elementValueMap.put(life, LifeElementValue);
            elementValueMap.put(water, WaterElementValue);
            elementValueMap.put(fire, FireElementValue);
            elementValueMap.put(stone, StoneElementValue);
            elementValueMap.put(ice, IceElementValue);
            elementValueMap.put(lightning, LightningElementValue);
            elementValueMap.put(wind, WindElementValue);
        }
        return elementValueMap;
    }


    public static WeakHashMap<Entity, Integer> lifeElementParticle = new WeakHashMap<>();
    public static WeakHashMap<Entity, Integer> waterElementParticle = new WeakHashMap<>();
    public static WeakHashMap<Entity, Integer> fireElementParticle = new WeakHashMap<>();
    public static WeakHashMap<Entity, Integer> stoneElementParticle = new WeakHashMap<>();
    public static WeakHashMap<Entity, Integer> iceElementParticle = new WeakHashMap<>();
    public static WeakHashMap<Entity, Integer> lightningElementParticle = new WeakHashMap<>();
    public static WeakHashMap<Entity, Integer> windElementParticle = new WeakHashMap<>();

    public record Unit(String type, double value) {
    }

    public static Map<LivingEntity, Unit> entityElementUnit = new HashMap<>();

    public static WeakHashMap<Player, String> PlayerResonanceType = new WeakHashMap<>();

    public static Map<String, Item> elementItemMap;
    public static Map<String, Item> getElementItemMap() {
        if (elementItemMap == null) {
            elementItemMap = new HashMap<>() {{
                put(life, ModItems.LIFE_ELEMENT.get());
                put(water, ModItems.WATER_ELEMENT.get());
                put(fire, ModItems.FIRE_ELEMENT.get());
                put(stone, ModItems.STONE_ELEMENT.get());
                put(ice, ModItems.ICE_ELEMENT.get());
                put(lightning, ModItems.LIGHTNING_ELEMENT.get());
                put(wind, ModItems.WIND_ELEMENT.get());
            }};
        }
        return elementItemMap;
    }

    public static void provideElement(LivingEntity livingEntity, String type, double value) {
        // 季节影响怪物元素量
        if (livingEntity instanceof Mob) {
            value *= (1 + MySeason.getCurrentSeasonElementEffect(type));
        }
        entityElementUnit.put(livingEntity, new Unit(type, value));
        if (livingEntity instanceof Player player) ElementEffectTimeSend(player,
                getElementItemMap().get(type).getDefaultInstance(), 8888, (int) (value * 100), true);
    }

    public static void handleServerTick() {
        entityElementUnit.entrySet().removeIf(entry -> entry.getKey() == null
                || entry.getKey().isRemoved() || entry.getKey().isDeadOrDying());
    }

    public static void provideElement(Player player, String type, double value, int lastTick) {
        entityElementUnit.put(player, new Unit(type, value));
        ElementEffectTimeSend(player, getElementItemMap().get(type).getDefaultInstance(),
                lastTick, (int) (value * 100), false);
    }

    public static double ElementEffectAddToEntity(LivingEntity active, LivingEntity passive, String type, double value, boolean isAd, double damage) {
        if (!entityElementUnit.containsKey(passive)) {
            entityElementUnit.put(passive, new Unit(life, 0));
        }
        Unit passiveUnit = entityElementUnit.get(passive);
        if (passiveUnit.value == 0 && value == 0) return 1;
        if (passiveUnit.value == 0) {
            entityElementUnit.put(passive, new Unit(type, value));
            ElementParticleProvider(passive);
            return 1;
        }
        double reactionElementValue = Math.min(passiveUnit.value, value);
        double strongRate = 0.2 * reactionElementValue;
        double weakRate = -0.2 * reactionElementValue;
        if (passiveUnit.type.equals(type)) {
            if (passiveUnit.value < value) entityElementUnit.put(passive, new Unit(type, value));
            return 1;
        } else {
            if (passiveUnit.value >= value)
                entityElementUnit.put(passive, new Unit(passiveUnit.type, passiveUnit.value - value));
            else entityElementUnit.put(passive, new Unit(type, value - passiveUnit.value));
        }
        if (type.equals(life)) {
            if (passiveUnit.type.equals(water)) {
                return 1 + strongRate;
            }
            if (passiveUnit.type.equals(fire)) {
                return 1 + weakRate;
            }
            if (passiveUnit.type.equals(stone)) {
                return 1 + strongRate;
            }
            if (passiveUnit.type.equals(ice)) {
                return 1 + weakRate;
            }
            if (passiveUnit.type.equals(lightning)) {
                return 1 + strongRate;
            }
            if (passiveUnit.type.equals(wind)) {
                return 1 + weakRate;
            }
        }
        if (type.equals(water)) {
            WaterElementSword.Passive(active);
            if (passiveUnit.type.equals(life)) {
                return 1 + weakRate;
            }
            if (passiveUnit.type.equals(fire)) {
                return 1 + strongRate;
            }
            if (passiveUnit.type.equals(lightning)) {
                return 1 + weakRate;
            }
            if (passiveUnit.type.equals(wind)) {
                return 1 + strongRate;
            }
        }
        if (type.equals(fire)) {
            if (passiveUnit.type.equals(life)) {
                return 1 + strongRate;
            }
            if (passiveUnit.type.equals(water)) {
                return 1 + weakRate;
            }
            if (passiveUnit.type.equals(stone)) {
                return 1 + weakRate;
            }
            if (passiveUnit.type.equals(ice)) {
                return 1 + strongRate;
            }
            if (passiveUnit.type.equals(lightning)) {
                return 1 + weakRate;
            }
            if (passiveUnit.type.equals(wind)) {
                return 1 + strongRate;
            }
        }
        if (type.equals(stone)) {
            if (passiveUnit.type.equals(life)) {
                return 1 + weakRate;
            }
            if (passiveUnit.type.equals(fire)) {
                return 1 + strongRate;
            }
            if (passiveUnit.type.equals(ice)) {
                return 1 + weakRate;
            }
            if (passiveUnit.type.equals(wind)) {
                return 1 + strongRate;
            }
        }
        if (type.equals(ice)) {
            if (passiveUnit.type.equals(fire)) {
                return 1 + weakRate;
            }
            if (passiveUnit.type.equals(stone)) {
                return 1 + strongRate;
            }
            if (passiveUnit.type.equals(life)) {
                return 1 + strongRate;
            }
        }
        if (type.equals(lightning)) {
            if (passiveUnit.type.equals(life)) {
                return 1 + weakRate;
            }
            if (passiveUnit.type.equals(water)) {
                return 1 + strongRate;
            }
            if (passiveUnit.type.equals(fire)) {
                return 1 + strongRate;
            }
            if (passiveUnit.type.equals(wind)) {
                return 1 + weakRate;
            }
        }
        if (type.equals(wind)) {
            if (passiveUnit.type.equals(life)) {
                return 1 + strongRate;
            }
            if (passiveUnit.type.equals(water)) {
                return 1 + weakRate;
            }
            if (passiveUnit.type.equals(fire)) {
                return 1 + weakRate;
            }
            if (passiveUnit.type.equals(stone)) {
                return 1 + weakRate;
            }
            if (passiveUnit.type.equals(lightning)) {
                return 1 + strongRate;
            }
        }
        ElementParticleProvider(passive);
        return 1;
    }

    public static void ElementParticleProvider(LivingEntity passive) {
        entityElementUnit.entrySet().removeIf(entry -> entry.getKey() == null || entry.getKey().isDeadOrDying());
        Unit unit = entityElementUnit.getOrDefault(passive, new Unit(life, 0));
        List<Player> players = passive.level().getEntitiesOfClass(Player.class, AABB.ofSize(passive.position(), 50, 50, 50));
        players.removeIf(player -> player.distanceTo(passive) > 20);
        if (unit.value > 0) {
            if (unit.type.equals(life)) players.forEach(player ->
                    ModNetworking.sendToClient(new LifeElementParticleS2CPacket(passive.getId(), 200), (ServerPlayer) player));
            if (unit.type.equals(water)) players.forEach(player ->
                    ModNetworking.sendToClient(new WaterElementParticleS2CPacket(passive.getId(), 200), (ServerPlayer) player));
            if (unit.type.equals(fire)) players.forEach(player ->
                    ModNetworking.sendToClient(new FireElementParticleS2CPacket(passive.getId(), 200), (ServerPlayer) player));
            if (unit.type.equals(stone)) players.forEach(player ->
                    ModNetworking.sendToClient(new StoneElementParticleS2CPacket(passive.getId(), 200), (ServerPlayer) player));
            if (unit.type.equals(ice)) players.forEach(player ->
                    ModNetworking.sendToClient(new IceElementParticleS2CPacket(passive.getId(), 200), (ServerPlayer) player));
            if (unit.type.equals(lightning)) players.forEach(player ->
                    ModNetworking.sendToClient(new LightningElementParticleS2CPacket(passive.getId(), 200), (ServerPlayer) player));
            if (unit.type.equals(wind)) players.forEach(player ->
                    ModNetworking.sendToClient(new WindElementParticleS2CPacket(passive.getId(), 200), (ServerPlayer) player));
        } else {
            players.forEach(player -> {
                ModNetworking.sendToClient(new ClearElementParticleS2CPacket(passive.getId()), (ServerPlayer) player);
            });
        }
    }

    public static void ElementParticleCreate(Player player) {
        if (player.tickCount % 50 == 0) {
            List<Mob> mobList = player.level().getEntitiesOfClass(Mob.class,
                    AABB.ofSize(player.position(), 32, 32, 32));
            mobList.forEach(Element::ElementParticleProvider);
        }
    }

    public static void PlayerTick(Player player) {
        ElementParticleCreate(player);
        if (player.tickCount % 5 == 0) {
            if (!ElementPieceOnWeapon(player)) {
                giveResonanceElement(player);
            }
        }
    }

    public static void ClientRemoveEntityParticle(Entity entity) {
        Element.fireElementParticle.remove(entity);
        Element.iceElementParticle.remove(entity);
        Element.lifeElementParticle.remove(entity);
        Element.lightningElementParticle.remove(entity);
        Element.stoneElementParticle.remove(entity);
        Element.waterElementParticle.remove(entity);
        Element.windElementParticle.remove(entity);
    }

    public static List<Item> elementItemList = new ArrayList<>();

    public static void setElementList() {
        Item[] items = {
                ModItems.LIFE_ELEMENT.get(),
                ModItems.WATER_ELEMENT.get(),
                ModItems.FIRE_ELEMENT.get(),
                ModItems.STONE_ELEMENT.get(),
                ModItems.ICE_ELEMENT.get(),
                ModItems.LIGHTNING_ELEMENT.get(),
                ModItems.WIND_ELEMENT.get()
        };
        elementItemList.addAll(List.of(items));
    }

    public static Vec3 calculateViewVector(float p_20172_, float p_20173_) {
        float f = p_20172_ * ((float) Math.PI / 180F);
        float f1 = -p_20173_ * ((float) Math.PI / 180F);
        float f2 = Mth.cos(f1);
        float f3 = Mth.sin(f1);
        float f4 = Mth.cos(f);
        float f5 = Mth.sin(f);
        return new Vec3((double) (f3 * f4), (double) (-f5), (double) (f2 * f4));
    }

    public static Vec3 getPos(Entity entity) {
        return calculateViewVector(0.0F, entity.getYRot() + 80).scale(0.5D);
    }

    public static void RangeElementProvider(LivingEntity active, String type, double value, boolean isAd, double damage, double r) {
        Map<String, ParticleOptions> map = new HashMap<>() {{
            put(life, ModParticles.LifeElementParticle.get());
            put(water, ModParticles.WaterElementParticle.get());
            put(fire, ModParticles.FireElementParticle.get());
            put(stone, ModParticles.StoneElementParticle.get());
            put(ice, ModParticles.IceElementParticle.get());
            put(lightning, ModParticles.LightningElementParticle.get());
            put(wind, ModParticles.WindElementParticle.get());
        }};
        List<Mob> mobList = active.level().getEntitiesOfClass(Mob.class, AABB.ofSize(active.position(), r * Math.sqrt(2), r * Math.sqrt(2), r * Math.sqrt(2)));
        mobList.removeIf(mob -> mob.distanceTo(active) > r);
        mobList.forEach(mob -> {
            ElementEffectAddToEntity(active, mob, type, value, isAd, damage);
        });
        ParticleProvider.DisperseParticle(active.position(), (ServerLevel) active.level(), 1, 1, 120, map.get(type), 1);
        ParticleProvider.DisperseParticle(active.position(), (ServerLevel) active.level(), 1.5, 1, 120, map.get(type), 1);
    }

    public static boolean ElementPieceOnWeapon(Player player) {
        if (!Utils.mainHandTag.containsKey(player.getMainHandItem().getItem())) return false;
        int slotIndex = player.getInventory().selected + 27;

        Map<Item, String> map = new HashMap<>() {{
            put(ModItems.LIFE_ELEMENT_PIECE_2.get(), life);
            put(ModItems.WATER_ELEMENT_PIECE_2.get(), water);
            put(ModItems.FIRE_ELEMENT_PIECE_2.get(), fire);
            put(ModItems.STONE_ELEMENT_PIECE_2.get(), stone);
            put(ModItems.ICE_ELEMENT_PIECE_2.get(), ice);
            put(ModItems.LIGHTNING_ELEMENT_PIECE_2.get(), lightning);
            put(ModItems.WIND_ELEMENT_PIECE_2.get(), wind);
        }};

        ItemStack itemStack = player.getInventory().getItem(slotIndex);
        if (!map.containsKey(itemStack.getItem())) return false;
        String type = map.get(itemStack.getItem());
        provideElement(player, type, ElementValue.getElementValueJudgeByType(player, type) * 0.01 * itemStack.getCount());
        return itemStack.getCount() == 0;
    }

    public static void giveResonanceElement(Player player) {
        if (PowerResonanceElementCoverTickMap.containsKey(player)
                && PowerResonanceElementCoverTickMap.get(player) > Tick.get()) {
            provideElement(player, PowerResonanceElementCoverTypeMap.get(player),
                    ElementValue.getElementValueJudgeByType(player, PowerResonanceElementCoverTypeMap.get(player)),
                    Tick.get() - PowerResonanceElementCoverTickMap.get(player));
        } else if (PlayerResonanceType.containsKey(player)) {
            provideElement(player, PlayerResonanceType.get(player),
                    ElementValue.getElementValueJudgeByType(player, PlayerResonanceType.get(player)));
        } else if (!PlayerResonanceType.containsKey(player)) {
            provideElement(player, life, 0);
        }
    }

    public static Map<String, String> nameMap = new HashMap<>() {{
        put(life, "生机元素");
        put(water, "碧水元素");
        put(fire, "炽焰元素");
        put(stone, "层岩元素");
        put(ice, "凛冰元素");
        put(lightning, "怒雷元素");
        put(wind, "澄风元素");
    }};

    public static Map<String, String> shortNameMap = new HashMap<>() {{
        put(life, "生机");
        put(water, "碧水");
        put(fire, "炽焰");
        put(stone, "层岩");
        put(ice, "凛冰");
        put(lightning, "怒雷");
        put(wind, "澄风");
    }};

    public static Map<String, Style> styleMap = new HashMap<>() {{
        put(life, CustomStyle.styleOfLife);
        put(water, CustomStyle.styleOfWater);
        put(fire, CustomStyle.styleOfFire);
        put(stone, CustomStyle.styleOfStone);
        put(ice, CustomStyle.styleOfIce);
        put(lightning, CustomStyle.styleOfLightning);
        put(wind, CustomStyle.styleOfWind);
    }};

    public static Component getElementDescription(String type) {
        return Te.s(nameMap.get(type), styleMap.get(type));
    }

    @Nullable
    public static String getResonanceType(Player player) {
        return PlayerResonanceType.getOrDefault(player, null);
    }

    public static Style getManaSkillParticleStyle(Player player) {
        return styleMap.getOrDefault(getResonanceType(player), CustomStyle.styleOfMana);
    }

    public static void resonance(Player player, String type) {
        if (Compute.playerIsInBattle(player) && !DivineArmorCommon.isWearingDivineArmor(player)) {
            Compute.sendFormatMSG(player, Component.literal("元素").withStyle(ChatFormatting.LIGHT_PURPLE),
                    Component.literal("请脱离战斗状态后重试").withStyle(ChatFormatting.WHITE));
            return;
        }

        if (PlayerResonanceType.containsKey(player) && PlayerResonanceType.get(player).equals(type)) {
            PlayerResonanceType.remove(player);
            Compute.sendFormatMSG(player, Component.literal("元素").withStyle(ChatFormatting.LIGHT_PURPLE),
                    Component.literal("共鸣").withStyle(CustomStyle.styleOfMoon).
                            append(Component.literal("类型已变更为 ").withStyle(ChatFormatting.WHITE)).
                            append(Component.literal("无")).withStyle(ChatFormatting.WHITE));
            return;
        }

        player.getPersistentData().putString(StringUtils.ResonanceType, type);
        PlayerResonanceType.put(player, type);
        Map<String, String> componentMap = new HashMap<>() {{
            put(life, "「春风化雨，生机盎然」");
            put(water, "「碧水如镜，映照山峦」");
            put(fire, "「炽焰奔腾，破云而出」");
            put(stone, "「层岩叠嶂，峭壁千仞」");
            put(ice, "「凛冰覆地，寒光闪烁」");
            put(lightning, "「怒雷轰鸣，震天撼地」");
            put(wind, "「澄风轻拂，云卷云舒」");
        }};
        if (!nameMap.containsKey(type)) return;
        Compute.sendFormatMSG(player, Component.literal("元素").withStyle(ChatFormatting.LIGHT_PURPLE),
                Component.literal("共鸣").withStyle(CustomStyle.styleOfMoon).
                        append(Component.literal("类型已变更为 ").withStyle(ChatFormatting.WHITE)).
                        append(Component.literal(nameMap.get(type))).withStyle(styleMap.get(type)));
        if (Compute.playerIsInBattle(player)) {
            Compute.setPlayerShortTitleAndSubTitle(player, Te.s(nameMap.get(type), styleMap.get(type)),
                    Te.s(componentMap.get(type), styleMap.get(type)));
        } else {
            Compute.setPlayerTitleAndSubTitle(player, Te.s(nameMap.get(type), styleMap.get(type)),
                    Te.s(componentMap.get(type), styleMap.get(type)));
        }
    }

    public static WeakHashMap<Player, String> PowerResonanceElementCoverTypeMap = new WeakHashMap<>();
    public static WeakHashMap<Player, Integer> PowerResonanceElementCoverTickMap = new WeakHashMap<>();

    public static void PowerResonance(Player player, String type, int lastTick) {
        PowerResonanceElementCoverTypeMap.put(player, type);
        PowerResonanceElementCoverTickMap.put(player, Tick.get() + lastTick);
    }

    public static void ElementEffectTimeSend(Player player, ItemStack itemStack, int tickCount, int level, boolean NoTime) {
        ModNetworking.sendToClient(new ElementEffectTimeS2CPacket(itemStack, tickCount, level, NoTime), (ServerPlayer) player);
    }

    public static class Description {
        public static Component LifeElement(String content) {
            return Component.literal("「" + content + "生机元素" + "」").withStyle(CustomStyle.styleOfLife);
        }

        public static Component WaterElement(String content) {
            return Component.literal("「" + content + "碧水元素" + "」").withStyle(CustomStyle.styleOfWater);
        }

        public static Component FireElement(String content) {
            return Component.literal("「" + content + "炽焰元素" + "」").withStyle(CustomStyle.styleOfFire);
        }

        public static Component StoneElement(String content) {
            return Component.literal("「" + content + "层岩元素" + "」").withStyle(CustomStyle.styleOfStone);
        }

        public static Component IceElement(String content) {
            return Component.literal("「" + content + "凛冰元素" + "」").withStyle(CustomStyle.styleOfIce);
        }

        public static Component LightningElement(String content) {
            return Component.literal("「" + content + "怒雷元素" + "」").withStyle(CustomStyle.styleOfLightning);
        }

        public static Component WindElement(String content) {
            return Component.literal("「" + content + "澄风元素" + "」").withStyle(CustomStyle.styleOfWind);
        }

        public static Component LifeElementValue(String content) {
            return Component.literal("「" + content + "归一化生机元素强度" + "」").withStyle(CustomStyle.styleOfLife);
        }

        public static Component WaterElementValue(String content) {
            return Component.literal("「" + content + "归一化碧水元素强度" + "」").withStyle(CustomStyle.styleOfWater);
        }

        public static Component FireElementValue(String content) {
            return Component.literal("「" + content + "归一化炽焰元素强度" + "」").withStyle(CustomStyle.styleOfFire);
        }

        public static Component StoneElementValue(String content) {
            return Component.literal("「" + content + "归一化层岩元素强度" + "」").withStyle(CustomStyle.styleOfStone);
        }

        public static Component IceElementValue(String content) {
            return Component.literal("「" + content + "归一化凛冰元素强度" + "」").withStyle(CustomStyle.styleOfIce);
        }

        public static Component LightningElementValue(String content) {
            return Component.literal("「" + content + "归一化怒雷元素强度" + "」").withStyle(CustomStyle.styleOfLightning);
        }

        public static Component WindElementValue(String content) {
            return Component.literal("「" + content + "归一化澄风元素强度" + "」").withStyle(CustomStyle.styleOfWind);
        }
    }

    public static class ReactionDescription {
        private static final Map<Pair<String, String>, Pair<Component, Component>> reactionDescription = new HashMap<>() {{
            put(new Pair<>(life, water), new Pair<>(Te.m("滋养", CustomStyle.styleOfLife), Te.m("为被动方回复").
                    append(ComponentUtils.AttributeDescription.health("10%"))));
            put(new Pair<>(life, fire), new Pair<>(Te.m("燃料", CustomStyle.styleOfFire), Te.m("增幅本次伤害", CustomStyle.styleOfPower).
                    append(Te.m("数值等同于反应量"))));
            put(new Pair<>(life, stone), new Pair<>(Te.m("汲取", CustomStyle.styleOfLife), Te.m("为主动方回复").
                    append(ComponentUtils.AttributeDescription.health("10%"))));
            put(new Pair<>(life, ice), new Pair<>(Te.m("霜冻", CustomStyle.styleOfLife), Te.m("提升被动方在5s内").
                    append(Te.m("受到的伤害", ChatFormatting.RED)).
                    append(Te.m("，数额等同于反应量"))));
            put(new Pair<>(life, lightning), new Pair<>(Te.m("氮化", CustomStyle.styleOfLightning), Te.m("为被动方每秒回复").
                    append(ComponentUtils.AttributeDescription.health("5%"))));
            put(new Pair<>(life, wind), new Pair<>(Te.m("断裂", CustomStyle.styleOfWind), Te.m("降低被动方").
                    append(ComponentUtils.AttributeDescription.defence("10%")).
                    append(Te.m("与")).
                    append(ComponentUtils.AttributeDescription.manaDefence("10%"))));

            put(new Pair<>(water, fire), new Pair<>(Te.m("蒸发", CustomStyle.styleOfWater), Te.m("增幅本次伤害", CustomStyle.styleOfPower).
                    append(Te.m("数值等同于反应量"))));
            put(new Pair<>(water, stone), new Pair<>(Te.m("泥化", CustomStyle.styleOfHusk), Te.m("削减", ChatFormatting.GRAY).
                    append(Te.m("被动方将受到的3次伤害"))));
            put(new Pair<>(water, ice), new Pair<>(Te.m("冰水", CustomStyle.styleOfIce), Te.m("回敬", ChatFormatting.LIGHT_PURPLE).
                    append(Te.m("被动方将受到的3次伤害"))));
            put(new Pair<>(water, lightning), new Pair<>(Te.m("感电", CustomStyle.styleOfLightning), Te.m("使被动方在接下来的3s内每秒受到").
                    append(Te.m("等级 * 500", ChatFormatting.LIGHT_PURPLE)).
                    append(Te.m("的伤害"))));
            put(new Pair<>(water, wind), new Pair<>(Te.m("风浪", CustomStyle.styleOfWater), Te.m("击飞被动方", CustomStyle.styleOfWind)));

            put(new Pair<>(fire, stone), new Pair<>(Te.m("灰化", CustomStyle.styleOfHusk), Te.m("增幅本次伤害", CustomStyle.styleOfPower).
                    append(Te.m("数值等同于反应量"))));
            put(new Pair<>(fire, ice), new Pair<>(Te.m("融化", CustomStyle.styleOfFire), Te.m("增幅本次伤害", CustomStyle.styleOfPower).
                    append(Te.m("数值等同于反应量"))));
            put(new Pair<>(fire, lightning), new Pair<>(Te.m("爆裂", CustomStyle.styleOfFire), Te.m("在被动方位置造成一次").
                    append(Te.m("范围伤害", CustomStyle.styleOfPower))));
            put(new Pair<>(fire, wind), new Pair<>(Te.m("旺盛", CustomStyle.styleOfFire), Te.m("使被动方获得持续5s的").
                    append(Te.m("伤害提升", CustomStyle.styleOfPower))));

            put(new Pair<>(stone, ice), new Pair<>(Te.m("冻土", CustomStyle.styleOfIce), Te.m("使被动方").
                    append(Te.m("受到的伤害增加", ChatFormatting.RED)).
                    append(Te.m("，持续5s"))));
            put(new Pair<>(stone, lightning), new Pair<>(Te.m("接地", CustomStyle.styleOfLightning), Te.m("使被动方获得持续5s的").
                    append(Te.m("伤害提升", CustomStyle.styleOfPower))));
            put(new Pair<>(stone, wind), new Pair<>(Te.m("风化", CustomStyle.styleOfWind), Te.m("使被动方获得持续5s的").
                    append(Te.m("造成/受到伤害提升", CustomStyle.styleOfPower))));

            put(new Pair<>(ice, lightning), new Pair<>(Te.m("超导", CustomStyle.styleOfLightning), Te.m("降低被动方").
                    append(ComponentUtils.AttributeDescription.defence("10%")).
                    append(Te.m("与")).
                    append(ComponentUtils.AttributeDescription.manaDefence("10%"))));
            put(new Pair<>(ice, wind), new Pair<>(Te.m("风寒", CustomStyle.styleOfWind), Te.m("降低被动方").
                    append(ComponentUtils.AttributeDescription.healValue("10%"))));

            put(new Pair<>(lightning, wind), new Pair<>(Te.m("灾难", CustomStyle.styleOfLightning), Te.m("在被动方位置造成一次").
                    append(Te.m("范围伤害", CustomStyle.styleOfPower))));
        }};

        public static Pair<Component, Component> getReactionPair(Pair<String, String> react) {
            Pair<String, String> reversePair = new Pair<>(react.getSecond(), react.getFirst());
            if (reactionDescription.containsKey(react)) {
                return reactionDescription.get(react);
            }
            if (reactionDescription.containsKey(reversePair)) {
                return reactionDescription.get(reversePair);
            }
            return new Pair<>(Component.literal(""), Component.literal(""));
        }
    }

    public static Map<String, String> typeLocationMap = new HashMap<>() {{
        put(Element.life, "life_element");
        put(Element.water, "water_element");
        put(Element.fire, "fire_element");
        put(Element.stone, "stone_element");
        put(Element.ice, "ice_element");
        put(Element.lightning, "lightning_element");
        put(Element.wind, "wind_element");
    }};

    public static ResourceLocation getResource(String type) {
        return new ResourceLocation(Utils.MOD_ID, "textures/hud/" + typeLocationMap.get(type) + ".png");
    }

    public static List<Item> getPiece0Items() {
        return List.of(
                ModItems.LIFE_ELEMENT_PIECE_0.get(),
                ModItems.WATER_ELEMENT_PIECE_0.get(),
                ModItems.FIRE_ELEMENT_PIECE_0.get(),
                ModItems.STONE_ELEMENT_PIECE_0.get(),
                ModItems.ICE_ELEMENT_PIECE_0.get(),
                ModItems.LIGHTNING_ELEMENT_PIECE_0.get(),
                ModItems.WIND_ELEMENT_PIECE_0.get()
        );
    }

    private static final Map<String, Item> piece0ItemMap = new HashMap<>();
    public static Map<String, Item> getPiece0ItemMap() {
        if (piece0ItemMap.isEmpty()) {
            piece0ItemMap.put(life, ModItems.LIFE_ELEMENT_PIECE_0.get());
            piece0ItemMap.put(water, ModItems.WATER_ELEMENT_PIECE_0.get());
            piece0ItemMap.put(fire, ModItems.FIRE_ELEMENT_PIECE_0.get());
            piece0ItemMap.put(stone, ModItems.STONE_ELEMENT_PIECE_0.get());
            piece0ItemMap.put(ice, ModItems.ICE_ELEMENT_PIECE_0.get());
            piece0ItemMap.put(wind, ModItems.WIND_ELEMENT_PIECE_0.get());
            piece0ItemMap.put(lightning, ModItems.LIGHTNING_ELEMENT_PIECE_0.get());
        }
        return piece0ItemMap;
    }

    private static final Map<String, Item> piece1ItemMap = new HashMap<>();
    public static Map<String, Item> getPiece1ItemMap() {
        if (piece1ItemMap.isEmpty()) {
            piece1ItemMap.put(life, ModItems.LIFE_ELEMENT_PIECE_1.get());
            piece1ItemMap.put(water, ModItems.WATER_ELEMENT_PIECE_1.get());
            piece1ItemMap.put(fire, ModItems.FIRE_ELEMENT_PIECE_1.get());
            piece1ItemMap.put(stone, ModItems.STONE_ELEMENT_PIECE_1.get());
            piece1ItemMap.put(ice, ModItems.ICE_ELEMENT_PIECE_1.get());
            piece1ItemMap.put(wind, ModItems.WIND_ELEMENT_PIECE_1.get());
            piece1ItemMap.put(lightning, ModItems.LIGHTNING_ELEMENT_PIECE_1.get());
        }
        return piece1ItemMap;
    }

    private static final Map<Item, String> piece1ToElementMap = new HashMap<>();
    public static Map<Item, String> getPiece1ToElementMap() {
        if (piece1ToElementMap.isEmpty()) {
            piece1ToElementMap.put(ModItems.LIFE_ELEMENT_PIECE_1.get(), life);
            piece1ToElementMap.put(ModItems.WATER_ELEMENT_PIECE_1.get(), water);
            piece1ToElementMap.put(ModItems.FIRE_ELEMENT_PIECE_1.get(), fire);
            piece1ToElementMap.put(ModItems.STONE_ELEMENT_PIECE_1.get(), stone);
            piece1ToElementMap.put(ModItems.ICE_ELEMENT_PIECE_1.get(), ice);
            piece1ToElementMap.put(ModItems.WIND_ELEMENT_PIECE_1.get(), wind);
            piece1ToElementMap.put(ModItems.LIGHTNING_ELEMENT_PIECE_1.get(), lightning);
        }
        return piece1ToElementMap;
    }

    private static final Map<String, Item> piece2ItemMap = new HashMap<>();
    public static Map<String, Item> getPiece2ItemMap() {
        if (piece2ItemMap.isEmpty()) {
            piece2ItemMap.put(life, ModItems.LIFE_ELEMENT_PIECE_2.get());
            piece2ItemMap.put(water, ModItems.WATER_ELEMENT_PIECE_2.get());
            piece2ItemMap.put(fire, ModItems.FIRE_ELEMENT_PIECE_2.get());
            piece2ItemMap.put(stone, ModItems.STONE_ELEMENT_PIECE_2.get());
            piece2ItemMap.put(ice, ModItems.ICE_ELEMENT_PIECE_2.get());
            piece2ItemMap.put(wind, ModItems.WIND_ELEMENT_PIECE_2.get());
            piece2ItemMap.put(lightning, ModItems.LIGHTNING_ELEMENT_PIECE_2.get());
        }
        return piece2ItemMap;
    }

    private static final Map<Item, String> piece2ToElementMap = new HashMap<>();
    public static Map<Item, String> getPiece2ToElementMap() {
        if (piece2ToElementMap.isEmpty()) {
            piece2ToElementMap.put(ModItems.LIFE_ELEMENT_PIECE_2.get(), life);
            piece2ToElementMap.put(ModItems.WATER_ELEMENT_PIECE_2.get(), water);
            piece2ToElementMap.put(ModItems.FIRE_ELEMENT_PIECE_2.get(), fire);
            piece2ToElementMap.put(ModItems.STONE_ELEMENT_PIECE_2.get(), stone);
            piece2ToElementMap.put(ModItems.ICE_ELEMENT_PIECE_2.get(), ice);
            piece2ToElementMap.put(ModItems.WIND_ELEMENT_PIECE_2.get(), wind);
            piece2ToElementMap.put(ModItems.LIGHTNING_ELEMENT_PIECE_2.get(), lightning);
        }
        return piece2ToElementMap;
    }
}
