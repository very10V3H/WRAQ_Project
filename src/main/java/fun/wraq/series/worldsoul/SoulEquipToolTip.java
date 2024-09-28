package fun.wraq.series.worldsoul;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class SoulEquipToolTip {
    @SubscribeEvent
    public static void SoulSwordToolTipEvent(ItemTooltipEvent event) {
        ItemStack itemStack = event.getItemStack();
        List<Component> components = event.getToolTip();
/*        if (Compute.IsSoulEquip(itemStack)) {
            CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
            int ForgeTime = data.getInt(StringUtils.SoulEquipForge);
            if (itemStack.is(ModItems.SoulSword.get())) {
                components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Utils.WeaponTypeComponents.NormalSword));
                ComponentUtils.DescriptionDash(components,ChatFormatting.WHITE,CustomStyle.styleOfWorld,ChatFormatting.WHITE);
                ComponentUtils.DescriptionOfBasic(components);
                Compute.SoulEmojiDescriptionBaseAttackDamage(components,SoulEquipAttribute.BaseAttribute.SoulSword.AttackDamage,ForgeTime);
                Compute.SoulEmojiDescriptionCritRate(components,SoulEquipAttribute.BaseAttribute.SoulSword.CritRate,ForgeTime);
                Compute.SoulEmojiDescriptionCritDamage(components,SoulEquipAttribute.BaseAttribute.SoulSword.CritDamage,ForgeTime);
                Compute.SoulEmojiDescriptionDefencePenetration(components,SoulEquipAttribute.BaseAttribute.SoulSword.DefencePenetration,ForgeTime);
                Compute.SoulEmojiDescriptionDefencePenetration0(components,SoulEquipAttribute.BaseAttribute.SoulSword.DefencePenetration0,ForgeTime);
                Compute.SoulEmojiDescriptionHealSteal(components,SoulEquipAttribute.BaseAttribute.SoulSword.HealthSteal,ForgeTime);
                Compute.SoulEmojiDescriptionMovementSpeed(components,SoulEquipAttribute.BaseAttribute.SoulSword.MovementSpeed,ForgeTime);
                ComponentUtils.DescriptionDash(components,ChatFormatting.WHITE,CustomStyle.styleOfWorld,ChatFormatting.WHITE);
                Compute.SuffixOfWorldSoul(components);
            }
            if (itemStack.is(ModItems.SoulBow.get())) {
                components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Utils.WeaponTypeComponents.Bow));
                ComponentUtils.DescriptionDash(components,ChatFormatting.WHITE,CustomStyle.styleOfWorld,ChatFormatting.WHITE);
                ComponentUtils.DescriptionOfBasic(components);
                Compute.SoulEmojiDescriptionBaseAttackDamage(components,SoulEquipAttribute.BaseAttribute.SoulBow.AttackDamage,ForgeTime);
                Compute.SoulEmojiDescriptionCritRate(components,SoulEquipAttribute.BaseAttribute.SoulBow.CritRate,ForgeTime);
                Compute.SoulEmojiDescriptionCritDamage(components,SoulEquipAttribute.BaseAttribute.SoulBow.CritDamage,ForgeTime);
                Compute.SoulEmojiDescriptionDefencePenetration(components,SoulEquipAttribute.BaseAttribute.SoulBow.DefencePenetration,ForgeTime);
                Compute.SoulEmojiDescriptionDefencePenetration0(components,SoulEquipAttribute.BaseAttribute.SoulBow.DefencePenetration0,ForgeTime);
                Compute.SoulEmojiDescriptionMovementSpeed(components,SoulEquipAttribute.BaseAttribute.SoulBow.MovementSpeed,ForgeTime);
                ComponentUtils.DescriptionDash(components,ChatFormatting.WHITE,CustomStyle.styleOfWorld,ChatFormatting.WHITE);
                Compute.SuffixOfWorldSoul(components);
            }
            if (itemStack.is(ModItems.SoulSceptre.get())) {
                components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Utils.WeaponTypeComponents.Sceptre));
                ComponentUtils.DescriptionDash(components,ChatFormatting.WHITE,CustomStyle.styleOfWorld,ChatFormatting.WHITE);
                ComponentUtils.DescriptionOfBasic(components);
                Compute.SoulEmojiDescriptionManaAttackDamage(components,SoulEquipAttribute.BaseAttribute.SoulSceptre.ManaAttackDamage,ForgeTime);
                Compute.SoulManaCostDescription(components,SoulEquipAttribute.BaseAttribute.SoulSceptre.ManaCost,ForgeTime);
                Compute.SoulEmojiDescriptionManaRecover(components,SoulEquipAttribute.BaseAttribute.SoulSceptre.ManaRecover,ForgeTime);
                Compute.SoulEmojiDescriptionManaPenetration(components,SoulEquipAttribute.BaseAttribute.SoulSceptre.ManaPenetration,ForgeTime);
                Compute.SoulEmojiDescriptionManaPenetration0(components,SoulEquipAttribute.BaseAttribute.SoulSceptre.ManaPenetration0,ForgeTime);
                Compute.SoulEmojiDescriptionMaxMana(components,SoulEquipAttribute.BaseAttribute.SoulSceptre.MaxMana,ForgeTime);
                Compute.SoulEmojiDescriptionMovementSpeed(components,SoulEquipAttribute.BaseAttribute.SoulSceptre.MovementSpeed,ForgeTime);
                ComponentUtils.DescriptionDash(components,ChatFormatting.WHITE,CustomStyle.styleOfWorld,ChatFormatting.WHITE);
                Compute.SuffixOfWorldSoul(components);
            }
        }*/
    }

}
