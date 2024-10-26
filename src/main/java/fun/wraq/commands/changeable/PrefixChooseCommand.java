package fun.wraq.commands.changeable;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.common.Compute;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class PrefixChooseCommand implements Command<CommandSourceStack> {
    public static PrefixChooseCommand instance = new PrefixChooseCommand();


    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        CompoundTag data = player.getPersistentData();
        String string = StringArgumentType.getString(context, "num");
        int chooseCount = Integer.parseInt(string);
        int count = 0;
        boolean flag = true;
        count++;
        if (chooseCount == count) {
            data.putString("Prefix", "初来乍到");
            Compute.sendFormatMSG(player, Component.literal("称号").withStyle(ChatFormatting.GOLD),
                    Component.literal("已激活称号").withStyle(ChatFormatting.WHITE).
                            append(Component.literal("初来乍到").withStyle(ChatFormatting.GRAY)));
            flag = false;
        }

        for (PrefixCommand.PrefixCondition prefixCondition : PrefixCommand.getSimplePrefixTypeList()) {
            if (prefixCondition.matchCondition(player) == 1) {
                count++;
                if (chooseCount == count) {
                    data.putString(PrefixCommand.prefix, prefixCondition.getPrefixDescription());
                    data.putString(PrefixCommand.prefixColor, String.valueOf(prefixCondition.getStyle().getColor()));
                    Compute.sendFormatMSG(player, Component.literal("称号").withStyle(ChatFormatting.GOLD),
                            Component.literal("已激活称号").withStyle(ChatFormatting.WHITE).
                                    append(Component.literal(prefixCondition.getPrefixDescription()).withStyle(prefixCondition.getStyle())));
                    flag = false;
                }
            }
        }

/*        if (data.contains(StringUtils.DragonPrefix)) {
            count ++;
            if (chooseCount == count) {
                data.putString("Prefix", "龙行龘龘");
                Compute.formatMSGSend(player, Component.literal("称号").withStyle(ChatFormatting.GOLD),
                        Component.literal("已激活称号").withStyle(ChatFormatting.WHITE).
                                append(Component.literal("龙行龘龘").withStyle(CustomStyle.styleOfSpring)));
                flag = false;
            }
        }
        if (data.contains(StringUtils.XiangLiPrefix)) {
            count ++;
            if (chooseCount == count) {
                data.putString("Prefix", "理塘王");
                Compute.formatMSGSend(player, Component.literal("称号").withStyle(ChatFormatting.GOLD),
                        Component.literal("已激活称号").withStyle(ChatFormatting.WHITE).
                                append(Component.literal("理塘王").withStyle(CustomStyle.styleOfField)));
                flag = false;
            }
        }
        if (data.contains(StringUtils.LotteryPrefix)) {
            count ++;
            if (chooseCount == count) {
                data.putString("Prefix", "赌神");
                Compute.formatMSGSend(player, Component.literal("称号").withStyle(ChatFormatting.GOLD),
                        Component.literal("已激活称号").withStyle(ChatFormatting.WHITE).
                                append(Component.literal("赌神").withStyle(ChatFormatting.GOLD)));
                flag = false;
            }
        }
        if (data.contains(StringUtils.QingMingPrefix)) {
            count ++;
            if (chooseCount == count) {
                data.putString("Prefix", "雨纷纷");
                Compute.formatMSGSend(player, Component.literal("称号").withStyle(ChatFormatting.GOLD),
                        Component.literal("已激活称号").withStyle(ChatFormatting.WHITE).
                                append(Component.literal("雨纷纷").withStyle(CustomStyle.styleOfHealth)));
                flag = false;
            }
        }*/
        if (flag) {
            Compute.sendFormatMSG(player, Component.literal("称号").withStyle(ChatFormatting.GOLD),
                    Component.literal("似乎还没有达到解锁此称号的条件。").withStyle(ChatFormatting.WHITE));
        } else {
            Compute.sendFormatMSG(player, Component.literal("称号").withStyle(ChatFormatting.GOLD),
                    Component.literal("称号需等待5s左右方可生效。").withStyle(ChatFormatting.WHITE));
        }
        return 0;
    }
}
