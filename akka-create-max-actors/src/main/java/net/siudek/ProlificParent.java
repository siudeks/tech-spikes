package net.siudek;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

/**
 * Entry actor of the application.
 *
 * Produces single instance of {@link MySimpleActor}
 */
public final class ProlificParent
       extends AbstractBehavior<ProlificParent.MakeNewChild> {

  /** Command to produce a child. */
  public static class MakeNewChild {
  }

  /**
   * Method to create initial instance of the behavior.
   * @return Behavior of just created instance.
   */
  public static Behavior<MakeNewChild> create() {
    return Behaviors.setup(ProlificParent::new);
  }

  /**
   * Internal constructor to invoke super's constructor.
   * @param context required by super's constructor
   */
  private ProlificParent(final ActorContext<MakeNewChild> context) {
    super(context);
  }

  @Override
  public Receive<MakeNewChild> createReceive() {
    return newReceiveBuilder()
           .onMessage(MakeNewChild.class, this::onNewChildCmd).build();
  }

  /** How big group of children should be reported on console. */
  private static final int SIZE_OF_REPORTED_CHILDREN_GROUP = 10;

  /** Number of currently created childrens. */
  private int counter;

  /**
   * Creates a new child and logs info about creation when new group is started.
   * @param cmd Request to create one more child
   * @return {@link Behaviors#same} to still allow create childrens
   */
  private Behavior<MakeNewChild> onNewChildCmd(final MakeNewChild cmd) {
    final var childName = String.format("child-%1$d", ++counter);
    final var child = super.getContext().spawn(Child.create(), childName);

    final var self = this.getContext().getSelf();
    child.tell(new Child.WelcomeOnTheWorld(self));

    if (counter % SIZE_OF_REPORTED_CHILDREN_GROUP == 0) {
      System.out.println(childName);
    }

    return Behaviors.same();
  }
}
