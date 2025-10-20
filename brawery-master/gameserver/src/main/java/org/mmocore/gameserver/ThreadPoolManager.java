package org.mmocore.gameserver;

import org.mmocore.commons.threading.LoggingRejectedExecutionHandler;
import org.mmocore.commons.threading.PriorityThreadFactory;
import org.mmocore.commons.threading.RunnableImpl;
import org.mmocore.commons.threading.RunnableStatsWrapper;
import org.mmocore.gameserver.configuration.config.ServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import static org.mmocore.gameserver.configuration.config.ServerConfig.ENABLE_RUNNABLE_STATS;

public class ThreadPoolManager {
    protected static final Logger LOGGER = LoggerFactory.getLogger(ThreadPoolManager.class);
    private static final long MAX_DELAY = TimeUnit.NANOSECONDS.toMillis(Long.MAX_VALUE - System.nanoTime()) / 2;
    private final ScheduledThreadPoolExecutor scheduledExecutor;
    private final ScheduledThreadPoolExecutor moveScheduledExecutor;
    private final ThreadPoolExecutor executor;
    private final ThreadPoolExecutor moveExecutor;
    private final ThreadPoolExecutor broadcastExecutor;
	private volatile boolean shutdown;

    private ThreadPoolManager() {
        scheduledExecutor = new ScheduledThreadPoolExecutor(ServerConfig.SCHEDULED_THREAD_POOL_SIZE, new PriorityThreadFactory("ScheduledThreadPool", Thread.NORM_PRIORITY), new LoggingRejectedExecutionHandler());
        executor = new ThreadPoolExecutor(ServerConfig.EXECUTOR_THREAD_POOL_SIZE, Integer.MAX_VALUE, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), new PriorityThreadFactory("ThreadPoolExecutor", Thread.NORM_PRIORITY), new LoggingRejectedExecutionHandler());
        moveScheduledExecutor = new ScheduledThreadPoolExecutor(ServerConfig.SCHEDULED_MOVE_POOL_SIZE, new PriorityThreadFactory("ScheduledMovePool", Thread.NORM_PRIORITY), new LoggingRejectedExecutionHandler());
        moveExecutor = new ThreadPoolExecutor(ServerConfig.EXECUTOR_MOVE_POOL_SIZE, ServerConfig.EXECUTOR_MOVE_POOL_SIZE, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), new PriorityThreadFactory("MovePoolExecutor", Thread.NORM_PRIORITY), new LoggingRejectedExecutionHandler());
        moveExecutor.allowCoreThreadTimeOut(true);
        broadcastExecutor = new ThreadPoolExecutor(ServerConfig.EXECUTOR_BROADCAST_POOL_SIZE, ServerConfig.EXECUTOR_BROADCAST_POOL_SIZE, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), new PriorityThreadFactory("MovePoolExecutor", Thread.NORM_PRIORITY), new LoggingRejectedExecutionHandler());
        broadcastExecutor.allowCoreThreadTimeOut(true);
        //Очистка каждые 5 минут
        scheduleAtFixedRate(new RunnableImpl() {
            @Override
            public void runImpl() {
	            if (ENABLE_RUNNABLE_STATS)
		            LOGGER.info(getStats().toString());
	            scheduledExecutor.purge();
                moveScheduledExecutor.purge();
	            executor.purge();
	            moveExecutor.purge();
                broadcastExecutor.purge();
            }
        }, 5L, 5L, TimeUnit.MINUTES);
    }

    public static ThreadPoolManager getInstance() {
        return LazyHolder.INSTANCE;
    }

    public static Runnable wrap(final Runnable r) {
	    return ENABLE_RUNNABLE_STATS ? RunnableStatsWrapper.wrap(r) : r;
    }

    public boolean isShutdown() {
        return shutdown;
    }

    private long validate(long delay) {
        return Math.max(0, Math.min(MAX_DELAY, delay));
    }

    public ScheduledFuture<?> schedule(final Runnable r, final long delay) {
        return schedule(r, validate(delay), TimeUnit.MILLISECONDS);
    }

    public ScheduledFuture<?> schedule(final Runnable r, final long delay, final TimeUnit timeUnit) {
        return scheduledExecutor.schedule(wrap(r), delay, timeUnit);
    }

    public ScheduledFuture<?> scheduleAtFixedRate(final Runnable r, final long initial, final long delay) {
        return scheduleAtFixedRate(r, validate(initial), validate(delay), TimeUnit.MILLISECONDS);
    }

    public ScheduledFuture<?> scheduleAtFixedRate(final Runnable r, final long initial, final long delay, final TimeUnit timeUnit) {
        return scheduledExecutor.scheduleAtFixedRate(wrap(r), initial, delay, timeUnit);
    }

    public ScheduledFuture<?> scheduleAtFixedDelay(final Runnable r, final long initial, final long delay) {
        return scheduleAtFixedDelay(r, validate(initial), validate(delay), TimeUnit.MILLISECONDS);
    }

    public ScheduledFuture<?> scheduleAtFixedDelay(final Runnable r, final long initial, final long delay, final TimeUnit timeUnit) {
        return scheduledExecutor.scheduleWithFixedDelay(wrap(r), initial, delay, timeUnit);
    }

    public void execute(final Runnable r) {
        executor.execute(wrap(r));
    }

    public void executeBroadcast(final Runnable r) {
        broadcastExecutor.execute(wrap(r));
    }

    public ScheduledFuture<?> scheduleMove(final Runnable r, long delay) {
        return moveScheduledExecutor.schedule(() -> moveExecutor.execute(r), validate(delay), TimeUnit.MILLISECONDS);
    }

    public <E> CompletableFuture<E> submitMove(Supplier<E> supplier) {
        CompletableFuture<E> future = new CompletableFuture<>();
        Runnable wrapper = () -> {
            try {
                E result = supplier.get();
                future.complete(result);
            } catch (Exception e) {
                future.completeExceptionally(e);
            }
        };

        moveExecutor.execute(wrapper);
        return future;
    }

    public void shutdown() throws InterruptedException {
        shutdown = true;
        try {
            scheduledExecutor.shutdown();
            moveScheduledExecutor.shutdown();
            moveExecutor.shutdown();
            broadcastExecutor.shutdown();

            scheduledExecutor.awaitTermination(10, TimeUnit.SECONDS);
            moveScheduledExecutor.awaitTermination(10, TimeUnit.SECONDS);
            moveExecutor.awaitTermination(10, TimeUnit.SECONDS);
            broadcastExecutor.awaitTermination(10, TimeUnit.SECONDS);
        } finally {
            executor.shutdown();
            executor.awaitTermination(1, TimeUnit.MINUTES);
        }
    }

    public CharSequence getStats() {
        final StringBuilder list = new StringBuilder();

        List<ThreadPoolExecutor> executorList = Arrays.asList(
                scheduledExecutor,
                moveScheduledExecutor,
                executor,
                moveExecutor,
                broadcastExecutor);

        for (ThreadPoolExecutor executor : executorList) {
            list.append(((PriorityThreadFactory) executor.getThreadFactory()).getName()).append("\n");
            list.append("=================================================\n");
            list.append("\tgetActiveCount: ...... ").append(executor.getActiveCount()).append('\n');
            list.append("\tgetCorePoolSize: ..... ").append(executor.getCorePoolSize()).append('\n');
            list.append("\tgetPoolSize: ......... ").append(executor.getPoolSize()).append('\n');
            list.append("\tgetLargestPoolSize: .. ").append(executor.getLargestPoolSize()).append('\n');
            list.append("\tgetMaximumPoolSize: .. ").append(executor.getMaximumPoolSize()).append('\n');
            list.append("\tgetCompletedTaskCount: ").append(executor.getCompletedTaskCount()).append('\n');
            list.append("\tgetQueuedTaskCount: .. ").append(executor.getQueue().size()).append('\n');
            list.append("\tgetTaskCount: ........ ").append(executor.getTaskCount()).append('\n');
        }
        return list;
    }

    private static class LazyHolder {
        private static final ThreadPoolManager INSTANCE = new ThreadPoolManager();
    }
}
