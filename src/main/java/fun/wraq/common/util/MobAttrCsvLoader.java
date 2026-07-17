/** AI-Generated, 2026-05-10 */
package fun.wraq.common.util;

import fun.wraq.common.attribute.MobAttributes;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class MobAttrCsvLoader {

    private static final String CSV_PATH = "data/vmd/balance/mob_attributes.csv";
    /** Primary index: mobName (Chinese name, matches MobSpawn.getMobOriginName()) → entry */
    private static final Map<String, MobAttrEntry> entries = new LinkedHashMap<>();
    /** Secondary index: className (controller simple class name) → mobName */
    private static final Map<String, String> classToMobName = new LinkedHashMap<>();
    private static volatile boolean loaded = false;

    public static boolean enable = false;

    public record MobAttrEntry(
            String type,
            String className,
            String mobName,
            String chapter,
            Integer averageLevel,
            double attackDamage,
            double defence,
            double manaDefence,
            double critRate,
            double critDamage,
            double defencePenetration,
            double defencePenetration0,
            double healthSteal,
            double maxHealth,
            double movementSpeed,
            String playerScaling
    ) {}

    public static void load() {
        if (loaded) return;
        InputStream is = null;
        BufferedReader reader = null;
        try {
            is = MobAttrCsvLoader.class.getClassLoader().getResourceAsStream(CSV_PATH);
            if (is == null) {
                System.err.println("[VMD] mob_attributes.csv not found at " + CSV_PATH);
                return;
            }
            reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String line;
            int lineNum = 0;
            while ((line = reader.readLine()) != null) {
                lineNum++;
                if (line.startsWith("#") || line.isBlank()) continue;
                String[] parts = splitCSV(line);
                if (parts.length < 16) {
                    System.err.println("[VMD] Skipping malformed line " + lineNum + ": " + line);
                    continue;
                }
                try {
                    MobAttrEntry entry = new MobAttrEntry(
                            parts[0].trim(),
                            parts[1].trim(),
                            parts[2].trim(),
                            parts[3].trim(),
                            parts[4].isBlank() ? null : Integer.parseInt(parts[4].trim()),
                            parts[5].isBlank() ? 0 : Double.parseDouble(parts[5].trim()),
                            parts[6].isBlank() ? 0 : Double.parseDouble(parts[6].trim()),
                            parts[7].isBlank() ? 0 : Double.parseDouble(parts[7].trim()),
                            parts[8].isBlank() ? 0 : Double.parseDouble(parts[8].trim()),
                            parts[9].isBlank() ? 0 : Double.parseDouble(parts[9].trim()),
                            parts[10].isBlank() ? 0 : Double.parseDouble(parts[10].trim()),
                            parts[11].isBlank() ? 0 : Double.parseDouble(parts[11].trim()),
                            parts[12].isBlank() ? 0 : Double.parseDouble(parts[12].trim()),
                            parts[13].isBlank() ? 0 : Double.parseDouble(parts[13].trim()),
                            parts[14].isBlank() ? 0 : Double.parseDouble(parts[14].trim()),
                            parts.length > 15 ? parts[15].trim() : ""
                    );
                    entries.put(entry.mobName, entry);
                    classToMobName.put(entry.className, entry.mobName);
                } catch (NumberFormatException e) {
                    System.err.println("[VMD] Number parse error at line " + lineNum + ": " + line);
                }
            }
            System.out.println("[VMD] Loaded " + entries.size() + " mob attribute entries from CSV");
        } catch (Exception e) {
            System.err.println("[VMD] Failed to load mob_attributes.csv: " + e.getMessage());
        } finally {
            loaded = true;
            if (reader != null) {
                try { reader.close(); } catch (Exception ignored) {}
            }
            if (is != null) {
                try { is.close(); } catch (Exception ignored) {}
            }
        }
    }

    /** Quote-aware CSV splitter */
    private static String[] splitCSV(String line) {
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

    /** Lookup by mob origin name (matches MobSpawn.getMobOriginName()) */
    public static MobAttrEntry getByMobName(String mobOriginName) {
        load();
        return entries.get(mobOriginName);
    }

    /** Lookup by controller class name */
    public static MobAttrEntry getByClass(String className) {
        load();
        String mobName = classToMobName.get(className);
        return mobName != null ? entries.get(mobName) : null;
    }

    /** Get MobAttributes by mob origin name (MobSpawn.getMobOriginName()), with optional player scaling */
    public static MobAttributes getScaledMobAttributes(String mobOriginName, int playerCount) {
        if (!enable) {
            return null;
        }
        MobAttrEntry entry = getByMobName(mobOriginName);
        if (entry == null) return null;
        double hp = getScaledMaxHealth(entry, playerCount);
        return new MobAttributes(
                entry.attackDamage, entry.defence, entry.manaDefence,
                entry.critRate, entry.critDamage, entry.defencePenetration,
                entry.defencePenetration0, entry.healthSteal,
                hp, entry.movementSpeed
        );
    }

    /** Check if a mob name has player scaling enabled */
    public static boolean hasPlayerScaling(String mobOriginName) {
        MobAttrEntry entry = getByMobName(mobOriginName);
        return entry != null && entry.playerScaling != null && !entry.playerScaling.isEmpty();
    }

    public static MobAttributes toMobAttributes(MobAttrEntry entry) {
        return new MobAttributes(
                entry.attackDamage, entry.defence, entry.manaDefence,
                entry.critRate, entry.critDamage, entry.defencePenetration,
                entry.defencePenetration0, entry.healthSteal,
                entry.maxHealth, entry.movementSpeed
        );
    }

    public static double getScaledMaxHealth(MobAttrEntry entry, int playerCount) {
        if (entry.playerScaling == null || entry.playerScaling.isEmpty()) {
            return entry.maxHealth;
        }
        return entry.maxHealth * (1.0 + 0.75 * (Math.max(1, playerCount) - 1));
    }

    public static List<MobAttrEntry> getAll() {
        load();
        return List.copyOf(entries.values());
    }

    public static List<MobAttrEntry> getByChapter(String chapter) {
        load();
        return entries.values().stream()
                .filter(e -> e.chapter.equals(chapter))
                .toList();
    }
}