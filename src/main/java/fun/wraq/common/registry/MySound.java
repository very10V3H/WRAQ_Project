package fun.wraq.common.registry;

import net.minecraft.core.Holder;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class MySound {
    public static void soundToNearPlayer(Player player, SoundEvent soundEvent) {
        if (soundEvent.equals(ModSounds.Mana.get()) || soundEvent.equals(ModSounds.Use.get()) || soundEvent.equals(ModSounds.Wind.get())
                || soundEvent.equals(ModSounds.Lava.get()) || soundEvent.equals(ModSounds.Nether_Power.get()) || soundEvent.equals(ModSounds.Mana_Sword.get()))
            return;
        Level level = player.level();
        level.getServer().getPlayerList().getPlayers().forEach(serverPlayer -> {
            if (serverPlayer.distanceTo(player) <= 16) {
                ClientboundSoundPacket clientboundSoundPacket = new ClientboundSoundPacket(Holder.direct(soundEvent),
                        SoundSource.PLAYERS, player.getX(), player.getY(), player.getZ(), 1f, 1, 0);
                serverPlayer.connection.send(clientboundSoundPacket);
            }
        });
    }

    public static void soundToAllPlayer(Level level, SoundEvent soundEvent) {
        List<ServerPlayer> playerList = level.getServer().getPlayerList().getPlayers();
        playerList.forEach(serverPlayer -> {
            ClientboundSoundPacket clientboundSoundPacket =
                    new ClientboundSoundPacket(Holder.direct(soundEvent), SoundSource.PLAYERS, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), 1, 1, 0);
            serverPlayer.connection.send(clientboundSoundPacket);
        });
    }

    public static void soundToPlayer(Player player, SoundEvent soundEvent) {
        ServerPlayer serverPlayer = (ServerPlayer) player;
        ClientboundSoundPacket clientboundSoundPacket =
                new ClientboundSoundPacket(Holder.direct(soundEvent), SoundSource.PLAYERS, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), 1, 1, 0);
        serverPlayer.connection.send(clientboundSoundPacket);
    }

    public static void soundToPlayer(Player player, SoundEvent soundEvent, Vec3 pos) {
        ServerPlayer serverPlayer = (ServerPlayer) player;
        ClientboundSoundPacket clientboundSoundPacket =
                new ClientboundSoundPacket(Holder.direct(soundEvent), SoundSource.PLAYERS, pos.x, pos.y, pos.z, 1, 1, 0);
        serverPlayer.connection.send(clientboundSoundPacket);
    }

    public static void soundOnMob(Mob mob, SoundEvent soundEvent) {
        List<ServerPlayer> playerList = mob.level().getServer().getPlayerList().getPlayers();
        ClientboundSoundPacket clientboundSoundPacket =
                new ClientboundSoundPacket(Holder.direct(soundEvent), SoundSource.PLAYERS, mob.getX(), mob.getY(), mob.getZ(), 1, 1, 0);
        playerList.forEach(serverPlayer -> {
            serverPlayer.connection.send(clientboundSoundPacket);
        });
    }
}
