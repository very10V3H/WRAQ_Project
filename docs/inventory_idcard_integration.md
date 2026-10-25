# 身份卡功能整合进原版物品栏：可行性与方案

> 评估日期：2026-10-25
> 结论：**可行**。视觉风格可以先落地；功能整合建议做成「原版物品栏 + 右侧身份卡面板」，不建议把所有按钮硬塞进 176×166 的原版面板。

## 1. 现状

- `ID_Card` 物品右键后通过 `ScreenSetS2CPacket(3)` 打开 `IdCardGui`（300×200 独立界面）。
- `IdCardGui` 已把功能拆成客户端按钮 + 现成 C2S 包：
  - 精通 → `MasteryScreen`
  - 领取补给 → `DailySupplyC2SPacket`
  - 研发采购 → `WeeklyStoreScreenC2SPacket`
  - 任务列表 → `MissionScreenOpenC2SPacket(2)`
  - 全球市场 → `MarketScreenC2SPacket(0)`
  - 声望商店 → `ReputationStore`
  - vp商店 → `VpStoreScreen`
  - 图鉴 → `Illustrate`
  - 本源回廊 → `TowerScreen`
  - 组队/副本 → `TeamScreenOpenRequestC2SPacket`
- 界面还显示：玩家名、探索点、理智、每日补给预览。
- 因此**不需要改服务端协议**，整合主要是客户端 UI 布局与渲染。

## 2. 原版物品栏的硬约束

- 原版 `InventoryScreen` 背景只有 **176×166**：左侧 4 格护甲 + 玩家预览、2×2 合成、3×9 背包、9 格快捷栏、副手槽。
- 10 个功能按钮 + 点数/理智/补给信息无法在不遮挡槽位的前提下全部塞进 176×166。
- 直接替换 `InventoryScreen` 为自绘界面会破坏：
  - 配方书（Recipe Book）展开时的左移布局；
  - Curios / 饰品栏、Cosmetic Armor 等扩展物品栏模组；
  - JEI / REI 的物品面板定位；
  - 原版拖拽、Shift 快速移动、数字键交换等容器交互。

## 3. Curios API 兼容性（必须一起考虑）

当前 Curios 5.9.0 的接入方式已经确认：

- `CuriosButton` 是叠加在 `InventoryScreen` 上的 `ImageButton`：
  - 默认位置 `TOP_LEFT`，实际为 `guiLeft + 26, guiTop + 8`；
  - 位置可通过 Curios 客户端配置 `buttonCorner / buttonXOffset / buttonYOffset` 改成四角任意位置；
  - 点击时先关闭配方书，再发 `CPacketOpenCurios`，服务端打开 `CuriosContainer`，客户端切到 **独立的 `CuriosScreen`**；
  - 再次点击（在 CuriosScreen 内）通过 `CPacketOpenVanilla` 回到原版 `InventoryScreen`。
- `CuriosScreen` 不是「在原版物品栏旁边展开一块」，而是另一张 Screen：
  - 它先在 `renderBg` 里 blit **原版 `textures/gui/container/inventory.png`** 作为主物品栏，再 blit Curios 的饰品列纹理；
  - 当前 Curios 5.9.x 默认使用 **V2 界面**（`CuriosScreenV2`），饰品列/翻页/装饰按钮读取的是 `curios:textures/gui/inventory_revamp.png`；旧版 `inventory.png` 只给旧 `CuriosScreen` 用；
  - 基础宽度约 **431px**，有滚动条时 +30、有 cosmetic 列时 +40；
  - 自带 `widthTooNarrow`、配方书左移和窄屏处理。
- 由此带来两个结论：
  1. 我们替换的原版 `inventory.png` **会同时影响 CuriosScreen 的主物品栏区域**（这是好事，风格能延续）；
  2. 但 Curios 饰品列是另一张纹理，而且 V2 用的是 `inventory_revamp.png`；**只改 `inventory.png` 不会影响截图左侧的饰品栏**。两张曲奇纹理都要覆盖，且 CuriosScreen 已经较宽，不能简单把 112px 的身份卡右侧面板同时挂到 CuriosScreen。

### 对 UI 方案的影响

- `InventoryScreen`：使用右侧身份卡面板（176 + 112，常规 GUI Scale 可放下）。
- `CuriosScreen`：**不挂完整面板**，只放一个「身份卡」标签/按钮（或复用底部安全区域），点击后以 **居中弹层** 打开同一套身份卡内容；或者直接提示玩家切回原版物品栏。
- 身份卡标签的位置必须避开 Curios 按钮：读取 `CuriosClientConfig.CLIENT.buttonCorner`，自动选对侧；不要写死右上角，因为 Curios 按钮可以配置到右上角。
- 两个 Screen 共用一套 `IdCardActions` / `IdCardPanel` 组件；`ScreenEvent.Init.Post` 对 `InventoryScreen` 和 `CuriosScreen` 分别挂载，避免复制两份业务逻辑。
- 打开 CuriosScreen 时，原版界面的右侧面板会随 Screen 切换消失；这是预期行为，身份卡入口以标签/按钮保留。

## 4. 推荐方案：原版物品栏 + 右侧身份卡面板

### 布局

```
┌────────────────────┐  ┌──────────────┐
│  原版 176×166 物品栏 │  │ 身份卡面板     │
│  护甲/合成/背包/快捷栏│  │ 玩家名/点数/理智│
│                    │  │ [精通][补给]  │
│                    │  │ [任务][市场]  │
│                    │  │ [声望][VP]    │
│                    │  │ [图鉴][回廊]  │
│                    │  │ [组队][研发]  │
└────────────────────┘  └──────────────┘

CuriosScreen：
┌──────────────────────────────────────┐
│ 主物品栏（沿用新纹理） │ 饰品列 │ [ID] │
└──────────────────────────────────────┘
        └─ 点击 [ID] → 居中身份卡弹层
```

- 面板宽约 112px，2 列 × 5 行按钮（16px 高），顶部 3~4 行信息。
- 位置由 `InventoryScreen` 的 `guiLeft + 176 + 6`、`guiTop` 计算；配方书展开时随 `guiLeft` 一起移动。
- 小屏幕/高 GUI Scale 下若右侧放不下，回退为「顶部身份卡标签」或折叠面板。
- 默认折叠，点击物品栏右上角的「ID」标签展开，避免遮挡视野。
- CuriosScreen 的 `[ID]` 标签放在与 Curios 按钮相反的角落；窄屏时改为底部/中央弹层。

### 技术落点

- `ScreenEvent.Init.Post`：`if (event.getScreen() instanceof InventoryScreen screen)` 时向 `screen.addRenderableWidget(...)` 添加按钮；按钮复用 `IdCardGui` 的点击逻辑。
- `ScreenEvent.Render.Pre`：在按钮渲染之前绘制面板背景/信息；面板在 176px 之外，不会盖住物品栏内容。
- `InventoryScreen.getGuiLeft()/getGuiTop()` 取坐标；若映射访问受限，使用 AccessTransformer 或 `@Accessor`（项目目前无 Mixin，优先用公开 getter）。
- 打开物品栏时按需发送一次 `SkillRequestC2SPacket`，同步精通/技能数据；点数、理智、补给状态在登录时已有同步。
- `ID_Card.use` 可以改为「打开原版物品栏」并发送数据请求；保留物品作为快捷入口，避免旧引导文本（多处提到右键身份卡）失效。

## 5. 备选方案

| 方案 | 优点 | 缺点 |
|---|---|---|
| A. 原版右侧面板 + Curios 单标签/弹层（推荐） | 功能完整、Curios 不溢出、原版交互不受影响 | 需要处理缩放/配方书/Curios 按钮角配置 |
| B. 两个界面都只放「身份卡」按钮 → 打开旧 `IdCardGui` | 改动最小（约半天） | 不是真正的功能整合，只是快捷入口 |
| C. 完全替换 `InventoryScreen` / `CuriosScreen` | 视觉最统一 | 兼容性风险最高，不推荐 |

## 6. 风险与工作量

- 视觉换材质：**已完成**（见 `assets/minecraft/textures/gui/container/inventory.png`；Curios 列纹理同样已做一份）。
- 原版右侧面板 + 10 个按钮：约 **1~2 天**（含配方书/缩放处理）。
- CuriosScreen 单标签 + 弹层、按钮角冲突处理：约 **0.5~1 天**。
- 点数/理智/补给信息移植 + 旧界面退役：约 **1~2 天**。
- 兼容性回归：JEI、Curios（展开/收起、滚动条、cosmetic 列）、Cosmetic Armor、不同 GUI Scale、配方书展开、Curios 按钮四角配置；建议保留旧 `IdCardGui` 作为回退入口至少一个版本。

## 7. 建议实施顺序

1. **已完成**：身份卡风格物品栏材质（含 Curios 饰品列），先看实机观感。
2. 原版物品栏加「ID」折叠标签 + 右侧面板骨架（空按钮）。
3. CuriosScreen 加单标签/弹层入口，读取 Curios 按钮角配置避开冲突。
4. 接入 10 个按钮的现有 C2S 包，保留旧 `IdCardGui` 作为回退。
5. 移植玩家名/点数/理智/补给信息。
6. 实机回归后再决定是否让 `ID_Card` 改为直接打开原版物品栏。

## 8. 纹理说明

- 原版物品栏：`src/main/resources/assets/minecraft/textures/gui/container/inventory.png`（256×256，主面板 176×166）。
- Curios 旧界面：`src/main/resources/assets/curios/textures/gui/inventory.png`（Curios 5.9.0 旧 `CuriosScreen`）。
- Curios V2 界面（当前默认）：`src/main/resources/assets/curios/textures/gui/inventory_revamp.png`（`CuriosScreenV2` 实际读取的纹理）。
- 调色：主底色取新旧方案中间值 **#A7E6FF**（旧 #E8FBFF 与新 #65D1FF 的中间），槽位/数字框用 **#C0B8F3** 淡紫，描边用 **#6DB7F6** 蓝；顶部保留四色身份卡缎带和右上角四色标签。
- 原版路径是**对原版物品栏纹理的全局覆盖**，会同时影响 `InventoryScreen` 和 `CuriosScreen` 的主物品栏区域；Curios 纹理是对另一个模组资源的覆盖，最终是否生效取决于资源包加载顺序（vmd / curios / 用户资源包谁在上层）。若需资源包完全可控，应改为 `assets/vmd/...` + `ScreenEvent` 自绘（见方案 A）。
- Curios 的 `CuriosButton` 也使用同一张 `curios:textures/gui/inventory.png` 的图标区域，因此按钮图标会一起变成浅青/淡紫风格；需实机确认可见性，必要时只重着色槽位区域、保留按钮图标原色。

## 9. 合成配方书按钮

- 已新增 `fun.wraq.events.client.InventoryUiTweaks`：在 `ScreenEvent.Init.Post` 中把原版 `InventoryScreen` 与 Curios `CuriosScreen(V2)` 的 **20×18 配方书按钮**设为 `visible = false, active = false`。
- 位置按原版布局固定为 `guiLeft + 104, guiTop + 61`；Curios V2 的配方书按钮也使用同一坐标。
- 只隐藏按钮，不修改 `RecipeBookComponent`、合成格和背包交互。
- 副作用：配方书不再有鼠标入口；如果仍需使用，需要后续补一个快捷键或身份卡标签入口。

## 10. 身份卡功能已接入原版背包

`InventoryUiTweaks` 现在会在 `InventoryScreen` 和 CuriosScreen 上添加身份卡布局：

- **右侧 100×166 面板**：
  - 顶部「身份卡」标题 + 已用/总精通点；
  - 5 条精通路径（精密/主宰/巫术/坚决/启迪）的点数概览，颜色与 `MasteryTree` 一致；
  - 「精通」按钮；
  - 「每日补给」物品渲染（数量、悬停 tooltip）与「领取补给」按钮。
- **上方 196×20 横条**：研发采购、全球市场、VP商店、声望商店；
- **下方 196×20 横条**：图鉴、本源回廊、任务列表、组队副本；
- 按钮动作统一走 `IdCardActions`，与独立 `IdCardGui` 使用同一套 C2S 包/界面；
- 右侧面板优先挂在物品栏右侧（`guiLeft + guiSize + 4`），放不下尝试左侧；上下横条居中；任一区域放不下则回退成一个「身份卡」按钮，打开旧 `IdCardGui`；
- 背景由 `IdCardPanelWidget` / `IdCardBarWidget` 自绘（身份卡配色）；每日补给 tooltip 用最后一个 widget 渲染，避免被「领取补给」按钮盖住；
- CuriosScreen 同样适用；高 GUI Scale 下自动回退。

> 精通概览读取 `MasteryClientData`（登录时同步）；每日补给物品读取 `DailySupply.getRewardItemMap()`，与旧身份卡界面一致。

## 11. 打开背包时自动打开 Curios

已新增逻辑（同一个 `InventoryUiTweaks`）：

- 客户端 `ClientTickEvent.END` 监听 Screen 切换；
- 从游戏/其他界面进入 `InventoryScreen` 时，按类名找到 Curios 的 `CuriosButton`，调用它原本的点击逻辑；
- CuriosButton 自己会发送 `CPacketOpenCurios`，服务端打开 `CuriosContainerV2`，客户端切到 `CuriosScreenV2`，于是主物品栏 + 饰品栏一起显示；
- 从 Curios 界面点按钮返回原版 `InventoryScreen` 时会跳过自动打开，避免「关不掉饰品栏」；
- Curios 按钮被配置禁用或 Curios 不存在时自动跳过，不影响原版背包。

注意：这是**客户端自动点击**方案，会有约 1 tick 的界面切换延迟；如果玩家确实只想看原版背包，目前需要先关闭 Curios 再关闭背包，没有单独配置项。需要的话可以后续加「自动打开 Curios」客户端开关。
