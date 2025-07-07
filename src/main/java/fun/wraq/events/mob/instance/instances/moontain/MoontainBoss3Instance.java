package fun.wraq.events.mob.instance.instances.moontain;

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
import fun.wraq.process.func.StableAttributesModifier;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.effect.SpecialEffectOnPlayer;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.system.forge.ForgeEquipUtils;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.moontain.MoontainItems;
import fun.wraq.series.moontain.equip.curios.MoontainCurios;
import fun.wraq.series.moontain.equip.weapon.MoontainUtils;
import net.mcreator.borninchaosv.entity.LordTheHeadlessEntity;
import net.mcreator.borninchaosv.init.BornInChaosV1ModEntities;
import net.mcreator.borninchaosv.init.BornInChaosV1ModMobEffects;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.BossEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MoontainBoss3Instance extends NoTeamInstance {

    private static MoontainBoss3Instance instance;

    public static String mobName = "望山武魂";
    public static Style style = CustomStyle.styleOfMoontain;

    public static MoontainBoss3Instance getInstance() {
        if (instance == null) {
            instance = new MoontainBoss3Instance(new Vec3(1983, 239, -881), 20, 200, new Vec3(1983, 239, -881),
                    Te.s(mobName, style));
        }
        return instance;
    }

    public MoontainBoss3Instance(Vec3 pos, double range, int delayTick, Vec3 armorStandPos, MutableComponent name) {
        super(pos, range, delayTick, armorStandPos, name, 240);
    }

    public static int leftLifeTimes = 0;
    public static int recoverFlag = 0;
    public static int stage = 0;

    public double getRate() {
        return 1 + stage * 0.25;
    }

    public static boolean beforeKill(Mob mob) {
        if (MobSpawn.getMobOriginName(mob).equals(mobName) && leftLifeTimes > 0) {
            leftLifeTimes --;
            recoverFlag = 1;
            stage ++;
            mob.setHealth(mob.getMaxHealth());
            return false;
        }
        return true;
    }

    @Override
    public void tickModule() {
        if (mobList.isEmpty()) return;
        Mob boss = mobList.get(0);
        if (recoverFlag == 1) {
            recoverFlag = 0;
            MobSpawn.MobBaseAttributes.attackDamage.put(MobSpawn.getMobOriginName(boss), 7600 * getRate());
            Level level = boss.level();
            getNearPlayers(level)
                    .forEach(player -> {
                        Damage.causeAttackDamageToPlayer(boss, player, 10000 * getRate());
                        Damage.causeManaDamageToPlayer(boss, player, 5000 * getRate(), 0, 250);
                        ParticleProvider.createLineEffectParticle(level, (int) boss.distanceTo(player) * 5,
                                boss.getEyePosition(), player.getEyePosition(), CustomStyle.styleOfMoontain);
                        SpecialEffectOnPlayer.addHealingReduction(player, "MoontainBoss3HealingReduction",
                                0.6, 100);
                        Compute.sendFormatMSG(player, Te.s("望山武士", CustomStyle.styleOfMoontain),
                                Te.s(" - 回火 - " + (leftLifeTimes + 1), CustomStyle.styleOfMoontain));
                    });
            MySound.soundToNearPlayer(level, boss.getEyePosition(), SoundEvents.WITHER_AMBIENT);
        }
        skill0(boss);
        skill1(boss);
        skill2(boss);
    }


    /**
     * 每2秒对周围玩家造成5k物理伤害与3.5k魔法伤害，并施加炼狱之火
     */
    public void skill0(Mob boss) {
        if (Tick.get() % 40 == 0) {
            Level level = boss.level();
            getNearPlayers(level)
                    .forEach(player -> {
                        Damage.causeAttackDamageToPlayer(boss, player, 10000 * getRate());
                        Damage.causeManaDamageToPlayer(boss, player, 5000 * getRate(), 0, 250);
                        ParticleProvider.createLineEffectParticle(level, (int) boss.distanceTo(player) * 5,
                                boss.getEyePosition(), player.getEyePosition(), CustomStyle.styleOfMoontain);
                        player.addEffect(new MobEffectInstance(BornInChaosV1ModMobEffects.INFERNAL_FLAME.get(), 50));
                    });
        }
    }

    /**
     * 每5s对距离最近的玩家施加5s的40%重伤效果，并降低40%造成伤害
     */
    public void skill1(Mob boss) {
        if (Tick.get() % 100 == 0) {
            getNearPlayers(boss.level()).stream().min(new Comparator<Player>() {
                @Override
                public int compare(Player o1, Player o2) {
                    return (int) (o1.distanceTo(boss) - o2.distanceTo(boss));
                }
            }).ifPresent(player -> {
                SpecialEffectOnPlayer.addHealingReduction(player, "MoontainBoss3HealingReduction", 0.4, 100);
                StableAttributesModifier.addM(player, StableAttributesModifier.playerMonsterControlDamageEffect,
                        "MoontainBoss3DamageControl", -0.4, Tick.get() + 100);
                Compute.sendDebuffTime(player, "hud/damage_reduction", 100, 40, false);
                ParticleProvider.createBreakBlockParticle(player, Blocks.DARK_PRISMARINE);
                SpecialEffectOnPlayer.addSlowdownEffect(player, 0.6, 100, "MoontainBoss3SlowdownEffect");
            });
        }
    }


    /**
     * 每过一个阶段，削减附近玩家100护甲，40魔抗
     */
    public void skill2(Mob boss) {
        if (stage > 0 && Tick.get() % 20 == 0) {
            getNearPlayers(boss.level())
                    .forEach(player -> {
                        StableAttributesModifier.addM(player, StableAttributesModifier.playerDefenceDecreaseModifier,
                                "MoontainBoss3DefenceReduction", 100 * stage, Tick.get() + 100);
                        StableAttributesModifier.addM(player, StableAttributesModifier.playerManaDefenceDecreaseModifier,
                                "MoontainBoss3ManaDefenceReduction", 40 * stage, Tick.get() + 100);
                        Compute.sendDebuffTime(player, "hud/defence_reduction", 100, stage, false);
                    });
        }
    }

    @Override
    public MobAttributes getMainMobAttributes() {
        double maxHealth = 2500 * Math.pow(10, 4) * (1 + 0.75 * (Math.max(1, players.size()) - 1));
        return new MobAttributes(3800, 360, 360, 0.4, 3, 0.6, 175, 0, maxHealth, 0.45);
    }

    @Override
    public void summonModule(Level level) {
        LordTheHeadlessEntity entity =
                new LordTheHeadlessEntity(BornInChaosV1ModEntities.LORD_THE_HEADLESS.get(), level);
        MobSpawn.setMobCustomName(entity, Te.s(mobName, style), 240);
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(entity), 240);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(entity, getMainMobAttributes());
        entity.setHealth(entity.getMaxHealth());
        entity.moveTo(pos);
        level.addFreshEntity(entity);
        ServerBossEvent serverBossEvent = (ServerBossEvent) (new ServerBossEvent(entity.getDisplayName(),
                BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);
        getNearPlayers(level).forEach(player -> {
            serverBossEvent.addPlayer((ServerPlayer) player);
        });
        bossInfoList.add(serverBossEvent);
        mobList.add(entity);
        leftLifeTimes = 4;
        recoverFlag = 0;
        stage = 0;
        MobSpawn.setCanNotAddSlowDownOrImprison(entity);
    }

    private final String PLAYER_LAST_GET_REWARD_TIMES_KEY = "PlayerLastGetMoontainBoss3RewardTimes";

    @Override
    public void exReward(Player player) {
        boolean getReward = false;
        SecureRandom secureRandom = new SecureRandom();
        if (secureRandom.nextDouble() < 0.05) {
            giveEquip(player, secureRandom);
            getReward = true;
        }

        if (secureRandom.nextDouble() < 0.1) {
            giveCurios(player, secureRandom);
            getReward = true;
        }

        CompoundTag data = player.getPersistentData();
        if (getReward) {
            data.putInt(PLAYER_LAST_GET_REWARD_TIMES_KEY, 0);
        } else {
            data.putInt(PLAYER_LAST_GET_REWARD_TIMES_KEY, data.getInt(PLAYER_LAST_GET_REWARD_TIMES_KEY) + 1);
            int times = data.getInt(PLAYER_LAST_GET_REWARD_TIMES_KEY);
            if (times < 8) {
                Compute.sendFormatMSG(player, Te.s("望山武魂", CustomStyle.styleOfMoontain),
                        Te.s("再完成", String.valueOf(8 - times), CustomStyle.styleOfMoontain,
                                "次挑战就能直接获得", "望山装备/望山饰品", CustomStyle.styleOfMoontain, "了!"));
            } else {
                data.putInt(PLAYER_LAST_GET_REWARD_TIMES_KEY, 0);
                if (secureRandom.nextBoolean()) {
                    giveCurios(player, secureRandom);
                } else {
                    giveEquip(player, secureRandom);
                }
                Compute.formatBroad(player.level(), Te.s("望山武魂", CustomStyle.styleOfMoontain),
                        Te.s("", player.getDisplayName(), "经过了重重磨难，终于获得了梦寐以求的", "望山装备",
                                CustomStyle.styleOfMoontain));
            }
        }

        stage = 0;
    }

    public void giveEquip(Player player, SecureRandom secureRandom) {
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
        Compute.forgingHoverName(stack);
        MoontainUtils.formatBroad(player.level(), Te.s(player.getDisplayName(),
                " 击杀 ", mobName, style, " 获得了 ", stack.getDisplayName()));
        InventoryOperation.giveItemStack(player, stack);
        NoTeamInstanceModule.putPlayerAllowReward(player, NoTeamInstanceModule.AllowRewardKey.enderGuardian, true);
    }

    public void giveCurios(Player player, SecureRandom secureRandom) {
        List<Item> curiosList = MoontainUtils.getMoontainCurios();
        ItemStack stack = new ItemStack(curiosList.get(secureRandom.nextInt(curiosList.size())));
        if (stack.getItem() instanceof MoontainCurios moontainCurios) {
            moontainCurios.setAttribute(stack);
        }
        Compute.forgingHoverName(stack);
        MoontainUtils.formatBroad(player.level(), Te.s(player.getDisplayName(),
                " 击杀 ", mobName, style, " 获得了 ", stack.getDisplayName()));
        InventoryOperation.giveItemStack(player, stack);
    }

    @Override
    public boolean allowReward(Player player) {
        return NoTeamInstanceModule.getPlayerAllowReward(player, NoTeamInstanceModule.AllowRewardKey.moontainBoss);
    }

    @Override
    public Component allowRewardCondition() {
        return NoTeamInstanceModule.AllowRewardCondition.BLACK_CASTLE_WEAPON;
    }

    public List<ItemAndRate> getRewardList() {
        return List.of(
                new ItemAndRate(MoontainItems.STONE_FRAGMENT.get(), 16),
                new ItemAndRate(ModItems.WORLD_SOUL_2.get(), 0.25),
                new ItemAndRate(ModItems.GOLD_COIN_BAG.get(), 0.1));
    }

    @Override
    public Item getSummonAndRewardNeedItem() {
        return MoontainItems.HEART.get();
    }

    @Override
    public String getKillCountDataKey() {
        return "MoontainBoss3";
    }

    @Override
    public List<Component> getIntroduction() {
        List<Component> components = new ArrayList<>();
        components.add(Te.s("1.", style, "每2s对周围玩家造成混合伤害，并施加炼狱之火。"));
        components.add(Te.s("2.", style, "每5s对距离最近的玩家施加5s的40%重伤效果，并降低40%造成伤害。"));
        components.add(Te.s("3.", style, "每当生命值为0%，阻止死亡，削减附近玩家双抗，至多5次。"));
        return components;
    }
}
