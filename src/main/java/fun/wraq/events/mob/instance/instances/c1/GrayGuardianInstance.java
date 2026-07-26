/** AI-Generated, 2026-07-26 */
package fun.wraq.events.mob.instance.instances.c1;

import com.Polarice3.Goety.common.entities.ModEntityType;
import com.Polarice3.Goety.common.entities.hostile.HauntedArmor;
import com.Polarice3.Goety.common.entities.hostile.illagers.Crusher;
import com.Polarice3.Goety.common.entities.hostile.illagers.Piker;
import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.Utils;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.instance.NoTeamInstance;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.guide.Guide;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.c1.NewC1Items;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

/**
 * 苍岩守卫 — 领主级怪物
 * <p>
 * 生成于苍峡附近(3577, 80, 2922)，主怪为 HauntedArmor，
 * 伴生 Piker(3577,80,2917) 和 Crusher(3577,80,2926) 护卫。
 * 推荐挑战等级 40，掉落盔甲碎片、世界之魂、金币袋。
 * </p>
 */
public class GrayGuardianInstance extends NoTeamInstance {

    private static GrayGuardianInstance instance;

    public static String mobName = "苍岩守卫";

    public static GrayGuardianInstance getInstance() {
        if (instance == null) {
            instance = new GrayGuardianInstance(new Vec3(3577, 80, 2922), 30, 60,
                    new Vec3(3577, 80, 2922),
                    Te.s("苍岩守卫", CustomStyle.styleOfStone));
        }
        return instance;
    }

    public GrayGuardianInstance(Vec3 pos, double range, int delayTick, Vec3 armorStandPos, MutableComponent name) {
        super(pos, range, delayTick, armorStandPos, name, 40);
    }

    @Override
    public MobAttributes getMainMobAttributes() {
        double maxHealth = 15000 * (1 + 0.75 * (Math.max(1, players.size()) - 1));
        return new MobAttributes(500, 50, 50, 0.25, 1.2, 0.1, 0, 0, maxHealth, 0.25);
    }

    @Override
    public void summonModule(Level level) {
        // ---- 主怪：HauntedArmor ----
        HauntedArmor boss = new HauntedArmor(ModEntityType.HAUNTED_ARMOR.get(), level);
        MobSpawn.setMobCustomName(boss, Te.s("苍岩守卫", CustomStyle.styleOfStone), 40);
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(boss), 40);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(boss, getMainMobAttributes());
        boss.setHealth(boss.getMaxHealth());
        boss.moveTo(3577.5, 80, 2922.5);
        boss.setItemSlot(EquipmentSlot.HEAD,
                new ItemStack(com.Polarice3.Goety.common.items.ModItems.CURSED_KNIGHT_HELMET.get()));
        boss.setItemSlot(EquipmentSlot.CHEST,
                new ItemStack(com.Polarice3.Goety.common.items.ModItems.CURSED_KNIGHT_CHESTPLATE.get()));
        boss.setItemSlot(EquipmentSlot.LEGS,
                new ItemStack(com.Polarice3.Goety.common.items.ModItems.CURSED_KNIGHT_LEGGINGS.get()));
        boss.setItemSlot(EquipmentSlot.FEET,
                new ItemStack(com.Polarice3.Goety.common.items.ModItems.CURSED_KNIGHT_BOOTS.get()));
        boss.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
        boss.setItemSlot(EquipmentSlot.MAINHAND,
                new ItemStack(com.Polarice3.Goety.common.items.ModItems.FELL_BLADE.get()));
        level.addFreshEntity(boss);
        mobList.add(boss);
        MobSpawn.setCanNotAddSlowDownOrImprison(boss);

        ServerBossEvent bossEvent = (ServerBossEvent) new ServerBossEvent(boss.getDisplayName(),
                        BossEvent.BossBarColor.WHITE, BossEvent.BossBarOverlay.PROGRESS)
                .setDarkenScreen(true);
        getNearPlayers(level).forEach(player -> bossEvent.addPlayer((ServerPlayer) player));
        bossInfoList.add(bossEvent);

        // ---- 护卫1：Piker (南) ----
        Piker piker = new Piker(ModEntityType.PIKER.get(), level);
        MobSpawn.setMobCustomName(piker, Te.s("苍岩护卫", ChatFormatting.GRAY), 40);
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(piker), 40);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(piker, 150, 30, 30, 0.2,
                1, 0, 0, 0, 3000 * (1 + 0.75 * (Math.max(1, players.size()) - 1)), 0.3);
        piker.setHealth(piker.getMaxHealth());
        piker.moveTo(3577.5, 80, 2917.5);
        level.addFreshEntity(piker);
        mobList.add(piker);

        // ---- 护卫2：Crusher (北) ----
        Crusher crusher = new Crusher(ModEntityType.CRUSHER.get(), level);
        MobSpawn.setMobCustomName(crusher, Te.s("苍岩粉碎者", ChatFormatting.DARK_GRAY), 40);
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(crusher), 40);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(crusher, 200, 40, 20, 0.15,
                1.2, 0, 0, 0, 4000 * (1 + 0.75 * (Math.max(1, players.size()) - 1)), 0.2);
        crusher.setHealth(crusher.getMaxHealth());
        crusher.moveTo(3577.5, 80, 2926.5);
        level.addFreshEntity(crusher);
        mobList.add(crusher);
    }

    @Override
    public void tickModule() {
        if (mobList.isEmpty()) return;
        Mob boss = mobList.get(0);
        if (boss == null || !boss.isAlive()) return;

        // 每 5 秒主怪对近战玩家造成伤害并回复自身
        if (boss.tickCount % 100 == 0) {
            Level level = boss.level();
            List<Player> players = getNearPlayers(level);
            for (Player player : players) {
                if (player.position().distanceTo(boss.position()) <= 6) {
                    Damage.causeManaDamageToPlayer(boss, player, 300);
                    boss.heal(100);
                } else {
                    Damage.causeManaDamageToPlayer(boss, player, 150);
                }
            }
        }
    }

    @Override
    public boolean allowReward(Player player) {
        return player.experienceLevel >= 40;
    }

    @Override
    public Component allowRewardCondition() {
        return Te.s("需要达到", Utils.getLevelDescription(40), "才能获取奖励");
    }

    @Override
    public void exReward(Player player) {
        Guide.trigV2(player, Guide.StageV2.DEFEAT_GRAY_GUARDIAN);
    }

    @Override
    public List<ItemAndRate> getRewardList() {
        return List.of(
                new ItemAndRate(NewC1Items.ARMOR_PIECE.get(), 1),
                new ItemAndRate(ModItems.WORLD_SOUL_2.get(), 0.25),
                new ItemAndRate(ModItems.GOLD_COIN_BAG.get(), 0.1)
        );
    }

    @Override
    public String getKillCountDataKey() {
        return "GrayGuardian";
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
