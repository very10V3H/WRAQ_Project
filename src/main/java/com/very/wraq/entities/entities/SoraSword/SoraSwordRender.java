package com.very.wraq.entities.entities.SoraSword;

import com.very.wraq.customized.players.sceptre.Sora_vanilla.SoraVanillaSword;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class SoraSwordRender extends GeoItemRenderer<SoraVanillaSword> {
    public SoraSwordRender() {
        super(new SoraSwordModel());
    }
}
