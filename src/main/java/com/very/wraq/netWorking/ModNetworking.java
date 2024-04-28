package com.very.wraq.netWorking;

import com.very.wraq.coreAttackModule.BowRequestC2SPacket;
import com.very.wraq.coreAttackModule.ManaAttackRequestC2SPacket;
import com.very.wraq.netWorking.bowAndSceptreActive.*;
import com.very.wraq.netWorking.customized.*;
import com.very.wraq.netWorking.dailyMission.DailyMissionContentS2CPacket;
import com.very.wraq.netWorking.dailyMission.DailyMissionFinishedRequestC2SPacket;
import com.very.wraq.netWorking.dailyMission.DailyMissionFinishedTimeS2CPacket;
import com.very.wraq.netWorking.dailyMission.DailyMissionRequestC2SPacket;
import com.very.wraq.netWorking.misc.AnimationPackets.*;
import com.very.wraq.netWorking.misc.AttackPackets.AttackC2SPacket;
import com.very.wraq.netWorking.misc.AttributePackets.*;
import com.very.wraq.netWorking.misc.BowSoundParticle.BowShootS2CPacket;
import com.very.wraq.netWorking.misc.BowSoundParticle.SkyBowShootS2CPacket;
import com.very.wraq.netWorking.misc.CodeSceptrePackets.CodeC2SPacket;
import com.very.wraq.netWorking.misc.CrestPackets.CrestStatusS2CPacket;
import com.very.wraq.netWorking.misc.*;
import com.very.wraq.netWorking.misc.EarthPower.EarthPowerC2SPacket;
import com.very.wraq.netWorking.misc.EarthPower.EarthPowerS2CPacket;
import com.very.wraq.netWorking.misc.EntropyPackets.EntropyS2CPacket;
import com.very.wraq.netWorking.misc.Limit.CheckBlockLimitS2CPacket;
import com.very.wraq.netWorking.misc.Limit.LimitC2SPacket;
import com.very.wraq.netWorking.misc.Limit.RemoveBlockLimitC2SPacket;
import com.very.wraq.netWorking.misc.Limit.ScreenCloseC2SPacket;
import com.very.wraq.netWorking.misc.MusicPlayerPackets.MusicIdolS2CPacket;
import com.very.wraq.netWorking.misc.ParticlePackets.*;
import com.very.wraq.netWorking.misc.ParticlePackets.EffectParticle.CritHitParticleS2CPacket;
import com.very.wraq.netWorking.misc.ParticlePackets.EffectParticle.DamageDecreaseParticleS2CPacket;
import com.very.wraq.netWorking.misc.ParticlePackets.EffectParticle.DefencePenetrationParticleS2CPacket;
import com.very.wraq.netWorking.misc.ParticlePackets.EffectParticle.ManaDefencePenetrationParticleS2CPacket;
import com.very.wraq.netWorking.misc.ParticlePackets.ElementParticle.*;
import com.very.wraq.netWorking.misc.ParticlePackets.NewParticlePackets.*;
import com.very.wraq.netWorking.misc.PrefixPackets.PrefixS2CPacket;
import com.very.wraq.netWorking.misc.QuartzSword.QuartzSwordParticleS2CPacket;
import com.very.wraq.netWorking.misc.QuartzSword.QuartzSwordPlayerS2CPacket;
import com.very.wraq.netWorking.misc.SkillPackets.*;
import com.very.wraq.netWorking.misc.SkillPackets.Charging.*;
import com.very.wraq.netWorking.misc.SmartPhonePackets.*;
import com.very.wraq.netWorking.misc.SoundsPackets.SoundsS2CPacket;
import com.very.wraq.netWorking.misc.TeamPackets.*;
import com.very.wraq.netWorking.misc.TestPackets.TestC2SPacket;
import com.very.wraq.netWorking.misc.ToolTipPackets.CoolDownTimeS2CPacket;
import com.very.wraq.netWorking.misc.ToolTipPackets.DailyMissionS2CPacket;
import com.very.wraq.netWorking.misc.USE.*;
import com.very.wraq.netWorking.reputation.ReputationBuyRequestC2SPacket;
import com.very.wraq.netWorking.reputation.ReputationValueS2CPacket;
import com.very.wraq.netWorking.reputationMission.*;
import com.very.wraq.netWorking.unSorted.*;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.ClientUtils;
import com.very.wraq.valueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModNetworking {
    private static SimpleChannel INSTANCE;
    private static int packetID = 0;
    private static int id()
    {
        return packetID++;
    }

    public static void register()
    {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(Utils.MOD_ID,"messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(DrugsC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(DrugsC2SPacket::new)
                .encoder(DrugsC2SPacket::toBytes)
                .consumerMainThread(DrugsC2SPacket::handle)
                .add();

        net.messageBuilder(Use1C2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(Use1C2SPacket::new)
                .encoder(Use1C2SPacket::toBytes)
                .consumerMainThread(Use1C2SPacket::handle)
                .add();

        net.messageBuilder(Use2C2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(Use2C2SPacket::new)
                .encoder(Use2C2SPacket::toBytes)
                .consumerMainThread(Use2C2SPacket::handle)
                .add();

        net.messageBuilder(Use3C2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(Use3C2SPacket::new)
                .encoder(Use3C2SPacket::toBytes)
                .consumerMainThread(Use3C2SPacket::handle)
                .add();

        net.messageBuilder(Use4C2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(Use4C2SPacket::new)
                .encoder(Use4C2SPacket::toBytes)
                .consumerMainThread(Use4C2SPacket::handle)
                .add();

        net.messageBuilder(ResetC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ResetC2SPacket::new)
                .encoder(ResetC2SPacket::toBytes)
                .consumerMainThread(ResetC2SPacket::handle)
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
        net.messageBuilder(MusicIdolS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(MusicIdolS2CPacket::new)
                .encoder(MusicIdolS2CPacket::toBytes)
                .consumerMainThread(MusicIdolS2CPacket::handle)
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
        net.messageBuilder(BowShootS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(BowShootS2CPacket::new)
                .encoder(BowShootS2CPacket::toBytes)
                .consumerMainThread(BowShootS2CPacket::handle)
                .add();
        net.messageBuilder(SkyBowShootS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SkyBowShootS2CPacket::new)
                .encoder(SkyBowShootS2CPacket::toBytes)
                .consumerMainThread(SkyBowShootS2CPacket::handle)
                .add();
        net.messageBuilder(LimitC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(LimitC2SPacket::new)
                .encoder(LimitC2SPacket::toBytes)
                .consumerMainThread(LimitC2SPacket::handle)
                .add();
        net.messageBuilder(QuartzSwordPlayerS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(QuartzSwordPlayerS2CPacket::new)
                .encoder(QuartzSwordPlayerS2CPacket::toBytes)
                .consumerMainThread(QuartzSwordPlayerS2CPacket::handle)
                .add();
        net.messageBuilder(QuartzSwordParticleS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(QuartzSwordParticleS2CPacket::new)
                .encoder(QuartzSwordParticleS2CPacket::toBytes)
                .consumerMainThread(QuartzSwordParticleS2CPacket::handle)
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
        net.messageBuilder(RuneHud0S2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(RuneHud0S2CPacket::new)
                .encoder(RuneHud0S2CPacket::toBytes)
                .consumerMainThread(RuneHud0S2CPacket::handle)
                .add();
        net.messageBuilder(TestC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(TestC2SPacket::new)
                .encoder(TestC2SPacket::toBytes)
                .consumerMainThread(TestC2SPacket::handle)
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
        net.messageBuilder(RequestC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(RequestC2SPacket::new)
                .encoder(RequestC2SPacket::toBytes)
                .consumerMainThread(RequestC2SPacket::handle)
                .add();
        net.messageBuilder(SilverC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SilverC2SPacket::new)
                .encoder(SilverC2SPacket::toBytes)
                .consumerMainThread(SilverC2SPacket::handle)
                .add();
        net.messageBuilder(GoldC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(GoldC2SPacket::new)
                .encoder(GoldC2SPacket::toBytes)
                .consumerMainThread(GoldC2SPacket::handle)
                .add();
        net.messageBuilder(SecuritySellC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SecuritySellC2SPacket::new)
                .encoder(SecuritySellC2SPacket::toBytes)
                .consumerMainThread(SecuritySellC2SPacket::handle)
                .add();
        net.messageBuilder(SecurityBuyC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SecurityBuyC2SPacket::new)
                .encoder(SecurityBuyC2SPacket::toBytes)
                .consumerMainThread(SecurityBuyC2SPacket::handle)
                .add();
        net.messageBuilder(MarketDataS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(MarketDataS2CPacket::new)
                .encoder(MarketDataS2CPacket::toBytes)
                .consumerMainThread(MarketDataS2CPacket::handle)
                .add();
        net.messageBuilder(MarketDataClearS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(MarketDataClearS2CPacket::new)
                .encoder(MarketDataClearS2CPacket::toBytes)
                .consumerMainThread(MarketDataClearS2CPacket::handle)
                .add();
        net.messageBuilder(BuyCheckC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(BuyCheckC2SPacket::new)
                .encoder(BuyCheckC2SPacket::toBytes)
                .consumerMainThread(BuyCheckC2SPacket::handle)
                .add();
        net.messageBuilder(RequestMarketC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(RequestMarketC2SPacket::new)
                .encoder(RequestMarketC2SPacket::toBytes)
                .consumerMainThread(RequestMarketC2SPacket::handle)
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
        net.messageBuilder(SwordAttackAnimationS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SwordAttackAnimationS2CPacket::new)
                .encoder(SwordAttackAnimationS2CPacket::toBytes)
                .consumerMainThread(SwordAttackAnimationS2CPacket::handle)
                .add();
        net.messageBuilder(SwordAttackAnimationRequestC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SwordAttackAnimationRequestC2SPacket::new)
                .encoder(SwordAttackAnimationRequestC2SPacket::toBytes)
                .consumerMainThread(SwordAttackAnimationRequestC2SPacket::handle)
                .add();
        net.messageBuilder(RangeAttackRequestC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(RangeAttackRequestC2SPacket::new)
                .encoder(RangeAttackRequestC2SPacket::toBytes)
                .consumerMainThread(RangeAttackRequestC2SPacket::handle)
                .add();
        net.messageBuilder(PickAxeAttackAnimationS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(PickAxeAttackAnimationS2CPacket::new)
                .encoder(PickAxeAttackAnimationS2CPacket::toBytes)
                .consumerMainThread(PickAxeAttackAnimationS2CPacket::handle)
                .add();
        net.messageBuilder(PickAxeAttackAnimationRequestC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(PickAxeAttackAnimationRequestC2SPacket::new)
                .encoder(PickAxeAttackAnimationRequestC2SPacket::toBytes)
                .consumerMainThread(PickAxeAttackAnimationRequestC2SPacket::handle)
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
        net.messageBuilder(ManaSkill13S2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ManaSkill13S2CPacket::new)
                .encoder(ManaSkill13S2CPacket::toBytes)
                .consumerMainThread(ManaSkill13S2CPacket::handle)
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
        net.messageBuilder(BufferChangeS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(BufferChangeS2CPacket::new)
                .encoder(BufferChangeS2CPacket::toBytes)
                .consumerMainThread(BufferChangeS2CPacket::handle)
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
        net.messageBuilder(CrestStatusS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(CrestStatusS2CPacket::new)
                .encoder(CrestStatusS2CPacket::toBytes)
                .consumerMainThread(CrestStatusS2CPacket::handle)
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
        net.messageBuilder(AttackC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(AttackC2SPacket::new)
                .encoder(AttackC2SPacket::toBytes)
                .consumerMainThread(AttackC2SPacket::handle)
                .add();
        net.messageBuilder(AttackAnimationS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(AttackAnimationS2CPacket::new)
                .encoder(AttackAnimationS2CPacket::toBytes)
                .consumerMainThread(AttackAnimationS2CPacket::handle)
                .add();
        net.messageBuilder(AttackAnimationRequestC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(AttackAnimationRequestC2SPacket::new)
                .encoder(AttackAnimationRequestC2SPacket::toBytes)
                .consumerMainThread(AttackAnimationRequestC2SPacket::handle)
                .add();
        net.messageBuilder(ManaAttackAnimationRequestC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ManaAttackAnimationRequestC2SPacket::new)
                .encoder(ManaAttackAnimationRequestC2SPacket::toBytes)
                .consumerMainThread(ManaAttackAnimationRequestC2SPacket::handle)
                .add();
        net.messageBuilder(ManaAttackAnimationS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ManaAttackAnimationS2CPacket::new)
                .encoder(ManaAttackAnimationS2CPacket::toBytes)
                .consumerMainThread(ManaAttackAnimationS2CPacket::handle)
                .add();
        net.messageBuilder(ManaAttackRequestC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ManaAttackRequestC2SPacket::new)
                .encoder(ManaAttackRequestC2SPacket::toBytes)
                .consumerMainThread(ManaAttackRequestC2SPacket::handle)
                .add();
        net.messageBuilder(UseAnimationS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(UseAnimationS2CPacket::new)
                .encoder(UseAnimationS2CPacket::toBytes)
                .consumerMainThread(UseAnimationS2CPacket::handle)
                .add();
        net.messageBuilder(UseRequestC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(UseRequestC2SPacket::new)
                .encoder(UseRequestC2SPacket::toBytes)
                .consumerMainThread(UseRequestC2SPacket::handle)
                .add();
        net.messageBuilder(UseAnimationRequestC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(UseAnimationRequestC2SPacket::new)
                .encoder(UseAnimationRequestC2SPacket::toBytes)
                .consumerMainThread(UseAnimationRequestC2SPacket::handle)
                .add();
        net.messageBuilder(AnimationTickResetS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(AnimationTickResetS2CPacket::new)
                .encoder(AnimationTickResetS2CPacket::toBytes)
                .consumerMainThread(AnimationTickResetS2CPacket::handle)
                .add();

        net.messageBuilder(BowAnimationS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(BowAnimationS2CPacket::new)
                .encoder(BowAnimationS2CPacket::toBytes)
                .consumerMainThread(BowAnimationS2CPacket::handle)
                .add();
        net.messageBuilder(BowRequestC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(BowRequestC2SPacket::new)
                .encoder(BowRequestC2SPacket::toBytes)
                .consumerMainThread(BowRequestC2SPacket::handle)
                .add();
        net.messageBuilder(BowAnimationRequestC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(BowAnimationRequestC2SPacket::new)
                .encoder(BowAnimationRequestC2SPacket::toBytes)
                .consumerMainThread(BowAnimationRequestC2SPacket::handle)
                .add();
        net.messageBuilder(RollingAnimationRequestC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(RollingAnimationRequestC2SPacket::new)
                .encoder(RollingAnimationRequestC2SPacket::toBytes)
                .consumerMainThread(RollingAnimationRequestC2SPacket::handle)
                .add();
        net.messageBuilder(RollingAnimationS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(RollingAnimationS2CPacket::new)
                .encoder(RollingAnimationS2CPacket::toBytes)
                .consumerMainThread(RollingAnimationS2CPacket::handle)
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
        net.messageBuilder(ItemStackNameS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ItemStackNameS2CPacket::new)
                .encoder(ItemStackNameS2CPacket::toBytes)
                .consumerMainThread(ItemStackNameS2CPacket::handle)
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
        net.messageBuilder(TeamInfoClearS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(TeamInfoClearS2CPacket::new)
                .encoder(TeamInfoClearS2CPacket::toBytes)
                .consumerMainThread(TeamInfoClearS2CPacket::handle)
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
        net.messageBuilder(DragonBreathParticleS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(DragonBreathParticleS2CPacket::new)
                .encoder(DragonBreathParticleS2CPacket::toBytes)
                .consumerMainThread(DragonBreathParticleS2CPacket::handle)
                .add();
        net.messageBuilder(EndRune3TypeS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(EndRune3TypeS2CPacket::new)
                .encoder(EndRune3TypeS2CPacket::toBytes)
                .consumerMainThread(EndRune3TypeS2CPacket::handle)
                .add();
        net.messageBuilder(ShangMengLiC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ShangMengLiC2SPacket::new)
                .encoder(ShangMengLiC2SPacket::toBytes)
                .consumerMainThread(ShangMengLiC2SPacket::handle)
                .add();
        net.messageBuilder(ZuesSwordS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ZuesSwordS2CPacket::new)
                .encoder(ZuesSwordS2CPacket::toBytes)
                .consumerMainThread(ZuesSwordS2CPacket::handle)
                .add();
        net.messageBuilder(ShowDickerBowC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ShowDickerBowC2SPacket::new)
                .encoder(ShowDickerBowC2SPacket::toBytes)
                .consumerMainThread(ShowDickerBowC2SPacket::handle)
                .add();
        net.messageBuilder(ClientLevelS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ClientLevelS2CPacket::new)
                .encoder(ClientLevelS2CPacket::toBytes)
                .consumerMainThread(ClientLevelS2CPacket::handle)
                .add();
        net.messageBuilder(SoulSceptreC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SoulSceptreC2SPacket::new)
                .encoder(SoulSceptreC2SPacket::toBytes)
                .consumerMainThread(SoulSceptreC2SPacket::handle)
                .add();
        net.messageBuilder(LiuLiXianSceptreC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(LiuLiXianSceptreC2SPacket::new)
                .encoder(LiuLiXianSceptreC2SPacket::toBytes)
                .consumerMainThread(LiuLiXianSceptreC2SPacket::handle)
                .add();
        net.messageBuilder(GuangYiBowC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(GuangYiBowC2SPacket::new)
                .encoder(GuangYiBowC2SPacket::toBytes)
                .consumerMainThread(GuangYiBowC2SPacket::handle)
                .add();
        net.messageBuilder(LiuLiXianActiveC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(LiuLiXianActiveC2SPacket::new)
                .encoder(LiuLiXianActiveC2SPacket::toBytes)
                .consumerMainThread(LiuLiXianActiveC2SPacket::handle)
                .add();
        net.messageBuilder(WcBowC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(WcBowC2SPacket::new)
                .encoder(WcBowC2SPacket::toBytes)
                .consumerMainThread(WcBowC2SPacket::handle)
                .add();
        net.messageBuilder(BlackFeisaC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(BlackFeisaC2SPacket::new)
                .encoder(BlackFeisaC2SPacket::toBytes)
                .consumerMainThread(BlackFeisaC2SPacket::handle)
                .add();
        net.messageBuilder(EliaoiC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(EliaoiC2SPacket::new)
                .encoder(EliaoiC2SPacket::toBytes)
                .consumerMainThread(EliaoiC2SPacket::handle)
                .add();
        net.messageBuilder(Use5C2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(Use5C2SPacket::new)
                .encoder(Use5C2SPacket::toBytes)
                .consumerMainThread(Use5C2SPacket::handle)
                .add();
        net.messageBuilder(Use6C2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(Use6C2SPacket::new)
                .encoder(Use6C2SPacket::toBytes)
                .consumerMainThread(Use6C2SPacket::handle)
                .add();
        net.messageBuilder(QiFuSceptreC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(QiFuSceptreC2SPacket::new)
                .encoder(QiFuSceptreC2SPacket::toBytes)
                .consumerMainThread(QiFuSceptreC2SPacket::handle)
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
        net.messageBuilder(ShangMengLiSwordTickS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ShangMengLiSwordTickS2CPacket::new)
                .encoder(ShangMengLiSwordTickS2CPacket::toBytes)
                .consumerMainThread(ShangMengLiSwordTickS2CPacket::handle)
                .add();
        net.messageBuilder(ShangMengLiAttackAnimationS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ShangMengLiAttackAnimationS2CPacket::new)
                .encoder(ShangMengLiAttackAnimationS2CPacket::toBytes)
                .consumerMainThread(ShangMengLiAttackAnimationS2CPacket::handle)
                .add();
        net.messageBuilder(CurveParticleS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(CurveParticleS2CPacket::new)
                .encoder(CurveParticleS2CPacket::toBytes)
                .consumerMainThread(CurveParticleS2CPacket::handle)
                .add();
        net.messageBuilder(YSRAnimationS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(YSRAnimationS2CPacket::new)
                .encoder(YSRAnimationS2CPacket::toBytes)
                .consumerMainThread(YSRAnimationS2CPacket::handle)
                .add();
        net.messageBuilder(NULLAnimationS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(NULLAnimationS2CPacket::new)
                .encoder(NULLAnimationS2CPacket::toBytes)
                .consumerMainThread(NULLAnimationS2CPacket::handle)
                .add();
        net.messageBuilder(YuanShiRenC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(YuanShiRenC2SPacket::new)
                .encoder(YuanShiRenC2SPacket::toBytes)
                .consumerMainThread(YuanShiRenC2SPacket::handle)
                .add();
        net.messageBuilder(CgswdC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(CgswdC2SPacket::new)
                .encoder(CgswdC2SPacket::toBytes)
                .consumerMainThread(CgswdC2SPacket::handle)
                .add();
        net.messageBuilder(YxwgC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(YxwgC2SPacket::new)
                .encoder(YxwgC2SPacket::toBytes)
                .consumerMainThread(YxwgC2SPacket::handle)
                .add();
        net.messageBuilder(MyMissionC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(MyMissionC2SPacket::new)
                .encoder(MyMissionC2SPacket::toBytes)
                .consumerMainThread(MyMissionC2SPacket::handle)
                .add();
        net.messageBuilder(QuickChooseC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(QuickChooseC2SPacket::new)
                .encoder(QuickChooseC2SPacket::toBytes)
                .consumerMainThread(QuickChooseC2SPacket::handle)
                .add();
        net.messageBuilder(LeiyanC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(LeiyanC2SPacket::new)
                .encoder(LeiyanC2SPacket::toBytes)
                .consumerMainThread(LeiyanC2SPacket::handle)
                .add();
        net.messageBuilder(PlayerFlyingSpeedSetS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(PlayerFlyingSpeedSetS2CPacket::new)
                .encoder(PlayerFlyingSpeedSetS2CPacket::toBytes)
                .consumerMainThread(PlayerFlyingSpeedSetS2CPacket::handle)
                .add();
        net.messageBuilder(CastleBowC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(CastleBowC2SPacket::new)
                .encoder(CastleBowC2SPacket::toBytes)
                .consumerMainThread(CastleBowC2SPacket::handle)
                .add();
        net.messageBuilder(CastleSceptreC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(CastleSceptreC2SPacket::new)
                .encoder(CastleSceptreC2SPacket::toBytes)
                .consumerMainThread(CastleSceptreC2SPacket::handle)
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
        net.messageBuilder(ClientLimitCheckS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ClientLimitCheckS2CPacket::new)
                .encoder(ClientLimitCheckS2CPacket::toBytes)
                .consumerMainThread(ClientLimitCheckS2CPacket::handle)
                .add();
        net.messageBuilder(ClientLimitSetS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ClientLimitSetS2CPacket::new)
                .encoder(ClientLimitSetS2CPacket::toBytes)
                .consumerMainThread(ClientLimitSetS2CPacket::handle)
                .add();
        net.messageBuilder(ClientLimitWrongC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ClientLimitWrongC2SPacket::new)
                .encoder(ClientLimitWrongC2SPacket::toBytes)
                .consumerMainThread(ClientLimitWrongC2SPacket::handle)
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
        net.messageBuilder(SoraSwordC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SoraSwordC2SPacket::new)
                .encoder(SoraSwordC2SPacket::toBytes)
                .consumerMainThread(SoraSwordC2SPacket::handle)
                .add();
        net.messageBuilder(LifeElementBowC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(LifeElementBowC2SPacket::new)
                .encoder(LifeElementBowC2SPacket::toBytes)
                .consumerMainThread(LifeElementBowC2SPacket::handle)
                .add();
        net.messageBuilder(LifeElementSceptreC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(LifeElementSceptreC2SPacket::new)
                .encoder(LifeElementSceptreC2SPacket::toBytes)
                .consumerMainThread(LifeElementSceptreC2SPacket::handle)
                .add();
        net.messageBuilder(WaterElementBowC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(WaterElementBowC2SPacket::new)
                .encoder(WaterElementBowC2SPacket::toBytes)
                .consumerMainThread(WaterElementBowC2SPacket::handle)
                .add();
        net.messageBuilder(WaterElementSceptreC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(WaterElementSceptreC2SPacket::new)
                .encoder(WaterElementSceptreC2SPacket::toBytes)
                .consumerMainThread(WaterElementSceptreC2SPacket::handle)
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
        net.messageBuilder(FireElementBowC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(FireElementBowC2SPacket::new)
                .encoder(FireElementBowC2SPacket::toBytes)
                .consumerMainThread(FireElementBowC2SPacket::handle)
                .add();
        net.messageBuilder(FireElementSceptreC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(FireElementSceptreC2SPacket::new)
                .encoder(FireElementSceptreC2SPacket::toBytes)
                .consumerMainThread(FireElementSceptreC2SPacket::handle)
                .add();
        net.messageBuilder(ElementEffectTimeS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ElementEffectTimeS2CPacket::new)
                .encoder(ElementEffectTimeS2CPacket::toBytes)
                .consumerMainThread(ElementEffectTimeS2CPacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message)
    {
        ClientUtils.clientPacketLimit--;
        if (ClientUtils.clientPacketLimit > 0) INSTANCE.sendToServer(message);
        else {
            if (ClientUtils.clientPlayer != null) {
                Compute.FormatMSGSend(ClientUtils.clientPlayer, Component.literal("安全").withStyle(ChatFormatting.GREEN),
                        Component.literal("请减少操作频率或降低连点器/脚本每秒操作频率！当前频率已超过150/s，当你看到此信息时，请联系管理员。").withStyle(ChatFormatting.RED));
            }
        }
    }

    public static <MSG> void sendToClient(MSG message , ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}
