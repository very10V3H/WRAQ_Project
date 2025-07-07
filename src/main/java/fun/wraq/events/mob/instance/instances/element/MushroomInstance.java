package fun.wraq.events.mob.instance.instances.element;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.instance.NoTeamInstance;
import fun.wraq.events.mob.instance.NoTeamInstanceModule;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.effect.SpecialEffectOnPlayer;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.instance.series.mushroom.MushroomItems;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.List;

public class MushroomInstance extends NoTeamInstance {

    private static MushroomInstance instance;

    public static String mobName = "菌菇灵统领";
    public Mob boss;
    public Cow cow;
    public static Style style = CustomStyle.MUSHROOM_STYLE;
    public static final double MAX_HEALTH = 1 * Math.pow(10, 8);
    public static final int XP_LEVEL = 235;

    public static MushroomInstance getInstance() {
        if (instance == null) {
            instance = new MushroomInstance(new Vec3(2011, 129, -1788), 60, 60, new Vec3(2011, 129, -1788),
                    Te.s(mobName, style));
        }
        return instance;
    }

    public MushroomInstance(Vec3 pos, double range, int delayTick, Vec3 armorStandPos, MutableComponent name) {
        super(pos, range, delayTick, armorStandPos, name, XP_LEVEL);
    }

    @Override
    public void tickModule() {
        if (mobList.isEmpty()) return;
        if (boss == null || boss.tickCount == 0) return;
        int tick = boss.tickCount;
        if (tick % 20 == 0) {
            commonAttack();
        }
        if (tick % 40 == 10) {
            skill1();
        }
        if (tick % 200 == 70) {
            skill2();
        }
    }

    @Override
    public void reset(int tick, boolean removeMob) {
        if (cow != null)  {
            cow.remove(Entity.RemovalReason.KILLED);
        }
        super.reset(tick, removeMob);
    }

    @Override
    public MobAttributes getMainMobAttributes() {
        return new MobAttributes(3750, 340, 340, 0.4, 3, 0.55, 160, 25, MAX_HEALTH, 0.45);
    }

    @Override
    public void summonModule(Level level) {
        cow = new Cow(EntityType.MOOSHROOM, level);
        cow.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.45);
        cow.moveTo(pos);
        level.addFreshEntity(cow);
        Piglin mob = new Piglin(EntityType.PIGLIN_BRUTE, level);
        MobSpawn.setMobCustomName(mob, Te.s(mobName, style), XP_LEVEL);
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(mob), XP_LEVEL);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(mob, getMainMobAttributes());
        // 设置物品
        ItemStack[] itemStacks = {new ItemStack(Items.LEATHER_HELMET), new ItemStack(Items.LEATHER_CHESTPLATE),
                new ItemStack(Items.LEATHER_LEGGINGS), new ItemStack(Items.LEATHER_BOOTS)};
        EquipmentSlot[] equipmentSlots = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
        for (int i = 0; i < itemStacks.length; i++) {
            CompoundTag tag = itemStacks[i].getTag();
            CompoundTag tag1 = new CompoundTag();
            tag1.putInt("color", CustomStyle.styleOfRed.getColor().getValue());
            tag.put("display", tag1);
            mob.setItemSlot(equipmentSlots[i], itemStacks[i]);
        }
        mob.setItemSlot(EquipmentSlot.HEAD, Compute.getSkullByName("MHF_MushroomCow"));
        mob.setItemInHand(InteractionHand.MAIN_HAND, Compute.getSimpleFoiledItemStack(Items.GOLDEN_AXE));
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
        mob.startRiding(cow);
        MobSpawn.setCanNotAddSlowDownOrImprison(mob);
        MobSpawn.setCanNotAddSlowDownOrImprison(cow);
    }

    @Override
    public boolean allowReward(Player player) {
        return NoTeamInstanceModule.getPlayerAllowReward(player, NoTeamInstanceModule.AllowRewardKey.moontainBoss);
    }

    @Override
    public Component allowRewardCondition() {
        return NoTeamInstanceModule.AllowRewardCondition.BLACK_CASTLE_WEAPON;
    }

    @Override
    public int getRewardNeedItemCount() {
        return 2;
    }

    public List<ItemAndRate> getRewardList() {
        return List.of(
                new ItemAndRate(MushroomItems.RED_MUSHROOM.get(), 1),
                new ItemAndRate(ModItems.WORLD_SOUL_2.get(), 0.25),
                new ItemAndRate(ModItems.GOLD_COIN_BAG.get(), 0.1),
                new ItemAndRate(MushroomItems.NETHER_MUSHROOM.get(), 0.01)
        );
    }

    public static void sendFormatMSG(Player player, Component content) {
        Compute.sendFormatMSG(player, Te.s("菌菇", style), content);
    }

    public void commonAttack() {
        double commonAttackDamage = 10000;
        Level level = boss.level();
        players.forEach(player -> {
            ParticleProvider.createLineEffectParticle(level, (int) player.distanceTo(boss) * 5,
                    boss.getEyePosition(), player.getEyePosition(), style);
            Damage.causeAttackDamageToPlayer(boss, player, commonAttackDamage);
            Damage.causeManaDamageToPlayer(boss, player, commonAttackDamage, 0, 100);
        });
    }

    public void skill1() {
        List<Player> playerList = players.stream().toList();
        if (playerList.isEmpty()) return;
        Player randomPlayer = playerList.get(RandomUtils.nextInt(0, playerList.size()));
        Compute.createRangeEffectDot(boss, randomPlayer.position(), 5, ((mob, player) -> {
            Damage.causeManaDamageToPlayer(mob, player, 10000, 0, 100);
        }), CustomStyle.MUSHROOM_STYLE, 5, 40);
    }

    public void skill2() {
        players.forEach(player -> {
            ParticleProvider.createBreakBlockParticle(player, Blocks.BROWN_MUSHROOM);
            SpecialEffectOnPlayer.addImprisonEffect(player, 40);
        });
    }

    public static double getAdjustDamageRate(Mob mob) {
        if (MobSpawn.getMobOriginName(mob).equals(mobName)) {
            return -0.9;
        }
        return 0;
    }

    @Override
    public String getKillCountDataKey() {
        return "MushroomBoss";
    }

    @Override
    public List<Component> getIntroduction() {
        List<Component> components = new ArrayList<>();
        components.add(Te.s("1.", style, "每秒对所有玩家造成混合伤害。"));
        components.add(Te.s("2.", style, "每2s选定一个随机玩家，在其位置制造一片孢子区域；"));
        components.add(Te.s(" 在区域内的玩家会持续受到魔法伤害。"));
        components.add(Te.s("3.", style, "每10s对所有玩家造成持续2s的禁锢效果。"));
        components.add(Te.s("4.", style, "免疫90%非真实伤害。"));
        return components;
    }

    public static void bugCompensate(Player player) {
        int killCount = MobSpawn.getPlayerKillCount(player, mobName);
        if (killCount / 100 > 0) {
            Compute.sendFormatMSG(player, Te.s("补偿", ChatFormatting.LIGHT_PURPLE),
                    Te.s("基于你的", mobName, style, "击杀数:", killCount,
                            "为你提供了", killCount / 100, "个", MushroomItems.NETHER_MUSHROOM));
            InventoryOperation.giveItemStackWithMSG(player,
                    new ItemStack(MushroomItems.NETHER_MUSHROOM.get(), killCount / 100));
        }
    }
}
