package net.onlex;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.LockModeType;
import javax.transaction.Transactional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@QuarkusTest
@Slf4j
class PanacheTest {
   
    @Inject
    MyClass1 dbService;

    @Test
    @Disabled
    void should_version_using_managed_entity() throws InterruptedException {
        var eId = 1L;
        dbService.create(eId, "my name");
        dbService.update(eId, "name1");
        dbService.test(eId, 1, "name1");
        dbService.update(eId, "name2");
        dbService.test(eId, 2, "name2");
    }

    @SneakyThrows
    @Test
    @Disabled
    void should_protect_readonly_entity() {
        var eId = 1L;
        dbService.create(eId, "my name");
        Semaphore readFlowSem = new Semaphore(0);
        Semaphore readControlSem = new Semaphore(0);

        var executorService = Executors.newCachedThreadPool();
        executorService.submit(() -> dbService.readWaitRead(eId, readFlowSem, readControlSem));
        readControlSem.acquire();

        dbService.update(eId, "name2");

        readFlowSem.release();


        dbService.test(eId, 0, "my name");
    }

    @Inject
    ClientRepository clientRepository;

    /**
     * <p> The semantics of requesting locks of type
     * <code>LockModeType.OPTIMISTIC</code> and
     * <code>LockModeType.OPTIMISTIC_FORCE_INCREMENT</code> are the
     * following.
     *
     * <p> If transaction T1 calls for a lock of type 
     * <code>LockModeType.OPTIMISTIC</code> on a versioned object, 
     * the entity manager must ensure that neither of the following 
     * phenomena can occur:
     * <ul>
     *   <li> P1 (Dirty read): Transaction T1 modifies a row. 
     * Another transaction T2 then reads that row and obtains 
     * the modified value, before T1 has committed or rolled back. 
     * Transaction T2 eventually commits successfully; it does not 
     * matter whether T1 commits or rolls back and whether it does 
     * so before or after T2 commits.
     *   </li>
     *   <li> P2 (Non-repeatable read): Transaction T1 reads a row. 
     * Another transaction T2 then modifies or deletes that row, 
     * before T1 has committed. Both transactions eventually commit 
     * successfully.
     *   </li>
     * </ul>
     *
     * <p> Lock modes must always prevent the phenomena P1 and P2.
     */
    @SneakyThrows
    @Test
    @Disabled
    void dirty_read() {

        var main = new Semaphore(0);
        var finish = new CyclicBarrier(2, main::release);

        var eId = 1L;
        var initialName = "my name";
        var changedName = "my name - modified";

        // prepare initial entity
        dbService.runTransactional(() -> create(eId, initialName));

        var semaphore1 = new Semaphore(0);
        var semaphore2 = new Semaphore(0);

        Callable<String> t1 = () -> {
            @Cleanup
            var onExit = (AutoCloseable) () -> finish.await();

            update(eId, changedName);
            // 1. Inform T2 name is updated
            log.info("Stage 1");
            semaphore1.release();

            // 2. Wait for T2 to read modified value
            semaphore2.acquire();

            return null;
        };

        Callable<String> t2 = () -> {
            @Cleanup
            var onExit = (AutoCloseable) () -> finish.await();

            // 1. After update
            semaphore1.acquire();

            var myEntity = clientRepository.findById(eId);
            var name = myEntity.name;

            // T2 reads name
            log.info("Stage 2");
            semaphore2.release();

            return name;
        };

        var executor = Executors.newCachedThreadPool();
        executor.submit(() -> dbService.runTransactional(t1));
        var t2future = executor.submit(() -> dbService.runTransactional(t2));
        var t2Name = t2future.get();

        main.acquire();

        Assertions.assertThat(t2Name).isEqualTo(initialName);
    }


    @SneakyThrows
    @Test
    void non_repeatable_read() {

        var main = new Semaphore(0);
        var finish = new CyclicBarrier(2, main::release);

        var eId1 = 1L;
        var initialName = "my name";
        var changedName = "my name - modified";

        // prepare initial entities
        dbService.runTransactional(() -> create(eId1, initialName));

        var semaphore1 = new Semaphore(0);
        var semaphore2 = new Semaphore(0);

        Callable<String> t1 = () -> {
            @Cleanup
            var onExit = (AutoCloseable) () -> finish.await();

            var myEntity = clientRepository.findById(eId1, LockModeType.OPTIMISTIC);
            // 1. Inform T2 name has been read
            semaphore1.release();

            // 2. Wait for T2 to modify modified value
            semaphore2.acquire();

            // exit with commiting curent transaction
            return null;
        };

        Callable<String> t2 = () -> {
            @Cleanup
            var onExit = (AutoCloseable) () -> finish.await();

            // 1. After read initial value
            semaphore1.acquire();

            //update(eId1, changedName);

            // T2 reads name
            semaphore2.release();

            return null;
        };

        var executor = Executors.newCachedThreadPool();
        var t1future = executor.submit(() -> dbService.runTransactional(t1));
        var t2future = executor.submit(() -> dbService.runTransactional(t2));

        t1future.get();
        t2future.get();

        main.acquire();
    }


    Void create(Long eId, String name) {
        var entity1 = new MyClient();
        entity1.id = eId;
        entity1.name = name;
        clientRepository.persist(entity1);
        return null;
    }

    void update(Long eId, String newName) {
        var myEntity = clientRepository.findById(eId, LockModeType.NONE);
        myEntity.name = newName;
        clientRepository.flush();
    }
}

interface TestRunnable {
    void run() throws Exception;
}

@Transactional
@ApplicationScoped
class MyClass1 {
    @Inject
    ClientRepository clientRepository;

    @Transactional
    void create(Long eId, String name) {
        var entity1 = new MyClient();
        entity1.id = eId;
        entity1.name = name;
        clientRepository.persist(entity1);        
    }

    @Transactional
    public void update(Long eId, String newName) {
        var myEntity = clientRepository.findById(eId, LockModeType.WRITE);
        myEntity.name = newName;
    }

    @Transactional
    public void test(Long eId, int expectedVersion, String expectedName) {
        var myEntity = clientRepository.findById(eId, LockModeType.READ);
        Assertions.assertThat(myEntity.version).isEqualTo(expectedVersion);
        Assertions.assertThat(myEntity.name).isEqualTo(expectedName);
    }

    @Transactional
    @SneakyThrows
    void readWaitRead(Long eId, Semaphore myFlowSem, Semaphore controlSem ) {
        var myEntity = clientRepository.findById(eId, LockModeType.READ);
        controlSem.release();

        // wait for allowance to exit
        myFlowSem.acquire();
    }

    @Transactional
    @SneakyThrows
    <T> T runTransactional(Callable<T> runnable) {
        return runnable.call();
    }
}

class Stepper<E>  {
    private List<Semaphore> steps = new LinkedList<>();
    public Stepper(int numberOfSteps) {
        for (int i = 0; i < numberOfSteps; i++) {
            steps.add(new Semaphore(0));
        }
    }
}

interface StepDefinition {
    BlockedSide side();
}

enum BlockedSide {
    LEFT,
    RIGHT,
}


class Point {

    // Entry
    // 1st thread locked
    // 2nd thread does logic:
    // 

    private final Semaphore left;
    private final Semaphore right;
    private Point(BlockedSide... side) {
        var sides = Arrays.asList(side);
        left = sides.contains(BlockedSide.LEFT) ? new Semaphore(0) : new Semaphore(1);
        right = sides.contains(BlockedSide.RIGHT) ? new Semaphore(0) : new Semaphore(1);        
    }

    @SneakyThrows
    void await(BlockedSide side) {
        if (side == BlockedSide.LEFT) left.acquire();
        if (side == BlockedSide.RIGHT) right.acquire();
    }
}

