package com.Very.very.Hcommand;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.StringUtils;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.Moditems;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.Arrays;

public class VillagerSummonCommand implements Command<CommandSourceStack> {
    public static VillagerSummonCommand instance = new VillagerSummonCommand();


    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        String type = StringArgumentType.getString(context,"type");
        Level level = player.level();
        if (player.isCreative()) {
            MerchantOffer[] OfferList = new MerchantOffer[20];
            Component VillagerName = null;
            double[] VillagerPos = {0,0,0};
            VillagerType villagerType = VillagerType.PLAINS;
            VillagerProfession villagerProfession = VillagerProfession.ARMORER;
            int Length = 0;
            if (type.equals(StringUtils.VillagerType.MainMission.Trade1)) {
                MerchantOffer[] merchantOfferList = {
                        new MerchantOffer(new ItemStack(Moditems.Main0.get(),1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(Moditems.Main0_1.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(Moditems.Main0_1.get(),1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(Moditems.Main0_2.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(Moditems.Main0_2.get(),1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(Moditems.Main0_3.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(Moditems.Main0_3.get(),1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(Moditems.Main0_4.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(Moditems.Main0_4.get(),1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(Moditems.Main0_5.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(Moditems.Main0_5.get(),1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(Moditems.Main1_0.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                };
                System.arraycopy(merchantOfferList,0,OfferList,0,merchantOfferList.length);
                VillagerName = Component.literal("主线任务道具兑换").withStyle(ChatFormatting.AQUA);
                villagerProfession = VillagerProfession.CLERIC;
                villagerType = VillagerType.PLAINS;
                Length = merchantOfferList.length;
                VillagerPos[0] = 354;
                VillagerPos[1] = 63;
                VillagerPos[2] = 893;
            }
            if (type.equals(StringUtils.VillagerType.MainMission.Trade2)) {
                MerchantOffer[] merchantOfferList = {
                        new MerchantOffer(new ItemStack(Moditems.Main1_0.get(),1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(Moditems.Main1_1.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(Moditems.Main1_1.get(),1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(Moditems.Main1_2.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(Moditems.Main1_2.get(),1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(Moditems.Main1_3.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                };
                System.arraycopy(merchantOfferList,0,OfferList,0,merchantOfferList.length);
                VillagerName = Component.literal("主线任务道具兑换").withStyle(ChatFormatting.AQUA);
                villagerProfession = VillagerProfession.CLERIC;
                villagerType = VillagerType.PLAINS;
                Length = merchantOfferList.length;
                VillagerPos[0] = 342;
                VillagerPos[1] = 64;
                VillagerPos[2] = 903;
            }
            if (type.equals(StringUtils.VillagerType.MainMission.Trade3)) {
                MerchantOffer[] merchantOfferList = {
                        new MerchantOffer(new ItemStack(Moditems.Main1_3.get(),1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(Moditems.Main1_4.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(Moditems.Main1_4.get(),1),
                                new ItemStack(Items.EMERALD,3),
                                new ItemStack(Moditems.main1reward.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(Items.MUTTON,16),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(Items.EMERALD,1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(Items.BEEF,16),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(Items.EMERALD,1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(Items.PORKCHOP,16),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(Items.EMERALD,1),
                                0,Integer.MAX_VALUE,0,0,0),
                };
                System.arraycopy(merchantOfferList,0,OfferList,0,merchantOfferList.length);
                VillagerName = Component.literal("主线任务道具兑换").withStyle(ChatFormatting.AQUA);
                villagerProfession = VillagerProfession.CLERIC;
                villagerType = VillagerType.PLAINS;
                Length = merchantOfferList.length;
                VillagerPos[0] = 267;
                VillagerPos[1] = 130;
                VillagerPos[2] = 1265;
            }
            if (type.equals(StringUtils.VillagerType.Spawn.Main1Gems)) {
                MerchantOffer[] merchantOfferList = {
                        new MerchantOffer(new ItemStack(Moditems.RandomGemPiece.get(),3),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(Moditems.GemBag.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                };
                System.arraycopy(merchantOfferList,0,OfferList,0,merchantOfferList.length);
                VillagerName = Component.literal("四元饰品袋兑换").withStyle(ChatFormatting.LIGHT_PURPLE);
                villagerProfession = VillagerProfession.CARTOGRAPHER;
                villagerType = VillagerType.PLAINS;
                Length = merchantOfferList.length;
                VillagerPos[0] = 359;
                VillagerPos[1] = 64;
                VillagerPos[2] = 902;
            }

            Villager villager = new Villager(EntityType.VILLAGER,level);
            VillagerData villagerData = new VillagerData(villagerType, villagerProfession,5);
            villager.setVillagerData(villagerData);
            MerchantOffers merchantOffers = new MerchantOffers();
            merchantOffers.addAll(Arrays.asList(OfferList).subList(0, Length));
            villager.setOffers(merchantOffers);
            villager.moveTo(VillagerPos[0] + 0.5,VillagerPos[1] + 1,VillagerPos[2] + 0.5);
            villager.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0);
            villager.setCustomName(VillagerName);
            level.addFreshEntity(villager);
            player.sendSystemMessage(Component.literal("已生成" + type));
        }
        else {
            Compute.FormatMSGSend(player,Component.literal("维瑞阿契").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA),
                    Component.literal("此命令仅管理员可用。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
        }
        return 0;
    }
}
