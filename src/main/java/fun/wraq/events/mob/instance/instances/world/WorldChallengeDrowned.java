/**
 * AI-Generated, 2026-07-18
 * 本源挑战 - Drowned
 * 每日挑战，使用熵尘召唤，不消耗体力
 * 星级评价与奖励状态存储使用 Tower#putPlayerStatus / getPlayerStatus
 */
package fun.wraq.events.mob.instance.instances.world;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.Utils;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.instance.NoTeamInstance;
import fun.wraq.process.func.guide.Guide;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.tower.Tower;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class WorldChallengeDrowned extends NoTeamInstance {

    private static WorldChallengeDrowned instance;

    public static final String mobName = "Drowned";
    /** 使用 Tower 的 status string 来存储该挑战的星级 (0-4) */
    private static final int STATUS_INDEX = 0;

    public static WorldChallengeDrowned getInstance() {
        if (instance == null) {
            instance = new WorldChallengeDrowned(new Vec3(3967.5, 92, 3092.5), 40, 100,
                    new Vec3(3967.5, 92, 3092.5), Te.s(mobName, CustomStyle.styleOfSea));
        }
        return instance;
    }

    private WorldChallengeDrowned(Vec3 pos, double range, int delayTick,
                                   Vec3 armorStandPos, MutableComponent name) {
        super(pos, range, delayTick, armorStandPos, name, 40);
    }

    @Override
    public MobAttributes getMainMobAttributes() {
        double maxHealth = 3500 * (1 + 0.75 * (Math.max(1, players.size()) - 1));
        return new MobAttributes(200, 40, 40, 0.2, 1, 0, 0, 0, maxHealth, 0.2);
    }

    @Override
    public Element.Unit getElementUnit() {
        return new Element.Unit(Element.water, 2);
    }

    @Override
    public void summonModule(Level level) {
        List<Vec3> spawnPositions = List.of(
                new Vec3(3965, 90, 3081),
                new Vec3(3947, 92, 3087),
                new Vec3(3962, 91, 3091),
                new Vec3(3954, 91, 3101)
        );

        for (int i = 0; i < spawnPositions.size(); i++) {
            Drowned drowned = new Drowned(EntityType.DROWNED, level);
            MobSpawn.setMobCustomName(drowned, Te.s("本源溺尸", CustomStyle.styleOfSea), 40);
            MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(drowned), 40);
            MobSpawn.MobBaseAttributes.setMobBaseAttributes(drowned, getMainMobAttributes());
            drowned.setHealth(drowned.getMaxHealth());

            // 头顶龟壳
            drowned.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.TURTLE_HELMET));
            // 钻石靴子
            drowned.setItemSlot(EquipmentSlot.FEET, new ItemStack(Items.DIAMOND_BOOTS));
            // 手持击退三叉戟
            ItemStack trident = new ItemStack(Items.TRIDENT);
            trident.enchant(Enchantments.KNOCKBACK, 2);
            drowned.setItemSlot(EquipmentSlot.MAINHAND, trident);

            drowned.moveTo(spawnPositions.get(i));
            level.addFreshEntity(drowned);
            mobList.add(drowned);
            MobSpawn.setCanNotAddSlowDownOrImprison(drowned);
        }
    }

    @Override
    public void exReward(Player player) {
        // 参考 Tower#reward: 60s(1200tick)为一阶段，计算星级
        int stage = 4 - Math.min(3, Math.max(0, (Tick.get() - this.spawnTick) / Tick.s(15)));

        int storedStars = Tower.getPlayerStatus(player, STATUS_INDEX);
        int minus = stage - storedStars;

        if (minus > 0) {
            // 参考 Tower#givePlayerStar: 星级差额 * 基础奖励
            ItemStack rewardStack = new ItemStack(ModItems.WORLD_SOUL_5.get(), minus);
            InventoryOperation.giveItemStackWithMSGByBatch(player, rewardStack);

            // 更新 Tower status string 中对应索引的星级
            String statusStr = Tower.getPlayerStatus(player);
            StringBuilder sb = new StringBuilder(statusStr);
            sb.setCharAt(STATUS_INDEX, String.valueOf(stage).charAt(0));
            Tower.putPlayerStatus(player, sb.toString());

            Compute.sendFormatMSG(player, Te.s("本源挑战", CustomStyle.styleOfWorld),
                    Te.s("星级评价提升至 ", stage + "★", CustomStyle.styleOfWorld,
                            "，额外获得 ", String.valueOf(minus),
                            CustomStyle.styleOfWorld, " 个", ModItems.WORLD_SOUL_5.get().getDefaultInstance().getDisplayName()));
        }

        // 参考 Tower: 无论是否有奖励提升，均发送完成消息
        double elapsedSeconds = (Tick.get() - this.spawnTick) / 20.0;
        String timeStr = String.format("%.2fs", elapsedSeconds);
        Compute.sendFormatMSG(player, Te.s("本源挑战", CustomStyle.styleOfWorld),
                Te.s(player.getDisplayName(), " 完成了 ",
                        mobName, CustomStyle.styleOfSea, " 的 ",
                        stage + "★", CustomStyle.styleOfWorld, " 挑战.",
                        " (" + timeStr + ")", CustomStyle.styleOfWorld));

        Guide.trigV2(player, Guide.StageV2.DROWNED_CHALLENGE);
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
    public List<ItemAndRate> getRewardList() {
        return List.of();
    }

    @Override
    public String getKillCountDataKey() {
        return "WorldChallengeDrowned";
    }

    @Override
    public Item getSummonNeedItem() {
        return ModItems.WORLD_SOUL_1.get();
    }

    @Override
    public Item getRewardNeedItem() {
        return ModItems.REASON.get();
    }

    @Override
    public int getRewardNeedItemCount() {
        return 0;
    }

    @Override
    public int getMaxPlayerNum() {
        return 1;
    }

    @Override
    public List<Component> getIntroduction() {
        return List.of(
                Te.s("1. ", CustomStyle.styleOfSea, "手持", ModItems.WORLD_SOUL_1.get().getDefaultInstance().getDisplayName(),
                        "右键以召唤挑战"),
                Te.s("2. ", CustomStyle.styleOfSea, "击败4只本源溺尸"),
                Te.s("3. ", CustomStyle.styleOfSea, "根据完成时间获得星级评价(1-4★):"),
                Te.s("   ", CustomStyle.styleOfSea, "60s内4★, 120s内3★, 180s内2★, 240s内1★"),
                Te.s("4. ", CustomStyle.styleOfSea, "提升星级可额外获得星辰奖励"));
    }

    @Override
    protected boolean allowSummon(Player player) {
        return Tower.getPlayerStatus(player, STATUS_INDEX) < 4 && player.experienceLevel >= 40;
    }

    @Override
    protected Component disallowReason(Player player) {
        if (player.experienceLevel < 40) {
            return Te.s("需要达到", Utils.getLevelDescription(40), "才能进行挑战");
        }
        return Te.s("今天已经4★通关了！");
    }

    @Override
    protected Component prefix() {
        return Te.s("本源挑战", CustomStyle.styleOfWorld);
    }

    @Override
    protected boolean allowAutoStart() {
        return false;
    }
}
