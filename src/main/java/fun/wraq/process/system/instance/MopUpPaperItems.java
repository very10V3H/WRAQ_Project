package fun.wraq.process.system.instance;

import fun.wraq.common.util.Utils;
import fun.wraq.events.mob.instance.instances.dimension.CitadelGuardianInstance;
import fun.wraq.events.mob.instance.instances.dimension.NetherInstance;
import fun.wraq.events.mob.instance.instances.element.*;
import fun.wraq.events.mob.instance.instances.sakura.DevilInstance;
import fun.wraq.events.mob.instance.instances.sakura.SakuraBossInstance;
import fun.wraq.process.system.teamInstance.instances.blackCastle.NewCastleInstance;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MopUpPaperItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);
    public static final RegistryObject<Item> PLAIN = ITEMS.register("plain_mop_up_paper",
            () -> new MopUpPaper(new Item.Properties().rarity(CustomStyle.RedBold), PlainInstance.getInstance()));
    public static final RegistryObject<Item> NETHER = ITEMS.register("nether_mop_up_paper",
            () -> new MopUpPaper(new Item.Properties().rarity(CustomStyle.RedBold), NetherInstance.getInstance()));
    public static final RegistryObject<Item> PURPLE_IRON_BOSS = ITEMS.register("purple_iron_boss_mop_up_paper",
            () -> new MopUpPaper(new Item.Properties().rarity(CustomStyle.RedBold), PurpleIronInstance.getInstance()));
    public static final RegistryObject<Item> ICE_KNIGHT = ITEMS.register("ice_knight_mop_up_paper",
            () -> new MopUpPaper(new Item.Properties().rarity(CustomStyle.RedBold), IceInstance.getInstance()));
    public static final RegistryObject<Item> SAKURA_BOSS = ITEMS.register("sakura_boss_mop_up_paper",
            () -> new MopUpPaper(new Item.Properties().rarity(CustomStyle.RedBold), SakuraBossInstance.getInstance()));
    public static final RegistryObject<Item> DEVIL = ITEMS.register("devil_mop_up_paper",
            () -> new MopUpPaper(new Item.Properties().rarity(CustomStyle.RedBold), DevilInstance.getInstance()));
    public static final RegistryObject<Item> MOON = ITEMS.register("moon_mop_up_paper",
            () -> new MopUpPaper(new Item.Properties().rarity(CustomStyle.RedBold), MoonInstance.getInstance()));
    public static final RegistryObject<Item> CITADEL = ITEMS.register("citadel_mop_up_paper",
            () -> new MopUpPaper(new Item.Properties().rarity(CustomStyle.RedBold), CitadelGuardianInstance.getInstance()));
    public static final RegistryObject<Item> WARDEN = ITEMS.register("warden_mop_up_paper",
            () -> new MopUpPaper(new Item.Properties().rarity(CustomStyle.RedBold), WardenInstance.getInstance()));
    public static final RegistryObject<Item> CASTLE = ITEMS.register("castle_mop_up_paper",
            () -> new MopUpPaper(new Item.Properties().rarity(CustomStyle.RedBold), NewCastleInstance.getInstance()));
}
