package fun.wraq.items.forge;

import com.mojang.logging.LogUtils;
import fun.wraq.blocks.blocks.forge.ForgeRecipe;
import fun.wraq.common.Compute;
import fun.wraq.common.fast.Name;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.impl.display.BeforeRemoveMaterialOnForge;
import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.events.mob.instance.NoTeamInstanceModule;
import fun.wraq.process.func.guide.Guide;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.system.forge.ForgeEquipUtils;
import fun.wraq.process.system.forge.ForgeHammer;
import fun.wraq.process.system.profession.smith.SmithHammer;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.divine.DivineIslandItems;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
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
import org.jetbrains.annotations.NotNull;
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
            Compute.sendFormatMSG(player, Te.s("锻造", ChatFormatting.GRAY),
                    Te.s("请手持 ", "锻造锤", ChatFormatting.GOLD, "  进行锻造."));
            return;
        }
        List<ItemStack> materialList;
        if (forgedItem instanceof ForgeItem forgeItem) {
            materialList = forgeItem.forgeRecipe();
        } else {
            materialList = ForgeRecipe.recipes.get(forgedItem);
        }
        Inventory inventory = player.getInventory();
        boolean containMaterial = true;
        for (ItemStack value : materialList) {
            if (!InventoryOperation.checkPlayerHasItem(inventory, value.getItem(), value.getCount())) {
                containMaterial = false;
            }
        }
        ItemStack sameStack = InventoryOperation.findFirstItem(player, forgedItem);
        if (sameStack != null
                && (Utils.mainHandTag.containsKey(forgedItem) || Utils.armorTag.containsKey(forgedItem))) {
            int oldTier = ForgeEquipUtils.getEquipForgeQuality(sameStack);
            Item needPiece = ForgeEquipUtils.getEquipPiece(oldTier);
            if (!InventoryOperation.checkPlayerHasItem(player, needPiece, 1)) {
                Compute.sendInfoToScreen(player,
                        Te.s("需要", needPiece, "来重新锻造."));
                return;
            }
            InventoryOperation.removeItem(player, needPiece, 1);
            int tier = 0;
            if (hammer instanceof ForgeHammer forgeHammer1) {
                tier = ForgeEquipUtils.getOneTimeForgeTier(forgeHammer1.getTier());
                Compute.playerItemUseWithRecord(player);
            } else {
                SmithHammer smithHammer = (SmithHammer) hammer;
                tier = ForgeEquipUtils.getOneTimeForgeTier(smithHammer.getTier());
            }
            Component oldDisplayName = sameStack.getDisplayName();
            ForgeEquipUtils.setForgeQualityOnEquip(sameStack, tier);
            Compute.formatBroad(Te.s("锻造", CustomStyle.styleOfStone),
                    Te.s(player, "将 ", ForgeEquipUtils.getDescription(oldTier), " ", oldDisplayName,
                            "重新锻造为 ", ForgeEquipUtils.getDescription(tier), " ", sameStack));
            Compute.clearPlayerScreen(player);
            MySound.soundToPlayer(player, SoundEvents.ANVIL_USE);
            return;
        }
        if (player.isCreative()) {
            containMaterial = true;
        }
        if (containMaterial) {
            ItemStack productItemStack = forgedItem.getDefaultInstance();
            CompoundTag data = null;
            int oldTier = 0;
            OldEquipInfo result
                    = getOldEquipInfo(player, forgedItem, materialList, inventory, data, oldTier, productItemStack);
            Compute.sendFormatMSG(player, Component.literal("锻造").withStyle(ChatFormatting.GRAY),
                    Component.literal("锻造成功！").withStyle(ChatFormatting.GOLD));
            MySound.soundToPlayer(player, SoundEvents.ANVIL_USE);
            if (result.data() != null) {
                productItemStack.getOrCreateTagElement(Utils.MOD_ID).merge(result.data());
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
                    tier = ForgeEquipUtils.getOneTimeForgeTier(smithHammer.getTier());
                }
                if (tier < result.oldTier()) {
                    tier = result.oldTier();
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
            Compute.clearPlayerScreen(player);
            judgeFrontAndGuideV2(player, productItemStack);
            LogUtils.getLogger().info("锻造 {} 锻造了 {}", Name.get(player), productItemStack);
            InventoryOperation.giveItemStack(player, productItemStack);
        } else {
            Compute.sendFormatMSG(player, Component.literal("锻造").withStyle(ChatFormatting.GRAY),
                    Component.literal("背包里似乎没有足够的物品用于锻造。"));
            for (ItemStack itemStack : materialList) {
                if (InventoryOperation.itemStackCount(inventory, itemStack.getItem()) < itemStack.getCount()) {
                    Compute.sendFormatMSG(player, Component.literal("锻造").withStyle(ChatFormatting.GRAY),
                            Component.literal("缺少:").withStyle(ChatFormatting.WHITE).append(itemStack.getItem().getDefaultInstance().getDisplayName()).append(Component.literal("*" + (itemStack.getCount() - InventoryOperation.itemStackCount(inventory, itemStack.getItem())))));
                }
            }
            Compute.sendFormatMSG(player, Component.literal("锻造预览").withStyle(ChatFormatting.GRAY),
                    Component.literal("锻造预览：").withStyle(ChatFormatting.WHITE).append(forgedItem.getDefaultInstance().getDisplayName()));
        }
    }

    private static @NotNull OldEquipInfo getOldEquipInfo(Player player, Item forgedItem, List<ItemStack> materialList, Inventory inventory, CompoundTag data, int oldTier, ItemStack productItemStack) {
        for (ItemStack stack : materialList) {
            Item item = stack.getItem();
            // 复制必要的Tag
            if (Utils.weaponList.contains(item)
                    || Utils.armorList.contains(item)
                    || Utils.offHandTag.containsKey(item)) {
                for (int i = 0; i < inventory.getContainerSize(); i++) {
                    ItemStack itemStack = inventory.getItem(i);
                    if (itemStack.is(item)) {
                        if ((Utils.mainHandTag.containsKey(item) && Utils.mainHandTag.containsKey(forgedItem))
                                || (Utils.armorTag.containsKey(item) && Utils.armorTag.containsKey(forgedItem))
                                || (Utils.offHandTag.containsKey(item) && Utils.offHandTag.containsKey(forgedItem))) {
                            data = inventory.getItem(i).getOrCreateTagElement(Utils.MOD_ID).copy();
                        }
                        if (Utils.mainHandTag.containsKey(item) || Utils.armorTag.containsKey(item)) {
                            oldTier = ForgeEquipUtils.getEquipForgeQuality(itemStack);
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
        OldEquipInfo result = new OldEquipInfo(data, oldTier);
        return result;
    }

    private record OldEquipInfo(CompoundTag data, int oldTier) {
    }

    private static void judgeFrontAndGuideV2(Player player, ItemStack productItemStack) {
        // 前置条件判定
        Item item = productItemStack.getItem();
        Set<Item> iceKnightEquips = Set.of(ModItems.ICE_SWORD.get(),
                ModItems.ICE_BOW.get(), ModItems.ICE_SCEPTRE.get());
        Set<Item> moonEquips = Set.of(ModItems.MOON_SWORD.get(), ModItems.MOON_BOW.get(),
                ModItems.MOON_SCEPTRE.get(), ModItems.MOON_SHIELD.get(),
                ModItems.MOON_KNIFE.get(), ModItems.MOON_BOOK.get(),
                ModItems.MOON_BELT.get());
        Set<Item> castleEquips = Set.of(ModItems.CASTLE_SWORD.get(),
                ModItems.CASTLE_BOW.get(), ModItems.CASTLE_SCEPTRE.get());
        Set<Item> divineWeapons = Set.of(DivineIslandItems.DIVINE_SWORD_0.get(),
                DivineIslandItems.DIVINE_BOW_0.get(), DivineIslandItems.DIVINE_SCEPTRE_0.get());
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
        if (divineWeapons.contains(item)) {
            NoTeamInstanceModule.putPlayerAllowReward(player, NoTeamInstanceModule.AllowRewardKey.divine, true);
        }

        // 引导触发
        Set<Item> forestEquips = Set.of(ModItems.FOREST_HELMET.get(), ModItems.FOREST_CHEST.get(),
                ModItems.FOREST_LEGGINGS.get(), ModItems.FOREST_BOOTS.get(),
                ModItems.FOREST_SWORD_0.get(), ModItems.FOREST_BOW_0.get());
        if (forestEquips.contains(item)) {
            Guide.trigV2(player, Guide.StageV2.FOREST_EQUIP);
        }
        Set<Item> lakeEquips = Set.of(ModItems.LAKE_HELMET.get(), ModItems.LAKE_CHEST.get(),
                ModItems.LAKE_LEGGINGS.get(), ModItems.LAKE_BOOTS.get(),
                ModItems.LAKE_SWORD_0.get(), ModItems.LAKE_BOW_0.get(), ModItems.LAKE_SCEPTRE_0.get());
        if (lakeEquips.contains(item)) {
            Guide.trigV2(player, Guide.StageV2.LAKE_EQUIP);
        }
        Set<Item> mineEquips = Set.of(ModItems.MINE_HELMET.get(), ModItems.MINE_CHEST.get(),
                ModItems.MINE_LEGGINGS.get(), ModItems.MINE_BOOTS.get(),
                ModItems.MINE_SWORD_0.get(), ModItems.MINE_BOW_0.get());
        if (mineEquips.contains(item)) {
            Guide.trigV2(player, Guide.StageV2.MINE_EQUIP);
        }
        Set<Item> volcanoEquips = Set.of(ModItems.VOLCANO_HELMET.get(), ModItems.VOLCANO_CHEST.get(),
                ModItems.VOLCANO_LEGGINGS.get(), ModItems.VOLCANO_BOOTS.get(),
                ModItems.VOLCANO_SWORD_0.get(), ModItems.VOLCANO_BOW_0.get());
        if (volcanoEquips.contains(item)) {
            Guide.trigV2(player, Guide.StageV2.VOLCANO_EQUIP);
        }
        Set<Item> enhanceEquips = Set.of(ModItems.SKY_HELMET.get(),
                ModItems.SKY_CHEST.get(), ModItems.SKY_LEGGINGS.get(), ModItems.SKY_BOOTS.get(),
                ModItems.SKY_BOW.get(), ModItems.SKY_SWORD.get());
        if (enhanceEquips.contains(item)) {
            Guide.trigV2(player, Guide.StageV2.ENHANCE_EQUIP);
        }
        Guide.trigV2(player, Guide.StageV2.FIRST_FORGE);
        if (!StringUtils.FlagInTag.getPlayerFlag(player, firstTimeForge)) {
            StringUtils.FlagInTag.setPlayerString(player, firstTimeForge, true);
            playerMSGSendDelayMap.put(player.getName().getString(), Tick.get() + 100);
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
                ItemStack itemStack = new ItemStack(ModItems.PLAIN_RUNE.get(), 2);
                Compute.sendFormatMSG(serverPlayer, Component.literal("引导-灌注").withStyle(ChatFormatting.AQUA), Component.literal("现在，拿着给予你的").withStyle(ChatFormatting.WHITE).append(itemStack.getDisplayName()).append(Component.literal("找到灌注台(在村庄锻造区域均有分布)，尝试给平原系列武器进行灌注升级吧！").withStyle(ChatFormatting.WHITE)));
                InventoryOperation.giveItemStack(serverPlayer, itemStack);
                MySound.soundToPlayer(serverPlayer, SoundEvents.EXPERIENCE_ORB_PICKUP);
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        components.add(Component.literal("-右键尝试锻造").withStyle(ChatFormatting.AQUA));
        components.add(Component.literal("  ").withStyle(ChatFormatting.AQUA).append(forgedItem.getDefaultInstance().getDisplayName()));
        components.add(Component.literal("-材料需求:").withStyle(ChatFormatting.AQUA));
        List<ItemStack> materialList = ForgeRecipe.recipes.get(forgedItem);
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
