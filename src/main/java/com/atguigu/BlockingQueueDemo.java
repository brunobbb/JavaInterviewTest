package com.atguigu;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 队列
 *
 * 阻塞队列
 *
 *
 */
public class BlockingQueueDemo {
    public static void main(String[] args) {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
        System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));

        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        //获取第一个队首元素
        //System.out.println(blockingQueue.element());
        //当队列已满 抛出异常
        //Exception in thread "main" java.lang.IllegalStateException: Queue full
        //System.out.println(blockingQueue.add("d"));



    }
}
