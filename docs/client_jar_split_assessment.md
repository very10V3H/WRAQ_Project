# VMD 客户端专用 jar 方案评估

> 评估日期：2026-10-19
> 基线：`wraq3-master`，VMD 3.0.0，Forge 1.20.1-47.3.0，Java 17
> 目标：给客户端提供一个 jar，只包含客户端运行所需的代码；服务端 jar 暂时保持完整。

---

## 0. 结论摘要

1. **Gradle 可以产出“只含客户端代码”的 jar，但 `jar { exclude ... }` 本身不是一个安全的方案。**  
   Gradle 只能按路径删 class；它不会检查剩下的 class 是否还在引用被删的 class。Java 的懒加载能兜住一部分引用，但构造器、`instanceof`、静态初始化、字段/方法签名、Forge 的类扫描都会在客户端提前触发类加载。

2. **Forge 1.20.1 没有“打包期客户端裁剪”机制。**  
   - `mods.toml` 的 `clientSideOnly` 是 1.21.x 才有的字段；1.20.1 没有。即使有，它的语义是“dedicated server 跳过整个 mod”，不是“从客户端 jar 里删服务端 class”。
   - `@OnlyIn(Dist.CLIENT/DEDICATED_SERVER)` 是**运行期**剥离：在物理服务端剥客户端成员，或在物理客户端剥服务端成员；jar 里的字节码仍然存在。
   - `@OnlyIn` 不是“打包期裁剪”。因此不能靠一个 Forge 注解/开关解决。

3. **当前代码是按功能分包，不是按端分包。**  
   `process/system` 里有 16 个 `@OnlyIn(CLIENT)` 的 Screen；`render/gui` 里有 C2S 包的服务端处理；`events/mob/loot` 里放的是物品构造器要用的公共接口；`process/system/estate/EstateKey` 是客户端必须注册的物品。  
   所以“`exclude fun/wraq/process/system/**`”这种整包排除，会在客户端注册物品 / 初始化网络时直接 `NoClassDefFoundError`。

4. **真正可行的路线有三条：**
   - **A. 单 mod 双 artifact（推荐先做）**：源码仍在一棵树；用 Gradle 任务从完整 jar 过滤出 `-client.jar`，服务端继续用完整 jar。先做“敏感服务端子系统”过滤，不追求 100%。
   - **B. 单 mod 双 source set（长期最干净）**：`src/main` 只放公共+客户端，`src/server` 放服务端；`main` 编译时不依赖 server，编译期强制边界；`clientJar` = main，`serverJar` = main+server。
   - **C. 双 mod（vmd + vmd-server）**：客户端只装 `vmd`，服务端装两个；`vmd-server` 是标准 Forge server-only mod，`displayTest="IGNORE_SERVER_VERSION"`，不注册任何 registry 内容。适合把敏感逻辑彻底隔离。
   - 如果真实目的是“防逆向”而不是“减体积”，**混淆/加密**（1–3 天）比拆包更划算；但它不减少 jar 内容，只是提高阅读成本。

5. **不建议**复制仓库里 `wraq2-2.0.37-client` 分支的做法（手工删 `/commands`、`/events/server` 再发 `-client` 版本）。它验证了“删服务端入口”可行，但会形成长期分叉；应改为构建任务或 source set 自动产出。

6. **体积不是这件事的主要收益。**  
   当前 class 共 11.5 MB（压缩后约 5.3 MB），资源 13.6 MB；即使删掉 400–700 个服务端 class，最终 jar 大概只小 5%–10%。如果是为了“客户端只带必要代码/防泄漏”，值得做；如果是为了“减小下载体积”，收益很低。

---

## 1. 基线数据（本仓库实测）

| 指标 | 数值 |
|---|---|
| Java 文件 / 行数 | 1,747 / 161,554 |
| 编译后 class | 2,605 个，11.5 MB（zip 后约 5.3 MB） |
| 资源 | 13.6 MB / 4,884 文件（assets 13.5 MB） |
| `import net.minecraft.client.*` 的文件 | 487 |
| `import net.minecraft.server.*` 的文件 | 400 |
| 同时 import client + server 的文件 | 6 |
| `@Mod.EventBusSubscriber` | 60 个类（其中 `Dist.CLIENT` 22 个，`DEDICATED_SERVER` 0 个） |
| `@OnlyIn` | 60 处 / 58 个文件（几乎全是 `Dist.CLIENT`） |
| `DistExecutor` | 5 处 |
| `*Packet.java` | 284 个；`messageBuilder(...)` 278 次 |
| `DeferredRegister` / `.register(...)` | 129 / 2,394 处 |
| `RegistryObject` | 2,291 处 |
| `process/system` | 330 个文件，23 个 import client，107 个 import server，16 个 `@OnlyIn(CLIENT)` |
| `render/gui` | 54 个文件，29 个 import client，5 个 import server |

关键结论：**代码量很大，但“端”并没有形成目录边界**。大量类同时承担客户端展示和服务端逻辑。

---

## 2. 为什么“Gradle 直接 exclude 服务端包”会炸

### 2.1 网络注册是硬约束

`ModNetworking.register()` 在 `FMLCommonSetupEvent`（双端都会执行）里逐个注册 278 个包：

```java
net.messageBuilder(SomeC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
        .decoder(SomeC2SPacket::new)
        .encoder(SomeC2SPacket::toBytes)
        .consumerMainThread(SomeC2SPacket::handle)
        .add();
```

这里用了 **`SomeC2SPacket.class` 类字面量**。客户端执行到这一行时，JVM 必须解析并加载这个类。  
因此：**在当前注册方式下，所有在 `ModNetworking.register()` 里出现过的包类，都必须留在客户端 jar 里**，哪怕它的 `handle` 只会在服务端执行。  
（如果以后把注册拆成“客户端只注册 S2C、服务端注册全部”，才可以考虑删 C2S 包类；那是另一个重构。）

很多包类恰好放在“服务端系统”包里，例如：

- `fun.wraq.process.system.backpack.audit.networking.*`
- `fun.wraq.process.system.missions.netWorking.*`
- `fun.wraq.process.system.teamInstance.networking.*`
- `fun.wraq.process.system.tower.*`

如果整包 exclude `process/system`，客户端会在 `ModNetworking.register()` 里立刻缺类。

> 这也是一个重要的设计结论：**包类（packet class）是公共层，不是服务端层**。  
> 正确的切法是：包类留 common，handler 里的服务端逻辑要么留在包类里（靠懒加载），要么拆到 server 模块。

### 2.2 物品注册也是硬约束

`VMD` 构造器里注册了 30+ 个 `DeferredRegister<Item>`；每个 `RegistryObject` 的 supplier 都会在客户端创建物品实例。  
`ModItems` 直接引用了一些“放在服务端包里”的物品类：

```java
import fun.wraq.events.mob.instance.item.NetherHand;
import fun.wraq.events.mob.instance.item.PlainNecklace;
import fun.wraq.events.mob.instance.item.RevenantGoldenHelmet;
import fun.wraq.process.system.estate.EstateKey;
import fun.wraq.process.system.lottery.NewLotteries;
```

`EstateKey` 是 `process.system.estate` 包里的一个 `WraqItem`，客户端需要它来注册、显示 tooltip、发 C2S 请求。  
如果按包排除 `process.system.estate`，客户端注册 `ModItems.ESTATE_KEY` 时就会缺类。

> 结论：**物品类不能按“它所在的业务包”来判断端**。物品类必须留在客户端；它的服务端使用方法（如 `use` 里的经济/房屋逻辑）才可能需要拆。

### 2.3 基类构造器里的 `instanceof` 是提前加载

```java
// WraqMainHandEquip / WraqSword
if (!(this instanceof ManageEquip) && !(this instanceof RandomLootEquip)) {
    Utils.weaponList.add(this);
}
```

```java
// WraqArmor
if (!(this instanceof RandomArmor)) { ... }
if (this instanceof RandomLootEquip randomLootEquip) { ... }
```

`RandomLootEquip`、`RandomArmor` 在 `fun.wraq.events.mob.loot` 包里。  
`instanceof` 是运行期指令，会解析目标类；这些构造器对每个武器/护甲都会执行，所以 **`events.mob.loot` 必须留在客户端 jar**，即使 `events.mob` 的其余部分（刷怪控制器）是服务端专属。

### 2.4 “服务端包”里也有客户端 HUD 需要的数据

```java
// common/attribute/MobAttributes.java
double defence = MobSpawn.MobBaseAttributes.defence.getOrDefault(
        mob.getName().getString(),
        MobSpawn.MobBaseAttributes.defence.getOrDefault(MobSpawn.getMobOriginName(mob), 0d));
```

`MobSpawn` 是刷怪系统的入口，但它里面的 `MobBaseAttributes` 是公共数值表；客户端血条 / 属性展示可能会读。  
如果整类删掉 `MobSpawn`，客户端 HUD 会在读怪物属性时缺类。正确做法是：**把 `MobBaseAttributes` 拆成公共 data 类，刷怪控制器留服务端**。

### 2.5 `VMD` 主类同时引用两端

```java
// commonStart：双端都会执行
AuditBackpackProvider.init();

// clientStart：只在客户端执行
MenuScreens.register(..., ForgingBlockScreen::new);
```

`AuditBackpackProvider` 在 `process.system.backpack.audit`（服务端审计）；如果把它从客户端 jar 删掉，`VMD` 的 `commonStart` 会在客户端初始化时缺类。  
这类“双端生命周期里调用了服务端类”的地方，必须改成 `FMLEnvironment.dist.isDedicatedServer()` 分支、事件或服务接口。

### 2.6 Forge 的运行期剥离帮不上“打包”

Forge 的 `runtimedistcleaner` 会在物理服务端剥掉 `@OnlyIn(Dist.CLIENT)` 的成员；在物理客户端剥掉 `@OnlyIn(Dist.DEDICATED_SERVER)` 的成员。  
但：

- 它只改内存中的类定义，不改 jar；
- 它要求你先把注解放对；
- 如果客户端代码调用了被剥掉的方法，仍然会 `NoSuchMethodError`。

所以它适合作为**运行期兜底**，不是“客户端 jar 裁剪”的实现。

> 反过来说：**不要给仍被客户端代码引用的类随便加 `@OnlyIn(Dist.DEDICATED_SERVER)`**。Forge 会在物理客户端把这个类剥掉，客户端一旦走到引用点就会 `NoClassDefFoundError`；这比“类还在、只是没执行”更早崩。  
> 只有在确认客户端完全不引用、或引用点已经用 `DistExecutor`/hook 隔离后，才适合加。

### 2.7 用 jdeps 量化“删包后会引用到谁”

对当前编译产物做了一次 class 级依赖审计（`jdeps -verbose:class`，再按候选排除集合过滤）：

| 排除范围 | 被排除 class | 仍会引用它们的 class |
|---|---:|---:|
| 仅 `commands/**` | 89 | 15 |
| `commands/**` + `events/server/**` | 95 | 13 |
| 再加上 `events/mob/**` | 248 | 137 |
| 再加上 `process/system` 中经济/副本/任务等敏感系统 | 411 | 174 |

这组数字说明：

- **第一步（commands + events/server）很便宜**：只有 13 个类需要看，且大多是服务端事件/调试命令，适合做 PoC。
- **第二步（events/mob）开始变贵**：`MobSpawn` 既被客户端 HUD 读数据，又被服务端刷怪用，必须拆。
- **第三步（process/system 整包）最贵**：174 个引用类，其中包含 `ModNetworking`、`VMD`、`ModItems`、`Damage`、`ClientPlayerTickEvent`、`KeyInput`、`MobAttributes`、`BuffHud` 等双端/客户端类。

> 注意：jdeps 统计的是常量池引用，包含“方法体里只写不执行”的引用。  
> 所以 174 不是“174 个一定崩”，而是“174 个需要人工判断/验证”。但这也说明不能靠拍脑袋整包删。

---

## 3. 方案对比

| 方案 | 做法 | 客户端 jar | 服务端 jar | 工作量 | 风险 | 适用 |
|---|---|---|---|---|---|---|
| 0. 包级 `exclude` | `clientJar` 从完整 jar 过滤包 | 可能缺类 | 不变 | 1–3 天 | 高（启动/注册/构造器崩） | 只做 PoC |
| 1. 单 mod 双 artifact（过滤） | 保留一棵源码树；`clientJar` 过滤敏感服务端包 + 引用审计 | 少 95–411 个 class | 不变 | 1–3 周 | 中 | 先落地“客户端不带敏感逻辑” |
| 2. 单 mod 双 source set | `src/main` = 公共+客户端；`src/server` = 服务端；编译期强制 | main 输出 | main+server | 4–8 周 | 中高（重构面大） | 长期维护 |
| 3. 双 mod | `vmd` 客户端+公共；`vmd-server` 服务端 mod | 只有 vmd | vmd + vmd-server | 4–8 周 | 中（网络/开发环境） | 强隔离、敏感逻辑 |
| 4. 注解 + ASM 构建期剥离 | 给服务端类/方法加 `@OnlyIn(DEDICATED_SERVER)` 或自定义注解，`clientJar` 用 ASM 删 | 只留客户端成员 | 不变 | 2–4 周 | 中高（注解覆盖/边界遗漏） | 不想搬文件，但想细粒度 |
| 5. 混淆/加密 | ProGuard/自研 classloader/加密 class | 代码仍在，难读 | 不变 | 1–3 天 | 低（功能）/中（兼容性） | 防逆向，不防“有代码” |
| 6. 手工 `-client` 分支 | 复制分支删类 | 删掉的类 | 不变 | 1–2 天/次 | 高（长期分叉、漏删） | 一次性应急 |

### 方案 1 与方案 2 的关系

- 方案 1 是**打包层**：服务端 jar 完全不动，客户端 jar 是“完整 jar 的过滤副本”。
- 方案 2 是**源码层**：把服务端代码搬到另一个 source set，编译期就不允许公共层引用它；`clientJar` 不需要过滤，因为它根本不包含服务端代码。
- 建议先做方案 1 的“阶段 1”，验证客户端能跑；再决定是否投入方案 2 的大重构。

---

## 4. 推荐落地路线（先做 1，再决定 2）

### 阶段 0：目标和验收先定清楚

先回答三个问题：

1. **是为了防泄漏，还是为了减体积？**
   - 防泄漏：服务端经济/反作弊/副本/刷怪逻辑不应出现在客户端；战斗公式、物品 tooltip 逻辑可以保留。
   - 减体积：收益只有 5%–10%，不建议大动。
2. **客户端是否允许单机？**
   - 如果客户端 jar 删了服务端逻辑，**单机会缺逻辑**。VMD 是服务器 mod，建议明确“客户端 jar 仅用于连专用服；单机请用完整 jar”。
3. **验收标准是什么？**
   - 客户端能启动、能进服、能注册全部物品、能开菜单、能战斗/放技能、能收到 S2C 包、能跨维度、能进副本。
   - 服务端 jar 完全不变，专用服务器启动无新增报错。

### 阶段 1：只删 `commands` + `events/server`（1–3 天，低风险）

这两个包是当前最适合先动的：

- `commands/**` 89 个 class（其中 `PrefixPaperItem` 是物品，**不能删**，需先移走或单独保留）；
- `events/server/**` 7 个 class（`LoginInEvent`、`ServerTick`、`ServerPlayerTickEvent`、`LevelEvents`、`AttributeSet`、`BackpackAuditEvents`、`ThreadPools`）；
- jdeps 显示只有 13 个类还引用它们，且大多是服务端事件/调试命令/包 handler。

**先处理一个“伪服务端”例外：**  
`fun.wraq.commands.changeable.PrefixPaperItem` 是 **物品类**（`extends WraqItem`），被 `series/events/SpecialEventItems` 注册。  
如果直接 `exclude 'fun/wraq/commands/**'`，客户端会在 `SpecialEventItems` 注册时缺 `PrefixPaperItem`。  
**先把 `PrefixPaperItem` 移到 `series/events` 或 `common` 包**（并改 import），再排除 `commands/**`；或者用 `keepPrefixes` 单独保留它。

**需要处理的引用（第一轮）：**

| 引用方 | 引用目标 | 处理建议 |
|---|---|---|
| `series.events.SpecialEventItems` | `commands.changeable.PrefixPaperItem` | **物品类，必须先移到公共包**；不能随 commands 一起删 |
| `events.core.VariousEvents` | `PrefixCommand.prefix` / `clientPrefixInfo` | 把共享常量/客户端缓存移到 `common` 的 `PrefixData`；命令类只做命令注册 |
| `networking.misc.PrefixPackets.PrefixS2CPacket` | `PrefixCommand` | 同上，包类只读 `PrefixData` |
| `networking.misc.SmartPhonePackets.BuyCheckC2SPacket` | `SellCommand` | 包类不直接调命令；把出售逻辑抽到 common service |
| `networking...VpStoreBuyC2SPacket` | `CustomPrefixCommand` | 同上 |
| `process.func.multiblockactive...RightClickActiveHandler` | `TextCommand` | 抽共享数据/服务 |
| `process.system.bank.BankOperationCommand` | `SummonVillagerCommand` | 命令之间不要互相 import；抽 common helper |
| `process.system.profession.ProfessionOperationCommand` | `SummonVillagerCommand` | 同上 |
| `core.AttackEvent` / `core.ManaAttackModule` / `core.bow.MyArrow` | `DebugCommand` | 调试命令的静态开关/数据抽到 common |
| `events.mob.MobSpawn` | `LoginInEvent.sunPowerGetCount` 等 | 这些是登录初始化数据；抽到 common data 类 |
| `events.server.LoginInEvent` | `commands...` | `LoginInEvent` 本身要搬到 server；它引用的命令改 common helper |

**Gradle 任务（从最终发布 jar 过滤）：**

```groovy
// 以你们实际发布的完整 jar 为输入；通常是 jarJar（含 Registrate 嵌套 jar）
def keepPrefixes = [
        // 物品类，不能随 commands 一起删；更推荐先移到 series/events/common
        'fun/wraq/commands/changeable/PrefixPaperItem',
]

tasks.register('clientJar', Zip) {
    dependsOn tasks.jarJar
    archiveClassifier = 'client'
    archiveExtension = 'jar'   // Zip 默认是 .zip，必须改回 .jar

    from(zipTree(tasks.jarJar.archiveFile)) { spec ->
        spec.exclude { d ->
            def p = d.path
            if (!p.endsWith('.class')) return false
            if (keepPrefixes.any { p.startsWith(it) }) return false
            return p.startsWith('fun/wraq/commands/')
                    || p.startsWith('fun/wraq/events/server/')
        }
    }
}
```

> 为什么用 `Zip` 而不是 `Jar`：`Zip` 不会自己生成 manifest，会原样保留完整 jar 里的 `META-INF/MANIFEST.MF`、`mods.toml` 和 `META-INF/jarjar/**`。  
> 如果 `jarJar.archiveFile` 在你们的 FG 版本里不是 reobf 后的最终文件，改成 `dependsOn tasks.reobfJarJar` 并指向 `build/libs/...-all.jar`。  
> 这一点很关键：客户端产物必须是 **reobf 后** 的 class；直接拿未 reobf 的 jar 会在正式客户端因 SRG/官方名不匹配而崩。

**阶段 1 验收：**

- 客户端用 `clientJar` 启动；
- 进专用服；
- 物品注册、tooltip、菜单、战斗、技能、S2C 包正常；
- 服务端日志无新增 `NoClassDefFoundError`。

### 阶段 2：扩到“敏感服务端系统”（1–2 周，中等风险）

建议排除：

- `commands/**`
- `events/server/**`
- `events/mob/**` 的刷怪控制器/副本（**但保留 `events/mob/loot/**`、`events/mob/instance/item/**`**）
- `process/system` 下的经济/副本/任务/刷怪相关系统：
  - `bank`, `market`, `lottery`, `vp`, `stock`, `estate`, `randomevent`, `missions`, `teamInstance`, `endlessinstance`, `instance`, `tower`, `npc`, `backpack`, `smelt`, `point`, `reason`, `security`
- `process/func/security/**`、`process/func/plan/**`、`process/func/rank/**` 的服务端部分

**不能整包删的例外（必须保留在客户端）：**

- `events/mob/loot/**`：`RandomLootEquip`、`RandomArmor` 被装备基类构造器 `instanceof`；
- `events/mob/instance/item/**`：`NetherHand` 等是注册物品；
- **所有 `*Items` / `*Items.java` 物品容器、所有物品类**：`VMD` 构造器会注册它们；例如 `process/system/instance/MopUpPaperItems`、`process/system/estate/EstateKey`、`process/system/lottery/NewLotteries`；
- `process/system/*/item/**`：各系统的物品类；
- `process/system/*Screen*.class` / `*Gui*.class`：客户端 GUI；
- **所有 `networking/**` 包类**：`ModNetworking.register()` 会加载它们；
- `process/system/estate/EstateKey.class`、`lottery/NewLotteries.class` 这类“物品/数据 + 服务逻辑”混合类：先保留，后续再拆方法。

Gradle 过滤要支持“排除大包、保留例外”：

```groovy
def serverPrefixes = [
        'fun/wraq/commands/',
        'fun/wraq/events/server/',
        'fun/wraq/events/mob/',
        'fun/wraq/process/system/bank/',
        'fun/wraq/process/system/market/',
        'fun/wraq/process/system/lottery/',
        'fun/wraq/process/system/vp/',
        'fun/wraq/process/system/stock/',
        'fun/wraq/process/system/estate/',
        'fun/wraq/process/system/randomevent/',
        'fun/wraq/process/system/missions/',
        'fun/wraq/process/system/teamInstance/',
        'fun/wraq/process/system/endlessinstance/',
        'fun/wraq/process/system/instance/',
        'fun/wraq/process/system/tower/',
        'fun/wraq/process/system/npc/',
        'fun/wraq/process/system/backpack/',
        'fun/wraq/process/system/smelt/',
        'fun/wraq/process/system/point/',
        'fun/wraq/process/system/reason/',
        'fun/wraq/process/func/security/',
]

def keepPrefixes = [
        'fun/wraq/events/mob/loot/',
        'fun/wraq/events/mob/instance/item/',
]

tasks.register('clientJar', Zip) {
    dependsOn tasks.jarJar
    archiveClassifier = 'client'
    archiveExtension = 'jar'   // Zip 默认是 .zip，必须改回 .jar

    from(zipTree(tasks.jarJar.archiveFile)) { spec ->
        spec.exclude { d ->
            def p = d.path
            if (!p.endsWith('.class')) return false
            if (keepPrefixes.any { p.startsWith(it) }) return false
            return serverPrefixes.any { p.startsWith(it) }
        }
    }
}
```

> 注意：上面的 `keepPrefixes` 只解决“包内物品/接口”的例外；`process/system/**/networking/**` 的包类仍然会被 `serverPrefixes` 命中。  
> 所以阶段 2 更稳妥的做法是：**先把所有包类/物品类/GUI 类移到公共包**，再按前缀删。否则就要维护更复杂的例外表。

### 阶段 3：拆混合类（2–4 周，高风险）

到了这一步，剩下的是“同一个类里既有客户端代码又有服务端逻辑”：

- `VMD`：`commonStart` / `serverStartEvent` / `serverStopEvent` / `clientStart`；
- `MobSpawn`：`MobBaseAttributes` 数据 + 刷怪控制器；
- `EstateKey` / `NewLotteries`：物品展示 + 服务端经济逻辑；
- `Damage` / `MobAttributes` / `DamageInfluence`：公共战斗计算 + 服务端调试/副本；
- 284 个包类：数据 + handler；
- 248 个装备类：注册/tooltip + `tick` 服务端行为。

**推荐工具：服务端 hook + 自定义事件 / 服务接口**

```java
// common
public final class ServerHooks {
    public interface ItemTick {
        void tick(Player player, ItemStack stack);
    }

    private static ItemTick itemTick = (player, stack) -> {};

    public static void setItemTick(ItemTick hook) {
        itemTick = hook;
    }

    public static void tickItem(Player player, ItemStack stack) {
        itemTick.tick(player, stack);
    }
}
```

- 公共物品类只调 `ServerHooks.tickItem(...)`；
- 服务端 mod / server source set 在初始化时 `ServerHooks.setItemTick(realImpl)`；
- 客户端没有设置，就是 no-op；
- 这样公共层不再 import 服务端类。

也可以用 Forge 事件：

```java
// common
public class VmdItemTickEvent extends Event { ... }

// server
@SubscribeEvent
public static void onItemTick(VmdItemTickEvent event) { ... }
```

事件方式更 Forge 风格，但每个 tick 都发事件有性能开销；适合低频（登录、使用、击杀），不适合每 tick 的装备行为。

### 阶段 4：可选，升级为 source set / 双 mod

如果阶段 2/3 的例外表越来越长，说明“过滤”已经不适合维护，转成编译期边界：

- 单 mod 双 source set（推荐）：
  - `src/main/java`：公共 + 客户端；
  - `src/server/java`：服务端；
  - `main` 编译时不加 `server`，任何公共层 import 服务端类都会编译失败；
  - `clientJar` = main；`serverJar` = main + server。
- 双 mod：
  - `vmd`：公共 + 客户端；
  - `vmd-server`：服务端，依赖 `vmd`；
  - `vmd-server` 的 `mods.toml` 设置 `displayTest="IGNORE_SERVER_VERSION"`，且**不注册任何 registry 内容**（物品/方块/实体/效果/粒子等），否则客户端会 registry mismatch；
  - 客户端只装 `vmd`，服务端装两个。

---

## 5. Gradle 配置草案（单 mod 双 source set）

> 下面只是骨架，具体以 ForgeGradle 6.0.54 的实际行为为准；建议先在独立分支验证。

```groovy
sourceSets {
    main {
        // 公共 + 客户端
        java.srcDirs = ['src/main/java']
        // 注意：不要覆盖 resources.srcDirs，保留现有 src/generated/resources 配置
    }
    server {
        java.srcDirs = ['src/server/java']
        compileClasspath += sourceSets.main.output + sourceSets.main.compileClasspath
        runtimeClasspath += sourceSets.main.output + sourceSets.main.runtimeClasspath
    }
}

configurations {
    serverImplementation.extendsFrom implementation
    serverCompileOnly.extendsFrom compileOnly
    serverRuntimeOnly.extendsFrom runtimeOnly
}

minecraft {
    runs {
        client {
            mods {
                vmd { source sourceSets.main }
            }
        }
        server {
            mods {
                vmd {
                    source sourceSets.main
                    source sourceSets.server
                }
            }
        }
    }
}

// 客户端 artifact：main 输出（不包含 server）
tasks.named('jar', Jar) {
    archiveClassifier = 'client'
}

// 服务端 artifact：main + server
tasks.register('serverJar', Jar) {
    archiveClassifier = 'server'
    from sourceSets.main.output
    from sourceSets.server.output
    manifest {
        // 复制 jar 的 manifest 配置
    }
}

reobf {
    jar { }
    serverJar { }
}

tasks.named('serverJar').configure {
    finalizedBy 'reobfServerJar'
}

// FG 文档要求：自定义 source set 的 reobf 需要额外 classpath
tasks.named('reobfServerJar') {
    libraries.from sourceSets.server.compileClasspath
}
```

注意事项：

1. **`jarJar`**：Registrate 是 `jarJar` 嵌套依赖。客户端 artifact 继续用 `jarJar` 即可；服务端 artifact 如果也要嵌套，需要为 `serverJar` 单独配 `serverJarJar`，或让服务端继续用现有完整 `jarJar`（也就是“客户端过滤、服务端完整”的方案 1）。
2. **`reobf`**：客户端/服务端两个 artifact 都要 reobf；自定义 source set 的 `libraries` 要加，否则 reobf 可能找不到类。
3. **开发环境**：`runServer` 的 `mods` 里同时挂 `main` 和 `server`；如果 FG 版本不支持多个 `source`，就改用两个子项目，或构建后把完整 jar 丢进 `run/mods`。  
   如果 `server` source set 找不到 Minecraft/Forge 类，需要把 FG 的 `minecraft` 配置也挂到 `server.compileClasspath`（不同 FG 版本配置名可能是 `configurations.minecraft` 或由 FG 动态生成）；先在独立分支跑 `compileServerJava` 验证。
4. **不要**在 `main` 的 `compileClasspath` 里加 `server.output`；否则编译期边界失效，又会回到“能编译但运行时缺类”。

---

## 6. 验证与 CI

### 6.1 构建后静态检查

建议加一个 `verifyClientJar` 任务：

1. 解开 `clientJar`；
2. 对每个 `.class` 用 ASM 读常量池；
3. 如果发现它引用了被排除的 class，且引用方本身没被排除，就报错/输出清单；
4. 初期可以用 allowlist 放过“只会在服务端执行的方法体引用”，但每个 allowlist 项都要写原因。

伪代码：

```groovy
tasks.register('verifyClientJar') {
    dependsOn tasks.clientJar
    doLast {
        // 1. 收集 clientJar 中所有 class 名称
        // 2. 用 ASM ClassReader 读每个 class 的 constant pool
        // 3. 如果 CONSTANT_Class 指向 serverPrefixes 中的类，报错
        // 4. 输出 (sourceClass -> targetClass) 清单
    }
}
```

第一轮跑出来大概率就是 jdeps 那份 174 个引用类的清单；按清单逐个处理。

### 6.2 运行时验证

- **专用服务器**：继续用完整 `jarJar`，确认启动/登录/战斗/副本无变化。
- **客户端**：用 `clientJar` 启动，连专用服务器，至少覆盖：
  - 登录初始化、HUD、技能栏；
  - 打开所有 Screen（锻造/酿造/注入/熔炉/背包/市场/股票/抽奖/任务/副本/队伍）；
  - 物品 tooltip、物品使用、装备 `tick`、Curios `tick`；
  - S2C 包（粒子/伤害数字/冷却/状态）；
  - 跨维度、传送、路径点；
  - 副本进入/退出、队伍同步。
- **单机**：明确不支持，或提供完整 jar。客户端 jar 缺服务端逻辑，单机会在创建世界/进世界时崩。

### 6.3 Forge 握手 / 注册表

- **两个 artifact 必须使用相同的 `modId` 和 `version`**；客户端 lite 包和服务端完整包在 Forge 看来是同一个 mod。版本号不同会触发 `displayTest="MATCH_VERSION"` 的红叉。
- `ModNetworking` 的两个 channel 都使用 `clientAcceptedVersions(s -> true)` / `serverAcceptedVersions(s -> true)`；Forge 的 `validateChannels` 对缺失 channel 会调用该 predicate，`s -> true` 会接受 `ABSENT`。所以“客户端少几个服务端 class、但 channel 还在”不会因为 channel 版本被拒。  
  前提是 **所有包类都还在、注册顺序/ID 不变**；删任何一个包类都可能让 ID 偏移或注册失败。
- **registry 必须一致**：客户端 jar 不能缺任何 `DeferredRegister` 注册的物品/方块/实体/效果/粒子/菜单/声音。服务端多注册内容 -> 客户端 registry snapshot 缺失 -> 连接失败。  
  所以“客户端专用 jar”只适合删**不注册 registry 的服务端逻辑**，不适合删服务端专属内容（除非这些内容本来就不该存在于这个 mod）。
- 如果走双 mod 方案，`vmd-server` 要设置 `displayTest="IGNORE_SERVER_VERSION"`，并且不注册 registry 内容；否则客户端会看到红叉/连接失败。

---

## 7. 工作量与收益

### 7.1 工作量（1 人）

| 阶段 | 内容 | 估算 |
|---|---|---|
| 0 | 盘点、jdeps/ASM 审计、验收标准 | 2–3 天 |
| 1 | 删 commands + events/server，修 13 个引用 | 1–3 天 |
| 2 | 删经济/副本/任务/刷怪敏感系统，修 50–80 个引用 | 1–2 周 |
| 3 | 拆混合类（MobSpawn、EstateKey、Damage、包类 handler、装备 tick） | 2–4 周 |
| 4 | source set / 双 mod 重构 + CI + 回归 | 2–4 周 |
| **合计** | | **6–10 周** |

如果只做阶段 1–2（“客户端不带敏感服务端逻辑”），大约 **2–3 周**，风险中等。

### 7.2 体积收益

- 当前：class 11.5 MB（zip 后 5.3 MB）+ 资源 13.6 MB，最终 jar 大约 19 MB。
- 删 95 个 class（阶段 1）：约 0.5 MB raw / 0.2 MB zip。
- 删 411 个 class（阶段 2）：约 2.4 MB raw / 1.1 MB zip。
- 删 700–900 个 class（完整）：约 4–5 MB raw / 2 MB zip。
- 最终 jar 大概只小 **5%–10%**。如果目标是下载体积，不值得。

### 7.3 防泄漏收益

- 阶段 1：客户端看不到命令实现，但经济/副本/刷怪逻辑仍在。
- 阶段 2：客户端看不到经济/副本/任务/刷怪控制器，收益明显。
- 完整：客户端只剩物品/战斗/UI/网络数据；但战斗公式、物品行为仍然可见（客户端需要它们做 tooltip/预测）。
- 如果只想“让人读起来费劲”，混淆/加密更便宜；但 GPLv3 下分发二进制仍需提供对应源码，混淆不免除 GPL 义务（建议法务确认）。

---

## 8. 最终建议

1. **先做阶段 1**：`clientJar` 过滤 `commands` + `events/server`，用 13 个引用类的清单修一遍，跑客户端进服。  
   这是最低风险、最快看到效果的 PoC，也验证“过滤 jar”这条流水线。
2. **再做阶段 2**：把经济/副本/任务/刷怪这些敏感系统排除，但**保留包类、物品类、GUI、`events/mob/loot`、`events/mob/instance/item`**。  
   这一步能做到“客户端不携带大部分服务端敏感逻辑”，且不需要大改架构。
3. **不要一上来搞 source set 全量重构**。当前代码 1,747 个文件、0 个自动化测试，全量拆分的回归风险很高；先用过滤 + 审计把边界摸清。
4. **如果阶段 2 的例外表失控**（例如每加一个系统就要加 keep 规则），再升级到 **单 mod 双 source set** 或 **vmd-server 双 mod**；这时你已经有了清晰的 class 清单，迁移会快很多。
5. **如果真实目标是防逆向**，先评估混淆；如果目标是减体积，直接放弃——收益只有个位数百分比。
6. **不要**继续维护 `wraq2-2.0.37-client` 那种手工删类的分支；用 Gradle 任务/source set 自动产出，避免长期分叉。

---

## 附录 A：当前代码里最容易踩的“跨端引用”样例

| 类 | 引用 | 为什么危险 |
|---|---|---|
| `ModNetworking` | 278 个 `*Packet.class` | `FMLCommonSetupEvent` 双端执行，类字面量强制加载 |
| `VMD` | `AuditBackpackProvider`、`MobSpawn`、`RandomEventsHandler`、`MarketInfo` | 主类双端加载；`commonStart` 会执行服务端初始化 |
| `WraqMainHandEquip` / `WraqSword` | `RandomLootEquip` | 构造器 `instanceof`，每个武器注册时执行 |
| `WraqArmor` | `RandomArmor`、`RandomLootEquip` | 构造器 `instanceof`，每个护甲注册时执行 |
| `ModItems` | `EstateKey`、`NetherHand`、`NewLotteries` | 物品注册 supplier 引用；删了就直接缺类 |
| `MobAttributes` | `MobSpawn.MobBaseAttributes` | 客户端 HUD/属性展示可能读怪物数值 |
| `DamageInfluence` | `MobSpawn`、多个 `*Instance` | 公共伤害计算引用服务端副本/刷怪 |
| `ClientPlayerTickEvent` / `KeyInput` | `MissionScreen`、`SmeltRecipeScreen`、`DailyEndlessInstance` | 客户端事件直接引用 Screen / 服务端数据 |
| `BuffHud` | `ManaTowerData`、`DailyEndlessInstance`、`SpringMobEvent` | 客户端 HUD 读服务端同步数据 |
| `EstateKey` | `EstateUtil`、`EstateInfo` | 物品在 `process.system.estate` 包，但客户端必须注册 |
| `render/gui` 中的 C2S 包 | `ServerPlayer` / 服务端数据 | 包在“render”包里，但 handler 是服务端逻辑 |

## 附录 B：建议的 class 清单维护方式

不要手写 411 个 class 的排除列表。建议：

1. 用 `jdeps`/ASM 生成 `build/client-exclude.txt`（每行一个 class 路径）；
2. 人工在 PR 里 review 这个文件；
3. `clientJar` 读取该文件；
4. `verifyClientJar` 检查“剩余 class 是否引用 excluded class”；
5. 每次新增服务端类，跑一次审计任务，把新类加进清单。

这样比在 `build.gradle` 里堆正则更可控，也方便 code review。

---

### 一句话总结

**Gradle 能打出“客户端专用 jar”，但前提是先把“服务端 class 集合”和“客户端仍会引用的边界”盘清楚；直接按包 `exclude` 不是方案。**  
**推荐先做 `commands + events/server` 的低风险过滤，再逐步扩到经济/副本/刷怪；等例外表稳定后，再升级到 source set 或双 mod。**
