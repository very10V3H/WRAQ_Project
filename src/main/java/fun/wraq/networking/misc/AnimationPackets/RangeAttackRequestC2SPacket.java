package fun.wraq.networking.misc.AnimationPackets;

import fun.wraq.common.Compute;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
import java.util.function.Supplier;

public class RangeAttackRequestC2SPacket {
    private final int Range;

    public RangeAttackRequestC2SPacket(int range) {
        this.Range = range;
    }

    public RangeAttackRequestC2SPacket(FriendlyByteBuf buf) {
        this.Range = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.Range);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer serverPlayer = context.getSender();
            Level level = serverPlayer.level();
            ServerPlayer player = serverPlayer;
            CompoundTag data = player.getPersistentData();
            Item item = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();

            HitResult hitResult = serverPlayer.pick(1, 0, false);
            Vec3 playerPos = serverPlayer.getEyePosition();
            List<Mob> mobList = level.getEntitiesOfClass(Mob.class, AABB.ofSize(playerPos, 20, 20, 20));
            for (Mob mob : mobList) {
                if (mob.getEyePosition().distanceTo(playerPos) <= 4 && Compute.Vec3Angle(hitResult.getLocation().subtract(playerPos), mob.getEyePosition().subtract(playerPos)) < Math.PI / 4.0) {
                    Mob monster = mob;
/*                    double Defence = MobAttributes.MonsterDefence(monster);
                    AttackEventModule.SnowRune2(data,monster,player,Defence);
                    double BaseDamage = PlayerAttributes.PlayerAttackDamage(player);
                    float BreakDefense = PlayerAttributes.PlayerBreakDefence(player);
                    double CriticalHitRate = PlayerAttributes.PlayerCriticalHitRate(player);
                    double CHitDamage = PlayerAttributes.PlayerCriticalHitDamage(player);
                    if (Utils.SnowRune2MobController.contains(monster)) Defence *= 0.5f;
                    double BreakDefence0 = PlayerAttributes.PlayerBreakDefence0(player);
                    int LightningArmorCount = Compute.LightningArmorCount(player);
                    if (monster instanceof Evoker && player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof ManaSword)
                        BreakDefense = 1.0d;
                    Random r = new Random();
                    double RanNum = r.nextDouble(1.00d);
                    AttackEventModule.MineSwordSlowDownForce(item, monster);
                    if (Defence == 0) Defence = (float) (monster.getAttribute(Attributes.ARMOR).getValue());

                    float damage = 0;
                    float ExDamage = 0;
                    float ExDamageIgnoreDefence = 0;
                    data.putBoolean("IsAttack",true);
                    // 变量定义与部分效果附加

                    ExDamage += AttackEventModule.ForestRune3(data,monster,BaseDamage); // 森林符石-生长汲取
                    ExDamage += AttackEventModule.BlackForest(data,monster,BaseDamage); // 灵魂收割者主动
                    ExDamageIgnoreDefence += AttackEventModule.WaltzCompute(player,monster,data); //华尔兹
                    ExDamageIgnoreDefence += AttackEventModule.LightningArmor(player,LightningArmorCount,data); // 唤雷套被动
                    ExDamageIgnoreDefence += AttackEventModule.SeaSword(data,monster,BaseDamage); //灵魂救赎者主动
                    ExDamageIgnoreDefence += AttackEventModule.VolcanoRune2(data,monster,BaseDamage); //火山符石-熔岩强击

                    if (RanNum < CriticalHitRate) {
                        if (BreakDefence0 >= Defence) {
                            damage = BaseDamage * (1.0d + CHitDamage) + ExDamage;
                            AttackEventModule.BlackForestExDamageNumGet1(data,monster,CHitDamage);
                        }
                        else {
                            damage = (BaseDamage * (1.0d + CHitDamage) + ExDamage) * (1.0d - (0.25F * (float) log(((Defence - BreakDefence0) * (1.0d - BreakDefense) * (E * E / 100) + 1))));
                            AttackEventModule.BlackForestExDamageNumGet2(data,monster,CHitDamage,Defence,BreakDefence0,BreakDefense);
                        } // 基本伤害计算
                        if (Utils.OverWorldLevelIsNight && player.level().equals(player.getServer().getLevel(Level.OVERWORLD)))
                            damage *= 0.8f; // 主世界怪物强化
                        data.putBoolean("Crit", true);
                        data.putDouble("DamageAmount",damage);
                        data.putDouble("DamageIgnoreDefenceAmount",ExDamageIgnoreDefence);
                        livingEntity.hurt(livingEntity.damageSources().playerAttack(player),damage + ExDamageIgnoreDefence);

                    } else {
                        if (BreakDefence0 >= Defence) {
                            damage = BaseDamage + ExDamage;
                            AttackEventModule.BlackForestSwordGet3(data,monster);
                        }
                        else {
                            damage = (BaseDamage + ExDamage) * (1.0d - (0.25F * (float) log(((Defence - BreakDefence0) * (1.0d - BreakDefense)) * (E * E / 100) + 1)));
                            AttackEventModule.BlackForestSwordGet4(data,monster,Defence,BreakDefence0,BreakDefense);
                        }
                        if (Utils.OverWorldLevelIsNight && player.level().equals(player.getServer().getLevel(Level.OVERWORLD)))
                            damage *= 0.8f;
                        data.putBoolean("Crit", false);
                        data.putDouble("DamageAmount",damage);
                        data.putDouble("DamageIgnoreDefenceAmount",ExDamageIgnoreDefence);
                        livingEntity.hurt(livingEntity.damageSources().playerAttack(player),damage + ExDamageIgnoreDefence);
                    }*/
                }
            }
            List<Player> playerList = level.getEntitiesOfClass(Player.class, AABB.ofSize(playerPos, 20, 20, 20));
            for (Player hurter : playerList) {
                if (hurter.getEyePosition().distanceTo(playerPos) <= 4 && Compute.Vec3Angle(hitResult.getLocation().subtract(playerPos), hurter.getEyePosition().subtract(playerPos)) < Math.PI / 4.0) {
/*                    double Defence = PlayerAttributes.PlayerDefence(hurter);
                    double BaseDamage = PlayerAttributes.PlayerAttackDamage(player);
                    float BreakDefense = PlayerAttributes.PlayerBreakDefence(player);
                    double CriticalHitRate = PlayerAttributes.PlayerCriticalHitRate(player);
                    double CHitDamage = PlayerAttributes.PlayerCriticalHitDamage(player);
                    double BreakDefence0 = PlayerAttributes.PlayerBreakDefence0(player);
                    data.putBoolean("IsAttack",true);
                    int LightningArmorCount = Compute.LightningArmorCount(player);
                    Random r = new Random();
                    double RanNum = r.nextDouble(1.00d);
                    AttackEventModule.MineSwordSlowDownForcePlayer(item, hurter);
                    if (Defence == 0) Defence = (float) hurter.getAttribute(Attributes.ARMOR).getValue();
                    float damage = 0;
                    float ExDamage = 0;
                    float ExDamageIgnoreDefence = 0;

                    ExDamage += AttackEventModule.ToPlayerBlackForest(data,hurter);
                    ExDamage += AttackEventModule.ToPlayerForestRune3(data,hurter);
                    ExDamageIgnoreDefence += AttackEventModule.ToPlayerWaltz(player,hurter,data);
                    ExDamageIgnoreDefence += AttackEventModule.LightningArmor(player,LightningArmorCount,data);
                    ExDamageIgnoreDefence += AttackEventModule.ToPlayerSeaSword(data,hurter);
                    ExDamageIgnoreDefence += AttackEventModule.ToPlayerVolcanoRune2(data,hurter);

                    if (RanNum < CriticalHitRate) { // 暴击
                        if (BreakDefence0 >= Defence) {
                            damage = (BaseDamage * (1.0d + CHitDamage) + ExDamage);
                            AttackEventModule.ToPlayerBlackForestGet1(data,hurter,CHitDamage);
                        }
                        else {
                            damage = (BaseDamage * (1.0d + CHitDamage) + ExDamage) * (1.0d - (0.25F * (float) log(((Defence - BreakDefence0) * (1.0d - BreakDefense) * (E * E / 100) + 1))));
                            AttackEventModule.ToPlayerBlackForestGet2(data,hurter,CHitDamage,BreakDefence0,BreakDefense,Defence);
                        }
                        data.putDouble("ToPlayerDamage",(damage + ExDamageIgnoreDefence) * 0.1f);
                        data.putBoolean("Crit", true);
                        hurter.hurt(hurter.damageSources().playerAttack(player), (damage + ExDamageIgnoreDefence) * 0.1f);
                    } else { //非暴击
                        if (BreakDefence0 >= Defence) {
                            damage = BaseDamage;
                            AttackEventModule.ToPlayerBlackForestGet3(data,hurter,CHitDamage);
                        }
                        else {
                            damage = BaseDamage * (1.0d - (0.25F * (float) log(((Defence - BreakDefence0) * (1.0d - BreakDefense)) * (E * E / 100) + 1)));
                            AttackEventModule.ToPlayerBlackForestGet4(data,hurter,CHitDamage,BreakDefence0,BreakDefense,Defence);
                        }
                        data.putDouble("ToPlayerDamage",(damage + ExDamageIgnoreDefence) * 0.1f);
                        data.putBoolean("Crit", false);
                        hurter.hurt(hurter.damageSources().playerAttack(player), (damage + ExDamageIgnoreDefence) * 0.1f);
                    }*/
                }
            }
        });
        return true;
    }
}
