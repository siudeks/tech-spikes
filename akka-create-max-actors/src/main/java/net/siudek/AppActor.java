package net.siudek;

import java.time.LocalTime;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.OneForOneStrategy;
import akka.actor.SupervisorStrategy;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import scala.concurrent.duration.Duration;

/**
 * Entry actor of the application.
 *
 * Produces single instance of {@link MySimpleActor}
 */
public final class AppActor extends AbstractActor {

    /** wanna to log? here is your tool */
    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    /** number of instance of {@link MySimpleActor} created sequentially. */
    private int current;

    @Override
    public void preStart() throws Exception {
        super.preStart();
        self().tell(Integer.valueOf(0), ActorRef.noSender());
    }

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return new OneForOneStrategy(-1, Duration.Inf(), t -> {
            log.error("Error handled by instance " + current);
            return OneForOneStrategy.escalate();
        });
    }

    @Override
    public void postStop() throws Exception {
        super.postStop();
    }

    LocalTime whenPrint = LocalTime.now();

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(Integer.class, v -> {
            current = v + 1;

            LocalTime now = LocalTime.now();
            if (now.minus(java.time.Duration.ofMillis(10)).isAfter(whenPrint)) {
                whenPrint = now;
                log.info("Instance " + current);
            }

            var initialActor = context().actorOf(MySimpleActor.props());
            initialActor.tell(Integer.valueOf(current), self());
        }).build();
    }
}
