package com.Very.very.Items.Drugs;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;


@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class KeyBoradInput {
    public static final String KEY_CATEGORY = "key.category.vmd.vmd";
    public static final String KEY_DRUGS = "key.vmd.ues_drugs";
    public static final KeyMapping DRUGS = new KeyMapping(KEY_DRUGS, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_G, KEY_CATEGORY);

    public static final KeyMapping USE1 = new KeyMapping("key.vmd.ues_1", KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_F, KEY_CATEGORY);

    public static final KeyMapping USE2 = new KeyMapping("key.vmd.ues_2", KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_H, KEY_CATEGORY);

    public static final KeyMapping USE3 = new KeyMapping("key.vmd.ues_3", KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_Q, KEY_CATEGORY);

    public static final KeyMapping USE4 = new KeyMapping("key.vmd.ues_4", KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_V, KEY_CATEGORY);
}

