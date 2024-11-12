package fun.wraq.series.nether.equip.attack.sword;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.equip.WraqSword;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.registry.ModSounds;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;

public class QuartzSword extends WraqSword implements ActiveItem {

    public QuartzSword(Properties properties) {
        super(properties);
        Utils.attackDamage.put(this, 80d);
        Utils.manaDamage.put(this, 65d);
        Utils.defencePenetration0.put(this, 12d);
        Utils.healthSteal.put(this, 0.08);
        Utils.critRate.put(this, 0.45);
        Utils.critDamage.put(this, 0.35);
        Element.FireElementValue.put(this, 0.8);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfPower;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        components.add(Component.literal("主动：").withStyle(ChatFormatting.AQUA).
                append(Component.literal("夸塔兹能量涌动").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#ea7552")))));
        components.add(Component.literal("对周围所有单位雷击，造成").withStyle(ChatFormatting.WHITE).
                append(Component.literal("燃烧").withStyle(ChatFormatting.YELLOW)).
                append(Component.literal("与").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.manaDamage("250%")));
        ComponentUtils.coolDownTimeDescription(components, 5);
        ComponentUtils.manaCostDescription(components, 90);
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfNether();
    }

    @Override
    public void active(Player player) {
        Compute.PlayerPowerParticle(player);
        Level level = player.level();
        player.getCooldowns().addCooldown(ModItems.QuartzSword.get(), (int) (100 - 100 * PlayerAttributes.coolDownDecrease(player)));
        List<Player> playerList = level.getNearbyPlayers(TargetingConditions.DEFAULT, player, AABB.ofSize(player.position(), 10, 10, 10));
        for (Player player1 : playerList) {
            Damage.manaDamageToPlayer(player, player1, 2.5f);
            LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
            lightningBolt.setCause((ServerPlayer) player);
            lightningBolt.setSilent(true);
            lightningBolt.setVisualOnly(true);
            lightningBolt.setDamage(0);
            lightningBolt.moveTo(player1.position());
            level.addFreshEntity(lightningBolt);
        }
        List<Mob> monsterList = level.getNearbyEntities(Mob.class, TargetingConditions.DEFAULT, player, AABB.ofSize(player.position(), 10, 10, 10));
        for (Mob monster : monsterList) {
            Damage.causeManaDamageToMonster_RateApDamage(player, monster, 2.5f, true);
            LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
            lightningBolt.setCause((ServerPlayer) player);
            lightningBolt.setSilent(true);
            lightningBolt.setVisualOnly(true);
            lightningBolt.setDamage(0);
            lightningBolt.moveTo(monster.position());
            level.addFreshEntity(lightningBolt);
        }
        MySound.soundToNearPlayer(player, ModSounds.Nether_Power.get());
    }

    @Override
    public double manaCost(Player player) {
        return 90;
    }
}
