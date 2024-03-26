package com.Very.very.ValueAndTools.registry;

import com.Very.very.Blocks.Blocks.InjectNote;
import com.Very.very.Blocks.Brewing.*;
import com.Very.very.Blocks.Brewing.SolidifiedSouls.*;
import com.Very.very.Customize.CustomizePaper;
import com.Very.very.Customize.Players.Black_Feisa_.BlackFeisaCurios;
import com.Very.very.Customize.Players.Black_Feisa_.BlackFeisaCurios1;
import com.Very.very.Customize.Players.Black_Feisa_.BlackFeisaCurios2;
import com.Very.very.Customize.Players.Black_Feisa_.BlackFeisaSceptre;
import com.Very.very.Customize.Players.Crush.CrushiSword;
import com.Very.very.Customize.Players.Crush.ZeusCurios;
import com.Very.very.Customize.Players.Eliaoi.EliaoiCurios;
import com.Very.very.Customize.Players.Eliaoi.EliaoiCurios1;
import com.Very.very.Customize.Players.Eliaoi.EliaoiSceptre;
import com.Very.very.Customize.Players.FengXiaoju.FengXiaoJuCurios;
import com.Very.very.Customize.Players.FengXiaoju.FengXiaoJuCurios1;
import com.Very.very.Customize.Players.Guang_Yi.GuangYiBow;
import com.Very.very.Customize.Players.LXYZO.LXYZO_Sword;
import com.Very.very.Customize.Players.Lei_yan233.LeiyanBow;
import com.Very.very.Customize.Players.Lei_yan233.LeiyanCurios;
import com.Very.very.Customize.Players.MyMission.MyMissionBow;
import com.Very.very.Customize.Players.MyMission.MyMissionCurios;
import com.Very.very.Customize.Players.MyMission.MyMissionCurios1;
import com.Very.very.Customize.Players.Qi_Fu.QiFuCurios;
import com.Very.very.Customize.Players.Qi_Fu.QiFuBow;
import com.Very.very.Customize.Players.Qi_Fu.QiFuCurios1;
import com.Very.very.Customize.Players.ShowDicker.ShowdickerBow;
import com.Very.very.Customize.Players.Sora_vanilla.SoraVanilla1;
import com.Very.very.Customize.Players.Sora_vanilla.SoraVanilla2;
import com.Very.very.Customize.Players.Wcndymlgb.WcndymlgbBow;
import com.Very.very.Customize.Players.Wcndymlgb.WcndymlgbCurios;
import com.Very.very.Customize.Players.XiangLi.XiangLiCurios;
import com.Very.very.Customize.Players.XiangLi.XiangLiPickaxe;
import com.Very.very.Customize.Players.XiangLi.XiangLiPrefix;
import com.Very.very.Customize.Players.XiangLi.XiangliSmoke;
import com.Very.very.Customize.Players.YuanShiRen.YuanShiRen;
import com.Very.very.Customize.Players.YuanShiRen.YuanShiRenCurios;
import com.Very.very.Customize.Players.Yxwg.YxwgBow;
import com.Very.very.Customize.Players.Yxwg.YxwgCurios;
import com.Very.very.Customize.Players.Yxwg.YxwgCurios1;
import com.Very.very.Customize.Players.Yxwg.YxwgCurios2;
import com.Very.very.Customize.Players.cgswd.CgswdCurios;
import com.Very.very.Customize.Players.cgswd.CgswdSceptre;
import com.Very.very.Customize.Players.liulixian_.*;
import com.Very.very.Customize.Players.shangmengli.*;
import com.Very.very.Customize.Players.very_new.VeryNewCurios;
import com.Very.very.Customize.Uniform.AttackCurios0;
import com.Very.very.Customize.Uniform.BowCurios0;
import com.Very.very.Customize.Uniform.ManaCurios0;
import com.Very.very.Events.SecEvents.SoulBag;
import com.Very.very.Items.Forging.*;
import com.Very.very.Items.KillPaper.KillPaper;
import com.Very.very.Items.LevelReward.VariousBag.LogBag;
import com.Very.very.Items.Lotteries.*;
import com.Very.very.Items.Ps.PsBottle;
import com.Very.very.Process.Lottery.LotteryPrefix;
import com.Very.very.Process.Parkour.KillPaperLoot;
import com.Very.very.Process.Parkour.ParkourGloves;
import com.Very.very.Process.Parkour.ParkourTicket;
import com.Very.very.Series.EndSeries.EndPower;
import com.Very.very.Series.EndSeries.EventControl.BlackForestRecall.BlackForestRecallSoul;
import com.Very.very.Series.EndSeries.EventControl.BlackForestRecall.BlackForestSword4;
import com.Very.very.Series.EndSeries.EventControl.BlackForestRecall.IntensifiedBlackForestSoul;
import com.Very.very.Series.EndSeries.EventControl.ForestRecall.ForestRecallSoul;
import com.Very.very.Series.EndSeries.EventControl.ForestRecall.ForestSword4;
import com.Very.very.Series.EndSeries.EventControl.ForestRecall.IntensifiedForestSoul;
import com.Very.very.Series.EndSeries.EventControl.KazeRecall.IntensifiedKazeSoul;
import com.Very.very.Series.EndSeries.EventControl.KazeRecall.KazeRecallSoul;
import com.Very.very.Series.EndSeries.EventControl.KazeRecall.KazeSword4;
import com.Very.very.Series.EndSeries.EventControl.LightningIslandRecall.*;
import com.Very.very.Series.EndSeries.EventControl.NetherRecall1.IntensifiedRuby;
import com.Very.very.Series.EndSeries.EventControl.NetherRecall1.ManaSword1;
import com.Very.very.Series.EndSeries.EventControl.NetherRecall1.RecallRuby;
import com.Very.very.Series.EndSeries.EventControl.SeaRecall.IntensifiedSeaSoul;
import com.Very.very.Series.EndSeries.EventControl.SeaRecall.SeaRecallSoul;
import com.Very.very.Series.EndSeries.EventControl.SeaRecall.SeaSword4;
import com.Very.very.Series.EndSeries.EventControl.SnowRecall.IntensifiedSnowSoul;
import com.Very.very.Series.EndSeries.EventControl.SnowRecall.SnowRecallSoul;
import com.Very.very.Series.EndSeries.EventControl.SnowRecall.SnowSword4;
import com.Very.very.Series.EndSeries.EventControl.SpiderRecall.*;
import com.Very.very.Series.EndSeries.EventControl.VolcanoRecall.VolcanoRecallSoul;
import com.Very.very.Series.EndSeries.EventControl.VolcanoRecall.VolcanoSword4;
import com.Very.very.Series.EndSeries.RecallBooks.*;
import com.Very.very.Series.EndSeries.RecallPiece;
import com.Very.very.Items.Animation.AnimatedItem;
import com.Very.very.Items.Drugs.drug0;
import com.Very.very.Items.DevelopmentTools.*;
import com.Very.very.Items.Explore.*;
import com.Very.very.Items.LevelReward.LevelReward.*;
import com.Very.very.Items.LevelReward.PotionPackets.*;
import com.Very.very.Items.LevelReward.VariousBag.BackPackTicket;
import com.Very.very.Items.LevelReward.VariousBag.GoldCoinBag;
import com.Very.very.Items.MainStory_1.*;
import com.Very.very.Items.MainStory_1.Mission.*;
import com.Very.very.Items.MobItem.*;
import com.Very.very.Items.Money.*;
import com.Very.very.Items.RandomGems.*;
import com.Very.very.Items.SkillItems.ID_Card;
import com.Very.very.Items.Gems.*;
import com.Very.very.Items.ProfessionAndQuest.Profession_Barker;
import com.Very.very.Items.ProfessionAndQuest.Profession_Bow;
import com.Very.very.Items.ProfessionAndQuest.Profession_Sword;
import com.Very.very.Items.ProfessionAndQuest.Quest;
import com.Very.very.Items.Mission.Daily;
import com.Very.very.Items.Mission.IronLove;
import com.Very.very.Series.EndSeries.Runes.EndRune0;
import com.Very.very.Series.EndSeries.Runes.EndRune1;
import com.Very.very.Series.EndSeries.Runes.EndRune2;
import com.Very.very.Series.EndSeries.Runes.EndRune3;
import com.Very.very.Series.Gems.Castle.CastleArmorGem;
import com.Very.very.Series.Gems.Castle.CastleWeaponGem;
import com.Very.very.Series.Gems.Curios.FancySapphireNecklace;
import com.Very.very.Series.Gems.Curios.RubyNecklace;
import com.Very.very.Series.Gems.Curios.SapphireNecklace;
import com.Very.very.Series.Gems.MainStoryI.*;
import com.Very.very.Series.Gems.MainStoryII.EvokerGem;
import com.Very.very.Series.Gems.MainStoryII.LifeManaGem;
import com.Very.very.Series.Gems.MainStoryII.ObsiManaGem;
import com.Very.very.Series.Gems.MainStoryII.SkyGem;
import com.Very.very.Series.Gems.MainStoryIII.MagmaGem;
import com.Very.very.Series.Gems.MainStoryIII.NetherSkeletonGem;
import com.Very.very.Series.Gems.MainStoryIII.PiglinGem;
import com.Very.very.Series.Gems.MainStoryIII.WitherGem;
import com.Very.very.Series.Gems.MainStoryIII_D.MagmaGemD;
import com.Very.very.Series.Gems.MainStoryIII_D.NetherSkeletonGemD;
import com.Very.very.Series.Gems.MainStoryIII_D.PiglinGemD;
import com.Very.very.Series.Gems.MainStoryIII_D.WitherGemD;
import com.Very.very.Series.Gems.MainStoryII_D.EvokerGemD;
import com.Very.very.Series.Gems.MainStoryII_D.LifeManaGemD;
import com.Very.very.Series.Gems.MainStoryII_D.ObsiManaGemD;
import com.Very.very.Series.Gems.MainStoryII_D.SkyGemD;
import com.Very.very.Series.Gems.MainStoryI_D.*;
import com.Very.very.Series.Gems.MainStoryV.SakuraGem;
import com.Very.very.Series.Gems.MainStoryV.ShipGem;
import com.Very.very.Series.Gems.MainStoryV_D.SakuraGemD;
import com.Very.very.Series.Gems.MainStoryV_D.ShipGemD;
import com.Very.very.Series.InstanceSeries.PurpleIronKnight.PurpleIronBow;
import com.Very.very.Series.InstanceSeries.PurpleIronKnight.PurpleIronSceptre;
import com.Very.very.Series.InstanceSeries.PurpleIronKnight.PurpleIronSword;
import com.Very.very.Series.OverWorldSeries.Castle.*;
import com.Very.very.Series.OverWorldSeries.IceSeries.LeatherArmor;
import com.Very.very.Series.InstanceSeries.Castle.*;
import com.Very.very.Series.InstanceSeries.Devil.*;
import com.Very.very.Series.InstanceSeries.Ice.*;
import com.Very.very.Series.InstanceSeries.Moon.*;
import com.Very.very.Series.InstanceSeries.Moon.Equip.*;
import com.Very.very.Series.InstanceSeries.Plain.PlainAttackRing;
import com.Very.very.Series.InstanceSeries.Plain.PlainDefenceRing;
import com.Very.very.Series.InstanceSeries.Plain.PlainHealthRing;
import com.Very.very.Series.InstanceSeries.Plain.PlainManaAttackRing;
import com.Very.very.Series.InstanceSeries.SakuraBoss2.Boss2AttackRing;
import com.Very.very.Series.InstanceSeries.SakuraBoss2.Boss2DefenceRing;
import com.Very.very.Series.InstanceSeries.SakuraBoss2.Boss2HealthRing;
import com.Very.very.Series.InstanceSeries.SakuraBoss2.Boss2ManaAttackRing;
import com.Very.very.Series.InstanceSeries.Taboo.TabooAttackArmor;
import com.Very.very.Series.InstanceSeries.Taboo.TabooManaArmor;
import com.Very.very.Series.InstanceSeries.Taboo.TabooSwiftArmor;
import com.Very.very.Series.NetherSeries.Equip.Armor.*;
import com.Very.very.Series.NetherSeries.Equip.MagmaSceptre.MagmaSceptre;
import com.Very.very.Series.NetherSeries.Equip.MagmaSceptre.NetherMagmaRune;
import com.Very.very.Series.NetherSeries.Equip.PiglinHelmet.PiglinHelmet;
import com.Very.very.Series.NetherSeries.Equip.PiglinHelmet.PiglinRune;
import com.Very.very.Series.NetherSeries.Equip.WitherBow.NetherBoneRune;
import com.Very.very.Series.NetherSeries.Equip.WitherBow.WitherBow;
import com.Very.very.Series.NetherSeries.Equip.WitherSword.WitherBoneRune;
import com.Very.very.Series.NetherSeries.Equip.WitherSword.WitherSword;
import com.Very.very.Series.NetherSeries.Material.*;
import com.Very.very.Series.NetherSeries.Runes.NetherRune0;
import com.Very.very.Series.NetherSeries.Runes.NetherRune1;
import com.Very.very.Series.NetherSeries.Runes.NetherRune2;
import com.Very.very.Series.NetherSeries.Runes.NetherRune3;
import com.Very.very.Series.NetherSeries.Equip.*;
import com.Very.very.Series.NetherSeries.Power.MagmaPower;
import com.Very.very.Series.NetherSeries.Power.PiglinPower;
import com.Very.very.Series.NetherSeries.Power.WitherBoneMealPower;
import com.Very.very.Series.NetherSeries.Power.WitherBonePower;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Forest.Bow.ForestBow;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Forest.Crest.ForestCrest;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Main1Boss.FInalCord;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Main1Boss.LVCord;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Main1Boss.PFCord;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Main1Boss.main1crystal;
import com.Very.very.Series.OverWorldSeries.MainStory_I.ManaBook.ManaNote;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Mine.Armor.MineArmorBoots;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Mine.Armor.MineArmorChest;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Mine.Armor.MineArmorHelmet;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Mine.Armor.MineArmorLeggings;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Mine.Bow.MineBow;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Mine.Crest.MineCrest;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Mine.Sword.MineSword0;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Mine.Sword.MineSword1;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Mine.Sword.MineSword2;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Mine.Sword.MineSword3;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Plain.Bow.PlainBow;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Plain.Crest.PlainCrest;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Snow.Armor.SnowArmorBoots;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Snow.Armor.SnowArmorChest;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Snow.Armor.SnowArmorHelmet;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Snow.Armor.SnowArmorLeggings;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Snow.Crest.SnowCrest;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Snow.Runes.SnowRune0;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Snow.Runes.SnowRune1;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Snow.Runes.SnowRune2;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Snow.Runes.SnowRune3;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Snow.Sword.SnowSword0;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Snow.Sword.SnowSword1;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Snow.Sword.SnowSword2;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Snow.Sword.SnowSword3;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Volcano.Bow.VolcanoBow;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Volcano.Crest.VolcanoCrest;
import com.Very.very.Series.OverWorldSeries.MainStory_I.WaterSystem.*;
import com.Very.very.Series.OverWorldSeries.MainStory_I.WaterSystem.Armor.LakeArmorBoots;
import com.Very.very.Series.OverWorldSeries.MainStory_I.WaterSystem.Armor.LakeArmorChest;
import com.Very.very.Series.OverWorldSeries.MainStory_I.WaterSystem.Armor.LakeArmorHelmet;
import com.Very.very.Series.OverWorldSeries.MainStory_I.WaterSystem.Armor.LakeArmorLeggings;
import com.Very.very.Series.OverWorldSeries.MainStory_I.WaterSystem.BossItems.LakeBoss;
import com.Very.very.Series.OverWorldSeries.MainStory_I.WaterSystem.Crest.LakeCrest;
import com.Very.very.Series.OverWorldSeries.MainStory_I.WaterSystem.Runes.LakeRune0;
import com.Very.very.Series.OverWorldSeries.MainStory_I.WaterSystem.Runes.LakeRune1;
import com.Very.very.Series.OverWorldSeries.MainStory_I.WaterSystem.Runes.LakeRune2;
import com.Very.very.Series.OverWorldSeries.MainStory_I.WaterSystem.Runes.LakeRune3;
import com.Very.very.Series.OverWorldSeries.MainStory_I.WaterSystem.Sword.LakeSword0;
import com.Very.very.Series.OverWorldSeries.MainStory_I.WaterSystem.Sword.LakeSword1;
import com.Very.very.Series.OverWorldSeries.MainStory_I.WaterSystem.Sword.LakeSword2;
import com.Very.very.Series.OverWorldSeries.MainStory_I.WaterSystem.Sword.LakeSword3;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Field.*;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Forest.*;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Forest.Armor.ForestArmorBoots;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Forest.Armor.ForestArmorChest;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Forest.Armor.ForestArmorHelmet;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Forest.Armor.ForestArmorLeggings;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Forest.BossItems.*;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Forest.Rune.ForestRune0;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Forest.Rune.ForestRune1;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Forest.Rune.ForestRune2;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Forest.Rune.ForestRune3;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Forest.Sword.ForestSword;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Mine.*;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Plain.*;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Plain.Armor.PlainArmorBoots;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Plain.Armor.PlainArmorChest;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Plain.Armor.PlainArmorHelmet;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Plain.Armor.PlainArmorLeggings;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Plain.Runes_Plain.*;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Plain.Sceptre.*;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Plain.Sword.PlainSword;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Snow.*;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Volcano.*;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Volcano.Armor.VolcanoArmorBoots;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Volcano.Armor.VolcanoArmorChest;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Volcano.Armor.VolcanoArmorHelmet;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Volcano.Armor.VolcanoArmorLeggings;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Volcano.BossItems.*;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Volcano.Rune.VolcanoRune0;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Volcano.Rune.VolcanoRune1;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Volcano.Rune.VolcanoRune2;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Volcano.Rune.VolcanoRune3;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Volcano.Sword.VolcanoSword;
import com.Very.very.Series.OverWorldSeries.MainStory_II.BlackForest.*;
import com.Very.very.Series.OverWorldSeries.MainStory_II.CodeMana.*;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Evoker.Crest.ManaCrest;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Dimension.SilverFishSoul;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Evoker.*;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Evoker.Runes.ManaRune0;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Evoker.Runes.ManaRune1;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Evoker.Runes.ManaRune2;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Evoker.Runes.ManaRune3;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Kaze.*;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Kaze.Sword.KazeSword0;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Kaze.Sword.KazeSword1;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Kaze.Sword.KazeSword2;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Kaze.Sword.KazeSword3;
import com.Very.very.Series.OverWorldSeries.MainStory_II.LightningIsland.*;
import com.Very.very.Series.OverWorldSeries.MainStory_II.LightningIsland.Armor.LightningArmorBoots;
import com.Very.very.Series.OverWorldSeries.MainStory_II.LightningIsland.Armor.LightningArmorChest;
import com.Very.very.Series.OverWorldSeries.MainStory_II.LightningIsland.Armor.LightningArmorHelmet;
import com.Very.very.Series.OverWorldSeries.MainStory_II.LightningIsland.Armor.LightningArmorLeggings;
import com.Very.very.Series.OverWorldSeries.MainStory_II.ManaArmor.*;
import com.Very.very.Series.OverWorldSeries.MainStory_II.ManaArmor.LifeMana.LifeManaArmorBoots;
import com.Very.very.Series.OverWorldSeries.MainStory_II.ManaArmor.LifeMana.LifeManaArmorChest;
import com.Very.very.Series.OverWorldSeries.MainStory_II.ManaArmor.LifeMana.LifeManaArmorHelmet;
import com.Very.very.Series.OverWorldSeries.MainStory_II.ManaArmor.LifeMana.LifeManaArmorLeggings;
import com.Very.very.Series.OverWorldSeries.MainStory_II.ManaArmor.ObsiMana.ObsiManaArmorBoots;
import com.Very.very.Series.OverWorldSeries.MainStory_II.ManaArmor.ObsiMana.ObsiManaArmorChest;
import com.Very.very.Series.OverWorldSeries.MainStory_II.ManaArmor.ObsiMana.ObsiManaArmorHelmet;
import com.Very.very.Series.OverWorldSeries.MainStory_II.ManaArmor.ObsiMana.ObsiManaArmorLeggings;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Sea.*;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Sea.Sword.SeaSword0;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Sea.Sword.SeaSword1;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Sea.Sword.SeaSword2;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Sea.Sword.SeaSword3;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Sky.*;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Sky.Armor.SkyArmorBoots;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Sky.Armor.SkyArmorChest;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Sky.Armor.SkyArmorHelmet;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Sky.Armor.SkyArmorLeggings;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Sky.BossItems.SkyBoss;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Sky.Crest.SkyCrest;
import com.Very.very.Projectile.Mana.Shoot;
import com.Very.very.Items.RewardPack.Boss1;
import com.Very.very.Items.RewardPack.Boss2;
import com.Very.very.Items.RewardPack.Main1Reward;
import com.Very.very.Items.RewardPack.SignInReward;
import com.Very.very.Series.OverWorldSeries.Forging.forgingstone0;
import com.Very.very.Series.OverWorldSeries.Forging.forgingstone1;
import com.Very.very.Series.OverWorldSeries.Forging.forgingstone2;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Spider.*;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Spider.Ointment.*;
import com.Very.very.Series.OverWorldSeries.SakuraSeries.SakuraMob.*;
import com.Very.very.Series.OverWorldSeries.SakuraSeries.Scarecrow.Wheat;
import com.Very.very.Series.OverWorldSeries.SakuraSeries.Scarecrow.WheatArmorChest;
import com.Very.very.Series.OverWorldSeries.SakuraSeries.Scarecrow.WheatPocket;
import com.Very.very.Series.OverWorldSeries.SakuraSeries.Scarecrow.WheatReForge;
import com.Very.very.Series.OverWorldSeries.VariousItem.FeiLeiShen;
import com.Very.very.Items.SkyCity.TicketToSkyCity;
import com.Very.very.Series.OverWorldSeries.SakuraSeries.BloodMana.BloodManaArmor;
import com.Very.very.Series.OverWorldSeries.SakuraSeries.BloodMana.BloodManaCurios;
import com.Very.very.Series.OverWorldSeries.SakuraSeries.BloodMana.ManaKnife;
import com.Very.very.Series.OverWorldSeries.SakuraSeries.BloodMana.ManaShield;
import com.Very.very.Series.OverWorldSeries.SakuraSeries.Boss2.Boss2Piece;
import com.Very.very.Series.OverWorldSeries.SakuraSeries.Boss2.GoldShield;
import com.Very.very.Series.OverWorldSeries.SakuraSeries.Boss2.GoldenBook;
import com.Very.very.Series.OverWorldSeries.SakuraSeries.EarthMana.EarthBook;
import com.Very.very.Series.OverWorldSeries.SakuraSeries.EarthMana.EarthManaArmor;
import com.Very.very.Series.OverWorldSeries.SakuraSeries.EarthMana.EarthManaCurios;
import com.Very.very.Series.OverWorldSeries.SakuraSeries.EarthMana.EarthPower;
import com.Very.very.Series.OverWorldSeries.SakuraSeries.MineWorker.MinePants;
import com.Very.very.Series.OverWorldSeries.SakuraSeries.MineWorker.PurpleArmor;
import com.Very.very.Series.OverWorldSeries.SakuraSeries.MineWorker.PurplePickaxe;
import com.Very.very.Series.OverWorldSeries.SakuraSeries.Ship.ShipBow;
import com.Very.very.Series.OverWorldSeries.SakuraSeries.Ship.ShipSceptre;
import com.Very.very.Series.OverWorldSeries.SakuraSeries.Ship.ShipSword;
import com.Very.very.Series.OverWorldSeries.SakuraSeries.Slime.SlimeBoots;
import com.Very.very.Series.QingMing.QingTuan;
import com.Very.very.Series.SpringEvent.*;
import com.Very.very.Series.OverWorldSeries.WorldBoss.CropPackets;
import com.Very.very.Series.OverWorldSeries.WorldBoss.GiantTicket;
import com.Very.very.Series.WorldSoulSeries.*;
import com.Very.very.ValueAndTools.*;
import com.Very.very.ValueAndTools.Utils.StringUtils;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.Render.Gui.TestAndHelper.SmartPhoneOpen;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    public static final RegistryObject<Item> Obsidian_INGOT = ITEMS.register("obsidian_ingot",
            ()->new Item(new Item.Properties()));
    public static final RegistryObject<Item> PP = ITEMS.register("pp",
            ()->new Item(new Item.Properties()));
    public static final RegistryObject<Item> Sliver_BLOCK = ITEMS.register("sliver_block",
            ()->new BlockItem(ModBlocks.Sliver_Block.get(),new Item.Properties()));
    public static final RegistryObject<Item> HBOSSITEM = ITEMS.register("hboss",
            ()->new ForgeSpawnEggItem(ModEntityType.HETITY,9577503,13423070,new Item.Properties()));
    public static final RegistryObject<Item> Sword1 = ITEMS.register("sword1",
            ()->new Sword1(ItemTier.VMaterial,2,0));
    public static final RegistryObject<Item> Sword2 = ITEMS.register("sword2",
            ()->new Sword2(ItemTier.VMaterial,2,0));
    public static final RegistryObject<Item> Main0 = ITEMS.register("main0",
            ()->new Main0(new Item.Properties()));
    public static final RegistryObject<Item> Main0_1 = ITEMS.register("main0_1",
            ()->new Main0_1(new Item.Properties()));
    public static final RegistryObject<Item> Main0_2 = ITEMS.register("main0_2",
            ()->new Main0_2(new Item.Properties()));
    public static final RegistryObject<Item> Main0_3 = ITEMS.register("main0_3",
            ()->new Main0_3(new Item.Properties()));
    public static final RegistryObject<Item> Main1_1 = ITEMS.register("main1_1",
            ()->new Main1_1(new Item.Properties()));
    public static final RegistryObject<Item> Main1_2 = ITEMS.register("main1_2",
            ()->new Main1_2(new Item.Properties()));
    public static final RegistryObject<Item> Main1_0 = ITEMS.register("main1_0",
            ()->new Main1_0(new Item.Properties()));
    public static final RegistryObject<Item> Main1_3 = ITEMS.register("main1_3",
            ()->new Main1_3(new Item.Properties()));
    public static final RegistryObject<Item> Main1_4 = ITEMS.register("main1_4",
            ()->new Main1_4(new Item.Properties()));
    public static final RegistryObject<Item> GoldCoin = ITEMS.register("gold_coin",
            ()->new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> SilverCoin = ITEMS.register("silver_coin",
            ()->new Item(new Item.Properties()));
    public static final RegistryObject<Item> SignInReset = ITEMS.register("signinreset",
            ()->new SignInReset(new Item.Properties()));
    public static final RegistryObject<Item> SignInGet = ITEMS.register("signinget",
            ()->new SignInGet(new Item.Properties()));
    public static final RegistryObject<Item> GetTime = ITEMS.register("gettime",
            ()->new GetTime(new Item.Properties()));
    public static final RegistryObject<Item> ItemIDCheck = ITEMS.register("idcheck",
            ()->new ItemIDCheck(new Item.Properties()));
    public static final RegistryObject<Item> SignInReward = ITEMS.register("signinreward",
            ()->new SignInReward(new Item.Properties()));
    public static final RegistryObject<Item> Note_0 = ITEMS.register("note_0",
            ()->new Note_0(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> ExploreNote = ITEMS.register("explorenote",
            ()->new ExploreNote(new Item.Properties()));
    public static final RegistryObject<Item> Knife = ITEMS.register("knife",
            ()->new Knife(ItemTier.VMaterial,2,0));
    public static final RegistryObject<Item> ForNew = ITEMS.register("fornew",
            ()->new ForNew(new Item.Properties()));
    public static final RegistryObject<Item> RunePiece = ITEMS.register("rune_piece",
            ()->new Piece(new Item.Properties()));
    public static final RegistryObject<Item> PlainRune0 = ITEMS.register("green_runes_0",
            ()->new GreenRunes_0(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> PlainRune1 = ITEMS.register("green_runes_1",
            ()->new GreenRunes_1(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> ArmorPlain = ITEMS.register("armor1",
            ()->new MobArmor(StringUtils.MobName.PlainZombie));
    public static final RegistryObject<Item> PlainRune2 = ITEMS.register("green_runes_2",
            ()->new GreenRunes_2(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> PlainSoul = ITEMS.register("plain_souls",
            ()->new PlainSoul(new Item.Properties().rarity(Utils.Plain)));
    public static final RegistryObject<Item> PlainRune = ITEMS.register("plain_runes",
            ()->new PlainRune(new Item.Properties().rarity(Utils.PlainBold)));
    public static final RegistryObject<Item> runes = ITEMS.register("runes",
            ()->new Profile(new Item.Properties()));
    public static final RegistryObject<Item> PlainRune3 = ITEMS.register("green_runes_3",
            ()->new GreenRunes_3(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> ArmorForestSkeleton = ITEMS.register("armor2",
            ()->new MobArmor(StringUtils.MobName.ForestSkeleton));
    public static final RegistryObject<Item> PlainArmorBoots = ITEMS.register("plainarmorboots",
            ()->new PlainArmorBoots(ItemMaterial.PlainMaterialBoots, ArmorItem.Type.BOOTS));
    public static final RegistryObject<Item> PlainArmorLeggings = ITEMS.register("plainarmorleggings",
            ()->new PlainArmorLeggings(ItemMaterial.PlainMaterialLeggings, ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<Item> PlainArmorChest = ITEMS.register("plainarmorchest",
            ()->new PlainArmorChest(ItemMaterial.PlainMaterialChest, ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> PlainArmorHelmet = ITEMS.register("plainarmorhelmet",
            ()->new PlainArmorHelmet(ItemMaterial.PlainMaterialHelmet, ArmorItem.Type.HELMET));
    public static final RegistryObject<Item> PlainSword0 = ITEMS.register("plainsword0",
            ()->new PlainSword(ItemTier.VMaterial,2,0,0));
    public static final RegistryObject<Item> PlainSword1 = ITEMS.register("plainsword1",
            ()->new PlainSword(ItemTier.VMaterial,2,0,1));
    public static final RegistryObject<Item> PlainSword2 = ITEMS.register("plainsword2",
            ()->new PlainSword(ItemTier.VMaterial,2,0,2));
    public static final RegistryObject<Item> PlainSword3 = ITEMS.register("plainsword3",
            ()->new PlainSword(ItemTier.VMaterial,2,0,3));
    public static final RegistryObject<Item> ArmorForestZombie = ITEMS.register("armorforest2",
            ()->new MobArmor(StringUtils.MobName.ForestZombie));
    public static final RegistryObject<Item> ArmorBlaze = ITEMS.register("armorblaze",
            ()->new MobArmor(StringUtils.MobName.VolcanoBlaze));
    public static final RegistryObject<Item> ArmorDrown = ITEMS.register("armordrown",
            ()->new MobArmor(StringUtils.MobName.LakeDrowned));
    public static final RegistryObject<Item> ForestSword0 = ITEMS.register("forestsword0",
            ()->new ForestSword(ItemTier.VMaterial,2,0,new Item.Properties().rarity(Utils.ForestItalic),0));
    public static final RegistryObject<Item> ForestSword1 = ITEMS.register("forestsword1",
            ()->new ForestSword(ItemTier.VMaterial,2,0,new Item.Properties().rarity(Utils.ForestItalic),1));
    public static final RegistryObject<Item> ForestSword2 = ITEMS.register("forestsword2",
            ()->new ForestSword(ItemTier.VMaterial,2,0,new Item.Properties().rarity(Utils.ForestItalic),2));
    public static final RegistryObject<Item> ForestSword3 = ITEMS.register("forestsword3",
            ()->new ForestSword(ItemTier.VMaterial,2,0,new Item.Properties().rarity(Utils.ForestItalic),3));
    public static final RegistryObject<Item> ForestSoul = ITEMS.register("forestsoul",
            ()->new ForestSoul(new Item.Properties().rarity(Utils.Forest)));
    public static final RegistryObject<Item> ForestRune = ITEMS.register("forestrune",
            ()->new ForestRune(new Item.Properties().rarity(Utils.ForestBold)));
    public static final RegistryObject<Item> ForestArmorBoots = ITEMS.register("forestarmorboots",
            ()->new ForestArmorBoots(ItemMaterial.ForestMaterialBoots, ArmorItem.Type.BOOTS));
    public static final RegistryObject<Item> ForestArmorLeggings = ITEMS.register("forestarmorleggings",
            ()->new ForestArmorLeggings(ItemMaterial.ForestMaterialLeggings, ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<Item> ForestArmorChest = ITEMS.register("forestarmorchest",
            ()->new ForestArmorChest(ItemMaterial.ForestMaterialChest, ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> ForestArmorHelmet = ITEMS.register("forestarmorhelmet",
            ()->new ForestArmorHelmet(ItemMaterial.ForestMaterialHelmet, ArmorItem.Type.HELMET));
    public static final RegistryObject<Item> GemPiece = ITEMS.register("gemspiece",
            ()->new gemspiece(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> bossaward1 = ITEMS.register("bossaward1",
            ()->new Boss1(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> bossaward2 = ITEMS.register("bossaward2",
            ()->new Boss2(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> LakeSoul = ITEMS.register("watersoul",
            ()->new LakeSoul(new Item.Properties().rarity(Utils.Lake)));
    public static final RegistryObject<Item> LakeRune = ITEMS.register("waterrune",
            ()->new LakeRune(new Item.Properties().rarity(Utils.LakeBold)));
    public static final RegistryObject<Item> VolcanoSoul = ITEMS.register("volcanosoul",
            ()->new VolcanoSoul(new Item.Properties().rarity(Utils.Volcano)));
    public static final RegistryObject<Item> VolcanoRune = ITEMS.register("volcanorune",
            ()->new VolcanoRune(new Item.Properties().rarity(Utils.VolcanoBold)));
    public static final RegistryObject<Item> Main0_4 = ITEMS.register("main0_4",
            ()->new Main0_4(new Item.Properties()));
    public static final RegistryObject<Item> Main0_5 = ITEMS.register("main0_5",
            ()->new Main0_5(new Item.Properties()));
    public static final RegistryObject<Item> Main1_5 = ITEMS.register("main1_5",
            ()->new Main1_5(new Item.Properties()));
    public static final RegistryObject<Item> BackSpawn = ITEMS.register("backspawn",
            ()->new BackSpawn(new Item.Properties().rarity(Rarity.create("souvenirs",ChatFormatting.BLUE))));
    public static final RegistryObject<Item> main1reward = ITEMS.register("main1reward",
            ()->new Main1Reward(new Item.Properties()));
    public static final RegistryObject<Item> ForestRune0 = ITEMS.register("forestrune0",
            ()->new ForestRune0(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> ForestRune1 = ITEMS.register("forestrune1",
            ()->new ForestRune1(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> ForestRune2 = ITEMS.register("forestrune2",
            ()->new ForestRune2(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> ForestRune3 = ITEMS.register("forestrune3",
            ()->new ForestRune3(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> LakeArmorBoots = ITEMS.register("lakearmorboots",
            ()->new LakeArmorBoots(ItemMaterial.LakeMaterialBoots, ArmorItem.Type.BOOTS));
    public static final RegistryObject<Item> LakeArmorLeggings = ITEMS.register("lakearmorleggings",
            ()->new LakeArmorLeggings(ItemMaterial.LakeMaterialLeggings, ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<Item> LakeArmorChest = ITEMS.register("lakearmorchest",
            ()->new LakeArmorChest(ItemMaterial.LakeMaterialChest, ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> LakeArmorHelmet = ITEMS.register("lakearmorhelmet",
            ()->new LakeArmorHelmet(ItemMaterial.LakeMaterialHelmet, ArmorItem.Type.HELMET));
    public static final RegistryObject<Item> VolcanoArmorBoots = ITEMS.register("volcanoarmorboots",
            ()->new VolcanoArmorBoots(ItemMaterial.VolcanoMaterialBoots, ArmorItem.Type.BOOTS));
    public static final RegistryObject<Item> VolcanoArmorLeggings = ITEMS.register("volcanoarmorleggings",
            ()->new VolcanoArmorLeggings(ItemMaterial.VolcanoMaterialLeggings, ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<Item> VolcanoArmorChest = ITEMS.register("volcanoarmorchest",
            ()->new VolcanoArmorChest(ItemMaterial.VolcanoMaterialChest, ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> VolcanoArmorHelmet = ITEMS.register("volcanoarmorhelmet",
            ()->new VolcanoArmorHelmet(ItemMaterial.VolcanoMaterialHelmet, ArmorItem.Type.HELMET));
    public static final RegistryObject<Item> LakeSword0 = ITEMS.register("lakesword0",
            ()->new LakeSword0(ItemTier.VMaterial,2,0));
    public static final RegistryObject<Item> LakeSword1 = ITEMS.register("lakesword1",
            ()->new LakeSword1(ItemTier.VMaterial,2,0));
    public static final RegistryObject<Item> LakeSword2 = ITEMS.register("lakesword2",
            ()->new LakeSword2(ItemTier.VMaterial,2,0));
    public static final RegistryObject<Item> LakeSword3 = ITEMS.register("lakesword3",
            ()->new LakeSword3(ItemTier.VMaterial,2,0));
    public static final RegistryObject<Item> attributecheck = ITEMS.register("attributecheck",
            ()->new AttributeCheck(new Item.Properties()));
    public static final RegistryObject<Item> VolcanoSword0 = ITEMS.register("volcanosword0",
            ()->new VolcanoSword(ItemTier.VMaterial,2,0,0));
    public static final RegistryObject<Item> VolcanoSword1 = ITEMS.register("volcanosword1",
            ()->new VolcanoSword(ItemTier.VMaterial,2,0,1));
    public static final RegistryObject<Item> VolcanoSword2 = ITEMS.register("volcanosword2",
            ()->new VolcanoSword(ItemTier.VMaterial,2,0,2));
    public static final RegistryObject<Item> VolcanoSword3 = ITEMS.register("volcanosword3",
            ()->new VolcanoSword(ItemTier.VMaterial,2,0,3));
    public static final RegistryObject<Item> plaingems = ITEMS.register("plaingems",
            ()->new PlainRing(new Item.Properties().stacksTo(1).rarity(Utils.PlainItalic)));
    public static final RegistryObject<Item> forestgems = ITEMS.register("forestgems",
            ()->new ForestRing(new Item.Properties().stacksTo(1).rarity(Utils.ForestItalic)));
    public static final RegistryObject<Item> lakegems = ITEMS.register("lakegems",
            ()->new LakeRing(new Item.Properties().stacksTo(1).rarity(Utils.LakeItalic)));
    public static final RegistryObject<Item> volcanogems = ITEMS.register("volcanogems",
            ()->new VolcanoRing(new Item.Properties().stacksTo(1).rarity(Utils.VolcanoItalic)));
    public static final RegistryObject<Item> randomsword = ITEMS.register("randomsword",
            ()->new randomsword(ItemTier.VMaterial,2,1.0f));
    public static final RegistryObject<Item> tickettosky = ITEMS.register("tickettosky",
            ()->new TicketToSkyCity(new Item.Properties().rarity(Rarity.create("souvenirs",ChatFormatting.BLUE))));
    public static final RegistryObject<Item> quest = ITEMS.register("quest",
            ()->new Quest(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> hovertest = ITEMS.register("hovertest",
            ()->new HoverTest(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> ArrowItem = ITEMS.register("arrowitem",
            ()->new ArrowItem(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> ForestBow0 = ITEMS.register("forestbow0",
            ()->new ForestBow(new Item.Properties().stacksTo(1).rarity(Utils.ForestItalic),0));
    public static final RegistryObject<Item> ForestBow1 = ITEMS.register("forestbow1",
            ()->new ForestBow(new Item.Properties().stacksTo(1).rarity(Utils.ForestItalic),1));
    public static final RegistryObject<Item> ForestBow2 = ITEMS.register("forestbow2",
            ()->new ForestBow(new Item.Properties().stacksTo(1).rarity(Utils.ForestItalic),2));
    public static final RegistryObject<Item> ForestBow3 = ITEMS.register("forestbow3",
            ()->new ForestBow(new Item.Properties().stacksTo(1).rarity(Utils.ForestItalic),3));
    public static final RegistryObject<Item> MineSword0 = ITEMS.register("minesword0",
            ()->new MineSword0(ItemTier.MaterialForPickaxe0,2,0,new Item.Properties().rarity(Utils.MineItalic)));
    public static final RegistryObject<Item> MineSword1 = ITEMS.register("minesword1",
            ()->new MineSword1(ItemTier.MaterialForPickaxe1,2,0,new Item.Properties().rarity(Utils.MineItalic)));
    public static final RegistryObject<Item> MineSword2 = ITEMS.register("minesword2",
            ()->new MineSword2(ItemTier.MaterialForPickaxe2,2,0,new Item.Properties().rarity(Utils.MineItalic)));
    public static final RegistryObject<Item> MineSword3 = ITEMS.register("minesword3",
            ()->new MineSword3(ItemTier.MaterialForPickaxe3,2,0,new Item.Properties().rarity(Utils.MineItalic)));
    public static final RegistryObject<Item> ArmorMine = ITEMS.register("armormine",
            ()->new MobArmor(StringUtils.MobName.MineZombie));
    public static final RegistryObject<Item> spawn1 = ITEMS.register("spawn1",
            ()->new Spawn1(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> ArmorSnow = ITEMS.register("armorsnow",
            ()->new MobArmor(StringUtils.MobName.SnowStray));
    public static final RegistryObject<Item> ArmorField = ITEMS.register("armorfield",
            ()->new MobArmor(StringUtils.MobName.FieldZombie));
    public static final RegistryObject<Item> profession_bow = ITEMS.register("profession_bow",
            ()->new Profession_Bow(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> profession_sword = ITEMS.register("profession_sword",
            ()->new Profession_Sword(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> profession_barker = ITEMS.register("profession_barker",
            ()->new Profession_Barker(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> MineSoul = ITEMS.register("minesoul",
            ()->new MineSoul(new Item.Properties().rarity(Utils.Mine)));
    public static final RegistryObject<Item> MineSoul1 = ITEMS.register("minesoul1",
            ()->new MineSoul1(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> MineRune = ITEMS.register("minerune",
            ()->new MineRune(new Item.Properties().rarity(Utils.MineBold)));
    public static final RegistryObject<Item> FieldSoul = ITEMS.register("fieldsoul",
            ()->new FieldSoul(new Item.Properties().rarity(Utils.Field)));
    public static final RegistryObject<Item> FieldRune = ITEMS.register("fieldrune",
            ()->new FieldRune(new Item.Properties().rarity(Utils.FieldBold)));
    public static final RegistryObject<Item> FieldSword0 = ITEMS.register("fieldsword0",
            ()->new FieldSword0(ItemTier.VMaterial,2,0));
    public static final RegistryObject<Item> FieldSword1 = ITEMS.register("fieldsword1",
            ()->new FieldSword1(ItemTier.VMaterial,2,0));
    public static final RegistryObject<Item> FieldSword2 = ITEMS.register("fieldsword2",
            ()->new FieldSword2(ItemTier.VMaterial,2,0));
    public static final RegistryObject<Item> FieldSword3 = ITEMS.register("fieldsword3",
            ()->new FieldSword3(ItemTier.VMaterial,2,0));
    public static final RegistryObject<Item> SnowSoul = ITEMS.register("snowsoul",
            ()->new SnowSoul(new Item.Properties().rarity(Utils.Snow)));
    public static final RegistryObject<Item> SnowRune = ITEMS.register("snowrune",
            ()->new SnowRune(new Item.Properties().rarity(Utils.SnowBold)));
    public static final RegistryObject<Item> SnowSword0 = ITEMS.register("snowsword0",
            ()->new SnowSword0(ItemTier.MaterialForPickaxe3,2,0,new Item.Properties().rarity(Utils.SnowItalic)));
    public static final RegistryObject<Item> SnowSword1 = ITEMS.register("snowsword1",
            ()->new SnowSword1(ItemTier.MaterialForPickaxe3,2,0,new Item.Properties().rarity(Utils.SnowItalic)));
    public static final RegistryObject<Item> SnowSword2 = ITEMS.register("snowsword2",
            ()->new SnowSword2(ItemTier.MaterialForPickaxe3,2,0,new Item.Properties().rarity(Utils.SnowItalic)));
    public static final RegistryObject<Item> SnowSword3 = ITEMS.register("snowsword3",
            ()->new SnowSword3(ItemTier.MaterialForPickaxe3,2,0,new Item.Properties().rarity(Utils.SnowItalic)));
    public static final RegistryObject<Item> Note_1 = ITEMS.register("note_1",
            ()->new Note_1(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> Note_2 = ITEMS.register("note_2",
            ()->new Note_2(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> Note_3 = ITEMS.register("note_3",
            ()->new Note_3(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> Breathe = ITEMS.register("breathe",
            ()->new Breath(new Item.Properties().rarity(Utils.Sea)));
    public static final RegistryObject<Item> FireResistent = ITEMS.register("fireresistent",
            ()->new FireResistent(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> Climb = ITEMS.register("climb",
            ()->new Climb(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> Extraction = ITEMS.register("extraction",
            ()->new Extraction(ItemTier.Extraction,2,0));
    public static final RegistryObject<Item> SmartPhone = ITEMS.register("smartphone",
            ()->new SmartPhoneitem(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> PlainBow0 = ITEMS.register("plainbow0",
            ()->new PlainBow(new Item.Properties().stacksTo(1).rarity(Utils.PlainItalic),0));
    public static final RegistryObject<Item> PlainBow1 = ITEMS.register("plainbow1",
            ()->new PlainBow(new Item.Properties().stacksTo(1).rarity(Utils.PlainItalic),1));
    public static final RegistryObject<Item> PlainBow2 = ITEMS.register("plainbow2",
            ()->new PlainBow(new Item.Properties().stacksTo(1).rarity(Utils.PlainItalic),2));
    public static final RegistryObject<Item> PlainBow3 = ITEMS.register("plainbow3",
            ()->new PlainBow(new Item.Properties().stacksTo(1).rarity(Utils.PlainItalic),3));
    public static final RegistryObject<Item> VolcanoBow0 = ITEMS.register("volcanobow0",
            ()->new VolcanoBow(new Item.Properties().stacksTo(1).rarity(Utils.VolcanoItalic),0));
    public static final RegistryObject<Item> VolcanoBow1 = ITEMS.register("volcanobow1",
            ()->new VolcanoBow(new Item.Properties().stacksTo(1).rarity(Utils.VolcanoItalic),1));
    public static final RegistryObject<Item> VolcanoBow2 = ITEMS.register("volcanobow2",
            ()->new VolcanoBow(new Item.Properties().stacksTo(1).rarity(Utils.VolcanoItalic),2));
    public static final RegistryObject<Item> VolcanoBow3 = ITEMS.register("volcanobow3",
            ()->new VolcanoBow(new Item.Properties().stacksTo(1).rarity(Utils.VolcanoItalic),3));
    public static final RegistryObject<Item> Security = ITEMS.register("security",
            ()->new openSecurity(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> ResetSecurity = ITEMS.register("resetsecurity",
            ()->new ResetSecurity(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> Main1Crystal = ITEMS.register("main1crystal",
            ()->new main1crystal(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> PLAIN_CORD = ITEMS.register("plain_cord",
            ()->new PlainCord(new Item.Properties().rarity(Utils.PlainItalic)));
    public static final RegistryObject<Item> FOREST_CORD = ITEMS.register("forest_cord",
            ()->new ForestCord(new Item.Properties().rarity(Utils.ForestItalic)));
    public static final RegistryObject<Item> LAKE_CORD = ITEMS.register("lake_cord",
            ()->new LakeCord(new Item.Properties().rarity(Utils.LakeItalic)));
    public static final RegistryObject<Item> VOLCANO_CORD = ITEMS.register("volcano_cord",
            ()->new VolcanoCord(new Item.Properties().rarity(Utils.VolcanoItalic)));
    public static final RegistryObject<Item> drug0= ITEMS.register("drug0",
            ()->new drug0(new Item.Properties()));
    public static final RegistryObject<Item> PLAINFOREST_CORD = ITEMS.register("plainforest_cord",
            ()->new PFCord(new Item.Properties().rarity(Utils.LifeItalic)));
    public static final RegistryObject<Item> LAKEVOLCANO_CORD = ITEMS.register("lakevolcano_cord",
            ()->new LVCord(new Item.Properties().rarity(Utils.EvokerItalic)));
    public static final RegistryObject<Item> FINAL_CORD = ITEMS.register("final_cord",
            ()->new FInalCord(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> NewCurios = ITEMS.register("newcurios",
            ()->new NewCurios(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> SpeIron = ITEMS.register("speiron",
            ()->new SpeIronIngot(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> tick = ITEMS.register("tick",
            ()->new Tick(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> SkyBow = ITEMS.register("skybow",
            ()->new SkyBow(new Item.Properties().stacksTo(1).rarity(Utils.SkyItalic)));
    public static final RegistryObject<Item> ArmorSky = ITEMS.register("armorsky",
            ()->new MobArmor(StringUtils.MobName.SkyVex));
    public static final RegistryObject<Item> SkySoul = ITEMS.register("skysoul",
            ()->new skysoul(new Item.Properties().rarity(Utils.Sky)));
    public static final RegistryObject<Item> SkyRune = ITEMS.register("skyrune",
            ()->new skyrune(new Item.Properties().rarity(Utils.SkyBold)));
    public static final RegistryObject<Item> OpenSlot = ITEMS.register("openslot",
            ()->new SlotOpen(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> SkyGem = ITEMS.register("skygem",
            ()->new SkyGem(new Item.Properties().rarity(Utils.SkyBold)));
    public static final RegistryObject<Item> SkyGemD = ITEMS.register("skygem_d",
            ()->new SkyGemD(new Item.Properties().rarity(Utils.SkyBold)));
    public static final RegistryObject<Item> EntityCopy = ITEMS.register("entitycopy",
            ()->new EntityTP(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> BlockReset = ITEMS.register("blockreset",
            ()->new BlockPosReset(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> Shoot = ITEMS.register("shoot",
            ()->new Shoot(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> ArmorEvoker = ITEMS.register("armorevoker",
            ()->new MobArmor(StringUtils.MobName.Evoker));
    public static final RegistryObject<Item> EvokerSoul = ITEMS.register("evokersoul",
            ()->new EvokerSoul(new Item.Properties().rarity(Utils.Evoker)));
    public static final RegistryObject<Item> ManaBucket = ITEMS.register("manabucket",
            ()->new ManaBucket(new Item.Properties().rarity(Utils.Evoker)));
    public static final RegistryObject<Item> EvokerRune = ITEMS.register("evokerrune",
            ()->new EvokerRune(new Item.Properties().rarity(Utils.EvokerBold)));
    public static final RegistryObject<Item> ManaBalance_Empty = ITEMS.register("manabalance_empty",
            ()->new ManaBalance_Empty(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> ManaBalance_filled = ITEMS.register("manabalance_filled",
            ()->new ManaBalance_Filled(new Item.Properties().rarity(Utils.EvokerBold)));
    public static final RegistryObject<Item> EvokerSword = ITEMS.register("evokersword",
            ()->new EvokerSword(ItemTier.VMaterial,2,0,new Item.Properties().rarity(Utils.EvokerItalic)));
    public static final RegistryObject<Item> PlainSceptre0 = ITEMS.register("plainsceptre0",
            ()->new PlainSceptre0(ItemTier.VMaterial,2,0,new Item.Properties().rarity(Utils.PlainItalic)));
    public static final RegistryObject<Item> PlainSceptre1 = ITEMS.register("plainsceptre1",
            ()->new PlainSceptre1(ItemTier.VMaterial,2,0,new Item.Properties().rarity(Utils.PlainItalic)));
    public static final RegistryObject<Item> PlainSceptre2 = ITEMS.register("plainsceptre2",
            ()->new PlainSceptre2(ItemTier.VMaterial,2,0,new Item.Properties().rarity(Utils.PlainItalic)));
    public static final RegistryObject<Item> PlainSceptre3 = ITEMS.register("plainsceptre3",
            ()->new PlainSceptre3(ItemTier.VMaterial,2,0,new Item.Properties().rarity(Utils.PlainItalic)));
    public static final RegistryObject<Item> PlainSceptre4 = ITEMS.register("plainsceptre4",
            ()->new PlainSceptre4(ItemTier.VMaterial,2,0,new Item.Properties().rarity(Utils.LifeItalic)));
    public static final RegistryObject<Item> LifeManaArmorBoots = ITEMS.register("lifemanaarmorboots",
            ()->new LifeManaArmorBoots(ItemMaterial.LifeManaBoots, ArmorItem.Type.BOOTS));
    public static final RegistryObject<Item> LifeManaArmorLeggings = ITEMS.register("lifemanaarmorleggings",
            ()->new LifeManaArmorLeggings(ItemMaterial.LifeManaLeggings, ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<Item> LifeManaArmorChest = ITEMS.register("lifemanaarmorchest",
            ()->new LifeManaArmorChest(ItemMaterial.LifeManaChest, ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> LifeManaArmorHelmet = ITEMS.register("lifemanaarmorhelmet",
            ()->new LifeManaArmorHelmet(ItemMaterial.LifeManaHelmet, ArmorItem.Type.HELMET));
    public static final RegistryObject<Item> ObsiManaArmorBoots = ITEMS.register("obsimanaarmorboots",
            ()->new ObsiManaArmorBoots(ItemMaterial.ObsiManaBoots, ArmorItem.Type.BOOTS));
    public static final RegistryObject<Item> ObsiManaArmorLeggings = ITEMS.register("obsimanaarmorleggings",
            ()->new ObsiManaArmorLeggings(ItemMaterial.ObsiManaLeggings, ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<Item> ObsiManaArmorChest = ITEMS.register("obsimanaarmorchest",
            ()->new ObsiManaArmorChest(ItemMaterial.ObsiManaChest, ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> ObsiManaArmorHelmet = ITEMS.register("obsimanaarmorhelmet",
            ()->new ObsiManaArmorHelmet(ItemMaterial.ObsiManaHelmet, ArmorItem.Type.HELMET));
    public static final RegistryObject<Item> plainmana = ITEMS.register("plainmana",
            ()->new PlainMana(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> forestmana = ITEMS.register("forestmana",
            ()->new ForestMana(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> lakemana = ITEMS.register("lakemana",
            ()->new LakeMana(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> volcanomana = ITEMS.register("volcanomana",
            ()->new VolcanoMana(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> ForgingStone0 = ITEMS.register("forgingstone0",
            ()->new forgingstone0(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> ForgingStone1 = ITEMS.register("forgingstone1",
            ()->new forgingstone1(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> ForgingStone2 = ITEMS.register("forgingstone2",
            ()->new forgingstone2(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> FeiLeiShen = ITEMS.register("feileishen",
            ()->new FeiLeiShen(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> IndexCheck = ITEMS.register("indexcheck",
            ()->new InventoryCheck(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> Ruby = ITEMS.register("ruby",
            ()->new Ruby(new Item.Properties().rarity(Utils.Nether)));
    public static final RegistryObject<Item> ArmorWitherSkeleton = ITEMS.register("armorwitherskeleton",
            ()->new MobArmor(StringUtils.MobName.WitherSkeleton));
    public static final RegistryObject<Item> ManaSword = ITEMS.register("manasword",
            ()->new ManaSword(ItemTier.VMaterial,2,0));
    public static final RegistryObject<Item> ArmorEvokerMaster = ITEMS.register("armorevokermaster",
            ()->new MobArmor(StringUtils.MobName.EvokerMaster));
    public static final RegistryObject<Item> NetherSoul = ITEMS.register("nethersoul",
            ()->new NetherSoul(new Item.Properties().rarity(Utils.Nether)));
    public static final RegistryObject<Item> NetherRune = ITEMS.register("netherrune",
            ()->new NetherRune(new Item.Properties().rarity(Utils.NetherBold)));
    public static final RegistryObject<Item> NetherSwordModel = ITEMS.register("netherswordmodel",
            ()->new NetherSwordModel(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> witherBone = ITEMS.register("witherbone",
            ()->new WitherBone(new Item.Properties().rarity(Utils.Wither)));
    public static final RegistryObject<Item> manaRune0 = ITEMS.register("manarune0",
            ()->new ManaRune0(new Item.Properties().rarity(Utils.EvokerItalic)));
    public static final RegistryObject<Item> manaRune1 = ITEMS.register("manarune1",
            ()->new ManaRune1(new Item.Properties().rarity(Utils.EvokerItalic)));
    public static final RegistryObject<Item> manaRune2 = ITEMS.register("manarune2",
            ()->new ManaRune2(new Item.Properties().rarity(Utils.EvokerItalic)));
    public static final RegistryObject<Item> manaRune3 = ITEMS.register("manarune3",
            ()->new ManaRune3(new Item.Properties().rarity(Utils.EvokerItalic)));
    public static final RegistryObject<Item> ArmorZombiePiglin = ITEMS.register("armorzombiepiglin",
            ()->new MobArmor(StringUtils.MobName.Piglin));
    public static final RegistryObject<Item> PigLinSoul = ITEMS.register("piglinsoul",
            ()->new PiglinSoul(new Item.Properties().rarity(Utils.Magma)));
    public static final RegistryObject<Item> ArmorNetherSkeletonHelmet = ITEMS.register("armornetherskeletonhelmet",
            ()->new MobArmor(ItemMaterial.MaterialForArmor2, ArmorItem.Type.HELMET, StringUtils.MobName.NetherSkeleton));
    public static final RegistryObject<Item> ArmorNetherSkeletonChest = ITEMS.register("armornetherskeletonchest",
            ()->new MobArmor(ItemMaterial.NetherAll, ArmorItem.Type.CHESTPLATE,StringUtils.MobName.NetherSkeleton));
    public static final RegistryObject<Item> ArmorNetherSkeletonLeggings = ITEMS.register("armornetherskeletonleggings",
            ()->new MobArmor(ItemMaterial.NetherAll, ArmorItem.Type.LEGGINGS,StringUtils.MobName.NetherSkeleton));
    public static final RegistryObject<Item> ArmorNetherSkeletonBoots = ITEMS.register("armornetherskeletonboots",
            ()->new MobArmor(ItemMaterial.NetherAll, ArmorItem.Type.BOOTS,StringUtils.MobName.NetherSkeleton));

    public static final RegistryObject<Item> ArmorNetherInstanceChest = ITEMS.register("nether_instance_chest",
            ()->new MobArmor(ItemMaterial.NetherAll, ArmorItem.Type.CHESTPLATE,StringUtils.MobName.NoAttribute));
    public static final RegistryObject<Item> ArmorNetherInstanceLeggings = ITEMS.register("nether_instance_leggings",
            ()->new MobArmor(ItemMaterial.NetherAll, ArmorItem.Type.LEGGINGS,StringUtils.MobName.NoAttribute));
    public static final RegistryObject<Item> ArmorNetherInstanceBoots = ITEMS.register("nether_instance_boots",
            ()->new MobArmor(ItemMaterial.NetherAll, ArmorItem.Type.BOOTS,StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> netherbonemeal = ITEMS.register("netherbonemeal",
            ()->new NetherBoneMeal(new Item.Properties().rarity(Utils.Wither)));
    public static final RegistryObject<Item> NetherQuartz = ITEMS.register("netherquartz",
            ()->new NetherQuartz(new Item.Properties().rarity(Utils.Quartz)));
    public static final RegistryObject<Item> WitherBonePower = ITEMS.register("witherbonepower",
            ()->new WitherBonePower(new Item.Properties().rarity(Utils.EvokerItalic)));
    public static final RegistryObject<Item> PigLinPower = ITEMS.register("piglinpower",
            ()->new PiglinPower(new Item.Properties().rarity(Utils.EvokerItalic)));
    public static final RegistryObject<Item> WitherBoneMealPower = ITEMS.register("witherbonemealpower",
            ()->new WitherBoneMealPower(new Item.Properties().rarity(Utils.EvokerItalic)));
    public static final RegistryObject<Item> EvokerGem = ITEMS.register("evokergem",
            ()->new EvokerGem(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> EvokerGemD = ITEMS.register("evokergem_d",
            ()->new EvokerGemD(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> NetherPower = ITEMS.register("netherpower",
            ()->new NetherPower(new Item.Properties().rarity(Utils.NetherItalic)));
    public static final RegistryObject<Item> NetherBow = ITEMS.register("netherbow",
            ()->new NetherBow(new Item.Properties().stacksTo(1).rarity(Utils.NetherItalic)));
    public static final RegistryObject<Item> QuartzSword = ITEMS.register("quartzsword",
            ()->new QuartzSword(ItemTier.VMaterial,2,0));
    public static final RegistryObject<Item> QuartzSoul = ITEMS.register("quartzsoul",
            ()->new QuartzSoul(new Item.Properties().rarity(Utils.Quartz)));
    public static final RegistryObject<Item> QuartzRune = ITEMS.register("quartzrune",
            ()->new QuartzRune(new Item.Properties().rarity(Utils.QuartzBold)));
    public static final RegistryObject<Item> PowerModel = ITEMS.register("powermodel",
            ()->new PowerModel(new Item.Properties().rarity(Utils.Evoker)));
    public static final RegistryObject<Item> QuartzSabre = ITEMS.register("quartzsabre",
            ()->new QuartzSabre(ItemTier.VMaterial,2,0));
    public static final RegistryObject<Item> quartzcheck = ITEMS.register("quartzcheck",
            ()->new quartzsabrecheck(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> NetherSabreModel = ITEMS.register("nethersabremodel",
            ()->new NetherSabreModel(new Item.Properties().rarity(Utils.Quartz)));
    public static final RegistryObject<Item> ArmorGuardian = ITEMS.register("armorguardian",
            ()->new MobArmor(StringUtils.MobName.SeaGuardian));
    public static final RegistryObject<Item> SeaSoul = ITEMS.register("seasoul",
            ()->new SeaSoul(new Item.Properties().rarity(Utils.Sea)));
    public static final RegistryObject<Item> SeaRune = ITEMS.register("searune",
            ()->new SeaRune(new Item.Properties().rarity(Utils.SeaBold)));
    public static final RegistryObject<Item> ArmorLZHelmet = ITEMS.register("armorlzhelmet",
            ()->new MobArmor(ItemMaterial.MaterialForArmor2, ArmorItem.Type.HELMET,StringUtils.MobName.LightingZombie));
    public static final RegistryObject<Item> ArmorLZChest = ITEMS.register("armorlzchest",
            ()->new MobArmor(ItemMaterial.MaterialForArmor2, ArmorItem.Type.CHESTPLATE,StringUtils.MobName.LightingZombie));
    public static final RegistryObject<Item> ArmorLZLeggings = ITEMS.register("armorlzleggings",
            ()->new MobArmor(ItemMaterial.MaterialForArmor2, ArmorItem.Type.LEGGINGS,StringUtils.MobName.LightingZombie));
    public static final RegistryObject<Item> ArmorLZBoots = ITEMS.register("armorlzboots",
            ()->new MobArmor(ItemMaterial.MaterialForArmor2, ArmorItem.Type.BOOTS,StringUtils.MobName.LightingZombie));
    public static final RegistryObject<Item> LightningSoul = ITEMS.register("lightningsoul",
            ()->new LightningSoul(new Item.Properties().rarity(Utils.Lightning)));
    public static final RegistryObject<Item> LightningRune = ITEMS.register("lightningrune",
            ()->new LightningRune(new Item.Properties().rarity(Utils.LightningBold)));
    public static final RegistryObject<Item> PlainGem = ITEMS.register("plaingem",
            ()->new PlainGem(new Item.Properties().rarity(Utils.Plain)));
    public static final RegistryObject<Item> ForestGem = ITEMS.register("forestgem",
            ()->new ForestGem(new Item.Properties().rarity(Utils.Forest)));
    public static final RegistryObject<Item> LakeGem = ITEMS.register("lakegem",
            ()->new LakeGem(new Item.Properties().rarity(Utils.Lake)));
    public static final RegistryObject<Item> VolcanoGem = ITEMS.register("volcanogem",
            ()->new VolcanoGem(new Item.Properties().rarity(Utils.Volcano)));
    public static final RegistryObject<Item> SnowGem = ITEMS.register("snowgem",
            ()->new SnowGem(new Item.Properties().rarity(Utils.Snow)));

    public static final RegistryObject<Item> PlainGemD = ITEMS.register("plaingem_d",
            ()->new PlainGemD(new Item.Properties().rarity(Utils.Plain)));
    public static final RegistryObject<Item> ForestGemD = ITEMS.register("forestgem_d",
            ()->new ForestGemD(new Item.Properties().rarity(Utils.Forest)));
    public static final RegistryObject<Item> LakeGemD = ITEMS.register("lakegem_d",
            ()->new LakeGemD(new Item.Properties().rarity(Utils.Lake)));
    public static final RegistryObject<Item> VolcanoGemD = ITEMS.register("volcanogem_d",
            ()->new VolcanoGemD(new Item.Properties().rarity(Utils.Volcano)));
    public static final RegistryObject<Item> SnowGemD = ITEMS.register("snowgem_d",
            ()->new SnowGemD(new Item.Properties().rarity(Utils.Snow)));

    public static final RegistryObject<Item> IslandArmorHelmet = ITEMS.register("islandarmorhelmet",
            ()->new LightningArmorHelmet(ItemMaterial.IslandMaterial, ArmorItem.Type.HELMET));
    public static final RegistryObject<Item> IslandArmorChest = ITEMS.register("islandarmorchest",
            ()->new LightningArmorChest(ItemMaterial.IslandMaterial, ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> IslandArmorLeggings = ITEMS.register("islandarmorleggings",
            ()->new LightningArmorLeggings(ItemMaterial.IslandMaterial, ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<Item> IslandArmorBoots = ITEMS.register("islandarmorboots",
            ()->new LightningArmorBoots(ItemMaterial.IslandMaterial, ArmorItem.Type.BOOTS));
    public static final RegistryObject<Item> SeaSword0 = ITEMS.register("seasword0",
            ()->new SeaSword0(ItemTier.VMaterial,2,0));
    public static final RegistryObject<Item> SeaSword1 = ITEMS.register("seasword1",
            ()->new SeaSword1(ItemTier.VMaterial,2,0));
    public static final RegistryObject<Item> SeaSword2 = ITEMS.register("seasword2",
            ()->new SeaSword2(ItemTier.VMaterial,2,0));
    public static final RegistryObject<Item> SeaSword3 = ITEMS.register("seasword3",
            ()->new SeaSword3(ItemTier.VMaterial,2,0));
    public static final RegistryObject<Item> BlackForestSword0 = ITEMS.register("blackforestsword0",
            ()->new BlackForestSword0(ItemTier.VMaterial,2,0));
    public static final RegistryObject<Item> BlackForestSword1 = ITEMS.register("blackforestsword1",
            ()->new BlackForestSword1(ItemTier.VMaterial,2,0));
    public static final RegistryObject<Item> BlackForestSword2 = ITEMS.register("blackforestsword2",
            ()->new BlackForestSword2(ItemTier.VMaterial,2,0));
    public static final RegistryObject<Item> BlackForestSword3 = ITEMS.register("blackforestsword3",
            ()->new BlackForestSword3(ItemTier.VMaterial,2,0));
    public static final RegistryObject<Item> volcanoRune0 = ITEMS.register("volcanorune0",
            ()->new VolcanoRune0(new Item.Properties().rarity(Utils.Volcano)));
    public static final RegistryObject<Item> volcanoRune1 = ITEMS.register("volcanorune1",
            ()->new VolcanoRune1(new Item.Properties().rarity(Utils.Volcano)));
    public static final RegistryObject<Item> volcanoRune2 = ITEMS.register("volcanorune2",
            ()->new VolcanoRune2(new Item.Properties().rarity(Utils.Volcano)));
    public static final RegistryObject<Item> volcanoRune3 = ITEMS.register("volcanorune3",
            ()->new VolcanoRune3(new Item.Properties().rarity(Utils.Volcano)));
    public static final RegistryObject<Item> DailyMission = ITEMS.register("dailymission",
            ()->new Daily(new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1)));
    public static final RegistryObject<Item> LevelReward5 = ITEMS.register("levelreward5",
            ()->new LevelReward5_55(new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1),5));
    public static final RegistryObject<Item> LevelReward10 = ITEMS.register("levelreward10",
            ()->new LevelReward5_55(new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1),10));
    public static final RegistryObject<Item> LevelReward15 = ITEMS.register("levelreward15",
            ()->new LevelReward5_55(new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1),15));
    public static final RegistryObject<Item> LevelReward20 = ITEMS.register("levelreward20",
            ()->new LevelReward5_55(new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1),20));
    public static final RegistryObject<Item> LevelReward25 = ITEMS.register("levelreward25",
            ()->new LevelReward5_55(new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1),25));
    public static final RegistryObject<Item> LevelReward30 = ITEMS.register("levelreward30",
            ()->new LevelReward5_55(new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1),30));
    public static final RegistryObject<Item> LevelReward35 = ITEMS.register("levelreward35",
            ()->new LevelReward5_55(new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1),35));
    public static final RegistryObject<Item> LevelReward40 = ITEMS.register("levelreward40",
            ()->new LevelReward5_55(new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1),40));
    public static final RegistryObject<Item> LevelReward45 = ITEMS.register("levelreward45",
            ()->new LevelReward5_55(new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1),45));
    public static final RegistryObject<Item> LevelReward50 = ITEMS.register("levelreward50",
            ()->new LevelReward5_55(new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1),50));
    public static final RegistryObject<Item> LevelReward55 = ITEMS.register("levelreward55",
            ()->new LevelReward5_55(new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1),55));
    public static final RegistryObject<Item> LevelReward60 = ITEMS.register("levelreward60",
            ()->new LevelReward60(new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1)));
    public static final RegistryObject<Item> SkyArmorHelmet = ITEMS.register("skyarmorhelmet",
            ()->new SkyArmorHelmet(ItemMaterial.SkyMaterial, ArmorItem.Type.HELMET));
    public static final RegistryObject<Item> SkyArmorChest = ITEMS.register("skyarmorchest",
            ()->new SkyArmorChest(ItemMaterial.SkyMaterial, ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> SkyArmorLeggings = ITEMS.register("skyarmorleggings",
            ()->new SkyArmorLeggings(ItemMaterial.SkyMaterial, ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<Item> SkyArmorBoots = ITEMS.register("skyarmorboots",
            ()->new SkyArmorBoots(ItemMaterial.SkyMaterial, ArmorItem.Type.BOOTS));
    public static final RegistryObject<Item> NetherArmorHelmet = ITEMS.register("netherarmorhelmet",
            ()->new NetherArmorHelmet(ItemMaterial.NetherAll, ArmorItem.Type.HELMET));
    public static final RegistryObject<Item> NetherArmorChest = ITEMS.register("netherarmorchest",
            ()->new NetherArmorChest(ItemMaterial.NetherAll, ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> NetherArmorLeggings = ITEMS.register("netherarmorleggings",
            ()->new NetherArmorLeggings(ItemMaterial.NetherAll, ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<Item> NetherArmorBoots = ITEMS.register("netherarmorboots",
            ()->new NetherArmorBoots(ItemMaterial.NetherAll, ArmorItem.Type.BOOTS));

    public static final RegistryObject<Item> AttackUp_PotionBag = ITEMS.register("attackup_potionbag",
            ()->new PotionBag(new Item.Properties().rarity(Rarity.RARE),
                    Utils.PotionStringComponentMap.get(StringUtils.PotionTypes.AttackUp),
                    StringUtils.PotionTypes.AttackUp));

    public static final RegistryObject<Item> Breakdefenceup_potionBag = ITEMS.register("breakdefenceup_potionbag",
            ()->new PotionBag(new Item.Properties().rarity(Rarity.RARE),
                    Utils.PotionStringComponentMap.get(StringUtils.PotionTypes.BreakDefenceUp),
                    StringUtils.PotionTypes.BreakDefenceUp));

    public static final RegistryObject<Item> CritRateUp_PotionBag = ITEMS.register("critrateup_potionbag",
            ()->new PotionBag(new Item.Properties().rarity(Rarity.RARE),
                    Utils.PotionStringComponentMap.get(StringUtils.PotionTypes.CritRateUp),
                    StringUtils.PotionTypes.CritRateUp));

    public static final RegistryObject<Item> CritDamageUp_PotionBag = ITEMS.register("critdamageup_potionbag",
            ()->new PotionBag(new Item.Properties().rarity(Rarity.RARE),
                    Utils.PotionStringComponentMap.get(StringUtils.PotionTypes.CritDamageUp),
                    StringUtils.PotionTypes.CritDamageUp));

    public static final RegistryObject<Item> ManaDamageUp_PotionBag = ITEMS.register("manadamageup_potionbag",
            ()->new PotionBag(new Item.Properties().rarity(Rarity.RARE),
                    Utils.PotionStringComponentMap.get(StringUtils.PotionTypes.ManaDamageUp),
                    StringUtils.PotionTypes.ManaDamageUp));

    public static final RegistryObject<Item> ManaBreakdefenceup_potionBag = ITEMS.register("manabreakdefenceup_potionbag",
            ()->new PotionBag(new Item.Properties().rarity(Rarity.RARE),
                    Utils.PotionStringComponentMap.get(StringUtils.PotionTypes.ManaBreakDefenceUp),
                    StringUtils.PotionTypes.ManaBreakDefenceUp));

    public static final RegistryObject<Item> ManaReplyUp_PotionBag = ITEMS.register("manareplyup_potionbag",
            ()->new PotionBag(new Item.Properties().rarity(Rarity.RARE),
                    Utils.PotionStringComponentMap.get(StringUtils.PotionTypes.ManaReplyUp),
                    StringUtils.PotionTypes.ManaReplyUp));

    public static final RegistryObject<Item> CoolDownDecreaseUp_PotionBag = ITEMS.register("cooldowndecreaseup_potionbag",
            ()->new PotionBag(new Item.Properties().rarity(Rarity.RARE),
                    Utils.PotionStringComponentMap.get(StringUtils.PotionTypes.CoolDownDecreaseUp),
                    StringUtils.PotionTypes.CoolDownDecreaseUp));

    public static final RegistryObject<Item> HealStealUp_PotionBag = ITEMS.register("healstealup_potionbag",
            ()->new PotionBag(new Item.Properties().rarity(Rarity.RARE),
                    Utils.PotionStringComponentMap.get(StringUtils.PotionTypes.HealStealUp),
                    StringUtils.PotionTypes.HealStealUp));

    public static final RegistryObject<Item> defenceup_potionBag = ITEMS.register("defenceup_potionbag",
            ()->new PotionBag(new Item.Properties().rarity(Rarity.RARE),
                    Utils.PotionStringComponentMap.get(StringUtils.PotionTypes.DefenceUp),
                    StringUtils.PotionTypes.DefenceUp));

    public static final RegistryObject<Item> Manadefenceup_potionBag = ITEMS.register("manadefenceup_potionbag",
            ()->new PotionBag(new Item.Properties().rarity(Rarity.RARE),
                    Utils.PotionStringComponentMap.get(StringUtils.PotionTypes.ManaDefenceUp),
                    StringUtils.PotionTypes.ManaDefenceUp));

    public static final RegistryObject<Item> SpeedUp_PotionBag = ITEMS.register("speedup_potionbag",
            ()->new PotionBag(new Item.Properties().rarity(Rarity.RARE),
                    Utils.PotionStringComponentMap.get(StringUtils.PotionTypes.SpeedUp),
                    StringUtils.PotionTypes.SpeedUp));

    public static final RegistryObject<Item> HealthRecover_PotionBag = ITEMS.register("healthrecover_potionbag",
            ()->new PotionBag(new Item.Properties().rarity(Rarity.RARE),
                    Utils.PotionStringComponentMap.get(StringUtils.PotionTypes.HealthRecover),
                    StringUtils.PotionTypes.HealthRecover));

    public static final RegistryObject<Item> GoldCoinBag = ITEMS.register("goldcoinbag",
            ()->new GoldCoinBag(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> Purifier = ITEMS.register("purifier",
            ()->new Purifier(new Item.Properties()));
    public static final RegistryObject<Item> PurifiedWater = ITEMS.register("purified_water",
            ()->new PurifiedWater(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> BrewingNote = ITEMS.register("brewingnote",
            ()->new BrewingNote(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> PlainSolidifiedSoul = ITEMS.register("plain_solidified_soul",
            ()->new PlainSolidifiedSoul(new Item.Properties().rarity(Utils.Plain)));
    public static final RegistryObject<Item> ForestSolidifiedSoul = ITEMS.register("forest_solidified_soul",
            ()->new ForestSolidifiedSoul(new Item.Properties().rarity(Utils.Forest)));
    public static final RegistryObject<Item> LakeSolidifiedSoul = ITEMS.register("lake_solidified_soul",
            ()->new LakeSolidifiedSoul(new Item.Properties().rarity(Utils.Lake)));
    public static final RegistryObject<Item> VolcanoSolidifiedSoul = ITEMS.register("volcano_solidified_soul",
            ()->new VolcanoSolidifiedSoul(new Item.Properties().rarity(Utils.Volcano)));
    public static final RegistryObject<Item> SnowSolidifiedSoul = ITEMS.register("snow_solidified_soul",
            ()->new SnowSolidifiedSoul(new Item.Properties().rarity(Utils.Snow)));
    public static final RegistryObject<Item> SkySolidifiedSoul = ITEMS.register("sky_solidified_soul",
            ()->new SkySolidifiedSoul(new Item.Properties().rarity(Utils.Sky)));
    public static final RegistryObject<Item> BlackForestSoul = ITEMS.register("blackforestsoul",
            ()->new BlackForestSoul(new Item.Properties().rarity(Utils.BlackForest)));
    public static final RegistryObject<Item> BlackForestRune = ITEMS.register("blackforestrune",
            ()->new BlackForestRune(new Item.Properties().rarity(Utils.BlackForestBold)));
    public static final RegistryObject<Item> EvokerSolidifiedSoul = ITEMS.register("evoker_solidified_soul",
            ()->new EvokerSolidifiedSoul(new Item.Properties().rarity(Utils.Evoker)));
    public static final RegistryObject<Item> NetherSolidifiedSoul = ITEMS.register("nether_solidified_soul",
            ()->new NetherSolidifiedSoul(new Item.Properties().rarity(Utils.Nether)));
    public static final RegistryObject<Item> Solidifier = ITEMS.register("solidifier",
            ()->new Solidifier(new Item.Properties()));
    public static final RegistryObject<Item> ArmorHusk = ITEMS.register("armorhusk",
                ()->new MobArmor(StringUtils.MobName.Husk));
    public static final RegistryObject<Item> ArmorSpider = ITEMS.register("armorspider",
            ()->new MobArmor(StringUtils.MobName.Spider));
    public static final RegistryObject<Item> Stabilizer = ITEMS.register("stabilizer",
            ()->new Stabilizer(new Item.Properties()));
    public static final RegistryObject<Item> Concentrater = ITEMS.register("concentrater",
            ()->new Concentrater(new Item.Properties()));
    public static final RegistryObject<Item> BackPackTickets = ITEMS.register("backpackticket",
            ()->new BackPackTicket(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1)));
    public static final RegistryObject<Item> ArmorPFH = ITEMS.register("armorpfhelmet",
            ()->new MobArmor(ItemMaterial.ArmorPF, ArmorItem.Type.HELMET, 45,25,10));
    public static final RegistryObject<Item> ArmorPFC = ITEMS.register("armorpfchest",
            ()->new MobArmor(ItemMaterial.ArmorPF, ArmorItem.Type.CHESTPLATE, 45,25,10));
    public static final RegistryObject<Item> ArmorPFL = ITEMS.register("armorpfleggings",
            ()->new MobArmor(ItemMaterial.ArmorPF, ArmorItem.Type.LEGGINGS, 45,25,10));
    public static final RegistryObject<Item> ArmorPFB = ITEMS.register("armorpfboots",
            ()->new MobArmor(ItemMaterial.ArmorPF, ArmorItem.Type.BOOTS, 45,25,10));
    public static final RegistryObject<Item> SunPower = ITEMS.register("sunpower",
            ()->new SunPower(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> ArmorSVH = ITEMS.register("armorsvhelmet",
            ()->new MobArmor(ItemMaterial.ArmorSV, ArmorItem.Type.HELMET,80,45,20));
    public static final RegistryObject<Item> ArmorSVC = ITEMS.register("armorsvchest",
            ()->new MobArmor(ItemMaterial.ArmorSV, ArmorItem.Type.CHESTPLATE,80,45,20));
    public static final RegistryObject<Item> ArmorSVL = ITEMS.register("armorsvleggings",
            ()->new MobArmor(ItemMaterial.ArmorSV, ArmorItem.Type.LEGGINGS,80,45,20));
    public static final RegistryObject<Item> ArmorSVB = ITEMS.register("armorsvboots",
            ()->new MobArmor(ItemMaterial.ArmorSV, ArmorItem.Type.BOOTS,80,45,20));
    public static final RegistryObject<Item> ArmorSLH = ITEMS.register("armorslhelmet",
            ()->new MobArmor(ItemMaterial.ArmorSL, ArmorItem.Type.HELMET,80,45,20));
    public static final RegistryObject<Item> ArmorSLC = ITEMS.register("armorslchest",
            ()->new MobArmor(ItemMaterial.ArmorSL, ArmorItem.Type.CHESTPLATE,80,45,20));
    public static final RegistryObject<Item> ArmorSLL = ITEMS.register("armorslleggings",
            ()->new MobArmor(ItemMaterial.ArmorSL, ArmorItem.Type.LEGGINGS,80,45,20));
    public static final RegistryObject<Item> ArmorSLB = ITEMS.register("armorslboots",
            ()->new MobArmor(ItemMaterial.ArmorSL, ArmorItem.Type.BOOTS,80,45,20));
    public static final RegistryObject<Item> Main1HandGem = ITEMS.register("mainhand",
            ()->new RandomGemPlain(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> Main1BeltGem = ITEMS.register("mainbelt",
            ()->new RandomGemForest(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> Main1necklaceGem = ITEMS.register("mainnecklace",
            ()->new RandomGemLake(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> Main1BraceletGem = ITEMS.register("mainbracelet",
            ()->new RandomGemVolcano(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> LightningChange = ITEMS.register("lightningchange",
            ()->new LightningChange(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> GuiOpen = ITEMS.register("guiopen",
            ()->new SmartPhoneOpen(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> GemBag = ITEMS.register("gembag",
            ()->new RandomGemGive(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> RandomGemPiece = ITEMS.register("randomgempiece",
            ()->new RandomGemPiece(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> ArmorMagma = ITEMS.register("armormagma",
            ()->new MobArmor(StringUtils.MobName.NetherMagma));
    public static final RegistryObject<Item> NetherMagmaPower = ITEMS.register("nethermagmapower",
            ()->new NetherMagmaPower(new Item.Properties().rarity(Utils.Magma)));
    public static final RegistryObject<Item> MagmaPower = ITEMS.register("magmapower",
            ()->new MagmaPower(new Item.Properties().rarity(Utils.MagmaItalic)));
    public static final RegistryObject<Item> NetherRune0 = ITEMS.register("netherrune0",
            ()->new NetherRune0(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> NetherRune1 = ITEMS.register("netherrune1",
            ()->new NetherRune1(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> NetherRune2 = ITEMS.register("netherrune2",
            ()->new NetherRune2(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> NetherRune3 = ITEMS.register("netherrune3",
            ()->new NetherRune3(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> ArmorNSHelmet = ITEMS.register("armornshelmet",
            ()->new MobArmor(ItemMaterial.NetherAll, ArmorItem.Type.HELMET,400,50,75));
    public static final RegistryObject<Item> SnowRune0 = ITEMS.register("snowrune0",
            ()->new SnowRune0(new Item.Properties().rarity(Utils.SnowItalic)));
    public static final RegistryObject<Item> SnowRune1 = ITEMS.register("snowrune1",
            ()->new SnowRune1(new Item.Properties().rarity(Utils.SnowItalic)));
    public static final RegistryObject<Item> SnowRune2 = ITEMS.register("snowrune2",
            ()->new SnowRune2(new Item.Properties().rarity(Utils.SnowItalic)));
    public static final RegistryObject<Item> SnowRune3 = ITEMS.register("snowrune3",
            ()->new SnowRune3(new Item.Properties().rarity(Utils.SnowItalic)));
    public static final RegistryObject<Item> KazeSword0 = ITEMS.register("kazesword0",
            ()->new KazeSword0(ItemTier.VMaterial,2,0));
    public static final RegistryObject<Item> KazeSword1 = ITEMS.register("kazesword1",
            ()->new KazeSword1(ItemTier.VMaterial,2,0));
    public static final RegistryObject<Item> KazeSword2 = ITEMS.register("kazesword2",
            ()->new KazeSword2(ItemTier.VMaterial,2,0));
    public static final RegistryObject<Item> KazeSword3 = ITEMS.register("kazesword3",
            ()->new KazeSword3(ItemTier.VMaterial,2,0));
    public static final RegistryObject<Item> ArmorKazeHelmet = ITEMS.register("armorkazehelmet",
            ()->new MobArmor(StringUtils.MobName.KazeZombie));
    public static final RegistryObject<Item> ArmorKazeChest = ITEMS.register("armorkazechest",
            ()->new MobArmor(ItemMaterial.ArmorKaze, ArmorItem.Type.CHESTPLATE,100,50,70));
    public static final RegistryObject<Item> ArmorKazeLeggings = ITEMS.register("armorkazeleggings",
            ()->new MobArmor(ItemMaterial.ArmorKaze, ArmorItem.Type.LEGGINGS,100,50,70));
    public static final RegistryObject<Item> ArmorKazeBoots = ITEMS.register("armorkazeboots",
            ()->new MobArmor(ItemMaterial.ArmorKaze, ArmorItem.Type.BOOTS,100,50,70));
    public static final RegistryObject<Item> ArmorKazeRecall = ITEMS.register("armorkazerecall",
            ()->new MobArmor(ItemMaterial.ArmorKaze, ArmorItem.Type.HELMET,500,50,90));
    public static final RegistryObject<Item> KazeSoul = ITEMS.register("kazesoul",
            ()->new KazeSoul(new Item.Properties().rarity(Utils.Kaze)));
    public static final RegistryObject<Item> KazeRune = ITEMS.register("kazerune",
            ()->new KazeRune(new Item.Properties().rarity(Utils.KazeBold)));
    public static final RegistryObject<Item> LakeCore = ITEMS.register("lakecore",
            ()->new LakeCore(new Item.Properties().rarity(Utils.LakeItalic)));
    public static final RegistryObject<Item> KazeBoots = ITEMS.register("kazeboots",
            ()->new KazeArmorBoots(ItemMaterial.ArmorKaze, ArmorItem.Type.BOOTS));
    public static final RegistryObject<Item> NetherGem = ITEMS.register("nethergem",
            ()->new RandomGemNether(new Item.Properties().rarity(Utils.NetherItalic)));
    public static final RegistryObject<Item> SpiderSoul = ITEMS.register("spidersoul",
            ()->new SpiderSoul(new Item.Properties().rarity(Utils.SpiderRarity)));
    public static final RegistryObject<Item> SpiderRune = ITEMS.register("spiderrune",
            ()->new SpiderRune(new Item.Properties().rarity(Utils.SpiderBold)));
    public static final RegistryObject<Item> SBoots = ITEMS.register("sboots",
            ()->new SpiderArmorBoots(ItemMaterial.ArmorS, ArmorItem.Type.BOOTS));
    public static final RegistryObject<Item> SLeggings = ITEMS.register("sleggings",
            ()->new SpiderArmorLeggings(ItemMaterial.ArmorS, ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<Item> SChest = ITEMS.register("schest",
            ()->new SpiderArmorChest(ItemMaterial.ArmorS, ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> SHelmet= ITEMS.register("shelmet",
            ()->new SpiderArmorHelmet(ItemMaterial.ArmorS, ArmorItem.Type.HELMET));
    public static final RegistryObject<Item> SunOintment0 = ITEMS.register("sunointment0",
            ()->new SunOintment0(new Item.Properties().rarity(Utils.Life)));
    public static final RegistryObject<Item> SunOintment1 = ITEMS.register("sunointment1",
            ()->new SunOintment0(new Item.Properties().rarity(Utils.Life),1));
    public static final RegistryObject<Item> SunOintment2 = ITEMS.register("sunointment2",
            ()->new SunOintment0(new Item.Properties().rarity(Utils.LifeItalic),2));
    public static final RegistryObject<Item> LakeOintment0 = ITEMS.register("lakeointment0",
            ()->new LakeOintment0(new Item.Properties().rarity(Utils.Lake)));
    public static final RegistryObject<Item> LakeOintment1 = ITEMS.register("lakeointment1",
            ()->new LakeOintment0(new Item.Properties().rarity(Utils.Lake),1));
    public static final RegistryObject<Item> LakeOintment2 = ITEMS.register("lakeointment2",
            ()->new LakeOintment0(new Item.Properties().rarity(Utils.LakeItalic),2));
    public static final RegistryObject<Item> VolcanoOintment0 = ITEMS.register("volcanoointment0",
            ()->new VolcanoOintment0(new Item.Properties().rarity(Utils.Volcano)));
    public static final RegistryObject<Item> VolcanoOintment1 = ITEMS.register("volcanoointment1",
            ()->new VolcanoOintment0(new Item.Properties().rarity(Utils.Volcano),1));
    public static final RegistryObject<Item> VolcanoOintment2 = ITEMS.register("volcanoointment2",
            ()->new VolcanoOintment0(new Item.Properties().rarity(Utils.VolcanoItalic),2));
    public static final RegistryObject<Item> SnowOintment0 = ITEMS.register("snowointment0",
            ()->new SnowOintment0(new Item.Properties().rarity(Utils.Snow)));
    public static final RegistryObject<Item> SnowOintment1 = ITEMS.register("snowointment1",
            ()->new SnowOintment0(new Item.Properties().rarity(Utils.Snow),1));
    public static final RegistryObject<Item> SnowOintment2 = ITEMS.register("snowointment2",
            ()->new SnowOintment0(new Item.Properties().rarity(Utils.SnowItalic),2));
    public static final RegistryObject<Item> SkyOintment0 = ITEMS.register("skyointment0",
            ()->new SkyOintment0(new Item.Properties().rarity(Utils.Sky)));
    public static final RegistryObject<Item> SkyOintment1 = ITEMS.register("skyointment1",
            ()->new SkyOintment0(new Item.Properties().rarity(Utils.Sky),1));
    public static final RegistryObject<Item> SkyOintment2 = ITEMS.register("skyointment2",
            ()->new SkyOintment0(new Item.Properties().rarity(Utils.SkyItalic),2));
    public static final RegistryObject<Item> ManaOintment0 = ITEMS.register("manaointment0",
            ()->new ManaOintment0(new Item.Properties().rarity(Utils.Evoker)));
    public static final RegistryObject<Item> ManaOintment1 = ITEMS.register("manaointment1",
            ()->new ManaOintment0(new Item.Properties().rarity(Utils.Evoker),1));
    public static final RegistryObject<Item> ManaOintment2 = ITEMS.register("manaointment2",
            ()->new ManaOintment0(new Item.Properties().rarity(Utils.EvokerItalic),2));
    public static final RegistryObject<Item> NetherOintment0 = ITEMS.register("netherointment0",
            ()->new NetherOintment0(new Item.Properties().rarity(Utils.Nether)));
    public static final RegistryObject<Item> NetherOintment1 = ITEMS.register("netherointment1",
            ()->new NetherOintment0(new Item.Properties().rarity(Utils.Nether),1));
    public static final RegistryObject<Item> NetherOintment2 = ITEMS.register("netherointment2",
            ()->new NetherOintment0(new Item.Properties().rarity(Utils.NetherItalic),2));

    public static final RegistryObject<Item> VolcanoCore = ITEMS.register("volcanocore",
            ()->new VolcanoCore(new Item.Properties().rarity(Utils.VolcanoBold)));
    public static final RegistryObject<Item> GoldSword0 = ITEMS.register("goldsword0",
            ()->new GoldSword0(ItemTier.VMaterial,2,0));
    public static final RegistryObject<Item> CodeSceptre = ITEMS.register("codesceptre",
            ()->new CodeSceptre(ItemTier.VMaterial,2,0,new Item.Properties().rarity(Utils.MagmaItalic)));
    public static final RegistryObject<Item> BreakMana = ITEMS.register("breakmana",
            ()->new BreakMana(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> DamageMana = ITEMS.register("damagemana",
            ()->new DamageMana(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> EffectMana = ITEMS.register("effectmana",
            ()->new EffectMana(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> GatherMana = ITEMS.register("gathermana",
            ()->new GatherMana(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> KazeMana = ITEMS.register("kazemana",
            ()->new KazeMana(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> LightningMana = ITEMS.register("lightningmana",
            ()->new LightningMana(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> RangeMana = ITEMS.register("rangemana",
            ()->new RangeMana(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> SnowMana = ITEMS.register("snowmana",
            ()->new SnowMana(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> LightningPower = ITEMS.register("lightningpower",
            ()->new LightningPower(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> ManaModel = ITEMS.register("manamodel",
            ()->new ManaModel(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> IronLove = ITEMS.register("ironlove",
            ()->new IronLove(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> SilverFishSoul = ITEMS.register("silverfishsoul",
            ()->new SilverFishSoul(new Item.Properties().rarity(Utils.End)));
    public static final RegistryObject<Item> ArmorSilverFish = ITEMS.register("armorsilverfish",
            ()->new MobArmor(StringUtils.MobName.SilverFish));
    public static final RegistryObject<Item> BlackForestRecall = ITEMS.register("blackforestrecall",
            ()->new BlackForestSwordRecallBook(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> EvokerRecallBook = ITEMS.register("evokerrecallbook",
            ()->new EvokerRecallBook(new Item.Properties().rarity(Utils.End)));
    public static final RegistryObject<Item> ForestRecallBook = ITEMS.register("forestrecallbook",
            ()->new ForestRecallBook(new Item.Properties().rarity(Utils.End)));
    public static final RegistryObject<Item> KazeRecallBook = ITEMS.register("kazerecallbook",
            ()->new KazeRecallBook(new Item.Properties().rarity(Utils.End)));
    public static final RegistryObject<Item> LakeRecallBook = ITEMS.register("lakerecallbook",
            ()->new LakeRecallBook(new Item.Properties().rarity(Utils.End)));
    public static final RegistryObject<Item> LightningRecallBook = ITEMS.register("lightningrecallbook",
            ()->new LightningRecallBook(new Item.Properties().rarity(Utils.End)));
    public static final RegistryObject<Item> NetherRecallBook1 = ITEMS.register("netherrecallbook1",
            ()->new NetherRecallBook1(new Item.Properties().rarity(Utils.End)));
    public static final RegistryObject<Item> NetherRecallBook2 = ITEMS.register("netherrecallbook2",
            ()->new NetherRecallBook2(new Item.Properties().rarity(Utils.End)));
    public static final RegistryObject<Item> PlainRecallBook = ITEMS.register("plainrecallbook",
            ()->new PlainRecallBook(new Item.Properties().rarity(Utils.End)));
    public static final RegistryObject<Item> SeaRecallBook = ITEMS.register("searecallbook",
            ()->new SeaRecallBook(new Item.Properties().rarity(Utils.End)));
    public static final RegistryObject<Item> SkyRecallBook = ITEMS.register("skyrecallbook",
            ()->new SkyRecallBook(new Item.Properties().rarity(Utils.End)));
    public static final RegistryObject<Item> SnowRecallBook = ITEMS.register("snowrecallbook",
            ()->new SnowRecallBook(new Item.Properties().rarity(Utils.End)));
    public static final RegistryObject<Item> SpiderRecallBook = ITEMS.register("spiderrecallbook",
            ()->new SpiderRecallBook(new Item.Properties().rarity(Utils.End)));
    public static final RegistryObject<Item> VolcanoRecallBook = ITEMS.register("volcanorecallbook",
            ()->new VolcanoRecallBook(new Item.Properties().rarity(Utils.End)));
    public static final RegistryObject<Item> RecallPiece = ITEMS.register("recallpiece",
            ()->new RecallPiece(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> ArmorEnderMan = ITEMS.register("armorenderman",
            ()->new MobArmor(StringUtils.MobName.EnderMan));
    public static final RegistryObject<Item> BarrierSet = ITEMS.register("barrierset",
            ()->new BarrierSet(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> KazeRecallSoul = ITEMS.register("kazerecallsoul",
            ()->new KazeRecallSoul(new Item.Properties().rarity(Utils.Kaze)));
    public static final RegistryObject<Item> IntensifiedKazeSoul = ITEMS.register("intensifiedkazesoul",
            ()->new IntensifiedKazeSoul(new Item.Properties().rarity(Utils.KazeBold)));
    public static final RegistryObject<Item> KazeSword4 = ITEMS.register("kazesword4",
            ()->new KazeSword4(ItemTier.VMaterial,2,0));
    public static final RegistryObject<Item> ArmorSpiderRecall = ITEMS.register("armorspiderecall",
            ()->new MobArmor(500,50,90));
    public static final RegistryObject<Item> SpiderRecallSoul = ITEMS.register("spiderrecallsoul",
            ()->new SpiderRecallSoul(new Item.Properties().rarity(Utils.SpiderRarity)));
    public static final RegistryObject<Item> ManageSword = ITEMS.register("managesword",
            ()->new ManageSword(ItemTier.VMaterial,2,0));
    public static final RegistryObject<Item> ISArmorHelmet = ITEMS.register("isarmorhelmet",
            ()->new SpiderRecallArmorHelmet(ItemMaterial.ArmorKaze, ArmorItem.Type.HELMET));
    public static final RegistryObject<Item> ISArmorChest = ITEMS.register("isarmorchest",
            ()->new SpiderRecallArmorChest(ItemMaterial.ArmorKaze, ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> ISArmorLeggings = ITEMS.register("isarmorleggings",
            ()->new SpiderRecallArmorLeggings(ItemMaterial.ArmorKaze, ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<Item> ISArmorBoots = ITEMS.register("isarmorboots",
            ()->new SpiderRecallArmorBoots(ItemMaterial.ArmorKaze, ArmorItem.Type.BOOTS));
    public static final RegistryObject<Item> IntensifiedSpiderSoul = ITEMS.register("intensifiedspidersoul",
            ()->new IntensifiedSpiderSoul(new Item.Properties().rarity(Utils.SpiderBold)));
    public static final RegistryObject<Item> BlackForestRecallSoul = ITEMS.register("blackforestrecallsoul",
            ()->new BlackForestRecallSoul(new Item.Properties().rarity(Utils.BlackForest)));
    public static final RegistryObject<Item> IntensifiedBlackForestSoul = ITEMS.register("intensifiedblackforestsoul",
            ()->new IntensifiedBlackForestSoul(new Item.Properties().rarity(Utils.BlackForestBold)));
    public static final RegistryObject<Item> ArmorHuskRecall = ITEMS.register("armorhuskrecall",
            ()->new MobArmor(800,50,90));
    public static final RegistryObject<Item> BlackForestSword4 = ITEMS.register("blackforestsword4",
            ()->new BlackForestSword4(ItemTier.VMaterial,2,0));
    public static final RegistryObject<Item> WaterSet = ITEMS.register("waterset",
            ()->new WaterSet(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> ArmorSeaRecall = ITEMS.register("armorsearecall",
            ()->new MobArmor(500,50,90));
    public static final RegistryObject<Item> SeaRecallSoul = ITEMS.register("searecallsoul",
            ()->new SeaRecallSoul(new Item.Properties().rarity(Utils.Sea)));
    public static final RegistryObject<Item> IntensifiedSeaSoul = ITEMS.register("intensifiedseasoul",
            ()->new IntensifiedSeaSoul(new Item.Properties().rarity(Utils.SeaBold)));
    public static final RegistryObject<Item> SeaSword4 = ITEMS.register("seasword4",
            ()->new SeaSword4(ItemTier.VMaterial,2,0));
    public static final RegistryObject<Item> ILArmorHelmet = ITEMS.register("ilarmorhelmet",
            ()->new iLightningArmorHelmet(ItemMaterial.ArmorIL, ArmorItem.Type.HELMET));
    public static final RegistryObject<Item> ILArmorChest = ITEMS.register("ilarmorchest",
            ()->new iLightningArmorChest(ItemMaterial.ArmorIL, ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> ILArmorLeggings = ITEMS.register("ilarmorleggings",
            ()->new iLightningArmorLeggings(ItemMaterial.ArmorIL, ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<Item> ILArmorBoots = ITEMS.register("ilarmorboots",
            ()->new iLightningArmorBoots(ItemMaterial.ArmorIL, ArmorItem.Type.BOOTS));
    public static final RegistryObject<Item> LightningRecallSoul = ITEMS.register("lightningrecallsoul",
            ()->new LightningRecallSoul(new Item.Properties().rarity(Utils.Lightning)));
    public static final RegistryObject<Item> IntensifiedLightningSoul = ITEMS.register("intensifiedlightningsoul",
            ()->new IntensifiedLightningSoul(new Item.Properties().rarity(Utils.LightningBold)));
    public static final RegistryObject<Item> ArmorLightningRecall = ITEMS.register("armorlightningrecall",
            ()->new MobArmor(ItemMaterial.IslandMaterial, ArmorItem.Type.HELMET, 500, 50, 90));
    public static final RegistryObject<Item> NetherGemPiece = ITEMS.register("nethergempiece",
            ()->new NetherGemPiece(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> NetherGemPieceBag1 = ITEMS.register("nethergempiecebag1",
            ()->new NetherGemPieceBag1(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> NetherGemPieceBag2 = ITEMS.register("nethergempiecebag2",
            ()->new NetherGemPieceBag2(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> NetherGemPieceBag3 = ITEMS.register("nethergempiecebag3",
            ()->new NetherGemPieceBag3(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> NetherGemPieceBag4 = ITEMS.register("nethergempiecebag4",
            ()->new NetherGemPieceBag4(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> NetherRecallSoul = ITEMS.register("netherrecallsoul",
            ()->new RecallRuby(new Item.Properties().rarity(Utils.Nether)));
    public static final RegistryObject<Item> IntensifiedNetherRecallSoul = ITEMS.register("intensifiednetherrecallsoul",
            ()->new IntensifiedRuby(new Item.Properties().rarity(Utils.NetherBold)));
    public static final RegistryObject<Item> ArmorNetherRecall = ITEMS.register("armornetherrecall",
            ()->new MobArmor(ItemMaterial.IslandMaterial, ArmorItem.Type.HELMET, 500, 50, 90));
    public static final RegistryObject<Item> ManaSword1 = ITEMS.register("manasword1",
            ()->new ManaSword1(ItemTier.VMaterial,2,0));
    public static final RegistryObject<Item> SnowRecallSoul = ITEMS.register("snowrecallsoul",
            ()->new SnowRecallSoul(new Item.Properties().rarity(Utils.Snow)));
    public static final RegistryObject<Item> IntensifiedSnowRecallSoul = ITEMS.register("intensifiedsnowrecallsoul",
            ()->new IntensifiedSnowSoul(new Item.Properties().rarity(Utils.SnowBold)));
    public static final RegistryObject<Item> ArmorSnowRecall = ITEMS.register("armorsnowrecall",
            ()->new MobArmor(ItemMaterial.IslandMaterial, ArmorItem.Type.HELMET, 500, 50, 90));
    public static final RegistryObject<Item> SnowSword4 = ITEMS.register("snowsword4",
            ()->new SnowSword4(ItemTier.MaterialForPickaxe3,2,0,new Item.Properties().rarity(Utils.MagmaItalic)));
    public static final RegistryObject<Item> ForestRecallSoul = ITEMS.register("forestrecallsoul",
            ()->new ForestRecallSoul(new Item.Properties().rarity(Utils.Forest)));
    public static final RegistryObject<Item> IntensifiedForestRecallSoul = ITEMS.register("intensifiedforestsoul",
            ()->new IntensifiedForestSoul(new Item.Properties().rarity(Utils.ForestBold)));
    public static final RegistryObject<Item> ArmorForestRecall = ITEMS.register("armorforestrecall",
            ()->new MobArmor(ItemMaterial.IslandMaterial, ArmorItem.Type.HELMET, 500, 50, 90));
    public static final RegistryObject<Item> ForestSword4 = ITEMS.register("forestsword4",
            ()->new ForestSword4(ItemTier.VMaterial,2,0,new Item.Properties().rarity(Utils.MagmaItalic)));
    public static final RegistryObject<Item> VolcanoSword4 = ITEMS.register("volcanosword4",
            ()->new VolcanoSword4(ItemTier.VMaterial,2,0));
    public static final RegistryObject<Item> IntensifiedVolcanoRecallSoul = ITEMS.register("intensifiedvolcanosoul",
            ()->new IntensifiedForestSoul(new Item.Properties().rarity(Utils.VolcanoBold)));
    public static final RegistryObject<Item> ArmorVolcanoRecall = ITEMS.register("armorvolcanorecall",
            ()->new MobArmor(ItemMaterial.IslandMaterial, ArmorItem.Type.HELMET, 500, 50, 90));
    public static final RegistryObject<Item> VolcanoRecallSoul = ITEMS.register("volcanorecallsoul",
            ()->new VolcanoRecallSoul(new Item.Properties().rarity(Utils.Volcano)));
    public static final RegistryObject<Item> ANIMATED_ITEM = ITEMS.register("animated_item",
            () -> new AnimatedItem(new Item.Properties()));
    public static final RegistryObject<Item> ID_Card = ITEMS.register("id_card",
            ()->new ID_Card(new Item.Properties().rarity(Rarity.RARE)));

    public static final RegistryObject<Item> ForestBossPocket = ITEMS.register("forest_boss_pocket",
            ()->new ForestBossPocket(new Item.Properties().rarity(Utils.ForestBold)));
    public static final RegistryObject<Item> ForestBossCore = ITEMS.register("forest_boss_core",
            ()->new ForestBossCore(new Item.Properties().rarity(Utils.ForestBold)));
    public static final RegistryObject<Item> ForestBossGem = ITEMS.register("forest_boss_gem",
            ()->new ForestBossGem(new Item.Properties().rarity(Utils.ForestBold)));
    public static final RegistryObject<Item> ForestBossCentral = ITEMS.register("forest_boss_central",
            ()->new ForestBossCentral(new Item.Properties().rarity(Utils.ForestBold)));
    public static final RegistryObject<Item> ForestBossSword = ITEMS.register("forest_boss_sword",
            ()->new ForestBossSword(ItemTier.VMaterial,2,0));

    public static final RegistryObject<Item> VolcanoBossPocket = ITEMS.register("volcano_boss_pocket",
            ()->new VolcanoBossPocket(new Item.Properties().rarity(Utils.VolcanoBold)));
    public static final RegistryObject<Item> VolcanoBossCore = ITEMS.register("volcano_boss_core",
            ()->new VolcanoBossCore(new Item.Properties().rarity(Utils.VolcanoBold)));
    public static final RegistryObject<Item> VolcanoBossGem = ITEMS.register("volcano_boss_gem",
            ()->new VolcanoBossGem(new Item.Properties().rarity(Utils.VolcanoBold)));
    public static final RegistryObject<Item> VolcanoBossCentral = ITEMS.register("volcano_boss_central",
            ()->new VolcanoBossCentral(new Item.Properties().rarity(Utils.VolcanoBold)));
    public static final RegistryObject<Item> VolcanoBossSword = ITEMS.register("volcano_boss_sword",
            ()->new VolcanoBossSword(ItemTier.VMaterial,2,0));
    public static final RegistryObject<Item> ArmorForestBoss = ITEMS.register("armor_forest_boss",
            ()->new MobArmor(ItemMaterial.MaterialForBoss, ArmorItem.Type.HELMET, StringUtils.MobName.ForestBoss));
    public static final RegistryObject<Item> ArmorVolcanoBoss = ITEMS.register("armor_volcano_boss",
            ()->new MobArmor(ItemMaterial.MaterialForArmor2, ArmorItem.Type.HELMET, StringUtils.MobName.VolcanoBoss));
    public static final RegistryObject<Item> SakuraChange = ITEMS.register("sakura_change",
            ()->new SakuraChange(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> BlockFill = ITEMS.register("block_fill",
            ()->new BlockFill(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> BlockFix = ITEMS.register("block_fix",
            ()->new BlockFix(new Item.Properties().rarity(Rarity.RARE)));

    public static final RegistryObject<Item> SakuraPetal = ITEMS.register("sakura_petal",
            ()->new SakuraPetal(new Item.Properties().rarity(Utils.SakuraBold)));
    public static final RegistryObject<Item> SakuraPocket = ITEMS.register("sakura_pocket",
            ()->new SakuraPocket(new Item.Properties().rarity(Utils.SakuraBold)));
    public static final RegistryObject<Item> SakuraReForge = ITEMS.register("sakura_reforge",
            ()->new SakuraReForge(new Item.Properties().rarity(Utils.SakuraBold)));
    public static final RegistryObject<Item> SakuraDemonSword = ITEMS.register("sakura_demon_sword",
            ()->new SakuraSword(ItemTier.VMaterial,2,0));

    public static final RegistryObject<Item> SakuraArmorHelmet = ITEMS.register("sakura_armor_helmet",
            ()->new SakuraArmorHelmet(ItemMaterial.LakeMaterialHelmet, ArmorItem.Type.HELMET));

    public static final RegistryObject<Item> Wheat = ITEMS.register("vmd_wheat",
            ()->new Wheat(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> WheatPocket = ITEMS.register("wheat_pocket",
            ()->new WheatPocket(new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> WheatReForge = ITEMS.register("wheat_reforge",
            ()->new WheatReForge(new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> WheatArmorChest = ITEMS.register("wheat_armor_chest",
            ()->new WheatArmorChest(ItemMaterial.LakeMaterialChest, ArmorItem.Type.CHESTPLATE));

    public static final RegistryObject<Item> ArmorSakuraMob = ITEMS.register("armor_sakura_mob",
            ()->new MobArmor(ItemMaterial.MaterialForBoss, ArmorItem.Type.HELMET, StringUtils.MobName.SakuraMob));

    public static final RegistryObject<Item> MinePants = ITEMS.register("mine_pants",
            ()->new MinePants(ItemMaterial.LakeMaterialChest, ArmorItem.Type.LEGGINGS));

    public static final RegistryObject<Item> MinePantsForgingDraw = ITEMS.register("mine_leggings_fd",
            ()->new ForgeDraw(new Item.Properties().rarity(Rarity.EPIC),ModItems.MinePants.get()));

    public static final RegistryObject<Item> ArmorScarecrow = ITEMS.register("armor_scare_crow",
            ()->new MobArmor(ItemMaterial.MaterialForBoss, ArmorItem.Type.HELMET, StringUtils.MobName.Scarecrow));

    public static final RegistryObject<Item> PlainBracelet = ITEMS.register("plainbracelet",
            ()->new PlainBracelet());
    public static final RegistryObject<Item> ForestBracelet = ITEMS.register("forestbracelet",
            ()->new ForestBracelet());
    public static final RegistryObject<Item> LakeBracelet = ITEMS.register("lakebracelet",
            ()->new LakeBracelet());
    public static final RegistryObject<Item> VolcanoBracelet = ITEMS.register("volcanobracelet",
            ()->new VolcanoBracelet());
    public static final RegistryObject<Item> MineBracelet = ITEMS.register("minebracelet",
            ()->new MineBracelet());
    public static final RegistryObject<Item> SnowBracelet = ITEMS.register("snowbracelet",
            ()->new SnowBracelet());
    public static final RegistryObject<Item> SkyBracelet = ITEMS.register("skybracelet",
            ()->new SkyBracelet());

    public static final RegistryObject<Item> MineArmorHelmet = ITEMS.register("minearmorhelmet",
            ()->new MineArmorHelmet(ItemMaterial.IslandMaterial, ArmorItem.Type.HELMET));
    public static final RegistryObject<Item> MineArmorChest = ITEMS.register("minearmorchest",
            ()->new MineArmorChest(ItemMaterial.IslandMaterial, ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> MineArmorLeggings = ITEMS.register("minearmorleggings",
            ()->new MineArmorLeggings(ItemMaterial.IslandMaterial, ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<Item> MineArmorBoots = ITEMS.register("minearmorboots",
            ()->new MineArmorBoots(ItemMaterial.IslandMaterial, ArmorItem.Type.BOOTS));

    public static final RegistryObject<Item> SnowArmorHelmet = ITEMS.register("snowarmorhelmet",
            ()->new SnowArmorHelmet(ItemMaterial.SkyMaterial, ArmorItem.Type.HELMET));
    public static final RegistryObject<Item> SnowArmorChest = ITEMS.register("snowarmorchest",
            ()->new SnowArmorChest(ItemMaterial.SkyMaterial, ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> SnowArmorLeggings = ITEMS.register("snowarmorleggings",
            ()->new SnowArmorLeggings(ItemMaterial.SkyMaterial, ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<Item> SnowArmorBoots = ITEMS.register("snowarmorboots",
            ()->new SnowArmorBoots(ItemMaterial.SkyMaterial, ArmorItem.Type.BOOTS));

    public static final RegistryObject<Item> LakeBossSword = ITEMS.register("lake_boss_sword",
            ()->new LakeBoss.LakeBossSword(ItemTier.VMaterial,2,0));
    public static final RegistryObject<Item> LakeBossCore = ITEMS.register("lake_boss_core",
            ()->new LakeBoss.LakeBossCore(new Item.Properties().rarity(Utils.LakeBold)));
    public static final RegistryObject<Item> LakeBossGem = ITEMS.register("lake_boss_gem",
            ()->new LakeBoss.LakeBossGem(new Item.Properties().rarity(Utils.LakeBold)));
    public static final RegistryObject<Item> LakeBossCentral = ITEMS.register("lake_boss_central",
            ()->new LakeBoss.LakeBossCentral(new Item.Properties().rarity(Utils.LakeBold)));
    public static final RegistryObject<Item> LakeBossPocket = ITEMS.register("lake_boss_pocket",
            ()->new LakeBoss.LakeBossPocket(new Item.Properties().rarity(Utils.LakeBold)));
    public static final RegistryObject<Item> ArmorLakeBoss = ITEMS.register("armor_lake_boss",
            ()->new MobArmor(ItemMaterial.MaterialForBoss, ArmorItem.Type.HELMET, StringUtils.MobName.LakeBoss));

    public static final RegistryObject<Item> SkyBossBow = ITEMS.register("sky_boss_bow",
            ()->new SkyBoss.SkyBossBow(new Item.Properties().rarity(Utils.EntropyItalic).stacksTo(1)));
    public static final RegistryObject<Item> SkyBossCore = ITEMS.register("sky_boss_core",
            ()->new SkyBoss.SkyBossCore(new Item.Properties().rarity(Utils.SkyBold)));
    public static final RegistryObject<Item> SkyBossGem = ITEMS.register("sky_boss_gem",
            ()->new SkyBoss.SkyBossGem(new Item.Properties().rarity(Utils.SkyBold)));
    public static final RegistryObject<Item> SkyBossCentral = ITEMS.register("sky_boss_central",
            ()->new SkyBoss.SkyBossCentral(new Item.Properties().rarity(Utils.SkyBold)));
    public static final RegistryObject<Item> SkyBossPocket = ITEMS.register("sky_boss_pocket",
            ()->new SkyBoss.SkyBossPocket(new Item.Properties().rarity(Utils.SkyBold)));
    public static final RegistryObject<Item> ArmorSkyBoss = ITEMS.register("armor_sky_boss",
            ()->new MobArmor(ItemMaterial.MaterialForBoss, ArmorItem.Type.HELMET, StringUtils.MobName.SkyBoss));

    public static final RegistryObject<Item> WitherSword0 = ITEMS.register("wither_sword_0",
            ()->new WitherSword(ItemTier.VMaterial,2,0,0));
    public static final RegistryObject<Item> WitherSword1 = ITEMS.register("wither_sword_1",
            ()->new WitherSword(ItemTier.VMaterial,2,0,1));
    public static final RegistryObject<Item> WitherSword2 = ITEMS.register("wither_sword_2",
            ()->new WitherSword(ItemTier.VMaterial,2,0,2));
    public static final RegistryObject<Item> WitherSword3 = ITEMS.register("wither_sword_3",
            ()->new WitherSword(ItemTier.VMaterial,2,0,3));

    public static final RegistryObject<Item> PiglinHelmet0 = ITEMS.register("piglin_helmet_0",
            ()->new PiglinHelmet(ItemMaterial.Golden, ArmorItem.Type.HELMET,0));
    public static final RegistryObject<Item> PiglinHelmet1 = ITEMS.register("piglin_helmet_1",
            ()->new PiglinHelmet(ItemMaterial.Golden, ArmorItem.Type.HELMET,1));
    public static final RegistryObject<Item> PiglinHelmet2 = ITEMS.register("piglin_helmet_2",
            ()->new PiglinHelmet(ItemMaterial.Golden, ArmorItem.Type.HELMET,2));
    public static final RegistryObject<Item> PiglinHelmet3 = ITEMS.register("piglin_helmet_3",
            ()->new PiglinHelmet(ItemMaterial.Golden, ArmorItem.Type.HELMET,3));

    public static final RegistryObject<Item> WitherBow0 = ITEMS.register("wither_bow_0",
            ()->new WitherBow(new Item.Properties().rarity(Utils.WitherItalic).stacksTo(1),0));
    public static final RegistryObject<Item> WitherBow1 = ITEMS.register("wither_bow_1",
            ()->new WitherBow(new Item.Properties().rarity(Utils.WitherItalic).stacksTo(1),1));
    public static final RegistryObject<Item> WitherBow2 = ITEMS.register("wither_bow_2",
            ()->new WitherBow(new Item.Properties().rarity(Utils.WitherItalic).stacksTo(1),2));
    public static final RegistryObject<Item> WitherBow3 = ITEMS.register("wither_bow_3",
            ()->new WitherBow(new Item.Properties().rarity(Utils.WitherItalic).stacksTo(1),3));

    public static final RegistryObject<Item> MagmaSceptre0 = ITEMS.register("magma_sceptre_0",
            ()->new MagmaSceptre(ItemTier.VMaterial,2,0, new Item.Properties().rarity(Rarity.EPIC),0));
    public static final RegistryObject<Item> MagmaSceptre1 = ITEMS.register("magma_sceptre_1",
            ()->new MagmaSceptre(ItemTier.VMaterial,2,0, new Item.Properties().rarity(Rarity.EPIC),1));
    public static final RegistryObject<Item> MagmaSceptre2 = ITEMS.register("magma_sceptre_2",
            ()->new MagmaSceptre(ItemTier.VMaterial,2,0, new Item.Properties().rarity(Rarity.EPIC),2));
    public static final RegistryObject<Item> MagmaSceptre3 = ITEMS.register("magma_sceptre_3",
            ()->new MagmaSceptre(ItemTier.VMaterial,2,0, new Item.Properties().rarity(Rarity.EPIC),3));

    public static final RegistryObject<Item> WitherRune = ITEMS.register("wither_rune",
            ()->new WitherBoneRune(new Item.Properties().rarity(Utils.WitherBold)));
    public static final RegistryObject<Item> PiglinRune = ITEMS.register("piglin_rune",
            ()->new PiglinRune(new Item.Properties().rarity(Utils.Piglin)));
    public static final RegistryObject<Item> NetherSkeletonRune = ITEMS.register("nether_rune",
            ()->new NetherBoneRune(new Item.Properties().rarity(Utils.WitherBold)));
    public static final RegistryObject<Item> MagmaRune = ITEMS.register("magma_rune",
            ()->new NetherMagmaRune(new Item.Properties().rarity(Utils.MagmaBold)));

    public static final RegistryObject<Item> ArmorRandomSlime = ITEMS.register("armor_random_slime",
            ()->new MobArmor(StringUtils.MobName.RandomSlime));

    public static final RegistryObject<Item> PlainCrest0 = ITEMS.register("plain_crest_0",
            ()->new PlainCrest(new Item.Properties().rarity(Rarity.COMMON),0));
    public static final RegistryObject<Item> PlainCrest1 = ITEMS.register("plain_crest_1",
            ()->new PlainCrest(new Item.Properties().rarity(Rarity.UNCOMMON),1));
    public static final RegistryObject<Item> PlainCrest2 = ITEMS.register("plain_crest_2",
            ()->new PlainCrest(new Item.Properties().rarity(Rarity.RARE),2));
    public static final RegistryObject<Item> PlainCrest3 = ITEMS.register("plain_crest_3",
            ()->new PlainCrest(new Item.Properties().rarity(Rarity.EPIC),3));
    public static final RegistryObject<Item> PlainCrest4 = ITEMS.register("plain_crest_4",
            ()->new PlainCrest(new Item.Properties().rarity(Utils.Red),4));

    public static final RegistryObject<Item> ForestCrest0 = ITEMS.register("forest_crest_0",
            ()->new ForestCrest(new Item.Properties().rarity(Rarity.COMMON),0));
    public static final RegistryObject<Item> ForestCrest1 = ITEMS.register("forest_crest_1",
            ()->new ForestCrest(new Item.Properties().rarity(Rarity.UNCOMMON),1));
    public static final RegistryObject<Item> ForestCrest2 = ITEMS.register("forest_crest_2",
            ()->new ForestCrest(new Item.Properties().rarity(Rarity.RARE),2));
    public static final RegistryObject<Item> ForestCrest3 = ITEMS.register("forest_crest_3",
            ()->new ForestCrest(new Item.Properties().rarity(Rarity.EPIC),3));
    public static final RegistryObject<Item> ForestCrest4 = ITEMS.register("forest_crest_4",
            ()->new ForestCrest(new Item.Properties().rarity(Utils.Red),4));

    public static final RegistryObject<Item> LakeCrest0 = ITEMS.register("lake_crest_0",
            ()->new LakeCrest(new Item.Properties().rarity(Rarity.COMMON),0));
    public static final RegistryObject<Item> LakeCrest1 = ITEMS.register("lake_crest_1",
            ()->new LakeCrest(new Item.Properties().rarity(Rarity.UNCOMMON),1));
    public static final RegistryObject<Item> LakeCrest2 = ITEMS.register("lake_crest_2",
            ()->new LakeCrest(new Item.Properties().rarity(Rarity.RARE),2));
    public static final RegistryObject<Item> LakeCrest3 = ITEMS.register("lake_crest_3",
            ()->new LakeCrest(new Item.Properties().rarity(Rarity.EPIC),3));
    public static final RegistryObject<Item> LakeCrest4 = ITEMS.register("lake_crest_4",
            ()->new LakeCrest(new Item.Properties().rarity(Utils.Red),4));

    public static final RegistryObject<Item> VolcanoCrest0 = ITEMS.register("volcano_crest_0",
            ()->new VolcanoCrest(new Item.Properties().rarity(Rarity.COMMON),0));
    public static final RegistryObject<Item> VolcanoCrest1 = ITEMS.register("volcano_crest_1",
            ()->new VolcanoCrest(new Item.Properties().rarity(Rarity.UNCOMMON),1));
    public static final RegistryObject<Item> VolcanoCrest2 = ITEMS.register("volcano_crest_2",
            ()->new VolcanoCrest(new Item.Properties().rarity(Rarity.RARE),2));
    public static final RegistryObject<Item> VolcanoCrest3 = ITEMS.register("volcano_crest_3",
            ()->new VolcanoCrest(new Item.Properties().rarity(Rarity.EPIC),3));
    public static final RegistryObject<Item> VolcanoCrest4 = ITEMS.register("volcano_crest_4",
            ()->new VolcanoCrest(new Item.Properties().rarity(Utils.Red),4));

    public static final RegistryObject<Item> MineCrest0 = ITEMS.register("mine_crest_0",
            ()->new MineCrest(new Item.Properties().rarity(Rarity.COMMON),0));
    public static final RegistryObject<Item> MineCrest1 = ITEMS.register("mine_crest_1",
            ()->new MineCrest(new Item.Properties().rarity(Rarity.UNCOMMON),1));
    public static final RegistryObject<Item> MineCrest2 = ITEMS.register("mine_crest_2",
            ()->new MineCrest(new Item.Properties().rarity(Rarity.RARE),2));
    public static final RegistryObject<Item> MineCrest3 = ITEMS.register("mine_crest_3",
            ()->new MineCrest(new Item.Properties().rarity(Rarity.EPIC),3));
    public static final RegistryObject<Item> MineCrest4 = ITEMS.register("mine_crest_4",
            ()->new MineCrest(new Item.Properties().rarity(Utils.Red),4));

    public static final RegistryObject<Item> SnowCrest0 = ITEMS.register("snow_crest_0",
            ()->new SnowCrest(new Item.Properties().rarity(Rarity.COMMON),0));
    public static final RegistryObject<Item> SnowCrest1 = ITEMS.register("snow_crest_1",
            ()->new SnowCrest(new Item.Properties().rarity(Rarity.UNCOMMON),1));
    public static final RegistryObject<Item> SnowCrest2 = ITEMS.register("snow_crest_2",
            ()->new SnowCrest(new Item.Properties().rarity(Rarity.RARE),2));
    public static final RegistryObject<Item> SnowCrest3 = ITEMS.register("snow_crest_3",
            ()->new SnowCrest(new Item.Properties().rarity(Rarity.EPIC),3));
    public static final RegistryObject<Item> SnowCrest4 = ITEMS.register("snow_crest_4",
            ()->new SnowCrest(new Item.Properties().rarity(Utils.Red),4));

    public static final RegistryObject<Item> SkyCrest0 = ITEMS.register("sky_crest_0",
            ()->new SkyCrest(new Item.Properties().rarity(Rarity.COMMON),0));
    public static final RegistryObject<Item> SkyCrest1 = ITEMS.register("sky_crest_1",
            ()->new SkyCrest(new Item.Properties().rarity(Rarity.UNCOMMON),1));
    public static final RegistryObject<Item> SkyCrest2 = ITEMS.register("sky_crest_2",
            ()->new SkyCrest(new Item.Properties().rarity(Rarity.RARE),2));
    public static final RegistryObject<Item> SkyCrest3 = ITEMS.register("sky_crest_3",
            ()->new SkyCrest(new Item.Properties().rarity(Rarity.EPIC),3));
    public static final RegistryObject<Item> SkyCrest4 = ITEMS.register("sky_crest_4",
            ()->new SkyCrest(new Item.Properties().rarity(Utils.Red),4));

    public static final RegistryObject<Item> ManaCrest0 = ITEMS.register("mana_crest_0",
            ()->new ManaCrest(new Item.Properties().rarity(Rarity.COMMON),0));
    public static final RegistryObject<Item> ManaCrest1 = ITEMS.register("mana_crest_1",
            ()->new ManaCrest(new Item.Properties().rarity(Rarity.UNCOMMON),1));
    public static final RegistryObject<Item> ManaCrest2 = ITEMS.register("mana_crest_2",
            ()->new ManaCrest(new Item.Properties().rarity(Rarity.RARE),2));
    public static final RegistryObject<Item> ManaCrest3 = ITEMS.register("mana_crest_3",
            ()->new ManaCrest(new Item.Properties().rarity(Rarity.EPIC),3));
    public static final RegistryObject<Item> ManaCrest4 = ITEMS.register("mana_crest_4",
            ()->new ManaCrest(new Item.Properties().rarity(Utils.Red),4));

    public static final RegistryObject<Item> SnowBossArmorChest = ITEMS.register("snow_boss_armor_chest",
            ()->new SnowBoss.SnowBossArmorChest(ItemMaterial.SkyMaterial, ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> SnowBossCore = ITEMS.register("snow_boss_core",
            ()->new SnowBoss.SnowBossCore(new Item.Properties().rarity(Utils.SnowBold)));
    public static final RegistryObject<Item> SnowBossGem = ITEMS.register("snow_boss_gem",
            ()->new SnowBoss.SnowBossGem(new Item.Properties().rarity(Utils.SnowBold)));
    public static final RegistryObject<Item> SnowBossCentral = ITEMS.register("snow_boss_central",
            ()->new SnowBoss.SnowBossCentral(new Item.Properties().rarity(Utils.SnowBold)));
    public static final RegistryObject<Item> SnowBossPocket = ITEMS.register("snow_boss_pocket",
            ()->new SnowBoss.SnowBossPocket(new Item.Properties().rarity(Utils.SnowBold)));
    public static final RegistryObject<Item> ArmorSnowBoss = ITEMS.register("armor_snow_boss",
            ()->new MobArmor(ItemMaterial.MaterialForBoss, ArmorItem.Type.HELMET, StringUtils.MobName.SnowBoss));

    public static final RegistryObject<Item> SpongeClear = ITEMS.register("sponge_clear",
            ()->new SpongeClear(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> PillarsSet = ITEMS.register("pillars_set",
            ()->new PillarsSet(new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> ArmorBoss2 = ITEMS.register("armor_boss2",
            ()->new MobArmor(StringUtils.MobName.Boss2));


    public static final RegistryObject<Item> Spawn2 = ITEMS.register("spawn2",
            ()->new Spawn2(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> Boss2Piece = ITEMS.register("boss_2_piece",
            ()->new Boss2Piece(new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> Dismantle = ITEMS.register("dismantle",
            ()->new Dismantle(new Item.Properties().rarity(Rarity.RARE)));

    public static final RegistryObject<Item> ManaNote_Plain = ITEMS.register("mananote_plain",
            ()->new ManaNote(new Item.Properties().rarity(Utils.PlainItalic),0,
                    40,0.1f,50,10,3,0.05f,0.1f));

    public static final RegistryObject<Item> ManaNote_Forest = ITEMS.register("mananote_forest",
            ()->new ManaNote(new Item.Properties().rarity(Utils.ForestItalic),1,
                    80,0.12f,75,15,4,0.1f,0.2f));

    public static final RegistryObject<Item> ManaNote_Lake = ITEMS.register("mananote_lake",
            ()->new ManaNote(new Item.Properties().rarity(Utils.LakeItalic),2,
                    120,0.14f,100,20,6,0.15f,0.3f));

    public static final RegistryObject<Item> ManaNote_Volcano = ITEMS.register("mananote_volcano",
            ()->new ManaNote(new Item.Properties().rarity(Utils.VolcanoItalic),3,
                    160,0.17f,125,25,8,0.20d,0.4f));

    public static final RegistryObject<Item> ManaNote_Snow = ITEMS.register("mananote_snow",
            ()->new ManaNote(new Item.Properties().rarity(Utils.SnowItalic),4,
                    200,0.2f,150,30,10,0.25f,0.5f));

    public static final RegistryObject<Item> EvokerBook0 = ITEMS.register("evokerbook0",
            ()->new ManaNote(new Item.Properties().rarity(Utils.EvokerItalic),5,
                    240,0.22f,200,35,12,0.3f,0.6f));

    public static final RegistryObject<Item> EvokerBook1 = ITEMS.register("evokerbook1",
            ()->new ManaNote(new Item.Properties().rarity(Utils.EvokerItalic),6,
                    280,0.24f,300,40,14,0.35f,0.7f));

    public static final RegistryObject<Item> EvokerBook2 = ITEMS.register("evokerbook2",
            ()->new ManaNote(new Item.Properties().rarity(Utils.EvokerItalic),7,
                    320,0.26f,400,45,16,0.4f,0.8f));

    public static final RegistryObject<Item> EvokerBook3 = ITEMS.register("evokerbook3",
            ()->new ManaNote(new Item.Properties().rarity(Utils.EvokerItalic),8,
                    360,0.28f,500,50,18,0.45f,0.9f));

    public static final RegistryObject<Item> WorldSoul1 = ITEMS.register("worldsoul1",
            ()->new Item(new Item.Properties().rarity(Utils.WorldBold)));

    public static final RegistryObject<Item> WorldSoul2 = ITEMS.register("worldsoul2",
            ()->new Item(new Item.Properties().rarity(Utils.WorldBold)));

    public static final RegistryObject<Item> WorldSoul3 = ITEMS.register("worldsoul3",
            ()->new Item(new Item.Properties().rarity(Utils.WorldBold)));

    public static final RegistryObject<Item> WorldSoul4 = ITEMS.register("worldsoul4",
            ()->new Item(new Item.Properties().rarity(Utils.WorldBold)));

    public static final RegistryObject<Item> WorldSoul5 = ITEMS.register("worldsoul5",
            ()->new Item(new Item.Properties().rarity(Utils.WorldBold)));

    public static final RegistryObject<Item> SoulBow = ITEMS.register("soulbow",
            ()->new SoulBow(new Item.Properties().stacksTo(1).rarity(Utils.WorldBold)));

    public static final RegistryObject<Item> SoulSword = ITEMS.register("soulsword",
            ()->new SoulSword(ItemTier.VMaterial,2,0,new Item.Properties().rarity(Utils.WorldBold)));

    public static final RegistryObject<Item> SoulSceptre = ITEMS.register("soulsceptre",
            ()->new SoulSceptre(ItemTier.VMaterial,2,0,new Item.Properties().rarity(Utils.WorldBold)));

    public static final RegistryObject<Item> WorldSoulNote = ITEMS.register("world_soul_note",
            ()->new WorldSoulNote(new Item.Properties().rarity(Utils.WorldItalic)));

    public static final RegistryObject<Item> InjectNote = ITEMS.register("inject_note",
            ()->new InjectNote(new Item.Properties().rarity(Utils.InjectItalic)));

    public static final RegistryObject<Item> SkillReset = ITEMS.register("skill_reset",
            ()->new SkillReset(new Item.Properties().rarity(Utils.WorldItalic)));

    public static final RegistryObject<Item> TpToSky = ITEMS.register("tp_to_sky",
            ()->new TeleportToSky(new Item.Properties().rarity(Utils.WorldItalic)));

    public static final RegistryObject<Item> TpToSakura = ITEMS.register("tp_to_sakura",
            ()->new TeleportToSakuraIsland(new Item.Properties().rarity(Utils.WorldItalic)));

    public static final RegistryObject<Item> PlainPower = ITEMS.register("plain_power",
            ()->new PlainPower(new Item.Properties().rarity(Utils.PlainItalic),0));
    public static final RegistryObject<Item> PlainPower1 = ITEMS.register("plain_power1",
            ()->new PlainPower(new Item.Properties().rarity(Utils.PlainItalic),1));
    public static final RegistryObject<Item> PlainPower2 = ITEMS.register("plain_power2",
            ()->new PlainPower(new Item.Properties().rarity(Utils.PlainItalic),2));
    public static final RegistryObject<Item> PlainPower3 = ITEMS.register("plain_power3",
            ()->new PlainPower(new Item.Properties().rarity(Utils.PlainItalic),3));


    public static final RegistryObject<Item> ForestPower = ITEMS.register("forest_power",
            ()->new ForestPower(new Item.Properties().rarity(Utils.ForestItalic),0));
    public static final RegistryObject<Item> ForestPower1 = ITEMS.register("forest_power1",
            ()->new ForestPower(new Item.Properties().rarity(Utils.ForestItalic),1));
    public static final RegistryObject<Item> ForestPower2 = ITEMS.register("forest_power2",
            ()->new ForestPower(new Item.Properties().rarity(Utils.ForestItalic),2));
    public static final RegistryObject<Item> ForestPower3 = ITEMS.register("forest_power3",
            ()->new ForestPower(new Item.Properties().rarity(Utils.ForestItalic),3));

    public static final RegistryObject<Item> LakePower = ITEMS.register("lake_power",
            ()->new LakePower(new Item.Properties().rarity(Utils.LakeItalic),0));
    public static final RegistryObject<Item> LakePower1 = ITEMS.register("lake_power1",
            ()->new LakePower(new Item.Properties().rarity(Utils.LakeItalic),1));
    public static final RegistryObject<Item> LakePower2 = ITEMS.register("lake_power2",
            ()->new LakePower(new Item.Properties().rarity(Utils.LakeItalic),2));
    public static final RegistryObject<Item> LakePower3 = ITEMS.register("lake_power3",
            ()->new LakePower(new Item.Properties().rarity(Utils.LakeItalic),3));

    public static final RegistryObject<Item> VolcanoPower = ITEMS.register("volcano_power",
            ()->new VolcanoPower(new Item.Properties().rarity(Utils.VolcanoItalic),0));
    public static final RegistryObject<Item> VolcanoPower1 = ITEMS.register("volcano_power1",
            ()->new VolcanoPower(new Item.Properties().rarity(Utils.VolcanoItalic),1));
    public static final RegistryObject<Item> VolcanoPower2 = ITEMS.register("volcano_power2",
            ()->new VolcanoPower(new Item.Properties().rarity(Utils.VolcanoItalic),2));
    public static final RegistryObject<Item> VolcanoPower3 = ITEMS.register("volcano_power3",
            ()->new VolcanoPower(new Item.Properties().rarity(Utils.VolcanoItalic),3));

    public static final RegistryObject<Item> SnowPower = ITEMS.register("snow_power",
            ()->new SnowPower(new Item.Properties().rarity(Utils.SnowItalic),0));
    public static final RegistryObject<Item> SnowPower1 = ITEMS.register("snow_power1",
            ()->new SnowPower(new Item.Properties().rarity(Utils.SnowItalic),1));
    public static final RegistryObject<Item> SnowPower2 = ITEMS.register("snow_power2",
            ()->new SnowPower(new Item.Properties().rarity(Utils.SnowItalic),2));
    public static final RegistryObject<Item> SnowPower3 = ITEMS.register("snow_power3",
            ()->new SnowPower(new Item.Properties().rarity(Utils.SnowItalic),3));

    public static final RegistryObject<Item> SakuraSwordForgeDraw = ITEMS.register("sakura_sword_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.SakuraBold),ModItems.SakuraDemonSword.get()));

    public static final RegistryObject<Item> IslandHelmetForgeDraw = ITEMS.register("island_helmet_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.LightningBold),ModItems.IslandArmorHelmet.get()));

    public static final RegistryObject<Item> IslandChestForgeDraw = ITEMS.register("island_chest_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.LightningBold),ModItems.IslandArmorChest.get()));

    public static final RegistryObject<Item> IslandLeggingsForgeDraw = ITEMS.register("island_leggings_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.LightningBold),ModItems.IslandArmorLeggings.get()));

    public static final RegistryObject<Item> IslandBootsForgeDraw = ITEMS.register("island_boots_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.LightningBold),ModItems.IslandArmorBoots.get()));

    public static final RegistryObject<Item> SkyHelmetForgeDraw = ITEMS.register("sky_helmet_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.SkyBold),ModItems.SkyArmorHelmet.get()));

    public static final RegistryObject<Item> SkyChestForgeDraw = ITEMS.register("sky_chest_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.SkyBold),ModItems.SkyArmorChest.get()));

    public static final RegistryObject<Item> SkyLeggingsForgeDraw = ITEMS.register("sky_leggings_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.SkyBold),ModItems.SkyArmorLeggings.get()));

    public static final RegistryObject<Item> SkyBootsForgeDraw = ITEMS.register("sky_boots_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.SkyBold),ModItems.SkyArmorBoots.get()));

    public static final RegistryObject<Item> SeaSwordForgeDraw = ITEMS.register("sea_sword_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.SeaBold),ModItems.SeaSword0.get()));

    public static final RegistryObject<Item> BlackForestSwordForgeDraw = ITEMS.register("blackforest_sword_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.BlackForestBold),ModItems.BlackForestSword0.get()));

    public static final RegistryObject<Item> NetherHelmetForgeDraw = ITEMS.register("nether_helmet_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.NetherBold),ModItems.NetherArmorHelmet.get()));

    public static final RegistryObject<Item> NetherChestForgeDraw = ITEMS.register("nether_chest_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.NetherBold),ModItems.NetherArmorChest.get()));

    public static final RegistryObject<Item> NetherLeggingsForgeDraw = ITEMS.register("nether_leggings_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.NetherBold),ModItems.NetherArmorLeggings.get()));

    public static final RegistryObject<Item> NetherBootsForgeDraw = ITEMS.register("nether_boots_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.NetherBold),ModItems.NetherArmorBoots.get()));

    public static final RegistryObject<Item> KazeSwordForgeDraw = ITEMS.register("kaze_sword_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.KazeBold),ModItems.KazeSword0.get()));

    public static final RegistryObject<Item> KazeBootsForgeDraw = ITEMS.register("kaze_boots_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.KazeBold),ModItems.KazeBoots.get()));

    public static final RegistryObject<Item> ForestSword4ForgeDraw = ITEMS.register("forest_sword4_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.ForestBold),ModItems.ForestBossSword.get()));

    public static final RegistryObject<Item> VolcanoSword4ForgeDraw = ITEMS.register("volcano_sword4_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.VolcanoBold),ModItems.VolcanoBossSword.get()));

    public static final RegistryObject<Item> LakeSword4ForgeDraw = ITEMS.register("lake_sword4_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.LakeBold),ModItems.LakeBossSword.get()));

    public static final RegistryObject<Item> SkyBossBowForgeDraw = ITEMS.register("sky_boss_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.SkyBold),ModItems.SkyBossBow.get()));

    public static final RegistryObject<Item> SnowBossChestForgeDraw = ITEMS.register("snow_boss_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.SnowBold),ModItems.SnowBossArmorChest.get()));


    public static final RegistryObject<Item> PlainSoulBag = ITEMS.register("plain_soul_bag",
            ()->new SoulBag(new Item.Properties().rarity(Utils.PlainBold),ModItems.PlainSoul.get()));

    public static final RegistryObject<Item> ForestSoulBag = ITEMS.register("forest_soul_bag",
            ()->new SoulBag(new Item.Properties().rarity(Utils.ForestBold),ModItems.ForestSoul.get()));

    public static final RegistryObject<Item> VolcanoSoulBag = ITEMS.register("volcano_soul_bag",
            ()->new SoulBag(new Item.Properties().rarity(Utils.VolcanoBold),ModItems.VolcanoSoul.get()));

    public static final RegistryObject<Item> LakeSoulBag = ITEMS.register("lake_soul_bag",
            ()->new SoulBag(new Item.Properties().rarity(Utils.LakeBold),ModItems.LakeSoul.get()));

    public static final RegistryObject<Item> ArmorPlainBossHelmet = ITEMS.register("armor_plain_boss_helmet",
            ()->new MobArmor(ItemMaterial.ArmorKaze, ArmorItem.Type.HELMET,StringUtils.MobName.PlainBoss));

    public static final RegistryObject<Item> ArmorPlainBossChest = ITEMS.register("armor_plain_boss_chest",
            ()->new MobArmor(ItemMaterial.ArmorKaze, ArmorItem.Type.CHESTPLATE,StringUtils.MobName.PlainBoss));

    public static final RegistryObject<Item> ArmorPlainBossLeggings = ITEMS.register("armor_plain_boss_leggings",
            ()->new MobArmor(ItemMaterial.ArmorKaze, ArmorItem.Type.LEGGINGS,StringUtils.MobName.PlainBoss));

    public static final RegistryObject<Item> ArmorPlainBossBoots = ITEMS.register("armor_plain_boss_boots",
            ()->new MobArmor(ItemMaterial.ArmorKaze, ArmorItem.Type.BOOTS,StringUtils.MobName.PlainBoss));

    public static final RegistryObject<Item> ArmorMain1Boss = ITEMS.register("armor_main1boss",
            ()->new MobArmor(StringUtils.MobName.Main1Boss));

    public static final RegistryObject<Item> PlainAttackRing0 = ITEMS.register("plain_attack_ring0",
            ()->new PlainAttackRing(new Item.Properties().rarity(Utils.PlainItalic),0));

    public static final RegistryObject<Item> PlainAttackRing1 = ITEMS.register("plain_attack_ring1",
            ()->new PlainAttackRing(new Item.Properties().rarity(Utils.PlainItalic),1));

    public static final RegistryObject<Item> PlainAttackRing2 = ITEMS.register("plain_attack_ring2",
            ()->new PlainAttackRing(new Item.Properties().rarity(Utils.PlainItalic),2));

    public static final RegistryObject<Item> PlainAttackRing3 = ITEMS.register("plain_attack_ring3",
            ()->new PlainAttackRing(new Item.Properties().rarity(Utils.PlainItalic),3));

    public static final RegistryObject<Item> PlainManaAttackRing0 = ITEMS.register("plain_manaattack_ring0",
            ()->new PlainManaAttackRing(new Item.Properties().rarity(Utils.PlainItalic),0));

    public static final RegistryObject<Item> PlainManaAttackRing1 = ITEMS.register("plain_manaattack_ring1",
            ()->new PlainManaAttackRing(new Item.Properties().rarity(Utils.PlainItalic),1));

    public static final RegistryObject<Item> PlainManaAttackRing2 = ITEMS.register("plain_manaattack_ring2",
            ()->new PlainManaAttackRing(new Item.Properties().rarity(Utils.PlainItalic),2));

    public static final RegistryObject<Item> PlainManaAttackRing3 = ITEMS.register("plain_manaattack_ring3",
            ()->new PlainManaAttackRing(new Item.Properties().rarity(Utils.PlainItalic),3));

    public static final RegistryObject<Item> PlainHealthRing0 = ITEMS.register("plain_health_ring0",
            ()->new PlainHealthRing(new Item.Properties().rarity(Utils.PlainItalic),0));

    public static final RegistryObject<Item> PlainHealthRing1 = ITEMS.register("plain_health_ring1",
            ()->new PlainHealthRing(new Item.Properties().rarity(Utils.PlainItalic),1));

    public static final RegistryObject<Item> PlainHealthRing2 = ITEMS.register("plain_health_ring2",
            ()->new PlainHealthRing(new Item.Properties().rarity(Utils.PlainItalic),2));

    public static final RegistryObject<Item> PlainHealthRing3 = ITEMS.register("plain_health_ring3",
            ()->new PlainHealthRing(new Item.Properties().rarity(Utils.PlainItalic),3));

    public static final RegistryObject<Item> PlainDefenceRing0 = ITEMS.register("plain_defence_ring0",
            ()->new PlainDefenceRing(new Item.Properties().rarity(Utils.PlainItalic),0));

    public static final RegistryObject<Item> PlainDefenceRing1 = ITEMS.register("plain_defence_ring1",
            ()->new PlainDefenceRing(new Item.Properties().rarity(Utils.PlainItalic),1));

    public static final RegistryObject<Item> PlainDefenceRing2 = ITEMS.register("plain_defence_ring2",
            ()->new PlainDefenceRing(new Item.Properties().rarity(Utils.PlainItalic),2));

    public static final RegistryObject<Item> PlainDefenceRing3 = ITEMS.register("plain_defence_ring3",
            ()->new PlainDefenceRing(new Item.Properties().rarity(Utils.PlainItalic),3));

    public static final RegistryObject<Item> PlainBossSoul = ITEMS.register("plain_boss_soul",
            ()->new Item(new Item.Properties().rarity(Utils.PlainBold)));

    public static final RegistryObject<Item> Ps_Bottle0 = ITEMS.register("ps_bottle0",
            ()->new PsBottle(new Item.Properties().rarity(Rarity.UNCOMMON),20));

    public static final RegistryObject<Item> Ps_Bottle1 = ITEMS.register("ps_bottle1",
            ()->new PsBottle(new Item.Properties().rarity(Rarity.UNCOMMON),50));

    public static final RegistryObject<Item> Ps_Bottle2 = ITEMS.register("ps_bottle2",
            ()->new PsBottle(new Item.Properties().rarity(Rarity.UNCOMMON),100));

    public static final RegistryObject<Item> SeaManaCore = ITEMS.register("sea_mana_core",
            ()->new SeaCore(new Item.Properties().rarity(Utils.SeaBold)));

    public static final RegistryObject<Item> BlackForestManaCore = ITEMS.register("blackforest_mana_core",
            ()->new BlackForestCore(new Item.Properties().rarity(Utils.BlackForestBold)));

    public static final RegistryObject<Item> KazeManaCore = ITEMS.register("kaze_mana_core",
            ()->new KazeCore(new Item.Properties().rarity(Utils.KazeBold)));

    public static final RegistryObject<Item> SeaManaCoreForgeDraw = ITEMS.register("sea_mana_core_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.SeaBold),ModItems.SeaManaCore.get()));

    public static final RegistryObject<Item> BlackForestManaCoreForgeDraw = ITEMS.register("blackforest_mana_core_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.BlackForestBold),ModItems.BlackForestManaCore.get()));

    public static final RegistryObject<Item> KazeManaCoreForgeDraw = ITEMS.register("kaze_mana_core_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.KazeBold),ModItems.KazeManaCore.get()));

    public static final RegistryObject<Item> PlainZombieKillPaper = ITEMS.register("plainzombie_killpaper",
            ()->new KillPaper(new Item.Properties().rarity(Rarity.EPIC),StringUtils.MobName.PlainZombie));

    public static final RegistryObject<Item> ForestSkeletonKillPaper = ITEMS.register("forestskeleton_killpaper",
            ()->new KillPaper(new Item.Properties().rarity(Rarity.EPIC),StringUtils.MobName.ForestSkeleton));

    public static final RegistryObject<Item> ForestZombieKillPaper = ITEMS.register("forestzombie_killpaper",
            ()->new KillPaper(new Item.Properties().rarity(Rarity.EPIC),StringUtils.MobName.ForestZombie));

    public static final RegistryObject<Item> FieldZombieKillPaper = ITEMS.register("fieldzombie_killpaper",
            ()->new KillPaper(new Item.Properties().rarity(Rarity.EPIC),StringUtils.MobName.FieldZombie));

    public static final RegistryObject<Item> LakeDrownedKillPaper = ITEMS.register("lakedrowned_killpaper",
            ()->new KillPaper(new Item.Properties().rarity(Rarity.EPIC),StringUtils.MobName.LakeDrowned));

    public static final RegistryObject<Item> VolcanoBlazeKillPaper = ITEMS.register("volcanoblaze_killpaper",
            ()->new KillPaper(new Item.Properties().rarity(Rarity.EPIC),StringUtils.MobName.VolcanoBlaze));

    public static final RegistryObject<Item> MineZombieKillPaper = ITEMS.register("minezombie_killpaper",
            ()->new KillPaper(new Item.Properties().rarity(Rarity.EPIC),StringUtils.MobName.MineZombie));

    public static final RegistryObject<Item> MineSkeletonKillPaper = ITEMS.register("mineskeleton_killpaper",
            ()->new KillPaper(new Item.Properties().rarity(Rarity.EPIC),StringUtils.MobName.MineSkeleton));

    public static final RegistryObject<Item> SnowStrayKillPaper = ITEMS.register("snowstray_killpaper",
            ()->new KillPaper(new Item.Properties().rarity(Rarity.EPIC),StringUtils.MobName.SnowStray));

    public static final RegistryObject<Item> SkyVexKillPaper = ITEMS.register("skyvex_killpaper",
            ()->new KillPaper(new Item.Properties().rarity(Rarity.EPIC),StringUtils.MobName.SkyVex));

    public static final RegistryObject<Item> EvokerKillPaper = ITEMS.register("evoker_killpaper",
            ()->new KillPaper(new Item.Properties().rarity(Rarity.EPIC),StringUtils.MobName.Evoker));

    public static final RegistryObject<Item> EvokerMasterKillPaper = ITEMS.register("evokermaster_killpaper",
            ()->new KillPaper(new Item.Properties().rarity(Rarity.EPIC),StringUtils.MobName.EvokerMaster));

    public static final RegistryObject<Item> SeaGuardianKillPaper = ITEMS.register("seaguardian_killpaper",
            ()->new KillPaper(new Item.Properties().rarity(Rarity.EPIC),StringUtils.MobName.SeaGuardian));

    public static final RegistryObject<Item> LightingZombieKillPaper = ITEMS.register("lightingzombie_killpaper",
            ()->new KillPaper(new Item.Properties().rarity(Rarity.EPIC),StringUtils.MobName.LightingZombie));

    public static final RegistryObject<Item> HuskKillPaper = ITEMS.register("husk_killpaper",
            ()->new KillPaper(new Item.Properties().rarity(Rarity.EPIC),StringUtils.MobName.Husk));

    public static final RegistryObject<Item> KazeZombieKillPaper = ITEMS.register("kazezombie_killpaper",
            ()->new KillPaper(new Item.Properties().rarity(Rarity.EPIC),StringUtils.MobName.KazeZombie));

    public static final RegistryObject<Item> SpiderKillPaper = ITEMS.register("spider_killpaper",
            ()->new KillPaper(new Item.Properties().rarity(Rarity.EPIC),StringUtils.MobName.Spider));

    public static final RegistryObject<Item> SilverFishKillPaper = ITEMS.register("silverfish_killpaper",
            ()->new KillPaper(new Item.Properties().rarity(Rarity.EPIC),StringUtils.MobName.SilverFish));

    public static final RegistryObject<Item> WitherSkeletonKillPaper = ITEMS.register("witherskeleton_killpaper",
            ()->new KillPaper(new Item.Properties().rarity(Rarity.EPIC),StringUtils.MobName.WitherSkeleton));

    public static final RegistryObject<Item> PiglinKillPaper = ITEMS.register("piglin_killpaper",
            ()->new KillPaper(new Item.Properties().rarity(Rarity.EPIC),StringUtils.MobName.Piglin));

    public static final RegistryObject<Item> NetherSkeletonKillPaper = ITEMS.register("netherskeleton_killpaper",
            ()->new KillPaper(new Item.Properties().rarity(Rarity.EPIC),StringUtils.MobName.NetherSkeleton));

    public static final RegistryObject<Item> NetherMagmaKillPaper = ITEMS.register("nethermagma_killpaper",
            ()->new KillPaper(new Item.Properties().rarity(Rarity.EPIC),StringUtils.MobName.NetherMagma));

    public static final RegistryObject<Item> EnderManKillPaper = ITEMS.register("enderman_killpaper",
            ()->new KillPaper(new Item.Properties().rarity(Rarity.EPIC),StringUtils.MobName.EnderMan));

    public static final RegistryObject<Item> SakuraMobKillPaper = ITEMS.register("sakuramob_killpaper",
            ()->new KillPaper(new Item.Properties().rarity(Rarity.EPIC),StringUtils.MobName.SakuraMob));

    public static final RegistryObject<Item> ScarecrowKillPaper = ITEMS.register("scarecrow_killpaper",
            ()->new KillPaper(new Item.Properties().rarity(Rarity.EPIC),StringUtils.MobName.Scarecrow));

    public static final RegistryObject<Item> QuiverBag = ITEMS.register("quiver_bag",
            ()->new Item(new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> SakuraBow = ITEMS.register("sakura_bow",
            ()->new SakuraBow(new Item.Properties().rarity(Utils.SakuraItalic)));

    public static final RegistryObject<Item> SakuraCore = ITEMS.register("sakura_mana_core",
            ()->new SakuraCore(new Item.Properties().rarity(Utils.SakuraBold)));

    public static final RegistryObject<Item> SakuraBowForgingDraw = ITEMS.register("sakura_bow_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.SakuraBold),ModItems.SakuraBow.get()));

    public static final RegistryObject<Item> SakuraCoreForgingDraw = ITEMS.register("sakura_mana_core_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.SakuraBold),ModItems.SakuraCore.get()));

    public static final RegistryObject<Item> MobArmorPurpleIronHelmet = ITEMS.register("mob_armor_purple_iron_helmet",
            ()->new MobArmor(ItemMaterial.ArmorPurpleIron, ArmorItem.Type.HELMET, StringUtils.MobName.MineWorker));

    public static final RegistryObject<Item> MobArmorPurpleIronChest = ITEMS.register("mob_armor_purple_iron_chest",
            ()->new MobArmor(ItemMaterial.ArmorPurpleIron, ArmorItem.Type.CHESTPLATE, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> MobArmorPurpleIronLeggings = ITEMS.register("mob_armor_purple_iron_leggings",
            ()->new MobArmor(ItemMaterial.ArmorPurpleIron, ArmorItem.Type.LEGGINGS, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> MobArmorPurpleIronBoots = ITEMS.register("mob_armor_purple_iron_boots",
            ()->new MobArmor(ItemMaterial.ArmorPurpleIron, ArmorItem.Type.BOOTS, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> PurpleIron = ITEMS.register("purpleiron",
            ()->new Item(new Item.Properties().rarity(Utils.PurpleIron)));

    public static final RegistryObject<Item> PurpleIronPiece = ITEMS.register("purpleiron_piece",
            ()->new Item(new Item.Properties().rarity(Utils.PurpleIron)));

    public static final RegistryObject<Item> PurpleIronPickaxe0 = ITEMS.register("purpleiron_pickaxe0",
            ()->new PurplePickaxe(ItemTier.MaterialForPurplePickaxe0,new Item.Properties().rarity(Utils.PurpleIronItalic),0));

    public static final RegistryObject<Item> PurpleIronPickaxe1 = ITEMS.register("purpleiron_pickaxe1",
            ()->new PurplePickaxe(ItemTier.MaterialForPurplePickaxe1,new Item.Properties().rarity(Utils.PurpleIronItalic),1));

    public static final RegistryObject<Item> PurpleIronPickaxe2 = ITEMS.register("purpleiron_pickaxe2",
            ()->new PurplePickaxe(ItemTier.MaterialForPurplePickaxe2,new Item.Properties().rarity(Utils.PurpleIronItalic),2));

    public static final RegistryObject<Item> PurpleIronPickaxe3 = ITEMS.register("purpleiron_pickaxe3",
            ()->new PurplePickaxe(ItemTier.MaterialForPurplePickaxe3,new Item.Properties().rarity(Utils.PurpleIronItalic),3));

    public static final RegistryObject<Item> PurpleIronArmorHelmet = ITEMS.register("purpleiron_armor_helmet",
            ()->new PurpleArmor(ItemMaterial.ArmorPurpleIron, ArmorItem.Type.HELMET,new Item.Properties().rarity(Utils.PurpleIronItalic),0));

    public static final RegistryObject<Item> PurpleIronArmorChest = ITEMS.register("purpleiron_armor_chest",
            ()->new PurpleArmor(ItemMaterial.ArmorPurpleIron, ArmorItem.Type.CHESTPLATE,new Item.Properties().rarity(Utils.PurpleIronItalic),1));

    public static final RegistryObject<Item> PurpleIronArmorLeggings = ITEMS.register("purpleiron_armor_leggings",
            ()->new PurpleArmor(ItemMaterial.ArmorPurpleIron, ArmorItem.Type.LEGGINGS,new Item.Properties().rarity(Utils.PurpleIronItalic),2));

    public static final RegistryObject<Item> PurpleIronArmorBoots = ITEMS.register("purpleiron_armor_boots",
            ()->new PurpleArmor(ItemMaterial.ArmorPurpleIron, ArmorItem.Type.BOOTS,new Item.Properties().rarity(Utils.PurpleIronItalic),3));

    public static final RegistryObject<Item> IceArmorHelmet = ITEMS.register("ice_armor_helmet",
            ()->new IceArmor(ItemMaterial.ArmorIce, ArmorItem.Type.HELMET,new Item.Properties().rarity(Utils.IceItalic),0));

    public static final RegistryObject<Item> IceArmorChest = ITEMS.register("ice_armor_chest",
            ()->new IceArmor(ItemMaterial.ArmorIce, ArmorItem.Type.CHESTPLATE,new Item.Properties().rarity(Utils.IceItalic),1));

    public static final RegistryObject<Item> IceArmorLeggings = ITEMS.register("ice_armor_leggings",
            ()->new IceArmor(ItemMaterial.ArmorIce, ArmorItem.Type.LEGGINGS,new Item.Properties().rarity(Utils.IceItalic),2));

    public static final RegistryObject<Item> IceArmorBoots = ITEMS.register("ice_armor_boots",
            ()->new IceArmor(ItemMaterial.ArmorIce, ArmorItem.Type.BOOTS,new Item.Properties().rarity(Utils.IceItalic),3));

    public static final RegistryObject<Item> IceBook = ITEMS.register("ice_book",
            ()->new IceBook(new Item.Properties().rarity(Utils.IceItalic)));

    public static final RegistryObject<Item> IceBracelet = ITEMS.register("ice_bracelet",
            ()->new IceBracelet(new Item.Properties().rarity(Utils.IceItalic)));

    public static final RegistryObject<Item> MobArmorIceHelmet = ITEMS.register("mob_armor_ice_helmet",
            ()->new MobArmor(ItemMaterial.ArmorIce, ArmorItem.Type.HELMET, StringUtils.MobName.IceKnight));

    public static final RegistryObject<Item> MobArmorIceChest = ITEMS.register("mob_armor_ice_chest",
            ()->new MobArmor(ItemMaterial.ArmorIce, ArmorItem.Type.CHESTPLATE, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> MobArmorIceLeggings = ITEMS.register("mob_armor_ice_leggings",
            ()->new MobArmor(ItemMaterial.ArmorIce, ArmorItem.Type.LEGGINGS, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> MobArmorIceBoots = ITEMS.register("mob_armor_ice_boots",
            ()->new MobArmor(ItemMaterial.ArmorIce, ArmorItem.Type.BOOTS, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> IceSoul = ITEMS.register("ice_soul",
            ()->new Item(new Item.Properties().rarity(Utils.IceBold)));

    public static final RegistryObject<Item> IceBarrierSet = ITEMS.register("ice_barrier_set",
            ()->new IceBarrierSet(new Item.Properties().rarity(Utils.IceBold)));

    public static final RegistryObject<Item> CompleteGem = ITEMS.register("complete_gem",
            ()->new Item(new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> PlainCompleteGem = ITEMS.register("plain_complete_gem",
            ()->new Item(new Item.Properties().rarity(Utils.PlainBold)));

    public static final RegistryObject<Item> IceCompleteGem = ITEMS.register("ice_complete_gem",
            ()->new Item(new Item.Properties().rarity(Utils.IceBold)));

    public static final RegistryObject<Item> ReputationMedal = ITEMS.register("reputation_medal",
            ()->new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> IceHelmetForgeDraw = ITEMS.register("ice_helmet_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.IceBold),ModItems.IceArmorHelmet.get()));

    public static final RegistryObject<Item> IceChestForgeDraw = ITEMS.register("ice_chest_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.IceBold),ModItems.IceArmorChest.get()));

    public static final RegistryObject<Item> IceLeggingsForgeDraw = ITEMS.register("ice_leggings_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.IceBold),ModItems.IceArmorLeggings.get()));

    public static final RegistryObject<Item> IceBootsForgeDraw = ITEMS.register("ice_boots_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.IceBold),ModItems.IceArmorBoots.get()));

    public static final RegistryObject<Item> FantasyMedal = ITEMS.register("fantasy_medal",
            ()->new FantasyMedal(new Item.Properties().rarity(Utils.FantasyBold)));

    public static final RegistryObject<Item> FantasyBracelet = ITEMS.register("fantasy_bracelet",
            ()->new FantasyBracelet(new Item.Properties().rarity(Utils.FantasyBold)));

    public static final RegistryObject<Item> CommonLotteries = ITEMS.register("common_lotteries",
            ()->new CommonLotteries(new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> UnCommonLotteries = ITEMS.register("uncommon_lotteries",
            ()->new UnCommonLotteries(new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> RevelationBook = ITEMS.register("revelation_book",
            ()->new RevelationBook(new Item.Properties().rarity(Utils.Fantasy)));

    public static final RegistryObject<Item> LeatherArmorHelmet = ITEMS.register("leather_armor_helmet",
            ()->new LeatherArmor(ItemMaterial.ArmorLeather, ArmorItem.Type.HELMET,0));

    public static final RegistryObject<Item> LeatherArmorChest = ITEMS.register("leather_armor_chest",
            ()->new LeatherArmor(ItemMaterial.ArmorLeather, ArmorItem.Type.CHESTPLATE,1));

    public static final RegistryObject<Item> LeatherArmorLeggings = ITEMS.register("leather_armor_leggings",
            ()->new LeatherArmor(ItemMaterial.ArmorLeather, ArmorItem.Type.LEGGINGS,2));

    public static final RegistryObject<Item> LeatherArmorBoots = ITEMS.register("leather_armor_boots",
            ()->new LeatherArmor(ItemMaterial.ArmorLeather, ArmorItem.Type.BOOTS,3));

    public static final RegistryObject<Item> LeatherSoul = ITEMS.register("leather_soul",
            ()->new Item(new Item.Properties().rarity(Utils.Ice)));

    public static final RegistryObject<Item> LeatherRune = ITEMS.register("leather_rune",
            ()->new Item(new Item.Properties().rarity(Utils.IceBold)));

    public static final RegistryObject<Item> MobArmorIceHunterHelmet = ITEMS.register("mob_armor_ice_hunter_helmet",
            ()->new MobArmor(ItemMaterial.ArmorLeather, ArmorItem.Type.HELMET, StringUtils.MobName.IceHunter));

    public static final RegistryObject<Item> MobArmorIceHunterChest = ITEMS.register("mob_armor_ice_hunter_chest",
            ()->new MobArmor(ItemMaterial.ArmorLeather, ArmorItem.Type.CHESTPLATE, StringUtils.MobName.IceHunter));

    public static final RegistryObject<Item> MobArmorIceHunterLeggings = ITEMS.register("mob_armor_ice_hunter_leggings",
            ()->new MobArmor(ItemMaterial.ArmorLeather, ArmorItem.Type.LEGGINGS, StringUtils.MobName.IceHunter));

    public static final RegistryObject<Item> MobArmorIceHunterBoots = ITEMS.register("mob_armor_ice_hunter_boots",
            ()->new MobArmor(ItemMaterial.ArmorLeather, ArmorItem.Type.BOOTS, StringUtils.MobName.IceHunter));

    public static final RegistryObject<Item> OreSoul = ITEMS.register("ore_soul",
            ()->new Item(new Item.Properties().rarity(Utils.Mine)));

    public static final RegistryObject<Item> OreRune = ITEMS.register("ore_rune",
            ()->new Item(new Item.Properties().rarity(Utils.MineBold)));

    public static final RegistryObject<Item> Value = ITEMS.register("value",
            ()->new Item(new Item.Properties().rarity(Utils.MineBold).stacksTo(1)));

    public static final RegistryObject<Item> SeaBow = ITEMS.register("sea_bow",
            ()->new SeaBow(new Item.Properties().rarity(Utils.SeaBold)));

    public static final RegistryObject<Item> SeaBowForgeDraw = ITEMS.register("sea_bow_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.SeaBold),ModItems.SeaBow.get()));

    public static final RegistryObject<Item> CrudeCoal = ITEMS.register("crude_coal",
            ()->new Item(new Item.Properties().rarity(Utils.Plain)));

    public static final RegistryObject<Item> HotCoal = ITEMS.register("hot_coal",
            ()->new Item(new Item.Properties().rarity(Utils.Volcano)));

    public static final RegistryObject<Item> RefiningCoal = ITEMS.register("refining_coal",
            ()->new Item(new Item.Properties().rarity(Utils.Lake)));

    public static final RegistryObject<Item> BlazeCoal = ITEMS.register("blaze_coal",
            ()->new Item(new Item.Properties().rarity(Utils.Quartz)));

    public static final RegistryObject<Item> CrudeIron = ITEMS.register("crude_iron",
            ()->new Item(new Item.Properties().rarity(Utils.Plain)));

    public static final RegistryObject<Item> HotIron = ITEMS.register("hot_iron",
            ()->new Item(new Item.Properties().rarity(Utils.Volcano)));

    public static final RegistryObject<Item> RefiningIron = ITEMS.register("refining_iron",
            ()->new Item(new Item.Properties().rarity(Utils.Lake)));

    public static final RegistryObject<Item> CrudeCopper = ITEMS.register("crude_copper",
            ()->new Item(new Item.Properties().rarity(Utils.Plain)));

    public static final RegistryObject<Item> HotCopper = ITEMS.register("hot_copper",
            ()->new Item(new Item.Properties().rarity(Utils.Volcano)));

    public static final RegistryObject<Item> RefiningCopper = ITEMS.register("refining_copper",
            ()->new Item(new Item.Properties().rarity(Utils.Lake)));

    public static final RegistryObject<Item> CrudeGold= ITEMS.register("crude_gold",
            ()->new Item(new Item.Properties().rarity(Utils.Plain)));

    public static final RegistryObject<Item> BlazeGold = ITEMS.register("blaze_gold",
            ()->new Item(new Item.Properties().rarity(Utils.Quartz)));

    public static final RegistryObject<Item> RefiningGold = ITEMS.register("refining_gold",
            ()->new Item(new Item.Properties().rarity(Utils.Lake)));

    public static final RegistryObject<Item> NaturalCore = ITEMS.register("natural_core",
            ()->new Item(new Item.Properties().rarity(Utils.Life)));

    public static final RegistryObject<Item> MineBow0 = ITEMS.register("minebow0",
            ()->new MineBow(new Item.Properties().stacksTo(1).rarity(Utils.MineItalic),0));
    public static final RegistryObject<Item> MineBow1 = ITEMS.register("minebow1",
            ()->new MineBow(new Item.Properties().stacksTo(1).rarity(Utils.MineItalic),1));
    public static final RegistryObject<Item> MineBow2 = ITEMS.register("minebow2",
            ()->new MineBow(new Item.Properties().stacksTo(1).rarity(Utils.MineItalic),2));
    public static final RegistryObject<Item> MineBow3 = ITEMS.register("minebow3",
            ()->new MineBow(new Item.Properties().stacksTo(1).rarity(Utils.MineItalic),3));

    public static final RegistryObject<Item> IceSword = ITEMS.register("ice_sword",
            ()->new IceSword(ItemTier.VMaterial,2,0));

    public static final RegistryObject<Item> IceBow = ITEMS.register("ice_bow",
            ()->new IceBow(new Item.Properties().stacksTo(1).rarity(Utils.IceItalic)));

    public static final RegistryObject<Item> IceSceptre = ITEMS.register("ice_sceptre",
            ()->new IceSceptre(ItemTier.VMaterial,2,0, new Item.Properties().rarity(Utils.IceItalic),0));

    public static final RegistryObject<Item> IceSwordForgeDraw = ITEMS.register("ice_sword_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.IceBold),ModItems.IceSword.get()));

    public static final RegistryObject<Item> IceBowForgeDraw = ITEMS.register("ice_bow_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.IceBold),ModItems.IceBow.get()));

    public static final RegistryObject<Item> IceSceptreForgeDraw = ITEMS.register("ice_sceptre_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.IceBold),ModItems.IceSceptre.get()));

    public static final RegistryObject<Item> LifeManaArmorHelmetE = ITEMS.register("life_armor_helmet_e",
            ()->new LifeMana1(ItemMaterial.ArmorLifeE, ArmorItem.Type.HELMET,new Item.Properties().rarity(Utils.LifeItalic),0));

    public static final RegistryObject<Item> LifeManaArmorChestE = ITEMS.register("life_armor_chest_e",
            ()->new LifeMana1(ItemMaterial.ArmorLifeE, ArmorItem.Type.CHESTPLATE,new Item.Properties().rarity(Utils.LifeItalic),1));

    public static final RegistryObject<Item> LifeManaArmorLeggingsE = ITEMS.register("life_armor_leggings_e",
            ()->new LifeMana1(ItemMaterial.ArmorLifeE, ArmorItem.Type.LEGGINGS,new Item.Properties().rarity(Utils.LifeItalic),2));

    public static final RegistryObject<Item> LifeManaArmorBootsE = ITEMS.register("life_armor_boots_e",
            ()->new LifeMana1(ItemMaterial.ArmorLifeE, ArmorItem.Type.BOOTS,new Item.Properties().rarity(Utils.LifeItalic),3));

    public static final RegistryObject<Item> ObsiManaArmorHelmetE = ITEMS.register("obsi_armor_helmet_e",
            ()->new ObsiMana1(ItemMaterial.ArmorObsiE, ArmorItem.Type.HELMET,new Item.Properties().rarity(Utils.EvokerItalic),0));

    public static final RegistryObject<Item> ObsiManaArmorChestE = ITEMS.register("obsi_armor_chest_e",
            ()->new ObsiMana1(ItemMaterial.ArmorObsiE, ArmorItem.Type.CHESTPLATE,new Item.Properties().rarity(Utils.EvokerItalic),1));

    public static final RegistryObject<Item> ObsiManaArmorLeggingsE = ITEMS.register("obsi_armor_leggings_e",
            ()->new ObsiMana1(ItemMaterial.ArmorObsiE, ArmorItem.Type.LEGGINGS,new Item.Properties().rarity(Utils.EvokerItalic),2));

    public static final RegistryObject<Item> ObsiManaArmorBootsE = ITEMS.register("obsi_armor_boots_e",
            ()->new ObsiMana1(ItemMaterial.ArmorObsiE, ArmorItem.Type.BOOTS,new Item.Properties().rarity(Utils.EvokerItalic),3));

    public static final RegistryObject<Item> MineShield = ITEMS.register("mine_shield",
            ()->new MineShield());

    public static final RegistryObject<Item> U_Disk = ITEMS.register("u_disk",
            ()->new U_Disk(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> ShipSword = ITEMS.register("ship_sword",
            ()->new ShipSword(ItemTier.VMaterial,2,0));

    public static final RegistryObject<Item> ShipBow = ITEMS.register("ship_bow",
            ()->new ShipBow(new Item.Properties().stacksTo(1).rarity(Utils.ShipItalic)));

    public static final RegistryObject<Item> ShipSceptre = ITEMS.register("ship_sceptre",
            ()->new ShipSceptre(ItemTier.VMaterial,2,0, new Item.Properties().rarity(Utils.ShipItalic),0));

    public static final RegistryObject<Item> ShipPiece = ITEMS.register("ship_piece",
            ()->new Item(new Item.Properties().rarity(Utils.ShipBold)));

    public static final RegistryObject<Item> MobArmorShipHelmet = ITEMS.register("mob_armor_ship_helmet",
            ()->new MobArmor(ItemMaterial.ArmorPurpleIron, ArmorItem.Type.HELMET, StringUtils.MobName.ShipWorker));

    public static final RegistryObject<Item> MobArmorShipChest = ITEMS.register("mob_armor_ship_chest",
            ()->new MobArmor(ItemMaterial.ArmorPurpleIron, ArmorItem.Type.CHESTPLATE, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> MobArmorShipLeggings = ITEMS.register("mob_armor_ship_leggings",
            ()->new MobArmor(ItemMaterial.ArmorPurpleIron, ArmorItem.Type.LEGGINGS, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> MobArmorShipBoots = ITEMS.register("mob_armor_ship_boots",
            ()->new MobArmor(ItemMaterial.ArmorPurpleIron, ArmorItem.Type.BOOTS, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> ShipSwordForgeDraw = ITEMS.register("ship_sword_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.ShipBold),ModItems.ShipSword.get()));

    public static final RegistryObject<Item> ShipBowForgeDraw = ITEMS.register("ship_bow_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.ShipBold),ModItems.ShipBow.get()));

    public static final RegistryObject<Item> ShipSceptreForgeDraw = ITEMS.register("ship_sceptre_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.ShipBold),ModItems.ShipSceptre.get()));

    public static final RegistryObject<Item> IceLoot = ITEMS.register("ice_loot",
            ()->new IceLoot(new Item.Properties().rarity(Utils.Ice)));

    public static final RegistryObject<Item> MineHat = ITEMS.register("mine_hat",
            ()->new MineHat(ItemMaterial.SkyMaterial, ArmorItem.Type.HELMET));

    public static final RegistryObject<Item> ForgeImprove0 = ITEMS.register("forge_improve_0",
            ()->new ForgeImprove(new Item.Properties().rarity(Utils.Plain),0));

    public static final RegistryObject<Item> ForgeImprove1 = ITEMS.register("forge_improve_1",
            ()->new ForgeImprove(new Item.Properties().rarity(Utils.Sky),1));

    public static final RegistryObject<Item> ForgeImprove2 = ITEMS.register("forge_improve_2",
            ()->new ForgeImprove(new Item.Properties().rarity(Utils.End),2));

    public static final RegistryObject<Item> ForgeEnhance0 = ITEMS.register("forge_enhance_0",
            ()->new ForgeEnhance(new Item.Properties().rarity(Utils.Plain),0));

    public static final RegistryObject<Item> ForgeEnhance1 = ITEMS.register("forge_enhance_1",
            ()->new ForgeEnhance(new Item.Properties().rarity(Utils.Sky),1));

    public static final RegistryObject<Item> ForgeEnhance2 = ITEMS.register("forge_enhance_2",
            ()->new ForgeEnhance(new Item.Properties().rarity(Utils.End),2));

    public static final RegistryObject<Item> ForgeProtect = ITEMS.register("forge_protect",
            ()->new ForgeProtect(new Item.Properties().rarity(Utils.End)));

    public static final RegistryObject<Item> NetherSceptre = ITEMS.register("nether_sceptre",
            ()->new NetherSceptre(ItemTier.VMaterial,2,0, new Item.Properties().rarity(Utils.ShipItalic),0));

    public static final RegistryObject<Item> NetherManaArmorHelmet = ITEMS.register("nether_mana_helmet",
            ()->new NetherManaArmor(ItemMaterial.NetherMana, ArmorItem.Type.HELMET,new Item.Properties().rarity(Utils.NetherItalic),0));

    public static final RegistryObject<Item> NetherManaArmorChest = ITEMS.register("nether_mana_chest",
            ()->new NetherManaArmor(ItemMaterial.NetherMana, ArmorItem.Type.CHESTPLATE,new Item.Properties().rarity(Utils.NetherItalic),1));

    public static final RegistryObject<Item> NetherManaArmorLeggings = ITEMS.register("nether_mana_leggings",
            ()->new NetherManaArmor(ItemMaterial.NetherMana, ArmorItem.Type.LEGGINGS,new Item.Properties().rarity(Utils.NetherItalic),2));

    public static final RegistryObject<Item> NetherManaArmorBoots = ITEMS.register("nether_mana_boots",
            ()->new NetherManaArmor(ItemMaterial.NetherMana, ArmorItem.Type.BOOTS,new Item.Properties().rarity(Utils.NetherItalic),3));

    public static final RegistryObject<Item> NetherSceptreForgeDraw = ITEMS.register("nether_sceptre_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.NetherBold),ModItems.NetherSceptre.get()));

    public static final RegistryObject<Item> NetherManaHelmetForgeDraw = ITEMS.register("nether_mana_helmet_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.NetherBold),ModItems.NetherManaArmorHelmet.get()));

    public static final RegistryObject<Item> NetherManaChestForgeDraw = ITEMS.register("nether_mana_chest_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.NetherBold),ModItems.NetherManaArmorChest.get()));

    public static final RegistryObject<Item> NetherManaLeggingsForgeDraw = ITEMS.register("nether_mana_leggings_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.NetherBold),ModItems.NetherManaArmorLeggings.get()));

    public static final RegistryObject<Item> NetherManaBootsForgeDraw = ITEMS.register("nether_mana_boots_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.NetherBold),ModItems.NetherManaArmorBoots.get()));

    public static final RegistryObject<Item> FireWorkGun = ITEMS.register("fire_work_gun",
            ()->new FireWorkGun(new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> SpringRing0 = ITEMS.register("spring_ring0",
            ()->new SpringRing(new Item.Properties().rarity(Utils.SpringBold),0));

    public static final RegistryObject<Item> SpringRing1 = ITEMS.register("spring_ring1",
            ()->new SpringRing(new Item.Properties().rarity(Utils.SpringBold),1));

    public static final RegistryObject<Item> SpringRing2 = ITEMS.register("spring_ring2",
            ()->new SpringRing(new Item.Properties().rarity(Utils.SpringBold),2));

    public static final RegistryObject<Item> SpringRing3 = ITEMS.register("spring_ring3",
            ()->new SpringRing(new Item.Properties().rarity(Utils.SpringBold),3));

    public static final RegistryObject<Item> SpringMoney = ITEMS.register("spring_money",
            ()->new Item(new Item.Properties().rarity(Utils.SpringBold)));

    public static final RegistryObject<Item> RedEnvelope = ITEMS.register("red_envelope",
            ()->new RedEnvelope(new Item.Properties().rarity(Utils.SpringBold)));

    public static final RegistryObject<Item> IceHeart = ITEMS.register("ice_heart",
            ()->new Item(new Item.Properties().rarity(Utils.IceBold)));

    public static final RegistryObject<Item> WoodenStake0 = ITEMS.register("wooden_stake0",
            ()->new MobArmor(ItemMaterial.MaterialForBoss, ArmorItem.Type.HELMET, 100,100,20));

    public static final RegistryObject<Item> WoodenStake1 = ITEMS.register("wooden_stake1",
            ()->new MobArmor(ItemMaterial.MaterialForBoss, ArmorItem.Type.HELMET, 400,400,40));

    public static final RegistryObject<Item> WoodenStake2 = ITEMS.register("wooden_stake2",
            ()->new MobArmor(ItemMaterial.MaterialForBoss, ArmorItem.Type.HELMET, 600,600,60));

    public static final RegistryObject<Item> WoodenStake3 = ITEMS.register("wooden_stake3",
            ()->new MobArmor(ItemMaterial.MaterialForBoss, ArmorItem.Type.HELMET, 900,900,80));

    public static final RegistryObject<Item> WoodenStake4 = ITEMS.register("wooden_stake4",
            ()->new MobArmor(ItemMaterial.MaterialForBoss, ArmorItem.Type.HELMET, 1200,1200,100));

    public static final RegistryObject<Item> WoodenStake5 = ITEMS.register("wooden_stake5",
            ()->new MobArmor(ItemMaterial.MaterialForBoss, ArmorItem.Type.HELMET, 1600,1600,120));

    public static final RegistryObject<Item> SpringHand0 = ITEMS.register("spring_hand0",
            ()->new SpringHand(new Item.Properties().rarity(Utils.SpringBold),0));

    public static final RegistryObject<Item> SpringHand1 = ITEMS.register("spring_hand1",
            ()->new SpringHand(new Item.Properties().rarity(Utils.SpringBold),1));

    public static final RegistryObject<Item> SpringHand2 = ITEMS.register("spring_hand2",
            ()->new SpringHand(new Item.Properties().rarity(Utils.SpringBold),2));

    public static final RegistryObject<Item> SpringHand3 = ITEMS.register("spring_hand3",
            ()->new SpringHand(new Item.Properties().rarity(Utils.SpringBold),3));

    public static final RegistryObject<Item> SpringBelt0 = ITEMS.register("spring_belt0",
            ()->new SpringBelt(new Item.Properties().rarity(Utils.SpringBold),0));

    public static final RegistryObject<Item> SpringBelt1 = ITEMS.register("spring_belt1",
            ()->new SpringBelt(new Item.Properties().rarity(Utils.SpringBold),1));

    public static final RegistryObject<Item> SpringBelt2 = ITEMS.register("spring_belt2",
            ()->new SpringBelt(new Item.Properties().rarity(Utils.SpringBold),2));

    public static final RegistryObject<Item> SpringBelt3 = ITEMS.register("spring_belt3",
            ()->new SpringBelt(new Item.Properties().rarity(Utils.SpringBold),3));

    public static final RegistryObject<Item> SpringNecklace0 = ITEMS.register("spring_necklace0",
            ()->new SpringNecklace(new Item.Properties().rarity(Utils.SpringBold),0));

    public static final RegistryObject<Item> SpringNecklace1 = ITEMS.register("spring_necklace1",
            ()->new SpringNecklace(new Item.Properties().rarity(Utils.SpringBold),1));

    public static final RegistryObject<Item> SpringNecklace2 = ITEMS.register("spring_necklace2",
            ()->new SpringNecklace(new Item.Properties().rarity(Utils.SpringBold),2));

    public static final RegistryObject<Item> SpringNecklace3 = ITEMS.register("spring_necklace3",
            ()->new SpringNecklace(new Item.Properties().rarity(Utils.SpringBold),3));

    public static final RegistryObject<Item> SpringGoldCoin = ITEMS.register("spring_gold_coin",
            ()->new Item(new Item.Properties().rarity(Utils.SpringBold)));

    public static final RegistryObject<Item> MobArmorSpringHelmet = ITEMS.register("mob_armor_spring_helmet",
            ()->new MobArmor(ItemMaterial.Spring, ArmorItem.Type.HELMET, StringUtils.MobName.SpringMob));

    public static final RegistryObject<Item> MobArmorSpringChest = ITEMS.register("mob_armor_spring_chest",
            ()->new MobArmor(ItemMaterial.Spring, ArmorItem.Type.CHESTPLATE, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> MobArmorSpringLeggings = ITEMS.register("mob_armor_spring_leggings",
            ()->new MobArmor(ItemMaterial.Spring, ArmorItem.Type.LEGGINGS, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> MobArmorSpringBoots = ITEMS.register("mob_armor_spring_boots",
            ()->new MobArmor(ItemMaterial.Spring, ArmorItem.Type.BOOTS, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> SpringAttackArmorHelmet = ITEMS.register("spring_attack_helmet",
            ()->new SpringAttackArmor(ItemMaterial.Spring, ArmorItem.Type.HELMET,new Item.Properties().rarity(Utils.SpringItalic),0));

    public static final RegistryObject<Item> SpringAttackArmorChest = ITEMS.register("spring_attack_chest",
            ()->new SpringAttackArmor(ItemMaterial.Spring, ArmorItem.Type.CHESTPLATE,new Item.Properties().rarity(Utils.SpringItalic),1));

    public static final RegistryObject<Item> SpringAttackArmorLeggings = ITEMS.register("spring_attack_leggings",
            ()->new SpringAttackArmor(ItemMaterial.Spring, ArmorItem.Type.LEGGINGS,new Item.Properties().rarity(Utils.SpringItalic),2));

    public static final RegistryObject<Item> SpringAttackArmorBoots = ITEMS.register("spring_attack_boots",
            ()->new SpringAttackArmor(ItemMaterial.Spring, ArmorItem.Type.BOOTS,new Item.Properties().rarity(Utils.SpringItalic),3));

    public static final RegistryObject<Item> SpringSwiftArmorHelmet = ITEMS.register("spring_swift_helmet",
            ()->new SpringSwiftArmor(ItemMaterial.Spring, ArmorItem.Type.HELMET,new Item.Properties().rarity(Utils.SpringItalic),0));

    public static final RegistryObject<Item> SpringSwiftArmorChest = ITEMS.register("spring_swift_chest",
            ()->new SpringSwiftArmor(ItemMaterial.Spring, ArmorItem.Type.CHESTPLATE,new Item.Properties().rarity(Utils.SpringItalic),1));

    public static final RegistryObject<Item> SpringSwiftArmorLeggings = ITEMS.register("spring_swift_leggings",
            ()->new SpringSwiftArmor(ItemMaterial.Spring, ArmorItem.Type.LEGGINGS,new Item.Properties().rarity(Utils.SpringItalic),2));

    public static final RegistryObject<Item> SpringSwiftArmorBoots = ITEMS.register("spring_swift_boots",
            ()->new SpringSwiftArmor(ItemMaterial.Spring, ArmorItem.Type.BOOTS,new Item.Properties().rarity(Utils.SpringItalic),3));

    public static final RegistryObject<Item> SpringManaArmorHelmet = ITEMS.register("spring_mana_helmet",
            ()->new SpringManaArmor(ItemMaterial.Spring, ArmorItem.Type.HELMET,new Item.Properties().rarity(Utils.SpringItalic),0));

    public static final RegistryObject<Item> SpringManaArmorChest = ITEMS.register("spring_mana_chest",
            ()->new SpringManaArmor(ItemMaterial.Spring, ArmorItem.Type.CHESTPLATE,new Item.Properties().rarity(Utils.SpringItalic),1));

    public static final RegistryObject<Item> SpringManaArmorLeggings = ITEMS.register("spring_mana_leggings",
            ()->new SpringManaArmor(ItemMaterial.Spring, ArmorItem.Type.LEGGINGS,new Item.Properties().rarity(Utils.SpringItalic),2));

    public static final RegistryObject<Item> SpringManaArmorBoots = ITEMS.register("spring_mana_boots",
            ()->new SpringManaArmor(ItemMaterial.Spring, ArmorItem.Type.BOOTS,new Item.Properties().rarity(Utils.SpringItalic),3));

    public static final RegistryObject<Item> SpringAttackHelmetForgeDraw = ITEMS.register("spring_attack_helmet_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.SpringBold),ModItems.SpringAttackArmorHelmet.get()));

    public static final RegistryObject<Item> SpringAttackChestForgeDraw = ITEMS.register("spring_attack_chest_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.SpringBold),ModItems.SpringAttackArmorChest.get()));

    public static final RegistryObject<Item> SpringAttackLeggingsForgeDraw = ITEMS.register("spring_attack_leggings_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.SpringBold),ModItems.SpringAttackArmorLeggings.get()));

    public static final RegistryObject<Item> SpringAttackBootsForgeDraw = ITEMS.register("spring_attack_boots_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.SpringBold),ModItems.SpringAttackArmorBoots.get()));

    public static final RegistryObject<Item> SpringSwiftHelmetForgeDraw = ITEMS.register("spring_swift_helmet_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.SpringBold),ModItems.SpringSwiftArmorHelmet.get()));

    public static final RegistryObject<Item> SpringSwiftChestForgeDraw = ITEMS.register("spring_swift_chest_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.SpringBold),ModItems.SpringSwiftArmorChest.get()));

    public static final RegistryObject<Item> SpringSwiftLeggingsForgeDraw = ITEMS.register("spring_swift_leggings_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.SpringBold),ModItems.SpringSwiftArmorLeggings.get()));

    public static final RegistryObject<Item> SpringSwiftBootsForgeDraw = ITEMS.register("spring_swift_boots_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.SpringBold),ModItems.SpringSwiftArmorBoots.get()));

    public static final RegistryObject<Item> SpringManaHelmetForgeDraw = ITEMS.register("spring_mana_helmet_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.SpringBold),ModItems.SpringManaArmorHelmet.get()));

    public static final RegistryObject<Item> SpringManaChestForgeDraw = ITEMS.register("spring_mana_chest_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.SpringBold),ModItems.SpringManaArmorChest.get()));

    public static final RegistryObject<Item> SpringManaLeggingsForgeDraw = ITEMS.register("spring_mana_leggings_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.SpringBold),ModItems.SpringManaArmorLeggings.get()));

    public static final RegistryObject<Item> SpringManaBootsForgeDraw = ITEMS.register("spring_mana_boots_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.SpringBold),ModItems.SpringManaArmorBoots.get()));

    public static final RegistryObject<Item> SpringHeart = ITEMS.register("spring_heart",
            ()->new Item(new Item.Properties().rarity(Utils.SpringBold)));

    public static final RegistryObject<Item> SpringSoul = ITEMS.register("spring_soul",
            ()->new Item(new Item.Properties().rarity(Utils.SpringBold)));

    public static final RegistryObject<Item> SpringLoot = ITEMS.register("spring_loot",
            ()->new SpringLoot(new Item.Properties().rarity(Utils.SpringBold)));

    public static final RegistryObject<Item> FireCracker = ITEMS.register("fire_cracker",
            ()->new FireCracker(new Item.Properties().rarity(Utils.Spring)));

    public static final RegistryObject<Item> SpringPiece = ITEMS.register("spring_piece",
            ()->new Item(new Item.Properties().rarity(Utils.Spring)));

    public static final RegistryObject<Item> SpringScale0 = ITEMS.register("spring_scale0",
            ()->new SpringScale(new Item.Properties().rarity(Utils.SpringBold),0));

    public static final RegistryObject<Item> SpringScale1 = ITEMS.register("spring_scale1",
            ()->new SpringScale(new Item.Properties().rarity(Utils.SpringBold),1));

    public static final RegistryObject<Item> SpringScale2 = ITEMS.register("spring_scale2",
            ()->new SpringScale(new Item.Properties().rarity(Utils.SpringBold),2));

    public static final RegistryObject<Item> SpringScale3 = ITEMS.register("spring_scale3",
            ()->new SpringScale(new Item.Properties().rarity(Utils.SpringBold),3));

    public static final RegistryObject<Item> EndRune0 = ITEMS.register("end_rune0",
            ()->new EndRune0(new Item.Properties().rarity(Utils.End)));

    public static final RegistryObject<Item> EndRune1 = ITEMS.register("end_rune1",
            ()->new EndRune1(new Item.Properties().rarity(Utils.End)));

    public static final RegistryObject<Item> EndRune2 = ITEMS.register("end_rune2",
            ()->new EndRune2(new Item.Properties().rarity(Utils.End)));

    public static final RegistryObject<Item> EndRune3 = ITEMS.register("end_rune3",
            ()->new EndRune3(new Item.Properties().rarity(Utils.End)));

    public static final RegistryObject<Item> LXYZOSword = ITEMS.register("lxyzo_sword",
            ()->new LXYZO_Sword(ItemTier.VMaterial,2,0));

    public static final RegistryObject<Item> LXYZOSwordPaper = ITEMS.register("lxyzo_sword_paper",
            ()->new CustomizePaper(new Item.Properties().rarity(Utils.SpringBold),LXYZOSword.get()));

    public static final RegistryObject<Item> MineWorkerKillPaper = ITEMS.register("mineworker_killpaper",
            ()->new KillPaper(new Item.Properties().rarity(Rarity.EPIC),StringUtils.MobName.MineWorker));

    public static final RegistryObject<Item> ShipWorkerKillPaper = ITEMS.register("shipworker_killpaper",
            ()->new KillPaper(new Item.Properties().rarity(Rarity.EPIC),StringUtils.MobName.ShipWorker));

    public static final RegistryObject<Item> IceHunterKillPaper = ITEMS.register("icehunter_killpaper",
            ()->new KillPaper(new Item.Properties().rarity(Rarity.EPIC),StringUtils.MobName.IceHunter));

    public static final RegistryObject<Item> ShangMengLi_Sceptre = ITEMS.register("shangmengli_sceptre",
            ()->new ShangMengLiSceptre(ItemTier.VMaterial,2,0, new Item.Properties().rarity(Utils.SeaItalic),0));

    public static final RegistryObject<Item> ShangMengLi_Sceptre1 = ITEMS.register("shangmengli_sceptre1",
            ()->new ShangMengLiSceptre(ItemTier.VMaterial,2,0, new Item.Properties().rarity(Utils.SeaItalic),0));

    public static final RegistryObject<Item> XiangLiPickaxe = ITEMS.register("xiangli_pickaxe",
            ()->new XiangLiPickaxe(ItemTier.XiangLiPickaxe,new Item.Properties().rarity(Utils.NetherItalic),0));

    public static final RegistryObject<Item> ZeusCurios = ITEMS.register("zeus_curios",
            ()->new ZeusCurios(new Item.Properties().rarity(Utils.LightningBold).stacksTo(1)));

    public static final RegistryObject<Item> ShangMengLiSceptre_Paper = ITEMS.register("shangmengli_sceptre_paper",
            ()->new CustomizePaper(new Item.Properties().rarity(Utils.SeaBold),ShangMengLi_Sceptre.get()));

    public static final RegistryObject<Item> XiangLiPickaxe_Paper = ITEMS.register("xiangli_pickaxe_paper",
            ()->new CustomizePaper(new Item.Properties().rarity(Utils.NetherBold),XiangLiPickaxe.get()));

    public static final RegistryObject<Item> Crush1CuriosPaper = ITEMS.register("crush1_curios_paper",
            ()->new CustomizePaper(new Item.Properties().rarity(Utils.LightningBold),ZeusCurios.get()));

    public static final RegistryObject<Item> Boss2AttackRing0 = ITEMS.register("boss2_attack_ring0",
            ()->new Boss2AttackRing(new Item.Properties().rarity(Utils.GoldBold),0));

    public static final RegistryObject<Item> Boss2AttackRing1 = ITEMS.register("boss2_attack_ring1",
            ()->new Boss2AttackRing(new Item.Properties().rarity(Utils.GoldBold),1));

    public static final RegistryObject<Item> Boss2AttackRing2 = ITEMS.register("boss2_attack_ring2",
            ()->new Boss2AttackRing(new Item.Properties().rarity(Utils.GoldBold),2));

    public static final RegistryObject<Item> Boss2AttackRing3 = ITEMS.register("boss2_attack_ring3",
            ()->new Boss2AttackRing(new Item.Properties().rarity(Utils.GoldBold),3));

    public static final RegistryObject<Item> Boss2ManaAttackRing0 = ITEMS.register("boss2_mana_attack_ring0",
            ()->new Boss2ManaAttackRing(new Item.Properties().rarity(Utils.GoldBold),0));

    public static final RegistryObject<Item> Boss2ManaAttackRing1 = ITEMS.register("boss2_mana_attack_ring1",
            ()->new Boss2ManaAttackRing(new Item.Properties().rarity(Utils.GoldBold),1));

    public static final RegistryObject<Item> Boss2ManaAttackRing2 = ITEMS.register("boss2_mana_attack_ring2",
            ()->new Boss2ManaAttackRing(new Item.Properties().rarity(Utils.GoldBold),2));

    public static final RegistryObject<Item> Boss2ManaAttackRing3 = ITEMS.register("boss2_mana_attack_ring3",
            ()->new Boss2ManaAttackRing(new Item.Properties().rarity(Utils.GoldBold),3));

    public static final RegistryObject<Item> Boss2DefenceRing0 = ITEMS.register("boss2_defence_ring0",
            ()->new Boss2DefenceRing(new Item.Properties().rarity(Utils.GoldBold),0));

    public static final RegistryObject<Item> Boss2DefenceRing1 = ITEMS.register("boss2_defence_ring1",
            ()->new Boss2DefenceRing(new Item.Properties().rarity(Utils.GoldBold),1));

    public static final RegistryObject<Item> Boss2DefenceRing2 = ITEMS.register("boss2_defence_ring2",
            ()->new Boss2DefenceRing(new Item.Properties().rarity(Utils.GoldBold),2));

    public static final RegistryObject<Item> Boss2DefenceRing3 = ITEMS.register("boss2_defence_ring3",
            ()->new Boss2DefenceRing(new Item.Properties().rarity(Utils.GoldBold),3));

    public static final RegistryObject<Item> Boss2HealthRing0 = ITEMS.register("boss2_health_ring0",
            ()->new Boss2HealthRing(new Item.Properties().rarity(Utils.GoldBold),0));

    public static final RegistryObject<Item> Boss2HealthRing1 = ITEMS.register("boss2_health_ring1",
            ()->new Boss2HealthRing(new Item.Properties().rarity(Utils.GoldBold),1));

    public static final RegistryObject<Item> Boss2HealthRing2 = ITEMS.register("boss2_health_ring2",
            ()->new Boss2HealthRing(new Item.Properties().rarity(Utils.GoldBold),2));

    public static final RegistryObject<Item> Boss2HealthRing3 = ITEMS.register("boss2_health_ring3",
            ()->new Boss2HealthRing(new Item.Properties().rarity(Utils.GoldBold),3));

    public static final RegistryObject<Item> MobArmorGiant = ITEMS.register("mob_armor_giant",
            ()->new MobArmor(ItemMaterial.ArmorIce, ArmorItem.Type.HELMET, StringUtils.MobName.Giant));

    public static final RegistryObject<Item> GiantTicket = ITEMS.register("giant_ticket",
            ()->new GiantTicket(new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> GiantMedal = ITEMS.register("giant_medal",
            ()->new Item(new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> CropBag = ITEMS.register("crop_bag",
            ()->new CropPackets(new Item.Properties().rarity(Utils.FieldBold)));

    public static final RegistryObject<Item> LogBag = ITEMS.register("log_bag",
            ()->new LogBag(new Item.Properties().rarity(Utils.FieldBold)));

    public static final RegistryObject<Item> ZeusSword = ITEMS.register("zeus_sword",
            ()->new CrushiSword(ItemTier.VMaterial,2,0));

    public static final RegistryObject<Item> ZeusSword1 = ITEMS.register("zeus_sword1",
            ()->new CrushiSword(ItemTier.VMaterial,2,0));

    public static final RegistryObject<Item> ZeusSwordPaper = ITEMS.register("zeus_sword_paper",
            ()->new CustomizePaper(new Item.Properties().rarity(Utils.LightningBold),ZeusSword.get()));

    public static final RegistryObject<Item> ShangMengLiCurios = ITEMS.register("shangmengli_curios",
            ()->new ShangMengLiCurios(new Item.Properties().rarity(Utils.EvokerBold).stacksTo(1)));

    public static final RegistryObject<Item> ShangMengLiCuriosPaper = ITEMS.register("shangmengli_curios_paper",
            ()->new CustomizePaper(new Item.Properties().rarity(Utils.EvokerBold),ShangMengLiCurios.get()));

    public static final RegistryObject<Item> LiuLiXianCurios = ITEMS.register("liulixian_curios",
            ()->new LiulixianCurios(new Item.Properties().rarity(Utils.SakuraBold).stacksTo(1)));

    public static final RegistryObject<Item> LiuLiXianCuriosPaper = ITEMS.register("liulixian_curios_paper",
            ()->new CustomizePaper(new Item.Properties().rarity(Utils.SakuraBold),LiuLiXianCurios.get()));

    public static final RegistryObject<Item> VeryNewCurios = ITEMS.register("very_new_curios",
            ()->new VeryNewCurios(new Item.Properties().rarity(Utils.VolcanoItalic).stacksTo(1)));

    public static final RegistryObject<Item> VeryNewCuriosPaper = ITEMS.register("very_new_curios_paper",
            ()->new CustomizePaper(new Item.Properties().rarity(Utils.VolcanoItalic),VeryNewCurios.get()));

    public static final RegistryObject<Item> ShowDickerBow = ITEMS.register("showdicker_bow",
            ()->new ShowdickerBow(new Item.Properties().stacksTo(1).rarity(Utils.IceItalic)));

    public static final RegistryObject<Item> ShowDickerBowPaper = ITEMS.register("showdicker_bow_paper",
            ()->new CustomizePaper(new Item.Properties().rarity(Utils.IceBold),ShowDickerBow.get()));

    public static final RegistryObject<Item> MobArmorBloodManaHelmet = ITEMS.register("mob_armor_blood_mana",
            ()->new MobArmor(ItemMaterial.BloodMana, ArmorItem.Type.HELMET, StringUtils.MobName.BloodMana));

    public static final RegistryObject<Item> MobArmorEarthManaHelmet = ITEMS.register("mob_armor_earth_mana_helmet",
            ()->new MobArmor(ItemMaterial.BloodMana, ArmorItem.Type.HELMET, StringUtils.MobName.EarthMana));

    public static final RegistryObject<Item> MobArmorEarthManaChest = ITEMS.register("mob_armor_earth_mana_chest",
            ()->new MobArmor(ItemMaterial.BloodMana, ArmorItem.Type.CHESTPLATE, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> MobArmorEarthManaLeggings = ITEMS.register("mob_armor_earth_mana_leggings",
            ()->new MobArmor(ItemMaterial.BloodMana, ArmorItem.Type.LEGGINGS, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> MobArmorEarthManaBoots = ITEMS.register("mob_armor_earth_mana_boots",
            ()->new MobArmor(ItemMaterial.BloodMana, ArmorItem.Type.BOOTS, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> BloodManaArmorHelmet = ITEMS.register("blood_mana_helmet",
            ()->new BloodManaArmor(ItemMaterial.BloodMana, ArmorItem.Type.HELMET,new Item.Properties().rarity(Utils.BloodManaItalic),0));

    public static final RegistryObject<Item> BloodManaArmorChest = ITEMS.register("blood_mana_chest",
            ()->new BloodManaArmor(ItemMaterial.BloodMana, ArmorItem.Type.CHESTPLATE,new Item.Properties().rarity(Utils.BloodManaItalic),1));

    public static final RegistryObject<Item> BloodManaArmorLeggings = ITEMS.register("blood_mana_leggings",
            ()->new BloodManaArmor(ItemMaterial.BloodMana, ArmorItem.Type.LEGGINGS,new Item.Properties().rarity(Utils.BloodManaItalic),2));

    public static final RegistryObject<Item> BloodManaArmorBoots = ITEMS.register("blood_mana_boots",
            ()->new BloodManaArmor(ItemMaterial.BloodMana, ArmorItem.Type.BOOTS,new Item.Properties().rarity(Utils.BloodManaItalic),3));

    public static final RegistryObject<Item> EarthManaArmorHelmet = ITEMS.register("earth_mana_helmet",
            ()->new EarthManaArmor(ItemMaterial.BloodMana, ArmorItem.Type.HELMET,new Item.Properties().rarity(Utils.BloodManaItalic),0));

    public static final RegistryObject<Item> EarthManaArmorChest = ITEMS.register("earth_mana_chest",
            ()->new EarthManaArmor(ItemMaterial.BloodMana, ArmorItem.Type.CHESTPLATE,new Item.Properties().rarity(Utils.BloodManaItalic),1));

    public static final RegistryObject<Item> EarthManaArmorLeggings = ITEMS.register("earth_mana_leggings",
            ()->new EarthManaArmor(ItemMaterial.BloodMana, ArmorItem.Type.LEGGINGS,new Item.Properties().rarity(Utils.BloodManaItalic),2));

    public static final RegistryObject<Item> EarthManaArmorBoots = ITEMS.register("earth_mana_boots",
            ()->new EarthManaArmor(ItemMaterial.BloodMana, ArmorItem.Type.BOOTS,new Item.Properties().rarity(Utils.BloodManaItalic),3));

    public static final RegistryObject<Item> EarthManaCurios = ITEMS.register("earth_mana_curios",
            ()->new EarthManaCurios(new Item.Properties().rarity(Utils.BloodManaBold).stacksTo(1)));

    public static final RegistryObject<Item> BloodManaCurios = ITEMS.register("blood_mana_curios",
            ()->new BloodManaCurios(new Item.Properties().rarity(Utils.BloodManaBold).stacksTo(1)));

    public static final RegistryObject<Item> EarthManaSoul = ITEMS.register("earth_mana_soul",
            ()->new Item(new Item.Properties().rarity(Utils.EvokerBold)));

    public static final RegistryObject<Item> BloodManaSoul = ITEMS.register("blood_mana_soul",
            ()->new Item(new Item.Properties().rarity(Utils.BloodManaBold)));

    public static final RegistryObject<Item> EarthManaRune = ITEMS.register("earth_mana_rune",
            ()->new Item(new Item.Properties().rarity(Utils.EvokerBold)));

    public static final RegistryObject<Item> BloodManaRune = ITEMS.register("blood_mana_rune",
            ()->new Item(new Item.Properties().rarity(Utils.BloodManaBold)));

    public static final RegistryObject<Item> LiuLiXianSword = ITEMS.register("liulixian_sword",
            ()->new LiulixianSword(ItemTier.VMaterial,2,0));

    public static final RegistryObject<Item> LiuLiXianSwordPaper = ITEMS.register("liulixian_sword_paper",
            ()->new CustomizePaper(new Item.Properties().rarity(Utils.SakuraBold),LiuLiXianSword.get()));

    public static final RegistryObject<Item> LiuLiXianSceptre = ITEMS.register("liulixian_sceptre",
            ()->new LiulixianSceptre(ItemTier.VMaterial,2,0));

    public static final RegistryObject<Item> ShangMengLiCurios1 = ITEMS.register("shangmengli_curios1",
            ()->new ShangMengLiCurios1(new Item.Properties().rarity(Utils.RedBold).stacksTo(1)));

    public static final RegistryObject<Item> ShangMengLiCurios1Paper = ITEMS.register("shangmengli_curios1_paper",
            ()->new CustomizePaper(new Item.Properties().rarity(Utils.IceBold),ShangMengLiCurios1.get()));

    public static final RegistryObject<Item> ShangMengLiCurios11 = ITEMS.register("shangmengli_curios11",
            ()->new ShangMengLiCurios1(new Item.Properties().rarity(Utils.RedBold).stacksTo(1)));

    public static final RegistryObject<Item> GuangYiBow = ITEMS.register("guangyi_bow",
            ()->new GuangYiBow(new Item.Properties().stacksTo(1).rarity(Utils.MineItalic)));

    public static final RegistryObject<Item> GuangYiBowPaper = ITEMS.register("guangyi_bow_paper",
            ()->new CustomizePaper(new Item.Properties().rarity(Utils.MineBold), GuangYiBow.get()));

    public static final RegistryObject<Item> SnowShield = ITEMS.register("snow_shield",
            ()->new SnowShield());

    public static final RegistryObject<Item> NetherShield = ITEMS.register("nether_shield",
            ()->new NetherShield());

    public static final RegistryObject<Item> GoldenShield = ITEMS.register("golden_shield",
            ()->new GoldShield());

    public static final RegistryObject<Item> GoldenShieldForgeDraw = ITEMS.register("golden_shield_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.GoldItalic),ModItems.GoldenShield.get()));

    public static final RegistryObject<Item> ManaShield = ITEMS.register("mana_shield",
            ()->new ManaShield());

    public static final RegistryObject<Item> ManaKnife = ITEMS.register("mana_knife",
            ()->new ManaKnife());

    public static final RegistryObject<Item> FengxiaojuCurios = ITEMS.register("fengxiaoju_curios",
            ()->new FengXiaoJuCurios(new Item.Properties().rarity(Utils.LightningItalic).stacksTo(1)));

    public static final RegistryObject<Item> FengxiaojuCuriosPaper = ITEMS.register("fengxiaoju_curios_paper",
            ()->new CustomizePaper(new Item.Properties().rarity(Utils.LightningBold).stacksTo(1),FengxiaojuCurios.get()));

    public static final RegistryObject<Item> FengxiaojuCurios1 = ITEMS.register("fengxiaoju_curios1",
            ()->new FengXiaoJuCurios(new Item.Properties().rarity(Utils.LightningItalic).stacksTo(1)));

    public static final RegistryObject<Item> XiangLiSmoke = ITEMS.register("xiangli_smoke",
            ()->new XiangliSmoke(ItemTier.VMaterial,2,0));

    public static final RegistryObject<Item> XiangLiSmokePaper = ITEMS.register("xiangli_smoke_paper",
            ()->new CustomizePaper(new Item.Properties().rarity(Utils.FieldBold).stacksTo(1),XiangLiSmoke.get()));

    public static final RegistryObject<Item> WcBow = ITEMS.register("wc_bow",
            ()->new WcndymlgbBow(new Item.Properties().stacksTo(1).rarity(Utils.LifeItalic)));

    public static final RegistryObject<Item> WcBowPaper = ITEMS.register("wc_bow_paper",
            ()->new CustomizePaper(new Item.Properties().rarity(Utils.LifeItalic).stacksTo(1),WcBow.get()));

    public static final RegistryObject<Item> WitherBook = ITEMS.register("wither_book",
            ()->new WitherBook(new Item.Properties().rarity(Utils.IceItalic)));

    public static final RegistryObject<Item> EarthBook = ITEMS.register("earth_book",
            ()->new EarthBook(new Item.Properties().rarity(Utils.BloodManaItalic)));

    public static final RegistryObject<Item> GoldenBook = ITEMS.register("golden_book",
            ()->new GoldenBook(new Item.Properties().rarity(Utils.GoldItalic)));

    public static final RegistryObject<Item> FieldGem = ITEMS.register("field_gem",
            ()->new FieldGem(new Item.Properties().rarity(Utils.FieldBold)));

    public static final RegistryObject<Item> MineGem = ITEMS.register("mine_gem",
            ()->new MineGem(new Item.Properties().rarity(Utils.MineBold)));

    public static final RegistryObject<Item> LifeManaGem = ITEMS.register("life_mana_gem",
            ()->new LifeManaGem(new Item.Properties().rarity(Utils.LifeBold)));

    public static final RegistryObject<Item> ObsiManaGem = ITEMS.register("obsi_mana_gem",
            ()->new ObsiManaGem(new Item.Properties().rarity(Utils.EvokerBold)));

    public static final RegistryObject<Item> NetherSkeletonGem = ITEMS.register("nether_skeleton_gem",
            ()->new NetherSkeletonGem(new Item.Properties().rarity(Utils.WitherBold)));

    public static final RegistryObject<Item> MagmaGem = ITEMS.register("magma_gem",
            ()->new MagmaGem(new Item.Properties().rarity(Utils.MagmaBold)));

    public static final RegistryObject<Item> WitherGem = ITEMS.register("wither_gem",
            ()->new WitherGem(new Item.Properties().rarity(Utils.WitherBold)));

    public static final RegistryObject<Item> PiglinGem = ITEMS.register("piglin_gem",
            ()->new PiglinGem(new Item.Properties().rarity(Utils.GoldBold)));

    public static final RegistryObject<Item> SakuraGem = ITEMS.register("sakura_gem",
            ()->new SakuraGem(new Item.Properties().rarity(Utils.SakuraBold)));

    public static final RegistryObject<Item> ShipGem = ITEMS.register("ship_gem",
            ()->new ShipGem(new Item.Properties().rarity(Utils.ShipBold)));

    public static final RegistryObject<Item> FieldGemD = ITEMS.register("field_gem_d",
            ()->new FieldGemD(new Item.Properties().rarity(Utils.FieldBold)));

    public static final RegistryObject<Item> MineGemD = ITEMS.register("mine_gem_d",
            ()->new MineGemD(new Item.Properties().rarity(Utils.MineBold)));

    public static final RegistryObject<Item> LifeManaGemD = ITEMS.register("life_mana_gem_d",
            ()->new LifeManaGemD(new Item.Properties().rarity(Utils.LifeBold)));

    public static final RegistryObject<Item> ObsiManaGemD = ITEMS.register("obsi_mana_gem_d",
            ()->new ObsiManaGemD(new Item.Properties().rarity(Utils.EvokerBold)));

    public static final RegistryObject<Item> NetherSkeletonGemD = ITEMS.register("nether_skeleton_gem_d",
            ()->new NetherSkeletonGemD(new Item.Properties().rarity(Utils.WitherBold)));

    public static final RegistryObject<Item> MagmaGemD = ITEMS.register("magma_gem_d",
            ()->new MagmaGemD(new Item.Properties().rarity(Utils.MagmaBold)));

    public static final RegistryObject<Item> WitherGemD = ITEMS.register("wither_gem_d",
            ()->new WitherGemD(new Item.Properties().rarity(Utils.WitherBold)));

    public static final RegistryObject<Item> PiglinGemD = ITEMS.register("piglin_gem_d",
            ()->new PiglinGemD(new Item.Properties().rarity(Utils.GoldBold)));

    public static final RegistryObject<Item> SakuraGemD = ITEMS.register("sakura_gem_d",
            ()->new SakuraGemD(new Item.Properties().rarity(Utils.SakuraBold)));

    public static final RegistryObject<Item> ShipGemD = ITEMS.register("ship_gem_d",
            ()->new ShipGemD(new Item.Properties().rarity(Utils.ShipBold)));

    public static final RegistryObject<Item> AirChest = ITEMS.register("air_chest",
            ()->new MobArmor(ItemMaterial.AIR, ArmorItem.Type.CHESTPLATE, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> XiangLiCurios = ITEMS.register("xiangli_curios",
            ()->new XiangLiCurios(new Item.Properties().rarity(Utils.FieldItalic).stacksTo(1)));

    public static final RegistryObject<Item> XiangLiCuriosPaper = ITEMS.register("xiangli_curios_paper",
            ()->new CustomizePaper(new Item.Properties().rarity(Utils.FieldItalic),XiangLiCurios.get()));

    public static final RegistryObject<Item> MobArmorDevilHelmet = ITEMS.register("mob_armor_devil_helmet",
            ()->new MobArmor(ItemMaterial.BloodMana, ArmorItem.Type.HELMET, StringUtils.MobName.Devil));

    public static final RegistryObject<Item> DevilBlood = ITEMS.register("devil_blood",
            ()->new Item(new Item.Properties().rarity(Utils.BloodManaBold)));

    public static final RegistryObject<Item> DevilAttackSoul = ITEMS.register("devil_attack_soul",
            ()->new Item(new Item.Properties().rarity(Utils.BloodManaBold)));

    public static final RegistryObject<Item> DevilSwiftSoul = ITEMS.register("devil_swift_soul",
            ()->new Item(new Item.Properties().rarity(Utils.BloodManaBold)));

    public static final RegistryObject<Item> DevilManaSoul = ITEMS.register("devil_mana_soul",
            ()->new Item(new Item.Properties().rarity(Utils.BloodManaBold)));

    public static final RegistryObject<Item> DevilAttackChest = ITEMS.register("devil_attack_chest",
            ()->new DevilAttackArmor(ItemMaterial.Devil, ArmorItem.Type.CHESTPLATE,new Item.Properties().rarity(Utils.BloodManaItalic),1));

    public static final RegistryObject<Item> DevilSwiftBoots = ITEMS.register("devil_swift_boots",
            ()->new DevilSwiftArmor(ItemMaterial.Devil, ArmorItem.Type.BOOTS,new Item.Properties().rarity(Utils.BloodManaItalic),3));

    public static final RegistryObject<Item> DevilManaHelmet = ITEMS.register("devil_mana_helmet",
            ()->new DevilManaArmor(ItemMaterial.Devil, ArmorItem.Type.HELMET,new Item.Properties().rarity(Utils.BloodManaItalic),0));

    public static final RegistryObject<Item> DevilAttackChestForgeDraw = ITEMS.register("devil_attack_chest_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.BloodManaBold),ModItems.DevilAttackChest.get()));

    public static final RegistryObject<Item> DevilSwiftBootsForgeDraw = ITEMS.register("devil_swift_boots_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.BloodManaBold),ModItems.DevilSwiftBoots.get()));

    public static final RegistryObject<Item> DevilManaHelmetForgeDraw = ITEMS.register("devil_mana_helmet_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.BloodManaBold),ModItems.DevilManaHelmet.get()));

    public static final RegistryObject<Item> DevilBloodManaCurios = ITEMS.register("devil_blood_mana_curios",
            ()->new DevilBloodManaCurios(new Item.Properties().rarity(Utils.BloodManaBold)));

    public static final RegistryObject<Item> DevilEarthManaCurios = ITEMS.register("devil_earth_mana_curios",
            ()->new DevilEarthManaCurios(new Item.Properties().rarity(Utils.BloodManaBold)));

    public static final RegistryObject<Item> DevilLoot = ITEMS.register("devil_loot",
            ()->new DevilLoot(new Item.Properties().rarity(Utils.BloodManaBold)));

    public static final RegistryObject<Item> DragonPrefix = ITEMS.register("dragon_prefix",
            ()->new DragonPrefix(new Item.Properties().rarity(Utils.SpringBold)));

    public static final RegistryObject<Item> XiangLiPrefix = ITEMS.register("xiangli_prefix",
            ()->new XiangLiPrefix(new Item.Properties().rarity(Utils.FieldBold)));

    public static final RegistryObject<Item> BlackFeisaSceptre = ITEMS.register("black_feisa_sceptre",
            ()->new BlackFeisaSceptre(ItemTier.VMaterial,2,0, new Item.Properties().rarity(Utils.End),0));

    public static final RegistryObject<Item> BlackFeisaSceptrePaper = ITEMS.register("black_feisa_sceptre_paper",
            ()->new CustomizePaper(new Item.Properties().rarity(Utils.End),BlackFeisaSceptre.get()));

    public static final RegistryObject<Item> EliaoiBook = ITEMS.register("eliaoi_book",
            ()->new EliaoiSceptre(ItemTier.VMaterial,2,0, new Item.Properties().rarity(Utils.RedItalic),0));

    public static final RegistryObject<Item> EliaoiCurios = ITEMS.register("eliaoi_curios",
            ()->new EliaoiCurios(new Item.Properties().rarity(Utils.RedBold)));

    public static final RegistryObject<Item> EliaoiBookPaper = ITEMS.register("eliaoi_book_paper",
            ()->new CustomizePaper(new Item.Properties().rarity(Utils.RedBold),EliaoiBook.get()));

    public static final RegistryObject<Item> EliaoiCuriosPaper = ITEMS.register("eliaoi_curios_paper",
            ()->new CustomizePaper(new Item.Properties().rarity(Utils.RedBold),EliaoiCurios.get()));

    public static final RegistryObject<Item> DING_ZHEN_MUSIC_DISC = ITEMS.register("ding_zhen_music_disc",
            () -> new RecordItem(6, ModSounds.IGotSmoke, new Item.Properties().stacksTo(1), 5200));

    public static final RegistryObject<Item> MobArmorMoonAttack = ITEMS.register("mob_armor_moon_attack",
            ()->new MobArmor(ItemMaterial.Moon, ArmorItem.Type.HELMET, StringUtils.MobName.MoonAttack));

    public static final RegistryObject<Item> MobArmorMoonMana = ITEMS.register("mob_armor_moon_mana",
            ()->new MobArmor(ItemMaterial.Moon, ArmorItem.Type.HELMET, StringUtils.MobName.MoonMana));

    public static final RegistryObject<Item> MobArmorMoonChest = ITEMS.register("mob_armor_moon_chest",
            ()->new MobArmor(ItemMaterial.Moon, ArmorItem.Type.CHESTPLATE, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> MobArmorMoonLeggings = ITEMS.register("mob_armor_moon_leggings",
            ()->new MobArmor(ItemMaterial.Moon, ArmorItem.Type.LEGGINGS, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> MobArmorMoonBoots = ITEMS.register("mob_armor_moon_boots",
            ()->new MobArmor(ItemMaterial.Moon, ArmorItem.Type.BOOTS, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> MoonSoul = ITEMS.register("moon_soul",
            ()->new Item(new Item.Properties().rarity(Utils.MoonBold)));

    public static final RegistryObject<Item> MoonCompleteGem = ITEMS.register("moon_complete_gem",
            ()->new Item(new Item.Properties().rarity(Utils.MoonBold)));

    public static final RegistryObject<Item> MoonShield = ITEMS.register("moon_shield",
            ()->new MoonShield());

    public static final RegistryObject<Item> MoonKnife = ITEMS.register("moon_knife",
            ()->new MoonKnife());

    public static final RegistryObject<Item> MoonBook = ITEMS.register("moon_book",
            ()->new MoonBook());

    public static final RegistryObject<Item> MoonShieldForgeDraw = ITEMS.register("moon_shield_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.MoonBold),ModItems.MoonShield.get()));

    public static final RegistryObject<Item> MoonKnifeForgeDraw = ITEMS.register("moon_knife_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.MoonBold),ModItems.MoonKnife.get()));

    public static final RegistryObject<Item> MoonBookForgeDraw = ITEMS.register("moon_book_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.MoonBold),ModItems.MoonBook.get()));

    public static final RegistryObject<Item> MoonLoot = ITEMS.register("moon_loot",
            ()->new MoonLoot(new Item.Properties().rarity(Utils.Moon)));

    public static final RegistryObject<Item> LiuLiSkill1 = ITEMS.register("liuli_skill1",
            ()->new Item(new Item.Properties().rarity(Utils.Moon)));

    public static final RegistryObject<Item> LiuLiSkill2 = ITEMS.register("liuli_skill2",
            ()->new Item(new Item.Properties().rarity(Utils.Moon)));

    public static final RegistryObject<Item> LiuLiSkill3 = ITEMS.register("liuli_skill3",
            ()->new Item(new Item.Properties().rarity(Utils.Moon)));

    public static final RegistryObject<Item> LiuLiSkill4 = ITEMS.register("liuli_skill4",
            ()->new Item(new Item.Properties().rarity(Utils.Moon)));

    public static final RegistryObject<Item> LiuLiSkill5 = ITEMS.register("liuli_skill5",
            ()->new Item(new Item.Properties().rarity(Utils.Moon)));

    public static final RegistryObject<Item> LiuLiSkill6 = ITEMS.register("liuli_skill6",
            ()->new Item(new Item.Properties().rarity(Utils.Moon)));

    public static final RegistryObject<Item> LiuLiSkill7 = ITEMS.register("liuli_skill7",
            ()->new Item(new Item.Properties().rarity(Utils.Moon)));

    public static final RegistryObject<Item> LiuLiSkill8 = ITEMS.register("liuli_skill8",
            ()->new Item(new Item.Properties().rarity(Utils.Moon)));

    public static final RegistryObject<Item> LiuLiXianCurios1 = ITEMS.register("liulixian_curios1",
            ()->new LiulixianCurios1(new Item.Properties().rarity(Utils.SakuraBold)));

    public static final RegistryObject<Item> LiuLiCount1 = ITEMS.register("liuli_count1",
            ()->new Item(new Item.Properties().rarity(Utils.Moon)));

    public static final RegistryObject<Item> LiuLiCount2 = ITEMS.register("liuli_count2",
            ()->new Item(new Item.Properties().rarity(Utils.Moon)));

    public static final RegistryObject<Item> LiuLiXianCurios1Paper = ITEMS.register("liulixian_curios1_paper",
            ()->new CustomizePaper(new Item.Properties().rarity(Utils.SakuraBold),LiuLiXianCurios1.get()));

    public static final RegistryObject<Item> ShangMengLiCurios2 = ITEMS.register("shangmengli_curios2",
            ()->new ShangMengLiCurios2(new Item.Properties().rarity(Utils.MoonBold)));

    public static final RegistryObject<Item> ShangMengLiCurios2Paper = ITEMS.register("shangmengli_curios2_paper",
            ()->new CustomizePaper(new Item.Properties().rarity(Utils.MoonBold),ShangMengLiCurios2.get()));

    public static final RegistryObject<Item> ShangMengLiCurios21 = ITEMS.register("shangmengli_curios21",
            ()->new Item(new Item.Properties().rarity(Utils.MoonBold)));

    public static final RegistryObject<Item> BlackFeisaCurios = ITEMS.register("black_feisa_curios",
            ()->new BlackFeisaCurios(new Item.Properties().rarity(Utils.FieldBold)));

    public static final RegistryObject<Item> BlackFeisaCuriosPaper = ITEMS.register("black_feisa_curios_paper",
            ()->new CustomizePaper(new Item.Properties().rarity(Utils.FieldBold),BlackFeisaCurios.get()));

    public static final RegistryObject<Item> BlackFeisaCurios1 = ITEMS.register("black_feisa_curios1",
            ()->new Item(new Item.Properties().rarity(Utils.FieldBold)));

    public static final RegistryObject<Item> QiFuBow = ITEMS.register("qifu_sceptre",
            ()->new QiFuBow(ItemTier.VMaterial,2,0, new Item.Properties().rarity(Utils.Moon1Italic),0));

    public static final RegistryObject<Item> QiFuSceptrePaper = ITEMS.register("qifu_sceptre_paper",
            ()->new CustomizePaper(new Item.Properties().rarity(Utils.Moon1Bold), QiFuBow.get()));

    public static final RegistryObject<Item> DevilSword = ITEMS.register("devil_sword",
            ()->new DevilSword(ItemTier.VMaterial,2,0));

    public static final RegistryObject<Item> DevilBow = ITEMS.register("devil_bow",
            ()->new DevilBow(new Item.Properties().stacksTo(1).rarity(Utils.BloodManaItalic)));

    public static final RegistryObject<Item> DevilSceptre = ITEMS.register("devil_sceptre",
            ()->new DevilSceptre(ItemTier.VMaterial,2,0, new Item.Properties().rarity(Utils.BloodManaItalic)));

    public static final RegistryObject<Item> MobArmorSlime = ITEMS.register("mob_armor_slime",
            ()->new MobArmor(ItemMaterial.ArmorIce, ArmorItem.Type.HELMET, StringUtils.MobName.Slime));

    public static final RegistryObject<Item> SlimeBall = ITEMS.register("slime_ball",
            ()->new Item(new Item.Properties().rarity(Utils.Life)));

    public static final RegistryObject<Item> BigSlimeBall = ITEMS.register("big_slime_ball",
            ()->new Item(new Item.Properties().rarity(Utils.LifeBold)));

    public static final RegistryObject<Item> SlimeBoots = ITEMS.register("slime_boots",
            ()->new SlimeBoots(ItemMaterial.ArmorKaze, ArmorItem.Type.BOOTS));

    public static final RegistryObject<Item> MoonLeggings = ITEMS.register("moon_leggings",
            ()->new MoonArmor(ItemMaterial.Moon, ArmorItem.Type.LEGGINGS,new Item.Properties().rarity(Utils.Moon1Italic),2));

    public static final RegistryObject<Item> MoonHelmet = ITEMS.register("moon_helmet",
            ()->new MoonArmor(ItemMaterial.Moon, ArmorItem.Type.HELMET,new Item.Properties().rarity(Utils.Moon1Italic),0));

    public static final RegistryObject<Item> MoonLeggingsForgeDraw = ITEMS.register("moon_leggings_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.Moon1Bold),ModItems.MoonLeggings.get()));

    public static final RegistryObject<Item> MoonHelmetForgeDraw = ITEMS.register("moon_helmet_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.Moon1Bold),ModItems.MoonHelmet.get()));

    public static final RegistryObject<Item> MoonSword = ITEMS.register("moon_sword",
            ()->new MoonSword(ItemTier.VMaterial,2,0));

    public static final RegistryObject<Item> MoonBow = ITEMS.register("moon_bow",
            ()->new MoonBow(new Item.Properties().stacksTo(1).rarity(Utils.Moon1Italic)));

    public static final RegistryObject<Item> MoonSceptre = ITEMS.register("moon_sceptre",
            ()->new MoonSceptre(ItemTier.VMaterial,2,0, new Item.Properties().rarity(Utils.Moon1Italic)));

    public static final RegistryObject<Item> MoonSwordForgeDraw = ITEMS.register("moon_sword_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.MoonBold),ModItems.MoonSword.get()));

    public static final RegistryObject<Item> MoonBowForgeDraw = ITEMS.register("moon_bow_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.MoonBold),ModItems.MoonBow.get()));

    public static final RegistryObject<Item> MoonSceptreForgeDraw = ITEMS.register("moon_sceptre_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.MoonBold),ModItems.MoonSceptre.get()));

    public static final RegistryObject<Item> ParkourMedal = ITEMS.register("parkour_medal",
            ()->new Item(new Item.Properties().rarity(Utils.LifeBold)));

    public static final RegistryObject<Item> ParkourTicket = ITEMS.register("parkour_ticket",
            ()->new ParkourTicket(new Item.Properties().rarity(Utils.LifeBold)));

    public static final RegistryObject<Item> EarthPower = ITEMS.register("earth_power",
            ()->new EarthPower(new Item.Properties().rarity(Utils.BloodManaItalic)));

    public static final RegistryObject<Item> MoonCurios = ITEMS.register("moon_curios",
            ()->new MoonCurios(new Item.Properties().rarity(Utils.Moon1Bold)));

    public static final RegistryObject<Item> MoonAttackGem = ITEMS.register("moon_attack_gem",
            ()->new MoonAttackGem(new Item.Properties().rarity(Utils.MoonBold)));

    public static final RegistryObject<Item> MoonManaGem = ITEMS.register("moon_mana_gem",
            ()->new MoonManaGem(new Item.Properties().rarity(Utils.Moon1Bold)));

    public static final RegistryObject<Item> MoonAttackGemD = ITEMS.register("moon_attack_gem_d",
            ()->new MoonAttackGemD(new Item.Properties().rarity(Utils.MoonBold)));

    public static final RegistryObject<Item> MoonManaGemD = ITEMS.register("moon_mana_gem_d",
            ()->new MoonManaGemD(new Item.Properties().rarity(Utils.Moon1Bold)));

    public static final RegistryObject<Item> MoonBelt = ITEMS.register("moon_belt",
            ()->new MoonBelt(new Item.Properties().rarity(Utils.Moon1Bold)));

    public static final RegistryObject<Item> MoonBeltForgeDraw = ITEMS.register("moon_belt_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.Moon1Bold),ModItems.MoonBelt.get()));

    public static final RegistryObject<Item> BlackFeisaCurios2 = ITEMS.register("black_feisa_curios2",
            ()->new BlackFeisaCurios1(new Item.Properties().rarity(Utils.FieldBold)));

    public static final RegistryObject<Item> BlackFeisaCurios21 = ITEMS.register("black_feisa_curios21",
            ()->new BlackFeisaCurios1(new Item.Properties().rarity(Utils.FieldBold)));

    public static final RegistryObject<Item> BlackFeisaCurios2Paper = ITEMS.register("black_feisa_curios2_paper",
            ()->new CustomizePaper(new Item.Properties().rarity(Utils.FieldBold),BlackFeisaCurios2.get()));

    public static final RegistryObject<Item> IntensifiedDevilBlood = ITEMS.register("intensified_devil_blood",
            ()->new Item(new Item.Properties().rarity(Utils.BloodManaBold)));

    public static final RegistryObject<Item> TabooPiece = ITEMS.register("taboo_piece",
            ()->new Item(new Item.Properties().rarity(Utils.BloodManaBold)));

    public static final RegistryObject<Item> PurpleIronConstraintStone = ITEMS.register("purple_iron_constraint_stone",
            ()->new Item(new Item.Properties().rarity(Utils.BloodManaBold)));

    public static final RegistryObject<Item> ConstrainTaboo = ITEMS.register("constraint_taboo",
            ()->new Item(new Item.Properties().rarity(Utils.BloodManaBold)));

    public static final RegistryObject<Item> MobArmorTabooDevil = ITEMS.register("mob_armor_taboo_devil",
            ()->new MobArmor(ItemMaterial.BloodMana, ArmorItem.Type.HELMET, StringUtils.MobName.TabooDevil));

    public static final RegistryObject<Item> TabooAttackLeggings = ITEMS.register("taboo_attack_leggings",
            ()->new TabooAttackArmor(ItemMaterial.BloodMana, ArmorItem.Type.LEGGINGS,new Item.Properties().rarity(Utils.BloodManaItalic),2));

    public static final RegistryObject<Item> TabooSwiftHelmet = ITEMS.register("taboo_swift_helmet",
            ()->new TabooSwiftArmor(ItemMaterial.BloodMana, ArmorItem.Type.HELMET,new Item.Properties().rarity(Utils.BloodManaItalic),0));

    public static final RegistryObject<Item> TabooManaBoots = ITEMS.register("taboo_mana_boots",
            ()->new TabooManaArmor(ItemMaterial.BloodMana, ArmorItem.Type.BOOTS,new Item.Properties().rarity(Utils.BloodManaItalic),3));

    public static final RegistryObject<Item> TabooAttackLeggingsForgeDraw = ITEMS.register("taboo_attack_leggings_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.BloodManaBold),ModItems.TabooAttackLeggings.get()));

    public static final RegistryObject<Item> TabooSwiftHelmetForgeDraw = ITEMS.register("taboo_swift_helmet_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.BloodManaBold),ModItems.TabooSwiftHelmet.get()));

    public static final RegistryObject<Item> TabooManaBootsForgeDraw = ITEMS.register("taboo_mana_boots_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.BloodManaBold),ModItems.TabooManaBoots.get()));

    public static final RegistryObject<Item> EarthManaKillPaper = ITEMS.register("earth_mana_killpaper",
            ()->new KillPaper(new Item.Properties().rarity(Rarity.EPIC),StringUtils.MobName.EarthMana));

    public static final RegistryObject<Item> BloodManaKillPaper = ITEMS.register("blood_mana_killpaper",
            ()->new KillPaper(new Item.Properties().rarity(Rarity.EPIC),StringUtils.MobName.BloodMana));

    public static final RegistryObject<Item> SlimeKillPaper = ITEMS.register("slime_killpaper",
            ()->new KillPaper(new Item.Properties().rarity(Rarity.EPIC),StringUtils.MobName.Slime));

    public static final RegistryObject<Item> ShangMengLiSword = ITEMS.register("shangmengli_sword",
            ()->new ShangMengLiSword(ItemTier.VMaterial,2,0));

    public static final RegistryObject<Item> ShangMengLiSwordPaper = ITEMS.register("shangmengli_sword_paper",
            ()->new CustomizePaper(new Item.Properties().rarity(Utils.EvokerItalic),ShangMengLiSword.get()));

    public static final RegistryObject<Item> YuanShiRenSceptre = ITEMS.register("yuanshiren_sceptre",
            ()->new YuanShiRen(ItemTier.VMaterial,2,0, new Item.Properties().rarity(Utils.YSRItalic),0));

    public static final RegistryObject<Item> YuanShiRenSceptrePaper = ITEMS.register("yuanshiren_sceptre_paper",
            ()->new CustomizePaper(new Item.Properties().rarity(Utils.YSRBold), YuanShiRenSceptre.get()));

    public static final RegistryObject<Item> YuanShiRenCurios = ITEMS.register("yuanshiren_curios",
            ()->new YuanShiRenCurios(new Item.Properties().rarity(Utils.YSR1Italic)));

    public static final RegistryObject<Item> YuanShiRenCuriosPaper = ITEMS.register("yuanshiren_curios_paper",
            ()->new CustomizePaper(new Item.Properties().rarity(Utils.YSR1Bold), YuanShiRenCurios.get()));

    public static final RegistryObject<Item> QiFuCurios = ITEMS.register("qifu_curios",
            ()->new QiFuCurios(new Item.Properties().rarity(Utils.Moon1Bold)));

    public static final RegistryObject<Item> QiFuCuriosPaper = ITEMS.register("qifu_curios_paper",
            ()->new CustomizePaper(new Item.Properties().rarity(Utils.Moon1Bold),QiFuCurios.get()));

    public static final RegistryObject<Item> BlackFeisaCurios3 = ITEMS.register("black_feisa_curios3",
            ()->new BlackFeisaCurios2(new Item.Properties().rarity(Utils.MoonBold)));

    public static final RegistryObject<Item> BlackFeisaCurios3Paper = ITEMS.register("black_feisa_curios3_paper",
            ()->new CustomizePaper(new Item.Properties().rarity(Utils.MoonBold),BlackFeisaCurios3.get()));

    public static final RegistryObject<Item> KillPaperLoot = ITEMS.register("kill_paper_loot",
            ()->new KillPaperLoot(new Item.Properties().rarity(Utils.LifeBold)));

    public static final RegistryObject<Item> ParkourGloves = ITEMS.register("parkour_gloves",
            ()->new ParkourGloves(new Item.Properties().rarity(Utils.LifeBold)));

/*
    public static final RegistryObject<Item> SoraCurios = ITEMS.register("sora_curios",
            ()->new SoraVanilla(new Item.Properties().rarity(Utils.FieldBold)));

    public static final RegistryObject<Item> SoraCuriosPaper = ITEMS.register("sora_curios_paper",
            ()->new CustomizePaper(new Item.Properties().rarity(Utils.FieldBold),SoraCurios.get()));
*/

    public static final RegistryObject<Item> MobArmorBlackCastleOneFloorManaHelmet = ITEMS.register("mob_armor_black_castle_one_floor_mana_helmet",
            ()->new MobArmor(ItemMaterial.Castle, ArmorItem.Type.HELMET, StringUtils.MobName.BlackCastleOneFloorMana));

    public static final RegistryObject<Item> MobArmorBlackCastleOneFloorAttackHelmet = ITEMS.register("mob_armor_black_castle_one_floor_attack_helmet",
            ()->new MobArmor(ItemMaterial.Castle, ArmorItem.Type.HELMET, StringUtils.MobName.BlackCastleOneFloorAttack));

    public static final RegistryObject<Item> MobArmorBlackCastleOneFloorChest = ITEMS.register("mob_armor_black_castle_one_floor_chest",
            ()->new MobArmor(ItemMaterial.Castle, ArmorItem.Type.CHESTPLATE,StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> MobArmorBlackCastleOneFloorLeggings = ITEMS.register("mob_armor_black_castle_one_floor_leggings",
            ()->new MobArmor(ItemMaterial.Castle, ArmorItem.Type.LEGGINGS,StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> MobArmorBlackCastleOneFloorBoots = ITEMS.register("mob_armor_black_castle_one_floor_boots",
            ()->new MobArmor(ItemMaterial.Castle, ArmorItem.Type.BOOTS,StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> CastlePiece = ITEMS.register("castle_piece",
            ()->new Item(new Item.Properties().rarity(Utils.Castle)));

    public static final RegistryObject<Item> CastleSword = ITEMS.register("castle_sword",
            ()->new CastleSword(ItemTier.VMaterial,2,0,new Item.Properties().stacksTo(1).rarity(Utils.CastleItalic)));

    public static final RegistryObject<Item> CastleBow = ITEMS.register("castle_bow",
            ()->new CastleBow(new Item.Properties().stacksTo(1).rarity(Utils.CastleItalic)));

    public static final RegistryObject<Item> CastleSceptre = ITEMS.register("castle_sceptre",
            ()->new CastleSceptre(ItemTier.VMaterial,2,0, new Item.Properties().rarity(Utils.CastleItalic)));

    public static final RegistryObject<Item> CastleSwordForgeDraw = ITEMS.register("castle_sword_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.CastleBold),ModItems.CastleSword.get()));

    public static final RegistryObject<Item> CastleBowForgeDraw = ITEMS.register("castle_bow_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.CastleBold),ModItems.CastleBow.get()));

    public static final RegistryObject<Item> CastleSceptreForgeDraw = ITEMS.register("castle_sceptre_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.CastleBold),ModItems.CastleSceptre.get()));

    public static final RegistryObject<Item> CastleIngot = ITEMS.register("castle_ingot",
            ()->new Item(new Item.Properties().rarity(Utils.CastleBold)));

    public static final RegistryObject<Item> CastleSwordPiece = ITEMS.register("castle_sword_piece",
            ()->new Item(new Item.Properties().rarity(Utils.CastleBold)));

    public static final RegistryObject<Item> CastleBowPiece = ITEMS.register("castle_bow_piece",
            ()->new Item(new Item.Properties().rarity(Utils.CastleBold)));

    public static final RegistryObject<Item> CastleSceptrePiece = ITEMS.register("castle_sceptre_piece",
            ()->new Item(new Item.Properties().rarity(Utils.CastleBold)));

    public static final RegistryObject<Item> CastleArmorPiece = ITEMS.register("castle_armor_piece",
            ()->new Item(new Item.Properties().rarity(Utils.CastleBold)));

    public static final RegistryObject<Item> CastleLoot = ITEMS.register("castle_loot",
            ()->new CastleLoot(new Item.Properties().rarity(Utils.CastleBold)));

    public static final RegistryObject<Item> CastleAttackHelmet = ITEMS.register("castle_attack_helmet",
            ()-> new CastleAttackArmor(ItemMaterial.Castle, ArmorItem.Type.HELMET,new Item.Properties().rarity(Utils.CastleItalic),0));

    public static final RegistryObject<Item> CastleAttackChest = ITEMS.register("castle_attack_chest",
            ()->new CastleAttackArmor(ItemMaterial.Castle, ArmorItem.Type.CHESTPLATE,new Item.Properties().rarity(Utils.CastleItalic),1));

    public static final RegistryObject<Item> CastleAttackLeggings = ITEMS.register("castle_attack_leggings",
            ()->new CastleAttackArmor(ItemMaterial.Castle, ArmorItem.Type.LEGGINGS,new Item.Properties().rarity(Utils.CastleItalic),2));

    public static final RegistryObject<Item> CastleAttackBoots = ITEMS.register("castle_attack_boots",
            ()->new CastleAttackArmor(ItemMaterial.Castle, ArmorItem.Type.BOOTS,new Item.Properties().rarity(Utils.CastleItalic),3));

    public static final RegistryObject<Item> CastleAttackHelmetForgeDraw = ITEMS.register("castle_attack_helmet_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.CastleBold),ModItems.CastleAttackHelmet.get()));

    public static final RegistryObject<Item> CastleAttackChestForgeDraw = ITEMS.register("castle_attack_chest_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.CastleBold),ModItems.CastleAttackChest.get()));

    public static final RegistryObject<Item> CastleAttackLeggingsForgeDraw = ITEMS.register("castle_attack_leggings_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.CastleBold),ModItems.CastleAttackLeggings.get()));

    public static final RegistryObject<Item> CastleAttackBootsForgeDraw = ITEMS.register("castle_attack_boots_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.CastleBold),ModItems.CastleAttackBoots.get()));

    public static final RegistryObject<Item> CastleSwiftHelmet = ITEMS.register("castle_swift_helmet",
            ()-> new CastleSwiftArmor(ItemMaterial.Castle, ArmorItem.Type.HELMET,new Item.Properties().rarity(Utils.CastleItalic),0));

    public static final RegistryObject<Item> CastleSwiftChest = ITEMS.register("castle_swift_chest",
            ()->new CastleSwiftArmor(ItemMaterial.Castle, ArmorItem.Type.CHESTPLATE,new Item.Properties().rarity(Utils.CastleItalic),1));

    public static final RegistryObject<Item> CastleSwiftLeggings = ITEMS.register("castle_swift_leggings",
            ()->new CastleSwiftArmor(ItemMaterial.Castle, ArmorItem.Type.LEGGINGS,new Item.Properties().rarity(Utils.CastleItalic),2));

    public static final RegistryObject<Item> CastleSwiftBoots = ITEMS.register("castle_swift_boots",
            ()->new CastleSwiftArmor(ItemMaterial.Castle, ArmorItem.Type.BOOTS,new Item.Properties().rarity(Utils.CastleItalic),3));

    public static final RegistryObject<Item> CastleSwiftHelmetForgeDraw = ITEMS.register("castle_swift_helmet_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.CastleBold),ModItems.CastleSwiftHelmet.get()));

    public static final RegistryObject<Item> CastleSwiftChestForgeDraw = ITEMS.register("castle_swift_chest_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.CastleBold),ModItems.CastleSwiftChest.get()));

    public static final RegistryObject<Item> CastleSwiftLeggingsForgeDraw = ITEMS.register("castle_swift_leggings_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.CastleBold),ModItems.CastleSwiftLeggings.get()));

    public static final RegistryObject<Item> CastleSwiftBootsForgeDraw = ITEMS.register("castle_swift_boots_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.CastleBold),ModItems.CastleSwiftBoots.get()));

    public static final RegistryObject<Item> CastleManaHelmet = ITEMS.register("castle_mana_helmet",
            ()-> new CastleManaArmor(ItemMaterial.Castle, ArmorItem.Type.HELMET,new Item.Properties().rarity(Utils.CastleItalic),0));

    public static final RegistryObject<Item> CastleManaChest = ITEMS.register("castle_mana_chest",
            ()->new CastleManaArmor(ItemMaterial.Castle, ArmorItem.Type.CHESTPLATE,new Item.Properties().rarity(Utils.CastleItalic),1));

    public static final RegistryObject<Item> CastleManaLeggings = ITEMS.register("castle_mana_leggings",
            ()->new CastleManaArmor(ItemMaterial.Castle, ArmorItem.Type.LEGGINGS,new Item.Properties().rarity(Utils.CastleItalic),2));

    public static final RegistryObject<Item> CastleManaBoots = ITEMS.register("castle_mana_boots",
            ()->new CastleManaArmor(ItemMaterial.Castle, ArmorItem.Type.BOOTS,new Item.Properties().rarity(Utils.CastleItalic),3));

    public static final RegistryObject<Item> CastleManaHelmetForgeDraw = ITEMS.register("castle_mana_helmet_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.CastleBold),ModItems.CastleManaHelmet.get()));

    public static final RegistryObject<Item> CastleManaChestForgeDraw = ITEMS.register("castle_mana_chest_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.CastleBold),ModItems.CastleManaChest.get()));

    public static final RegistryObject<Item> CastleManaLeggingsForgeDraw = ITEMS.register("castle_mana_leggings_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.CastleBold),ModItems.CastleManaLeggings.get()));

    public static final RegistryObject<Item> CastleManaBootsForgeDraw = ITEMS.register("castle_mana_boots_forge_draw",
            ()->new ForgeDraw(new Item.Properties().rarity(Utils.CastleBold),ModItems.CastleManaBoots.get()));

    public static final RegistryObject<Item> CastleSoul = ITEMS.register("castle_soul",
            ()->new Item(new Item.Properties().rarity(Utils.Castle)));

    public static final RegistryObject<Item> CgswdCurios = ITEMS.register("cgswd_curios",
            ()->new CgswdCurios(new Item.Properties().rarity(Utils.MoonBold)));

    public static final RegistryObject<Item> CgswdCuriosPaper = ITEMS.register("cgswd_curios_paper",
            ()->new CustomizePaper(new Item.Properties().rarity(Utils.MoonBold),CgswdCurios.get()));

    public static final RegistryObject<Item> LeiyanCurios = ITEMS.register("leiyan_curios",
            ()->new LeiyanCurios(new Item.Properties().rarity(Utils.LifeBold)));

    public static final RegistryObject<Item> LeiyanCuriosPaper = ITEMS.register("leiyan_curios_paper",
            ()->new CustomizePaper(new Item.Properties().rarity(Utils.LifeBold),LeiyanCurios.get()));

    public static final RegistryObject<Item> YxwgBow = ITEMS.register("yxwg_bow",
            ()->new YxwgBow( new Item.Properties().rarity(Utils.LifeBold)));

    public static final RegistryObject<Item> YxwgBowPaper = ITEMS.register("yxwg_bow_paper",
            ()->new CustomizePaper(new Item.Properties().rarity(Utils.LifeBold),YxwgBow.get()));

    public static final RegistryObject<Item> CgswdSceptre = ITEMS.register("cgswd_sceptre",
            ()->new CgswdSceptre(ItemTier.VMaterial,2,0, new Item.Properties().rarity(Utils.YSRItalic)));

    public static final RegistryObject<Item> CgswdSceptrePaper = ITEMS.register("cgswd_sceptre_paper",
            ()->new CustomizePaper(new Item.Properties().rarity(Utils.YSRBold),CgswdSceptre.get()));

    public static final RegistryObject<Item> MyMissionBow = ITEMS.register("mymission_bow",
            ()->new MyMissionBow(new Item.Properties().rarity(Utils.LifeItalic)));

    public static final RegistryObject<Item> MyMissionBowPaper = ITEMS.register("mymission_bow_paper",
            ()->new CustomizePaper(new Item.Properties().rarity(Utils.LifeBold),MyMissionBow.get()));

    public static final RegistryObject<Item> YxwgCurios = ITEMS.register("yxwg_curios",
            ()->new YxwgCurios(new Item.Properties().rarity(Utils.LifeBold)));

    public static final RegistryObject<Item> YxwgCuriosPaper = ITEMS.register("yxwg_curios_paper",
            ()->new CustomizePaper(new Item.Properties().rarity(Utils.LifeBold),YxwgCurios.get()));

    public static final RegistryObject<Item> YxwgCurios1 = ITEMS.register("yxwg_curios1",
            ()->new YxwgCurios1(new Item.Properties().rarity(Utils.LifeBold)));

    public static final RegistryObject<Item> YxwgCurios1Paper = ITEMS.register("yxwg_curios1_paper",
            ()->new CustomizePaper(new Item.Properties().rarity(Utils.LifeBold),YxwgCurios1.get()));

    public static final RegistryObject<Item> SoraCurios1 = ITEMS.register("sora_curios1",
            ()->new SoraVanilla1(new Item.Properties().rarity(Utils.MagmaBold)));

    public static final RegistryObject<Item> SoraCurios1Paper = ITEMS.register("sora_curios1_paper",
            ()->new CustomizePaper(new Item.Properties().rarity(Utils.MagmaBold),SoraCurios1.get()));

    public static final RegistryObject<Item> MyMissionCurios = ITEMS.register("mymission_curios",
            ()->new MyMissionCurios(new Item.Properties().rarity(Utils.LifeBold)));

    public static final RegistryObject<Item> MyMissionCuriosPaper = ITEMS.register("mymission_curios_paper",
            ()->new CustomizePaper(new Item.Properties().rarity(Utils.LifeBold),MyMissionCurios.get()));

    public static final RegistryObject<Item> MyMissionCurios1 = ITEMS.register("mymission_curios1",
            ()->new MyMissionCurios1(new Item.Properties().rarity(Utils.LifeBold)));

    public static final RegistryObject<Item> MyMissionCurios1Paper = ITEMS.register("mymission_curios1_paper",
            ()->new CustomizePaper(new Item.Properties().rarity(Utils.LifeBold),MyMissionCurios1.get()));

    public static final RegistryObject<Item> LeiyanBow = ITEMS.register("leiyan_bow",
            ()->new LeiyanBow(new Item.Properties().rarity(Utils.LifeBold)));

    public static final RegistryObject<Item> LeiyanBowPaper = ITEMS.register("leiyan_bow_paper",
            ()->new CustomizePaper(new Item.Properties().rarity(Utils.LifeBold),LeiyanBow.get()));

    public static final RegistryObject<Item> MobArmorBeaconHelmet = ITEMS.register("mob_armor_beacon_helmet",
            ()->new MobArmor(ItemMaterial.Castle, ArmorItem.Type.HELMET, StringUtils.MobName.Beacon));

    public static final RegistryObject<Item> MobArmorBeaconChest = ITEMS.register("mob_armor_beacon_chest",
            ()->new MobArmor(ItemMaterial.Castle, ArmorItem.Type.CHESTPLATE,StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> MobArmorBeaconLeggings = ITEMS.register("mob_armor_beacon_leggings",
            ()->new MobArmor(ItemMaterial.Castle, ArmorItem.Type.LEGGINGS,StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> MobArmorBeaconBoots = ITEMS.register("mob_armor_beacon_boots",
            ()->new MobArmor(ItemMaterial.Castle, ArmorItem.Type.BOOTS,StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> MobArmorTreeHelmet = ITEMS.register("mob_armor_tree_helmet",
            ()->new MobArmor(ItemMaterial.Castle, ArmorItem.Type.HELMET, StringUtils.MobName.Tree));

    public static final RegistryObject<Item> MobArmorBlazeHelmet = ITEMS.register("mob_armor_blaze_helmet",
            ()->new MobArmor(ItemMaterial.Castle, ArmorItem.Type.HELMET, StringUtils.MobName.Blaze));

    public static final RegistryObject<Item> BeaconBow = ITEMS.register("beacon_bow",
            ()->new BeaconBow(new Item.Properties().rarity(Utils.MagmaItalic),0));

    public static final RegistryObject<Item> BeaconBow1 = ITEMS.register("beacon_bow1",
            ()->new BeaconBow(new Item.Properties().rarity(Utils.MagmaItalic),1));

    public static final RegistryObject<Item> BeaconBow2 = ITEMS.register("beacon_bow2",
            ()->new BeaconBow(new Item.Properties().rarity(Utils.MagmaItalic),2));

    public static final RegistryObject<Item> BeaconBow3 = ITEMS.register("beacon_bow3",
            ()->new BeaconBow(new Item.Properties().rarity(Utils.MagmaItalic),3));

    public static final RegistryObject<Item> BeaconBracelet = ITEMS.register("beacon_bracelet",
            ()->new BeaconBracelet(new Item.Properties().rarity(Utils.MagmaBold)));

    public static final RegistryObject<Item> BlazeSword = ITEMS.register("blaze_sword",
            ()->new BlazeSword(new Item.Properties().rarity(Utils.MagmaItalic),0));

    public static final RegistryObject<Item> BlazeSword1 = ITEMS.register("blaze_sword1",
            ()->new BlazeSword(new Item.Properties().rarity(Utils.MagmaItalic),1));

    public static final RegistryObject<Item> BlazeSword2 = ITEMS.register("blaze_sword2",
            ()->new BlazeSword(new Item.Properties().rarity(Utils.MagmaItalic),2));

    public static final RegistryObject<Item> BlazeSword3 = ITEMS.register("blaze_sword3",
            ()->new BlazeSword(new Item.Properties().rarity(Utils.MagmaItalic),3));

    public static final RegistryObject<Item> BlazeBracelet = ITEMS.register("blaze_bracelet",
            ()->new BlazeBracelet(new Item.Properties().rarity(Utils.MagmaBold)));

    public static final RegistryObject<Item> TreeBracelet = ITEMS.register("tree_bracelet",
            ()->new TreeBracelet(new Item.Properties().rarity(Utils.LifeBold)));

    public static final RegistryObject<Item> TreeSceptre = ITEMS.register("tree_sceptre",
            ()->new TreeSceptre(new Item.Properties().rarity(Utils.LifeItalic),0));

    public static final RegistryObject<Item> TreeSceptre1 = ITEMS.register("tree_sceptre1",
            ()->new TreeSceptre(new Item.Properties().rarity(Utils.LifeItalic),1));

    public static final RegistryObject<Item> TreeSceptre2 = ITEMS.register("tree_sceptre2",
            ()->new TreeSceptre(new Item.Properties().rarity(Utils.LifeItalic),2));

    public static final RegistryObject<Item> TreeSceptre3 = ITEMS.register("tree_sceptre3",
            ()->new TreeSceptre(new Item.Properties().rarity(Utils.LifeItalic),3));

    public static final RegistryObject<Item> BeaconSoul = ITEMS.register("beacon_soul",
            ()->new Item(new Item.Properties().rarity(Utils.Magma)));

    public static final RegistryObject<Item> BeaconRune = ITEMS.register("beacon_rune",
            ()->new Item(new Item.Properties().rarity(Utils.MagmaBold)));

    public static final RegistryObject<Item> BlazeSoul = ITEMS.register("blaze_soul",
            ()->new Item(new Item.Properties().rarity(Utils.Magma)));

    public static final RegistryObject<Item> BlazeRune = ITEMS.register("blaze_rune",
            ()->new Item(new Item.Properties().rarity(Utils.MagmaBold)));

    public static final RegistryObject<Item> TreeSoul = ITEMS.register("tree_soul",
            ()->new Item(new Item.Properties().rarity(Utils.Life)));

    public static final RegistryObject<Item> TreeRune = ITEMS.register("tree_rune",
            ()->new Item(new Item.Properties().rarity(Utils.LifeBold)));

    public static final RegistryObject<Item> EndPower = ITEMS.register("end_power",
            ()->new EndPower(new Item.Properties().rarity(Utils.End),0));

    public static final RegistryObject<Item> EndPower1 = ITEMS.register("end_power1",
            ()->new EndPower(new Item.Properties().rarity(Utils.End),1));

    public static final RegistryObject<Item> EndPower2 = ITEMS.register("end_power2",
            ()->new EndPower(new Item.Properties().rarity(Utils.End),2));

    public static final RegistryObject<Item> EndPower3 = ITEMS.register("end_power3",
            ()->new EndPower(new Item.Properties().rarity(Utils.End),3));

    public static final RegistryObject<Item> CastleNecklace = ITEMS.register("castle_necklace",
            ()->new CastleCurios(new Item.Properties().rarity(Utils.CastleBold).stacksTo(1)));

    public static final RegistryObject<Item> FengxiaojuCurios_1 = ITEMS.register("fengxiaoju_curios_1",
            ()->new FengXiaoJuCurios1(new Item.Properties().rarity(Utils.SkyBold)));

    public static final RegistryObject<Item> FengxiaojuCurios_1Paper = ITEMS.register("fengxiaoju_curios_1_paper",
            ()->new CustomizePaper(new Item.Properties().rarity(Utils.SkyBold),FengxiaojuCurios_1.get()));

    public static final RegistryObject<Item> EliaoiCurios1 = ITEMS.register("eliaoi_curios1",
            ()->new EliaoiCurios1(new Item.Properties().rarity(Utils.BlackBold)));

    public static final RegistryObject<Item> EliaoiCurios1Paper = ITEMS.register("eliaoi_curios1_paper",
            ()->new CustomizePaper(new Item.Properties().rarity(Utils.BlackBold),EliaoiCurios1.get()));

    public static final RegistryObject<Item> LiulixianCurios2 = ITEMS.register("liulixian_curios2",
            ()->new LiulixianCurios2(new Item.Properties().rarity(Utils.SakuraBold)));

    public static final RegistryObject<Item> LiulixianCurios2Paper = ITEMS.register("liulixian_curios2_paper",
            ()->new CustomizePaper(new Item.Properties().rarity(Utils.SakuraBold),LiulixianCurios2.get()));

    public static final RegistryObject<Item> CastleTabooPiece = ITEMS.register("castle_taboo_piece",
            ()->new Item(new Item.Properties().rarity(Utils.CastleBold)));

    public static final RegistryObject<Item> SoraCurios2 = ITEMS.register("sora_curios2",
            ()->new SoraVanilla2(new Item.Properties().rarity(Utils.MagmaBold)));

    public static final RegistryObject<Item> SoraCurios2Paper = ITEMS.register("sora_curios2_paper",
            ()->new CustomizePaper(new Item.Properties().rarity(Utils.MagmaBold),SoraCurios2.get()));

    public static final RegistryObject<Item> MobArmorCastleKnightHelmet = ITEMS.register("mob_armor_castle_knight_helmet",
            ()->new MobArmor(ItemMaterial.Castle, ArmorItem.Type.HELMET, StringUtils.MobName.CastleKnight));

    public static final RegistryObject<Item> CastleBrooch = ITEMS.register("castle_brooch",
            ()->new CastleCurios(new Item.Properties().rarity(Utils.CastleBold).stacksTo(1)));

    public static final RegistryObject<Item> CastleKnightStone = ITEMS.register("castle_knight_stone",
            ()->new Item(new Item.Properties().rarity(Utils.CastleCrystalBold)));

    public static final RegistryObject<Item> CastleCrystal = ITEMS.register("castle_crystal",
            ()->new Item(new Item.Properties().rarity(Utils.CastleCrystalBold)));

    public static final RegistryObject<Item> CastleCrystalNorth = ITEMS.register("castle_crystal_north",
            ()->new Item(new Item.Properties().rarity(Utils.CastleCrystalBold)));

    public static final RegistryObject<Item> CastleCrystalSouth = ITEMS.register("castle_crystal_south",
            ()->new Item(new Item.Properties().rarity(Utils.CastleCrystalBold)));

    public static final RegistryObject<Item> RubyNecklace = ITEMS.register("ruby_necklace",
            ()->new RubyNecklace(new Item.Properties().rarity(Utils.RedBold),0));

    public static final RegistryObject<Item> RubyNecklace1 = ITEMS.register("ruby_necklace1",
            ()->new RubyNecklace(new Item.Properties().rarity(Utils.RedBold),1));

    public static final RegistryObject<Item> RubyNecklace2 = ITEMS.register("ruby_necklace2",
            ()->new RubyNecklace(new Item.Properties().rarity(Utils.RedBold),2));

    public static final RegistryObject<Item> RubyNecklace3 = ITEMS.register("ruby_necklace3",
            ()->new RubyNecklace(new Item.Properties().rarity(Utils.RedBold),3));

    public static final RegistryObject<Item> SapphireNecklace = ITEMS.register("sapphire_necklace",
            ()->new SapphireNecklace(new Item.Properties().rarity(Utils.LakeBold),0));

    public static final RegistryObject<Item> SapphireNecklace1 = ITEMS.register("sapphire_necklace1",
            ()->new SapphireNecklace(new Item.Properties().rarity(Utils.LakeBold),1));

    public static final RegistryObject<Item> SapphireNecklace2 = ITEMS.register("sapphire_necklace2",
            ()->new SapphireNecklace(new Item.Properties().rarity(Utils.LakeBold),2));

    public static final RegistryObject<Item> SapphireNecklace3 = ITEMS.register("sapphire_necklace3",
            ()->new SapphireNecklace(new Item.Properties().rarity(Utils.LakeBold),3));

    public static final RegistryObject<Item> FancySapphireNecklace = ITEMS.register("fancy_sapphire_necklace",
            ()->new FancySapphireNecklace(new Item.Properties().rarity(Utils.SakuraBold),0));

    public static final RegistryObject<Item> FancySapphireNecklace1 = ITEMS.register("fancy_sapphire_necklace1",
            ()->new FancySapphireNecklace(new Item.Properties().rarity(Utils.SakuraBold),1));

    public static final RegistryObject<Item> FancySapphireNecklace2 = ITEMS.register("fancy_sapphire_necklace2",
            ()->new FancySapphireNecklace(new Item.Properties().rarity(Utils.SakuraBold),2));

    public static final RegistryObject<Item> FancySapphireNecklace3 = ITEMS.register("fancy_sapphire_necklace3",
            ()->new FancySapphireNecklace(new Item.Properties().rarity(Utils.SakuraBold),3));

    public static final RegistryObject<Item> CastleWeaponGem = ITEMS.register("castle_weapon_gem",
            ()->new CastleWeaponGem(new Item.Properties().rarity(Utils.CastleCrystalBold)));

    public static final RegistryObject<Item> CastleArmorGem = ITEMS.register("castle_armor_gem",
            ()->new CastleArmorGem(new Item.Properties().rarity(Utils.CastleCrystalBold)));

    public static final RegistryObject<Item> CastleCuriosPowder = ITEMS.register("castle_curios_powder",
            ()->new Item(new Item.Properties().rarity(Utils.CastleCrystalBold)));

    public static final RegistryObject<Item> MobArmorPurpleIronKnightHelmet = ITEMS.register("mob_armor_purple_knight_helmet",
            ()->new HolyArmor(ItemMaterial.LakeMaterialHelmet, ArmorItem.Type.HELMET,StringUtils.MobName.PurpleIronKnight));

    public static final RegistryObject<Item> MobArmorPurpleIronKnightChest = ITEMS.register("mob_armor_purple_knight_chest",
            ()->new HolyArmor(ItemMaterial.LakeMaterialChest, ArmorItem.Type.CHESTPLATE,StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> MobArmorPurpleIronKnightLeggings = ITEMS.register("mob_armor_purple_knight_leggings",
            ()->new HolyArmor(ItemMaterial.LakeMaterialLeggings, ArmorItem.Type.LEGGINGS,StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> MobArmorPurpleIronKnightBoots = ITEMS.register("mob_armor_purple_knight_boots",
            ()->new HolyArmor(ItemMaterial.LakeMaterialBoots, ArmorItem.Type.BOOTS,StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> PurpleIronBow = ITEMS.register("purple_iron_bow",
            ()->new PurpleIronBow(new Item.Properties().rarity(Utils.PurpleIronItalic),0));

    public static final RegistryObject<Item> PurpleIronBow1 = ITEMS.register("purple_iron_bow1",
            ()->new PurpleIronBow(new Item.Properties().rarity(Utils.PurpleIronItalic),1));

    public static final RegistryObject<Item> PurpleIronBow2 = ITEMS.register("purple_iron_bow2",
            ()->new PurpleIronBow(new Item.Properties().rarity(Utils.PurpleIronItalic),2));

    public static final RegistryObject<Item> PurpleIronBow3 = ITEMS.register("purple_iron_bow3",
            ()->new PurpleIronBow(new Item.Properties().rarity(Utils.PurpleIronItalic),3));

    public static final RegistryObject<Item> PurpleIronSword = ITEMS.register("purple_iron_sword",
            ()->new PurpleIronSword(new Item.Properties().rarity(Utils.PurpleIronItalic),0));

    public static final RegistryObject<Item> PurpleIronSword1 = ITEMS.register("purple_iron_sword1",
            ()->new PurpleIronSword(new Item.Properties().rarity(Utils.PurpleIronItalic),1));

    public static final RegistryObject<Item> PurpleIronSword2 = ITEMS.register("purple_iron_sword2",
            ()->new PurpleIronSword(new Item.Properties().rarity(Utils.PurpleIronItalic),2));

    public static final RegistryObject<Item> PurpleIronSword3 = ITEMS.register("purple_iron_sword3",
            ()->new PurpleIronSword(new Item.Properties().rarity(Utils.PurpleIronItalic),3));

    public static final RegistryObject<Item> PurpleIronSceptre = ITEMS.register("purple_iron_sceptre",
            ()->new PurpleIronSceptre(new Item.Properties().rarity(Utils.PurpleIronItalic),0));

    public static final RegistryObject<Item> PurpleIronSceptre1 = ITEMS.register("purple_iron_sceptre1",
            ()->new PurpleIronSceptre(new Item.Properties().rarity(Utils.PurpleIronItalic),1));

    public static final RegistryObject<Item> PurpleIronSceptre2 = ITEMS.register("purple_iron_sceptre2",
            ()->new PurpleIronSceptre(new Item.Properties().rarity(Utils.PurpleIronItalic),2));

    public static final RegistryObject<Item> PurpleIronSceptre3 = ITEMS.register("purple_iron_sceptre3",
            ()->new PurpleIronSceptre(new Item.Properties().rarity(Utils.PurpleIronItalic),3));

    public static final RegistryObject<Item> PurpleIronBud1 = ITEMS.register("purple_iron_bud1",
            ()->new Item(new Item.Properties().rarity(Utils.PurpleIronBold)));

    public static final RegistryObject<Item> PurpleIronBud2 = ITEMS.register("purple_iron_bud2",
            ()->new Item(new Item.Properties().rarity(Utils.PurpleIronBold)));

    public static final RegistryObject<Item> PurpleIronBud3 = ITEMS.register("purple_iron_bud3",
            ()->new Item(new Item.Properties().rarity(Utils.PurpleIronBold)));

    public static final RegistryObject<Item> LotteryStar = ITEMS.register("lottery_star",
            ()->new Item(new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> LotteryPrefix = ITEMS.register("lottery_prefix",
            ()->new LotteryPrefix(new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> LakeRune0 = ITEMS.register("lake_rune0",
            ()->new LakeRune0(new Item.Properties().rarity(Utils.LakeBold)));

    public static final RegistryObject<Item> LakeRune1 = ITEMS.register("lake_rune1",
            ()->new LakeRune1(new Item.Properties().rarity(Utils.LakeBold)));

    public static final RegistryObject<Item> LakeRune2 = ITEMS.register("lake_rune2",
            ()->new LakeRune2(new Item.Properties().rarity(Utils.LakeBold)));

    public static final RegistryObject<Item> LakeRune3 = ITEMS.register("lake_rune3",
            ()->new LakeRune3(new Item.Properties().rarity(Utils.LakeBold)));

    public static final RegistryObject<Item> YxwgCurios2 = ITEMS.register("yxwg_curios2",
            ()->new YxwgCurios2(new Item.Properties().rarity(Utils.LifeBold)));

    public static final RegistryObject<Item> YxwgCurios2Paper = ITEMS.register("yxwg_curios2_paper",
            ()->new CustomizePaper(new Item.Properties().rarity(Utils.LifeBold),YxwgCurios2.get()));

    public static final RegistryObject<Item> YxwgCurios2Passive1 = ITEMS.register("yxwg_curios2_passive1",
            ()->new Item(new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> YxwgCurios2Passive2 = ITEMS.register("yxwg_curios2_passive2",
            ()->new Item(new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> YxwgCurios2Passive3 = ITEMS.register("yxwg_curios2_passive3",
            ()->new Item(new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> YxwgCuriospa2Passive5 = ITEMS.register("yxwg_curios2_passive5",
            ()->new Item(new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> AttackCurios0 = ITEMS.register("attack_curios_0",
            ()->new AttackCurios0(new Item.Properties().rarity(Utils.MagmaBold)));

    public static final RegistryObject<Item> BowCurios0 = ITEMS.register("bow_curios_0",
            ()->new BowCurios0(new Item.Properties().rarity(Utils.LifeBold)));

    public static final RegistryObject<Item> ManaCurios0 = ITEMS.register("mana_curios_0",
            ()->new ManaCurios0(new Item.Properties().rarity(Utils.EvokerBold)));

    public static final RegistryObject<Item> AttackSword0 = ITEMS.register("attack_sword_0",
            ()->new Item(new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> WcCurios = ITEMS.register("wc_curios",
            ()->new WcndymlgbCurios(new Item.Properties().rarity(Utils.SkyBold)));

    public static final RegistryObject<Item> WcCuriosPaper = ITEMS.register("wc_curios_paper",
            ()->new CustomizePaper(new Item.Properties().rarity(Utils.SkyBold),WcCurios.get()));

    public static final RegistryObject<Item> QiFuCurios1 = ITEMS.register("qifu_curios1",
            ()->new QiFuCurios1(new Item.Properties().rarity(Utils.BloodManaBold)));

    public static final RegistryObject<Item> QiFuCurios1Paper = ITEMS.register("qifu_curios1_paper",
            ()->new CustomizePaper(new Item.Properties().rarity(Utils.BloodManaBold),QiFuCurios1.get()));

    public static final RegistryObject<Item> QiFuCurios1Passive1 = ITEMS.register("qifu_curios1_passive1",
            ()->new Item(new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> QiFuCurios1Passive2 = ITEMS.register("qifu_curios1_passive2",
            ()->new Item(new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> QiFuCurios1Passive3 = ITEMS.register("qifu_curios1_passive3",
            ()->new Item(new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> QingTuan = ITEMS.register("qing_tuan",
            ()->new QingTuan(new Item.Properties().rarity(Utils.ForestBold)));
    /*    public static final RegistryObject<Item> BloodPower = ITEMS.register("blood_power",
            ()->new Item(new Item.Properties().rarity(Utils.BloodManaItalic)));

    public static final RegistryObject<Item> BloodPower1 = ITEMS.register("blood_power1",
            ()->new Item(new Item.Properties().rarity(Utils.BloodManaItalic)));

    public static final RegistryObject<Item> BloodPower2 = ITEMS.register("blood_power2",
            ()->new Item(new Item.Properties().rarity(Utils.BloodManaItalic)));

    public static final RegistryObject<Item> BloodPower3 = ITEMS.register("blood_power3",
            ()->new Item(new Item.Properties().rarity(Utils.BloodManaItalic)));

    public static final RegistryObject<Item> BloodPower_0 = ITEMS.register("blood_power_0",
            ()->new Item(new Item.Properties().rarity(Utils.EvokerItalic)));

    public static final RegistryObject<Item> BloodPower_1 = ITEMS.register("blood_power_1",
            ()->new Item(new Item.Properties().rarity(Utils.EvokerItalic)));

    public static final RegistryObject<Item> BloodPower_2 = ITEMS.register("blood_power_2",
            ()->new Item(new Item.Properties().rarity(Utils.EvokerItalic)));

    public static final RegistryObject<Item> BloodPower_3 = ITEMS.register("blood_power_3",
            ()->new Item(new Item.Properties().rarity(Utils.EvokerItalic)));*/


}

