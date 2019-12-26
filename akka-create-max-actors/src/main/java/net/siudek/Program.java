package net.siudek;

import akka.actor.typed.ActorSystem;

/** Utility class with startable main method to run the program. */
public final class Program {

    /** Constructor for static utility class. */
    private Program() {
        // no-op
    }

    /**
     * Entry point of the application.
     * @param notUsed application arguments, currently not used
     */
    public static void main(final String[] notUsed) {
        final var sys = ActorSystem.create(ProlificParent.create(), "sys");
        sys.tell(new ProlificParent.MakeNewChild());
    }
}
