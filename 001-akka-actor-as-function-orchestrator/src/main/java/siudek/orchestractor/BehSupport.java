package siudek.orchestractor;

import java.time.Duration;

import akka.actor.typed.Behavior;

/**
 * Allows to spawn given Behavior and send an initial message.
 *
 * <p>If the actor will not stop working after <code>timeout</code>, <code>timeoutHandler</code> will be informed.
 */
@FunctionalInterface
public interface BehSupport {
    
  void spawn(Behavior<?> behaviorToSpawnAndRun, Runnable timeoutHandler, Duration timeout);
}

