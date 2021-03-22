package org.solar.editor.core.jme.injfx;

import com.ss.rlib.common.util.ArrayUtils;
import com.ss.rlib.common.util.array.Array;
import com.ss.rlib.common.util.array.ArrayFactory;
import com.ss.rlib.common.util.array.ConcurrentArray;
import org.jetbrains.annotations.NotNull;

/**
 * The executor for executing tasks in application thread.
 *
 * @author JavaSaBr
 */
public class ApplicationThreadExecutor {

    private static final ApplicationThreadExecutor INSTANCE = new ApplicationThreadExecutor();

    public static @NotNull ApplicationThreadExecutor getInstance() {
        return INSTANCE;
    }

    /**
     * The list of waiting tasks.
     */
    @NotNull
    private final ConcurrentArray<Runnable> waitTasks;

    /**
     * The list of tasks to execute.
     */
    @NotNull
    private final Array<Runnable> execute;

    private ApplicationThreadExecutor() {
        this.waitTasks = ArrayFactory.newConcurrentAtomicARSWLockArray(Runnable.class);
        this.execute = ArrayFactory.newArray(Runnable.class);
    }

    /**
     * Add the task to execute.
     *
     * @param task the new task.
     */
    public void addToExecute(@NotNull Runnable task) {
        ArrayUtils.runInWriteLock(waitTasks, task, Array::add);
    }

    /**
     * Execute the waiting tasks.
     */
    public void execute() {

        if (waitTasks.isEmpty()) {
            return;
        }

        ArrayUtils.runInWriteLock(waitTasks, execute, ArrayUtils::move);
        try {
            execute.forEach(Runnable::run);
        } finally {
            execute.clear();
        }
    }
}
