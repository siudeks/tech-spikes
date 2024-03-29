package net.siudek.dev.example1;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import akka.actor.typed.scaladsl.Behaviors;
import net.siudek.dev.orchestractor.BehSupport;

/**
 * Example http controller to show timeout on {@code BehSupport}.
 *
 * <p>usage
 * <ul>
 * <li>run the application using mvn spring-boot:run`
 * <li>wait when appliction run
 * <li>open http://localhost:8080/a
 * <li>wait 5 seconds
 * <li>see 'empty!' response
 * </ul>
 *
 * <p>What happens? The controller uses {@see BehSupport} to run an empty behavior.
 * Because the behavior is 'never ending', TBD 
 */
@RestController
public class MyController1 {

  @Autowired
  private BehSupport runner;

  /** TBD. */
  @GetMapping(path = "/a")
  public CompletionStage<String> hello() {

    var result = new CompletableFuture<String>();
    var emptyBehavior = Behaviors.empty();
    runner.run(emptyBehavior, () -> result.complete("empty!"), Duration.ofSeconds(5));

    return result
        .thenApply(Object::toString);
  }
}