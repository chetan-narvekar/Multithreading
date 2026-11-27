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
        