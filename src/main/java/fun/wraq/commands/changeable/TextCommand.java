package fun.wraq.commands.changeable;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextCommand implements Command<CommandSourceStack> {
    public static TextCommand instance = new TextCommand();


    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        String name = StringArgumentType.getString(context, "name");
        if (!player.isCreative()) {
            Compute.sendFormatMSG(player, Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA),
                    Component.literal("此命令仅管理员可用。").withStyle(ChatFormatting.WHITE));
        } else {
            List<Component> components = new ArrayList<>();
            Map<String, List<Component>> nameToTextMap = new HashMap<>() {{
                put("toSkyCityTpCenter", List.of(
                        Component.literal("前往:").withStyle(CustomStyle.styleOfEnd),
                        Component.literal("天空城位移中枢").withStyle(CustomStyle.styleOfEnd)
                ));
                put("toIceKnight", List.of(
                        Component.literal("前往:").withStyle(CustomStyle.styleOfEnd),
                        Component.literal("冰霜骑士驻地").withStyle(CustomStyle.styleOfIce)
                ));
                put("toStarDream", List.of(
                        Component.literal("前往:").withStyle(CustomStyle.styleOfEnd),
                        Component.literal("尘月之梦").withStyle(CustomStyle.styleOfMoon)
                ));
                put("toLightningIsland", List.of(
                        Component.literal("前往:").withStyle(CustomStyle.styleOfEnd),
                        Component.literal("雷光灯塔").withStyle(CustomStyle.styleOfLightning)
                ));
                put("toEasternTower", List.of(
                        Component.literal("前往:").withStyle(CustomStyle.styleOfEnd),
                        Component.literal("东洋塔").withStyle(CustomStyle.styleOfHusk)
                ));
                put("toOriginalForest", List.of(
                        Component.literal("前往:").withStyle(CustomStyle.styleOfEnd),
                        Component.literal("原始森林").withStyle(CustomStyle.styleOfForest)
                ));
                put("toMoontain", List.of(
                        Component.literal("前往:").withStyle(CustomStyle.styleOfEnd),
                        Component.literal("望山据点").withStyle(CustomStyle.styleOfMoontain)
                ));
                put("toSakura", List.of(
                        Component.literal("前往:").withStyle(CustomStyle.styleOfEnd),
                        Component.literal("绯樱村").withStyle(CustomStyle.styleOfSakura)
                ));
                put("toSunIsland", List.of(
                        Component.literal("前往:").withStyle(CustomStyle.styleOfEnd),
                        Component.literal("旭升岛").withStyle(CustomStyle.styleOfSunIsland)
                ));
                put("toSnowVillage", List.of(
                        Te.s("前往:", CustomStyle.styleOfEnd),
                        Te.s("北洋村", CustomStyle.styleOfSnow)
                ));
                put("toBlackCastle", List.of(
                        Te.s("前往:", CustomStyle.styleOfEnd),
                        Te.s("暗黑城堡", CustomStyle.styleOfCastle)
                ));
                put("toMushroom", List.of(
                        Te.s("前往:", CustomStyle.styleOfEnd),
                        Te.s("菌菇聚落", CustomStyle.MUSHROOM_STYLE)
                ));
                put("toXunXiVillage", List.of(
                        Te.s("前往:", CustomStyle.styleOfEnd),
                        Te.s("薰曦村", CustomStyle.styleOfJacaranda)
                ));
                put("toXunNanVillage", List.of(
                        Te.s("前往:", CustomStyle.styleOfEnd),
                        Te.s("薰楠村", CustomStyle.styleOfJacaranda)
                ));
                put("toManaRain", List.of(
                        Te.s("前往:", CustomStyle.styleOfEnd),
                        Te.s("炼雨府邸", CustomStyle.styleOfSea)
                ));
                put("toSeaVillage", List.of(
                        Te.s("前往:", CustomStyle.styleOfEnd),
                        Te.s("海岸村", CustomStyle.styleOfSea)
                ));
                put("toVolcanoVillage", List.of(
                        Te.s("前往:", CustomStyle.styleOfEnd),
                        Te.s("火山村", CustomStyle.styleOfVolcano)
                ));
                put("toOceanMonument", List.of(
                        Te.s("前往:", CustomStyle.styleOfEnd),
                        Te.s("海底神殿", CustomStyle.styleOfSea)
                ));
                put("toManaForest", List.of(
                        Te.s("前往:", CustomStyle.styleOfEnd),
                        Te.s("唤魔森林", CustomStyle.styleOfMana)
                ));
            }};
            if (nameToTextMap.containsKey(name)) {
                components = nameToTextMap.get(name);
            }
            if (name.equals(StringUtils.TextType.GoldCoinStore)) {
                components.add(Component.literal("樱岛黄金商店").withStyle(ChatFormatting.YELLOW));
            }
            if (name.equals(StringUtils.TextType.WelCome)) {
                components.add(Component.literal("欢迎来到维瑞阿契！").withStyle(ChatFormatting.AQUA));
                components.add(Component.literal("维瑞阿契qq交流群：184453807").withStyle(ChatFormatting.AQUA));
                components.add(Component.literal("内容尚未完善，如有任何疑惑或想法欢迎提交！").withStyle(CustomStyle.styleOfSakura));
            }
            if (name.equals(StringUtils.TextType.PlainStation0)) {
                components.add(Component.literal("往天空城方向").withStyle(CustomStyle.styleOfSky));
            }
            if (name.equals(StringUtils.TextType.PlainStation1)) {
                components.add(Component.literal("往樱岛方向").withStyle(CustomStyle.styleOfSakura));
            }
            if (name.equals(StringUtils.TextType.WorldPlain)) {
                components.add(Component.literal("世界本源解析装置-平原").withStyle(CustomStyle.styleOfWorld));
            }
            if (name.equals(StringUtils.TextType.WorldSky)) {
                components.add(Component.literal("世界本源解析装置-天空城").withStyle(CustomStyle.styleOfWorld));
            }
            if (name.equals(StringUtils.TextType.WorldSakura)) {
                components.add(Component.literal("世界本源解析装置-樱岛").withStyle(CustomStyle.styleOfWorld));
            }
            if (name.equals(StringUtils.TextType.Smithy)) {
                components.add(Component.literal("铁匠铺").withStyle(CustomStyle.styleOfMine));
            }
            if (name.equals(StringUtils.TextType.Bank)) {
                components.add(Component.literal("银行").withStyle(ChatFormatting.YELLOW));
            }
            if (name.equals(StringUtils.TextType.Nether)) {
                components.add(Component.literal("下界").withStyle(CustomStyle.styleOfNether));
                components.add(Component.literal("等级需求：").withStyle(ChatFormatting.AQUA).
                        append(Component.literal("50").withStyle(CustomStyle.styleOfNether)));
            }
            if (name.equals(StringUtils.TextType.Channel0)) {
                components.add(Component.literal("平原-森林快速通道-往森林").withStyle(CustomStyle.styleOfForest));
            }
            if (name.equals(StringUtils.TextType.Channel1)) {
                components.add(Component.literal("平原-森林快速通道-往平原").withStyle(CustomStyle.styleOfPlain));
            }
            if (name.equals(StringUtils.TextType.PF)) {
                components.add(Component.literal("平原-森林隐藏副本").withStyle(CustomStyle.styleOfHealth));
                components.add(Component.literal("刷新时间：").withStyle(ChatFormatting.AQUA).
                        append(Component.literal("8hrs").withStyle(CustomStyle.styleOfHealth)));
            }
            if (name.equals(StringUtils.TextType.NetherRequisition)) {
                components.add(Component.literal("下界征讨协会").withStyle(CustomStyle.styleOfQuartz));
            }
            if (name.equals(StringUtils.TextType.SkyCityGems)) {
                components.add(Component.literal("天空城珠宝商人").withStyle(CustomStyle.styleOfSky));
            }
            if (name.equals(StringUtils.TextType.SkyCityTower)) {
                components.add(Component.literal("天空城高塔").withStyle(CustomStyle.styleOfSky));
            }
            if (name.equals(StringUtils.TextType.Crest)) {
                components.add(Component.literal("纹章制作师").withStyle(CustomStyle.styleOfSky));
            }
            if (name.equals(StringUtils.TextType.Spider)) {
                components.add(Component.literal("微光研究所").withStyle(CustomStyle.styleOfSpider));
            }
            if (name.equals(StringUtils.TextType.Evoker)) {
                components.add(Component.literal("炼金术士").withStyle(CustomStyle.styleOfMana));
            }
            if (name.equals(StringUtils.TextType.Brew)) {
                components.add(Component.literal("天空城酿造所").withStyle(CustomStyle.styleOfBrew));
            }
            if (name.equals(StringUtils.TextType.SL)) {
                components.add(Component.literal("湖泊隐藏副本").withStyle(CustomStyle.styleOfLake));
                components.add(Component.literal("刷新时间：").withStyle(ChatFormatting.AQUA).
                        append(Component.literal("8hrs").withStyle(CustomStyle.styleOfLake)));

            }
            if (name.equals(StringUtils.TextType.SV)) {
                components.add(Component.literal("火山隐藏副本").withStyle(CustomStyle.styleOfVolcano));
                components.add(Component.literal("刷新时间：").withStyle(ChatFormatting.AQUA).
                        append(Component.literal("8hrs").withStyle(CustomStyle.styleOfVolcano)));
            }
            if (name.equals(StringUtils.TextType.VolcanoTP)) {
                components.add(Component.literal("天空城牵引器").withStyle(CustomStyle.styleOfSky));
            }
            if (name.equals(StringUtils.TextType.PurpleIronHelmet)) {
                components.add(Component.literal("打造：紫晶铁头盔").withStyle(CustomStyle.styleOfPurpleIron));
                components.add(Component.literal("材料需求：").withStyle(ChatFormatting.GOLD).
                        append(Component.literal("紫晶铁锭x256").withStyle(CustomStyle.styleOfPurpleIron)));
            }
            if (name.equals(StringUtils.TextType.PurpleIronChest)) {
                components.add(Component.literal("打造：紫晶铁胸甲").withStyle(CustomStyle.styleOfPurpleIron));
                components.add(Component.literal("材料需求：").withStyle(ChatFormatting.GOLD).
                        append(Component.literal("紫晶铁锭x256").withStyle(CustomStyle.styleOfPurpleIron)));
            }
            if (name.equals(StringUtils.TextType.PurpleIronLeggings)) {
                components.add(Component.literal("打造：紫晶铁护腿").withStyle(CustomStyle.styleOfPurpleIron));
                components.add(Component.literal("材料需求：").withStyle(ChatFormatting.GOLD).
                        append(Component.literal("紫晶铁锭x256").withStyle(CustomStyle.styleOfPurpleIron)));
            }
            if (name.equals(StringUtils.TextType.PurpleIronBoots)) {
                components.add(Component.literal("打造：紫晶铁靴子").withStyle(CustomStyle.styleOfPurpleIron));
                components.add(Component.literal("材料需求：").withStyle(ChatFormatting.GOLD).
                        append(Component.literal("紫晶铁锭x256").withStyle(CustomStyle.styleOfPurpleIron)));
            }
            if (name.equals(StringUtils.TextType.PurpleIronReset)) {
                components.add(Component.literal("重铸：紫晶铁系列装备").withStyle(CustomStyle.styleOfPurpleIron));
                components.add(Component.literal("材料需求：").withStyle(ChatFormatting.GOLD).
                        append(Component.literal("紫晶铁锭x64").withStyle(CustomStyle.styleOfPurpleIron)));
            }
            if (name.equals(StringUtils.TextType.PurpleIronPickaxe0)) {
                components.add(Component.literal("提示：当你的紫晶矿工击杀数达到").withStyle(CustomStyle.styleOfSakura).
                        append(Component.literal("200").withStyle(CustomStyle.styleOfPurpleIron)));
                components.add(Component.literal("会直接获得").withStyle(CustomStyle.styleOfSakura).
                        append(Component.literal("紫晶铁镐").withStyle(CustomStyle.styleOfPurpleIron)));
            }
            if (name.equals(StringUtils.TextType.PurpleIronPickaxe1)) {
                components.add(Component.literal("打造：紫晶铁镐¹").withStyle(CustomStyle.styleOfPurpleIron));
                components.add(Component.literal("材料需求：").withStyle(ChatFormatting.GOLD).
                        append(Component.literal("紫晶铁锭x128").withStyle(CustomStyle.styleOfPurpleIron)));
            }
            if (name.equals(StringUtils.TextType.PurpleIronPickaxe2)) {
                components.add(Component.literal("打造：紫晶铁镐²").withStyle(CustomStyle.styleOfPurpleIron));
                components.add(Component.literal("材料需求：").withStyle(ChatFormatting.GOLD).
                        append(Component.literal("紫晶铁锭x192").withStyle(CustomStyle.styleOfPurpleIron)));
            }
            if (name.equals(StringUtils.TextType.PurpleIronPickaxe3)) {
                components.add(Component.literal("打造：紫晶铁镐³").withStyle(CustomStyle.styleOfPurpleIron));
                components.add(Component.literal("材料需求：").withStyle(ChatFormatting.GOLD).
                        append(Component.literal("紫晶铁锭x256").withStyle(CustomStyle.styleOfPurpleIron)));
            }
            if (name.equals(StringUtils.TextType.PurpleIron)) {
                components.add(Component.literal("紫晶铁锻造").withStyle(CustomStyle.styleOfPurpleIron));
            }
            if (name.equals(StringUtils.TextType.IceArmor)) {
                components.add(Component.literal("重铸：冰霜骑士防具").withStyle(CustomStyle.styleOfIce));
                components.add(Component.literal("材料需求：").withStyle(ChatFormatting.GOLD).
                        append(Component.literal("冰霜骑士之心x8").withStyle(CustomStyle.styleOfIce)));
            }
            if (name.equals(StringUtils.TextType.GoldSmith)) {
                components.add(Component.literal("回收：锻造装备").withStyle(CustomStyle.styleOfGold));
                components.add(Component.literal("材料需求：").withStyle(ChatFormatting.AQUA).
                        append(Component.literal("任意锻造装备").withStyle(CustomStyle.styleOfGold)));
                components.add(Component.literal("手持锻造装备shift右击铁砧").withStyle(CustomStyle.styleOfGold));
            }

            if (name.equals(StringUtils.TextType.Blood)) {
                components.add(Component.literal("樱岛旧世魔力屋").withStyle(CustomStyle.styleOfBloodMana));
            }

            if (name.equals(StringUtils.TextType.ManaBook)) {
                components.add(Component.literal("魔导师协会").withStyle(CustomStyle.styleOfMana));
            }

            if (name.equals(StringUtils.TextType.BackEnd)) {
                components.add(Component.literal("返回终末之地").withStyle(CustomStyle.styleOfEnd));
            }

            if (name.equals("ToDivineIsland")) {
                components.add(Te.s("前往", "圣光岛", CustomStyle.DIVINE_STYLE));
                components.add(Te.s("等级需求:", Utils.getLevelDescription(230)));
            }

            if (name.equals("ToSunIsland")) {
                components.add(Te.s("前往", "旭升岛", CustomStyle.styleOfSunIsland));
            }

            Style style = CustomStyle.DIVINE_STYLE;
            Component divineName = Te.s("圣光岛", style);
            Component countName = ComponentUtils.getRightAngleQuote("圣光辐照", style);
            if (name.equals("SunIsland")) {
                components.add(Te.s("在", divineName, "每击杀一只怪物，会获得一层", countName));
                components.add(Te.s("至多叠加至", "1000层", style, "，每层", countName, "会使:"));
                components.add(Te.s(" 1.", style, "受到的伤害提升", "1%", style));
                components.add(Te.s(" 2.", style,
                        ComponentUtils.AttributeDescription.getElementStrength(""), "减少", "0.1%", style));
                components.add(Te.s(" 3.", style,
                        ComponentUtils.getCommonDamageEnhance(""), "提升", "0.2%", style));
                components.add(Te.s(" 受到怪物的伤害时，会减少5层", countName,
                        "并损失", ComponentUtils.AttributeDescription.maxHealth("5%")));
                components.add(Te.s(" 在脱离战斗60s后，每6s会减少10层", countName,
                        "并损失", ComponentUtils.AttributeDescription.maxHealth("10%")));
                components.add(Te.s(" 在", divineName, "击杀任意怪物，有", "10%", style, "触发", "圣光", style));
                components.add(Te.s(" 在1s的延迟后，扣除一定范围内的玩家",
                        ComponentUtils.AttributeDescription.health("")));
                components.add(Te.s(" 在", divineName, "，使用鞘翅也将受到", "圣光", style, "的辐照"));
            }

            ArmorStandSummon(components, player);
        }
        return 0;
    }

    public static void ArmorStandSummon(List<Component> components, Player player) {
        if (components.isEmpty()) {
            player.sendSystemMessage(Component.literal("检查参数"));
        } else {
            summonArmorStand(components, player.level(), player.pick(3, 0, false).getLocation());
            for (Component component : components) {
                player.sendSystemMessage(Component.literal("已生成").withStyle(ChatFormatting.WHITE).append(component));
            }
        }
    }

    public static void summonArmorStand(List<Component> components, Level level, Vec3 pos) {
        for (int i = 0; i < components.size(); i++) {
            ArmorStand armorStand = new ArmorStand(EntityType.ARMOR_STAND, level);
            armorStand.setNoGravity(true);
            armorStand.setCustomNameVisible(true);
            armorStand.setCustomName(components.get(i));
            armorStand.setInvulnerable(true);
            armorStand.setInvisible(true);
            armorStand.noPhysics = true;
            armorStand.setBoundingBox(AABB.ofSize(new Vec3(0, 0, 0), 0.1, 0.1, 0.1));
            armorStand.moveTo(pos.add(0, -0.25 * i, 0));
            level.addFreshEntity(armorStand);
        }
    }
}
