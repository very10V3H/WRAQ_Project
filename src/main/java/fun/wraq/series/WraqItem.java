package fun.wraq.series;

import fun.wraq.render.gui.illustrate.Display;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WraqItem extends Item {

    private boolean isFoiled = false;
    public WraqItem(Properties properties) {
        super(properties);
        if (Display.materialList.isEmpty()) Display.setMaterialList();
        Display.materialList.add(this);
    }

    public WraqItem(Properties properties, boolean isMaterial, boolean isFoiled) {
        super(properties);
        if (isMaterial) {
            if (Display.materialList.isEmpty()) {
                Display.setMaterialList();
            }
            Display.materialList.add(this);
        }
        this.isFoiled = isFoiled;
    }

    private List<Component> description = List.of();
    public WraqItem(Properties properties, boolean isMaterial, boolean isFoiled, List<Component> description) {
        this(properties, isMaterial, isFoiled);
        this.description = description;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        if (!description.isEmpty()) {
            components.addAll(description);
        }
        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return isFoiled;
    }
}
