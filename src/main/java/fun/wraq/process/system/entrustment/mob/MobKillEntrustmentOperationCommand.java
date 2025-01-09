package fun.wraq.process.system.entrustment.mob;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.common.fast.Te;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class MobKillEntrustmentOperationCommand implements Command<CommandSourceStack> {
    public static MobKillEntrustmentOperationCommand instance = new MobKillEntrustmentOperationCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        if (player == null) return 0;
        String operation = StringArgumentType.getString(context, "operation");
        switch (operation) {
            case "accept" -> {
                MobKillEntrustment.playerTryToAccept(player);
            }
            case "cancel" -> {
                MobKillEntrustment.playerTryToCancel(player);
            }
            case "resetAllowTime" -> {
                MobKillEntrustment.playerNextAllowAcceptTickMap.remove(player.getName().getString());
            }
            case "villager" -> {
                if (player.isCreative()) {
                    Vec3 pos = player.pick(5, 0, true).getLocation();
                    Level level = player.level();
                    Villager villager = new Villager(EntityType.VILLAGER, level);
                    VillagerData villagerData = new VillagerData(VillagerType.PLAINS, VillagerProfession.LIBRARIAN, 5);
                    villager.setVillagerData(villagerData);
                    villager.moveTo(pos.x, pos.y, pos.z);
                    villager.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0);
                    villager.setCustomName(Te.s(MobKillEntrustment.VILLAGER_NAME, CustomStyle.styleOfWorld));
                    villager.setCustomNameVisible(true);
                    villager.setInvulnerable(true);
                    level.addFreshEntity(villager);
                } else {
                    MobKillEntrustment.sendMSG(player, Te.s("仅op使用"));
                }
            }
            case "queryDaily" -> {
                MobKillEntrustment.queryDailyTimes(player);
            }
            case "queryWeekly" -> {
                MobKillEntrustment.queryWeeklyTimes(player);
            }
            case "queryEachTierFinishedTimes" -> {
                MobKillEntrustment.queryEachTierFinishedTimes(player);
            }
            case "openStore" -> {
                MobKillEntrustment.openStore(player);
            }
        }
        return 0;
    }
}
