package fun.wraq.process.system.skill;

import fun.wraq.common.util.StringUtils;
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

    public static int getProficiencyTier(Player player, int type) {
        return player.getPersistentData().getInt(StringUtils.SkillArray[type]);
    }

    public static int getMaxType(Player player) {
        int swordProficiencyTier = getProficiencyTier(player, 0);
        int bowProficiencyTier = getProficiencyTier(player, 1);
        int manaProficiencyTier = getProficiencyTier(player, 2);
        if (swordProficiencyTier > bowProficiencyTier && swordProficiencyTier > manaProficiencyTier) {
            return 0;
        } else if (bowProficiencyTier > swordProficiencyTier && bowProficiencyTier > manaProficiencyTier) {
            return 1;
        } else {
            return 2;
        }
    }
}
