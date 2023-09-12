package com.Very.very.Hcommand;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.StringUtils;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class ClearSkillCommand implements Command<CommandSourceStack> {
    public static ClearSkillCommand instance = new ClearSkillCommand();


    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        CompoundTag data = player.getPersistentData();
        if (data.getString(StringUtils.Login.Status).equals(StringUtils.Login.Offline)) {
            Compute.FormatMSGSend(player,Component.literal("维瑞阿契").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA),
                    Component.literal("请先登录。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
        }
        if (data.getString(StringUtils.Login.Status).equals(StringUtils.Login.Online)) {
            data.remove(StringUtils.SkillPoint_Total);
            data.remove(StringUtils.SkillPoint_Used);
            data.remove(StringUtils.SkillData.Sword);
            data.remove(StringUtils.SkillData.Bow);
            data.remove(StringUtils.SkillData.Mana);
            data.remove(StringUtils.Skill.SwordBase);
            data.remove(StringUtils.Skill.BowBase);
            data.remove(StringUtils.Skill.ManaBase);
            data.remove(StringUtils.AbilityPoint_Total);
            data.remove(StringUtils.AbilityPoint_Used);
            for (int i = 0; i < 5; i ++) {
                data.remove(StringUtils.AbilityArray[i]);
            }
            for (int i = 0; i < 3; i ++) {
                data.remove(StringUtils.SkillArray[i]);
            }
        }
        return 0;
    }
}
