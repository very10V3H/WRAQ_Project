package fun.wraq.events.mob.instance.instances.element;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.instance.NoTeamInstance;
import fun.wraq.events.mob.instance.NoTeamInstanceModule;
import fun.wraq.events.mob.instance.instances.dimension.CitadelGuardianInstance;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.effect.SpecialEffectOnPlayer;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Unit;
import net.minecraft.world.BossEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.monster.warden.WardenAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.level.BlockEvent;

import java.util.*;

public class WardenInstance extends NoTeamInstance {

    private static WardenInstance instance;

    public static String mobName = "坚守者";
    public Mob boss;
    public static Style style = CustomStyle.styleOfWarden;
    public static final double MAX_HEALTH = 1.5 * Math.pow(10, 8);
    public static final int XP_LEVEL = 260;

    public static WardenInstance getInstance() {
        if (instance == null) {
            instance = new WardenInstance(new Vec3(2347, -42, -704), 60, 60, new Vec3(2347, -42, -704),
                    Component.literal("坚守者").withStyle(style));
        }
        return instance;
    }

    public WardenInstance(Vec3 pos, double range, int delayTick, Vec3 armorStandPos, MutableComponent name) {
        super(pos, range, delayTick, armorStandPos, name, XP_LEVEL);
    }

    @Override
    public void tickModule() {
        if (mobList.isEmpty()) return;

        if (boss == null) return;
        tips();
        detectNearPlayer();
        summonBlock();
        detectBlock();
        summonFlySkeleton();
        onBossDead();
        commonAttack();
    }

    @Override
    public void reset(int tick, boolean removeMob) {
        resetNextAllowTrigTick();
        resetFlag();
        resetBlock();
        resetSummonTick();
        super.reset(tick, removeMob);
    }

    @Override
    public void summonModule(Level level) {
        Warden warden = new Warden(EntityType.WARDEN, level);
        MobSpawn.setMobCustomName(warden, Component.literal("坚守者").withStyle(style), XP_LEVEL);

        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(warden), XP_LEVEL);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(warden, 6500, 600, 600, 0.4,
                5, 0.6, 300, 25, MAX_HEALTH, 0.35);

        warden.setHealth(warden.getMaxHealth());
        warden.getBrain().setMemoryWithExpiry(MemoryModuleType.DIG_COOLDOWN, Unit.INSTANCE, 12000L);
        warden.getBrain().setMemoryWithExpiry(MemoryModuleType.IS_EMERGING, Unit.INSTANCE, WardenAi.EMERGE_DURATION);
        warden.moveTo(pos);
        level.addFreshEntity(warden);
        mobList.add(warden);
        boss = warden;

        ServerBossEvent serverBossEvent = (ServerBossEvent) (new ServerBossEvent(warden.getDisplayName(),
                BossEvent.BossBarColor.BLUE, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);
        getNearPlayers(level).forEach(player -> {
            serverBossEvent.addPlayer((ServerPlayer) player);
        });
        bossInfoList.add(serverBossEvent);
    }

    @Override
    public void rewardModule(Player player) {
        List<ItemAndRate> rewardList = getRewardList();
        rewardList.forEach(itemAndRate -> {
            itemAndRate.sendWithMSG(player, 1);
        });

        String name = player.getName().getString();
        if (!MobSpawn.tempKillCount.containsKey(name)) MobSpawn.tempKillCount.put(name, new HashMap<>());
        Map<String, Integer> map = MobSpawn.tempKillCount.get(name);
        map.put(mobName, map.getOrDefault(mobName, 0) + 1);

        Compute.givePercentExpToPlayer(player, 0.02, PlayerAttributes.expUp(player), XP_LEVEL);
    }

    @Override
    public boolean allowReward(Player player) {
        if (MobSpawn.totalKillCount.getOrDefault(player.getName().getString(), new HashMap<>())
                .getOrDefault(CitadelGuardianInstance.mobName, 0) >= 50) {
            NoTeamInstanceModule.putPlayerAllowReward(player, NoTeamInstanceModule.AllowRewardKey.warden, true);
        }
        return NoTeamInstanceModule.getPlayerAllowReward(player, NoTeamInstanceModule.AllowRewardKey.warden);
    }

    @Override
    public Component allowRewardCondition() {
        return Te.s("必须击杀", CitadelGuardianInstance.mobName, CustomStyle.styleOfEnd, "至少",
                "50次", ChatFormatting.RED, "才能获取奖励。");
    }

    public List<ItemAndRate> getRewardList() {
        return List.of(
                new ItemAndRate(ModItems.WorldSoul2.get(), 0.25),
                new ItemAndRate(ModItems.GoldCoinBag.get(), 0.1)
        );
    }

    // 灾变神力：减少91%受到的伤害
    public static double mobWithstandDamageRate(Mob mob) {
        if (MobSpawn.getMobOriginName(mob).equals(mobName)) {
            WardenInstance instance = getInstance();
            if (instance.boss != null && instance.spawnTick + 100 > Tick.get()) {
                return 0;
            }
            return 0.09;
        }
        return 1;
    }

    // 循声：对普通攻击、施法的玩家造成低额伤害
    public static void onPlayerNormalAttackOrReleasePower(Player player) {
        WardenInstance wardenInstance = WardenInstance.getInstance();
        if (wardenInstance.players.contains(player)) {
            Damage.manaDamageToPlayer(wardenInstance.boss, player, 1500, 0.5, 100);
        }
    }

    public int nextAllowTrigTick = 0;
    // 当坚守者附近没有玩家时，会牵引所有玩家至身边，并击碎玩家所有双抗持续较久。施加重伤。
    public void detectNearPlayer() {
        if (Compute.getNearEntity(boss, Player.class, 8).isEmpty() && Tick.get() > nextAllowTrigTick) {
            nextAllowTrigTick = Tick.get() + 100;
            getAllPlayers(boss.level()).forEach(player -> {
                sendFormatMSG(player, Te.s(mobName, style, "附近没有玩家，其释放了", "回响", style));
                Compute.causeGatherEffect(player, 20, boss.position());
                SpecialEffectOnPlayer.causeBothDefenceReductionToPlayer(player,
                        "WardenDefenceReduction", 200,200,
                        "WardenManaDefenceReduction", 200, 80);
                SpecialEffectOnPlayer.addHealingReduction(player, "WardenHealingReduction", 0.6, 200);
            });
        }
    }

    public void resetNextAllowTrigTick() {
        nextAllowTrigTick = 0;
    }

    // 坚守者生成方块，玩家需要及时摧毁方块，否则坚守者将回复大量生命值。在方块存活时，坚守者减少99%受到伤害，并沉默/致盲所有玩家。黑暗。
    public static List<BlockPos> blockPosList = List.of(
            new BlockPos(2332, -50, -677),
            new BlockPos(2332, -50, -731),
            new BlockPos(2363, -50, -731),
            new BlockPos(2363, -50, -677)
    );

    public static void onBreakBlockEvent(BlockEvent.BreakEvent event) {
        BlockPos pos = event.getPos();
        if (blockPosList.contains(pos)) {
            Player player = event.getPlayer();
            event.getPlayer().level().destroyBlock(pos, false);
            sendFormatMSG(player, Te.s("已摧毁一个", "幽匿尖啸体", style));
            SpecialEffectOnPlayer.cleanse(player);
        }
    }

    public int summonTick = 0;
    public void summonBlock() {
        if (summonTick == 0 && boss.getHealth() / boss.getMaxHealth() < 0.5) {
            blockPosList.forEach(pos -> {
                boss.level().setBlockAndUpdate(pos, Blocks.SCULK_SHRIEKER.defaultBlockState());
            });
            summonTick = Tick.get();
            getAllPlayers(boss.level()).forEach(player -> {
                Compute.setPlayerTitleAndSubTitle((ServerPlayer) player, Te.s("幽匿尖啸体", style, "已生成在四周"),
                        Te.s("快去分头摧毁!"));
                SpecialEffectOnPlayer.addSilentEffect(player, 100);
                SpecialEffectOnPlayer.addBlindEffect(player, 100);
                player.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 200));
            });
        }
    }

    public void detectBlock() {
        boolean existing = blockPosList.stream().anyMatch(pos -> {
            return boss.level().getBlockState(pos).getBlock().equals(Blocks.SCULK_SHRIEKER);
        });
        if (existing) {
            if ((Tick.get() - summonTick) % 100 == 99) {
                getAllPlayers(boss.level()).forEach(player -> {
                    SpecialEffectOnPlayer.addSilentEffect(player, 100);
                    SpecialEffectOnPlayer.addBlindEffect(player, 100);
                    player.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 200));
                });
            }
            if (summonTick + 400 < Tick.get()) {
                boss.heal(boss.getMaxHealth() * 0.25f);
                getAllPlayers(boss.level()).forEach(player -> {
                    Compute.setPlayerTitleAndSubTitle((ServerPlayer) player, Te.s("幽匿尖啸体", style, "未及时被摧毁"),
                            Te.s(mobName, style, "从幽匿中汲取了力量"));
                    SpecialEffectOnPlayer.addSilentEffect(player, 100);
                    SpecialEffectOnPlayer.addBlindEffect(player, 100);
                    player.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 200));
                });
                resetBlock();
                summonTick = -1;
            }
        } else {
            if (summonTick > 0) {
                summonTick = 0;
                getAllPlayers(boss.level()).forEach(player -> {
                    Compute.setPlayerTitleAndSubTitle((ServerPlayer) player, Te.s("幽匿尖啸体", style, "已全部摧毁"),
                            Te.s("回去击杀boss!"));
                    SpecialEffectOnPlayer.cleanse(player);
                    player.removeEffect(MobEffects.DARKNESS);
                });
            }
        }
    }

    public void resetSummonTick() {
        summonTick = 0;
    }

    public void resetBlock() {
        if (boss != null) {
            blockPosList.forEach(pos -> {
                boss.level().destroyBlock(pos, false);
            });
        }
    }

    // 当坚守者生命值低于50%时，会召唤骑蝙蝠远程弓手。
    private boolean canSpawnSkeleton;
    public void summonFlySkeleton() {
        if (canSpawnSkeleton && boss.getHealth() / boss.getMaxHealth() < 0.5) {
            canSpawnSkeleton = false;
            Random random = new Random();
            for (int i = 0 ; i < 6 ; i ++) {
                Bat bat = new Bat(EntityType.BAT, boss.level());
                bat.getAttribute(Attributes.MAX_HEALTH).setBaseValue(5000 * Math.pow(10, 4));
                bat.setHealth(bat.getMaxHealth());
                mobList.add(bat);
                bat.moveTo(boss.getEyePosition()
                        .add(3 - random.nextDouble(6), 2, 3 - random.nextDouble(6)));
                boss.level().addFreshEntity(bat);

                Style style = CustomStyle.styleOfWarden;
                Skeleton skeleton = new Skeleton(EntityType.SKELETON, boss.level());
                MobSpawn.setMobCustomName(skeleton, Component.literal("循声骷髅").withStyle(style), XP_LEVEL);
                MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(skeleton), XP_LEVEL);
                MobSpawn.MobBaseAttributes.setMobBaseAttributes(skeleton, 5000, 400, 400,
                        0.4, 5, 0.6, 300, 25,
                        5000 * Math.pow(10, 4), 0.35);
                ItemStack[] itemStacks = {new ItemStack(Items.DIAMOND_HELMET), new ItemStack(Items.CHAINMAIL_CHESTPLATE),
                        new ItemStack(Items.LEATHER_LEGGINGS), new ItemStack(Items.LEATHER_BOOTS)};
                EquipmentSlot[] equipmentSlots = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
                for (int j = 0; j < itemStacks.length; j++) {
                    CompoundTag tag = itemStacks[j].getTag();
                    CompoundTag tag1 = new CompoundTag();
                    tag1.putInt("color", style.getColor().getValue());
                    tag.put("display", tag1);
                    skeleton.setItemSlot(equipmentSlots[j], itemStacks[j]);
                }
                skeleton.setItemInHand(InteractionHand.MAIN_HAND, Items.BOW.getDefaultInstance());
                mobList.add(skeleton);
                skeleton.moveTo(boss.getEyePosition()
                        .add(3 - random.nextDouble(6), 2, 3 - random.nextDouble(6)));
                boss.level().addFreshEntity(skeleton);
                skeleton.startRiding(bat);
            }
        }
    }

    public void resetFlag() {
        canSpawnSkeleton = true;
    }

    public void onBossDead() {
        if (boss.isDeadOrDying()) {
            mobList.forEach(LivingEntity::kill);
        }
    }

    // 对处于潜行状态的玩家，将免疫其50%伤害。
    public static double onPlayerWithstandDamageRate(Player player, Mob mob) {
        if (player.isShiftKeyDown() && mob.equals(instance.boss)) {
            return 0.5;
        }
        return 1;
    }

    // 玩家之间应当保持距离，过近的距离将会使范围伤害叠加，容易暴毙。有20%几率造成持续3s的致盲与沉默
    public void commonAttack() {
        if (Tick.get() % 20 == 0) {
            Random random = new Random();
            getAllPlayers(boss.level()).forEach(player -> {
                Set<Player> playerSet =
                        new HashSet<>(Compute.getNearEntity(player, Player.class, 3)
                                .stream().map(e -> (Player) e).toList());
                playerSet.forEach(eachPlayer -> {
                    Damage.AttackDamageToPlayer(boss, player, 5000);
                    Damage.manaDamageToPlayer(boss, player, 5000, 0.5, 100);
                    if (random.nextDouble() < 0.2) {
                        SpecialEffectOnPlayer.addSilentEffect(player, 60);
                        SpecialEffectOnPlayer.addBlindEffect(player, 60);
                    }
                });
            });
        }
    }

    // 提示
    public void tips() {
        if (spawnTick == Tick.get()) {
            getAllPlayers(boss.level()).forEach(player -> {
                player.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 40));
                MySound.soundToPlayer(player, SoundEvents.WARDEN_AGITATED);
            });
        }
        if (spawnTick + 100 == Tick.get()) {
            getAllPlayers(boss.level()).forEach(player -> {
                sendFormatMSG(player, Te.s("坚守者", style, "对处于",
                        "潜行状态", CustomStyle.styleOfEnd, "的玩家造成的伤害", "降低50%", ChatFormatting.GREEN));
            });
        }
        if (spawnTick + 200 == Tick.get()) {
            getAllPlayers(boss.level()).forEach(player -> {
                sendFormatMSG(player, Te.s("玩家之间需要保持一定距离，因为", "坚守者", style, "的普通技能是",
                        "范围伤害", CustomStyle.styleOfStone));
            });
        }
    }

    public static void sendFormatMSG(Player player, Component content) {
        Compute.sendFormatMSG(player, Te.s("循声", style), content);
    }
}
