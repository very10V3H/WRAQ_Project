package fun.wraq.series.nether.Equip;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.damage.Dot;
import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.impl.onhit.OnHitEffectOffHandWeapon;
import fun.wraq.common.equip.WraqOffHandItem;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.chapter1.Mine.MineShield;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

public class NetherShield extends WraqOffHandItem implements ForgeItem, OnHitEffectOffHandWeapon {

    public NetherShield(Properties properties) {
        super(properties, Component.literal("手盾").withStyle(CustomStyle.styleOfMine));
        Utils.defence.put(this, 3d);
        Utils.maxHealth.put(this, 250d);
        Utils.attackDamage.put(this, 20d);
        Utils.critDamage.put(this, 0.15);
        Utils.expUp.put(this, 0.75);
        Utils.shieldTag.put(this, 1d);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfNether;
    }

    @Override
    public List<Component> getAdditionalComponents() {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        MineShield.shieldAdditionDescription(components);
        Compute.DescriptionPassive(components, Component.literal("碎骨化灰").withStyle(style));
        components.add(Component.literal(" 近战攻击").withStyle(CustomStyle.styleOfPower).
                append(Component.literal("将使").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("主要目标").withStyle(ChatFormatting.AQUA)).
                append(ComponentUtils.getAttackDamageDotDescription(1, 3, "20%")));
        components.add(ComponentUtils.getCritDamageInfluenceDescription());
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixChapterIII();
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    @Override
    public List<ItemStack> forgeRecipe() {
        return new ArrayList<>() {{
            add(new ItemStack(ModItems.NetherRune.get(), 4));
            add(new ItemStack(ModItems.Ruby.get(), 128));
            add(new ItemStack(ModItems.NetherQuartz.get(), 32));
            add(new ItemStack(Items.IRON_INGOT, 64));
            add(new ItemStack(ModItems.goldCoin.get(), 64));
        }};
    }

    @Override
    public void onHit(Player player, Mob mob) {
        player.sendSystemMessage(Component.literal("1"));
        double defenceValue = PlayerAttributes.defence(player) + MobSpawn.MobBaseAttributes.getMobBaseAttribute(mob, MobSpawn.MobBaseAttributes.defence);
        double rate = 2 - Damage.defenceDamageDecreaseRate(defenceValue, 0, 0);
        int tick = player.getServer().getTickCount();
        Dot.addDotOnMob(mob, new Dot(1, PlayerAttributes.attackDamage(player) * 0.2 * rate, 3, tick + 20, player.getName().getString(), true));
        Compute.sendMobEffectHudToNearPlayer(mob, ModItems.Ruby.get(), "NetherShieldDot", 20, 0, false);
    }
}