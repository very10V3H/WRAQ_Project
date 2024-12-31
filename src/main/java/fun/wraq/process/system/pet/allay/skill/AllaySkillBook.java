package fun.wraq.process.system.pet.allay.skill;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.MySound;
import fun.wraq.process.system.pet.allay.AllayPet;
import fun.wraq.process.system.pet.allay.AllayPetPlayerData;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.WraqItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class AllaySkillBook extends WraqItem {

    private final String skillLevelDataKey;
    private final Component skillDescription;
    private final Component levelDescription;
    public AllaySkillBook(Properties properties, String skillLevelDataKey,
                          Component skillDescription, Component levelDescription) {
        super(properties);
        this.skillLevelDataKey = skillLevelDataKey;
        this.skillDescription = skillDescription;
        this.levelDescription = levelDescription;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        components.add(Te.s(" 为", "悦灵", CustomStyle.styleOfWorld, "使用"));
        components.add(Te.s(" 概率提升", skillDescription, "的", "等级", ChatFormatting.LIGHT_PURPLE));
        components.add(Te.s(" ", levelDescription));
        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            if (AllayPetPlayerData.getAllayXpLevel(player) < 1) {
                AllayPet.sendMSG(player, Te.s("似乎还未拥有一只属于你的", "悦灵", CustomStyle.styleOfWorld));
            } else {
                int skillLevel = AllayPetPlayerData.getAllayPetData(player).getInt(skillLevelDataKey);
                if (skillLevel < 20) {
                    Compute.playerItemUseWithRecord(player);
                    double rate = (20 - skillLevel) / 20d;
                    Random random = new Random();
                    if (random.nextDouble() < rate) {
                        AllayPetPlayerData.incrementSkillLevel(player, skillLevelDataKey, 1);
                        AllayPet.sendMSG(player, Te.s(AllayPetPlayerData.getAllayName(player), "的",
                                skillDescription, "已提升至",
                                "Lv." + AllayPetPlayerData.getSkillLevel(player, skillLevelDataKey), CustomStyle.styleOfWorld));
                        MySound.soundToPlayer(player, SoundEvents.PLAYER_LEVELUP);
                        MySound.soundToPlayer(player, SoundEvents.ALLAY_ITEM_TAKEN);
                    } else {
                        AllayPet.sendMSG(player, Te.s(AllayPetPlayerData.getAllayName(player),
                                "似乎还没有完全领悟到", skillDescription, "的精髓",
                                "(" + String.format("%.2f%%", rate * 100) + ")", CustomStyle.styleOfStone));
                        MySound.soundToPlayer(player, SoundEvents.ALLAY_THROW);
                    }
                } else {
                    AllayPet.sendMSG(player, Te.s(AllayPetPlayerData.getAllayName(player),
                            "已经完全掌握了", skillDescription, "，不需要再学习了。"));
                }
            }
        }
        return super.use(level, player, interactionHand);
    }
}
