package com.very.wraq.common.util.struct;

import com.very.wraq.common.util.StringUtils;

import java.util.HashMap;

public class MobArmorNum {
    public final double AttackDamage;
    public final double Defence;
    public final double ManaDefence;
    public final double MobLevel;
    public final double DefencePenetration;
    public final double DefencePenetration0;
    public final double CriticalHitRate;
    public final double CritDamage;
    public final double HealSteal;

    public MobArmorNum(double AttackDamage, double Defence, double ManaDefence, float MobLevel, double DefencePenetration,
                       double DefencePenetration0, double CriticalHitRate, float CritDamage, double HealSteal) {
        this.AttackDamage = AttackDamage;
        this.Defence = Defence;
        this.ManaDefence = ManaDefence;
        this.MobLevel = MobLevel;
        this.DefencePenetration = DefencePenetration;
        this.DefencePenetration0 = DefencePenetration0;
        this.CriticalHitRate = CriticalHitRate;
        this.CritDamage = CritDamage;
        this.HealSteal = HealSteal;
    }

    public MobArmorNum(double AttackDamage, double Defence, double ManaDefence, float MobLevel) {
        this.AttackDamage = AttackDamage;
        this.Defence = Defence;
        this.ManaDefence = ManaDefence;
        this.MobLevel = MobLevel;
        this.DefencePenetration = 0;
        this.DefencePenetration0 = 0;
        this.CriticalHitRate = 0;
        this.CritDamage = 0;
        this.HealSteal = 0;
    }

    public static HashMap<String, MobArmorNum> mobArmorNumHashMap = new HashMap<>() {{

        put(StringUtils.MobName.NoAttribute, new MobArmorNum(0, 0, 0, 0));
        put(StringUtils.MobName.PlainZombie, new MobArmorNum(4, 25, 25, 2));
        put(StringUtils.MobName.ForestSkeleton, new MobArmorNum(30, 50, 50, 5));
        put(StringUtils.MobName.ForestZombie, new MobArmorNum(20, 100, 100, 8));
        put(StringUtils.MobName.LakeDrowned, new MobArmorNum(25, 200, 200, 12,
                0.3f, 30, 0.5f, 0.75f, 0.03f));
        put(StringUtils.MobName.VolcanoBlaze, new MobArmorNum(30, 200, 200, 18,
                0.3f, 40, 0.5f, 0.75f, 0.03f));
        put(StringUtils.MobName.MineZombie, new MobArmorNum(25, 300, 300, 25,
                0.2f, 50, 0.8f, 0.75f, 0.03f));
        put(StringUtils.MobName.MineSkeleton, new MobArmorNum(25, 300, 300, 25,
                0.2f, 50, 0.8f, 0.75f, 0.03f));
        put(StringUtils.MobName.FieldZombie, new MobArmorNum(25, 400, 400, 32,
                0.2f, 50, 0.3f, 1f, 0.03f));
        put(StringUtils.MobName.SnowStray, new MobArmorNum(80, 500, 500, 35,
                0.2f, 50, 0.3f, 1f, 0.03f));
        put(StringUtils.MobName.SkyVex, new MobArmorNum(80, 150, 400, 40,
                0.5f, 80, 0.5f, 1f, 1));
        put(StringUtils.MobName.Evoker, new MobArmorNum(250, 500, 50, 45,
                0.3f, 100, 0.5f, 0.75f, 0.03f));
        put(StringUtils.MobName.EvokerMaster, new MobArmorNum(500, 2000, 200, 60,
                0.3f, 120, 0.5f, 0.75f, 0.1f));
        put(StringUtils.MobName.SeaGuardian, new MobArmorNum(60, 500, 500, 48));
        put(StringUtils.MobName.LightingZombie, new MobArmorNum(60, 250, 250, 50,
                0.3f / 4, 100 / 4.0d, 0.3f / 4, 1.5f / 4, 0.03f / 4));
        put(StringUtils.MobName.Husk, new MobArmorNum(60, 400, 400, 55,
                0.3f, 100, 0.5f, 0.75f, 0.03f));
        put(StringUtils.MobName.KazeZombie, new MobArmorNum(100, 200, 200, 70,
                0.3f / 4, 150 / 4f, 0.4f / 4, 1.5f / 4, 0.05f / 4));
        put(StringUtils.MobName.Spider, new MobArmorNum(84, 400, 400, 58,
                0.3f, 100, 0.5f, 0.75f, 0.03f));
        put(StringUtils.MobName.SilverFish, new MobArmorNum(100, 400, 400, 70,
                0.3f, 150, 0.5f, 0.75f, 0.03f));
        put(StringUtils.MobName.WitherSkeleton, new MobArmorNum(90, 900, 900, 50,
                0.3f, 100, 0.4f, 1.2f, 0.1f));
        put(StringUtils.MobName.Piglin, new MobArmorNum(100, 1000, 1000, 55,
                0.3f, 100, 0.4f, 1.2f, 0.1f));
        put(StringUtils.MobName.NetherSkeleton, new MobArmorNum(210, 600, 600, 60,
                0.3f / 4, 50 / 4f, 0.4f / 4, 1.2f / 4, 0.1f / 4));
        put(StringUtils.MobName.NetherMagma, new MobArmorNum(60, 900, 900, 55,
                0.3f, 50, 0.4f, 1.2f, 0.03f));
        put(StringUtils.MobName.ForestBoss, new MobArmorNum(60, 300, 300, 15,
                0.3f, 20, 0.25f, 1.25f, 0.05f));
        put(StringUtils.MobName.VolcanoBoss, new MobArmorNum(80, 500, 500, 30,
                0.4f, 30, 0.3f, 1.25f, 0.08f));
        put(StringUtils.MobName.LakeBoss, new MobArmorNum(100, 400, 400, 23,
                0.3f, 20, 0.25f, 1.25f, 0.05f));
        put(StringUtils.MobName.SkyBoss, new MobArmorNum(120, 600, 600, 48,
                0.3f, 20, 0.25f, 1.25f, 0.05f));
        put(StringUtils.MobName.SnowBoss, new MobArmorNum(100, 600, 600, 40,
                0.3f, 20, 0.25f, 1.25f, 0.05f));
        put(StringUtils.MobName.EnderMan, new MobArmorNum(200, 700, 700, 75,
                0.3f, 150, 0.5f, 0.75f, 0.03f));
        put(StringUtils.MobName.SakuraMob, new MobArmorNum(60, 1800, 1800, 90,
                0.3f, 150, 0.5f, 0.75f, 0.03f));
        put(StringUtils.MobName.Scarecrow, new MobArmorNum(100, 1800, 1800, 90,
                0.3f, 150, 0.5f, 0.75f, 0.03f));
        put(StringUtils.MobName.RandomSlime, new MobArmorNum(0, 100, 100, 0,
                0, 0, 0, 0, 0));
        put(StringUtils.MobName.Boss2, new MobArmorNum(120, 1800, 1800, 100,
                0, 0, 0, 0, 0.3f));
        put(StringUtils.MobName.PlainBoss, new MobArmorNum(50, 50, 50, 25,
                0.1, 10, 0.1, 0.5f, 0.1f));
        put(StringUtils.MobName.Main1Boss, new MobArmorNum(120, 500, 500, 40,
                0.4, 30, 0.3, 1, 0.3f));
        put(StringUtils.MobName.MineWorker, new MobArmorNum(100, 1800, 1800, 90,
                0.3f, 150, 0.5f, 0.75f, 0.03f));
        put(StringUtils.MobName.IceHunter, new MobArmorNum(1000, 1400, 1400, 85,
                0.5f, 100, 0.5f, 0.75f, 0.0f));
        put(StringUtils.MobName.IceKnight, new MobArmorNum(100, 800, 800, 99,
                0.5f, 100, 0.5f, 0.75f, 0.0f));
        put(StringUtils.MobName.ShipWorker, new MobArmorNum(150, 2000, 2000, 100,
                0.5f, 100, 0.5f, 0.75f, 0.0f));
        put(StringUtils.MobName.SpringMob, new MobArmorNum(188, 1800, 1800, 88,
                0.5f, 100, 0.5f, 0.75f, 0.0f));
        put(StringUtils.MobName.Giant, new MobArmorNum(1000, 1000, 1000, 100,
                0.5f, 100, 0.5f, 0.75f, 0.0f));
        put(StringUtils.MobName.EarthMana, new MobArmorNum(1000, 2000, 2000, 110,
                0.5f, 300, 0.5f, 0.75f, 1.0f));
        put(StringUtils.MobName.BloodMana, new MobArmorNum(1000, 2000, 2000, 110,
                0.5f, 300, 0.5f, 0.75f, 1.0f));

        put(StringUtils.MobName.Devil, new MobArmorNum(3000, 2400, 2400, 110,
                0.75f, 500, 0.5f, 0.75f, 100.0f));
        put(StringUtils.MobName.MoonAttack, new MobArmorNum(6000, 2800, 2800, 120,
                0.75f, 500, 0.5f, 0.75f, 100.0f));
        put(StringUtils.MobName.MoonMana, new MobArmorNum(6000, 2800, 2800, 120,
                0.75f, 500, 0.5f, 0.75f, 100.0f));
        put(StringUtils.MobName.Slime, new MobArmorNum(2000, 2200, 2200, 120,
                0.75f, 500, 0.5f, 0.75f, 100.0f));

        put(StringUtils.MobName.TabooDevil, new MobArmorNum(3000, 3200, 3200, 120,
                0.75f, 1000, 0.8f, 1.5f, 400.0f));

        put(StringUtils.MobName.Beacon, new MobArmorNum(3000, 3200, 3200, 130,
                0.75f, 1000, 0.8f, 1.5f, 400.0f));
        put(StringUtils.MobName.Blaze, new MobArmorNum(3000, 3200, 3200, 130,
                0.75f, 1000, 0.8f, 1.5f, 400.0f));
        put(StringUtils.MobName.Tree, new MobArmorNum(3000, 3200, 3200, 130,
                0.75f, 1000, 0.8f, 1.5f, 400.0f));

        put(StringUtils.MobName.BlackCastleOneFloorMana, new MobArmorNum(10000, 7200, 3600, 140,
                0.75f, 800, 0.8f, 1.5f, 1000));
        put(StringUtils.MobName.BlackCastleOneFloorAttack, new MobArmorNum(10000, 3600, 7200, 140,
                0.75f, 800, 0.8f, 1.5f, 1000));

        put(StringUtils.MobName.Shulker, new MobArmorNum(6000, 4500, 4500, 150,
                0.75f, 1500, 0.8f, 1.5f, 1000));

        put(StringUtils.MobName.EnderMite, new MobArmorNum(6000, 4500, 4500, 150,
                0.75f, 1500, 0.8f, 1.5f, 1000));

        put(StringUtils.MobName.EndStray, new MobArmorNum(15000, 5500, 5500, 160,
                0.75f, 800, 0.8f, 1.5f, 1000));

        put(StringUtils.MobName.CastleKnight, new MobArmorNum(15000, 5500, 5500, 160,
                0.75f, 800, 0.8f, 1.5f, 1000));
        put(StringUtils.MobName.PurpleIronKnight, new MobArmorNum(15000, 7500, 7500, 180,
                0.75f, 800, 0.8f, 1.5f, 1000));

        put(StringUtils.MobName.Star, new MobArmorNum(10000, 9000, 9000, 200,
                0.75f, 1000, 0.8f, 1.5f, 400.0f));

        put(StringUtils.MobName.Star1, new MobArmorNum(40000, 15000, 15000, 200,
                0.75f, 4000, 0.8f, 1.5f, 400.0f));

        put(StringUtils.MobName.LifeElement, new MobArmorNum(60000, 14400, 14400, 200,
                0.75f, 4000, 0.8f, 1.5f, 400.0f));

        put(StringUtils.MobName.WaterElement, new MobArmorNum(60000, 14400, 14400, 200,
                0.75f, 4000, 0.8f, 1.5f, 400.0f));

        put(StringUtils.MobName.FireElement, new MobArmorNum(60000, 14400, 14400, 200,
                0.75f, 4000, 0.8f, 1.5f, 400.0f));

        put(StringUtils.MobName.StoneElement, new MobArmorNum(60000, 14400, 14400, 200,
                0.75f, 4000, 0.8f, 1.5f, 400.0f));

        put(StringUtils.MobName.IceElement, new MobArmorNum(60000, 14400, 14400, 200,
                0.75f, 4000, 0.8f, 1.5f, 400.0f));

        put(StringUtils.MobName.LightningElement, new MobArmorNum(60000, 14400, 14400, 200,
                0.75f, 4000, 0.8f, 1.5f, 400.0f));

        put(StringUtils.MobName.WindElement, new MobArmorNum(60000, 14400, 14400, 200,
                0.75f, 4000, 0.8f, 1.5f, 400.0f));

        put(StringUtils.MobName.OriginLifeElement, new MobArmorNum(60000, 19200, 19200, 214,
                0.75f, 4000, 0.8f, 1.5f, 400.0f));

        put(StringUtils.MobName.OriginWaterElement, new MobArmorNum(60000, 19200, 19200, 214,
                0.75f, 4000, 0.8f, 1.5f, 400.0f));

        put(StringUtils.MobName.OriginFireElement, new MobArmorNum(60000, 19200, 19200, 214,
                0.75f, 4000, 0.8f, 1.5f, 400.0f));

        put(StringUtils.MobName.OriginStoneElement, new MobArmorNum(60000, 19200, 19200, 214,
                0.75f, 4000, 0.8f, 1.5f, 400.0f));

        put(StringUtils.MobName.OriginIceElement, new MobArmorNum(60000, 19200, 19200, 214,
                0.75f, 4000, 0.8f, 1.5f, 400.0f));

        put(StringUtils.MobName.OriginLightningElement, new MobArmorNum(60000, 19200, 19200, 214,
                0.75f, 4000, 0.8f, 1.5f, 400.0f));

        put(StringUtils.MobName.OriginWindElement, new MobArmorNum(60000, 19200, 19200, 214,
                0.75f, 4000, 0.8f, 1.5f, 400.0f));

        put(StringUtils.MobName.LabourDay1, new MobArmorNum(510, 510, 510, 51,
                0.75f, 51, 0.8f, 1.5f, 51));

        put(StringUtils.MobName.LabourDay2, new MobArmorNum(5100, 5100, 5100, 151,
                0.75f, 510, 0.8f, 1.5f, 510));


        put(StringUtils.MobName.Tower1Floor, new MobArmorNum(1000, 1400, 1400, 100,
                0.5f, 100, 0.5f, 0.75f, 0.0f));

        put(StringUtils.MobName.Tower2Floor, new MobArmorNum(1500, 2000, 2000, 120,
                0.6, 400, 0.5f, 1, 1));

        put(StringUtils.MobName.Tower3Floor, new MobArmorNum(3500, 3500, 3500, 140,
                0.6, 1000, 0.5f, 1.25f, 10));

        put(StringUtils.MobName.Tower4Floor, new MobArmorNum(5000, 15000, 5000, 160,
                0.6, 2000, 0.5f, 1.5f, 10));

        put(StringUtils.MobName.Tower5Floor, new MobArmorNum(8000, 6500, 6500, 180,
                0.6, 3000, 0.5f, 1.75f, 10));

        put(StringUtils.MobName.Tower6Floor, new MobArmorNum(10000, 8000, 8000, 200,
                0.6, 5000, 0.5f, 2, 10));
    }};
}
