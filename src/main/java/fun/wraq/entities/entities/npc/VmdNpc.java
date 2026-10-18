/** AI-Generated, 2026-10-17 */
package fun.wraq.entities.entities.npc;

import fun.wraq.common.impl.npc.VmdNpcLike;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/**
 * VMD 自研的玩家模型 NPC，替代 CustomNPCs 的 {@code EntityCustomNpc}。
 * <p>
 * 只实现服务器实际需要的四项能力：玩家模型、玩家皮肤、禁止移动、注视附近玩家；
 * 外加「可穿戴装备与手持物品」（与原版玩家行为一致）。
 * <p>
 * 交互：右键不会打开任何界面（{@link #mobInteract} 直接返回 SUCCESS），
 * 真正的对话逻辑由 {@code VillagerEvents} 通过 {@link VmdNpcLike#getDialogId()} 路由，
 * 因此服务端与客户端都不需要为 NPC 单独写一套交互协议。
 * <p>
 * 渲染见 {@code fun.wraq.render.entity.VmdNpcRenderer}，皮肤解析见
 * {@code fun.wraq.render.entity.NpcSkinCache}。
 */
public class VmdNpc extends PathfinderMob implements VmdNpcLike {

    // ---- 同步字段：客户端渲染与交互路由都需要 ----
    private static final EntityDataAccessor<String> DATA_DIALOG_ID =
            SynchedEntityData.defineId(VmdNpc.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<String> DATA_SKIN_NAME =
            SynchedEntityData.defineId(VmdNpc.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<String> DATA_SKIN_PROPERTY =
            SynchedEntityData.defineId(VmdNpc.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<String> DATA_SKIN_SIGNATURE =
            SynchedEntityData.defineId(VmdNpc.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<String> DATA_SKIN_FILE =
            SynchedEntityData.defineId(VmdNpc.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<Boolean> DATA_SLIM =
            SynchedEntityData.defineId(VmdNpc.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Float> DATA_LOOK_RADIUS =
            SynchedEntityData.defineId(VmdNpc.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> DATA_SCALE =
            SynchedEntityData.defineId(VmdNpc.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Boolean> DATA_SITTING =
            SynchedEntityData.defineId(VmdNpc.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Float> DATA_SIT_OFFSET =
            SynchedEntityData.defineId(VmdNpc.class, EntityDataSerializers.FLOAT);

    /** 生成时的朝向，没有玩家可注视时回到这个角度。 */
    private float homeYaw;

    public VmdNpc(EntityType<? extends VmdNpc> entityType, Level level) {
        super(entityType, level);
        this.setNoAi(true);
        this.setInvulnerable(true);
        this.setPersistenceRequired();
        this.setCustomNameVisible(true);
        this.setCanPickUpLoot(false);
        this.homeYaw = this.getYRot();
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            this.setDropChance(slot, 0.0F);
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(Attributes.FOLLOW_RANGE, 16.0D)
                .add(Attributes.ATTACK_DAMAGE, 0.0D);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_DIALOG_ID, "");
        this.entityData.define(DATA_SKIN_NAME, "");
        this.entityData.define(DATA_SKIN_PROPERTY, "");
        this.entityData.define(DATA_SKIN_SIGNATURE, "");
        this.entityData.define(DATA_SKIN_FILE, "");
        this.entityData.define(DATA_SLIM, false);
        this.entityData.define(DATA_LOOK_RADIUS, 8.0F);
        this.entityData.define(DATA_SCALE, 1.0F);
        this.entityData.define(DATA_SITTING, false);
        this.entityData.define(DATA_SIT_OFFSET, 0.0F);
    }

    // ------------------------------------------------------------------
    // 交互与生命状态：NPC 不参与战斗，不掉落，不自然消失
    // ------------------------------------------------------------------

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        // 不做任何原生交互（不换装、不交易、不喂食），对话由 VillagerEvents 接管
        return InteractionResult.SUCCESS;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        return false;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return true;
    }

    /**
     * 让管理命令能删除 NPC。
     * <p>
     * 原版 {@code /kill} 的实现是 {@code entity.kill()}（内部走 {@link #hurt}），
     * 而 {@link #hurt} 恒返回 false，所以不覆写这里的话 NPC 会「杀不死」。
     * 覆写后 {@code /kill @e[type=vmd:npc]} 可以直接清理误生成的 NPC。
     */
    @Override
    public void kill() {
        this.discard();
    }

    @Override
    public void checkDespawn() {
        // 永不自动消失
    }

    @Override
    public boolean removeWhenFarAway(double distance) {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        // 玩家可以直接穿过（明确设计决策）
        return false;
    }

    @Override
    public boolean isPickable() {
        // 主线阶段未到的 NPC：客户端准星不再选中（服务端交互另有门控）
        if (this.level().isClientSide
                && !fun.wraq.process.system.missions.mainline.MainQuestVisibility
                .isVisibleToLocalPlayer(getDialogId())) {
            return false;
        }
        return super.isPickable();
    }

    @Override
    public void push(double x, double y, double z) {
        // 不被实体/活塞/载具推挤
    }

    @Override
    public void knockback(double strength, double ratioX, double ratioZ) {
        // 免疫击退
    }

    @Override
    public void travel(Vec3 travelVector) {
        // 禁止移动：重力、流体、冲击都在 travel 内生效，直接吞掉即可
    }

    // ------------------------------------------------------------------
    // 视线：范围内缓慢跟随玩家；范围内/外都不再"瞬间弹回"；无目标时每 5s 随机转 45°
    // ------------------------------------------------------------------

    /** 每次更新（2 tick）的最大转角，约 7.5°/tick，转 90° 约 1 秒。 */
    private static final float TURN_STEP_DEG = 15.0F;
    /** 无目标时两次随机转头的间隔（tick）= 5 秒。 */
    private static final int IDLE_LOOK_INTERVAL_TICKS = 100;
    /** 随机转头每次的幅度。 */
    private static final float IDLE_LOOK_STEP_DEG = 45.0F;
    /** 俯仰上下限，避免头翻到背后。 */
    private static final float PITCH_LIMIT_DEG = 60.0F;
    /** 随机转头时相对身体朝向的偏航上限（避免脑袋转到背后）。 */
    private static final float IDLE_YAW_LIMIT_DEG = 75.0F;

    /** 距离下一次随机转头还剩多少 tick。 */
    private int idleLookTimer = IDLE_LOOK_INTERVAL_TICKS;
    /** 随机转头的目标角度。 */
    private float idleTargetYaw;
    private float idleTargetPitch;
    /** 是否正在做随机转头（插值中）。 */
    private boolean idleLooking;

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide || this.tickCount % 2 != 0) {
            return;
        }
        this.updateLook();
    }

    private void updateLook() {
        Player target = this.level().getNearestPlayer(this, this.getLookRadius());
        if (target != null && !target.isSpectator()) {
            // 有人：缓慢转向玩家（限速，避免玩家刚进入范围时"瞬间扭头"）
            this.idleLooking = false;
            this.idleLookTimer = IDLE_LOOK_INTERVAL_TICKS;
            this.turnTowards(this.desiredYawTo(target), this.desiredPitchTo(target), TURN_STEP_DEG);
            return;
        }

        // 没人：保持当前朝向（不再回正/弹回），并每 5 秒随机转 45°
        if (this.idleLooking) {
            boolean done = this.turnTowards(this.idleTargetYaw, this.idleTargetPitch, TURN_STEP_DEG);
            if (done) {
                this.idleLooking = false;
            }
            return;
        }
        this.idleLookTimer -= 2;
        if (this.idleLookTimer <= 0) {
            this.idleLookTimer = IDLE_LOOK_INTERVAL_TICKS;
            this.startIdleLook();
        }
    }

    /** 随机挑一个方向（左/右/上/下）转 45°。 */
    private void startIdleLook() {
        boolean horizontal = this.random.nextBoolean();
        boolean positive = this.random.nextBoolean();
        float delta = positive ? IDLE_LOOK_STEP_DEG : -IDLE_LOOK_STEP_DEG;
        if (horizontal) {
            // 相对身体朝向限制在 ±75°，避免随机游走后脑袋转到背后
            float bodyYaw = this.getYRot();
            float relative = Mth.wrapDegrees(this.getYHeadRot() - bodyYaw);
            float target = Mth.clamp(relative + delta, -IDLE_YAW_LIMIT_DEG, IDLE_YAW_LIMIT_DEG);
            this.idleTargetYaw = bodyYaw + target;
            this.idleTargetPitch = this.getXRot();
        } else {
            this.idleTargetYaw = this.getYHeadRot();
            this.idleTargetPitch = Mth.clamp(this.getXRot() + delta, -PITCH_LIMIT_DEG, PITCH_LIMIT_DEG);
        }
        this.idleLooking = true;
    }

    private float desiredYawTo(Player target) {
        Vec3 eye = target.getEyePosition();
        Vec3 from = this.getEyePosition();
        return (float) Math.toDegrees(Math.atan2(eye.z - from.z, eye.x - from.x)) - 90.0F;
    }

    private float desiredPitchTo(Player target) {
        Vec3 eye = target.getEyePosition();
        Vec3 from = this.getEyePosition();
        double dx = eye.x - from.x;
        double dy = eye.y - from.y;
        double dz = eye.z - from.z;
        double horizontal = Math.sqrt(dx * dx + dz * dz);
        if (horizontal < 1.0E-4D) {
            return this.getXRot();
        }
        return (float) (-Math.toDegrees(Math.atan2(dy, horizontal)));
    }

    /**
     * 限速转向目标角度。
     *
     * @return 是否已经到达目标（用于结束随机转头插值）
     */
    private boolean turnTowards(float targetYaw, float targetPitch, float maxStep) {
        float yawDiff = Mth.wrapDegrees(targetYaw - this.getYHeadRot());
        float pitchDiff = targetPitch - this.getXRot();
        boolean yawDone = Math.abs(yawDiff) <= maxStep;
        boolean pitchDone = Math.abs(pitchDiff) <= maxStep;
        this.setYHeadRot(yawDone ? targetYaw : this.getYHeadRot() + Math.signum(yawDiff) * maxStep);
        this.setXRot(pitchDone ? targetPitch : this.getXRot() + Math.signum(pitchDiff) * maxStep);
        return yawDone && pitchDone;
    }

    // ------------------------------------------------------------------
    // 存档：Mob 自身已负责 HandItems / ArmorItems，这里只补 NPC 自有字段
    // ------------------------------------------------------------------

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putString("DialogId", this.getDialogId());
        tag.putString("SkinName", this.getSkinName());
        tag.putString("SkinProperty", this.getSkinProperty());
        tag.putString("SkinSignature", this.getSkinSignature());
        tag.putString("SkinFile", this.getSkinFile());
        tag.putBoolean("Slim", this.isSlim());
        tag.putFloat("LookRadius", this.getLookRadius());
        tag.putFloat("Scale", this.getScale());
        tag.putBoolean("Sitting", this.isSitting());
        tag.putFloat("SitOffset", this.getSitOffset());
        tag.putFloat("HomeYaw", this.homeYaw);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setDialogId(tag.getString("DialogId"));
        this.setSkinName(tag.getString("SkinName"));
        this.setSkinProperty(tag.getString("SkinProperty"), tag.getString("SkinSignature"));
        this.setSkinFile(tag.getString("SkinFile"));
        this.setSlim(tag.getBoolean("Slim"));
        this.setLookRadius(tag.contains("LookRadius") ? tag.getFloat("LookRadius") : 8.0F);
        this.setScale(tag.contains("Scale") ? tag.getFloat("Scale") : 1.0F);
        this.setSitting(tag.getBoolean("Sitting"));
        this.setSitOffset(tag.contains("SitOffset") ? tag.getFloat("SitOffset") : 0.0F);
        this.homeYaw = tag.contains("HomeYaw") ? tag.getFloat("HomeYaw") : this.getYRot();
    }

    // ------------------------------------------------------------------
    // 字段访问器
    // ------------------------------------------------------------------

    @Override
    public String getDialogId() {
        return this.entityData.get(DATA_DIALOG_ID);
    }

    public void setDialogId(String dialogId) {
        this.entityData.set(DATA_DIALOG_ID, dialogId == null ? "" : dialogId);
    }

    @Override
    public Component getNpcName() {
        Component customName = this.getCustomName();
        return customName != null ? customName : this.getName();
    }

    public String getSkinName() {
        return this.entityData.get(DATA_SKIN_NAME);
    }

    public void setSkinName(String skinName) {
        this.entityData.set(DATA_SKIN_NAME, skinName == null ? "" : skinName);
    }

    public String getSkinProperty() {
        return this.entityData.get(DATA_SKIN_PROPERTY);
    }

    public String getSkinSignature() {
        return this.entityData.get(DATA_SKIN_SIGNATURE);
    }

    public void setSkinProperty(String value, String signature) {
        this.entityData.set(DATA_SKIN_PROPERTY, value == null ? "" : value);
        this.entityData.set(DATA_SKIN_SIGNATURE, signature == null ? "" : signature);
    }

    public String getSkinFile() {
        return this.entityData.get(DATA_SKIN_FILE);
    }

    public void setSkinFile(String skinFile) {
        this.entityData.set(DATA_SKIN_FILE, skinFile == null ? "" : skinFile);
    }

    public boolean isSlim() {
        return this.entityData.get(DATA_SLIM);
    }

    public void setSlim(boolean slim) {
        this.entityData.set(DATA_SLIM, slim);
    }

    public float getLookRadius() {
        return this.entityData.get(DATA_LOOK_RADIUS);
    }

    public void setLookRadius(float lookRadius) {
        this.entityData.set(DATA_LOOK_RADIUS, Math.max(0.0F, lookRadius));
    }

    public float getScale() {
        return this.entityData.get(DATA_SCALE);
    }

    public void setScale(float scale) {
        this.entityData.set(DATA_SCALE, scale <= 0.0F ? 1.0F : scale);
    }

    /** 是否使用坐姿（骑乘姿态）：只影响渲染，不真的骑乘实体。 */
    public boolean isSitting() {
        return this.entityData.get(DATA_SITTING);
    }

    public void setSitting(boolean sitting) {
        this.entityData.set(DATA_SITTING, sitting);
    }

    /**
     * 坐姿时模型整体下移的格数（用于贴合椅子/地面）。
     * 正值向下。默认 0（纯姿态），按需用 {@code /vmd npc set sitoffset} 微调。
     */
    public float getSitOffset() {
        return this.entityData.get(DATA_SIT_OFFSET);
    }

    public void setSitOffset(float sitOffset) {
        this.entityData.set(DATA_SIT_OFFSET, sitOffset);
    }

    public float getHomeYaw() {
        return homeYaw;
    }

    public void setHomeYaw(float homeYaw) {
        this.homeYaw = homeYaw;
    }

    /** 装备栏快捷设置（命令用）。 */
    public void setEquipment(EquipmentSlot slot, ItemStack stack) {
        this.setItemSlot(slot, stack);
    }
}
