package com.Very.very.NetWorking;

import com.Very.very.NetWorking.Packets.AnimationPackets.*;
import com.Very.very.NetWorking.Packets.AttributePackets.*;
import com.Very.very.NetWorking.Packets.BowSoundParticle.BowShootS2CPacket;
import com.Very.very.NetWorking.Packets.BowSoundParticle.SkyBowShootS2CPacket;
import com.Very.very.NetWorking.Packets.CodeSceptrePackets.CodeC2SPacket;
import com.Very.very.NetWorking.Packets.DrugsC2SPacket;
import com.Very.very.NetWorking.Packets.EntropyPackets.EntropyS2CPacket;
import com.Very.very.NetWorking.Packets.Limit.LimitC2SPacket;
import com.Very.very.NetWorking.Packets.Limit.ScreenCloseC2SPacket;
import com.Very.very.NetWorking.Packets.ManaSyncS2CPacket;
import com.Very.very.NetWorking.Packets.MusicPlayerPackets.MusicIdolS2CPacket;
import com.Very.very.NetWorking.Packets.ParticlePackets.*;
import com.Very.very.NetWorking.Packets.PrefixPackets.PrefixS2CPacket;
import com.Very.very.NetWorking.Packets.QuartzSword.QuartzSwordParticleS2CPacket;
import com.Very.very.NetWorking.Packets.QuartzSword.QuartzSwordPlayerS2CPacket;
import com.Very.very.NetWorking.Packets.ResetC2SPacket;
import com.Very.very.NetWorking.Packets.ShieldSyncS2CPacket;
import com.Very.very.NetWorking.Packets.SkillPackets.*;
import com.Very.very.NetWorking.Packets.SkillPackets.Charging.*;
import com.Very.very.NetWorking.Packets.SmartPhonePackets.*;
import com.Very.very.NetWorking.Packets.SoundsPackets.SoundsS2CPacket;
import com.Very.very.NetWorking.Packets.TestPackets.TestC2SPacket;
import com.Very.very.NetWorking.Packets.ToolTipPackets.DailyMissionS2CPacket;
import com.Very.very.NetWorking.Packets.USE.*;
import com.Very.very.ValueAndTools.Utils.Utils;
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
    }

    public static <MSG> void sendToServer(MSG message)
    {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToClient(MSG message , ServerPlayer player)
    {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}
