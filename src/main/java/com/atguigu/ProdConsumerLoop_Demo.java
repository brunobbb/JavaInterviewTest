package com.atguigu;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProdConsumerLoop_Demo {
    public static void main(String[] args) {
        MyShareData myShareData = new MyShareData();

        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                myShareData.print5();
            },"PleaseInputThreadName").start();
        }
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                myShareData.print10();
            },"PleaseInputThreadName").start();
        }
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                myShareData.print15();
            },"PleaseInputThreadName").start();
        }
    }
}

class MyShareData{

    private Lock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();
    private int num = 1;

    public void print5(){
        lock.lock();
        try {
            while (num != 1){
                c1.await();
            }
            System.out.println("5");
            num = 2;
            c2.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    public void print10(){
        lock.lock();
        try {
            while (num != 2){
                c2.await();
            }
            System.out.println("10");
            num = 3;
            c3.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    public void print15(){
        lock.lock();
        try {
            while (num != 3){
                c3.await();
            }
            System.out.println("15");
            num = 1;
            c1.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
