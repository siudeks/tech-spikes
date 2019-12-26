package net.siudek;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import lombok.Value;

/**
 * Actor designed to
 * create new clone of themself (to use whole memory) and
 * increase a global counter (to measure number of created instances).
 */
public final class Child extends AbstractBehavior<Child.WelcomeOnTheWorld> {

    /** Initial info sent from parent when the child actor has been created. */
    @Value
    public static class WelcomeOnTheWorld {
        /**
         * Reference to parent actor where the action of makin the child
         * has been invoked.
         */
        private ActorRef<ProlificParent.MakeNewChild> parent;
    }

    /**
     * Allows to create new instance.
     * @return Just created behavior
     */
    static Behavior<WelcomeOnTheWorld> create() {
        return Behaviors.setup(Child::new);
    }

    /**
     * Internal constructor.
     * @param context required by super's constructor
     */
    private Child(final ActorContext<WelcomeOnTheWorld> context) {
        super(context);
    }

    /**
     * Boilerplate - prepare handling for incoming messages.
     *
     * @return {@link AbstractBehavior#createReceive}
     */
    @Override
    public Receive<WelcomeOnTheWorld> createReceive() {
        return newReceiveBuilder()
               .onMessage(WelcomeOnTheWorld.class, this::askAboutSiblings)
               .build();
    }

    /**
     * Invoked by the parent allows to request a new child.
     *
     * @param msg welcome mesage from the parent.
     * @return {@link Behaviors#ignore} because it doesn't need consume
     *         more messages.
     */
    private Behavior<WelcomeOnTheWorld> askAboutSiblings(
            final WelcomeOnTheWorld msg) {
        msg.parent.tell(new ProlificParent.MakeNewChild());
        return Behaviors.ignore();
    }
}
