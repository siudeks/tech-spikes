package onlexnet;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class FutureTest {
    
    // test if we can discover loosed threads
    // more: https://www.youtube.com/watch?v=zPhkg8dYysY&t=799s
    @Test
    void testUnusedFutures() throws InterruptedException {

        Callable<Integer> fail = () -> { 
            throw new RuntimeException(); 
        };
        Callable<Integer> success = () -> 1;

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        var c1 = executor.submit(fail);
        var c2 = executor.submit(fail);

        try {
            var result1 = c1.get(1, TimeUnit.SECONDS);
            var result2 = c2.get(1, TimeUnit.SECONDS);

        } catch (Exception ex) {

        } finally {
            // executor.awaitTermination(1, TimeUnit.SECONDS);

            // Assertions.assertThat(c1.isDone()).isTrue();
            // Assertions.assertThat(c2.isDone()).isTrue();
            Assertions.assertThat(executor.getTaskCount()).isEqualTo(2);
            Assertions.assertThat(executor.getPoolSize()).isEqualTo(2);
            Assertions.assertThat(executor.getActiveCount()).isEqualTo(0);
            Assertions.assertThat(executor.getQueue()).isEmpty();
            Assertions.assertThat(executor.getCompletedTaskCount()).isEqualTo(2);
        }
    }
}
