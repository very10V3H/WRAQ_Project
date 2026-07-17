/** AI-Generated, 2026-05-10 */
package fun.wraq.common.util;

import fun.wraq.common.util.struct.HudIcon;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.hud.CoolDownTimeS2CPacket;
import fun.wraq.networking.hud.DebuffTimeS2CPacket;
import fun.wraq.networking.hud.EffectLastTimeS2CPacket;
import fun.wraq.networking.misc.RemoveEffectLastTimeByItemIdS2CPacket;
import fun.wraq.networking.misc.RemoveEffectLastTimeS2CPacket;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class HudUtil {

    // ---- Effect Last Time (HUD buff display) ----

    public static void removeEffectLastTime(Player player, Item item) {
        ModNetworking.sendToClient(new RemoveEffectLastTimeS2CPacket("item/" + item), (ServerPlayer) player);
    }

    public static void removeEffectLastTime(Player player, String url) {
        ModNetworking.sendToClient(new RemoveEffectLastTimeS2CPacket(url), (ServerPlayer) player);
    }

    public static void removeEffectLastTimeByItemId(Player player, String itemId) {
        ModNetworking.sendToClient(new RemoveEffectLastTimeByItemIdS2CPacket(itemId), (ServerPlayer) player);
    }

    public static void sendEffectLastTime(Player player, ItemStack itemStack, int tickCount) {
        ModNetworking.sendToClient(new EffectLastTimeS2CPacket("item/" + itemStack.getItem(), tickCount), (ServerPlayer) player);
    }

    public static void sendEffectLastTimeToClientPlayer(Item item, int level, int tick, boolean noTime) {
        ClientUtils.effectTimeLasts.removeIf(hudIcon -> hudIcon.url.equals("item/" + item.toString()));
        if (noTime) {
            ClientUtils.effectTimeLasts.add(new HudIcon("item/" + item, tick, tick, level, true));
        } else {
            ClientUtils.effectTimeLasts.add(new HudIcon("item/" + item, tick, tick, level));
        }
    }

    public static void sendEffectLastTime(Player player, Item item, int tickCount) {
        sendEffectLastTime(player, item, tickCount, 0, false);
    }

    public static void sendEffectLastTime(Player player, ItemStack itemStack, int tickCount, int level, boolean forever) {
        sendEffectLastTime(player, itemStack.getItem(), tickCount, level, forever);
    }

    public static void sendEffectLastTime(Player player, Item item, int level, boolean forever) {
        sendEffectLastTime(player, item, 25565, level, forever);
    }

    public static void sendEffectLastTimeByItemId(Player player, String itemId, int level, boolean forever) {
        sendEffectLastTime(player, "item/" + itemId, 25565, level, forever);
    }

    public static void sendEffectLastTime(Player player, Item item, int tickCount, int level, boolean forever) {
        sendEffectLastTime(player, "item/" + item, tickCount, level, forever);
    }

    public static void sendEffectLastTime(Player player, String url, int level, boolean forever) {
        sendEffectLastTime(player, url, 25565, level, forever);
    }

    public static void sendEffectLastTime(Player player, String url, int tickCount, int level, boolean forever) {
        ModNetworking.sendToClient(new EffectLastTimeS2CPacket(url, tickCount, level, forever), (ServerPlayer) player);
    }

    // ---- Cooldown HUD ----

    public static void sendCoolDownTime(Player player, Item item, int tickCount) {
        ModNetworking.sendToClient(new CoolDownTimeS2CPacket("item/" + item, tickCount), (ServerPlayer) player);
    }

    public static void sendCoolDownTime(Player player, ItemStack itemStack, int tickCount) {
        ModNetworking.sendToClient(new CoolDownTimeS2CPacket("item/" + itemStack.getItem(), tickCount), (ServerPlayer) player);
    }

    public static void sendCoolDownTime(Player player, String url, int tickCount) {
        ModNetworking.sendToClient(new CoolDownTimeS2CPacket(url, tickCount), (ServerPlayer) player);
    }

    // ---- Debuff HUD ----

    public static void sendDebuffTime(Player player, String url, int tickCount, int level, boolean forever) {
        ModNetworking.sendToClient(new DebuffTimeS2CPacket(url, tickCount, level, forever), (ServerPlayer) player);
    }

    public static void sendDebuffTime(Player player, Item item, int tickCount, int level) {
        sendDebuffTime(player, "item/" + item, tickCount, level, false);
    }

    public static void sendDebuffTime(Player player, String url, int tickCount) {
        sendDebuffTime(player, url, tickCount, 0, false);
    }

    public static void removeDebuffTime(Player player, String url) {
        ModNetworking.sendToClient(new fun.wraq.networking.hud.RemoveDebuffTimeS2CPacket(url), (ServerPlayer) player);
    }

    public static void removeDebuffTime(Player player, Item item) {
        ModNetworking.sendToClient(new fun.wraq.networking.hud.RemoveDebuffTimeS2CPacket("item/" + item), (ServerPlayer) player);
    }

    // ---- Action Bar (damage display at bottom of screen) ----

    public static void damageActionBarPacketSend(Player player, double baseDamage, double ignoreDefenceDamage,
                                                  boolean isMana, boolean isCrit) {
        if ((baseDamage + ignoreDefenceDamage - 0) < 1E-6) {
            return;
        }
        String string = "";
        String crit = " ";
        if (isCrit) crit = Utils.Emoji.CritRate;
        if (ignoreDefenceDamage > 0) {
            string = "+ [" + String.format("%.0f", ignoreDefenceDamage) + "]";
        }
        Style critStyle = isMana ? CustomStyle.styleOfEntropy : CustomStyle.styleOfPower;
        ChatFormatting damageStyle = isMana ? ChatFormatting.LIGHT_PURPLE : ChatFormatting.YELLOW;
        ClientboundSetActionBarTextPacket clientboundSetActionBarTextPacket =
                new ClientboundSetActionBarTextPacket(Component.literal(crit).withStyle(critStyle).
                        append(Component.literal(String.format("%.0f", baseDamage) + " ").withStyle(damageStyle)).
                        append(Component.literal(string).withStyle(CustomStyle.styleOfSea)));
        ServerPlayer serverPlayer = (ServerPlayer) player;
        serverPlayer.connection.send(clientboundSetActionBarTextPacket);
    }

    public static void damageActionBarPacketSend(Player player, double baseDamage, double ignoreDefenceDamage,
                                                  boolean isMana, boolean isCrit, String elementType, double elementDamageValue) {
        String string = "";
        String crit = " ";
        if (isCrit) crit = Utils.Emoji.CritRate;
        if (ignoreDefenceDamage > 0) {
            string = "+ [" + String.format("%.0f", ignoreDefenceDamage) + "] ";
        }
        String elementDamageValueString = " ";
        if (elementDamageValue != 0) {
            elementDamageValueString = "「" + String.format("%.0f", elementDamageValue) + "」";
        }
        Style critStyle = isMana ? CustomStyle.styleOfEntropy : CustomStyle.styleOfPower;
        ChatFormatting damageStyle = isMana ? ChatFormatting.LIGHT_PURPLE : ChatFormatting.YELLOW;
        ClientboundSetActionBarTextPacket clientboundSetActionBarTextPacket =
                new ClientboundSetActionBarTextPacket(Component.literal(crit).withStyle(critStyle).
                        append(Component.literal(String.format("%.0f", baseDamage) + " ").withStyle(damageStyle)).
                        append(Component.literal(string).withStyle(CustomStyle.styleOfSea)).
                        append(Component.literal(elementDamageValueString).withStyle(Element.styleMap.get(elementType))));
        ServerPlayer serverPlayer = (ServerPlayer) player;
        serverPlayer.connection.send(clientboundSetActionBarTextPacket);
    }

    public static void sendActionBarTextContentToPlayer(Player player, Component content) {
        ClientboundSetActionBarTextPacket clientboundSetActionBarTextPacket
                = new ClientboundSetActionBarTextPacket(content);
        ServerPlayer serverPlayer = (ServerPlayer) player;
        serverPlayer.connection.send(clientboundSetActionBarTextPacket);
    }

    public static void sendActionBarMSG(Player player, Component component) {
        ClientboundSetActionBarTextPacket clientboundSetActionBarTextPacket =
                new ClientboundSetActionBarTextPacket(component);
        ((ServerPlayer) player).connection.send(clientboundSetActionBarTextPacket);
    }
}
