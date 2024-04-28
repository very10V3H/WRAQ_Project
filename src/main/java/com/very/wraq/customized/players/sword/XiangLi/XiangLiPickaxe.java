package com.very.wraq.customized.players.sword.XiangLi;

import com.very.wraq.customized.Customize;
import com.very.wraq.render.ToolTip.CustomStyle;
import com.very.wraq.valueAndTools.BasicAttributeDescription;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class XiangLiPickaxe extends PickaxeItem{

    private final int Num;
    public XiangLiPickaxe(Tier p_42961_, Properties p_42964_, int Num) {
        super(p_42961_, 2, 0, p_42964_);
        this.Num = Num;
        Utils.AttackDamage.put(this,this.BaseDamage);

        Utils.DefencePenetration0.put(this,this.DefencePenetration0);
        Utils.HealthSteal.put(this,this.HealSteal);
        Utils.CritRate.put(this,this.CriticalHitRate);
        Utils.CritDamage.put(this,this.CHitDamage);
        Utils.MovementSpeed.put(this,this.SpeedUp);
        Utils.MainHandTag.put(this,1d);
        Utils.CustomizedList.add(this);
        Utils.SwordTag.put(this,1d);
    }
    private final double BaseDamage = Customize.AttackDamage;

    private final double DefencePenetration0 = Customize.DefencePenetration0;
    private final double CriticalHitRate = 0.3;
    private final double CHitDamage = 2.4;
    private final double HealSteal = 0.08;
    private final double SpeedUp = 0.4F;

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        stack.hideTooltipPart(ItemStack.TooltipPart.ENCHANTMENTS);
        Compute.ForgingHoverName(stack,Component.literal("<维瑞级>").withStyle(ChatFormatting.GOLD).append(Component.literal("冰川探索者").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD)));
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("镐子").withStyle(ChatFormatting.GRAY)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,CustomStyle.styleOfNether,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,CustomStyle.styleOfNether,ChatFormatting.WHITE);
        Compute.DescriptionPassive(components,Component.literal("红物质能量充盈").withStyle(ChatFormatting.RED));
        components.add(Component.literal(" 这把充满着").withStyle(ChatFormatting.WHITE).
                append(Component.literal("红物质能量").withStyle(ChatFormatting.RED)).
                append(Component.literal("的镐子拥有着").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("瞬间破坏").withStyle(ChatFormatting.RED)).
                append(Component.literal("方块的能力.").withStyle(ChatFormatting.WHITE)));
        Compute.DescriptionActive(components,Component.literal("红物质能量释放").withStyle(ChatFormatting.RED));
        components.add(Component.literal(" 常年的矿石采集，使红物质镐的能量几乎快要溢出").withStyle(ChatFormatting.DARK_RED));
        components.add(Component.literal(" 获得: ").withStyle(ChatFormatting.WHITE));
        components.add(Component.literal(" 1.").withStyle(ChatFormatting.RED).
                append(Compute.AttributeDescription.DefencePenetration("30% + [500]")));
        components.add(Component.literal(" 2.").withStyle(ChatFormatting.RED).
                append(Compute.AttributeDescription.CritDamage("360%")));
        components.add(Component.literal(" 3.").withStyle(ChatFormatting.RED).
                append(Compute.AttributeDescription.ExAttackDamage("360")));
        components.add(Component.literal(" 持续20s"));
        Compute.CoolDownTimeDescription(components,30);
        Compute.ManaCostDescription(components,50);
        Compute.DescriptionDash(components,ChatFormatting.WHITE, CustomStyle.styleOfNether,ChatFormatting.WHITE);
        components.add(Component.literal(" 崇高的敬意化作一把红物质镐，授予对维瑞阿契做出了杰出贡献的 - 一只香梨").withStyle(ChatFormatting.AQUA));
        super.appendHoverText(stack,level,components,flag);
    }
    @Override
    public boolean hurtEnemy(ItemStack p_40994_, LivingEntity p_40995_, LivingEntity p_40996_) {
        p_40994_.hurtAndBreak(0, p_40996_, (p_41007_) -> {
            p_41007_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
        });
        return true;
    }

    @Override
    public boolean mineBlock(ItemStack p_40998_, Level p_40999_, BlockState p_41000_, BlockPos p_41001_, LivingEntity p_41002_) {
        if (!p_40999_.isClientSide && p_41000_.getDestroySpeed(p_40999_, p_41001_) != 0.0d) {
            p_40998_.hurtAndBreak(0, p_41002_, (p_40992_) -> {
                p_40992_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
        }

        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide) {
            Compute.USE(player,this);
            ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
            Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(itemStack);
            map.put(Enchantments.BLOCK_EFFICIENCY,10);
            EnchantmentHelper.setEnchantments(map,itemStack);
        }
        return super.use(level, player, interactionHand);
    }


/*    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private static final HumanoidModel.ArmPose EXAMPLE_POSE = HumanoidModel.ArmPose.create("EXAMPLE", false, (model, entity, arm) -> {
                if (arm == HumanoidArm.RIGHT) {
                    model.rightArm.xRot = (float) (Math.random() * Math.PI * 2);
                } else {
                    model.leftArm.xRot = (float) (Math.random() * Math.PI * 2);
                }
            });
            @Override
            public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm, ItemStack itemInHand, float partialTick, float equipProcess, float swingProcess) {
                int i = arm == HumanoidArm.RIGHT ? 1 : -1;
                poseStack.translate(i * 0.56F, -0.52F, -0.72F);
                if (player.getUseItem() == itemInHand && player.isUsingItem()) {
                    poseStack.translate(0.0, -0.05, 0.0);
                }
                return true;
            }
        });

    }*/

}
