package fun.wraq.series.nether.equip.attack.sword;

import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.common.util.struct.ServerWaltzMonster;
import fun.wraq.common.util.struct.ServerWaltzPlayer;
import fun.wraq.process.system.element.Element;
import fun.wraq.common.equip.WraqSword;
import fun.wraq.render.hud.Mana;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class QuartzSabre extends WraqSword {

    public QuartzSabre(Properties properties) {
        super(properties);
        Utils.attackDamage.put(this, 70d);
        Utils.defencePenetration.put(this, 0.15);
        Utils.healthSteal.put(this, 0.08);
        Utils.critRate.put(this, 0.5);
        Utils.critDamage.put(this, 0.35);
        Element.FireElementValue.put(this, 0.8);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand useHand) {
        if (!level.isClientSide && useHand.equals(InteractionHand.MAIN_HAND)) {
            CompoundTag data = player.getPersistentData();
            if (data.contains("MANA") && data.getInt("MANA") < 80)
                player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.GRAY).append(Component.literal("法力不足").withStyle(ChatFormatting.WHITE)));
            else {
                data.putInt("MANA", data.getInt("MANA") - 80);
                Mana.updateManaStatus(player);
                Player player1 = level.getNearestPlayer(TargetingConditions.DEFAULT, player, 10, 10, 10);
                if (player1 != null) {
                    player1.getPersistentData().putInt("QuartzSabre", 1200);
                    player1.getPersistentData().putInt("QuartzSabrePos", -1);
                    ServerWaltzPlayer serverWaltzPlayer = new ServerWaltzPlayer(player, player1);
                    Iterator<ServerWaltzPlayer> iterator = Utils.QuartzSabreCCPlayer.iterator();
                    while (iterator.hasNext()) {
                        ServerWaltzPlayer serverWaltzPlayer1 = iterator.next();
                        if (serverWaltzPlayer1.getAttacker().equals(player))
                            Utils.QuartzSabreCCPlayer.remove(serverWaltzPlayer1);
                    }
                    Utils.QuartzSabreCCPlayer.add(serverWaltzPlayer);
                } else {
                    Mob monster = level.getNearestEntity(Mob.class, TargetingConditions.DEFAULT, player, 10, 10, 10, AABB.ofSize(player.position(), 10, 10, 10));
                    if (monster != null) {
                        monster.getPersistentData().putInt("QuartzSabre", 1200);
                        monster.getPersistentData().putInt("QuartzSabrePos", -1);
                        ServerWaltzMonster serverWaltzMonster = new ServerWaltzMonster(player, monster);
                        Iterator<ServerWaltzMonster> iterator = Utils.QuartzSabreCCMonster.iterator();
                        while (iterator.hasNext()) {
                            ServerWaltzMonster serverWaltzMonster1 = iterator.next();
                            if (serverWaltzMonster1.getAttacker().equals(player))
                                Utils.QuartzSabreCCMonster.remove(serverWaltzMonster1);
                        }
                        Utils.QuartzSabreCCMonster.add(serverWaltzMonster);
                    }
                }
            }
        }
        return super.use(level, player, useHand);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfPower;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        components.add(Component.literal("主动：").withStyle(ChatFormatting.AQUA).
                append(Component.literal("利刃华尔兹").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#ea7552")))));
        components.add(Component.literal("标记一个距你最近的单位，该单位将会持续露出").withStyle(ChatFormatting.WHITE).
                append(Component.literal("破绽").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#b26814")))));
        components.add(Component.literal("击打").withStyle(ChatFormatting.WHITE).
                append(Component.literal("破绽").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#b26814")))).
                append(Component.literal("将会造成(5%攻击力)%目标最大生命值").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("额外真实伤害").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#b26814")))));
        components.add(Component.literal("并回复等量生命值，提供持续2s的").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.movementSpeedWithoutBattle("50%")));
        ComponentUtils.coolDownTimeDescription(components, 120);
        ComponentUtils.manaCostDescription(components, 80);
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfNether();
    }
}
