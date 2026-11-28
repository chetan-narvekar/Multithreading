What is a process and what is a thread ?

->  A process is an instance of a program that is currently being executed by the operating system.
    When we execute an application the os creates a process and allocates resources such as :
        Virtual memory address space
        Heap and Stack memory
        Process ID
        Security and execution context
    Each process has its own isolated virtual address space and this provides isolation meaning one process generally cannot directly access another process's context/memory.

->  A thread is the smallest unit of execution that the operating system schedules on a CPU 
    A process can own multiple threads and these threads share the process's resources such as 
        Heap Memory
        Static Variables
        Other process level resources
    A process maintains its own :
        Stack Memory
        Program counter
        CPU register
        Thread local storage 

e.g :

    When we start the springboot application 
        The operating system creates its a process for the JVM 
        The JVM initializes its runtime memory areas 
        The JVM creates the main thread
        When request arrives , the embedded tomcat server uses worker thread to process request
        Multiple threads executes concurrently within the same JVM process.

        Threads can shares the same heap area of process but maintains its owm stacks.

    What happens if one thread crashes?
    
        An uncaught excpetion in ordinary java thread normally terminates that thread not the entire JVM process. 
        But if main thread crashes then the whole process is terminated.

What is the difference between concurrency and parallelism.

    Concurrency is the ability of a system to manage multiple tasks during overlappinhg periods of time.
    On a single core cpu the operating system achieves the concurrency using context switching where as in multicore cpu concurrent tasks may run in parallel.

    Parallelism means executing the multiple taks physically at the same time using multiple processor core


What are the different states of a thread?

    NEW
    RUNNABLE
    BLOCKED
    TERMINATED
    WAITING
    TIMED_WAITING

    1. NEW :

        A thread is in the new state when we create a thread object but havent called start method yet.
        
        Thread thread = new Thread();
        System.out.println(thread.getState());

    2. RUNNABLE:
    
        When we invoke the start method, thread moves from new to runnable.
        This states mean thread is eligible to execute or is currently executing.( java does not have the running state in ThreadStatet.ENUM )
        The JVM and operating system decides when to schedule a thread on cpu.

        thread.start();

    3. BLOCKED:
    
        A thread enters the blocked state when is waiting to accquire an intrinsic monitor lock to enter a synchronized method or block.

        public class ThreadExample{
            
            public synchronized void printMessage(){
                System.out.println("OK");
            }

            public static void main(String[] args){
                
                ThreadExample threadExample = new ThreadExample();

                Thread t1 = new Thread(obj::printMessage);
                Thread t2 = new Thread(obj::printMessage);

                t1.start(); 
                t2.start();

            }
        }

        Consider the thread 1 accquires the objects monitor lock then thread 2 will be in blocked state.
        Blocked specifically means waiting for an intrinsic monitor lock nor every kind of lock contention.

    4. WAITING:

        A thread enters the waiting state when it waits indefinitely for another thread to perform a particular action.
        
        Common methods that cause waiting:
            1. Object.wait()
            2. Thread.join()
            3. LockSupport.park()

    5. TIMED_WAITING:
    
        A thread enters in this state when it waits for specifc amount of time.
        
        Common Methods:
            1. Thred.sleep(111);
            2. Thread.join(2344);
            3. Object.wait(1111);

        Thread t1 = new Thread(()->{
            try{
                Thread.sleep(300);
            }catch(InterruptedException e){
                Thread.currentThread.inturrupt();
            }
        );

        t1.start();

        Unless interrupt is called , thread enters in timed waiting for the specified time.

    6. TERMINATED:

        A thread enters in terminated state only when its execution completes.
        
        this happens when:
            1. the run() method completes normally.
            2. AN uncaught exception terminates the thread.

        Once a thread is terminated , it cannot be restarted by calling the start() methid again. Doing so throws IllegalThreadStateExecption

What is the difference between start() and run()?

    -> start() creates a new thread of execution whereas calling run () directly executes method like a normal method call in the current thread.

    -> start():

        The JVM arranges for the newely started thread to execute its run() method
        The thread is schedules by the JVM and operating system
        We cannot call start() more than once on the same Thread object.

    -> run() :
        
        it contains the actual task that needs to be executed and calling it directly does not create a new thread.
        we can invoke it multiple times like on ordinary method.

    Conceptually:

        main thread -> Calls t1.start() -> JVM thread start mechanism initiates a new thead of execution
        -> New thread becomes runnable -> JVM invokes run() on the new thread

What happens internally when you call Thread.start()?

    1. Thread state validation :
        JVM checks whether the thread has already been started , if it has Java throws IllegalThreadStateException
    
    2. JVM and OS interaction :
        JVM initiates the underlying thread using its platform specific implementation, this a platform thread it involves creating an OS managed threads.

    3. Thread resource initialization :
        The new thread gets its own execution context including stack , process counter, register state. It shares the process the process level resources and heap with other threads.

    4. Thread Scheduling :
        The new thread becomes eligible for execution. The OS scheduler decides when it gets CPU time.

    5. Execution of run() :
        When thread gets scheduled the JVM executes its run method on the newely created thread.

    6. Thread Termination :
        Once run() completes or exits due to exception the thread gets TERMINATED

    Upon calling start() method does not gurantee that thread will be executed right away, it only makes that thread eligible for execution.


What is the difference between sleep() and wait()?
    
    Both the sleep() and wait() are used to pause the execution of a thread but they serve different purpose.

    Thread.sleep():
        
        sleep() is a static method of the thread class and pauses the execution for specific duration.
        It does not releases the monitoer lock held by the thread 
        It is primarily used when we want to introduce a delay in thread execution and when this is called the thread is moved in timed waiting state.

    Object.wait() / Object.wait(2999):
        
        wait() is a method of object class and is used for inter-thread communication.
        It caused to release the monitor lock of that object and enter in the waiting or timed waiting state.
        The other methods in object class such as notify and notifyAll() help to notify after the current thread completes the execution
        notify() wakes one arbitary selected thread waiting on on the Objects monitor
        notifyAll() wakes all thread waiting on the object's monitor
        both notify() and notifyAll() wake the waiting thread but the notified thread cannot continue until the notifying thread releases the monitor.

What is the difference between join() and yield()?
    
    join() ->   Makes the current thread wait until another thread completes its exection. Simply it provides a defined completion condition .
    yield() ->  Tells the scheduler that the current thread is willing to give up its current cpu time slice. It is only a hint not a gurantee, Scheduler may ignore it.

What is a daemon thread?
    
    A daemon thread is background thread that provides supporting services to user thread.
    The JVM does not wait for daemon thread to complete when the user thread have terminated.

    IN SIMPLE TERMS :

    User thread: Performs the main application work . The JVM stays alive until the any user thread is alive.
    Daemon thread: Performs the background or supporting tasks. It does not prevent the JVM from shutting down.


    How to create daemon thread : 
        thread.setDaemon(true)

    Important Points:
        Call the setDaemon() method before starting the thread start().
        Calling it after thread started throws IllegalThreadStateException.
        A newely created thread inherits the status of deamon thread from its parent.
        A main thread is ususally user thread by default.
        Daemon status can be cheked using isDaemon() method.

    Examples of Daemon Thread:
        JVM Garbage collector thread
        Some JVM background service threads
        Monitoring and maintainance threads

    Never rely on daemon thread for critical tasks that require guranteed completion, cleanup or data persistence.

    We can create thread pool of deamon threads in executor service:
    
        ExecutorService executor = Executor.newFixedThreadPool( 2, runnable -> {
                                                                                    Thread t = new Thread(runnable);
                                                                                    t.setDaemon(true;
                                                                                    return t;
                                                                                });

Can we restart a Java thread?

    NO , in Java a thread object cannot be restarted once its execution has completed. If done so it will throw IllegalThreadStateException.

    But we can reuse the thread using executor service to execute multiple tasks.