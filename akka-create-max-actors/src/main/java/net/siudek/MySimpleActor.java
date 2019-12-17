package net.siudek;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;

/**
 * Actor designed to create new clone of themself and increase global counter.
 */
public final class MySimpleActor extends AbstractActor {

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
