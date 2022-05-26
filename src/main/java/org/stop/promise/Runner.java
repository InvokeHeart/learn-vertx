package org.stop.promise;

public class Runner {
    public static void run(final Runnable hooker,
                           final String name) {
        final Thread thread = new Thread(hooker);
        // Append Thread id
        thread.setName(name + "-" + thread.getId());
        thread.start();
    }
}
