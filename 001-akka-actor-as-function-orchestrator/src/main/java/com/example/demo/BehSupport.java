package com.example.demo;

import java.time.Duration;
import java.util.UUID;

import akka.NotUsed;
import akka.actor.typed.Behavior;
import akka.actor.typed.Terminated;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import akka.actor.typed.javadsl.TimerScheduler;

/**
 * Allows to spawn given Behavior and send an initial message.
 *
 * If the actor will not stop working after <code>timeout</code>, <code>timeoutHandler</code> will be informed.
 */
@FunctionalInterface
public interface BehSupport {
    
    void spawn(Behavior<?> behaviorToSpawnAndRun, Runnable timeoutHandler, Duration timeout);
}

