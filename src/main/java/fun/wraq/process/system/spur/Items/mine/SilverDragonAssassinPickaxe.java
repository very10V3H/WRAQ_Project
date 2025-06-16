package fun.wraq.process.system.spur.Items.mine;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqSword;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.EnhanceNormalAttackModifier;
import fun.wraq.process.func.power.WraqPower;
import fun.wraq.process.system.spur.events.MineSpur;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.crystal.CrystalItems;
import fun.wraq.series.events.SpecialEventItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SilverDragonAssassinPickaxe extends WraqSword implements ActiveItem {

    public final int tier;
    public static List<Item> list = new ArrayList<>();
    public SilverDragonAssassinPickaxe(Properties properties, int tier) {
        super(properties);
        this.tier = tier;
        Utils.attackDamage.put(this, new double[]{120, 240, 1200, 2400, 3600}[tier]);
        Utils.defencePenetration0.put(this, new double[]{6, 18, 29, 36, 80}[tier]);
        Utils.healthSteal.put(this, 0.08);
        Utils.critRate.put(this, 0.35);
        Utils.critDamage.put(this, new double[]{0.02, 0.05, 0.08, 0.12, 0.2}[tier]);
        Utils.levelRequire.put(this, (tier + 1) * 50);
        list.add(this);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.SILVER_DRAGON_STYLE;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Te.s("旧世刺客之道", getMainStyle()));
        components.add(Te.s(" 基于", "采矿等级", CustomStyle.styleOfMine,
                "增幅", ComponentUtils.AttributeDescription.critDamage("2% * 采矿等级")));
        if (tier < 2) {
            return components;
        }
        ComponentUtils.descriptionActive(components, Te.s("刺杀", getMainStyle()));
        components.add(Te.s(" 位移至准星选定目标后"));
        components.add(Te.s(" 下一次普通攻击倍率将调整为", "500%", CustomStyle.styleOfPower));
        components.add(Te.s(" · 冷却时间 4s", ChatFormatting.AQUA));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfSilverDragon();
    }

    @Override
    public void active(Player player) {
        if (tier < 2) {
            return;
        }
        Mob mob = WraqPower.getDefaultTargetMobList(player).stream().min(new Comparator<Mob>() {
            @Override
            public int compare(Mob o1, Mob o2) {
                return (int) (o1.distanceTo(player) - o2.distanceTo(player));
            }
        }).orElse(null);
        if (mob != null) {
            Vec3 delta = mob.getEyePosition().subtract(player.getEyePosition());
            float xRot = player.getXRot();
            float yRot = player.getYRot();
            Vec3 pos = mob.position().add(delta.normalize().scale(2));
            ServerPlayer serverPlayer = (ServerPlayer) player;
            if (!serverPlayer.serverLevel()
                    .getBlockState(new BlockPos((int) pos.x, (int) pos.y, (int) pos.z)).is(Blocks.AIR)) {
                pos.add(0, 1, 0);
            }
            serverPlayer.teleportTo(serverPlayer.serverLevel(), pos.x, pos.y, pos.z,
                    yRot > 0 ? yRot - 180 : yRot + 180, -xRot);
            EnhanceNormalAttackModifier.addModifier(player,
                    new EnhanceNormalAttackModifier("SilverDragonAssassinPickaxeActive", 4, 0));
            Compute.playerItemCoolDown(player, this, 4);
        }
    }

    @Override
    public double manaCost(Player player) {
        return 0;
    }

    public static double getCritDamageEnhanceRate(Player player) {
        if (player.getMainHandItem().getItem() instanceof SilverDragonAssassinPickaxe) {
            return (MineSpur.getPlayerMineLevel(player) + 1) * 0.02;
        }
        return 0;
    }

    public static List<ItemStack> getCommonUpgradeMaterials(ItemStack baseEquip, Item piece1, int tier) {
        List<ItemStack> list = new ArrayList<>();
        if (!baseEquip.is(Items.AIR)) {
            list.add(baseEquip);
        }
        switch (tier) {
            case 0 -> {
                list.add(new ItemStack(CrystalItems.GREEN_CRYSTAL_A.get(), 3));
                list.add(new ItemStack(piece1, 2));
            }
            case 1 -> {
                list.add(new ItemStack(CrystalItems.BLUE_CRYSTAL_A.get(), 3));
                list.add(new ItemStack(piece1, 4));
            }
            case 2 -> {
                list.add(new ItemStack(CrystalItems.YELLOW_CRYSTAL_P.get()));
                list.add(new ItemStack(CrystalItems.RED_CRYSTAL_C.get()));
                list.add(new ItemStack(piece1, 8));
                list.add(new ItemStack(SpecialEventItems.DRAGON_DIAMOND.get()));
            }
            case 3 -> {
                list.add(new ItemStack(CrystalItems.RED_CRYSTAL_A.get()));
                list.add(new ItemStack(CrystalItems.YELLOW_CRYSTAL_A.get()));
                list.add(new ItemStack(piece1, 16));
                list.add(new ItemStack(SpecialEventItems.DRAGON_DIAMOND.get(), 3));
            }
            case 4 -> {
                list.add(new ItemStack(CrystalItems.PURPLE_CRYSTAL_A.get()));
                list.add(new ItemStack(CrystalItems.RED_CRYSTAL_B.get()));
                list.add(new ItemStack(piece1, 32));
                list.add(new ItemStack(SpecialEventItems.DRAGON_DIAMOND.get(), 9));
            }
        }
        return list;
    }
}
