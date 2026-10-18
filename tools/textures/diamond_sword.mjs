/**
 * AI-Generated, 2026-10-18
 *
 * diamond_sword.mjs — 64x64 钻石剑材质（背景全透明）
 * 生成：node tools/pixelgen.mjs tools/textures/diamond_sword.mjs --preview 6
 */
import {
  makeCanvas, rect, disc, polygon, line, mapPx, setPx, outline, rows,
} from '../pixelart.mjs';

// 剑身轴线：A 剑柄端 -> B 剑尖（45° 从左下指向右上）
const A = [26, 36];
const B = [57, 5];
const HW = 7; // 剑身半宽

function buildDiamondSword64() {
  const c = makeCanvas(64, 64);

  const dx = B[0] - A[0];
  const dy = B[1] - A[1];
  const L = Math.hypot(dx, dy);
  const ux = dx / L, uy = dy / L; // 轴向单位向量
  const px = -uy, py = ux; // 法线（指向右下）
  const at = (t, off = 0) => [A[0] + ux * t + px * off, A[1] + uy * t + py * off];

  // ---------------------------------------------------------------- 剑柄
  rect(c, 21, 43, 30, 54, 'N');
  for (const y of [46, 49, 52]) rect(c, 21, y, 30, y, 'n'); // 缠绕环纹
  for (let y = 44; y <= 53; y++) setPx(c, 22, y, 'm'); // 左侧反光
  for (let y = 44; y <= 53; y++) setPx(c, 29, y, 'n'); // 右侧暗部

  // ---------------------------------------------------------------- 剑身
  const shoulder = L * 0.86;
  polygon(c, [
    at(0, -HW), // 上左基部
    at(shoulder, -2.4), // 上左肩
    at(L, 0), // 剑尖
    at(shoulder, 2.4), // 下右肩
    at(0, HW), // 下右基部
  ], 'Q');

  // 按到轴线的有符号距离上色：钻石切面 = 高光脊 + 两侧渐暗
  // 注意：只处理剑身自己的填充字符 'Q'，避免误伤剑柄/护手
  const signedDist = (x, y) => {
    const rx = x + 0.5 - A[0];
    const ry = y + 0.5 - A[1];
    return rx * px + ry * py;
  };
  mapPx(c, (ch, x, y) => {
    if (ch !== 'Q') return undefined;
    const o = signedDist(x, y);
    if (o < -5.4) return 'k'; // 上左描边
    if (o < -4.0) return 'L'; // 上左亮边
    if (o < -1.8) return 'C'; // 钻石主体
    if (o < -0.6) return 'W'; // 中央高光脊
    if (o < 1.4) return 'C'; // 脊下刻面
    if (o < 3.8) return 'M'; // 下半
    if (o < 5.6) return 'D'; // 暗面
    return 'k'; // 下右描边
  });

  // 剑身上的细碎高光（钻石反光）
  setPx(c, 34, 28, 'W');
  setPx(c, 33, 29, 'W');
  setPx(c, 45, 17, 'W');
  setPx(c, 44, 18, 'L');
  setPx(c, 27, 35, 'L');

  // ---------------------------------------------------------------- 护手（钢制，尖角六边形截面）
  polygon(c, [[12, 40], [15, 37], [37, 37], [40, 40], [37, 43], [15, 43]], 'H');
  mapPx(c, (ch, x, y) => {
    if (ch !== 'H') return undefined;
    return y <= 38 ? 'G' : y <= 41 ? 'G' : 'g'; // 上亮下暗
  });

  // ---------------------------------------------------------------- 剑首（钻石）
  // 先用临时字符 'Z' 画，避免 mapPx 误伤整张画布
  disc(c, 25.5, 58, 4.6, 'Z');
  mapPx(c, (ch, x, y) => {
    if (ch !== 'Z') return undefined;
    const d = Math.hypot(x + 0.5 - 25.5, y + 0.5 - 58);
    if (d > 4.0) return 'k'; // 描边
    if (y < 57 && x < 26) return 'L';
    if (y > 58) return 'D';
    return 'C';
  });
  setPx(c, 24, 56, 'W');
  setPx(c, 25, 56, 'W');
  setPx(c, 23, 57, 'L');

  outline(c, 'k', { mode: 'in' });
  return rows(c);
}

export default {
  outDir: 'build/texgen/diamond_sword',
  preview: 6,
  palette: {
    '.': null,
    k: '#0a1622', // 描边
    W: '#ffffff', // 高光
    L: '#d6f6ff', // 亮
    C: '#7fe0ff', // 钻石主色
    M: '#45b6de', // 中
    D: '#2a7ba3', // 暗
    G: '#c3d0dd', // 护手亮
    g: '#7a8798', // 护手暗
    H: '#c3d0dd', // 护手填充（临时字符，着色后不再出现）
    N: '#2b3547', // 剑柄
    n: '#161d28', // 剑柄暗
    m: '#3f4d63', // 剑柄反光
  },
  items: [{ name: 'diamond_sword_64', rows: buildDiamondSword64() }],
};
