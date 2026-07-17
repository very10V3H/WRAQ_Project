# VMD (维瑞阿契) - Minecraft Forge Mod

## Project Overview

| Item | Value |
|---|---|
| **Mod Name** | VMD (维瑞阿契 / WRAQ) |
| **Mod ID** | `vmd` |
| **Minecraft Version** | 1.20.1 |
| **Forge Version** | 47.3.0 |
| **ForgeGradle Plugin** | `[6.0, 6.2)` |
| **Mappings** | Mojang official `1.20.1` |
| **Java Target** | Java 17 |
| **Mod Version** | `2.1.12a` |
| **Maven Group** | `com.very.vmd` |
| **Author** | very_H |
| **Root Package** | `fun.wraq` |
| **License** | GNU GPLv3 |

This is a large-scale Chinese RPG mod for a survival server called "维瑞阿契" (WRAQ). It adds custom weapons (swords, bows, scepters), armor, curios (via Curios API), gems, multi-chapter mob progression, dungeons/instances, skill trees (sword/bow/mana), forging/brewing/injection crafting systems, an economy (market/bank/lottery), and dozens of game systems.

## Key Dependencies

| Dependency | Version | Purpose |
|---|---|---|
| **Curios API** | 5.9.0+1.20.1 | Accessory/curio slots (optional but core to equipment) |
| **GeckoLib** | 4.4.4 | Entity animations |
| **Player Animation Lib** | 1.0.2-rc1+1.20 | Player animation layer |
| **Patchouli** | 1.20.1-84-FORGE | In-game guidebook |
| **Registrate** | MC1.20-1.3.11 | JAR-in-JAR, used for some registration |

There are also ~50+ content mod dependencies (Create, Ice and Fire, Biomes O' Plenty, Quark, Farmer's Delight, etc.) all declared as `implementation fg.deobf` from CurseMaven. These are used for world generation and integration but the core VMD mod logic does not depend on most of them at compile time.

## Directory Structure

```
src/main/java/fun/wraq/
├── VMD.java                    # Main mod class (@Mod)
├── blocks/                     # Custom blocks & block entities
│   ├── blocks/brew/            # Brewing system (Purifier, Solidifier, Stabilizer, etc.)
│   ├── blocks/forge/           # Forging block
│   ├── blocks/furnace/         # Custom furnace
│   ├── blocks/inject/          # Inject block
│   └── entity/                 # Block entities
├── commands/                   # In-game commands
│   ├── changeable/             # Player commands (Compensate, Prefix, Recycle, etc.)
│   └── stable/
│       ├── ops/                # Admin commands (Clear, Copy, Giant, Reset, etc.)
│       └── players/            # Player commands (Forge, Sell, Dps, Debug, etc.)
├── common/                     # Shared utilities and base classes
│   ├── attribute/              # Attribute descriptions & damage influence
│   ├── equip/                  # Abstract base classes: WraqSword, WraqBow, WraqArmor,
│   │   │                       #   WraqCurios, WraqSceptre, WraqMainHandEquip, etc.
│   │   └── impl/               # Equipment behavior interfaces
│   ├── fast/                   # Quick-access utilities (Tick, Te, PlayerHashMap, etc.)
│   ├── impl/                   # Equipment behavior interfaces
│   │   ├── damage/             # Damage-related interfaces (BeforeCause/OnCause/OnWithStand)
│   │   ├── display/            # Tooltip/dispay interfaces (ForgeItem, EnhancedForgedItem)
│   │   ├── onhit/              # On-hit effect interfaces
│   │   ├── onkill/             # On-kill effect interfaces
│   │   ├── onshoot/            # On-shoot/release interfaces
│   │   ├── oncostmana/         # On-mana-cost interfaces
│   │   ├── inslot/             # In-slot attribute modifiers
│   │   ├── skillv2/            # Skill equipment interfaces
│   │   └── withstand/          # Damage modification interfaces
│   ├── registry/               # Forge registries (ModItems, ModBlocks, ModEntityType, etc.)
│   └── util/                   # Utility classes & data structures
├── core/                       # Core combat mechanics
│   ├── AttackEvent.java        # Attack event handling
│   └── bow/                    # Custom arrow mechanics
├── customized/                 # Custom uniform/crafted items
├── entities/                   # Custom entities, mobs, NPCs, models, renderers
├── events/                     # Forge event subscribers
│   ├── client/                 # Client-side events (tick, key input, particles)
│   ├── core/                   # Core game events (blocks, bows, tools, villagers)
│   ├── fight/                  # Combat events (HurtEvent, MonsterAttackEvent)
│   ├── mob/                    # Mob spawn controllers by chapter/region
│   ├── modules/                # Modular event fragments
│   └── server/                 # Server-side events (tick, login, player tick)
├── files/                      # CSV/File I/O (market data)
├── items/                      # Custom item implementations (dev tools, forge items, etc.)
├── networking/                 # Network packet definitions
├── process/                    # Core game systems (the bulk of the mod logic)
│   ├── func/                   # Shared processing functions (damage, effects, particles, guide)
│   └── system/                 # Game systems (bank, forge, instance, market, skill, etc.)
├── projectiles/                # Custom projectile entities
├── render/                     # Client-side rendering
│   ├── gui/                    # GUI screens (blocks, market, mission, skills, trade, team)
│   ├── hud/                    # HUD overlays (buffs, attributes, mana, shield)
│   ├── mobEffects/             # Custom potion effects
│   ├── particles/              # Custom particle rendering
│   └── toolTip/                # Custom tooltip styles
└── series/                     # Equipment series by region/chapter/event
    ├── comsumable/             # Consumable items (mixtures, quivers, whetstones)
    ├── crystal/                # Crystal items
    ├── dragon/                 # Silver Dragon series
    ├── end/                    # End dimension equipment (recall books, citadel, runes)
    ├── events/                 # Event-themed items (festivals, seasons)
    ├── gems/                   # Gem items and passive gem behaviors
    ├── holy/                   # Holy/Ice equipment
    ├── instance/               # Instance/dungeon drop series
    ├── moontain/               # Moontain region equipment
    ├── nether/                 # Nether equipment
    ├── newrunes/               # Rune items by chapter
    ├── overworld/              # Overworld equipment (chapters 1-7, regions, biomes)
    ├── secret/                 # Secret treasure system
    └── worldsoul/              # World soul equipment
```

```
src/main/resources/
├── META-INF/
│   ├── mods.toml               # Mod metadata
│   └── accesstransformer.cfg   # Access transformer
├── assets/vmd/
│   ├── animations/             # GeckoLib entity animations
│   ├── blockstates/            # Block state JSONs
│   ├── geo/                    # GeckoLib model geometry
│   ├── lang/                   # Localization (zh_cn.json, en_us.json)
│   ├── models/                 # Block & item model JSONs
│   ├── particles/              # Particle definitions
│   ├── patchouli_books/        # In-game guidebook content
│   ├── sounds/                 # Custom sound files
│   └── textures/               # All textures
├── data/
│   ├── curios/                 # Curios API tag integration
│   ├── minecraft/              # Vanilla overrides (tags, loot tables)
│   └── vmd/                    # VMD data (loot tables, patchouli, recipes)
```

## Architecture & Design Patterns

### 1. Main Mod Class (`VMD.java`)

The main class is annotated with `@Mod(Utils.MOD_ID)` and `@Mod.EventBusSubscriber`. Constructor-based registration pattern:

```java
public VMD() {
    IEventBus modEvenBus = FMLJavaModLoadingContext.get().getModEventBus();
    // Register all DeferredRegisters
    ModItems.ITEMS.register(modEvenBus);
    ModBlocks.BLOCKS.register(modEvenBus);
    ModEntityType.ENTITY_TYPES.register(modEvenBus);
    // ... many more
    // Lifecycle listeners
    modEvenBus.addListener(this::commonStart);  // FMLCommonSetupEvent
    modEvenBus.addListener(this::clientStart);  // FMLClientSetupEvent
}
```

Static `@SubscribeEvent` methods handle `ServerStartingEvent` and `ServerStoppingEvent`.

### 2. DeferredRegister Registration Pattern

Every item group has its own `DeferredRegister<Item>` stored as a public static field:

```java
public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
        DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);
    public static final RegistryObject<Item> SOME_ITEM = ITEMS.register("name",
        () -> new SomeItem(...));
}
```

There are **dozens** of separate `DeferredRegister<Item>` instances across the codebase:
- `ModItems.ITEMS` — core items
- `OreItems.ITEMS` — ore-related items
- `GemItems.ITEMS` — gems
- `SpurItems.ITEMS` — spur/支线 items
- Each `series/` package has its own items container with `ITEMS`

The same pattern applies to blocks (`ModBlocks.BLOCKS`), entities (`ModEntityType.ENTITY_TYPES`), creative tabs (`ModCreativeModeTab.CREATIVE_MODE_TAB`), attributes (`HAttribute.ATTRIBUTES`), sounds (`ModSounds.SOUND_EVENTS`), mob effects, potions, particles, and menu types.

**Registration order in VMD constructor matters** — all `ITEMS.register()` must be called before `ModCreativeModeTab.register()` because the creative tab's `AddItemToTab` event handlers reference items via `RegistryObject::get`.

### 3. Equipment Inheritance Hierarchy

**Main-hand weapons:**
```
SwordItem (vanilla)
  └── WraqMainHandEquip (abstract, sets tier, adds to weaponList)
        ├── SwordAttribute interface
        │   └── WraqSword
        ├── BowAttribute interface
        │   └── WraqBow
        └── SceptreAttribute interface
            └── WraqSceptre
```

**Curios (accessories):**
```
Item (vanilla)
  └── WraqCurios (abstract, implements ICurioItem)
        ├── ForgeItem interface → forging recipe
        ├── RuneItem → added to rune display list
        └── Souvenirs → special collectible variant
```

**Armor:**
```
ArmorItem (vanilla)
  └── WraqArmor
```

**Pickaxes:**
```
PickaxeItem (vanilla)
  └── WraqPickaxe
```

**Finding an item's Chinese name & registration:**
- Weapon constructors set attributes via `Utils.attackDamage.put(this, ...)`, `Utils.critRate.put(this, ...)`, etc.
- The item is registered in its series' `*Items.java` (e.g., `ModItems.java`, `PlainItems.java`) as `ITEMS.register("registerName", () -> new PlainSword(...))`. The first argument `"registerName"` is the registry key.
- The Chinese display name is in `src/main/resources/assets/vmd/lang/zh_cn.json` under key `"item.vmd.registerName"`.

**Example**: `PlainSword` → `ModItems.PLAIN_SWORD_0 = ITEMS.register("plainsword0", ...)` → `zh_cn.json: "item.vmd.plainsword0": "平原短匕"`.

**Key abstract methods on WraqCurios:**
- `getTypeDescription()` — returns type Component
- `additionHoverText(ItemStack)` — extra tooltip lines
- `hoverMainStyle()` — primary Style for tooltip formatting
- `suffix()` — bottom tooltip line
- `tick(Player)` / `clientTick(Player)` — per-tick logic (called from `curioTick`)

**Key abstract methods on WraqMainHandEquip:**
- `getMainStyle()` — primary Style
- `getAdditionalComponents(ItemStack)` — extra tooltip lines
- `getSuffix()` — bottom tooltip line
- `getType()` — type description (近战/远程/法术)
- `tick(Player)` — per-tick logic

### 4. Equipment Behavior Interfaces

Equipment uses a **composition-over-inheritance** interface pattern for behaviors. Classes implement interfaces to declare behavior, and processing code checks with `instanceof`:

| Interface Package | Purpose |
|---|---|
| `impl/display/ForgeItem` | Declares forging recipe via `forgeRecipe()` |
| `impl/display/EnhancedForgedItem` | Enhanced forging variant with extra attributes |
| `impl/onhit/OnHitEffectEquip` | Effect triggered when hitting a mob |
| `impl/onhit/OnHitDamageInfluenceEquip` | Modifies hit damage |
| `impl/onkill/OnKillEffectEquip` | Effect on killing a mob |
| `impl/onshoot/OnShootArrowCurios` | Effect when shooting an arrow |
| `impl/onshoot/OnPowerReleaseCurios` | Effect when using charged attack |
| `impl/damage/OnCauseFinalDamageCurios` | Modifies final damage dealt |
| `impl/damage/OnWithStandDamageCurios` | Modifies damage received |
| `impl/withstand/ModifyPlayerWithstandDamageInfluenceCurios` | Damage reduction |
| `impl/inslot/InCuriosOrEquipSlotAttributesModify` | Attribute modifier while in curio slot |
| `impl/oncostmana/OnCostManaEquip` | Triggered on mana consumption |
| `impl/skillv2/EnhanceSkillRateEquip` | Skill rate enhancement |

### 5. Event Handling

All event handlers use `@Mod.EventBusSubscriber` on the class (auto-registration) with `@SubscribeEvent` on static methods. Events are organized by domain:

```
events/
├── client/     # ClientTickEvent, ClientPlayerTickEvent, KeyInput, RenderTooltipEvent, etc.
├── core/       # BlockEvent, BowEvent, RightClickEvent, VillagerEvents, etc.
├── fight/      # LivingHurtEvent, LivingAttackEvent
├── mob/        # MobSpawn, LivingDeathEvent (mob-specific handlers)
├── server/     # ServerTickEvent, PlayerEvent.PlayerLoggedInEvent, PlayerTickEvent
└── modules/    # Refactored modular event handlers
```

**Server tick pattern** — The `ServerTick.ServerTickEvent` dispatches to all game system tick handlers:
```java
@SubscribeEvent
public static void ServerTickEvent(TickEvent.ServerTickEvent event) {
    if (event.side.isServer() && event.phase == TickEvent.Phase.START) {
        if (tickCount % 20 == 1) BowEvent.handleServerTick();
        if (tickCount % 80 == 0) PurpleIronCommon.handleServerTick();
        RandomEventsHandler.tick();
        // etc.
    }
}
```

**Login event pattern** — `LoginInEvent.loginEvent` handles `PlayerEvent.PlayerLoggedInEvent` and is the central place to:
1. Initialize new player persistent data (NBT)
2. Sync all client state via packets
3. Handle daily/weekly/monthly refresh logic
4. Run migration/compensation logic for version updates

### 6. Networking

Uses a single `SimpleChannel` (Forge's simplified networking):

```java
// Registration in ModNetworking.register():
SimpleChannel net = NetworkRegistry.ChannelBuilder
    .named(new ResourceLocation(Utils.MOD_ID, "messages"))
    .networkProtocolVersion(() -> "1.0")
    .clientAcceptedVersions(s -> true)
    .serverAcceptedVersions(s -> true)
    .simpleChannel();

// Each packet is registered with a sequential ID:
net.messageBuilder(SomeC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
    .decoder(SomeC2SPacket::new)   // Constructor from FriendlyByteBuf
    .encoder(SomeC2SPacket::toBytes)
    .consumerMainThread(SomeC2SPacket::handle)
    .add();
```

**Packet naming convention:**
- `*C2SPacket` — Client-to-Server (PLAY_TO_SERVER)
- `*S2CPacket` — Server-to-Client (PLAY_TO_CLIENT)

**Packet sending helpers:**
```java
ModNetworking.sendToServer(message);         // Client → Server
ModNetworking.sendToClient(message, player); // Server → specific client
```

The `sendToServer` helper includes a built-in client-side rate limiter (`clientPacketLimit`) that prevents >100 packets/tick.

**Packet implementation pattern:**
```java
public class SomeC2SPacket {
    private final int data;
    public SomeC2SPacket(int data) { this.data = data; }
    public SomeC2SPacket(FriendlyByteBuf buf) { this.data = buf.readInt(); }
    public void toBytes(FriendlyByteBuf buf) { buf.writeInt(data); }
    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            // Handle logic here
        });
        return true;
    }
}
```

### 7. Custom Attributes

Uses static `Map<Item, Double>` maps in `Utils.java` for item attribute storage (NOT Forge's capability/attribute system):

```java
Utils.attackDamage    // Item → attack damage
Utils.critRate        // Item → crit rate
Utils.critDamage      // Item → crit damage
Utils.defence         // Item → defence
Utils.manaDamage      // Item → mana damage
// ... ~40+ attribute maps
```

Player attributes are computed in `Compute.java` by iterating over equipped items and summing all these map values. Custom attributes (haste, mana, etc.) are registered via `HAttribute` as Forge `Attribute` types with UUID modifiers applied in `AttributeSet` events.

### 8. Player Persistent Data

Player state is stored using Minecraft's built-in NBT persistent data system — NOT Forge capabilities:

```java
CompoundTag data = player.getPersistentData();
data.putDouble("MANA", 100);
data.putInt("SkillPoint_Total", 50);
data.putString("LastDailyMission", "...");
```

Key prefixes are defined as constants in `StringUtils`. The persistent data is the single source of truth for all player progression (levels, skills, currency, quest progress, etc.).

### 9. Tick Utility

`fun.wraq.common.fast.Tick` provides time-related helpers:

```java
Tick.get()           // Current server tick count (requires Tick.server to be set)
Tick.s(int seconds)  // Convert seconds to ticks (multiplies by 20)
Tick.min(int minutes)// Convert minutes to ticks
Tick.toSeconds(int)  // Convert ticks to seconds
```

`Tick.server` is set in `serverStartEvent` and must not be null for `Tick.get()` to work.

### 10. Text Helpers

- `Te.s(String)` — creates a `Component.literal(s)` without style
- `Te.s(String, Style)` — creates a styled literal Component
- `Te.m(String)` — creates a `MutableComponent` (shorthand)
- `Compute.sendFormatMSG(player, title, content)` — sends a formatted `[title] content` message
- `Compute.formatBroad(level, title, content)` — broadcasts a formatted message
- `CustomStyle` — holds named Style constants (styleOfPower, styleOfSakura, styleOfEnd, styleOfHealth, styleOfGold, styleOfMoontain, styleOfFlexible)
- `ComponentUtils.descriptionDash(...)` — renders tooltip section dividers
- `ComponentUtils.descriptionOfBasic(...)` — renders "基础属性" section headers

### 11. Creative Tab Organization

30+ creative mode tabs, each registered with `DeferredRegister<CreativeModeTab>`. Tabs are populated in `VMD.AddItemToTab()` by checking `event.getTabKey()`. Items are added either individually, from a list, or from a `DeferredRegister`'s entries via stream.

### 12. Forge Recipe Integration

Custom crafting systems (Forge, Brew, Inject, Furnace) each have:
- A `Block` class extending `BaseEntityBlock`
- A `BlockEntity` class
- A `Menu` (container) class
- A `Screen` class (client-side GUI)
- Recipe data classes

The forging system uses `ForgeRecipe.recipes` (a static `Map<Item, ForgeRecipe>`) populated in the constructor of items that implement `ForgeItem`.

## Game Systems (process/)

The `process/` directory contains all game system logic, split into `func/` (shared processing utilities) and `system/` (individual game systems).

### Shared Processing (`func/`)

| Package/File | System | Description |
|---|---|---|
| `damage/Damage.java` | Core Damage | Central damage calculation — integrates all damage interfaces (element, skill, on-hit, on-kill), handles crit, defence penetration, mana damage. The single most important combat file. |
| `damage/Dot.java` | DoT | Damage-over-time tracking per entity. |
| `damage/SputteringDamage.java` | Splash Damage | Splash/AoE damage around the target. |
| `effect/` | Status Effects | Blind, Silence, and other custom debuffs synced via S2C packets. `SpecialEffectOnPlayer.java` manages temporary player buffs/debuffs. |
| `guide/` | Tutorial Guide | Stage-based new-player guide with waypoint markers, per-stage rewards, and a client-side HUD. Stages defined in `Guide.StageV2` (backpack→rolling→illustrate→...→forge→elements). |
| `item/InventoryOperation.java` | Inventory | Utility for checking, finding, adding, and removing items from player inventory. |
| `multiblockactive/rightclick/` | Multi-block Machines | Right-click-activated workstations: `ItemChanger` (convert items), `ItemEnhancer` (upgrade items), `ItemDecomposer` (break down items), `GemEnhancer` (enhance gems). Uses `RightClickActiveHandler` and `RightClickActivation` base infrastructure. |
| `particle/` | Particle Rendering | Custom particle effects (lines, circles, space dust, dispersion balls) sent via S2C packets from server to all nearby clients. |
| `plan/` | VIP Subscription | Tiered subscription system (`PlanPlayer`): tiers 1-3 with daily supply rewards (`DailySupply.java`, `SupplyBox.java`), mission assignments, and a lottery system. |
| `power/` | Power System | Activated power/ultimate mechanic: player charges power then releases. Implementations: `BloodManaPower`, `IcePower`, `LifeManaPower`, `ObsiManaPower`, `SakuraPower`. `WraqPower` is the abstract base. |
| `rank/` | Ranking | Player rank system (13C→13B→...→1A with many sub-tiers). Ranks grant daily wages (gold beans), determine Reason upper limit, and unlock features. |
| `security/` | Anti-Cheat | MAC-based hardware identification for anti-cheat. `Security.java` enforces login checks. |
| `suit/SuitCount.java` | Set Effects | Counts equipped items of matching sets and applies set bonuses. |
| `ChangedAttributesModifier.java` | Random Attributes | Handles randomly-rolled attribute modifications on equipment. Used alongside `StableAttributesModifier.java` and `StableTierAttributeModifier.java`. |
| `EnhanceNormalAttack.java` | Attack Enhancement | Modifies normal attack behavior (range, damage, effects). |
| `PersistentRangeEffect.java` | Persistent AoE | Long-running area effects tied to a position. |
| `DelayOperationWithAnimation.java` | Delayed Actions | Animates a weapon swing then executes a delayed operation — used for skill releases. |
| `MobEffectAndDamageMethods.java` | Mob Utilities | Shared methods for applying effects and damage to mobs. |

### Game Systems (`system/`)

#### Economy & Currency

| Package | System | Key Files | Description |
|---|---|---|---|
| `bank/` | Bank (GB) | `Bank.java`, `BondDividends.java` | Gold Bean (GB) currency. Deposit/withdraw via commands, daily bond dividends, million-money bills. GB is the primary server economy currency. |
| `market/` | Auction House | `MarketInfo.java` | Player-to-player item market. Lists items with player name, price, and type. Data persisted via `MarketItemData` using the overworld's `SavedData` system. |
| `point/` | Exploration Points | `Point.java` | Region-specific exploration currencies (EXPT, DSPT, ELPT, SKPT, NTPT, EDPT, BCPT, MTPT, ACPT, OCPT, NOPT, ATPT). Earned through exploration and spent in region-specific shops. |
| `vp/` | VP Store | `VpStore.java`, `VpDataHandler.java` | Virtual Points store: currency earned from events. Exchange VP for lottery boxes, consumables, world soul pieces, etc. `VpStoreScreen` is the client GUI. |

#### Progression & Combat

| Package | System | Key Files | Description |
|---|---|---|---|
| `skill/` | Skill Trees | `SkillV2.java`, `SwordSkillTree.java`, `BowSkillTree.java`, `ManaSkillTree.java` | **SkillV2** is the current skill framework. Players choose a profession (战士/弓箭手/法师), then unlock skills: 1 Passive + 3 Base + 1 Final per tree. Skills have cooldowns (synced via `SkillV2CooldownS2CPacket`), mana costs, upgradeable levels, and element customization via `SkillV2PlayerTryToSetSkillElementC2SPacket`. Skill releases trigger `DelayOperationWithAnimation`. `SkillV2Hud` shows the skill bar clientside. |
| `element/` | Element System | `Element.java` | 7 elements: Life, Water, Fire, Stone, Ice, Lightning, Wind. Items carry element values. Players have a resonance type. Season affects element effectiveness (§Season). `ElementPieceData` and `ElementRoulette` allow element customization. Element damage is applied in `Damage.java`. |
| `tower/` | Tower | `Tower.java` | Fixed-structure tower challenge (6 tiers I-VI, level 100-200). Player teleports to a tower floor, mobs spawn at fixed positions. Rewards scale with tier. |
| `forge/` | Forging | `ForgeScreen.java`, `ForgeEquipUtils.java`, `ForgeHammer.java`, `ForgeTemplate.java` | Zone-gated equipment forging. Each village zone offers specific equipment recipes. Items implementing `ForgeItem` declare their recipe. Equipment can be decomposed back to materials. Quick-decompose supported. |
| `enhanceForge/` | Enhanced Forging | `ForgeMaterials.java`, `Pearl.java` | Advanced forging materials and pearls for upgrading forged equipment. |
| `smelt/` | Smelting | `Smelt.java`, `SmeltRecipe.java` | Multi-slot timed smelting system. Players submit items to slots, wait for progress, then harvest results. `SmeltProgressScreen` shows progress. Slots can be expanded. Daily/weekly smelt limits. |
| `instance/` | Instance Sweep | `MopUpPaper.java` | Sweep tickets for instances. After 32 successful runs, players can use sweep tickets to instantly claim rewards without running the instance. |
| `endlessinstance/` | Endless Instance | `DailyEndlessInstance.java` | Timed wave-based instances. Mobs continuously spawn; the player has a fixed duration to kill as many as possible. Kill count tracked and recorded in `EndlessInstanceRecordData`. Implementations: `EasternTower`, `ManaPlainTemple`, `MansionInstance`. |
| `teamInstance/` | Team Dungeons | `NewTeamInstance.java`, `NewTeamInstanceHandler.java` | Multi-player dungeon instances with mechanics: `HarbingerInstance`, `NewCastleInstance` (Black Castle), `SpringSnakeInstance`. Has min/max player count, level requirements, and team management. Uses conditional mob summoning (on-approach or on-previous-wave-dead). |

#### Professions & Life Skills

| Package | System | Key Files | Description |
|---|---|---|---|
| `profession/alchemy/` | Alchemy | `AlchemyPlayerData.java` | Choose an attribute to enhance (attack, defence, crit, mana, etc.). Tier-based: each tier gives +3% to the chosen attribute. |
| `profession/pet/` | Pet (Allay) | `AllayPet.java`, `AllayCrystal.java`, `AllaySpawner.java`, `AllaySkillBook.java` | Allay pet system with skill books and crystals. |
| `profession/smith/` | Smithing | `SmithPlayerData.java`, `SmithHammer.java`, `SmithBook.java` | Smithing profession with hammers and skill books. |
| `cooking/` | Cooking | `CookingPlayerData.java`, `CookingValue.java`, `CookingVillager.java` | Cooking skill: sell food to earn cooking XP. Food coins and medals are the cooking currencies. |
| `spur/` | Spur (支线) | `CropSpur.java`, `MineSpur.java`, `WoodSpur.java`, `FishEvent.java` | Gathering activities: automatic rewards when harvesting crops, mining ores, chopping logs, or fishing. Each activity type has a charm item that boosts rewards. |

#### World & Exploration

| Package | System | Key Files | Description |
|---|---|---|---|
| `missions/` | Quests | `MissionV2.java`, `MissionV2Helper.java` | Daily mission system: 4 types — Explore (reach location), Kill (kill N mobs), Collection (gather items → manual submit), Challenge (complete N challenges). Auto-submit vs manual-submit. Rewards scale with player tier. |
| `randomevent/` | Random Events | `RandomEvent.java`, `RandomEventsHandler.java` | Server-wide random events triggered periodically. Types: `KillMobEvent` (kill mobs for rewards), `DigBlockEvent` (dig blocks), `MultiMobEvent` (multi-wave mob attacks like `VillageAttack`), `UrgentEvent` (urgent tasks), `SpringMobEvent` (special event mobs). Events have ready/begin/finish/overtime announcements. |
| `reason/` | Sanity (理智) | `Reason.java` | Sanity system: consumes sanity for instance entries. Cap determined by rank. Recovers over time (stored via `REASON_LastRecoverTime`). |
| `bonuschest/` | Bonus Chests | `BonusChestContent.java`, `BonusChestPlayerData.java` | Tiered loot boxes with random rewards. Tier 0-3 with increasing value. Item + max count pairs. |
| `restzone/` | Rest Zones | `RestZone.java` | Standing near rest block positions grants periodic bonus rewards (random items from bonus chest tables). |
| `miles/` | Travel Miles | `Miles.java` | Tracks total player travel distance (walking, flying, swimming → weighted calculation). Stores as BigInteger in player NBT. |
| `wayPoints/` | Waypoints | `MyWayPoint.java` | Integrates with Xaero's Minimap to add/remove waypoints programmatically. Supports local and global visibility. Used by guide system and region discovery. |
| `border/` | World Border | `WorldBorder.java` | Custom world border management. |
| `respawn/` | Respawn | `MyRespawnRule.java` | Custom respawn logic (setRespawnPoint optimization). |

#### Environment & Cosmetic

| Package | System | Key Files | Description |
|---|---|---|---|
| `season/` | Seasons | `MySeason.java` | Integrates with Serene Seasons. Each sub-season (early/mid/late × spring/summer/autumn/winter) applies multipliers to element values. Some elements get buffed, others debuffed per season. |
| `cold/` | Cold System | `ColdSystem.java` | Cold climate mechanic: certain world regions have cold levels (0-4) that apply debuffs. Countered by Heat Injection consumables and Bunker Armor. |
| `bgm/` | BGM | `WraqBgm.java` | Custom background music system. |
| `channel/` | Teleport Channel | `SakuraIslandChannel.java`, `SakuraIndustrySceptre.java` | Channel/teleport mechanic for Sakura region. |
| `tp/` | Teleport | `GateWay.java`, `TpPass.java` | Gate and teleport pass items. |

#### Other Systems

| Package | System | Key Files | Description |
|---|---|---|---|
| `estate/` | Real Estate | `EstateInfo.java`, `EstatePlayerData.java`, `EstateServerData.java`, `EstateKey.java` | Housing system: players can buy/sell apartments (e.g., "玉林公寓"). Each estate has a lock block, sign block, and price. Managed via commands. |
| `lottery/` | Lottery | `LotteryScreen.java`, `NewLotteries.java` | Lottery/gacha for equipment. Uses `ForgeC2SPacket` for claiming. Lottery tickets come from VP store and events. |
| `parkour/` | Parkour | `Parkour.java`, `ParkourTicket.java`, `ParkourGloves.java` | Pre-defined parkour course with waypoints. Parkour gloves enhance parkour ability. |
| `potion/` | Potions | `NewPotion.java`, `PotionBag.java` | Custom throwable potions and potion bags (convenience containers). |
| `ore/` | Pickaxes | `NormalPickaxe.java`, `OreItems.java` | Custom pickaxe items and ore blocks integration. |
| `expired/` | Item Expiry | `ExpiredSystem.java` | Manages item expiration (time-limited items). |
| `entrustment/` | Entrustment | `MobKillEntrustment.java`, `MobKillEntrustmentHud.java` | Mob-kill commission system with a client-side HUD tracking progress. |

### init System Flow (Player Login)

When a player logs in, these systems initialize/sync state:
1. `Reason` — recovers sanity since last login
2. `RankData` — sends rank info to all players
3. `PlanPlayer` — checks subscription expiry, sends plan info
4. `MissionV2` — generates daily missions, syncs data
5. `SkillV2` — syncs skill cooldowns and info
6. `Element` — syncs element resonance and season data
7. `Point` — syncs exploration point values
8. `Bank` — syncs GB balance
9. `Smelt` — syncs smelt slot data
10. `Estate` — syncs housing data

### Key Inter-System Dependencies

- **Damage.java** depends on: Element, ElementValue, SkillV2, Power, Season, and all equipment behavior interfaces (OnCause, OnHit, OnKill, etc.)
- **SkillV2** depends on: Element (skill element customization), Power (charge/release), Guide (tutorial unlocks)
- **Forge** depends on: zone definitions (ForgeEquipUtils), ForgeItem interface
- **Reason** depends on: RankData (determines upper limit)
- **PlanPlayer** depends on: RankData (rank-based tier), Lottery, Tower
- **Element** depends on: Season (seasonal element buffs/debuffs)

## Common Pitfalls & Conventions

### Do NOT:
- **Use `@OnlyIn(Dist.CLIENT)` or `@OnlyIn(Dist.DEDICATED_SERVER)`** — the project does not use these. Side checks are done with `player.level().isClientSide` or `event.side.isServer()`.
- **Use Forge's Capability system** — persistent player data uses `player.getPersistentData()` (NBT), not Forge Capabilities.
- **Use vanilla EquipmentSlot for curios** — curios are accessed via `Compute.CuriosAttribute.getDistinctCuriosList(player)`.
- **Register items directly in ModItems** for new series — each equipment series has its own `DeferredRegister` (e.g., `CrystalItems.ITEMS`). Follow the existing pattern.
- **Use `ItemStack.getOrCreateTag()` without the mod ID namespace** — use `stack.getOrCreateTagElement(Utils.MOD_ID)` to avoid conflicts with vanilla or other mods' NBT.
- **Block client-side operations during server tick** — always check `event.side.isServer()` before running server logic in `ServerTickEvent`.

### Do:
- **Import all new packet classes in `ModNetworking.register()`** and register them with sequential IDs.
- **Initialize new player NBT keys in `LoginInEvent.loginEvent`** if they don't already exist.
- **Add items to `Utils.weaponList`, `Utils.armorList`, `Utils.curiosList`, `Display.swordList`**, etc. in the item's constructor (unless it's a dev tool or random loot).
- **Implement `ForgeItem`** if the item has a forging recipe.
- **Use `Compute.forgingHoverName(stack)`** in `appendHoverText` for items that are forgeable.
- **Call `super.appendHoverText()` at the end** of overridden tooltip methods.
- **Use `Tick.get()` for timing** rather than maintaining separate tick counters.
- **Add Javadoc to AI-generated classes** — every new class created by AI must include a Javadoc comment at the class level: `/** AI-Generated, yyyy-MM-dd */` with the current date.
- **Define normal mobs in `events/mob/`** — each mob type extends `MobSpawnController` and overrides `getMobAttributes()` returning a `MobAttributes(attackDamage, defence, manaDefence, critRate, critDamage, defencePenetration, defencePenetration0, healthSteal, maxHealth, movementSpeed)`. Mob spawning positions, boundary, and averageLevel are set in the constructor.
- **Define instance bosses in `events/mob/instance/`** — each instance extends `NoTeamInstance` and overrides `getMainMobAttributes()` returning a `MobAttributes(...)` with the same constructor signature. Boss HP typically scales with player count: `maxHealth = baseMaxHealth * (1 + 0.75 * (playerCount - 1))`. Bosses are custom-summoned in `summonModule(Level level)`.
- **武器继承层次** — 近战→`WraqSword`，弓箭→`WraqBow`，法杖→`WraqSceptre`（详见 §3 Equipment Inheritance Hierarchy）。属性在构造函数中通过 `Utils.attackDamage`/`Utils.manaDamage`/`Utils.critRate` 等 Map 注册。

## Building & Running

```bash
# Windows
gradlew.bat runClient
gradlew.bat build

# Linux/Mac
./gradlew runClient
./gradlew build
```

The Gradle wrapper uses JDK 17. The `run` directory contains runtime configs (the `build.gradle` run configs still reference `examplemod` as a template leftover but the actual mod ID is `vmd`).

## Commit Style

Recent commits follow the format:
```
[2.1.12a-hotfix] 1.问题描述。
[2.1.12a] 1.改动描述。2.改动描述。...
```

Version tags in brackets, followed by numbered change descriptions in Chinese separated by periods.
