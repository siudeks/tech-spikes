# Actor as an orchestrator

[![001-akka-actor-as-function-orchestrator](https://github.com/siudeks/tech-spikes/actions/workflows/001.yml/badge.svg)](https://github.com/siudeks/tech-spikes/actions/workflows/001.yml)

At some point of my coding experience I've found lack of a repeatable pattern of writing well-described, testable pieces of code, responsible for handle a single incoming request (the most often either REST or GraphQL call), next coordinating internal services and compose a single response finally sent back to the sender.

Lets describe the problem with a practical example.
Let's collect a checklist of similar common aspects for an operation like described above, so later on we can verify the checklist vs provided new repeatable pattern.
Warning 1: I am not going to deep dive into Akka typed and async word. I assume you know such concepts.
Warning 2: I am aware of possibility of handling async code with vert.x verticles, async callbacks or flow of reactive streams in many flavors. I will not compare such approaches with actor-based approach for many reasons. Such comparison has been ommited intentionally to make the article shorted and focused on proposed solution.

List of mentioned 'common aspects' consists of:
the operation has a single parametrized entry point where the work is started
when the work is finalized, the operations produces results with some response model or asynchronous fail
flow of work is generally not a linear - some internal services can be asked in different order because of various content of incoming data, time, internal state etc.
all internal services, used by the operation, are asynchronous
the operation is short-living. It means it is not expected to survive restart of host application (if any)

So, let start from an example scenario
A: In the System we have 2 internal services named: A and B. We have to start all of them in parallel instead of sequentially to save time. If one of them can't start, we have to stop both, wait when they are stopped and return information about the whole result of starting (respresented as boolean value). It is actually kind of compensation operation to keep the whole system in consistent state (
B: If we need to stop a service, we have to send 'stop' command only after it answered 'success' for 'start command. Sending 'stop' command when 'start' is in progress can be ignored bu the internal service
C: 'start' command is indempotent, so, if start time outed, we should resend them once again
Sounds simple, isn't it? Let me draw possible variations of described scenario:
Both services started, or
Service A failed, or
Service A time-outed, or
Service B failed, or 
Service B time-outed, or
Both services failed

So, event at he first look, we have 6 cases which we have to cover in such simple scenario with 2 internal services.
@startuml
Request -> Controller: Some web request
Controller -> ServiceA: Start service
ServiceA → Controller: Service started
Controller -> ServiceB: Start service
ServiceB → Controller: Service started
Controller -> ServiceC: Start service
ServiceC → Controller: Service started
Controller → Request: Services started
@enduml

Synchronous 

 what are well-known ways of handle such
Of course we should support:
* supporting all possible combinations of services' responses (with timeouts and exceptions), and
* using thread-safe way, and
* using non-blocking approach, and finally
* being covered by unit tests.
Let's go through some scenarios and diagnose what options we have. 

Simple real scenarios from my past:

Scenario 1
We have 3 external services, which has to be started in some order, and stopped in oposit order

According to my experience, we have at least 3 options of combining asynchronous sub requests to a final results :
Option 1: combining CompletableFuture(s)
Why to use:
Why to avoid:
Option 2: composing Reactive Streams(s)
Why to use:
Why to avoid:
Option 3: using power of Actor(s)
Why to use:
Why to avoid:

Have you heard about Mediator pattern? Do you recognize difference between Orchestration and Synchronization? Have you seen
Spaghetti code, in flavor of asynchrony, quite often without proper unit tests, can appear in many places. Nothing new. Good examples of such code are http controllers' methods, where we are handling input data (probably json object and / or input parameters), we have to do some multistep-asynchronous-conditional magic to return back some response.
The problem is we do not have a good pattern how to write this kind of code other than adding new code, try to keep them as clean as possible and cover with tests. Pack method in try-catch. Add logging.
In fact, if you method has some aspects listed below, let me show you how to put whole function logic into one testable piece of code using Akka actor.

Because your http method has some input and output, let treat them - and call them later on - as a general function. Character of the function (being invoked in http flow) is only some side aspect of the function, irrelevant for the article.
Your function is asynchronous
You function invokes some external services (like database, infrastructure) and, based on invocation result, invokes / repeates some operation and finally returns something to the invoker.

What we should know about Akka? What aspects of Akka are interesting for us?
Akka is a library, not a framework: you can use single actors to solve local problems if you would like to. We would like to focus especially on that feature - using local actors to solve local problems.

Aspects of mediation and behind:
Avoid exhausting resources by disposing 
Graceful termination
Thread-safe sync non-blocking processing
State machine