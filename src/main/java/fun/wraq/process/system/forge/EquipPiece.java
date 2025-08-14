package fun.wraq.process.system.forge;

import fun.wraq.blocks.entity.Decomposable;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.series.WraqItem;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class EquipPiece extends WraqItem implements Decomposable {

    private final int tier;
    public EquipPiece(Properties properties, int tier) {
        super(properties, true, tier > 2);
        this.tier = tier;
    }

    @Override
    public ItemStack getProduct() {
        if (tier > 0) {
            return new ItemStack(ForgeEquipUtils.getEquipPiece(tier - 1), 4);
        }
        return Items.AIR.getDefaultInstance();
    }

    public static void onKillMob(Player player, Mob mob) {
        int mobLevelTier = Math.min(7, MobSpawn.getMobXpLevel(mob) / 40);
        for (int i = 0; i < mobLevelTier; i ++) {
            double dropRate = 0.01 / (Math.pow(4, i));
            ItemAndRate itemAndRate = new ItemAndRate(ForgeEquipUtils.getEquipPiece(i), dropRate);
            itemAndRate.sendWithMSG(player, 1);
        }
    }
}
