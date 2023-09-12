package com.Very.very.ValueAndTools;

import com.Very.very.Items.Drugs.KeyBoradInput;
import com.Very.very.NetWorking.ModNetworking;
import com.Very.very.NetWorking.Packets.DrugsC2SPacket;
import com.Very.very.NetWorking.Packets.Limit.LimitC2SPacket;
import com.Very.very.NetWorking.Packets.USE.Use1C2SPacket;
import com.Very.very.NetWorking.Packets.USE.Use2C2SPacket;
import com.Very.very.NetWorking.Packets.USE.Use3C2SPacket;
import com.Very.very.NetWorking.Packets.USE.Use4C2SPacket;
import com.Very.very.ValueAndTools.Utils.ClientUtils;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class KeyInput {
    @Mod.EventBusSubscriber(modid = Utils.MOD_ID , value = Dist.CLIENT)
    public static class ClientForgeEvents
    {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event)
        {
            if(ClientUtils.PacketsLimit <= 0)
            {
                ModNetworking.sendToServer(new LimitC2SPacket());
            }
            else
            {
                if(KeyBoradInput.DRUGS.consumeClick())
                {
                    if (ClientUtils.IsHandlingPower) {
                        ClientUtils.IsAdjustingPower = !ClientUtils.IsAdjustingPower;
                    }
                    else {
                        ClientUtils.PacketsLimit --;
                        ModNetworking.sendToServer(new DrugsC2SPacket());
                    }
                }
                if(KeyBoradInput.USE1.consumeClick())
                {
                    if (ClientUtils.IsAdjustingPower) {
                        if (ClientUtils.PowerQueue.size() >= 4) {
                            ClientUtils.PowerQueue.poll();
                            ClientUtils.PowerQueue.add(1);
                        }
                        else ClientUtils.PowerQueue.add(1);
                    }
                    else {
                        ClientUtils.PacketsLimit --;
                        ModNetworking.sendToServer(new Use1C2SPacket());
                    }
                }
                if(KeyBoradInput.USE2.consumeClick())
                {
                    if (ClientUtils.IsAdjustingPower) {
                        if (ClientUtils.PowerQueue.size() >= 4) {
                            ClientUtils.PowerQueue.poll();
                            ClientUtils.PowerQueue.add(2);
                        }
                        else ClientUtils.PowerQueue.add(2);
                    }
                    else {
                        ClientUtils.PacketsLimit --;
                        ModNetworking.sendToServer(new Use2C2SPacket());
                    }
                }
                if(KeyBoradInput.USE3.consumeClick())
                {
                    if (ClientUtils.IsAdjustingPower) {
                        if (ClientUtils.PowerQueue.size() >= 4) {
                            ClientUtils.PowerQueue.poll();
                            ClientUtils.PowerQueue.add(3);
                        }
                        else ClientUtils.PowerQueue.add(3);
                    }
                    else {
                        ClientUtils.PacketsLimit --;
                        ModNetworking.sendToServer(new Use3C2SPacket());
                    }
                }
                if(KeyBoradInput.USE4.consumeClick())
                {
                    if (ClientUtils.IsAdjustingPower) {
                        if (ClientUtils.PowerQueue.size() >= 4) {
                            ClientUtils.PowerQueue.poll();
                            ClientUtils.PowerQueue.add(4);
                        }
                        else ClientUtils.PowerQueue.add(4);
                    }
                    else {
                        ClientUtils.PacketsLimit --;
                        ModNetworking.sendToServer(new Use4C2SPacket());
                    }
                }
            }
        }
    }
    @Mod.EventBusSubscriber(modid = Utils.MOD_ID , value = Dist.CLIENT , bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents
    {
        @SubscribeEvent
        public static void KeyBinding(RegisterKeyMappingsEvent event)
        {
            event.register(KeyBoradInput.DRUGS);
            event.register(KeyBoradInput.USE1);
            event.register(KeyBoradInput.USE2);
            event.register(KeyBoradInput.USE3);
            event.register(KeyBoradInput.USE4);
        }
    }


}
