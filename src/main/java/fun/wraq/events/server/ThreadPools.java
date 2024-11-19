package fun.wraq.events.server;

import com.mojang.logging.LogUtils;
import net.minecraft.util.thread.NamedThreadFactory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPools {
    public static ThreadPoolExecutor attributeExecutor =
            new ThreadPoolExecutor(20, 100, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(),
                    new NamedThreadFactory("WRAQ-ATTRIBUTES"),
                    new RejectedExecutionHandler() {
                        public void rejectedExecution(final Runnable r, final ThreadPoolExecutor executor) {
                            LogUtils.getLogger().error("attribute executor rejected");
                        }
                    });

    public static ThreadPoolExecutor clearFireExecutor =
            new ThreadPoolExecutor(2, 5, 10, TimeUnit.MINUTES, new LinkedBlockingQueue<>(),
                    new NamedThreadFactory("WRAQ-CLEAR-FIRE"),
                    new RejectedExecutionHandler() {
                        public void rejectedExecution(final Runnable r, final ThreadPoolExecutor executor) {
                            LogUtils.getLogger().error("clear fire executor rejected");
                        }
                    });

    public static ThreadPoolExecutor dataExecutor =
            new ThreadPoolExecutor(2, 5, 10, TimeUnit.MINUTES, new LinkedBlockingQueue<>(),
                    new NamedThreadFactory("WRAQ-DATA"),
                    new RejectedExecutionHandler() {
                        public void rejectedExecution(final Runnable r, final ThreadPoolExecutor executor) {
                            LogUtils.getLogger().error("data executor rejected");
                        }
                    });
}
