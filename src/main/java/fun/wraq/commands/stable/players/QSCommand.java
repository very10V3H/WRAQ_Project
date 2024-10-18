package fun.wraq.commands.stable.players;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.StringUtils;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class QSCommand implements Command<CommandSourceStack> {
    public static QSCommand instance = new QSCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        if (player == null) return 0;
        CompoundTag data = player.getPersistentData();
        String profession = StringArgumentType.getString(context, "type").toLowerCase();
        String sword = "sword";
        String bow = "bow";
        String mana = "mana";
        int xpLevel = player.experienceLevel;
        if (xpLevel < 90) {
            Compute.sendFormatMSG(player, Component.literal("快捷配置").withStyle(CustomStyle.styleOfFlexible),
                    Te.s("需要达到", "90级", ChatFormatting.LIGHT_PURPLE, "才能使用快捷配置"));
            return 0;
        }
        if (List.of(sword, bow, mana).contains(profession)) {
            if (!player.isCreative()) {
                if (!InventoryOperation.removeItem(player.getInventory(), ModItems.notePaper.get(), 1)) {
                    Compute.sendFormatMSG(player, Component.literal("快捷配置").withStyle(CustomStyle.styleOfFlexible),
                            Component.literal("需要消耗 ").withStyle(ChatFormatting.WHITE).
                                    append(ModItems.notePaper.get().getDefaultInstance().getDisplayName()).
                                    append(Component.literal(" 方能进行快捷配置").withStyle(ChatFormatting.WHITE)));
                    return 0;
                }
            }
            Compute.resetSkillAndAbility(player);
            if (profession.equals(sword)) {
                data.putInt(StringUtils.Skill.SwordBase, xpLevel / 2);
                data.putInt(StringUtils.SkillPoint_Used, xpLevel / 2);
                data.putInt(StringUtils.Ability.Power, xpLevel / 2);
                data.putInt(StringUtils.AbilityPoint_Used, xpLevel / 2);

                data.putString(StringUtils.SkillData.Sword, "X0005X0500X0500");

                Compute.sendFormatMSG(player, Component.literal("快捷配置").withStyle(CustomStyle.styleOfFlexible),
                        Component.literal("已配置 ").withStyle(ChatFormatting.WHITE).
                                append(Component.literal("战士").withStyle(CustomStyle.styleOfPower)).
                                append(Component.literal(" 常用专精与能力配置").withStyle(ChatFormatting.WHITE)));
            }
            if (profession.equals(bow)) {
                data.putInt(StringUtils.Skill.BowBase, xpLevel / 2);
                data.putInt(StringUtils.SkillPoint_Used, xpLevel /2);
                data.putInt(StringUtils.Ability.Flexibility, xpLevel / 2);
                data.putInt(StringUtils.AbilityPoint_Used, xpLevel / 2);

                data.putString(StringUtils.SkillData.Bow, "X0005X0500X0500");

                Compute.sendFormatMSG(player, Component.literal("快捷配置").withStyle(CustomStyle.styleOfFlexible),
                        Component.literal("已配置 ").withStyle(ChatFormatting.WHITE).
                                append(Component.literal("弓箭手").withStyle(CustomStyle.styleOfFlexible)).
                                append(Component.literal(" 常用专精与能力配置").withStyle(ChatFormatting.WHITE)));
            }
            if (profession.equals(mana)) {
                data.putInt(StringUtils.Skill.ManaBase, xpLevel / 2);
                data.putInt(StringUtils.SkillPoint_Used, xpLevel / 2);
                data.putInt(StringUtils.Ability.Intelligent, xpLevel / 2);
                data.putInt(StringUtils.AbilityPoint_Used, xpLevel / 2);

                data.putString(StringUtils.SkillData.Mana, "0X0050X050X0500");

                Compute.sendFormatMSG(player, Component.literal("快捷配置").withStyle(CustomStyle.styleOfFlexible),
                        Component.literal("已配置 ").withStyle(ChatFormatting.WHITE).
                                append(Component.literal("法师").withStyle(CustomStyle.styleOfMana)).
                                append(Component.literal(" 常用专精与能力配置").withStyle(ChatFormatting.WHITE)));
            }
        }
        return 0;
    }
}
