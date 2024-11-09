package fun.wraq.commands.changeable;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.common.Compute;
import fun.wraq.common.util.StringUtils;
import fun.wraq.render.gui.villagerTrade.MyVillagerData;
import fun.wraq.render.gui.villagerTrade.TradeList;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class VillagerSummonCommand implements Command<CommandSourceStack> {
    public static VillagerSummonCommand instance = new VillagerSummonCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        String type = StringArgumentType.getString(context, "type").toLowerCase(Locale.ROOT);
        Level level = player.level();
        if (TradeList.tradeContent.isEmpty()) TradeList.setTradeContent();
        if (player.isCreative()) {
            double[] VillagerPos = {0, 0, 0};
            Vec3 Pos = player.pick(5, 0, true).getLocation();
            VillagerPos[0] = Pos.x;
            VillagerPos[1] = Pos.y;
            VillagerPos[2] = Pos.z;
            VillagerType villagerType = villagerTypeMap.getOrDefault(type, VillagerType.PLAINS);
            VillagerProfession villagerProfession = villagerProfessionMap.getOrDefault(type, VillagerProfession.WEAPONSMITH);
            MutableComponent mutableComponent = StringUtils.VillagerNameMap.getOrDefault(type, Component.literal(""));
            if (MyVillagerData.villagerNameMap.containsKey(type)) {
                villagerType = MyVillagerData.villagerTypeMap.get(type);
                villagerProfession = MyVillagerData.villagerProfessionMap.get(type);
                mutableComponent = MyVillagerData.villagerNameMap.get(type);
            }
            if (!MyVillagerData.villagerNameMap.containsKey(type) && !StringUtils.VillagerNameMap.containsKey(type)) {
                Compute.sendFormatMSG(player, Component.literal("生成").withStyle(ChatFormatting.LIGHT_PURPLE),
                        Component.literal("请检查拼写是否正确。").withStyle(ChatFormatting.WHITE));
                return 0;
            }

            Villager villager = new Villager(EntityType.VILLAGER, level);
            VillagerData villagerData = new VillagerData(villagerType, villagerProfession, 5);
            villager.setVillagerData(villagerData);
            villager.moveTo(VillagerPos[0], VillagerPos[1], VillagerPos[2]);
            villager.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0);
            villager.setCustomName(mutableComponent);
            villager.setCustomNameVisible(true);
            villager.setInvulnerable(true);
            level.addFreshEntity(villager);
            player.sendSystemMessage(Component.literal("已生成 ").withStyle(ChatFormatting.WHITE).
                    append(mutableComponent));
        } else {
            Compute.sendFormatMSG(player, Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA),
                    Component.literal("此命令仅管理员可用。").withStyle(ChatFormatting.WHITE));
        }
        return 0;
    }

    Map<String, VillagerType> villagerTypeMap = new HashMap<>() {{
        put(StringUtils.NewVillagerName.Forest.toLowerCase(Locale.ROOT), VillagerType.JUNGLE);
        put(StringUtils.NewVillagerName.Lake.toLowerCase(Locale.ROOT), VillagerType.SWAMP);
        put(StringUtils.NewVillagerName.Volcano.toLowerCase(Locale.ROOT), VillagerType.DESERT);
        put(StringUtils.NewVillagerName.Mine.toLowerCase(Locale.ROOT), VillagerType.SAVANNA);
        put(StringUtils.NewVillagerName.Snow.toLowerCase(Locale.ROOT), VillagerType.SNOW);
        put(StringUtils.NewVillagerName.Sky.toLowerCase(Locale.ROOT), VillagerType.SNOW);
        put(StringUtils.NewVillagerName.Wither.toLowerCase(Locale.ROOT), VillagerType.SAVANNA);
        put(StringUtils.NewVillagerName.Piglin.toLowerCase(Locale.ROOT), VillagerType.SAVANNA);
        put(StringUtils.NewVillagerName.Skeleton.toLowerCase(Locale.ROOT), VillagerType.SAVANNA);
        put(StringUtils.NewVillagerName.Magma.toLowerCase(Locale.ROOT), VillagerType.SAVANNA);
        put(StringUtils.NewVillagerName.Ice.toLowerCase(Locale.ROOT), VillagerType.SNOW);
        put(StringUtils.NewVillagerName.NetherRune.toLowerCase(Locale.ROOT), VillagerType.SAVANNA);
        put(StringUtils.NewVillagerName.NetherPower.toLowerCase(Locale.ROOT), VillagerType.SAVANNA);
        put(StringUtils.NewVillagerName.NetherArmor.toLowerCase(Locale.ROOT), VillagerType.SAVANNA);
        put(StringUtils.NewVillagerName.NetherGem.toLowerCase(Locale.ROOT), VillagerType.SAVANNA);
        put(StringUtils.NewVillagerName.NetherBow.toLowerCase(Locale.ROOT), VillagerType.SAVANNA);
        put(StringUtils.NewVillagerName.NetherSwordModel.toLowerCase(Locale.ROOT), VillagerType.SAVANNA);
        put(StringUtils.NewVillagerName.NetherWeapon.toLowerCase(Locale.ROOT), VillagerType.SAVANNA);
        put(StringUtils.NewVillagerName.Ruby.toLowerCase(Locale.ROOT), VillagerType.SAVANNA);
        put(StringUtils.NewVillagerName.Nature.toLowerCase(Locale.ROOT), VillagerType.SNOW);
        put(StringUtils.NewVillagerName.GoldSmith.toLowerCase(Locale.ROOT), VillagerType.SNOW);
        put(StringUtils.NewVillagerName.Blood.toLowerCase(Locale.ROOT), VillagerType.SAVANNA);
        put(StringUtils.NewVillagerName.Taboo.toLowerCase(Locale.ROOT), VillagerType.SAVANNA);
        put(StringUtils.NewVillagerName.Earth.toLowerCase(Locale.ROOT), VillagerType.SWAMP);
        put(StringUtils.NewVillagerName.ManaBook.toLowerCase(Locale.ROOT), VillagerType.SWAMP);
        put(StringUtils.NewVillagerName.Slime.toLowerCase(Locale.ROOT), VillagerType.JUNGLE);
        put(StringUtils.NewVillagerName.Castle.toLowerCase(Locale.ROOT), VillagerType.SAVANNA);
        put(StringUtils.NewVillagerName.CastleCommon.toLowerCase(Locale.ROOT), VillagerType.SAVANNA);
        put(StringUtils.NewVillagerName.LakeRune.toLowerCase(Locale.ROOT), VillagerType.SWAMP);
        put(StringUtils.NewVillagerName.QingMing.toLowerCase(Locale.ROOT), VillagerType.JUNGLE);
        put(StringUtils.NewVillagerName.LifeElement.toLowerCase(Locale.ROOT), VillagerType.JUNGLE);
        put(StringUtils.NewVillagerName.WindElement.toLowerCase(Locale.ROOT), VillagerType.PLAINS);
        put(StringUtils.NewVillagerName.StoneElement.toLowerCase(Locale.ROOT), VillagerType.SAVANNA);
        put(StringUtils.NewVillagerName.FireElement.toLowerCase(Locale.ROOT), VillagerType.SAVANNA);
        put(StringUtils.NewVillagerName.LightningElement.toLowerCase(Locale.ROOT), VillagerType.TAIGA);
        put(StringUtils.NewVillagerName.WaterElement.toLowerCase(Locale.ROOT), VillagerType.SWAMP);
        put(StringUtils.NewVillagerName.IceElement.toLowerCase(Locale.ROOT), VillagerType.SNOW);
        put(StringUtils.NewVillagerName.Food.toLowerCase(Locale.ROOT), VillagerType.PLAINS);
        put(StringUtils.NewVillagerName.RoseGoldStore.toLowerCase(Locale.ROOT), VillagerType.PLAINS);
        put(StringUtils.NewVillagerName.Pearl.toLowerCase(Locale.ROOT), VillagerType.SNOW);
    }};
    Map<String, VillagerProfession> villagerProfessionMap = new HashMap<>() {{
        put(StringUtils.NewVillagerName.Ice.toLowerCase(Locale.ROOT), VillagerProfession.LEATHERWORKER);
        put(StringUtils.NewVillagerName.PurpleIron.toLowerCase(Locale.ROOT), VillagerProfession.MASON);
        put(StringUtils.NewVillagerName.Mine.toLowerCase(Locale.ROOT), VillagerProfession.MASON);
        put(StringUtils.NewVillagerName.WorldSoul.toLowerCase(Locale.ROOT), VillagerProfession.LIBRARIAN);
        put(StringUtils.NewVillagerName.Forge.toLowerCase(Locale.ROOT), VillagerProfession.WEAPONSMITH);
        put(StringUtils.NewVillagerName.Currency.toLowerCase(Locale.ROOT), VillagerProfession.CARTOGRAPHER);
        put(StringUtils.NewVillagerName.NetherWeapon.toLowerCase(Locale.ROOT), VillagerProfession.WEAPONSMITH);
        put(StringUtils.NewVillagerName.NetherBow.toLowerCase(Locale.ROOT), VillagerProfession.WEAPONSMITH);
        put(StringUtils.NewVillagerName.SoulToGoldCoin.toLowerCase(Locale.ROOT), VillagerProfession.CARTOGRAPHER);
        put(StringUtils.NewVillagerName.BossCore.toLowerCase(Locale.ROOT), VillagerProfession.CARTOGRAPHER);
        put(StringUtils.NewVillagerName.Main1Gems.toLowerCase(Locale.ROOT), VillagerProfession.CARTOGRAPHER);
        put(StringUtils.NewVillagerName.Cord.toLowerCase(Locale.ROOT), VillagerProfession.CARTOGRAPHER);
        put(StringUtils.NewVillagerName.T1Equip.toLowerCase(Locale.ROOT), VillagerProfession.CARTOGRAPHER);
        put(StringUtils.NewVillagerName.PlainRune.toLowerCase(Locale.ROOT), VillagerProfession.LIBRARIAN);
        put(StringUtils.NewVillagerName.ForestRune.toLowerCase(Locale.ROOT), VillagerProfession.LIBRARIAN);
        put(StringUtils.NewVillagerName.VolcanoRune.toLowerCase(Locale.ROOT), VillagerProfession.LIBRARIAN);
        put(StringUtils.NewVillagerName.ManaRune.toLowerCase(Locale.ROOT), VillagerProfession.LIBRARIAN);
        put(StringUtils.NewVillagerName.SnowRune.toLowerCase(Locale.ROOT), VillagerProfession.LIBRARIAN);
        put(StringUtils.NewVillagerName.NetherRune.toLowerCase(Locale.ROOT), VillagerProfession.LIBRARIAN);
        put(StringUtils.NewVillagerName.Spider.toLowerCase(Locale.ROOT), VillagerProfession.LIBRARIAN);
        put(StringUtils.NewVillagerName.Brewing.toLowerCase(Locale.ROOT), VillagerProfession.LIBRARIAN);
        put(StringUtils.NewVillagerName.Nature.toLowerCase(Locale.ROOT), VillagerProfession.FARMER);
        put(StringUtils.NewVillagerName.Pickaxe.toLowerCase(Locale.ROOT), VillagerProfession.TOOLSMITH);
        put(StringUtils.NewVillagerName.Axe.toLowerCase(Locale.ROOT), VillagerProfession.TOOLSMITH);
        put(StringUtils.NewVillagerName.Field.toLowerCase(Locale.ROOT), VillagerProfession.FARMER);
        put(StringUtils.NewVillagerName.Spring.toLowerCase(Locale.ROOT), VillagerProfession.CARTOGRAPHER);
        put(StringUtils.NewVillagerName.QingMing.toLowerCase(Locale.ROOT), VillagerProfession.CARTOGRAPHER);
        put(StringUtils.NewVillagerName.GoldSmith.toLowerCase(Locale.ROOT), VillagerProfession.CARTOGRAPHER);
        put(StringUtils.NewVillagerName.Blood.toLowerCase(Locale.ROOT), VillagerProfession.LEATHERWORKER);
        put(StringUtils.NewVillagerName.Taboo.toLowerCase(Locale.ROOT), VillagerProfession.LEATHERWORKER);
        put(StringUtils.NewVillagerName.Earth.toLowerCase(Locale.ROOT), VillagerProfession.LEATHERWORKER);
        put(StringUtils.NewVillagerName.ManaBook.toLowerCase(Locale.ROOT), VillagerProfession.LIBRARIAN);
        put(StringUtils.NewVillagerName.Slime.toLowerCase(Locale.ROOT), VillagerProfession.LEATHERWORKER);
        put(StringUtils.NewVillagerName.Parkour.toLowerCase(Locale.ROOT), VillagerProfession.CARTOGRAPHER);
        put(StringUtils.NewVillagerName.Castle.toLowerCase(Locale.ROOT), VillagerProfession.CARTOGRAPHER);
        put(StringUtils.NewVillagerName.CastleCommon.toLowerCase(Locale.ROOT), VillagerProfession.WEAPONSMITH);
        put(StringUtils.NewVillagerName.SkyGemStore.toLowerCase(Locale.ROOT), VillagerProfession.CARTOGRAPHER);
        put(StringUtils.NewVillagerName.LakeRune.toLowerCase(Locale.ROOT), VillagerProfession.LIBRARIAN);
        put(StringUtils.NewVillagerName.LifeElement.toLowerCase(Locale.ROOT), VillagerProfession.LIBRARIAN);
        put(StringUtils.NewVillagerName.WindElement.toLowerCase(Locale.ROOT), VillagerProfession.LIBRARIAN);
        put(StringUtils.NewVillagerName.StoneElement.toLowerCase(Locale.ROOT), VillagerProfession.LIBRARIAN);
        put(StringUtils.NewVillagerName.FireElement.toLowerCase(Locale.ROOT), VillagerProfession.LIBRARIAN);
        put(StringUtils.NewVillagerName.LightningElement.toLowerCase(Locale.ROOT), VillagerProfession.LIBRARIAN);
        put(StringUtils.NewVillagerName.WaterElement.toLowerCase(Locale.ROOT), VillagerProfession.LIBRARIAN);
        put(StringUtils.NewVillagerName.IceElement.toLowerCase(Locale.ROOT), VillagerProfession.LIBRARIAN);
        put(StringUtils.NewVillagerName.Food.toLowerCase(Locale.ROOT), VillagerProfession.BUTCHER);
        put(StringUtils.NewVillagerName.RoseGoldStore.toLowerCase(Locale.ROOT), VillagerProfession.CARTOGRAPHER);
        put(StringUtils.NewVillagerName.LabourDayStore.toLowerCase(Locale.ROOT), VillagerProfession.FARMER);
        put(StringUtils.NewVillagerName.BackPack.toLowerCase(Locale.ROOT), VillagerProfession.LEATHERWORKER);
        put(StringUtils.NewVillagerName.Pearl.toLowerCase(Locale.ROOT), VillagerProfession.LIBRARIAN);
    }};
}
