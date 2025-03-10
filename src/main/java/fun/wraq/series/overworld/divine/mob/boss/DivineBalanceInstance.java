package fun.wraq.series.overworld.divine.mob.boss;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.instance.NoTeamInstance;
import fun.wraq.events.mob.instance.NoTeamInstanceModule;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.divine.DivineIslandItems;
import fun.wraq.series.overworld.divine.DivineUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class DivineBalanceInstance extends NoTeamInstance {

    private static DivineBalanceInstance instance;

    public static String mobName = "均衡圣光之子";
    public Mob boss;
    public static Style style = CustomStyle.DIVINE_STYLE;
    public static final double MAX_HEALTH = 20 * Math.pow(10, 8);
    public static final int XP_LEVEL = 300;

    public static DivineBalanceInstance getInstance() {
        if (instance == null) {
            instance = new DivineBalanceInstance(new Vec3(2489, 95, 615), 32, 60,
                    new Vec3(2489, 95, 615), Te.s(mobName, style));
        }
        return instance;
    }

    public DivineBalanceInstance(Vec3 pos, double range, int delayTick, Vec3 armorStandPos, MutableComponent name) {
        super(pos, range, delayTick, armorStandPos, name, XP_LEVEL);
    }

    @Override
    public void tickModule() {
        if (mobList.isEmpty()) return;
        if (boss == null || boss.tickCount == 0 || boss.isDeadOrDying()) return;

        DivineUtils.handleMobTick(boss);
        setMobElement(boss);

        if (boss.tickCount % 10 == 0) {
            players.forEach(player -> {
                DivineUtils.createDivineParticle(player.level(), boss.getEyePosition(),
                        player.position().add(0, 1, 0));
                Damage.causeAttackDamageToPlayer(boss, player, 24000);
            });
        }
    }

    @Override
    public void summonModule(Level level) {
        Zombie mob = new Zombie(EntityType.ZOMBIE, level);
        mob.setBaby(true);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(mob, Te.s(name, style), XP_LEVEL,
                24000, 1100, 1100, 0.4, 3,
                0.6, 750, 25,
                20 * Math.pow(10, 8), 0.45);
        mob.setItemSlot(EquipmentSlot.HEAD,
                Compute.getSimpleFoiledItemStack(DivineIslandItems.DIVINE_HELMET_0.get()));
        mob.setItemSlot(EquipmentSlot.CHEST,
                Compute.getSimpleFoiledItemStack(DivineIslandItems.DIVINE_CHEST_0.get()));
        mob.setItemSlot(EquipmentSlot.LEGS,
                Compute.getSimpleFoiledItemStack(DivineIslandItems.DIVINE_LEGGINGS_0.get()));
        mob.setItemSlot(EquipmentSlot.FEET,
                Compute.getSimpleFoiledItemStack(DivineIslandItems.DIVINE_BOOTS_0.get()));
        mob.setItemInHand(InteractionHand.MAIN_HAND,
                Compute.getSimpleFoiledItemStack(DivineIslandItems.DIVINE_SWORD_0.get()));
        mob.moveTo(pos);
        level.addFreshEntity(mob);
        mobList.add(mob);
        boss = mob;
        ServerBossEvent serverBossEvent = (ServerBossEvent) (new ServerBossEvent(mob.getDisplayName(),
                BossEvent.BossBarColor.YELLOW, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);
        getNearPlayers(level).forEach(player -> {
            serverBossEvent.addPlayer((ServerPlayer) player);
        });
        bossInfoList.add(serverBossEvent);
    }

    private void setMobElement(Mob mob) {
        double oneTierHealth = mob.getMaxHealth() / 7;
        double currentTier = mob.getHealth() / oneTierHealth;
        if (currentTier >= 7) {
            DivineUtils.provideElementToMob(mob);
        } else {
            Element.provideElement(mob, Element.elementList.get((int) currentTier), 7);
        }
    }

    @Override
    public boolean allowReward(Player player) {
        return NoTeamInstanceModule.getPlayerAllowReward(player, NoTeamInstanceModule.AllowRewardKey.divine);
    }

    @Override
    public Component allowRewardCondition() {
        return Te.s("需要至少锻造过一件", "圣光武器", style, "才能获取奖励");
    }

    @Override
    public Item getSummonAndRewardNeedItem() {
        return ModItems.REASON.get();
    }

    @Override
    public int getRewardNeedItemCount() {
        return 5;
    }

    public List<ItemAndRate> getRewardList() {
        return List.of(
                new ItemAndRate(DivineIslandItems.DIVINE_BALANCE_STAR.get(), 0.5),
                new ItemAndRate(ModItems.WORLD_SOUL_2.get(), 0.25),
                new ItemAndRate(ModItems.GoldCoinBag.get(), 0.1)
        );
    }

    public static void sendFormatMSG(Player player, Component content) {
        DivineUtils.sendMSG(player, content);
    }

    @Override
    public String getKillCountDataKey() {
        return "DivineBalance";
    }

    @Override
    public List<Component> getIntroduction() {
        List<Component> components = new ArrayList<>();
        components.add(Te.s("1.", style, "随着其", ComponentUtils.AttributeDescription.health(""),
                "的衰减", "，其在", "凡光域", style, "的", "元素表现", CustomStyle.styleOfWorld, "会变更。"));
        return components;
    }
}
