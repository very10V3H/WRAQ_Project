package fun.wraq.events.mob.jungle;

import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.loot.C2LootItems;
import fun.wraq.process.func.StableAttributesModifier;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.newrunes.NewRuneItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Evoker;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class EvokerMasterSpawnController extends JungleMobSpawnController {

    public static final String MOB_NAME = "唤魔大师";
    public static final Style STYLE = CustomStyle.styleOfMana;
    public static final int XP_LEVEL = 60;

    public static EvokerMasterSpawnController instance;

    public static EvokerMasterSpawnController getInstance() {
        if (instance == null) {
            instance = new EvokerMasterSpawnController(Te.s(MOB_NAME, STYLE), new Vec3(1376, 81, -272),
                    new Vec3(1397, 90, -259), new Vec3(1362, 70, -288), XP_LEVEL, 48, Tick.min(3));
        }
        return instance;
    }

    public EvokerMasterSpawnController(Component name, Vec3 descriptionPos,
                                       Vec3 mobUpperBoundary, Vec3 mobLowerBoundary,
                                       int mobXpLevel, double detectionRadius, int refreshInterval) {
        super(name, descriptionPos, mobUpperBoundary, mobLowerBoundary, mobXpLevel, detectionRadius, refreshInterval);
    }

    @Override
    public void tryToReward(Player player) {
        getRewardItemList().forEach(itemAndRate -> {
            itemAndRate.sendWithMSG(player, 1);
        });
        double manaRecoverValue = (double) player.experienceLevel / 2 + PlayerAttributes.maxMana(player) * 0.05;
        StableAttributesModifier.addM(player, StableAttributesModifier.playerManaRecoverModifier,
                "BlueBuff", manaRecoverValue,
                Tick.get() + Tick.min(8), ModItems.EVOKER_SOUL.get());
        StableAttributesModifier.addM(player, StableAttributesModifier.playerCooldownModifier,
                "BlueBuff", 0.3, Tick.get() + Tick.min(8));
        sendMSG(player, Te.s("获得了持续", "8min", ChatFormatting.AQUA, "的", "唤魔之力", STYLE,
                "。在持续时间内，",
                ComponentUtils.AttributeDescription.manaRecover("+" + (int) manaRecoverValue),
                "、", ComponentUtils.AttributeDescription.coolDown("+30")));
    }

    @Override
    public MobAttributes getMobAttributes() {
        return new MobAttributes(200, 40, 40, 0.3, 2, 0.1, 3, 10, 5500, 0.3);
    }

    @Override
    public void spawnMob(Level level) {
        Evoker evoker = new Evoker(EntityType.EVOKER, level);
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(evoker), XP_LEVEL);
        MobSpawn.setMobCustomName(evoker, Te.s(name, STYLE), XP_LEVEL);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(evoker, getMobAttributes());
        evoker.moveTo(new Vec3(1376, 81, -272));
        level.addFreshEntity(evoker);
        mobs.add(evoker);
    }

    @Override
    public List<ItemAndRate> getRewardItemList() {
        return List.of(
                new ItemAndRate(ModItems.EVOKER_SOUL.get(), 16),
                new ItemAndRate(ModItems.SILVER_COIN.get(), 8),
                new ItemAndRate(ModItems.GEM_PIECE.get(), 1),
                new ItemAndRate(ModItems.LIGHTNING_ELEMENT_PIECE_0.get(), 4),
                new ItemAndRate(C2LootItems.EVOKER_SCEPTRE.get(), 0.02),
                new ItemAndRate(NewRuneItems.EVOKER_NEW_RUNE.get(), 0.02)
        );
    }

    @Override
    public double modifyMobWithstandDamage(Mob mob, Player player) {
        double rate = mob.getHealth() / mob.getMaxHealth();
        mob.setHealth((float) (mob.getMaxHealth() * (rate - 0.01)));
        return 0;
    }
}
