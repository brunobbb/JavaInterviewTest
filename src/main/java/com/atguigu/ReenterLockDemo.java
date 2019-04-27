package com.atguigu;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁/递归锁
 *
 * 指的是同一线程外层函数获得锁之后，内层递归函数仍然能获取该锁的代码，
 * 在同一线程在外层方法获取锁的时候，在进入内层方法会自动获取锁。
 *
 * 也就是说，线程可以进入任何一个他已经拥有的锁所同步着的代码块。
 *
 */
public class ReenterLockDemo {
    public static void main(String[] args) {
        Phone phone = new Phone();

        new Thread(()->{
            phone.sendSMS();
        },"t1").start();

        new Thread(()->{
            phone.set();
        },"t2").start();


    }
}

class Phone{
    public synchronized void sendSMS(){
        System.out.println(Thread.currentThread().getName() + "\t synchronized 发短信");
        sendEmail();
    }
    public synchronized void sendEmail(){
        System.out.println(Thread.currentThread().getName() + "\t synchronized 发邮件");
    }
    Lock lock = new ReentrantLock();

    public void set (){
        lock.lock();
        lock.lock();
        try{
            System.out.println(Thread.currentThread().getName() + "\t ReentrantLock set");
            get();
        }finally {
            lock.unlock();
            lock.unlock();
        }
    }

    public void get (){
        lock.lock();
        try{
            System.out.println(Thread.currentThread().getName() + "\t ReentrantLock get");
        }finally {
            lock.unlock();
        }
    }
}
