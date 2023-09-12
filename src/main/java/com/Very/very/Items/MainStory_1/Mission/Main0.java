package com.Very.very.Items.MainStory_1.Mission;

import com.Very.very.Projectile.BowTest.MyArrow;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Struct.SwordSkillStruct.SwordSkill13;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.Moditems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.datafix.fixes.VillagerDataFix;
import net.minecraft.util.datafix.fixes.VillagerTradeFix;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.npc.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class Main0 extends Item{
    public Main0(Properties p_41383_) {
        super(p_41383_);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal("欢迎你来到").append(Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA)));
        components.add(Component.literal("这是一个任务物品，你可以看到它有着一个感叹号。"));
        components.add(Component.literal("在维瑞阿契，任务可以引导你探索这个世界。"));
        components.add(Component.literal("如果这个物品属于任务物品，你将可以看到："));
        components.add(Component.literal("在它底部描述有着它所属的任务章节。"));
        components.add(Component.literal("本任务属于维瑞阿契的序章，不是剧情的正式开始。"));
        components.add(Component.literal("维瑞阿契的序章，将会介绍维瑞阿契的各种要素。"));
        components.add(Component.literal("关于其他序章没有提及的要素，请在游戏过程中积极探索。"));
        components.add(Component.literal("希望你能有一个美好的游戏体验！"));
        components.add(Component.literal("本章作者：").append(Component.literal("very_H").withStyle(ChatFormatting.AQUA)));
        components.add(Component.literal(" "));
        components.add(Component.literal(" "));
        components.add(Component.literal("Prologue-O").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

/*    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public @Nullable Font getFont(ItemStack stack, FontContext context) {
                return IClientItemExtensions.super.getFont(stack, context);
            }
        });
        super.initializeClient(consumer);
    }*/



    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand)
    {
        if (level.isClientSide) {

        }
        if (!level.isClientSide) {
/*            Villager villager = new Villager(EntityType.VILLAGER,level);
            VillagerData villagerData = new VillagerData(VillagerType.PLAINS, VillagerProfession.ARMORER,5);
            villager.setVillagerData(villagerData);
            MerchantOffer merchantOffer = new MerchantOffer(Moditems.GoldCoin.get().getDefaultInstance(),Moditems.GoldCoin.get().getDefaultInstance(),Moditems.GoldCoin.get().getDefaultInstance(),0,Integer.MAX_VALUE,0,0,0);
            MerchantOffers merchantOffers = new MerchantOffers();
            merchantOffers.add(merchantOffer);
            villager.setOffers(merchantOffers);
            villager.moveTo(player.getPosition(1));
            villager.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0);
            villager.setCustomName(Component.literal("自定义村民").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA));
            level.addFreshEntity(villager);*/
/*            float damage = Compute.PlayerAttackDamage(player);
            float CriticalHitRate = Compute.PlayerCriticalHitRate(player);
            float CHitDamage = Compute.PlayerCriticalHitDamage(player);
            float BreakDefence = Compute.PlayerBreakDefence(player);
            float BreakDefence0 = Compute.PlayerBreakDefence0(player);
            float ExpUp = Compute.ExpGetImprove(player);
            Random random = new Random();
            for (int i = 0; i < 20; i ++) {
                MyArrow arrow = new MyArrow(EntityType.ARROW,player,level,player,damage,CriticalHitRate,CHitDamage,BreakDefence,ExpUp,BreakDefence0,player.getItemInHand(InteractionHand.MAIN_HAND),false);
                arrow.setDeltaMovement(0,-1,0);
                arrow.moveTo(player.getX() + random.nextInt(-2,2),player.getY() + random.nextInt(-5,5) + 10,player.getZ() + random.nextInt(-2,2));
                level.addFreshEntity(arrow);
            }*/
/*            float damage = Compute.PlayerAttackDamage(player);
            float CriticalHitRate = Compute.PlayerCriticalHitRate(player);
            float CHitDamage = Compute.PlayerCriticalHitDamage(player);
            float BreakDefence = Compute.PlayerBreakDefence(player);
            float BreakDefence0 = Compute.PlayerBreakDefence0(player);
            float ExpUp = Compute.ExpGetImprove(player);
            Random random = new Random();
            for (int i = 0; i < 20; i ++) {
                MyArrow arrow = new MyArrow(EntityType.ARROW,player,level,player,damage,CriticalHitRate,CHitDamage,BreakDefence,ExpUp,BreakDefence0,player.getItemInHand(InteractionHand.MAIN_HAND));
                arrow.setDeltaMovement(0,-1,0);
                arrow.moveTo(player.getX() + random.nextInt(-5,5),player.getY() + random.nextInt(-2,2) + 10,player.getZ() + random.nextInt(-5,5));
                level.addFreshEntity(arrow);
            }*/
/*            ServerPlayer serverPlayer = (ServerPlayer) player;
            player.sendSystemMessage(Component.literal("" + serverPlayer.getIpAddress()));*/
/*            SwordSkill13 swordSkill13 = Utils.SwordSkill13Map.get(player);
            CompoundTag data = player.getPersistentData();
            player.sendSystemMessage(Component.literal("" + swordSkill13.getTime() + " " + swordSkill13.getCount()));
            player.sendSystemMessage(Component.literal("" + player.getServer().getTickCount()));
            if (swordSkill13.getTime() > player.getServer().getTickCount()) {
                player.sendSystemMessage(Component.literal("" + Compute.PlayerAttackDamage(player) * swordSkill13.getCount() * 0.005 * Compute.SwordSkillLevelGet(data,13)));
            }*/
/*            var animation = (ModifierLayer<IAnimation>) PlayerAnimationAccess.getPlayerAssociatedData((AbstractClientPlayer) player).get(new ResourceLocation(Utils.MOD_ID, "animation"));
            if (animation != null) {
                animation.setAnimation(new KeyframeAnimationPlayer(PlayerAnimationRegistry.getAnimation(new ResourceLocation(Utils.MOD_ID,"attack"))));
            }
            player.sendSystemMessage(Component.literal("" + 1));*/
            /*            List<Mob> list = level.getEntitiesOfClass(Mob.class,AABB.ofSize(player.getPosition(1.0f),10,10,10));
            player.sendSystemMessage(Component.literal(""+list.size()));*/
/*            int num = 160;
            for (int i = 0; i < num; i++) {
                double angle = (2*Math.PI/num)*(i);
                double X = Math.cos(angle) * 0.5;
                double Y = 0;
                double Z = Math.sin(angle) * 0.5;
                level.addParticle(ModParticles.BLACKFOREST_RECALL.get(),player.getX(),player.getY() + 1,player.getZ(),
                        X,Y,Z);
            }*/
        }
        if(!level.isClientSide) {
/*            HitResult hitResult = player.pick(1,0,false);
            Vec3 playerPos = player.getEyePosition();
            List<Mob> mobList = level.getEntitiesOfClass(Mob.class, AABB.ofSize(playerPos,20,20,20));
            for (Mob mob : mobList) {
                if (mob.getEyePosition().distanceTo(playerPos) <= 4 && Compute.Vec3Angle(hitResult.getLocation().subtract(playerPos),mob.getEyePosition().subtract(playerPos)) < Math.PI / 4.0) {
                    mob.hurt(mob.damageSources().playerAttack(player),1.0f);
                }
            }*/
/*            Utils.SpiderRecall.KillCount = 0;
            Utils.KazeRecall.KillCount = 0;
            Utils.HuskRecall.KillCount = 0;
            Utils.SeaRecall.KillCount = 0;
            Utils.LightningRecall.KillCount = 0;
            Utils.NetherRecall.KillCount = 0;
            Utils.SnowRecall.KillCount = 0;
            Utils.ForestRecall.KillCount = 0;
            Utils.VolcanoRecall.KillCount = 0;
            for (PlayerCallBack playerCallBack : Utils.playerCallBackQueue) {
                player.sendSystemMessage(Component.literal(playerCallBack.getPlayer().getName().getString()+" "+playerCallBack.getBlockPos()));
            }*/
/*            int num = 40;
            for (int i = 0; i < num; i++) {
                double angle = (2*Math.PI/num)*(i);
                double X = player.getPosition(1.0f).x + Math.cos(angle) * 0.5;
                double Y = player.getPosition(1.0f).y;
                double Z = player.getPosition(1.0f).z + Math.sin(angle) * 0.5;
                ClientboundLevelParticlesPacket clientboundLevelParticlesPacket =
                        new ClientboundLevelParticlesPacket(ModParticles.BLACKFOREST.get(),true,X,Y,Z,
                                1,1,1,0,8);
                List<ServerPlayer> playerList = level.getServer().getPlayerList().getPlayers();
                for (ServerPlayer serverPlayer : playerList) {
                    serverPlayer.connection.send(clientboundLevelParticlesPacket);
                }
            }*/
/*            ClientboundSetActionBarTextPacket clientboundSetActionBarTextPacket =
                    new ClientboundSetActionBarTextPacket(Component.literal("test!"));
            ServerPlayer serverPlayer = (ServerPlayer) player;
            serverPlayer.connection.send(clientboundSetActionBarTextPacket);*/
/*            Utils.KazeKillCount = 0;*/
/*            ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                    new ClientboundSetSubtitleTextPacket(Component.literal("狂风击杀数:").withStyle(Utils.styleOfKaze));
            ServerPlayer serverPlayer = (ServerPlayer) player;
            serverPlayer.connection.send(clientboundSetSubtitleTextPacket);*/

            /*            player.changeDimension(player.getServer().getLevel(Level.END));*/
/*            player.sendSystemMessage(Component.literal(""+player.getDeltaMovement()));
            Giant zombie = new Giant(EntityType.GIANT,level);
            zombie.moveTo(player.getPosition(1.0f));
            level.addFreshEntity(zombie);*/
/*
            player.sendSystemMessage(Component.literal("" + Minecraft.getInstance().player.getMaxHealth()));
            player.sendSystemMessage(Component.literal("" + Minecraft.getInstance().player.getHealth()));
            player.sendSystemMessage(Component.literal("" + Minecraft.getInstance().player.getFoodData().getFoodLevel()));
*/
/*
            level.addParticle(ModParticles.FIRST_PARTICLE.get(),player.getX() + 1,player.getY(),player.getZ() + 1,0,0,0);
            level.addParticle(ModParticles.BREAKDEFENCE_MANA.get(),player.getX() + 1,player.getY()+0.1,player.getZ() + 1,0,0,0);
            level.addParticle(ModParticles.DAMAGE_MANA.get(),player.getX() + 1,player.getY()+0.2,player.getZ() + 1,0,0,0);
            level.addParticle(ModParticles.EFFECT_MANA.get(),player.getX() + 1,player.getY()+0.3,player.getZ() + 1,0,0,0);
            level.addParticle(ModParticles.GATHER_MANA.get(),player.getX() + 1,player.getY()+0.4,player.getZ() + 1,0,0,0);
            level.addParticle(ModParticles.KAZE_MANA.get(),player.getX() + 1,player.getY()+0.5,player.getZ() + 1,0,0,0);
            level.addParticle(ModParticles.LIGHTNING_MANA.get(),player.getX() + 1,player.getY()+0.6,player.getZ() + 1,0,0,0);
            level.addParticle(ModParticles.RANGE_MANA.get(),player.getX() + 1,player.getY()+0.7,player.getZ() + 1,0,0,0);
            level.addParticle(ModParticles.SNOW_MANA.get(),player.getX() + 1,player.getY()+0.8,player.getZ() + 1,0,0,0);
*/
/*            List<Mob> list = level.getEntitiesOfClass(Mob.class,AABB.ofSize(player.getPosition(1.0f),30,30,30));
            for (Mob mob : list) {
                mob.setDeltaMovement(player.getPosition(1.0f).subtract(mob.getPosition(1.0f)).scale(0.2f));
            }*/
/*            Vec3 Pick = player.pick(1.0f,0,true).getLocation();
            Vec3 Rotation = player.pick(10f,0,true).getLocation().subtract(Pick);
            Compute.EntityFaceConnectCircleCreate(player,player.pick(1.0f,0,true).getLocation(),player.getPosition(1.0f),
                    0,0.5,16,ParticleTypes.SCRAPE,Rotation.x,Rotation.y,Rotation.z);*/
/*            ServerPlayer serverPlayer = (ServerPlayer) player;
            ClientboundSetTitleTextPacket clientboundSetTitleTextPacket = new ClientboundSetTitleTextPacket(Component.literal("你好！"));
            serverPlayer.connection.send(clientboundSetTitleTextPacket);*/
/*            ItemStack itemStack = player.getOffhandItem();
            CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
            String[] DataName = {
                    "PlainBrewingExp",
                    "ForestBrewingExp",
                    "LakeBrewingExp",
                    "VolcanoBrewingExp",
                    "SnowBrewingExp",
                    "SkyBrewingExp",
                    "EvokerBrewingExp",
                    "NetherBrewingExp"
            };
            for (int i = 0; i < 8; i++) {
                data.putInt(DataName[i],1000);
            }*/
/*            ClientboundSetEntityMotionPacket clientboundSetEntityMotionPacket = new ClientboundSetEntityMotionPacket(player.getId(),new Vec3(0,1,0));
            ServerPlayer serverPlayer = (ServerPlayer) player;
            serverPlayer.connection.send(clientboundSetEntityMotionPacket);
            BlockState blockState = Blocks.ICE.defaultBlockState();
            level.setBlockAndUpdate(new BlockPos(player.getPosition(1.0f)),blockState);
            level.destroyBlock(new BlockPos(player.getPosition(1.0f)),false);
            if (player.isOnGround()) ((ServerPlayer) player).sendSystemMessage(Component.literal("onGround!"));
            else ((ServerPlayer) player).sendSystemMessage(Component.literal("onSky!"));
            */
/*            ClientboundLevelParticlesPacket clientboundLevelParticlesPacket = new ClientboundLevelParticlesPacket(
                    ParticleTypes.TOTEM_OF_UNDYING,
                    true,
                    player.getX(),
                    player.getY(),
                    player.getZ(),
                    0,0,0,0,8
            );
            ServerPlayer serverPlayer = (ServerPlayer) player;
            serverPlayer.connection.send(clientboundLevelParticlesPacket);*/
/*
            Vec2 vec2 = new Vec2((float) player.getX(),(float) player.getZ());
            ((ServerPlayer) player).sendSystemMessage(Component.literal(""+Compute.DotIn(vec2, Utils.KazeDot1, Utils.KazeDot2, Utils.KazeDot3, Utils.KazeDot4)));
*/
        }
/*            CompoundTag data = player.getPersistentData();
            data.putInt("KillCountOfWitherSkeleton",400);
            data.putInt("KillCountOfZombiePigLin",400);
            data.putInt("KillCountOfNetherSkeleton",400);
            data.putInt("KillCountOfNetherMagma",400);
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.HOUR,-24);
            Date date = cal.getTime();
            SimpleDateFormat tmpDate = new SimpleDateFormat("yyyyMMddHHmmss");
            String DateString = tmpDate.format(date);
            data.putString("NSDATE",DateString);
            player.sendSystemMessage(Component.literal(Utils.NSController+" "+Utils.NSClear));*/
/*
            level.addParticle(ParticleTypes.EXPLOSION_EMITTER,player.getX(),player.getY(),player.getZ(),0,0,0);
*/
/*            MagmaCube magmaCube = new MagmaCube(EntityType.MAGMA_CUBE,level);
            magmaCube.setSize(2,true);
            magmaCube.getAttribute(Attributes.MAX_HEALTH).setBaseValue(1024);
            magmaCube.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(20);
            magmaCube.setHealth(magmaCube.getMaxHealth());
            magmaCube.setItemSlot(EquipmentSlot.HEAD,Moditems.armorevoker.get().getDefaultInstance());
            magmaCube.moveTo(player.getPosition(1.0f));
            level.addFreshEntity(magmaCube);*/
/*            ClientboundSoundPacket clientboundSoundPacket = new ClientboundSoundPacket(SoundEvents.EXPERIENCE_ORB_PICKUP,SoundSource.PLAYERS,
                    player.getX(),player.getY(),player.getZ(),0.5f,1,1);
            ServerPlayer serverPlayer = (ServerPlayer) player;
            serverPlayer.connection.send(clientboundSoundPacket);*/

/*            ServerPlayer serverPlayer = (ServerPlayer) player;
            Compute.EntityEffectVerticleCircleParticle(player,1.5,0.4,8,ParticleTypes.WITCH);
            Compute.EntityEffectVerticleCircleParticle(player,1.25,0.4,8,ParticleTypes.WITCH);
            Compute.EntityEffectVerticleCircleParticle(player,1,0.4,8,ParticleTypes.WITCH);
            Compute.EntityEffectVerticleCircleParticle(player,0.75,0.4,8,ParticleTypes.WITCH);
            Compute.EntityEffectVerticleCircleParticle(player,0.5,0.4,8,ParticleTypes.WITCH);
            ClientboundLevelParticlesPacket clientboundLevelParticlesPacket = new ClientboundLevelParticlesPacket(ParticleTypes.WITCH,true,
                    player.getX(),
                    player.getY(),
                    player.getZ(),
                    0.5f,
                    0.5f,
                    0.5f,
                    1,
                    50);
            List<ServerPlayer> list = player.getServer().getPlayerList().getPlayers();
            for (ServerPlayer serverPlayer1 : list) {
                serverPlayer1.connection.send(clientboundLevelParticlesPacket);
            }*/
/*        if (!level.isClientSide) {
            CompoundTag data = player.getPersistentData();
            Compute.ExpPercentGetAndMSGSend(player,0.5,0);
        }*/
/*        if (!level.isClientSide) {
            List<Monster> list = level.getEntitiesOfClass(Monster.class, AABB.ofSize(new Vec3(260,98,1105),130,86,115));
            player.sendSystemMessage(Component.literal(list.size()+" "));
            List<Monster> list1 = level.getEntitiesOfClass(Monster.class, AABB.ofSize(new Vec3(1425,70,566),100,25,110));
            player.sendSystemMessage(Component.literal(list1.size()+" "));
        }*/

/*        if (!level.isClientSide) {
            player.sendSystemMessage(player.getName());
            List<Monster> list = level.getEntitiesOfClass(Monster.class,AABB.ofSize(player.getPosition(1.0f),200,200,200));
            player.sendSystemMessage(Component.literal(list.size()+""));
            if(list.size() > 30)
            {
                player.sendSystemMessage(Component.literal("周围怪物数量过多，已移除一部分怪物。"));
                for(int i = 30; i < list.size(); i++){
                    list.get(i).remove(Entity.RemovalReason.KILLED);
                }
            }
        }*/
/*        if (!level.isClientSide) {
            if(player.isShiftKeyDown()){
                MarketItemInfo marketItemInfo = Utils.MarketInfo.poll();
                Utils.itemStackMapInit();
                MarketPlayerInfo marketPlayerInfo = new MarketPlayerInfo(marketItemInfo.getPlayer(),marketItemInfo.getPrice());
                Utils.MarketPlayerInfo.add(marketPlayerInfo);
                if(Utils.itemStackMap.containsKey(marketItemInfo.getItemStackName())){
                    ItemStack itemStack = Utils.itemStackMap.get(marketItemInfo.getItemStackName());
                    itemStack.setCount(marketItemInfo.getItemStackCount());
                    Compute.ItemStackGive(player,itemStack);
                }
                if(Utils.PotionMap.containsKey(marketItemInfo.getItemStackName())){
                    ItemStack itemStack = Items.POTION.getDefaultInstance();
                    itemStack.getOrCreateTagElement(Utils.MOD_ID);
                    itemStack.getOrCreateTag().putString("Potion",marketItemInfo.getItemStackName());
                    itemStack.setCount(marketItemInfo.getItemStackCount());
                    Compute.ItemStackGive(player,itemStack);
                }
            }
            else{
                ItemStack itemStack = player.getItemInHand(InteractionHand.OFF_HAND);
                if(itemStack.is(Items.POTION)){
                    itemStack.getOrCreateTag().getString("Potion");
                    if(Utils.PotionMap.containsKey(itemStack.getOrCreateTag().getString("Potion"))){
                        MarketItemInfo marketItemInfo = new MarketItemInfo(player.getName().getString(),itemStack.getCount()+" "+itemStack.getOrCreateTag().getString("Potion"),233);
                        Utils.MarketInfo.add(marketItemInfo);
                    }
                }
                else{
                    MarketItemInfo marketItemInfo = new MarketItemInfo(player.getName().getString(),player.getItemInHand(InteractionHand.OFF_HAND).toString(),233);
                    if(Utils.itemStackMap.containsKey(marketItemInfo.getItemStackName())) Utils.MarketInfo.add(marketItemInfo);
                }
                Iterator<MarketItemInfo> iterator = Utils.MarketInfo.iterator();
                while(iterator.hasNext()){
                    MarketItemInfo marketItemInfo1 = iterator.next();
                    player.sendSystemMessage(Component.literal(marketItemInfo1.getPlayer()+marketItemInfo1.getItemStack()+marketItemInfo1.getPrice()));
                }
            }
        }*/
/*        if(!level.isClientSide){
            CompoundTag data = player.getPersistentData();
            data.putInt("KillCountOfForestSkeleton",1001);
            data.putInt("KillCountOfForestZombie",1001);
            data.putInt("KillCountOfPlainZombie",1001);
            data.putInt("KillCountOfLakeDrowned",1001);
            data.putInt("KillCountOfVolcanoBlazw",1001);
        }*/
/*        if (!level.isClientSide) {
            Inventory inventory = player.getInventory();
            ItemStack itemStack = Items.DIRT.getDefaultInstance();
            itemStack.setCount(64);
            Compute.ItemStackGive(player,itemStack);
        }*/
/*        if(interactionHand.equals(InteractionHand.OFF_HAND))
        {
            ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
            CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
            data.putInt("KillCount",1001);
        }*/
/*            if(player.isShiftKeyDown()) level.getServer().setDifficulty(Difficulty.NORMAL,false);
            else level.getServer().setDifficulty(Difficulty.PEACEFUL,false);
            CompoundTag data = player.getPersistentData();
            data.putInt("Green",-1);
            data.putInt(StringUtils.ForestRune.ForestRune,-1);
            data.putInt("ManaRune",-1);
            data.putInt("volcanoRune",-1);*/
/*            ServerPlayer serverPlayer = (ServerPlayer) player;
            player.sendSystemMessage(Component.literal(serverPlayer.getIpAddress()));*/
/*        HitResult hitResult =  player.pick(1,0,false);
        level.addParticle(ParticleTypes.COMPOSTER,hitResult.getLocation().x,hitResult.getLocation().y,hitResult.getLocation().z,0,0,0);*/
/*        Calendar calendar = Calendar.getInstance();
        player.sendSystemMessage(Component.literal(String.valueOf(calendar.get(Calendar.YEAR))));
        player.sendSystemMessage(Component.literal(String.valueOf(calendar.get(Calendar.MONTH))));
        player.sendSystemMessage(Component.literal(String.valueOf(calendar.get(Calendar.DATE))));
        player.sendSystemMessage(Component.literal(String.valueOf(calendar.get(Calendar.HOUR))));
        String string = "123456";
        player.sendSystemMessage(Component.literal(String.valueOf(Integer.valueOf(string))));*/
/*        if(!level.isClientSide && interactionHand.equals(InteractionHand.OFF_HAND))
        {
            String[] DataName = {
                    "PlainBrewingExp",
                    "ForestBrewingExp",
                    "LakeBrewingExp",
                    "VolcanoBrewingExp",
                    "SnowBrewingExp",
                    "SkyBrewingExp",
                    "EvokerBrewingExp",
                    "NetherBrewingExp"
            };
            ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
            CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
            for(int i = 0; i < 8; i++){
                data.putInt(DataName[i],1500);
            }
        }*/
/*        if(!level.isClientSide)
        {
            CompoundTag data = player.getPersistentData();
            if(data.contains("Xp")) data.putDouble("Xp",data.getDouble("Xp")+10);
            else data.putDouble("Xp",1);
            player.sendSystemMessage(Component.literal(data.getDouble("Xp")+"Xp"));
        }*/
/*        if(!level.isClientSide)
        {
            *//*TowerBuild.Build(level);
            if(interactionHand.equals(InteractionHand.OFF_HAND)) TowerBuild.Destory(level);*//*
*//*            ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
            itemStack.setCount(itemStack.getCount()+1);*//*
        }*/
        /*player.playSound(SoundEvents.PLAYER_ATTACK_CRIT);*/
/*        if(!level.isClientSide)
        {
            Player player1 = level.getNearestPlayer(TargetingConditions.DEFAULT,player,10,10,10);
            if(player1 != null)
            {
                player1.sendSystemMessage(Component.literal("test!"));
            }
            else
            {
                Monster monster = level.getNearestEntity(Monster.class,TargetingConditions.DEFAULT,player,10,10,10, AABB.ofSize(player.getPosition(1.0F),10,10,10));
                if(monster != null)
                {
                    monster.moveTo(player.getPosition(1.0f));
                }
            }
        }*/
/*        Zombie zombie = new Zombie(EntityType.ZOMBIE,level);
        zombie.getAttribute(Attributes.MAX_HEALTH).setBaseValue(10000);
        zombie.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0);
        zombie.setHealth(zombie.getMaxHealth());
        zombie.setItemSlot(EquipmentSlot.HEAD, Moditems.Armor2.get().getDefaultInstance());
        zombie.moveTo(player.getPosition(1));
        level.addFreshEntity(zombie);*/
        /*player.sendSystemMessage(Component.literal("test!"));
        level.playSound(player,player.getX(),player.getY(),player.getZ(), ModSounds.IDOL.get(), SoundSource.MUSIC,1f,1f);*/
/*        MySnowBall snowball = new MySnowBall(level,player);
        snowball.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, 1.5F, 1.0F);
        level.addFreshEntity(snowball);*/
/*        FireBallTest fireBallTest = new FireBallTest(level,player,0,0,0,2);
        fireBallTest.shootFromRotation(player,player.getXRot(),player.getYRot(),0.0F,3.0F,0.0F);
        level.addFreshEntity(fireBallTest);*/
/*        MyArrow arrow = new MyArrow(EntityType.ARROW,player,level);
        arrow.shootFromRotation(player,player.getXRot(),player.getYRot(),0.0F,3.0F,1.0F);
        level.addFreshEntity(arrow);*/
/*        Arrowtest arrowtest = new Arrowtest(EntityType.DRAGON_FIREBALL,level);
        arrowtest.shootFromRotation(player,player.getXRot(),player.getYRot(),0.0F,3.0F,1.0F);
        level.addFreshEntity(arrowtest);*/
/*        player.sendSystemMessage(Component.literal(String.valueOf(ConfigTest.VALUE.get())));
        if(level.getNearestEntity(Monster.class,TargetingConditions.DEFAULT,player,player.getX(),player.getY(),player.getZ(),AABB.ofSize(player.getPosition(1.0F),10,10,10)) != null)
        {
            Monster monster = level.getNearestEntity(Monster.class,TargetingConditions.DEFAULT,player,player.getX(),player.getY(),player.getZ(),AABB.ofSize(player.getPosition(1.0F),10,10,10));
            monster.moveTo(player.getX(),player.getY(),player.getZ());
        }
        level.addFreshEntity(new EvokerFangs(level, player.getX(), player.getY(), player.getZ(), 1.0F, 1, player));*/
/*        HitResult result = player.pick(10,0,true);
        Vec3 PickPos = result.getLocation();
        Vec3 PlayerPos = player.getEyePosition();
        Vec3 PickVec = PickPos.subtract(PlayerPos);
        Vec3 PickVecNormal = PickVec.normalize();

        player.sendSystemMessage(Component.literal(String.valueOf(PickVecNormal.x)+" "+PickVecNormal.y+" "+PickVecNormal.z));*/
/*        if(!level.isClientSide && level.getNearestEntity(Monster.class,TargetingConditions.DEFAULT,player,player.getX(),player.getY(),player.getZ(),AABB.ofSize(player.getPosition(1.0F),10,10,10)) != null)
        {
            List<Monster> List = level.getNearbyEntities(Monster.class,TargetingConditions.DEFAULT,player,AABB.ofSize(player.getPosition(1.0F),10,10,10));
            ListIterator<Monster> iterator = List.listIterator();
            while(iterator.hasNext())
            {
                Monster monster = iterator.next();
                monster.forceAddEffect(new MobEffectInstance(MobEffects.WITHER,100,10),player);
                LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT,level);
                lightningBolt.setDamage(0);
                lightningBolt.setCause((ServerPlayer) player);
                monster.hurt(DamageSource.playerAttack(player),10);
                lightningBolt.moveTo(monster.getPosition(1));
                monster.playSound(SoundEvents.LIGHTNING_BOLT_IMPACT,0.0000001F,0.0000001F);
                lightningBolt.setSilent(true);
                level.addFreshEntity(lightningBolt);
            }
        }*/
/*        if(!level.isClientSide)
        {
            List<Player> playerList = level.getNearbyPlayers(TargetingConditions.DEFAULT,player,AABB.ofSize(player.getPosition(1.0F),10,10,10));
            Iterator<Player> iterator = playerList.iterator();
            while(iterator.hasNext())
            {
                Player player1 = iterator.next();
                player1.sendSystemMessage(Component.literal("test!"));
            }
        }*/
/*        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,100,2));*/
        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }


}
