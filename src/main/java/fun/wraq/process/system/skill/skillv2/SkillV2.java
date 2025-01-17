package fun.wraq.process.system.skill.skillv2;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.util.Utils;
import fun.wraq.networking.ModNetworking;
import fun.wraq.process.system.skill.skillv2.bow.*;
import fun.wraq.process.system.skill.skillv2.mana.*;
import fun.wraq.process.system.skill.skillv2.network.SkillV2CooldownS2CPacket;
import fun.wraq.process.system.skill.skillv2.network.SkillV2InfoS2CPacket;
import fun.wraq.process.system.skill.skillv2.sword.*;
import fun.wraq.render.hud.Mana;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class SkillV2 {

    protected final int cooldownTick;
    protected final int manaCost;
    protected final int professionType; // -1 - 未选择 0 - 战士 1 - 弓箭手 2 - 法师
    protected final int skillType; // 0 - 被动 1 - 基础技能1 2 - 基础技能2 3 - 基础技能3 4 - 终极技能
    protected final int serial; // 该技能的序号

    protected SkillV2(int cooldownTick, int manaCost, int professionType, int skillType, int serial) {
        this.cooldownTick = cooldownTick;
        this.manaCost = manaCost;
        this.professionType = professionType;
        this.skillType = skillType;
        this.serial = serial;
    }

    public int getSkillType() {
        return skillType;
    }

    protected abstract boolean canUpgrade(Player player);

    protected abstract List<Component> getUpgradeConditionDescription();

    protected abstract void upgradeOperation(Player player);

    protected abstract boolean canRelease(Player player);

    protected abstract List<Component> getReleaseConditionDescription();

    protected abstract void releaseOperation(Player player);

    protected abstract List<Component> getSkillDescription(int level);

    public static int clientProfessionType = 0;
    public static List<Integer> clientSwordSkillLevel = new ArrayList<>();
    public static List<Integer> clientBowSkillLevel = new ArrayList<>();
    public static List<Integer> clientManaSkillLevel = new ArrayList<>();
    public static List<Integer> clientCurrentSkillSerial = new ArrayList<>();
    public static Map<Integer, Integer> clientLeftCooldownTick = new HashMap<>();
    public static Map<Integer, Integer> clientOriginCooldownTick = new HashMap<>();
    public static boolean screenRefreshFlag = false;

    public static List<SkillV2> swordSkillV2 = new ArrayList<>();
    public static List<SkillV2> bowSkillV2 = new ArrayList<>();
    public static List<SkillV2> manaSkillV2 = new ArrayList<>();

    public static List<SkillV2> getSwordSkillV2() {
        if (swordSkillV2.isEmpty()) {
            swordSkillV2.add(new SwordNewSkillPassive0(0, 0, 0, 0, 0));
            swordSkillV2.add(new SwordNewSkillBase1_0(200, 50, 0, 1, 0));
            swordSkillV2.add(new SwordNewSkillBase1_1(200, 50, 0, 1, 1));
            swordSkillV2.add(new SwordNewSkillBase2_0(200, 50, 0, 2, 0));
            swordSkillV2.add(new SwordNewSkillBase3_0(200, 50, 0, 3, 0));
            swordSkillV2.add(new SwordNewSkillFinal0(200, 50, 0, 4, 0));
        }
        return swordSkillV2;
    }

    public static List<SkillV2> getBowSkillV2() {
        if (bowSkillV2.isEmpty()) {
            bowSkillV2.add(new BowNewSkillPassive0(0, 0, 1, 0, 0));
            bowSkillV2.add(new BowNewSkillBase1_0(200, 50, 1, 1, 0));
            bowSkillV2.add(new BowNewSkillBase2_0(200, 50, 1, 2, 0));
            bowSkillV2.add(new BowNewSkillBase3_0(200, 50, 1, 3, 0));
            bowSkillV2.add(new BowNewSkillFinal0(200, 50, 1, 4, 0));
        }
        return bowSkillV2;
    }

    public static List<SkillV2> getManaSkillV2() {
        if (manaSkillV2.isEmpty()) {
            manaSkillV2.add(new ManaNewSkillPassive0(0, 0, 2, 0, 0));
            manaSkillV2.add(new ManaNewSkillBase1_0(200, 50, 2, 1, 0));
            manaSkillV2.add(new ManaNewSkillBase2_0(200, 50, 2, 2, 0));
            manaSkillV2.add(new ManaNewSkillBase3_0(200, 50, 2, 3, 0));
            manaSkillV2.add(new ManaNewSkillFinal0(200, 50, 2, 4, 0));
        }
        return manaSkillV2;
    }

    public static List<SkillV2> getSkillV2ListByProfession(int profession) {
        switch (profession) {
            case 1 -> {
                return getBowSkillV2();
            }
            case 2 -> {
                return getManaSkillV2();
            }
            default -> {
                return getSwordSkillV2();
            }
        }
    }

    public static Map<String, Map<SkillV2, Integer>> playerSkillV2AllowReleaseTickMap = new HashMap<>();

    public static Map<String, SkillV2> playerPassiveSkillMap = new HashMap<>();
    public static Map<String, Integer> playerPassiveSkillLevelMap = new HashMap<>();
    public static Map<String, SkillV2> playerSkill1Map = new HashMap<>();
    public static Map<String, Integer> playerSkill1LevelMap = new HashMap<>();
    public static Map<String, SkillV2> playerSkill2Map = new HashMap<>();
    public static Map<String, Integer> playerSkill2LevelMap = new HashMap<>();
    public static Map<String, SkillV2> playerSkill3Map = new HashMap<>();
    public static Map<String, Integer> playerSkill3LevelMap = new HashMap<>();
    public static Map<String, SkillV2> playerFinalSkillMap = new HashMap<>();
    public static Map<String, Integer> playerFinalSkillLevelMap = new HashMap<>();

    public static Map<String, SkillV2> getSkillMapBySkillType(int type) {
        switch (type) {
            default -> {
                return playerPassiveSkillMap;
            }
            case 1 -> {
                return playerSkill1Map;
            }
            case 2 -> {
                return playerSkill2Map;
            }
            case 3 -> {
                return playerSkill3Map;
            }
            case 4 -> {
                return playerFinalSkillMap;
            }
        }
    }

    public static Map<String, Integer> getSkillLevelMapBySkillType(int type) {
        switch (type) {
            default -> {
                return playerPassiveSkillLevelMap;
            }
            case 1 -> {
                return playerSkill1LevelMap;
            }
            case 2 -> {
                return playerSkill2LevelMap;
            }
            case 3 -> {
                return playerSkill3LevelMap;
            }
            case 4 -> {
                return playerFinalSkillLevelMap;
            }
        }
    }

    private static final String SKILL_V2_DATA_KEY = "SkillV2Data";

    private static CompoundTag getSkillV2Data(Player player) {
        return Compute.getPlayerSpecificKeyCompoundTagData(player, SKILL_V2_DATA_KEY);
    }

    private static final String SWORD_SKILL_DATA_KEY = "SwordSkillData";
    private static final String BOW_SKILL_DATA_KEY = "BowSkillData";
    private static final String MANA_SKILL_DATA_KEY = "ManaSkillData";
    private static final List<String> PROFESSION_DATA_KEYS = List.of(
            SWORD_SKILL_DATA_KEY, BOW_SKILL_DATA_KEY, MANA_SKILL_DATA_KEY
    );

    public static CompoundTag getProfessionSkillData(Player player, int type) {
        if (type == -1) {
            return new CompoundTag();
        }

        String dataKey = PROFESSION_DATA_KEYS.get(type);
        CompoundTag data = getSkillV2Data(player);
        if (!data.contains(dataKey)) {
            data.put(dataKey, new CompoundTag());
        }
        return data.getCompound(dataKey);
    }

    public static int getProfessionSkillSerial(Player player, int profession, int skillType) {
        CompoundTag data = getProfessionSkillData(player, profession);
        return data.getInt(getAllSkillSerialDataKey().get(skillType));
    }

    public static List<Integer> getProfessionSkillSerial(Player player, int profession) {
        CompoundTag data = getProfessionSkillData(player, profession);
        List<Integer> skillSerials = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            skillSerials.add(data.getInt(getAllSkillSerialDataKey().get(i)));
        }
        return skillSerials;
    }

    public static void setProfessionSkillSerial(Player player, int profession, int skillType, int serial) {
        CompoundTag data = getProfessionSkillData(player, profession);
        data.putInt(getAllSkillSerialDataKey().get(skillType), serial);
    }

    public static int getProfessionSkillLevel(Player player, int profession, int skillType, int serial) {
        CompoundTag data = getProfessionSkillData(player, profession);
        int dataLevel = data.getInt(getAllSkillLevelDataKey().get(skillType) + "_" + serial);
        return serial == 0 ? Math.max(1, dataLevel) : dataLevel;
    }

    public static void setProfessionSkillLevel(Player player, int profession, int skillType, int serial, int skillLevel) {
        CompoundTag data = getProfessionSkillData(player, profession);
        data.putInt(getAllSkillLevelDataKey().get(skillType) + "_" + serial, skillLevel);
    }

    public static int getPlayerSkillLevelBySkillV2(Player player, SkillV2 skillV2) {
        return getProfessionSkillLevel(player, skillV2.professionType, skillV2.skillType, skillV2.serial);
    }

    private static final String PROFESSION_TYPE = "ProfessionType";

    public static int getProfessionType(Player player) {
        if (!getSkillV2Data(player).contains(PROFESSION_TYPE)) {
            return -1;
        }
        return getSkillV2Data(player).getInt(PROFESSION_TYPE);
    }

    public static void setProfessionType(Player player, int type) {
        getSkillV2Data(player).putInt(PROFESSION_TYPE, type);
        resetSkillV2Data(player);
    }

    public static final String PASSIVE_SKILL_SERIAL = "PassiveSkillSerial";
    public static final String BASE_SKILL_1_SERIAL = "BaseSkill1Serial";
    public static final String BASE_SKILL_2_SERIAL = "BaseSkill2Serial";
    public static final String BASE_SKILL_3_SERIAL = "BaseSkill3Serial";
    public static final String FINAL_SKILL_SERIAL = "FinalSkillSerial";

    public static List<String> getAllSkillSerialDataKey() {
        return List.of(
                PASSIVE_SKILL_SERIAL,
                BASE_SKILL_1_SERIAL,
                BASE_SKILL_2_SERIAL,
                BASE_SKILL_3_SERIAL,
                FINAL_SKILL_SERIAL
        );
    }

    public static final String BASE_SKILL_1_LEVEL = "BaseSkill1Level";
    public static final String BASE_SKILL_2_LEVEL = "BaseSkill2Level";
    public static final String BASE_SKILL_3_LEVEL = "BaseSkill3Level";
    public static final String FINAL_SKILL_LEVEL = "FinalSkillLevel";
    public static final String PASSIVE_SKILL_LEVEL = "PassiveSkillLevel";

    public static List<String> getAllSkillLevelDataKey() {
        return List.of(
                PASSIVE_SKILL_LEVEL,
                BASE_SKILL_1_LEVEL,
                BASE_SKILL_2_LEVEL,
                BASE_SKILL_3_LEVEL,
                FINAL_SKILL_LEVEL
        );
    }

    @Nullable
    public static SkillV2 getSkillV2(int professionType, int skillType, int serial) {
        return getSkillV2ListByProfession(professionType)
                .stream().filter(skillV2 -> skillV2.skillType == skillType && skillV2.serial == serial)
                .findFirst().orElse(null);
    }

    public static void resetSkillV2Data(Player player) {
        String name = player.getName().getString();
        int profession = getProfessionType(player);
        if (profession == -1) {
            return;
        }
        for (int i = 0; i < 5; i++) {
            int skillSerial = getProfessionSkillSerial(player, profession, i);
            int skillLevel = getProfessionSkillLevel(player, profession, i, skillSerial);
            getSkillMapBySkillType(i).put(name, getSkillV2(profession, i, skillSerial));
            getSkillLevelMapBySkillType(i).put(name, skillLevel);
        }
        sendInfoToClient(player);
    }

    public static void onPlayerTryToReleaseSkillBySkillType(Player player, int skillType) {
        String name = player.getName().getString();
        Map<String, SkillV2> skillV2Map = getSkillMapBySkillType(skillType);
        if (!skillV2Map.containsKey(name)) {
            return;
        }
        SkillV2 skillV2 = getSkillMapBySkillType(skillType).get(name);
        skillV2.onPlayerTryToRelease(player);
    }

    public static void onPlayerTryToEquipSkill(Player player, int professionType, int skillType, int serial) {
        List<SkillV2> skillV2List = getSkillV2ListByProfession(professionType);
        skillV2List.stream().filter(skillV2 -> {
            return skillV2.skillType == skillType && skillV2.serial == serial;
        }).findAny().ifPresent(skillV2 -> {
            skillV2.onPlayerTryToEquip(player);
        });
        sendInfoToClient(player);
    }

    public static void onPlayerTryToUpgradeSkillByTypeAndSerial(Player player,
                                                                int professionType, int skillType, int serial) {
        List<SkillV2> skillV2List = getSkillV2ListByProfession(professionType);
        skillV2List.stream().filter(skillV2 -> {
            return skillV2.skillType == skillType && skillV2.serial == serial;
        }).findAny().ifPresent(skillV2 -> {
            skillV2.onPlayerTryToUpgrade(player);
        });
        sendInfoToClient(player);
    }

    protected int getPlayerSkillLevel(Player player) {
        return getProfessionSkillLevel(player, professionType, skillType, serial);
    }

    protected void onPlayerTryToEquip(Player player) {
        int professionType = getProfessionType(player);
        if (professionType == -1) {
            sendMSG(player, Te.s("请先选择职业"));
            return;
        }

        if (professionType != this.professionType) {
            sendMSG(player, Te.s("这不是当前职业所能使用的技能"));
            return;
        }

        String name = player.getName().getString();
        Map<String, SkillV2> skillV2Map = getSkillMapBySkillType(this.skillType);
        skillV2Map.put(name, this);
        int skillLevel = getPlayerSkillLevel(player);
        getSkillLevelMapBySkillType(this.skillType).put(name, skillLevel);
        sendMSG(player, Te.s("已装备技能"));
        setProfessionSkillSerial(player, professionType, skillType, serial);
        setProfessionSkillLevel(player, professionType, skillType, serial, skillLevel);
    }

    protected void onPlayerTryToUpgrade(Player player) {
        if (!canUpgrade(player)) {
            sendMSG(player, Te.s("未达到升级条件"));
            return;
        }

        setProfessionSkillLevel(player, this.professionType, this.skillType, this.serial,
                getPlayerSkillLevel(player) + 1);
        upgradeOperation(player);
        sendMSG(player, Te.s("升级成功!"));
    }

    protected void onPlayerTryToRelease(Player player) {
        String name = player.getName().getString();
        if (!playerSkillV2AllowReleaseTickMap.containsKey(name)) {
            playerSkillV2AllowReleaseTickMap.put(name, new HashMap<>());
        }
        Map<SkillV2, Integer> skillV2AllowReleaseTickMap = playerSkillV2AllowReleaseTickMap.get(name);
        if (canRelease(player)
                && skillV2AllowReleaseTickMap.getOrDefault(this, 0) <= Tick.get()
                && Mana.getPlayerCurrentManaNum(player) >= manaCost) {
            int afterDecreaseCooldownTick = (int) (cooldownTick * (1 - PlayerAttributes.coolDownDecrease(player)));
            skillV2AllowReleaseTickMap.put(this, Tick.get() + afterDecreaseCooldownTick);
            ModNetworking.sendToClient(new SkillV2CooldownS2CPacket(skillType, afterDecreaseCooldownTick,
                    afterDecreaseCooldownTick), player);
            Mana.addOrCostPlayerMana(player, -manaCost);
            releaseOperation(player);
        }
    }

    public String getTexture0Url() {
        String professionWord = List.of("sword", "bow", "mana").get(professionType);
        return "skills/v2/" + professionWord + "/" + professionWord + skillType + "_" + serial + "_0";
    }

    public String getTexture1Url() {
        String professionWord = List.of("sword", "bow", "mana").get(professionType);
        return "skills/v2/" + professionWord + "/" + professionWord + skillType + "_" + serial;
    }

    public ResourceLocation getTexture0() {
        return new ResourceLocation(Utils.MOD_ID, "textures/" + getTexture0Url() + ".png");
    }

    public ResourceLocation getTexture1() {
        return new ResourceLocation(Utils.MOD_ID, "textures/" + getTexture1Url() + ".png");
    }

    @Nullable
    public static SkillV2 getPlayerCurrentSkillByType(Player player, int type) {
        return getSkillMapBySkillType(type).getOrDefault(player.getName().getString(), null);
    }

    public static void sendMSG(Player player, Component content) {
        Compute.sendFormatMSG(player, Te.s("技能", CustomStyle.styleOfWorld), content);
    }

    public static void sendInfoToClient(Player player) {
        ModNetworking.sendToClient(new SkillV2InfoS2CPacket(getProfessionType(player),
                        getSwordSkillV2().stream().map(skillV2 -> getPlayerSkillLevelBySkillV2(player, skillV2)).toList(),
                        getBowSkillV2().stream().map(skillV2 -> getPlayerSkillLevelBySkillV2(player, skillV2)).toList(),
                        getManaSkillV2().stream().map(skillV2 -> getPlayerSkillLevelBySkillV2(player, skillV2)).toList(),
                        getProfessionSkillSerial(player, getProfessionType(player))),
                player);
    }

    public static void clientTick() {
        for (int i = 1 ; i <= 4 ; i ++) {
            clientLeftCooldownTick.compute(i, (k, v) -> v == null ? 0 : Math.max(0, v - 1));
        }
    }
}
