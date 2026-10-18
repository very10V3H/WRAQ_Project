# 方案 v3：用 VMD 自研 NPC 实体替代 CustomNPCs 依赖

> 状态：**7 项决策已锁定，WP1–WP7 已实施，`compileJava` 通过；运行时验收待实机**
> v2 → v3：进入实施阶段，新增「实施进度」章节；v1 → v2 变更见历史（删除迁移章节、`npcId` 化升为本轮范围、补入碰撞体/名字板/装备层/配置位置四项决策）。
> 本文「现状」结论均已在代码或反编译产物中核验，证据见附录 A。

---

## 0. 实施进度（2026-10-17）

### 已完成（`gradlew compileJava` BUILD SUCCESSFUL）

**2026-10-17 追加：NPC 位置固化 + 启动自动转换**

| 项 | 内容 |
|---|---|
| 离线扫描 | 真 NBT 解析（非字符串匹配）扫描 `wraq2-2026-03-02` 备份：主世界 231 region / 14,187 区块 / 456 村民，其中 **77 只带自定义名**；末地 2 只（终界技艺师、终界研究者）；下界 0 |
| 命中筛选 | 77+2 只共 **50 个不同名字**，逐个在 `src/**/*.java` 中检索，**全部有引用**（`StringUtils` 商人名表、`TradeList`、`MyVillagerData` 等），因此全部认定为 VMD 交易类 NPC |
| 固化产物 | [`run/config/vmd_npcs.json`](../../run/config/vmd_npcs.json)：**79 条**（主世界 77 + 末地 2），字段含 `dialogId / name / nameJson / dimension / x / y / z / yaw / skinType=file / skinValue=civil`。`nameJson` 保留原始颜色（如 `#FFAE00`），避免转换后名字变白 |
| id 约定 | 交易类 NPC 使用 `trade:<显示名>`；对话类仍用 `NpcIds` 常量。交互路由不变：注册表命中则走对话，未命中落到既有的「按显示名开交易界面」分支（交易系统 id 化仍是明确的范围外项） |
| 启动转换 | `NpcRegistry.applyOnServerStart`（`ServerStartedEvent`）：目标位置**没有实体 → 补建 NPC**；**能看到原版村民 → 就地转换**（取最近一只为锚点继承名字/朝向，4 格内其余村民全部 `discard()`）；**已是 VMD NPC → 刷新**。判定键 = `dialogId` + 同维度 4 格内，可重复执行 |
| 村民拦截（关键修正） | 启动期「转换」实测恒为 0：**1.20 实体数据是懒加载的**，只在区块进入 `ENTITY_TICKING`（玩家附近 / forceload）时才读盘，`ServerStartedEvent` 时没有玩家，`level.getAllEntities()` 里看不到远处村民 → 只会盲建 NPC，造成村民与 NPC 重合。修正：新增 `NpcVillagerFilter`，监听 `EntityJoinLevelEvent`——村民一旦进入世界且位置命中配置（4 格内），直接 `event.setCanceled(true)` + `discard()`，并 `ensureNpc`（已有则刷新、没有则以该村民为锚点生成）。与加载时机、是否有玩家、是否 forceload 全部无关。Forge 对**从存档加载的实体**同样触发该事件（已在 `PersistentEntitySectionManager` 中确认） |
| 新命令 | `/vmd npc scan`（把已加载区块里的旧村民按名字补录进配置，并覆盖为真实坐标）、`/vmd npc apply`（立即按配置补建/转换）、`/vmd npc dedup`（同位置同名 NPC 去重）、`/vmd npc save`（也会导出未转换的村民）、`/vmd npc reload`（只对齐不转换）。另：`VmdNpc.kill()` 覆写为 `discard()`，`/kill @e[type=vmd:npc]` 可清理误生成的 NPC |
| 主动去重 | 三层：① **配置层** `dedupeDefs`——apply 前折叠「同维度 + 同 dialogId + 4 格内」的重复条目；② **世界层** `dedupLevel`——apply 结束时逐维度扫描，同 4 格内「dialogId 或显示名相同」只保留一只，`/vmd npc dedup` 可手动触发；③ **加载层** `EntityJoinLevelEvent`——NPC 每次进入世界都跑 `dedupAround`，重复的那只会连同事件一起取消。保留优先级：**命中配置且离配置坐标最近 > 实体 id 更小（更早存在）**，保留后按配置再刷新一次（名字/皮肤/朝向）。已校验当前 79 条配置里同名最近距离 73 格，不会误删合法 NPC |
| 坐姿 | `VmdNpcPlayerModel extends PlayerModel<VmdNpc>` 覆写 `setupAnim`：先跑原版，再按 `sitting` 覆盖双腿角度（常量与原版 `HumanoidModel` 骑乘分支一致）。**不**覆写 `isPassenger()`（会连带隐藏名字板——`shouldShowName` 含 `!isPassenger()`——并让部分旅行逻辑走进骑乘分支），也**不**召唤隐形载具实体。配置字段 `sitting` / `sitOffset`（正值下移，贴合椅子/地面，默认 0）；命令 `/vmd npc set sitting <bool>`、`/vmd npc set sitoffset <float>` |
| 名字颜色 | 村民名字带 hex/命名色（如 `#BA00DF`、`light_purple`），转换后必须保留：配置里存 `nameJson`（原始组件 JSON），刷新与生成都走 `componentOf(def)` 解析。**坑**：`applyTo` 早期版本用 `Te.s(def.name)` 刷新，会把颜色剥成白字；已修正为 `componentOf`。`/vmd npc set name <文本>` 仍是纯文本（会清色，适合无意保留样式时用），保留颜色请用新命令 `/vmd npc set namejson <json>`（会校验 JSON 合法性） |
| 视线行为 | 三处调整：① **脱离范围不再弹回**——原实现在无目标时 `setYHeadRot(homeYaw)` + `setXRot(0)`，玩家一离开视野头就"瞬移"，已删除该回正，改为保持最后朝向；② **限速转向**——用 `turnTowards` 每次更新（2 tick）最多转 `TURN_STEP_DEG = 15°`（约 7.5°/tick，转 90° 约 1 秒），进范围也不会瞬间扭头；③ **随机视线**——无目标时每 `IDLE_LOOK_INTERVAL_TICKS = 100`（5 秒）随机挑「左/右/上/下」转 `45°`，3 次更新内插值完成（约 0.3 秒），俯仰限制 ±60°、偏航相对身体限制 ±75°（避免随机游走后脑袋转到背后）。身体朝向始终固定为配置 yaw，只有头动 |

**风险/注意**

- 转换会 `discard()` 掉村民实体：其 `VillagerData`（职业/等级）随之丢失。VMD 的交易界面是按**名字**查 `TradeList`/`MyVillagerData`，不读实体职业，所以功能不受影响；但若将来有逻辑依赖实体职业，需要先迁移。
- 配置里没列出的村民**不会**被改动（避免把野外普通村民变成静态 NPC）。
- 幂等判定是「同 id + 4 格内」：若 GM 用 `/tp` 把 NPC 挪走超过 4 格，下次启动会在原位再生成一只（此时应 `save` 更新配置）。
- 扫描只覆盖**已加载区块**：`/vmd npc scan` 需要在线上世界巡图或配合 `/forceload`；离线扫描器 `build/tmp/npc-scan.ps1` 直接读 region 文件，不受加载限制。

### 已完成（首轮）

| 工作包 | 产出 |
|---|---|
| WP1 实体 | `entities/entities/npc/VmdNpc.java`：同步字段（dialogId/皮肤 4 字段/slim/lookRadius/scale）、禁移动、免伤、可穿过、永不消失、注视 tick、NBT 往返（含 `Mob` 自带的 HandItems/ArmorItems） |
| WP2 渲染 | `render/entity/VmdNpcRenderer.java`：原版 `PlayerModel` slim/wide 单渲染器切换、ArmPose、缩放、皮肤取址 |
| WP3 皮肤 | `render/entity/NpcSkinCache.java`：内置 PNG → `textures` 属性 → 在线玩家 → 默认皮肤 四级回退 + 来源指纹缓存 |
| WP5 装备 | `render/entity/VmdNpcArmorLayer.java`（slim/wide 盔甲委托层）+ `ItemInHandLayer` + `/vmd npc equip <slot>` |
| WP6 `npcId` 化 | `process/system/npc/NpcIds.java`（12 个 id 常量 + 显示名反正查）、`NpcIdResolver.java`（三级解析 + 兼容兜底 + stamp）、`NpcInteractionRegistry.java`（id → 行为注册表，取代 12 分支 switch）、`DialogTrees` 改注册表（`DialogTree` 接口 + `EntrustmentTree`） |
| WP7 命令/配置 | `process/system/npc/NpcCommand.java`（create / remove / set×6 / equip / list / stamp / reload / save）、`NpcRegistry.java`（`config/vmd_npcs.json` 幂等对齐 + 导出） |
| WP8 依赖移除 | `build.gradle` 删除 customnpcs 依赖（留注释说明）；`VillagerEvents` 重写为「id 路由 + 村民/容器拦截 + 商人回退」 |

### 未做 / 需后续

| 项 | 说明 |
|---|---|
| 运行时验收 | 20 项矩阵（第 8 节）需在 IDEA `runClient` 实机验证：皮肤分级、注视、装备渲染、改名字不断链、reload 幂等 |
| 内置 PNG 资源 | 目录 `assets/vmd/textures/entity/npc/` 已建立，并放入 `civil.png`（源图取自 `textures/entity/civil.png`）与 `default.png`（同一张，作为无皮肤时的兜底）。**逐 NPC 指定**：`/vmd npc set skinfile <名字>` 或配置 `"skin":{"type":"file","value":"<名字>"}`。已核验 `civil.png` 是标准 64×64 原版玩家皮肤布局（geo 模型 UV 锚点与原版头部/身体/四肢/帽子层完全一致），可直接用于 `PlayerModel` |
| `skinUrl`（L5） | 按决策 1 未实现（主方案不需要图床）；如后续需要可加 30 行 |
| 交易系统 id 化 | 明确不在本轮范围（`TradeList`/`MyVillagerData` 双键空间，见 5.5 范围边界） |
| GM 手册 | 命令用法与 `vmd_npcs.json` 字段说明建议整理成一页 |


---

## 1. 决策记录（本轮已锁定）

| # | 决策 | 对方案的影响 |
|---|---|---|
| 1 | 皮肤主方案 = **创建时抄取在线玩家 `textures` 属性** + **内置 PNG 兜底** | 客户端皮肤解析以 L1/L5 两级为主；URL 直链降为可选开关（默认关闭） |
| 2 | **暂不考虑迁移**，存量 NPC 全是原版村民 | §「线上世界迁移」整章删除；工期 −0.5 天；风险清单去掉最高风险项 |
| 3 | **推进 `npcId` 化** | `NpcIds` 常量表 + `DialogTrees` 改注册表 + 实体存 id + 兼容兜底，`VillagerEvents` 分发改按 id |
| 4 | NPC **允许玩家穿过**（无碰撞体） | `canBeCollidedWith() = false` |
| 5 | 名字板**常显**（与原版一致） | `setCustomNameVisible(true)`，不额外做准星判定 |
| 6 | **允许 NPC 穿戴装备与手持物品**，行为同原版玩家 | 增加 `HumanoidArmorLayer` + `ItemInHandLayer` + `ArmPose` + 装备槽命令，工期 +0.5 天 |
| 7 | 配置**跨存档复用** | `config/vmd_npcs.json`（不是 `world/`） |

---

## 2. 结论摘要

| 项 | 结论 |
|---|---|
| 可行性 | **高**。VMD 对 CustomNPCs 的依赖面仅为 1 行 gradle + 1 个文件的一处 `instanceof` |
| 新增代码量 | 约 **950–1150 行 / 11 个新文件 + 8 处修改** |
| 工期估算 | WP1–WP9 合计 **4.5–5 个工作日**（不含实机灰度） |
| 最大风险 | ① 皮肤依赖 Mojang 纹理 CDN 可达性（已用内置 PNG 兜底对冲）② GM 失去 CustomNPCs 图形化编辑器（用 JSON + 命令对冲）③ VMD 自有伤害系统可能绕过 `hurt()` |
| 阻塞项 | **当前 `pwsh` 不可用**（DSH 沙箱后端起不来），无法编译验证 → 建议先恢复执行环境再开工 |

---

## 3. 现状核验（代码事实）

### 3.1 依赖面只有两处

```
build.gradle:251     implementation fg.deobf("curse.maven:customnpcs-unofficial-1052708:7694841")

src/main/java/fun/wraq/events/core/VillagerEvents.java:29       import noppes.npcs.entity.EntityCustomNpc;
src/main/java/fun/wraq/events/core/VillagerEvents.java:44-127   if (target instanceof EntityCustomNpc npc) { ... }
```

- 全仓库 grep `customnpcs|noppes`：**命中 2 个文件，仅此 2 处**。
- `mods.toml` **未**声明 customnpcs 依赖（只声明 `curios`）。所以「只删 mod 不改代码」会导致 `VillagerEvents` 类加载失败 → 所有村民交互失效。**必须改代码。**

### 3.2 对话系统与实体类型解耦（关键利好）

```java
// DialogManager.java:63
public static void open(Player player, Entity npc, String npcId)
```

只用到 `Entity`（取 `position()` 做 8 格距离校验）与 `npcId` 字符串，**没有任何 customnpcs 类型参与**。现有 12 个 `switch` 分支全部按 `npc.getName().getString()` 分发，因此新实体的显示名一致即可无缝接管。

### 3.3 可复用的实体基建

| 已有 | 位置 | 用法 |
|---|---|---|
| 实体注册 | `ModEntityType.ENTITY_TYPES` | 加一个 `RegistryObject<EntityType<VmdNpc>>` |
| 属性注册 | `VMD.Attribute(EntityAttributeCreationEvent)` | 加一行 `event.put(...)` |
| 渲染器注册 | `ClientModEventSubscriber.OnClientSetup` | 加一行 `EntityRenderers.register(...)` |
| 命令入口 | `commands.CommandHandler.onServerStarting(RegisterCommandsEvent)` | 挂 `/vmd npc` 子命令 |
| 服务端 tick | `events/server/ServerTick` | NPC 注册表 tick（可选） |
| 自研 Mob 范例 | `entities/entities/Civil/Civil.java` | 属性/名字/清理写法参考 |

### 3.4 CustomNPCs 体量对照

反编译统计：**10.4 MB / 1242 个 class**。我们需要的 4 项能力在原版 API 里全部现成（`PlayerModel`、`SkinManager`、`ServerEntity` 自动同步），**不需要照搬它 200+ class 的 MPM 模型系统**。为 4 项需求背一个非官方续作分支（CC BY-NC 授权）不成立。

---

## 4. 方案对比（结论未变）

| | A. 自研实体（**采用**） | B. 保留 CustomNPCs | C. 裁剪内嵌 JiJ | D. 换别的 NPC mod |
|---|---|---|---|---|
| 依赖体积 | 0 | +10.4 MB | +10 MB | +X MB |
| 授权 | 无冲突 | CC BY-NC（禁商用） | 演绎作品风险 | 视 mod |
| 工作量 | 4.5–5 天 | 0 | 3–5 天 + 每版维护 | 2–3 天但换汤不换药 |
| 长期收益 | NPC 布局可版本化 | 无 | 无 | 无 |

---

## 5. 详细设计

### 5.1 实体层 `VmdNpc`

```
src/main/java/fun/wraq/entities/entities/npc/VmdNpc.java
```

```java
public class VmdNpc extends PathfinderMob {
    // 同步字段（SynchedEntityData.defineId）
    //   dialogId     : String   对话/交易标识（决策 3）
    //   skinName     : String   皮肤所有者名字
    //   skinProperty : String   base64 textures 属性（决策 1，可离线还原）
    //   skinSignature: String
    //   slim         : boolean  纤细模型
    //   lookRadius   : float    注视半径（默认 8）
    //   scale        : float    模型缩放（默认 1.0）
    //   skinFile     : String   内置 PNG 名（决策 1 兜底）
}
```

核心覆写：

```java
@Override public void travel(Vec3 v) { }                         // 禁止移动（含重力/流体/传送带）
@Override public void push(double x, double y, double z) { }     // 禁止被推挤
@Override public void knockback(double s, double rx, double rz) { }
@Override public boolean isPushable() { return false; }
@Override public boolean hurt(DamageSource src, float amount) { return false; }
@Override public boolean isInvulnerableTo(DamageSource src) { return true; }
@Override protected void checkDespawn() { }                      // 永不自然消失
@Override public boolean removeWhenFarAway(double d) { return false; }
@Override public boolean canBeCollidedWith() { return false; }   // 决策 4：玩家可穿过
@Override public InteractionResult mobInteract(Player p, InteractionHand h) {
    return InteractionResult.SUCCESS;                            // 不触发换装/交易界面
}
```

构造时：`setNoAi(true)`、`setInvulnerable(true)`、`setPersistenceRequired()`、`setCustomNameVisible(true)`（决策 5）、`setCanPickUpLoot(false)`（不捡地上的东西）、`setDropChance` 全部 0（防御性）。

尺寸 `0.6 × 1.8`，`MobCategory.MISC`，`clientTrackingRange(10)`；属性 `maxHealth 20 / movementSpeed 0 / knockbackResistance 1 / followRange 16`。

**装备（决策 6）**：直接使用 `Mob` 自带的装备槽与 NBT 持久化，不需要自建容器。

```java
setItemSlot(EquipmentSlot.MAINHAND, stack);   // OFFHAND / HEAD / CHEST / LEGS / FEET 同理
// NBT 由 Mob 自身写入 HandItems / ArmorItems，随实体存档自动往返
```

### 5.2 渲染层

```
src/main/java/fun/wraq/render/entity/VmdNpcRenderer.java      （客户端专用包）
src/main/java/fun/wraq/render/entity/NpcSkinCache.java
src/main/java/fun/wraq/render/entity/VmdNpcArmorLayer.java    （slim/wide 委托层）
```

```java
public class VmdNpcRenderer extends LivingEntityRenderer<VmdNpc, PlayerModel<VmdNpc>> {
    private final PlayerModel<VmdNpc> wide, slim;

    public VmdNpcRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new PlayerModel<>(ctx.bakeLayer(ModelLayers.PLAYER), false), 0.5F);
        wide = (PlayerModel<VmdNpc>) this.model;                               // protected 非 final，可写
        slim = new PlayerModel<>(ctx.bakeLayer(ModelLayers.PLAYER_SLIM), true);
        this.addLayer(new VmdNpcArmorLayer(this, ctx));                        // 装备层（按 slim 选模型）
        this.addLayer(new ItemInHandLayer<>(this, ctx.getItemInHandRenderer())); // 手持物品层
    }

    @Override public void render(VmdNpc npc, float yaw, float pt, PoseStack ps,
                                 MultiBufferSource buf, int light) {
        this.model = npc.isSlim() ? slim : wide;      // 单渲染器切换 slim/wide
        this.model.setAllVisible(true);
        this.model.crouching = false;
        // 手持物品时手臂抬起（不加这段，物品会挂在垂下的手臂上）
        this.model.rightArmPose = npc.getMainHandItem().isEmpty()
                ? HumanoidModel.ArmPose.EMPTY : HumanoidModel.ArmPose.ITEM;
        this.model.leftArmPose  = npc.getOffhandItem().isEmpty()
                ? HumanoidModel.ArmPose.EMPTY : HumanoidModel.ArmPose.ITEM;
        super.render(npc, yaw, pt, ps, buf, light);
    }

    @Override public ResourceLocation getTextureLocation(VmdNpc npc) {
        return NpcSkinCache.resolve(npc);
    }
}
```

- **模型**：`PlayerModel(ModelPart, boolean slim)`，`ModelPart` 由 `ctx.bakeLayer(ModelLayers.PLAYER / PLAYER_SLIM)` 提供 —— 不新建模型层、不需要 AT。
- **装备层**：`HumanoidArmorLayer` 的 inner/outer 模型在构造时固定，而 `addLayer` 是 `final` 无法动态增删 → 用 `VmdNpcArmorLayer` 包住「宽版/细版」两套 `HumanoidArmorLayer`，在 `render()` 里按 `npc.isSlim()` 委派（约 30 行）。备选（更省事但细版 NPC 盔甲略错位）：固定宽版。
- **手持层**：`PlayerItemInHandLayer` 是 `AbstractClientPlayer` 专用，**不能用**；用父类 `ItemInHandLayer<T extends LivingEntity, M extends EntityModel<T>>`。
- **名字板**：默认 `shouldShowName()` 为 true，配合 `setCustomNameVisible(true)` 即与原版玩家一致（决策 5）。
- **不做**：披风/鞘翅/鹦鹉/蜜蜂蜇针/deadmau5 耳朵 —— `CapeLayer`、`ElytraLayer` 等都要 `AbstractClientPlayer`，与需求无关。

### 5.3 皮肤解析管线（决策 1 锁定）

服务端**不下载任何皮肤**，只把来源写进实体同步数据；客户端 5 级回退：

```
L0  skinFile 非空        → ResourceLocation("vmd","textures/entity/npc/<file>.png")   内置，永不联网 ✅主方案
L1  skinProperty 非空    → GameProfile + textures 属性
                            → SkinManager#getInsecureSkinLocation(profile)              可离线还原真实玩家皮肤 ✅主方案
L2  skinName 命中在线玩家 → Minecraft#getConnection()#getPlayerInfo(name)#getSkinLocation()  零网络
L3  skinName 有但不在线   → SkinManager#registerSkins(profile, cb, false)               异步、需可达 Mojang
L4  全部失败             → DefaultPlayerSkin#getDefaultSkin(uuid)                       兜底，绝不白板
L5  skinUrl（可选，默认关）→ HttpTexture(url)                                           自建图床用
```

创建 NPC 时服务端抄取属性（仅当被指定玩家在线时可用）：

```java
// ServerPlayer 上直接可读，无需外部 API
var textures = serverPlayer.getGameProfile().getProperties().get("textures");  // Property: value + signature
```

`SkinManager` 注册的 `HttpTexture` 会把皮肤缓存到 `assets/skins/xx/<sha1>`，玩家下次离线进服也能显示；**关键 NPC 一律配 `skinFile`**，把外部依赖压到零。

已核验 API：`SkinManager#registerTexture / registerSkins / getInsecureSkinLocation / getInsecureSkinInformation`、`AbstractClientPlayer#getSkinTextureLocation / registerSkinTexture / getSkinLocation`、`HttpTexture(File,String,ResourceLocation,boolean,Runnable)`、`DefaultPlayerSkin#getDefaultSkin(UUID)`。
注意 1.20.1 **没有** `PlayerSkin` record（1.20.2+ 才有），皮肤元数据走 `PlayerInfo` / `SkinManager`。

### 5.4 注视附近玩家

```java
@Override public void tick() {
    super.tick();
    if (level().isClientSide || tickCount % 2 != 0) return;

    Player target = level().getNearestPlayer(this, getLookRadius());
    if (target == null) { setYHeadRot(spawnYaw); setXRot(0); return; }

    Vec3 eye = target.getEyePosition(), from = getEyePosition();
    double dx = eye.x - from.x, dy = eye.y - from.y, dz = eye.z - from.z;
    double horiz = Math.sqrt(dx * dx + dz * dz);
    setYHeadRot((float) (Math.toDegrees(Math.atan2(dz, dx))) - 90.0F);   // 只转头
    setXRot((float) (-Math.toDegrees(Math.atan2(dy, horiz))));            // 头俯仰
}
```

**已核验**：原版 `ServerEntity.tick()`（`ServerEntity.java:191-194`）在 `yHeadRot` 变化 ≥ 1/256 圈时自动广播 `ClientboundRotateHeadPacket`，`xRot` 走 `ClientboundMoveEntityPacket.Rot`。
→ **无需自定义网络包**；身体不转（`LivingEntityRenderer` 用 `yHeadRot - yBodyRot` 得 `netHeadYaw`）。

### 5.5 `npcId` 化（决策 3，本轮范围内）

#### 现状问题（一句话）

`DialogPage(String npcId, ...)` / `DialogManager.select(player, npcId, ...)` 的字段叫 id、注释写「对话树标识」，但实参是**显示名**——借名字当主键。后果：改名即断链且静默失败（`switch` 无 default、`DialogTrees.build()` 落到「这个 NPC 还没有配置对话」），另有 `getCustomName()` 与 `getName()` 两种取值方式不一致、同名 NPC 无法区分、`vmd_npcs.json` 的幂等对齐没有稳定主键。

#### 目标结构

```
src/main/java/fun/wraq/process/system/npc/NpcIds.java       // 常量表（唯一事实源）
src/main/java/fun/wraq/process/system/npc/NpcIdResolver.java // 实体 → dialogId（含兼容兜底）
src/main/java/fun/wraq/common/impl/npc/VmdNpcLike.java       // getDialogId() / getDisplayNameString()
```

```java
// NpcIds.java
public final class NpcIds {
    public static final String ENTRUSTMENT   = "entrustment";     // 联合研院秘书 - 贝尔
    public static final String BANK_CLERK    = "bank_clerk";      // 联合银行职员
    public static final String ALLAY_SCHOLAR = "allay_scholar";   // 悦灵学者
    public static final String SMITH_SCHOLAR = "smith_scholar";   // 工匠学者
    public static final String NEGENTROPY    = "negentropy";      // 逆熵学者
    public static final String LAOBA         = "laoba";           // 联合研院大吃货 - 老八
    public static final String ZONGZI_KING   = "zongzi_king";     // 粽子大王
    public static final String WEEKLY_STORE  = "weekly_store";    // 联合研院采购
    public static final String ORIGIN_STONE  = "origin_stone";    // 多彩宝石商人
    public static final String ICE_DRAGON    = "ice_dragon";      // 极寒冰龙征服科科长
    public static final String SUMMER_2025   = "summer2025_store";// 暑期活动兑换
    public static final String MID_AUTUMN    = "midautumn_store"; // 天官 - 饕餮
    // 商人（StringUtils.VillagerNameMap / MyVillagerData.villagerNameMap 的 codeName 直接复用作 id）
}
```

分发改造：

```java
// VillagerEvents.Trade —— 改造后
String dialogId = NpcIdResolver.resolve(event.getTarget());   // 永不 null
if (dialogId == null) return;                                  // 不认识的实体：原逻辑
DialogManager.open(player, event.getTarget(), dialogId);
```

`DialogTrees` 从 `if (ENTRUSTMENT_NPC.equals(npcId))` 升级为注册表：

```java
public interface DialogTree {                     // 每个 NPC 一个实现
    Component onOpen(Player p, String dialogId);
    DialogPage buildPage(Player p, String dialogId, String sessionId, Component notice);
    DialogPage handle(Player p, String dialogId, String sessionId, String actionId);
}
private static final Map<String, DialogTree> TREES = new HashMap<>();   // 注册式，未知 id → buildUnknown
```

#### 实体侧 id 存储

| 实体 | 存储位置 |
|---|---|
| `VmdNpc` | 同步字段 + NBT 的 `dialogId` |
| 原版 `Villager` | `entity.getPersistentData().putString("vmd.npcId", id)`（Forge 通用 NBT，不改动村民本体） |

#### 兼容兜底（永久保留，3 行）

```java
static String resolve(Entity e) {
    String id = storedId(e);                 // 先读实体上存的 id
    return id != null ? id : NpcIds.byDisplayName(displayNameOf(e));  // 再按显示名回退
}
```

这样即使有村民没被补写 id（包括玩家用 `/summon villager` 自己摆的），行为与今天完全一致 —— **零回归风险**。

#### 存量原版村民怎么办

决策 2 已确认：存量 NPC 全是原版村民，**不需要任何数据迁移**。它们靠上面的「显示名 → id」兜底即可正常工作。若希望彻底摆脱名字依赖，可执行一次 `/vmd npc stamp`：扫描附近村民，按显示名匹配 `NpcIds` 并把 id 写进 `persistentData`（幂等，跑几次都行）。

#### 范围边界（重要）

本轮**只把「对话路由」id 化**。交易系统暂不动：`TradeList.tradeContent` 以 `displayName` 为键，`MyVillagerData` 内部同时存在 `codeName`（小写）与 `displayName` 两套键空间 —— 把它一并 id 化会扩散到交易 GUI/配方数据，属于独立重构，建议单独立项。

### 5.6 管理命令与配置

```
/vmd npc create <dialogId> <skinName> [slim]     # 视线落点生成，自动抄取在线玩家皮肤属性
/vmd npc remove                                  # 删除准星指向的 NPC
/vmd npc set <name|skin|skinfile|slim|scale|lookRadius> <value>
/vmd npc equip <hand|offhand|head|chest|legs|feet>  # 把主手物品装到准星 NPC 的槽位
/vmd npc list                                    # 列出本世界已加载 NPC（id/名字/坐标/皮肤来源）
/vmd npc stamp                                   # 给附近原版村民补写 vmd.npcId（幂等）
/vmd npc reload                                  # 按 config/vmd_npcs.json 幂等对齐
```

`config/vmd_npcs.json`（决策 7：跨存档复用）：

```json
[{ "id": "bank_clerk", "dialogId": "bank_clerk", "name": "联合银行职员",
   "dim": "minecraft:overworld", "x": 123.5, "y": 65, "z": -40.5, "yaw": 180,
   "skin": { "type": "file", "value": "bank_clerk", "slim": false },
   "lookRadius": 8, "scale": 1.0 }]
```

`/vmd npc reload` 幂等对齐：缺的补建、多的清理、位置/皮肤漂移的修正（主键 = `dialogId`）。配置进版本控制，资料片重置世界后一条命令还原。

---

## 6. 文件清单

### 新增（11）

| 文件 | 行数估 | 说明 |
|---|---|---|
| `entities/entities/npc/VmdNpc.java` | ~220 | 实体：同步字段 / NBT / 禁移动 / 免伤 / 可穿过 / 注视 / 装备槽 |
| `entities/entities/npc/VmdNpcData.java` | ~80 | 皮肤与外观参数的可变记录（命令与 JSON 共用） |
| `render/entity/VmdNpcRenderer.java` | ~130 | `LivingEntityRenderer` + slim/wide 切换 + ArmPose |
| `render/entity/NpcSkinCache.java` | ~190 | L0–L5 解析 + 纹理注册缓存 |
| `render/entity/VmdNpcArmorLayer.java` | ~40 | slim/wide 盔甲层委托 |
| `process/system/npc/NpcIds.java` | ~40 | id 常量表 + 显示名映射 |
| `process/system/npc/NpcIdResolver.java` | ~50 | 实体 → id（含兼容兜底） |
| `process/system/npc/NpcRegistry.java` | ~140 | JSON 加载 / 幂等对齐 / stamp |
| `process/system/npc/NpcCommand.java` | ~200 | `/vmd npc ...` 8 个子命令 |
| `common/impl/npc/VmdNpcLike.java` | ~15 | 统一交互接口 |
| `assets/vmd/textures/entity/npc/*.png` + `lang` 增量 | — | 内置皮肤与文案 |

### 修改（8）

| 文件 | 改动 |
|---|---|
| `build.gradle:251` | **删除** customnpcs 依赖行（留注释说明替代方案） |
| `events/core/VillagerEvents.java` | 去 `noppes` import；`instanceof EntityCustomNpc` → `NpcIdResolver.resolve()` + `DialogManager.open()` |
| `process/system/dialog/DialogTrees.java` | `if/equals` → `Map<String, DialogTree>` 注册表 |
| `process/system/dialog/DialogManager.java` | 签名不变，仅内部转调（预计 0–5 行） |
| `common/registry/ModEntityType.java` | 新增 `VMD_NPC` 注册 |
| `VMD.java` | `Attribute(...)` 加一行；起停挂 `NpcRegistry` |
| `common/registry/ClientModEventSubscriber.java` | 注册渲染器 |
| `commands/CommandHandler.java` | 注册 `/vmd npc` 子命令 |

---

## 7. 工作量拆解

| 工作包 | 内容 | 估时 |
|---|---|---|
| WP1 实体 | 注册、属性、禁移动/免伤/可穿过、NBT、同步字段 | 0.5 天 |
| WP2 渲染 | 双模型 + 渲染器注册 + 缩放 + 名字板 | 0.5 天 |
| WP3 皮肤 | L0–L5 管线、属性抄取、内置 PNG、缓存 | 1 天 |
| WP4 注视 | tick、回正、半径、取最近 | 0.25 天 |
| WP5 装备 | 装备槽、盔甲层（slim/wide）、手持层、ArmPose、equip 命令 | 0.5 天 |
| WP6 `npcId` 化 | `NpcIds` + `NpcIdResolver` + `DialogTrees` 注册表 + `VillagerEvents` | 0.75 天 |
| WP7 命令/配置 | 8 个子命令 + JSON + 幂等对齐 + stamp | 1 天 |
| WP8 依赖移除 | 删 gradle 行、清 mods、全量编译与启动验证 | 0.25 天 |
| WP9 测试 | 第 8 节矩阵（20 项） | 0.75–1 天 |
| **合计** | | **4.5–5 天** |

---

## 8. 验收标准（测试矩阵）

| # | 场景 | 期望 |
|---|---|---|
| 1 | 在线玩家名生成 NPC | 模型/皮肤正确 |
| 2 | 不在线玩家名生成（有属性） | L1 还原，皮肤正确 |
| 3 | 不存在的名字 | L4 默认皮肤，不崩、不刷屏 |
| 4 | 断网启动客户端 | 有缓存/内置 PNG 则正常；否则 L4 |
| 5 | `skinFile` 内置纹理 | 100% 显示 |
| 6 | 玩家撞击/推挤/水流/爆炸/活塞 | NPC 零位移 |
| 7 | 近战/远程/技能攻击 | 不掉血、不死亡、无掉落、不计入击杀统计 |
| 8 | 绕 NPC 走动（前/后/头顶） | 头部跟随，离开半径回正，**身体不转** |
| 9 | 多人同时靠近 | 注视最近者，不抖动 |
| 10 | 玩家穿过 NPC 身体 | 正常穿过（决策 4） |
| 11 | 名字板 | 常显，样式与 `Te.s(name, style)` 一致 |
| 12 | 给 NPC 穿全套盔甲 + 手持武器 | 渲染正确；宽版/细版模型盔甲贴合 |
| 13 | 重启服务器 | NPC、装备、皮肤、参数全保留（`HandItems`/`ArmorItems` 往返） |
| 14 | 右键带装备的 NPC | 不打开换装/交易界面，只走 VMD 对话 |
| 15 | 12 个对话分支逐一右键 | 与改造前完全一致 |
| 16 | 改 NPC 显示名后右键 | **对话仍正常**（id 生效，改名不断链） |
| 17 | 未 stamp 的老村民右键 | 靠名字兜底正常工作 |
| 18 | `/vmd npc stamp` 跑两次 | 幂等，`persistentData` 一致 |
| 19 | `/vmd npc reload` 跑三次 | 不产生重复实体 |
| 20 | 删除 customnpcs mod 后启动 | 无 `NoClassDefFoundError`，所有交互正常 |

---

## 9. 风险与对策

| 风险 | 等级 | 对策 |
|---|---|---|
| VMD 伤害系统绕过 `hurt()` 直接改血 | 中 | 矩阵 #7 实测；必要时在 `LivingHurtEvent`/`LivingAttackEvent` 对 `VmdNpc` 直接 cancel |
| 皮肤 CDN 不可达（国内网络） | 中 | 关键 NPC 全用 `skinFile`；L4 兜底 |
| 失去图形化编辑器 | 中 | `config/vmd_npcs.json` + `/vmd npc reload` + GM 手册 |
| 细版模型穿盔甲错位 | 低 | `VmdNpcArmorLayer` 双模型委派；实测矩阵 #12 |
| `ItemInHandLayer` 泛型/签名与我预期不符 | 低 | 开工第一步 javap 确认（附录 A-5） |
| `Mob` 装备槽 API 与我预期不符 | 低 | 同上 |
| 未来有人把 customnpcs 加回来 | 低 | `build.gradle` 删除处留注释 |
| **执行环境**：`pwsh` 当前不可用 | **高（阻塞）** | 恢复沙箱后端后再开工，否则无法编译/启动验证 |

**回滚**：纯增量改造。回滚 = 恢复 `build.gradle` 一行 + `VillagerEvents` 一段 + 把 customnpcs jar 放回 `mods/`；世界里误建的新 NPC 用 `/kill @e[type=vmd:npc]` 清掉。

---

## 10. 实现默认值（无需再确认，除非你要改）

| 项 | 默认值 |
|---|---|
| 实体 id | `vmd:npc`，`MobCategory.MISC`，`0.6×1.8` |
| 注视半径 | 8 格，可被 `/vmd npc set lookRadius` 覆盖 |
| 注视采样 | 每 2 tick（10 Hz），无目标回正到生成朝向 |
| 名字板 | 常显（决策 5），保留 `CustomStyle` 样式 |
| 碰撞 | 可穿过（决策 4）；`isPushable=false` |
| 装备掉落 | 全部 0，且不掉落（不会死亡） |
| 拾取物品 | 关闭 |
| 存档 | 实体自包含（NBT + 同步字段），不依赖外部文件 |
| 配置文件 | `config/vmd_npcs.json`（决策 7） |
| `skinUrl` | 本轮**未实现**（决策 1 主方案不需要图床）；如后续需要，在 `NpcSkinCache` 增加一级 `HttpTexture` 即可 |
| 命令权限 | 与现有 `/vmd` ops 子命令一致（2 级） |

---

## 11. 明确不建议做的事

- ❌ 不要裁剪 CustomNPCs 成 JiJ 内嵌：模型/皮肤/编辑器/脚本互相耦合，裁剪量 ≈ 重写，且 CC BY-NC 下分发裁剪版属演绎作品。
- ❌ 不要用 `EntityType.PLAYER` + FakePlayer 冒充玩家：需伪造 `PlayerInfo`/连接状态，单人/离线服会崩，且无存档持久性。
- ❌ 不要每 tick `setPos` 锁坐标实现「禁止移动」：与碰撞/区块/传送对抗会抖动，覆写 `travel/push` 才是正解。
- ❌ 不要自定义 S2C 包同步头朝向：原版 `ServerEntity` 已自动同步（附录 A-3）。
- ❌ 不要在服务端代码 import `net.minecraft.client.*`：皮肤/纹理全部封在 `render/entity/` 内，仅由客户端事件进入（项目约定不用 `@OnlyIn`）。
- ❌ 不要本轮顺手把交易系统也 id 化：`TradeList`/`MyVillagerData` 的双键空间改造会扩散到交易 GUI 与配方数据，应独立立项（见 5.5 范围边界）。

---

## 附录 A：核验证据

A-1. **依赖面**：`grep -r "customnpcs|noppes"` → 仅 `build.gradle:251` 与 `VillagerEvents.java:29`；`mods.toml` 无 customnpcs 依赖。

A-2. **CustomNPCs 反编译**：ForgeFlower 2.0.629.0 将 10.4 MB / 1242 class 还原为 1044 个 `.java`，确认其模型系统规模（`ModelDataShared`/`ModelPartData`/`MpmPartData`/`ImageDownloadAlt`/`RenderCustomNpc`）与禁移动实现（`EntityNPCInterface.travel(Vec3.ZERO)`、`isPushable()`、击退抗性）。

A-3. **原版渲染/同步 API**（`forge-1.20.1-47.3.0_mapped_official_1.20.1.jar`，反编译 + `javap`，均已实际跑过）：
- `PlayerModel(ModelPart, boolean)`；`ModelLayers.PLAYER` / `PLAYER_SLIM` / `PLAYER_INNER_ARMOR` / `PLAYER_OUTER_ARMOR` / `PLAYER_SLIM_*` 全部存在 → 无需新模型层、无需 AT。
- `LivingEntityRenderer<T extends LivingEntity, M extends EntityModel<T>>` 的 `protected M model` 非 final → 单渲染器切换 slim/wide 可行（`javap` 已确认字段）。
- `SkinManager` / `AbstractClientPlayer` / `HttpTexture` / `DefaultPlayerSkin` 的上述方法签名。
- `ServerEntity.tick():191-194` 在 `yHeadRot` 变化 ≥ 1/256 圈时广播 `ClientboundRotateHeadPacket`。
- 1.20.1 没有 `PlayerSkin` record（1.20.2+ 才有）。

A-4. **VMD 侧**：`DialogManager.open(Player, Entity, String)` 无 customnpcs 类型；`VillagerEvents` 12 个 `switch` 分支按名字分发；实体/属性/渲染器/命令注册入口已定位。

A-5. **待确认（开工第一步用 `javap` 复核，约 1 分钟）**：
- `Mob` 的装备槽 API：`setItemSlot(EquipmentSlot, ItemStack)` 与 `HandItems`/`ArmorItems` 的 NBT 往返是否如预期。
- `ItemInHandLayer<T extends LivingEntity, M extends EntityModel<T>>` 的泛型与构造签名（`PlayerItemInHandLayer` 已确认是 `AbstractClientPlayer` 专用，不可用）。
- `HumanoidArmorLayer` 构造：`(RenderLayerParent, A innerModel, A outerModel, ModelManager)`（源码反编译已见 PlayerRenderer 的实际调用形态）。
