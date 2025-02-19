package fun.wraq.events.server;

import net.minecraft.util.thread.NamedThreadFactory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPools {
    public static ThreadPoolExecutor clearFireExecutor =
            new ThreadPoolExecutor(3, 5, 10, TimeUnit.MINUTES, new LinkedBlockingQueue<>(),
                    new NamedThreadFactory("WRAQ-CLEAR-FIRE"), new ThreadPoolExecutor.CallerRunsPolicy());
    public static ThreadPoolExecutor dataExecutor =
            new ThreadPoolExecutor(2, 5, 10, TimeUnit.MINUTES, new LinkedBlockingQueue<>(),
                    new NamedThreadFactory("WRAQ-DATA"), new ThreadPoolExecutor.CallerRunsPolicy());
}
