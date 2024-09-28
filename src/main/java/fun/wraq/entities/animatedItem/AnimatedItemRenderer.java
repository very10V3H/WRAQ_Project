package fun.wraq.entities.animatedItem;

import fun.wraq.entities.animatedItem.AnimatedItem;
import fun.wraq.entities.animatedItem.AnimatedItemModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class AnimatedItemRenderer extends GeoItemRenderer<AnimatedItem> {
    public AnimatedItemRenderer() {
        super(new AnimatedItemModel());
    }
}
