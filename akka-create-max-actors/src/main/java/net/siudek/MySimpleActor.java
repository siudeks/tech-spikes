package net.siudek;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * Actor designed to create new clone of themself and increase global counter.
 */
public final class MySimpleActor extends AbstractActor {

    /** wanna to log? here is your tool */
    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(Integer.class, v -> {
            sender().tell(v, ActorRef.noSender());
        }).build();
    }

    /**
     * Produces Props used by Akka to produce new instances of
     * {@link MySimpleActor}.
     *
     * @return Props
     */
    public static Props props() {
        return Props.create(MySimpleActor.class);
    }

}
