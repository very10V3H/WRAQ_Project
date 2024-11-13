package fun.wraq.events.mob.instance.instances.moontain;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ItemAndRate;
import fun.wraq.events.core.InventoryCheck;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.instance.NoTeamInstance;
import fun.wraq.events.mob.instance.NoTeamInstanceModule;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.system.forge.ForgeEquipUtils;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.moontain.MoontainItems;
import fun.wraq.series.moontain.equip.weapon.MoontainUtils;
import net.mcreator.borninchaosv.entity.LordTheHeadlessEntity;
import net.mcreator.borninchaosv.init.BornInChaosV1ModEntities;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.security.SecureRandom;
import java.util.List;

public class MoontainBoss3Instance extends NoTeamInstance {

    private static MoontainBoss3Instance instance;

    public static String mobName = "望山武魂";
    public static Style style = CustomStyle.styleOfMoontain;

    public static MoontainBoss3Instance getInstance() {
        if (instance == null) {
            instance = new MoontainBoss3Instance(new Vec3(1983, 239, -881), 15, 60, new Vec3(1983, 239, -881),
                    Component.literal(mobName).withStyle(style));
        }
        return instance;
    }

    public MoontainBoss3Instance(Vec3 pos, double range, int delayTick, Vec3 armorStandPos, MutableComponent name) {
        super(pos, range, delayTick, armorStandPos, name);
    }

    @Override
    public void tickModule() {
        if (mobList.isEmpty()) return;
    }

    @Override
    public void summonModule(Level level) {
        LordTheHeadlessEntity entity =
                new LordTheHeadlessEntity(BornInChaosV1ModEntities.LORD_THE_HEADLESS.get(), level);

        MobSpawn.setMobCustomName(entity, Component.literal(mobName).withStyle(style), 240);

        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(entity), 240);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(entity, 3800, 360, 360,
                0.4, 5, 0.6, 175, 0,
                5000 * Math.pow(10, 4), 0.45);

        entity.setHealth(entity.getMaxHealth());

        entity.moveTo(pos);
        level.addFreshEntity(entity);

        ServerBossEvent serverBossEvent = (ServerBossEvent) (new ServerBossEvent(entity.getDisplayName(),
                BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);
        getPlayerList(level).forEach(player -> {
            serverBossEvent.addPlayer((ServerPlayer) player);
        });
        bossInfoList.add(serverBossEvent);
        mobList.add(entity);
    }

    @Override
    public void rewardModule(Player player) {
        List<ItemAndRate> rewardList = getRewardList();
        rewardList.forEach(itemAndRate -> itemAndRate.sendWithMSG(player, 1));
        MobSpawn.killCountIncrement(player, mobName);
        Compute.givePercentExpToPlayer(player, 0.02, PlayerAttributes.expUp(player), 240);

        SecureRandom secureRandom = new SecureRandom();
        if (secureRandom.nextDouble() < 0.05) {
            ItemStack stack;
            if (secureRandom.nextBoolean()) {
                List<Item> weapons = MoontainUtils.getMoontainWeapons();
                stack = new ItemStack(weapons.get(secureRandom.nextInt(weapons.size())));
            } else {
                List<Item> armors = MoontainUtils.getMoontainArmors();
                stack = new ItemStack(armors.get(secureRandom.nextInt(armors.size())));
            }
            double randomDouble = secureRandom.nextDouble();
            if (randomDouble < 0.05) {
                ForgeEquipUtils.setForgeQualityOnEquip(stack, secureRandom.nextInt(7, 10));
            } else {
                ForgeEquipUtils.setForgeQualityOnEquip(stack, secureRandom.nextInt(2, 7));
            }
            InventoryCheck.addOwnerTagToItemStack(player, stack);
            Compute.forgingHoverName(stack);
            MoontainUtils.formatBroad(player.level(), Te.s(player.getDisplayName(),
                    " 击杀 ", mobName, style, " 获得了 ", stack.getDisplayName()));
            InventoryOperation.itemStackGive(player, stack);
        }

        if (secureRandom.nextDouble() < 0.1) {
            List<Item> curiosList = MoontainUtils.getMoontainCurios();
            ItemStack stack = new ItemStack(curiosList.get(secureRandom.nextInt(curiosList.size())));
            InventoryCheck.addOwnerTagToItemStack(player, stack);
            Compute.forgingHoverName(stack);
            MoontainUtils.formatBroad(player.level(), Te.s(player.getDisplayName(),
                    " 击杀 ", mobName, style, " 获得了 ", stack.getDisplayName()));
            InventoryOperation.itemStackGive(player, stack);
        }
    }

    @Override
    public boolean allowReward(Player player) {
        return NoTeamInstanceModule.getPlayerAllowReward(player, NoTeamInstanceModule.AllowRewardKey.moontainBoss);
    }

    @Override
    public Component allowRewardCondition() {
        return Component.literal("需要至少").withStyle(ChatFormatting.WHITE).
                append(Component.literal("锻造").withStyle(ChatFormatting.GRAY)).
                append(Component.literal("过").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("1件").withStyle(ChatFormatting.AQUA)).
                append(Component.literal("暗黑城堡武器").withStyle(CustomStyle.styleOfCastleCrystal)).
                append(Component.literal("，方能获取奖励。").withStyle(ChatFormatting.WHITE));
    }

    public static List<ItemAndRate> getRewardList() {
        return List.of(
                new ItemAndRate(MoontainItems.STONE_FRAGMENT.get(), 16),
                new ItemAndRate(ModItems.WorldSoul2.get(), 0.25),
                new ItemAndRate(ModItems.GoldCoinBag.get(), 0.1));
    }

    @Override
    public Item getSummonAndRewardNeedItem() {
        return MoontainItems.HEART.get();
    }
}
