package com.Very.very.ValueAndTools.Utils;

public class StringUtils {
    public static String SkillPoint_Total = "SkillPoint_Total";
    public static String AbilityPoint_Total = "AbilityPoint_Total";
    public static String SkillPoint_Used = "SkillPoint_Used";
    public static String AbilityPoint_Used = "AbilityPoint_Used";
    public static class Ability {
        public static String Power = "Ability_Power";
        public static String Intelligent = "Ability_Intelligent";
        public static String Flexibility = "Ability_Flexibility";
        public static String Lucky = "Ability_Lucky";
        public static String Vitality = "Ability_Vitality";
    }
    public static String[] AbilityArray = {
            "Ability_Power",
            "Ability_Intelligent",
            "Ability_Flexibility",
            "Ability_Lucky",
            "Ability_Vitality"
    };
    public static class Skill {
        public static String SwordBase = "Skill_SwordBase";
        public static String BowBase = "Skill_BowBase";
        public static String ManaBase = "Skill_ManaBase";
    }
    public static String[] SkillArray = {
            "Skill_SwordBase",
            "Skill_BowBase",
            "Skill_ManaBase"
    };
    public static class SkillData {
        public static String Sword = "SkillData_Sword";
        public static String Bow = "SkillData_Bow";
        public static String Mana = "SkillData_Mana";
    }
    public static String[] TickStringArray = {
            "Critical0","Critical1","Critical2",
            "Critical3","Critical4","volcanobow",
            "plainbow","lakesword","BreakDefence",
            "BreakDefenceX","DGreen1","NetherRune2",
            SwordSkillNum.Skill2,SwordSkillNum.Skill5,
            BowSkillNum.Skill2,BowSkillNum.Skill13,
            BowSkillNum.Skill14,BowSkillNum.Skill5,
            ManaSkillNum.Skill2,ManaSkillNum.Skill14
    };
    public static class VolcanoSwordSkill {
        public static String Skill0 = "Critical0";
        public static String Skill1 = "Critical1";
        public static String Skill2 = "Critical2";
        public static String Skill3 = "Critical3";
        public static String Skill4 = "Critical4";
    }
    public static String VolcanoBowSkill = "volcanobow";
    public static String PlainBowSkill = "plainbow";
    public static String LakeSwordSkill = "lakesword";
    public static String ForestSwordSkill0 = "BreakDefence";
    public static String ForestSwordSkill1 = "BreakDefenceX";
    public static class ForestRune {
        public static String ForestRune = "DGreen";
        public static String ForestRune1 = "DGreen1";
        public static String ForestRune2 = "DGreen2";
    }
    public static class NetherRune {
        public static String NetherRune2 = "NetherRune2";
    }
    public static class SwordSkillNum {
        public static String Skill2 = "SwordSkill2";
        public static String Skill5 = "SwordSkill5";
    }
    public static class BowSkillNum {
        public static String Skill2 = "BowSkill2";
        public static String Skill5 = "BowSkill5";
        public static String Skill13 = "BowSkill13";
        public static String Skill14 = "BowSkill14";
    }
    public static class ManaSkillNum {
        public static String Skill2 = "ManaSkill2";
        public static String Skill14 = "ManaSkill14";
    }
    public static class DamageTypes {
        public static String ToPlayerDamage = "ToPlayerDamage";
        public static String DamageAmount = "DamageAmount";
        public static String DamageIgnoreDefenceAmount = "DamageIgnoreDefenceAmount";
        public static String Crit = "Crit";
        public static String IsAttack = "IsAttack";
        public static String IsMana = "IsMana";
        public static String IsBow = "IsBow";
    }
    public static class Extracts {
        public static String Prefix = "Extracts_";
        public static String Plain = Prefix + "Plain";
        public static String Forest = Prefix + "Forest";
        public static String Lake = Prefix + "Lake";
        public static String Volcano = Prefix + "Volcano";
        public static String Mine = Prefix + "Mine";
        public static String Snow = Prefix + "Snow";
        public static String Sky = Prefix + "Sky";
        public static String Evoker = Prefix + "Evoker";
    }
    public static class LogsType {
        public static String ItemUse = "ItemUse";
        public static String ItemGet = "ItemGet";
        public static String MobKill = "MobKill";
    }
    public static class Entropy {
        public static String Forest = "ForestEntropy";
        public static String Volcano = "VolcanoEntropy";
        public static String ForestEntropyLevel = "ForestEntropyLevel";
        public static String ForestEntropyTime = "ForestEntropyTime";
    }
    public static class ForestBossSwordActive {
        public static String ForestBossActive = "ForestBossActive";
        public static String Pare = ForestBossActive + "Pare";
        public static String PareTime = ForestBossActive + "PareTime";
    }
    public static class KillCount {
        public static String KillCountOf = "KillCountOf";
        public static String KillCount = "KillCount";
        public static String Total = "KillCountTotal" ;
        public static String ForestBoss = KillCountOf + "ForestBoss";
        public static String VolcanoBoss = KillCountOf + "VolcanoBoss";
        public static String SakuraMob = KillCountOf + "SakuraMob";
    }
    public static class Login {
        public static String Status = "status";
        public static String Online = "online";
        public static String Offline = "offline";
        public static String Password = "password";
    }
    public static class VillagerType {
        public static class MainMission {
            public static String Trade1 = "MainMission1";
            public static String Trade2 = "MainMission2";
            public static String Trade3 = "MainMission3";
        }
        public static class Spawn {
            public static String Main1Gems = "Main1Gems";
            public static String EpicRune = "EpicRune";
            public static String T1Equip = "T1Equip";
        }

    }
}