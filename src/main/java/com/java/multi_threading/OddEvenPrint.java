package com.java.multi_threading;

public class OddEvenPrint {

    private int number = 1;
    private final int MAX = 10;

    public synchronized void printOdd(){
        while( number <= MAX){

            while (number <= MAX && number % 2 == 0){

                try{
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            if( number <= MAX){
                System.out.println(Thread.currentThread().getName()+ ":"+ number);
                number++;
                notifyAll();
            }
        }

    }
    public synchronized void printEven(){
        while( number <= MAX){

            while (number <= MAX && number % 2 != 0){

                try{
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            if( number <= MAX){
                System.out.println(Thread.currentThread().getName()+ ":"+ number);
                number++;
                notifyAll();
            }
        }

    }
}
