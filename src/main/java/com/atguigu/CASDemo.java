package com.atguigu;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 1. CAS是什么? ==> compareAndSwap
 *      比较并交换
 */
public class CASDemo {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(5);
        System.out.println(atomicInteger.compareAndSet(5, 2000) + "\t" + atomicInteger.get());
        System.out.println(atomicInteger.compareAndSet(5, 1000) + "\t" + atomicInteger.get());
    }


}
