package com.very.wraq.events.mob.chapter7;

import com.obscuria.aquamirae.common.entities.TorturedSoul;
import com.obscuria.aquamirae.registry.AquamiraeEntities;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.ItemAndRate;
import com.very.wraq.common.registry.ModItems;
import com.very.wraq.events.mob.MobSpawn;
import com.very.wraq.events.mob.MobSpawnController;
import com.very.wraq.events.mob.loot.C7LootItems;
import com.very.wraq.process.system.element.Element;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.overworld.chapter7.C7Items;
import net.mcreator.borninchaosv.entity.BoneImpEntity;
import net.mcreator.borninchaosv.init.BornInChaosV1ModEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TorturedSoulSpawnController extends MobSpawnController {

    public static String mobName = "被折磨的灵魂";
    private static TorturedSoulSpawnController instance;

    public static TorturedSoulSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(968, 199, -15),
                    new Vec3(944, 198, -2),
                    new Vec3(954, 193, 0),
                    new Vec3(970, 199, 0),
                    new Vec3(981, 199, -5),
                    new Vec3(938, 197, 5),
                    new Vec3(954, 194, 7),
                    new Vec3(931, 195, 15),
                    new Vec3(940, 195, 11),
                    new Vec3(951, 195, 12),
                    new Vec3(966, 193, 14),
                    new Vec3(943, 196, 24),
                    new Vec3(943, 196, 35)
            );
            instance = new TorturedSoulSpawnController(spawnPos, spawnPos.size() * 4, 1000, 51, 900, -50, world, 2, 220);
        }
        return instance;
    }

    public TorturedSoulSpawnController(List<Vec3> canSpawnPos, int oneZoneMaxMobNum, int boundaryUpX, int boundaryUpZ,
                                       int boundaryDownX, int boundaryDownZ, Level level, int mobPlayerRate, int averageLevel) {
        super(canSpawnPos, oneZoneMaxMobNum, boundaryUpX, 210, boundaryUpZ, boundaryDownX, 188, boundaryDownZ, 0, 60, level, mobPlayerRate, averageLevel);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        TorturedSoul boneImp = new TorturedSoul(AquamiraeEntities.TORTURED_SOUL.get(), this.level);

        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));

        // 设置颜色与名称
        Style style = CustomStyle.styleOfWorld;
        Compute.SetMobCustomName(boneImp, Component.literal(mobName).withStyle(style), xpLevel);

        // 需要验证
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(boneImp), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(boneImp, 220, 3200, 5500, 5500, 0.4, 5, 0.3, 3000, 25, 2000 * Math.pow(10, 4), 0.4);

        // 设置物品

        // 设置掉落
        List<ItemAndRate> list = getDropList();

        // 添加至掉落物列表
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(boneImp), list);
        return boneImp;
    }

    @Override
    public void tick() {

    }

    public static List<ItemAndRate> getDropList() {
        return new ArrayList<>() {{
            add(new ItemAndRate(C7Items.vdSoul.get(), 0.1));
            add(new ItemAndRate(ModItems.silverCoin.get(), 1));
            add(new ItemAndRate(ModItems.gemPiece.get(), 0.06));
        }};
    }

}
