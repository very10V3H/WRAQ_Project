package com.very.wraq.Items.Forging;

import com.very.wraq.blocks.blocks.forge.ForgeRecipe;
import com.very.wraq.common.Compute;
import com.very.wraq.common.registry.MySound;
import com.very.wraq.common.util.StringUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.common.registry.ModItems;
import com.very.wraq.events.mob.instance.NoTeamInstanceModule;
import com.very.wraq.networking.ModNetworking;
import com.very.wraq.networking.misc.TeamPackets.ScreenSetS2CPacket;
import com.very.wraq.process.func.guide.Guide;
import com.very.wraq.process.system.forge.ForgeEquipUtils;
import com.very.wraq.process.system.forge.ForgeHammer;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WraqForge extends Item {

    public static String firstTimeForge = "firstTimeForge";
    public static Map<String, Integer> playerMSGSendDelayMap = new HashMap<>();
    public static Map<String, Integer> playerMSGSendDelayMap1 = new HashMap<>();
    public static Map<String, Integer> playerMSGSendDelayMap2 = new HashMap<>();
    private final Item forgedItem;

    public WraqForge(Properties p_41383_, Item item) {
        super(p_41383_);
        this.forgedItem = item;
    }

    public static void tryToForge(Player player, Item forgedItem) throws IOException {
        ItemStack forgeHammer = player.getMainHandItem();
        if (!(forgeHammer.getItem() instanceof ForgeHammer)) {
            Compute.sendFormatMSG(player, Component.literal("锻造").withStyle(ChatFormatting.GRAY),
                    Component.literal("请手持 ").withStyle(ChatFormatting.WHITE).
                            append(Component.literal("锻造锤").withStyle(ChatFormatting.GOLD)).
                            append(Component.literal(" 进行锻造").withStyle(ChatFormatting.WHITE)));
            return;
        }

        List<ItemStack> MaterialList = ForgeRecipe.forgeDrawRecipe.get(forgedItem);
        Inventory inventory = player.getInventory();
        boolean ContainMaterial = true;
        for (int i = 0; i < MaterialList.size(); i++) {
            if (!Compute.checkPlayerHasItem(inventory, MaterialList.get(i).getItem(), MaterialList.get(i).getCount()))
                ContainMaterial = false;
        }

        if (ContainMaterial) {

            CompoundTag data = null;
            for (ItemStack stack : MaterialList) {
                if (Utils.weaponList.contains(stack.getItem())) {
                    for (int i = 0 ; i < inventory.getContainerSize() ; i ++) {
                        if (inventory.getItem(i).is(stack.getItem())) {
                            data = inventory.getItem(i).getOrCreateTagElement(Utils.MOD_ID).copy();
                        }
                    }
                }
                Compute.removeItem(inventory, stack.getItem(), stack.getCount());
            }

            Compute.sendFormatMSG(player, Component.literal("锻造").withStyle(ChatFormatting.GRAY),
                    Component.literal("锻造成功！").withStyle(ChatFormatting.GOLD));
            MySound.soundToPlayer(player, SoundEvents.ANVIL_USE);

            ItemStack productItemStack = forgedItem.getDefaultInstance();

            // 锻造品质
            if (Utils.mainHandTag.containsKey(forgedItem) || Utils.armorTag.containsKey(forgedItem)) {
                int tier = 0;
                if (forgeHammer.getItem() instanceof ForgeHammer forgeHammer1) {
                    tier = ForgeEquipUtils.getOneTimeForgeTier(forgeHammer1.getTier());
                    Compute.playerItemUseWithRecord(player);
                }
                ForgeEquipUtils.setForgeQualityOnEquip(productItemStack, tier);

                Compute.formatBroad(player.level(), Component.literal("锻造").withStyle(ChatFormatting.GRAY),
                        Component.literal("").withStyle(ChatFormatting.WHITE).append(player.getDisplayName()).
                                append(Component.literal(" 成功锻造了 ").withStyle(ChatFormatting.WHITE)).
                                append(ForgeEquipUtils.description.get(tier)).append(Component.literal(" 的 ").withStyle(ChatFormatting.WHITE)).
                                append(productItemStack.getDisplayName()));
            } else {
                Compute.formatBroad(player.level(), Component.literal("锻造").withStyle(ChatFormatting.GRAY),
                        Component.literal("").withStyle(ChatFormatting.WHITE).append(player.getDisplayName()).
                                append(Component.literal(" 成功锻造了 ").withStyle(ChatFormatting.WHITE)).
                                append(productItemStack.getDisplayName()));
            }

            if (data != null) productItemStack.getOrCreateTagElement(Utils.MOD_ID).merge(data);
            Compute.forgingHoverName(productItemStack);

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
            List<Item> iceKnightEquips = List.of(ModItems.IceSword.get(), ModItems.IceBow.get(), ModItems.IceSceptre.get(),
                    ModItems.IceArmorHelmet.get(), ModItems.IceArmorChest.get(), ModItems.IceArmorLeggings.get(), ModItems.IceArmorBoots.get());
            List<Item> moonEquips = List.of(ModItems.MoonSword.get(), ModItems.MoonBow.get(), ModItems.MoonSceptre.get(),
                    ModItems.MoonHelmet.get(), ModItems.MoonLeggings.get());
            if (iceKnightEquips.contains(productItemStack.getItem())) {
                NoTeamInstanceModule.putPlayerAllowReward(player, NoTeamInstanceModule.AllowRewardKey.sakuraBoss, true);
                NoTeamInstanceModule.putPlayerAllowReward(player, NoTeamInstanceModule.AllowRewardKey.devil, true);
            }
            if (moonEquips.contains(productItemStack.getItem())) {
                NoTeamInstanceModule.putPlayerAllowReward(player, NoTeamInstanceModule.AllowRewardKey.blackCastle, true);
            }


            Compute.itemStackGive(player, productItemStack);
            Guide.trig(player, 4);
            if (!StringUtils.FlagInTag.getPlayerFlag(player, firstTimeForge)) {
                StringUtils.FlagInTag.setPlayerString(player, firstTimeForge, true);
                playerMSGSendDelayMap.put(player.getName().getString(), player.getServer().getTickCount() + 100);
            }
        } else {
            Compute.sendFormatMSG(player, Component.literal("锻造").withStyle(ChatFormatting.GRAY), Component.literal("背包里似乎没有足够的物品用于锻造。"));
            for (ItemStack itemStack : MaterialList) {
                if (Compute.itemStackCount(inventory, itemStack.getItem()) < itemStack.getCount()) {
                    Compute.sendFormatMSG(player, Component.literal("锻造").withStyle(ChatFormatting.GRAY), Component.literal("缺少:").withStyle(ChatFormatting.WHITE).append(itemStack.getItem().getDefaultInstance().getDisplayName()).append(Component.literal("*" + (itemStack.getCount() - Compute.itemStackCount(inventory, itemStack.getItem())))));
                }
            }
            Compute.sendFormatMSG(player, Component.literal("锻造预览").withStyle(ChatFormatting.GRAY), Component.literal("锻造预览：").withStyle(ChatFormatting.WHITE).append(forgedItem.getDefaultInstance().getDisplayName()));
        }
    }

    public static void tick(TickEvent.PlayerTickEvent event) throws IOException {
        ServerPlayer serverPlayer = (ServerPlayer) event.player;
        String name = serverPlayer.getName().getString();
        int tick = serverPlayer.getServer().getTickCount();
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
                Compute.itemStackGive(serverPlayer, itemStack);
                playerMSGSendDelayMap2.put(name, tick + 40);
                MySound.soundToPlayer(serverPlayer, SoundEvents.EXPERIENCE_ORB_PICKUP);
            }
        }

        if (playerMSGSendDelayMap2.containsKey(name)) {
            if (playerMSGSendDelayMap2.get(name) < tick) {
                playerMSGSendDelayMap2.remove(name);
                Compute.sendFormatMSG(serverPlayer, Component.literal("引导-快捷使用").withStyle(ChatFormatting.AQUA), Component.literal("你知道吗，在按键绑定中可以绑定快捷使用按键。").withStyle(ChatFormatting.WHITE));
                Compute.sendFormatMSG(serverPlayer, Component.literal("引导-快捷使用").withStyle(ChatFormatting.AQUA), Component.literal("使用快捷使用可以使你无前摇地施放主动，对于法师而言，").withStyle(ChatFormatting.WHITE).append(Component.literal("必须").withStyle(ChatFormatting.RED)).append(Component.literal("使用快捷使用来释放法术以获得主手武器提供的法术伤害加成").withStyle(ChatFormatting.WHITE)));
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
                components.add(Compute.MaterialRequirement((i + 1), materialList.get(i).getDisplayName(), materialList.get(i).getCount()));
            }
        }
        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        return super.use(level, player, interactionHand);
    }


}
