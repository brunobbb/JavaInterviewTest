package com.atguigu;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;


public class ABADemo {
    static AtomicReference<Integer> atomicReference = new AtomicReference<>(100);
    static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100,1 );
    public static void main(String[] args) {
        new Thread(()->{
            atomicReference.compareAndSet(100,102);
            atomicReference.compareAndSet(102,100);
        },"a").start();

        new Thread(()->{
            //暂停1秒钟，保证a线程完成了一次ABA操作
            try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
            System.out.println(atomicReference.compareAndSet(100, 2019) + "\t" + atomicReference.get());
        },"b").start();

//**********************以下是ABA问题的解决*****************************
        try { TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) { e.printStackTrace(); }


        new Thread(()->{
            atomicStampedReference.compareAndSet(100,102,1,2);
            atomicStampedReference.compareAndSet(102,100,2,3);
        },"c").start();

        new Thread(()->{
            //暂停1秒钟，保证a线程完成了一次ABA操作
            try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
            System.out.println(atomicStampedReference.compareAndSet(100, 2019,1,4) + "\t" + atomicStampedReference.getReference());
        },"d").start();
    }
}

