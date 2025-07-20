package fun.wraq.events.mob.instance.instances.element;

import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.Amethyst_Crab_Entity;
import com.github.L_Ender.cataclysm.init.ModEntities;
import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.Utils;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.instance.NoTeamInstance;
import fun.wraq.process.func.guide.Guide;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class PurpleIronInstance extends NoTeamInstance {

    private static PurpleIronInstance instance;

    public static String mobName = "紫水晶巨蟹";

    public static PurpleIronInstance getInstance() {
        if (instance == null) {
            instance = new PurpleIronInstance(new Vec3(1171, -36, -172), 50, 60, new Vec3(1171, -36, -172),
                    Component.literal("紫水晶巨蟹").withStyle(CustomStyle.styleOfPurpleIron));
        }
        return instance;
    }

    public PurpleIronInstance(Vec3 pos, double range, int delayTick, Vec3 armorStandPos, MutableComponent name) {
        super(pos, range, delayTick, armorStandPos, name, 120);
    }

    @Override
    public void tickModule() {
        if (mobList.isEmpty()) return;
        Mob mob = this.mobList.get(0);
        if (mob == null || !mob.isAlive()) return;
        Element.provideElement(mob, Element.stone, 3);
    }

    @Override
    public MobAttributes getMainMobAttributes() {
        double maxHealth = 150000 * (1 + 0.75 * (Math.max(1, players.size()) - 1));
        return new MobAttributes(1000, 120, 120, 0.35, 3, 0.2, 25, 0, maxHealth, 0.3);
    }

    @Override
    public void summonModule(Level level) {
        Amethyst_Crab_Entity amethystCrabEntity = new Amethyst_Crab_Entity(ModEntities.AMETHYST_CRAB.get(), level);
        MobSpawn.setMobCustomName(amethystCrabEntity, Component.literal("紫水晶巨蟹").withStyle(CustomStyle.styleOfPurpleIron), 120);
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(amethystCrabEntity), 120);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(amethystCrabEntity, getMainMobAttributes());
        amethystCrabEntity.setHealth(amethystCrabEntity.getMaxHealth());
        amethystCrabEntity.moveTo(pos);
        level.addFreshEntity(amethystCrabEntity);
        mobList.add(amethystCrabEntity);
        ServerBossEvent serverBossEvent = (ServerBossEvent) (new ServerBossEvent(amethystCrabEntity.getDisplayName(), BossEvent.BossBarColor.BLUE, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);
        getNearPlayers(level).forEach(player -> {
            serverBossEvent.addPlayer((ServerPlayer) player);
        });
        bossInfoList.add(serverBossEvent);
        double maxHealth = 150000 * (1 + 0.75 * (Math.max(1, players.size()) - 1));
        Utils.fourPosOffset.forEach(offset -> {
            spawnEndermite(level, maxHealth * 0.25, pos.add(offset));
        });
        MobSpawn.setCanNotAddSlowDownOrImprison(amethystCrabEntity);
    }

    private void spawnEndermite(Level level, double maxHealth, Vec3 pos) {
        Endermite endermite = new Endermite(EntityType.ENDERMITE, level);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(endermite, Te.s("紫晶螨", CustomStyle.styleOfPurpleIron),
                120, 500, 80, 80, 0.35, 3,
                0.2, 25, 0, maxHealth, 0.3);
        endermite.moveTo(pos);
        level.addFreshEntity(endermite);
        mobList.add(endermite);
    }

    @Override
    public void exReward(Player player) {
        Guide.trigV2(player, Guide.StageV2.PURPLE_IRON_BOSS);
    }

    @Override
    public boolean allowReward(Player player) {
        return true;
    }

    @Override
    public Component allowRewardCondition() {
        return Te.s("");
    }

    public List<ItemAndRate> getRewardList() {
        return List.of(new ItemAndRate(ModItems.PURPLE_IRON_BUD_1.get(), 1),
                new ItemAndRate(new ItemStack(ModItems.GEM_PIECE.get(), 8), 0.1),
                new ItemAndRate(ModItems.PURPLE_IRON_BUD_2.get(), 0.1),
                new ItemAndRate(ModItems.ENHANCE_PURPLE_IRON_CHEST.get(), 0.01),
                new ItemAndRate(ModItems.PURPLE_IRON_SWORD.get(), 0.01),
                new ItemAndRate(ModItems.PURPLE_IRON_BOW.get(), 0.01),
                new ItemAndRate(ModItems.PURPLE_IRON_SCEPTRE.get(), 0.01),
                new ItemAndRate(ModItems.WORLD_SOUL_2.get(), 0.25),
                new ItemAndRate(ModItems.GOLD_COIN_BAG.get(), 0.1));
    }

    @Override
    public String getKillCountDataKey() {
        return "PurpleIronBoss";
    }
}
