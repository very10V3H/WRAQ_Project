# Aquamirae 依赖消减可行方案

> 状态：探索稿（2026-10-19），**尚未改动任何代码**
> 结论：**可行，而且成本远低于预期**。VMD 对 Aquamirae 的依赖面只有 **1 个生物（2 处生成点）+ 1 个方块黑名单 + 1 个物品模型**；移除 Aquamirae 及仅它所需的 Obscure API，可减少约 **17 MB** 运行体积，并去掉其 worldgen / mixin / 刷怪配置。

---

## 0. 结论摘要

| 项 | 结论 |
|---|---|
| 依赖面 | `build.gradle` 2 行 + 3 个 Java 文件 + 1 个模型 JSON |
| 真正用到的 Aquamirae 内容 | **只有 `aquamirae:tortured_soul`（被折磨的灵魂）** |
| 使用点 | ① 第七章普通刷怪 `TorturedSoulSpawnController`；② 炼魔塔第 3 层 `ManaTowerEachFloorMob` |
| 代码改动量 | 约 **10 行**（替换实体类），外加 1 行方块黑名单、1 个模型 parent |
| 工期 | **0.5–1 个工作日**（含实机验收） |
| 可节省 | Aquamirae 16.52 MB + Obscure API 0.47 MB ≈ **17 MB**，以及其冰迷宫 worldgen、刷怪、mixins |
| 数据迁移 | **VMD 侧无需迁移**（击杀数 / 前缀 / 掉落 / CSV / AFK 全部按中文名工作） |
| 最大风险 | 替换后的生物是**飞行单位**，炼魔塔第 3 层需要防止它飞出平台（可加 5 行 leash，或该层改用地面生物） |
| 授权风险 | Aquamirae 为 **All Rights Reserved**，**不能**把它的模型 / 贴图 / 音效拷进 VMD |

---

## 1. 代码事实（已核验）

### 1.1 全部引用点

全仓库（含 `src/main/resources`、`docs`、`run`）检索 `aquamirae|obscure`，源码侧只有以下 4 个文件：

| 位置 | 引用 | 用途 |
|---|---|---|
| `build.gradle:211` | `implementation fg.deobf("curse.maven:ob-aquamirae-536254:4616373")` | 编译 / 运行依赖 |
| `build.gradle:212` | `implementation fg.deobf("curse.maven:obscure-api-638417:4616364")` | Aquamirae 的强制前置 |
| `events/mob/chapter7/TorturedSoulSpawnController.java:3-4,62` | `TorturedSoul` + `AquamiraeEntities.TORTURED_SOUL` | 第七章普通刷怪“被折磨的灵魂” |
| `events/mob/instance/instances/tower/ManaTowerEachFloorMob.java:7-8,92` | `TorturedSoul` + `AquamiraeEntities.TORTURED_SOUL` | 炼魔塔第 3 层“元素魔物” |
| `events/core/BlockEvent.java:4,233` | `AquamiraeBlocks.LUMINESCENT_BUBBLE` | 非创造模式右键黑名单中的“发光气泡” |
| `assets/vmd/models/item/super_cold_dragon_lottery.json:2` | `"parent": "aquamirae:block/frozen_chest"` | “极寒冰龙战利品”的物品模型 |

补充：

- `TorturedSoulSpawnController` 在 `events/mob/MobSpawn.java:191` 注册进主世界刷怪列表；类名 / 刷怪点 / 掉落 / 击杀键均**不需要改**。
- 前缀“解脱者”在 `commands/changeable/PrefixCommand.java:305` 按 `TorturedSoulSpawnController.mobName`（即中文名“被折磨的灵魂”）绑定，不依赖实体类。
- 炼魔塔第 3 层在 `process/func/damage/Damage.java:645` 按 `FLOOR_3_MOB_NAME`（“元素魔物”）配置 70% 元素强度门槛（“蔑视”机制：元素强度不足时无法造成伤害），不依赖实体类。
- `data/vmd/balance/mob_attributes.csv:50` 的键是中文名“被折磨的灵魂”，`className` 列只是二级索引；**不重命名 controller 类就不用改 CSV**。
- 除了 `docs/neoforge_migration_assessment.md` 中的迁移评估表，文档与数据包没有其他引用。

> 事实修正：需求里说“主要是使用 Aquamirae 的生物”，实际**只用了 1 个生物**，而且只在这 2 个生成点。依赖面比预期小得多。

### 1.2 依赖与体积

| 项 | 事实 |
|---|---|
| Aquamirae jar | 16.52 MB / 741 entries，`modId=aquamirae`，license = `Obscuria Lithium (c) 2023, All Rights Reserved.` |
| 前置 | `mods.toml` 声明 `obscure_api` **mandatory=true, versionRange=[15,)** |
| Obscure API jar | 0.47 MB / 241 entries，同样 ARR |
| 依赖传播 | 扫描 Gradle 缓存中 63 个 CurseMaven jar：**只有 Aquamirae 声明 `obscure_api`**；**没有任何 mod 声明依赖 `aquamirae`** |
| 实际加载版本 | `run/logs/latest.log`：`aquamirae 6.API15`、`obscure_api 15` |
| 结论 | 删 Aquamirae 后可**连带删除 Obscure API**（最终发布前再用完整 `mods/` 目录复核一次即可） |

### 1.3 `TorturedSoul` 实际是什么（javap 核验）

| 项 | 值 |
|---|---|
| 实体 id | `aquamirae:tortured_soul` |
| 父类 | `net.minecraft.world.entity.monster.Monster`（同时实现 Obscure API 的 `IAnimated`） |
| 碰撞箱 | `0.6 × 1.8`，`MobCategory.MONSTER`，trackingRange 64 / updateInterval 3 |
| 刷怪放置 | `SpawnPlacements.Type.ON_GROUND`（**地面单位**，不是飞行） |
| MobType | `ILLAGER`（不是亡灵） |
| AI | `FloatGoal`、近战 / 目标选择、`WaterAvoidingRandomStrollGoal`、`LookAtPlayerGoal`、`RandomLookAroundGoal`；目标包括玩家、`AbstractIllager`、`AbstractVillager` |
| 远程 | 无 `RangedAttackMob` / 无自定义弹射物引用 |

### 1.4 为什么替换实体是安全的

VMD 的怪物系统**全程按“实体的自定义名”工作**，不依赖实体类：

- `MobSpawn.getMobOriginName(mob)` 取自定义名第一个空格之后的部分（`Lv.220 被折磨的灵魂` → `被折磨的灵魂`）。
- 属性表 `MobBaseAttributes.*`、掉落表 `MobSpawn.dropList`、AFK 分类、击杀数、前缀、CSV、`Damage.scornValueMap` 全部以这个中文名为键。
- 全仓库没有任何 `instanceof TorturedSoul`、`getClass() == ...` 或按 Aquamirae 类型分支的逻辑。
- 死亡掉落被 `events/core/VariousEvents.java:483` **全局取消**（`LivingDropsEvent` 一律 cancel），因此换任何生物都不会引入它自己的战利品表。
- 属性写入只用到 `MAX_HEALTH / MOVEMENT_SPEED / KNOCKBACK_RESISTANCE / ARMOR / ARMOR_TOUGHNESS`，任何 `Monster` 都有。

因此**替换实体类不会影响数值、掉落、击杀统计、前缀、AFK、元素**。

---

## 2. 只删 mod、不改代码会发生什么

`mods.toml` 没有声明 Aquamirae 依赖，所以 Forge **不会**在启动时给出清晰报错；故障是延迟且分散的：

| 触发点 | 现象 |
|---|---|
| 非创造玩家右键任意方块 | `BlockEvent.disallowToRightClick` 首次执行到 `AquamiraeBlocks.LUMINESCENT_BUBBLE.get()` → `NoClassDefFoundError` |
| 第七章刷怪区首次尝试生成 | `new TorturedSoul(...)` → `NoClassDefFoundError` |
| 炼魔塔首次进入第 3 层 | 同上 |
| 打开“极寒冰龙战利品” | 模型 parent 缺失 → 紫黑格 + 资源包报错（不崩） |

**结论：必须先改代码，不能只删 jar。**

---

## 3. 方案对比

| | A1. 换成 Born in Chaos `RestlessSpirit`（推荐） | A2. 换成原版 `Vex` | A3. 自研 `vmd:soul` | B. 软依赖（Aquamirae 可选） | C. 保留现状 |
|---|---|---|---|---|---|
| 新增依赖 | **0**（Born in Chaos 已是硬依赖） | 0 | 0 | 0 | — |
| 新增代码 | ~10 行 | ~10 行 | ~250 行 + 贴图 | ~80 行 + 回退 | 0 |
| 视觉 | 幽灵（飞行，GeckoLib） | 恼鬼（飞行，穿墙） | 完全可控（原版几何 + 自绘贴图） | 装了 Aquamirae 则原样，否则走回退 | 原样 |
| 行为 | 飞行近战 / 索敌 | 飞行近战，穿墙 | 自定 | 双路径 | 地面近战 |
| 工期 | 0.5–1 天 | 0.25–0.5 天 | 1–1.5 天 | 0.5–1 天 | 0 |
| 风险 | 塔内可能飞出平台（可加 leash） | 穿墙 / 飞出平台更明显 | 资产工作量 | 两条路径都要测 | 17 MB + worldgen 继续保留 |
| 能否删 Aquamirae | ✅ | ✅ | ✅ | ❌（只是不再硬依赖） | ❌ |

### 为什么推荐 A1

1. **零新增依赖**：VMD 已经大量直接使用 Born in Chaos（第七章的 `BoneImpEntity`、第二章的 `SearedSpiritEntity` / `FirelightEntity`、月山三 Boss 等，约 40 处 import/引用）。
2. **已有飞行单位先例**：`SkyVexSpawnController`（第二章）直接用原版 `Vex`——同样是飞行、穿墙单位——走的就是同一套 `MobSpawnController` 边界传送逻辑；`SearedSpiritSpawnController` 则证明 Born in Chaos 的“魂”类生物可以直接套 `MobSpawn` 的属性 / 掉落 / 命名链路（它是地面移动的幽灵模型）。
3. **行为干净**：`RestlessSpirit` 是 `Monster`，`FlyingMoveControl` + `FlyingPathNavigation`，`0.8 × 1.5`，无弹射物；`MobType.UNDEFINED`（与原来的 `ILLAGER` 都不是亡灵，不影响 VMD 的 Boss 目标过滤）。
4. **免疫项无害**：只免疫摔落 / 溺水 / 凋零 / 凋零头颅 / 投掷药水 / 区域效果云，不免疫 VMD 的元素伤害。
5. **死亡掉落已被全局取消**，不会带出 Born in Chaos 的战利品。

### 为什么不用 Goety 的 `Wraith` / `Reaper`

它们虽然是“魂”，但都继承 `Summoned` / `Owned`，带**主人 UUID、有限寿命、传送 AI、魔法火焰**等召唤物机制；而 VMD 的 `VariousEvents.fixProjectileErrorDamageToPlayer` 会拦截所有非 VMD 弹射物，可能让它们的远程行为失效。行为面太大，不值得为省一个依赖引入。

### 为什么 A2（原版 Vex）只作为快速回退

Vex 同样飞行、穿墙（`noPhysics`），碰撞箱小、外观通用；但**零跨 mod 行为**，改动只有两行，适合“先删依赖、以后再美化”。

---

## 4. 推荐实施方案（A1）

### 4.1 改动清单

| 文件 | 改动 |
|---|---|
| `build.gradle:211` | 删除 Aquamirae 依赖（留注释） |
| `build.gradle:212` | 删除 Obscure API 依赖（留注释，说明它只被 Aquamirae 使用） |
| `TorturedSoulSpawnController.java` | 换 import + 构造 `RestlessSpiritEntity`；**类名、`mobName`、刷怪点、掉落、`killCountDataKey` 全部不动** |
| `ManaTowerEachFloorMob.java` | 同上；`FLOOR_3_MOB_NAME = "元素魔物"` 不动 |
| `BlockEvent.java:4,233` | 删除 import 与黑名单里的 `LUMINESCENT_BUBBLE` 一行 |
| `super_cold_dragon_lottery.json` | 改 parent（临时可用 `minecraft:block/packed_ice`，正式建议自绘图标） |
| `ManaTowerInstance.java`（可选加固） | 给第 3 层加 5 行位置 leash，防飞行单位飞出平台 |
| `mods.toml` | **不需要改**（从未声明 Aquamirae） |
| `mob_attributes.csv` | **不需要改**（键是中文名；controller 类名不变） |

### 4.2 实体替换

`TorturedSoulSpawnController.java`：

```java
- import com.obscuria.aquamirae.common.entities.TorturedSoul;
- import com.obscuria.aquamirae.registry.AquamiraeEntities;
+ import net.mcreator.borninchaosv.entity.RestlessSpiritEntity;
+ import net.mcreator.borninchaosv.init.BornInChaosV1ModEntities;

- TorturedSoul boneImp = new TorturedSoul(AquamiraeEntities.TORTURED_SOUL.get(), this.level);
+ RestlessSpiritEntity boneImp =
+         new RestlessSpiritEntity(BornInChaosV1ModEntities.RESTLESS_SPIRIT.get(), this.level);
```

`ManaTowerEachFloorMob.java`：

```java
- import com.obscuria.aquamirae.common.entities.TorturedSoul;
- import com.obscuria.aquamirae.registry.AquamiraeEntities;
+ import net.mcreator.borninchaosv.entity.RestlessSpiritEntity;
+ import net.mcreator.borninchaosv.init.BornInChaosV1ModEntities;

- TorturedSoul mob = new TorturedSoul(AquamiraeEntities.TORTURED_SOUL.get(), level);
+ RestlessSpiritEntity mob =
+         new RestlessSpiritEntity(BornInChaosV1ModEntities.RESTLESS_SPIRIT.get(), level);
```

其余代码（`setMobBaseAttributes`、`provideElementOnFloor3Mob`、`getMobOriginName`）完全不用改。

### 4.3 炼魔塔第 3 层的可选加固（推荐做）

飞行单位有飞出平台、导致 `allDead` 永远不成立的风险。在 `ManaTowerInstance.tickModule` 的怪物 tick 段加：

```java
mobList.stream()
        .filter(mob -> ManaTowerEachFloorMob.FLOOR_3_MOB_NAME
                .equals(MobSpawn.getMobOriginName(mob)) && mob.isAlive())
        .forEach(mob -> {
            double dist = mob.position().distanceTo(ManaTowerInstance.FLOOR_3_PLATFORM_CENTER_POS);
            if (dist > 32 || mob.getY() < ManaTowerInstance.FLOOR_3_PLATFORM_CENTER_POS.y - 8) {
                mob.moveTo(ManaTowerInstance.FLOOR_3_PLATFORM_CENTER_POS.add(0, 2, 0));
            }
        });
```

如果不想改塔逻辑，也可以**只在第 3 层保留地面生物**（如 `WitherSkeleton`），但外观与“魂”主题不一致。

### 4.4 物品模型

`super_cold_dragon_lottery.json` 必须改，否则紫黑格。注意**不能**用 `minecraft:block/chest` 兜底——原版 `block/chest.json` 只有 particle 定义、没有 elements，作为物品模型会**完全不可见**。零美术的可用兜底是原版冰块方块模型：

```json
{
  "parent": "minecraft:block/packed_ice"
}
```

三个选项：

| 方案 | 成本 | 效果 |
|---|---|---|
| 临时：`minecraft:block/packed_ice` / `blue_ice` | 1 分钟 | 一个冰块（有 3D 体积，可见），失去“冰封宝箱”感 |
| 正式：自绘 16×16 图标 + `minecraft:item/generated` | 0.5 小时美术 | 标准物品图标，最省事 |
| 讲究：自建 `vmd:block/frozen_chest` 模型 + 自绘贴图 | 0.5 天 | 保留冰封宝箱观感（elements 与贴图都要自己写） |

> 注意：**不能**直接复制 Aquamirae 的 `frozen_chest.png`、`models/block/custom/frozen_chest.json` 或其 elements（ARR）。要保留这个外观只能重画 / 重写。

### 4.5 `build.gradle`

```groovy
// 已移除 ob-aquamirae：VMD 只用到其 TorturedSoul（被折磨的灵魂），
// 已替换为 born_in_chaos_v1 的 Restless Spirit。
// obscure-api 仅 Aquamirae 需要，随之一并移除。
// implementation fg.deobf("curse.maven:ob-aquamirae-536254:4616373")
// implementation fg.deobf("curse.maven:obscure-api-638417:4616364")
```

---

## 5. 备选方案

### 5.1 A2：原版 `Vex`（最快回退）

把上面两处 `new RestlessSpiritEntity(...)` 换成 `new Vex(EntityType.VEX, level)` 即可，零依赖、零资产。代价是外观变成恼鬼、穿墙更明显；塔内同样建议加 4.3 的 leash。

### 5.2 A3：自研 `vmd:soul`（外观最优）

若不能接受“用别的 mod 的怪”，可做一个**只属于 VMD** 的幽魂：

- `VmdSoul extends Vex`（复用原版飞行 AI），或 `extends Monster` + 抄 `Vex` 的飞行控制；
- 注册 `vmd:soul`（`ModEntityType` + `VMD.Attribute`）；
- 渲染：复用 `ModelLayers.VEX` 的 `VexModel`，只换一张**自绘**贴图（64×64，按 Vex UV 布局）；
- 音效用原版 Vex / 灵魂沙；
- 成本约 1–1.5 天（实体 0.5 + 贴图 0.25–0.5 + 验收 0.5）。

这条路线没有授权问题，也不需要任何新依赖，适合后续想彻底掌控外观时做。

### 5.3 B：软依赖（保留 Aquamirae 外观，但不硬依赖）

只在“希望服务器装了 Aquamirae 时保持原样、没装时自动回退”时采用。核心是用注册名解析，**不 import 它的类**：

```java
public final class AquamiraeCompat {
    private static final ResourceLocation TORTURED_SOUL =
            new ResourceLocation("aquamirae", "tortured_soul");

    @Nullable
    public static Mob createTorturedSoul(Level level) {
        EntityType<?> type = ForgeRegistries.ENTITY_TYPES.getValue(TORTURED_SOUL);
        if (type == null) return null;
        Entity entity = type.create(level);
        return entity instanceof Mob mob ? mob : null;
    }
}
```

刷怪处 `AquamiraeCompat.createTorturedSoul(level)`，为 `null` 时回退 `RestlessSpirit` / `Vex`。方块黑名单同理用 `ForgeRegistries.BLOCKS.getValue(new ResourceLocation("aquamirae","luminescent_bubble"))`，为 `null` 时跳过。模型必须无条件换成 VMD 自己的（JSON 不能条件回退）。

**局限**：这只是“不硬依赖”，服务器仍要保留 Aquamirae 才能看到原外观，**不减少体积**；而且装 / 不装两条路径都要测，反而不如 A1 干净。

### 5.4 C：保留现状

只适合“Aquamirae 的其他内容（冰迷宫、船、Boss）仍想留给玩家”的情况。代价是 17 MB、worldgen、mixins 和版本锁定。

---

## 6. 数据与存档影响

| 项 | 影响 |
|---|---|
| VMD 玩家数据 | **无**。击杀数键 `TorturedSoul`、前缀“解脱者”、CSV 中文名、AFK 分类、掉落表键全部不变 |
| 现有 Aquamirae 实体 | 读档时因实体类型未注册被丢弃；VMD 刷怪器会在原刷新点重新生成新生物 |
| 现有 Aquamirae 方块 / 结构 | 冰迷宫、船、前哨等结构中的 Aquamirae 方块会变成空气，留下空腔；结构箱子若引用 Aquamirae 战利品表会变空 |
| 现有 Aquamirae 物品 | 玩家背包 / 箱子里的 Aquamirae 物品会失效消失；**VMD 没有任何配方 / 商店 / 任务使用它们** |
| 世界生成 | 冰迷宫等结构不再生成；其 biome modifier / 刷怪不再生效 |
| 回滚 | 恢复 `build.gradle` 两行 + 3 个 Java 文件 + 1 个 JSON + jar 即可；玩家数据无需回滚 |

建议上线前**备份世界**；如果不想让旧结构留下空腔，可在删 mod 前先把相关区域清理或放弃。

---

## 7. 验收矩阵

| # | 场景 | 期望 |
|---|---|---|
| 1 | `gradlew compileJava` | 通过，无 Aquamirae / Obscure 引用 |
| 2 | 从 mods 目录删除 Aquamirae + Obscure API 后启动客户端 | 无 `NoClassDefFoundError`，无 missing registry 崩溃 |
| 3 | 第七章刷怪区 | 13 个刷新点正常生成“Lv.X 被折磨的灵魂”，属性 / 名称样式正确 |
| 4 | 击杀该怪 | 掉落 `VD_SOUL` / 银币 / 宝石碎片概率不变；击杀数 +1；前缀“解脱者”进度正常；AFK 分类正常 |
| 5 | 炼魔塔第 3 层 | 4 只“元素魔物”生成；每日元素正确施加；70% 元素强度门槛（“蔑视”）生效；全灭后传送门正常开启 |
| 6 | 炼魔塔第 3 层长时间战斗 | 怪物不会飞出平台 / 卡在塔外（若做了 4.3 则验证 leash） |
| 7 | 非创造模式右键各种方块 | 无崩溃；原黑名单行为不变 |
| 8 | 查看“极寒冰龙战利品” | 图标正常（非紫黑格） |
| 9 | 携带旧存档启动 | 旧 Aquamirae 方块 / 实体被安全丢弃，无崩溃 |
| 10 | 专用服务器 + 2 名玩家 | 刷怪 / 伤害 / 击杀统计 / 元素均正常 |

---

## 8. 风险与对策

| 风险 | 等级 | 对策 |
|---|---|---|
| 飞行单位在炼魔塔飞出平台，导致挑战卡住 | 中 | 4.3 的 leash；或第 3 层改用地面生物 |
| 外观从“被折磨的灵魂”变成 Born in Chaos 幽灵 | 中 | 可接受则 A1；不接受则 A3 自研 |
| Aquamirae 的 ARR 授权 | 高（法律） | 不复制其贴图 / 模型 / 音效；只用 Born in Chaos、原版或自绘资源 |
| `RestlessSpirit` 的 MCreator AI 与预期不符（攻击距离 / 索敌） | 低 | 实机矩阵 #3/#4；不行就回退 Vex |
| 删除 Obscure API 影响其他 mod | 低 | 当前 63 个 CurseMaven jar 中只有 Aquamirae 依赖它；发布前用完整 `mods/` 再扫一次 |
| Born in Chaos 未来也被移除 | 低 | VMD 已有约 40 处直接 import，它本来就是硬依赖；若真要移除，属于另一个独立项目 |
| 旧存档 Aquamirae 结构留空腔 | 低 | 备份；或接受旧区域地形变化 |

**回滚**：纯增量替换，回滚 = 恢复 2 行 gradle + 3 个 Java 文件 + 1 个 JSON，把 jar 放回 `mods/`。

---

## 9. 明确不建议做的事

- ❌ **`compileOnly` + 继续直接 import `TorturedSoul`**：编译能过，运行到刷怪点仍然 `NoClassDefFoundError`。
- ❌ **反射 / 字符串加载 Aquamirae 类**：比注册名查表更脆，且没有收益。
- ❌ **JiJ 内嵌 Aquamirae**：ARR 授权、16.5 MB、mixins，且等于没消减。
- ❌ **复制 Aquamirae 的模型 / 贴图 / 音效**：ARR，禁止。
- ❌ **保留 `AquamiraeBlocks.LUMINESCENT_BUBBLE` 行并只靠 `ModList.isLoaded` 包一层**：类引用仍在，未装 mod 时照样炸。
- ❌ **为了“省事”同时重命名 controller / 怪物中文名**：会牵动 CSV、前缀、击杀数 NBT、`material_sources.json`，收益为零。

---

## 附录 A：证据

- A-1 全仓库检索：`aquamirae` 命中 `build.gradle:211`、`BlockEvent.java:4/233`、`TorturedSoulSpawnController.java:3/4/62`、`ManaTowerEachFloorMob.java:7/8/92`、`super_cold_dragon_lottery.json:2`；`obscure` 只随 Aquamirae 的 3 处 import 出现（无 VMD 代码使用）。
- A-2 jar 元数据：Aquamirae `mods.toml` → `modId="aquamirae"`、`license="Obscuria Lithium (c) 2023, All Rights Reserved."`、依赖 `obscure_api [15,)` mandatory；Obscure API `modId="obscure_api"`、0.47 MB。
- A-3 依赖扫描：Gradle 缓存 63 个 CurseMaven jar 的 `mods.toml` 中，只有 Aquamirae 的 `obscure_api` 依赖；无任何 jar 依赖 `aquamirae`。
- A-4 `javap`：`TorturedSoul extends Monster implements IAnimated`；`AquamiraeEntities` 注册 `tortured_soul`，`sized(0.6, 1.8)`、`MobCategory.MONSTER`、`ON_GROUND`；`MobType = ILLAGER`；无 `RangedAttackMob`。
- A-5 `javap`：`RestlessSpiritEntity extends Monster implements GeoEntity`；构造含 `FlyingMoveControl` / `FlyingPathNavigation`；`sized(0.8, 1.5)`；`MobType = UNDEFINED`；`isInvulnerableTo` 仅免疫 `FALL / DROWN / WITHER / WITHER_SKULL` + 投掷药水 + 区域效果云；无弹射物引用。
- A-6 运行时日志：`run/logs/latest.log` 加载 `aquamirae 6.API15` 与 `obscure_api 15`；Puzzles Lib 的 mod 列表中仅 Aquamirae 依赖 Obscure API。
- A-7 全局掉落取消：`events/core/VariousEvents.java:483` `LivingDropsEvent` 一律 `setCanceled(true)`，因此替换生物不会引入其原版战利品。
- A-8 名称键链路：`MobSpawn.getMobOriginName`（按自定义名第一空格切分）、`MobSpawn.killCountAdd`、`PrefixCommand:305`、`Damage.java:645`、`mob_attributes.csv:50` 全部按中文名匹配。
