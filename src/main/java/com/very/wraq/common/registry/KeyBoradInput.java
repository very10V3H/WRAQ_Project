package com.very.wraq.common.registry;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;


@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class KeyBoradInput {
    public static final String KEY_CATEGORY = "key.category.vmd.vmd";

    public static final KeyMapping USE1 = new KeyMapping("key.vmd.ues_1", KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_F, KEY_CATEGORY);

    public static final KeyMapping USE2 = new KeyMapping("key.vmd.ues_2", KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_H, KEY_CATEGORY);

    public static final KeyMapping USE3 = new KeyMapping("key.vmd.ues_3", KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_Q, KEY_CATEGORY);

    public static final KeyMapping USE4 = new KeyMapping("key.vmd.ues_4", KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_V, KEY_CATEGORY);

    public static final KeyMapping USE5 = new KeyMapping("key.vmd.ues_5", KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_J, KEY_CATEGORY);

    public static final KeyMapping USE6 = new KeyMapping("key.vmd.ues_6", KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_K, KEY_CATEGORY);

    public static final KeyMapping Rolling = new KeyMapping("key.vmd.rolling", KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_Z, KEY_CATEGORY);

    public static final KeyMapping Mission = new KeyMapping("key.vmd.mission", KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_P, KEY_CATEGORY);

    public static final KeyMapping NavigateSet = new KeyMapping("key.vmd.navigate_set", KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_G, KEY_CATEGORY);

/*    public static final KeyMapping TeamGuiOpen = new KeyMapping("key.vmd.team_gui_open", KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_U, KEY_CATEGORY);*/

    public static final KeyMapping GUIDE = new KeyMapping("key.vmd.guide", KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_CAPS_LOCK, KEY_CATEGORY);

    public static final KeyMapping ElementRoulette = new KeyMapping("key.vmd.element_roulette", KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_LEFT_ALT, KEY_CATEGORY);

}

