package fun.wraq.process.system.ore;

import fun.wraq.blocks.blocks.inject.InjectRecipe;
import fun.wraq.common.equip.WraqPickaxe;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.struct.InjectingRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class NormalPickaxe extends WraqPickaxe {

    private final Style style;
    private final boolean isFoiled;
    public NormalPickaxe(Properties properties, double mineSpeed, Style style, boolean isFoiled) {
        super(properties);
        this.style = style;
        this.isFoiled = isFoiled;
        WraqPickaxe.mineSpeed.put(this, mineSpeed);
    }

    public NormalPickaxe(Properties properties, double mineSpeed, Style style, boolean isFoiled, InjectingRecipe injectingRecipe) {
        this(properties, mineSpeed, style, isFoiled);
        InjectRecipe.injectingRecipeMap.put(this, injectingRecipe);
    }

    @Override
    public Style getMainStyle() {
        return style;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        return List.of();
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getNormalPickaxeSuffix();
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return isFoiled;
    }
}
