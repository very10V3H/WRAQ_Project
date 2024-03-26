package com.Very.very.Hcommand;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.StringUtils;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.ArmorStandModel;
import net.minecraft.client.renderer.entity.ArmorStandRenderer;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class TextCommand implements Command<CommandSourceStack> {
    public static TextCommand instance = new TextCommand();


    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        String name = StringArgumentType.getString(context, "name");
        CompoundTag dataP = player.getPersistentData();
        if (!player.isCreative()) {
            Compute.FormatMSGSend(player, Component.literal("维瑞阿契").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA),
                    Component.literal("此命令仅管理员可用。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
        } else {
            List<Component> components = new ArrayList<>();
            if (name.equals(StringUtils.TextType.GoldCoinStore)) {
                components.add(Component.literal("樱岛黄金商店").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.YELLOW));
            }
            if (name.equals(StringUtils.TextType.WelCome)) {
                components.add(Component.literal("欢迎来到维瑞阿契！").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA));
                components.add(Component.literal("维瑞阿契qq交流群：184453807").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA));
                components.add(Component.literal("内容尚未完善，如有任何疑惑或想法欢迎提交！").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSakura));
            }
            if (name.equals(StringUtils.TextType.PlainStation0)) {
                components.add(Component.literal("往天空城方向").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSky));
            }
            if (name.equals(StringUtils.TextType.PlainStation1)) {
                components.add(Component.literal("往樱岛方向").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSakura));
            }
            if (name.equals(StringUtils.TextType.WorldPlain)) {
                components.add(Component.literal("世界本源解析装置-平原").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfWorld));
            }
            if (name.equals(StringUtils.TextType.WorldSky)) {
                components.add(Component.literal("世界本源解析装置-天空城").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfWorld));
            }
            if (name.equals(StringUtils.TextType.WorldSakura)) {
                components.add(Component.literal("世界本源解析装置-樱岛").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfWorld));
            }
            if (name.equals(StringUtils.TextType.Smithy)) {
                components.add(Component.literal("铁匠铺").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfMine));
            }
            if (name.equals(StringUtils.TextType.Bank)) {
                components.add(Component.literal("银行").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.YELLOW));
            }
            if (name.equals(StringUtils.TextType.Nether)) {
                components.add(Component.literal("下界").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfNether));
                components.add(Component.literal("等级需求：").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA).
                        append(Component.literal("50").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfNether)));
            }
            if (name.equals(StringUtils.TextType.Channel0)) {
                components.add(Component.literal("平原-森林快速通道-往森林").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfForest));
            }
            if (name.equals(StringUtils.TextType.Channel1)) {
                components.add(Component.literal("平原-森林快速通道-往平原").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfPlain));
            }
            if (name.equals(StringUtils.TextType.PF)) {
                components.add(Component.literal("平原-森林隐藏副本").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfHealth));
                components.add(Component.literal("刷新时间：").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA).
                        append(Component.literal("8hrs").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfHealth)));
            }
            if (name.equals(StringUtils.TextType.NetherRequisition)) {
                components.add(Component.literal("下界征讨协会").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfQuartz));
            }
            if (name.equals(StringUtils.TextType.SkyCityGems)) {
                components.add(Component.literal("天空城珠宝商人").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSky));
            }
            if (name.equals(StringUtils.TextType.SkyCityTower)) {
                components.add(Component.literal("天空城高塔").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSky));
            }
            if (name.equals(StringUtils.TextType.Crest)) {
                components.add(Component.literal("纹章制作师").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSky));
            }
            if (name.equals(StringUtils.TextType.Spider)) {
                components.add(Component.literal("微光研究所").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSpider));
            }
            if (name.equals(StringUtils.TextType.Evoker)) {
                components.add(Component.literal("炼金术士").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfMana));
            }
            if (name.equals(StringUtils.TextType.Brew)) {
                components.add(Component.literal("天空城酿造所").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfBrew));
            }
            if (name.equals(StringUtils.TextType.SL)) {
                components.add(Component.literal("湖泊隐藏副本").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfLake));
                components.add(Component.literal("刷新时间：").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA).
                        append(Component.literal("8hrs").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfLake)));

            }
            if (name.equals(StringUtils.TextType.SV)) {
                components.add(Component.literal("火山隐藏副本").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfVolcano));
                components.add(Component.literal("刷新时间：").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA).
                        append(Component.literal("8hrs").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfVolcano)));
            }
            if (name.equals(StringUtils.TextType.VolcanoTP)) {
                components.add(Component.literal("天空城牵引器").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSky));
            }
            if (name.equals(StringUtils.TextType.PurpleIronHelmet)) {
                components.add(Component.literal("打造：紫晶铁头盔").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfPurpleIron));
                components.add(Component.literal("材料需求：").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD).
                        append(Component.literal("紫晶铁锭x256").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfPurpleIron)));
            }
            if (name.equals(StringUtils.TextType.PurpleIronChest)) {
                components.add(Component.literal("打造：紫晶铁胸甲").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfPurpleIron));
                components.add(Component.literal("材料需求：").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD).
                        append(Component.literal("紫晶铁锭x256").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfPurpleIron)));
            }
            if (name.equals(StringUtils.TextType.PurpleIronLeggings)) {
                components.add(Component.literal("打造：紫晶铁护腿").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfPurpleIron));
                components.add(Component.literal("材料需求：").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD).
                        append(Component.literal("紫晶铁锭x256").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfPurpleIron)));
            }
            if (name.equals(StringUtils.TextType.PurpleIronBoots)) {
                components.add(Component.literal("打造：紫晶铁靴子").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfPurpleIron));
                components.add(Component.literal("材料需求：").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD).
                        append(Component.literal("紫晶铁锭x256").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfPurpleIron)));
            }
            if (name.equals(StringUtils.TextType.PurpleIronReset)) {
                components.add(Component.literal("重铸：紫晶铁系列装备").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfPurpleIron));
                components.add(Component.literal("材料需求：").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD).
                        append(Component.literal("紫晶铁锭x64").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfPurpleIron)));
            }
            if (name.equals(StringUtils.TextType.PurpleIronPickaxe0)) {
                components.add(Component.literal("提示：当你的紫晶矿工击杀数达到").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSakura).
                        append(Component.literal("200").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfPurpleIron)));
                components.add(Component.literal("会直接获得").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSakura).
                        append(Component.literal("紫晶铁镐").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfPurpleIron)));
            }
            if (name.equals(StringUtils.TextType.PurpleIronPickaxe1)) {
                components.add(Component.literal("打造：紫晶铁镐¹").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfPurpleIron));
                components.add(Component.literal("材料需求：").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD).
                        append(Component.literal("紫晶铁锭x128").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfPurpleIron)));
            }
            if (name.equals(StringUtils.TextType.PurpleIronPickaxe2)) {
                components.add(Component.literal("打造：紫晶铁镐²").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfPurpleIron));
                components.add(Component.literal("材料需求：").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD).
                        append(Component.literal("紫晶铁锭x192").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfPurpleIron)));
            }
            if (name.equals(StringUtils.TextType.PurpleIronPickaxe3)) {
                components.add(Component.literal("打造：紫晶铁镐³").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfPurpleIron));
                components.add(Component.literal("材料需求：").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD).
                        append(Component.literal("紫晶铁锭x256").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfPurpleIron)));
            }
            if (name.equals(StringUtils.TextType.PurpleIron)) {
                components.add(Component.literal("紫晶铁锻造").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfPurpleIron));
            }
            if (name.equals(StringUtils.TextType.IceArmor)) {
                components.add(Component.literal("重铸：冰霜骑士防具").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfIce));
                components.add(Component.literal("材料需求：").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD).
                        append(Component.literal("冰霜骑士之心x8").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfIce)));
            }
            if (name.equals(StringUtils.TextType.GoldSmith)) {
                components.add(Component.literal("回收：锻造装备").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfGold));
                components.add(Component.literal("材料需求：").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA).
                        append(Component.literal("任意锻造装备").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfGold)));
                components.add(Component.literal("手持锻造装备shift右击铁砧").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfGold));
            }

            if (name.equals(StringUtils.TextType.Blood)) {
                components.add(Component.literal("樱岛旧世魔力屋").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfBloodMana));
            }

            if (name.equals(StringUtils.TextType.ManaBook)) {
                components.add(Component.literal("魔导师协会").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfMana));
            }
            ArmorStandSummon(components, player);

        }
        return 0;
    }

    public static void ArmorStandSummon(List<Component> components, Player player) {
        for (int i = 0; i < components.size(); i++) {
            ArmorStand armorStand = new ArmorStand(EntityType.ARMOR_STAND, player.level());
            armorStand.setNoGravity(true);
            armorStand.setCustomNameVisible(true);
            armorStand.setCustomName(components.get(i));
            armorStand.setInvulnerable(true);
            armorStand.setInvisible(true);
            armorStand.noPhysics = true;
            armorStand.setBoundingBox(AABB.ofSize(new Vec3(0, 0, 0), 0.1, 0.1, 0.1));
            armorStand.moveTo(player.pick(3, 0, false).getLocation().add(0, -0.25 * i, 0));
            player.level().addFreshEntity(armorStand);
        }
    }
}
