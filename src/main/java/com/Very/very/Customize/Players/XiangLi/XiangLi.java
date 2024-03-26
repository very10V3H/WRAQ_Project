package com.Very.very.Customize.Players.XiangLi;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.ModItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.ForgeMod;

public class XiangLi {
    public static void Xiangli(Player player) {
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.XiangLiSmoke.get())) {
            player.addEffect(new MobEffectInstance(MobEffects.JUMP,20,2,false,false,false));
        }
    }

    public static void XiangliCuriosSkillHealthSteal(Player player, double damage) {
        if (Utils.Xiangli != null && Utils.Xiangli.equals(player) && Utils.XiangliCurios) {
            Compute.PlayerHeal(player,damage * 0.05);
        }
    }
}

