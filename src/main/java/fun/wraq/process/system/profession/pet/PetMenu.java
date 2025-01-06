package fun.wraq.process.system.profession.pet;

import com.simibubi.create.foundation.gui.menu.GhostItemMenu;
import fun.wraq.render.gui.testAndHelper.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.ItemStackHandler;

public class PetMenu extends GhostItemMenu<ItemStack> {

    public PetMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, extraData.readItem());
    }

    public PetMenu(int id, Inventory inv, ItemStack contentHolder) {
        super(ModMenuTypes.PET_MENU.get(), id, inv, contentHolder);
    }

    @Override
    protected ItemStackHandler createGhostInventory() {
        return new ItemStackHandler(18);
    }

    @Override
    protected boolean allowRepeats() {
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    protected ItemStack createOnClient(FriendlyByteBuf extraData) {
        return extraData.readItem();
    }

    @Override
    protected void addSlots() {
        this.addPlayerSlots(23, 22);
    }

    @Override
    protected void saveData(ItemStack itemStack) {
        contentHolder.getOrCreateTag().put("Items", this.ghostInventory.serializeNBT());
    }
}
