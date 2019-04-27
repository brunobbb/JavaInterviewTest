package com.atguigu;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProdConsumer_Demo {

    public static void main(String[] args) {
        AirCondition airCondition = new AirCondition();

        for (int i = 0; i < 5; i++) {
            new Thread(()->{
                try {
                    airCondition.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();
        }

        for (int i = 0; i < 5; i++) {
            new Thread(()->{
                try {
                    airCondition.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();
        }
    }
}

class AirCondition{
    private int temp = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void increment() throws InterruptedException {
        lock.lock();
        try{
            while (temp != 0){
                condition.await();
            }
            temp ++;
            System.out.println(Thread.currentThread().getName() + "\t温度为: " + temp);
            condition.signalAll();
        }finally {
            lock.unlock();
        }
    }
    public void decrement() throws InterruptedException {
        lock.lock();
        try{
            while (temp == 0){
                condition.await();
            }
            temp --;
            System.out.println(Thread.currentThread().getName() + "\t温度为: " + temp);
            condition.signalAll();
        }finally {
            lock.unlock();
        }
    }
}


