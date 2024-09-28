package fun.wraq.commands.stable.players;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.common.Compute;
import fun.wraq.common.util.StringUtils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class ParticleCommand implements Command<CommandSourceStack> {
    public static ParticleCommand instance = new ParticleCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        CompoundTag data = player.getPersistentData();
        String second = StringArgumentType.getString(context, "level");
        int level = Integer.parseInt(second);
        if (level >= 1 && level <= 10) {
            data.putInt(StringUtils.IgnoreParticleLevel, level);
            Compute.sendFormatMSG(player, Component.literal("粒子屏蔽").withStyle(CustomStyle.styleOfMana),
                    Component.literal("已将粒子屏蔽等级设置为:" + level + " (等级越高，粒子越少，最高为10，最低为1)").withStyle(ChatFormatting.WHITE));
        } else {
            Compute.sendFormatMSG(player, Component.literal("粒子屏蔽").withStyle(CustomStyle.styleOfMana),
                    Component.literal("屏蔽等级范围为1~10整数 (等级越高，粒子越少，最高为10，最低为1)").withStyle(ChatFormatting.WHITE));
        }
        return 0;
    }
}
