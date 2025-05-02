package fun.wraq.networking;

import fun.wraq.Items.DevelopmentTools.rail.RailwayPillarSetToolModeC2SPacket;
import fun.wraq.Items.MainStory_1.NearestSpawnPointS2CPacket;
import fun.wraq.blocks.blocks.inject.InjectC2SPacket;
import fun.wraq.common.Compute;
import fun.wraq.common.util.ClientUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.events.mob.instance.instances.tower.network.ManaTowerS2CPacket;
import fun.wraq.networking.bowAndSceptreActive.CommonActiveC2SPacket;
import fun.wraq.networking.dailyMission.DailyMissionContentS2CPacket;
import fun.wraq.networking.dailyMission.DailyMissionFinishedRequestC2SPacket;
import fun.wraq.networking.dailyMission.DailyMissionFinishedTimeS2CPacket;
import fun.wraq.networking.dailyMission.DailyMissionRequestC2SPacket;
import fun.wraq.networking.hud.CoolDownTimeS2CPacket;
import fun.wraq.networking.hud.DebuffTimeS2CPacket;
import fun.wraq.networking.hud.EffectLastTimeS2CPacket;
import fun.wraq.networking.hud.RemoveDebuffTimeS2CPacket;
import fun.wraq.networking.misc.AnimationPackets.*;
import fun.wraq.networking.misc.AttributePackets.*;
import fun.wraq.networking.misc.CodeSceptrePackets.CodeC2SPacket;
import fun.wraq.networking.misc.*;
import fun.wraq.networking.misc.EarthPower.EarthPowerC2SPacket;
import fun.wraq.networking.misc.EarthPower.EarthPowerS2CPacket;
import fun.wraq.networking.misc.EntropyPackets.EntropyS2CPacket;
import fun.wraq.networking.misc.Limit.CheckBlockLimitS2CPacket;
import fun.wraq.networking.misc.Limit.RemoveBlockLimitC2SPacket;
import fun.wraq.networking.misc.Limit.ScreenCloseC2SPacket;
import fun.wraq.networking.misc.ParticlePackets.*;
import fun.wraq.networking.misc.ParticlePackets.EffectParticle.CritHitParticleS2CPacket;
import fun.wraq.networking.misc.ParticlePackets.EffectParticle.DamageDecreaseParticleS2CPacket;
import fun.wraq.networking.misc.ParticlePackets.EffectParticle.DefencePenetrationParticleS2CPacket;
import fun.wraq.networking.misc.ParticlePackets.EffectParticle.ManaDefencePenetrationParticleS2CPacket;
import fun.wraq.networking.misc.ParticlePackets.ElementParticle.*;
import fun.wraq.networking.misc.ParticlePackets.NewParticlePackets.*;
import fun.wraq.networking.misc.PrefixPackets.PrefixS2CPacket;
import fun.wraq.networking.misc.SkillPackets.*;
import fun.wraq.networking.misc.SkillPackets.Charging.*;
import fun.wraq.networking.misc.SmartPhonePackets.*;
import fun.wraq.networking.misc.SmartPhonePackets.Currency.*;
import fun.wraq.networking.misc.SoundsPackets.SoundsS2CPacket;
import fun.wraq.networking.misc.TeamPackets.*;
import fun.wraq.networking.misc.ToolTipPackets.DailyMissionS2CPacket;
import fun.wraq.networking.misc.USE.*;
import fun.wraq.networking.misc.attack.AttackRequestC2SPacket;
import fun.wraq.networking.misc.attack.BowAttackRequestC2SPacket;
import fun.wraq.networking.misc.attack.ManaAttackRequestC2SPacket;
import fun.wraq.networking.reputation.ReputationBuyRequestC2SPacket;
import fun.wraq.networking.reputation.ReputationValueS2CPacket;
import fun.wraq.networking.reputationMission.*;
import fun.wraq.networking.unSorted.*;
import fun.wraq.process.func.effect.BlindTickS2CPacket;
import fun.wraq.process.func.effect.SilentTickS2CPacket;
import fun.wraq.process.func.guide.networking.GuideDisplayS2CPacket;
import fun.wraq.process.func.guide.networking.GuideFinishC2SPacket;
import fun.wraq.process.func.guide.networking.GuideHudCloseStatusS2CPacket;
import fun.wraq.process.func.guide.networking.GuideStageS2CPacket;
import fun.wraq.process.func.particle.packets.*;
import fun.wraq.process.func.plan.networking.DailySupplyC2SPacket;
import fun.wraq.process.func.plan.networking.DailySupplyS2CPacket;
import fun.wraq.process.func.plan.networking.PlanDateAndTierS2CPacket;
import fun.wraq.process.func.plan.networking.mission.PlanMissionCancelRequestC2SPacket;
import fun.wraq.process.func.plan.networking.mission.PlanMissionFinishedRequestC2SPacket;
import fun.wraq.process.func.plan.networking.mission.PlanMissionRequestC2SPacket;
import fun.wraq.process.func.rank.network.RankChangeS2CPacket;
import fun.wraq.process.func.rank.network.RankDataS2CPacket;
import fun.wraq.process.func.security.mac.network.MacC2SPacket;
import fun.wraq.process.func.security.mac.network.MacRequestS2CPacket;
import fun.wraq.process.system.element.networking.*;
import fun.wraq.process.system.endlessinstance.network.EndlessInstanceKillCountS2CPacket;
import fun.wraq.process.system.entrustment.mob.MobKillEntrustmentInfoS2CPacket;
import fun.wraq.process.system.estate.EstateDataS2CPacket;
import fun.wraq.process.system.forge.networking.*;
import fun.wraq.process.system.lottery.networking.LotteryRewardTimeS2CPacket;
import fun.wraq.process.system.missions.netWorking.*;
import fun.wraq.process.system.point.network.PointDataS2CPacket;
import fun.wraq.process.system.randomStore.networking.TradeListClearS2CPacket;
import fun.wraq.process.system.randomStore.networking.TradeListS2CPacket;
import fun.wraq.process.system.randomevent.impl.special.SpringMobDamageS2CPacket;
import fun.wraq.process.system.skill.skillv2.network.*;
import fun.wraq.process.system.smelt.*;
import fun.wraq.process.system.teamInstance.networking.NewTeamInstanceClearS2CPacket;
import fun.wraq.process.system.teamInstance.networking.NewTeamInstanceJoinedPlayerInfoS2CPacket;
import fun.wraq.process.system.teamInstance.networking.NewTeamInstancePrepareInfoS2CPacket;
import fun.wraq.process.system.tower.TowerChallengeC2SPacket;
import fun.wraq.process.system.tower.TowerStatusS2CPacket;
import fun.wraq.process.system.vp.networking.VpStoreBuyC2SPacket;
import fun.wraq.process.system.vp.networking.VpValueS2CPacket;
import fun.wraq.process.system.wayPoints.networking.ClientWayPointS2CPacket;
import fun.wraq.process.system.wayPoints.networking.SpecificWayPointAddS2CPacket;
import fun.wraq.process.system.wayPoints.networking.SpecificWayPointRemoveS2CPacket;
import fun.wraq.render.gui.trade.SingleItemChangeC2SPacket;
import fun.wraq.render.gui.trade.SingleItemChangeFullDataS2CPacket;
import fun.wraq.render.gui.trade.SingleItemChangeSingleRecipeTimeS2CPacket;
import fun.wraq.render.hud.networking.*;
import fun.wraq.series.overworld.sun.network.TotalKillCountS2CPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModNetworking {
    private static SimpleChannel INSTANCE;
    private static int packetID = 0;

    private static int id() {
        return packetID++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(Utils.MOD_ID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(UseC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(UseC2SPacket::new)
                .encoder(UseC2SPacket::toBytes)
                .consumerMainThread(UseC2SPacket::handle)
                .add();
        net.messageBuilder(MoveToC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(MoveToC2SPacket::new)
                .encoder(MoveToC2SPacket::toBytes)
                .consumerMainThread(MoveToC2SPacket::handle)
                .add();
        net.messageBuilder(ManaSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ManaSyncS2CPacket::new)
                .encoder(ManaSyncS2CPacket::toBytes)
                .consumerMainThread(ManaSyncS2CPacket::handle)
                .add();
        net.messageBuilder(UtilsLakeSwordS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(UtilsLakeSwordS2CPacket::new)
                .encoder(UtilsLakeSwordS2CPacket::toBytes)
                .consumerMainThread(UtilsLakeSwordS2CPacket::handle)
                .add();
        net.messageBuilder(UtilsSnowSwordS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(UtilsSnowSwordS2CPacket::new)
                .encoder(UtilsSnowSwordS2CPacket::toBytes)
                .consumerMainThread(UtilsSnowSwordS2CPacket::handle)
                .add();
        net.messageBuilder(UtilsParticleS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(UtilsParticleS2CPacket::new)
                .encoder(UtilsParticleS2CPacket::toBytes)
                .consumerMainThread(UtilsParticleS2CPacket::handle)
                .add();
        net.messageBuilder(SnowSwordParticleC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SnowSwordParticleC2SPacket::new)
                .encoder(SnowSwordParticleC2SPacket::toBytes)
                .consumerMainThread(SnowSwordParticleC2SPacket::handle)
                .add();
        net.messageBuilder(SnowSwordParticleS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SnowSwordParticleS2CPacket::new)
                .encoder(SnowSwordParticleS2CPacket::toBytes)
                .consumerMainThread(SnowSwordParticleS2CPacket::handle)
                .add();
        net.messageBuilder(SoundsS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SoundsS2CPacket::new)
                .encoder(SoundsS2CPacket::toBytes)
                .consumerMainThread(SoundsS2CPacket::handle)
                .add();
        net.messageBuilder(DailyMissionS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(DailyMissionS2CPacket::new)
                .encoder(DailyMissionS2CPacket::toBytes)
                .consumerMainThread(DailyMissionS2CPacket::handle)
                .add();
        net.messageBuilder(ShieldSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ShieldSyncS2CPacket::new)
                .encoder(ShieldSyncS2CPacket::toBytes)
                .consumerMainThread(ShieldSyncS2CPacket::handle)
                .add();
        net.messageBuilder(Attribute0S2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(Attribute0S2CPacket::new)
                .encoder(Attribute0S2CPacket::toBytes)
                .consumerMainThread(Attribute0S2CPacket::handle)
                .add();
        net.messageBuilder(Attribute1S2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(Attribute1S2CPacket::new)
                .encoder(Attribute1S2CPacket::toBytes)
                .consumerMainThread(Attribute1S2CPacket::handle)
                .add();
        net.messageBuilder(Attribute2S2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(Attribute2S2CPacket::new)
                .encoder(Attribute2S2CPacket::toBytes)
                .consumerMainThread(Attribute2S2CPacket::handle)
                .add();
        net.messageBuilder(Attribute3S2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(Attribute3S2CPacket::new)
                .encoder(Attribute3S2CPacket::toBytes)
                .consumerMainThread(Attribute3S2CPacket::handle)
                .add();
        net.messageBuilder(Attribute4S2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(Attribute4S2CPacket::new)
                .encoder(Attribute4S2CPacket::toBytes)
                .consumerMainThread(Attribute4S2CPacket::handle)
                .add();
        net.messageBuilder(RuneHud0S2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(RuneHud0S2CPacket::new)
                .encoder(RuneHud0S2CPacket::toBytes)
                .consumerMainThread(RuneHud0S2CPacket::handle)
                .add();
        net.messageBuilder(CritHitParticleS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(CritHitParticleS2CPacket::new)
                .encoder(CritHitParticleS2CPacket::toBytes)
                .consumerMainThread(CritHitParticleS2CPacket::handle)
                .add();
        net.messageBuilder(SmartPhoneS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SmartPhoneS2CPacket::new)
                .encoder(SmartPhoneS2CPacket::toBytes)
                .consumerMainThread(SmartPhoneS2CPacket::handle)
                .add();
        net.messageBuilder(SilverCoinC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SilverCoinC2SPacket::new)
                .encoder(SilverCoinC2SPacket::toBytes)
                .consumerMainThread(SilverCoinC2SPacket::handle)
                .add();
        net.messageBuilder(GoldCoinC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(GoldCoinC2SPacket::new)
                .encoder(GoldCoinC2SPacket::toBytes)
                .consumerMainThread(GoldCoinC2SPacket::handle)
                .add();
        net.messageBuilder(MarketDataS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(MarketDataS2CPacket::new)
                .encoder(MarketDataS2CPacket::toBytes)
                .consumerMainThread(MarketDataS2CPacket::handle)
                .add();
        net.messageBuilder(BuyCheckC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(BuyCheckC2SPacket::new)
                .encoder(BuyCheckC2SPacket::toBytes)
                .consumerMainThread(BuyCheckC2SPacket::handle)
                .add();
        net.messageBuilder(RequestGetC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(RequestGetC2SPacket::new)
                .encoder(RequestGetC2SPacket::toBytes)
                .consumerMainThread(RequestGetC2SPacket::handle)
                .add();
        net.messageBuilder(PrefixS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(PrefixS2CPacket::new)
                .encoder(PrefixS2CPacket::toBytes)
                .consumerMainThread(PrefixS2CPacket::handle)
                .add();
        net.messageBuilder(CodeC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(CodeC2SPacket::new)
                .encoder(CodeC2SPacket::toBytes)
                .consumerMainThread(CodeC2SPacket::handle)
                .add();
        net.messageBuilder(OpenSmartPhoneS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(OpenSmartPhoneS2CPacket::new)
                .encoder(OpenSmartPhoneS2CPacket::toBytes)
                .consumerMainThread(OpenSmartPhoneS2CPacket::handle)
                .add();
        net.messageBuilder(BlackForestRecallParticleS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(BlackForestRecallParticleS2CPacket::new)
                .encoder(BlackForestRecallParticleS2CPacket::toBytes)
                .consumerMainThread(BlackForestRecallParticleS2CPacket::handle)
                .add();
        net.messageBuilder(NetherRecallParticleS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(NetherRecallParticleS2CPacket::new)
                .encoder(NetherRecallParticleS2CPacket::toBytes)
                .consumerMainThread(NetherRecallParticleS2CPacket::handle)
                .add();
        net.messageBuilder(ScreenCloseC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ScreenCloseC2SPacket::new)
                .encoder(ScreenCloseC2SPacket::toBytes)
                .consumerMainThread(ScreenCloseC2SPacket::handle)
                .add();
        net.messageBuilder(SkillRequestC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SkillRequestC2SPacket::new)
                .encoder(SkillRequestC2SPacket::toBytes)
                .consumerMainThread(SkillRequestC2SPacket::handle)
                .add();
        net.messageBuilder(SkillPointS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SkillPointS2CPacket::new)
                .encoder(SkillPointS2CPacket::toBytes)
                .consumerMainThread(SkillPointS2CPacket::handle)
                .add();
        net.messageBuilder(AbilityPointS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(AbilityPointS2CPacket::new)
                .encoder(AbilityPointS2CPacket::toBytes)
                .consumerMainThread(AbilityPointS2CPacket::handle)
                .add();
        net.messageBuilder(AbilityDataS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(AbilityDataS2CPacket::new)
                .encoder(AbilityDataS2CPacket::toBytes)
                .consumerMainThread(AbilityDataS2CPacket::handle)
                .add();
        net.messageBuilder(SkillDataS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SkillDataS2CPacket::new)
                .encoder(SkillDataS2CPacket::toBytes)
                .consumerMainThread(SkillDataS2CPacket::handle)
                .add();
        net.messageBuilder(AbilityDataC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(AbilityDataC2SPacket::new)
                .encoder(AbilityDataC2SPacket::toBytes)
                .consumerMainThread(AbilityDataC2SPacket::handle)
                .add();
        net.messageBuilder(SkillDataC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SkillDataC2SPacket::new)
                .encoder(SkillDataC2SPacket::toBytes)
                .consumerMainThread(SkillDataC2SPacket::handle)
                .add();
        net.messageBuilder(SkillSaveC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SkillSaveC2SPacket::new)
                .encoder(SkillSaveC2SPacket::toBytes)
                .consumerMainThread(SkillSaveC2SPacket::handle)
                .add();
        net.messageBuilder(SkillSaveS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SkillSaveS2CPacket::new)
                .encoder(SkillSaveS2CPacket::toBytes)
                .consumerMainThread(SkillSaveS2CPacket::handle)
                .add();
        net.messageBuilder(SwordSkill12S2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SwordSkill12S2CPacket::new)
                .encoder(SwordSkill12S2CPacket::toBytes)
                .consumerMainThread(SwordSkill12S2CPacket::handle)
                .add();
        net.messageBuilder(BowSkill12S2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(BowSkill12S2CPacket::new)
                .encoder(BowSkill12S2CPacket::toBytes)
                .consumerMainThread(BowSkill12S2CPacket::handle)
                .add();
        net.messageBuilder(ManaSkill12S2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ManaSkill12S2CPacket::new)
                .encoder(ManaSkill12S2CPacket::toBytes)
                .consumerMainThread(ManaSkill12S2CPacket::handle)
                .add();
        net.messageBuilder(ChargedClearS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ChargedClearS2CPacket::new)
                .encoder(ChargedClearS2CPacket::toBytes)
                .consumerMainThread(ChargedClearS2CPacket::handle)
                .add();
        net.messageBuilder(ChargedFullC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ChargedFullC2SPacket::new)
                .encoder(ChargedFullC2SPacket::toBytes)
                .consumerMainThread(ChargedFullC2SPacket::handle)
                .add();
        net.messageBuilder(SkillImageS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SkillImageS2CPacket::new)
                .encoder(SkillImageS2CPacket::toBytes)
                .consumerMainThread(SkillImageS2CPacket::handle)
                .add();
        net.messageBuilder(EntropyS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(EntropyS2CPacket::new)
                .encoder(EntropyS2CPacket::toBytes)
                .consumerMainThread(EntropyS2CPacket::handle)
                .add();
        net.messageBuilder(ManaAttackParticleS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ManaAttackParticleS2CPacket::new)
                .encoder(ManaAttackParticleS2CPacket::toBytes)
                .consumerMainThread(ManaAttackParticleS2CPacket::handle)
                .add();
        net.messageBuilder(DefencePenetrationParticleS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(DefencePenetrationParticleS2CPacket::new)
                .encoder(DefencePenetrationParticleS2CPacket::toBytes)
                .consumerMainThread(DefencePenetrationParticleS2CPacket::handle)
                .add();
        net.messageBuilder(ManaDefencePenetrationParticleS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ManaDefencePenetrationParticleS2CPacket::new)
                .encoder(ManaDefencePenetrationParticleS2CPacket::toBytes)
                .consumerMainThread(ManaDefencePenetrationParticleS2CPacket::handle)
                .add();
        net.messageBuilder(SlowDownParticleS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SlowDownParticleS2CPacket::new)
                .encoder(SlowDownParticleS2CPacket::toBytes)
                .consumerMainThread(SlowDownParticleS2CPacket::handle)
                .add();
        net.messageBuilder(DamageDecreaseParticleS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(DamageDecreaseParticleS2CPacket::new)
                .encoder(DamageDecreaseParticleS2CPacket::toBytes)
                .consumerMainThread(DamageDecreaseParticleS2CPacket::handle)
                .add();
        net.messageBuilder(VerticleCircleParticleS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(VerticleCircleParticleS2CPacket::new)
                .encoder(VerticleCircleParticleS2CPacket::toBytes)
                .consumerMainThread(VerticleCircleParticleS2CPacket::handle)
                .add();
        net.messageBuilder(EntityEffectVerticleCircleParticleS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(EntityEffectVerticleCircleParticleS2CPacket::new)
                .encoder(EntityEffectVerticleCircleParticleS2CPacket::toBytes)
                .consumerMainThread(EntityEffectVerticleCircleParticleS2CPacket::handle)
                .add();
        net.messageBuilder(EntityFaceCircleParticleS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(EntityFaceCircleParticleS2CPacket::new)
                .encoder(EntityFaceCircleParticleS2CPacket::toBytes)
                .consumerMainThread(EntityFaceCircleParticleS2CPacket::handle)
                .add();
        net.messageBuilder(FaceCircleParticleS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(FaceCircleParticleS2CPacket::new)
                .encoder(FaceCircleParticleS2CPacket::toBytes)
                .consumerMainThread(FaceCircleParticleS2CPacket::handle)
                .add();
        net.messageBuilder(LineParticleS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(LineParticleS2CPacket::new)
                .encoder(LineParticleS2CPacket::toBytes)
                .consumerMainThread(LineParticleS2CPacket::handle)
                .add();
        net.messageBuilder(RandomMoveParticleS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(RandomMoveParticleS2CPacket::new)
                .encoder(RandomMoveParticleS2CPacket::toBytes)
                .consumerMainThread(RandomMoveParticleS2CPacket::handle)
                .add();
        net.messageBuilder(EntityFaceConnectCircleParticleS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(EntityFaceConnectCircleParticleS2CPacket::new)
                .encoder(EntityFaceConnectCircleParticleS2CPacket::toBytes)
                .consumerMainThread(EntityFaceConnectCircleParticleS2CPacket::handle)
                .add();
        net.messageBuilder(AttackRequestC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(AttackRequestC2SPacket::new)
                .encoder(AttackRequestC2SPacket::toBytes)
                .consumerMainThread(AttackRequestC2SPacket::handle)
                .add();
        net.messageBuilder(UseRequestC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(UseRequestC2SPacket::new)
                .encoder(UseRequestC2SPacket::toBytes)
                .consumerMainThread(UseRequestC2SPacket::handle)
                .add();
        net.messageBuilder(AnimationTickResetS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(AnimationTickResetS2CPacket::new)
                .encoder(AnimationTickResetS2CPacket::toBytes)
                .consumerMainThread(AnimationTickResetS2CPacket::handle)
                .add();
        net.messageBuilder(BowAttackRequestC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(BowAttackRequestC2SPacket::new)
                .encoder(BowAttackRequestC2SPacket::toBytes)
                .consumerMainThread(BowAttackRequestC2SPacket::handle)
                .add();
        net.messageBuilder(ManaAttackRequestC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ManaAttackRequestC2SPacket::new)
                .encoder(ManaAttackRequestC2SPacket::toBytes)
                .consumerMainThread(ManaAttackRequestC2SPacket::handle)
                .add();
        net.messageBuilder(RollingRequestC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(RollingRequestC2SPacket::new)
                .encoder(RollingRequestC2SPacket::toBytes)
                .consumerMainThread(RollingRequestC2SPacket::handle)
                .add();
        net.messageBuilder(RollingS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(RollingS2CPacket::new)
                .encoder(RollingS2CPacket::toBytes)
                .consumerMainThread(RollingS2CPacket::handle)
                .add();
        net.messageBuilder(SwiftSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SwiftSyncS2CPacket::new)
                .encoder(SwiftSyncS2CPacket::toBytes)
                .consumerMainThread(SwiftSyncS2CPacket::handle)
                .add();
        net.messageBuilder(MissionInfoS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(MissionInfoS2CPacket::new)
                .encoder(MissionInfoS2CPacket::toBytes)
                .consumerMainThread(MissionInfoS2CPacket::handle)
                .add();
        net.messageBuilder(MissionCompletedS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(MissionCompletedS2CPacket::new)
                .encoder(MissionCompletedS2CPacket::toBytes)
                .consumerMainThread(MissionCompletedS2CPacket::handle)
                .add();
        net.messageBuilder(MissionResetS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(MissionResetS2CPacket::new)
                .encoder(MissionResetS2CPacket::toBytes)
                .consumerMainThread(MissionResetS2CPacket::handle)
                .add();
        net.messageBuilder(PlayerConfirmC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(PlayerConfirmC2SPacket::new)
                .encoder(PlayerConfirmC2SPacket::toBytes)
                .consumerMainThread(PlayerConfirmC2SPacket::handle)
                .add();
        net.messageBuilder(PlayerLeaveC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(PlayerLeaveC2SPacket::new)
                .encoder(PlayerLeaveC2SPacket::toBytes)
                .consumerMainThread(PlayerLeaveC2SPacket::handle)
                .add();
        net.messageBuilder(PlayerRequestC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(PlayerRequestC2SPacket::new)
                .encoder(PlayerRequestC2SPacket::toBytes)
                .consumerMainThread(PlayerRequestC2SPacket::handle)
                .add();
        net.messageBuilder(TeamConfirmC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(TeamConfirmC2SPacket::new)
                .encoder(TeamConfirmC2SPacket::toBytes)
                .consumerMainThread(TeamConfirmC2SPacket::handle)
                .add();
        net.messageBuilder(TeamCreateC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(TeamCreateC2SPacket::new)
                .encoder(TeamCreateC2SPacket::toBytes)
                .consumerMainThread(TeamCreateC2SPacket::handle)
                .add();
        net.messageBuilder(TeamDeleteC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(TeamDeleteC2SPacket::new)
                .encoder(TeamDeleteC2SPacket::toBytes)
                .consumerMainThread(TeamDeleteC2SPacket::handle)
                .add();
        net.messageBuilder(TeamInfoRequestC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(TeamInfoRequestC2SPacket::new)
                .encoder(TeamInfoRequestC2SPacket::toBytes)
                .consumerMainThread(TeamInfoRequestC2SPacket::handle)
                .add();
        net.messageBuilder(TeamRemovePlayerC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(TeamRemovePlayerC2SPacket::new)
                .encoder(TeamRemovePlayerC2SPacket::toBytes)
                .consumerMainThread(TeamRemovePlayerC2SPacket::handle)
                .add();
        net.messageBuilder(PlayerRequestS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(PlayerRequestS2CPacket::new)
                .encoder(PlayerRequestS2CPacket::toBytes)
                .consumerMainThread(PlayerRequestS2CPacket::handle)
                .add();
        net.messageBuilder(TeamInfoS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(TeamInfoS2CPacket::new)
                .encoder(TeamInfoS2CPacket::toBytes)
                .consumerMainThread(TeamInfoS2CPacket::handle)
                .add();
        net.messageBuilder(TeamInviteRequestS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(TeamInviteRequestS2CPacket::new)
                .encoder(TeamInviteRequestS2CPacket::toBytes)
                .consumerMainThread(TeamInviteRequestS2CPacket::handle)
                .add();
        net.messageBuilder(TeamDeleteS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(TeamDeleteS2CPacket::new)
                .encoder(TeamDeleteS2CPacket::toBytes)
                .consumerMainThread(TeamDeleteS2CPacket::handle)
                .add();
        net.messageBuilder(TeamNameConfirmC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(TeamNameConfirmC2SPacket::new)
                .encoder(TeamNameConfirmC2SPacket::toBytes)
                .consumerMainThread(TeamNameConfirmC2SPacket::handle)
                .add();
        net.messageBuilder(TeamInviteC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(TeamInviteC2SPacket::new)
                .encoder(TeamInviteC2SPacket::toBytes)
                .consumerMainThread(TeamInviteC2SPacket::handle)
                .add();
        net.messageBuilder(TeamInfoResetS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(TeamInfoResetS2CPacket::new)
                .encoder(TeamInfoResetS2CPacket::toBytes)
                .consumerMainThread(TeamInfoResetS2CPacket::handle)
                .add();
        net.messageBuilder(ScreenSetS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ScreenSetS2CPacket::new)
                .encoder(ScreenSetS2CPacket::toBytes)
                .consumerMainThread(ScreenSetS2CPacket::handle)
                .add();
        net.messageBuilder(ClientLevelTypeS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ClientLevelTypeS2CPacket::new)
                .encoder(ClientLevelTypeS2CPacket::toBytes)
                .consumerMainThread(ClientLevelTypeS2CPacket::handle)
                .add();
        net.messageBuilder(TeamScreenOpenS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(TeamScreenOpenS2CPacket::new)
                .encoder(TeamScreenOpenS2CPacket::toBytes)
                .consumerMainThread(TeamScreenOpenS2CPacket::handle)
                .add();
        net.messageBuilder(TeamScreenOpenRequestC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(TeamScreenOpenRequestC2SPacket::new)
                .encoder(TeamScreenOpenRequestC2SPacket::toBytes)
                .consumerMainThread(TeamScreenOpenRequestC2SPacket::handle)
                .add();
        net.messageBuilder(PlayerInfoS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(PlayerInfoS2CPacket::new)
                .encoder(PlayerInfoS2CPacket::toBytes)
                .consumerMainThread(PlayerInfoS2CPacket::handle)
                .add();
        net.messageBuilder(PsValueS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(PsValueS2CPacket::new)
                .encoder(PsValueS2CPacket::toBytes)
                .consumerMainThread(PsValueS2CPacket::handle)
                .add();
        net.messageBuilder(PlayerRequestListS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(PlayerRequestListS2CPacket::new)
                .encoder(PlayerRequestListS2CPacket::toBytes)
                .consumerMainThread(PlayerRequestListS2CPacket::handle)
                .add();
        net.messageBuilder(TeamInviteListS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(TeamInviteListS2CPacket::new)
                .encoder(TeamInviteListS2CPacket::toBytes)
                .consumerMainThread(TeamInviteListS2CPacket::handle)
                .add();
        net.messageBuilder(InstanceChooseC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(InstanceChooseC2SPacket::new)
                .encoder(InstanceChooseC2SPacket::toBytes)
                .consumerMainThread(InstanceChooseC2SPacket::handle)
                .add();
        net.messageBuilder(DisperseParticleS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(DisperseParticleS2CPacket::new)
                .encoder(DisperseParticleS2CPacket::toBytes)
                .consumerMainThread(DisperseParticleS2CPacket::handle)
                .add();
        net.messageBuilder(GatherParticleS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(GatherParticleS2CPacket::new)
                .encoder(GatherParticleS2CPacket::toBytes)
                .consumerMainThread(GatherParticleS2CPacket::handle)
                .add();
        net.messageBuilder(Boss2AnimationStartS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(Boss2AnimationStartS2CPacket::new)
                .encoder(Boss2AnimationStartS2CPacket::toBytes)
                .consumerMainThread(Boss2AnimationStartS2CPacket::handle)
                .add();
        net.messageBuilder(ColdSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ColdSyncS2CPacket::new)
                .encoder(ColdSyncS2CPacket::toBytes)
                .consumerMainThread(ColdSyncS2CPacket::handle)
                .add();
        net.messageBuilder(TimeS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(TimeS2CPacket::new)
                .encoder(TimeS2CPacket::toBytes)
                .consumerMainThread(TimeS2CPacket::handle)
                .add();
        net.messageBuilder(DailyMissionFinishedTimeS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(DailyMissionFinishedTimeS2CPacket::new)
                .encoder(DailyMissionFinishedTimeS2CPacket::toBytes)
                .consumerMainThread(DailyMissionFinishedTimeS2CPacket::handle)
                .add();
        net.messageBuilder(DailyMissionContentS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(DailyMissionContentS2CPacket::new)
                .encoder(DailyMissionContentS2CPacket::toBytes)
                .consumerMainThread(DailyMissionContentS2CPacket::handle)
                .add();
        net.messageBuilder(DailyMissionFinishedRequestC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(DailyMissionFinishedRequestC2SPacket::new)
                .encoder(DailyMissionFinishedRequestC2SPacket::toBytes)
                .consumerMainThread(DailyMissionFinishedRequestC2SPacket::handle)
                .add();
        net.messageBuilder(DailyMissionRequestC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(DailyMissionRequestC2SPacket::new)
                .encoder(DailyMissionRequestC2SPacket::toBytes)
                .consumerMainThread(DailyMissionRequestC2SPacket::handle)
                .add();
        net.messageBuilder(ReputationMissionStartTimeS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ReputationMissionStartTimeS2CPacket::new)
                .encoder(ReputationMissionStartTimeS2CPacket::toBytes)
                .consumerMainThread(ReputationMissionStartTimeS2CPacket::handle)
                .add();
        net.messageBuilder(ReputationMissionContentS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ReputationMissionContentS2CPacket::new)
                .encoder(ReputationMissionContentS2CPacket::toBytes)
                .consumerMainThread(ReputationMissionContentS2CPacket::handle)
                .add();
        net.messageBuilder(ReputationMissionFinishedRequestC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ReputationMissionFinishedRequestC2SPacket::new)
                .encoder(ReputationMissionFinishedRequestC2SPacket::toBytes)
                .consumerMainThread(ReputationMissionFinishedRequestC2SPacket::handle)
                .add();
        net.messageBuilder(ReputationMissionRequestC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ReputationMissionRequestC2SPacket::new)
                .encoder(ReputationMissionRequestC2SPacket::toBytes)
                .consumerMainThread(ReputationMissionRequestC2SPacket::handle)
                .add();
        net.messageBuilder(ReputationMissionCancelRequestC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ReputationMissionCancelRequestC2SPacket::new)
                .encoder(ReputationMissionCancelRequestC2SPacket::toBytes)
                .consumerMainThread(ReputationMissionCancelRequestC2SPacket::handle)
                .add();
        net.messageBuilder(ReputationMissionAllowRequestTimeS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ReputationMissionAllowRequestTimeS2CPacket::new)
                .encoder(ReputationMissionAllowRequestTimeS2CPacket::toBytes)
                .consumerMainThread(ReputationMissionAllowRequestTimeS2CPacket::handle)
                .add();
        net.messageBuilder(ReputationBuyRequestC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ReputationBuyRequestC2SPacket::new)
                .encoder(ReputationBuyRequestC2SPacket::toBytes)
                .consumerMainThread(ReputationBuyRequestC2SPacket::handle)
                .add();
        net.messageBuilder(ReputationValueS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ReputationValueS2CPacket::new)
                .encoder(ReputationValueS2CPacket::toBytes)
                .consumerMainThread(ReputationValueS2CPacket::handle)
                .add();
        net.messageBuilder(PlayerIsNearbyCampfireC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(PlayerIsNearbyCampfireC2SPacket::new)
                .encoder(PlayerIsNearbyCampfireC2SPacket::toBytes)
                .consumerMainThread(PlayerIsNearbyCampfireC2SPacket::handle)
                .add();
        net.messageBuilder(TradeBuyRequestC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(TradeBuyRequestC2SPacket::new)
                .encoder(TradeBuyRequestC2SPacket::toBytes)
                .consumerMainThread(TradeBuyRequestC2SPacket::handle)
                .add();
        net.messageBuilder(VillagerTradeScreenS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(VillagerTradeScreenS2CPacket::new)
                .encoder(VillagerTradeScreenS2CPacket::toBytes)
                .consumerMainThread(VillagerTradeScreenS2CPacket::handle)
                .add();
        net.messageBuilder(WaterBlockCountsC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(WaterBlockCountsC2SPacket::new)
                .encoder(WaterBlockCountsC2SPacket::toBytes)
                .consumerMainThread(WaterBlockCountsC2SPacket::handle)
                .add();
        net.messageBuilder(DingS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(DingS2CPacket::new)
                .encoder(DingS2CPacket::toBytes)
                .consumerMainThread(DingS2CPacket::handle)
                .add();
        net.messageBuilder(MineHatConfirmC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(MineHatConfirmC2SPacket::new)
                .encoder(MineHatConfirmC2SPacket::toBytes)
                .consumerMainThread(MineHatConfirmC2SPacket::handle)
                .add();
        net.messageBuilder(VersionC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(VersionC2SPacket::new)
                .encoder(VersionC2SPacket::toBytes)
                .consumerMainThread(VersionC2SPacket::handle)
                .add();
        net.messageBuilder(VersionCheckS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(VersionCheckS2CPacket::new)
                .encoder(VersionCheckS2CPacket::toBytes)
                .consumerMainThread(VersionCheckS2CPacket::handle)
                .add();
        net.messageBuilder(UdiskWorldSoulC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(UdiskWorldSoulC2SPacket::new)
                .encoder(UdiskWorldSoulC2SPacket::toBytes)
                .consumerMainThread(UdiskWorldSoulC2SPacket::handle)
                .add();
        net.messageBuilder(SpringInstanceS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SpringInstanceS2CPacket::new)
                .encoder(SpringInstanceS2CPacket::toBytes)
                .consumerMainThread(SpringInstanceS2CPacket::handle)
                .add();
        net.messageBuilder(EffectLastTimeS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(EffectLastTimeS2CPacket::new)
                .encoder(EffectLastTimeS2CPacket::toBytes)
                .consumerMainThread(EffectLastTimeS2CPacket::handle)
                .add();
        net.messageBuilder(CoolDownTimeS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(CoolDownTimeS2CPacket::new)
                .encoder(CoolDownTimeS2CPacket::toBytes)
                .consumerMainThread(CoolDownTimeS2CPacket::handle)
                .add();
        net.messageBuilder(EndRune3TypeS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(EndRune3TypeS2CPacket::new)
                .encoder(EndRune3TypeS2CPacket::toBytes)
                .consumerMainThread(EndRune3TypeS2CPacket::handle)
                .add();
        net.messageBuilder(SoulSceptreC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SoulSceptreC2SPacket::new)
                .encoder(SoulSceptreC2SPacket::toBytes)
                .consumerMainThread(SoulSceptreC2SPacket::handle)
                .add();
        net.messageBuilder(EarthPowerC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(EarthPowerC2SPacket::new)
                .encoder(EarthPowerC2SPacket::toBytes)
                .consumerMainThread(EarthPowerC2SPacket::handle)
                .add();
        net.messageBuilder(EarthPowerS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(EarthPowerS2CPacket::new)
                .encoder(EarthPowerS2CPacket::toBytes)
                .consumerMainThread(EarthPowerS2CPacket::handle)
                .add();
        net.messageBuilder(CurveParticleS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(CurveParticleS2CPacket::new)
                .encoder(CurveParticleS2CPacket::toBytes)
                .consumerMainThread(CurveParticleS2CPacket::handle)
                .add();
        net.messageBuilder(DebuffTimeS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(DebuffTimeS2CPacket::new)
                .encoder(DebuffTimeS2CPacket::toBytes)
                .consumerMainThread(DebuffTimeS2CPacket::handle)
                .add();
        net.messageBuilder(PacketLimitS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(PacketLimitS2CPacket::new)
                .encoder(PacketLimitS2CPacket::toBytes)
                .consumerMainThread(PacketLimitS2CPacket::handle)
                .add();
        net.messageBuilder(SpaceRangeParticleS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SpaceRangeParticleS2CPacket::new)
                .encoder(SpaceRangeParticleS2CPacket::toBytes)
                .consumerMainThread(SpaceRangeParticleS2CPacket::handle)
                .add();
        net.messageBuilder(LifeElementParticleS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(LifeElementParticleS2CPacket::new)
                .encoder(LifeElementParticleS2CPacket::toBytes)
                .consumerMainThread(LifeElementParticleS2CPacket::handle)
                .add();
        net.messageBuilder(FireElementParticleS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(FireElementParticleS2CPacket::new)
                .encoder(FireElementParticleS2CPacket::toBytes)
                .consumerMainThread(FireElementParticleS2CPacket::handle)
                .add();
        net.messageBuilder(WaterElementParticleS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(WaterElementParticleS2CPacket::new)
                .encoder(WaterElementParticleS2CPacket::toBytes)
                .consumerMainThread(WaterElementParticleS2CPacket::handle)
                .add();
        net.messageBuilder(StoneElementParticleS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(StoneElementParticleS2CPacket::new)
                .encoder(StoneElementParticleS2CPacket::toBytes)
                .consumerMainThread(StoneElementParticleS2CPacket::handle)
                .add();
        net.messageBuilder(IceElementParticleS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(IceElementParticleS2CPacket::new)
                .encoder(IceElementParticleS2CPacket::toBytes)
                .consumerMainThread(IceElementParticleS2CPacket::handle)
                .add();
        net.messageBuilder(LightningElementParticleS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(LightningElementParticleS2CPacket::new)
                .encoder(LightningElementParticleS2CPacket::toBytes)
                .consumerMainThread(LightningElementParticleS2CPacket::handle)
                .add();
        net.messageBuilder(WindElementParticleS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(WindElementParticleS2CPacket::new)
                .encoder(WindElementParticleS2CPacket::toBytes)
                .consumerMainThread(WindElementParticleS2CPacket::handle)
                .add();
        net.messageBuilder(CheckBlockLimitS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(CheckBlockLimitS2CPacket::new)
                .encoder(CheckBlockLimitS2CPacket::toBytes)
                .consumerMainThread(CheckBlockLimitS2CPacket::handle)
                .add();
        net.messageBuilder(RemoveBlockLimitC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(RemoveBlockLimitC2SPacket::new)
                .encoder(RemoveBlockLimitC2SPacket::toBytes)
                .consumerMainThread(RemoveBlockLimitC2SPacket::handle)
                .add();
        net.messageBuilder(WindElementParticleS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(WindElementParticleS2CPacket::new)
                .encoder(WindElementParticleS2CPacket::toBytes)
                .consumerMainThread(WindElementParticleS2CPacket::handle)
                .add();
        net.messageBuilder(ClearElementParticleS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ClearElementParticleS2CPacket::new)
                .encoder(ClearElementParticleS2CPacket::toBytes)
                .consumerMainThread(ClearElementParticleS2CPacket::handle)
                .add();
        net.messageBuilder(MobAttributeC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(MobAttributeC2SPacket::new)
                .encoder(MobAttributeC2SPacket::toBytes)
                .consumerMainThread(MobAttributeC2SPacket::handle)
                .add();
        net.messageBuilder(MobAttributeS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(MobAttributeS2CPacket::new)
                .encoder(MobAttributeS2CPacket::toBytes)
                .consumerMainThread(MobAttributeS2CPacket::handle)
                .add();
        net.messageBuilder(ElementEffectTimeS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ElementEffectTimeS2CPacket::new)
                .encoder(ElementEffectTimeS2CPacket::toBytes)
                .consumerMainThread(ElementEffectTimeS2CPacket::handle)
                .add();
        net.messageBuilder(MissionAcceptC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(MissionAcceptC2SPacket::new)
                .encoder(MissionAcceptC2SPacket::toBytes)
                .consumerMainThread(MissionAcceptC2SPacket::handle)
                .add();
        net.messageBuilder(MissionSubmitC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(MissionSubmitC2SPacket::new)
                .encoder(MissionSubmitC2SPacket::toBytes)
                .consumerMainThread(MissionSubmitC2SPacket::handle)
                .add();
        net.messageBuilder(MissionScreenOpenS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(MissionScreenOpenS2CPacket::new)
                .encoder(MissionScreenOpenS2CPacket::toBytes)
                .consumerMainThread(MissionScreenOpenS2CPacket::handle)
                .add();
        net.messageBuilder(MissionScreenOpenC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(MissionScreenOpenC2SPacket::new)
                .encoder(MissionScreenOpenC2SPacket::toBytes)
                .consumerMainThread(MissionScreenOpenC2SPacket::handle)
                .add();
        net.messageBuilder(TowerStatusS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(TowerStatusS2CPacket::new)
                .encoder(TowerStatusS2CPacket::toBytes)
                .consumerMainThread(TowerStatusS2CPacket::handle)
                .add();
        net.messageBuilder(TowerChallengeC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(TowerChallengeC2SPacket::new)
                .encoder(TowerChallengeC2SPacket::toBytes)
                .consumerMainThread(TowerChallengeC2SPacket::handle)
                .add();
        net.messageBuilder(TradeListClearS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(TradeListClearS2CPacket::new)
                .encoder(TradeListClearS2CPacket::toBytes)
                .consumerMainThread(TradeListClearS2CPacket::handle)
                .add();
        net.messageBuilder(TradeListS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(TradeListS2CPacket::new)
                .encoder(TradeListS2CPacket::toBytes)
                .consumerMainThread(TradeListS2CPacket::handle)
                .add();
        net.messageBuilder(CopperCoinC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(CopperCoinC2SPacket::new)
                .encoder(CopperCoinC2SPacket::toBytes)
                .consumerMainThread(CopperCoinC2SPacket::handle)
                .add();
        net.messageBuilder(ForgeC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ForgeC2SPacket::new)
                .encoder(ForgeC2SPacket::toBytes)
                .consumerMainThread(ForgeC2SPacket::handle)
                .add();
        net.messageBuilder(ClientWayPointS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ClientWayPointS2CPacket::new)
                .encoder(ClientWayPointS2CPacket::toBytes)
                .consumerMainThread(ClientWayPointS2CPacket::handle)
                .add();
        net.messageBuilder(DecomposeC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(DecomposeC2SPacket::new)
                .encoder(DecomposeC2SPacket::toBytes)
                .consumerMainThread(DecomposeC2SPacket::handle)
                .add();
        net.messageBuilder(DecomposeDoubleClickTickS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(DecomposeDoubleClickTickS2CPacket::new)
                .encoder(DecomposeDoubleClickTickS2CPacket::toBytes)
                .consumerMainThread(DecomposeDoubleClickTickS2CPacket::handle)
                .add();
        net.messageBuilder(DecomposeRecipeLossS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(DecomposeRecipeLossS2CPacket::new)
                .encoder(DecomposeRecipeLossS2CPacket::toBytes)
                .consumerMainThread(DecomposeRecipeLossS2CPacket::handle)
                .add();
        net.messageBuilder(DailySupplyC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(DailySupplyC2SPacket::new)
                .encoder(DailySupplyC2SPacket::toBytes)
                .consumerMainThread(DailySupplyC2SPacket::handle)
                .add();
        net.messageBuilder(DailySupplyS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(DailySupplyS2CPacket::new)
                .encoder(DailySupplyS2CPacket::toBytes)
                .consumerMainThread(DailySupplyS2CPacket::handle)
                .add();
        net.messageBuilder(GemPieceC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(GemPieceC2SPacket::new)
                .encoder(GemPieceC2SPacket::toBytes)
                .consumerMainThread(GemPieceC2SPacket::handle)
                .add();
        net.messageBuilder(OffShellC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(OffShellC2SPacket::new)
                .encoder(OffShellC2SPacket::toBytes)
                .consumerMainThread(OffShellC2SPacket::handle)
                .add();
        net.messageBuilder(AllCurrencyC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(AllCurrencyC2SPacket::new)
                .encoder(AllCurrencyC2SPacket::toBytes)
                .consumerMainThread(AllCurrencyC2SPacket::handle)
                .add();
        net.messageBuilder(AttributeDataS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(AttributeDataS2CPacket::new)
                .encoder(AttributeDataS2CPacket::toBytes)
                .consumerMainThread(AttributeDataS2CPacket::handle)
                .add();
        net.messageBuilder(AttributeDataC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(AttributeDataC2SPacket::new)
                .encoder(AttributeDataC2SPacket::toBytes)
                .consumerMainThread(AttributeDataC2SPacket::handle)
                .add();
        net.messageBuilder(GuideStageS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(GuideStageS2CPacket::new)
                .encoder(GuideStageS2CPacket::toBytes)
                .consumerMainThread(GuideStageS2CPacket::handle)
                .add();
        net.messageBuilder(SpecificWayPointAddS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SpecificWayPointAddS2CPacket::new)
                .encoder(SpecificWayPointAddS2CPacket::toBytes)
                .consumerMainThread(SpecificWayPointAddS2CPacket::handle)
                .add();
        net.messageBuilder(SpecificWayPointRemoveS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SpecificWayPointRemoveS2CPacket::new)
                .encoder(SpecificWayPointRemoveS2CPacket::toBytes)
                .consumerMainThread(SpecificWayPointRemoveS2CPacket::handle)
                .add();
        net.messageBuilder(GuideFinishC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(GuideFinishC2SPacket::new)
                .encoder(GuideFinishC2SPacket::toBytes)
                .consumerMainThread(GuideFinishC2SPacket::handle)
                .add();
        net.messageBuilder(MarketDataC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(MarketDataC2SPacket::new)
                .encoder(MarketDataC2SPacket::toBytes)
                .consumerMainThread(MarketDataC2SPacket::handle)
                .add();
        net.messageBuilder(MarketScreenS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(MarketScreenS2CPacket::new)
                .encoder(MarketScreenS2CPacket::toBytes)
                .consumerMainThread(MarketScreenS2CPacket::handle)
                .add();
        net.messageBuilder(MarketScreenC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(MarketScreenC2SPacket::new)
                .encoder(MarketScreenC2SPacket::toBytes)
                .consumerMainThread(MarketScreenC2SPacket::handle)
                .add();
        net.messageBuilder(CurrentSeasonAndResonanceTypeS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(CurrentSeasonAndResonanceTypeS2CPacket::new)
                .encoder(CurrentSeasonAndResonanceTypeS2CPacket::toBytes)
                .consumerMainThread(CurrentSeasonAndResonanceTypeS2CPacket::handle)
                .add();
        net.messageBuilder(CurrentSeasonC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(CurrentSeasonC2SPacket::new)
                .encoder(CurrentSeasonC2SPacket::toBytes)
                .consumerMainThread(CurrentSeasonC2SPacket::handle)
                .add();
        net.messageBuilder(ResonanceC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ResonanceC2SPacket::new)
                .encoder(ResonanceC2SPacket::toBytes)
                .consumerMainThread(ResonanceC2SPacket::handle)
                .add();
        net.messageBuilder(NewTeamInstancePrepareInfoS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(NewTeamInstancePrepareInfoS2CPacket::new)
                .encoder(NewTeamInstancePrepareInfoS2CPacket::toBytes)
                .consumerMainThread(NewTeamInstancePrepareInfoS2CPacket::handle)
                .add();
        net.messageBuilder(NewTeamInstanceJoinedPlayerInfoS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(NewTeamInstanceJoinedPlayerInfoS2CPacket::new)
                .encoder(NewTeamInstanceJoinedPlayerInfoS2CPacket::toBytes)
                .consumerMainThread(NewTeamInstanceJoinedPlayerInfoS2CPacket::handle)
                .add();
        net.messageBuilder(NewTeamInstanceClearS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(NewTeamInstanceClearS2CPacket::new)
                .encoder(NewTeamInstanceClearS2CPacket::toBytes)
                .consumerMainThread(NewTeamInstanceClearS2CPacket::handle)
                .add();
        net.messageBuilder(VpValueS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(VpValueS2CPacket::new)
                .encoder(VpValueS2CPacket::toBytes)
                .consumerMainThread(VpValueS2CPacket::handle)
                .add();
        net.messageBuilder(VpStoreBuyC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(VpStoreBuyC2SPacket::new)
                .encoder(VpStoreBuyC2SPacket::toBytes)
                .consumerMainThread(VpStoreBuyC2SPacket::handle)
                .add();
        net.messageBuilder(PlanDateAndTierS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(PlanDateAndTierS2CPacket::new)
                .encoder(PlanDateAndTierS2CPacket::toBytes)
                .consumerMainThread(PlanDateAndTierS2CPacket::handle)
                .add();
        net.messageBuilder(PlanMissionInfoS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(PlanMissionInfoS2CPacket::new)
                .encoder(PlanMissionInfoS2CPacket::toBytes)
                .consumerMainThread(PlanMissionInfoS2CPacket::handle)
                .add();
        net.messageBuilder(PlanMissionCancelRequestC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(PlanMissionCancelRequestC2SPacket::new)
                .encoder(PlanMissionCancelRequestC2SPacket::toBytes)
                .consumerMainThread(PlanMissionCancelRequestC2SPacket::handle)
                .add();
        net.messageBuilder(PlanMissionFinishedRequestC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(PlanMissionFinishedRequestC2SPacket::new)
                .encoder(PlanMissionFinishedRequestC2SPacket::toBytes)
                .consumerMainThread(PlanMissionFinishedRequestC2SPacket::handle)
                .add();
        net.messageBuilder(PlanMissionRequestC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(PlanMissionRequestC2SPacket::new)
                .encoder(PlanMissionRequestC2SPacket::toBytes)
                .consumerMainThread(PlanMissionRequestC2SPacket::handle)
                .add();
        net.messageBuilder(CommonActiveC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(CommonActiveC2SPacket::new)
                .encoder(CommonActiveC2SPacket::toBytes)
                .consumerMainThread(CommonActiveC2SPacket::handle)
                .add();
        net.messageBuilder(RemoveEffectLastTimeS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(RemoveEffectLastTimeS2CPacket::new)
                .encoder(RemoveEffectLastTimeS2CPacket::toBytes)
                .consumerMainThread(RemoveEffectLastTimeS2CPacket::handle)
                .add();
        net.messageBuilder(RemoveEffectLastTimeByItemIdS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(RemoveEffectLastTimeByItemIdS2CPacket::new)
                .encoder(RemoveEffectLastTimeByItemIdS2CPacket::toBytes)
                .consumerMainThread(RemoveEffectLastTimeByItemIdS2CPacket::handle)
                .add();
        net.messageBuilder(DustParticleS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(DustParticleS2CPacket::new)
                .encoder(DustParticleS2CPacket::toBytes)
                .consumerMainThread(DustParticleS2CPacket::handle)
                .add();
        net.messageBuilder(EndlessInstanceKillCountS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(EndlessInstanceKillCountS2CPacket::new)
                .encoder(EndlessInstanceKillCountS2CPacket::toBytes)
                .consumerMainThread(EndlessInstanceKillCountS2CPacket::handle)
                .add();
        net.messageBuilder(CraftC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(CraftC2SPacket::new)
                .encoder(CraftC2SPacket::toBytes)
                .consumerMainThread(CraftC2SPacket::handle)
                .add();
        net.messageBuilder(MobEffectHudS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(MobEffectHudS2CPacket::new)
                .encoder(MobEffectHudS2CPacket::toBytes)
                .consumerMainThread(MobEffectHudS2CPacket::handle)
                .add();
        net.messageBuilder(AnimationS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(AnimationS2CPacket::new)
                .encoder(AnimationS2CPacket::toBytes)
                .consumerMainThread(AnimationS2CPacket::handle)
                .add();
        net.messageBuilder(SmeltDataS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SmeltDataS2CPacket::new)
                .encoder(SmeltDataS2CPacket::toBytes)
                .consumerMainThread(SmeltDataS2CPacket::handle)
                .add();
        net.messageBuilder(InjectC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(InjectC2SPacket::new)
                .encoder(InjectC2SPacket::toBytes)
                .consumerMainThread(InjectC2SPacket::handle)
                .add();
        net.messageBuilder(SmeltRequestC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SmeltRequestC2SPacket::new)
                .encoder(SmeltRequestC2SPacket::toBytes)
                .consumerMainThread(SmeltRequestC2SPacket::handle)
                .add();
        net.messageBuilder(SmeltHarvestC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SmeltHarvestC2SPacket::new)
                .encoder(SmeltHarvestC2SPacket::toBytes)
                .consumerMainThread(SmeltHarvestC2SPacket::handle)
                .add();
        net.messageBuilder(PlayerClickSpaceC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(PlayerClickSpaceC2SPacket::new)
                .encoder(PlayerClickSpaceC2SPacket::toBytes)
                .consumerMainThread(PlayerClickSpaceC2SPacket::handle)
                .add();
        net.messageBuilder(SmeltDataRequestC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SmeltDataRequestC2SPacket::new)
                .encoder(SmeltDataRequestC2SPacket::toBytes)
                .consumerMainThread(SmeltDataRequestC2SPacket::handle)
                .add();
        net.messageBuilder(DisperseBallParticleS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(DisperseBallParticleS2CPacket::new)
                .encoder(DisperseBallParticleS2CPacket::toBytes)
                .consumerMainThread(DisperseBallParticleS2CPacket::handle)
                .add();
        net.messageBuilder(ExpGetS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ExpGetS2CPacket::new)
                .encoder(ExpGetS2CPacket::toBytes)
                .consumerMainThread(ExpGetS2CPacket::handle)
                .add();
        net.messageBuilder(ItemGetS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ItemGetS2CPacket::new)
                .encoder(ItemGetS2CPacket::toBytes)
                .consumerMainThread(ItemGetS2CPacket::handle)
                .add();
        net.messageBuilder(PointDataS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(PointDataS2CPacket::new)
                .encoder(PointDataS2CPacket::toBytes)
                .consumerMainThread(PointDataS2CPacket::handle)
                .add();
        net.messageBuilder(ExpGetResetS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ExpGetResetS2CPacket::new)
                .encoder(ExpGetResetS2CPacket::toBytes)
                .consumerMainThread(ExpGetResetS2CPacket::handle)
                .add();
        net.messageBuilder(PlayerIsInBattleS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(PlayerIsInBattleS2CPacket::new)
                .encoder(PlayerIsInBattleS2CPacket::toBytes)
                .consumerMainThread(PlayerIsInBattleS2CPacket::handle)
                .add();
        net.messageBuilder(QuickUseDisplayS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(QuickUseDisplayS2CPacket::new)
                .encoder(QuickUseDisplayS2CPacket::toBytes)
                .consumerMainThread(QuickUseDisplayS2CPacket::handle)
                .add();
        net.messageBuilder(MacRequestS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(MacRequestS2CPacket::new)
                .encoder(MacRequestS2CPacket::toBytes)
                .consumerMainThread(MacRequestS2CPacket::handle)
                .add();
        net.messageBuilder(MacC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(MacC2SPacket::new)
                .encoder(MacC2SPacket::toBytes)
                .consumerMainThread(MacC2SPacket::handle)
                .add();
        net.messageBuilder(LineEffectParticleS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(LineEffectParticleS2CPacket::new)
                .encoder(LineEffectParticleS2CPacket::toBytes)
                .consumerMainThread(LineEffectParticleS2CPacket::handle)
                .add();
        net.messageBuilder(LineSpaceEffectParticleS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(LineSpaceEffectParticleS2CPacket::new)
                .encoder(LineSpaceEffectParticleS2CPacket::toBytes)
                .consumerMainThread(LineSpaceEffectParticleS2CPacket::handle)
                .add();
        net.messageBuilder(LineSpaceDustParticleS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(LineSpaceDustParticleS2CPacket::new)
                .encoder(LineSpaceDustParticleS2CPacket::toBytes)
                .consumerMainThread(LineSpaceDustParticleS2CPacket::handle)
                .add();
        net.messageBuilder(RemoveDebuffTimeS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(RemoveDebuffTimeS2CPacket::new)
                .encoder(RemoveDebuffTimeS2CPacket::toBytes)
                .consumerMainThread(RemoveDebuffTimeS2CPacket::handle)
                .add();
        net.messageBuilder(LotteryRewardTimeS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(LotteryRewardTimeS2CPacket::new)
                .encoder(LotteryRewardTimeS2CPacket::toBytes)
                .consumerMainThread(LotteryRewardTimeS2CPacket::handle)
                .add();
        net.messageBuilder(SmeltProgressCancelC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SmeltProgressCancelC2SPacket::new)
                .encoder(SmeltProgressCancelC2SPacket::toBytes)
                .consumerMainThread(SmeltProgressCancelC2SPacket::handle)
                .add();
        net.messageBuilder(SingleItemChangeC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SingleItemChangeC2SPacket::new)
                .encoder(SingleItemChangeC2SPacket::toBytes)
                .consumerMainThread(SingleItemChangeC2SPacket::handle)
                .add();
        net.messageBuilder(SingleItemChangeFullDataS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SingleItemChangeFullDataS2CPacket::new)
                .encoder(SingleItemChangeFullDataS2CPacket::toBytes)
                .consumerMainThread(SingleItemChangeFullDataS2CPacket::handle)
                .add();
        net.messageBuilder(SingleItemChangeSingleRecipeTimeS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SingleItemChangeSingleRecipeTimeS2CPacket::new)
                .encoder(SingleItemChangeSingleRecipeTimeS2CPacket::toBytes)
                .consumerMainThread(SingleItemChangeSingleRecipeTimeS2CPacket::handle)
                .add();
        net.messageBuilder(SilentTickS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SilentTickS2CPacket::new)
                .encoder(SilentTickS2CPacket::toBytes)
                .consumerMainThread(SilentTickS2CPacket::handle)
                .add();
        net.messageBuilder(BlindTickS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(BlindTickS2CPacket::new)
                .encoder(BlindTickS2CPacket::toBytes)
                .consumerMainThread(BlindTickS2CPacket::handle)
                .add();
        net.messageBuilder(RankDataS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(RankDataS2CPacket::new)
                .encoder(RankDataS2CPacket::toBytes)
                .consumerMainThread(RankDataS2CPacket::handle)
                .add();
        net.messageBuilder(RankChangeS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(RankChangeS2CPacket::new)
                .encoder(RankChangeS2CPacket::toBytes)
                .consumerMainThread(RankChangeS2CPacket::handle)
                .add();
        net.messageBuilder(TotalKillCountS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(TotalKillCountS2CPacket::new)
                .encoder(TotalKillCountS2CPacket::toBytes)
                .consumerMainThread(TotalKillCountS2CPacket::handle)
                .add();
        net.messageBuilder(RailwayPillarSetToolModeC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(RailwayPillarSetToolModeC2SPacket::new)
                .encoder(RailwayPillarSetToolModeC2SPacket::toBytes)
                .consumerMainThread(RailwayPillarSetToolModeC2SPacket::handle)
                .add();
        net.messageBuilder(ServerTickS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ServerTickS2CPacket::new)
                .encoder(ServerTickS2CPacket::toBytes)
                .consumerMainThread(ServerTickS2CPacket::handle)
                .add();
        net.messageBuilder(MobKillEntrustmentInfoS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(MobKillEntrustmentInfoS2CPacket::new)
                .encoder(MobKillEntrustmentInfoS2CPacket::toBytes)
                .consumerMainThread(MobKillEntrustmentInfoS2CPacket::handle)
                .add();
        net.messageBuilder(GuideHudCloseStatusS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(GuideHudCloseStatusS2CPacket::new)
                .encoder(GuideHudCloseStatusS2CPacket::toBytes)
                .consumerMainThread(GuideHudCloseStatusS2CPacket::handle)
                .add();
        net.messageBuilder(NearestSpawnPointS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(NearestSpawnPointS2CPacket::new)
                .encoder(NearestSpawnPointS2CPacket::toBytes)
                .consumerMainThread(NearestSpawnPointS2CPacket::handle)
                .add();
        net.messageBuilder(QuickDecomposeC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(QuickDecomposeC2SPacket::new)
                .encoder(QuickDecomposeC2SPacket::toBytes)
                .consumerMainThread(QuickDecomposeC2SPacket::handle)
                .add();
        net.messageBuilder(SpaceEffectParticleS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SpaceEffectParticleS2CPacket::new)
                .encoder(SpaceEffectParticleS2CPacket::toBytes)
                .consumerMainThread(SpaceEffectParticleS2CPacket::handle)
                .add();
        net.messageBuilder(SkillV2InfoS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SkillV2InfoS2CPacket::new)
                .encoder(SkillV2InfoS2CPacket::toBytes)
                .consumerMainThread(SkillV2InfoS2CPacket::handle)
                .add();
        net.messageBuilder(SkillV2PlayerTryToUpgradeSkillC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SkillV2PlayerTryToUpgradeSkillC2SPacket::new)
                .encoder(SkillV2PlayerTryToUpgradeSkillC2SPacket::toBytes)
                .consumerMainThread(SkillV2PlayerTryToUpgradeSkillC2SPacket::handle)
                .add();
        net.messageBuilder(SkillV2PlayerTryToReleaseSkillC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SkillV2PlayerTryToReleaseSkillC2SPacket::new)
                .encoder(SkillV2PlayerTryToReleaseSkillC2SPacket::toBytes)
                .consumerMainThread(SkillV2PlayerTryToReleaseSkillC2SPacket::handle)
                .add();
        net.messageBuilder(SkillV2PlayerTryToEquipSkillC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SkillV2PlayerTryToEquipSkillC2SPacket::new)
                .encoder(SkillV2PlayerTryToEquipSkillC2SPacket::toBytes)
                .consumerMainThread(SkillV2PlayerTryToEquipSkillC2SPacket::handle)
                .add();
        net.messageBuilder(SkillV2PlayerTryToChooseProfessionTypeC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SkillV2PlayerTryToChooseProfessionTypeC2SPacket::new)
                .encoder(SkillV2PlayerTryToChooseProfessionTypeC2SPacket::toBytes)
                .consumerMainThread(SkillV2PlayerTryToChooseProfessionTypeC2SPacket::handle)
                .add();
        net.messageBuilder(SkillV2CooldownS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SkillV2CooldownS2CPacket::new)
                .encoder(SkillV2CooldownS2CPacket::toBytes)
                .consumerMainThread(SkillV2CooldownS2CPacket::handle)
                .add();
        net.messageBuilder(SkillV2LeftCooldownS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SkillV2LeftCooldownS2CPacket::new)
                .encoder(SkillV2LeftCooldownS2CPacket::toBytes)
                .consumerMainThread(SkillV2LeftCooldownS2CPacket::handle)
                .add();
        net.messageBuilder(GuideDisplayS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(GuideDisplayS2CPacket::new)
                .encoder(GuideDisplayS2CPacket::toBytes)
                .consumerMainThread(GuideDisplayS2CPacket::handle)
                .add();
        net.messageBuilder(LastVerticalCircleParticleS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(LastVerticalCircleParticleS2CPacket::new)
                .encoder(LastVerticalCircleParticleS2CPacket::toBytes)
                .consumerMainThread(LastVerticalCircleParticleS2CPacket::handle)
                .add();
        net.messageBuilder(SkillV2PlayerTryToSetSkillElementC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SkillV2PlayerTryToSetSkillElementC2SPacket::new)
                .encoder(SkillV2PlayerTryToSetSkillElementC2SPacket::toBytes)
                .consumerMainThread(SkillV2PlayerTryToSetSkillElementC2SPacket::handle)
                .add();
        net.messageBuilder(SpringMobDamageS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SpringMobDamageS2CPacket::new)
                .encoder(SpringMobDamageS2CPacket::toBytes)
                .consumerMainThread(SpringMobDamageS2CPacket::handle)
                .add();
        net.messageBuilder(MissionV2DataS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(MissionV2DataS2CPacket::new)
                .encoder(MissionV2DataS2CPacket::toBytes)
                .consumerMainThread(MissionV2DataS2CPacket::handle)
                .add();
        net.messageBuilder(ManaTowerS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ManaTowerS2CPacket::new)
                .encoder(ManaTowerS2CPacket::toBytes)
                .consumerMainThread(ManaTowerS2CPacket::handle)
                .add();
        net.messageBuilder(ElementPieceS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ElementPieceS2CPacket::new)
                .encoder(ElementPieceS2CPacket::toBytes)
                .consumerMainThread(ElementPieceS2CPacket::handle)
                .add();
        net.messageBuilder(ElementPieceC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ElementPieceC2SPacket::new)
                .encoder(ElementPieceC2SPacket::toBytes)
                .consumerMainThread(ElementPieceC2SPacket::handle)
                .add();
        net.messageBuilder(EstateDataS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(EstateDataS2CPacket::new)
                .encoder(EstateDataS2CPacket::toBytes)
                .consumerMainThread(EstateDataS2CPacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        ClientUtils.clientPacketLimit--;
        if (ClientUtils.clientPacketLimit > 0) INSTANCE.sendToServer(message);
        else {
            if (ClientUtils.clientPlayer != null) {
                Compute.sendFormatMSG(ClientUtils.clientPlayer, Component.literal("安全").withStyle(ChatFormatting.GREEN),
                        Component.literal("请减少操作频率或降低连点器/脚本每秒操作频率！当前频率已超过100/s").withStyle(ChatFormatting.RED));
            }
        }
    }

    public static <MSG> void sendToClient(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static <MSG> void sendToClient(MSG message, Player player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), message);
    }
}
