package fun.wraq.events.mob.instance.instances.element;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.Utils;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSkillUtil;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.instance.NoTeamInstance;
import fun.wraq.process.func.guide.Guide;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class PlainInstance extends NoTeamInstance {

    private static PlainInstance instance;

    public static final String mobName = "普莱尼";

    public static final Vec3 POS = new Vec3(612, 70, 440);

    /** 普莱尼出生点 Z-20 的传送路径点。 */
    public static final Vec3 WAYPOINT_POS = POS.add(0, 0, -20);

    private static final int BOSS_LEVEL = 20;
    private static final double MAX_HEALTH = 3000;
    private static final double FOLLOWER_MAX_HEALTH = 1000;
    private static final float HEAL_PER_FOLLOWER = 25;

    public static PlainInstance getInstance() {
        if (instance == null) {
            instance = new PlainInstance(POS, 30, 60, POS,
                    Te.s("普莱尼", ChatFormatting.GREEN));
        }
        return instance;
    }

    public PlainInstance(Vec3 pos, double range, int delayTick, Vec3 armorStandPos, MutableComponent name) {
        super(pos, range, delayTick, armorStandPos, name, BOSS_LEVEL);
    }

    @Override
    public void tickModule() {
        if (mobList.isEmpty()) return;
        Mob mob = this.mobList.get(0);
        if (mob == null || !mob.isAlive()) return;
        Level level = mob.level();
        List<Player> players = getNearPlayers(level);
        // 技能玩家选定：排除与Boss垂直高差超过8格的玩家
        List<Player> skillPlayers = players.stream()
                .filter(player -> Math.abs(player.getY() - mob.getY()) <= 8)
                .toList();
        // 每 2.5s 向一名玩家发射带前摇的慢速生机绿法球。
        if (mob.tickCount % 50 == 0 && !skillPlayers.isEmpty()) {
            Player target = null;
            if (mob.getTarget() instanceof Player targeted && skillPlayers.contains(targeted)) {
                target = targeted;
            } else {
                target = skillPlayers.get(mob.getRandom().nextInt(skillPlayers.size()));
            }
            MobSkillUtil.shootManaProjectile(mob, target, true, 0.5f, 300,
                    CustomStyle.styleOfLife, 0, 0);
        }
        // 每有一名存活信徒，普莱尼每秒回复 25 点生命值。
        if (mob.tickCount % 20 == 0) {
            long aliveFollowers = mobList.stream()
                    .skip(1)
                    .filter(follower -> follower != null && follower.isAlive())
                    .count();
            if (aliveFollowers > 0) {
                mob.heal(aliveFollowers * HEAL_PER_FOLLOWER);
            }
        }
    }

    @Override
    public MobAttributes getMainMobAttributes() {
        return new MobAttributes(400, 40, 40, 0.2, 1, 0, 0, 0, MAX_HEALTH, 0.2);
    }

    @Override
    public Element.Unit getElementUnit() {
        return new Element.Unit(Element.life, 2);
    }

    @Override
    public List<Component> getIntroduction() {
        Style style = CustomStyle.styleOfPlain;
        return List.of(
                Te.s("1.召唤:", style, "战斗开始时召唤4名", "「普莱尼信徒」", ChatFormatting.GRAY, "协助作战"),
                Te.s("2.元素:", style, "拥有", "2级生命元素", ChatFormatting.GREEN, "加成"),
                Te.s("3.生命法球:", style, "每2.5s向一名玩家发射慢速法球，前摇0.5s，造成300魔法伤害。"),
                Te.s("4.信徒增益:", style, "每有一名存活的普莱尼信徒，普莱尼每秒回复", "25", ChatFormatting.GREEN, "生命值。")
        );
    }

    @Override
    public void summonModule(Level level) {
        ZombieVillager zombie = new ZombieVillager(EntityType.ZOMBIE_VILLAGER, level);
        MobSpawn.setMobCustomName(zombie, Component.literal("普莱尼").withStyle(CustomStyle.styleOfPlain), BOSS_LEVEL);
        zombie.setVillagerData(new VillagerData(VillagerType.PLAINS, VillagerProfession.LIBRARIAN, 0));
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(zombie), BOSS_LEVEL);
        applyBossAttributes(zombie, "main", getMainMobAttributes());
        zombie.setHealth(zombie.getMaxHealth());
        MobSpawn.setStainArmorOnMob(zombie, CustomStyle.styleOfLife);
        zombie.setItemInHand(InteractionHand.MAIN_HAND, Compute.getSimpleFoiledItemStack(Items.GOLDEN_HOE));
        zombie.moveTo(pos);
        level.addFreshEntity(zombie);
        mobList.add(zombie);
        MobSpawn.setCanNotAddSlowDownOrImprison(zombie);
        ServerBossEvent serverBossEvent = (ServerBossEvent) (new ServerBossEvent(zombie.getDisplayName(), BossEvent.BossBarColor.GREEN, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);
        getNearPlayers(level).forEach(player -> {
            serverBossEvent.addPlayer((ServerPlayer) player);
        });
        bossInfoList.add(serverBossEvent);

        List<Vec3> offset = List.of(
                new Vec3(1, 0, 0),
                new Vec3(-1, 0, 0),
                new Vec3(0, 0, 1),
                new Vec3(0, 0, -1)
        );
        for (int i = 0 ; i < 4 ; i ++) {
            spawnBabyZombie(level, pos.add(offset.get(i)));
        }
    }

    private void spawnBabyZombie(Level level, Vec3 pos) {
        ZombieVillager babyZombie = new ZombieVillager(EntityType.ZOMBIE_VILLAGER, level);
        MobSpawn.setMobCustomName(babyZombie, Te.s("普莱尼信徒"), 32);
        babyZombie.setVillagerData(new VillagerData(VillagerType.PLAINS, VillagerProfession.LIBRARIAN, 0));
        babyZombie.setBaby(true);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(babyZombie, 100, 40, 40, 0.2,
                1, 0, 0, 0, FOLLOWER_MAX_HEALTH, 0.2);
        babyZombie.setItemSlot(EquipmentSlot.HEAD, lifeHat());
        babyZombie.moveTo(pos);
        level.addFreshEntity(babyZombie);
        mobList.add(babyZombie);
    }

    /** 与普莱尼同色系的信徒帽子。 */
    private static ItemStack lifeHat() {
        ItemStack hat = new ItemStack(Items.LEATHER_HELMET);
        CompoundTag display = new CompoundTag();
        display.putInt("color", CustomStyle.styleOfLife.getColor().getValue());
        hat.getOrCreateTag().put("display", display);
        return hat;
    }

    @Override
    public void exReward(Player player) {
        Guide.trigV2(player, Guide.StageV2.DEFEAT_PLAIN_BOSS);
    }

    @Override
    public boolean allowReward(Player player) {
        return player.experienceLevel >= 10;
    }

    @Override
    public Component allowRewardCondition() {
        return Te.s("需要达到", Utils.getLevelDescription(10), "才能获取奖励");
    }

    public List<ItemAndRate> getRewardList() {
        return List.of(new ItemAndRate(ModItems.PLAIN_BOSS_SOUL.get(), 2),
                new ItemAndRate(ModItems.PLAIN_BOSS_SCEPTRE.get(), 0.02),
                new ItemAndRate(ModItems.PLAIN_NECKLACE.get(), 0.16),
                new ItemAndRate(ModItems.PLAIN_ATTACK_RING_0.get(), 0.1),
                new ItemAndRate(ModItems.PLAIN_MANA_RING_0.get(), 0.1),
                new ItemAndRate(ModItems.PLAIN_HEALTH_RING_0.get(), 0.1),
                new ItemAndRate(ModItems.PLAIN_DEFENCE_RING_0.get(), 0.1),
                new ItemAndRate(ModItems.WORLD_SOUL_2.get(), 0.25),
                new ItemAndRate(ModItems.GOLD_COIN_BAG.get(), 0.1));
    }

    @Override
    public List<ItemAndRate> getFirstClearRewards() {
        return List.of(
                new ItemAndRate(ModItems.PLAIN_ATTACK_RING_0.get(), 1),
                new ItemAndRate(ModItems.PLAIN_MANA_RING_0.get(), 1),
                new ItemAndRate(ModItems.PLAIN_HEALTH_RING_0.get(), 1),
                new ItemAndRate(ModItems.PLAIN_DEFENCE_RING_0.get(), 1)
        );
    }

    @Override
    public String getKillCountDataKey() {
        return "PlainBoss";
    }

    @Override
    public Item getSummonNeedItem() {
        return ModItems.PLAIN_SOUL.get();
    }

    @Override
    public Item getRewardNeedItem() {
        return ModItems.REASON.get();
    }

    @Override
    public int getRewardNeedItemCount() {
        return 5;
    }
}
