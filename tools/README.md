# tools/ — 程序化像素材质生成（16×16 / 32×32）

用 Node.js 直接画像素画并输出 **8bit RGBA PNG（背景完全透明）**，
零依赖、不联网，产物可直接放进 Minecraft 资源包 / 本 mod 的 `assets/vmd/textures/item/`。

## 为什么是「程序化绘制」而不是 AI 出图

- 尺寸**精确**可控（16×16 / 32×32 一格不多一格不少，不会出现 AI 图常见的糊边、尺寸偏移）；
- 每个像素都是硬边、固定调色板，符合 Minecraft 贴图规范（无抗锯齿、无半透明杂边）；
- alpha 通道干净：未绘制的像素 alpha=0，不会出现"白色底"；
- 可复现、可版本管理：材质由代码/字符画描述，改一个数字就能重出图。

## 用法

```bash
# 生成 demo 三件套（剑 16x16、药水 16x16、宝石 32x32）+ 16 倍放大预览
node tools/pixelgen.mjs tools/textures/demo.mjs --preview 16

# 只出图，不要预览
node tools/pixelgen.mjs tools/textures/demo.mjs
```

输出：`build/texgen/demo/<name>.png` 与 `<name>@16x.png`（预览，最近邻放大，不要用于游戏）。

## 两种写谱方式

### 1）字符画（最快，适合一次成型）

```js
export default {
  outDir: 'build/texgen',
  preview: 16,
  palette: { '.': null, k: '#141a2b', L: '#a9dff7' },
  items: [
    {
      name: 'my_item',
      rows: [
        '................',
        '.....kkkk.......',
        // ... 必须 16 行 × 16 列（工具会逐行校验长度并报错）
      ],
    },
  ],
};
```

- `'.'` / `null` / 未定义的字符 → 透明
- 支持 `#rgb`、`#rrggbb`、`#rrggbbaa`
- `mirror: 'right'` → 只需写左半（8 列），自动镜像成 16 列（适合宝石、瓶子等对称物品）

### 2）图元绘制（画复杂图形更快，见 `demo.mjs`）

`pixelart.mjs` 提供的原语：

| 函数 | 说明 |
|---|---|
| `makeCanvas(w, h)` | 建画布，返回 `{ w, h, px }` |
| `rect / disc / line` | 矩形、圆、细线 |
| `polygon(pts, ch)` | 多边形扫描线填充（宝石、布料、地形剪影） |
| `thickLine(x0,y0,x1,y1,offsets,charOf)` | 带明暗色带的粗线，`charOf(法线偏移, 进度)` 决定每条色带的颜色 → 剑身/法杖/弓臂的立体感靠它 |
| `outline(ch, {mode:'in'|'out'})` | 自动描边；`in` 保持剪影，`out` 向外扩一圈 |
| `mapPx(fn)` | 按坐标批量重新上色（渐变、刻面） |
| `setPx / getPx` | 单点读写 |

## 放进 mod 的完整步骤

1. 贴图：`src/main/resources/assets/vmd/textures/item/<registerName>.png`（16×16 推荐给普通物品，32×32 用于宝石/贵重物品）
2. 模型：`src/main/resources/assets/vmd/models/item/<registerName>.json`
   ```json
   { "parent": "minecraft:item/generated", "textures": { "layer0": "vmd:item/<registerName>" } }
   ```
3. 语言：`zh_cn.json` / `en_us.json` 加 `"item.vmd.<registerName>": "中文名"`
4. 注册：在对应系列的 `*Items.java` 里 `ITEMS.register("<registerName>", () -> new ...)`

> 1.20.1 的物品贴图必须是**正方形、2 的幂**（16/32/64…），且用 RGBA；
> 本工具输出完全符合，直接覆盖即可。

## 画 16×16 的几条经验（写提示词时也用得上）

- 16×16 里**只用 1px 粗的斜线会显得发虚**，主体结构尽量 2–3px 宽；
- 明暗只留 3–4 级色阶（如 `#eaf9ff → #a9dff7 → #5ea6d6 → #35688f`），多了会脏；
- 光源统一在**左上**，右下加深；
- 高光只点在 1–3 个像素上，多了就没重点；
- 背景不要画任何"假透明"的棋盘格或白底，直接留 `'.'`。

---

# tools/generate_material_sources.ps1 — 图鉴材料「获取途径」固定资产

图鉴中查看材料时，tooltip 会显示该材料的获取途径。数据来源是 mod 内置的固定资产
`src/main/resources/assets/vmd/material_sources.json`（物品注册名 → 获取途径文本列表）。

该 JSON 由脚本检索 Java 源码后生成，覆盖：

1. 怪物掉落表（`MobSpawnController` / `NoTeamInstance` 的 `ItemAndRate`）
2. 村民交易（`TradeList` / `TradeListNew`）
3. VP 商店、每周商店、无尽核心商店、活动商店、结晶商店
4. 锻造配方、灌注配方、奖励箱
5. 全局掉落（任意怪物概率掉落）

```powershell
pwsh tools/generate_material_sources.ps1
```

> 只有材料来源发生变化时才需要重新生成；生成结果是纯数据文件，构建/运行时不依赖脚本。
