package fun.wraq.common.attribute;

import fun.wraq.Items.DevelopmentTools.equip.ManageEquip;
import fun.wraq.Items.DevelopmentTools.equip.OpsAttributes;
import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.equip.WraqPickaxe;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.impl.inslot.InCuriosOrEquipSlotAttributesModify;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.customized.uniform.attack.AttackCurios0;
import fun.wraq.customized.uniform.attack.AttackCurios1;
import fun.wraq.customized.uniform.attack.AttackCurios2;
import fun.wraq.customized.uniform.bow.BowCurios0;
import fun.wraq.customized.uniform.bow.BowCurios2;
import fun.wraq.customized.uniform.mana.ManaCurios0;
import fun.wraq.customized.uniform.mana.ManaCurios2;
import fun.wraq.events.mob.loot.C5LootItems;
import fun.wraq.process.func.ChangedAttributesModifier;
import fun.wraq.process.func.StableAttributesModifier;
import fun.wraq.process.func.StableTierAttributeModifier;
import fun.wraq.process.func.plan.PlanPlayer;
import fun.wraq.process.func.suit.SArmorAttribute;
import fun.wraq.process.func.suit.SuitCount;
import fun.wraq.process.system.element.equipAndCurios.lifeElement.LifeElementBow;
import fun.wraq.process.system.element.equipAndCurios.lifeElement.LifeElementSceptre;
import fun.wraq.process.system.element.equipAndCurios.lifeElement.LifeElementSword;
import fun.wraq.process.system.forge.ForgeEquipUtils;
import fun.wraq.process.system.spur.events.MineSpur;
import fun.wraq.process.system.tower.TowerMob;
import fun.wraq.render.mobEffects.ModEffects;
import fun.wraq.series.gems.GemAttributes;
import fun.wraq.series.instance.series.castle.CastleAttackArmor;
import fun.wraq.series.instance.series.castle.CastleManaArmor;
import fun.wraq.series.instance.series.castle.CastleSwiftArmor;
import fun.wraq.series.instance.series.devil.DevilAttackArmor;
import fun.wraq.series.instance.series.moon.Equip.MoonBook;
import fun.wraq.series.instance.series.moon.Equip.MoonKnife;
import fun.wraq.series.instance.series.moon.Equip.MoonShield;
import fun.wraq.series.newrunes.chapter1.ForestNewRune;
import fun.wraq.series.newrunes.chapter1.PlainNewRune;
import fun.wraq.series.newrunes.chapter2.SkyNewRune;
import fun.wraq.series.newrunes.chapter6.CastleNewRune;
import fun.wraq.series.overworld.chapter1.Mine.MineShield;
import fun.wraq.series.overworld.chapter1.forest.armor.ForestArmorBoots;
import fun.wraq.series.overworld.chapter1.forest.armor.ForestArmorChest;
import fun.wraq.series.overworld.chapter1.forest.armor.ForestArmorHelmet;
import fun.wraq.series.overworld.chapter1.forest.armor.ForestArmorLeggings;
import fun.wraq.series.overworld.chapter1.plain.armor.PlainArmorHelmet;
import fun.wraq.series.overworld.chapter1.volcano.armor.VolcanoArmorHelmet;
import fun.wraq.series.overworld.chapter1.waterSystem.equip.armor.LakeArmorHelmet;
import fun.wraq.series.overworld.chapter2.evoker.Crest.ManaCrestAttributes;
import fun.wraq.series.overworld.chapter2.manaArmor.LifeMana.LifeManaArmor;
import fun.wraq.series.overworld.sakuraSeries.EarthMana.EarthPower;
import fun.wraq.series.worldsoul.SoulEquipAttribute;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class PlayerAttributes {

    public static Map<Player, Map<Map<Item, Double>, Double>> playerAttributeCache = new ConcurrentHashMap<>();
    public static Map<Player, Map<Map<Item, Double>, Integer>> computeAttributeTick = new ConcurrentHashMap<>();

    public static boolean canGetFromCache(Player player, Map<Item, Double> attribute) {
        if (player.getMainHandItem().getItem() instanceof ManageEquip) return true;
        // 初始化
        int tick = Tick.get();
        if (!playerAttributeCache.containsKey(player)) {
            playerAttributeCache.put(player, new ConcurrentHashMap<>());
        }
        if (!computeAttributeTick.containsKey(player)) {
            computeAttributeTick.put(player, new ConcurrentHashMap<>());
        }
        if (!computeAttributeTick.get(player).containsKey(attribute)) {
            computeAttributeTick.get(player).put(attribute, 0);
        }

        Map<Map<Item, Double>, Integer> tickMap = computeAttributeTick.get(player);
        return tickMap.getOrDefault(attribute, 0) == tick;
    }

    public static double getFromCache(Player player, Map<Item, Double> attribute) {
        if (player.getMainHandItem().getItem() instanceof ManageEquip) {
            return OpsAttributes.getValue(attribute, player);
        }
        Map<Map<Item, Double>, Double> attributeMap = playerAttributeCache.get(player);
        return attributeMap.get(attribute);
    }

    public static void writeToCache(Player player, Map<Item, Double> attribute, double value) {
        Map<Map<Item, Double>, Integer> tickMap = computeAttributeTick.get(player);
        Map<Map<Item, Double>, Double> attributeMap = playerAttributeCache.get(player);
        tickMap.put(attribute, Tick.get());
        attributeMap.put(attribute, value);
    }

    public static double getMainDamage(Player player) {
        double attackDamage = attackDamage(player);
        double manaDamage = manaDamage(player);
        return Math.max(attackDamage * 2, manaDamage);
    }

    public static double attackDamage(Player player) {
        if (canGetFromCache(player, Utils.attackDamage)) {
            return getFromCache(player, Utils.attackDamage);
        }
        int TickCount = player.getServer().getTickCount();
        double baseAttackDamage = player.experienceLevel;
        double exDamage = 0;
        Item boots = player.getItemBySlot(EquipmentSlot.FEET).getItem();
        Item leggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item chest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
        Item helmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        Item offhand = player.getItemInHand(InteractionHand.OFF_HAND).getItem();
        CompoundTag data = player.getPersistentData();
        CompoundTag mainHandItemTag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            mainHandItemTag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }
        CompoundTag helmetTag = player.getItemBySlot(EquipmentSlot.HEAD).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag chestTag = player.getItemBySlot(EquipmentSlot.CHEST).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag leggingsTag = player.getItemBySlot(EquipmentSlot.LEGS).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag bootsTag = player.getItemBySlot(EquipmentSlot.FEET).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag stackmainhandtag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            stackmainhandtag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }

        // 基础数值
        if (Utils.mainHandTag.containsKey(mainhand) && stackmainhandtag.contains("attackdamage"))
            baseAttackDamage += stackmainhandtag.getDouble("attackdamage");
        if (Utils.attackDamage.containsKey(boots))
            baseAttackDamage += ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.FEET), Utils.attackDamage);
        if (Utils.attackDamage.containsKey(leggings))
            baseAttackDamage += ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.LEGS), Utils.attackDamage);
        if (Utils.attackDamage.containsKey(chest))
            baseAttackDamage += ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.CHEST), Utils.attackDamage);
        if (Utils.attackDamage.containsKey(helmet))
            baseAttackDamage += ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.HEAD), Utils.attackDamage);

        baseAttackDamage += handleAllEquipRandomAttribute(player, StringUtils.RandomAttribute.attackDamage);

        if (Utils.mainHandTag.containsKey(mainhand) && Utils.attackDamage.containsKey(mainhand))
            baseAttackDamage += ForgeEquipUtils.getTraditionalEquipBaseValue(player.getMainHandItem(), Utils.attackDamage);
        if (Utils.offHandTag.containsKey(offhand) && Utils.attackDamage.containsKey(offhand))
            baseAttackDamage += ForgeEquipUtils.getTraditionalEquipBaseValue(player.getOffhandItem(), Utils.attackDamage);
        if (Utils.mainHandTag.containsKey(mainhand) && Utils.attackDamage.containsKey(mainhand) && mainHandItemTag.contains("Forging"))
            baseAttackDamage += Compute.forgingValue(mainHandItemTag, ForgeEquipUtils.getTraditionalEquipBaseValue(player.getMainHandItem(), Utils.attackDamage));

        // Fixed:防具攻击强化失效
        if (Utils.armorTag.containsKey(helmet) && Utils.attackDamage.containsKey(helmet) && helmetTag.contains("Forging"))
            baseAttackDamage += Compute.forgingValue(helmetTag, ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.HEAD), Utils.attackDamage));
        if (Utils.armorTag.containsKey(chest) && Utils.attackDamage.containsKey(chest) && chestTag.contains("Forging"))
            baseAttackDamage += Compute.forgingValue(chestTag, ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.CHEST), Utils.attackDamage));
        if (Utils.armorTag.containsKey(leggings) && Utils.attackDamage.containsKey(leggings) && leggingsTag.contains("Forging"))
            baseAttackDamage += Compute.forgingValue(leggingsTag, ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.LEGS), Utils.attackDamage));
        if (Utils.armorTag.containsKey(boots) && Utils.attackDamage.containsKey(boots) && bootsTag.contains("Forging"))
            baseAttackDamage += Compute.forgingValue(bootsTag, ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.FEET), Utils.attackDamage));

        // 计算线性等级强度属性数值
        baseAttackDamage += computeAllEquipSlotXpLevelAttributeValue(player, Utils.xpLevelAttackDamage, true);

        // 额外数值
        if (helmetTag.contains("newGems1")) exDamage += GemAttributes.gemsAttackDamage(helmetTag);
        if (chestTag.contains("newGems1")) exDamage += GemAttributes.gemsAttackDamage(chestTag);
        if (leggingsTag.contains("newGems1")) exDamage += GemAttributes.gemsAttackDamage(leggingsTag);
        if (bootsTag.contains("newGems1")) exDamage += GemAttributes.gemsAttackDamage(bootsTag);
        if (stackmainhandtag.contains("newGems1") && Utils.mainHandTag.containsKey(mainhand)) exDamage += GemAttributes.gemsAttackDamage(stackmainhandtag);
        if (SuitCount.getVolcanoSuitCount(player) >= 2) exDamage += baseAttackDamage * 0.15F;
        if (SuitCount.getObsiManaSuitCount(player) >= 4) exDamage += baseAttackDamage * 0.15F;
        if (data.contains("Sword")) exDamage += baseAttackDamage * (data.getInt("Sword") / 1000000.0d);
        if (data.contains("Barker")) exDamage += baseAttackDamage * ((data.getInt("Barker") / 100000.0d) * 0.05);
        if (data.contains("ManaSwordActive") && data.getInt("ManaSwordActive") > 0)
            exDamage += data.getInt("ManaSwordActive");

        if (SuitCount.getSkySuitCount(player) > 0) {
            if (player.getHealth() / player.getMaxHealth() > 0.8)
                exDamage += baseAttackDamage * 0.7 * Compute.SkySuitEffectRate(player);
            else if (player.getHealth() / player.getMaxHealth() > 0.4)
                exDamage += baseAttackDamage * 0.2 * Compute.SkySuitEffectRate(player);
        }
        if (player.getEffect(ModEffects.ATTACKUP.get()) != null && player.getEffect(ModEffects.ATTACKUP.get()).getAmplifier() == 0)
            exDamage += baseAttackDamage * 0.25 + 25;
        if (player.getEffect(ModEffects.ATTACKUP.get()) != null && player.getEffect(ModEffects.ATTACKUP.get()).getAmplifier() == 1)
            exDamage += baseAttackDamage * 0.40 + 40;

        if (Compute.getSwordSkillLevel(data, 2) > 0 && data.contains(StringUtils.SwordSkillNum.Skill2) && data.getInt(StringUtils.SwordSkillNum.Skill2) > TickCount) {
            exDamage += baseAttackDamage * Compute.getSwordSkillLevel(data, 2) * 0.02;
        } // 战斗渴望（击杀一个单位时，提升2%攻击力，持续10s）‘

        if (Compute.getBowSkillLevel(data, 2) > 0 && data.contains(StringUtils.BowSkillNum.Skill2) && data.getInt(StringUtils.BowSkillNum.Skill2) > TickCount) {
            exDamage += baseAttackDamage * Compute.getBowSkillLevel(data, 2) * 0.02;
        } // 狩猎渴望（击杀一个单位时，提升2%攻击力，持续10s）

        if (Compute.getSwordSkillLevel(data, 5) > 0 && data.contains(StringUtils.SwordSkillNum.Skill5) && data.getInt(StringUtils.SwordSkillNum.Skill5) > TickCount) {
            exDamage += baseAttackDamage * Compute.getSwordSkillLevel(data, 5) * 0.015;
        } // 剑术-狂暴（造成暴击后，提升1%攻击力，持续3s）

        if (Compute.getBowSkillLevel(data, 5) > 0 && data.contains(StringUtils.BowSkillNum.Skill5) && data.getInt(StringUtils.BowSkillNum.Skill5) > TickCount) {
            exDamage += baseAttackDamage * Compute.getBowSkillLevel(data, 5) * 0.01;
        } // 弓术-狂暴（造成暴击后，提升1%攻击力，持续5s）

        String name = player.getName().getString();
        if (Compute.getSwordSkillLevel(data, 6) > 0 && Utils.SwordSkill6Map.containsKey(name) && Utils.SwordSkill6Map.get(name).getTime() > TickCount) {
            exDamage += baseAttackDamage * Compute.getSwordSkillLevel(data, 6) * 0.003 * Utils.SwordSkill6Map.get(name).getCount();
        } // 完美（持续造成暴击，将提供至多30%攻击力，持续10s，在十次暴击后达最大值，在未造成暴击时重置层数）

        if (Compute.getBowSkillLevel(data, 6) > 0 && Utils.BowSkill6Map.containsKey(name) && Utils.BowSkill6Map.get(name).getTime() > TickCount) {
            exDamage += baseAttackDamage * Compute.getBowSkillLevel(data, 6) * 0.005 * Utils.BowSkill6Map.get(name).getCount();
        } // 完美（持续的命中目标的箭矢，将提供至多3%攻击力，持续10s，在十次命中后达最大值，在未命中时重置层数）

        if (Compute.getBowSkillLevel(data, 8) > 0 && Utils.bowTag.containsKey(mainhand)) {
            exDamage += baseAttackDamage * Compute.getBowSkillLevel(data, 8) * 0.02;
        } // 锻弦-力量（手持弓时，获得2%攻击力）

        int powerAbilityPoint = data.getInt(StringUtils.Ability.Power);
        if (data.contains(StringUtils.Ability.Power) && data.getInt(StringUtils.Ability.Power) > 0) {
            exDamage += powerAbilityPoint * 2;
        } // 能力

        if (leggings.equals(ModItems.MinePants.get()) && (Utils.OverWorldLevelIsNight || player.getY() < 63))
            exDamage += 50;
        // 矿工裤被动

        if (SuitCount.getMineSuitCount(player) >= 4) exDamage += baseAttackDamage * 0.3;

        if (stackmainhandtag.contains(StringUtils.SoulEquipForge) && (Utils.swordTag.containsKey(mainhand) || Utils.bowTag.containsKey(mainhand)))
            exDamage +=
                    stackmainhandtag.getInt(StringUtils.SoulEquipForge) * SoulEquipAttribute.ForgingAddition.AttackDamage;

        if (data.contains("GemSAttack")) exDamage += data.getDouble("GemSAttack");

        exDamage += SArmorAttribute.value(player, SArmorAttribute.volcanoPower);

        if (Utils.PlayerSpringRingAttackAttribute.containsKey(player) && Utils.PlayerSpringRingLevelRequire.get(player) <= player.experienceLevel) {
            exDamage += Utils.PlayerSpringRingAttackAttribute.get(player);
        }

        if (Utils.PlayerSpringHandAttackAttribute.containsKey(player) && Utils.PlayerSpringHandLevelRequire.get(player) <= player.experienceLevel) {
            exDamage += Utils.PlayerSpringHandAttackAttribute.get(player);
        }

        if (Utils.PlayerSpringBeltAttackAttribute.containsKey(player) && Utils.PlayerSpringBeltLevelRequire.get(player) <= player.experienceLevel) {
            exDamage += Utils.PlayerSpringBeltAttackAttribute.get(player);
        }

        if (Utils.PlayerFireWorkGunEffect.containsKey(player) && Utils.PlayerFireWorkGunEffect.get(player) > TickCount) {
            exDamage += baseAttackDamage * 0.1;
        } //新春礼炮

        if (Utils.SpringScaleTime.containsKey(player) && Utils.SpringScaleTime.get(player) > TickCount) {
            int SwordSkill = data.getInt(StringUtils.SkillArray[0]);
            int BowSkill = data.getInt(StringUtils.SkillArray[1]);
            int ManaSkill = data.getInt(StringUtils.SkillArray[2]);
            if (SwordSkill > Math.max(BowSkill, ManaSkill))
                exDamage += baseAttackDamage * (Utils.SpringScaleEffect.get(player) + 1) * 0.1;
        } //年兽鳞片

        if (mainhand.equals(ModItems.SoulSword.get())) {
            if (stackmainhandtag.contains(StringUtils.SoulSwordKillCount)) {
                exDamage += Math.min(256, stackmainhandtag.getInt(StringUtils.SoulSwordKillCount) / 100);
            }
        } // 本源被动

        if (Utils.MeteoriteAttackTimeMap.containsKey(player) && Utils.MeteoriteAttackTimeMap.get(player) > TickCount) {
            exDamage += baseAttackDamage * 0.1;
        }

        if (Utils.BloodManaCurios.containsKey(player) && Utils.BloodManaCurios.get(player)) exDamage += 100;

        if (Utils.DevilBloodManaCurios.containsKey(player) && Utils.DevilBloodManaCurios.get(player)) exDamage += 400;

        exDamage += DevilAttackArmor.DevilAttackArmorPassiveExDamage(player);
        exDamage += baseAttackDamage * EarthPower.PlayerDamageEnhance(player);

        // 新版饰品属性加成
        exDamage += Compute.CuriosAttribute.attributeValue(player, Utils.attackDamage, StringUtils.CuriosAttribute.attackDamage);
        exDamage += Compute.CuriosAttribute.attributeValue(player, Utils.xpLevelAttackDamage,
                StringUtils.CuriosAttribute.xpLevelAttackDamage) * player.experienceLevel;

        // 器灵属性加成
        exDamage += Compute.PassiveEquip.getAttribute(player, Utils.attackDamage);
        exDamage += Compute.PassiveEquip.getAttribute(player, Utils.xpLevelAttackDamage) * player.experienceLevel;

        exDamage += CastleAttackArmor.ExAttributeValue(player, CastleAttackArmor.ExAttackDamage);
        exDamage += CastleSwiftArmor.ExAttributeValue(player, CastleSwiftArmor.ExAttackDamage);
        exDamage += LifeElementSword.ExAttackDamage(player);
        exDamage += LifeElementBow.ExAttackDamage(player);
        exDamage += VolcanoArmorHelmet.exAttackDamage(player);
        exDamage += CastleNewRune.attackDamage(player);
        exDamage += StableAttributesModifier.getModifierValue(player, StableAttributesModifier.playerAttackDamageModifier);
        exDamage += ChangedAttributesModifier.getModifierValue(player, ChangedAttributesModifier.exAttackDamage);
        exDamage += InCuriosOrEquipSlotAttributesModify.getAttributes(player, Utils.attackDamage);
        // 请在上方添加

        double totalAttackDamage = baseAttackDamage + exDamage;

        totalAttackDamage *= (1 + MoonShield.damageEnhance(player));
        totalAttackDamage *= (1 + MoonKnife.damageEnhance(player));
        totalAttackDamage *= Compute.playerFantasyAttributeEnhance(player);
        totalAttackDamage *= AttackCurios1.playerAttackDamageEnhance(player);
        totalAttackDamage *= (1 + GemAttributes.getPlayerCurrentAllEquipGemsValue(player, Utils.percentAttackDamageEnhance)
                + Compute.CuriosAttribute.attributeValue(player, Utils.percentAttackDamageEnhance,
                StringUtils.CuriosAttribute.percentAttackDamage));
        if (data.contains("NetherRecallBuff") && data.getInt("NetherRecallBuff") > 0) {
            totalAttackDamage *= 0.5;
        }

        writeToCache(player, Utils.attackDamage, totalAttackDamage);
        return totalAttackDamage;
    }

    public static double critRate(Player player) {
        if (canGetFromCache(player, Utils.critRate)) {
            return getFromCache(player, Utils.critRate);
        }
        CompoundTag data = player.getPersistentData();
        double critRate = 0.0d;
        if (player.getMainHandItem().is(C5LootItems.pillagerBow.get())) {
            CompoundTag tempData = player.getMainHandItem().getOrCreateTagElement(Utils.MOD_ID);
            if (tempData.getDouble(StringUtils.RandomAttribute.critRate) > 1)
                tempData.putDouble(StringUtils.RandomAttribute.critRate, 0.5);
        }

        Item boots = player.getItemBySlot(EquipmentSlot.FEET).getItem();
        Item leggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item chest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
        Item helmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        Item offhand = player.getItemInHand(InteractionHand.OFF_HAND).getItem();
        CompoundTag helmetTag = player.getItemBySlot(EquipmentSlot.HEAD).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag chestTag = player.getItemBySlot(EquipmentSlot.CHEST).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag leggingsTag = player.getItemBySlot(EquipmentSlot.LEGS).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag bootsTag = player.getItemBySlot(EquipmentSlot.FEET).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag stackmainhandtag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            stackmainhandtag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }

        critRate += handleAllEquipRandomAttribute(player, StringUtils.RandomAttribute.critRate);

        if (helmetTag.contains("newGems1")) critRate += GemAttributes.gemsCritRate(helmetTag);
        if (chestTag.contains("newGems1")) critRate += GemAttributes.gemsCritRate(chestTag);
        if (leggingsTag.contains("newGems1")) critRate += GemAttributes.gemsCritRate(leggingsTag);
        if (bootsTag.contains("newGems1")) critRate += GemAttributes.gemsCritRate(bootsTag);
        if (stackmainhandtag.contains("newGems1") && Utils.mainHandTag.containsKey(mainhand))
            critRate += GemAttributes.gemsCritRate(stackmainhandtag);
        if (Utils.mainHandTag.containsKey(mainhand) && stackmainhandtag.contains("criticalrate"))
            critRate += stackmainhandtag.getDouble("criticalrate");

        if (Utils.critRate.containsKey(boots)) critRate += Utils.critRate.get(boots);
        if (Utils.critRate.containsKey(leggings)) critRate += Utils.critRate.get(leggings);
        if (Utils.critRate.containsKey(chest)) critRate += Utils.critRate.get(chest);
        if (Utils.critRate.containsKey(helmet)) critRate += Utils.critRate.get(helmet);
        if (Utils.mainHandTag.containsKey(mainhand) && Utils.critRate.containsKey(mainhand))
            critRate += Utils.critRate.get(mainhand);
        if (Utils.offHandTag.containsKey(offhand) && Utils.critRate.containsKey(offhand))
            critRate += Utils.critRate.get(offhand);

        // 器灵属性加成
        critRate += Compute.PassiveEquip.getAttribute(player, Utils.critRate);

        if (player.getEffect(ModEffects.CRITRATEUP.get()) != null && player.getEffect(ModEffects.CRITRATEUP.get()).getAmplifier() == 0)
            critRate += 0.2;
        if (player.getEffect(ModEffects.CRITRATEUP.get()) != null && player.getEffect(ModEffects.CRITRATEUP.get()).getAmplifier() == 1)
            critRate += 0.4;
        if (data.contains("GemSCritRate")) critRate += data.getDouble("GemSCritRate");
        critRate += SArmorAttribute.value(player, SArmorAttribute.skyPower);
        if (Compute.getBowSkillLevel(data, 11) > 0 && Utils.bowTag.containsKey(mainhand)) {
            critRate += Compute.getBowSkillLevel(data, 11) * 0.02;
        } // 锻弦-平衡（手持弓时，获得额外2%暴击几率）

        int luckyAbilityPoint = data.getInt(StringUtils.Ability.Lucky);
        if (data.contains(StringUtils.Ability.Lucky) && data.getInt(StringUtils.Ability.Lucky) > 0) {
            critRate += luckyAbilityPoint * 0.001;
        }

        if (stackmainhandtag.contains(StringUtils.SoulEquipForge) && (Utils.swordTag.containsKey(mainhand) || Utils.bowTag.containsKey(mainhand)))
            critRate +=
                    stackmainhandtag.getInt(StringUtils.SoulEquipForge) * SoulEquipAttribute.ForgingAddition.CritRate;

        critRate += Compute.CuriosAttribute.attributeValue(player, Utils.critRate, StringUtils.CuriosAttribute.critRate); // 新版饰品属性加成

        critRate += AttackCurios2.playerCritRateUp(player);
        critRate += BowCurios2.playerCritRateUp(player);
        critRate += StableAttributesModifier.getModifierValue(player, StableAttributesModifier.playerCritRateModifier);
        // 请在上方添加
        critRate *= Compute.playerFantasyAttributeEnhance(player);
        writeToCache(player, Utils.critRate, critRate);
        return critRate;
    }

    public static double critDamage(Player player) {
        if (canGetFromCache(player, Utils.critDamage)) {
            return getFromCache(player, Utils.critDamage);
        }
        int tickCount = player.getServer().getTickCount();
        CompoundTag data = player.getPersistentData();
        double critDamage = 1;
        Item boots = player.getItemBySlot(EquipmentSlot.FEET).getItem();
        Item leggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item chest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
        Item helmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        Item offhand = player.getItemInHand(InteractionHand.OFF_HAND).getItem();
        CompoundTag helmetTag = player.getItemBySlot(EquipmentSlot.HEAD).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag chestTag = player.getItemBySlot(EquipmentSlot.CHEST).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag leggingsTag = player.getItemBySlot(EquipmentSlot.LEGS).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag bootsTag = player.getItemBySlot(EquipmentSlot.FEET).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag stackmainhandtag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            stackmainhandtag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }

        // 计算随机属性装备数值
        critDamage += handleAllEquipRandomAttribute(player, StringUtils.RandomAttribute.critDamage);

        // 计算线性等级强度装备数值
        critDamage += computeAllEquipSlotXpLevelAttributeValue(player, Utils.xpLevelCritDamage, false);

        critDamage += Compute.CuriosAttribute.attributeValue(player, Utils.xpLevelCritDamage,
                StringUtils.CuriosAttribute.xpLevelCritDamage) * player.experienceLevel;

        if (helmetTag.contains("newGems1")) critDamage += GemAttributes.gemsCritDamage(helmetTag);
        if (chestTag.contains("newGems1")) critDamage += GemAttributes.gemsCritDamage(chestTag);
        if (leggingsTag.contains("newGems1")) critDamage += GemAttributes.gemsCritDamage(leggingsTag);
        if (bootsTag.contains("newGems1")) critDamage += GemAttributes.gemsCritDamage(bootsTag);
        if (stackmainhandtag.contains("newGems1") && Utils.mainHandTag.containsKey(mainhand))
            critDamage += GemAttributes.gemsCritDamage(stackmainhandtag);
        if (Utils.mainHandTag.containsKey(mainhand) && stackmainhandtag.contains("criticaldamage"))
            critDamage += stackmainhandtag.getDouble("criticaldamage");
        if (Utils.critDamage.containsKey(boots)) critDamage += Utils.critDamage.get(boots);
        if (Utils.critDamage.containsKey(leggings)) critDamage += Utils.critDamage.get(leggings);
        if (Utils.critDamage.containsKey(chest)) critDamage += Utils.critDamage.get(chest);
        if (Utils.critDamage.containsKey(helmet)) critDamage += Utils.critDamage.get(helmet);
        if (Utils.mainHandTag.containsKey(mainhand) && Utils.critDamage.containsKey(mainhand))
            critDamage += Utils.critDamage.get(mainhand);
        if (Utils.offHandTag.containsKey(offhand) && Utils.critDamage.containsKey(offhand))
            critDamage += Utils.critDamage.get(offhand);
        if (data.contains("Bow")) critDamage += (data.getInt("Bow") / 30000.0d) * 0.3F;

        if (player.getEffect(ModEffects.CRITDAMAGEUP.get()) != null && player.getEffect(ModEffects.CRITDAMAGEUP.get()).getAmplifier() == 0)
            critDamage += 0.4;
        if (player.getEffect(ModEffects.CRITDAMAGEUP.get()) != null && player.getEffect(ModEffects.CRITDAMAGEUP.get()).getAmplifier() == 1)
            critDamage += 0.8;
        if (data.contains("GemSCritDamage")) critDamage += data.getDouble("GemSCritDamage");
        critDamage += SArmorAttribute.value(player, SArmorAttribute.snowPower);

        if (Compute.getBowSkillLevel(data, 7) > 0 && Utils.bowTag.containsKey(mainhand)) {
            critDamage += Compute.getBowSkillLevel(data, 7) * 0.1;
        } // 锻矢-锋利（手持弓时，获得10%暴击伤害）

        if (SuitCount.getVolcanoSuitCount(player) >= 4) critDamage += 0.35;

        if (stackmainhandtag.contains(StringUtils.SoulEquipForge) && (Utils.swordTag.containsKey(mainhand) || Utils.bowTag.containsKey(mainhand)))
            critDamage +=
                    stackmainhandtag.getInt(StringUtils.SoulEquipForge) * SoulEquipAttribute.ForgingAddition.CritDamage;

        if (Utils.IceSwordEffectMap.containsKey(player) && Utils.IceSwordEffectMap.get(player) > tickCount) {
            critDamage += Utils.IceSwordEffectNumMap.get(player) / 1200;
        } //冰霜剑

        if (player.getItemInHand(InteractionHand.OFF_HAND).is(ModItems.ManaShield.get())) {
            if (player.getHealth() / player.getMaxHealth() > 0.5) {
                critDamage += data.getDouble("HealthStealAfterCompute") * 5;
            }
        } // 封魔者法盾

        if (player.getItemInHand(InteractionHand.OFF_HAND).is(ModItems.manaKnife.get())) {
            critDamage += data.getDouble("HealthStealAfterCompute") * 3;
        } // 猎魔者小刀

        critDamage += Compute.CuriosAttribute.attributeValue(player, Utils.critDamage, StringUtils.CuriosAttribute.critDamage); // 新版饰品属性加成

        critDamage += Compute.PassiveEquip.getAttribute(player, Utils.critDamage); // 器灵属性加成
        critDamage += CastleAttackArmor.ExAttributeValue(player, CastleAttackArmor.ExCritDamage);
        critDamage += AttackCurios1.playerCritDamageUp(player);
        critDamage += StableAttributesModifier.getModifierValue(player, StableAttributesModifier.playerCritDamageModifier);
        // 请在上方添加

        critDamage *= Compute.playerFantasyAttributeEnhance(player);
        critDamage *= AttackCurios0.PlayerFinalCritDamageEnhance(player);
        critDamage *= AttackCurios2.playerCritDamageEnhance(player);
        critDamage *= BowCurios2.playerCritDamageEnhance(player);
        critDamage *= SkyNewRune.critDamageInfluence(player);

        if (player.getItemInHand(InteractionHand.OFF_HAND).is(ModItems.ManaShield.get())) {
            if (player.getHealth() / player.getMaxHealth() < 0.5) {
                data.putDouble("CritDamageAfterCompute", critDamage);
                critDamage = 0;
            }
        } // 封魔者法盾
        writeToCache(player, Utils.critDamage, critDamage);
        return critDamage;
    }

    public static double decreasePlayerCritDamage(Player player) {
        double CritDamageDecrease = 0.0d;
        Item boots = player.getItemBySlot(EquipmentSlot.FEET).getItem();
        Item leggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item chest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
        Item helmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        Item offhand = player.getItemInHand(InteractionHand.OFF_HAND).getItem();
        CompoundTag stackmainhandtag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            stackmainhandtag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }
        if (Utils.mainHandTag.containsKey(mainhand) && stackmainhandtag.contains("CritDamageDecrease"))
            CritDamageDecrease += stackmainhandtag.getDouble("CritDamageDecrease");
        if (Utils.critDamageDecrease.containsKey(boots)) CritDamageDecrease += Utils.critDamageDecrease.get(boots);
        if (Utils.critDamageDecrease.containsKey(leggings))
            CritDamageDecrease += Utils.critDamageDecrease.get(leggings);
        if (Utils.critDamageDecrease.containsKey(chest)) CritDamageDecrease += Utils.critDamageDecrease.get(chest);
        if (Utils.critDamageDecrease.containsKey(helmet)) CritDamageDecrease += Utils.critDamageDecrease.get(helmet);
        if (Utils.mainHandTag.containsKey(mainhand) && Utils.critDamageDecrease.containsKey(mainhand))
            CritDamageDecrease += Utils.critDamageDecrease.get(mainhand);
        if (Utils.offHandTag.containsKey(offhand) && Utils.critDamageDecrease.containsKey(offhand))
            CritDamageDecrease += Utils.critDamageDecrease.get(offhand);
        if (SuitCount.getMineSuitCount(player) >= 2) CritDamageDecrease += 0.5;
        return CritDamageDecrease;
    }

    public static double movementSpeedCommon(Player player) {
        if (canGetFromCache(player, Utils.movementSpeedCommon)) {
            return getFromCache(player, Utils.movementSpeedCommon);
        }
        double movementSpeedUp = 0;
        if (TowerMob.playerIsChallenging2Floor(player)) return 0;
        int tick = Tick.get();
        CompoundTag data = player.getPersistentData();
        ItemStack mainHandStack = player.getMainHandItem();
        Item mainHandItem = mainHandStack.getItem();
        CompoundTag mainHandTag = mainHandStack.getTagElement(Utils.MOD_ID);

        movementSpeedUp += computeAllEquipSlotBaseAttributeValue(player, Utils.movementSpeedCommon, true);
        movementSpeedUp += StableAttributesModifier.getModifierValue(player, StableAttributesModifier.playerMovementSpeedModifier);
        movementSpeedUp += ChangedAttributesModifier.getModifierValue(player, ChangedAttributesModifier.movementSpeedUp);

        if (data.contains(StringUtils.SakuraDemonSword) && data.getInt(StringUtils.SakuraDemonSword) > tick)
            movementSpeedUp += 0.2;
        // 妖刀-樱

        if (SuitCount.getLakeSuitCount(player) >= 4) movementSpeedUp += 0.1;
        if (SuitCount.getMineSuitCount(player) >= 4) movementSpeedUp -= 0.2;

        if (player.getEffect(ModEffects.SPEEDUP.get()) != null && player.getEffect(ModEffects.SPEEDUP.get()).getAmplifier() == 0)
            movementSpeedUp += 0.1;
        if (player.getEffect(ModEffects.SPEEDUP.get()) != null && player.getEffect(ModEffects.SPEEDUP.get()).getAmplifier() == 1)
            movementSpeedUp += 0.2;

        if (SuitCount.getSkySuitCount(player) > 0 && player.getHealth() / player.getMaxHealth() > 0.8)
            movementSpeedUp += 0.2 * Compute.SkySuitEffectRate(player);

        int flexibilityAbilityPoint = data.getInt(StringUtils.Ability.Flexibility);
        if (data.contains(StringUtils.Ability.Flexibility) && data.getInt(StringUtils.Ability.Flexibility) > 0) {
            movementSpeedUp += flexibilityAbilityPoint * 0.001;
        } // 能力

        if (Compute.getSwordSkillLevel(data, 9) > 0 && Utils.swordTag.containsKey(mainHandItem)) {
            movementSpeedUp += Compute.getSwordSkillLevel(data, 9) * 0.01;
        } // 剑舞（手持近战武器时，获得1%额外移动速度）

        if (Compute.getBowSkillLevel(data, 1) > 0 && Utils.bowTag.containsKey(mainHandItem)) {
            movementSpeedUp += Compute.getBowSkillLevel(data, 1) * 0.01;
        } // 原野护符（持有弓时，获得1%的额外移动速度）

        if (Compute.getBowSkillLevel(data, 9) > 0 && Utils.bowTag.containsKey(mainHandItem)) {
            movementSpeedUp += Compute.getBowSkillLevel(data, 9) * 0.01;
        } // 猎手本能（手持弓时，获得1%额外移动速度）

        if (Compute.getManaSkillLevel(data, 9) > 0 && Utils.sceptreTag.containsKey(mainHandItem)) {
            movementSpeedUp += Compute.getManaSkillLevel(data, 9) * 0.01;
        } // 法师（手持法杖时，获得1%额外移动速度）

        if (mainHandTag != null && mainHandTag.contains(StringUtils.ManaCore.ManaCore)
                && mainHandTag.getString(StringUtils.ManaCore.ManaCore).
                equals(StringUtils.ManaCore.KazeCore)) {
            movementSpeedUp += 0.08;
        }

        if (WraqCurios.isOn(SkyNewRune.class, player)) movementSpeedUp += 0.2;

        movementSpeedUp += Compute.CuriosAttribute.attributeValue(player, Utils.movementSpeedCommon,
                StringUtils.CuriosAttribute.commonMovementSpeed); // 新版饰品属性加成

        movementSpeedUp += GemAttributes.getPlayerCurrentAllEquipGemsValue(player, Utils.movementSpeedCommon);

        // 上方添加

        movementSpeedUp *= Compute.playerFantasyAttributeEnhance(player);

        writeToCache(player, Utils.movementSpeedCommon, movementSpeedUp);
        return movementSpeedUp;
    }

    public static double movementSpeedCurrent(Player player) {
        double movementSpeed = movementSpeedCommon(player);
        if (!Compute.playerIsInBattle(player)) movementSpeed += movementSpeedWithoutBattle(player);
        return movementSpeed;
    }

    public static double movementSpeedWithoutBattle(Player player) {
        if (TowerMob.playerIsChallenging2Floor(player)) return 0;
        if (canGetFromCache(player, Utils.movementSpeedWithoutBattle)) {
            return getFromCache(player, Utils.movementSpeedWithoutBattle);
        }
        CompoundTag data = player.getPersistentData();
        double speedUp = 0;
        Item boots = player.getItemBySlot(EquipmentSlot.FEET).getItem();
        Item leggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item chest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
        Item helmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        Item offhand = player.getItemInHand(InteractionHand.OFF_HAND).getItem();

        CompoundTag helmetTag = player.getItemBySlot(EquipmentSlot.HEAD).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag chestTag = player.getItemBySlot(EquipmentSlot.CHEST).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag leggingsTag = player.getItemBySlot(EquipmentSlot.LEGS).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag bootsTag = player.getItemBySlot(EquipmentSlot.FEET).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag stackmainhandtag = new CompoundTag();

        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            stackmainhandtag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }

        if (Utils.mainHandTag.containsKey(mainhand) && stackmainhandtag.contains("speedup"))
            speedUp += stackmainhandtag.getDouble("speedup");

        if (Utils.movementSpeedWithoutBattle.containsKey(boots)) speedUp += Utils.movementSpeedWithoutBattle.get(boots);
        if (Utils.movementSpeedWithoutBattle.containsKey(leggings)) speedUp += Utils.movementSpeedWithoutBattle.get(leggings);
        if (Utils.movementSpeedWithoutBattle.containsKey(chest)) speedUp += Utils.movementSpeedWithoutBattle.get(chest);
        if (Utils.movementSpeedWithoutBattle.containsKey(helmet)) speedUp += Utils.movementSpeedWithoutBattle.get(helmet);

        if (Utils.mainHandTag.containsKey(mainhand) && Utils.movementSpeedWithoutBattle.containsKey(mainhand))
            speedUp += Utils.movementSpeedWithoutBattle.get(mainhand);
        if (Utils.offHandTag.containsKey(offhand) && Utils.movementSpeedWithoutBattle.containsKey(offhand))
            speedUp += Utils.movementSpeedWithoutBattle.get(offhand);

        speedUp += handleAllEquipRandomAttribute(player, StringUtils.RandomAttribute.movementSpeedWithoutBattle);

        if (helmetTag.contains("newGems1")) speedUp += GemAttributes.gemsMovementSpeedUp(helmetTag);
        if (chestTag.contains("newGems1")) speedUp += GemAttributes.gemsMovementSpeedUp(chestTag);
        if (leggingsTag.contains("newGems1")) speedUp += GemAttributes.gemsMovementSpeedUp(leggingsTag);
        if (bootsTag.contains("newGems1")) speedUp += GemAttributes.gemsMovementSpeedUp(bootsTag);
        if (stackmainhandtag.contains("newGems1") && Utils.mainHandTag.containsKey(mainhand))
            speedUp += GemAttributes.gemsMovementSpeedUp(stackmainhandtag);

        if (data.contains("GemSSpeed")) speedUp += data.getDouble("GemSSpeed");

        if (leggings.equals(ModItems.MinePants.get()) && (Utils.OverWorldLevelIsNight || player.getY() < 63))
            speedUp += 0.4;
        // 矿工裤被动

        if (stackmainhandtag.contains(StringUtils.SoulEquipForge)) speedUp +=
                stackmainhandtag.getInt(StringUtils.SoulEquipForge) * SoulEquipAttribute.ForgingAddition.MovementSpeed;

        if (Utils.PlayerSpringBeltMovementAttribute.containsKey(player) && Utils.PlayerSpringBeltLevelRequire.get(player) <= player.experienceLevel) {
            speedUp += Utils.PlayerSpringBeltMovementAttribute.get(player);
        }

        speedUp += Compute.CuriosAttribute.attributeValue(player, Utils.movementSpeedWithoutBattle,
                StringUtils.CuriosAttribute.movementSpeed); // 新版饰品属性加成
        speedUp += Compute.PassiveEquip.getAttribute(player, Utils.movementSpeedWithoutBattle); // 器灵属性加成
        speedUp += StableAttributesModifier.getModifierValue(player, StableAttributesModifier.playerMovementSpeedWithoutBattleModifier);

        // 请在上方添加
        speedUp *= Compute.playerFantasyAttributeEnhance(player);
        writeToCache(player, Utils.movementSpeedWithoutBattle, speedUp);
        return speedUp;
    }

    public static double expUp(Player player) {
        if (canGetFromCache(player, Utils.expUp)) {
            return getFromCache(player, Utils.expUp);
        }
        CompoundTag data = player.getPersistentData();
        double expUp = 0.0d;
        Item boots = player.getItemBySlot(EquipmentSlot.FEET).getItem();
        Item leggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item chest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
        Item helmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        Item offhand = player.getItemInHand(InteractionHand.OFF_HAND).getItem();
        CompoundTag stackmainhandtag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            stackmainhandtag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }

        if (Utils.expUp.containsKey(boots)) expUp += Utils.expUp.get(boots);
        if (Utils.expUp.containsKey(leggings)) expUp += Utils.expUp.get(leggings);
        if (Utils.expUp.containsKey(chest)) expUp += Utils.expUp.get(chest);
        if (Utils.expUp.containsKey(helmet)) expUp += Utils.expUp.get(helmet);
        if (Utils.mainHandTag.containsKey(mainhand) && Utils.expUp.containsKey(mainhand))
            expUp += Utils.expUp.get(mainhand);
        if (Utils.offHandTag.containsKey(offhand) && Utils.expUp.containsKey(offhand))
            expUp += Utils.expUp.get(offhand);

        if (data.contains("GemSExpImprove")) expUp += data.getDouble("GemSExpImprove");
        int luckyAbilityPoint = data.getInt(StringUtils.Ability.Lucky);
        if (data.contains(StringUtils.Ability.Lucky) && data.getInt(StringUtils.Ability.Lucky) > 0) {
            expUp += luckyAbilityPoint * 0.01;
        }

        if (Utils.PlayerSpringRingExpUpAttribute.containsKey(player) && Utils.PlayerSpringRingLevelRequire.get(player) <= player.experienceLevel) {
            expUp += Utils.PlayerSpringRingExpUpAttribute.get(player);
        }

        if (Utils.PlayerSpringHandExpUpAttribute.containsKey(player) && Utils.PlayerSpringHandLevelRequire.get(player) <= player.experienceLevel) {
            expUp += Utils.PlayerSpringHandExpUpAttribute.get(player);
        }

        if (Utils.PlayerSpringBeltExpUpAttribute.containsKey(player) && Utils.PlayerSpringBeltLevelRequire.get(player) <= player.experienceLevel) {
            expUp += Utils.PlayerSpringBeltExpUpAttribute.get(player);
        }

        if (Utils.PlayerSpringBraceletExpUpAttribute.containsKey(player) && Utils.PlayerSpringBraceletLevelRequire.get(player) <= player.experienceLevel) {
            expUp += Utils.PlayerSpringBraceletExpUpAttribute.get(player);
        }

        CompoundTag helmetTag = player.getItemBySlot(EquipmentSlot.HEAD).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag chestTag = player.getItemBySlot(EquipmentSlot.CHEST).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag leggingsTag = player.getItemBySlot(EquipmentSlot.LEGS).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag bootsTag = player.getItemBySlot(EquipmentSlot.FEET).getOrCreateTagElement(Utils.MOD_ID);
        if (helmetTag.contains("newGems1")) expUp += GemAttributes.gemsExpUp(helmetTag);
        if (chestTag.contains("newGems1")) expUp += GemAttributes.gemsExpUp(chestTag);
        if (leggingsTag.contains("newGems1")) expUp += GemAttributes.gemsExpUp(leggingsTag);
        if (bootsTag.contains("newGems1")) expUp += GemAttributes.gemsExpUp(bootsTag);
        if (stackmainhandtag.contains("newGems1") && Utils.mainHandTag.containsKey(mainhand))
            expUp += GemAttributes.gemsExpUp(stackmainhandtag);

        expUp += Compute.CuriosAttribute.attributeValue(player, Utils.expUp, StringUtils.CuriosAttribute.expUp); // 新版饰品属性加成

        expUp += Compute.PassiveEquip.getAttribute(player, Utils.expUp); // 器灵属性加成

        int tier = 0;
        try {
            tier = PlanPlayer.getPlayerTier(player);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        expUp += new double[]{0, 1, 2, 3}[tier];

        // 请在上方添加
        expUp *= Compute.playerFantasyAttributeEnhance(player);
        writeToCache(player, Utils.expUp, expUp);
        return expUp;
    }

    public static double defence(Player player) {
        if (canGetFromCache(player, Utils.defence)) {
            return getFromCache(player, Utils.defence);
        }
        int TickCount = player.getServer().getTickCount();
        CompoundTag data = player.getPersistentData();
        double baseDefence = player.experienceLevel * 0.2;
        double exDefence = 0.0d;
        Item boots = player.getItemBySlot(EquipmentSlot.FEET).getItem();
        Item leggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item chest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
        Item helmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        Item offhand = player.getItemInHand(InteractionHand.OFF_HAND).getItem();
        CompoundTag helmetTag = player.getItemBySlot(EquipmentSlot.HEAD).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag chestTag = player.getItemBySlot(EquipmentSlot.CHEST).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag leggingsTag = player.getItemBySlot(EquipmentSlot.LEGS).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag bootsTag = player.getItemBySlot(EquipmentSlot.FEET).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag stackmainhandtag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            stackmainhandtag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }
        if (Utils.defence.containsKey(boots))
            baseDefence += ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.FEET), Utils.defence);
        if (Utils.defence.containsKey(leggings))
            baseDefence += ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.LEGS), Utils.defence);
        if (Utils.defence.containsKey(chest))
            baseDefence += ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.CHEST), Utils.defence);
        if (Utils.defence.containsKey(helmet))
            baseDefence += ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.HEAD), Utils.defence);

        baseDefence += handleAllEquipRandomAttribute(player, StringUtils.RandomAttribute.defence);

        if (helmetTag.contains("newGems1")) exDefence += GemAttributes.gemsDefence(helmetTag);
        if (chestTag.contains("newGems1")) exDefence += GemAttributes.gemsDefence(chestTag);
        if (leggingsTag.contains("newGems1")) exDefence += GemAttributes.gemsDefence(leggingsTag);
        if (bootsTag.contains("newGems1")) exDefence += GemAttributes.gemsDefence(bootsTag);
        if (stackmainhandtag.contains("newGems1")) exDefence += GemAttributes.gemsDefence(stackmainhandtag);
        if (Utils.mainHandTag.containsKey(mainhand) && Utils.defence.containsKey(mainhand))
            exDefence += Utils.defence.get(mainhand);
        if (Utils.offHandTag.containsKey(offhand) && Utils.defence.containsKey(offhand))
            baseDefence += Utils.defence.get(offhand);
        if (Utils.defence.containsKey(helmet) && helmetTag.contains("Forging"))
            baseDefence += Compute.forgingValue(helmetTag, ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.HEAD), Utils.defence));
        if (Utils.defence.containsKey(chest) && chestTag.contains("Forging"))
            baseDefence += Compute.forgingValue(chestTag, ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.CHEST), Utils.defence));
        if (Utils.defence.containsKey(leggings) && leggingsTag.contains("Forging"))
            baseDefence += Compute.forgingValue(leggingsTag, ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.LEGS), Utils.defence));
        if (Utils.defence.containsKey(boots) && bootsTag.contains("Forging"))
            baseDefence += Compute.forgingValue(bootsTag, ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.FEET), Utils.defence));

        // 计算线性等级强度装备数值
        baseDefence += computeAllEquipSlotXpLevelAttributeValue(player, Utils.xpLevelDefence, false);

        baseDefence += Compute.CuriosAttribute.attributeValue(player, Utils.xpLevelDefence,
                StringUtils.CuriosAttribute.xpLevelDefence) * player.experienceLevel;

        // 以下为额外护甲
        if (SuitCount.getForestSuitCount(player) >= 2) exDefence += baseDefence * 0.25;
        if (SuitCount.getLifeManaSuitCount(player) >= 4) exDefence += baseDefence * 0.25;
        if (player.getEffect(ModEffects.DefenceUP.get()) != null
                && player.getEffect(ModEffects.DefenceUP.get()).getAmplifier() == 0) {
            exDefence += baseDefence * 0.25 + 5;
        }
        if (player.getEffect(ModEffects.DefenceUP.get()) != null
                && player.getEffect(ModEffects.DefenceUP.get()).getAmplifier() == 1) {
            exDefence += baseDefence * 0.40 + 10;
        }
        if (leggings.equals(ModItems.MinePants.get()) && (Utils.OverWorldLevelIsNight || player.getY() < 63))
            exDefence += 1;
        // 矿工裤被动
        if (SuitCount.getMineSuitCount(player) >= 4) exDefence += 60;

        if (data.getInt(StringUtils.PlainSwordActive.PlainSceptre) > TickCount) exDefence += 1;

        if (Utils.MineShieldEffect.containsKey(player) && Utils.MineShieldEffect.get(player) > TickCount) {
            exDefence += player.experienceLevel;
        }

        if (Utils.PlayerSpringHandDefenceAttribute.containsKey(player) && Utils.PlayerSpringHandLevelRequire.get(player) <= player.experienceLevel) {
            exDefence += Utils.PlayerSpringHandDefenceAttribute.get(player) * 0.01;
        }

        if (Utils.MeteoriteDefenceMap.containsKey(player) && Utils.MeteoriteDefenceTimeMap.get(player) > TickCount) {
            exDefence += Utils.MeteoriteDefenceMap.get(player) * 0.01;
        }

        if (Utils.SnowShieldPlayerEffectTickMap.containsKey(player) && Utils.SnowShieldPlayerEffectTickMap.get(player) > TickCount) {
            exDefence += Utils.SnowShieldPlayerEffectMap.get(player);
        } // 玉山圆盾

        exDefence += Compute.CuriosAttribute.attributeValue(player, Utils.defence, StringUtils.CuriosAttribute.defence); // 新版饰品属性加成

        exDefence += Compute.PassiveEquip.getAttribute(player, Utils.defence); // 器灵属性加成

        exDefence += CastleAttackArmor.ExAttributeValue(player, CastleAttackArmor.ExDefence);
        exDefence += CastleManaArmor.ExAttributeValue(player, CastleManaArmor.ExDefence);
        exDefence += CastleSwiftArmor.ExAttributeValue(player, CastleSwiftArmor.ExDefence);
        exDefence += ForestArmorHelmet.exDefence(player);
        exDefence += InCuriosOrEquipSlotAttributesModify.getAttributes(player, Utils.defence);
        exDefence += StableTierAttributeModifier.getModifierValue(player, StableTierAttributeModifier.defence);
        exDefence += StableAttributesModifier.getModifierValue(player, StableAttributesModifier.playerDefenceModifier);
        // 请在上方添加
        double totalDefence = 0;
        totalDefence = baseDefence + exDefence;
        totalDefence *= (1 + EarthPower.PlayerDefenceEnhance(player));
        totalDefence *= Compute.playerFantasyAttributeEnhance(player);
        totalDefence *= MineShield.defenceEnhance(player);
        totalDefence *= (1 + GemAttributes.getPlayerCurrentAllEquipGemsValue(player, Utils.percentDefenceEnhance) +
                Compute.CuriosAttribute.attributeValue(player, Utils.percentDefenceEnhance,
                StringUtils.CuriosAttribute.percentDefenceEnhance));
        totalDefence *= (1 + StableAttributesModifier.getModifierValue(player, StableAttributesModifier.playerPercentDefenceModifier));
        if (data.contains("ManaRune") && data.getInt("ManaRune") == 3) return (baseDefence + exDefence) * 0.5f;

        totalDefence -= StableAttributesModifier.getModifierValue(player, StableAttributesModifier.playerDefenceDecreaseModifier);

        totalDefence = Math.max(0, totalDefence);
        writeToCache(player, Utils.defence, totalDefence);
        return totalDefence;
    }

    public static double damageDirectDecrease(Player player) {
        double value = 0;
        CompoundTag data = player.getPersistentData();

        int powerAbilityPoint = data.getInt(StringUtils.Ability.Power);
        if (data.contains(StringUtils.Ability.Power) && data.getInt(StringUtils.Ability.Power) > 0) {
            value += powerAbilityPoint * 5;
        } // 能力

        return value;
    }

    public static double getHealEffect(Player player) {
        if (canGetFromCache(player, Utils.healingAmplification)) {
            return getFromCache(player, Utils.healingAmplification);
        }
        int tick = Tick.get();
        double healEffectUp = 1;
        CompoundTag data = player.getPersistentData();
        Item boots = player.getItemBySlot(EquipmentSlot.FEET).getItem();
        Item leggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item chest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
        Item helmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        Item offhand = player.getItemInHand(InteractionHand.OFF_HAND).getItem();
        CompoundTag helmetTag = player.getItemBySlot(EquipmentSlot.HEAD).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag chestTag = player.getItemBySlot(EquipmentSlot.CHEST).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag leggingsTag = player.getItemBySlot(EquipmentSlot.LEGS).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag bootsTag = player.getItemBySlot(EquipmentSlot.FEET).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag stackmainhandtag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            stackmainhandtag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }
        if (Utils.mainHandTag.containsKey(mainhand) && stackmainhandtag.contains("healeffectup"))
            healEffectUp += stackmainhandtag.getDouble("healeffectup");
        if (Utils.healingAmplification.containsKey(boots)) healEffectUp += Utils.healingAmplification.get(boots);
        if (Utils.healingAmplification.containsKey(leggings)) healEffectUp += Utils.healingAmplification.get(leggings);
        if (Utils.healingAmplification.containsKey(chest)) healEffectUp += Utils.healingAmplification.get(chest);
        if (Utils.healingAmplification.containsKey(helmet)) healEffectUp += Utils.healingAmplification.get(helmet);
        if (Utils.mainHandTag.containsKey(mainhand) && Utils.healingAmplification.containsKey(mainhand))
            healEffectUp += Utils.healingAmplification.get(mainhand);
        if (Utils.offHandTag.containsKey(offhand) && Utils.healingAmplification.containsKey(offhand))
            healEffectUp += Utils.healingAmplification.get(offhand);
        if (SuitCount.getForestSuitCount(player) >= 4) healEffectUp += 0.5f;
        int vitalityAbilityPoint = data.getInt(StringUtils.Ability.Vitality);
        if (data.contains(StringUtils.Ability.Vitality) && data.getInt(StringUtils.Ability.Vitality) > 0) {
            healEffectUp += vitalityAbilityPoint * 0.01;
        }
        if (data.getInt(StringUtils.MineMonsterEffect) >= tick) healEffectUp -= 0.8;

        if (helmetTag.contains("newGems1")) healEffectUp += GemAttributes.gemsHealEffectUp(helmetTag);
        if (chestTag.contains("newGems1")) healEffectUp += GemAttributes.gemsHealEffectUp(chestTag);
        if (leggingsTag.contains("newGems1")) healEffectUp += GemAttributes.gemsHealEffectUp(leggingsTag);
        if (bootsTag.contains("newGems1")) healEffectUp += GemAttributes.gemsHealEffectUp(bootsTag);
        if (stackmainhandtag.contains("newGems1") && Utils.mainHandTag.containsKey(mainhand))
            healEffectUp += GemAttributes.gemsHealEffectUp(stackmainhandtag);

        healEffectUp += Compute.CuriosAttribute.attributeValue(player, Utils.healingAmplification, StringUtils.CuriosAttribute.healEffectUp); // 新版饰品属性加成
        healEffectUp += StableAttributesModifier.getModifierValue(player, StableAttributesModifier.playerHealAmplifierModifier);

        writeToCache(player, Utils.healingAmplification, healEffectUp * (1 - healEffectDecrease(player)));
        return healEffectUp * (1 - healEffectDecrease(player));
    }

    public static double healEffectDecrease(Player player) {
        double rate = 0;
        rate += StableAttributesModifier.getModifierValue(player, StableAttributesModifier.playerHealAmplifierReductionModifier);
        return rate;
    }

    public static double extraSwiftness(Player player) {
        if (canGetFromCache(player, Utils.swiftnessUp)) {
            return getFromCache(player, Utils.swiftnessUp);
        }
        CompoundTag data = player.getPersistentData();
        int TickCount = player.getServer().getTickCount();
        double swiftnessUp = 0.0d;
        Item boots = player.getItemBySlot(EquipmentSlot.FEET).getItem();
        Item leggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item chest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
        Item helmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        Item offhand = player.getItemInHand(InteractionHand.OFF_HAND).getItem();
        CompoundTag stackmainhandtag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            stackmainhandtag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }
        if (Utils.mainHandTag.containsKey(mainhand) && stackmainhandtag.contains("Swiftness"))
            swiftnessUp += stackmainhandtag.getDouble("Swiftness");
        if (Utils.swiftnessUp.containsKey(boots)) swiftnessUp += Utils.swiftnessUp.get(boots);
        if (Utils.swiftnessUp.containsKey(leggings)) swiftnessUp += Utils.swiftnessUp.get(leggings);
        if (Utils.swiftnessUp.containsKey(chest)) swiftnessUp += Utils.swiftnessUp.get(chest);
        if (Utils.swiftnessUp.containsKey(helmet)) swiftnessUp += Utils.swiftnessUp.get(helmet);
        if (Utils.mainHandTag.containsKey(mainhand) && Utils.swiftnessUp.containsKey(mainhand))
            swiftnessUp += Utils.swiftnessUp.get(mainhand);
        if (Utils.offHandTag.containsKey(offhand) && Utils.swiftnessUp.containsKey(offhand))
            swiftnessUp += Utils.swiftnessUp.get(offhand);

        int flexibilityAbilityPoint = data.getInt(StringUtils.Ability.Flexibility);
        if (data.contains(StringUtils.Ability.Flexibility) && data.getInt(StringUtils.Ability.Flexibility) > 0) {
            swiftnessUp += flexibilityAbilityPoint * 0.1;
        } // 能力

        if (Utils.PlayerSpringBeltSwiftAttribute.containsKey(player) && Utils.PlayerSpringBeltLevelRequire.get(player) <= player.experienceLevel) {
            swiftnessUp += Utils.PlayerSpringBeltSwiftAttribute.get(player);
        }

        if (Utils.SpringScaleTime.containsKey(player) && Utils.SpringScaleTime.get(player) > TickCount) {
            int SwordSkill = data.getInt(StringUtils.SkillArray[0]);
            int BowSkill = data.getInt(StringUtils.SkillArray[1]);
            int ManaSkill = data.getInt(StringUtils.SkillArray[2]);
            if (BowSkill > Math.max(SwordSkill, ManaSkill)) swiftnessUp += Utils.SpringScaleEffect.get(player) + 1;
        } //年兽鳞片

        swiftnessUp += Compute.CuriosAttribute.attributeValue(player, Utils.swiftnessUp, StringUtils.CuriosAttribute.swiftnessUp); // 新版饰品属性加成

        swiftnessUp += Compute.PassiveEquip.getAttribute(player, Utils.swiftnessUp); // 器灵属性加成
        swiftnessUp += CastleSwiftArmor.ExAttributeValue(player, CastleSwiftArmor.ExSwiftnessUp);
        // 请在上方添加
        swiftnessUp *= Compute.playerFantasyAttributeEnhance(player);
        swiftnessUp *= BowCurios0.SwiftnessUp(player);

        writeToCache(player, Utils.swiftnessUp, swiftnessUp);
        return swiftnessUp;
    }

    public static double attackRangeUp(Player player) {
        if (canGetFromCache(player, Utils.attackRangeUp)) {
            return getFromCache(player, Utils.attackRangeUp);
        }
        CompoundTag data = player.getPersistentData();
        double rangeUp = 0.0d;
        Item boots = player.getItemBySlot(EquipmentSlot.FEET).getItem();
        Item leggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item chest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
        Item helmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        Item offhand = player.getItemInHand(InteractionHand.OFF_HAND).getItem();
        CompoundTag stackmainhandtag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            stackmainhandtag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }
        if (Utils.mainHandTag.containsKey(mainhand) && stackmainhandtag.contains("AttackRangeUp"))
            rangeUp += stackmainhandtag.getDouble("AttackRangeUp");
        if (Utils.attackRangeUp.containsKey(boots)) rangeUp += Utils.attackRangeUp.get(boots);
        if (Utils.attackRangeUp.containsKey(leggings)) rangeUp += Utils.attackRangeUp.get(leggings);
        if (Utils.attackRangeUp.containsKey(chest)) rangeUp += Utils.attackRangeUp.get(chest);
        if (Utils.attackRangeUp.containsKey(helmet)) rangeUp += Utils.attackRangeUp.get(helmet);
        if (Utils.mainHandTag.containsKey(mainhand) && Utils.attackRangeUp.containsKey(mainhand))
            rangeUp += Utils.attackRangeUp.get(mainhand);
        if (Utils.offHandTag.containsKey(offhand) && Utils.attackRangeUp.containsKey(offhand))
            rangeUp += Utils.attackRangeUp.get(offhand);
        if (Compute.getSwordSkillLevel(data, 11) > 0 && Utils.swordTag.containsKey(mainhand))
            rangeUp += Compute.getSwordSkillLevel(data, 11) * 0.2;

        // 请在上方添加
        rangeUp *= Compute.playerFantasyAttributeEnhance(player);

        writeToCache(player, Utils.attackRangeUp, rangeUp);
        return rangeUp;
    }

    public static double powerReleaseSpeed(Player player) {
        if (TowerMob.playerIsChallenging2Floor(player)) return 0;
        if (player.isCreative()) return 100;
        if (canGetFromCache(player, Utils.coolDownDecrease)) {
            return getFromCache(player, Utils.coolDownDecrease);
        }
        CompoundTag data = player.getPersistentData();
        double releaseSpeed = 0;
        Item boots = player.getItemBySlot(EquipmentSlot.FEET).getItem();
        Item leggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item chest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
        Item helmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        Item offhand = player.getItemInHand(InteractionHand.OFF_HAND).getItem();
        CompoundTag helmetTag = player.getItemBySlot(EquipmentSlot.HEAD).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag chestTag = player.getItemBySlot(EquipmentSlot.CHEST).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag leggingsTag = player.getItemBySlot(EquipmentSlot.LEGS).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag bootsTag = player.getItemBySlot(EquipmentSlot.FEET).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag stackmainhandtag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            stackmainhandtag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }
        if (helmetTag.contains("newGems1")) releaseSpeed += GemAttributes.gemsCoolDown(helmetTag);
        if (chestTag.contains("newGems1")) releaseSpeed += GemAttributes.gemsCoolDown(chestTag);
        if (leggingsTag.contains("newGems1")) releaseSpeed += GemAttributes.gemsCoolDown(leggingsTag);
        if (bootsTag.contains("newGems1")) releaseSpeed += GemAttributes.gemsCoolDown(bootsTag);
        if (stackmainhandtag.contains("newGems1") && Utils.mainHandTag.containsKey(mainhand))
            releaseSpeed += GemAttributes.gemsCoolDown(stackmainhandtag);
        if (Utils.coolDownDecrease.containsKey(boots)) releaseSpeed += Utils.coolDownDecrease.get(boots);
        if (Utils.coolDownDecrease.containsKey(leggings))
            releaseSpeed += Utils.coolDownDecrease.get(leggings);
        if (Utils.coolDownDecrease.containsKey(chest)) releaseSpeed += Utils.coolDownDecrease.get(chest);
        if (Utils.coolDownDecrease.containsKey(helmet)) releaseSpeed += Utils.coolDownDecrease.get(helmet);
        if (Utils.mainHandTag.containsKey(mainhand) && Utils.coolDownDecrease.containsKey(mainhand))
            releaseSpeed += Utils.coolDownDecrease.get(mainhand);
        if (Utils.offHandTag.containsKey(offhand) && Utils.coolDownDecrease.containsKey(offhand))
            releaseSpeed += Utils.coolDownDecrease.get(offhand);
        if (SuitCount.getLakeSuitCount(player) >= 2) releaseSpeed += 0.1;
        if (SuitCount.getObsiManaSuitCount(player) >= 4) releaseSpeed += 0.2;
        if (player.getPersistentData().contains("Blue") && player.getPersistentData().getInt("Blue") == 0)
            releaseSpeed += player.getAttribute(Attributes.MOVEMENT_SPEED).getValue() - 0.1F;

        releaseSpeed += handleAllEquipRandomAttribute(player, StringUtils.RandomAttribute.coolDown);

        if (player.getEffect(ModEffects.COOLDOWNUP.get()) != null && player.getEffect(ModEffects.COOLDOWNUP.get()).getAmplifier() == 0)
            releaseSpeed += 0.2;
        if (player.getEffect(ModEffects.COOLDOWNUP.get()) != null && player.getEffect(ModEffects.COOLDOWNUP.get()).getAmplifier() == 1)
            releaseSpeed += 0.4;
        if (data.contains("GemSCoolDown")) releaseSpeed += data.getDouble("GemSCoolDown");
        releaseSpeed += SArmorAttribute.value(player, SArmorAttribute.lakePower);
        if (Compute.getSwordSkillLevel(data, 7) > 0 && Utils.swordTag.containsKey(mainhand))
            releaseSpeed += Compute.getSwordSkillLevel(data, 7) * 0.03; // 冷静（手持近战武器时，获得3%冷却缩减）

        if (Compute.getManaSkillLevel(data, 7) > 0 && Utils.sceptreTag.containsKey(mainhand))
            releaseSpeed += Compute.getManaSkillLevel(data, 7) * 0.03; // 冷静（手持法杖时，获得3%冷却缩减）

        releaseSpeed += EarthPower.PlayerCoolDownEnhance(player); // 地蕴法术

        releaseSpeed += Compute.CuriosAttribute.attributeValue(player, Utils.coolDownDecrease, StringUtils.CuriosAttribute.coolDown); // 新版饰品属性加成
        releaseSpeed += CastleManaArmor.ExAttributeValue(player, CastleManaArmor.ExCoolDownDecrease);
        releaseSpeed += LakeArmorHelmet.exCooldown(player);

        int luckyAbilityPoint = data.getInt(StringUtils.Ability.Lucky);
        if (data.contains(StringUtils.Ability.Lucky) && data.getInt(StringUtils.Ability.Lucky) > 0) {
            releaseSpeed += luckyAbilityPoint * 0.01;
        }

        releaseSpeed += StableAttributesModifier.getModifierValue(player, StableAttributesModifier.playerCooldownModifier);
        releaseSpeed += InCuriosOrEquipSlotAttributesModify.getAttributes(player, Utils.coolDownDecrease);
        if (Utils.sceptreTag.containsKey(mainhand)) {
            releaseSpeed += Compute.getManaSkillLevel(data, 11) * 0.02; // 术法全析
        }

        // 请在上方添加
        releaseSpeed *= Compute.playerFantasyAttributeEnhance(player);

        writeToCache(player, Utils.coolDownDecrease, releaseSpeed);
        return releaseSpeed;
    }

    public static double coolDownDecrease(Player player) {
        return 1 - (1 / (1 + (powerReleaseSpeed(player))));
    }

    public static double defencePenetration(Player player) {
        if (canGetFromCache(player, Utils.defencePenetration)) {
            return getFromCache(player, Utils.defencePenetration);
        }
        int TickCount = player.getServer().getTickCount();
        CompoundTag data = player.getPersistentData();
        double defenceRate = 1;
        Item boots = player.getItemBySlot(EquipmentSlot.FEET).getItem();
        Item leggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item chest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
        Item helmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        Item offhand = player.getItemInHand(InteractionHand.OFF_HAND).getItem();
        CompoundTag helmetTag = player.getItemBySlot(EquipmentSlot.HEAD).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag chestTag = player.getItemBySlot(EquipmentSlot.CHEST).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag leggingsTag = player.getItemBySlot(EquipmentSlot.LEGS).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag bootsTag = player.getItemBySlot(EquipmentSlot.FEET).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag stackmainhandtag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            stackmainhandtag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }
        if (Utils.mainHandTag.containsKey(mainhand) && stackmainhandtag.contains("breakDefence"))
            defenceRate *= (1 - stackmainhandtag.getDouble("breakDefence"));
        if (Utils.defencePenetration.containsKey(boots)) defenceRate *= (1 - Utils.defencePenetration.get(boots));
        if (Utils.defencePenetration.containsKey(leggings))
            defenceRate *= (1 - Utils.defencePenetration.get(leggings));
        if (Utils.defencePenetration.containsKey(chest)) defenceRate *= (1 - Utils.defencePenetration.get(chest));
        if (Utils.defencePenetration.containsKey(helmet)) defenceRate *= (1 - Utils.defencePenetration.get(helmet));
        if (Utils.mainHandTag.containsKey(mainhand) && Utils.defencePenetration.containsKey(mainhand))
            defenceRate *= (1 - Utils.defencePenetration.get(mainhand));
        if (Utils.offHandTag.containsKey(offhand) && Utils.defencePenetration.containsKey(offhand))
            defenceRate *= (1 - Utils.defencePenetration.get(offhand));

        if (player.getEffect(ModEffects.BREAKDefenceUP.get()) != null && player.getEffect(ModEffects.BREAKDefenceUP.get()).getAmplifier() == 0)
            defenceRate *= (1 - 0.20);
        if (player.getEffect(ModEffects.BREAKDefenceUP.get()) != null && player.getEffect(ModEffects.BREAKDefenceUP.get()).getAmplifier() == 1)
            defenceRate *= (1 - 0.45);
        if (data.contains("GemSBreakDefence")) defenceRate *= (1 - data.getDouble("GemSBreakDefence"));

        if (Utils.PlayerSpringHandDefencePenetraionAttribute.containsKey(player) && Utils.PlayerSpringHandLevelRequire.get(player) <= player.experienceLevel) {
            defenceRate *= (1 - Utils.PlayerSpringHandDefencePenetraionAttribute.get(player));
        }

        if (Utils.MeteoriteAttackTimeMap.containsKey(player) && Utils.MeteoriteAttackTimeMap.get(player) > TickCount) {
            defenceRate *= (1 - 0.2);
        }

        defenceRate *= (1 - StableAttributesModifier.getModifierValue(player, StableAttributesModifier.playerDefencePenetrationModifier));

        double decreaseRate = 0;
        if (helmetTag.contains("newGems1")) decreaseRate += GemAttributes.gemsDefencePenetration(helmetTag);
        if (chestTag.contains("newGems1")) decreaseRate += GemAttributes.gemsDefencePenetration(chestTag);
        if (leggingsTag.contains("newGems1")) decreaseRate += GemAttributes.gemsDefencePenetration(leggingsTag);
        if (bootsTag.contains("newGems1")) decreaseRate += GemAttributes.gemsDefencePenetration(bootsTag);
        if (stackmainhandtag.contains("newGems1") && Utils.mainHandTag.containsKey(mainhand))
            decreaseRate += GemAttributes.gemsDefencePenetration(stackmainhandtag);

        if (decreaseRate > 0) defenceRate *= (1 - decreaseRate);
        defenceRate *= (1 - Compute.CuriosAttribute.attributeValue(player, Utils.defencePenetration, StringUtils.CuriosAttribute.defencePenetration)); // 新版饰品属性加成

        // 请在上方添加
        defenceRate *= (2 - Compute.playerFantasyAttributeEnhance(player));

        writeToCache(player, Utils.defencePenetration, 1 - defenceRate);
        return 1 - defenceRate;
    }

    public static double defencePenetration0(Player player) {
        if (canGetFromCache(player, Utils.defencePenetration0)) {
            return getFromCache(player, Utils.defencePenetration0);
        }
        CompoundTag data = player.getPersistentData();
        int TickCount = player.getServer().getTickCount();
        double defencePenetration0 = 0;
        Item boots = player.getItemBySlot(EquipmentSlot.FEET).getItem();
        Item leggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item chest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
        Item helmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        Item offhand = player.getItemInHand(InteractionHand.OFF_HAND).getItem();
        CompoundTag stackmainhandtag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            stackmainhandtag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }

        defencePenetration0 += handleAllEquipRandomAttribute(player, StringUtils.RandomAttribute.defencePenetration0);

        defencePenetration0 += computeAllEquipSlotXpLevelAttributeValue(player, Utils.xpLevelDefencePenetration0, false);
        defencePenetration0 += Compute.CuriosAttribute.attributeValue(player, Utils.xpLevelDefencePenetration0,
                StringUtils.CuriosAttribute.xpLevelDefencePenetration0) * player.experienceLevel;

        // 器灵属性加成
        defencePenetration0 += Compute.PassiveEquip.getAttribute(player, Utils.defencePenetration0);

        if (Utils.defencePenetration0.containsKey(boots)) defencePenetration0 += Utils.defencePenetration0.get(boots);
        if (Utils.defencePenetration0.containsKey(leggings))
            defencePenetration0 += Utils.defencePenetration0.get(leggings);
        if (Utils.defencePenetration0.containsKey(chest)) defencePenetration0 += Utils.defencePenetration0.get(chest);
        if (Utils.defencePenetration0.containsKey(helmet)) defencePenetration0 += Utils.defencePenetration0.get(helmet);
        if (Utils.mainHandTag.containsKey(mainhand) && Utils.defencePenetration0.containsKey(mainhand))
            defencePenetration0 += Utils.defencePenetration0.get(mainhand);
        if (Utils.offHandTag.containsKey(offhand) && Utils.defencePenetration0.containsKey(offhand))
            defencePenetration0 += Utils.defencePenetration0.get(offhand);
        if (Compute.getSwordSkillLevel(data, 10) > 0 && Utils.swordTag.containsKey(mainhand))
            defencePenetration0 += Compute.getSwordSkillLevel(data, 10) * 3;
        if (Compute.getBowSkillLevel(data, 10) > 0 && Utils.bowTag.containsKey(mainhand))
            defencePenetration0 += Compute.getBowSkillLevel(data, 10) * 3;

        if (data.getInt(StringUtils.WitherBow.Effect3) > TickCount) defencePenetration0 += 4;
        else if (data.getInt(StringUtils.WitherBow.Effect2) > TickCount) defencePenetration0 += 3;
        else if (data.getInt(StringUtils.WitherBow.Effect1) > TickCount) defencePenetration0 += 2;
        else if (data.getInt(StringUtils.WitherBow.Effect0) > TickCount) defencePenetration0 += 1;

        if (stackmainhandtag.contains(StringUtils.SoulEquipForge) && (Utils.swordTag.containsKey(mainhand) || Utils.bowTag.containsKey(mainhand)))
            defencePenetration0 +=
                    stackmainhandtag.getInt(StringUtils.SoulEquipForge) * SoulEquipAttribute.ForgingAddition.DefencePenetration0;

        if (mainhand.equals(ModItems.ShipBow.get())) {
            List<Player> serverPlayerList = player.level().getEntitiesOfClass(Player.class, AABB.ofSize(player.position(), 20, 20, 20));
            AtomicInteger Count = new AtomicInteger();
            serverPlayerList.forEach(player1 -> {
                if (player1.distanceTo(player) <= 6) Count.getAndIncrement();
            });
            defencePenetration0 += 2.5 * Math.min(Count.get(), 4);
        }

        if (Utils.IceBowEffectMap.containsKey(player) && Utils.IceBowEffectMap.get(player) > TickCount) {
            defencePenetration0 += Utils.IceBowEffectNumMap.get(player) / 200;
        } //冰霜弓

        if (Utils.PlayerSpringRingDefencePenetration0Attribute.containsKey(player) && Utils.PlayerSpringRingLevelRequire.get(player) <= player.experienceLevel) {
            defencePenetration0 += Utils.PlayerSpringRingDefencePenetration0Attribute.get(player) * 0.01;
        }

        if (Utils.PlayerSpringBeltDefencePenetration0Attribute.containsKey(player) && Utils.PlayerSpringBeltLevelRequire.get(player) <= player.experienceLevel) {
            defencePenetration0 += Utils.PlayerSpringBeltDefencePenetration0Attribute.get(player) * 0.01;
        }

        CompoundTag helmetTag = player.getItemBySlot(EquipmentSlot.HEAD).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag chestTag = player.getItemBySlot(EquipmentSlot.CHEST).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag leggingsTag = player.getItemBySlot(EquipmentSlot.LEGS).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag bootsTag = player.getItemBySlot(EquipmentSlot.FEET).getOrCreateTagElement(Utils.MOD_ID);
        if (helmetTag.contains("newGems1")) defencePenetration0 += GemAttributes.gemsDefencePenetration0(helmetTag);
        if (chestTag.contains("newGems1")) defencePenetration0 += GemAttributes.gemsDefencePenetration0(chestTag);
        if (leggingsTag.contains("newGems1")) defencePenetration0 += GemAttributes.gemsDefencePenetration0(leggingsTag);
        if (bootsTag.contains("newGems1")) defencePenetration0 += GemAttributes.gemsDefencePenetration0(bootsTag);
        if (stackmainhandtag.contains("newGems1") && Utils.mainHandTag.containsKey(mainhand))
            defencePenetration0 += GemAttributes.gemsDefencePenetration0(stackmainhandtag);

        defencePenetration0 += Compute.CuriosAttribute.attributeValue(player, Utils.defencePenetration0, StringUtils.CuriosAttribute.defencePenetration0); // 新版饰品属性加成

        defencePenetration0 += StableAttributesModifier.getModifierValue(player, StableAttributesModifier.playerDefencePenetration0Modifier);
        defencePenetration0 += InCuriosOrEquipSlotAttributesModify.getAttributes(player, Utils.defencePenetration0);
        // 请在上方添加
        defencePenetration0 *= Compute.playerFantasyAttributeEnhance(player);

        writeToCache(player, Utils.defencePenetration0, defencePenetration0);
        return defencePenetration0;
    }

    public static double healthRecover(Player player) {
        if (canGetFromCache(player, Utils.healthRecover)) {
            return getFromCache(player, Utils.healthRecover);
        }
        double healthRecover = 5 + player.experienceLevel * 0.1;
        CompoundTag data = player.getPersistentData();

        // 处理随机属性装备
        healthRecover += handleAllEquipRandomAttribute(player, StringUtils.RandomAttribute.healthRecover);

        // 处理宝石属性
        healthRecover += GemAttributes.getPlayerCurrentAllEquipGemsValue(player, Utils.healthRecover);

        // 处理基础属性
        healthRecover += computeAllEquipSlotBaseAttributeValue(player, Utils.healthRecover, true);

        // 处理主动被动等额外属性
        if (SuitCount.getPlainSuitCount(player) >= 2) healthRecover += 0.5 + player.getMaxHealth() * 0.01F;
        if (SuitCount.getLifeManaSuitCount(player) >= 4) healthRecover += 1 + player.getMaxHealth() * 0.01F;
        if (!Utils.OverWorldLevelIsNight && SuitCount.getForestSuitCount(player) >= 4) healthRecover += 5;

        if (player.getEffect(ModEffects.HEALTHRECOVER.get()) != null && player.getEffect(ModEffects.HEALTHRECOVER.get()).getAmplifier() == 0)
            healthRecover += player.getMaxHealth() * 0.025;
        if (player.getEffect(ModEffects.HEALTHRECOVER.get()) != null && player.getEffect(ModEffects.HEALTHRECOVER.get()).getAmplifier() == 1)
            healthRecover += player.getMaxHealth() * 0.05;

        int vitalityAbilityPoint = data.getInt(StringUtils.Ability.Vitality);
        if (data.contains(StringUtils.Ability.Vitality) && data.getInt(StringUtils.Ability.Vitality) > 0) {
            healthRecover += vitalityAbilityPoint;
        }

        healthRecover += Compute.CuriosAttribute.attributeValue(player, Utils.healthRecover, StringUtils.CuriosAttribute.healthRecover); // 新版饰品属性加成
        healthRecover += PlainNewRune.playerHealthRecover(player);
        healthRecover += ForestNewRune.playerHealthRecoverUp(player);

        healthRecover += StableAttributesModifier.getModifierValue(player, StableAttributesModifier.playerHealthRecoverModifier);

        // 最大生命值百分比生命回复
        healthRecover += computeAllEquipSlotBaseAttributeValue(player, Utils.percentHealthRecover, true) * player.getMaxHealth();

        // 请在上方添加
        healthRecover *= Compute.playerFantasyAttributeEnhance(player);

        writeToCache(player, Utils.healthRecover, healthRecover);
        return healthRecover;
    }

    public static double maxHealth(Player player) {
        if (canGetFromCache(player, Utils.maxHealth)) {
            return getFromCache(player, Utils.maxHealth);
        }
        double maxHealth = 200 + player.experienceLevel * 10;
        CompoundTag data = player.getPersistentData();
        Item boots = player.getItemBySlot(EquipmentSlot.FEET).getItem();
        Item leggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item chest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
        Item helmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        Item offhand = player.getItemInHand(InteractionHand.OFF_HAND).getItem();
        CompoundTag helmetTag = player.getItemBySlot(EquipmentSlot.HEAD).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag chestTag = player.getItemBySlot(EquipmentSlot.CHEST).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag leggingsTag = player.getItemBySlot(EquipmentSlot.LEGS).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag bootsTag = player.getItemBySlot(EquipmentSlot.FEET).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag stackmainhandtag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            stackmainhandtag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }
        if (helmetTag.contains("newGems1")) maxHealth += GemAttributes.gemsMaxHealth(helmetTag);
        if (chestTag.contains("newGems1")) maxHealth += GemAttributes.gemsMaxHealth(chestTag);
        if (leggingsTag.contains("newGems1")) maxHealth += GemAttributes.gemsMaxHealth(leggingsTag);
        if (bootsTag.contains("newGems1")) maxHealth += GemAttributes.gemsMaxHealth(bootsTag);
        if (stackmainhandtag.contains("newGems1") && Utils.mainHandTag.containsKey(mainhand))
            maxHealth += GemAttributes.gemsMaxHealth(stackmainhandtag);

        if (Utils.maxHealth.containsKey(boots))
            maxHealth += ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.FEET), Utils.maxHealth);
        if (Utils.maxHealth.containsKey(leggings))
            maxHealth += ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.LEGS), Utils.maxHealth);
        if (Utils.maxHealth.containsKey(chest))
            maxHealth += ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.CHEST), Utils.maxHealth);
        if (Utils.maxHealth.containsKey(helmet))
            maxHealth += ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.HEAD), Utils.maxHealth);

        maxHealth += handleAllEquipRandomAttribute(player, StringUtils.RandomAttribute.maxHealth);

        if (Utils.maxHealth.containsKey(helmet) && helmetTag.contains("Forging"))
            maxHealth += Compute.forgingValue(helmetTag, ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.HEAD), Utils.maxHealth));
        if (Utils.maxHealth.containsKey(chest) && chestTag.contains("Forging"))
            maxHealth += Compute.forgingValue(chestTag, ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.CHEST), Utils.maxHealth));
        if (Utils.maxHealth.containsKey(leggings) && leggingsTag.contains("Forging"))
            maxHealth += Compute.forgingValue(leggingsTag, ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.LEGS), Utils.maxHealth));
        if (Utils.maxHealth.containsKey(boots) && bootsTag.contains("Forging"))
            maxHealth += Compute.forgingValue(bootsTag, ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.FEET), Utils.maxHealth));

        if (Utils.mainHandTag.containsKey(mainhand) && Utils.maxHealth.containsKey(mainhand))
            maxHealth += Utils.maxHealth.get(mainhand);
        if (Utils.offHandTag.containsKey(offhand) && Utils.maxHealth.containsKey(offhand))
            maxHealth += Utils.maxHealth.get(offhand);
        if (SuitCount.getPlainSuitCount(player) >= 4) maxHealth += 200;
        if (data.contains("Barker")) maxHealth *= 1.0d + (data.getInt("Barker") / 100000.0d) * 0.05F;

        if (data.contains("GemSMaxHeal")) maxHealth += data.getDouble("GemSMaxHeal");
        maxHealth += SArmorAttribute.value(player, SArmorAttribute.sumPower);

        int vitalityAbilityPoint = data.getInt(StringUtils.Ability.Vitality);
        if (data.contains(StringUtils.Ability.Vitality) && data.getInt(StringUtils.Ability.Vitality) > 0) {
            maxHealth += vitalityAbilityPoint * 10;
        }

        if (Utils.PlayerSpringHandMaxHealthAttribute.containsKey(player) && Utils.PlayerSpringHandLevelRequire.get(player) <= player.experienceLevel) {
            maxHealth += Utils.PlayerSpringHandMaxHealthAttribute.get(player);
        }

        maxHealth += Compute.CuriosAttribute.attributeValue(player, Utils.maxHealth, StringUtils.CuriosAttribute.maxHealth); // 新版饰品属性加成

        maxHealth += Compute.PassiveEquip.getAttribute(player, Utils.maxHealth); // 器灵属性加成
        maxHealth += CastleAttackArmor.ExAttributeValue(player, CastleAttackArmor.ExMaxHealth);
        maxHealth += CastleManaArmor.ExAttributeValue(player, CastleManaArmor.ExMaxHealth);
        maxHealth += CastleSwiftArmor.ExAttributeValue(player, CastleSwiftArmor.ExMaxHealth);
        maxHealth += PlainArmorHelmet.exMaxHealth(player);
        // 请在上方添加
        maxHealth *= Compute.playerFantasyAttributeEnhance(player);
        maxHealth *= (1 + Compute.CuriosAttribute.attributeValue(player, Utils.percentMaxHealthEnhance,
                StringUtils.CuriosAttribute.percentMaxHealthEnhance)); // 新版饰品属性加成
        maxHealth *= (1 + GemAttributes.getPlayerCurrentAllEquipGemsValue(player, Utils.percentMaxHealthEnhance) +
                Compute.CuriosAttribute.attributeValue(player, Utils.percentMaxHealthEnhance,
                StringUtils.CuriosAttribute.percentMaxHealthEnhance));
        maxHealth *= (1 +
                Compute.getPlayerPotionEffectRate(player, ModEffects.GIANT.get(), 0.15, 0.25));

        writeToCache(player, Utils.maxHealth, maxHealth);
        return maxHealth;
    }

    public static double manaDamage(Player player) {
        if (canGetFromCache(player, Utils.manaDamage)) {
            return getFromCache(player, Utils.manaDamage);
        }

        int tickCount = player.getServer().getTickCount();
        String name = player.getName().getString();
        double baseDamage = 0.0d;
        double exDamage = 0;
        Item boots = player.getItemBySlot(EquipmentSlot.FEET).getItem();
        Item leggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item chest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
        Item helmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        Item offhand = player.getItemInHand(InteractionHand.OFF_HAND).getItem();
        CompoundTag data = player.getPersistentData();
        CompoundTag mainHandItemTag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            mainHandItemTag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }
        CompoundTag helmetTag = player.getItemBySlot(EquipmentSlot.HEAD).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag chestTag = player.getItemBySlot(EquipmentSlot.CHEST).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag leggingsTag = player.getItemBySlot(EquipmentSlot.LEGS).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag bootsTag = player.getItemBySlot(EquipmentSlot.FEET).getOrCreateTagElement(Utils.MOD_ID);
        if (Utils.mainHandTag.containsKey(mainhand) && Utils.manaDamage.containsKey(mainhand))
            baseDamage += ForgeEquipUtils.getTraditionalEquipBaseValue(player.getMainHandItem(), Utils.manaDamage);

        // Fixed:防具攻击强化失效
        if (Utils.armorTag.containsKey(helmet) && Utils.manaDamage.containsKey(helmet) && helmetTag.contains("Forging"))
            baseDamage += Compute.forgingValue(helmetTag, ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.HEAD), Utils.manaDamage));
        if (Utils.armorTag.containsKey(chest) && Utils.manaDamage.containsKey(chest) && chestTag.contains("Forging"))
            baseDamage += Compute.forgingValue(chestTag, ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.CHEST), Utils.manaDamage));
        if (Utils.armorTag.containsKey(leggings) && Utils.manaDamage.containsKey(leggings) && leggingsTag.contains("Forging"))
            baseDamage += Compute.forgingValue(leggingsTag, ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.LEGS), Utils.manaDamage));
        if (Utils.armorTag.containsKey(boots) && Utils.manaDamage.containsKey(boots) && bootsTag.contains("Forging"))
            baseDamage += Compute.forgingValue(bootsTag, ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.FEET), Utils.manaDamage));

        baseDamage += handleAllEquipRandomAttribute(player, StringUtils.RandomAttribute.manaDamage);

        if (Utils.mainHandTag.containsKey(mainhand) && mainHandItemTag.contains("manadamage"))
            baseDamage += mainHandItemTag.getDouble("manadamage");
        if (Utils.mainHandTag.containsKey(mainhand) && Utils.manaDamage.containsKey(mainhand) && mainHandItemTag.contains("Forging"))
            baseDamage += Compute.forgingValue(mainHandItemTag, ForgeEquipUtils.getTraditionalEquipBaseValue(player.getMainHandItem(), Utils.manaDamage));
        if (Utils.manaDamage.containsKey(boots))
            baseDamage += ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.FEET), Utils.manaDamage);
        if (Utils.manaDamage.containsKey(leggings))
            baseDamage += ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.LEGS), Utils.manaDamage);
        if (Utils.manaDamage.containsKey(chest))
            baseDamage += ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.CHEST), Utils.manaDamage);
        if (Utils.manaDamage.containsKey(helmet))
            baseDamage += ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.HEAD), Utils.manaDamage);
        if (Utils.offHandTag.containsKey(offhand) && Utils.manaDamage.containsKey(offhand))
            baseDamage += ForgeEquipUtils.getTraditionalEquipBaseValue(player.getOffhandItem(), Utils.manaDamage);

        baseDamage += computeAllEquipSlotXpLevelAttributeValue(player, Utils.xpLevelManaDamage, true);
        exDamage += Compute.CuriosAttribute.attributeValue(player, Utils.xpLevelManaDamage,
                StringUtils.CuriosAttribute.xpLevelManaDamage) * player.experienceLevel;
        exDamage += Compute.PassiveEquip.getAttribute(player, Utils.xpLevelManaDamage) * player.experienceLevel;

        if (helmetTag.contains("newGems1")) exDamage += GemAttributes.gemsManaDamage(helmetTag);
        if (chestTag.contains("newGems1")) exDamage += GemAttributes.gemsManaDamage(chestTag);
        if (leggingsTag.contains("newGems1")) exDamage += GemAttributes.gemsManaDamage(leggingsTag);
        if (bootsTag.contains("newGems1")) exDamage += GemAttributes.gemsManaDamage(bootsTag);

        if (mainHandItemTag.contains("newGems1")) exDamage += GemAttributes.gemsManaDamage(mainHandItemTag);

        if (player.getEffect(ModEffects.MANADAMAGEUP.get()) != null && player.getEffect(ModEffects.MANADAMAGEUP.get()).getAmplifier() == 0)
            exDamage += baseDamage * 0.25 + 25;
        if (player.getEffect(ModEffects.MANADAMAGEUP.get()) != null && player.getEffect(ModEffects.MANADAMAGEUP.get()).getAmplifier() == 1)
            exDamage += baseDamage * 0.4 + 40;
        if (data.contains("GemSManaDamage")) exDamage += data.getDouble("GemSManaDamage");
        exDamage += SArmorAttribute.value(player, SArmorAttribute.manaPower);

        int intelligentAbilityPoint = data.getInt(StringUtils.Ability.Intelligent);
        if (data.contains(StringUtils.Ability.Intelligent) && data.getInt(StringUtils.Ability.Intelligent) > 0) {
            exDamage += intelligentAbilityPoint * 2;
        } // 能力

        if (Compute.getManaSkillLevel(data, 2) > 0 && data.contains(StringUtils.ManaSkillNum.Skill2) && data.getInt(StringUtils.ManaSkillNum.Skill2) > tickCount) {
            exDamage += baseDamage * Compute.getManaSkillLevel(data, 2) * 0.02;
        } // 战斗渴望（击杀一个单位时，提升2%攻击力，持续10s）

        if (leggings.equals(ModItems.MinePants.get()) && (Utils.OverWorldLevelIsNight || player.getY() < 63))
            exDamage += 75;
        // 矿工裤被动

        if (data.getInt(StringUtils.Crest.Mana.Crest0) > 0)
            exDamage += ManaCrestAttributes.ExManaDamage[0] * data.getInt(StringUtils.Crest.Mana.Crest0);
        if (data.getInt(StringUtils.Crest.Mana.Crest1) > 0)
            exDamage += ManaCrestAttributes.ExManaDamage[1] * data.getInt(StringUtils.Crest.Mana.Crest1);
        if (data.getInt(StringUtils.Crest.Mana.Crest2) > 0)
            exDamage += ManaCrestAttributes.ExManaDamage[2] * data.getInt(StringUtils.Crest.Mana.Crest2);
        if (data.getInt(StringUtils.Crest.Mana.Crest3) > 0)
            exDamage += ManaCrestAttributes.ExManaDamage[3] * data.getInt(StringUtils.Crest.Mana.Crest3);
        if (data.getInt(StringUtils.Crest.Mana.Crest4) > 0)
            exDamage += ManaCrestAttributes.ExManaDamage[4] * data.getInt(StringUtils.Crest.Mana.Crest4);

        if (SuitCount.getVolcanoSuitCount(player) >= 2) exDamage += baseDamage * 0.25F;
        if (SuitCount.getObsiManaSuitCount(player) >= 4) exDamage += baseDamage * 0.25F;

        if (mainHandItemTag.contains(StringUtils.SoulEquipForge) && Utils.sceptreTag.containsKey(mainhand))
            exDamage +=
                    mainHandItemTag.getInt(StringUtils.SoulEquipForge) * SoulEquipAttribute.ForgingAddition.ManaAttackDamage;

        if (Utils.PlayerSpringRingManaAttackAttribute.containsKey(player) && Utils.PlayerSpringRingLevelRequire.get(player) <= player.experienceLevel) {
            exDamage += Utils.PlayerSpringRingManaAttackAttribute.get(player);
        }

        if (Utils.PlayerSpringBraceletAttackAttribute.containsKey(player) && Utils.PlayerSpringBraceletLevelRequire.get(player) <= player.experienceLevel) {
            exDamage += Utils.PlayerSpringBraceletAttackAttribute.get(player);
        }

        if (Utils.PlayerFireWorkGunEffect.containsKey(player) && Utils.PlayerFireWorkGunEffect.get(player) > tickCount) {
            exDamage += baseDamage * 0.1;
        } //新春礼炮

        if (Utils.IceSceptreEffectMap.containsKey(player) && Utils.IceSceptreEffectMap.get(player) > tickCount) {
            exDamage += Utils.IceSceptreEffectNumMap.get(player) * 2;
        } //天源流光

        if (Utils.SpringScaleTime.containsKey(player) && Utils.SpringScaleTime.get(player) > tickCount) {
            int SwordSkill = data.getInt(StringUtils.SkillArray[0]);
            int BowSkill = data.getInt(StringUtils.SkillArray[1]);
            int ManaSkill = data.getInt(StringUtils.SkillArray[2]);
            if (ManaSkill > Math.max(SwordSkill, BowSkill))
                exDamage += (Utils.SpringScaleEffect.get(player) + 1) * 0.1 * baseDamage;
        } //年兽鳞片

        if (Utils.MeteoriteAttackTimeMap.containsKey(player) && Utils.MeteoriteAttackTimeMap.get(player) > tickCount) {
            exDamage += baseDamage * 0.1;
        }

        if (Utils.EarthManaCurios.containsKey(player) && Utils.EarthManaCurios.get(player)) exDamage += 400;

        if (Utils.DevilEarthManaCurios.containsKey(player) && Utils.DevilEarthManaCurios.get(player)) exDamage += 1600;

        exDamage += baseDamage * EarthPower.PlayerDamageEnhance(player);
        exDamage += Compute.CuriosAttribute.attributeValue(player, Utils.manaDamage, StringUtils.CuriosAttribute.manaDamage); // 新版饰品属性加成

        exDamage += Compute.PassiveEquip.getAttribute(player, Utils.manaDamage); // 器灵属性加成
        exDamage += CastleManaArmor.ExAttributeValue(player, CastleManaArmor.ExManaDamage);
        exDamage += LifeElementSceptre.ExManaDamage(player);
        exDamage += VolcanoArmorHelmet.exManaDamage(player);
        exDamage += CastleNewRune.manaDamage(player);
        exDamage += InCuriosOrEquipSlotAttributesModify.getAttributes(player, Utils.manaDamage);
        exDamage += ChangedAttributesModifier.getModifierValue(player, ChangedAttributesModifier.exManaDamage);
        exDamage += InCuriosOrEquipSlotAttributesModify.getAttributes(player, Utils.manaDamage);
        // 请在上方添加
        double totalDamage = baseDamage + exDamage;
        totalDamage *= (1 + MoonBook.damageEnhance(player));
        totalDamage *= Compute.playerFantasyAttributeEnhance(player);
        totalDamage *= ManaCurios0.PlayerFinalManaDamageEnhance(player);
        totalDamage *= ManaCurios2.playerFinalManaDamageEnhance(player);
        totalDamage *= (1 + GemAttributes.getPlayerCurrentAllEquipGemsValue(player, Utils.percentManaDamageEnhance) +
                Compute.CuriosAttribute.attributeValue(player, Utils.percentManaDamageEnhance,
                StringUtils.CuriosAttribute.percentManaDamageEnhance));

        Utils.playerManaDamageBeforeTransform.put(player, totalDamage);

        if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.DevilManaHelmet.get())) {
            totalDamage *= 1.25;
        }

        writeToCache(player, Utils.manaDamage, totalDamage);
        return totalDamage;
    }

    public static double manaHealthSteal(Player player) {
        if (canGetFromCache(player, Utils.manaHealthSteal)) {
            return getFromCache(player, Utils.manaHealthSteal);
        }
        double manaHealSteal = 0.0d;
        Item boots = player.getItemBySlot(EquipmentSlot.FEET).getItem();
        Item leggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item chest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
        Item helmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        Item offhand = player.getItemInHand(InteractionHand.OFF_HAND).getItem();
        CompoundTag stackmainhandtag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            stackmainhandtag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }
        if (Utils.mainHandTag.containsKey(mainhand) && stackmainhandtag.contains("ManaHealSteal"))
            manaHealSteal += stackmainhandtag.getDouble("ManaHealSteal");
        if (Utils.manaHealthSteal.containsKey(boots)) manaHealSteal += Utils.manaHealthSteal.get(boots);
        if (Utils.manaHealthSteal.containsKey(leggings)) manaHealSteal += Utils.manaHealthSteal.get(leggings);
        if (Utils.manaHealthSteal.containsKey(chest)) manaHealSteal += Utils.manaHealthSteal.get(chest);
        if (Utils.manaHealthSteal.containsKey(helmet)) manaHealSteal += Utils.manaHealthSteal.get(helmet);
        if (Utils.mainHandTag.containsKey(mainhand) && Utils.manaHealthSteal.containsKey(mainhand))
            manaHealSteal += Utils.manaHealthSteal.get(mainhand);
        if (Utils.offHandTag.containsKey(offhand) && Utils.manaHealthSteal.containsKey(offhand))
            manaHealSteal += Utils.manaHealthSteal.get(offhand);
        if (SuitCount.getLifeManaSuitCount(player) >= 4) manaHealSteal += 0.05;
        if (Utils.EarthManaCurios.containsKey(player) && Utils.EarthManaCurios.get(player)) manaHealSteal += 0.05;

        CompoundTag helmetTag = player.getItemBySlot(EquipmentSlot.HEAD).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag chestTag = player.getItemBySlot(EquipmentSlot.CHEST).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag leggingsTag = player.getItemBySlot(EquipmentSlot.LEGS).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag bootsTag = player.getItemBySlot(EquipmentSlot.FEET).getOrCreateTagElement(Utils.MOD_ID);
        if (helmetTag.contains("newGems1")) manaHealSteal += GemAttributes.gemsManaHealthSteal(helmetTag);
        if (chestTag.contains("newGems1")) manaHealSteal += GemAttributes.gemsManaHealthSteal(chestTag);
        if (leggingsTag.contains("newGems1")) manaHealSteal += GemAttributes.gemsManaHealthSteal(leggingsTag);
        if (bootsTag.contains("newGems1")) manaHealSteal += GemAttributes.gemsManaHealthSteal(bootsTag);
        if (stackmainhandtag.contains("newGems1") && Utils.mainHandTag.containsKey(mainhand))
            manaHealSteal += GemAttributes.gemsManaHealthSteal(stackmainhandtag);

        if (Utils.DevilEarthManaCurios.containsKey(player) && Utils.DevilEarthManaCurios.get(player))
            manaHealSteal += 0.05;

        manaHealSteal += Compute.CuriosAttribute.attributeValue(player, Utils.manaHealthSteal, StringUtils.CuriosAttribute.manaHealthSteal); // 新版饰品属性加成
        //请在上方添加
        manaHealSteal *= Compute.playerFantasyAttributeEnhance(player);

        writeToCache(player, Utils.manaHealthSteal, manaHealSteal);
        return manaHealSteal;
    }

    public static double manaRecover(Player player) {
        if (player.isCreative()) return 1000;
        if (canGetFromCache(player, Utils.manaRecover)) {
            return getFromCache(player, Utils.manaRecover);
        }
        double manaRecover = player.experienceLevel * 0.1;
        Item boots = player.getItemBySlot(EquipmentSlot.FEET).getItem();
        Item leggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item chest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
        Item helmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        Item offhand = player.getItemInHand(InteractionHand.OFF_HAND).getItem();
        CompoundTag data = player.getPersistentData();
        CompoundTag stackmainhandtag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            stackmainhandtag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }

        manaRecover += handleAllEquipRandomAttribute(player, StringUtils.RandomAttribute.manaRecover);

        CompoundTag helmetTag = player.getItemBySlot(EquipmentSlot.HEAD).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag chestTag = player.getItemBySlot(EquipmentSlot.CHEST).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag leggingsTag = player.getItemBySlot(EquipmentSlot.LEGS).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag bootsTag = player.getItemBySlot(EquipmentSlot.FEET).getOrCreateTagElement(Utils.MOD_ID);
        if (helmetTag.contains("newGems1")) manaRecover += GemAttributes.gemsManaRecover(helmetTag);
        if (chestTag.contains("newGems1")) manaRecover += GemAttributes.gemsManaRecover(chestTag);
        if (leggingsTag.contains("newGems1")) manaRecover += GemAttributes.gemsManaRecover(leggingsTag);
        if (bootsTag.contains("newGems1")) manaRecover += GemAttributes.gemsManaRecover(bootsTag);
        if (Utils.mainHandTag.containsKey(mainhand) && stackmainhandtag.contains("manareply"))
            manaRecover += stackmainhandtag.getDouble("manareply");
        if (Utils.manaRecover.containsKey(boots)) manaRecover += Utils.manaRecover.get(boots);
        if (Utils.manaRecover.containsKey(leggings)) manaRecover += Utils.manaRecover.get(leggings);
        if (Utils.manaRecover.containsKey(chest)) manaRecover += Utils.manaRecover.get(chest);
        if (Utils.manaRecover.containsKey(helmet)) manaRecover += Utils.manaRecover.get(helmet);
        if (Utils.mainHandTag.containsKey(mainhand) && Utils.manaRecover.containsKey(mainhand))
            manaRecover += Utils.manaRecover.get(mainhand);
        if (Utils.offHandTag.containsKey(offhand) && Utils.manaRecover.containsKey(offhand))
            manaRecover += Utils.manaRecover.get(offhand);
        if (SuitCount.getLifeManaSuitCount(player) >= 2) manaRecover += 5;
        if (SuitCount.getObsiManaSuitCount(player) >= 2) manaRecover += 5;
        if (stackmainhandtag.contains("newGems1")) manaRecover += GemAttributes.gemsManaRecover(stackmainhandtag);

        if (player.getEffect(ModEffects.MANAREPLYUP.get()) != null && player.getEffect(ModEffects.MANAREPLYUP.get()).getAmplifier() == 0)
            manaRecover += 20;
        if (player.getEffect(ModEffects.MANAREPLYUP.get()) != null && player.getEffect(ModEffects.MANAREPLYUP.get()).getAmplifier() == 1)
            manaRecover += 45;
        if (data.contains("GemSManaReply")) manaRecover += data.getDouble("GemSManaReply");
        if (Compute.getSwordSkillLevel(data, 8) > 0 && Utils.swordTag.containsKey(mainhand))
            manaRecover += Compute.getSwordSkillLevel(data, 8); // 洞悉（手持近战武器时，获得1额外法力回复）
        if (Compute.getManaSkillLevel(data, 1) > 0 && Utils.sceptreTag.containsKey(mainhand))
            manaRecover += Compute.getManaSkillLevel(data, 1) * 0.5; // 仙女护符（持有法杖时，获得额外0.5点法力回复）
        if (Compute.getManaSkillLevel(data, 8) > 0 && Utils.sceptreTag.containsKey(mainhand))
            manaRecover += Compute.getManaSkillLevel(data, 8); // 洞悉（手持法杖时，获得1额外法力回复）

        if (stackmainhandtag.contains(StringUtils.SoulEquipForge) && Utils.sceptreTag.containsKey(mainhand))
            manaRecover +=
                    stackmainhandtag.getInt(StringUtils.SoulEquipForge) * SoulEquipAttribute.ForgingAddition.ManaRecover;

        if (Utils.PlayerSpringBraceletManaRecoverAttribute.containsKey(player) && Utils.PlayerSpringBraceletLevelRequire.get(player) <= player.experienceLevel) {
            manaRecover += Utils.PlayerSpringBraceletManaRecoverAttribute.get(player);
        }

        manaRecover += Compute.CuriosAttribute.attributeValue(player, Utils.manaRecover, StringUtils.CuriosAttribute.manaRecover); // 新版饰品属性加成
        manaRecover += StableAttributesModifier.getModifierValue(player, StableAttributesModifier.playerManaRecoverModifier);
        manaRecover += InCuriosOrEquipSlotAttributesModify.getAttributes(player, Utils.manaRecover);
        // 请在上方添加
        manaRecover *= Compute.playerFantasyAttributeEnhance(player);

        writeToCache(player, Utils.manaRecover, manaRecover);
        return manaRecover;
    }

    public static double manaDefence(Player player) {
        if (canGetFromCache(player, Utils.manaDefence)) {
            return getFromCache(player, Utils.manaDefence);
        }
        int tickCount = player.getServer().getTickCount();
        CompoundTag data = player.getPersistentData();
        double basicDefence = player.experienceLevel * 0.2;
        double exDefence = 0.0d;
        Item boots = player.getItemBySlot(EquipmentSlot.FEET).getItem();
        Item leggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item chest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
        Item helmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        Item offhand = player.getItemInHand(InteractionHand.OFF_HAND).getItem();
        CompoundTag helmetTag = player.getItemBySlot(EquipmentSlot.HEAD).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag chestTag = player.getItemBySlot(EquipmentSlot.CHEST).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag leggingsTag = player.getItemBySlot(EquipmentSlot.LEGS).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag bootsTag = player.getItemBySlot(EquipmentSlot.FEET).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag stackmainhandtag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            stackmainhandtag = player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID);
        }
        if (Utils.manaDefence.containsKey(boots))
            basicDefence += ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.FEET), Utils.manaDefence);
        if (Utils.manaDefence.containsKey(leggings))
            basicDefence += ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.LEGS), Utils.manaDefence);
        if (Utils.manaDefence.containsKey(chest))
            basicDefence += ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.CHEST), Utils.manaDefence);
        if (Utils.manaDefence.containsKey(helmet))
            basicDefence += ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.HEAD), Utils.manaDefence);

        if (Utils.offHandTag.containsKey(offhand) && Utils.manaDefence.containsKey(offhand))
            basicDefence += Utils.manaDefence.get(offhand);
        if (Utils.manaDefence.containsKey(helmet) && helmetTag.contains("Forging"))
            basicDefence += Compute.forgingValue(helmetTag, ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.HEAD), Utils.manaDefence));
        if (Utils.manaDefence.containsKey(chest) && chestTag.contains("Forging"))
            basicDefence += Compute.forgingValue(chestTag, ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.CHEST), Utils.manaDefence));
        if (Utils.manaDefence.containsKey(leggings) && leggingsTag.contains("Forging"))
            basicDefence += Compute.forgingValue(leggingsTag, ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.LEGS), Utils.manaDefence));
        if (Utils.manaDefence.containsKey(boots) && bootsTag.contains("Forging"))
            basicDefence += Compute.forgingValue(bootsTag, ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.FEET), Utils.manaDefence));

        // 计算线性等级强度装备数值
        basicDefence += computeAllEquipSlotXpLevelAttributeValue(player, Utils.xpLevelManaDefence, false);

        basicDefence += Compute.CuriosAttribute.attributeValue(player, Utils.xpLevelManaDefence,
                StringUtils.CuriosAttribute.xpLevelManaDefence) * player.experienceLevel;

        // 以下为额外魔法抗性
        if (Utils.mainHandTag.containsKey(mainhand) && Utils.manaDefence.containsKey(mainhand))
            exDefence += Utils.manaDefence.get(mainhand);
        if (boots instanceof ForestArmorBoots && leggings instanceof ForestArmorLeggings
                && chest instanceof ForestArmorChest && helmet instanceof ForestArmorHelmet)
            exDefence += basicDefence * 0.25;
        if (LifeManaArmor.getPlayerLifeManaArmorCount(player) == 4) exDefence += basicDefence * 0.25;
        if (player.getEffect(ModEffects.MANADefenceUP.get()) != null && player.getEffect(ModEffects.MANADefenceUP.get()).getAmplifier() == 0)
            exDefence += basicDefence * 0.25 + 3;
        if (player.getEffect(ModEffects.MANADefenceUP.get()) != null && player.getEffect(ModEffects.MANADefenceUP.get()).getAmplifier() == 1)
            exDefence += basicDefence * 0.4 + 6;

        if (data.getInt(StringUtils.PlainSwordActive.PlainSceptre) > tickCount) exDefence += 4;

        if (helmetTag.contains("newGems1")) exDefence += GemAttributes.gemsManaDefence(helmetTag);
        if (chestTag.contains("newGems1")) exDefence += GemAttributes.gemsManaDefence(chestTag);
        if (leggingsTag.contains("newGems1")) exDefence += GemAttributes.gemsManaDefence(leggingsTag);
        if (bootsTag.contains("newGems1")) exDefence += GemAttributes.gemsManaDefence(bootsTag);
        if (stackmainhandtag.contains("newGems1") && Utils.mainHandTag.containsKey(mainhand))
            exDefence += GemAttributes.gemsManaDefence(stackmainhandtag);

        if (Utils.EarthManaCurios.containsKey(player) && Utils.EarthManaCurios.get(player)) exDefence += 2;
        if (Utils.BloodManaCurios.containsKey(player) && Utils.BloodManaCurios.get(player)) exDefence += 2;
        if (Utils.DevilEarthManaCurios.containsKey(player) && Utils.DevilEarthManaCurios.get(player)) exDefence += 4;
        if (Utils.DevilBloodManaCurios.containsKey(player) && Utils.DevilBloodManaCurios.get(player)) exDefence += 4;

        exDefence += Compute.CuriosAttribute.attributeValue(player, Utils.manaDefence, StringUtils.CuriosAttribute.manaDefence); // 新版饰品属性加成
        exDefence += CastleAttackArmor.ExAttributeValue(player, CastleAttackArmor.ExManaDefence);
        exDefence += CastleManaArmor.ExAttributeValue(player, CastleManaArmor.ExManaDefence);
        exDefence += CastleSwiftArmor.ExAttributeValue(player, CastleSwiftArmor.ExManaDefence);
        exDefence += StableTierAttributeModifier.getModifierValue(player, StableTierAttributeModifier.manaDefence);
        exDefence += StableAttributesModifier.getModifierValue(player, StableAttributesModifier.playerManaDefenceModifier);
        // 请在上方添加

        double totalDefence = basicDefence + exDefence;
        totalDefence *= (1 + EarthPower.PlayerDefenceEnhance(player));
        totalDefence *= Compute.playerFantasyAttributeEnhance(player);
        totalDefence *= MineShield.defenceEnhance(player);
        totalDefence *= (1 + GemAttributes.getPlayerCurrentAllEquipGemsValue(player, Utils.percentManaDefenceEnhance) +
                Compute.CuriosAttribute.attributeValue(player, Utils.percentManaDefenceEnhance,
                StringUtils.CuriosAttribute.percentManaDefenceEnhance));
        totalDefence *= (1 + StableAttributesModifier.getModifierValue(player, StableAttributesModifier.playerPercentManaDefenceModifier));

        totalDefence -= StableAttributesModifier.getModifierValue(player, StableAttributesModifier.playerManaDefenceDecreaseModifier);
        totalDefence = Math.max(0, totalDefence);
        writeToCache(player, Utils.manaDefence, totalDefence);
        return totalDefence;
    }

    public static double healthSteal(Player player) {
        if (canGetFromCache(player, Utils.healthSteal)) {
            return getFromCache(player, Utils.healthSteal);
        }
        double healthSteal = 0.0d;
        CompoundTag data = player.getPersistentData();
        Item boots = player.getItemBySlot(EquipmentSlot.FEET).getItem();
        Item leggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item chest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
        Item helmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        Item offhand = player.getItemInHand(InteractionHand.OFF_HAND).getItem();
        CompoundTag helmetTag = player.getItemBySlot(EquipmentSlot.HEAD).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag chestTag = player.getItemBySlot(EquipmentSlot.CHEST).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag leggingsTag = player.getItemBySlot(EquipmentSlot.LEGS).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag bootsTag = player.getItemBySlot(EquipmentSlot.FEET).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag stackmainhandtag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            stackmainhandtag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }

        healthSteal += handleAllEquipRandomAttribute(player, StringUtils.RandomAttribute.healthSteal);

        // 器灵属性加成
        healthSteal += Compute.PassiveEquip.getAttribute(player, Utils.healthSteal);

        if (Utils.mainHandTag.containsKey(mainhand) && stackmainhandtag.contains("healsteal"))
            healthSteal += stackmainhandtag.getDouble("healsteal");
        if (Utils.healthSteal.containsKey(boots)) healthSteal += Utils.healthSteal.get(boots);
        if (Utils.healthSteal.containsKey(leggings)) healthSteal += Utils.healthSteal.get(leggings);
        if (Utils.healthSteal.containsKey(chest)) healthSteal += Utils.healthSteal.get(chest);
        if (Utils.healthSteal.containsKey(helmet)) healthSteal += Utils.healthSteal.get(helmet);
        if (Utils.mainHandTag.containsKey(mainhand) && Utils.healthSteal.containsKey(mainhand))
            healthSteal += Utils.healthSteal.get(mainhand);
        if (Utils.offHandTag.containsKey(offhand) && Utils.healthSteal.containsKey(offhand))
            healthSteal += Utils.healthSteal.get(offhand);

        if (player.getEffect(ModEffects.HEALSTEALUP.get()) != null && player.getEffect(ModEffects.HEALSTEALUP.get()).getAmplifier() == 0)
            healthSteal += 0.12;
        if (player.getEffect(ModEffects.HEALSTEALUP.get()) != null && player.getEffect(ModEffects.HEALSTEALUP.get()).getAmplifier() == 1)
            healthSteal += 0.25;
        if (data.contains("GemSHealSteal")) healthSteal += data.getDouble("GemSHealSteal");
        healthSteal += SArmorAttribute.value(player, SArmorAttribute.netherPower);

        if (stackmainhandtag.contains(StringUtils.SoulEquipForge) && (Utils.swordTag.containsKey(mainhand)))
            healthSteal +=
                    stackmainhandtag.getInt(StringUtils.SoulEquipForge) * SoulEquipAttribute.ForgingAddition.HealthSteal;

        if (Utils.BloodManaCurios.containsKey(player) && Utils.BloodManaCurios.get(player)) healthSteal += 0.05;

        if (player.getItemInHand(InteractionHand.OFF_HAND).is(ModItems.ManaShield.get())) {
            if (player.getHealth() / player.getMaxHealth() < 0.5) {
                healthSteal += data.getDouble("CritDamageAfterCompute") / 5;
            }
        } // 封魔者法盾

        if (helmetTag.contains("newGems1")) healthSteal += GemAttributes.gemsHealthSteal(helmetTag);
        if (chestTag.contains("newGems1")) healthSteal += GemAttributes.gemsHealthSteal(chestTag);
        if (leggingsTag.contains("newGems1")) healthSteal += GemAttributes.gemsHealthSteal(leggingsTag);
        if (bootsTag.contains("newGems1")) healthSteal += GemAttributes.gemsHealthSteal(bootsTag);
        if (stackmainhandtag.contains("newGems1") && Utils.mainHandTag.containsKey(mainhand))
            healthSteal += GemAttributes.gemsHealthSteal(stackmainhandtag);

        if (Utils.DevilBloodManaCurios.containsKey(player) && Utils.DevilBloodManaCurios.get(player)) healthSteal += 0.05;

        healthSteal += Compute.CuriosAttribute.attributeValue(player, Utils.healthSteal, StringUtils.CuriosAttribute.healthSteal); // 新版饰品属性加成

        // 请在上方添加

        healthSteal *= Compute.playerFantasyAttributeEnhance(player);
        healthSteal *= (1 + SuitCount.getBloodManaSuitCount(player) * 0.08);

        if (player.getItemInHand(InteractionHand.OFF_HAND).is(ModItems.manaKnife.get())) {
            data.putDouble("HealthStealAfterCompute", healthSteal);
            healthSteal = 0;
        } // 猎魔者小刀

        if (player.getItemInHand(InteractionHand.OFF_HAND).is(ModItems.ManaShield.get())) {
            if (player.getHealth() / player.getMaxHealth() > 0.5) {
                data.putDouble("HealthStealAfterCompute", healthSteal);
                healthSteal = 0;
            }
        } // 封魔者法盾

        if (data.contains("NetherRecallBuff") && data.getInt("NetherRecallBuff") > 0) {
            healthSteal *= 0.25;
        }

        writeToCache(player, Utils.healthSteal, healthSteal);
        return healthSteal;
    }

    public static double manaPenetration(Player player) {
        if (canGetFromCache(player, Utils.manaPenetration)) {
            return getFromCache(player, Utils.manaPenetration);
        }
        CompoundTag data = player.getPersistentData();
        int TickCount = player.getServer().getTickCount();
        double defenceRate = 1;
        Item boots = player.getItemBySlot(EquipmentSlot.FEET).getItem();
        Item leggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item chest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
        Item helmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        Item offhand = player.getItemInHand(InteractionHand.OFF_HAND).getItem();
        CompoundTag stackmainhandtag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            stackmainhandtag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }
        if (Utils.manaPenetration.containsKey(boots))
            defenceRate *= (1 - Utils.manaPenetration.get(boots));
        if (Utils.manaPenetration.containsKey(leggings))
            defenceRate *= (1 - Utils.manaPenetration.get(leggings));
        if (Utils.manaPenetration.containsKey(chest))
            defenceRate *= (1 - Utils.manaPenetration.get(chest));
        if (Utils.manaPenetration.containsKey(helmet))
            defenceRate *= (1 - Utils.manaPenetration.get(helmet));
        if (Utils.mainHandTag.containsKey(mainhand) && Utils.manaPenetration.containsKey(mainhand))
            defenceRate *= (1 - Utils.manaPenetration.get(mainhand));
        if (Utils.offHandTag.containsKey(offhand) && Utils.manaPenetration.containsKey(offhand))
            defenceRate *= (1 - Utils.manaPenetration.get(offhand));

        if (player.getEffect(ModEffects.BREAKMANADefenceUP.get()) != null && player.getEffect(ModEffects.BREAKMANADefenceUP.get()).getAmplifier() == 0)
            defenceRate *= (1 - 0.2);
        if (player.getEffect(ModEffects.BREAKMANADefenceUP.get()) != null && player.getEffect(ModEffects.BREAKMANADefenceUP.get()).getAmplifier() == 1)
            defenceRate *= (1 - 0.45);
        if (data.contains("GemSManaBreakDefence")) defenceRate *= (1 - data.getDouble("GemSManaBreakDefence"));

        if (Utils.MeteoriteAttackTimeMap.containsKey(player) && Utils.MeteoriteAttackTimeMap.get(player) > TickCount) {
            defenceRate *= (1 - 0.2);
        }

        CompoundTag helmetTag = player.getItemBySlot(EquipmentSlot.HEAD).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag chestTag = player.getItemBySlot(EquipmentSlot.CHEST).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag leggingsTag = player.getItemBySlot(EquipmentSlot.LEGS).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag bootsTag = player.getItemBySlot(EquipmentSlot.FEET).getOrCreateTagElement(Utils.MOD_ID);

        double DecreaseRate = 0;
        if (helmetTag.contains("newGems1")) DecreaseRate += GemAttributes.gemsManaPenetration(helmetTag);
        if (chestTag.contains("newGems1")) DecreaseRate += GemAttributes.gemsManaPenetration(chestTag);
        if (leggingsTag.contains("newGems1")) DecreaseRate += GemAttributes.gemsManaPenetration(leggingsTag);
        if (bootsTag.contains("newGems1")) DecreaseRate += GemAttributes.gemsManaPenetration(bootsTag);
        if (stackmainhandtag.contains("newGems1") && Utils.mainHandTag.containsKey(mainhand))
            DecreaseRate += GemAttributes.gemsManaPenetration(stackmainhandtag);

        if (DecreaseRate > 0) defenceRate *= (1 - DecreaseRate);

        defenceRate *= (1 - Compute.CuriosAttribute.attributeValue(player, Utils.manaPenetration,
                StringUtils.CuriosAttribute.manaPenetration)); // 新版饰品属性加成
        // 请在上方添加
        defenceRate *= (2 - Compute.playerFantasyAttributeEnhance(player));

        writeToCache(player, Utils.manaPenetration, 1 - defenceRate);
        return 1 - defenceRate;
    }

    public static double manaPenetration0(Player player) {
        if (canGetFromCache(player, Utils.manaPenetration0)) {
            return getFromCache(player, Utils.manaPenetration0);
        }
        CompoundTag data = player.getPersistentData();
        double manaPenetration0 = 0;
        Item boots = player.getItemBySlot(EquipmentSlot.FEET).getItem();
        Item leggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item chest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
        Item helmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        Item offhand = player.getItemInHand(InteractionHand.OFF_HAND).getItem();
        CompoundTag stackmainhandtag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            stackmainhandtag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }

        manaPenetration0 += handleAllEquipRandomAttribute(player, StringUtils.RandomAttribute.manaPenetration0);

        manaPenetration0 += computeAllEquipSlotXpLevelAttributeValue(player, Utils.xpLevelManaPenetration0, false);
        manaPenetration0 += Compute.CuriosAttribute.attributeValue(player, Utils.xpLevelManaPenetration0,
                StringUtils.CuriosAttribute.xpLevelManaPenetration0) * player.experienceLevel;

        if (Utils.manaPenetration0.containsKey(boots))
            manaPenetration0 += Utils.manaPenetration0.get(boots);
        if (Utils.manaPenetration0.containsKey(leggings))
            manaPenetration0 += Utils.manaPenetration0.get(leggings);
        if (Utils.manaPenetration0.containsKey(chest))
            manaPenetration0 += Utils.manaPenetration0.get(chest);
        if (Utils.manaPenetration0.containsKey(helmet))
            manaPenetration0 += Utils.manaPenetration0.get(helmet);
        if (Utils.mainHandTag.containsKey(mainhand) && Utils.manaPenetration0.containsKey(mainhand))
            manaPenetration0 += Utils.manaPenetration0.get(mainhand);
        if (Utils.offHandTag.containsKey(offhand) && Utils.manaPenetration0.containsKey(offhand))
            manaPenetration0 += Utils.manaPenetration0.get(offhand);
        if (stackmainhandtag.contains(StringUtils.SoulEquipForge) && Utils.sceptreTag.containsKey(mainhand))
            manaPenetration0 +=
                    stackmainhandtag.getInt(StringUtils.SoulEquipForge) * SoulEquipAttribute.ForgingAddition.ManaPenetration0;

        if (stackmainhandtag.contains(StringUtils.ManaCore.ManaCore) && stackmainhandtag.getString(StringUtils.ManaCore.ManaCore).
                equals(StringUtils.ManaCore.KazeCore)) {
            manaPenetration0 += movementSpeedWithoutBattle(player);
        }

        if (SuitCount.getVolcanoSuitCount(player) >= 4) manaPenetration0 += 10;

        if (Utils.PlayerSpringRingManaPenetration0Attribute.containsKey(player) && Utils.PlayerSpringRingLevelRequire.get(player) <= player.experienceLevel) {
            manaPenetration0 += Utils.PlayerSpringRingManaPenetration0Attribute.get(player) * 0.01;
        }

        if (Utils.PlayerSpringBraceletManaPenetration0Attribute.containsKey(player) && Utils.PlayerSpringBraceletLevelRequire.get(player) <= player.experienceLevel) {
            manaPenetration0 += Utils.PlayerSpringBraceletManaPenetration0Attribute.get(player) * 0.01;
        }

        CompoundTag helmetTag = player.getItemBySlot(EquipmentSlot.HEAD).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag chestTag = player.getItemBySlot(EquipmentSlot.CHEST).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag leggingsTag = player.getItemBySlot(EquipmentSlot.LEGS).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag bootsTag = player.getItemBySlot(EquipmentSlot.FEET).getOrCreateTagElement(Utils.MOD_ID);
        if (helmetTag.contains("newGems1")) manaPenetration0 += GemAttributes.gemsManaPenetration0(helmetTag);
        if (chestTag.contains("newGems1")) manaPenetration0 += GemAttributes.gemsManaPenetration0(chestTag);
        if (leggingsTag.contains("newGems1")) manaPenetration0 += GemAttributes.gemsManaPenetration0(leggingsTag);
        if (bootsTag.contains("newGems1")) manaPenetration0 += GemAttributes.gemsManaPenetration0(bootsTag);
        if (stackmainhandtag.contains("newGems1") && Utils.mainHandTag.containsKey(mainhand))
            manaPenetration0 += GemAttributes.gemsManaPenetration0(stackmainhandtag);

        manaPenetration0 += Compute.CuriosAttribute.attributeValue(player, Utils.manaPenetration0, StringUtils.CuriosAttribute.manaPenetration0); // 新版饰品属性加成
        manaPenetration0 += Compute.PassiveEquip.getAttribute(player, Utils.manaPenetration0); // 器灵属性加成
        manaPenetration0 += InCuriosOrEquipSlotAttributesModify.getAttributes(player, Utils.manaPenetration0);
        if (Utils.sceptreTag.containsKey(mainhand)) {
            manaPenetration0 += Compute.getManaSkillLevel(data, 10) * 3; // 力凝魔核
        }

        manaPenetration0 += StableAttributesModifier.getModifierValue(player,
                StableAttributesModifier.playerManaPenetration0Modifier);
        // 请在上方添加
        manaPenetration0 *= Compute.playerFantasyAttributeEnhance(player);

        writeToCache(player, Utils.manaPenetration0, manaPenetration0);
        return manaPenetration0;
    }

    public static double maxMana(Player player) {
        if (canGetFromCache(player, Utils.maxMana)) {
            return getFromCache(player, Utils.maxMana);
        }
        CompoundTag data = player.getPersistentData();
        double maxMana = player.experienceLevel;
        Item boots = player.getItemBySlot(EquipmentSlot.FEET).getItem();
        Item leggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item chest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
        Item helmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        Item offhand = player.getItemInHand(InteractionHand.OFF_HAND).getItem();
        CompoundTag stackmainhandtag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            stackmainhandtag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }
        if (Utils.maxMana.containsKey(boots)) maxMana += Utils.maxMana.get(boots);
        if (Utils.maxMana.containsKey(leggings)) maxMana += Utils.maxMana.get(leggings);
        if (Utils.maxMana.containsKey(chest)) maxMana += Utils.maxMana.get(chest);
        if (Utils.maxMana.containsKey(helmet)) maxMana += Utils.maxMana.get(helmet);
        if (Utils.mainHandTag.containsKey(mainhand) && Utils.maxMana.containsKey(mainhand))
            maxMana += Utils.maxMana.get(mainhand);
        if (Utils.offHandTag.containsKey(offhand) && Utils.maxMana.containsKey(offhand))
            maxMana += Utils.maxMana.get(offhand);
        if (data.contains("GemSMaxMana")) maxMana += data.getDouble("GemSMaxMana");

        int intelligentAbilityPoint = data.getInt(StringUtils.Ability.Intelligent);
        if (data.contains(StringUtils.Ability.Intelligent) && data.getInt(StringUtils.Ability.Intelligent) > 0) {
            maxMana += intelligentAbilityPoint * 10;
        } // 能力

        if (stackmainhandtag.contains(StringUtils.SoulEquipForge) && Utils.sceptreTag.containsKey(mainhand))
            maxMana +=
                    stackmainhandtag.getInt(StringUtils.SoulEquipForge) * SoulEquipAttribute.ForgingAddition.MaxMana;

        if (Utils.PlayerSpringBraceletMaxManaAttribute.containsKey(player) && Utils.PlayerSpringBraceletLevelRequire.get(player) <= player.experienceLevel) {
            maxMana += Utils.PlayerSpringBraceletMaxManaAttribute.get(player);
        }

        maxMana += Compute.CuriosAttribute.attributeValue(player, Utils.maxMana, StringUtils.CuriosAttribute.maxMana); // 新版饰品属性加成

        maxMana += Compute.PassiveEquip.getAttribute(player, Utils.maxMana); // 器灵属性加成

        maxMana += handleAllEquipRandomAttribute(player, StringUtils.RandomAttribute.maxMana);

        maxMana += Compute.CuriosAttribute.attributeValue(player, Utils.maxMana, StringUtils.CuriosAttribute.maxMana); // 新版饰品属性加成
        maxMana += InCuriosOrEquipSlotAttributesModify.getAttributes(player, Utils.maxMana);
        // 请在上方添加

        maxMana *= Compute.playerFantasyAttributeEnhance(player);

        writeToCache(player, Utils.maxMana, 250 + maxMana);
        return 250 + maxMana;
    }

    public static double getMineSpeedEnhanceRate(Player player) {
        double rate = 0;
        rate += computeAllEquipSlotBaseAttributeValue(player, WraqPickaxe.mineSpeed, false);
        rate += MineSpur.getPlayerMineLevel(player);
        return rate;
    }

    public static double playerToughness(Player player) {
        double value = 0;
        CompoundTag data = player.getPersistentData();
        value += computeAllEquipSlotBaseAttributeValue(player, Utils.toughness, false);
        int powerAbilityPoint = data.getInt(StringUtils.Ability.Power);
        if (data.contains(StringUtils.Ability.Power) && data.getInt(StringUtils.Ability.Power) > 0) {
            value += powerAbilityPoint * 0.004;
        } // 能力
        return Math.min(0.5, value);
    }

    public static double handleArmorRandomAttribute(Player player, String attributeType) {
        double value = 0;
        CompoundTag helmetTag = player.getItemBySlot(EquipmentSlot.HEAD).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag chestTag = player.getItemBySlot(EquipmentSlot.CHEST).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag leggingsTag = player.getItemBySlot(EquipmentSlot.LEGS).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag bootsTag = player.getItemBySlot(EquipmentSlot.FEET).getOrCreateTagElement(Utils.MOD_ID);
        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
        ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
        ItemStack leggings = player.getItemBySlot(EquipmentSlot.LEGS);
        ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);
        if (helmetTag.contains(attributeType)) {
            double baseValue = ForgeEquipUtils.getRandomEquipBaseValue(player, helmet, attributeType);
            value += baseValue;
            if (helmetTag.contains("Forging"))
                value += Compute.forgingValue(helmetTag, baseValue);

        }
        if (chestTag.contains(attributeType)) {
            double baseValue = ForgeEquipUtils.getRandomEquipBaseValue(player, chest, attributeType);
            value += baseValue;
            if (chestTag.contains("Forging"))
                value += Compute.forgingValue(chestTag, baseValue);
        }
        if (leggingsTag.contains(attributeType)) {
            double baseValue = ForgeEquipUtils.getRandomEquipBaseValue(player, leggings, attributeType);
            value += baseValue;
            if (leggingsTag.contains("Forging"))
                value += Compute.forgingValue(leggingsTag, baseValue);
        }
        if (bootsTag.contains(attributeType)) {
            double baseValue = ForgeEquipUtils.getRandomEquipBaseValue(player, boots, attributeType);
            value += baseValue;
            if (bootsTag.contains("Forging"))
                value += Compute.forgingValue(bootsTag, baseValue);
        }
        return value;
    }

    public static double handleMainHandRandomAttribute(Player player, String attributeType) {
        double value = 0;
        ItemStack mainHand = player.getItemInHand(InteractionHand.MAIN_HAND);
        if (mainHand.getTagElement(Utils.MOD_ID) != null) {
            CompoundTag tag = mainHand.getOrCreateTagElement(Utils.MOD_ID);
            if (tag.contains(attributeType)) {
                double baseValue = ForgeEquipUtils.getRandomEquipBaseValue(player, mainHand, attributeType);
                value += baseValue;
                if (tag.contains("Forging"))
                    value += Compute.forgingValue(tag, baseValue);
            }
        }
        return value;
    }

    public static double handleAllEquipRandomAttribute(Player player, String attributeType) {
        double value = 0;
        value += handleMainHandRandomAttribute(player, attributeType);
        value += handleArmorRandomAttribute(player, attributeType);
        return value;
    }

    public static double dodgeRate(Player player) {
        double swift = Math.min(PlayerAttributes.extraSwiftness(player), 90);
        double rate = 0;
        if (swift <= 10) rate = swift * 0.02;
        else if (swift <= 20) rate = 0.2 + (swift - 10) * 0.015;
        else if (swift <= 30) rate = 0.35 + (swift - 20) * 0.01;
        else rate = 0.45 + (swift - 30) * 0.005;
        return rate;
    }

    public static List<ItemStack> getAllEquipSlotItems(Player player) {
        List<ItemStack> list = new ArrayList<>(List.of(player.getItemBySlot(EquipmentSlot.HEAD),
                player.getItemBySlot(EquipmentSlot.CHEST), player.getItemBySlot(EquipmentSlot.LEGS),
                player.getItemBySlot(EquipmentSlot.FEET)));
        if (Utils.mainHandTag.containsKey(player.getMainHandItem().getItem())) list.add(player.getMainHandItem());
        if (Utils.offHandTag.containsKey(player.getOffhandItem().getItem())) list.add(player.getOffhandItem());
        return list;
    }

    private static double computeAllEquipSlotXpLevelAttributeValue(Player player, Map<Item, Double> attributeMap,
                                                                   boolean computeForge) {
        int xpLevel = player.experienceLevel;
        double totalValue = 0;
        for (ItemStack equip : getAllEquipSlotItems(player)) {
            Item item = equip.getItem();
            if (attributeMap.containsKey(item)) {
                double value = 0;
                double baseValue = xpLevel * attributeMap.get(item);
                if (computeForge && equip.getTagElement(Utils.MOD_ID) != null) {
                    value += Compute.forgingValue(equip, baseValue);
                }
                totalValue += baseValue + value;
            }
        }
        return totalValue;
    }

    /**
     * 计算所有盔甲 + 主手 + 副手基础属性的总和
     * @param player 玩家
     * @param attributeMap 属性表
     * @param computeForge 是否计算强化等级
     * @return 得到的基础数值
     */
    private static double computeAllEquipSlotBaseAttributeValue(Player player, Map<Item, Double> attributeMap,
                                                                boolean computeForge) {
        double totalValue = 0;
        for (ItemStack equip : getAllEquipSlotItems(player)) {
            Item item = equip.getItem();
            if (attributeMap.containsKey(item) && player.experienceLevel >= Utils.levelRequire.getOrDefault(item, 0)) {
                double value = 0;
                double baseValue = ForgeEquipUtils.getTraditionalEquipBaseValue(equip, attributeMap);
                if (computeForge && equip.getTagElement(Utils.MOD_ID) != null) {
                    value += Compute.forgingValue(equip, baseValue);
                }
                totalValue += baseValue + value;
            }
        }
        return totalValue;
    }
}
