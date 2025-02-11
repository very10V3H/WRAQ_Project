package fun.wraq.series.instance.series.plain;

import fun.wraq.blocks.entity.Decomposable;
import fun.wraq.common.equip.WraqSceptre;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.projectiles.mana.ManaArrow;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class PlainBossSceptre extends WraqSceptre implements Decomposable {

    public PlainBossSceptre(Properties properties) {
        super(properties);
        Utils.percentHealthRecover.put(this, 0.025);
        Utils.healthRecover.put(this, 100d);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfPlain;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        return List.of();
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfPlainBoss();
    }

    @Override
    public ItemStack getProduct() {
        return new ItemStack(ModItems.PlainBossSoul.get(), 5);
    }

    @Override
    protected ManaArrow summonManaArrow(Player player, double rate) {
        return null;
    }
}
