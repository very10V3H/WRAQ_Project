package fun.wraq.process.system.profession.pet.allay.item;

import fun.wraq.common.util.Utils;
import fun.wraq.process.system.profession.pet.allay.AllayPetPlayerData;
import fun.wraq.process.system.profession.pet.allay.skill.AllaySkillBook;
import fun.wraq.process.system.profession.pet.allay.skill.AllaySkills;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AllayItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    public static final RegistryObject<Item> ALLAY_SPAWNER = ITEMS.register("allay_spawner",
            () -> new AllaySpawner(new Item.Properties().rarity(CustomStyle.WorldBold)));

    public static final RegistryObject<Item> ALLAY_CRYSTAL = ITEMS.register("allay_crystal",
            () -> new AllayCrystal(new Item.Properties().rarity(CustomStyle.WorldBold)));

    public static final RegistryObject<Item> ATTACK_SKILL_BOOK = ITEMS.register("allay_attack_skill_book",
            () -> new AllaySkillBook(new Item.Properties().rarity(CustomStyle.WorldBold),
                    AllayPetPlayerData.ATTACK_LEVEL_KEY, AllayPetPlayerData.ATTACK_SKILL_NAME,
                    AllaySkills.ATTACK_LEVEL_DESCRIPTION));

    public static final RegistryObject<Item> HEALING_SKILL_BOOK = ITEMS.register("allay_healing_skill_book",
            () -> new AllaySkillBook(new Item.Properties().rarity(CustomStyle.WorldBold),
                    AllayPetPlayerData.HEALING_LEVEL_KEY, AllayPetPlayerData.HEALING_SKILL_NAME,
                    AllaySkills.HEALING_LEVEL_DESCRIPTION));

    public static final RegistryObject<Item> GEM_PIECE_SKILL_BOOK = ITEMS.register("allay_gem_piece_skill_book",
            () -> new AllaySkillBook(new Item.Properties().rarity(CustomStyle.WorldBold),
                    AllayPetPlayerData.GEM_PIECE_LEVEL_KEY, AllayPetPlayerData.GEM_PIECE_SKILL_NAME,
                    AllaySkills.GEM_PIECE_LEVEL_DESCRIPTION));
}
