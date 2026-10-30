package fun.wraq.process.system.missions.mainline;

import fun.wraq.common.fast.Te;
import fun.wraq.process.system.npc.NpcIds;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.function.Supplier;

/**
 * AI-Generated, 2026-10-18
 * 主线任务的阶段定义（数据层）。
 * <p>
 * 这里只描述「一个阶段是什么」，不包含任何服务端状态；状态机在 {@link MainQuestHelper}。
 * 新增阶段时：在 {@link MainQuestData} 追加一个 {@link Stage}，并在 {@code MissionV2} 追加同名枚举。
 */
public final class MainQuestStage {

    private MainQuestStage() {
    }

    public enum Kind {
        /** 纯对话阶段：对话即完成。 */
        TALK,
        /** 有世界目标的阶段：世界目标达成后，由交付对话完成。 */
        OBJECTIVE,
        /** 自动完成阶段：世界目标达成的下一个 tick 直接完成，不需要回来交付（用于"做一次某操作"的引导任务）。 */
        AUTO
    }

    public record Stage(String id, String mission, String npc, String reportNpc, Kind kind,
                        Component title, Component description, Component tips, Component frontCondition,
                        List<Objective> objectives, List<Reward> rewards,
                        QuestWaypoint giverWaypoint, QuestWaypoint targetWaypoint,
                        Dialogue dialogue) {

        /** 交付 NPC（未单独配置时与告知 NPC 相同）。 */
        public String effectiveReportNpc() {
            return reportNpc == null || reportNpc.isEmpty() ? npc : reportNpc;
        }
    }

    public record QuestWaypoint(String name, Vec3 pos, ResourceKey<Level> dimension) {
        public QuestWaypoint(String name, Vec3 pos) {
            this(name, pos, Level.OVERWORLD);
        }
    }

    public record Reward(Supplier<ItemStack> stack) {
    }

    public record Dialogue(List<Component> firstLines, List<Component> readyLines,
                           String firstLabel, String readyLabel,
                           String progressText, String doneText) {
    }

    /**
     * 世界目标。{@link #text(CompoundTag)} 同时用于任务提示与 HUD。
     */
    public sealed interface Objective permits Objective.Kill, Objective.Reach, Objective.Level,
            Objective.CollectItem, Objective.Instance, Objective.Tower, Objective.SupplyPack,
            Objective.Daily, Objective.Talk,
            Objective.Roll, Objective.SelectSkillGroup, Objective.MapTeleport, Objective.Mastery {

        /** 进度文本（击杀类会显示 当前/目标）。 */
        Component text(CompoundTag stageProgress);

        /** 进度计数键；非计数类返回空串。 */
        default String counterKey() {
            return "";
        }

        record Kill(String mob, int count) implements Objective {
            @Override
            public Component text(CompoundTag stageProgress) {
                int current = Math.min(count, stageProgress.getInt(counterKey()));
                if (current >= count) {
                    MutableComponent done = Te.s("✓ ", ChatFormatting.GREEN);
                    done.append(Te.s("击杀 ", mob, " ", count, "/", count)
                            .withStyle(ChatFormatting.STRIKETHROUGH, ChatFormatting.GRAY));
                    return done;
                }
                return Te.s("击杀 ", mob, " ", current, "/", count);
            }

            @Override
            public String counterKey() {
                return "kill|" + mob;
            }
        }

        record Reach(String name, Vec3 pos, double radius) implements Objective {
            @Override
            public Component text(CompoundTag stageProgress) {
                return Te.s("前往 ", name);
            }
        }

        record Level(int level) implements Objective {
            @Override
            public Component text(CompoundTag stageProgress) {
                return Te.s("达到 ", level, " 级");
            }
        }

        record CollectItem(String name, Supplier<Item> item, int count) implements Objective {
            @Override
            public Component text(CompoundTag stageProgress) {
                return Te.s("收集 ", name, " ×", count);
            }
        }

        record Instance(String name, String display, int count) implements Objective {
            @Override
            public Component text(CompoundTag stageProgress) {
                int current = Math.min(count, stageProgress.getInt(counterKey()));
                if (current >= count) {
                    MutableComponent done = Te.s("✓ ", ChatFormatting.GREEN);
                    done.append(Te.s("通关 ", display, " ", count, "/", count)
                            .withStyle(ChatFormatting.STRIKETHROUGH, ChatFormatting.GRAY));
                    return done;
                }
                return Te.s("通关 ", display, " ", current, "/", count);
            }

            @Override
            public String counterKey() {
                return "instance|" + name;
            }
        }

        record Tower(int index, String display, int count) implements Objective {
            @Override
            public Component text(CompoundTag stageProgress) {
                int current = Math.min(count, stageProgress.getInt(counterKey()));
                if (current >= count) {
                    MutableComponent done = Te.s("✓ ", ChatFormatting.GREEN);
                    done.append(Te.s("挑战 ", display, " ", count, "/", count)
                            .withStyle(ChatFormatting.STRIKETHROUGH, ChatFormatting.GRAY));
                    return done;
                }
                return Te.s("挑战 ", display, " ", current, "/", count);
            }

            @Override
            public String counterKey() {
                return "tower|" + index;
            }
        }

        record SupplyPack(int count) implements Objective {
            @Override
            public Component text(CompoundTag stageProgress) {
                int current = Math.min(count, stageProgress.getInt(counterKey()));
                if (current >= count) {
                    MutableComponent done = Te.s("✓ ", ChatFormatting.GREEN);
                    done.append(Te.s("使用任意补给包 ", count, "/", count)
                            .withStyle(ChatFormatting.STRIKETHROUGH, ChatFormatting.GRAY));
                    return done;
                }
                return Te.s("使用任意补给包 ", current, "/", count);
            }

            @Override
            public String counterKey() {
                return "supply_pack";
            }
        }

        record Daily(String name) implements Objective {
            @Override
            public Component text(CompoundTag stageProgress) {
                return Te.s("完成 ", name);
            }

            @Override
            public String counterKey() {
                return "daily|" + name;
            }
        }

        record Talk(String npc) implements Objective {
            @Override
            public Component text(CompoundTag stageProgress) {
                String name = NpcIds.displayNameOf(npc);
                return Te.s("与 ", name == null ? npc : name, " 对话");
            }
        }

        /** 使用翻滚（默认 Z 键）：成功翻滚时由 {@code RollingRequestC2SPacket} 记录。 */
        record Roll(int count) implements Objective {
            @Override
            public Component text(CompoundTag stageProgress) {
                return Te.s("使用翻滚（Z） ", stageProgress.getInt(counterKey()), "/", count);
            }

            @Override
            public String counterKey() {
                return "roll";
            }
        }

        /** 选择技能组（按 N 打开技能界面）：由 {@code SkillV2PlayerTryToChooseProfessionTypeC2SPacket} 记录。 */
        record SelectSkillGroup(int count) implements Objective {
            @Override
            public Component text(CompoundTag stageProgress) {
                return Te.s("按 N 选择技能组 ", stageProgress.getInt(counterKey()), "/", count);
            }

            @Override
            public String counterKey() {
                return "skill_group";
            }
        }

        /**
         * 通过世界地图右键路径点传送成功一次：
         * Xaero 世界地图右键「Teleport」会执行 {@code /tp @s x y z}，由
         * {@code WaypointTeleportHandler} 拦截并记录。
         */
        record MapTeleport(String target) implements Objective {
            @Override
            public Component text(CompoundTag stageProgress) {
                return Te.s("使用世界地图传送到 ", target);
            }

            @Override
            public String counterKey() {
                return "map_teleport|" + target;
            }
        }

        /** 在精通页面分配至少 1 点并保存：由 MasterySaveC2SPacket 记录。 */
        record Mastery(int count) implements Objective {
            @Override
            public Component text(CompoundTag stageProgress) {
                return Te.s("按 E 打开物品栏 → 精通，配置 ", count, " 点并保存");
            }

            @Override
            public String counterKey() {
                return "mastery";
            }
        }
    }
}
