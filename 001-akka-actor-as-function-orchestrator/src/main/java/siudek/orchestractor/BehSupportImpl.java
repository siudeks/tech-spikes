package siudek.orchestractor;

import java.time.Duration;

import org.springframework.stereotype.Component;

import akka.actor.typed.ActorSystem;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import lombok.Value;

@Component
class BehSupportImpl implements BehSupport, AutoCloseable {

  /**
   * The only one purpose of the Actor System is to run Mediators.
   *
   * <p>The actorSystem is not intended to be used outside of {@link BehSupportImpl} because
   * BehSupport is designed to work in absence of actor-based solution.
   */
  ActorSystem<CreateRequest<?>> actorSystem;

  public BehSupportImpl() {
    var userGuardianBeh = Behaviors.<CreateRequest<?>>setup(ctx -> {
      return Behaviors.receiveMessage(msg -> this.create(ctx, msg));
    });
    actorSystem = ActorSystem.create(userGuardianBeh, "mediators");
  }

  private Behavior<CreateRequest<?>> create(ActorContext<CreateRequest<?>> ctx, CreateRequest<?> msg) {
    ctx.spawnAnonymous(msg.initialBehavior);
    return Behaviors.same();
  }

  @Override
  public void close() {
    actorSystem.terminate();
  }

  @Value
  private class CreateRequest<T> {
    private Behavior<T> initialBehavior;
  }

  @Override
  public void spawn(Behavior<?> behaviorToSpawn,
                    Runnable timeoutHandler,
                    Duration timeout) {

    var request = new CreateRequest<>(SpawnBehavior.create(behaviorToSpawn, timeoutHandler, timeout));
    actorSystem.tell(request);
  }
}
