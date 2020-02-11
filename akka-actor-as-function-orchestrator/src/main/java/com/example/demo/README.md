# I have a dream.

In async programming world, when my logic has only single input and output
BUT
to provide the outpus some biggest work hyave to be done
we are close to make a big, unreadable, not maintaneable piece of code
where we can predict only some of scenarios
where some async operations can never return
where 

In that case, as old developer, what I could show to my colleagues to prove I have repeatable
way of solving that kind of puzzles?

Let's start with some simple enough to do piece of code, and complex enough to reflect real problems.

Let's start.

In my bueatyful application I have some nagic place, called 'webapi' layer. It is set of classes (called Controllers) with methods


Controller method is - the most often - a simple Request/Response operation. At least it seems so from perspective of the invoker.

## Interesting links

- [Akka Typed - Testing](https://doc.akka.io/docs/akka/current/typed/testing.html#controlling-the-scheduler)
- [Mediator pattern](https://en.wikipedia.org/wiki/Mediator_pattern)

