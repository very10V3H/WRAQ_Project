/**
 * AI-Generated, 2026-10-18
 *
 * demo.mjs — 示例谱面：3 个 16x16 / 32x32 物品材质，背景全透明。
 * 生成：node tools/pixelgen.mjs tools/textures/demo.mjs --preview 12
 */
import {
  makeCanvas, rect, disc, polygon, thickLine, line, mapPx, setPx, outline, rows,
} from '../pixelart.mjs';

// ---------------------------------------------------------------- 16x16 长剑

function buildSword() {
  const c = makeCanvas(16, 16);

  // 剑身：(6.5,8.5) 剑柄端 -> (13,2) 剑尖，法线方向 5 条色带表现圆柱明暗
  thickLine(c, 6.5, 8.5, 13.0, 2.0, [-1.35, -0.72, -0.09, 0.54, 1.17], (off, u) => {
    if (u > 0.84 && Math.abs(off - 0.3) > 0.85) return null; // 剑尖收窄
    if (off < -1.1) return 'W';
    if (off < -0.45) return 'L';
    if (off < 0.2) return 'M';
    return 'D';
  });
  // 交叉护手（水平金条，横跨剑身基部）
  for (let x = 3; x <= 7; x++) setPx(c, x, 9, x <= 5 ? 'Y' : 'y');
  // 剑柄（垂直向下，木质）
  rect(c, 4, 10, 5, 12, 'H');
  for (let y = 10; y <= 12; y++) setPx(c, 5, y, 'J');
  // 剑首（金色配重）
  rect(c, 4, 13, 5, 13, 'Y');

  return rows(c);
}

// ---------------------------------------------------------------- 16x16 药水

function buildPotion() {
  const c = makeCanvas(16, 16);

  // 玻璃瓶体
  rect(c, 6, 3, 9, 6, 'g'); // 颈
  rect(c, 5, 7, 10, 7, 'g'); // 肩
  rect(c, 4, 8, 11, 13, 'g'); // 体
  // 圆角
  for (const [x, y] of [[4, 8], [11, 8], [4, 13], [11, 13]]) setPx(c, x, y, '.');

  // 液体（紫红）：留 1px 玻璃边
  rect(c, 5, 9, 10, 12, 'p');
  rect(c, 5, 11, 10, 12, 'n');
  rect(c, 6, 9, 7, 9, 'P'); // 液面反光

  // 玻璃高光
  setPx(c, 5, 9, 'G');
  setPx(c, 4, 9, 'G');
  setPx(c, 6, 4, 'G');
  setPx(c, 6, 5, 'G');

  // 软木塞
  rect(c, 5, 1, 10, 2, 'c');
  rect(c, 5, 1, 10, 1, 'C');
  setPx(c, 6, 0, 'c');
  setPx(c, 9, 0, 'c');

  outline(c, 'k', { mode: 'in' });
  return rows(c);
}

// ---------------------------------------------------------------- 32x32 宝石

function buildGem() {
  const c = makeCanvas(32, 32);
  const GIRDLE = 12;

  polygon(c, [[16, 1], [27, 11], [24, 25], [16, 31], [8, 25], [5, 11]], 'm');

  const CX = 15.5;
  mapPx(c, (ch, x, y) => {
    if (ch === '.') return undefined;
    const dx = (x - CX) / 11; // -1..1
    if (y <= GIRDLE) {
      // 冠部：台面最亮，两侧渐暗
      if (y <= 4 && Math.abs(dx) < 0.55) return 'h';
      if (y <= 6) return Math.abs(dx) < 0.7 ? 'l' : 'm';
      return dx < -0.45 ? 'm' : 'l';
    }
    // 亭部：由上到下逐级压暗，中轴提亮
    const t = (y - GIRDLE) / (31 - GIRDLE);
    const center = Math.abs(dx) < 0.32;
    if (t < 0.22) return center ? 'h' : 'l';
    if (t < 0.5) return center ? 'l' : 'm';
    if (t < 0.78) return center ? 'm' : 'd';
    return center ? 'd' : 'D';
  });

  // 腰线
  line(c, 5, GIRDLE, 26, GIRDLE, 'd');
  // 亭部刻面棱线
  line(c, 10, GIRDLE + 1, 16, 30, 'D');
  line(c, 21, GIRDLE + 1, 16, 30, 'D');
  // 冠部刻面棱线
  line(c, 16, 2, 11, GIRDLE, 'd');
  line(c, 16, 2, 20, GIRDLE, 'd');
  // 高光点缀
  setPx(c, 12, 5, 'w');
  setPx(c, 13, 5, 'w');
  setPx(c, 12, 6, 'h');
  setPx(c, 11, 16, 'w');
  setPx(c, 11, 17, 'h');

  outline(c, 'k', { mode: 'in' });
  return rows(c);
}

// ---------------------------------------------------------------- spec

export default {
  outDir: 'build/texgen/demo',
  preview: 12,
  palette: { '.': null, k: '#141a2b' },
  items: [
    {
      name: 'demo_sword',
      rows: buildSword(),
      palette: {
        W: '#f4fcff', L: '#a9dff7', M: '#5ea6d6', D: '#35688f',
        Y: '#f2d478', y: '#a8832f', H: '#8a5a33', J: '#563520',
      },
    },
    {
      name: 'demo_potion',
      rows: buildPotion(),
      palette: {
        g: '#9ed7e6', G: '#eafbff', c: '#6b5236', C: '#b08a5a',
        p: '#d84a92', P: '#f78cc6', n: '#8a2c5b',
      },
    },
    {
      name: 'demo_gem32',
      rows: buildGem(),
      palette: {
        D: '#16405e', d: '#2a6b96', m: '#3f97c4', l: '#74c8e8',
        h: '#b6ecff', w: '#ffffff',
      },
    },
  ],
};
