# 运行时装备属性表命令：`/vmd itemattr`

该命令用于运行时修改 `fun.wraq.common.util.Utils` 第 45~101 行的静态装备属性表，不需要重启服务器。

## 权限

需要权限等级 **2**（OP）。

## 命令一览

```text
/vmd itemattr fields
/vmd itemattr list <item>
/vmd itemattr get <item> <attribute>
/vmd itemattr set <item> <attribute> <value>
/vmd itemattr reset <item>
/vmd itemattr reset <item> <attribute>
```

### `fields`

列出所有可修改字段：

```text
/vmd itemattr fields
```

### `list`

列出该物品当前在属性表中的所有非空记录：

```text
/vmd itemattr list vmd:plainsword0
/vmd itemattr list plainsword0
```

物品 ID 可以省略 `vmd:` 命名空间。

### `get`

查看单个字段：

```text
/vmd itemattr get plainsword0 attackDamage
```

### `set`

设置字段：

```text
/vmd itemattr set plainsword0 attackDamage 100
/vmd itemattr set plainsword0 maxHealth 500
/vmd itemattr set vmd:forestsword0 critRate 0.35
```

修改会直接作用于 `Utils` 的静态 Map，**立即影响后续属性计算**。

### `reset`

恢复为命令修改前的值：

```text
/vmd itemattr reset plainsword0 attackDamage
/vmd itemattr reset plainsword0
```

- 单字段 `reset` 只恢复该字段；
- 不带字段的 `reset` 恢复该物品全部命令修改；
- 原本不存在的字段会被移除。

## 字段说明

### 装备属性字段

包括 `attackDamage`、`critRate`、`critDamage`、`defence`、`manaDefence`、`maxHealth`、`manaDamage`、`maxMana`、`healthSteal`、`manaRecover`、`coolDownDecrease`、`movementSpeedCommon`、`elementStrength`、`finalDamageEnhance`、`attackDamageEnhance` 等，共 47 个数值属性。

完整列表以 `/vmd itemattr fields` 为准。

### 装备标签字段

以下字段是装备分类/机制标记，建议只使用 `0` 或 `1`：

```text
mainHandTag
offHandTag
shieldTag
armorTag
powerTag
passiveEquipTag
mobLevel
```

其中 `mobLevel` 实际表示装备等级标记；`mainHandTag`、`armorTag` 等会参与装备分类，修改后可能影响装备识别，请谨慎使用。

### 整数字段

```text
levelRequire
gemsTag
```

这两个字段只接受整数。

## 常用别名

| 别名 | 对应字段 |
|---|---|
| `attack`、`atk`、`damage` | `attackDamage` |
| `crit` | `critRate` |
| `critdmg` | `critDamage` |
| `health`、`hp`、`maxhp` | `maxHealth` |
| `speed` | `movementSpeedCommon` |
| `mana`、`maxmana` | `maxMana` |

## 生效与恢复

- `set` 立即修改内存中的 `Utils` Map；
- 不需要重新生成怪物或重启服务器；
- 命令会记录修改前的原值；
- `reset` 恢复原值；
- 服务器停止时自动恢复所有命令修改；
- 不写入配置文件或 NBT，重启服务器后恢复默认。

## 示例

给平原短匕增加攻击力：

```text
/vmd itemattr get plainsword0 attackDamage
/vmd itemattr set plainsword0 attackDamage 120
```

临时提高森林粉碎者的暴击率：

```text
/vmd itemattr set forestsword0 critRate 0.5
```

查看并恢复：

```text
/vmd itemattr list forestsword0
/vmd itemattr reset forestsword0 critRate
```

修改整数装备等级要求：

```text
/vmd itemattr set plainsword0 levelRequire 10
```
