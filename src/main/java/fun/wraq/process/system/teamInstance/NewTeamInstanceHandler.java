package fun.wraq.process.system.teamInstance;

import fun.wraq.process.system.teamInstance.instances.blackCastle.NewCastleInstance;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class NewTeamInstanceHandler {

    public static List<NewTeamInstance> instances = new ArrayList<>();

    public static List<NewTeamInstance> getInstances() {
        if (instances.isEmpty()) {
            instances.add(NewCastleInstance.getInstance());
        }
        return instances;
    }

    @SubscribeEvent
    public static void newTeamInstanceEvent(TickEvent.LevelTickEvent event) {
        if (event.side.isServer() && event.phase == TickEvent.Phase.START) {
            Level level = event.level;
            List<ServerPlayer> serverPlayerList = level.getServer().getPlayerList().getPlayers();
            if (level.dimension().equals(Level.OVERWORLD)) {
                getInstances().forEach(newTeamInstance -> {
                    boolean hasPlayerNearby = false;
                    for (ServerPlayer serverPlayer : serverPlayerList) {
                        if (serverPlayer.position().distanceTo(newTeamInstance.prepareCenterPos) < 20) {
                            hasPlayerNearby = true;
                            break;
                        }
                    }
                    if (hasPlayerNearby || !newTeamInstance.players.isEmpty()) newTeamInstance.tick(level);
                });
            }
        }
    }
}
