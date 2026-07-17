# 数值框架文档 — VMD (维瑞阿契)

## 一、设计哲学

### 1.1 核心原则

| 原则 | 说明 |
|---|---|
| **乘法主导** | 伤害 = 基础值 × 攻击增幅 × 防御减免 × 最终乘区，各乘区独立，避免堆叠单一属性 |
| **防区递减** | 防御公式采用 `100/(100+DEF)` 经典 MMORPG 递减曲线，鼓励均衡配装 |
| **三职业对称** | 战士/法师/弓手共享同一框架，通过属性权重差异化实现职业特色 |
| **数值可见** | 玩家面板数值与战斗跳字一致，公式可推导 |
| **软上限控制** | 暴击率、闪避率、冷却缩减设置硬上限或分段衰减 |

### 1.2 数值膨胀控制

- 每 50 级为一个资料片梯度，怪物生命约增长 3~4 倍
- 装备属性增长呈对数曲线，前期快速成长（1-100级），中期放缓（100-200级），后期精细（200-300级）
- 所有百分比属性设置上限，防止叠加失控

---

## 二、等级系统

### 2.1 经验公式

**代码路径**: `fun.wraq.common.Compute#getCurrentXpLevelUpNeedXpPoint`

```
XP_required(Lv) = e^(3 + 7 * Lv / 100)
```

经验值存储于玩家持久化数据 `"Xp"` 键 (`player.getPersistentData()`)，与 Minecraft 原版经验条完全独立。当 `Xp >= XP_required(currentLevel)` 时升级，超出经验自动结转到下一级。

| 等级 | 升级所需经验 | 累计经验 |
|---|---|---|
| 1 | ~22 | 0 |
| 10 | ~40 | ~300 |
| 25 | ~116 | ~1,700 |
| 50 | ~665 | ~13,000 |
| 100 | ~22,026 | ~720,000 |
| 125 (expGetUpperLimit) | ~126,472 | ~2,400,000 |
| 150 | ~729,416 | ~22,000,000 |
| 200 | ~24,154,952 | ~730,000,000 |
| 250 | ~799,901,773 | ~24,000,000,000 |
| 300 (levelUpperLimit) | ~26,489,122,132 | ~800,000,000,000 |

**经验曲线特征**: 纯指数增长，每级增长因子 `e^0.07 ≈ 1.0725`（约 7.25%/级）。Lv 1→100 增长 ~1000 倍，Lv 100→200 增长 ~1100 倍，Lv 200→300 增长 ~1100 倍。等比递增强调后期每一级的价值感。

### 2.2 经验获取机制

**代码路径**: `fun.wraq.common.Compute#givePercentExpToPlayer`

经验获取采用**百分比制**，核心公式：

```
xp = getCurrentXpLevelUpNeedXpPoint(expLevel) * num * (1 + expUp)
```

| 参数 | 含义 | 说明 |
|---|---|---|
| `expLevel` | 经验来源等级 | 怪物等级、任务等级等 |
| `num` | 经验百分比 | 获得 expLevel 对应升级所需经验的百分比 |
| `expUp` | 经验加成率 | 装备词条、药水等提供的额外经验倍率，乘法叠加 |

**经验来源典型 `num` 值**:

| 来源 | num | 说明 |
|---|---|---|
| 怪物击杀 | 0.02 (2%) | `MobSpawn.java:484`，取怪物等级对应的 XP |
| 副本 Boss | 0.1 (10%) | `NoTeamInstance.java:200`，取副本等级 |
| 每日任务 | 0.5 (50%) | `DailyMissionFinishedRequestC2SPacket.java:58` |
| 委托任务 | 0.02 × tier | `MobKillEntrustment.java:546` |
| 声望任务 | 0.02 × tier | `ReputationMissionFinishedRequestC2SPacket.java:66` |
| 计划任务 | 0.04 × tier | `PlanMissionFinishedRequestC2SPacket.java:70` |
| 休息区挂机 | 0.02 / 次 | `RestZone.java:68` |
| 经验道具 | 随机区间 | `ExpItem.java:42` |
| 黑堡副本 | 4.0 (400%) | `NewCastleInstance.java:194`，固定 expLevel=180 |
| 支线采集类 | 0.0025~0.05 | 采集/钓鱼/伐木活动 |

### 2.3 经验获取限制

**等级上限**:
- `levelUpperLimit = 300` — 玩家最高等级，达到后不再获取任何经验

**经验来源等级上限** (`expGetUpperLimit = 125`):
- 当 `expLevel >= 125` 时，`num` 等比例缩放: `num *= expLevel / 125`，然后 `expLevel = 125`
- 防止高等级内容溢出过多经验，同时保留高等级来源的相对优势

**等级差硬上限** (防越级刷怪):
- `if (expLevel - player.experienceLevel > 8) expLevel = player.experienceLevel`
- 经验来源等级超过玩家 **8 级以上**时，直接降为玩家等级计算
- 不同于原文档描述的平滑系数，代码中是**硬截断**而非渐变

**经验加成来源**:
- 装备词条 (通过 `PlayerAttributes.expUp(player)` 获取)
- 组队加成
- 活动/药水等限时增益

---

## 三、属性体系

### 3.1 属性来源与计算

所有玩家属性存储在 `Utils.java` 的静态 `Map<Item, Double>` 中（~40+ 个属性图）。玩家装备（武器/防具/Curios饰品）通过 `Compute.CuriosAttribute.attributeValue(player, attributeMap)` 迭代累加。属性值在登录和装备变更时全量重算。

### 3.2 核心攻击属性 (进攻端)

| 代码属性 | 说明 | 使用者 |
|---|---|---|
| `attackDamage` | 物理攻击力，战士/弓手普攻和技能的基础伤害来源 | 战士, 弓手 |
| `manaDamage` | 法术强度，法师普攻（魔法攻击）和技能伤害的基础来源 | 法师 |
| `critRate` | 暴击率，0.0~1.0，判定公式 `random < critRate` | 战士, 弓手 |
| `critDamage` | 暴击伤害加成，暴击时 `damage = baseDamage * (1 + critDamage)` | 战士, 弓手 |
| `defencePenetration` | 护甲百分比穿透，`有效防御 = 防御 * (1 - defencePenetration) - penetration0` | 战士, 弓手 |
| `defencePenetration0` | 护甲固定穿透 | 战士, 弓手 |
| `manaPenetration` | 法术百分比穿透，作用于法力防御 | 法师 |
| `manaPenetration0` | 法术固定穿透 | 法师 |
| `attackRangeUp` | 攻击距离加成（战士用） | 战士 |
| `healthSteal` | 物理吸血率，吸血量 = `damage * healthSteal * 0.1`，上限 `maxHP * 2%` | 战士, 弓手 |
| `manaHealthSteal` | 法术吸血率，吸血量 = `damage * manaHealthSteal * 0.33 * 0.1`（33%效率） | 法师 |

### 3.3 核心防御属性 (防守端)

| 代码属性 | 说明 |
|---|---|
| `defence` | 物理防御力，减免公式 `承伤 = 100 / (100 + 有效防御)` |
| `manaDefence` | 法术抗性，法师伤害减免使用同一公式但取 `MobAttributes.manaDefence` |
| `maxHealth` | 最大生命值（通过盔甲等装备间接加成） |
| `extraSwiftness` | 敏捷，决定闪避率（见3.5节） |
| `healingAmplification` | 治疗增幅，受治疗效果 = 基础治疗 * (1 + 治疗增幅) |

### 3.4 伤害乘区属性 (增伤端)

| 代码含义 | 在代码中的体现 | 说明 |
|---|---|---|
| **基础伤害** | `baseDamage = attackDamage(player) * rate` | 攻击力 × 技能倍率（战士/弓手） |
| | `baseDamage = manaDamage(player) * num` | 法术强度 × 倍率（法师） |
| **额外伤害 (exDamage)** | `exDamage` 变量累加 | 灵魂收割者、刀光剑影/热能注入等技能额外附加 |
| **真实伤害 (trueDamage)** | `trueDamage` 变量累加 | 无视防御的直接伤害（剑术热诚1%、弓术热诚1%、恃强凌弱等） |
| **伤害增强 (damageEnhance)** | 加法叠加 | 破绽观察/习惯获取(+2%)、CommonDamageUpOrDown、Attack/ManaDamageEnhance |
| **普通攻击增伤** | `NormalAttackDamageEnhance` | 对普攻独立乘区（战士: `getPlayerNormalSwordAttackDamageEnhance`，弓手: `getPlayerNormalBowAttackDamageEnhance`） |
| **最终伤害乘区** | `DamageInfluence.getPlayerFinalDamageEnhance(player, mob)` | 独立的最终乘法乘区 |
| **总伤害比率** | `DamageInfluence.getPlayerTotalDamageRate(player)` | 战士/弓手专用，对 damage 和 trueDamage 均生效 |
| **怪物承伤修正** | `DamageInfluence.getMonsterControlDamageEffect(player, mob)` | 怪物侧的控制/减伤修正 |
| **元素增伤** | `(1 + ElementDamageEnhance) * ElementDamageEffect` | 元素因子系统的独立乘法乘区 |
| **法师额外修正** | `DamageInfluence.getAdjustManaDamageRate(player, mob)` | 法师专用最终调整乘区 |

### 3.5 闪避率公式

```
闪避率 = 敏捷(extraSwiftness)分段函数, 敏捷上限90:

  swift ≤ 10:  swift * 2%          → 最大值 20%
  swift ≤ 20:  20% + (swift-10)*1.5% → 最大值 35%
  swift ≤ 30:  35% + (swift-20)*1%  → 最大值 45%
  swift > 30:  45% + (swift-30)*0.5% → 最大值 75%

最终闪避率 = 上述结果 * 0.5 (即硬上限 25%)
```

### 3.6 吸血公式

```
物理吸血:
  单次吸血量 = 造成伤害 * healthSteal * 0.1
  单次上限 = maxHP * 2%

法术吸血:
  单次吸血量 = 造成伤害 * manaHealthSteal * 0.33 * 0.1 (物理的33%效率)
  仅在 Damage.causeRateApDamageWithElement 中触发
  healByHealthSteal(mob, player, totalDamage * manaHealthSteal * 0.33)
```

### 3.7 防御减免公式

```
物理: 承伤 = 100 / (100 + max(0, defence * (1 - defencePenetration) - defencePenetration0))
法术: 承伤 = 100 / (100 + max(0, manaDefence * (1 - manaPenetration) - manaPenetration0))

特殊: 南海A饰品可无视防御 (defenceAfterCompute = 0, 当目标血量 < 40%)
```

### 3.8 三职业属性差异总结

| 维度 | 战士 (Sword) | 弓手 (Bow) | 法师 (Sceptre) |
|---|---|---|---|
| **主属性** | `attackDamage` | `attackDamage` | `manaDamage` |
| **攻击目标防御** | `defence` (物理) | `defence` (物理) | `manaDefence` (法术) |
| **穿透属性** | `defencePenetration` + `0` | `defencePenetration` + `0` | `manaPenetration` + `0` |
| **暴击体系** | 标准暴率/暴伤 | 标准暴率/暴伤 + 额外必暴来源(BoneImpKnife/BowCurios6) | 不使用暴击 |
| **真实伤害** | 剑术热诚1%、战争热诚(层数*1%)、恃强凌弱(至多20%)、城堡攻击甲 | 弓术热诚1%、城堡迅捷甲 | 无(所有伤害经法抗减免) |
| **吸血** | healthSteal, 满效率 | healthSteal, 满效率 | manaHealthSteal, 33%效率 |
| **独立乘区** | NormalSwordAttackEnhance, TotalDamageRate | NormalBowAttackEnhance, TotalDamageRate | AdjustManaDamageRate |
| **额外伤害来源** | 灵魂收割者、刀光剑影(200%)、本源具象 | 灵魂收割者、热能注入(200%)、禁忌迅捷甲 | 无 exDamage 机制 |

---

## 四、战斗公式 (基于实际代码)

### 4.1 战士 (Sword) 近战普攻 — `AttackEvent.attackToMonster`

**代码路径**: `fun.wraq.core.AttackEvent#attackToMonster`

```
① baseDamage = PlayerAttributes.attackDamage(player) * rate
   rate 累加来源:
     - 技能倍率参数
     - DamageInfluence.getPlayerNormalAttackBaseDamageEnhance(player, 0)  [主攻击时]
     - AttackCurios4.getAttackDamageRate(player, monster)                [主攻击时]

② exDamage (额外物理伤害, 累加叠加):
     - HuskSword.getHuskSwordExDamage(player, monster)         // 灵魂收割者
     - AttackEventModule.SwordSkill12(data, player, baseDamage) // 刀光剑影 (充能满 +200%)
     - AttackEventModule.SoulSwordActive(player)               // 本源具象

③ trueDamage (真实伤害, 无视防御):
     - SeaSword.getSeaSwordExDamage(player, monster)             // 灵魂救赎者
     - AttackEventModule.SwordSkill0(data, baseDamage)           // 剑术热诚 (攻击力1%)
     - AttackEventModule.SwordSkill13(data, player, baseDamage)  // 战争热诚 (层数*1%)
     - AttackEventModule.SwordSkill14(data, player, baseDamage, monster) // 恃强凌弱 (至多20%)
     - CastleAttackArmor.ExIgnoreDefenceDamage(player)
     - SakuraSword.SakuraDemonSword(player, damageBeforeDefence) // 妖刀

④ damageEnhance (伤害增幅, 加法叠加):
     - AttackEventModule.SwordSkill3(data, player, monster)     // 破绽观察 (至多2%)
     - DamageInfluence.getPlayerCommonDamageUpOrDown(player, monster)
     - DamageInfluence.getPlayerAttackDamageEnhance(player, monster)

⑤ 暴击:
     if crit:
       damageBeforeDefence = baseDamage * (1 + critDamage)
     else:
       damageBeforeDefence = baseDamage

⑥ 乘区聚合:
     damageBeforeDefence *= (1 + damageEnhance) * (1 + NormalAttackDamageEnhance)
     exDamage *= (1 + damageEnhance)
     trueDamage *= (1 + damageEnhance)
     damageBeforeDefence += exDamage
     // trueDamage += SakuraSword 妖刀

⑦ 最终乘区:
     damageBeforeDefence *= (1 + getPlayerFinalDamageEnhance)
     trueDamage *= (1 + getPlayerFinalDamageEnhance)

⑧ 防御减免:
     damage = damageBeforeDefence * defenceDamageDecreaseRate(defence, defPen, defPen0)
     // 公式: 100 / (100 + max(0, defence*(1-defPen) - defPen0))

⑨ 总伤害与怪物控制:
     damage *= getPlayerTotalDamageRate(player)
     trueDamage *= getPlayerTotalDamageRate(player)
     damage *= getMonsterControlDamageEffect(player, monster)
     trueDamage *= getMonsterControlDamageEffect(player, monster)

⑩ 元素乘区:
     ElementDamageEffect = Element.ElementEffectAddToEntity(player, monster, elementType, elementValue, isPhysical, damage+trueDamage)
     damage *= (1 + ElementDamageEnhance) * ElementDamageEffect
     trueDamage *= (1 + ElementDamageEnhance) * ElementDamageEffect

⑪ 最终伤害:
     totalDamage = damage + trueDamage
     Damage.beforeCauseDamage(player, monster, totalDamage)
     Damage.causeDirectDamageToMob(player, monster, totalDamage)
     Compute.healByHealthSteal(player, monster, damage)  // 仅对damage部分吸血
```

### 4.2 弓手 (Bow) 箭矢 — `MyArrow.causeDamage`

**代码路径**: `fun.wraq.core.bow.MyArrow#causeDamage`

```
弓手公式与战士基本相同，差异点:

① baseDamage = myArrow.BaseDamage * rate
   rate 累加 (主射击时):
     - DamageInfluence.getPlayerNormalAttackBaseDamageEnhance(player, 1)  [注: 参数1而非0]
     - BowCurios0.getArrowBaseDamageEnhanceRate(player)
     - BowCurios5.getExArrowDamageRate(player, mob)
     - QuiverAttack.getExAttackRate(player)
   rate 累加 (无条件下):
     - StableTierAttributeModifier.baseArrowDamageEnhanceRate

② exDamage:
     - AttackEventModule.BowSkill12(data, player, baseDamage) // 热能注入 (充能满 +200%)
     - HuskSword.getHuskSwordExDamage(player, mob)            // 灵魂收割者
     - TabooSwiftArmor.ExDamage(player)

③ trueDamage:
     - AttackEventModule.BowSkill0(data, baseDamage)          // 弓术热诚 (攻击力1%)
     - SeaSword.getSeaSwordExDamage(player, mob)              // 灵魂救赎者
     - CastleSwiftArmor.ExIgnoreDefenceDamage(player)

④ damageEnhance:
     - BowSkillTree.getSkillIndex3DamageEnhanceRate(player, mob) // 习惯获取 (至多2%*, 10次攻击达最大值)
     - AttackEventModule.NetherBow(player, mob)                  // 夸塔兹长弓
     - DamageInfluence.getPlayerCommonDamageUpOrDown(player, mob)
     - DamageInfluence.getPlayerAttackDamageEnhance(player, mob)

⑤ 暴击 (额外必暴来源):
     - myArrow.certainlyCritical (构造参数指定)
     - BoneImpKnife.passive(player, mob)
     - BowCurios6.isSurelyCrit(player, mob)

⑥ 主射击特有触发:
     - EnhanceNormalAttackModifier.onHitEffect(player, mob, 1)  [元素影响在主射击时]
     - OnArrowHitEffectCurios.hit(player, mob)
     - Quiver.onArrowHit(player)
     - BowNewSkillPassive0.onArrowHit(player, mob)
     - CastleBow.onNormalAttack(player, mob, damage)

其余乘区顺序与战士一致: ⑦⑧⑨⑩⑪ 完全相同
```

### 4.3 法师 (Sceptre) 法术攻击 — `Damage.causeRateApDamageWithElement`

**代码路径**: `fun.wraq.process.func.damage.Damage#causeRateApDamageWithElement`

```
法师公式结构与战士/弓手有显著差异 (无法师暴击体系，无trueDamage):

① baseDamage = PlayerAttributes.manaDamage(player) * num

② exDamage = 0  [法师无exDamage累加机制]

③ damageEnhance (加法叠加):
     - DamageInfluence.getPlayerCommonDamageUpOrDown(player, mob)
     - IceInstance.IceKnightHealthManaDamageFix(mob)          // 冰霜骑士修正
     - DamageInfluence.getPlayerManaDamageEnhance(player)     // 法术伤害提升

④ 防御减免 (先于增伤, 与战士弓手顺序不同):
     defenceDamageDecreaseRate = Damage.defenceDamageDecreaseRate(player, mob,
         MobAttributes.manaDefence(mob), manaPenetration, manaPenetration0)
     baseDamage *= defenceDamageDecreaseRate
     exDamage *= defenceDamageDecreaseRate

⑤ 增伤聚合:
     totalDamage = (baseDamage + exDamage) * (1 + damageEnhance) * (1 + getPlayerFinalDamageEnhance)

⑥ 元素:
     if isPower:
       ElementDamageEffect = Element.ElementEffectAddToEntity(player, mob, elementType, elementValue, false, totalDamage)
     elementDamage = totalDamage * ((1 + ElementDamageEnhance) * ElementDamageEffect - 1)

⑦ 怪物控制与额外乘区:
     totalDamage *= getMonsterControlDamageEffect(player, mob)
     totalDamage *= (1 + ElementDamageEnhance) * ElementDamageEffect
     totalDamage *= (1 + getAdjustManaDamageRate(player, mob))  [法师专用乘区]

⑧ 最终:
     Damage.beforeCauseDamage(player, mob, totalDamage)
     Damage.causeDirectDamageToMob(player, mob, totalDamage)
     Compute.healByHealthSteal(player, mob, totalDamage * manaHealthSteal * 0.33)  [33%效率]
     Compute.manaDamageExEffect(player, mob, totalDamage)       // 法术额外真伤
     ManaCurios1.getManaDamageExTrueDamage(player, mob, totalDamage)  // 魔核额外真伤

     if isPower:
       Compute.additionEffects(player, mob, totalDamage, 1)
       OnPowerCauseDamageEquip.causeDamage(player, mob)
       ManaNewSkillPassive0.onManaPowerHit(player, mob)
       ManaSkillTree.handleManaDamageExTrueDamage(player, mob, totalDamage)
```

### 4.4 防御减免公式 (三职业共用)

```
物理: 承伤 = 100 / (100 + max(0, defence * (1 - defencePenetration) - defencePenetration0))
法术: 承伤 = 100 / (100 + max(0, manaDefence * (1 - manaPenetration) - manaPenetration0))

防御 | 承伤
  0  | 100%
 50  | 66.7%
100  | 50.0%
200  | 33.3%
300  | 25.0%
500  | 16.7%
```

### 4.5 关键公式差异对比

| 维度 | 战士 | 弓手 | 法师 |
|---|---|---|---|
| 基础伤害 | `ATK * rate` | `ATK * rate` | `AP * num` |
| 暴击 | 有 | 有 (+必暴来源) | 无 |
| trueDamage | 有 (多来源) | 有 (多来源) | 无 (但 manaDamageExEffect 存在) |
| exDamage | 有 (3来源) | 有 (3来源) | 无 |
| 防御减免时机 | 最终乘区后 | 最终乘区后 | 增伤+最终乘区前 |
| 吸血效率 | 100% | 100% | 33% |
| 独有乘区 | NormalSwordAttackEnhance, TotalDamageRate | NormalBowAttackEnhance, TotalDamageRate | AdjustManaDamageRate |

---

## 五、装备数值模型

### 5.1 装备品质系数 (锻造品质 Tier)

**代码路径**: `fun.wraq.process.system.forge.ForgeEquipUtils#tierValueAndDescriptionMap`

装备属性通过两层乘法缩放：(1) 锻造品质 Tier 倍率 + (2) 锻造等级加成。Tier 由锻造锤(hammerTier)和成功率表决定，每次锻造随机升级品质。

**装备最终属性** = `(物品基础值 + exValue) * Tier倍率 + forgingValue`

| Tier | 名称 | 属性倍率 | 风格颜色 |
|---|---|---|---|
| 0 | 粗糙 | 0.80 | 灰色 |
| 1 | 优秀 | 1.00 | 绿色 |
| 2 | 精良 | 1.10 | 青色 |
| 3 | 史诗 | 1.20 | 浅紫 |
| 4 | 传说 | 1.30 | 金色 |
| 5 | 神话 | 1.40 | 红色 |
| 6 | 终末 | 1.50 | 末地风格 |
| 7 | 思旧 | 1.65 | 月亮风格 |
| 8 | 追忆 | 1.80 | 月亮风格1 |
| 9 | 不可思议 | 2.00 | 力量风格 |
| 10 | 望尘莫及 | 2.25 | 城堡水晶 |
| 11 | 仅存于梦 | 2.50 | 樱花风格 |
| 12 | 绝无仅有 | 2.75 | YSR风格 |
| 13 | 巅峰之作 | 3.00 | 天空风格 |

**Tier 成长梯度**: 0→1(+25%), 1→5(+40%总, 即每级~7%), 5→9(+43%总, 即每级~9%), 9→13(+50%总, 即每级~11%)。高品质的边际收益递增，激励追求高 Tier。

### 5.2 锻造成功率 (Hammer Tier 决定)

每次锻造根据锤子等级(HammerTier 0-8)从对应概率表随机抽取品质提升：

| HammerTier | Tier0 | Tier1 | Tier2 | Tier3 | Tier4 | Tier5 | Tier6 | Tier7 | Tier8 | Tier9 | Tier10+ |
|---|---|---|---|---|---|---|---|---|---|---|---|
| 0 | 25% | 25% | 25% | 10% | 10% | 5% | - | - | - | - | - |
| 1 | 15% | 20% | 25% | 25% | 10% | 5% | - | - | - | - | - |
| 2 | 10% | 15% | 25% | 30% | 15% | 5% | - | - | - | - | - |
| 3 | 5% | 15% | 20% | 35% | 20% | 5% | - | - | - | - | - |
| 4 | - | - | 20% | 45% | 25% | 5% | 3% | 2% | - | - | - |
| 5 | - | - | - | 35% | 45% | 10% | 5% | 3% | 2% | - | - |
| 6 | - | - | - | - | 15% | 45% | 30% | 5% | 3% | 2% | - |
| 7 | - | - | - | - | - | 30% | 45% | 10% | 10% | 3% | 2% |
| 8 | - | - | - | - | - | - | 60% | 20% | 10% | 5% | 3%+2% |

### 5.3 装备属性计算模型

物品的基础属性值由开发者手动设置并注册到 `Utils.java` 的静态属性 Map 中（如 `Utils.attackDamage.put(swordItem, 150.0)`），而非通过公式自动生成。

**通用属性计算公式** (`ForgeEquipUtils#getTraditionalEquipBaseValue`):

```java
baseValue = Utils.someAttributeMap.get(item)  // 手设基础值
          + exValue                             // NBT附加属性 (ExBaseAttributeValueEquip接口)
result = baseValue * getTierValueEffect(tier)   // × Tier品质倍率
       + forgingValue(data, baseValue)          // + 锻造等级加成
```

**等级需求**: 如果玩家等级低于物品的 `levelRequire`，属性归零，无法使用。

**设计含义**: 物品数值非公式化生成，而是策划为每个物品单独配置基础值，再套用 Tier + 锻造两层乘法缩放。这件设计允许每个物品有独特的"风味"（例如某把剑基础攻击高但无附加属性），同时保持锻造系统的统一成长曲线。

### 5.4 锻造系统加成

**代码路径**: `fun.wraq.common.Compute#forgingValue(CompoundTag data, double baseValue)`

锻造加成为分段线性函数。`forgingLevel` 包含基础锻造等级 + 每种锻造纸(ForgePaper)额外+1级加成：

```java
forgingLevel = data.getInt("Forging") + forgePaperCount;
```

**加成公式 (基础值倍率)**:

```
forgingLevel ≤ 10:  baseValue * 0.04 * forgingLevel
forgingLevel ≤ 20:  baseValue * (0.08 * (forgingLevel - 10) + 0.4)
forgingLevel ≤ 24:  baseValue * (0.16 * (forgingLevel - 20) + 1.2)
forgingLevel > 24:  baseValue * (0.32 * (forgingLevel - 24) + 1.84)
```

| 锻造等级 | 基础加成倍率 | 等效百分比 |
|---|---|---|
| 1 | 0.04 | +4% |
| 5 | 0.20 | +20% |
| 10 | 0.40 | +40% |
| 15 | 0.80 | +80% |
| 20 | 1.20 | +120% |
| 21 | 1.36 | +136% |
| 22 | 1.52 | +152% |
| 23 | 1.68 | +168% |
| 24 | 1.84 | +184% |
| 25 | 2.16 | +216% |
| 28 | 3.12 | +312% |

**分段梯度设计意图**: 每段斜率翻倍 — 0.04(1-10级) → 0.08(11-20级) → 0.16(21-24级) → 0.32(25级以上)，鼓励玩家追求高锻造成长，但 24 级后需要极稀有的锻造纸(ForgePaper)来突破门槛。

**最终装备数值** = 基础数值 + forgingValue(data, 基础数值) = 基础数值 * (1 + 锻造加成倍率)

---

## 六、怪物数值模型

### 6.1 怪物等级模板 (同等级普通怪物)

| 属性 | 公式 |
|---|---|
| HP | `80 * Lv^0.75 * typeMultiplier` |
| ATK | `6 * Lv^0.55 * typeMultiplier` |
| DEF | `2 * Lv^0.55 * typeMultiplier` |
| manaDefence | `2 * Lv^0.55 * typeMultiplier` (与DEF同值曲线) |

怪物经验采用百分比制（见 §2.2），击杀普通怪物默认 `num = 0.02`（怪物等级对应升级经验的 2%），不同内容类型使用不同 `num` 值，不再由怪物属性公式决定。

### 6.2 怪物类型系数

| 类型 | HP | ATK | DEF |
|---|---|---|---|---|
| 普通 | 1.0 | 1.0 | 1.0 |
| 精英 | 3.0 | 1.5 | 1.5 |
| Boss | 15.0 | 2.5 | 2.0 |
| 世界Boss | 50.0 | 3.5 | 2.5 |

### 6.3 同等级玩家 vs 怪物 (Lv 100, Tier1优秀装备, forgingLevel 5: +20%)

| | 玩家 (战士) | 普通怪物 | 精英 | Boss |
|---|---|---|---|---|
| HP | ~6,000 | ~2,500 | ~7,500 | ~37,500 |
| ATK/AP | 204 (170*1.0+34) | ~76 | ~114 | ~190 |
| DEF | ~150 | ~25 (承伤80%) | ~38 (承伤72%) | ~50 (承伤67%) |
| 单次非暴伤害 | ~163 | — | — | — |
| TTK (玩家→怪) | — | ~3.8s (8击) | ~11s | ~55s |
| TTK (怪→玩家) | — | ~26s | ~17s | ~10s |

*TTK 基于战士攻速 0.5s/次计算。玩家 ATK 公式: `170(base)*1.0(Tier1) + 170*0.04*5(forgingLv5) = 204`*

### 6.4 怪物等级缩放系数

章节地图怪物等级区间:
- 第1章 (平原): Lv 1-25
- 第2章 (森林): Lv 20-40
- 第3章 (沙漠): Lv 35-60
- 第4章 (雪原): Lv 55-80
- 第5章 (下界): Lv 75-110
- 第6章 (末地): Lv 105-150
- 第7章 (星界): Lv 145-200
- 第8章+ : Lv 195-300

---

## 七、职业平衡

### 7.1 三职业伤害管道差异

三职业的伤害计算共享同一套防御减免公式 (`100 / (100 + 有效防御)`)，但核心流程差异如下：

| 维度 | 战士 (Sword) | 弓手 (Bow) | 法师 (Sceptre) |
|---|---|---|---|
| **主属性** | `attackDamage` | `attackDamage` | `manaDamage` |
| **目标防御** | `defence` (物理) | `defence` (物理) | `manaDefence` (法术) |
| **穿透属性** | `defencePenetration` + `defencePenetration0` | 同战士 | `manaPenetration` + `manaPenetration0` |
| **暴击** | 标准暴率×暴伤 | 暴率×暴伤 + 额外必暴来源(BoneImpKnife/BowCurios6) | 无暴击体系 |
| **攻击距离** | 近战 3~6格 | 远程 15~20格 | 远程 10~15格 |
| **攻速参考** | ~0.5秒/次 | ~0.8秒/次 | ~0.6秒/次 |
| **exDamage** | 灵魂收割者、刀光剑影(充能+200%)、本源具象 | 热能注入(充能+200%)、灵魂收割者、禁忌迅捷甲 | 无 |
| **trueDamage** | 剑术热诚(1%ATK)、战争热诚(层数×1%)、恃强凌弱(至多20%)、城堡攻击甲 | 弓术热诚(1%ATK)、城堡迅捷甲 | 无 (替代机制: manaDamageExEffect, ManaCurios1 额外真伤) |
| **防御减免时机** | 最终乘区 **后** | 最终乘区 **后** | 增伤与最终乘区 **前** |
| **吸血** | `healthSteal`, 满效率 | `healthSteal`, 满效率 | `manaHealthSteal`, 33%效率 |
| **独有乘区** | `NormalSwordAttackEnhance`, `PlayerTotalDamageRate` | `NormalBowAttackEnhance`, `PlayerTotalDamageRate` | `AdjustManaDamageRate` |

### 7.2 生存能力差异

战士通过更高基础血量（盔甲装备的 def/maxHealth 属性更优）和满效率吸血获得生存优势。弓手靠远程安全位置。法师靠更高的单次爆发和独立乘区达成速杀。

| 生存维度 | 战士 | 弓手 | 法师 |
|---|---|---|---|
| 受击频率 | 最高 (近战) | 中低 (远程) | 中 (中程) |
| HP/DEF 基数 | 高 (盔甲侧重防御) | 中 | 低 |
| 吸血恢复 | 满效率 | 满效率 | 33%效率 |
| 闪避来源 | 敏捷 (extraSwiftness) | 同战士 | 同战士 |
| 综合生存评分 | 1.5 | 0.9 | 0.7 |

### 7.3 输出期望对比 (Lv100, Tier1, forgingLv5)

| | 战士 | 弓手 | 法师 |
|---|---|---|---|
| 主属性基础值 | ATK 204 | ATK 180 | AP 265 |
| 倍率 (技能/普攻) | rate 1.15 | rate 1.25 | num 1.5 |
| 原始伤害 | 204×1.15=235 | 180×1.25=225 | 265×1.5=398 |
| 暴击期望乘数 | 1+0.35×0.80=1.28 | 1+0.40×0.90=1.36 | 1.0 (无暴击) |
| 增伤乘区 (damageEnhance等) | 1.22×1.08=1.32 | 1.18×1.10=1.30 | 1.25×1.10=1.375 |
| 最终乘区 (FinalDamageEnhance) | ×1.10 | ×1.10 | ×1.10 |
| 独有乘区 (TotalRate/AdjustMana) | ×1.0 | ×1.0 | ×1.08 |
| 防御减免 (承伤≈80-92%) | ×0.92 | ×0.92 | ×0.97 (法抗低) |
| **单次伤害期望** | ~305 | ~301 | ~624 |
| **攻速** | 2次/秒 | 1.25次/秒 | 1.67次/秒 |
| **DPS** | ~610 | ~376 | ~1040 |
| **TTK (HP 2500普通怪)** | ~4.1秒 | ~6.6秒 | ~2.4秒 |

*法师 DPS 最高但生存评分最低；弓手最安全但单体 DPS 最低；战士居中。组队时战士坦克+法师输出+弓手辅助形成互补。*

### 7.4 组队协同

- 2人组队: 怪物HP ×1.8, 经验 ×1.1/人
- 3人组队: 怪物HP ×2.5, 经验 ×1.05/人
- 4人组队: 怪物HP ×3.2, 经验 ×1.0/人
- 5人组队: 怪物HP ×3.8, 经验 ×0.95/人

---

## 八、经济系统

### 8.1 货币体系

| 货币 | 用途 | 获取方式 |
|---|---|---|
| VB (虚拟币) | 市场交易、基础购买 | 杀怪掉落、任务、出售物品 |
| 金币 | 特殊商店、锻造 | Boss掉落、活动 |
| 声望币 | 声望商店 | 区域任务、声望委托 |
| 活动币 | 限时商店 | 节日活动、赛季通行证 |

### 8.2 掉落经济

```
怪物VB掉落 = 怪物等级 * (1 + 额外产出%) * 类型系数
普通: Lv * 1
精英: Lv * 5
Boss: Lv * 30
```

### 8.3 经济平衡参考 (Lv100 玩家每日)

| 来源 | VB收入 |
|---|---|
| 击杀怪物 (200只) | ~20,000 |
| 日常任务 (5个) | ~30,000 |
| Boss击杀 (2只) | ~6,000 |
| 副本奖励 | ~15,000 |
| **日均总收入** | **~71,000** |

| 消耗 | VB支出 |
|---|---|
| 装备锻造 (1次) | ~10,000 |
| 药水补给 | ~5,000 |
| 市场交易税 | ~3,000 |
| 传送费用 | ~2,000 |
| **日均总支出** | **~20,000** |

**日均净收入: ~51,000 VB** — 支持约每周一次重大装备升级

---

## 九、关键数值上限一览

| 属性 | 软上限 | 硬上限 | 说明 |
|---|---|---|---|
| 暴击率 | 60% | 80% | 超过60%后收益减半 |
| 暴击伤害 | 200% | 250% | 含基础150% |
| 闪避率 | 20% | 25% | 敏捷90可达上限 |
| 冷却缩减 | 50% | 60% | 技能急速150可达50% CDR |
| 吸血率 | 20% | 30% | 单次上限2%最大生命 |
| 百分比穿透 | 40% | 60% | PvE有效上限 |
| 额外产出 | 80% | 100% | - |
| 移动速度 | 130% | 150% | - |
| 最终伤害加成 | 50% | 80% | 稀有乘区, 严格控制来源 |
| 伤害减免 | 60% | 75% | 包含防御减免后的额外减免 |

---

## 十、伤害计算完整示例 (基于实际代码)

**场景**: Lv100 战士 vs Lv100 普通怪物, Tier1(优秀)装备, forgingLevel 5

### 10.1 战士普攻 (AttackEvent.attackToMonster)

```
玩家属性:
  attackDamage = 170 (武器基础值) * 1.0 (Tier1) + 170*0.04*5 (forgingLv5) = 204
  critRate = 0.35
  critDamage = 0.80 (面板暴伤加成)
  defencePenetration = 0.25
  defencePenetration0 = 10
  getPlayerNormalAttackBaseDamageEnhance = 0.10 (AttackCurios4, 主攻击时)
  getPlayerCommonDamageUpOrDown = 0.05
  getPlayerAttackDamageEnhance = 0.15
  NormalAttackDamageEnhance = 0.08 (NormalSwordAttackEnhance)
  getPlayerFinalDamageEnhance = 0.10
  getPlayerTotalDamageRate = 1.0

怪物属性:
  defence = 25
  getMonsterControlDamageEffect = 1.0

元素: 玩家无元素因子 (ElementDamageEffect = 1.0)

计算流程 (按代码顺序):

① baseDamage  = attackDamage * rate
     rate = 1.0 + 0.10(getNormalAttackBaseDamageEnhance) + 0.05(AttackCurios4) = 1.15
     baseDamage = 204 * 1.15 = 234.6

② exDamage  = 0 (假定无灵魂收割者等额外效果触发)
③ trueDamage = 0.01 * 234.6 (剑术热诚1%) + 0(其他) = 2.35

④ damageEnhance = 0.02(SwordSkill3破绽观察) + 0.05(Common) + 0.15(AttackEnhance) = 0.22

⑤ 暴击判定: random(0,1) < 0.35 → 暴击!
     damageBeforeDefence = baseDamage * (1 + critDamage) = 234.6 * 1.80 = 422.3

⑥ 乘区聚合:
     damageBeforeDefence *= (1 + damageEnhance) * (1 + NormalAttackDamageEnhance)
                         = 422.3 * 1.22 * 1.08 = 556.0
     exDamage *= 1.22 = 0
     trueDamage *= 1.22 = 2.35 * 1.22 = 2.87
     damageBeforeDefence += exDamage = 556.0

⑦ 最终乘区:
     damageBeforeDefence *= (1 + 0.10) = 556.0 * 1.10 = 611.6
     trueDamage *= 1.10 = 2.87 * 1.10 = 3.16

⑧ 防御减免:
     承伤 = 100/(100 + max(0, 25*(1-0.25) - 10)) = 100/(100+8.75) = 100/108.75 = 0.9195
     damage = 611.6 * 0.9195 = 562.4

⑨ 总伤害与怪物控制:
     damage *= 1.0(getPlayerTotalDamageRate) = 562.4
     trueDamage *= 1.0 = 3.16
     damage *= 1.0(getMonsterControlDamageEffect) = 562.4
     trueDamage *= 1.0 = 3.16

⑩ 元素乘区 (无元素因子, =1.0):
     damage *= 1.0, trueDamage *= 1.0

⑪ 最终:
     totalDamage = 562.4 + 3.16 = 565.6 → 取整显示 "566"
     Damage.causeDirectDamageToMob(player, monster, 566)

⑫ 吸血:
     healByHealthSteal(player, monster, 562)
     = 562 * 0.05(吸血率) * 0.1 = 2.81 → 受上限 maxHP*2% 约束

结果:
  暴击: 566 伤害 (黄色跳字)
  非暴击期望: 234.6*1.22*1.08*1.10*0.9195 + 2.35*1.22*1.10 ≈ 312 + 3 = 315
  DPS ≈ 315*(1-0.35) + 566*0.35 = 205 + 198 = 403 每击, /0.5s = 806 DPS
  TTK (普通怪 HP 2500) ≈ 2500/806 ≈ 3.1 秒
```

### 10.2 法师法术 (Damage.causeRateApDamageWithElement)

```
玩家属性:
  manaDamage = 280 (法杖基础 * Tier1 * forging加成)
  manaPenetration = 0.30
  manaPenetration0 = 15
  getPlayerCommonDamageUpOrDown = 0.05
  getPlayerManaDamageEnhance = 0.20
  getPlayerFinalDamageEnhance = 0.10
  getAdjustManaDamageRate = 0.08

怪物:
  manaDefence = 25

计算:
  baseDamage = 280 * 1.5(num=技能倍率) = 420
  damageEnhance = 0.05 + 0.20 = 0.25

④ 防御减免 (先于增伤):
  承伤 = 100/(100 + max(0, 25*(1-0.30) - 15)) = 100/(100+2.5) = 0.9756
  baseDamage *= 0.9756 = 409.8

⑤ 增伤聚合:
  totalDamage = (409.8 + 0) * (1+0.25) * (1+0.10) = 409.8 * 1.25 * 1.10 = 563.5

⑦ 怪物控制 + 法师乘区:
  totalDamage *= getAdjustManaDamageRate = 563.5 * 1.08 = 608.6

⑧ 吸血:
  healByHealthSteal(mob, player, 608.6 * manaHealthSteal * 0.33)
  = 608.6 * 0.04 * 0.33 * 0.1 = 0.80 (极低, 因为33%效率)

最终伤害: 609 (浅紫色跳字)
```

---

## 十一、数值基准体系 (2026-05-11 重构)

基于上述战斗公式，统一推导了所有武器/防具/怪物的基准数值，确保 Lv1→300 平滑演进。

### 11.1 核心基准公式

**武器 ATK (分段指数, 锚点: Lv1=15, Lv50=100, Lv100=280, Lv200=1100, Lv300=3200)**:

```
Sword ATK(level):
  Lv   1-50:  15   * e^(0.03873 * (Lv - 1))     → Lv50 = 100
  Lv  50-100: 100  * e^(0.02059 * (Lv - 50))    → Lv100 = 280
  Lv 100-200: 280  * e^(0.01368 * (Lv - 100))   → Lv200 = 1100
  Lv 200-300: 1100 * e^(0.01068 * (Lv - 200))   → Lv300 = 3200

法杖 manaDamage  = 剑 ATK * 2.2
弓 ATK           = 剑 ATK * 0.95              (远程折价 5%)
```

**怪物属性**:

```
怪物 HP   ≈ 武器ATK * 76                       (普通怪 TTK ≈ 13-18 击)
怪物 ATK  ≈ level                              (Lv100=100, Lv200=192实际, Lv300=282实际)
怪物 DEF  ≈ 2 + 0.4 * level                    (Lv1=2.4 → Lv300=122)
Boss HP   = 普通怪 HP * 15 (ATK*2.5, DEF*2.0)
```

**防具基础属性 (Chest)**:

```
Chest DEF(level):
  Lv ≤ 100:   2 + 0.6 * level                   (Lv1=2.6 → Lv100=62)
  Lv > 100:   2 + 0.6 * level                   (统一公式, Lv300=182)

Chest HP(level):
  Lv ≤ 100:   10 * level                        (Lv1=10 → Lv100=1000)
  Lv 100-200: 1000 * e^(0.01609 * (Lv - 100))  (→ Lv200=5000)
  Lv 200-300: 5000 * e^(0.01386 * (Lv - 200))  (→ Lv300=20000)

Legs  = Chest * 0.6, Head = Chest * 0.4, Boots = Chest * 0.2
```

**有效属性** = `baseValue * tierMultiplier(0.80~3.00) + forgingValue(分段线性)`

Tier 倍率: 0(0.80) 1(1.00) 2(1.10) 3(1.20) 4(1.30) 5(1.40) 6(1.50) 7(1.65) 8(1.80) 9(2.00) 10(2.25) 11(2.50) 12(2.75) 13(3.00)

### 11.2 Lv100 / Lv200 / Lv300 对照表

| 属性 | Lv 100 | Lv 200 | Lv 300 |
|---|---|---|---|
| 剑基础 ATK | 280 | 1100 | 3200 |
| 法杖基础 manaDamage | 616 | 2420 | 7040 |
| 弓基础 ATK | 266 | 1045 | 3040 |
| 普通怪物 HP | 21,000 | 100,000 | 400,000 |
| 普通怪物 ATK | 100 | 192 | 282 |
| 普通怪物 DEF / manaDef | 42 | 82 | 122 |
| Chest DEF (基础) | 62 | 122 | 182 |
| Chest HP (基础) | 1000 | 5000 | 20000 |
| Boss HP (×15, 1人) | 315,000 | 1,500,000 | 6,000,000 |
| Boss HP (4人组队) | 1,024,000 | 4,875,000 | 19,500,000 |
| 普通怪 TTK (~16击) | 7-9秒 | 9-11秒 | 10-13秒 |
| 玩家承伤/击 (avg gear) | ~40 | ~55 | ~60 |
| 玩家 0DEF 承伤/击 | ~100 | ~192 | ~282 |

### 11.3 乘区预算分配 (Lv 100 示例)

以 Lv100 Tier5(神话1.4x) + ForgingLv5(+0.2x) 的战士为例:

```
基础 ATK           = 280 * 1.6  = 448
damageEnhance      (25%)                     → 560
NormalAtkEnhance   (8%)                      → 605
FinalDamageEnhance (10%)                     → 666
暴击期望 (35%×1.8) (1.28x)                  → 852
DEF 减免 100/(100+42) (0.704)                → 600
元素乘区           (1.15x)                   → 690
怪物承伤修正       (1.0x)                    → 690
trueDamage+exDamage (~10%)                    → 759
─────────────────────────────────────────────────
最终伤害/击        ≈ 759
普通怪 HP 21,000   → TTK ≈ 28 击 (14秒)
若 avg gear(1.2x)  → TTK ≈ 38 击 (19秒)
若 BIS gear(3.0x)  → TTK ≈ 10 击 (5秒)
```

### 11.4 数值膨胀控制

| 指标 | Lv 1→300 增长倍数 | 年均膨胀率 |
|---|---|---|
| 武器基础 ATK | ×213 | ~40%/50级 |
| 有效伤害 (含 Tier+技能) | ×530 | ~65%/50级 |
| 普通怪物 HP | ×1140 | ~80%/50级 |
| Boss HP (4人) | ×1100 | ~78%/50级 |

每 50 级一个资料片梯度，怪物 HP 约增长 3-4 倍，与 §1.2 设计哲学一致。

### 11.5 数值源文件

| 文件 | 说明 |
|---|---|
| `data/vmd/balance/weapon_attributes.csv` | 武器基础属性 |
| `data/vmd/balance/armor_attributes.csv` | 防具基础属性 |
| `data/vmd/balance/mob_attributes.csv` | 怪物属性 |
| `data/vmd/balance/curio_attributes.csv` | 饰品基础属性及被动效果 |

所有 CSV 中的属性均为**基础值** (构造函数中注册到 Utils Map 的原始数值)。
Tier 倍率和锻造加成由 `ForgeEquipUtils.getTierValueEffect()` 和 `Compute.forgingValue()` 统一施加。

---

## 十二、饰品数值模型 (2026-05-12 新增)

### 12.1 饰品定位

饰品 (Curios) 通过 Curios API 提供额外装备槽位，是玩家构建配装的重要组成部分。与武器/防具不同，饰品不仅提供基础属性，还通过**被动效果**提供机制层面的增益。

饰品总数: **98 个** (继承 `WraqCurios` 的类)

### 12.2 饰品分类

| 类型 | 数量 | 说明 |
|---|---|---|
| **符文 (Rune)** | 18 | 提供被动机制，属性加成较少 |
| **章纹 (Crest)** | 10 | 可堆叠 (maxSlotSize=16)，提供元素值和递增属性 |
| **指环 (Ring)** | 10 | 纯属性饰品，少量被动 |
| **Boss掉落饰品** | 20 | 副本/Boss掉落，含独特被动 |
| **活动饰品** | 20 | 季节/节日限定，有特殊机制 |
| **支线护符 (Charm)** | 4 | 专业技能线奖励 (serial 1-10) |
| **随机饰品 (RandomCurios)** | 8 | 属性随机生成 |
| **冰圣饰品 (IceHoly)** | 5 | Lv230 冰元素体系 |
| **其他** | 3 | 烹饪、匠魂、合成品 |

### 12.3 属性基准

饰品的基础属性值约为同等级武器的 **30-60%**（因其提供被动机制补偿）。具体基准：

```
饰品 ATK(level)   ≈ 武器 ATK * 0.35       (攻击向饰品)
饰品 DEF(level)   ≈ 防具 Chest DEF * 0.25  (防御向饰品)
饰品 HP(level)    ≈ 防具 Chest HP * 0.30   (生命向饰品)
```

**章纹 (Crest) 多级属性**:
- 每级增长约 `baseValue * 0.1`，最高 16 级即 `baseValue * 2.5`
- 元素值每级 +0.03，最高 0.5

### 12.4 被动效果伤害增幅评估

被动效果的伤害增幅 (`estimatedDamageAmpPercent`) 为粗略估算（±10% 容差）：

| 增幅范围 | 数量 | 说明 |
|---|---|---|
| **0%** (纯防御/功能) | ~55 | 生存、移速、经验等非伤害被动 |
| **1-10%** | ~25 | 条件增伤、小幅度加成 |
| **10-25%** | ~12 | 显著增伤被动 |
| **25-50%** | ~6 | 高增伤被动，建议复核 |

### 12.5 标记为需复核的高增幅饰品

| 饰品 | 估算增幅 | 建议 |
|---|---|---|
| **FrameArrow (红莲弓矢)** | +50% | 弓技能伤害+100%冷却-1s，建议降至+50%/+0.5s |
| **BrokenBlade (剑气残刃)** | +50% | 同FrameArrow，剑技版，建议降至+50%/+0.5s |
| **TongTian (通天)** | +30% | 空中+30%通用增伤，条件易满足，建议降至+20% |
| **NanHai (南海A)** | +30% | 无视低血量目标全部防御，建议加内置CD或降至40%穿防 |
| **EndCuriosMana/Bow (跃迁/折跃)** | +33% | 穿透增伤叠加过高，建议降至+20%/穿透 |
| **CastleNewRune (物法兼修)** | +20% | 当前已禁用，若启用建议降至+10% |
| **DarkRune (暗影符文)** | +24% | 双被动组合，明镜止水部分建议降至+4%/物品 |
| **BlazeRune (炽焰符文)** | +25% | 双被动组合，建议微调至+20% |
| **Spring2025Scale (春晓鳞片)** | +5%×(tier+1) | T4时+25%全属性，跨乘区增幅过高，建议上限+15% |
| **MoonBelt (苍白之瀑)** | +20% | 充能机制复杂但上限高，建议降低存储倍率 |

### 12.6 饰品设计原则

1. **被动 < 属性**: 饰品的伤害增幅主要来自基础属性，被动效果提供 **0-25%** 的额外增伤（机制型被动）或 **0%**（防御/功能被动）
2. **条件触发**: 高增幅被动应有明确触发条件（如血量门槛、冷却、层数叠加），覆盖率控制在 **30-70%**
3. **乘区分散**: 避免多个饰品提供同一乘区的高额叠加
4. **等级锚点**: 饰品的属性基准参照同章节武器/防具等级设定
