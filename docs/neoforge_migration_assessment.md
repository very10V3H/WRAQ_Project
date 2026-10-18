# VMD 迁移 NeoForge 代价评估

> 评估日期：2026-10-18　｜　评估对象：`wraq3-master` 分支（VMD 3.0.0，Forge 1.20.1-47.3.0）
> 方法：全仓静态统计（1,734 个 Java 文件 / 158,705 行）+ NeoForge 21.1 源码核实 + Modrinth/CurseForge 依赖可用性核查

---

## 一、结论（先看这段）

1. **"迁到 NeoForge" 有两个完全不同的含义，代价差 10 倍：**
   - **NeoForge 1.20.1（47.1.x）**：只换包名，10–15 人日。但该分支是 NeoForge 分家后的第一个版本、**早已停更**，等于从一个停更平台搬到一个更早停更的平台 —— **纯负收益，不要做**。
   - **NeoForge 1.21.1（21.1.x，LTS）**：真正解决问题，代价主体**不是** Forge→NeoForge，而是 **Minecraft 1.20.1→1.21 的原版 API 断层**（尤其物品 NBT → DataComponents）。
2. **总代价：90–150 人日（约 18–30 人周）。**
   - 1 人独立完成 ≈ 4.5–7.5 个月；2 人并行 ≈ 2.5–4.5 个月；3 人 ≈ 2–3 个月（边际收益递减，且需要一个人专门做回归）。
3. **依赖不是拦路虎。** 23 个有代码耦合的第三方模组中，19 个已有 1.21.1 官方版（含 Curios / GeckoLib / Patchouli / Citadel / Create / Xaero / Sophisticated / Goety / Born in Chaos 等全部硬依赖）。只有 4 个没有官方 1.21.1 版本，**合计只影响 14 个文件**，且都是"查注册表拿一个实体/物品"级别的耦合，改造成本以天计：Ice and Fire(8)、Collector's Reap(4)、Seasonals(1)、Loot Beams(1)。
4. **最大风险不是编译，是物品数据与存档。** 全部装备的随机属性、强化等级、锻造品质、归属者都写在 `ItemStack` 的 `vmd` NBT 里（76 个文件 / 188 处读写），1.21 取消了物品 NBT；此外玩家数据 `player.getPersistentData()` 在 NeoForge 21.1 **仍然存在**（已核对 NeoForge 21.1 源码 `IEntityExtension#getPersistentData`），287 处可以不动 —— 这是本评估里最重要的一个减负项。
5. **建议路线：不要现在动大手术。** 先用 2–4 周在 1.20.1 上做"迁移友好化重构"（不阻塞内容更新、立刻提升可维护性），再用 1 周做 1.21.1 PoC 验证，最后在 v3 内容冻结窗口里做 6–8 周的机械迁移。

---

## 二、基线数据（全部实测）

| 维度 | 数值 |
|---|---|
| Java 文件 / 行数 | 1,734 / 158,705（`src/test` 仅 1 个文件 63 行 → **无自动化测试网**） |
| 类 / 方法 / 字段（CodeGraph） | 2,381 / 10,456 / 4,360 |
| 注册项 `.register(...)` | **2,180** 处，覆盖 12 个 registry，58 个 `DeferredRegister` |
| `RegistryObject` 使用 | **2,311** 处 / 58 文件 |
| 数据包类 `*Packet.java` | **281** 个（C2S 96 / S2C 179），注册点集中在 1 个 `SimpleChannel` |
| `import net.minecraftforge.*` | **528** 个文件（`NetworkEvent` 280、`ForgeRegistries` 62、`TickEvent` 42、`IGuiOverlay` 16…） |
| 事件 | `@SubscribeEvent` 126 处、`@Mod.EventBusSubscriber` 60 个类 |
| `TickEvent` | 52 处（`LevelTickEvent` 27 / `PlayerTickEvent` 16 / `ServerTickEvent` 6 / `ClientTickEvent` 3） |
| 物品 NBT | `getOrCreateTagElement` **188 处 / 76 文件**；`CompoundTag` 204 文件 / ~1,004 处 |
| 玩家持久数据 | `getPersistentData()` **287 处**（可原样保留，见 §1.4） |
| 覆写签名变更 | `appendHoverText` **382 处**（`Level` → `Item.TooltipContext`） |
| 客户端 | 32 个 `Screen`、15 个 `Menu`、16 个 HUD（`IGuiOverlay`）、7 处 `RenderLevelStageEvent` |
| 动画 | GeckoLib 138 处 / 49 文件；PlayerAnimator 19 处 / 4 文件 |
| 内容体量 | 装备类 248（剑40/弓40/法杖24/饰品94/护甲45/其他5）、`Item` 子类 114、刷怪控制器 69、副本 24 |
| 资源 | 2,514 模型 JSON、1,928 贴图、85 粒子、67 Patchouli、2,683 语言键 → **迁移零成本** |
| 数据包 JSON | 极少（curios 13、curios_slot 13、minecraft 覆写 6、farmersdelight 5）→ **配方全在 Java 里，数据迁移成本极低** |
| 数值外置 | `data/vmd/balance/*.csv`（武器/护甲/饰品/怪物属性表）→ **数值不需要改代码** |
| 第三方耦合 | **137 个文件（7.9%）** import 了第三方模组 |

改动面估算：约 **40–50% 的文件至少需要改一行**，但其中 **80% 是机械替换**（包名、类型名、签名）；真正需要人动脑的约 **350 个文件**（281 个数据包 + 76 个 NBT + 16 个 HUD + 14 个失效依赖）。

---

## 三、平台现状（2026-10 实测）

| 事实 | 数据 |
|---|---|
| NeoForge + 1.21.1 的 Modrinth 项目数 | **23,708** |
| NeoForge + 26.1（最新年号版本）的项目数 | **6,258** |
| 当前 MC 版本号体系 | 已改为年号制：1.21.11 → **26.1 / 26.1.1 / 26.2 / 26.3** |
| Forge 1.20.1 | **仍在维护**（47.4.x 线，最新可见 47.4.22）→ 1.20.1 是 Forge 事实上的 LTS |
| NeoForge 1.20.1 | 分家首版，早已停更，仅为历史存档 |

**结论**：用户的直觉（"Forge 已经几乎停止更新"）对**新版本**成立，对 **1.20.1** 不完全成立 —— 1.20.1 的 Forge 分支还在发维护版。真正的风险是"**新模组不再为 1.20.1 Forge 出包**"，这对自给自足的服主 mod（VMD 自己实现战斗/经济/技能）影响**远小于**对通用整合包的影响。

---

## 四、三条路线对比

| 路线 | 工作量 | 收益 | 风险 |
|---|---|---|---|
| **A. 留在 Forge 1.20.1** | 0 | 47.4.x 仍在维护；现有 30+ 系统零风险 | 生态停止增长；客户端整合包成员逐渐断供；以后再迁代价更高（跨度更大） |
| **B. NeoForge 1.20.1（47.1.x）** | 10–15 人日 | 无 | 平台停更更早；等于白搬一次 |
| **C. NeoForge 1.21.1 LTS** | **90–150 人日** | 真正解决；生态最大（23.7k 项目）；LTS 周期长；未来跳 26.x 已经吃掉 DataComponents 这块最大的债 | 存档/物品迁移、全量回归、迁移期停更 |

> 补充：26.x 现在不做。等 1.21.1 LTS 上跑稳、且 26.x 生态成熟（现在只有 1.21.1 的 1/4）后再跳一次，届时 NBT 债已还清，跳跃成本会显著低于现在这一次。

---

## 五、工作量拆解（NeoForge 1.21.1）

| # | 工作块 | 实际触点 | 人日 | 风险 |
|---|---|---|---|---|
| 1 | 构建系统与元数据 | ForgeGradle→ModDevGradle、`mods.toml`→`neoforge.mods.toml`、AT 改官方名（当前 `f_22308_` SRG 名会被 NeoForge 忽略）、`pack.mcmeta`、jarJar | 1–2 | 低 |
| 2 | 包名与注册 API | 528 文件 `net.minecraftforge.*`→`net.neoforged.*`；`ForgeRegistries`→`BuiltInRegistries`（62 文件/145 处）；`RegistryObject`→`DeferredHolder`（58 文件/2,311 处）；`FMLJavaModLoadingContext` 弃用 | 4–6 | 低（脚本化） |
| 3 | 事件系统 | `TickEvent` 52 处拆成 `*.Pre/*.Post`（`LevelTickEvent` 27 处需逐个对位）；`@Mod.EventBusSubscriber`→`@EventBusSubscriber` 60 个类；MOD/GAME 总线归属重新核对 | 3–5 | 中 |
| 4 | 网络 | **281 个包**全部改 `CustomPacketPayload` + `StreamCodec` + `PayloadRegistrar`；`channelFor` 反射式分发（class→channel 映射）在 1.21 不可用，需改由每个包自带 handler | 6–10 | 中 |
| 5 | **物品数据 NBT→DataComponents** | 188 处 / 76 文件；382 处 `appendHoverText` 签名；248 个装备类 + 114 个物品类的读写路径 | **15–25** | **高** |
| 6 | 属性/状态效果/护甲 Holder 化 | `AttributeModifier` UUID→`ResourceLocation`（31 文件）；`MobEffectInstance`→`Holder<MobEffect>`（49 文件）；`ArmorItem(Holder<ArmorMaterial>)`（集中在基类）；`RangedAttribute` + AT | 5–8 | 中 |
| 7 | 客户端 GUI/HUD/渲染 | 16 个 HUD：`IGuiOverlay`→`LayeredDraw.Layer`（NeoForge 20.5+ 分层 GUI）；32 Screen / 15 Menu 的 `TooltipContext` 相关签名；85 个自定义粒子 | 6–10 | 中 |
| 8 | 动画库 | GeckoLib 4.4.4→4.9.3（49 文件，个别 `getModelResource` 等签名变化）；PlayerAnimator 1.0.2→2.0.4（4 文件） | 3–5 | 中 |
| 9 | 依赖替换 / 降耦合 | Ice and Fire(8文件)、Collector's Reap(4)、Seasonals(1)、Loot Beams(1) 抽成适配层或直接本地替代 | 5–10 | 高（外部） |
| 10 | 存档与数据迁移 | 物品 NBT→组件的一次性迁移器（老存档里所有装备）；维度/战利品少量；玩家数据可不动 | 8–12 | 高 |
| 11 | 编译收敛与合并冲突 | 1,734 文件滚雪球式报错；与 v3 分支 477 个已改动文件的合并 | 10–15 | 中 |
| 12 | **全系统回归** | 69 刷怪控制器、24 副本、30+ 子系统、战斗数值链路，**0 单元测试**，全人工 | **25–40** | **高** |
| | **合计** | | **91–148 人日** | |

**折算**：90–150 人日 ≈ **18–30 人周** ≈ 1 人 4.5–7.5 个月；2 人 2.5–4.5 个月。

---

## 六、关键技术风险详解

### 6.1 物品 NBT → DataComponents（最大单项，占 20% 工作量）

当前所有装备的元数据都在 `stack.getOrCreateTagElement("vmd")` 里，例如：

```java
stack.getOrCreateTagElement(Utils.MOD_ID).putDouble(fullRateTagKey, rate);
stack.getOrCreateTagElement(Utils.MOD_ID).putString(typeTagKey, type);
stack.getOrCreateTagElement(Utils.MOD_ID).put(EX_BASE_ATTRIBUTE_DATA_KEY, new CompoundTag());
```

1.21 中 `ItemStack` 没有 NBT 了。两条路：

| 方案 | 做法 | 工期 | 评价 |
|---|---|---|---|
| **(a) 兼容层** | 全部走 `DataComponents.CUSTOM_DATA`（`CustomData`），自建 `ItemTags.of(stack)` 返回 `CompoundTag`，语义与现在完全一致 | 2–3 周 | **推荐**。改动集中在 76 个文件，机械替换，风险可控，未来仍可逐步拆成强类型组件 |
| (b) 正统 | 为每个属性注册独立 `DataComponentType`（或一个属性 record） | 3–4 周+ | 类型安全、同步省流量，但需要重写全部读写路径 + 存档迁移逻辑，回归面更大 |

**不论走哪条路，都必须写"老存档物品迁移器"**：正式服玩家仓库/银行/市场里存量装备带的是旧 NBT，需要在 1.21 侧读旧结构并重写为新结构。这是唯一"做错了会掉档"的环节，必须先在存档副本上演练 2 遍。

### 6.2 网络（281 个包）

现在的 `ModNetworking`（96 KB、275 个 `messageBuilder`、`channelFor(Class)` 反射分发）在 NeoForge 21.1 完全不可用。但**现有的 `new Foo(FriendlyByteBuf)` + `toBytes(FriendlyByteBuf)` 双构造模式可以 1:1 包成 `StreamCodec.of(Foo::toBytes, Foo::new)`**，其余是 275 行注册样板 + 每包声明一个 `CustomPacketPayload.Type`。可以用脚本生成 80% 的样板代码。

附带要保留的东西：`sendToServer` 里 100 包/tick 的客户端限流逻辑（防止连点器）要迁移到新 API 上。

### 6.3 事件与 Tick（隐蔽 bug 高发区）

`TickEvent.Phase.START` 的时序语义在 NeoForge 中变成 `*.Pre` / `*.Post`，`LevelTickEvent`（27 处）要逐个确认对位。VMD 的战斗/技能/副本全都挂在 tick 链上（`ServerTick` → 各系统 `handleServerTick`），**时序错位不会编译报错，只会表现为"偶发数值不对/技能不触发"** —— 这部分预算必须给足回归时间。

### 6.4 依赖矩阵（实测结论）

**有代码耦合的 23 个依赖，1.21.1 可用性：**

| 状态 | 模组 | 影响文件 |
|---|---|---|
| ✅ 有 1.21.1 官方版 | Curios 9.5.1、GeckoLib 4.9.3、Patchouli 1.21.1-93、PlayerAnimator 2.0.4、Citadel 2.7.1、Create 6.0.10、Xaero 26.6.0/1.47.0、Sophisticated Backpacks 3.26.12、Serene Seasons 10.1.0.9、Goety 3.2.1、Born in Chaos 1.7.6、Neapolitan 6.0.1、Aquamirae 7.2.13、Mowzie's Mobs 1.8.2、Farmer's Delight 1.3.4、Corn Delight、Coffee Delight、Brewin' and Chewin'、Lionfish、Cataclysm、Blueprint … | 128 |
| ⚠️ 有 1.21.1 但需注意 | Registrate（**无公开发布的 1.21.1 maven 制品**，需自己构建/jarJar）、Steam 'n' Rails（仅社区移植版）、MrCrayfish 家具（1.21 是"Refurbished"另一套）、CoroUtil（只有一个 1.21.x 构建） | 0（当前只 jarJar，无 import） |
| ❌ 无官方 1.21.1，需替代/降级 | **Ice and Fire**（无任何 1.21.x）、**Collector's Reap**（社区移植版 1.0.7）、**Seasonals**（<=1.20.1）、**Loot Beams**（仅 Forge，可用 refork） | 8 / 4 / 1 / 1 |

补充：`Bendy Lib`、`Mobtimizations` 也没有 1.21.1，但 VMD **零代码引用**，属于整合包层面的替换问题（都有等价替代：Mobtimizations→FarmersCutting/优化模组，Bendy Lib→直接删）。

**结论：依赖层面没有真正的拦路虎，14 个文件级别的改造即可通过。**

### 6.5 存档 / 服务端数据

| 数据 | 影响 |
|---|---|
| `player.getPersistentData()`（287 处） | **NeoForge 21.1 仍然提供**（已核对源码），可原样迁移 ✅ |
| `ItemStack` NBT（188 处） | **必须迁移**，见 6.1 ⚠️ |
| `SavedData`（市场/银行/房地产等，57 处） | 格式不变，代码签名基本不变 ✅ |
| 维度 / 战利品 / 标签 JSON | 少量，schema 变动集中在 1.20.5 的配方格式（VMD 配方几乎全在 Java 里）✅ |
| `oshi.util.tuples.Pair` / `com.ibm.icu.impl.Pair` | 3+2 个文件用了不该直接引用的第三方内部类，顺手换成 `record`/`Pair` 自研 |

---

## 七、不能只用人日衡量的部分

1. **迁移窗口 = 内容停更窗口。** v3 分支 3 个月（2026-07→10）累计 477 文件 / +17,762 行改动。机械迁移会改写这批文件的大部分，**迁移期间无法并行做内容**，否则合并冲突会吃掉全部收益。建议明确"冻结 6–8 周，只修 bug 不出内容"。
2. **客户端整合包是一次整体搬迁。** `run/config` 里有 **133 个配置文件 / 100+ 模组**（embeddium+oculus 光影栈、Distant Horizons、Yes Steve Model、JarsAuth、Sinytra Connector、Xaero、ModernFix、Quark…）。VMD 迁完之后，服务器/玩家的整合包要整体重建并逐一验证 —— 这部分工作量通常被低估一半。
3. **服务端混合端风险。** 仓库里有 `wraq2-mohist-test` 分支（2025-06），`run/config` 里有 `connector` / `connectorextras_energy_bridge`（Sinytra Connector）。**Mohist/Arclight 这类 Forge 混合端不支持 NeoForge**，NeoForge 侧要走 Youer 等新方案 —— 如果线上依赖 Bukkit 插件（登录、经济、反作弊），必须先确认这条链路，否则迁移会直接卡死在运维层。**这一条建议在动工前先确认清楚。**
4. **玩家侧成本**：客户端重装、光影/小地图/自定义模型全部重配、1.21 原版机制变化（战斗/附魔/村庄交易）需要玩家重新适应。
5. **0 单元测试**：`src/test` 只有 63 行。所有验证靠人工进服点，这是 25–40 人日回归预算的来源，也是最容易被压缩后爆雷的地方。

---

## 八、建议执行方案（分四步，风险前置）

### 阶段 A：迁移友好化重构（现在就能做，2–4 周，**不阻塞内容**）

在 1.20.1 上先做 5 件"无论如何都该做"的事，把未来的迁移从"改 1,700 个文件"压成"改 20 个文件"：

1. **抽 `ItemTags` 访问层**：188 处 `getOrCreateTagElement` 全部收敛到一个 helper。→ 迁 1.21 时只改 helper（预计把第 5 项从 15–25 人日压到 6–10 人日）。
2. **抽 `WraqPacket` 基类**：引入 `StreamCodec` 友好的抽象（`decode/encode/handle`），让 281 个包**现在**就脱离 `SimpleChannel` 的写法。→ 迁移时只改注册器。
3. **抽 `WraqTick` 事件层**：把 52 处 `TickEvent.*.Phase` 收敛到自己的 `WraqTick` 分发器（内部再按阶段调用）。→ 迁移只改分发器。
4. **抽 `Compat` 适配层**：Ice and Fire / Seasonals / Loot Beams / Collector's Reap 的 14 个引用点全部走接口 + `try/catch` 可选加载。→ 迁移时直接砍掉。
5. **降依赖**：把 55 个依赖里 40 个"只有运行期存在、零 import"的从 `implementation` 降级为可选（世界生成/装饰类尤其如此），减少对上游更新节奏的敏感度。

**这 5 项本身就有独立价值**（可维护性、编译速度、未来换平台），且完全在 1.20.1 上完成，可以直接随 v3 发布。

### 阶段 B：1.21.1 PoC（1 周，**决定要不要继续**）

新建独立工程（ModDevGradle + NeoForge 21.1.x），只迁通一条最小链路：

- [ ] 构建脚本 + `neoforge.mods.toml` + AT 官方名 → 编译通过
- [ ] `DeferredRegister` + 2 个物品 + 1 个方块（含 DataComponents 写法验证）
- [ ] 1 个 C2S + 1 个 S2C 数据包往返
- [ ] 1 个 HUD（`LayeredDraw.Layer`）+ 1 个 `Screen`
- [ ] 1 个 GeckoLib 实体动画
- [ ] Curios 槽位 + 1 个饰品 onEquip 生效
- [ ] 存档写入/读取 + 一个"旧 NBT 物品 → 新组件"迁移函数跑通

**这一步花费 < 总成本的 5%，却能提前暴露 80% 的未知风险。** 建议把它当成硬性门槛：PoC 不过，就不启动阶段 C。

### 阶段 C：大迁移（6–8 周，v3 内容冻结）

- 机械迁移（包名/注册/事件/网络）→ 编译归零
- DataComponents + 存档迁移器
- 客户端 GUI/HUD/动画
- 依赖替换
- 每个子系统"能进服 + 冒烟通过"，但**不做完整回归**

### 阶段 D：并行测试与切换（4–8 周，可与内容开发重叠）

- 1.20.1 与 1.21.1 双端并行跑，同一份存档副本互相对照数值
- 逐系统回归（战斗→元素→技能→副本→经济→任务→UI）
- 切换窗口选择在赛季/版本节点

**总周期：阶段 A(2–4 周) + B(1 周) + C(6–8 周) + D(4–8 周) ≈ 3.5–5 个月（2 人为主）。**

---

## 九、如果决定暂时不迁，该做什么

1. **继续吃 1.20.1 的红利**：Forge 47.4.x 仍在维护，VMD 是自给自足的服主 mod，绝大多数功能不依赖上游新 API。
2. **但立刻开始做阶段 A 的 5 项重构** —— 它们与"是否迁移"无关，纯赚。
3. **把依赖分为三层**并写进 CLAUDE.md：
   - 硬依赖（有 import，必须紧跟版本）：Curios / GeckoLib / PlayerAnimator / Patchouli / 少数内容模组
   - 软依赖（只有运行期，允许缺）：世界生成 / 装饰 / 性能类 40 个
   - 一次性依赖（随时可砍）：Ice and Fire / Seasonals / Loot Beams / Collector's Reap
4. **新系统一律不写 `ItemStack` NBT**，改走阶段 A 的 `ItemTags` 抽象（哪怕底下还是 NBT）—— 这样未来任何一个新系统都不会新增迁移债。
5. **等 26.x 生态成熟再考虑平台切换**，届时 DataComponents 是主版本基础，一次跳跃即可覆盖两个大版本。

---

## 十、附录：完整依赖核查结果

| 模组 | NF 1.21.1 | 最新 1.21.1 版本 | 备注 |
|---|---|---|---|
| Curios API | ✅ | 9.5.1+1.21.1 | |
| GeckoLib | ✅ | 4.9.3 | 现有 4.4.4，API 小改 |
| PlayerAnimator | ✅ | 2.0.4+1.21.1 | |
| Patchouli | ✅ | 1.21.1-93-neoforge | |
| Registrate | ⚠️ | 1.21.1-1.4.0 | 无公开 maven 制品，需自建/jarJar |
| Serene Seasons | ✅ | 10.1.0.9 | |
| Sophisticated Backpacks / Core | ✅ | 3.26.12 / 1.5.14 | |
| Create | ✅ | 6.0.10+mc1.21.1 | 大版本 6.x，需注意 API |
| Create: Steam 'n' Rails | ⚠️ | 0.3.0-beta.2（社区移植） | 官方仅到 1.20.1 |
| Armourer's Workshop | ✅ | 3.4.0-beta.3+1.21.1 | |
| Xaero's Minimap / World Map | ✅ | 26.6.0 / 1.47.0 | |
| Citadel | ✅ | 2.7.1 | |
| Lionfish API | ✅ | 3.1 | |
| L_Ender's Cataclysm | ✅ | 3.33 | |
| Born in Chaos | ✅ | 1.7.6 | 21 个文件直接引用其实体类 |
| Biomes O' Plenty | ✅ | 21.1.0.14 | |
| Aquamirae | ✅ | 7.2.13 | |
| TerraBlender | ✅ | 4.1.0.8 | |
| Upgrade Aquatic | ✅ | 7.0.2 | |
| Blueprint | ✅ | 8.2.0 | |
| WWOO / Tectonic / Lithostitched | ✅ | 2.6.7 / 3.0.28 / 1.8.1 | |
| When Dungeons Arise (+Seven Seas) | ✅ | 2.1.68 / 1.0.4 | |
| CTOV | ✅ | 3.6.3 | |
| MrCrayfish 家具 | ⚠️ | Refurbished 1.0.22 | 1.21 换了另一套 mod |
| Additional Lights | ✅ | 2.1.10 | |
| AzureLib | ✅ | 3.1.22（避 alpha） | |
| Quark / Zeta | ✅ | 4.1-487 / 1.1-40 | |
| Macaw's Lights and Lamps | ✅ | 1.1.5 | |
| **Ice and Fire: Dragons** | ❌ | — | 无任何 1.21.x，影响 8 个文件 |
| Mowzie's Mobs | ✅ | 1.8.2 | |
| EmbellishCraft | ✅ | 8.0.0.4 | |
| Farmer's Delight | ✅ | 1.3.4 | |
| Mapper Base / Another Furniture | ✅ | 7.1.1.0 / 4.0.3 | |
| OpenBlocks Elevator | ✅ | 1.11.4 | |
| Mysterious Mountain Lib | ✅ | 1.2.15-1.21.1 | |
| Corn / Cultural / Display / Coffee Delight | ✅ | 均已有 1.21.1 | |
| **Collector's Reap** | ❌（有社区移植） | 1.0.7 | 影响 4 个文件 |
| Brewin' and Chewin' | ✅ | 4.5.0+1.21.1 | |
| Jade | ✅ | 15.10.6 | |
| Neapolitan | ✅ | 6.0.1 | |
| **Seasonals** | ❌ | — | 影响 1 个文件 |
| Goety | ✅ | 3.2.1 | 影响 6 个文件 |
| **Mobtimizations** | ❌ | — | 零代码引用，可替换 |
| CoroUtil | ⚠️ | 1.21.0-1.3.8 | 单一构建 |
| Iceberg / Prism / Legendary Tooltips | ✅ | 1.3.2 / 1.0.11 / 1.5.5 | |
| **Loot Beams** | ❌（有 refork） | refork 3.4.7 | 影响 1 个文件（ClientSetup） |
| CreativeCore / ItemPhysic / Puzzles Lib | ✅ | 2.13.51 / 1.8.17 / 21.1.64 | |
| Pick Up Notifier / Modern UI / ModernFix | ✅ | 均有 1.21.1 | |
| **Bendy Lib** | ❌ | — | 零代码引用，可直接删 |

---

### 一句话总结

**迁 NeoForge 1.21.1 的代价约 3–5 个月（2 人）或 5–7 个月（1 人），其中一半是"1.20.1→1.21 的原版断层"而不是"Forge→NeoForge"；依赖生态已经基本就位（只有 4 个模组、14 个文件需要动手），真正的硬骨头是物品数据迁移和没有测试网支撑的全量回归。建议先用 2–4 周做降耦合重构 + 1 周 PoC 把风险前置，再决定是否投入大迁移。**
