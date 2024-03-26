package com.Very.very.Customize.Players.Crush;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.ModItems;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class Crush1 {
    public static double ZeusCurios(Player player, Mob mob) {
        int TickCount = player.getServer().getTickCount();
        Level level = player.level();
        if (Utils.Crush1 != null && Utils.Crush1.equals(player) && TickCount > Utils.Crush1SkillTickCount) {
            Utils.Crush1SkillTickCount = TickCount + 60;
            for (int i = 0 ; i < 3 ; i ++) {
                LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT,level);
                lightningBolt.moveTo(mob.position());
                lightningBolt.setVisualOnly(true);
                lightningBolt.setCause((ServerPlayer) player);
                level.addFreshEntity(lightningBolt);
            }
            Compute.CoolDownTimeSend(player, ModItems.ZeusCurios.get().getDefaultInstance(),60);
            return Compute.PlayerAttributes.PlayerAttackDamage(player) * 3;
        }
        return 0;
    }
}
