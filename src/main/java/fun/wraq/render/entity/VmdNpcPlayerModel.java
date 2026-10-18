/** AI-Generated, 2026-10-17 */
package fun.wraq.render.entity;

import fun.wraq.entities.entities.npc.VmdNpc;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;

/**
 * 支持坐姿的玩家模型。
 * <p>
 * 原版 {@link PlayerModel} 的坐姿是由 {@code HumanoidModel#setupAnim} 里的
 * {@code this.riding = entity.isPassenger()} 分支驱动的（双腿前摆）。NPC 并不真的骑乘实体，
 * 所以直接覆写 {@code isPassenger()} 会有副作用（名字板会被原版 shouldShowName 的
 * {@code !isPassenger()} 条件隐藏、部分旅行/受控逻辑会走进骑乘分支）。
 * <p>
 * 这里采用最小侵入做法：{@code setupAnim} 先跑原版逻辑，再按 {@link #sitting} 覆盖双腿角度，
 * 角度常量与原版骑乘分支完全一致，视觉上就是标准的坐姿。
 */
public class VmdNpcPlayerModel extends PlayerModel<VmdNpc> {

    /** 原版 HumanoidModel 骑乘姿态的双腿角度。 */
    private static final float LEG_X = -1.4137167F;
    private static final float LEG_Y = 0.31415927F;
    private static final float LEG_Z = 0.07853982F;

    /** 由渲染器在每帧渲染前写入。 */
    public boolean sitting;

    public VmdNpcPlayerModel(ModelPart root, boolean slim) {
        super(root, slim);
    }

    @Override
    public void setupAnim(VmdNpc entity, float limbSwing, float limbSwingAmount, float ageInTicks,
                          float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        if (sitting) {
            this.rightLeg.xRot = LEG_X;
            this.rightLeg.yRot = LEG_Y;
            this.rightLeg.zRot = LEG_Z;
            this.leftLeg.xRot = LEG_X;
            this.leftLeg.yRot = -LEG_Y;
            this.leftLeg.zRot = -LEG_Z;
        }
    }
}
