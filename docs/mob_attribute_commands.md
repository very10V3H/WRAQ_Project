# 运行时怪物属性调整命令

本文档说明 VMD 的两套运行时怪物属性调整命令：

- `/vmd bossattr`：领主级怪物（`NoTeamInstance` 子类）
- `/vmd mobattr`：普通刷怪控制器（`MobSpawnController` 子类）

两套命令使用**独立的内存覆盖表**，互不影响。覆盖只在怪物下一次生成/召唤时生效，不修改当前已经存在的怪物，也不写入磁盘。

---

## 1. 共同规则

### 权限

两条命令都需要权限等级 **2**（OP）。

### 属性字段

| 字段 | 含义 | 常用别名 |
|---|---|---|
| `attackDamage` | 攻击力 | `attack`, `atk`, `damage` |
| `defence` | 防御力 | `defense`, `def` |
| `manaDefence` | 法术防御 | `manadefence`, `mdef` |
| `critRate` | 暴击率 | `crit` |
| `critDamage` | 暴击伤害 | `critdmg` |
| `defencePenetration` | 防御穿透 | `defpen` |
| `defencePenetration0` | 固定防御穿透 | `defpen0` |
| `healthSteal` | 生命偷取 | `lifesteal` |
| `maxHealth` | 最大生命 | `health`, `hp`, `maxhp` |
| `movementSpeed` | 移动速度 | `speed` |
| `healthRecover` | 每秒固定生命回复 | `healthregen` |
| `percentHealthRecover` | 每秒最大生命百分比回复 | `percentregen` |

规则：

- `maxHealth` 最小为 1；
- 其他属性不允许负数；
- `NaN`、`Infinity` 会被拒绝；
- 未设置的字段沿用该类原本的基础属性。

### 生效时机

- `set` 后不会立即修改已经生成的怪物；
- 目标怪物下一次生成/召唤时读取新属性；
- `reset` 后下一次生成恢复代码里的基础属性。

### 持久化

**不持久化。** 覆盖只存在于当前服务器进程内：

- 服务器停止时清空；
- 重启服务器后全部恢复默认；
- 不会生成配置文件。

---

## 2. 领主级怪物：`/vmd bossattr`

适用对象：继承 `NoTeamInstance` 的领主/副本 Boss。

### 查看可调整的 Boss

```text
/vmd bossattr list
```

输出示例：

```text
PlainInstance.main - 普莱尼
MoonInstance.attack - 阿尔忒弥斯 - 明镜
MoonInstance.mana - 阿尔忒弥斯 - 天镜
```

profile 的格式为：

```text
<实例ID>.<角色>
```

默认角色为 `main`，可以省略：

```text
/vmd bossattr info PlainInstance
/vmd bossattr info PlainInstance.main
```

两者等价。

### 查看基础属性与当前覆盖

```text
/vmd bossattr info <profile>
```

示例：

```text
/vmd bossattr info PlainInstance.main
```

### 修改属性

```text
/vmd bossattr set <profile> <attribute> <value>
```

示例：

```text
/vmd bossattr set PlainInstance.main attackDamage 500
/vmd bossattr set PlainInstance.main maxHealth 30000
/vmd bossattr set MoonInstance.attack defence 150
```

执行后会提示：

```text
下一次召唤生效，当前 Boss 不变。
```

### 清除单个覆盖

```text
/vmd bossattr reset <profile> <attribute>
```

示例：

```text
/vmd bossattr reset PlainInstance.main attackDamage
```

### 清除某个 profile 的全部覆盖

```text
/vmd bossattr reset <profile>
```

示例：

```text
/vmd bossattr reset PlainInstance.main
```

### 最大生命说明

领主属性在 `getMainMobAttributes()` 返回之后应用覆盖，因此：

- `maxHealth` 是**最终绝对生命值**；
- 原本 `getMainMobAttributes()` 里按玩家人数计算的生命缩放会被该值覆盖；
- 如果想保留人数缩放，不要设置 `maxHealth`，改为只调整攻击、防御等字段。

---

## 3. 普通刷怪：`/vmd mobattr`

适用对象：继承 `MobSpawnController` 的普通刷怪控制器。

### 查看可调整的控制器

```text
/vmd mobattr list
```

输出示例：

```text
PlainZombieSpawnController - 平原僵尸
HuskSpawnController - 尸壳
```

命令主键是控制器类名，例如 `PlainZombieSpawnController`。

### 查看属性

```text
/vmd mobattr info <controller>
```

示例：

```text
/vmd mobattr info HuskSpawnController
```

### 修改属性

```text
/vmd mobattr set <controller> <attribute> <value>
```

示例：

```text
/vmd mobattr set HuskSpawnController attackDamage 80
/vmd mobattr set HuskSpawnController maxHealth 1200
/vmd mobattr set PlainZombieSpawnController movementSpeed 0.25
```

执行后：

```text
下一次刷新生效，当前怪物不变。
```

### 清除覆盖

清除单个字段：

```text
/vmd mobattr reset HuskSpawnController attackDamage
```

清除整个控制器：

```text
/vmd mobattr reset HuskSpawnController
```

### 最大生命说明

普通刷怪控制器在刷新点可能存在 `attributeRate` 倍率。处理顺序为：

1. 先按刷新点倍率缩放基础属性；
2. 再应用命令覆盖。

因此 `maxHealth` 覆盖值是**缩放后的最终值**，不会再被刷新点倍率放大。

---

## 4. 完整示例

### 调整普莱尼

```text
/vmd bossattr list
/vmd bossattr info PlainInstance.main
/vmd bossattr set PlainInstance.main attackDamage 600
/vmd bossattr set PlainInstance.main maxHealth 25000
```

让当前战斗结束或离开副本，等待下一次召唤后，新普莱尼使用新属性。

恢复默认：

```text
/vmd bossattr reset PlainInstance.main
```

### 调整月亮双 Boss

```text
/vmd bossattr set MoonInstance.attack maxHealth 2500000
/vmd bossattr set MoonInstance.mana defence 200
```

`attack` 和 `mana` 分别对应「明镜」和「天镜」，互不影响。

### 调整普通尸壳

```text
/vmd mobattr set HuskSpawnController attackDamage 100
```

等待该区域下一次自然刷新后生效。

---

## 5. 当前限制

1. 不支持热更新当前 Boss/怪物；
2. 不支持持久化，重启即失效；
3. 领主侧目前只开放主 Boss 档案；月亮副本额外开放 `attack` / `mana` 两个档案，其他副 Boss、小怪仍使用硬编码属性；
4. 普通刷怪侧按控制器类名调整，同一个控制器刷新出的同类怪物共享覆盖；
5. 覆盖值不会改变怪物的 AI、技能倍率或特殊机制，只改变基础属性数值。

---

## 6. 实现位置

| 功能 | 位置 |
|---|---|
| 共享覆盖数据 | `fun.wraq.events.mob.attribute.MobAttributeOverride` |
| 领主覆盖表 | `fun.wraq.events.mob.attribute.BossAttributeOverrides` |
| 普通刷怪覆盖表 | `fun.wraq.events.mob.attribute.MobSpawnControllerAttributeOverrides` |
| 领主命令 | `fun.wraq.commands.stable.ops.BossAttributeCommand` |
| 普通刷怪命令 | `fun.wraq.commands.stable.ops.MobAttributeCommand` |
| 领主调用入口 | `NoTeamInstance#applyBossAttributes` |
| 普通刷怪调用入口 | `MobSpawnController#spawnMob` |
