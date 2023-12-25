package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

@SpringBootTest(webEnvironment = WebEnvironment.NONE, classes = DemoApplication.class)
@Import(TransactionalEventsTest.TestConfiguration.class)
@RecordApplicationEvents
public class TransactionalEventsTest {
   
    @Autowired
    ApplicationEvents events;

    @Autowired
    MyLogicWithExampleTransaction myLogic;

    @Autowired
    Event3Listener event3Listener;

    @Autowired
    MyRepository myRepository;

    @Test
    void shouldRecordEvent1() {
        myLogic.emitEvent1();
        Assertions.assertThat(events.stream(Event1.class)).size().isEqualTo(1);
    }

    @Test
    void shouldRecordEvent2() {
        try {
            myLogic.emitEvent2AndCrash();
        } catch (Exception ignored) {
            // no-op as we need only to check events
        }
        Assertions.assertThat(events.stream(Event2.class))
            .as("Event2 has been emited")
            .size().isEqualTo(1);
    }

    @Test
    void shouldRecordEvent3Transactionally() {
        try {
            myLogic.emitEvent3InTransactionAndCrash();
        } catch (Exception ignored) {
            // no-op as we need only to check events
        }

        Assertions.assertThat(events.stream(Event3.class))
            .as("Event3 has been emited")
            .size().isEqualTo(1);

        Assertions.assertThat(event3Listener.handledEvents).hasSize(1);
        Assertions.assertThat(event3Listener.handledEventsAfterCommit).hasSize(0);
    }

    @Configuration
    static class TestConfiguration {

        @Bean
        MyLogicWithExampleTransaction myLogic() {
            return new MyLogicWithExampleTransaction();
        }

        @Bean
        Event3Listener myLogicEventListener() {
            return new Event3Listener();
        }
    }

    public static class MyLogicWithExampleTransaction {

        @Autowired
        ApplicationEventPublisher eventPublisher;

        public void emitEvent1() {
            eventPublisher.publishEvent(new Event1());
        }

        public void emitEvent2AndCrash() {
            eventPublisher.publishEvent(new Event2());
            throw new IllegalStateException();
        }

        @Transactional
        public void emitEvent3InTransactionAndCrash() {
            eventPublisher.publishEvent(new Event3());
            throw new IllegalStateException();
        }

    }

    static class Event3Listener {

        List<Event3> handledEvents = new ArrayList<>();
        List<Event3> handledEventsAfterCommit = new ArrayList<>();
        List<Event3> handledEventsAfterComplete = new ArrayList<>();
        
        @EventListener
        public void onEvent3(Event3 event) {
            handledEvents.add(event);
        }

        @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
        public void onEvent3AfterTransactionCommit(Event3 event) {
            handledEventsAfterCommit.add(event);
        }

        @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
        public void onEvent3AfterTransactionComplete(Event3 event) {
            handledEventsAfterComplete.add(event);
        }
    }

    static class Event1 {
    }

    static class Event2 {
    }

    static class Event3 {
    }
}

interface MyRepository extends JpaRepository<MyModel, UUID> {
}

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
class MyModel {
    @Id
    private UUID id;
    private String name;   
}