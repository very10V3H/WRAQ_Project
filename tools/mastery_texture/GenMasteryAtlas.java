import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.CRC32;
import java.util.zip.DeflaterOutputStream;

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
            new Color(0xD8BE82), // 精密
            new Color(0xD99691), // 主宰
            new Color(0xBCA6E3), // 巫术
            new Color(0x9BCB96), // 坚决
            new Color(0x99C9D6)  // 启迪
    };

    public static void main(String[] args) throws Exception {
        BufferedImage image = new BufferedImage(CELL * COLS, CELL * ROWS, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        drawFill(graphics);
        drawGlow(graphics, 1, 0, new Color(0x86B6CD), 0.14f);
        drawRing(graphics, 2, 0, new Color(0x86B6CD), 2f);
        drawRing(graphics, 3, 0, new Color(0x8A99AB), 3f);
        drawRing(graphics, 4, 0, new Color(0x5A6B80), 3f);

        for (int tree = 0; tree < TREES.length; tree++) {
            Color color = TREES[tree];
            drawGlow(graphics, tree, 1, color, 0.16f);
            drawRing(graphics, tree, 2, color, 3f);
            drawDiamond(graphics, tree, 3, color, 2f);
        }

        graphics.dispose();
        File output = new File(args[0]);
        output.getParentFile().mkdirs();
        // 给全透明像素写入“所属格子的颜色但 alpha=0”，再用保留了 alpha=0 RGB 的
        // PNG 写入器保存。这样线性/模糊过滤不会把透明黑混到圆环边缘。
        bleedTransparentPixels(image);
        writePng(image, output);
        System.out.println("wrote " + output.getAbsolutePath() + " (" + image.getWidth() + "x" + image.getHeight() + ")");
    }

    private static void bleedTransparentPixels(BufferedImage image) {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Color base;
                if (row == 0) {
                    base = switch (col) {
                        case 0 -> new Color(0xF6FAFD);
                        case 1, 2 -> new Color(0x86B6CD);
                        case 3 -> new Color(0x8A99AB);
                        case 4 -> new Color(0x5A6B80);
                        default -> Color.WHITE;
                    };
                } else {
                    base = col < TREES.length ? TREES[col] : Color.WHITE;
                }
                for (int y = 0; y < CELL; y++) {
                    int py = row * CELL + y;
                    for (int x = 0; x < CELL; x++) {
                        int px = col * CELL + x;
                        int argb = image.getRGB(px, py);
                        int alpha = argb >>> 24;
                        if (alpha <= 1) {
                            image.setRGB(px, py, (alpha << 24) | (base.getRGB() & 0xFFFFFF));
                        }
                    }
                }
            }
        }
    }

    /** 最小 PNG 写入器：ImageIO 会把 alpha=0 的 RGB 清零，这里必须原样保留。 */
    private static void writePng(BufferedImage image, File file) throws IOException {
        int width = image.getWidth();
        int height = image.getHeight();
        ByteArrayOutputStream raw = new ByteArrayOutputStream();
        for (int y = 0; y < height; y++) {
            raw.write(0); // filter type: none
            for (int x = 0; x < width; x++) {
                int argb = image.getRGB(x, y);
                raw.write((argb >>> 16) & 0xFF);
                raw.write((argb >>> 8) & 0xFF);
                raw.write(argb & 0xFF);
                raw.write((argb >>> 24) & 0xFF);
            }
        }
        ByteArrayOutputStream compressed = new ByteArrayOutputStream();
        try (DeflaterOutputStream deflater = new DeflaterOutputStream(compressed)) {
            deflater.write(raw.toByteArray());
        }

        try (DataOutputStream out = new DataOutputStream(
                new BufferedOutputStream(new FileOutputStream(file)))) {
            out.write(new byte[]{(byte) 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A});
            byte[] ihdr = new byte[13];
            writeInt(ihdr, 0, width);
            writeInt(ihdr, 4, height);
            ihdr[8] = 8;  // bit depth
            ihdr[9] = 6;  // color type: RGBA
            writeChunk(out, "IHDR", ihdr);
            writeChunk(out, "IDAT", compressed.toByteArray());
            writeChunk(out, "IEND", new byte[0]);
        }
    }

    private static void writeChunk(DataOutputStream out, String type, byte[] data) throws IOException {
        byte[] typeBytes = type.getBytes(StandardCharsets.US_ASCII);
        out.writeInt(data.length);
        out.write(typeBytes);
        out.write(data);
        CRC32 crc = new CRC32();
        crc.update(typeBytes);
        crc.update(data);
        out.writeInt((int) crc.getValue());
    }

    private static void writeInt(byte[] target, int offset, int value) {
        target[offset] = (byte) (value >>> 24);
        target[offset + 1] = (byte) (value >>> 16);
        target[offset + 2] = (byte) (value >>> 8);
        target[offset + 3] = (byte) value;
    }

    private static Graphics2D cell(Graphics2D graphics, int col, int row) {
        Graphics2D g = (Graphics2D) graphics.create(col * CELL, row * CELL, CELL, CELL);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        return g;
    }

    private static void drawFill(Graphics2D graphics) {
        Graphics2D g = cell(graphics, 0, 0);
        // 只在填充圆边缘外做一圈极低透明度的浅色渗色，避免线性过滤黑边
        g.setColor(new Color(0xF6, 0xFA, 0xFD, 1));
        g.fill(new Ellipse2D.Float(4f, 4f, 120f, 120f));
        RadialGradientPaint paint = new RadialGradientPaint(
                new Point2D.Float(64f, 60f), 58f,
                new float[]{0f, 0.62f, 1f},
                new Color[]{new Color(250, 253, 255, 252), new Color(231, 241, 250, 252),
                        new Color(206, 222, 238, 252)});
        g.setPaint(paint);
        g.fill(new Ellipse2D.Float(10f, 10f, 108f, 108f));
        g.dispose();
    }

    private static void drawGlow(Graphics2D graphics, int col, int row, Color color, float maxAlpha) {
        Graphics2D g = cell(graphics, col, row);
        // 先铺一层同色 alpha=1 的底，避免径向渐变最外圈的透明黑在浅色背景上形成黑边
        g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 1));
        g.fill(new Ellipse2D.Float(0f, 0f, CELL, CELL));
        Color center = withAlpha(color, maxAlpha);
        Color mid = withAlpha(color, maxAlpha * 0.58f);
        Color outer = withAlpha(color, maxAlpha * 0.18f);
        RadialGradientPaint paint = new RadialGradientPaint(
                new Point2D.Float(64f, 64f), 56f,
                new float[]{0f, 0.45f, 0.74f, 1f},
                new Color[]{center, mid, outer, withAlpha(color, 0f)});
        g.setPaint(paint);
        g.fill(new Rectangle2D.Float(0f, 0f, CELL, CELL));
        g.dispose();
    }

    private static void drawRing(Graphics2D graphics, int col, int row, Color base, float stroke) {
        Graphics2D g = cell(graphics, col, row);
        // 沿圆环边缘渗一圈同色 alpha=1，线性过滤时不会与黑色混合
        g.setColor(new Color(base.getRed(), base.getGreen(), base.getBlue(), 1));
        g.setStroke(new BasicStroke(stroke + 16f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.draw(new Ellipse2D.Float(9f, 9f, 110f, 110f));

        Color light = mix(base, Color.WHITE, 0.45f);
        Color dark = mix(base, Color.BLACK, 0.18f);
        g.setPaint(new GradientPaint(16f, 16f, light, 112f, 112f, dark));
        g.setStroke(new BasicStroke(stroke, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.draw(new Ellipse2D.Float(9f, 9f, 110f, 110f)); // 半径55，外56.5/内53.5，覆盖填充边缘
        g.dispose();
    }

    private static void drawDiamond(Graphics2D graphics, int col, int row, Color base, float stroke) {
        Graphics2D g = cell(graphics, col, row);
        Path2D outer = new Path2D.Float();
        outer.moveTo(64f, 8f);
        outer.lineTo(120f, 64f);
        outer.lineTo(64f, 120f);
        outer.lineTo(8f, 64f);
        outer.closePath();

        g.setColor(new Color(base.getRed(), base.getGreen(), base.getBlue(), 1));
        g.setStroke(new BasicStroke(stroke + 16f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.draw(outer);

        Color light = mix(base, Color.WHITE, 0.48f);
        Color dark = mix(base, Color.BLACK, 0.20f);
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



