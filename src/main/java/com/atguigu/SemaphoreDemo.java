package com.atguigu;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreDemo {
    public static void main(String[] args) {
        //限制资源总数为3个
        Semaphore semaphore = new Semaphore(3);

        //模拟有6个线程在争抢3个资源
        for (int i = 1; i <= 6; i++) {
            new Thread(()->{
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "\t 获取到了");
                    //让线程暂停3秒，旨在让线程持有资源3秒
                    try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    semaphore.release();
                    System.out.println(Thread.currentThread().getName() + "\t 离开了");
                }
            },String.valueOf(i)).start();
        }
    }
}
