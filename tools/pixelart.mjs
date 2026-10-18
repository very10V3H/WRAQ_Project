/**
 * AI-Generated, 2026-10-18
 *
 * pixelart.mjs — 像素画绘制原语（配合 pixelgen.mjs 使用）
 *
 * 画布是「字符二维数组」：每个字符是 palette 的 key，'.' 表示透明。
 * 所有图元最后交给 pixelgen 的 renderPng 输出 RGBA PNG（背景透明）。
 */
import { mirrorRows } from './pixelgen.mjs';

export function makeCanvas(w, h, fill = '.') {
  return { w, h, px: Array.from({ length: h }, () => Array(w).fill(fill)) };
}

export function clone(c) {
  return { w: c.w, h: c.h, px: c.px.map((r) => [...r]) };
}

export function rows(c) {
  return c.px.map((r) => r.join(''));
}

export function getPx(c, x, y) {
  if (x < 0 || y < 0 || x >= c.w || y >= c.h) return '.';
  return c.px[y][x];
}

export function setPx(c, x, y, ch) {
  x = Math.round(x);
  y = Math.round(y);
  if (x < 0 || y < 0 || x >= c.w || y >= c.h) return;
  if (ch == null) return;
  c.px[y][x] = ch;
}

/** 矩形（含端点） */
export function rect(c, x0, y0, x1, y1, ch) {
  for (let y = Math.min(y0, y1); y <= Math.max(y0, y1); y++) {
    for (let x = Math.min(x0, x1); x <= Math.max(x0, x1); x++) setPx(c, x, y, ch);
  }
}

/** 实心圆 */
export function disc(c, cx, cy, r, ch) {
  for (let y = Math.floor(cy - r); y <= Math.ceil(cy + r); y++) {
    for (let x = Math.floor(cx - r); x <= Math.ceil(cx + r); x++) {
      const dx = x - cx;
      const dy = y - cy;
      if (dx * dx + dy * dy <= r * r) setPx(c, x, y, ch);
    }
  }
}

/** Bresenham 细线 */
export function line(c, x0, y0, x1, y1, ch) {
  x0 = Math.round(x0); y0 = Math.round(y0); x1 = Math.round(x1); y1 = Math.round(y1);
  const dx = Math.abs(x1 - x0), dy = Math.abs(y1 - y0);
  const sx = x0 < x1 ? 1 : -1, sy = y0 < y1 ? 1 : -1;
  let err = dx - dy;
  for (;;) {
    setPx(c, x0, y0, ch);
    if (x0 === x1 && y0 === y1) break;
    const e2 = 2 * err;
    if (e2 > -dy) { err -= dy; x0 += sx; }
    if (e2 < dx) { err += dx; y0 += sy; }
  }
}

/**
 * 带宽度 / 颜色函数的线：沿线的法线方向按 offsets 采样。
 * charOf(offset, u) -> 字符；u 是 0..1 的沿线进度。
 */
export function thickLine(c, x0, y0, x1, y1, offsets, charOf) {
  const dx = x1 - x0, dy = y1 - y0;
  const len = Math.hypot(dx, dy) || 1;
  const px = -dy / len, py = dx / len; // 法线
  const steps = Math.max(2, Math.ceil(len * 3));
  for (let i = 0; i <= steps; i++) {
    const u = i / steps;
    const bx = x0 + dx * u, by = y0 + dy * u;
    for (const off of offsets) {
      const ch = charOf(off, u);
      if (ch == null) continue;
      setPx(c, bx + px * off, by + py * off, ch);
    }
  }
}

/** 多边形扫描线填充（偶奇规则） */
export function polygon(c, pts, ch) {
  const ys = pts.map((p) => p[1]);
  const yMin = Math.floor(Math.min(...ys));
  const yMax = Math.ceil(Math.max(...ys));
  for (let y = yMin; y <= yMax; y++) {
    const xs = [];
    for (let i = 0; i < pts.length; i++) {
      const [x0, y0] = pts[i];
      const [x1, y1] = pts[(i + 1) % pts.length];
      if ((y0 <= y && y1 > y) || (y1 <= y && y0 > y)) {
        xs.push(x0 + ((y - y0) / (y1 - y0)) * (x1 - x0));
      }
    }
    xs.sort((a, b) => a - b);
    for (let i = 0; i + 1 < xs.length; i += 2) {
      for (let x = Math.ceil(xs[i]); x <= Math.floor(xs[i + 1]); x++) setPx(c, x, y, ch);
    }
  }
}

/** 按规则重上色：fn(ch, x, y) 返回新字符或 undefined（保持） */
export function mapPx(c, fn) {
  for (let y = 0; y < c.h; y++) {
    for (let x = 0; x < c.w; x++) {
      const next = fn(c.px[y][x], x, y);
      if (next !== undefined && next !== null) c.px[y][x] = next;
    }
  }
}

/**
 * 自动描边。
 * mode='in'  ：把轮廓内的边缘像素替换成描边色（保持剪影不变，推荐）
 * mode='out' ：在剪影外侧扩张一圈描边色（剪影更大）
 */
export function outline(c, ch = 'k', { mode = 'in', diag = true } = {}) {
  const solid = (x, y) => getPx(c, x, y) !== '.';
  const isEdge = (x, y) => {
    if (!solid(x, y)) return false;
    const nb = diag
      ? [[1, 0], [-1, 0], [0, 1], [0, -1], [1, 1], [1, -1], [-1, 1], [-1, -1]]
      : [[1, 0], [-1, 0], [0, 1], [0, -1]];
    return nb.some(([dx, dy]) => !solid(x + dx, y + dy));
  };
  if (mode === 'in') {
    const marks = [];
    for (let y = 0; y < c.h; y++) for (let x = 0; x < c.w; x++) if (isEdge(x, y)) marks.push([x, y]);
    for (const [x, y] of marks) c.px[y][x] = ch;
  } else {
    const marks = [];
    for (let y = 0; y < c.h; y++) {
      for (let x = 0; x < c.w; x++) {
        if (solid(x, y)) continue;
        const nb = diag
          ? [[1, 0], [-1, 0], [0, 1], [0, -1], [1, 1], [1, -1], [-1, 1], [-1, -1]]
          : [[1, 0], [-1, 0], [0, 1], [0, -1]];
        if (nb.some(([dx, dy]) => solid(x + dx, y + dy))) marks.push([x, y]);
      }
    }
    for (const [x, y] of marks) c.px[y][x] = ch;
  }
}

/** 左右镜像：用左半边覆盖右半边（宽必须为偶数） */
export function mirrorHalf(c) {
  const half = c.w / 2;
  for (let y = 0; y < c.h; y++) {
    for (let x = 0; x < half; x++) c.px[y][c.w - 1 - x] = c.px[y][x];
  }
  return c;
}

/** 半宽字符行 -> 全宽字符行（index 0 紧贴中心线） */
export function mirrorHalfRows(halfRows) {
  return mirrorRows(halfRows);
}

export { mirrorRows };
