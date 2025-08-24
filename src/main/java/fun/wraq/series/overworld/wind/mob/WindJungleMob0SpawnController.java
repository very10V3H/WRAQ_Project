package fun.wraq.series.overworld.wind.mob;

import com.Polarice3.Goety.common.entities.ModEntityType;
import com.Polarice3.Goety.common.entities.ally.illager.WindCallerServant;
import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.jungle.JungleMobSpawnController;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.wind.WindItems;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class WindJungleMob0SpawnController extends JungleMobSpawnController {

    public static final String MOB_NAME = "唤风者仆从";
    public static final Style STYLE = CustomStyle.styleOfWind;
    public static final int XP_LEVEL = 200;
    public static final Vec3 spawnPos = new Vec3(1813, 159, -1553);

    public static WindJungleMob0SpawnController instance;

    public static WindJungleMob0SpawnController getInstance() {
        if (instance == null) {
            instance = new WindJungleMob0SpawnController(Te.s(MOB_NAME, STYLE),
                    spawnPos.add(0, 1, 0),
                    new Vec3(1838, 171, -1529),
                    new Vec3(1782, 150, -1586), XP_LEVEL, 32, Tick.s(3));
        }
        return instance;
    }

    public WindJungleMob0SpawnController(Component name, Vec3 descriptionPos,
                                         Vec3 mobUpperBoundary, Vec3 mobLowerBoundary,
                                         int mobXpLevel, double detectionRadius, int refreshInterval) {
        super(name, descriptionPos, mobUpperBoundary, mobLowerBoundary, mobXpLevel, detectionRadius, refreshInterval);
    }

    @Override
    public void tryToReward(Player player) {
        getRewardItemList().forEach(itemAndRate -> {
            itemAndRate.sendWithMSG(player, 1);
        });
    }

    @Override
    public MobAttributes getMobAttributes() {
        return new MobAttributes(2800, 240, 240, 0.4, 3, 0.4, 80, 25, 400 * Math.pow(10, 4), 0.4);
    }

    @Override
    public void spawnMob(Level level) {
        WindCallerServant mob = new WindCallerServant(ModEntityType.WIND_CALLER_SERVANT.get(), level);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(mob, Te.s(name, STYLE), XP_LEVEL, getMobAttributes());
        MobSpawn.setStainArmorOnMob(mob, STYLE);
        mob.moveTo(spawnPos);
        level.addFreshEntity(mob);
        mobs.add(mob);
    }

    @Override
    public Element.Unit getElementUnit() {
        return new Element.Unit(Element.wind, 6);
    }

    @Override
    public List<ItemAndRate> getRewardItemList() {
        return List.of(
                new ItemAndRate(WindItems.WIND_CRYSTAL_0.get(), 0.8),
                new ItemAndRate(WindItems.WIND_SOUL.get(), 8),
                new ItemAndRate(WindItems.WIND_BOOTS_0.get(), 0.01),
                new ItemAndRate(ModItems.GOLD_COIN.get(), 1),
                new ItemAndRate(ModItems.GEM_PIECE.get(), 3)
        );
    }
}
