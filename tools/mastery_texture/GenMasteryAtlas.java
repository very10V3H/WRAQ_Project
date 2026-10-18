import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * 生成精通界面圆形图集：
 * 8列 x 4行，每格 128px。
 * 行0: fill / glow白 / ring白 / ring锁定 / ring解锁
 * 行1: 5条路径的 glow
 * 行2: 5条路径的 ring
 * 行3: 5条路径的 diamond（基石）
 */
public class GenMasteryAtlas {

    static final int CELL = 128;
    static final int COLS = 8;
    static final int ROWS = 4;

    // 与 MasteryTree 的颜色保持一致
    static final Color[] TREES = {
            new Color(0xD9A94E), // 精密
            new Color(0xE25A55), // 主宰
            new Color(0xA97BFF), // 巫术
            new Color(0x5FC95C), // 坚决
            new Color(0x5BC9E8)  // 启迪
    };

    public static void main(String[] args) throws Exception {
        BufferedImage image = new BufferedImage(CELL * COLS, CELL * ROWS, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        drawFill(graphics);
        drawGlow(graphics, 1, 0, Color.WHITE, 0.18f);
        drawRing(graphics, 2, 0, new Color(255, 255, 255, 220), 2f);
        drawRing(graphics, 3, 0, new Color(0x3A4254), 3f);
        drawRing(graphics, 4, 0, new Color(0x8A94A8), 3f);

        for (int tree = 0; tree < TREES.length; tree++) {
            Color color = TREES[tree];
            drawGlow(graphics, tree, 1, color, 0.22f);
            drawRing(graphics, tree, 2, color, 3f);
            drawDiamond(graphics, tree, 3, color, 2f);
        }

        graphics.dispose();
        File output = new File(args[0]);
        output.getParentFile().mkdirs();
        ImageIO.write(image, "png", output);
        System.out.println("wrote " + output.getAbsolutePath() + " (" + image.getWidth() + "x" + image.getHeight() + ")");
    }

    private static Graphics2D cell(Graphics2D graphics, int col, int row) {
        Graphics2D g = (Graphics2D) graphics.create(col * CELL, row * CELL, CELL, CELL);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        return g;
    }

    private static void drawFill(Graphics2D graphics) {
        Graphics2D g = cell(graphics, 0, 0);
        RadialGradientPaint paint = new RadialGradientPaint(
                new Point2D.Float(64f, 60f), 58f,
                new float[]{0f, 0.62f, 1f},
                new Color[]{new Color(24, 33, 50, 248), new Color(13, 19, 30, 248), new Color(7, 10, 16, 248)});
        g.setPaint(paint);
        g.fill(new Ellipse2D.Float(10f, 10f, 108f, 108f));
        g.dispose();
    }

    private static void drawGlow(Graphics2D graphics, int col, int row, Color color, float maxAlpha) {
        Graphics2D g = cell(graphics, col, row);
        Color center = withAlpha(color, maxAlpha);
        Color mid = withAlpha(color, maxAlpha * 0.58f);
        Color outer = withAlpha(color, maxAlpha * 0.18f);
        RadialGradientPaint paint = new RadialGradientPaint(
                new Point2D.Float(64f, 64f), 64f,
                new float[]{0f, 0.45f, 0.74f, 1f},
                new Color[]{center, mid, outer, withAlpha(color, 0f)});
        g.setPaint(paint);
        g.fill(new Rectangle2D.Float(0f, 0f, CELL, CELL));
        g.dispose();
    }

    private static void drawRing(Graphics2D graphics, int col, int row, Color base, float stroke) {
        Graphics2D g = cell(graphics, col, row);
        Color light = mix(base, Color.WHITE, 0.34f);
        Color dark = mix(base, Color.BLACK, 0.26f);
        g.setPaint(new GradientPaint(16f, 16f, light, 112f, 112f, dark));
        g.setStroke(new BasicStroke(stroke, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.draw(new Ellipse2D.Float(9f, 9f, 110f, 110f)); // 半径55，外56.5/内53.5，覆盖填充边缘
        g.dispose();
    }

    private static void drawDiamond(Graphics2D graphics, int col, int row, Color base, float stroke) {
        Graphics2D g = cell(graphics, col, row);
        Color light = mix(base, Color.WHITE, 0.38f);
        Color dark = mix(base, Color.BLACK, 0.30f);
        Path2D outer = new Path2D.Float();
        outer.moveTo(64f, 8f);
        outer.lineTo(120f, 64f);
        outer.lineTo(64f, 120f);
        outer.lineTo(8f, 64f);
        outer.closePath();
        g.setPaint(new GradientPaint(18f, 18f, light, 110f, 110f, dark));
        g.setStroke(new BasicStroke(stroke, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.draw(outer);
        g.dispose();
    }

    private static Color withAlpha(Color color, float alpha) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(),
                Math.max(0, Math.min(255, Math.round(alpha * 255))));
    }

    private static Color mix(Color a, Color b, float ratio) {
        return new Color(
                Math.round(a.getRed() * (1 - ratio) + b.getRed() * ratio),
                Math.round(a.getGreen() * (1 - ratio) + b.getGreen() * ratio),
                Math.round(a.getBlue() * (1 - ratio) + b.getBlue() * ratio),
                a.getAlpha());
    }
}



