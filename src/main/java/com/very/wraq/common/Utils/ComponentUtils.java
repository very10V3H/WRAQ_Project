package com.very.wraq.common.Utils;

import com.very.wraq.process.system.element.Color;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;

import java.util.List;

public class ComponentUtils {
    public static class AttributeDescription {

        public static Component ExpUp(String content) {
            return Component.literal(Utils.Emoji.ExpUp + " " + content + "经验加成").withStyle(ChatFormatting.LIGHT_PURPLE);
        }

        public static Component MaxHealth(String content) {
            return Component.literal(Utils.Emoji.Health + " " + content + "最大生命值").withStyle(ChatFormatting.GREEN);
        }

        public static Component LossHealth(String content) {
            return Component.literal(Utils.Emoji.Health + " " + content + "已损失生命值").withStyle(ChatFormatting.DARK_GREEN);
        }

        public static Component Swiftness(String content) {
            return Component.literal(Utils.Emoji.Swiftness + " " + content + "迅捷").withStyle(ChatFormatting.GREEN);
        }

        public static Component movementSpeed(String content) {
            return Component.literal(Utils.Emoji.Speed + " " + content + "移动速度").withStyle(ChatFormatting.GREEN);
        }

        public static Component movementSpeedWithoutBattle(String content) {
            return Component.literal(Utils.Emoji.Speed + " " + content + "移动速度").withStyle(ChatFormatting.GREEN);
        }

        public static Component ExMovementSpeed(String content) {
            return Component.literal(Utils.Emoji.Speed + " " + content + "额外移动速度").withStyle(ChatFormatting.GREEN);
        }

        public static Component MovementSpeedDecrease(String content) {
            return Component.literal(Utils.Emoji.Speed + " " + content + "移动速度").withStyle(ChatFormatting.RED);
        }

        public static Component ManaDamage(String content) {
            return Component.literal(Utils.Emoji.Mana + " " + content + "魔法攻击").withStyle(ChatFormatting.LIGHT_PURPLE);
        }

        public static Component ManaCost(String content) {
            return Component.literal(Utils.Emoji.ManaCost + " " + content + "法力消耗").withStyle(ChatFormatting.LIGHT_PURPLE);
        }

        public static Component ExManaDamage(String content) {
            return Component.literal(Utils.Emoji.Mana + " " + content + "额外魔法攻击").withStyle(ChatFormatting.LIGHT_PURPLE);
        }

        public static Component ManaRecover(String content) {
            return Component.literal(Utils.Emoji.ManaRecover + " " + content + "法力回复").withStyle(ChatFormatting.LIGHT_PURPLE);
        }

        public static Component Health(String content) {
            return Component.literal(Utils.Emoji.Health + " " + content + "生命值").withStyle(ChatFormatting.GREEN);
        }

        public static Component Defence(String content) {
            return Component.literal(Utils.Emoji.Defence + " " + content + "护甲").withStyle(ChatFormatting.GRAY);
        }

        public static Component HealAmplification(String content) {
            return Component.literal(Utils.Emoji.HealthAmplification + " " + content + "治疗强度").withStyle(CustomStyle.styleOfHealth);
        }

        public static Component HealthRecover(String content) {
            return Component.literal(Utils.Emoji.HealthRecover + " " + content + "生命回复").withStyle(CustomStyle.styleOfHealth);
        }

        public static Component ManaDefence(String content) {
            return Component.literal(Utils.Emoji.Defence + " " + content + "魔法抗性").withStyle(ChatFormatting.BLUE);
        }

        public static Component ExAttackDamage(String content) {
            return Component.literal(Utils.Emoji.Sword + " " + content + "额外攻击力").withStyle(ChatFormatting.YELLOW);
        }

        public static Component DefencePenetration(String content) {
            return Component.literal(Utils.Emoji.Defence + " " + content + "护甲穿透").withStyle(ChatFormatting.GRAY);
        }

        public static Component CritDamage(String content) {
            return Component.literal(Utils.Emoji.CritDamage + " " + content + "暴击伤害").withStyle(ChatFormatting.BLUE);
        }

        public static Component CritRate(String content) {
            return Component.literal(Utils.Emoji.CritRate + " " + content + "暴击几率").withStyle(ChatFormatting.LIGHT_PURPLE);
        }

        public static Component AttackDamage(String content) {
            return Component.literal(Utils.Emoji.Sword + " " + content + "攻击力").withStyle(ChatFormatting.YELLOW);
        }

        public static Component ManaPenetration(String content) {
            return Component.literal(Utils.Emoji.Defence + " " + content + "法术穿透").withStyle(ChatFormatting.BLUE);
        }

        public static Component CoolDown(String content) {
            return Component.literal(Utils.Emoji.CoolDown + " " + content + "技能急速").withStyle(ChatFormatting.AQUA);
        }

        public static Component HealthSteal(String content) {
            return Component.literal(Utils.Emoji.HealSteal + " " + content + "生命偷取").withStyle(ChatFormatting.RED);
        }

        public static Component SkillHealthSteal(String content) {
            return Component.literal(Utils.Emoji.HealSteal + " " + content + "全能吸血").withStyle(CustomStyle.styleOfField);
        }

        public static Component ManaHealSteal(String content) {
            return Component.literal(Utils.Emoji.HealSteal + " " + content + "法术吸血").withStyle(CustomStyle.styleOfMana);
        }

        public static Component MaxMana(String content) {
            return Component.literal(Utils.Emoji.MaxMana + " " + content + "法力值").withStyle(CustomStyle.styleOfMana);
        }

        public static Component ExHealth(String content) {
            return Component.literal(Utils.Emoji.Health + " " + content + "额外生命值").withStyle(ChatFormatting.GREEN);
        }

        public static Component AttackRange(String content) {
            return Component.literal(Utils.Emoji.AttackRange + " " + content + "攻击距离").withStyle(CustomStyle.styleOfSea);
        }

        public static Component AttackDamageValue(String content) {
            return Component.literal(Utils.Emoji.Sword + " " + content + "物理伤害").withStyle(ChatFormatting.YELLOW);
        }

        public static Component ManaDamageValue(String content) {
            return Component.literal(Utils.Emoji.Mana + " " + content + "魔法伤害").withStyle(ChatFormatting.LIGHT_PURPLE);
        }
    }

    public static void SuitDescription(List<Component> components) {
        components.add(Component.literal(Utils.Emoji.Suit + " " + "套装效果").withStyle(ChatFormatting.AQUA));
    }

    public static void SuitDoubleDescription(List<Component> components) {
        components.add(Component.literal("▷2件套效果:").withStyle(ChatFormatting.YELLOW));
    }

    public static void SuitDoubleDescription(List<Component> components, int Count) {
        if (Count >= 2)
            components.add(Component.literal("▷2件套效果:").withStyle(ChatFormatting.YELLOW));
        else
            components.add(Component.literal("▷2件套效果:").withStyle(ChatFormatting.GRAY));
    }

    public static void SuitQuadraDescription(List<Component> components) {
        components.add(Component.literal("▷4件套效果:").withStyle(ChatFormatting.LIGHT_PURPLE));
    }

    public static void SuitQuadraDescription(List<Component> components, int Count) {
        if (Count >= 4)
            components.add(Component.literal("▷4件套效果:").withStyle(ChatFormatting.LIGHT_PURPLE));
        else
            components.add(Component.literal("▷4件套效果:").withStyle(ChatFormatting.GRAY));
    }

    public static void suffixOfChapterI(List<Component> components) {
        components.add(Component.literal("ChapterI").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
    }

    public static Component getSuffixOfChapterI() {
        return Component.literal("ChapterI").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC);
    }

    public static void suffixOfChapterII(List<Component> components) {
        components.add(Component.literal("ChapterII").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
    }

    public static Component getSuffixChapterII() {
        return Component.literal("ChapterII").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC);
    }

    public static void suffixOfChapterIII(List<Component> components) {
        components.add(Component.literal("ChapterIII").withStyle(CustomStyle.styleOfNether).withStyle(ChatFormatting.ITALIC));
    }

    public static Component getSuffixChapterIII() {
        return Component.literal("ChapterIII").withStyle(CustomStyle.styleOfNether).withStyle(ChatFormatting.ITALIC);
    }

    public static Component getSuffixOfIgniteRevenant() {
        return Component.literal("IgniteRevenant").withStyle(CustomStyle.styleOfPower).withStyle(ChatFormatting.ITALIC);
    }

    public static void suffixOfChapterIV(List<Component> components) {
        components.add(Component.literal("ChapterIV").withStyle(CustomStyle.styleOfEnd).withStyle(ChatFormatting.ITALIC));
    }

    public static Component getSuffixChapterIV() {
        return Component.literal("ChapterIV").withStyle(CustomStyle.styleOfEnd).withStyle(ChatFormatting.ITALIC);
    }

    public static void suffixOfChapterV(List<Component> components) {
        components.add(Component.literal("ChapterV").withStyle(CustomStyle.styleOfSakura).withStyle(ChatFormatting.ITALIC));
    }

    public static Component getSuffixOfChapterV() {
        return Component.literal("ChapterV").withStyle(CustomStyle.styleOfSakura).withStyle(ChatFormatting.ITALIC);
    }

    public static void suffixOfMoon(List<Component> components) {
        components.add(Component.literal("MoonPalace").withStyle(CustomStyle.styleOfMoon).withStyle(ChatFormatting.ITALIC));
    }

    public static Component getSuffixOfMoon() {
        return Component.literal("MoonPalace").withStyle(CustomStyle.styleOfMoon).withStyle(ChatFormatting.ITALIC);
    }

    public static void suffixOfCastle(List<Component> components) {
        components.add(Component.literal("BlackCastle").withStyle(CustomStyle.styleOfCastle).withStyle(ChatFormatting.ITALIC));
    }

    public static Component getSuffixOfCastle() {
        return Component.literal("BlackCastle").withStyle(CustomStyle.styleOfCastle).withStyle(ChatFormatting.ITALIC);
    }

    public static Component getSuffixOfPurpleIron() {
        return Component.literal("PurpleIronKnight").withStyle(CustomStyle.styleOfPurpleIron).withStyle(ChatFormatting.ITALIC);
    }

    public static Component getSuffixOfPlainBoss() {
        return Component.literal("Plain").withStyle(CustomStyle.styleOfPlain).withStyle(ChatFormatting.ITALIC);
    }

    public static void SuffixOfPurpleIronKnight(List<Component> components) {
        components.add(Component.literal("PurpleIronKnight").withStyle(CustomStyle.styleOfPurpleIron).withStyle(ChatFormatting.ITALIC));
    }

    public static void SuffixOfIce(List<Component> components) {
        components.add(Component.literal("Ice").withStyle(CustomStyle.styleOfIce).withStyle(ChatFormatting.ITALIC));
    }

    public static void suffixOfWorldSoul(List<Component> components) {
        components.add(Component.literal("WorldSoul").withStyle(CustomStyle.styleOfWorld).withStyle(ChatFormatting.ITALIC));
    }

    public static Component getSuffixOfWorldSoul() {
        return Component.literal("WorldSoul").withStyle(CustomStyle.styleOfWorld).withStyle(ChatFormatting.ITALIC);
    }

    public static void SuffixOfMainStoryIV(List<Component> components) {
        components.add(Component.literal("MainStoryIV").withStyle(CustomStyle.styleOfEnd).withStyle(ChatFormatting.ITALIC));
    }

    public static void SuffixOfMainStoryVII(List<Component> components) {
        components.add(Component.literal("ChapterVII").withStyle(CustomStyle.styleOfMoon1).withStyle(ChatFormatting.ITALIC));
    }

    public static Component getSuffixOfChapterVII() {
        return Component.literal("ChapterVII").withStyle(CustomStyle.styleOfMoon1).withStyle(ChatFormatting.ITALIC);
    }

    public static void RuneAttributeDescription(List<Component> components) {
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
                append(Component.literal("唯一被动 ").withStyle(ChatFormatting.GREEN)).
                append(name));
    }

    public static void descriptionActive(List<Component> components, Component name) {
        components.add(Component.literal(" - ").withStyle(ChatFormatting.GRAY).
                append(Component.literal("主动 ").withStyle(ChatFormatting.AQUA)).
                append(name));
    }

    public static void SuitEffectRateDescription(List<Component> components, int Count) {
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

    public static void SkySuitEffectRateDescription(List<Component> components, int Count) {
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

    public static Component getSuffixOfIce() {
        return Component.literal("IceMemory").withStyle(CustomStyle.styleOfIce).withStyle(ChatFormatting.ITALIC);
    }

    public static Component getSuffixOfSakura() {
        return Component.literal("Sakura").withStyle(CustomStyle.styleOfSakura).withStyle(ChatFormatting.ITALIC);
    }

    public static Component getSuffixOfDemon() {
        return Component.literal("Demon").withStyle(CustomStyle.styleOfDemon).withStyle(ChatFormatting.ITALIC);
    }

    public static void coolDownTimeDescription(List<Component> components, int seconds) {
        components.add(Component.literal(" 冷却时间:").withStyle(ChatFormatting.WHITE).
                append(Component.literal(seconds + "s").withStyle(ChatFormatting.AQUA)));
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
        return Component.literal("Intensified").withStyle(CustomStyle.styleOfPower).withStyle(ChatFormatting.ITALIC);
    }

    public static Component getSpurSuffix() {
        return Component.literal("Wraq-Spur").withStyle(ChatFormatting.AQUA);
    }

    public static Component getAttackDamageDotDescription(int lastSeconds, int times, String rate) {
        return Component.literal("在").withStyle(ChatFormatting.WHITE).
                append(Component.literal(lastSeconds + "s").withStyle(ChatFormatting.AQUA)).
                append(Component.literal("内造成").withStyle(ChatFormatting.WHITE)).
                append(Component.literal(times + "次").withStyle(ChatFormatting.YELLOW)).
                append(ComponentUtils.AttributeDescription.AttackDamageValue(rate));
    }

    public static Component getCritDamageInfluenceDescription() {
        return Component.literal(" 这个伤害受").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.CritRate("")).
                append(Component.literal("及").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.CritDamage("")).
                append(Component.literal("影响").withStyle(ChatFormatting.WHITE));
    }

    public static Component getSuffixOfSummerEvent() {
        return Component.literal(" 2024暑期活动").withStyle(ChatFormatting.BOLD).withStyle(CustomStyle.styleOfPower);
    }

    public static Component getCommonDamageEnhance(String value) {
        return Component.literal(value + "普通伤害提升").withStyle(CustomStyle.styleOfMoon);
    }
}
