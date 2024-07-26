package com.very.wraq.commands.changeable;

import com.very.wraq.render.gui.villagerTrade.MyVillagerData;
import com.very.wraq.render.gui.villagerTrade.TradeList;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.StringUtils;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.Map;

public class VillagerSummonCommand implements Command<CommandSourceStack> {
    public static VillagerSummonCommand instance = new VillagerSummonCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        String type = StringArgumentType.getString(context, "type").toLowerCase();
        Level level = player.level();
        if (TradeList.tradeContent.isEmpty()) TradeList.setTradeContent();
        if (player.isCreative()) {
            Map<String, VillagerType> villagerTypeMap = new HashMap<>() {{
                put(StringUtils.NewVillagerName.Forest.toLowerCase(), VillagerType.JUNGLE);
                put(StringUtils.NewVillagerName.Lake.toLowerCase(), VillagerType.SWAMP);
                put(StringUtils.NewVillagerName.Volcano.toLowerCase(), VillagerType.DESERT);
                put(StringUtils.NewVillagerName.Mine.toLowerCase(), VillagerType.SAVANNA);
                put(StringUtils.NewVillagerName.Snow.toLowerCase(), VillagerType.SNOW);
                put(StringUtils.NewVillagerName.Sky.toLowerCase(), VillagerType.SNOW);
                put(StringUtils.NewVillagerName.Wither.toLowerCase(), VillagerType.SAVANNA);
                put(StringUtils.NewVillagerName.Piglin.toLowerCase(), VillagerType.SAVANNA);
                put(StringUtils.NewVillagerName.Skeleton.toLowerCase(), VillagerType.SAVANNA);
                put(StringUtils.NewVillagerName.Magma.toLowerCase(), VillagerType.SAVANNA);
                put(StringUtils.NewVillagerName.Ice.toLowerCase(), VillagerType.SNOW);
                put(StringUtils.NewVillagerName.NetherRune.toLowerCase(), VillagerType.SAVANNA);
                put(StringUtils.NewVillagerName.NetherPower.toLowerCase(), VillagerType.SAVANNA);
                put(StringUtils.NewVillagerName.NetherArmor.toLowerCase(), VillagerType.SAVANNA);
                put(StringUtils.NewVillagerName.NetherGem.toLowerCase(), VillagerType.SAVANNA);
                put(StringUtils.NewVillagerName.NetherBow.toLowerCase(), VillagerType.SAVANNA);
                put(StringUtils.NewVillagerName.NetherSwordModel.toLowerCase(), VillagerType.SAVANNA);
                put(StringUtils.NewVillagerName.NetherWeapon.toLowerCase(), VillagerType.SAVANNA);
                put(StringUtils.NewVillagerName.Ruby.toLowerCase(), VillagerType.SAVANNA);
                put(StringUtils.NewVillagerName.Nature.toLowerCase(), VillagerType.SNOW);
                put(StringUtils.NewVillagerName.GoldSmith.toLowerCase(), VillagerType.SNOW);
                put(StringUtils.NewVillagerName.Blood.toLowerCase(), VillagerType.SAVANNA);
                put(StringUtils.NewVillagerName.Taboo.toLowerCase(), VillagerType.SAVANNA);
                put(StringUtils.NewVillagerName.Earth.toLowerCase(), VillagerType.SWAMP);
                put(StringUtils.NewVillagerName.ManaBook.toLowerCase(), VillagerType.SWAMP);
                put(StringUtils.NewVillagerName.Slime.toLowerCase(), VillagerType.JUNGLE);
                put(StringUtils.NewVillagerName.Castle.toLowerCase(), VillagerType.SAVANNA);
                put(StringUtils.NewVillagerName.CastleCommon.toLowerCase(), VillagerType.SAVANNA);
                put(StringUtils.NewVillagerName.LakeRune.toLowerCase(), VillagerType.SWAMP);
                put(StringUtils.NewVillagerName.QingMing.toLowerCase(), VillagerType.JUNGLE);
                put(StringUtils.NewVillagerName.LifeElement.toLowerCase(), VillagerType.JUNGLE);
                put(StringUtils.NewVillagerName.WindElement.toLowerCase(), VillagerType.PLAINS);
                put(StringUtils.NewVillagerName.StoneElement.toLowerCase(), VillagerType.SAVANNA);
                put(StringUtils.NewVillagerName.FireElement.toLowerCase(), VillagerType.SAVANNA);
                put(StringUtils.NewVillagerName.LightningElement.toLowerCase(), VillagerType.TAIGA);
                put(StringUtils.NewVillagerName.WaterElement.toLowerCase(), VillagerType.SWAMP);
                put(StringUtils.NewVillagerName.IceElement.toLowerCase(), VillagerType.SNOW);
                put(StringUtils.NewVillagerName.Food.toLowerCase(), VillagerType.PLAINS);
                put(StringUtils.NewVillagerName.RoseGoldStore.toLowerCase(), VillagerType.PLAINS);
                put(StringUtils.NewVillagerName.Pearl.toLowerCase(), VillagerType.SNOW);
            }};
            Map<String, VillagerProfession> villagerProfessionMap = new HashMap<>() {{
                put(StringUtils.NewVillagerName.Ice.toLowerCase(), VillagerProfession.LEATHERWORKER);
                put(StringUtils.NewVillagerName.PurpleIron.toLowerCase(), VillagerProfession.MASON);
                put(StringUtils.NewVillagerName.Mine.toLowerCase(), VillagerProfession.MASON);
                put(StringUtils.NewVillagerName.WorldSoul.toLowerCase(), VillagerProfession.LIBRARIAN);
                put(StringUtils.NewVillagerName.Forge.toLowerCase(), VillagerProfession.WEAPONSMITH);
                put(StringUtils.NewVillagerName.Currency.toLowerCase(), VillagerProfession.CARTOGRAPHER);
                put(StringUtils.NewVillagerName.NetherWeapon.toLowerCase(), VillagerProfession.WEAPONSMITH);
                put(StringUtils.NewVillagerName.NetherBow.toLowerCase(), VillagerProfession.WEAPONSMITH);
                put(StringUtils.NewVillagerName.SoulToGoldCoin.toLowerCase(), VillagerProfession.CARTOGRAPHER);
                put(StringUtils.NewVillagerName.BossCore.toLowerCase(), VillagerProfession.CARTOGRAPHER);
                put(StringUtils.NewVillagerName.Main1Gems.toLowerCase(), VillagerProfession.CARTOGRAPHER);
                put(StringUtils.NewVillagerName.Cord.toLowerCase(), VillagerProfession.CARTOGRAPHER);
                put(StringUtils.NewVillagerName.T1Equip.toLowerCase(), VillagerProfession.CARTOGRAPHER);
                put(StringUtils.NewVillagerName.PlainRune.toLowerCase(), VillagerProfession.LIBRARIAN);
                put(StringUtils.NewVillagerName.ForestRune.toLowerCase(), VillagerProfession.LIBRARIAN);
                put(StringUtils.NewVillagerName.VolcanoRune.toLowerCase(), VillagerProfession.LIBRARIAN);
                put(StringUtils.NewVillagerName.ManaRune.toLowerCase(), VillagerProfession.LIBRARIAN);
                put(StringUtils.NewVillagerName.SnowRune.toLowerCase(), VillagerProfession.LIBRARIAN);
                put(StringUtils.NewVillagerName.NetherRune.toLowerCase(), VillagerProfession.LIBRARIAN);
                put(StringUtils.NewVillagerName.Spider.toLowerCase(), VillagerProfession.LIBRARIAN);
                put(StringUtils.NewVillagerName.Brewing.toLowerCase(), VillagerProfession.LIBRARIAN);
                put(StringUtils.NewVillagerName.Nature.toLowerCase(), VillagerProfession.FARMER);
                put(StringUtils.NewVillagerName.Pickaxe.toLowerCase(), VillagerProfession.TOOLSMITH);
                put(StringUtils.NewVillagerName.Axe.toLowerCase(), VillagerProfession.TOOLSMITH);
                put(StringUtils.NewVillagerName.Field.toLowerCase(), VillagerProfession.FARMER);
                put(StringUtils.NewVillagerName.Spring.toLowerCase(), VillagerProfession.CARTOGRAPHER);
                put(StringUtils.NewVillagerName.QingMing.toLowerCase(), VillagerProfession.CARTOGRAPHER);
                put(StringUtils.NewVillagerName.GoldSmith.toLowerCase(), VillagerProfession.CARTOGRAPHER);
                put(StringUtils.NewVillagerName.Blood.toLowerCase(), VillagerProfession.LEATHERWORKER);
                put(StringUtils.NewVillagerName.Taboo.toLowerCase(), VillagerProfession.LEATHERWORKER);
                put(StringUtils.NewVillagerName.Earth.toLowerCase(), VillagerProfession.LEATHERWORKER);
                put(StringUtils.NewVillagerName.ManaBook.toLowerCase(), VillagerProfession.LIBRARIAN);
                put(StringUtils.NewVillagerName.Slime.toLowerCase(), VillagerProfession.LEATHERWORKER);
                put(StringUtils.NewVillagerName.Parkour.toLowerCase(), VillagerProfession.CARTOGRAPHER);
                put(StringUtils.NewVillagerName.Castle.toLowerCase(), VillagerProfession.CARTOGRAPHER);
                put(StringUtils.NewVillagerName.CastleCommon.toLowerCase(), VillagerProfession.WEAPONSMITH);
                put(StringUtils.NewVillagerName.SkyGemStore.toLowerCase(), VillagerProfession.CARTOGRAPHER);
                put(StringUtils.NewVillagerName.LakeRune.toLowerCase(), VillagerProfession.LIBRARIAN);
                put(StringUtils.NewVillagerName.LifeElement.toLowerCase(), VillagerProfession.LIBRARIAN);
                put(StringUtils.NewVillagerName.WindElement.toLowerCase(), VillagerProfession.LIBRARIAN);
                put(StringUtils.NewVillagerName.StoneElement.toLowerCase(), VillagerProfession.LIBRARIAN);
                put(StringUtils.NewVillagerName.FireElement.toLowerCase(), VillagerProfession.LIBRARIAN);
                put(StringUtils.NewVillagerName.LightningElement.toLowerCase(), VillagerProfession.LIBRARIAN);
                put(StringUtils.NewVillagerName.WaterElement.toLowerCase(), VillagerProfession.LIBRARIAN);
                put(StringUtils.NewVillagerName.IceElement.toLowerCase(), VillagerProfession.LIBRARIAN);
                put(StringUtils.NewVillagerName.Food.toLowerCase(), VillagerProfession.BUTCHER);
                put(StringUtils.NewVillagerName.RoseGoldStore.toLowerCase(), VillagerProfession.CARTOGRAPHER);
                put(StringUtils.NewVillagerName.LabourDayStore.toLowerCase(), VillagerProfession.FARMER);
                put(StringUtils.NewVillagerName.BackPack.toLowerCase(), VillagerProfession.LEATHERWORKER);
                put(StringUtils.NewVillagerName.Pearl.toLowerCase(), VillagerProfession.LIBRARIAN);
            }};

            double[] VillagerPos = {0, 0, 0};
            Vec3 Pos = player.pick(5, 0, true).getLocation();
            VillagerPos[0] = Pos.x;
            VillagerPos[1] = Pos.y;
            VillagerPos[2] = Pos.z;
            VillagerType villagerType = villagerTypeMap.getOrDefault(type, VillagerType.PLAINS);
            VillagerProfession villagerProfession = villagerProfessionMap.getOrDefault(type, VillagerProfession.WEAPONSMITH);
            MutableComponent mutableComponent = StringUtils.VillagerNameMap.getOrDefault(type, Component.literal(""));
            if (MyVillagerData.villagerNameMap.containsKey(type)) {
                villagerType = MyVillagerData.villagerTypeMap.get(type);
                villagerProfession = MyVillagerData.villagerProfessionMap.get(type);
                mutableComponent = MyVillagerData.villagerNameMap.get(type);
            } else {
                if (!StringUtils.VillagerNameMap.containsKey(type)) {
                    Compute.sendFormatMSG(player, Component.literal("生成").withStyle(ChatFormatting.LIGHT_PURPLE),
                            Component.literal("请检查拼写是否正确。").withStyle(ChatFormatting.WHITE));
                    return 0;
                }
            }
            Villager villager = new Villager(EntityType.VILLAGER, level);
            VillagerData villagerData = new VillagerData(villagerType, villagerProfession, 5);
            villager.setVillagerData(villagerData);
            villager.moveTo(VillagerPos[0], VillagerPos[1], VillagerPos[2]);
            villager.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0);
            villager.setCustomName(mutableComponent);
            villager.setCustomNameVisible(true);
            villager.setInvulnerable(true);
            level.addFreshEntity(villager);
            player.sendSystemMessage(Component.literal("已生成 ").withStyle(ChatFormatting.WHITE).
                    append(mutableComponent));
        } else {
            Compute.sendFormatMSG(player, Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA),
                    Component.literal("此命令仅管理员可用。").withStyle(ChatFormatting.WHITE));
        }
/*            if (type.equals(StringUtils.VillagerType.MainMission.Trade1)) {
                MerchantOffer[] merchantOfferList = {
                        new MerchantOffer(new ItemStack(ModItems.Main0.get(),1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.Main0_1.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.Main0_1.get(),1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.Main0_2.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.Main0_2.get(),1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.Main0_3.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.Main0_3.get(),1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.Main0_4.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.Main0_4.get(),1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.Main0_5.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.Main0_5.get(),1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.Main1_0.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                };
                System.arraycopy(merchantOfferList,0,OfferList,0,merchantOfferList.length);
                VillagerName = Component.literal("主线任务道具兑换").withStyle(ChatFormatting.AQUA);
                villagerProfession = VillagerProfession.CLERIC;
                villagerType = VillagerType.PLAINS;
                Length = merchantOfferList.length;
            }
            if (type.equals(StringUtils.VillagerType.MainMission.Trade2)) {
                MerchantOffer[] merchantOfferList = {
                        new MerchantOffer(new ItemStack(ModItems.Main1_0.get(),1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.Main1_1.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.Main1_1.get(),1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.Main1_2.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.Main1_2.get(),1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.Main1_3.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                };
                System.arraycopy(merchantOfferList,0,OfferList,0,merchantOfferList.length);
                VillagerName = Component.literal("主线任务道具兑换").withStyle(ChatFormatting.AQUA);
                villagerProfession = VillagerProfession.CLERIC;
                villagerType = VillagerType.PLAINS;
                Length = merchantOfferList.length;
            }
            if (type.equals(StringUtils.VillagerType.MainMission.Trade3)) {
                MerchantOffer[] merchantOfferList = {
                        new MerchantOffer(new ItemStack(ModItems.Main1_3.get(),1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.Main1_4.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.Main1_4.get(),1),
                                new ItemStack(Items.EMERALD,3),
                                new ItemStack(ModItems.main1reward.get(),1),
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
            }
            if (type.equals(StringUtils.VillagerType.Spawn.Main1Gems)) {
                MerchantOffer[] merchantOfferList = {
                        new MerchantOffer(new ItemStack(ModItems.RandomGemPiece.get(),3),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.GemBag.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                };
                System.arraycopy(merchantOfferList,0,OfferList,0,merchantOfferList.length);
                VillagerName = Component.literal("四元饰品袋兑换").withStyle(ChatFormatting.LIGHT_PURPLE);
                villagerProfession = VillagerProfession.CARTOGRAPHER;
                villagerType = VillagerType.PLAINS;
                Length = merchantOfferList.length;
            }
            if (type.equals(StringUtils.VillagerType.Spawn.EpicRune)) {
                MerchantOffer[] merchantOfferList = {
                        new MerchantOffer(new ItemStack(ModItems.Main1Crystal.get(),9),
                                new ItemStack(ModItems.PlainBracelet.get(),1),
                                new ItemStack(ModItems.PLAIN_CORD.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.Main1Crystal.get(),9),
                                new ItemStack(ModItems.ForestBracelet.get(),1),
                                new ItemStack(ModItems.FOREST_CORD.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.Main1Crystal.get(),9),
                                new ItemStack(ModItems.LakeBracelet.get(),1),
                                new ItemStack(ModItems.LAKE_CORD.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.Main1Crystal.get(),9),
                                new ItemStack(ModItems.VolcanoBracelet.get(),1),
                                new ItemStack(ModItems.VOLCANO_CORD.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.PLAIN_CORD.get(),1),
                                new ItemStack(ModItems.FOREST_CORD.get(),1),
                                new ItemStack(ModItems.PLAINFOREST_CORD.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.LAKE_CORD.get(),1),
                                new ItemStack(ModItems.VOLCANO_CORD.get(),1),
                                new ItemStack(ModItems.LAKEVOLCANO_CORD.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.PLAINFOREST_CORD.get(),1),
                                new ItemStack(ModItems.LAKEVOLCANO_CORD.get(),1),
                                new ItemStack(ModItems.FINAL_CORD.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                };
                System.arraycopy(merchantOfferList,0,OfferList,0,merchantOfferList.length);
                VillagerName = Component.literal("传说之证").withStyle(ChatFormatting.YELLOW);
                villagerProfession = VillagerProfession.CARTOGRAPHER;
                villagerType = VillagerType.PLAINS;
                Length = merchantOfferList.length;
            }
            if (type.equals(StringUtils.VillagerType.Spawn.T1Equip)) {
                MerchantOffer[] merchantOfferList = {
                        new MerchantOffer(new ItemStack(ModItems.SakuraDemonSword.get(),1),
                                new ItemStack(ModItems.SakuraDemonSword.get(),1),
                                new ItemStack(ModItems.SakuraDemonSword.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.WheatArmorChest.get(),1),
                                new ItemStack(ModItems.WheatArmorChest.get(),1),
                                new ItemStack(ModItems.WheatArmorChest.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.MinePants.get(),1),
                                new ItemStack(ModItems.MinePants.get(),1),
                                new ItemStack(ModItems.MinePants.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.SakuraArmorHelmet.get(),1),
                                new ItemStack(ModItems.SakuraArmorHelmet.get(),1),
                                new ItemStack(ModItems.SakuraArmorHelmet.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.VolcanoBossSword.get(),1),
                                new ItemStack(ModItems.VolcanoBossSword.get(),1),
                                new ItemStack(ModItems.VolcanoBossSword.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.ForestBossSword.get(),1),
                                new ItemStack(ModItems.ForestBossSword.get(),1),
                                new ItemStack(ModItems.ForestBossSword.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.MagmaSceptre3.get(),1),
                                new ItemStack(ModItems.MagmaSceptre3.get(),1),
                                new ItemStack(ModItems.MagmaSceptre3.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.WitherBow3.get(),1),
                                new ItemStack(ModItems.WitherBow3.get(),1),
                                new ItemStack(ModItems.WitherBow3.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.WitherSword3.get(),1),
                                new ItemStack(ModItems.WitherSword3.get(),1),
                                new ItemStack(ModItems.WitherSword3.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.SkyBossBow.get(),1),
                                new ItemStack(ModItems.SkyBossBow.get(),1),
                                new ItemStack(ModItems.SkyBossBow.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.LakeBossSword.get(),1),
                                new ItemStack(ModItems.LakeBossSword.get(),1),
                                new ItemStack(ModItems.LakeBossSword.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                };
                System.arraycopy(merchantOfferList,0,OfferList,0,merchantOfferList.length);
                VillagerName = Component.literal("版本T1装备展示").withStyle(ChatFormatting.AQUA);
                villagerProfession = VillagerProfession.CARTOGRAPHER;
                villagerType = VillagerType.PLAINS;
                Length = merchantOfferList.length;
            }
            if (type.equals(StringUtils.VillagerType.Spawn.BossCore)) {
                MerchantOffer[] merchantOfferList = {
                        new MerchantOffer(new ItemStack(ModItems.ForestBossCore.get(),12),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.ForestBossPocket.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.VolcanoBossCore.get(),12),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.VolcanoBossPocket.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.LakeBossCore.get(),12),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.LakeBossPocket.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.SkyBossCore.get(),12),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.SkyBossPocket.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.SnowBossCore.get(),12),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.SnowBossPocket.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                };
                System.arraycopy(merchantOfferList,0,OfferList,0,merchantOfferList.length);
                VillagerName = Component.literal("Boss核心兑换").withStyle(ChatFormatting.AQUA);
                villagerProfession = VillagerProfession.CARTOGRAPHER;
                villagerType = VillagerType.PLAINS;
                Length = merchantOfferList.length;
            }
            if (type.equals(StringUtils.VillagerType.SakuraIsland.Trade1)) {
                MerchantOffer[] merchantOfferList = {
                        new MerchantOffer(new ItemStack(ModItems.SakuraPetal.get(),12),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.SakuraPocket.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.Wheat.get(),12),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.WheatPocket.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                };
                System.arraycopy(merchantOfferList,0,OfferList,0,merchantOfferList.length);
                VillagerName = Component.literal("樱岛商人").withStyle(CustomStyle.styleOfSakura);
                villagerProfession = VillagerProfession.CARTOGRAPHER;
                villagerType = VillagerType.PLAINS;
                Length = merchantOfferList.length;
            }
            if (type.equals(StringUtils.VillagerType.Series.Plain1)) {
                MerchantOffer[] merchantOfferList = {
                        new MerchantOffer(new ItemStack(ModItems.PlainSoul.get(),64),
                                new ItemStack(ModItems.GoldCoin.get(),1),
                                new ItemStack(ModItems.PlainRune.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.PlainRune.get(),1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.PlainSword0.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.PlainRune.get(),1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.PlainBow0.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.PlainRune.get(),1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.PlainSceptre0.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.PlainRune.get(),1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.PlainPower.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                };
                System.arraycopy(merchantOfferList,0,OfferList,0,merchantOfferList.length);
                VillagerName = Component.literal("平原系列武器打造").withStyle(ChatFormatting.GREEN);
                villagerProfession = VillagerProfession.WEAPONSMITH;
                villagerType = VillagerType.PLAINS;
                Length = merchantOfferList.length;
            }
            if (type.equals(StringUtils.VillagerType.Series.Plain2)) {
                MerchantOffer[] merchantOfferList = {
                        new MerchantOffer(new ItemStack(ModItems.PlainSoul.get(),64),
                                new ItemStack(ModItems.GoldCoin.get(),1),
                                new ItemStack(ModItems.PlainRune.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.PlainRune.get(),1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.PlainArmorHelmet.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.PlainRune.get(),1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.PlainArmorChest.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.PlainRune.get(),1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.PlainArmorLeggings.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.PlainRune.get(),1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.PlainArmorBoots.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.PlainRune.get(),5),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.PlainBracelet.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.PlainRune.get(),5),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.ManaNote_Plain.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.PlainRune.get(),10),
                                new ItemStack(ModItems.GemPiece.get(),64),
                                new ItemStack(ModItems.PlainGem.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                };
                System.arraycopy(merchantOfferList,0,OfferList,0,merchantOfferList.length);
                VillagerName = Component.literal("平原系列装备打造").withStyle(ChatFormatting.GREEN);
                villagerProfession = VillagerProfession.ARMORER;
                villagerType = VillagerType.PLAINS;
                Length = merchantOfferList.length;
            }
            if (type.equals(StringUtils.VillagerType.Series.Forest1)) {
                Item Soul = ModItems.ForestSoul.get();
                Item Rune = ModItems.ForestRune.get();
                Item Sword0 = ModItems.ForestSword0.get();
                Item Sword1 = ModItems.ForestSword1.get();
                Item Sword2 = ModItems.ForestSword2.get();
                Item Sword3 = ModItems.ForestSword3.get();
                MerchantOffer[] merchantOfferList = {
                        new MerchantOffer(new ItemStack(Soul,64),
                                new ItemStack(ModItems.GoldCoin.get(),3),
                                new ItemStack(Rune,1),
                                0,Integer.MAX_VALUE,0,0,0),

                        new MerchantOffer(new ItemStack(Rune,1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(Sword0,1),
                                0,Integer.MAX_VALUE,0,0,0),

                        new MerchantOffer(new ItemStack(Rune,1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.ForestBow0.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),

                        new MerchantOffer(new ItemStack(ModItems.ForestRune.get(),1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.ForestPower.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),

                };
                System.arraycopy(merchantOfferList,0,OfferList,0,merchantOfferList.length);
                VillagerName = Component.literal("森林系列武器打造").withStyle(ChatFormatting.DARK_GREEN);
                villagerProfession = VillagerProfession.WEAPONSMITH;
                villagerType = VillagerType.JUNGLE;
                Length = merchantOfferList.length;
            }
            if (type.equals(StringUtils.VillagerType.Series.Forest2)) {
                Item Soul = ModItems.ForestSoul.get();
                Item Rune = ModItems.ForestRune.get();
                MerchantOffer[] merchantOfferList = {
                        new MerchantOffer(new ItemStack(Soul,64),
                                new ItemStack(ModItems.GoldCoin.get(),1),
                                new ItemStack(Rune,1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(Rune,1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.ForestArmorHelmet.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(Rune,1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.ForestArmorChest.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(Rune,1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.PlainArmorLeggings.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(Rune,1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.PlainArmorBoots.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(Rune,5),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.ForestBracelet.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.ManaNote_Plain.get(),1),
                                new ItemStack(Rune,5),
                                new ItemStack(ModItems.ManaNote_Forest.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(Rune,10),
                                new ItemStack(ModItems.GemPiece.get(),64),
                                new ItemStack(ModItems.ForestGem.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                };
                System.arraycopy(merchantOfferList,0,OfferList,0,merchantOfferList.length);
                VillagerName = Component.literal("森林系列装备打造").withStyle(ChatFormatting.DARK_GREEN);
                villagerProfession = VillagerProfession.ARMORER;
                villagerType = VillagerType.JUNGLE;
                Length = merchantOfferList.length;
            }
            if (type.equals(StringUtils.VillagerType.Series.Lake)) {
                Item Soul = ModItems.LakeSoul.get();
                Item Rune = ModItems.LakeRune.get();
                Item Sword0 = ModItems.LakeSword0.get();
                Item Sword1 = ModItems.LakeSword1.get();
                Item Sword2 = ModItems.LakeSword2.get();
                Item Sword3 = ModItems.LakeSword3.get();
                MerchantOffer[] merchantOfferList = {
                        new MerchantOffer(new ItemStack(Soul,64),
                                new ItemStack(ModItems.GoldCoin.get(),5),
                                new ItemStack(Rune,1),

                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(Rune,1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(Sword0,1),
                                0,Integer.MAX_VALUE,0,0,0),

                        new MerchantOffer(new ItemStack(ModItems.LakeRune.get(),1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.LakePower.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),

                        new MerchantOffer(new ItemStack(Rune,1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.LakeArmorHelmet.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(Rune,1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.LakeArmorChest.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(Rune,1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.LakeArmorLeggings.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(Rune,1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.LakeArmorBoots.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(Rune,5),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.LakeBracelet.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.ManaNote_Forest.get(),1),
                                new ItemStack(Rune,5),
                                new ItemStack(ModItems.ManaNote_Lake.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(Rune,10),
                                new ItemStack(ModItems.GemPiece.get(),64),
                                new ItemStack(ModItems.LakeGem.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                };
                System.arraycopy(merchantOfferList,0,OfferList,0,merchantOfferList.length);
                VillagerName = Component.literal("湖泊系列打造").withStyle(ChatFormatting.BLUE);
                villagerProfession = VillagerProfession.ARMORER;
                villagerType = VillagerType.SWAMP;
                Length = merchantOfferList.length;
            }
            if (type.equals(StringUtils.VillagerType.Series.Volcano)) {
                Item Soul = ModItems.VolcanoSoul.get();
                Item Rune = ModItems.VolcanoRune.get();
                Item Sword0 = ModItems.VolcanoSword0.get();
                Item Sword1 = ModItems.VolcanoSword1.get();
                Item Sword2 = ModItems.VolcanoSword2.get();
                Item Sword3 = ModItems.VolcanoSword3.get();
                MerchantOffer[] merchantOfferList = {
                        new MerchantOffer(new ItemStack(Soul,64),
                                new ItemStack(ModItems.GoldCoin.get(),5),
                                new ItemStack(Rune,1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(Rune,1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(Sword0,1),
                                0,Integer.MAX_VALUE,0,0,0),

                        new MerchantOffer(new ItemStack(ModItems.VolcanoRune.get(),1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.VolcanoPower.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),

                        new MerchantOffer(new ItemStack(Rune,1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.VolcanoBow0.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),

                        new MerchantOffer(new ItemStack(Rune,1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.VolcanoArmorHelmet.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(Rune,1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.VolcanoArmorChest.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(Rune,1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.VolcanoArmorLeggings.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(Rune,1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.VolcanoArmorBoots.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(Rune,5),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.VolcanoBracelet.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.ManaNote_Lake.get(),1),
                                new ItemStack(Rune,5),
                                new ItemStack(ModItems.ManaNote_Volcano.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(Rune,10),
                                new ItemStack(ModItems.GemPiece.get(),64),
                                new ItemStack(ModItems.VolcanoGem.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                };
                System.arraycopy(merchantOfferList,0,OfferList,0,merchantOfferList.length);
                VillagerName = Component.literal("火山系列打造").withStyle(ChatFormatting.YELLOW);
                villagerProfession = VillagerProfession.ARMORER;
                villagerType = VillagerType.DESERT;
                Length = merchantOfferList.length;
            }
            if (type.equals(StringUtils.VillagerType.Series.Mine)) {
                Item Soul = ModItems.MineSoul.get();
                Item Soul1 = ModItems.MineSoul1.get();
                Item Rune = ModItems.MineRune.get();
                Item Sword0 = ModItems.MineSword0.get();
                Item Sword1 = ModItems.MineSword1.get();
                Item Sword2 = ModItems.MineSword2.get();
                Item Sword3 = ModItems.MineSword3.get();
                MerchantOffer[] merchantOfferList = {
                        new MerchantOffer(new ItemStack(Soul,64),
                                new ItemStack(ModItems.GoldCoin.get(),5),
                                new ItemStack(Rune,1),

                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(Soul1,16),
                                new ItemStack(ModItems.GoldCoin.get(),5),
                                new ItemStack(Rune,1),

                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(Rune,1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(Sword0,1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(Rune,5),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.MineBracelet.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(Rune,1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.MineArmorHelmet.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(Rune,1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.MineArmorChest.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(Rune,1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.MineArmorLeggings.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(Rune,1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.MineArmorBoots.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                };
                System.arraycopy(merchantOfferList,0,OfferList,0,merchantOfferList.length);
                VillagerName = Component.literal("矿洞系列打造").withStyle(CustomStyle.styleOfMine);
                villagerProfession = VillagerProfession.ARMORER;
                villagerType = VillagerType.PLAINS;
                Length = merchantOfferList.length;
            }
            if (type.equals(StringUtils.VillagerType.Series.Snow)) {
                Item Soul = ModItems.SnowSoul.get();
                Item Rune = ModItems.SnowRune.get();
                Item Sword0 = ModItems.SnowSword0.get();
                Item Sword1 = ModItems.SnowSword1.get();
                Item Sword2 = ModItems.SnowSword2.get();
                Item Sword3 = ModItems.SnowSword3.get();
                MerchantOffer[] merchantOfferList = {
                        new MerchantOffer(new ItemStack(Soul,64),
                                new ItemStack(ModItems.GoldCoin.get(),5),
                                new ItemStack(Rune,1),
                                0,Integer.MAX_VALUE,0,0,0),

                        new MerchantOffer(new ItemStack(ModItems.SnowRune.get(),1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.SnowPower.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),

                        new MerchantOffer(new ItemStack(Rune,1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.SnowArmorHelmet.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(Rune,1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.SnowArmorChest.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(Rune,1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.SnowArmorLeggings.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(Rune,1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.SnowArmorBoots.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(Rune,5),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.SnowBracelet.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.ManaNote_Volcano.get(),1),
                                new ItemStack(Rune,5),
                                new ItemStack(ModItems.ManaNote_Snow.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(Rune,10),
                                new ItemStack(ModItems.GemPiece.get(),64),
                                new ItemStack(ModItems.SnowGem.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                };
                System.arraycopy(merchantOfferList,0,OfferList,0,merchantOfferList.length);
                VillagerName = Component.literal("玉山系列打造").withStyle(CustomStyle.styleOfSnow);
                villagerProfession = VillagerProfession.ARMORER;
                villagerType = VillagerType.SNOW;
                Length = merchantOfferList.length;
            }
            if (type.equals(StringUtils.VillagerType.Series.Sky)) {
                Item Soul = ModItems.SkySoul.get();
                Item Rune = ModItems.SkyRune.get();
                Item Sword0 = ModItems.SkyBow.get();
                MerchantOffer[] merchantOfferList = {
                        new MerchantOffer(new ItemStack(Soul,64),
                                new ItemStack(ModItems.GoldCoin.get(),5),
                                new ItemStack(Rune,1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(Rune,10),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(Sword0,1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(Rune,10),
                                new ItemStack(ModItems.GemPiece.get(),64),
                                new ItemStack(ModItems.SkyGem.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(Rune,1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.ForgingStone1.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(Rune,5),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.SkyBracelet.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.SkyHelmetForgeDraw.get(),1),
                                new ItemStack(ModItems.SkyArmorHelmet.get(),1),
                                new ItemStack(ModItems.SkyArmorHelmet.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.SkyChestForgeDraw.get(),1),
                                new ItemStack(ModItems.SkyArmorChest.get(),1),
                                new ItemStack(ModItems.SkyArmorChest.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.SkyLeggingsForgeDraw.get(),1),
                                new ItemStack(ModItems.SkyArmorLeggings.get(),1),
                                new ItemStack(ModItems.SkyArmorLeggings.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.SkyBootsForgeDraw.get(),1),
                                new ItemStack(ModItems.SkyArmorBoots.get(),1),
                                new ItemStack(ModItems.SkyArmorBoots.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                };
                System.arraycopy(merchantOfferList,0,OfferList,0,merchantOfferList.length);
                VillagerName = Component.literal("天空系列打造").withStyle(CustomStyle.styleOfSky);
                villagerProfession = VillagerProfession.ARMORER;
                villagerType = VillagerType.SAVANNA;
                Length = merchantOfferList.length;
            }
            if (type.equals(StringUtils.VillagerType.Series.Evoker1)) {
                Item Rune = ModItems.EvokerRune.get();
                Item Sword0 = ModItems.EvokerBook0.get();
                Item Sword1 = ModItems.EvokerBook1.get();
                Item Sword2 = ModItems.EvokerBook2.get();
                Item Sword3 = ModItems.EvokerBook3.get();
                MerchantOffer[] merchantOfferList = {
                        new MerchantOffer(new ItemStack(ModItems.EvokerSoul.get(),4),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.ManaBucket.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.GoldCoin.get(),5),
                                new ItemStack(ModItems.GemPiece.get(),4),
                                new ItemStack(ModItems.ManaBalance_Empty.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.ManaBucket.get(),16),
                                new ItemStack(ModItems.ManaBalance_Empty.get(),2),
                                new ItemStack(ModItems.EvokerRune.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.EvokerRune.get(),5),
                                new ItemStack(ModItems.ManaBalance_Empty.get(),2),
                                new ItemStack(ModItems.EvokerSword.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.EvokerRune.get(),4),
                                new ItemStack(ModItems.ManaBalance_Empty.get(),4),
                                new ItemStack(ModItems.EvokerGem.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),

                        new MerchantOffer(new ItemStack(ModItems.ManaNote_Snow.get(),1),
                                new ItemStack(Rune,1),
                                new ItemStack(Sword0,1),
                                0,Integer.MAX_VALUE,0,0,0),

                        new MerchantOffer(new ItemStack(Sword0,1),
                                new ItemStack(Rune,2),
                                new ItemStack(Sword1,1),
                                0,Integer.MAX_VALUE,0,0,0),

                        new MerchantOffer(new ItemStack(Sword1,1),
                                new ItemStack(Rune,3),
                                new ItemStack(Sword2,1),
                                0,Integer.MAX_VALUE,0,0,0),

                        new MerchantOffer(new ItemStack(Sword2,1),
                                new ItemStack(Rune,5),
                                new ItemStack(Sword3,1),
                                0,Integer.MAX_VALUE,0,0,0),
                };
                System.arraycopy(merchantOfferList,0,OfferList,0,merchantOfferList.length);
                VillagerName = Component.literal("魔源系列打造").withStyle(CustomStyle.styleOfMana);
                villagerProfession = VillagerProfession.ARMORER;
                villagerType = VillagerType.TAIGA;
                Length = merchantOfferList.length;
            }
            if (type.equals(StringUtils.VillagerType.Nether.Wither)) {
                Item Rune = ModItems.WitherRune.get();
                Item Sword0 = ModItems.WitherSword0.get();
                Item Sword1 = ModItems.WitherSword1.get();
                Item Sword2 = ModItems.WitherSword2.get();
                Item Sword3 = ModItems.WitherSword3.get();
                ItemStack itemStack = Sword2.getDefaultInstance();
                itemStack.hideTooltipPart(ItemStack.TooltipPart.MODIFIERS);
                MerchantOffer[] merchantOfferList = {
                        new MerchantOffer(new ItemStack(ModItems.witherBone.get(),64),
                                new ItemStack(ModItems.NetherSoul.get(),1),
                                new ItemStack(Rune,1),
                                0,Integer.MAX_VALUE,0,0,0),

                        new MerchantOffer(new ItemStack(Rune,1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(Sword0,1),
                                0,Integer.MAX_VALUE,0,0,0),

                };
                System.arraycopy(merchantOfferList,0,OfferList,0,merchantOfferList.length);
                VillagerName = Component.literal("凋零系列打造").withStyle(CustomStyle.styleOfNether);
                villagerProfession = VillagerProfession.WEAPONSMITH;
                villagerType = VillagerType.SAVANNA;
                Length = merchantOfferList.length;
            }
            if (type.equals(StringUtils.VillagerType.Nether.Piglin)) {
                Item Rune = ModItems.PiglinRune.get();
                Item Sword0 = ModItems.PiglinHelmet0.get();
                Item Sword1 = ModItems.PiglinHelmet1.get();
                Item Sword2 = ModItems.PiglinHelmet2.get();
                Item Sword3 = ModItems.PiglinHelmet3.get();
                MerchantOffer[] merchantOfferList = {
                        new MerchantOffer(new ItemStack(ModItems.PigLinSoul.get(),64),
                                new ItemStack(ModItems.NetherSoul.get(),1),
                                new ItemStack(Rune,1),
                                0,Integer.MAX_VALUE,0,0,0),

                        new MerchantOffer(new ItemStack(Rune,1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(Sword0,1),
                                0,Integer.MAX_VALUE,0,0,0),

                };
                System.arraycopy(merchantOfferList,0,OfferList,0,merchantOfferList.length);
                VillagerName = Component.literal("猪灵系列打造").withStyle(CustomStyle.styleOfNether);
                villagerProfession = VillagerProfession.WEAPONSMITH;
                villagerType = VillagerType.SAVANNA;
                Length = merchantOfferList.length;
            }
            if (type.equals(StringUtils.VillagerType.Nether.Skeleton)) {
                Item Rune = ModItems.NetherSkeletonRune.get();
                Item Sword0 = ModItems.WitherBow0.get();
                Item Sword1 = ModItems.WitherBow1.get();
                Item Sword2 = ModItems.WitherBow2.get();
                Item Sword3 = ModItems.WitherBow3.get();
                MerchantOffer[] merchantOfferList = {
                        new MerchantOffer(new ItemStack(ModItems.netherbonemeal.get(),64),
                                new ItemStack(ModItems.NetherSoul.get(),1),
                                new ItemStack(Rune,1),
                                0,Integer.MAX_VALUE,0,0,0),

                        new MerchantOffer(new ItemStack(Rune,1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(Sword0,1),
                                0,Integer.MAX_VALUE,0,0,0),

                };
                System.arraycopy(merchantOfferList,0,OfferList,0,merchantOfferList.length);
                VillagerName = Component.literal("下界骷髅系列打造").withStyle(CustomStyle.styleOfNether);
                villagerProfession = VillagerProfession.WEAPONSMITH;
                villagerType = VillagerType.SAVANNA;
                Length = merchantOfferList.length;
            }
            if (type.equals(StringUtils.VillagerType.Nether.Magma)) {
                Item Rune = ModItems.MagmaRune.get();
                Item Sword0 = ModItems.MagmaSceptre0.get();
                Item Sword1 = ModItems.MagmaSceptre1.get();
                Item Sword2 = ModItems.MagmaSceptre2.get();
                Item Sword3 = ModItems.MagmaSceptre3.get();
                MerchantOffer[] merchantOfferList = {
                        new MerchantOffer(new ItemStack(ModItems.NetherMagmaPower.get(),64),
                                new ItemStack(ModItems.ManaBalance_Empty.get(),3),
                                new ItemStack(Rune,1),
                                0,Integer.MAX_VALUE,0,0,0),

                        new MerchantOffer(new ItemStack(Rune,1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(Sword0,1),
                                0,Integer.MAX_VALUE,0,0,0),

                };
                System.arraycopy(merchantOfferList,0,OfferList,0,merchantOfferList.length);
                VillagerName = Component.literal("下界骷髅系列打造").withStyle(CustomStyle.styleOfNether);
                villagerProfession = VillagerProfession.WEAPONSMITH;
                villagerType = VillagerType.SAVANNA;
                Length = merchantOfferList.length;
            }
            if (type.equals(StringUtils.VillagerType.Spawn.DailyFG)) {
                MerchantOffer[] merchantOfferList = {
                        new MerchantOffer(new ItemStack(ModItems.IronLove.get(),6),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.SkyBootsForgeDraw.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.IronLove.get(),6),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.NetherBootsForgeDraw.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.IronLove.get(),6),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.IslandBootsForgeDraw.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.IronLove.get(),10),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.BlackForestSwordForgeDraw.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.IronLove.get(),10),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.KazeBootsForgeDraw.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.IronLove.get(),10),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.SeaSwordForgeDraw.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.IronLove.get(),16),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.KazeSwordForgeDraw.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.IronLove.get(),16),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.SakuraSwordForgeDraw.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                };
                System.arraycopy(merchantOfferList,0,OfferList,0,merchantOfferList.length);
                VillagerName = Component.literal("每日任务奖励兑换（锻造图）").withStyle(ChatFormatting.GOLD);
                villagerProfession = VillagerProfession.CARTOGRAPHER;
                villagerType = VillagerType.PLAINS;
                Length = merchantOfferList.length;
            }
            if (type.equals(StringUtils.VillagerType.Spawn.DailyMisc)) {
                MerchantOffer[] merchantOfferList = {
                        new MerchantOffer(new ItemStack(ModItems.IronLove.get(),1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.PlainRune.get(),9),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.IronLove.get(),1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.ForestRune.get(),6),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.IronLove.get(),1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.LakeRune.get(),3),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.IronLove.get(),1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.VolcanoRune.get(),3),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.IronLove.get(),1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.MineRune.get(),3),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.IronLove.get(),1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.SnowRune.get(),3),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.IronLove.get(),1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.EvokerRune.get(),2),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.IronLove.get(),1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.ruby.get(),64),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.IronLove.get(),1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.Spawn2.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                };
                System.arraycopy(merchantOfferList,0,OfferList,0,merchantOfferList.length);
                VillagerName = Component.literal("每日任务奖励兑换（杂项）").withStyle(ChatFormatting.GOLD);
                villagerProfession = VillagerProfession.CARTOGRAPHER;
                villagerType = VillagerType.PLAINS;
                Length = merchantOfferList.length;
            }
            if (type.equals(StringUtils.VillagerType.Crest.First)) {
                MerchantOffer[] merchantOfferList = {
                        new MerchantOffer(new ItemStack(ModItems.PlainCrest0.get(),16),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.PlainCrest1.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.PlainCrest1.get(),16),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.PlainCrest2.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.PlainCrest2.get(),16),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.PlainCrest3.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),

                        new MerchantOffer(new ItemStack(ModItems.ForestCrest0.get(),16),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.ForestCrest1.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.ForestCrest1.get(),16),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.ForestCrest2.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.ForestCrest2.get(),16),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.ForestCrest3.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),

                        new MerchantOffer(new ItemStack(ModItems.LakeCrest0.get(),16),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.LakeCrest1.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.LakeCrest1.get(),16),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.LakeCrest2.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.LakeCrest2.get(),16),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.LakeCrest3.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),

                        new MerchantOffer(new ItemStack(ModItems.VolcanoCrest0.get(),16),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.VolcanoCrest1.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.VolcanoCrest1.get(),16),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.VolcanoCrest2.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.VolcanoCrest2.get(),16),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.VolcanoCrest3.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),

                };
                System.arraycopy(merchantOfferList,0,OfferList,0,merchantOfferList.length);
                VillagerName = Component.literal("纹章制作师").withStyle(ChatFormatting.GOLD);
                villagerProfession = VillagerProfession.LIBRARIAN;
                villagerType = VillagerType.PLAINS;
                Length = merchantOfferList.length;
            }
            if (type.equals(StringUtils.VillagerType.Crest.Second)) {
                MerchantOffer[] merchantOfferList = {
                        new MerchantOffer(new ItemStack(ModItems.MineCrest0.get(),16),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.MineCrest1.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.MineCrest1.get(),16),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.MineCrest2.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.MineCrest2.get(),16),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.MineCrest3.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),

                        new MerchantOffer(new ItemStack(ModItems.SnowCrest0.get(),16),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.SnowCrest1.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.SnowCrest1.get(),16),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.SnowCrest2.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.SnowCrest2.get(),16),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.SnowCrest3.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),

                        new MerchantOffer(new ItemStack(ModItems.SkyCrest0.get(),16),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.SkyCrest1.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.SkyCrest1.get(),16),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.SkyCrest2.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.SkyCrest2.get(),16),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.SkyCrest3.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),

                        new MerchantOffer(new ItemStack(ModItems.ManaCrest0.get(),16),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.ManaCrest1.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.ManaCrest1.get(),16),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.ManaCrest2.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.ManaCrest2.get(),16),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.ManaCrest3.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),

                };
                System.arraycopy(merchantOfferList,0,OfferList,0,merchantOfferList.length);
                VillagerName = Component.literal("纹章制作师").withStyle(ChatFormatting.GOLD);
                villagerProfession = VillagerProfession.CARTOGRAPHER;
                villagerType = VillagerType.PLAINS;
                Length = merchantOfferList.length;
            }
            if (type.equals(StringUtils.VillagerType.Series.PlainForge)) {
                MerchantOffer[] merchantOfferList = {
                        new MerchantOffer(new ItemStack(ModItems.MineSoul.get(),64),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.SpeIron.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),

                        new MerchantOffer(new ItemStack(ModItems.MineSoul1.get(),16),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.SpeIron.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),

                        new MerchantOffer(new ItemStack(ModItems.GoldCoin.get(),16),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.OpenSlot.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),

                        new MerchantOffer(new ItemStack(ModItems.SpeIron.get(),1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.ForgingStone0.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),

                        new MerchantOffer(new ItemStack(ModItems.MineSoul.get(),64),
                                new ItemStack(ModItems.GoldCoin.get(),4),
                                new ItemStack(ModItems.MineSoul1.get(),16),
                                0,Integer.MAX_VALUE,0,0,0),
                };
                System.arraycopy(merchantOfferList,0,OfferList,0,merchantOfferList.length);
                VillagerName = Component.literal("锻造大师").withStyle(ChatFormatting.GOLD);
                villagerProfession = VillagerProfession.ARMORER;
                villagerType = VillagerType.PLAINS;
                Length = merchantOfferList.length;
            }
            if (type.equals(StringUtils.VillagerType.Soul.Equipment)) {
                MerchantOffer[] merchantOfferList = {
                        new MerchantOffer(new ItemStack(ModItems.WorldSoulNote.get(),1),
                                new ItemStack(ModItems.WorldSoulNote.get(),1),
                                new ItemStack(ModItems.WorldSoulNote.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),

                        new MerchantOffer(new ItemStack(ModItems.WorldSoul3.get(),16),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.SoulSword.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),

                        new MerchantOffer(new ItemStack(ModItems.WorldSoul3.get(),16),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.SoulBow.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),

                        new MerchantOffer(new ItemStack(ModItems.WorldSoul3.get(),16),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.SoulSceptre.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),

                        new MerchantOffer(new ItemStack(ModItems.WorldSoul3.get(),4),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.SkillReset.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),

                        new MerchantOffer(new ItemStack(ModItems.WorldSoul2.get(),16),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.Dismantle.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),

                        new MerchantOffer(new ItemStack(ModItems.WorldSoul2.get(),16),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.TpToSakura.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),

                        new MerchantOffer(new ItemStack(ModItems.WorldSoul2.get(),16),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.TpToSky.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                };
                System.arraycopy(merchantOfferList,0,OfferList,0,merchantOfferList.length);
                VillagerName = Component.literal("本源学者").withStyle(CustomStyle.styleOfWorld);
                villagerProfession = VillagerProfession.WEAPONSMITH;
                villagerType = VillagerType.SNOW;
                Length = merchantOfferList.length;
            }
            if (type.equals(StringUtils.VillagerType.GoldCoinStore.KillPaper)) {
                ItemStack[] KillPaper = {
                        new ItemStack(ModItems.PlainZombieKillPaper.get(),5),
                        new ItemStack(ModItems.ForestSkeletonKillPaper.get(),5),
                        new ItemStack(ModItems.ForestZombieKillPaper.get(),5),
                        new ItemStack(ModItems.FieldZombieKillPaper.get(),5),
                        new ItemStack(ModItems.LakeDrownedKillPaper.get(),10),
                        new ItemStack(ModItems.VolcanoBlazeKillPaper.get(),10),
                        new ItemStack(ModItems.MineZombieKillPaper.get(),15),
                        new ItemStack(ModItems.MineSkeletonKillPaper.get(),15),
                        new ItemStack(ModItems.SnowStrayKillPaper.get(),20),
                        new ItemStack(ModItems.SkyVexKillPaper.get(),20),
                        new ItemStack(ModItems.EvokerKillPaper.get(),20),
                        new ItemStack(ModItems.EvokerMasterKillPaper.get(),80),
                        new ItemStack(ModItems.SeaGuardianKillPaper.get(),20),
                        new ItemStack(ModItems.LightingZombieKillPaper.get(),80),
                        new ItemStack(ModItems.HuskKillPaper.get(),20),
                        new ItemStack(ModItems.KazeZombieKillPaper.get(),30),
                        new ItemStack(ModItems.SpiderKillPaper.get(),30),
                        new ItemStack(ModItems.SilverFishKillPaper.get(),30),
                        new ItemStack(ModItems.WitherSkeletonKillPaper.get(),30),
                        new ItemStack(ModItems.PiglinKillPaper.get(),30),
                        new ItemStack(ModItems.NetherSkeletonKillPaper.get(),30),
                        new ItemStack(ModItems.NetherMagmaKillPaper.get(),30),
                        new ItemStack(ModItems.EnderManKillPaper.get(),30),
                        new ItemStack(ModItems.SakuraMobKillPaper.get(),128),
                        new ItemStack(ModItems.ScarecrowKillPaper.get(),128),
                };

                List<MerchantOffer> merchantOfferList = new ArrayList<>();

                for (ItemStack itemStack : KillPaper) {
                    if (itemStack.getCount() <= 64) {
                        merchantOfferList.add(new MerchantOffer(new ItemStack(ModItems.GoldCoin.get(), itemStack.getCount()),
                                new ItemStack(ModItems.GoldCoin.get(), 0),
                                new ItemStack(itemStack.getItem(), 1),
                                0, Integer.MAX_VALUE, 0, 0, 0));
                    }
                    else {
                        merchantOfferList.add(new MerchantOffer(new ItemStack(ModItems.GoldCoin.get(), 64),
                                new ItemStack(ModItems.GoldCoin.get(), itemStack.getCount() - 64),
                                new ItemStack(itemStack.getItem(), 1),
                                0, Integer.MAX_VALUE, 0, 0, 0));
                    }
                }

                merchantOfferList0 = merchantOfferList;
                VillagerName = Component.literal("黄金商人-征讨券兑换").withStyle(ChatFormatting.YELLOW);
                villagerProfession = VillagerProfession.CARTOGRAPHER;
                villagerType = VillagerType.PLAINS;
                Length = merchantOfferList.toArray().length;
            }
            if (type.equals(StringUtils.VillagerType.GoldCoinStore.PsBottle)) {
                MerchantOffer[] merchantOfferList = {
                        new MerchantOffer(new ItemStack(ModItems.GoldCoin.get(),16),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.Ps_Bottle0.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),

                        new MerchantOffer(new ItemStack(ModItems.GoldCoin.get(),32),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.Ps_Bottle1.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),

                        new MerchantOffer(new ItemStack(ModItems.GoldCoin.get(),48),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.Ps_Bottle2.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                };
                System.arraycopy(merchantOfferList,0,OfferList,0,merchantOfferList.length);
                VillagerName = Component.literal("黄金商人-体力药水兑换").withStyle(ChatFormatting.YELLOW);
                villagerProfession = VillagerProfession.CARTOGRAPHER;
                villagerType = VillagerType.PLAINS;
                Length = merchantOfferList.length;
            }
            if (type.equals(StringUtils.VillagerType.PurpleIron.PurpleIron)) {
                MerchantOffer[] merchantOfferList = {
                        new MerchantOffer(new ItemStack(ModItems.GoldCoin.get(),16),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.PurpleIron.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.PurpleIronPiece.get(),64),
                                new ItemStack(ModItems.MineSoul.get(),64),
                                new ItemStack(ModItems.PurpleIron.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.PurpleIron.get(),1),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.GoldCoin.get(),2),
                                0,Integer.MAX_VALUE,0,0,0),
                };
                System.arraycopy(merchantOfferList,0,OfferList,0,merchantOfferList.length);
                VillagerName = Component.literal("紫晶铁商人").withStyle(CustomStyle.styleOfPurpleIron);
                villagerProfession = VillagerProfession.CARTOGRAPHER;
                villagerType = VillagerType.PLAINS;
                Length = merchantOfferList.length;
            }
            if (type.equals(StringUtils.VillagerType.Series.Ice)) {
                MerchantOffer[] merchantOfferList = {
                        new MerchantOffer(new ItemStack(ModItems.LeatherSoul.get(),64),
                                new ItemStack(ModItems.GoldCoin.get(),10),
                                new ItemStack(ModItems.LeatherRune.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.LeatherRune.get(),5),
                                new ItemStack(ModItems.GoldCoin.get(),10),
                                new ItemStack(ModItems.LeatherArmorHelmet.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.LeatherRune.get(),8),
                                new ItemStack(ModItems.GoldCoin.get(),10),
                                new ItemStack(ModItems.LeatherArmorChest.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.LeatherRune.get(),7),
                                new ItemStack(ModItems.GoldCoin.get(),10),
                                new ItemStack(ModItems.LeatherArmorLeggings.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.LeatherRune.get(),4),
                                new ItemStack(ModItems.GoldCoin.get(),10),
                                new ItemStack(ModItems.LeatherArmorBoots.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),

                };
                System.arraycopy(merchantOfferList,0,OfferList,0,merchantOfferList.length);
                VillagerName = Component.literal("冰霜之地裁缝").withStyle(CustomStyle.styleOfIce);
                villagerProfession = VillagerProfession.CARTOGRAPHER;
                villagerType = VillagerType.TAIGA;
                Length = merchantOfferList.length;
            }
            if (type.equals(StringUtils.VillagerType.Bank.Currency)) {
                MerchantOffer[] merchantOfferList = {
                        new MerchantOffer(new ItemStack(ModItems.SilverCoin.get(),64),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.GoldCoin.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.GemPiece.get(),9),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.GoldCoin.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.RunePiece.get(),32),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.GoldCoin.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.MineSoul.get(),64),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.GoldCoin.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                        new MerchantOffer(new ItemStack(ModItems.GemPiece.get(),64),
                                new ItemStack(Items.AIR,1),
                                new ItemStack(ModItems.CompleteGem.get(),1),
                                0,Integer.MAX_VALUE,0,0,0),
                };
                System.arraycopy(merchantOfferList,0,OfferList,0,merchantOfferList.length);
                VillagerName = Component.literal("货币兑换").withStyle(ChatFormatting.GOLD);
                villagerProfession = VillagerProfession.CARTOGRAPHER;
                villagerType = VillagerType.PLAINS;
                Length = merchantOfferList.length;
            }*/
        return 0;
    }
}
