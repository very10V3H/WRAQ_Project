 package com.Very.very.Items.Forging;

 import com.Very.very.Files.FileHandler;
 import com.Very.very.Series.InstanceSeries.Ice.IceArmor;
 import com.Very.very.ValueAndTools.Compute;
 import com.Very.very.ValueAndTools.Utils.StringUtils;
 import com.Very.very.ValueAndTools.Utils.Utils;
 import com.Very.very.ValueAndTools.registry.ModItems;
 import net.minecraft.ChatFormatting;
 import net.minecraft.nbt.CompoundTag;
 import net.minecraft.network.chat.Component;
 import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
 import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
 import net.minecraft.server.level.ServerPlayer;
 import net.minecraft.world.InteractionHand;
 import net.minecraft.world.InteractionResultHolder;
 import net.minecraft.world.entity.player.Inventory;
 import net.minecraft.world.entity.player.Player;
 import net.minecraft.world.item.Item;
 import net.minecraft.world.item.ItemStack;
 import net.minecraft.world.item.TooltipFlag;
 import net.minecraft.world.level.Level;
 import org.jetbrains.annotations.Nullable;

 import java.io.IOException;
 import java.util.List;

 public class ForgeDraw extends Item {

     private final Item ForgedItem;
     public ForgeDraw(Properties p_41383_, Item item) {
         super(p_41383_);
         this.ForgedItem = item;
     }

     @Override
     public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
         components.add(Component.literal("-右键尝试锻造").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA));
         components.add(Component.literal("  ").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA).
                 append(ForgedItem.getDefaultInstance().getDisplayName()));
         components.add(Component.literal("-材料需求:").withStyle(ChatFormatting.AQUA));
         if (Utils.ForgeDrawRecipe.isEmpty()) Utils.ForgeDrawRecipeInit();
         List<ItemStack> MaterialList = Utils.ForgeDrawRecipe.get(ForgedItem);
         for (int i = 0; i < MaterialList.size(); i ++) {
             components.add(Compute.MaterialRequirement((i + 1),MaterialList.get(i).getDisplayName(),MaterialList.get(i).getCount()));
         }
         super.appendHoverText(itemStack, level, components, tooltipFlag);
     }

     @Override
     public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
         if (!level.isClientSide && interactionHand.equals(InteractionHand.OFF_HAND) && player.isShiftKeyDown()) {
             try {
                 Compute.PlayerItemUseOffHandWithRecord(player);
             } catch (IOException e) {
                 throw new RuntimeException(e);
             }
             try {
                 Compute.ItemStackGive(player,ModItems.CompleteGem.get().getDefaultInstance());
             } catch (IOException e) {
                 throw new RuntimeException(e);
             }
         }
         if(!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND))
         {
             if (Utils.ForgeDrawRecipe.isEmpty()) Utils.ForgeDrawRecipeInit();
             List<ItemStack> MaterialList = Utils.ForgeDrawRecipe.get(ForgedItem);
             Inventory inventory = player.getInventory();
             boolean ContainMaterial = true;
             for (int i = 0; i < MaterialList.size(); i ++) {
                 if (!Compute.ItemStackCheck(inventory,MaterialList.get(i).getItem(),MaterialList.get(i).getCount())) ContainMaterial = false;
             }
             if(ContainMaterial) {
                 for (int i = 0; i < MaterialList.size(); i ++) {
                     try {
                         Compute.ItemStackRemove(inventory,MaterialList.get(i).getItem(),MaterialList.get(i).getCount());
                     } catch (IOException e) {
                         throw new RuntimeException(e);
                     }
                 }
                 Compute.FormatMSGSend(player,Component.literal("锻造").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY),
                         Component.literal("锻造成功！").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD));

                 Utils.forgeNumMap.put(ForgedItem,Utils.forgeNumMap.get(ForgedItem) + 1);

                 try {
                     FileHandler.ForgeNumWrite();
                 } catch (IOException e) {
                     throw new RuntimeException(e);
                 }

                 ItemStack itemStack = ForgedItem.getDefaultInstance();
                 CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
                 data.putInt(StringUtils.ForgeNum,Utils.forgeNumMap.get(ForgedItem));
                 if (itemStack.getItem() instanceof IceArmor) Compute.IceArmorAttributeGive(itemStack);
                 Compute.FormatBroad(level,Component.literal("锻造").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY),
                         Component.literal(player.getName().getString()+"成功锻造了 ").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)
                                 .append(itemStack.getDisplayName())
                                 .append(Component.literal(" 这是维瑞阿契的第").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE))
                                 .append(Component.literal("" + Utils.forgeNumMap.get(ForgedItem)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA))
                                 .append(Component.literal("枚 ").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE))
                                 .append(itemStack.getDisplayName()));

                 List<ServerPlayer> playerList = player.getServer().getPlayerList().getPlayers();
                 ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                         new ClientboundSetTitleTextPacket(itemStack.getDisplayName());
                 ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                         new ClientboundSetSubtitleTextPacket(Component.literal("已被" + player.getName().getString() + "成功锻造！").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA));

                 playerList.forEach(serverPlayer -> {
                     serverPlayer.connection.send(clientboundSetTitleTextPacket);
                     serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
                 });

                 try {
                     Compute.PlayerItemUseWithRecord(player);
                 } catch (IOException e) {
                     throw new RuntimeException(e);
                 }
                 try {
                     Compute.ItemStackGive(player,itemStack);
                 } catch (IOException e) {
                     throw new RuntimeException(e);
                 }

             }
             else
             {
                 Compute.FormatMSGSend(player,Component.literal("锻造").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY),
                         Component.literal("背包里似乎没有足够的物品用于锻造。"));
                 for (int i = 0; i < MaterialList.size(); i ++) {
                     if (Compute.ItemStackCount(inventory,MaterialList.get(i).getItem()) < MaterialList.get(i).getCount()) {
                         Compute.FormatMSGSend(player,Component.literal("锻造").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY),
                                 Component.literal("缺少:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                         append(MaterialList.get(i).getItem().getDefaultInstance().getDisplayName()).
                                         append(Component.literal("*"+(MaterialList.get(i).getCount()-Compute.ItemStackCount(inventory,MaterialList.get(i).getItem())))));
                     }
                 }
                 Compute.FormatMSGSend(player,Component.literal("锻造预览").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY),
                         Component.literal("锻造预览：").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                 append(ForgedItem.getDefaultInstance().getDisplayName()));
             }
         }
         return super.use(level, player, interactionHand);
     }
 }
