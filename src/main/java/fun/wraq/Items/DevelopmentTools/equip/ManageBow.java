package fun.wraq.Items.DevelopmentTools.equip;

import fun.wraq.common.equip.WraqBow;
import fun.wraq.common.impl.onhit.OnHitEffectEquip;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class ManageBow extends WraqBow implements OnHitEffectEquip, ManageEquip {

    public ManageBow(Properties properties) {
        super(properties);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfEnd;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        return List.of();
    }

    @Override
    public Component getSuffix() {
        return null;
    }

    @Override
    public void onHit(Player player, Mob mob) {
        Damage.causeDirectDamageToMob(player, mob, mob.getMaxHealth() * 0.1);
    }
}
