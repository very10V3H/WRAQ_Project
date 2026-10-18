#!/usr/bin/env node
/**
 * AI-Generated, 2026-10-18
 *
 * pixelgen.mjs — 零依赖像素画 PNG 生成器（专为 Minecraft 物品材质设计）
 *
 * 特性：
 *  - 纯 Node.js 内置模块（zlib），无需 npm install / 无需联网
 *  - 输出 8bit RGBA PNG，背景完全透明（alpha = 0），可直接放进
 *    src/main/resources/assets/vmd/textures/item/
 *  - 支持任意尺寸（16x16 / 32x32 / 任何方形），逐像素校验行长
 *  - 支持左右镜像（写一半，自动补全，适合宝石/瓶子等对称物品）
 *  - 自动生成最近邻放大的预览图（@Nx.png），方便肉眼检查
 *
 * 用法：
 *   node tools/pixelgen.mjs tools/textures/demo.mjs
 *   node tools/pixelgen.mjs <spec.mjs> --out <dir> --preview <n>
 *
 * Spec 文件（ESM，默认导出）格式：
 *   export default {
 *     outDir: 'build/texgen',
 *     preview: 12,                 // 预览放大倍数，0 = 不生成
 *     palette: { '.': null, 'k': '#123047', 'l': '#7fd0ee' },
 *     items: [
 *       { name: 'my_sword', rows: ['................', ...] },
 *       { name: 'my_gem', mirror: 'right', rows: [/* 半宽行 *\/] },
 *       { name: 'computed', rows: buildSomehow() },
 *     ],
 *   }
 *
 * 行字符串规则：每个字符 = palette 的一个 key；null / 'transparent' / 未定义的
 * key 一律输出 alpha=0（透明）。palette 可用全局，也可在单个 item 上覆盖。
 */
import { deflateSync } from 'node:zlib';
import { mkdirSync, writeFileSync } from 'node:fs';
import { dirname, join, resolve } from 'node:path';
import { fileURLToPath, pathToFileURL } from 'node:url';

// ---------------------------------------------------------------- CRC32 / PNG

const CRC_TABLE = (() => {
  const table = new Int32Array(256);
  for (let n = 0; n < 256; n++) {
    let c = n;
    for (let k = 0; k < 8; k++) c = c & 1 ? 0xedb88320 ^ (c >>> 1) : c >>> 1;
    table[n] = c;
  }
  return table;
})();

function crc32(buf) {
  let c = -1;
  for (let i = 0; i < buf.length; i++) c = CRC_TABLE[(c ^ buf[i]) & 0xff] ^ (c >>> 8);
  return (c ^ -1) >>> 0;
}

const PNG_SIGNATURE = Buffer.from([0x89, 0x50, 0x4e, 0x47, 0x0d, 0x0a, 0x1a, 0x0a]);

function pngChunk(type, data) {
  const typeBuf = Buffer.from(type, 'ascii');
  const body = Buffer.concat([typeBuf, data]);
  const out = Buffer.alloc(body.length + 8);
  out.writeUInt32BE(data.length, 0);
  body.copy(out, 4);
  out.writeUInt32BE(crc32(body), body.length + 4);
  return out;
}

/** @param {number} width @param {number} height @param {Buffer} rgba 长度 = width*height*4 */
export function encodePng(width, height, rgba) {
  const stride = width * 4;
  const raw = Buffer.alloc((stride + 1) * height);
  for (let y = 0; y < height; y++) {
    raw[y * (stride + 1)] = 0; // filter: None
    rgba.copy(raw, y * (stride + 1) + 1, y * stride, (y + 1) * stride);
  }
  const ihdr = Buffer.alloc(13);
  ihdr.writeUInt32BE(width, 0);
  ihdr.writeUInt32BE(height, 4);
  ihdr[8] = 8; // bit depth
  ihdr[9] = 6; // color type: truecolor + alpha
  ihdr[10] = 0; // deflate
  ihdr[11] = 0; // adaptive filtering
  ihdr[12] = 0; // no interlace
  return Buffer.concat([
    PNG_SIGNATURE,
    pngChunk('IHDR', ihdr),
    pngChunk('IDAT', deflateSync(raw, { level: 9 })),
    pngChunk('IEND', Buffer.alloc(0)),
  ]);
}

// ---------------------------------------------------------------- 颜色工具

/** '#rgb' | '#rrggbb' | '#rrggbbaa' | null | 'transparent' -> [r,g,b,a] */
export function parseColor(value) {
  if (value == null) return [0, 0, 0, 0];
  if (typeof value === 'object' && Array.isArray(value)) {
    const [r, g, b, a = 255] = value;
    return [r, g, b, a];
  }
  const s = String(value).trim().toLowerCase();
  if (s === 'transparent' || s === 'none') return [0, 0, 0, 0];
  const hexBody = s.startsWith('#') ? s.slice(1) : s;
  if (hexBody.length === 3) {
    const [r, g, b] = hexBody.split('').map((c) => parseInt(c + c, 16));
    return [r, g, b, 255];
  }
  if (hexBody.length === 6 || hexBody.length === 8) {
    const r = parseInt(hexBody.slice(0, 2), 16);
    const g = parseInt(hexBody.slice(2, 4), 16);
    const b = parseInt(hexBody.slice(4, 6), 16);
    const a = hexBody.length === 8 ? parseInt(hexBody.slice(6, 8), 16) : 255;
    return [r, g, b, a];
  }
  throw new Error(`无法解析颜色: ${JSON.stringify(value)}`);
}

/** 把色阶数组转为 palette：'1'..'9' 直连色阶，简化手写 */
export function ramp(...colors) {
  const palette = { '.': null };
  colors.forEach((c, i) => {
    palette[String(i + 1)] = c;
  });
  return palette;
}

/** 左右镜像：行 = reverse(half) + half（half 的 index 0 紧贴中心线） */
export function mirrorRows(halfRows) {
  return halfRows.map((half) => {
    const reversed = [...half].reverse().join('');
    return reversed + half;
  });
}

/** 像素矩阵 -> PNG Buffer */
export function renderPng(rows, palette) {
  const height = rows.length;
  if (height === 0) throw new Error('rows 为空');
  const width = [...rows[0]].length;
  const rgba = Buffer.alloc(width * height * 4);
  const cache = new Map();
  for (let y = 0; y < height; y++) {
    const line = [...rows[y]];
    if (line.length !== width) {
      throw new Error(`第 ${y} 行长度 ${line.length} 与首行 ${width} 不一致: ${JSON.stringify(rows[y])}`);
    }
    for (let x = 0; x < width; x++) {
      const ch = line[x];
      let color = cache.get(ch);
      if (color === undefined) {
        color = ch in palette ? parseColor(palette[ch]) : [0, 0, 0, 0];
        cache.set(ch, color);
      }
      const o = (y * width + x) * 4;
      rgba[o] = color[0];
      rgba[o + 1] = color[1];
      rgba[o + 2] = color[2];
      rgba[o + 3] = color[3];
    }
  }
  return { png: encodePng(width, height, rgba), width, height };
}

/** 最近邻放大，返回新的 PNG Buffer（用于肉眼预览） */
export function upscale(rows, palette, factor) {
  const height = rows.length;
  const width = [...rows[0]].length;
  const big = [];
  for (let y = 0; y < height; y++) {
    let line = '';
    for (const ch of rows[y]) line += ch.repeat(factor);
    for (let i = 0; i < factor; i++) big.push(line);
  }
  return renderPng(big, palette);
}

// ---------------------------------------------------------------- CLI

function parseArgs(argv) {
  const args = { spec: null, out: null, preview: null };
  for (let i = 0; i < argv.length; i++) {
    const a = argv[i];
    if (a === '--out') args.out = argv[++i];
    else if (a === '--preview') args.preview = Number(argv[++i]);
    else if (!args.spec) args.spec = a;
  }
  return args;
}

export async function run(specPath, overrides = {}) {
  const abs = resolve(specPath);
  const spec = (await import(pathToFileURL(abs).href)).default;
  const outDir = resolve(overrides.out ?? spec.outDir ?? 'build/texgen');
  const preview = overrides.preview ?? spec.preview ?? 0;
  mkdirSync(outDir, { recursive: true });

  const results = [];
  for (const item of spec.items) {
    const palette = { ...(spec.palette ?? {}), ...(item.palette ?? {}) };
    const rows = item.mirror === 'right' ? mirrorRows(item.rows) : item.rows;
    const { png, width, height } = renderPng(rows, palette);
    const file = join(outDir, `${item.name}.png`);
    writeFileSync(file, png);
    let previewFile = null;
    if (preview > 0) {
      previewFile = join(outDir, `${item.name}@${preview}x.png`);
      writeFileSync(previewFile, upscale(rows, palette, preview).png);
    }
    results.push({ name: item.name, width, height, file, previewFile, bytes: png.length });
    console.log(`[ok] ${item.name}  ${width}x${height}  ${png.length} B  -> ${file}`);
  }
  return results;
}

const isMain = process.argv[1] && resolve(process.argv[1]) === resolve(fileURLToPath(import.meta.url));
if (isMain) {
  const args = parseArgs(process.argv.slice(2));
  if (!args.spec) {
    console.error('用法: node tools/pixelgen.mjs <spec.mjs> [--out dir] [--preview N]');
    process.exit(1);
  }
  run(args.spec, { out: args.out, preview: args.preview }).catch((err) => {
    console.error('[error]', err.message);
    process.exit(1);
  });
}
