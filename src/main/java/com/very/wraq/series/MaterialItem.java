package com.very.wraq.series;

import com.very.wraq.render.gui.illustrate.Display;
import net.minecraft.world.item.Item;

public class MaterialItem extends Item {

    public MaterialItem(Properties p_41383_) {
        super(p_41383_);
        if (Display.materialList.isEmpty()) Display.setMaterialList();
        Display.materialList.add(this);
    }
}
