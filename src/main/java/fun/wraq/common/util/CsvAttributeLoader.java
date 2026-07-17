/** AI-Generated, 2026-05-12 */
package fun.wraq.common.util;

import fun.wraq.process.system.element.Element;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.ForgeRegistries;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class CsvAttributeLoader {

    private static final String WEAPON_CSV_PATH = "data/vmd/balance/weapon_attributes.csv";
    private static final String ARMOR_CSV_PATH = "data/vmd/balance/armor_attributes.csv";

    private static final Map<String, String[]> weaponRows = new LinkedHashMap<>();
    private static final Map<String, String[]> armorRows = new LinkedHashMap<>();
    private static volatile boolean weaponLoaded = false;
    private static volatile boolean armorLoaded = false;

    private static final List<PendingWeapon> pendingWeapons = new ArrayList<>();
    private static final List<PendingArmor> pendingArmors = new ArrayList<>();
    private static volatile boolean processed = false;

    public static boolean enable = false;

    // -- records --
    private record PendingWeapon(Item item, String className) {}
    private record PendingArmor(Item item, String className, ArmorItem.Type slot) {}

    // -- registration (called from constructors) --

    public static void trackWeapon(Item item) {
        if (!enable) {
            return;
        }
        if (processed) return;
        pendingWeapons.add(new PendingWeapon(item, item.getClass().getSimpleName()));
    }

    public static void trackArmor(Item item, ArmorItem.Type slot) {
        if (!enable) {
            return;
        }
        if (processed) return;
        pendingArmors.add(new PendingArmor(item, item.getClass().getSimpleName(), slot));
    }

    // -- unified processing (called from FMLCommonSetupEvent) --

    public static void processAll() {
        if (processed) return;
        processed = true;
        loadWeaponCsv();
        loadArmorCsv();

        List<String> newWeaponLines = new ArrayList<>();
        List<String> newArmorLines = new ArrayList<>();

        for (PendingWeapon pw : pendingWeapons) {
            String[] row = weaponRows.get(pw.className);
            if (row != null) {
                int tier = extractTier(pw.item);
                applyWeaponRow(pw.item, row, tier);
            } else {
                String exported = exportWeaponToCsv(pw.item);
                if (exported != null) newWeaponLines.add(exported);
            }
        }

        for (PendingArmor pa : pendingArmors) {
            String[] row = armorRows.get(pa.className);
            if (row != null) {
                applyArmorRow(pa.item, row, pa.slot);
            } else {
                String exported = exportArmorToCsv(pa.item, pa.slot);
                if (exported != null) newArmorLines.add(exported);
            }
        }

        writeNewCsvLines("weapon_attributes.csv", newWeaponLines,
                "# VMD Weapon BASE Attributes — auto-exported entries",
                "type,className,registryName,chineseName,tiers,attackDamage,critRate,critDamage,manaDamage,manaRecover,coolDown,defencePen,defencePen0,healthSteal,manaHealthSteal,manaPen,manaPen0,maxHealth,movementSpeed,elementType,elementValue,levelRequire,notes");
        writeNewCsvLines("armor_attributes.csv", newArmorLines,
                "# VMD Armor BASE Attributes — auto-exported entries",
                "type,className,registryName,chineseName,slots,tiers,attackDamage,critRate,critDamage,manaDamage,defence,manaDefence,maxHealth,healthSteal,defencePen0,manaPen0,maxMana,manaRecover,coolDown,movementSpeed,healthRecover,percentHealthRecover,levelRequire,elementType,elementValue,notes");

        System.out.println("[VMD] CsvAttributeLoader: " + pendingWeapons.size() + " weapons, " + pendingArmors.size() + " armors processed");
    }

    // -- tier extraction --

    private static int extractTier(Item item) {
        var key = ForgeRegistries.ITEMS.getKey(item);
        if (key == null) return 0;
        String name = key.getPath();
        int i = name.length() - 1;
        while (i >= 0 && Character.isDigit(name.charAt(i))) i--;
        if (i < name.length() - 1) {
            try { return Integer.parseInt(name.substring(i + 1)); }
            catch (NumberFormatException e) { return 0; }
        }
        return 0;
    }

    // -- CSV loading --

    private static void loadWeaponCsv() {
        if (weaponLoaded) return;
        weaponLoaded = true;
        try (InputStream is = CsvAttributeLoader.class.getClassLoader().getResourceAsStream(WEAPON_CSV_PATH)) {
            if (is == null) { System.err.println("[VMD] weapon_attributes.csv not found"); return; }
            try (BufferedReader r = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                String line;
                while ((line = r.readLine()) != null) {
                    if (line.startsWith("#") || line.isBlank()) continue;
                    if (line.startsWith("type,")) continue;
                    String[] parts = splitCSV(line);
                    if (parts.length < 6) continue;
                    weaponRows.put(parts[1].trim(), parts);
                }
            }
            System.out.println("[VMD] Loaded " + weaponRows.size() + " weapon entries from CSV");
        } catch (Exception e) {
            System.err.println("[VMD] Failed to load weapon_attributes.csv: " + e.getMessage());
        }
    }

    private static void loadArmorCsv() {
        if (armorLoaded) return;
        armorLoaded = true;
        try (InputStream is = CsvAttributeLoader.class.getClassLoader().getResourceAsStream(ARMOR_CSV_PATH)) {
            if (is == null) { System.err.println("[VMD] armor_attributes.csv not found"); return; }
            try (BufferedReader r = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                String line;
                while ((line = r.readLine()) != null) {
                    if (line.startsWith("#") || line.isBlank()) continue;
                    if (line.startsWith("type,")) continue;
                    String[] parts = splitCSV(line);
                    if (parts.length < 10) continue;
                    armorRows.put(parts[1].trim(), parts);
                }
            }
            System.out.println("[VMD] Loaded " + armorRows.size() + " armor entries from CSV");
        } catch (Exception e) {
            System.err.println("[VMD] Failed to load armor_attributes.csv: " + e.getMessage());
        }
    }

    // -- apply CSV values to Utils maps (CSV is authoritative, overrides constructor values) --

    private static void applyWeaponRow(Item item, String[] row, int tier) {
        // [5] attackDamage
        putIfNonEmpty(item, Utils.attackDamage, tieredValue(row, 5, tier));
        // [6] critRate
        putIfNonEmpty(item, Utils.critRate, tieredValue(row, 6, tier));
        // [7] critDamage
        putIfNonEmpty(item, Utils.critDamage, tieredValue(row, 7, tier));
        // [8] manaDamage
        putIfNonEmpty(item, Utils.manaDamage, tieredValue(row, 8, tier));
        // [9] manaRecover
        putIfNonEmpty(item, Utils.manaRecover, tieredValue(row, 9, tier));
        // [10] coolDown → Utils.coolDownDecrease
        putIfNonEmpty(item, Utils.coolDownDecrease, tieredValue(row, 10, tier));
        // [11] defencePen → Utils.defencePenetration
        putIfNonEmpty(item, Utils.defencePenetration, tieredValue(row, 11, tier));
        // [12] defencePen0 → Utils.defencePenetration0
        putIfNonEmpty(item, Utils.defencePenetration0, tieredValue(row, 12, tier));
        // [13] healthSteal
        putIfNonEmpty(item, Utils.healthSteal, tieredValue(row, 13, tier));
        // [14] manaHealthSteal
        putIfNonEmpty(item, Utils.manaHealthSteal, tieredValue(row, 14, tier));
        // [15] manaPen → Utils.manaPenetration
        putIfNonEmpty(item, Utils.manaPenetration, tieredValue(row, 15, tier));
        // [16] manaPen0 → Utils.manaPenetration0
        putIfNonEmpty(item, Utils.manaPenetration0, tieredValue(row, 16, tier));
        // [17] maxHealth
        putIfNonEmpty(item, Utils.maxHealth, tieredValue(row, 17, tier));
        // [18] movementSpeed → Utils.movementSpeedCommon
        putIfNonEmpty(item, Utils.movementSpeedCommon, tieredValue(row, 18, tier));
        // [19-20] element
        applyElement(item, row, 19, 20, tier);
        // [21] levelRequire
        if (row.length > 21) {
            Double lv = parseDoubleOrNull(row[21].trim());
            if (lv != null && lv > 0) Utils.levelRequire.put(item, lv.intValue());
        }
    }

    private static void applyArmorRow(Item item, String[] row, ArmorItem.Type slot) {
        char slotKey = slotToChar(slot);
        // [6] attackDamage
        putIfNonEmpty(item, Utils.attackDamage, slotValue(row, 6, slotKey));
        // [7] critRate
        putIfNonEmpty(item, Utils.critRate, slotValue(row, 7, slotKey));
        // [8] critDamage
        putIfNonEmpty(item, Utils.critDamage, slotValue(row, 8, slotKey));
        // [9] manaDamage
        putIfNonEmpty(item, Utils.manaDamage, slotValue(row, 9, slotKey));
        // [10] defence
        putIfNonEmpty(item, Utils.defence, slotValue(row, 10, slotKey));
        // [11] manaDefence
        putIfNonEmpty(item, Utils.manaDefence, slotValue(row, 11, slotKey));
        // [12] maxHealth
        putIfNonEmpty(item, Utils.maxHealth, slotValue(row, 12, slotKey));
        // [13] healthSteal
        putIfNonEmpty(item, Utils.healthSteal, slotValue(row, 13, slotKey));
        // [14] defencePen0
        putIfNonEmpty(item, Utils.defencePenetration0, slotValue(row, 14, slotKey));
        // [15] manaPen0
        putIfNonEmpty(item, Utils.manaPenetration0, slotValue(row, 15, slotKey));
        // [16] maxMana
        putIfNonEmpty(item, Utils.maxMana, slotValue(row, 16, slotKey));
        // [17] manaRecover
        putIfNonEmpty(item, Utils.manaRecover, slotValue(row, 17, slotKey));
        // [18] coolDown → Utils.coolDownDecrease
        putIfNonEmpty(item, Utils.coolDownDecrease, slotValue(row, 18, slotKey));
        // [19] movementSpeed → Utils.movementSpeedCommon
        putIfNonEmpty(item, Utils.movementSpeedCommon, slotValue(row, 19, slotKey));
        // [20] healthRecover
        putIfNonEmpty(item, Utils.healthRecover, slotValue(row, 20, slotKey));
        // [22] levelRequire
        if (row.length > 22) {
            Double lv = parseDoubleOrNull(row[22].trim());
            if (lv != null && lv > 0) Utils.levelRequire.put(item, lv.intValue());
        }
        // [23-24] element
        applyElement(item, row, 23, 24, 0);
    }

    // -- value extraction helpers --

    /** Get value at tier index from a possibly comma-separated field */
    private static Double tieredValue(String[] row, int col, int tier) {
        if (col >= row.length) return null;
        String raw = row[col].trim();
        if (raw.isEmpty()) return null;
        String[] parts = raw.split(",");
        if (parts.length > tier) return parseDoubleOrNull(parts[tier].trim());
        if (parts.length == 1) return parseDoubleOrNull(parts[0].trim());
        return null;
    }

    /** Get value for a specific slot from slot:value format like "C:110/H:44/L:66/B:0" */
    private static Double slotValue(String[] row, int col, char slotKey) {
        if (col >= row.length) return null;
        String raw = row[col].trim();
        if (raw.isEmpty()) return null;
        if (!raw.contains(":")) return parseDoubleOrNull(raw);
        String[] parts = raw.split("/");
        for (String part : parts) {
            int colonIdx = part.indexOf(':');
            if (colonIdx > 0 && part.charAt(0) == slotKey) {
                return parseDoubleOrNull(part.substring(colonIdx + 1).trim());
            }
        }
        return null;
    }

    private static void applyElement(Item item, String[] row, int typeCol, int valueCol, int tier) {
        if (typeCol >= row.length) return;
        String type = row[typeCol].trim();
        if (type.isEmpty()) return;
        Double val = 0.0;
        if (valueCol < row.length) {
            String rawVal = row[valueCol].trim();
            if (!rawVal.isEmpty()) {
                String[] parts = rawVal.split(",");
                if (parts.length > tier) val = parseDoubleOrNull(parts[tier].trim());
                else val = parseDoubleOrNull(parts[0].trim());
                if (val == null) val = 0.0;
            }
        }
        switch (type.toLowerCase()) {
            case "life" -> Element.lifeElementValue.put(item, val);
            case "water" -> Element.waterElementValue.put(item, val);
            case "fire" -> Element.fireElementValue.put(item, val);
            case "ice" -> Element.iceElementValue.put(item, val);
            case "stone" -> Element.stoneElementValue.put(item, val);
            case "lightning" -> Element.lightningElementValue.put(item, val);
            case "wind" -> Element.windElementValue.put(item, val);
        }
    }

    private static void putIfNonEmpty(Item item, Map<Item, Double> map, Double value) {
        if (value != null && value != 0.0) {
            map.put(item, value);
        }
    }

    private static Double parseDoubleOrNull(String s) {
        if (s.isEmpty()) return null;
        try { return Double.parseDouble(s); }
        catch (NumberFormatException e) { return null; }
    }

    private static char slotToChar(ArmorItem.Type type) {
        return switch (type) {
            case HELMET -> 'H';
            case CHESTPLATE -> 'C';
            case LEGGINGS -> 'L';
            case BOOTS -> 'B';
        };
    }

    // -- CSV export (constructor values → new CSV row) --

    private static String exportWeaponToCsv(Item item) {
        var key = ForgeRegistries.ITEMS.getKey(item);
        String regName = key != null ? key.getPath() : "unknown";
        String className = item.getClass().getSimpleName();

        StringBuilder sb = new StringBuilder();
        sb.append("SWORD,");          // type
        sb.append(className).append(",");
        sb.append(regName).append(",");
        sb.append(",");               // chineseName — unknown at this point
        sb.append("1,");              // tiers — default 1
        appendMapVal(sb, Utils.attackDamage.get(item));
        appendMapVal(sb, Utils.critRate.get(item));
        appendMapVal(sb, Utils.critDamage.get(item));
        appendMapVal(sb, Utils.manaDamage.get(item));
        appendMapVal(sb, Utils.manaRecover.get(item));
        appendMapVal(sb, Utils.coolDownDecrease.get(item));
        appendMapVal(sb, Utils.defencePenetration.get(item));
        appendMapVal(sb, Utils.defencePenetration0.get(item));
        appendMapVal(sb, Utils.healthSteal.get(item));
        appendMapVal(sb, Utils.manaHealthSteal.get(item));
        appendMapVal(sb, Utils.manaPenetration.get(item));
        appendMapVal(sb, Utils.manaPenetration0.get(item));
        appendMapVal(sb, Utils.maxHealth.get(item));
        appendMapVal(sb, Utils.movementSpeedCommon.get(item));
        sb.append(",").append(",");   // elementType, elementValue
        Integer lv = Utils.levelRequire.get(item);
        sb.append(lv != null ? lv : "").append(",");
        sb.append("auto-exported");
        System.out.println("[VMD] Exported new weapon: " + className + " → weapon_attributes.csv");
        return sb.toString();
    }

    private static String exportArmorToCsv(Item item, ArmorItem.Type slot) {
        var key = ForgeRegistries.ITEMS.getKey(item);
        String regName = key != null ? key.getPath() : "unknown";
        String className = item.getClass().getSimpleName();
        char s = slotToChar(slot);

        StringBuilder sb = new StringBuilder();
        sb.append("ARMOR,");
        sb.append(className).append(",");
        sb.append(regName).append(",");
        sb.append(",");               // chineseName
        sb.append(s).append(",");     // slots
        sb.append("1,");              // tiers
        appendMapVal(sb, Utils.attackDamage.get(item));
        appendMapVal(sb, Utils.critRate.get(item));
        appendMapVal(sb, Utils.critDamage.get(item));
        appendMapVal(sb, Utils.manaDamage.get(item));
        appendMapVal(sb, Utils.defence.get(item));
        appendMapVal(sb, Utils.manaDefence.get(item));
        appendMapVal(sb, Utils.maxHealth.get(item));
        appendMapVal(sb, Utils.healthSteal.get(item));
        appendMapVal(sb, Utils.defencePenetration0.get(item));
        appendMapVal(sb, Utils.manaPenetration0.get(item));
        appendMapVal(sb, Utils.maxMana.get(item));
        appendMapVal(sb, Utils.manaRecover.get(item));
        appendMapVal(sb, Utils.coolDownDecrease.get(item));
        appendMapVal(sb, Utils.movementSpeedCommon.get(item));
        appendMapVal(sb, Utils.healthRecover.get(item));
        sb.append(",");               // percentHealthRecover
        Integer lv = Utils.levelRequire.get(item);
        sb.append(lv != null ? lv : "").append(",");
        sb.append(",").append(",");   // elementType, elementValue
        sb.append("auto-exported");
        System.out.println("[VMD] Exported new armor: " + className + " → armor_attributes.csv");
        return sb.toString();
    }

    private static void appendMapVal(StringBuilder sb, Double val) {
        if (val != null) sb.append(formatDouble(val));
        sb.append(",");
    }

    private static String formatDouble(double d) {
        if (d == Math.floor(d) && !Double.isInfinite(d)) return String.valueOf((long) d);
        return String.valueOf(d);
    }

    // -- write new CSV lines to game directory --

    private static void writeNewCsvLines(String filename, List<String> newLines, String... header) {
        if (newLines.isEmpty()) return;
        try {
            Path dir = FMLPaths.GAMEDIR.get().resolve("data/vmd/balance");
            Files.createDirectories(dir);
            Path file = dir.resolve(filename);
            boolean exists = Files.exists(file);
            try (BufferedWriter w = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file.toFile(), true), StandardCharsets.UTF_8))) {
                if (!exists) {
                    for (String h : header) { w.write(h); w.newLine(); }
                }
                for (String line : newLines) {
                    w.write(line);
                    w.newLine();
                }
            }
            System.out.println("[VMD] Appended " + newLines.size() + " row(s) to " + file);
        } catch (Exception e) {
            System.err.println("[VMD] Failed to write " + filename + ": " + e.getMessage());
        }
    }

    // -- quote-aware CSV splitter --

    static String[] splitCSV(String line) {
        List<String> result = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;
        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);
            if (ch == '"') {
                inQuotes = !inQuotes;
            } else if (ch == ',' && !inQuotes) {
                result.add(current.toString());
                current.setLength(0);
            } else {
                current.append(ch);
            }
        }
        result.add(current.toString());
        return result.toArray(new String[0]);
    }
}
