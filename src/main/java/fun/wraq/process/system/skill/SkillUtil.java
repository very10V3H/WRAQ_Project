package fun.wraq.process.system.skill;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public class SkillUtil {

    public static int getSkillTier(Player player, int index, String type) {
        CompoundTag data = player.getPersistentData();
        String skillData = data.getString(type);
        if (skillData.length() != 15) {
            return 0;
        }
        else {
            if (skillData.charAt(index) == 'X') {
                return 10;
            }
            else {
                return skillData.charAt(index) - 48;
            }
        }
    }
}
