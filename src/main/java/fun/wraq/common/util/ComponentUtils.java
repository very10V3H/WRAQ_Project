package fun.wraq.common.util;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.BasicAttributeDescription;
import fun.wraq.common.fast.Te;
import fun.wraq.process.system.element.Color;
import fun.wraq.process.system.element.RainbowCrystal;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.worldsoul.SoulEquipAttribute;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ComponentUtils {
    public static class AttributeDescription {

        public static Component expUp(String content) {
            return Component.literal(Utils.Emoji.ExpUp + " " + content + "经验加成").withStyle(ChatFormatting.LIGHT_PURPLE);
        }

        public static Component maxHealth(String content) {
            return Component.literal(Utils.Emoji.Health + " " + content + "最大生命值").withStyle(ChatFormatting.GREEN);
        }

        public static Component healValue(String content) {
            return Component.literal(Utils.Emoji.Health + " " + content + "治疗量").withStyle(ChatFormatting.GREEN);
        }

        public static Component lossHealth(String content) {
            return Component.literal(Utils.Emoji.Health + " " + content + "已损失生命值").withStyle(ChatFormatting.DARK_GREEN);
        }

        public static Component swiftness(String content) {
            return Component.literal(Utils.Emoji.Swiftness + " " + content + "迅捷").withStyle(ChatFormatting.GREEN);
        }

        public static Component movementSpeed(String content) {
            return Component.literal(Utils.Emoji.Speed + " " + content + "移动速度").withStyle(ChatFormatting.GREEN);
        }

        public static Component toughness(String content) {
            return Component.literal(Utils.Emoji.Speed + " " + content + "韧性").withStyle(CustomStyle.styleOfEnd);
        }

        public static Component movementSpeedWithoutBattle(String content) {
            return Component.literal(Utils.Emoji.Speed + " " + content + "脱战移动速度").withStyle(ChatFormatting.GREEN);
        }

        public static Component exMovementSpeed(String content) {
            return Component.literal(Utils.Emoji.Speed + " " + content + "额外移动速度").withStyle(ChatFormatting.GREEN);
        }

        public static Component movementSpeedDecrease(String content) {
            return Component.literal(Utils.Emoji.Speed + " " + content + "移动速度").withStyle(ChatFormatting.RED);
        }

        public static Component manaDamage(String content) {
            return Component.literal(Utils.Emoji.Mana + " " + content + "魔法攻击").withStyle(ChatFormatting.LIGHT_PURPLE);
        }

        public static Component manaCost(String content) {
            return Component.literal(Utils.Emoji.ManaCost + " " + content + "法力消耗").withStyle(ChatFormatting.LIGHT_PURPLE);
        }

        public static Component exManaDamage(String content) {
            return Component.literal(Utils.Emoji.Mana + " " + content + "额外魔法攻击").withStyle(ChatFormatting.LIGHT_PURPLE);
        }

        public static Component manaRecover(String content) {
            return Component.literal(Utils.Emoji.ManaRecover + " " + content + "法力回复").withStyle(ChatFormatting.LIGHT_PURPLE);
        }

        public static Component health(String content) {
            return Component.literal(Utils.Emoji.Health + " " + content + "生命值").withStyle(ChatFormatting.GREEN);
        }

        public static Component defence(String content) {
            return Component.literal(Utils.Emoji.Defence + " " + content + "护甲").withStyle(ChatFormatting.GRAY);
        }

        public static Component damageDirectDecrease(String content) {
            return Component.literal(Utils.Emoji.Defence + " " + content + "伤害削减").withStyle(CustomStyle.styleOfStone);
        }

        public static Component healAmplification(String content) {
            return Component.literal(Utils.Emoji.HealthAmplification + " " + content + "治疗强度").withStyle(CustomStyle.styleOfHealth);
        }

        public static Component healthRecover(String content) {
            return Component.literal(Utils.Emoji.HealthRecover + " " + content + "生命回复").withStyle(CustomStyle.styleOfHealth);
        }

        public static Component manaDefence(String content) {
            return Component.literal(Utils.Emoji.Defence + " " + content + "魔法抗性").withStyle(ChatFormatting.BLUE);
        }

        public static Component exAttackDamage(String content) {
            return Component.literal(Utils.Emoji.Sword + " " + content + "额外攻击力").withStyle(ChatFormatting.YELLOW);
        }

        public static Component defencePenetration(String content) {
            return Component.literal(Utils.Emoji.Defence + " " + content + "护甲穿透").withStyle(ChatFormatting.GRAY);
        }

        public static Component critDamage(String content) {
            return Component.literal(Utils.Emoji.CritDamage + " " + content + "暴击伤害").withStyle(ChatFormatting.BLUE);
        }

        public static Component critRate(String content) {
            return Component.literal(Utils.Emoji.CritRate + " " + content + "暴击几率").withStyle(ChatFormatting.LIGHT_PURPLE);
        }

        public static Component attackDamage(String content) {
            return Component.literal(Utils.Emoji.Sword + " " + content + "攻击力").withStyle(ChatFormatting.YELLOW);
        }

        public static Component manaPenetration(String content) {
            return Component.literal(Utils.Emoji.Defence + " " + content + "法术穿透").withStyle(ChatFormatting.BLUE);
        }

        public static Component releaseSpeed(String content) {
            return Component.literal(Utils.Emoji.CoolDown + " " + content + "技能急速").withStyle(ChatFormatting.AQUA);
        }

        public static Component coolDown(String content) {
            return Component.literal(Utils.Emoji.CoolDown + " " + content + "冷却时间").withStyle(ChatFormatting.AQUA);
        }

        public static Component healthSteal(String content) {
            return Component.literal(Utils.Emoji.HealSteal + " " + content + "生命偷取").withStyle(ChatFormatting.RED);
        }

        public static Component skillHealthSteal(String content) {
            return Component.literal(Utils.Emoji.HealSteal + " " + content + "全能吸血").withStyle(CustomStyle.styleOfField);
        }

        public static Component manaHealSteal(String content) {
            return Component.literal(Utils.Emoji.HealSteal + " " + content + "法术吸血").withStyle(CustomStyle.styleOfMana);
        }

        public static Component maxMana(String content) {
            return Component.literal(Utils.Emoji.MaxMana + " " + content + "最大法力值").withStyle(CustomStyle.styleOfMana);
        }

        public static Component manaValue(String content) {
            return Component.literal(Utils.Emoji.MaxMana + " " + content + "法力值").withStyle(CustomStyle.styleOfMana);
        }

        public static Component exHealth(String content) {
            return Component.literal(Utils.Emoji.Health + " " + content + "额外生命值").withStyle(ChatFormatting.GREEN);
        }

        public static Component attackRange(String content) {
            return Component.literal(Utils.Emoji.AttackRange + " " + content + "攻击距离").withStyle(CustomStyle.styleOfSea);
        }

        public static Component attackDamageValue(String content) {
            return Component.literal(Utils.Emoji.Sword + " " + content + "物理伤害").withStyle(ChatFormatting.YELLOW);
        }

        public static Component manaDamageValue(String content) {
            return Component.literal(Utils.Emoji.Mana + " " + content + "魔法伤害").withStyle(ChatFormatting.LIGHT_PURPLE);
        }

        public static Component getAttackSpeed(String content) {
            return Te.s(Utils.Emoji.AttackSpeed + " " + content + "攻击速度", CustomStyle.styleOfFlexible);
        }

        public static Component getElementStrength(String content) {
            return Te.s(Utils.Emoji.ELEMENT + " " + content + "元素强度", CustomStyle.styleOfWorld);
        }
    }

    public static void suitDescription(List<Component> components) {
        components.add(Component.literal(Utils.Emoji.Suit + " " + "套装效果").withStyle(ChatFormatting.AQUA));
    }

    public static void suitDoubleDescription(List<Component> components) {
        components.add(Component.literal("▷2件套效果:").withStyle(ChatFormatting.YELLOW));
    }

    public static void suitDoubleDescription(List<Component> components, int Count) {
        if (Count >= 2)
            components.add(Component.literal("▷2件套效果:").withStyle(ChatFormatting.YELLOW));
        else
            components.add(Component.literal("▷2件套效果:").withStyle(ChatFormatting.GRAY));
    }

    public static void suitQuadraDescription(List<Component> components) {
        components.add(Component.literal("▷4件套效果:").withStyle(ChatFormatting.LIGHT_PURPLE));
    }

    public static void suitQuadraDescription(List<Component> components, int Count) {
        if (Count >= 4)
            components.add(Component.literal("▷4件套效果:").withStyle(ChatFormatting.LIGHT_PURPLE));
        else
            components.add(Component.literal("▷4件套效果:").withStyle(ChatFormatting.GRAY));
    }

    public static void suffixOfChapterI(List<Component> components) {
        components.add(getSuffixOfChapterI());
    }

    public static Component getSuffixOfChapterI() {
        return Component.literal("德朗斯蒂克之作").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC);
    }

    public static void suffixOfChapterII(List<Component> components) {
        components.add(getSuffixOfChapterII());
    }

    public static Component getSuffixOfChapterII() {
        return Component.literal("艾里蒙特大陆杰作").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.ITALIC);
    }

    public static void suffixOfNether(List<Component> components) {
        components.add(getSuffixOfNether());
    }

    public static Component getSuffixOfNether() {
        return Component.literal("凶险下界之物").withStyle(CustomStyle.styleOfNether).withStyle(ChatFormatting.ITALIC);
    }

    public static Component getSuffixOfIgniteRevenant() {
        return Component.literal("燃魂残烬").withStyle(CustomStyle.styleOfPower).withStyle(ChatFormatting.ITALIC);
    }

    public static void suffixOfEnd(List<Component> components) {
        components.add(getSuffixOfEnd());
    }

    public static Component getSuffixOfEnd() {
        return Component.literal("终界遗响").withStyle(CustomStyle.styleOfEnd).withStyle(ChatFormatting.ITALIC);
    }

    public static void suffixOfSakura(List<Component> components) {
        components.add(getSuffixOfSakura());
    }

    public static Component getSuffixOfSakura() {
        return Component.literal("绯樱散华").withStyle(CustomStyle.styleOfSakura).withStyle(ChatFormatting.ITALIC);
    }

    public static void suffixOfMoon(List<Component> components) {
        components.add(getSuffixOfMoon());
    }

    public static Component getSuffixOfMoon() {
        return Component.literal("沐尘圣礼").withStyle(CustomStyle.styleOfMoon).withStyle(ChatFormatting.ITALIC);
    }

    public static void suffixOfCastle(List<Component> components) {
        components.add(getSuffixOfCastle());
    }

    public static Component getSuffixOfCastle() {
        return Component.literal("暗黑渊屑").withStyle(CustomStyle.styleOfCastle).withStyle(ChatFormatting.ITALIC);
    }

    public static void suffixOfPurpleIronKnight(List<Component> components) {
        components.add(getSuffixOfPurpleIron());
    }

    public static Component getSuffixOfPurpleIron() {
        return Component.literal("紫晶耀光").withStyle(CustomStyle.styleOfPurpleIron).withStyle(ChatFormatting.ITALIC);
    }

    public static Component getSuffixOfPlainBoss() {
        return Component.literal("普莱尼生机之作").withStyle(CustomStyle.styleOfPlain).withStyle(ChatFormatting.ITALIC);
    }

    public static void suffixOfIce(List<Component> components) {
        components.add(Component.literal("凛冰之作").withStyle(CustomStyle.styleOfIce).withStyle(ChatFormatting.ITALIC));
    }

    public static Component getSuffixOfIce() {
        return Component.literal("凛冰之作").withStyle(CustomStyle.styleOfIce).withStyle(ChatFormatting.ITALIC);
    }

    public static void suffixOfWorldSoul(List<Component> components) {
        components.add(Component.literal("新维地-本源名作").withStyle(CustomStyle.styleOfWorld).withStyle(ChatFormatting.ITALIC));
    }

    public static Component getSuffixOfWorldSoul() {
        return Component.literal("新维地-本源名作").withStyle(CustomStyle.styleOfWorld).withStyle(ChatFormatting.ITALIC);
    }

    public static Component getSuffixOfSunIsland() {
        return Component.literal("旭升岛-超凡秘藏").withStyle(CustomStyle.styleOfSunIsland).withStyle(ChatFormatting.ITALIC);
    }

    public static Component getSuffixOfDemon() {
        return Component.literal("妖魔邪力").withStyle(CustomStyle.styleOfDemon).withStyle(ChatFormatting.ITALIC);
    }

    public static void suffixOfMainStoryStar(List<Component> components) {
        components.add(Component.literal("尘月之梦").withStyle(CustomStyle.styleOfMoon1).withStyle(ChatFormatting.ITALIC));
    }

    public static Component getSuffixOfChapterStar() {
        return Component.literal("尘月之梦").withStyle(CustomStyle.styleOfMoon1).withStyle(ChatFormatting.ITALIC);
    }

    public static Component getSuffixOfBoneImp() {
        return Component.literal("炽热逸魂").withStyle(CustomStyle.styleOfWither).withStyle(ChatFormatting.ITALIC);
    }

    public static Component getSuffixOfMoontain() {
        return Component.literal("望山楼阁").withStyle(CustomStyle.styleOfMoontain).withStyle(ChatFormatting.ITALIC);
    }

    public static Component getSuffixOfAncient() {
        return Component.literal("远古沉都").withStyle(CustomStyle.styleOfWarden).withStyle(ChatFormatting.ITALIC);
    }

    public static Component getSuffixOfHarbinger() {
        return Component.literal("鹰眼工业杰作").withStyle(CustomStyle.styleOfHarbinger).withStyle(ChatFormatting.ITALIC);
    }

    public static Component getSuffixOfSakuraIslandIndustry() {
        return Component.literal("樱岛隐秘工业").withStyle(CustomStyle.styleOfSakura).withStyle(ChatFormatting.ITALIC);
    }

    public static Component getSuffixOfMushroom() {
        return Component.literal("分生之种").withStyle(CustomStyle.MUSHROOM_STYLE).withStyle(ChatFormatting.ITALIC);
    }

    public static Component getSuffixOfSmith() {
        return Component.literal("匠人").withStyle(CustomStyle.styleOfGold).withStyle(ChatFormatting.ITALIC);
    }

    public static Component getSuffixOfAlchemy() {
        return Component.literal("炼金").withStyle(CustomStyle.styleOfGold).withStyle(ChatFormatting.ITALIC);
    }

    public static Component getSuffixOfSpring2024() {
        return Component.literal("2024 - 甲辰春节")
                .withStyle(ChatFormatting.ITALIC).withStyle(CustomStyle.styleOfSpring);
    }

    public static Component getSuffixOfSpring2025() {
        return Component.literal("2025 - 乙巳春节")
                .withStyle(ChatFormatting.ITALIC).withStyle(CustomStyle.styleOfSpring);
    }

    public static Component getSuffixOfDivine() {
        return Component.literal("圣光岛")
                .withStyle(ChatFormatting.ITALIC).withStyle(CustomStyle.DIVINE_STYLE);
    }

    public static Component getSuffixOfGhastly() {
        return Component.literal("瑕光")
                .withStyle(ChatFormatting.ITALIC).withStyle(CustomStyle.GHASTLY_STYLE);
    }

    public static void runeAttributeDescription(List<Component> components) {
        components.add(Component.literal(" - ").withStyle(ChatFormatting.GRAY).
                append("符石属性:").withStyle(ChatFormatting.WHITE));
    }

    public static void descriptionPassive(List<Component> components, Component name) {
        components.add(Component.literal(" - ").withStyle(ChatFormatting.GRAY).
                append(Component.literal("被动 ").withStyle(ChatFormatting.GREEN)).
                append(name));
    }


    public static void solePassiveDescription(List<Component> components, Component name) {
        components.add(Component.literal(" - ").withStyle(ChatFormatting.GRAY).
                append(Component.literal("唯一被动 ").withStyle(CustomStyle.styleOfMoontain)).
                append(name));
    }

    public static void descriptionActive(List<Component> components, Component name) {
        components.add(Component.literal(" - ").withStyle(ChatFormatting.GRAY).
                append(Component.literal("主动 ").withStyle(ChatFormatting.AQUA)).
                append(name));
    }

    public static void suitEffectRateDescription(List<Component> components, int Count) {
        switch (Count) {
            case 1 -> {
                components.add(Component.literal("基于套装数量的数值:(").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC).
                        append(Component.literal("1_20%").withStyle(CustomStyle.styleOfSky)).
                        append(Component.literal(",2_50%,3_70%,4_100%)").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC)));
            }
            case 2 -> {
                components.add(Component.literal("基于套装数量的数值:(1_20%,").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC).
                        append(Component.literal("2_50%").withStyle(CustomStyle.styleOfSky)).
                        append(Component.literal(",3_70%,4_100%)").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC)));
            }
            case 3 -> {
                components.add(Component.literal("基于套装数量的数值:(1_20%,2_50%,").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC).
                        append(Component.literal("3_70%").withStyle(CustomStyle.styleOfSky)).
                        append(Component.literal(",4_100%)").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC)));
            }
            case 4 -> {
                components.add(Component.literal("基于套装数量的数值:(1_20%,2_50%,3_70%,").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC).
                        append(Component.literal("4_100%").withStyle(CustomStyle.styleOfSky)).
                        append(Component.literal(")").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC)));
            }
            default -> {
                components.add(Component.literal("基于套装数量的数值:(1_20%,2_50%,3_70%,4_100%").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC).
                        append(Component.literal(")").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC)));
            }
        }
    }

    public static void skySuitEffectRateDescription(List<Component> components, int Count) {
        switch (Count) {
            case 1 -> {
                components.add(Component.literal("基于套装数量的数值:(").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC).
                        append(Component.literal("1_20%").withStyle(CustomStyle.styleOfSky)).
                        append(Component.literal(",2_50%,3_140%,4_200%)").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC)));
            }
            case 2 -> {
                components.add(Component.literal("基于套装数量的数值:(1_20%,").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC).
                        append(Component.literal("2_50%").withStyle(CustomStyle.styleOfSky)).
                        append(Component.literal(",3_140%,4_200%)").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC)));
            }
            case 3 -> {
                components.add(Component.literal("基于套装数量的数值:(1_20%,2_50%,").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC).
                        append(Component.literal("3_140%").withStyle(CustomStyle.styleOfSky)).
                        append(Component.literal(",4_200%)").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC)));
            }
            case 4 -> {
                components.add(Component.literal("基于套装数量的数值:(1_20%,2_50%,3_140%,").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC).
                        append(Component.literal("4_200%").withStyle(CustomStyle.styleOfSky)).
                        append(Component.literal(")").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC)));
            }
            default -> {
                components.add(Component.literal("基于套装数量的数值:(1_20%,2_50%,3_140%,4_200%").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC).
                        append(Component.literal(")").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC)));
            }
        }
    }

    public static void suffixOfElement(List<Component> components) {
        components.add(getSuffixOfElement());
    }
    public static Component getSuffixOfElement() {
        for (int i = 0; i < Compute.colorList.size(); i++) {
            Color color = Compute.colorList.get(i);
            if (color.Add()) {
                Compute.colorList.set(i, new Color(color.targetRGB, Compute.colorMap.get(color.targetRGB), 100));
            }
        }
        return Component.literal("E").withStyle(Style.EMPTY.withColor(TextColor.parseColor(Compute.colorList.get(0).getRGB()))).withStyle(ChatFormatting.ITALIC).
                append(Component.literal("l").withStyle(Style.EMPTY.withColor(TextColor.parseColor(Compute.colorList.get(1).getRGB()))).withStyle(ChatFormatting.ITALIC)).
                append(Component.literal("e").withStyle(Style.EMPTY.withColor(TextColor.parseColor(Compute.colorList.get(2).getRGB()))).withStyle(ChatFormatting.ITALIC)).
                append(Component.literal("m").withStyle(Style.EMPTY.withColor(TextColor.parseColor(Compute.colorList.get(3).getRGB()))).withStyle(ChatFormatting.ITALIC)).
                append(Component.literal("e").withStyle(Style.EMPTY.withColor(TextColor.parseColor(Compute.colorList.get(4).getRGB()))).withStyle(ChatFormatting.ITALIC)).
                append(Component.literal("n").withStyle(Style.EMPTY.withColor(TextColor.parseColor(Compute.colorList.get(5).getRGB()))).withStyle(ChatFormatting.ITALIC)).
                append(Component.literal("t").withStyle(Style.EMPTY.withColor(TextColor.parseColor(Compute.colorList.get(6).getRGB()))).withStyle(ChatFormatting.ITALIC));
    }

    public static void coolDownTimeDescription(List<Component> components, int seconds) {
        components.add(Component.literal(" 冷却时间:").withStyle(ChatFormatting.WHITE).
                append(Component.literal(seconds + "s").withStyle(ChatFormatting.AQUA)));
    }

    public static void getStableCoolDownTimeDescription(List<Component> components, int seconds) {
        components.add(Component.literal(" 固定冷却时间:").withStyle(ChatFormatting.WHITE).
                append(Component.literal(seconds + "s").withStyle(ChatFormatting.AQUA)));
    }

    public static Component getCooldownTimeDescription(int seconds) {
        return Component.literal(" 冷却时间:").withStyle(ChatFormatting.WHITE).
                append(Component.literal(seconds + "s").withStyle(ChatFormatting.AQUA));
    }

    public static void coolDownTimeDescription(List<Component> components, double seconds) {
        components.add(Component.literal(" 冷却时间:").withStyle(ChatFormatting.WHITE).
                append(Component.literal(String.format("%.1f", seconds) + "s").withStyle(ChatFormatting.AQUA)));
    }

    public static void manaCostDescription(List<Component> components, int num) {
        components.add(Component.literal(" 法力消耗:").withStyle(ChatFormatting.WHITE).
                append(Component.literal(num + "").withStyle(ChatFormatting.LIGHT_PURPLE)));
    }

    public static Component getDefenceTypeDescriptionOfCurios() {
        return Component.literal(" 防御型饰品").withStyle(CustomStyle.styleOfPlain);
    }

    public static Component getAttackTypeDescriptionOfCurios() {
        return Component.literal(" 进攻型饰品").withStyle(CustomStyle.styleOfPower);
    }

    public static Component getFuncTypeDescriptionOfCurios() {
        return Component.literal(" 功能型饰品").withStyle(CustomStyle.styleOfWorld);
    }

    public static Component getComprehensiveTypeDescriptionOfCurios() {
        return Component.literal(" 综合型饰品").withStyle(CustomStyle.styleOfIce);
    }

    public static Component getAllTypeDescriptionOfCurios() {
        return Component.literal(" 全能型饰品").withStyle(CustomStyle.styleOfSakura);
    }

    public static Component getUniformSuffix() {
        /*return Component.literal("uniform - e").withStyle(ChatFormatting.AQUA);*/
        return Component.literal("联合研院").withStyle(CustomStyle.styleOfWorld).
                append(Component.literal(" - ").withStyle(ChatFormatting.YELLOW)).
                append(Component.literal("研制").withStyle(ChatFormatting.AQUA));
    }

    public static Component getDemonAndElementStorySuffix1Wraq() {
        return Component.literal("迪艾往事 - 新维地阵营").withStyle(CustomStyle.styleOfWorld).withStyle(ChatFormatting.ITALIC);
    }

    public static Component getDemonAndElementStorySuffix1Sakura() {
        return Component.literal("迪艾往事 - 新樱岛阵营").withStyle(CustomStyle.styleOfSakura).withStyle(ChatFormatting.ITALIC);
    }

    public static Component getDemonAndElementStorySuffix2Wraq() {
        return Component.literal("迪艾二战 - 天空城阵营").withStyle(CustomStyle.styleOfSky).withStyle(ChatFormatting.ITALIC);
    }

    public static Component getDemonAndElementStorySuffix2Sakura() {
        return Component.literal("迪艾二战 - 新樱岛阵营").withStyle(CustomStyle.styleOfDemon).withStyle(ChatFormatting.ITALIC);
    }

    public static Component getIntensifiedSuffix() {
        return Component.literal("激化-非稳定之物").withStyle(CustomStyle.styleOfPower).withStyle(ChatFormatting.ITALIC);
    }

    public static Component getSpurSuffix() {
        return Component.literal("支线-生产劳动所获").withStyle(ChatFormatting.AQUA);
    }

    public static Component getNormalPickaxeSuffix() {
        return Component.literal("史蒂夫圣剑").withStyle(ChatFormatting.AQUA);
    }

    public static Component getSuffixOfSouvenirs() {
        return Te.s(rainBowNameFiveChar("✧纪念品✧"));
    }

    public static Component rainBowNameFiveChar(String s) {
        for (int i = 0; i < RainbowCrystal.colorList.size(); i++) {
            fun.wraq.process.system.element.Color color = RainbowCrystal.colorList.get(i);
            if (color.Add()) {
                RainbowCrystal.colorList.set(i, new Color(color.targetRGB, RainbowCrystal.colorMap.get(color.targetRGB), 100));
            }
        }
        return Component.literal(s.substring(0, 1)).withStyle(Style.EMPTY.withColor(TextColor.parseColor(RainbowCrystal.colorList.get(0).getRGB()))).withStyle(ChatFormatting.BOLD)
                .append(Component.literal(s.substring(1, 2)).withStyle(Style.EMPTY.withColor(TextColor.parseColor(RainbowCrystal.colorList.get(1).getRGB()))).withStyle(ChatFormatting.BOLD))
                .append(Component.literal(s.substring(2, 3)).withStyle(Style.EMPTY.withColor(TextColor.parseColor(RainbowCrystal.colorList.get(2).getRGB()))).withStyle(ChatFormatting.BOLD))
                .append(Component.literal(s.substring(3, 4)).withStyle(Style.EMPTY.withColor(TextColor.parseColor(RainbowCrystal.colorList.get(3).getRGB()))).withStyle(ChatFormatting.BOLD))
                .append(Component.literal(s.substring(4, 5)).withStyle(Style.EMPTY.withColor(TextColor.parseColor(RainbowCrystal.colorList.get(0).getRGB()))).withStyle(ChatFormatting.BOLD));
    }

    public static Component getAttackDamageDotDescription(int lastSeconds, int times, String rate) {
        return Component.literal("在").withStyle(ChatFormatting.WHITE).
                append(Component.literal(lastSeconds + "s").withStyle(ChatFormatting.AQUA)).
                append(Component.literal("内造成").withStyle(ChatFormatting.WHITE)).
                append(Component.literal(times + "次").withStyle(ChatFormatting.YELLOW)).
                append(AttributeDescription.attackDamageValue(rate));
    }

    public static Component getCritDamageInfluenceDescription() {
        return Component.literal(" 这个伤害受").withStyle(ChatFormatting.WHITE).
                append(AttributeDescription.critRate("")).
                append(Component.literal("及").withStyle(ChatFormatting.WHITE)).
                append(AttributeDescription.critDamage("")).
                append(Component.literal("影响").withStyle(ChatFormatting.WHITE));
    }

    public static Component getAttackEffectDescription() {
        return Te.s("附带", "攻击特效", CustomStyle.styleOfSea);
    }

    public static Component getSuffixOfSummerEvent() {
        return Component.literal(" 2024暑期活动").withStyle(ChatFormatting.BOLD).withStyle(CustomStyle.styleOfPower);
    }

    public static Component getCommonDamageEnhance(String value) {
        return Te.m(value + "普通伤害提升", CustomStyle.styleOfMoon);
    }

    public static Component getAttackDamageEnhance(String value) {
        return Te.m(value + "物理伤害提升", CustomStyle.styleOfPower);
    }

    public static Component getManaDamageEnhance(String value) {
        return Te.m(value + "魔法伤害提升", CustomStyle.styleOfMana);
    }

    public static Component getNormalAttackDamageEnhance(String value) {
        return Te.m(value + "近战攻击增幅", ChatFormatting.YELLOW);
    }

    public static Component getBowAttackDamageEnhance(String value) {
        return Te.m(value + "箭矢攻击增幅", CustomStyle.styleOfFlexible);
    }

    public static Component getManaAttackDamageEnhance(String value) {
        return Te.m(value + "法球攻击增幅", CustomStyle.styleOfMana);
    }

    public static void emojiDescriptionBaseAttackDamage(List<Component> components, double BaseDamage) {
        components.add(Component.literal(Utils.Emoji.Sword + " 基础攻击").withStyle(ChatFormatting.AQUA).
                append(Component.literal(" " + String.format("%.0f", BaseDamage)).withStyle(ChatFormatting.WHITE)));
    }

    public static void soulEmojiDescriptionBaseAttackDamage(List<Component> components, double BaseDamage, int ForgeTimes) {
        components.add(Component.literal(Utils.Emoji.Sword + " 基础攻击").withStyle(ChatFormatting.AQUA).
                append(Component.literal(" " + String.format("%.0f", BaseDamage)).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("  ")).
                append(Component.literal("+ " + String.format("%.0f", SoulEquipAttribute.ForgingAddition.AttackDamage)).withStyle(CustomStyle.styleOfWorld)).
                append(Component.literal(" x [" + ForgeTimes + "]").withStyle(CustomStyle.styleOfWorld)));
    }

    public static void emojiDescriptionAttackSpeed(List<Component> components, double AttackSpeed) {
        components.add(Component.literal(Utils.Emoji.AttackSpeed + " 攻击速度").withStyle(CustomStyle.styleOfSky).
                append(Component.literal(" " + String.format("%.1f", (4 + AttackSpeed))).withStyle(ChatFormatting.WHITE)));
    }

    public static void emojiDescriptionDefencePenetration(List<Component> components, double DefencePenetration) {
        components.add(Component.literal(Utils.Emoji.Defence + " 护甲穿透").withStyle(ChatFormatting.GRAY).
                append(Component.literal("+" + String.format("%.0f%%", DefencePenetration * 100)).withStyle(ChatFormatting.WHITE)));
    }

    public static void soulEmojiDescriptionDefencePenetration(List<Component> components, double DefencePenetration, int ForgeTimes) {
        components.add(Component.literal(Utils.Emoji.Defence + " 护甲穿透").withStyle(ChatFormatting.GRAY).
                append(Component.literal("+" + String.format("%.0f%%", DefencePenetration * 100)).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("  ")).
                append(Component.literal("+ " + String.format("%.0f%%", SoulEquipAttribute.ForgingAddition.DefencePenetration * 100)).withStyle(CustomStyle.styleOfWorld)).
                append(Component.literal(" x [" + ForgeTimes + "]").withStyle(CustomStyle.styleOfWorld)));
    }

    public static void emojiDescriptionDefencePenetration0(List<Component> components, double DefencePenetration) {
        components.add(Component.literal(Utils.Emoji.Defence + " 护甲穿透").withStyle(ChatFormatting.GRAY).
                append(Component.literal("+" + String.format("%.0f", DefencePenetration)).withStyle(ChatFormatting.WHITE)));
    }

    public static void soulEmojiDescriptionDefencePenetration0(List<Component> components, double DefencePenetration, int ForgeTimes) {
        components.add(Component.literal(Utils.Emoji.Defence + " 护甲穿透").withStyle(ChatFormatting.GRAY).
                append(Component.literal("+" + String.format("%.0f", DefencePenetration)).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("  ")).
                append(Component.literal("+ " + String.format("%.0f", SoulEquipAttribute.ForgingAddition.DefencePenetration0)).withStyle(CustomStyle.styleOfWorld)).
                append(Component.literal(" x [" + ForgeTimes + "]").withStyle(CustomStyle.styleOfWorld)));
    }

    public static void emojiDescriptionCritRate(List<Component> components, double criticalHitRate) {
        components.add(Component.literal(Utils.Emoji.CritRate + " 暴击几率").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal("+" + String.format(criticalHitRate == 0 ? "%.0f%%" : "%.1f%%", criticalHitRate * 100)).withStyle(ChatFormatting.WHITE)));
    }

    public static void soulEmojiDescriptionCritRate(List<Component> components, double CriticalHitRate, int ForgeTimes) {
        components.add(Component.literal(Utils.Emoji.CritRate + " 暴击几率").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal("+" + String.format(CriticalHitRate == 0 ? "%.0f%%" : "%.1f%%", CriticalHitRate * 100)).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("  ")).
                append(Component.literal("+ " + String.format("%.1f%%", SoulEquipAttribute.ForgingAddition.CritRate * 100)).withStyle(CustomStyle.styleOfWorld)).
                append(Component.literal(" x [" + ForgeTimes + "]").withStyle(CustomStyle.styleOfWorld)));
    }

    public static void emojiDescriptionCritDamage(List<Component> components, double CriticalHitDamage) {
        components.add(Component.literal(Utils.Emoji.CritDamage + " 暴击伤害").withStyle(ChatFormatting.BLUE).
                append(Component.literal("+" + String.format("%.0f%%", CriticalHitDamage * 100)).withStyle(ChatFormatting.WHITE)));
    }

    public static void soulEmojiDescriptionCritDamage(List<Component> components, double CriticalHitDamage, int ForgeTimes) {
        components.add(Component.literal(Utils.Emoji.CritDamage + " 暴击伤害").withStyle(ChatFormatting.BLUE).
                append(Component.literal("+" + String.format("%.0f%%", CriticalHitDamage * 100)).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("  ")).
                append(Component.literal("+ " + String.format("%.0f%%", SoulEquipAttribute.ForgingAddition.CritDamage * 100)).withStyle(CustomStyle.styleOfWorld)).
                append(Component.literal(" x [" + ForgeTimes + "]").withStyle(CustomStyle.styleOfWorld)));
    }

    public static void emojiDescriptionHealSteal(List<Component> components, double HealSteal) {
        components.add(Component.literal(Utils.Emoji.HealSteal + " 生命偷取").withStyle(ChatFormatting.RED).
                append(Component.literal("+" + String.format("%.0f‱", HealSteal * 100)).withStyle(ChatFormatting.WHITE)));
    }

    public static void emojiDescriptionManaHealSteal(List<Component> components, double HealSteal) {
        components.add(Component.literal(Utils.Emoji.HealSteal + " 法术吸血").withStyle(CustomStyle.styleOfMana).
                append(Component.literal("+" + String.format("%.0f‱", HealSteal * 100)).withStyle(ChatFormatting.WHITE)));
    }

    public static void soulEmojiDescriptionHealSteal(List<Component> components, double HealSteal, int ForgeTimes) {
        components.add(Component.literal(Utils.Emoji.HealSteal + " 生命偷取").withStyle(ChatFormatting.RED).
                append(Component.literal("+" + String.format("%.0f‱", HealSteal * 100)).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("  ")).
                append(Component.literal("+ " + String.format("%.0f‱", SoulEquipAttribute.ForgingAddition.HealthSteal * 100)).withStyle(CustomStyle.styleOfWorld)).
                append(Component.literal(" x [" + ForgeTimes + "]").withStyle(CustomStyle.styleOfWorld)));
    }

    public static void emojiDescriptionCommonMovementSpeed(List<Component> components, double movementSpeed) {
        if (movementSpeed >= 0) {
            components.add(Component.literal(Utils.Emoji.Speed + " 移动速度").withStyle(ChatFormatting.GREEN).
                    append(Component.literal("+" + String.format(movementSpeed == 0 ? "%.0f%%" : "%.1f%%", movementSpeed * 100)).withStyle(ChatFormatting.WHITE)));
        } else {
            components.add(Component.literal(Utils.Emoji.Speed + " 移动速度").withStyle(ChatFormatting.RED).
                    append(Component.literal("-" + String.format(movementSpeed == 0 ? "%.0f%%" : "%.1f%%", -movementSpeed * 100)).withStyle(ChatFormatting.WHITE)));

        }
    }

    public static void soulEmojiDescriptionMovementSpeed(List<Component> components, double MovementSpeed, int ForgeTimes) {
        if (MovementSpeed >= 0) {
            components.add(Component.literal(Utils.Emoji.Speed + " 移动速度").withStyle(ChatFormatting.GREEN).
                    append(Component.literal("+" + String.format("%.0f%%", MovementSpeed * 100)).withStyle(ChatFormatting.WHITE)).
                    append(Component.literal("  ")).
                    append(Component.literal("+ " + String.format("%.0f%%", SoulEquipAttribute.ForgingAddition.MovementSpeed * 100)).withStyle(CustomStyle.styleOfWorld)).
                    append(Component.literal(" x [" + ForgeTimes + "]").withStyle(CustomStyle.styleOfWorld)));
        } else {
            components.add(Component.literal(Utils.Emoji.Speed + " 移动速度").withStyle(ChatFormatting.RED).
                    append(Component.literal("-" + String.format("%.0f%%", -MovementSpeed * 100)).withStyle(ChatFormatting.WHITE)).
                    append(Component.literal("  ")).
                    append(Component.literal("+ " + String.format("%.0f%%", SoulEquipAttribute.ForgingAddition.MovementSpeed * 100)).withStyle(CustomStyle.styleOfWorld)).
                    append(Component.literal(" x [" + ForgeTimes + "]").withStyle(CustomStyle.styleOfWorld)));

        }
    }

    public static void emojiDescriptionAttackRange(List<Component> components, double attackRangeUp) {
        components.add(Component.literal(Utils.Emoji.AttackRange + " 攻击距离").withStyle(CustomStyle.styleOfSea).
                append(Component.literal("+" + String.format(attackRangeUp == 0 ? "%.0f" : "%.2f", attackRangeUp)).withStyle(ChatFormatting.WHITE)));
    }

    public static void emojiDescriptionManaAttackDamage(List<Component> components, double ManaDamage) {
        components.add(Component.literal(Utils.Emoji.Mana + " 魔法攻击").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal(" " + String.format("%.0f", ManaDamage)).withStyle(ChatFormatting.WHITE)));
    }

    public static void soulEmojiDescriptionManaAttackDamage(List<Component> components, double ManaDamage, int ForgeTimes) {
        components.add(Component.literal(Utils.Emoji.Mana + " 魔法攻击").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal(" " + String.format("%.0f", ManaDamage)).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("  ")).
                append(Component.literal("+ " + String.format("%.0f", SoulEquipAttribute.ForgingAddition.ManaAttackDamage)).withStyle(CustomStyle.styleOfWorld)).
                append(Component.literal(" x [" + ForgeTimes + "]").withStyle(CustomStyle.styleOfWorld)));
    }

    public static void manaCostDescription(List<Component> components, double ManaCost) {
        components.add(Component.literal(Utils.Emoji.ManaCost + " 法力消耗").withStyle(ChatFormatting.DARK_PURPLE).
                append(Component.literal(" " + String.format("%.0f", ManaCost)).withStyle(ChatFormatting.WHITE)));
    }

    public static void soulManaCostDescription(List<Component> components, double ManaCost, int ForgeTimes) {
        components.add(Component.literal(Utils.Emoji.ManaCost + " 法力消耗").withStyle(ChatFormatting.DARK_PURPLE).
                append(Component.literal(" " + String.format("%.0f", ManaCost)).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("  ")).
                append(Component.literal("- " + String.format("%.0f", SoulEquipAttribute.ForgingAddition.ManaCost)).withStyle(CustomStyle.styleOfWorld)).
                append(Component.literal(" x [" + ForgeTimes + "]").withStyle(CustomStyle.styleOfWorld)));
    }

    public static void emojiDescriptionManaPenetration(List<Component> components, double ManaPenetration) {
        components.add(Component.literal(Utils.Emoji.Defence + " 魔法穿透").withStyle(ChatFormatting.BLUE).
                append(Component.literal("+" + String.format("%.0f%%", ManaPenetration * 100)).withStyle(ChatFormatting.WHITE)));
    }

    public static void soulEmojiDescriptionManaPenetration(List<Component> components, double ManaPenetration, int ForgeTimes) {
        components.add(Component.literal(Utils.Emoji.Defence + " 魔法穿透").withStyle(ChatFormatting.BLUE).
                append(Component.literal("+" + String.format("%.0f%%", ManaPenetration * 100)).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("  ")).
                append(Component.literal("+ " + String.format("%.0f%%", SoulEquipAttribute.ForgingAddition.ManaPenetration * 100)).withStyle(CustomStyle.styleOfWorld)).
                append(Component.literal(" x [" + ForgeTimes + "]").withStyle(CustomStyle.styleOfWorld)));
    }

    public static void emojiDescriptionManaPenetration0(List<Component> components, double ManaPenetration0) {
        components.add(Component.literal(Utils.Emoji.Defence + " 魔法穿透").withStyle(ChatFormatting.BLUE).
                append(Component.literal("+" + String.format("%.0f", ManaPenetration0)).withStyle(ChatFormatting.WHITE)));
    }

    public static void soulEmojiDescriptionManaPenetration0(List<Component> components, double ManaPenetration0, int ForgeTimes) {
        components.add(Component.literal(Utils.Emoji.Defence + " 魔法穿透").withStyle(ChatFormatting.BLUE).
                append(Component.literal("+" + String.format("%.0f", ManaPenetration0)).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("  ")).
                append(Component.literal("+ " + String.format("%.0f", SoulEquipAttribute.ForgingAddition.ManaPenetration0)).withStyle(CustomStyle.styleOfWorld)).
                append(Component.literal(" x [" + ForgeTimes + "]").withStyle(CustomStyle.styleOfWorld)));
    }

    public static void emojiDescriptionManaRecover(List<Component> components, double manaRecover) {
        components.add(Component.literal(Utils.Emoji.ManaRecover + " 法力回复").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal("+" + String.format(manaRecover == 0 ? "%.0f" : "%.1f", manaRecover)).withStyle(ChatFormatting.WHITE)));
    }

    public static void soulEmojiDescriptionManaRecover(List<Component> components, double ManaRecover, int ForgeTimes) {
        components.add(Component.literal(Utils.Emoji.ManaRecover + " 法力回复").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal("+" + String.format("%.0f", ManaRecover)).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("  ")).
                append(Component.literal("+ " + String.format("%.0f", SoulEquipAttribute.ForgingAddition.ManaRecover)).withStyle(CustomStyle.styleOfWorld)).
                append(Component.literal(" x [" + ForgeTimes + "]").withStyle(CustomStyle.styleOfWorld)));
    }

    public static void emojiDescriptionMaxHealth(List<Component> components, double MaxHealth) {
        if (MaxHealth < 0) {
            components.add(Component.literal(Utils.Emoji.Health + " 最大生命值").withStyle(ChatFormatting.GREEN).
                    append(Component.literal("-" + String.format("%.0f", -MaxHealth)).withStyle(ChatFormatting.RED)));

        } else {
            components.add(Component.literal(Utils.Emoji.Health + " 最大生命值").withStyle(ChatFormatting.GREEN).
                    append(Component.literal("+" + String.format("%.0f", MaxHealth)).withStyle(ChatFormatting.WHITE)));
        }
    }


    public static void emojiDescriptionHealAmplification(List<Component> components, double HealEffect) {
        components.add(Component.literal(Utils.Emoji.HealthAmplification + " 治疗强度").withStyle(CustomStyle.styleOfHealth).
                append(Component.literal("+" + String.format("%.0f%%", HealEffect * 100)).withStyle(ChatFormatting.WHITE)));
    }

    public static void emojiDescriptionExpUp(List<Component> components, double ExpUp) {
        components.add(Component.literal(Utils.Emoji.ExpUp + " 经验加成").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal("+" + String.format("%.0f%%", ExpUp * 100)).withStyle(ChatFormatting.WHITE)));
    }

    public static void emojiDescriptionLuckyUp(List<Component> components, double Lucky) {
        components.add(Component.literal(Utils.Emoji.Lucky + " 幸运加成").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal("+" + String.format("%.0f%%", Lucky * 100)).withStyle(ChatFormatting.WHITE)));
    }

    public static void emojiDescriptionDefenceRate(List<Component> components, double Defence) {
        components.add(Component.literal(Utils.Emoji.Defence + " 护甲加成").withStyle(ChatFormatting.GRAY).
                append(Component.literal("+" + String.format("%.0f%%", Defence * 100)).withStyle(ChatFormatting.WHITE)));
    }

    public static void emojiDescriptionExAttackDamageRate(List<Component> components, double ExDamageRate) {
        components.add(Component.literal(Utils.Emoji.Sword + " 额外攻击").withStyle(ChatFormatting.YELLOW).
                append(Component.literal(" " + String.format("%.0f%%", ExDamageRate * 100)).withStyle(ChatFormatting.WHITE)));
    }

    public static void emojiDescriptionCoolDown(List<Component> components, double CoolDown) {
        components.add(Component.literal(Utils.Emoji.CoolDown + " 技能急速").withStyle(ChatFormatting.AQUA).
                append(Component.literal(" " + String.format("%.0f", CoolDown * 100)).withStyle(ChatFormatting.WHITE)));
    }

    public static void emojiDescriptionDefence(List<Component> components, double Defence) {
        components.add(Component.literal(Utils.Emoji.Defence + " 基础护甲").withStyle(ChatFormatting.GRAY).
                append(Component.literal("+" + String.format("%.0f", Defence)).withStyle(ChatFormatting.WHITE)));
    }

    public static void emojiDescriptionDamageDirectDecrease(List<Component> components, double value) {
        components.add(Component.literal(Utils.Emoji.Defence + " 伤害削减").withStyle(CustomStyle.styleOfStone).
                append(Component.literal("+" + String.format("%.0f", value)).withStyle(ChatFormatting.WHITE)));
    }

    public static void emojiDescriptionHealthRecover(List<Component> components, double healthReplay) {
        components.add(Component.literal(Utils.Emoji.HealthRecover + " 生命回复").withStyle(ChatFormatting.GREEN).
                append(Component.literal("+" + String.format(healthReplay == 0 ? "%.0f" : "%.1f", healthReplay)).withStyle(ChatFormatting.WHITE)));
    }

    public static void emojiDescriptionExAttackDamage(List<Component> components, double ExDamage) {
        components.add(Component.literal(Utils.Emoji.Sword + " 额外攻击").withStyle(ChatFormatting.YELLOW).
                append(Component.literal(" " + String.format("%.0f", ExDamage)).withStyle(ChatFormatting.WHITE)));
    }

    public static void emojiDescriptionManaDefence(List<Component> components, double Defence) {
        components.add(Component.literal(Utils.Emoji.Defence + " 魔法抗性").withStyle(ChatFormatting.BLUE).
                append(Component.literal("+" + String.format("%.0f", Defence)).withStyle(ChatFormatting.WHITE)));
    }

    public static void emojiDescriptionMaxMana(List<Component> components, double MaxMana) {
        components.add(Component.literal(Utils.Emoji.MaxMana + " 最大法力值").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal("+" + String.format("%.0f", MaxMana)).withStyle(ChatFormatting.WHITE)));
    }

    public static void soulEmojiDescriptionMaxMana(List<Component> components, double MaxMana, int ForgeTimes) {
        components.add(Component.literal(Utils.Emoji.MaxMana + " 最大法力值").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal("+" + String.format("%.0f", MaxMana)).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("  ")).
                append(Component.literal("+ 16 x [" + ForgeTimes + "]").withStyle(CustomStyle.styleOfWorld)));
    }

    public static void emojiDescriptionSwiftness(List<Component> components, double swiftness) {
        components.add(Component.literal(Utils.Emoji.Swiftness + " 迅捷").withStyle(ChatFormatting.GREEN).
                append(Component.literal("+" + String.format(swiftness == 0 ? "%.0f" : "%.2f", swiftness)).withStyle(ChatFormatting.WHITE)));
    }

    public static void emojiDescriptionToughness(List<Component> components, double toughness) {
        components.add(Te.s(Utils.Emoji.Speed + " " + "韧性", CustomStyle.styleOfEnd, Te.s("+",
                BasicAttributeDescription.getDecimal(toughness, 1))));
    }

    public static void descriptionDash(List<Component> components, ChatFormatting chatFormatting0, ChatFormatting chatFormatting1, ChatFormatting chatFormatting2) {
        components.add(Component.literal("─").withStyle(chatFormatting0).
                append(Component.literal("───────────────────").withStyle(chatFormatting1).
                        append(Component.literal("─").withStyle(chatFormatting2))));
    }

    public static void descriptionDash(List<Component> components, ChatFormatting chatFormatting0, Style chatFormatting1, ChatFormatting chatFormatting2) {
        components.add(Component.literal("─").withStyle(chatFormatting0).
                append(Component.literal("───────────────────").withStyle(chatFormatting1).
                        append(Component.literal("─").withStyle(chatFormatting2))));
    }

    public static void descriptionDash(List<Component> components, Style style) {
        components.add(Te.s("─", "─".repeat(19), style, "─"));
    }

    public static void descriptionOfBasic(List<Component> components) {
        components.add(Component.literal("β-基础属性:").withStyle(CustomStyle.styleOfPlain));
    }


    public static void descriptionOfOnly(List<Component> components) {
        components.add(Component.literal("-唯一:").withStyle(ChatFormatting.WHITE).withStyle(ChatFormatting.GREEN));
    }

    public static void descriptionOfAddition(List<Component> components) {
        components.add(Component.literal("α-额外属性:").withStyle(CustomStyle.styleOfPower));
    }

    public static void descriptionNum(List<Component> components, String string, Component component, String string1) {
        components.add(Component.literal(string).withStyle(ChatFormatting.WHITE).
                append(component).append(string1).withStyle(ChatFormatting.WHITE));
    }

    public static Component getAutoAdaptDamageDescription(String value) {
        return Te.m(value + "自适应伤害", ChatFormatting.LIGHT_PURPLE);
    }

    public static Component getAutoAdaptTrueDamageDescription(String value) {
        return Te.m(value + "自适应真实伤害", CustomStyle.styleOfSea);
    }

    public static Component exAttackDamage(String content) {
        return Component.literal(content + "额外物理伤害").withStyle(CustomStyle.styleOfPower);
    }

    public static Component exManaDamage(String content) {
        return Component.literal(content + "额外魔法伤害").withStyle(CustomStyle.styleOfMana);
    }

    public static Component exTrueDamage(String content) {
        return Component.literal(content + "额外真实伤害").withStyle(CustomStyle.styleOfSea);
    }

    public static List<Component> getItemStackDescriptionList(List<ItemStack> list) {
        List<Component> components = new ArrayList<>();
        for (ItemStack stack : list) {
            components.add(Te.s(stack, " * " + stack.getCount(), CustomStyle.styleOfWorld));
        }
        return components;
    }

    public static Component getRightAngleQuote(String content, Style style) {
        return Te.s("「" + content + "」", style);
    }

    public static Component getProgressBar(int length, double count, double maxCount, Style style) {
        int num = (int) (Math.min(count, maxCount) / maxCount * length);
        return Te.s("|".repeat(num), style, "|".repeat(length - num), ChatFormatting.GRAY);
    }
}
