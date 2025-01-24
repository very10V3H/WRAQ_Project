package fun.wraq.Items.Forging;

import fun.wraq.blocks.blocks.forge.ForgeRecipe;
import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.impl.display.BeforeRemoveMaterialOnForge;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.events.mob.instance.NoTeamInstanceModule;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.TeamPackets.ScreenSetS2CPacket;
import fun.wraq.process.func.guide.Guide;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.system.forge.ForgeEquipUtils;
import fun.wraq.process.system.forge.ForgeHammer;
import fun.wraq.process.system.profession.smith.SmithHammer;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WraqForge extends Item {

    public static String firstTimeForge = "firstTimeForge";
    public static Map<String, Integer> playerMSGSendDelayMap = new HashMap<>();
    public static Map<String, Integer> playerMSGSendDelayMap1 = new HashMap<>();
    private final Item forgedItem;

    public WraqForge(Properties p_41383_, Item item) {
        super(p_41383_);
        this.forgedItem = item;
    }

    public static void tryToForge(Player player, Item forgedItem) throws IOException {
        ItemStack forgeHammer = player.getMainHandItem();
        Item hammer = forgeHammer.getItem();
        if (!(hammer instanceof ForgeHammer || hammer instanceof SmithHammer)) {
            Compute.sendFormatMSG(player, Component.literal("锻造").withStyle(ChatFormatting.GRAY),
                    Component.literal("请手持 ").withStyle(ChatFormatting.WHITE).
                            append(Component.literal("锻造锤").withStyle(ChatFormatting.GOLD)).
                            append(Component.literal(" 进行锻造").withStyle(ChatFormatting.WHITE)));
            return;
        }

        List<ItemStack> materialList = ForgeRecipe.forgeDrawRecipe.get(forgedItem);
        Inventory inventory = player.getInventory();
        boolean containMaterial = true;
        for (ItemStack value : materialList) {
            if (!InventoryOperation.checkPlayerHasItem(inventory, value.getItem(), value.getCount()))
                containMaterial = false;
        }

        if (player.isCreative()) {
            containMaterial = true;
        }
        if (containMaterial) {
            ItemStack productItemStack = forgedItem.getDefaultInstance();
            CompoundTag data = null;
            int oldTier = 0;
            for (ItemStack stack : materialList) {
                Item item = stack.getItem();
                // 复制必要的Tag
                if (Utils.weaponList.contains(item)) {
                    for (int i = 0; i < inventory.getContainerSize(); i++) {
                        ItemStack itemStack = inventory.getItem(i);
                        if (itemStack.is(item)) {
                            if ((Utils.mainHandTag.containsKey(item) && Utils.mainHandTag.containsKey(forgedItem))
                                    || (Utils.armorTag.containsKey(item) && Utils.armorTag.containsKey(forgedItem))
                                    || (Utils.offHandTag.containsKey(item) && Utils.offHandTag.containsKey(forgedItem))) {
                                data = inventory.getItem(i).getOrCreateTagElement(Utils.MOD_ID).copy();
                            }
                            if (Utils.mainHandTag.containsKey(item) || Utils.armorTag.containsKey(item)) {
                                oldTier = ForgeEquipUtils.getForgeQualityOnEquip(itemStack);
                            }
                            break;
                        }
                    }
                }
                // 在移除前作一些操作，例如耗材的品质判断
                if (forgedItem instanceof BeforeRemoveMaterialOnForge beforeRemoveMaterialOnForge) {
                    ItemStack removingStack = Items.AIR.getDefaultInstance();
                    for (int i = 0; i < inventory.getContainerSize(); i++) {
                        if (inventory.getItem(i).is(stack.getItem())) {
                            removingStack = inventory.getItem(i);
                        }
                    }
                    beforeRemoveMaterialOnForge.beforeRemoveMaterialOnForge(productItemStack, removingStack);
                }
                // 移除物品
                if (!player.isCreative()) {
                    InventoryOperation.removeItem(inventory, stack.getItem(), stack.getCount());
                }
            }

            Compute.sendFormatMSG(player, Component.literal("锻造").withStyle(ChatFormatting.GRAY),
                    Component.literal("锻造成功！").withStyle(ChatFormatting.GOLD));
            MySound.soundToPlayer(player, SoundEvents.ANVIL_USE);

            if (data != null) {
                productItemStack.getOrCreateTagElement(Utils.MOD_ID).merge(data);
            }
            Compute.forgingHoverName(productItemStack);

            // 锻造品质
            if (Utils.mainHandTag.containsKey(forgedItem) || Utils.armorTag.containsKey(forgedItem)) {
                int tier = 0;
                if (hammer instanceof ForgeHammer forgeHammer1) {
                    tier = ForgeEquipUtils.getOneTimeForgeTier(forgeHammer1.getTier());
                    Compute.playerItemUseWithRecord(player);
                } else {
                    SmithHammer smithHammer = (SmithHammer) hammer;
                    tier = smithHammer.getTier();
                }

                if (tier < oldTier) {
                    tier = oldTier;
                    sendFormatMSG(player, Te.s("新的锻造品质低于装备原有锻造品质，已继承原有锻造品质!"));
                }
                ForgeEquipUtils.setForgeQualityOnEquip(productItemStack, tier);
                Compute.forgingHoverName(productItemStack);
                Compute.formatBroad(player.level(), Component.literal("锻造").withStyle(ChatFormatting.GRAY),
                        Component.literal("").withStyle(ChatFormatting.WHITE).append(player.getDisplayName()).
                                append(Component.literal(" 成功锻造了 ").withStyle(ChatFormatting.WHITE)).
                                append(ForgeEquipUtils.getDescription(tier)).append(Component.literal(" 的 ")
                                        .withStyle(ChatFormatting.WHITE)).append(productItemStack.getDisplayName()));
            } else {
                Compute.formatBroad(player.level(), Component.literal("锻造").withStyle(ChatFormatting.GRAY),
                        Component.literal("").withStyle(ChatFormatting.WHITE).append(player.getDisplayName()).
                                append(Component.literal(" 成功锻造了 ").withStyle(ChatFormatting.WHITE)).
                                append(productItemStack.getDisplayName()));
            }

            // 提示信息与音效
            ModNetworking.sendToClient(new ScreenSetS2CPacket(0), (ServerPlayer) player);
            List<ServerPlayer> playerList = player.getServer().getPlayerList().getPlayers();
            ClientboundSetTitleTextPacket clientboundSetTitleTextPacket = new ClientboundSetTitleTextPacket(productItemStack.getDisplayName());
            ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket = new ClientboundSetSubtitleTextPacket(Component.literal("已被" + player.getName().getString() + "成功锻造！").withStyle(ChatFormatting.AQUA));
            playerList.forEach(serverPlayer -> {
                serverPlayer.connection.send(clientboundSetTitleTextPacket);
                serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
            });

            // 前置条件判定
            Item item = productItemStack.getItem();
            Set<Item> iceKnightEquips = Set.of(ModItems.IceSword.get(), ModItems.IceBow.get(), ModItems.IceSceptre.get());
            Set<Item> moonEquips = Set.of(ModItems.MoonSword.get(), ModItems.MoonBow.get(), ModItems.MoonSceptre.get());
            Set<Item> castleEquips = Set.of(ModItems.CastleSword.get(), ModItems.CastleBow.get(), ModItems.CastleSceptre.get());
            if (iceKnightEquips.contains(item)) {
                NoTeamInstanceModule.putPlayerAllowReward(player, NoTeamInstanceModule.AllowRewardKey.sakuraBoss, true);
                NoTeamInstanceModule.putPlayerAllowReward(player, NoTeamInstanceModule.AllowRewardKey.devil, true);
            }
            if (moonEquips.contains(item)) {
                NoTeamInstanceModule.putPlayerAllowReward(player, NoTeamInstanceModule.AllowRewardKey.blackCastle, true);
            }
            if (castleEquips.contains(item)) {
                NoTeamInstanceModule.putPlayerAllowReward(player, NoTeamInstanceModule.AllowRewardKey.moontainBoss, true);
            }

            // 引导触发
            Set<Item> forestEquips = Set.of(ModItems.ForestArmorHelmet.get(), ModItems.ForestArmorChest.get(),
                    ModItems.ForestArmorLeggings.get(), ModItems.ForestArmorBoots.get(),
                    ModItems.ForestSword0.get(), ModItems.ForestBow0.get());
            if (forestEquips.contains(item)) {
                Guide.trigV2(player, Guide.StageV2.FOREST_EQUIP);
            }
            Set<Item> lakeEquips = Set.of(ModItems.LakeArmorHelmet.get(), ModItems.LakeArmorChest.get(),
                    ModItems.LakeArmorLeggings.get(), ModItems.LakeArmorBoots.get(),
                    ModItems.LakeSword0.get(), ModItems.lakeBow0.get(), ModItems.lakeSceptre0.get());
            if (lakeEquips.contains(item)) {
                Guide.trigV2(player, Guide.StageV2.LAKE_EQUIP);
            }
            Set<Item> mineEquips = Set.of(ModItems.MineArmorHelmet.get(), ModItems.MineArmorChest.get(),
                    ModItems.MineArmorLeggings.get(), ModItems.MineArmorBoots.get(),
                    ModItems.MineSword0.get(), ModItems.MineBow0.get());
            if (mineEquips.contains(item)) {
                Guide.trigV2(player, Guide.StageV2.MINE_EQUIP);
            }
            Set<Item> volcanoEquips = Set.of(ModItems.VolcanoArmorHelmet.get(), ModItems.VolcanoArmorChest.get(),
                    ModItems.VolcanoArmorLeggings.get(), ModItems.VolcanoArmorBoots.get(),
                    ModItems.VolcanoSword0.get(), ModItems.VolcanoBow0.get());
            if (volcanoEquips.contains(item)) {
                Guide.trigV2(player, Guide.StageV2.VOLCANO_EQUIP);
            }
            Set<Item> enhanceEquips = Set.of(ModItems.SKY_ARMOR_HELMET.get(),
                    ModItems.SKY_ARMOR_CHEST.get(), ModItems.SKY_ARMOR_LEGGINGS.get(), ModItems.SKY_ARMOR_BOOTS.get(),
                    ModItems.SkyBow.get());
            if (enhanceEquips.contains(item)) {
                Guide.trigV2(player, Guide.StageV2.ENHANCE_EQUIP);
            }

            InventoryOperation.itemStackGive(player, productItemStack);
            Guide.trigV2(player, Guide.StageV2.FIRST_FORGE);
            if (!StringUtils.FlagInTag.getPlayerFlag(player, firstTimeForge)) {
                StringUtils.FlagInTag.setPlayerString(player, firstTimeForge, true);
                playerMSGSendDelayMap.put(player.getName().getString(), Tick.get() + 100);
            }
        } else {
            Compute.sendFormatMSG(player, Component.literal("锻造").withStyle(ChatFormatting.GRAY), Component.literal("背包里似乎没有足够的物品用于锻造。"));
            for (ItemStack itemStack : materialList) {
                if (InventoryOperation.itemStackCount(inventory, itemStack.getItem()) < itemStack.getCount()) {
                    Compute.sendFormatMSG(player, Component.literal("锻造").withStyle(ChatFormatting.GRAY), Component.literal("缺少:").withStyle(ChatFormatting.WHITE).append(itemStack.getItem().getDefaultInstance().getDisplayName()).append(Component.literal("*" + (itemStack.getCount() - InventoryOperation.itemStackCount(inventory, itemStack.getItem())))));
                }
            }
            Compute.sendFormatMSG(player, Component.literal("锻造预览").withStyle(ChatFormatting.GRAY), Component.literal("锻造预览：").withStyle(ChatFormatting.WHITE).append(forgedItem.getDefaultInstance().getDisplayName()));
        }
    }

    public static void tick(TickEvent.PlayerTickEvent event) throws IOException {
        ServerPlayer serverPlayer = (ServerPlayer) event.player;
        String name = serverPlayer.getName().getString();
        int tick = Tick.get();
        if (playerMSGSendDelayMap.containsKey(name)) {
            if (playerMSGSendDelayMap.get(name) < tick) {
                playerMSGSendDelayMap.remove(name);
                Compute.sendFormatMSG(serverPlayer, Component.literal("引导-灌注").withStyle(ChatFormatting.AQUA), Component.literal("恭喜你完成了第一次打造！可能你已经注意到了，在一些武器的描述下方有").withStyle(ChatFormatting.WHITE).append(Component.literal("[可灌注/增幅]").withStyle(CustomStyle.styleOfPurpleIron)).append(Component.literal("的字样。").withStyle(ChatFormatting.WHITE)));
                playerMSGSendDelayMap1.put(name, tick + 40);
                MySound.soundToPlayer(serverPlayer, SoundEvents.EXPERIENCE_ORB_PICKUP);
            }
        }

        if (playerMSGSendDelayMap1.containsKey(name)) {
            if (playerMSGSendDelayMap1.get(name) < tick) {
                playerMSGSendDelayMap1.remove(name);
                ItemStack itemStack = new ItemStack(ModItems.PlainRune.get(), 2);
                Compute.sendFormatMSG(serverPlayer, Component.literal("引导-灌注").withStyle(ChatFormatting.AQUA), Component.literal("现在，拿着给予你的").withStyle(ChatFormatting.WHITE).append(itemStack.getDisplayName()).append(Component.literal("找到灌注台(在村庄锻造区域均有分布)，尝试给平原系列武器进行灌注升级吧！").withStyle(ChatFormatting.WHITE)));
                InventoryOperation.itemStackGive(serverPlayer, itemStack);
                MySound.soundToPlayer(serverPlayer, SoundEvents.EXPERIENCE_ORB_PICKUP);
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        components.add(Component.literal("-右键尝试锻造").withStyle(ChatFormatting.AQUA));
        components.add(Component.literal("  ").withStyle(ChatFormatting.AQUA).append(forgedItem.getDefaultInstance().getDisplayName()));
        components.add(Component.literal("-材料需求:").withStyle(ChatFormatting.AQUA));
        List<ItemStack> materialList = ForgeRecipe.forgeDrawRecipe.get(forgedItem);
        if (materialList != null) {
            for (int i = 0; i < materialList.size(); i++) {
                components.add(requirementDescription((i + 1), materialList.get(i).getDisplayName(), materialList.get(i).getCount()));
            }
        }
        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }

    public static Component requirementDescription(int index, Component materialType, int num) {
        return Component.literal(" " + index + ".").withStyle(ChatFormatting.GRAY).
                append(materialType).
                append(Component.literal("*" + num).withStyle(ChatFormatting.WHITE));
    }

    public static void sendFormatMSG(Player player, Component content) {
        Compute.sendFormatMSG(player, Te.s("锻造", CustomStyle.styleOfStone), content);
    }
}
