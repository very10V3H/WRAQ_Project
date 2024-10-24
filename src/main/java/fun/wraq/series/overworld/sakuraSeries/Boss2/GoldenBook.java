package fun.wraq.series.overworld.sakuraSeries.Boss2;

import fun.wraq.common.Compute;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.chapter2.SlimeSpawnController;
import fun.wraq.events.mob.chapter3_nether.MagmaSpawnController;
import fun.wraq.process.func.StableAttributesModifier;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.common.impl.onkill.OnKillEffectEquip;
import fun.wraq.common.equip.WraqOffHandItem;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.*;

public class GoldenBook extends WraqOffHandItem implements OnKillEffectEquip {

    public GoldenBook(Properties properties) {
        super(properties, Component.literal("圣经").withStyle(CustomStyle.styleOfGold));
        Utils.manaDamage.put(this, 100d);
        Utils.manaPenetration0.put(this, 6d);
        Utils.maxMana.put(this, 50d);
        Utils.movementSpeedWithoutBattle.put(this, 1d);
        Utils.expUp.put(this, 1.5);
    }

    private final Style style = CustomStyle.styleOfGold;

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfGold;
    }

    @Override
    public List<Component> getAdditionalComponents() {
        List<Component> components = new ArrayList<>();
        Compute.DescriptionPassive(components, Component.literal("华贵金属").withStyle(style));
        components.add(Component.literal(" 副手").withStyle(ChatFormatting.GOLD).
                append(Component.literal("持有该物品时，").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("主手").withStyle(ChatFormatting.AQUA)).
                append(Component.literal("手持").withStyle(ChatFormatting.WHITE)).
                append(ModItems.goldCoin.get().getDefaultInstance().getDisplayName()).
                append(Component.literal("右键消耗，获得:").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 1.").withStyle(style).
                append(ComponentUtils.AttributeDescription.critRate("25%")));
        components.add(Component.literal(" 2.").withStyle(style).
                append(ComponentUtils.AttributeDescription.releaseSpeed("35")));
        components.add(Component.literal(" 3.").withStyle(style).
                append(ComponentUtils.AttributeDescription.manaRecover("30")));
        components.add(Component.literal(" 持续5min，在持续期间").withStyle(ChatFormatting.WHITE).
                append(Component.literal("击杀").withStyle(ChatFormatting.RED)).
                append(Component.literal("任意怪物，将有").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("4.5%").withStyle(style)).
                append(Component.literal("的概率为你提供1枚").withStyle(ChatFormatting.WHITE)).
                append(ModItems.goldCoin.get().getDefaultInstance().getDisplayName()));
        components.add(Component.literal(" 当副手不再是此物品时，华贵金属的全部效果会即刻失效").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfSakura();
    }

    public static Map<String, Integer> passiveLastTimeMap = new HashMap<>();
    public static String critRateTag = "GoldenBookPassiveCritRate";
    public static String cooldownTag = "GoldenBookPassiveCooldown";
    public static String manaRecoverTag = "GoldenBookPassiveManaRecover";

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && interactionHand.equals(InteractionHand.OFF_HAND)) {
            ItemStack targetGoldCoin = player.getMainHandItem();
            int tick = player.getServer().getTickCount();
            if (targetGoldCoin.is(ModItems.goldCoin.get())) {
                targetGoldCoin.shrink(1);
                passiveLastTimeMap.put(player.getName().getString(), tick + 6000);
                StableAttributesModifier.addAttributeModifier(player, StableAttributesModifier.playerCritRateModifier,
                        new StableAttributesModifier(critRateTag, 0.25, tick + 6000));
                StableAttributesModifier.addAttributeModifier(player, StableAttributesModifier.playerCooldownModifier,
                        new StableAttributesModifier(cooldownTag, 0.35, tick + 6000));
                StableAttributesModifier.addAttributeModifier(player, StableAttributesModifier.playerManaRecoverModifier,
                        new StableAttributesModifier(manaRecoverTag, 30, tick + 6000));
                Compute.sendEffectLastTime(player, ModItems.goldCoin.get(), 6000);
            }
        }
        return super.use(level, player, interactionHand);
    }


    @Override
    public void onKill(Player player, Mob mob) {
        int tick = player.getServer().getTickCount();
        String name = player.getName().getString();
        if (passiveLastTimeMap.getOrDefault(name, 0) > tick) {
            Random random = new Random();
            boolean isSlime = MobSpawn.getMobOriginName(mob).equals(SlimeSpawnController.mobName) || MobSpawn.getMobOriginName(mob).equals(MagmaSpawnController.mobName);
            if (random.nextDouble() < (isSlime ? 0.015 : 0.045)) {
                ItemStack goldCoin = ModItems.goldCoin.get().getDefaultInstance();
                Compute.sendFormatMSG(player, Component.literal("华贵金属").withStyle(style),
                        Component.literal("额外获得了1枚").withStyle(ChatFormatting.AQUA).
                                append(goldCoin.getDisplayName()));
                InventoryOperation.itemStackGive(player, goldCoin);
                MySound.soundToPlayer(player, SoundEvents.ARMOR_EQUIP_GOLD);
            }
        }
    }

    @Override
    public void tick(Player player) {
        int tick = player.getServer().getTickCount();
        String name = player.getName().getString();
        if (passiveLastTimeMap.getOrDefault(name, 0) > tick && !(player.getOffhandItem().getItem() instanceof GoldenBook)) {
            passiveLastTimeMap.put(name, 0);
            StableAttributesModifier.removeAttributeModifierByTag(player, StableAttributesModifier.playerCritRateModifier, critRateTag);
            StableAttributesModifier.removeAttributeModifierByTag(player, StableAttributesModifier.playerCooldownModifier, cooldownTag);
            StableAttributesModifier.removeAttributeModifierByTag(player, StableAttributesModifier.playerManaRecoverModifier, manaRecoverTag);
            Compute.removeEffectLastTime(player, ModItems.goldCoin.get());
        }
    }

}
