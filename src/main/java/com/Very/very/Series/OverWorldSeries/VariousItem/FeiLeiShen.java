package com.Very.very.Series.OverWorldSeries.VariousItem;

import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class FeiLeiShen extends Item {

    public FeiLeiShen(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if(!level.isClientSide)
        {
            HitResult result =  player.pick(10,0,true);
            if(Utils.FeiLeiShenMap.get(player) == null)
            {
                Queue <Vec3> Fei = new LinkedList<Vec3>();
                Fei.add(result.getLocation());
                Utils.FeiLeiShenMap.put(player,Fei);
            }
            else
            {
                Queue <Vec3> Fei = Utils.FeiLeiShenMap.get(player);
                Iterator<Vec3> iterator = Fei.iterator();
                Vec3 PickPos = result.getLocation();
                Vec3 PlayerPos = player.getEyePosition();
                Vec3 PickVec = PickPos.subtract(PlayerPos);
                Vec3 PickVecUnit = PickVec.normalize();
                boolean WhetherRegister = true;
                while(iterator.hasNext())
                {
                    Vec3 FeiPos = iterator.next();
                    Vec3 FeiVec = FeiPos.subtract(PlayerPos);
                    Vec3 FeiVecUnit = FeiVec.normalize();
                    double CosTheTa = (FeiVecUnit.dot(PickVecUnit))/(PickVecUnit.length() * FeiVecUnit.length());
                    if(CosTheTa > 0.99)
                    {
                        player.moveTo(FeiPos);
                        Fei.remove(FeiPos);
                        WhetherRegister = false;
                    }
                }
                if(WhetherRegister)
                {
                    while(Fei.size() == 3) Fei.remove();
                    Fei.add(PickPos);
                }
            }
        }
        return super.use(level, player, interactionHand);
    }
}

