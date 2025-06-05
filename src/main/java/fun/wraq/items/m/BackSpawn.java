package fun.wraq.items.m;

import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.process.system.respawn.MyRespawnRule;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BackSpawn extends Item {

    public static Component clientNearestSpawnPoint = Te.s("");

    public BackSpawn(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (interactionHand.equals(InteractionHand.MAIN_HAND) && !level.isClientSide) {
            ServerPlayer serverPlayer = (ServerPlayer) player;
            MyRespawnRule.SpawnPoint spawnPoint = MyRespawnRule.findNearestSpawnPoint(serverPlayer);
            if (serverPlayer.level().dimension().equals(Level.OVERWORLD)) {
                spawnPoint.teleport(serverPlayer);
                ClientboundSetActionBarTextPacket clientboundSetActionBarTextPacket =
                        new ClientboundSetActionBarTextPacket(Component.literal("回到了").withStyle(CustomStyle.styleOfSky).
                                append(spawnPoint.zoneName()));
                serverPlayer.connection.send(clientboundSetActionBarTextPacket);
            } else {
                String name = serverPlayer.getName().getString();
                if (MyRespawnRule.playerLastOverWorldPos.containsKey(name)) {
                    MyRespawnRule.SpawnPos spawnPos = MyRespawnRule.playerLastOverWorldPos.get(name);
                    serverPlayer.teleportTo(serverPlayer.getServer().getLevel(Level.OVERWORLD),
                            spawnPos.vec3().x, spawnPos.vec3().y, spawnPos.vec3().z, spawnPos.rotX(), 0);
                } else spawnPoint.teleport(serverPlayer);

                ClientboundSetActionBarTextPacket clientboundSetActionBarTextPacket =
                        new ClientboundSetActionBarTextPacket(Component.literal("回到了主世界").withStyle(CustomStyle.styleOfSky));
                serverPlayer.connection.send(clientboundSetActionBarTextPacket);
            }
            player.getCooldowns().addCooldown(this, Tick.s(10));
/*            CompoundTag data = player.getMainHandItem().getOrCreateTagElement(Utils.MOD_ID);
            if (!player.isShiftKeyDown()) {
                String name = player.getName().getString();
                if (isCallingMap.containsKey(name)) {
                    isCallingMap.remove(name);
                    calledTicksMap.remove(name);
                    ClientboundSetActionBarTextPacket clientboundSetActionBarTextPacket =
                            new ClientboundSetActionBarTextPacket(Component.literal("中断了回城引导..").withStyle(ChatFormatting.GREEN));
                    ServerPlayer serverPlayer = (ServerPlayer) player;
                    serverPlayer.connection.send(clientboundSetActionBarTextPacket);
                } else {
                    isCallingMap.put(name, true);
                    posIndexMap.put(name, data.getInt(spawnPointIndex));
                }
            } else {
                if (player.level().dimension().equals(Level.OVERWORLD)) {
                    player.getCooldowns().addCooldown(this, 20);
                    MyRespawnRule.SpawnPoint spawnPoint = MyRespawnRule.findNearestSpawnPoint(player);
                    if (spawnPoint.vec3().distanceTo(player.position()) > 32) {
                        Compute.sendActionBarMSG(player, Te.s("距离最近的传送点 - ", spawnPoint.zoneName(),
                                " 太远了，无法重新绑定传送点！"));
                    } else {
                        int index = MyRespawnRule.overworldSpawnPos.indexOf(spawnPoint);
                        if (index != -1) data.putInt(spawnPointIndex, index);
                        ClientboundSetActionBarTextPacket clientboundSetActionBarTextPacket =
                                new ClientboundSetActionBarTextPacket(Component.literal("已将该卷轴传送点绑定至 ").withStyle(ChatFormatting.WHITE).
                                        append(spawnPoint.zoneName()));
                        ServerPlayer serverPlayer = (ServerPlayer) player;
                        serverPlayer.connection.send(clientboundSetActionBarTextPacket);
                        setName(player.getMainHandItem());
                    }
                }
            }*/
        }
        return super.use(level, player, interactionHand);
    }

    public static void setName(Player player) {
        ItemStack itemStack = player.getMainHandItem();
        if (itemStack.is(ModItems.BACK_SPAWN_TICKET.get())) {
            if (player.level().dimension().equals(Level.OVERWORLD)) {
                itemStack.setHoverName(Te.s("回城卷轴 - ", ChatFormatting.BLUE, clientNearestSpawnPoint));
            } else {
                itemStack.setHoverName(Te.s("回城卷轴 - ", ChatFormatting.BLUE, "主世界", ChatFormatting.GREEN));
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag p_41424_) {
/*        if (level != null) {
            CompoundTag data = stack.getOrCreateTagElement(Utils.MOD_ID);
            int index = data.getInt(spawnPointIndex);
            if (level.dimension().equals(ClientLevel.OVERWORLD)) {
                components.add(Component.literal("右键").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("引导").withStyle(ChatFormatting.AQUA)).
                        append(Component.literal("传送至 ").withStyle(ChatFormatting.WHITE)).
                        append(MyRespawnRule.overworldSpawnPos.get(index).zoneName()));
                components.add(Component.literal(" "));
                components.add(Component.literal("[shift + 右键]").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("将此卷轴的锚定点标记至临近村庄/城镇").withStyle(ChatFormatting.AQUA)));
            } else {
                components.add(Component.literal("右键").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("引导").withStyle(ChatFormatting.AQUA)).
                        append(Component.literal("返回").withStyle(ChatFormatting.WHITE)).
                        append(Component.literal("主世界").withStyle(ChatFormatting.GREEN)));
            }
        }*/
        if (level != null) {
            if (level.dimension().equals(ClientLevel.OVERWORLD)) {
                components.add(Te.s(" 右键回到", "最近重生点", CustomStyle.styleOfSunIsland));
                components.add(Te.s(" 最近的重生点为: ", clientNearestSpawnPoint));
            } else {
                components.add(Te.s(" 右键回到", "主世界", ChatFormatting.GREEN));
            }
        }
        ComponentUtils.coolDownTimeDescription(components, 10);
        super.appendHoverText(stack, level, components, p_41424_);
    }

    public static Map<String, Boolean> isCallingMap = new HashMap<>();
    public static Map<String, Integer> calledTicksMap = new HashMap<>();
    public static Map<String, Integer> posIndexMap = new HashMap<>();
    public static Vec3 spawnPos = new Vec3(956.5, 232.5, 16.5);

    public static void tick(Player player) {
        String name = player.getName().getString();
        int needSeconds;
        ServerPlayer serverPlayer = (ServerPlayer) player;
        int index = posIndexMap.getOrDefault(name, 0);
        MyRespawnRule.SpawnPoint spawnPoint = MyRespawnRule.overworldSpawnPos.get(index);
        Vec3 vec3 = spawnPoint.vec3();

        if (isCallingMap.containsKey(name) && isCallingMap.get(name)) {

            if (player.level().dimension().equals(Level.OVERWORLD)) {
                needSeconds = (int) (vec3.distanceTo(player.position()) / 100);
            } else needSeconds = 15;

            int needTicks = needSeconds * 20;
            calledTicksMap.put(name, calledTicksMap.getOrDefault(name, 0) + 1);
            if (calledTicksMap.get(name) >= needTicks || player.isCreative()) {
                calledTicksMap.remove(name);
                isCallingMap.remove(name);
                if (serverPlayer.level().dimension().equals(Level.OVERWORLD)) {
                    spawnPoint.teleport(serverPlayer);
                    ClientboundSetActionBarTextPacket clientboundSetActionBarTextPacket =
                            new ClientboundSetActionBarTextPacket(Component.literal("回到了").withStyle(CustomStyle.styleOfSky).
                                    append(spawnPoint.zoneName()));
                    serverPlayer.connection.send(clientboundSetActionBarTextPacket);
                } else {
                    if (MyRespawnRule.playerLastOverWorldPos.containsKey(name)) {
                        MyRespawnRule.SpawnPos spawnPos = MyRespawnRule.playerLastOverWorldPos.get(name);
                        serverPlayer.teleportTo(serverPlayer.getServer().getLevel(Level.OVERWORLD),
                                spawnPos.vec3().x, spawnPos.vec3().y, spawnPos.vec3().z, spawnPos.rotX(), 0);
                    } else spawnPoint.teleport(serverPlayer);

                    ClientboundSetActionBarTextPacket clientboundSetActionBarTextPacket =
                            new ClientboundSetActionBarTextPacket(Component.literal("回到了主世界").withStyle(CustomStyle.styleOfSky));
                    serverPlayer.connection.send(clientboundSetActionBarTextPacket);
                }
            } else {
                ClientboundSetActionBarTextPacket clientboundSetActionBarTextPacket =
                        new ClientboundSetActionBarTextPacket(Component.literal("正在进行回城引导.. 还剩" +
                                String.format("%.2f", 0.05 * (needTicks - calledTicksMap.get(name))) + "秒").withStyle(CustomStyle.styleOfSky));
                serverPlayer.connection.send(clientboundSetActionBarTextPacket);
            }
        }
    }
}
