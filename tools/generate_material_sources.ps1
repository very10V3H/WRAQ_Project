#Requires -Version 7.0
<#
.SYNOPSIS
    检索 VMD 源码，生成图鉴材料「获取途径」固定资产：assets/vmd/material_sources.json

.DESCRIPTION
    该脚本会扫描 src/main/java/fun/wraq 下的 Java 源码，提取：
      1. 怪物掉落表（MobSpawnController / NoTeamInstance 的 ItemAndRate 列表）
      2. 村民交易（TradeList / TradeListNew）
      3. VP 商店
      4. 锻造配方与可锻造物品
      5. 灌注配方
      6. 奖励箱
    将结果汇总为 物品注册名 -> 获取途径文本列表 的 JSON 文件。

    生成的文件为「固定资产」，构建/运行时不再需要脚本；仅当材料来源发生变化时重新运行即可：
        pwsh tools/generate_material_sources.ps1
#>

param(
    [string]$ProjectRoot = (Resolve-Path (Join-Path $PSScriptRoot '..')).Path
)

$ErrorActionPreference = 'Stop'

$sourceRoot = Join-Path $ProjectRoot 'src\main\java\fun\wraq'
$outputPath = Join-Path $ProjectRoot 'src\main\resources\assets\vmd\material_sources.json'

if (-not (Test-Path $sourceRoot)) {
    throw "Source root not found: $sourceRoot"
}

$javaFiles = Get-ChildItem -Path $sourceRoot -Recurse -Filter *.java
Write-Host "Scanning $($javaFiles.Count) java files..."

$cjkPattern = '[\u4e00-\u9fa5]'

# ----------------------------------------------------------------------
# 1. 物品注册名映射
# ----------------------------------------------------------------------
$fieldToId = @{}      # "Container.FIELD" -> id
$bareFieldToId = @{}  # "FIELD" -> id (static import)
$classToIds = @{}     # "ClassName" -> HashSet[id]

foreach ($file in $javaFiles) {
    $text = Get-Content -LiteralPath $file.FullName -Raw -Encoding UTF8
    $classMatch = [regex]::Match($text, '(?m)^public\s+(?:final\s+)?(?:abstract\s+)?class\s+(\w+)')
    $className = if ($classMatch.Success) { $classMatch.Groups[1].Value } else { $file.BaseName }

    # 仅解析物品注册文件，避免方块 / 属性 / 实体等注册名污染映射
    if ($text -notmatch 'DeferredRegister<Item>') { continue }

    foreach ($m in [regex]::Matches($text, '(?m)(\w+)\s*=\s*([A-Za-z_]\w*)\.register\(\s*"([a-z0-9_]+)"')) {
        $field = $m.Groups[1].Value
        $id = $m.Groups[3].Value
        $fieldToId["$className.$field"] = $id
        if (-not $bareFieldToId.ContainsKey($field)) {
            $bareFieldToId[$field] = $id
        }
    }

    foreach ($m in [regex]::Matches($text, '\.register\(\s*"([a-z0-9_]+)",\s*\(\)\s*->\s*new\s+([\w.]+)')) {
        $id = $m.Groups[1].Value
        $newClass = $m.Groups[2].Value.Split('.')[-1]
        if (-not $classToIds.ContainsKey($newClass)) {
            $classToIds[$newClass] = [System.Collections.Generic.HashSet[string]]::new()
        }
        [void]$classToIds[$newClass].Add($id)
    }
}

Write-Host "Registered items: $($fieldToId.Count)"

function Resolve-ItemRef {
    param([string]$container, [string]$field)
    if ($container) {
        $key = "$container.$field"
        if ($fieldToId.ContainsKey($key)) { return $fieldToId[$key] }
    }
    if ($bareFieldToId.ContainsKey($field)) { return $bareFieldToId[$field] }
    return $null
}

function Add-Source {
    param([hashtable]$map, [string]$id, [string]$source)
    if (-not $id -or -not $source) { return }
    if (-not $map.ContainsKey($id)) {
        $map[$id] = [System.Collections.Generic.List[string]]::new()
    }
    if (-not $map[$id].Contains($source)) {
        $map[$id].Add($source)
    }
}

$sources = @{}

# ----------------------------------------------------------------------
# 2. 怪物掉落
# ----------------------------------------------------------------------
$itemAndRatePattern = 'new ItemAndRate\(\s*(?:new ItemStack\(\s*)?([A-Za-z_]\w*)\.([A-Za-z_]\w*)\.get\(\)'
$mobFileCount = 0
foreach ($file in $javaFiles) {
    if ($file.Name -eq 'ItemAndRate.java') { continue }

    $text = Get-Content -LiteralPath $file.FullName -Raw -Encoding UTF8
    if ($text -notmatch 'new ItemAndRate\(') { continue }

    $isMobSource = $text -match 'extends\s+\w*(SpawnController|NoTeamInstance|JungleMobSpawnController)' `
        -or $file.Name -match '(SpawnController|Instance)\.java$' `
        -or $text -match '(getDropList|getRewardList)\s*\('
    if (-not $isMobSource) { continue }

    $mobName = $null
    foreach ($m in [regex]::Matches($text,
            '(?m)(?:public\s+)?static\s+(?:final\s+)?String\s+(?:mobName|MOB_NAME|NAME|Name)\s*=\s*"([^"]+)"')) {
        if ($m.Groups[1].Value -match $cjkPattern) { $mobName = $m.Groups[1].Value; break }
    }
    if (-not $mobName) {
        foreach ($m in [regex]::Matches($text, 'super\([\s\S]{0,300}?Te\.s\(\s*"([^"]+)"')) {
            if ($m.Groups[1].Value -match $cjkPattern) { $mobName = $m.Groups[1].Value; break }
        }
    }
    if (-not $mobName) {
        foreach ($m in [regex]::Matches($text, 'Te\.s\(\s*"([^"]+)"')) {
            $value = $m.Groups[1].Value
            if ($value -match $cjkPattern -and $value -notmatch '获得|持续|时间|你|已|提示|警告') {
                $mobName = $value
                break
            }
        }
    }
    if (-not $mobName) { continue }

    $isInstance = $file.FullName -match '\\instance\\' -or $text -match 'extends\s+NoTeamInstance'
    $sourceText = if ($isInstance) { "挑战「$mobName」获得" } else { "击杀「$mobName」概率掉落" }

    foreach ($m in [regex]::Matches($text, $itemAndRatePattern)) {
        $id = Resolve-ItemRef $m.Groups[1].Value $m.Groups[2].Value
        if ($id) { Add-Source $sources $id $sourceText }
    }
    $mobFileCount++
}
Write-Host "Mob drop files: $mobFileCount"

# ----------------------------------------------------------------------
# 3. 村民交易
# ----------------------------------------------------------------------
$villagerNames = @{}
$stringUtils = Join-Path $sourceRoot 'common\util\StringUtils.java'
if (Test-Path $stringUtils) {
    $text = Get-Content -LiteralPath $stringUtils -Raw -Encoding UTF8
    $start = $text.IndexOf('class VillagerName')
    if ($start -ge 0) {
        $end = $text.IndexOf('class NewVillagerName', $start)
        if ($end -lt 0) { $end = $text.Length }
        $region = $text.Substring($start, $end - $start)
        foreach ($m in [regex]::Matches($region, 'public\s+static\s+String\s+(\w+)\s*=\s*"([^"]+)"')) {
            $villagerNames[$m.Groups[1].Value] = $m.Groups[2].Value
        }
    }
}

$tradeFile = Join-Path $sourceRoot 'render\gui\villagerTrade\TradeList.java'
if (Test-Path $tradeFile) {
    $text = Get-Content -LiteralPath $tradeFile -Raw -Encoding UTF8
    $methodMatches = [regex]::Matches($text, '(?m)^    public static void (\w+)\(\)\s*\{')
    for ($i = 0; $i -lt $methodMatches.Count; $i++) {
        $start = $methodMatches[$i].Index
        $end = if ($i + 1 -lt $methodMatches.Count) { $methodMatches[$i + 1].Index } else { $text.Length }
        $block = $text.Substring($start, $end - $start)

        $merchant = $null
        if ($block -match 'StringUtils\.VillagerName\.(\w+)') {
            $code = $Matches[1]
            if ($villagerNames.ContainsKey($code)) { $merchant = $villagerNames[$code] }
        }
        if (-not $merchant) { continue }

        $arrayMatch = [regex]::Match($block, 'ItemStack\[\]\s+itemStacks\s*=\s*\{(.*?)\};', 'Singleline')
        if (-not $arrayMatch.Success) { continue }
        foreach ($m in [regex]::Matches($arrayMatch.Groups[1].Value, '([A-Za-z_]\w*)\.([A-Za-z_]\w*)\.get\(\)')) {
            $id = Resolve-ItemRef $m.Groups[1].Value $m.Groups[2].Value
            if ($id) { Add-Source $sources $id "在「$merchant」处获取" }
        }
    }
}

$tradeNewFile = Join-Path $sourceRoot 'render\gui\villagerTrade\TradeListNew.java'
if (Test-Path $tradeNewFile) {
    $text = Get-Content -LiteralPath $tradeNewFile -Raw -Encoding UTF8
    $methodMatches = [regex]::Matches($text, '(?m)^    public static void (\w+)\(\)\s*\{')
    for ($i = 0; $i -lt $methodMatches.Count; $i++) {
        $start = $methodMatches[$i].Index
        $end = if ($i + 1 -lt $methodMatches.Count) { $methodMatches[$i + 1].Index } else { $text.Length }
        $block = $text.Substring($start, $end - $start)

        $merchant = $null
        if ($block -match 'setMyVillagerData\w*\(\s*"([^"]+)"') {
            $merchant = $Matches[1]
        }
        if (-not $merchant) { continue }

        foreach ($m in [regex]::Matches($block, 'Pair\.of\(\s*Is\.n\(\s*([A-Za-z_]\w*)')) {
            $id = Resolve-ItemRef $null $m.Groups[1].Value
            if ($id) { Add-Source $sources $id "在「$merchant」处获取" }
        }
        foreach ($m in [regex]::Matches($block, 'new TradeListNew\(\s*([A-Za-z_]\w*)\.([A-Za-z_]\w*)\.get\(\)')) {
            $id = Resolve-ItemRef $m.Groups[1].Value $m.Groups[2].Value
            if ($id) { Add-Source $sources $id "在「$merchant」处获取" }
        }
    }
}

# ----------------------------------------------------------------------
# 4. VP 商店
# ----------------------------------------------------------------------
$vpStoreFile = Join-Path $sourceRoot 'process\system\vp\VpStore.java'
if (Test-Path $vpStoreFile) {
    $text = Get-Content -LiteralPath $vpStoreFile -Raw -Encoding UTF8
    foreach ($m in [regex]::Matches($text, '([A-Za-z_]\w*)\.([A-Za-z_]\w*)\.get\(\)')) {
        $id = Resolve-ItemRef $m.Groups[1].Value $m.Groups[2].Value
        if ($id) { Add-Source $sources $id '在 VP 商店兑换' }
    }
}

# ----------------------------------------------------------------------
# 5. 锻造产物
# ----------------------------------------------------------------------
$forgeRecipeFile = Join-Path $sourceRoot 'blocks\blocks\forge\ForgeRecipe.java'
if (Test-Path $forgeRecipeFile) {
    $text = Get-Content -LiteralPath $forgeRecipeFile -Raw -Encoding UTF8
    foreach ($m in [regex]::Matches($text, 'recipes\.put\(\s*([A-Za-z_]\w*)\.([A-Za-z_]\w*)\.get\(\)')) {
        $id = Resolve-ItemRef $m.Groups[1].Value $m.Groups[2].Value
        if ($id) { Add-Source $sources $id '在锻造台锻造获得' }
    }
}

foreach ($file in $javaFiles) {
    $text = Get-Content -LiteralPath $file.FullName -Raw -Encoding UTF8
    if ($text -notmatch 'public\s+List<ItemStack>\s+forgeRecipe\(\)') { continue }
    $classMatch = [regex]::Match($text, '(?m)^public\s+(?:final\s+)?(?:abstract\s+)?class\s+(\w+)')
    if (-not $classMatch.Success) { continue }
    $ids = $classToIds[$classMatch.Groups[1].Value]
    if ($ids) {
        foreach ($id in $ids) { Add-Source $sources $id '在锻造台锻造获得' }
    }
}

# ----------------------------------------------------------------------
# 6. 灌注产物
# ----------------------------------------------------------------------
$injectFile = Join-Path $sourceRoot 'blocks\blocks\inject\InjectRecipe.java'
if (Test-Path $injectFile) {
    $text = Get-Content -LiteralPath $injectFile -Raw -Encoding UTF8
    $pattern = 'injectingRecipeMap\.put\(\s*([A-Za-z_]\w*)\.([A-Za-z_]\w*)\.get\(\),\s*new InjectingRecipe\(\s*([A-Za-z_]\w*)\.([A-Za-z_]\w*)\.get\(\),\s*\d+,\s*([A-Za-z_]\w*)\.([A-Za-z_]\w*)\.get\(\)'
    foreach ($m in [regex]::Matches($text, $pattern)) {
        $id = Resolve-ItemRef $m.Groups[5].Value $m.Groups[6].Value
        if ($id) { Add-Source $sources $id '通过灌注台灌注获得' }
    }
}

# ----------------------------------------------------------------------
# 7. 奖励箱
# ----------------------------------------------------------------------
$bonusFile = Join-Path $sourceRoot 'process\system\bonuschest\BonusChestContent.java'
if (Test-Path $bonusFile) {
    $text = Get-Content -LiteralPath $bonusFile -Raw -Encoding UTF8
    foreach ($m in [regex]::Matches($text, 'new ItemAndMaxNum\(\s*([A-Za-z_]\w*)\.([A-Za-z_]\w*)\.get\(\)')) {
        $id = Resolve-ItemRef $m.Groups[1].Value $m.Groups[2].Value
        if ($id) { Add-Source $sources $id '从奖励箱中获得' }
    }
}

# ----------------------------------------------------------------------
# 8. 全局掉落（任意怪物概率掉落）与锻造碎片
# ----------------------------------------------------------------------
$mobSpawnFile = Join-Path $sourceRoot 'events\mob\MobSpawn.java'
if (Test-Path $mobSpawnFile) {
    $text = Get-Content -LiteralPath $mobSpawnFile -Raw -Encoding UTF8
    foreach ($m in [regex]::Matches($text, 'ItemAndRate\.send\(\s*\w+\s*,\s*([A-Za-z_]\w*)\.([A-Za-z_]\w*)\.get\(\)')) {
        $id = Resolve-ItemRef $m.Groups[1].Value $m.Groups[2].Value
        if ($id) { Add-Source $sources $id '击杀任意怪物概率掉落' }
    }
    foreach ($m in [regex]::Matches($text, 'ItemAndRate\.send\(\s*\w+\s*,\s*([A-Za-z_]\w*)\.([A-Za-z_]\w*)\.get\(\)\.getDefaultInstance\(\)')) {
        $id = Resolve-ItemRef $m.Groups[1].Value $m.Groups[2].Value
        if ($id) { Add-Source $sources $id '击杀任意怪物概率掉落' }
    }
}

# 锻造碎片：EquipPiece.onKillMob 会按怪物等级掉落 0 - 7 阶碎片
foreach ($m in [regex]::Matches((Get-Content -LiteralPath (Join-Path $sourceRoot 'common\registry\ModItems.java') -Raw -Encoding UTF8),
        'RegistryObject<Item>\s+(EQUIP_PIECE_(\d+))\s*=\s*ITEMS\.register\(\s*"([a-z0-9_]+)"')) {
    $tier = [int]$m.Groups[2].Value
    if ($tier -le 7) {
        Add-Source $sources $m.Groups[3].Value '击杀怪物概率掉落'
    }
}

# ----------------------------------------------------------------------
# 9. 每周商店 / 活动兑换 / 结晶商店
# ----------------------------------------------------------------------
$weeklyFile = Join-Path $sourceRoot 'render\gui\trade\weekly\WeeklyStorePools.java'
if (Test-Path $weeklyFile) {
    $text = Get-Content -LiteralPath $weeklyFile -Raw -Encoding UTF8
    foreach ($m in [regex]::Matches($text, 'new ItemStack\(\s*([A-Za-z_]\w*)\.([A-Za-z_]\w*)\.get\(\)')) {
        $id = Resolve-ItemRef $m.Groups[1].Value $m.Groups[2].Value
        if ($id) { Add-Source $sources $id '在每周商店兑换' }
    }
}

$storeFiles = [ordered]@{
    'render\gui\trade\single\SingleItemChangeRecipe.java'  = '无尽核心商店'
    'process\system\cooking\item\FoodCoinStoreRecipe.java' = '食物商店'
    'series\events\dragonboat\DragonBoatStoreRecipe.java'  = '端午活动商店'
    'series\events\summer2025\Summer2025StoreRecipe.java'  = '夏日活动商店'
    'series\events\midautumn\MidAutumnStoreRecipe.java'    = '中秋活动商店'
}
foreach ($entry in $storeFiles.GetEnumerator()) {
    $path = Join-Path $sourceRoot $entry.Key
    if (-not (Test-Path $path)) { continue }
    $text = Get-Content -LiteralPath $path -Raw -Encoding UTF8
    $storeName = $entry.Value
    $pattern = 'new SingleItemChangeRecipe\(\s*new ItemStack\(\s*([A-Za-z_]\w*)\.([A-Za-z_]\w*)\.get\(\),\s*\d+\s*\),\s*new ItemStack\(\s*([A-Za-z_]\w*)\.([A-Za-z_]\w*)\.get\(\)'
    foreach ($m in [regex]::Matches($text, $pattern)) {
        $id = Resolve-ItemRef $m.Groups[3].Value $m.Groups[4].Value
        if ($id) { Add-Source $sources $id "在「$storeName」兑换" }
    }
}

# 结晶商店：CrystalItem 的实例既可在商店购买，也可出售
if ($classToIds.ContainsKey('CrystalItem')) {
    foreach ($id in $classToIds['CrystalItem']) {
        Add-Source $sources $id '在「结晶商店」购买'
    }
}

# ----------------------------------------------------------------------
# 10. 汇总同类来源（例如把多个怪物合并为一条）
# ----------------------------------------------------------------------
function Compress-Sources {
    param([System.Collections.Generic.List[string]]$list)
    $killNames = [System.Collections.Generic.List[string]]::new()
    $challengeNames = [System.Collections.Generic.List[string]]::new()
    $merchantNames = [System.Collections.Generic.List[string]]::new()
    $others = [System.Collections.Generic.List[string]]::new()
    foreach ($source in $list) {
        if ($source -match '^击杀「(.+)」概率掉落$') {
            if (-not $killNames.Contains($Matches[1])) { $killNames.Add($Matches[1]) }
        } elseif ($source -match '^挑战「(.+)」获得$') {
            if (-not $challengeNames.Contains($Matches[1])) { $challengeNames.Add($Matches[1]) }
        } elseif ($source -match '^在「(.+)」处获取$') {
            if (-not $merchantNames.Contains($Matches[1])) { $merchantNames.Add($Matches[1]) }
        } else {
            if (-not $others.Contains($source)) { $others.Add($source) }
        }
    }
    $result = [System.Collections.Generic.List[string]]::new()
    if ($killNames.Count -gt 0) {
        $shown = if ($killNames.Count -gt 6) { $killNames.GetRange(0, 6) } else { $killNames }
        $suffix = if ($killNames.Count -gt 6) { ' 等' } else { '' }
        $result.Add("击杀「$($shown -join '、')$suffix」概率掉落")
    }
    if ($challengeNames.Count -gt 0) {
        $shown = if ($challengeNames.Count -gt 4) { $challengeNames.GetRange(0, 4) } else { $challengeNames }
        $suffix = if ($challengeNames.Count -gt 4) { ' 等' } else { '' }
        $result.Add("挑战「$($shown -join '、')$suffix」获得")
    }
    if ($merchantNames.Count -gt 0) {
        $shown = if ($merchantNames.Count -gt 6) { $merchantNames.GetRange(0, 6) } else { $merchantNames }
        $suffix = if ($merchantNames.Count -gt 6) { ' 等' } else { '' }
        $result.Add("在「$($shown -join '、')$suffix」处获取")
    }
    foreach ($source in $others) { $result.Add($source) }
    return $result
}

# ----------------------------------------------------------------------
# 11. 输出 JSON
# ----------------------------------------------------------------------
$ordered = [ordered]@{}
foreach ($id in ($sources.Keys | Sort-Object)) {
    $ordered["vmd:$id"] = @(Compress-Sources $sources[$id])
}

$json = $ordered | ConvertTo-Json -Depth 5
[System.IO.File]::WriteAllText($outputPath, $json, [System.Text.UTF8Encoding]::new($false))
Write-Host "Wrote $($sources.Count) entries to $outputPath"
