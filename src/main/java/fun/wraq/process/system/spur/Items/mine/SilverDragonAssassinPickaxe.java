package fun.wraq.process.system.spur.Items.mine;

import fun.wraq.common.equip.WraqSword;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.fast.Te;
import fun.wraq.common.impl.inslot.InCuriosOrEquipSlotAttributesModify;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.EnhanceNormalAttackModifier;
import fun.wraq.process.func.power.WraqPower;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SilverDragonAssassinPickaxe extends WraqSword implements ActiveItem, InCuriosOrEquipSlotAttributesModify {
    public SilverDragonAssassinPickaxe(Properties properties) {
        super(properties);
    }

    @Override
    public Style getMainStyle() {
        return null;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Te.s("旧世刺客之道", getMainStyle()));
        components.add(Te.s(" 提供", ComponentUtils.AttributeDescription.critDamage("当前等级")));
        ComponentUtils.descriptionActive(components, Te.s("刺杀", getMainStyle()));
        components.add(Te.s(" 位移至准星选定目标后，下一次普通攻击倍率将调整为", "100%", CustomStyle.styleOfPower));
        return components;
    }

    @Override
    public Component getSuffix() {
        return null;
    }

    @Override
    public void active(Player player) {
        Mob mob = WraqPower.getDefaultTargetMobList(player).stream().min(new Comparator<Mob>() {
            @Override
            public int compare(Mob o1, Mob o2) {
                return (int) (o1.distanceTo(player) - o2.distanceTo(player));
            }
        }).orElse(null);
        if (mob != null) {
            Vec3 delta = player.getEyePosition().subtract(mob.getEyePosition());
            float xRot = player.getXRot();
            float yRot = player.getYRot();
            Vec3 pos = mob.position().add(delta.normalize());
            ServerPlayer serverPlayer = (ServerPlayer) player;
            serverPlayer.teleportTo(serverPlayer.serverLevel(), pos.x, pos.y, pos.z, yRot, xRot);
            EnhanceNormalAttackModifier.addModifier(player, new EnhanceNormalAttackModifier("", 4, 0));
        }
    }

    @Override
    public double manaCost(Player player) {
        return 0;
    }

    @Override
    public List<Attribute> getAttributes(Player player, ItemStack stack) {
        return List.of(
                new Attribute(Utils.critDamage, player.experienceLevel * 0.01)
        );
    }
}
