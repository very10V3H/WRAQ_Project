package fun.wraq.series.nether.power;

import fun.wraq.common.registry.ModSounds;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.process.func.power.WraqPower;
import fun.wraq.process.system.element.Element;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

import static fun.wraq.common.Compute.playerItemCoolDown;

public class MagmaPower extends WraqPower {

    public MagmaPower(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public Component getActiveName() {
        return null;
    }

    @Override
    public List<Component> getAdditionalComponents() {
        List<Component> components = new ArrayList<>();
        components.add(Component.literal("·[强化法术攻击]").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal("下次法术攻击命中时，将形成范围爆炸，对范围内的目标造成:").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.manaDamageValue("300%")));
        components.add(Component.literal(" - 这个伤害会附带").withStyle(ChatFormatting.WHITE).
                append(Element.Description.FireElement("1 + 100%")));
        components.add(Component.literal("并对受到影响的目标造成减速效果。").withStyle(ChatFormatting.GRAY));
        return components;
    }

    @Override
    public int getCoolDownSecond() {
        return 3;
    }

    @Override
    public double getManaCost() {
        return 300;
    }

    @Override
    public Component getSuffix() {
        return null;
    }

    @Override
    public void release(Player player) {
        CompoundTag data = player.getPersistentData();
        playerItemCoolDown(player, this, 3);
        data.putBoolean("MagmaPower", true);
        MySound.soundToNearPlayer(player, ModSounds.Nether_Power.get());
    }
}
