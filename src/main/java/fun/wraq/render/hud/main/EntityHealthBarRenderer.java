/**
 * AI-Generated, 2026-10-17
 */
package fun.wraq.render.hud.main;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import fun.wraq.common.util.NumberUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.entities.entities.npc.VmdNpc;
import fun.wraq.render.hud.ClientShieldData;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;

/**
 * 生物头顶血条渲染器（替代 AsteorBar 的实体血条）。
 *
 * <p>只给以下目标绘制血条：</p>
 * <ul>
 *     <li>玩家（含自己，第三人称可见）；</li>
 *     <li>VMD 生成的怪物：自定义名以 {@code Lv.} 开头（{@code MobSpawn.setMobCustomName}
 *         生成），或实体类型注册在 {@code vmd} 命名空间下。</li>
 * </ul>
 * <p>原版村民、野生动物等不再显示血条。</p>
 *
 * <p>视觉：</p>
 * <ul>
 *     <li>圆角血条（三角面片近似圆角）；</li>
 *     <li>底槽上下边缘各有 10% / 25% / 50% / 75% 的细黑刻度线（每段约占高度的 15%）；</li>
 *     <li>血条按血量比例填充，颜色随比例由亮红渐变到暗红；</li>
 *     <li>白色段为护盾（VMD 护盾 + 原版吸收伤害），紧接生命值右侧，比例为 护盾/最大生命值；
 *         当 生命值+护盾 超过最大生命值时，以 生命值+护盾 为分母整体压缩，保证不超出血条长度；</li>
 *     <li>数字使用 {@link NumberUtils#abbreviate(double)} 缩写，例如
 *         {@code 30000001024/30000001024} 会显示为 {@code 300e/300e}。</li>
 * </ul>
 *
 * <p>为了保证显示稳定：</p>
 * <ul>
 *     <li>血条与数字都关闭深度测试（{@code NO_DEPTH_TEST} / {@code SEE_THROUGH}），
 *         不会与世界几何、其他共面面片发生深度冲突，避免“随视角移动黑色闪动”；</li>
 *     <li>所有颜色都是不透明色，血条不会随背后的天空/墙面亮度变化；</li>
 *     <li>用 {@link net.minecraft.world.entity.LivingEntity#hasLineOfSight(Entity)} 做视线剔除，
 *         关闭深度测试后血条也不会隔墙显示；</li>
 *     <li>血条整体上移量收在 {@code CustomName} 下沿以下，仰视时不会盖住名字。</li>
 * </ul>
 *
 * <p>若整合包中仍装有 AsteorBar（modid: {@code asteorbar}），则本渲染器自动让位，
 * 避免出现两条血条。</p>
 */
@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EntityHealthBarRenderer {

    /** 血条最大显示距离（方块） */
    private static final double MAX_DISTANCE = 32.0;
    /** 血条坐标系中 1 “像素” 对应的世界坐标长度 */
    private static final float SCALE = 0.018F;
    /** 血条半宽（像素） */
    private static final int HALF_WIDTH = 38;
    /** 血条半高（像素） */
    private static final int HALF_HEIGHT = 4;
    /**
     * 血条相对实体碰撞箱顶部的抬高量。
     * 原版 CustomName 绘制在 {@code bbHeight + 0.5}，文字向下占约 0.225，
     * 所以血条顶边必须低于 {@code bbHeight + 0.275}，否则仰视时会遮住名字。
     */
    private static final double OFFSET_Y = 0.15;
    /** 数字相对血条的缩放 */
    private static final float TEXT_SCALE = 0.8F;
    /** 数字纵向偏移（像素，负值向上） */
    private static final float TEXT_OFFSET_Y = -3.6F;
    /** 圆角半径（像素） */
    private static final float CORNER_RADIUS = 3.0F;

    private static final int BORDER_COLOR = 0xFF000000;
    private static final int EMPTY_COLOR = 0xFF202020;
    private static final int FILL_COLOR_FULL = 0xFFE84848;
    private static final int FILL_COLOR_LOW = 0xFF7A1414;
    /** 护盾段颜色（白色，紧接生命值右侧） */
    private static final int SHIELD_COLOR = 0xFFFFFFFF;
    private static final int TEXT_COLOR = 0xFFFFFFFF;
    /** 刻度线颜色（细黑线，压在填充和底槽上） */
    private static final int TICK_COLOR = 0xFF101010;
    /** 刻度线宽度（像素） */
    private static final float TICK_WIDTH = 0.8F;
    /** 每段刻度线占血条高度的比例（上下各一段） */
    private static final float TICK_HEIGHT_RATE = 0.15F;
    /** 刻度位置 */
    private static final float[] TICKS = {0.1F, 0.25F, 0.5F, 0.75F};

    @SubscribeEvent
    public static void onRenderLevel(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_PARTICLES) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || mc.player == null) return;
        // 整合包仍装有 AsteorBar 时交给它渲染，避免双份血条
        if (ModList.get().isLoaded("asteorbar")) return;

        Camera camera = event.getCamera();
        Vec3 camPos = camera.getPosition();
        float partialTick = event.getPartialTick();
        PoseStack poseStack = event.getPoseStack();
        MultiBufferSource.BufferSource buffer = mc.renderBuffers().bufferSource();
        Font font = mc.font;

        List<LivingEntity> targets = new ArrayList<>();
        for (Entity entity : mc.level.entitiesForRendering()) {
            if (!(entity instanceof LivingEntity living)) continue;
            // 第一人称下能看到自己头顶的血条，这里直接跳过本地玩家
            if (living == mc.player) continue;
            // 盔甲架是 VMD 常用的特效载体，NPC 没有战斗意义，都不显示血条
            if (living instanceof ArmorStand || living instanceof VmdNpc) continue;
            if (!living.isAlive() || living.isInvisible() || living.isSpectator()) continue;
            // 只显示玩家与 VMD 生成的怪物
            if (!shouldShowFor(living)) continue;
            if (living.getPosition(partialTick).distanceTo(camPos) > MAX_DISTANCE) continue;
            if (!event.getFrustum().isVisible(living.getBoundingBox())) continue;
            // 关闭深度测试后需要手动做视线剔除，避免血条隔墙显示
            if (!mc.player.hasLineOfSight(living)) continue;
            targets.add(living);
        }
        if (targets.isEmpty()) return;

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableCull();
        RenderSystem.disableDepthTest();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);

        Tesselator tesselator = Tesselator.getInstance();
        for (LivingEntity living : targets) {
            poseStack.pushPose();
            applyBillboard(poseStack, camera, camPos, living, partialTick);

            BufferBuilder builder = tesselator.getBuilder();
            builder.begin(VertexFormat.Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);
            appendBarQuads(builder, poseStack.last().pose(), living);
            tesselator.end();

            poseStack.translate(0.0, TEXT_OFFSET_Y, 0.0);
            poseStack.scale(TEXT_SCALE, TEXT_SCALE, TEXT_SCALE);
            String text = NumberUtils.abbreviate(living.getHealth())
                    + "/" + NumberUtils.abbreviate(living.getMaxHealth());
            font.drawInBatch(text, -font.width(text) / 2.0F, 0, TEXT_COLOR, false,
                    poseStack.last().pose(), buffer, Font.DisplayMode.SEE_THROUGH, 0, LightTexture.FULL_BRIGHT);

            poseStack.popPose();
        }
        buffer.endBatch();

        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();
        RenderSystem.enableCull();
    }

    /**
     * 玩家，或 VMD 生成的怪物（自定义名以 {@code Lv.} 开头 / 实体类型属于 vmd 命名空间）。
     */
    @SuppressWarnings("deprecation")
    private static boolean shouldShowFor(LivingEntity living) {
        if (living instanceof Player) return true;
        Component customName = living.getCustomName();
        if (customName != null && customName.getString().startsWith("Lv.")) return true;
        ResourceLocation id = BuiltInRegistries.ENTITY_TYPE.getResourceKey(living.getType())
                .map(net.minecraft.resources.ResourceKey::location).orElse(null);
        return id != null && id.getNamespace().equals(Utils.MOD_ID);
    }

    /**
     * 把当前 PoseStack 变换到实体头顶、朝向相机的公告牌坐标系。
     */
    private static void applyBillboard(PoseStack poseStack, Camera camera, Vec3 camPos,
                                       LivingEntity living, float partialTick) {
        Vec3 pos = living.getPosition(partialTick);
        poseStack.translate(pos.x - camPos.x,
                pos.y - camPos.y + living.getBbHeight() + OFFSET_Y,
                pos.z - camPos.z);
        poseStack.mulPose(camera.rotation());
        poseStack.scale(-SCALE, -SCALE, SCALE);
    }

    private static void appendBarQuads(BufferBuilder builder, Matrix4f mat, LivingEntity living) {
        float maxHealth = Math.max(0.0F, living.getMaxHealth());
        float health = Mth.clamp(living.getHealth(), 0.0F, maxHealth);
        float healthRatio = maxHealth <= 0.0F ? 0.0F : health / maxHealth;
        float fullWidth = HALF_WIDTH * 2.0F;

        // 护盾 = 其他玩家的 VMD 护盾 + 原版吸收伤害。
        // 生命值 + 护盾超过最大生命值时，改用「生命值 + 护盾」作分母，
        // 保证健康段 + 白色护盾段不会超出血条长度（参考英雄联盟的血条压缩）。
        float shield = shieldAmount(living);
        float denominator = Math.max(maxHealth, health + shield);
        float healthWidth = denominator <= 0.0F ? 0.0F : fullWidth * (health / denominator);
        float shieldWidth = denominator <= 0.0F ? 0.0F : fullWidth * (shield / denominator);

        // 外边框（圆角）
        roundedRect(builder, mat, -HALF_WIDTH - 1, -HALF_HEIGHT - 1,
                HALF_WIDTH + 1, HALF_HEIGHT + 1, CORNER_RADIUS + 1,
                BORDER_COLOR, true, true);
        // 空槽（圆角）
        roundedRect(builder, mat, -HALF_WIDTH, -HALF_HEIGHT, HALF_WIDTH, HALF_HEIGHT,
                CORNER_RADIUS, EMPTY_COLOR, true, true);

        // 血量填充（左端圆角；后面接护盾时右端为直边，满血无护盾时右端圆角）
        if (healthWidth > 0.01F) {
            boolean fullBar = shieldWidth <= 0.01F && healthWidth >= fullWidth - 0.01F;
            float radius = Math.min(CORNER_RADIUS, healthWidth * 0.5F);
            roundedRect(builder, mat, -HALF_WIDTH, -HALF_HEIGHT,
                    -HALF_WIDTH + healthWidth, HALF_HEIGHT, radius,
                    lerpColor(FILL_COLOR_LOW, FILL_COLOR_FULL, healthRatio), true, fullBar);
        }

        // 白色护盾段（紧接生命值右侧）
        if (shieldWidth > 0.01F) {
            float left = -HALF_WIDTH + healthWidth;
            boolean reachesEnd = healthWidth + shieldWidth >= fullWidth - 0.01F;
            roundedRect(builder, mat, left, -HALF_HEIGHT, left + shieldWidth, HALF_HEIGHT,
                    reachesEnd ? CORNER_RADIUS : 0.0F, SHIELD_COLOR, false, reachesEnd);
        }

        // 10% / 25% / 50% / 75% 刻度：上下各一小段细黑线，各占血条高度约 15%，中间留空
        float tickHeight = HALF_HEIGHT * 2 * TICK_HEIGHT_RATE;
        for (float tick : TICKS) {
            float x = -HALF_WIDTH + fullWidth * tick;
            rect(builder, mat, x - TICK_WIDTH * 0.5F, -HALF_HEIGHT,
                    x + TICK_WIDTH * 0.5F, -HALF_HEIGHT + tickHeight, TICK_COLOR);
            rect(builder, mat, x - TICK_WIDTH * 0.5F, HALF_HEIGHT - tickHeight,
                    x + TICK_WIDTH * 0.5F, HALF_HEIGHT, TICK_COLOR);
        }
    }

    /**
     * 实体当前的护盾值：原版吸收伤害 + 客户端同步的 VMD 护盾（玩家与怪物通用）。
     */
    private static float shieldAmount(LivingEntity living) {
        float shield = Math.max(0.0F, living.getAbsorptionAmount());
        shield += (float) ClientShieldData.getEntityShield(living.getId());
        return shield;
    }

    /**
     * 圆角矩形；{@code roundLeft/roundRight} 指定左右两端是否圆角。
     * 半径过大时会自动收窄，避免退化。
     */
    private static void roundedRect(BufferBuilder builder, Matrix4f mat,
                                    float x1, float y1, float x2, float y2, float radius, int color,
                                    boolean roundLeft, boolean roundRight) {
        if (x2 - x1 <= 0.001F || y2 - y1 <= 0.001F) return;

        float maxRadius = Math.min((x2 - x1) * 0.5F, (y2 - y1) * 0.5F);
        float r = Math.min(radius, maxRadius);
        if (r < 0.5F) {
            rect(builder, mat, x1, y1, x2, y2, color);
            return;
        }

        float leftEdge = roundLeft ? x1 + r : x1;
        float rightEdge = roundRight ? x2 - r : x2;
        // 中间矩形
        rect(builder, mat, leftEdge, y1, rightEdge, y2, color);

        if (roundLeft) {
            // 左侧上下直边 + 两个四分之一圆
            rect(builder, mat, x1, y1 + r, x1 + r, y2 - r, color);
            corner(builder, mat, x1 + r, y1 + r, r,
                    (float) Math.PI, (float) (Math.PI * 1.5), color);
            corner(builder, mat, x1 + r, y2 - r, r,
                    (float) (Math.PI * 0.5), (float) Math.PI, color);
        }
        if (roundRight) {
            rect(builder, mat, x2 - r, y1 + r, x2, y2 - r, color);
            corner(builder, mat, x2 - r, y1 + r, r,
                    (float) (Math.PI * 1.5), (float) (Math.PI * 2), color);
            corner(builder, mat, x2 - r, y2 - r, r,
                    0.0F, (float) (Math.PI * 0.5), color);
        }
    }

    private static void corner(BufferBuilder builder, Matrix4f mat,
                               float cx, float cy, float r, float fromAngle, float toAngle, int color) {
        int segments = 6;
        for (int i = 0; i < segments; i++) {
            float a0 = fromAngle + (toAngle - fromAngle) * i / segments;
            float a1 = fromAngle + (toAngle - fromAngle) * (i + 1) / segments;
            tri(builder, mat, cx, cy,
                    cx + r * (float) Math.cos(a0), cy + r * (float) Math.sin(a0),
                    cx + r * (float) Math.cos(a1), cy + r * (float) Math.sin(a1), color);
        }
    }

    private static void rect(BufferBuilder builder, Matrix4f mat,
                             float x1, float y1, float x2, float y2, int color) {
        if (x2 - x1 <= 0.001F || y2 - y1 <= 0.001F) return;
        tri(builder, mat, x1, y1, x2, y1, x2, y2, color);
        tri(builder, mat, x1, y1, x2, y2, x1, y2, color);
    }

    private static void tri(BufferBuilder builder, Matrix4f mat,
                            float x1, float y1, float x2, float y2, float x3, float y3, int color) {
        builder.vertex(mat, x1, y1, 0.0F).color(color).endVertex();
        builder.vertex(mat, x2, y2, 0.0F).color(color).endVertex();
        builder.vertex(mat, x3, y3, 0.0F).color(color).endVertex();
    }

    private static int lerpColor(int from, int to, float ratio) {
        float t = Mth.clamp(ratio, 0.0F, 1.0F);
        int a = (int) (((from >>> 24) & 0xFF) * (1 - t) + ((to >>> 24) & 0xFF) * t);
        int r = (int) (((from >>> 16) & 0xFF) * (1 - t) + ((to >>> 16) & 0xFF) * t);
        int g = (int) (((from >>> 8) & 0xFF) * (1 - t) + ((to >>> 8) & 0xFF) * t);
        int b = (int) ((from & 0xFF) * (1 - t) + (to & 0xFF) * t);
        return (a << 24) | (r << 16) | (g << 8) | b;
    }
}
