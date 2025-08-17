package fun.wraq.series.overworld.cold.sc4;

import fun.wraq.common.equip.WraqArmor;
import fun.wraq.common.fast.Te;
import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.List;

public class BlizzardBoots extends WraqArmor implements ForgeItem {

    private final int tier;
    public BlizzardBoots(ArmorMaterial armorMaterial, Type type, Properties properties, int tier) {
        super(armorMaterial, type, properties);
        this.tier = tier;
        if (type.equals(Type.BOOTS)) {
            Utils.movementSpeedCommon.put(this, new double[]{0.18, 0.24, 0.3}[tier]);
            Utils.maxHealth.put(this, new double[]{25000, 27500, 30000}[tier]);
        }
        Element.iceElementValue.put(this, new double[]{0.35, 0.5, 0.8}[tier]);
        Utils.levelRequire.put(this, 225);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfIce;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Te.s("吹雪机动!", getMainStyle()));
        components.add(Te.s(" 在空中再次按下跳跃，会向准星方向位移."));
        components.add(Te.s(" 落地后再腾空方能再次施放."));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfSuperCold();
    }

    public static boolean allowRelease = true;

    public static void onPressJump(Player player) {
        if (player.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof BlizzardBoots && allowRelease) {
            if (!player.onGround() && !player.isInWater()) {
                allowRelease = false;
                Vec3 pos = player.pick(1, 0, false)
                        .getLocation().subtract(player.getEyePosition());
                player.setDeltaMovement(pos.normalize().scale(2));
                player.playSound(SoundEvents.SNOW_BREAK);
                for (int i = 0; i < 10; i ++) {
                    player.level().addParticle(ParticleTypes.SNOWFLAKE,
                            player.position().x + RandomUtils.nextDouble(0, 3),
                            player.position().y,
                            player.position().z + RandomUtils.nextDouble(0, 3),
                            0, 0, 0);
                }
            }
        }
    }

    public static void handleTick(Player player) {
        if (player.onGround()) {
            allowRelease = true;
        }
    }

    @Override
    public List<ItemStack> forgeRecipe() {
        return List.of(
                new ItemStack(ModItems.COLLEGE_SENIOR_EQUIP_TICKET.get())
        );
    }
}
