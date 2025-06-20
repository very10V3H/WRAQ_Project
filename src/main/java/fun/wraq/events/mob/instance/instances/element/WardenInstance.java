package fun.wraq.events.mob.instance.instances.element;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.instance.NoTeamInstance;
import fun.wraq.events.mob.instance.NoTeamInstanceModule;
import fun.wraq.events.mob.instance.instances.dimension.CitadelGuardianInstance;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.effect.SpecialEffectOnPlayer;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.system.wayPoints.MyWayPoint;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.gems.GemItems;
import fun.wraq.series.instance.series.warden.WardenItems;
import fun.wraq.series.moontain.MoontainItems;
import fun.wraq.series.moontain.equip.armor.MoontainArmor;
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
import net.minecraft.world.item.Item;
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
    public MobAttributes getMainMobAttributes() {
        double maxHealth = 5000 * Math.pow(10, 4) * (1 + 0.75 * (Math.max(1, players.size()) - 1));
        return new MobAttributes(6500, 600, 600, 0.4, 3, 0.6, 300, 25, maxHealth, 0.35);
    }

    @Override
    public void summonModule(Level level) {
        Warden warden = new Warden(EntityType.WARDEN, level);
        MobSpawn.setMobCustomName(warden, Component.literal("坚守者").withStyle(style), XP_LEVEL);
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(warden), XP_LEVEL);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(warden, getMainMobAttributes());
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
    public boolean allowReward(Player player) {
        if (MobSpawn.getPlayerKillCount(player, CitadelGuardianInstance.mobName) >= 50) {
            NoTeamInstanceModule.putPlayerAllowReward(player, NoTeamInstanceModule.AllowRewardKey.warden, true);
        }
        return NoTeamInstanceModule.getPlayerAllowReward(player, NoTeamInstanceModule.AllowRewardKey.warden);
    }

    @Override
    public int getRewardNeedItemCount() {
        return 4;
    }

    @Override
    public Component allowRewardCondition() {
        return Te.s("必须击杀", CitadelGuardianInstance.mobName, CustomStyle.styleOfEnd, "至少",
                "50次", ChatFormatting.RED, "才能获取奖励。");
    }

    public List<ItemAndRate> getRewardList() {
        return List.of(
                new ItemAndRate(WardenItems.WARDEN_SOUL_INGOT.get(), 1),
                new ItemAndRate(GemItems.ANCIENT_DARKNESS_GEM_0.get(), 0.0033),
                new ItemAndRate(GemItems.ANCIENT_SILENT_GEM_0.get(), 0.0033),
                new ItemAndRate(GemItems.ANCIENT_ECHO_GEM_0.get(), 0.0033),
                new ItemAndRate(ModItems.WORLD_SOUL_2.get(), 0.25),
                new ItemAndRate(ModItems.GOLD_COIN_BAG.get(), 0.1)
        );
    }

    // 灾变神力：减少90%受到的伤害
    public static double mobWithstandDamageRate(Mob mob, Player player) {
        if (MobSpawn.getMobOriginName(mob).equals(mobName)) {
            WardenInstance instance = getInstance();
            if (instance.boss != null && instance.spawnTick + 100 > Tick.get()) {
                return 0;
            }
            if (instance.summonTick > 0) {
                return 0;
            }
            List<Item> moontainWeaponList =
                    List.of(MoontainItems.SWORD.get(), MoontainItems.BOW.get(), MoontainItems.SCEPTRE.get());
            return 0.1 * (moontainWeaponList.contains(player.getMainHandItem().getItem()) ? 1.5 : 1);
        }
        return 1;
    }

    // 循声：对普通攻击、施法的玩家造成低额伤害
    public static void onPlayerNormalAttackOrReleasePower(Player player) {
        WardenInstance wardenInstance = WardenInstance.getInstance();
        if (wardenInstance.players.contains(player)) {
            Damage.causeManaDamageToPlayer(wardenInstance.boss, player, 3000, 0.5, 100);
        }
    }

    public int nextAllowTrigTick = 0;
    // 当坚守者附近没有玩家时，会牵引所有玩家至身边，并击碎玩家所有双抗持续较久。施加重伤。
    public void detectNearPlayer() {
        boolean existing = blockPosList.stream().anyMatch(pos -> {
            return boss.level().getBlockState(pos).getBlock().equals(Blocks.SCULK_SHRIEKER);
        });
        if (existing) {
            return;
        }
        if (Compute.getNearEntity(boss, Player.class, 8).isEmpty() && Tick.get() > nextAllowTrigTick) {
            nextAllowTrigTick = Tick.get() + 100;
            players.forEach(player -> {
                sendFormatMSG(player, Te.s(mobName, style, "附近没有玩家，其释放了", "回响", style));
                /*Compute.causeGatherEffect(player, 20, boss.position());*/
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
            for (int i = 0; i < Math.min(4, players.size()); i++) {
                BlockPos blockPos = blockPosList.get(i);
                boss.level().setBlockAndUpdate(blockPos, Blocks.SCULK_SHRIEKER.defaultBlockState());
            }
            summonTick = Tick.get();
            players.forEach(player -> {
                Compute.setPlayerTitleAndSubTitle((ServerPlayer) player, Te.s("幽匿尖啸体", style, "已生成在四周"),
                        Te.s("快去分头摧毁!"));
                SpecialEffectOnPlayer.addSilentEffect(player, 100);
                SpecialEffectOnPlayer.addBlindEffect(player, 100);
                player.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 200));
                for (int i = 0; i < Math.min(4, players.size()); i++) {
                    BlockPos blockPos = blockPosList.get(i);
                    MyWayPoint.sendAddPacketToClient(player,
                            new MyWayPoint(blockPos.getCenter(), "幽匿尖啸体" + (i + 1),
                                    MyWayPoint.colorMap.get(MyWayPoint.darkBlue), 1));
                }
            });
        }
    }

    public void detectBlock() {
        resetBlockWayPoint();
        boolean existing = blockPosList.stream().anyMatch(pos -> {
            return boss.level().getBlockState(pos).getBlock().equals(Blocks.SCULK_SHRIEKER);
        });
        if (existing) {
            if ((Tick.get() - summonTick) % 100 == 99) {
                players.forEach(player -> {
                    SpecialEffectOnPlayer.addSilentEffect(player, 100);
                    SpecialEffectOnPlayer.addBlindEffect(player, 100);
                    player.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 200));
                });
            }
            if (summonTick + 400 < Tick.get()) {
                boss.heal(boss.getMaxHealth());
                players.forEach(player -> {
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
                summonTick = -1;
                players.forEach(player -> {
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
            resetBlockWayPoint();
        }
    }

    public void resetBlockWayPoint() {
        for (int i = 0; i < blockPosList.size(); i++) {
            BlockPos blockPos = blockPosList.get(i);
            if (boss.level().getBlockState(blockPos).is(Blocks.AIR)) {
                int finalI = i;
                players.forEach(player -> {
                    MyWayPoint.sendRemovePacketToClient(player, "幽匿尖啸体" + (finalI + 1));
                });
            }
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
                MobSpawn.MobBaseAttributes.setMobBaseAttributes(skeleton, 2000, 400, 400,
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

    // 对处于潜行状态的玩家，将免疫其50%伤害。对于穿戴有望山防具的玩家，将减少50%伤害。
    public static double onPlayerWithstandDamageRate(Player player, Mob mob) {
        if (mob.equals(instance.boss)) {
            double rate = 1;
            if (player.isShiftKeyDown()) {
                rate *= 0.5;
            }
            if (InventoryOperation.getArmors(player)
                    .stream().anyMatch(stack -> stack.getItem() instanceof MoontainArmor)) {
                rate *= 0.5;
            }
            return rate;
        }
        return 1;
    }

    // 玩家之间应当保持距离，过近的距离将会使范围伤害叠加，容易暴毙
    public void commonAttack() {
        if (Tick.get() % 20 == 0) {
            players.forEach(player -> {
                Set<Player> playerSet =
                        new HashSet<>(Compute.getNearEntity(player, Player.class, 3)
                                .stream().map(e -> (Player) e).toList());
                playerSet.forEach(eachPlayer -> {
                    Damage.causeAttackDamageToPlayer(boss, player, 10000);
                    Damage.causeManaDamageToPlayer(boss, player, 10000, 0.5, 100);
                    if (Tick.get() % 100 == 0) {
                        player.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 150));
                    }
                });
                ParticleProvider.createLineEffectParticle(boss.level(), (int) player.distanceTo(boss) * 5,
                        boss.getEyePosition(), player.getEyePosition(), style);
            });
        }
    }

    // 提示
    public void tips() {
        if (spawnTick == Tick.get()) {
            players.forEach(player -> {
                player.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 100));
                MySound.soundToPlayer(player, SoundEvents.WARDEN_AGITATED);
            });
        }
        if (spawnTick + 80 == Tick.get()) {
            players.forEach(player -> {
                sendFormatMSG(player, Te.s("穿戴/使用", "望山装备/武器", CustomStyle.styleOfMoontain, "对付",
                        "坚守者", style, "似乎非常有效!"));
            });
        }
        if (spawnTick + 160 == Tick.get()) {
            players.forEach(player -> {
                sendFormatMSG(player, Te.s("坚守者", style, "对处于",
                        "潜行状态", CustomStyle.styleOfEnd, "的玩家造成的伤害", "降低50%", ChatFormatting.GREEN));
            });
        }
        if (spawnTick + 240 == Tick.get()) {
            players.forEach(player -> {
                sendFormatMSG(player, Te.s("玩家之间需要保持一定距离，因为", "坚守者", style, "的普通技能是",
                        "范围伤害", CustomStyle.styleOfStone));
            });
        }
    }

    public static void sendFormatMSG(Player player, Component content) {
        Compute.sendFormatMSG(player, Te.s("循声", style), content);
    }

    @Override
    public String getKillCountDataKey() {
        return "Warden";
    }

    @Override
    public List<Component> getIntroduction() {
        List<Component> components = new ArrayList<>();
        components.add(Te.s("1.灾变神力:", style, "减少90%受到的伤害。"));
        components.add(Te.s("2.循声:", style, "对普通攻击、施法的玩家造成低额伤害。"));
        components.add(Te.s("3.", style, "当坚守者附近没有玩家时，会牵引所有玩家至其身边；"));
        components.add(Te.s(" 并击碎所有玩家100%双抗，施加重伤，持续10s。"));
        components.add(Te.s("4.", style, "当坚守者的生命值达50%时，会生成幽匿尖啸体(会有路径点提示)；"));
        components.add(Te.s(" 若在20s内未摧毁所有方块，坚守者将回复满生命值(至多触发一次)。"));
        components.add(Te.s("5.", style, "当坚守者生命值低于50%时，会召唤蝙蝠骷髅骑士。"));
        components.add(Te.s("6.", style, "对处在潜行状态的玩家，将免疫坚守50%伤害；"));
        components.add(Te.s(" 对于穿戴有望山防具的玩家，坚守者将减少其造成的50%伤害。"));
        components.add(Te.s("7.", style, "每秒对所有玩家造成混合伤害；"));
        components.add(Te.s(" 玩家之间应保持距离，过近的距离将会使该伤害叠加。"));
        return components;
    }
}
