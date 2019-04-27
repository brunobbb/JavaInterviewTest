package com.atguigu;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        /**
         * CountryEnum使用枚举
         */
        int num = 6;
        CountDownLatch countDownLatch = new CountDownLatch(num);
        Lock lock = new ReentrantLock();
        for (int i = 1; i <= num; i++) {
            new Thread(()->{
                lock.lock();
                try{
                    System.out.println(Thread.currentThread().getName() + "\t国被灭了");
                    countDownLatch.countDown();
                    System.out.println("还剩余 " + countDownLatch.getCount());
                }finally {
                    lock.unlock();
                }
            }, CountryEnum.foreach_CountryEnum(i).getMessage()).start();
        }
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName() + "\t秦国统一");
    }

    /**
     * 0	号同学离开了教室
     * main	班长最后离开教室。。。。。
     * 2	号同学离开了教室
     * 1	号同学离开了教室
     * 4	号同学离开了教室
     * 3	号同学离开了教室
     *
     * 加了CountDownLatch之后
     * 1	号同学离开了教室
     * 还剩余 5
     * 5	号同学离开了教室
     * 还剩余 4
     * 0	号同学离开了教室
     * 还剩余 3
     * 2	号同学离开了教室
     * 还剩余 2
     * 3	号同学离开了教室
     * 还剩余 1
     * 4	号同学离开了教室
     * 还剩余 0
     * main	班长最后离开教室。。。。。
     *
     * 使用countDownLatch.countDown()倒数 并用countDownLatch.await()确认在倒数为0之后再进行操作
     */
    private static void closeDoor() throws InterruptedException {
        int num = 6;
        CountDownLatch countDownLatch = new CountDownLatch(num);
        Lock lock = new ReentrantLock();
        for (int i = 0; i < num; i++) {
            new Thread(()->{
                lock.lock();
                try{
                    System.out.println(Thread.currentThread().getName() + "\t号同学离开了教室");
                    countDownLatch.countDown();
                    System.out.println("还剩余 " + countDownLatch.getCount());
                }finally {
                    lock.unlock();
                }
            },String.valueOf(i)).start();
        }
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName() + "\t班长最后离开教室。。。。。");
    }
}

